package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

public class FeatureSizeType<P extends FeatureSize> {
   public static final FeatureSizeType<TwoLayersFeatureSize> f_68296_ = m_68303_("two_layers_feature_size", TwoLayersFeatureSize.f_68336_);
   public static final FeatureSizeType<ThreeLayersFeatureSize> f_68297_ = m_68303_("three_layers_feature_size", ThreeLayersFeatureSize.f_68306_);
   private final Codec<P> f_68298_;

   private static <P extends FeatureSize> FeatureSizeType<P> m_68303_(String p_68304_, Codec<P> p_68305_) {
      return Registry.m_122961_(Registry.f_122888_, p_68304_, new FeatureSizeType<>(p_68305_));
   }

   private FeatureSizeType(Codec<P> p_68301_) {
      this.f_68298_ = p_68301_;
   }

   public Codec<P> m_68302_() {
      return this.f_68298_;
   }
}