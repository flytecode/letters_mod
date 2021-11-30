package com.cs32.lettersmod;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class LettersItems {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LettersMod.MOD_ID);

  // method to register this registry in event bus
  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }

  // lines where you can add an item to the game
  public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst",
      () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));

}
