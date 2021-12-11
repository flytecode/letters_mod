package com.cs32.lettersmod.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class LetterModItemGroup {

    public static final ItemGroup LETTER_MOD_GROUP = new ItemGroup("letterModTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(LettersItems.STAMP.get());
        }
    };

}