package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.WeightedConfiguredFeature;

public class RandomFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<RandomFeatureConfiguration> f_67881_ = RecordCodecBuilder.create((p_67898_) -> {
      return p_67898_.apply2(RandomFeatureConfiguration::new, WeightedConfiguredFeature.f_67403_.listOf().fieldOf("features").forGetter((p_161053_) -> {
         return p_161053_.f_67882_;
      }), ConfiguredFeature.f_65374_.fieldOf("default").forGetter((p_161051_) -> {
         return p_161051_.f_67883_;
      }));
   });
   public final List<WeightedConfiguredFeature> f_67882_;
   public final Supplier<ConfiguredFeature<?, ?>> f_67883_;

   public RandomFeatureConfiguration(List<WeightedConfiguredFeature> p_67886_, ConfiguredFeature<?, ?> p_67887_) {
      this(p_67886_, () -> {
         return p_67887_;
      });
   }

   private RandomFeatureConfiguration(List<WeightedConfiguredFeature> p_67889_, Supplier<ConfiguredFeature<?, ?>> p_67890_) {
      this.f_67882_ = p_67889_;
      this.f_67883_ = p_67890_;
   }

   public Stream<ConfiguredFeature<?, ?>> m_7817_() {
      return Stream.concat(this.f_67882_.stream().flatMap((p_67894_) -> {
         return p_67894_.f_67404_.get().m_65398_();
      }), this.f_67883_.get().m_65398_());
   }
}