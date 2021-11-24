package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class GeodeLayerSettings {
   private static final Codec<Double> f_158346_ = Codec.doubleRange(0.01D, 50.0D);
   public static final Codec<GeodeLayerSettings> f_158341_ = RecordCodecBuilder.create((p_158354_) -> {
      return p_158354_.group(f_158346_.fieldOf("filling").orElse(1.7D).forGetter((p_158362_) -> {
         return p_158362_.f_158342_;
      }), f_158346_.fieldOf("inner_layer").orElse(2.2D).forGetter((p_158360_) -> {
         return p_158360_.f_158343_;
      }), f_158346_.fieldOf("middle_layer").orElse(3.2D).forGetter((p_158358_) -> {
         return p_158358_.f_158344_;
      }), f_158346_.fieldOf("outer_layer").orElse(4.2D).forGetter((p_158356_) -> {
         return p_158356_.f_158345_;
      })).apply(p_158354_, GeodeLayerSettings::new);
   });
   public final double f_158342_;
   public final double f_158343_;
   public final double f_158344_;
   public final double f_158345_;

   public GeodeLayerSettings(double p_158349_, double p_158350_, double p_158351_, double p_158352_) {
      this.f_158342_ = p_158349_;
      this.f_158343_ = p_158350_;
      this.f_158344_ = p_158351_;
      this.f_158345_ = p_158352_;
   }
}