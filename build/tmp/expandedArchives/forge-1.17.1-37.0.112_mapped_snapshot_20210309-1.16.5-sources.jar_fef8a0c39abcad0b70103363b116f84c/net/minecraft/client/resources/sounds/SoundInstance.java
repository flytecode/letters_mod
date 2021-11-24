package net.minecraft.client.resources.sounds;

import javax.annotation.Nullable;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface SoundInstance {
   ResourceLocation m_7904_();

   @Nullable
   WeighedSoundEvents m_6775_(SoundManager p_119841_);

   Sound m_5891_();

   SoundSource m_8070_();

   boolean m_7775_();

   boolean m_7796_();

   int m_7766_();

   float m_7769_();

   float m_7783_();

   double m_7772_();

   double m_7780_();

   double m_7778_();

   SoundInstance.Attenuation m_7438_();

   default boolean m_7784_() {
      return false;
   }

   default boolean m_7767_() {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Attenuation {
      NONE,
      LINEAR;
   }
}