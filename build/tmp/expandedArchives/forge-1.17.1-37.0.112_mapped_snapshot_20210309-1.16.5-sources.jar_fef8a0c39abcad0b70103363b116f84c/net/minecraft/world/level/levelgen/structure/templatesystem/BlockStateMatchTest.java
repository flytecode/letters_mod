package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.world.level.block.state.BlockState;

public class BlockStateMatchTest extends RuleTest {
   public static final Codec<BlockStateMatchTest> f_74089_ = BlockState.f_61039_.fieldOf("block_state").xmap(BlockStateMatchTest::new, (p_74099_) -> {
      return p_74099_.f_74090_;
   }).codec();
   private final BlockState f_74090_;

   public BlockStateMatchTest(BlockState p_74093_) {
      this.f_74090_ = p_74093_;
   }

   public boolean m_7715_(BlockState p_74096_, Random p_74097_) {
      return p_74096_ == this.f_74090_;
   }

   protected RuleTestType<?> m_7319_() {
      return RuleTestType.f_74314_;
   }
}