package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.OptionalInt;
import net.minecraft.core.Registry;

public abstract class FeatureSize {
   public static final Codec<FeatureSize> f_68281_ = Registry.f_122888_.dispatch(FeatureSize::m_7612_, FeatureSizeType::m_68302_);
   protected static final int f_161325_ = 16;
   protected final OptionalInt f_68282_;

   protected static <S extends FeatureSize> RecordCodecBuilder<S, OptionalInt> m_68286_() {
      return Codec.intRange(0, 80).optionalFieldOf("min_clipped_height").xmap((p_68292_) -> {
         return p_68292_.map(OptionalInt::of).orElse(OptionalInt.empty());
      }, (p_68294_) -> {
         return p_68294_.isPresent() ? Optional.of(p_68294_.getAsInt()) : Optional.empty();
      }).forGetter((p_68290_) -> {
         return p_68290_.f_68282_;
      });
   }

   public FeatureSize(OptionalInt p_68285_) {
      this.f_68282_ = p_68285_;
   }

   protected abstract FeatureSizeType<?> m_7612_();

   public abstract int m_6133_(int p_68287_, int p_68288_);

   public OptionalInt m_68295_() {
      return this.f_68282_;
   }
}