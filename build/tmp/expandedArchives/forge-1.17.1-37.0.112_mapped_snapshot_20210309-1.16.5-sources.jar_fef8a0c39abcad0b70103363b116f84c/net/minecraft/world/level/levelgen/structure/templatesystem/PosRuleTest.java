package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;

public abstract class PosRuleTest {
   public static final Codec<PosRuleTest> f_74198_ = Registry.f_122862_.dispatch("predicate_type", PosRuleTest::m_6158_, PosRuleTestType::m_74214_);

   public abstract boolean m_7124_(BlockPos p_74201_, BlockPos p_74202_, BlockPos p_74203_, Random p_74204_);

   protected abstract PosRuleTestType<?> m_6158_();
}