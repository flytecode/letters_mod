package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

public interface IntProviderType<P extends IntProvider> {
   IntProviderType<ConstantInt> f_146550_ = m_146557_("constant", ConstantInt.f_146477_);
   IntProviderType<UniformInt> f_146551_ = m_146557_("uniform", UniformInt.f_146614_);
   IntProviderType<BiasedToBottomInt> f_146552_ = m_146557_("biased_to_bottom", BiasedToBottomInt.f_146359_);
   IntProviderType<ClampedInt> f_146553_ = m_146557_("clamped", ClampedInt.f_146383_);

   Codec<P> m_146560_();

   static <P extends IntProvider> IntProviderType<P> m_146557_(String p_146558_, Codec<P> p_146559_) {
      return Registry.m_122961_(Registry.f_175417_, p_146558_, () -> {
         return p_146559_;
      });
   }
}