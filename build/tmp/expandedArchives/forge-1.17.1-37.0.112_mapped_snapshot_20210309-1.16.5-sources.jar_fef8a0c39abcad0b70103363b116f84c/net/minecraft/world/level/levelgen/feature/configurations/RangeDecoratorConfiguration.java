package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class RangeDecoratorConfiguration implements DecoratorConfiguration, FeatureConfiguration {
   public static final Codec<RangeDecoratorConfiguration> f_68006_ = RecordCodecBuilder.create((p_161080_) -> {
      return p_161080_.group(HeightProvider.f_161970_.fieldOf("height").forGetter((p_161082_) -> {
         return p_161082_.f_161076_;
      })).apply(p_161080_, RangeDecoratorConfiguration::new);
   });
   public final HeightProvider f_161076_;

   public RangeDecoratorConfiguration(HeightProvider p_161078_) {
      this.f_161076_ = p_161078_;
   }
}