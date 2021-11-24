package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class NoiseDependantDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<NoiseDependantDecoratorConfiguration> f_67793_ = RecordCodecBuilder.create((p_67805_) -> {
      return p_67805_.group(Codec.DOUBLE.fieldOf("noise_level").forGetter((p_160998_) -> {
         return p_160998_.f_67794_;
      }), Codec.INT.fieldOf("below_noise").forGetter((p_160996_) -> {
         return p_160996_.f_67795_;
      }), Codec.INT.fieldOf("above_noise").forGetter((p_160994_) -> {
         return p_160994_.f_67796_;
      })).apply(p_67805_, NoiseDependantDecoratorConfiguration::new);
   });
   public final double f_67794_;
   public final int f_67795_;
   public final int f_67796_;

   public NoiseDependantDecoratorConfiguration(double p_67799_, int p_67800_, int p_67801_) {
      this.f_67794_ = p_67799_;
      this.f_67795_ = p_67800_;
      this.f_67796_ = p_67801_;
   }
}