package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

public interface HeightProviderType<P extends HeightProvider> {
   HeightProviderType<ConstantHeight> f_161981_ = m_161989_("constant", ConstantHeight.f_161946_);
   HeightProviderType<UniformHeight> f_161982_ = m_161989_("uniform", UniformHeight.f_162023_);
   HeightProviderType<BiasedToBottomHeight> f_161983_ = m_161989_("biased_to_bottom", BiasedToBottomHeight.f_161918_);
   HeightProviderType<VeryBiasedToBottomHeight> f_161984_ = m_161989_("very_biased_to_bottom", VeryBiasedToBottomHeight.f_162045_);
   HeightProviderType<TrapezoidHeight> f_161985_ = m_161989_("trapezoid", TrapezoidHeight.f_161993_);

   Codec<P> m_161992_();

   static <P extends HeightProvider> HeightProviderType<P> m_161989_(String p_161990_, Codec<P> p_161991_) {
      return Registry.m_122961_(Registry.f_175419_, p_161990_, () -> {
         return p_161991_;
      });
   }
}