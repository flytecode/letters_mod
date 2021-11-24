package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockMatchTest extends RuleTest {
   public static final Codec<BlockMatchTest> f_74063_ = Registry.f_122824_.fieldOf("block").xmap(BlockMatchTest::new, (p_74073_) -> {
      return p_74073_.f_74064_;
   }).codec();
   private final Block f_74064_;

   public BlockMatchTest(Block p_74067_) {
      this.f_74064_ = p_74067_;
   }

   public boolean m_7715_(BlockState p_74070_, Random p_74071_) {
      return p_74070_.m_60713_(this.f_74064_);
   }

   protected RuleTestType<?> m_7319_() {
      return RuleTestType.f_74313_;
   }
}