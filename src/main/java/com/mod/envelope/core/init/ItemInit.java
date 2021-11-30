package com.mod.envelope.core.init;

import com.mod.envelope.EnvelopeMod;
import com.mod.envelope.item.SealedEnvelope;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            EnvelopeMod.MODID);

    public static final RegistryObject<Item> STAMP = ITEMS.register("stamp",
            () -> new Item(new Item.Properties().tab(EnvelopeMod.ENVELOPE_GROUP)));

    public static final RegistryObject<Item> ENVELOPE = ITEMS.register("envelope",
            () -> new Item(new Item.Properties().tab(EnvelopeMod.ENVELOPE_GROUP)));

    public static final RegistryObject<SealedEnvelope> SEALED_ENVELOPE = ITEMS.register("sealed_envelope",
            () -> new SealedEnvelope (new Item.Properties().tab(EnvelopeMod.ENVELOPE_GROUP).stacksTo(1)));
}
