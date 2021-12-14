package com.cs32.lettersmod.tileentity;

import com.cs32.lettersmod.saveddata.SavedDataClass;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import com.cs32.lettersmod.item.ModItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class MailboxTile extends TileEntity {

  // according to this post, need a wrapper thing so that you can only take out
  // https://forums.minecraftforge.net/topic/86173-1152-add-item-to-output-slot-player-can-only-remove/?do=findComment&comment=404876
  private ItemStackHandler itemHandler;
  private ItemStackHandler itemHandlerWrapper;
  private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandlerWrapper);

  public MailboxTile(TileEntityType<?> tileEntityTypeIn) {
    super(tileEntityTypeIn);
    itemHandler = createHandler();
    itemHandlerWrapper = new OnlyRemoveWrapper(itemHandler);
  }

  public MailboxTile() {
    this(ModTileEntities.MAILBOX_TILE.get());
  }

  @Override
  public void read(BlockState state, CompoundNBT nbt) {
    itemHandler.deserializeNBT(nbt.getCompound("inv"));
    super.read(state, nbt);
  }

  @Override
  public CompoundNBT write(CompoundNBT compound) {
    compound.put("inv", itemHandler.serializeNBT());
    return super.write(compound);
  }

  private ItemStackHandler createHandler() {
    return new ItemStackHandler(63) {
      @Override
      protected void onContentsChanged(int slot) {
        markDirty();
      }

      @Override
      public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        // TODO modify this so that the player cannot put any items in, but the game can.
        //  also make it so that it can only hold envelopes
//        switch (slot) {
//          case 0: return stack.getItem() == Items.GLASS_PANE;
//          case 1: return stack.getItem() == ModItems.AMETHYST.get() ||
//              stack.getItem() == ModItems.FIRESTONE.get();
//          default:
//            return false;
//        }
        return true;
      }

      // TODO here, for the send pane, we want to add logic to only allow 1 stamp and one parcel
      @Override
      public int getSlotLimit(int slot) {
        return 64;
      }

      @Nonnull
      @Override
      public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        if(!isItemValid(slot, stack)) {
          return stack;
        }

        return super.insertItem(slot, stack, simulate);
      }
    };
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return handler.cast();
    }

    return super.getCapability(cap, side);
  }

  /**
   * Method to get the number of slots available
   * @return - the number of empty slots available
   */
  public int getEmptySlots() {
    return itemHandler.getSlots();
  }

  /**
   * Method that can only be called by the game (not GUI) to add items to the inventory.
   * @param parcel - ItemStack to add
   * @return true if succeeded, false if no room
   */
  public boolean addParcel(ItemStack parcel) {
    // TODO change so that this only takes in sealed envelopes
    for (int i=0; i < 27; i++) {
      boolean slotEmpty = this.itemHandler.getStackInSlot(i).getCount() == 0;
      if (slotEmpty) {
        this.itemHandler.insertItem(i, parcel, false);
        return true;
      }
    }
    return false; //otherwise, we did not find space
  }

  public void lightningHasStruck() {
    boolean hasFocusInFirstSlot = this.itemHandler.getStackInSlot(0).getCount() > 0
        && this.itemHandler.getStackInSlot(0).getItem() == Items.GLASS_PANE;
    boolean hasAmethystInSecondSlot = this.itemHandler.getStackInSlot(1).getCount() > 0
        && this.itemHandler.getStackInSlot(1).getItem() == ModItems.AMETHYST.get();

    if(hasFocusInFirstSlot && hasAmethystInSecondSlot) {
      this.itemHandler.getStackInSlot(0).shrink(1);
      this.itemHandler.getStackInSlot(1).shrink(1);

      this.itemHandler.insertItem(1, new ItemStack(ModItems.FIRESTONE.get()), false);
    }
  }
}