package com.cs32.lettersmod.container;

import com.cs32.lettersmod.block.ModBlocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

/*
Mailbox Receiving Screen
** need to make it show "My address: 123456"
** need to make it so that players cannot put things into mailbox inventory, only take items out

on mailbox being opened:
- refreshMailView() to replace contents of mailbox with whatever's in parcelList

on refresh button being pressed:
- getMail() to do /getmail command and update parcelList
- if successful: refreshMailView() to replace contents of mailbox
- if not successful: message in GUI "could not connect"

on item being removed:
- delete that item from the parcelList
OR on mailbox being closed/exited:
- replace parcelList with contents of mailbox now that they've removed everything
 */


public class MailboxContainer extends Container {
  private final TileEntity tileEntity;
  private final PlayerEntity playerEntity;
  private final IItemHandler playerInventory;

  public MailboxContainer(int windowId, World world, BlockPos pos,
                                     PlayerInventory playerInventory, PlayerEntity player) {
    super(ModContainers.MAILBOX_CONTAINER.get(), windowId);
    this.tileEntity = world.getTileEntity(pos);
    playerEntity = player;
    this.playerInventory = new InvWrapper(playerInventory);
    layoutPlayerInventorySlots(8, 84);

    if(tileEntity != null) {
      tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
        for (int i=0; i < 9; i++) {
          addSlot(new SlotItemHandler(h, i, 8 + (18*i), 18)); //row 1
          addSlot(new SlotItemHandler(h, i + 18, 8 + (18*i), 36)); //row 2
          addSlot(new SlotItemHandler(h, i + 36, 8 + (18*i), 54)); //row 3
        }
      });
    }
  }

  public boolean isLightningStorm() {
    return tileEntity.getWorld().isThundering();
  }

  @Override
  public boolean canInteractWith(PlayerEntity playerIn) {
    return isWithinUsableDistance(IWorldPosCallable.of(tileEntity.getWorld(), tileEntity.getPos()),
        playerIn, ModBlocks.MAILBOX.get());
  }


  // tutorial code for laying out player inventory slots
  private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
    for (int i = 0; i < amount; i++) {
      addSlot(new SlotItemHandler(handler, index, x, y));
      x += dx;
      index++;
    }

    return index;
  }

  // tutorial code for laying out player inventory slots
  private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
    for (int j = 0; j < verAmount; j++) {
      index = addSlotRange(handler, index, x, y, horAmount, dx);
      y += dy;
    }

    return index;
  }

  private void layoutPlayerInventorySlots(int leftCol, int topRow) {
    addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

    topRow += 58;
    addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
  }


  // CREDIT GOES TO: diesieben07 | https://github.com/diesieben07/SevenCommons
  // must assign a slot number to each of the slots used by the GUI.
  // For this container, we can see both the tile inventory's slots as well as the player inventory slots and the hotbar.
  // Each time we add a Slot to the container, it automatically increases the slotIndex, which means
  //  0 - 8 = hotbar slots (which will map to the InventoryPlayer slot numbers 0 - 8)
  //  9 - 35 = player inventory slots (which map to the InventoryPlayer slot numbers 9 - 35)
  //  36 - 44 = TileInventory slots, which map to our TileEntity slot numbers 0 - 8)
  private static final int HOTBAR_SLOT_COUNT = 9;
  private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
  private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
  private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
  private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
  private static final int VANILLA_FIRST_SLOT_INDEX = 0;
  private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;

  // THIS YOU HAVE TO DEFINE!
  private static final int TE_INVENTORY_SLOT_COUNT = 27;  // must match TileEntityInventoryBasic.NUMBER_OF_SLOTS

  // TODO fix the small bug where if you shift click it moves it around within the player inventory, want it
  //  to have the same effect as normal inventory hotbar <-> main
  @Override
  public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
    Slot sourceSlot = inventorySlots.get(index);
    if (sourceSlot == null || !sourceSlot.getHasStack()) return ItemStack.EMPTY;  //EMPTY_ITEM
    ItemStack sourceStack = sourceSlot.getStack();
    ItemStack copyOfSourceStack = sourceStack.copy();

    // Check if the slot clicked is one of the vanilla container slots
    // THIS IS COMMENTED OUT SO THAT PLAYERS CANNOT INSERT INTO THE MAILBOX BY SHIFT CLICKING
//    if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
//      // This is a vanilla container slot so merge the stack into the tile inventory
//      if (!mergeItemStack(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
//          + TE_INVENTORY_SLOT_COUNT, false)) {
//        return ItemStack.EMPTY;  // EMPTY_ITEM
//      }
//    }
    if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
      // This is a TE slot so merge the stack into the players inventory
      if (!mergeItemStack(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
        return ItemStack.EMPTY;
      }
    } else {
      System.out.println("Invalid slotIndex:" + index);
      return ItemStack.EMPTY;
    }
    // If stack size == 0 (the entire stack was moved) set slot contents to null
    if (sourceStack.getCount() == 0) {
      sourceSlot.putStack(ItemStack.EMPTY);
    } else {
      sourceSlot.onSlotChanged();
    }
    sourceSlot.onTake(playerEntity, sourceStack);
    return copyOfSourceStack;
  }
}