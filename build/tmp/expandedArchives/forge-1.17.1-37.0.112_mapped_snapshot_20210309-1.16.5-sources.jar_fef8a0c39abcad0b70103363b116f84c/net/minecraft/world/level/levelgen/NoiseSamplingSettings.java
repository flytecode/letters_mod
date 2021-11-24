package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class NoiseSamplingSettings {
   private static final Codec<Double> f_64490_ = Codec.doubleRange(0.001D, 1000.0D);
   public static final Codec<NoiseSamplingSettings> f_64489_ = RecordCodecBuilder.create((p_64503_) -> {
      return p_64503_.group(f_64490_.fieldOf("xz_scale").forGetter(NoiseSamplingSettings::m_64501_), f_64490_.fieldOf("y_scale").forGetter(NoiseSamplingSettings::m_64504_), f_64490_.fieldOf("xz_factor").forGetter(NoiseSamplingSettings::m_64505_), f_64490_.fieldOf("y_factor").forGetter(NoiseSamplingSettings::m_64506_)).apply(p_64503_, NoiseSamplingSettings::new);
   });
   private final double f_64491_;
   private final double f_64492_;
   private final double f_64493_;
   private final double f_64494_;

   public NoiseSamplingSettings(double p_64497_, double p_64498_, double p_64499_, double p_64500_) {
      this.f_64491_ = p_64497_;
      this.f_64492_ = p_64498_;
      this.f_64493_ = p_64499_;
      this.f_64494_ = p_64500_;
   }

   public double m_64501_() {
      return this.f_64491_;
   }

   public double m_64504_() {
      return this.f_64492_;
   }

   public double m_64505_() {
      return this.f_64493_;
   }

   public double m_64506_() {
      return this.f_64494_;
   }
}