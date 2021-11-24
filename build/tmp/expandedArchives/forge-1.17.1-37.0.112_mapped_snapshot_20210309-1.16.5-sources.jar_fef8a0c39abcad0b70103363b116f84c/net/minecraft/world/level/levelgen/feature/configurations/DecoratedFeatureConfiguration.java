package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.ConfiguredDecorator;

public class DecoratedFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<DecoratedFeatureConfiguration> f_67576_ = RecordCodecBuilder.create((p_67586_) -> {
      return p_67586_.group(ConfiguredFeature.f_65374_.fieldOf("feature").forGetter((p_160729_) -> {
         return p_160729_.f_67577_;
      }), ConfiguredDecorator.f_70471_.fieldOf("decorator").forGetter((p_160727_) -> {
         return p_160727_.f_67578_;
      })).apply(p_67586_, DecoratedFeatureConfiguration::new);
   });
   public final Supplier<ConfiguredFeature<?, ?>> f_67577_;
   public final ConfiguredDecorator<?> f_67578_;

   public DecoratedFeatureConfiguration(Supplier<ConfiguredFeature<?, ?>> p_67581_, ConfiguredDecorator<?> p_67582_) {
      this.f_67577_ = p_67581_;
      this.f_67578_ = p_67582_;
   }

   public String toString() {
      return String.format("< %s [%s | %s] >", this.getClass().getSimpleName(), Registry.f_122839_.m_7981_(this.f_67577_.get().m_65394_()), this.f_67578_);
   }

   public Stream<ConfiguredFeature<?, ?>> m_7817_() {
      return this.f_67577_.get().m_65398_();
   }
}