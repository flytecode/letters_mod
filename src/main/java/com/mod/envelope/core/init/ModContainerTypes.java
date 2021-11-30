package com.mod.envelope.core.init;

import com.mod.envelope.EnvelopeMod;
import com.mod.envelope.client.gui.EnvelopeScreen;
import com.mod.envelope.container.EnvelopeContainer;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = EnvelopeMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainerTypes {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPE = DeferredRegister.create(ForgeRegistries.CONTAINERS, EnvelopeMod.MODID);

    public static final RegistryObject<ContainerType<EnvelopeContainer>> ENVELOPE = CONTAINER_TYPE.register(
            "envelope", () -> IForgeContainerType.create(EnvelopeContainer::new)
    );

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ScreenManager.register(ENVELOPE.get(), EnvelopeScreen::new);
        });
    }
}
