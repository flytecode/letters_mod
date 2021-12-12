package com.cs32.lettersmod.item.custom;

import com.cs32.lettersmod.LettersMod;
import com.cs32.lettersmod.container.ModContainers;
import com.cs32.lettersmod.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Envelope extends Item {

  private boolean isOpen;
  private static final ITextComponent title = new TranslationTextComponent("container.cs32.lettersmod.envelope");

  public Envelope(String name, boolean isOpen) {
    super(new Item.Properties().group(LettersMod.ITEM_GROUP).maxStackSize(1));
    this.setRegistryName(LettersMod.MOD_ID, name);
    this.isOpen = isOpen;
  }

  // adds tags to the item
  @Override
  public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
    if(stack.hasTag() && stack.getTag().contains("AddressedTo", Constants.NBT.TAG_STRING) && !stack.getTag().getString("AddressedTo").isEmpty()) {
      tooltip.add(new TranslationTextComponent("tooltip.lettersmod.to", new StringTextComponent(stack.getTag().getString("AddressedTo"))));
    }
    if(stack.hasTag() && stack.getTag().contains("AddressedFrom", Constants.NBT.TAG_STRING) && !stack.getTag().getString("AddressedFrom").isEmpty()) {
      tooltip.add(new TranslationTextComponent("tooltip.lettersmod.from", new StringTextComponent(stack.getTag().getString("AddressedFrom"))));
    }
    if(stack.hasTag() && stack.getTag().contains("delivery_failed", Constants.NBT.TAG_BYTE) && stack.getTag().getBoolean("delivery_failed")) {
      tooltip.add(new TranslationTextComponent("tooltip.lettersmod.delivery_failed"));
    }
  }

  // add tag for To, as well as set the item to the player's hand
  public static void setToName(PlayerEntity player, Hand hand, ItemStack stack, String s) {
    putStringChecked(stack, "AddressedTo", s);
    player.setHeldItem(hand, stack);
  }

  // add tag for From, as well as set the item to the player's hand
  public static void setFromName(PlayerEntity player, Hand hand, ItemStack stack, String s) {
    putStringChecked(stack, "AddressedFrom", s);
    player.setHeldItem(hand, stack);
  }

  // Get the string for a tag on an item.
  public static String getString(ItemStack stack, String key) {
    if(stack.hasTag()) {
      return stack.getTag().getString(key);
    }
    return "";
  }

  // When the stack has no tag, set the tag, otherwise modify it
  public static void putStringChecked(ItemStack stack, String key, String value) {
    if(!stack.hasTag()) {
      stack.setTag(new CompoundNBT());
    }
    stack.getTag().putString(key, value);
  }

  // If the original item has a tag and the target key, then copy it over to the new item (for envelopes)
  protected static void copyTagString(ItemStack original, ItemStack newStack, String key) {
    if(original.hasTag() && original.getTag().contains(key, Constants.NBT.TAG_STRING)) {
      putStringChecked(newStack, key, original.getTag().getString(key));
    }
  }

  /**
   * Converts between open and closed envelope. On open to closed conversion,
   * destroys stamp slot.
   *
   * @param stack - Stack to convert
   * @return Optional of converted stack, or empty optional if capabilities could not be found
   */
  public static Optional<ItemStack> convert(ItemStack stack) {
    boolean fromOpen = stack.getItem() == ModItems.ENVELOPE_OPEN;
    // if item is either open or closed envelope,
    if(fromOpen || stack.getItem() == ModItems.ENVELOPE_CLOSED) {
      LazyOptional<IItemHandler> hOpt = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
      if(hOpt.isPresent()) {
        IItemHandler handler = hOpt.orElse(null);
        // set the new item to a closed or open envelope based on what exists via fromOpen boolean
        ItemStack newStack = new ItemStack(fromOpen ? ModItems.ENVELOPE_CLOSED : ModItems.ENVELOPE_OPEN);
        LazyOptional<IItemHandler> nHOpt = newStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
        if(nHOpt.isPresent()) {
          IItemHandler newHandler = nHOpt.orElse(null);
          if(newHandler instanceof ItemStackHandler) {
            ItemStackHandler newH = (ItemStackHandler) newHandler;
            for(int i = 0; i < Math.min(newH.getSlots(), handler.getSlots()); i++) {
              newH.setStackInSlot(i, handler.getStackInSlot(i));
            }
            copyTagString(stack, newStack, "AddressedTo");
            copyTagString(stack, newStack, "AddressedFrom");
            return Optional.of(newStack);
          }
        }
      }
    }
    return Optional.empty();
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
    ItemStack stack = playerIn.getHeldItem(handIn);
    if(!stack.isEmpty() && stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) != null && playerIn instanceof ServerPlayerEntity) {
      if(stack.getItem() == ModItems.ENVELOPE_OPEN) {
        openGUI((ServerPlayerEntity) playerIn, stack);
        return ActionResult.resultSuccess(stack);
      } else if(stack.getItem() == ModItems.ENVELOPE_CLOSED) {
        Optional<ItemStack> open = convert(stack);
        // if the stack is converted, get the itemstack
        if(open.isPresent()) {
          return ActionResult.resultSuccess(open.get());
        }
      }

    }
    return super.onItemRightClick(worldIn, playerIn, handIn);
  }

  public static EnvelopeContainer getClientContainer(int id, PlayerInventory playerInventory, PacketBuffer extra) {
    if(extra.readableBytes() > 0) {
      try {
        String fromName = "";
        if(extra.readableBytes() > 0) {
          fromName = extra.readString(35);
        }
        return new EnvelopeContainer(id, playerInventory, new ItemStackHandler(28), fromName);
      } catch(IndexOutOfBoundsException e) {
      }
    }
    return new EnvelopeContainer(id, playerInventory, new ItemStackHandler(28));
  }

  public static IContainerProvider getServerContainerProvider(ItemStack stack) {
    return (id, playerInventory, serverPlayer) -> new EnvelopeContainer(id, playerInventory, stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null));
  }

  public static void openGUI(ServerPlayerEntity player, ItemStack stack) {
    IContainerProvider provider = getServerContainerProvider(stack);
    INamedContainerProvider namedProvider = new SimpleNamedContainerProvider(provider, title);
    NetworkHooks.openGui(player, namedProvider, buf -> {
      if(stack.hasTag()) {
        if(stack.getTag().contains("AddressedFrom", Constants.NBT.TAG_STRING)) {
          buf.writeString(stack.getTag().getString("AddressedFrom"), 35);
        }
      }
    });
  }

  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, CompoundNBT nbt) {
    return new EnvelopeCapabilityProvider(stack, nbt, isOpen);
  }

  public static class EnvelopeContainer extends Container {

    private final int SLOT_COUNT;
    private final IItemHandler items;
    public final String clientStartFromName;

    public EnvelopeContainer(int id, IInventory playerInventory, IItemHandler items) {
      this(id, playerInventory, items, "");
    }

    public EnvelopeContainer(int id, IInventory playerInventory, IItemHandler items, String fromName) {
      super(ModContainers.ENVELOPE, id);
      this.clientStartFromName = fromName; // TODO: set this to player name
      this.items = items;
      this.SLOT_COUNT = items.getSlots();
      addOwnSlots();
      addPlayerSlots(playerInventory);
    }

    private void addPlayerSlots(IInventory playerInventory) {
      // Slots for the main inventory
      for(int row = 0; row < 3; ++row) {
        for(int col = 0; col < 9; ++col) {
          int x = 8 + col * 18;
          int y = row * 18 + 96;
          this.addSlot(new Slot(playerInventory, col + row * 9 + 9, x, y));
        }
      }

      // Slots for the hotbar
      for(int row = 0; row < 9; ++row) {
        int x = 8 + row * 18;
        int y = 154;
        this.addSlot(new Slot(playerInventory, row, x, y));
      }
    }

    private void addOwnSlots() {
      for(int i = 0; i < 27; i++) {
        int yCoord = i / 9 * 18; // 9 slots fit per row, 18 is size of the slot texture
        int xCoord = i % 9 * 18; // 0, 1*18, 2*18, 3*18, loop per row
        this.addSlot(new SlotItemHandler(items, i, 8 + xCoord, 26 + yCoord));
      }
      this.addSlot(new SlotItemHandler(items, 27, 8 + 8 * 18, 6));
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.inventorySlots.get(index);

      if(slot != null && slot.getHasStack()) {
        ItemStack itemstack1 = slot.getStack();
        itemstack = itemstack1.copy();

        if(itemstack1.getItem() == ModItems.STAMP && index >= SLOT_COUNT && !this.mergeItemStack(itemstack1, 27, 28, false)) {
          return ItemStack.EMPTY;
        } else if(index < SLOT_COUNT) {
          if(!this.mergeItemStack(itemstack1, SLOT_COUNT, this.inventorySlots.size(), true)) {
            return ItemStack.EMPTY;
          }
        } else if(!this.mergeItemStack(itemstack1, 0, SLOT_COUNT, false)) {
          return ItemStack.EMPTY;
        }

        if(itemstack1.isEmpty()) {
          slot.putStack(ItemStack.EMPTY);
        } else {
          slot.onSlotChanged();
        }
      }

      return itemstack;
    }

    @Override
    public boolean canInteractWith(PlayerEntity player) {
      if(player.getHeldItemMainhand().getItem() == ModItems.ENVELOPE_OPEN || player.getHeldItemOffhand().getItem() == ModItems.ENVELOPE_OPEN) {
        if(player.getHeldItemMainhand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null) == this.items) {
          return true;
        } else if(player.getHeldItemOffhand().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null) == this.items) {
          return true;
        }
        return false;
      }
      return false;
    }

  }

  public static class EnvelopeCapabilityProvider implements ICapabilitySerializable<CompoundNBT> {

    private ItemStackHandler handler;
    public final LazyOptional<ItemStackHandler> handlerOptional;
    private ItemStack stack;

    public EnvelopeCapabilityProvider(ItemStack stack, CompoundNBT compound, boolean isOpen) {
      this.handler = new ItemStackHandler(isOpen ? 28 : 27);
      this.handlerOptional = LazyOptional.of(() -> handler);
      this.stack = stack;
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction dir) {
      if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && !stack.isEmpty()) {
        return handlerOptional.cast();
      }
      return LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
      return handler.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
      if(nbt != null) {
        handler.deserializeNBT(nbt);
      }
    }

  }

}