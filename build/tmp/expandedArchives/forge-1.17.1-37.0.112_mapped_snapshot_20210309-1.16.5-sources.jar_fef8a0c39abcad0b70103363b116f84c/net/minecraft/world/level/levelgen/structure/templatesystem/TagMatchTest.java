package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.Registry;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class TagMatchTest extends RuleTest {
   public static final Codec<TagMatchTest> f_74690_ = Tag.m_13290_(() -> {
      return SerializationTags.m_13199_().m_144452_(Registry.f_122901_);
   }).fieldOf("tag").xmap(TagMatchTest::new, (p_74700_) -> {
      return p_74700_.f_74691_;
   }).codec();
   private final Tag<Block> f_74691_;

   public TagMatchTest(Tag<Block> p_74694_) {
      this.f_74691_ = p_74694_;
   }

   public boolean m_7715_(BlockState p_74697_, Random p_74698_) {
      return p_74697_.m_60620_(this.f_74691_);
   }

   protected RuleTestType<?> m_7319_() {
      return RuleTestType.f_74315_;
   }
}