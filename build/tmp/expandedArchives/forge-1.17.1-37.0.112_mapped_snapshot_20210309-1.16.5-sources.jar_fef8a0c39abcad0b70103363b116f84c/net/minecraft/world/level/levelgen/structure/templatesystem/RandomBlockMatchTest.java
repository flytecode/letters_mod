package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class RandomBlockMatchTest extends RuleTest {
   public static final Codec<RandomBlockMatchTest> f_74258_ = RecordCodecBuilder.create((p_74270_) -> {
      return p_74270_.group(Registry.f_122824_.fieldOf("block").forGetter((p_163766_) -> {
         return p_163766_.f_74259_;
      }), Codec.FLOAT.fieldOf("probability").forGetter((p_163764_) -> {
         return p_163764_.f_74260_;
      })).apply(p_74270_, RandomBlockMatchTest::new);
   });
   private final Block f_74259_;
   private final float f_74260_;

   public RandomBlockMatchTest(Block p_74263_, float p_74264_) {
      this.f_74259_ = p_74263_;
      this.f_74260_ = p_74264_;
   }

   public boolean m_7715_(BlockState p_74267_, Random p_74268_) {
      return p_74267_.m_60713_(this.f_74259_) && p_74268_.nextFloat() < this.f_74260_;
   }

   protected RuleTestType<?> m_7319_() {
      return RuleTestType.f_74316_;
   }
}