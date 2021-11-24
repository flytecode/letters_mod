package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.world.level.block.state.BlockState;

public class RandomBlockStateMatchTest extends RuleTest {
   public static final Codec<RandomBlockStateMatchTest> f_74275_ = RecordCodecBuilder.create((p_74287_) -> {
      return p_74287_.group(BlockState.f_61039_.fieldOf("block_state").forGetter((p_163770_) -> {
         return p_163770_.f_74276_;
      }), Codec.FLOAT.fieldOf("probability").forGetter((p_163768_) -> {
         return p_163768_.f_74277_;
      })).apply(p_74287_, RandomBlockStateMatchTest::new);
   });
   private final BlockState f_74276_;
   private final float f_74277_;

   public RandomBlockStateMatchTest(BlockState p_74280_, float p_74281_) {
      this.f_74276_ = p_74280_;
      this.f_74277_ = p_74281_;
   }

   public boolean m_7715_(BlockState p_74284_, Random p_74285_) {
      return p_74284_ == this.f_74276_ && p_74285_.nextFloat() < this.f_74277_;
   }

   protected RuleTestType<?> m_7319_() {
      return RuleTestType.f_74317_;
   }
}