package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;

public interface RuleTestType<P extends RuleTest> {
   RuleTestType<AlwaysTrueTest> f_74312_ = m_74321_("always_true", AlwaysTrueTest.f_73953_);
   RuleTestType<BlockMatchTest> f_74313_ = m_74321_("block_match", BlockMatchTest.f_74063_);
   RuleTestType<BlockStateMatchTest> f_74314_ = m_74321_("blockstate_match", BlockStateMatchTest.f_74089_);
   RuleTestType<TagMatchTest> f_74315_ = m_74321_("tag_match", TagMatchTest.f_74690_);
   RuleTestType<RandomBlockMatchTest> f_74316_ = m_74321_("random_block_match", RandomBlockMatchTest.f_74258_);
   RuleTestType<RandomBlockStateMatchTest> f_74317_ = m_74321_("random_blockstate_match", RandomBlockStateMatchTest.f_74275_);

   Codec<P> m_74324_();

   static <P extends RuleTest> RuleTestType<P> m_74321_(String p_74322_, Codec<P> p_74323_) {
      return Registry.m_122961_(Registry.f_122861_, p_74322_, () -> {
         return p_74323_;
      });
   }
}