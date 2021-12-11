package com.cs32.lettersmod.item;

import com.cs32.lettersmod.LettersMod;
import com.cs32.lettersmod.item.custom.Envelope;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LettersMod.MOD_ID);

  // empty envelope item
  public static final RegistryObject<Item> ENVELOPE = ITEMS.register("envelope",
          () -> new Envelope(new Item.Properties().group(ModItemGroup.LETTERS_GROUP)));

//  // sealed envelope item
//  public static final RegistryObject<Item> SEALED_ENVELOPE = ITEMS.register("sealed_envelope",
//          () -> new Item(new Item.Properties().group(ItemGroup.MATERIALS)));

  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }
}
