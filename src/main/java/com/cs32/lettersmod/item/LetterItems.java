package com.cs32.lettersmod.item;

import net.minecraft.world.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;



public class LetterItems {
  private static final String MOD_ID = "lettersmod";
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }

  public static final RegistryObject<Item> AMETHYST = ITEMS.register("amethyst",
      () -> new Item(new Item.Properties().tab(ItemGroup.MATERIALS)));

}
