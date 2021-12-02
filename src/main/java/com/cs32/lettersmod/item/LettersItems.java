package com.cs32.lettersmod.item;

import com.cs32.lettersmod.LettersMod;
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

  // creating letter item
  public static final RegistryObject<Item> ENVELOPE = ITEMS.register("envelope",
      () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));

  // stamp item
  public static final RegistryObject<Item> STAMP = ITEMS.register("stamp",
          () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));

  // empty envelope item
  public static final RegistryObject<Item> EMPTY_ENVELOPE = ITEMS.register("empty_envelope",
          () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));

  // sealed envelope item
  public static final RegistryObject<Item> SEALED_ENVELOPE = ITEMS.register("sealed_envelope",
          () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));




}
