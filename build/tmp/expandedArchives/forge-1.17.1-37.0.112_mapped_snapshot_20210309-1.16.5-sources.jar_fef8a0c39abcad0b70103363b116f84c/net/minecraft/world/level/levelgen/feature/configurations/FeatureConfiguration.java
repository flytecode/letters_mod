package net.minecraft.world.level.levelgen.feature.configurations;

import java.util.stream.Stream;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public interface FeatureConfiguration {
   NoneFeatureConfiguration f_67737_ = NoneFeatureConfiguration.f_67816_;

   default Stream<ConfiguredFeature<?, ?>> m_7817_() {
      return Stream.empty();
   }
}