package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

public interface PosRuleTestType<P extends PosRuleTest> {
   PosRuleTestType<PosAlwaysTrueTest> f_74205_ = m_74211_("always_true", PosAlwaysTrueTest.f_74187_);
   PosRuleTestType<LinearPosTest> f_74206_ = m_74211_("linear_pos", LinearPosTest.f_74147_);
   PosRuleTestType<AxisAlignedLinearPosTest> f_74207_ = m_74211_("axis_aligned_linear_pos", AxisAlignedLinearPosTest.f_73962_);

   Codec<P> m_74214_();

   static <P extends PosRuleTest> PosRuleTestType<P> m_74211_(String p_74212_, Codec<P> p_74213_) {
      return Registry.m_122961_(Registry.f_122862_, p_74212_, () -> {
         return p_74213_;
      });
   }
}