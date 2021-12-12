package com.cs32.lettersmod.util;

import com.cs32.lettersmod.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class StampSlot extends SlotItemHandler {

  public StampSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
    super(itemHandler, index, xPosition, yPosition);
  }

  @Override
  public int getSlotStackLimit() {
    return 1;
  }

  @Override
  public int getItemStackLimit(ItemStack stack) {
    return getSlotStackLimit();
  }

  @Override
  public boolean isItemValid(ItemStack stack) {
    return stack.getItem() == ModItems.STAMP;
  }

}
