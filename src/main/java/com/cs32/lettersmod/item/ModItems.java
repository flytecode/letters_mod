package com.cs32.lettersmod.item;

import com.cs32.lettersmod.LettersMod;
import com.cs32.lettersmod.item.custom.CustomItem;
import com.cs32.lettersmod.item.custom.Envelope;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LettersMod.MOD_ID);

  public static final Envelope ENVELOPE_OPEN = new Envelope("envelope_open", true);
  public static final Envelope ENVELOPE_CLOSED = new Envelope("envelope_closed", false);
  public static final CustomItem STAMP = new CustomItem("stamp");

}
