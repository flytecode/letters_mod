package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class RandomBooleanFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<RandomBooleanFeatureConfiguration> f_67867_ = RecordCodecBuilder.create((p_67877_) -> {
      return p_67877_.group(ConfiguredFeature.f_65374_.fieldOf("feature_true").forGetter((p_161049_) -> {
         return p_161049_.f_67868_;
      }), ConfiguredFeature.f_65374_.fieldOf("feature_false").forGetter((p_161047_) -> {
         return p_161047_.f_67869_;
      })).apply(p_67877_, RandomBooleanFeatureConfiguration::new);
   });
   public final Supplier<ConfiguredFeature<?, ?>> f_67868_;
   public final Supplier<ConfiguredFeature<?, ?>> f_67869_;

   public RandomBooleanFeatureConfiguration(Supplier<ConfiguredFeature<?, ?>> p_67872_, Supplier<ConfiguredFeature<?, ?>> p_67873_) {
      this.f_67868_ = p_67872_;
      this.f_67869_ = p_67873_;
   }

   public Stream<ConfiguredFeature<?, ?>> m_7817_() {
      return Stream.concat(this.f_67868_.get().m_65398_(), this.f_67869_.get().m_65398_());
   }
}