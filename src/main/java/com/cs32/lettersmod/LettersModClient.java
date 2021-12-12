package com.cs32.lettersmod;

import com.cs32.lettersmod.container.ModContainers;
import com.cs32.lettersmod.container.screen.EnvelopeScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = LettersMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LettersModClient {

  @SubscribeEvent
  public static void clientSetup(final FMLClientSetupEvent event) {
    ScreenManager.registerFactory(ModContainers.ENVELOPE, EnvelopeScreen::new);
  }

}
