package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class NoiseCountFactorDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<NoiseCountFactorDecoratorConfiguration> f_70806_ = RecordCodecBuilder.create((p_70816_) -> {
      return p_70816_.group(Codec.INT.fieldOf("noise_to_count_ratio").forGetter((p_162268_) -> {
         return p_162268_.f_70807_;
      }), Codec.DOUBLE.fieldOf("noise_factor").forGetter((p_162266_) -> {
         return p_162266_.f_70808_;
      }), Codec.DOUBLE.fieldOf("noise_offset").orElse(0.0D).forGetter((p_162264_) -> {
         return p_162264_.f_70809_;
      })).apply(p_70816_, NoiseCountFactorDecoratorConfiguration::new);
   });
   public final int f_70807_;
   public final double f_70808_;
   public final double f_70809_;

   public NoiseCountFactorDecoratorConfiguration(int p_70812_, double p_70813_, double p_70814_) {
      this.f_70807_ = p_70812_;
      this.f_70808_ = p_70813_;
      this.f_70809_ = p_70814_;
   }
}