package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.state.BlockState;

public abstract class RuleTest {
   public static final Codec<RuleTest> f_74307_ = Registry.f_122861_.dispatch("predicate_type", RuleTest::m_7319_, RuleTestType::m_74324_);

   public abstract boolean m_7715_(BlockState p_74310_, Random p_74311_);

   protected abstract RuleTestType<?> m_7319_();
}