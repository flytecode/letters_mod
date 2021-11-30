package com.mod.envelope;

import com.mod.envelope.core.init.ItemInit;
import com.mod.envelope.core.init.ModContainerTypes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("envelope")
public class EnvelopeMod {
    public static final String MODID = "envelope";

    public static final ItemGroup ENVELOPE_GROUP = new ItemGroup("envelopeModTab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemInit.SEALED_ENVELOPE.get());
        }
    };

    public EnvelopeMod() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemInit.ITEMS.register(bus);
        ModContainerTypes.CONTAINER_TYPE.register(bus);
        MinecraftForge.EVENT_BUS.register(this);
    }

}
