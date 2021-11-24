package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

public interface FloatProviderType<P extends FloatProvider> {
   FloatProviderType<ConstantFloat> f_146519_ = m_146526_("constant", ConstantFloat.f_146452_);
   FloatProviderType<UniformFloat> f_146520_ = m_146526_("uniform", UniformFloat.f_146590_);
   FloatProviderType<ClampedNormalFloat> f_146521_ = m_146526_("clamped_normal", ClampedNormalFloat.f_146411_);
   FloatProviderType<TrapezoidFloat> f_146522_ = m_146526_("trapezoid", TrapezoidFloat.f_146561_);

   Codec<P> m_146529_();

   static <P extends FloatProvider> FloatProviderType<P> m_146526_(String p_146527_, Codec<P> p_146528_) {
      return Registry.m_122961_(Registry.f_175415_, p_146527_, () -> {
         return p_146528_;
      });
   }
}