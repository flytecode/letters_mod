package net.minecraft.world.level.biome;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.sounds.SoundEvent;

public class AmbientAdditionsSettings {
   public static final Codec<AmbientAdditionsSettings> f_47371_ = RecordCodecBuilder.create((p_47382_) -> {
      return p_47382_.group(SoundEvent.f_11655_.fieldOf("sound").forGetter((p_151642_) -> {
         return p_151642_.f_47372_;
      }), Codec.DOUBLE.fieldOf("tick_chance").forGetter((p_151640_) -> {
         return p_151640_.f_47373_;
      })).apply(p_47382_, AmbientAdditionsSettings::new);
   });
   private final SoundEvent f_47372_;
   private final double f_47373_;

   public AmbientAdditionsSettings(SoundEvent p_47376_, double p_47377_) {
      this.f_47372_ = p_47376_;
      this.f_47373_ = p_47377_;
   }

   public SoundEvent m_47378_() {
      return this.f_47372_;
   }

   public double m_47383_() {
      return this.f_47373_;
   }
}