package com.cs32.lettersmod.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {
  public static final ItemGroup LETTERS_GROUP = new ItemGroup("lettersModTab") {
    @Override
    public ItemStack createIcon() {
      return new ItemStack(ModItems.ENVELOPE.get());
    }
  };

}