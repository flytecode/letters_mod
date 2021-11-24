package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class SimpleRandomFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<SimpleRandomFeatureConfiguration> f_68089_ = ExtraCodecs.m_144637_(ConfiguredFeature.f_65375_).fieldOf("features").xmap(SimpleRandomFeatureConfiguration::new, (p_68095_) -> {
      return p_68095_.f_68090_;
   }).codec();
   public final List<Supplier<ConfiguredFeature<?, ?>>> f_68090_;

   public SimpleRandomFeatureConfiguration(List<Supplier<ConfiguredFeature<?, ?>>> p_68093_) {
      this.f_68090_ = p_68093_;
   }

   public Stream<ConfiguredFeature<?, ?>> m_7817_() {
      return this.f_68090_.stream().flatMap((p_68097_) -> {
         return p_68097_.get().m_65398_();
      });
   }
}