package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class ProbabilityFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<ProbabilityFeatureConfiguration> f_67858_ = RecordCodecBuilder.create((p_67866_) -> {
      return p_67866_.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((p_161045_) -> {
         return p_161045_.f_67859_;
      })).apply(p_67866_, ProbabilityFeatureConfiguration::new);
   });
   public final float f_67859_;

   public ProbabilityFeatureConfiguration(float p_67862_) {
      this.f_67859_ = p_67862_;
   }
}