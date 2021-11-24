package net.minecraft.client.resources.sounds;

import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.Weighted;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Sound implements Weighted<Sound> {
   private final ResourceLocation f_119770_;
   private final float f_119771_;
   private final float f_119772_;
   private final int f_119773_;
   private final Sound.Type f_119774_;
   private final boolean f_119775_;
   private final boolean f_119776_;
   private final int f_119777_;

   public Sound(String p_119779_, float p_119780_, float p_119781_, int p_119782_, Sound.Type p_119783_, boolean p_119784_, boolean p_119785_, int p_119786_) {
      this.f_119770_ = new ResourceLocation(p_119779_);
      this.f_119771_ = p_119780_;
      this.f_119772_ = p_119781_;
      this.f_119773_ = p_119782_;
      this.f_119774_ = p_119783_;
      this.f_119775_ = p_119784_;
      this.f_119776_ = p_119785_;
      this.f_119777_ = p_119786_;
   }

   public ResourceLocation m_119787_() {
      return this.f_119770_;
   }

   public ResourceLocation m_119790_() {
      return new ResourceLocation(this.f_119770_.m_135827_(), "sounds/" + this.f_119770_.m_135815_() + ".ogg");
   }

   public float m_119791_() {
      return this.f_119771_;
   }

   public float m_119792_() {
      return this.f_119772_;
   }

   public int m_7789_() {
      return this.f_119773_;
   }

   public Sound m_6776_() {
      return this;
   }

   public void m_8054_(SoundEngine p_119789_) {
      if (this.f_119776_) {
         p_119789_.m_120272_(this);
      }

   }

   public Sound.Type m_119795_() {
      return this.f_119774_;
   }

   public boolean m_119796_() {
      return this.f_119775_;
   }

   public boolean m_119797_() {
      return this.f_119776_;
   }

   public int m_119798_() {
      return this.f_119777_;
   }

   public String toString() {
      return "Sound[" + this.f_119770_ + "]";
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Type {
      FILE("file"),
      SOUND_EVENT("event");

      private final String f_119803_;

      private Type(String p_119809_) {
         this.f_119803_ = p_119809_;
      }

      public static Sound.Type m_119810_(String p_119811_) {
         for(Sound.Type sound$type : values()) {
            if (sound$type.f_119803_.equals(p_119811_)) {
               return sound$type;
            }
         }

         return null;
      }
   }
}