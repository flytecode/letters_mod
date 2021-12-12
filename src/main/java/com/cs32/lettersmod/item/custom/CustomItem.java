package com.cs32.lettersmod.item.custom;

import com.cs32.lettersmod.LettersMod;
import net.minecraft.item.Item;

public class CustomItem extends Item {

  public CustomItem(String name) {
    this(name, new Item.Properties().group(LettersMod.ITEM_GROUP));
  }

  public CustomItem(String name, Item.Properties props) {
    super(props);
    this.setRegistryName(LettersMod.MOD_ID, name);
  }

}
