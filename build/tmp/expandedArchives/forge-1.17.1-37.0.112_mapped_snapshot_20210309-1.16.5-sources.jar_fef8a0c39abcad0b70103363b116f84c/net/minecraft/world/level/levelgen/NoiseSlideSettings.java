package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public class NoiseSlideSettings {
   public static final Codec<NoiseSlideSettings> f_64548_ = RecordCodecBuilder.create((p_64559_) -> {
      return p_64559_.group(Codec.INT.fieldOf("target").forGetter(NoiseSlideSettings::m_64557_), ExtraCodecs.f_144628_.fieldOf("size").forGetter(NoiseSlideSettings::m_64560_), Codec.INT.fieldOf("offset").forGetter(NoiseSlideSettings::m_64561_)).apply(p_64559_, NoiseSlideSettings::new);
   });
   private final int f_64549_;
   private final int f_64550_;
   private final int f_64551_;

   public NoiseSlideSettings(int p_64554_, int p_64555_, int p_64556_) {
      this.f_64549_ = p_64554_;
      this.f_64550_ = p_64555_;
      this.f_64551_ = p_64556_;
   }

   public int m_64557_() {
      return this.f_64549_;
   }

   public int m_64560_() {
      return this.f_64550_;
   }

   public int m_64561_() {
      return this.f_64551_;
   }
}