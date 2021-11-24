package net.minecraft.world.level.levelgen.structure.templatesystem;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;

public class LinearPosTest extends PosRuleTest {
   public static final Codec<LinearPosTest> f_74147_ = RecordCodecBuilder.create((p_74160_) -> {
      return p_74160_.group(Codec.FLOAT.fieldOf("min_chance").orElse(0.0F).forGetter((p_163737_) -> {
         return p_163737_.f_74148_;
      }), Codec.FLOAT.fieldOf("max_chance").orElse(0.0F).forGetter((p_163735_) -> {
         return p_163735_.f_74149_;
      }), Codec.INT.fieldOf("min_dist").orElse(0).forGetter((p_163733_) -> {
         return p_163733_.f_74150_;
      }), Codec.INT.fieldOf("max_dist").orElse(0).forGetter((p_163731_) -> {
         return p_163731_.f_74151_;
      })).apply(p_74160_, LinearPosTest::new);
   });
   private final float f_74148_;
   private final float f_74149_;
   private final int f_74150_;
   private final int f_74151_;

   public LinearPosTest(float p_74154_, float p_74155_, int p_74156_, int p_74157_) {
      if (p_74156_ >= p_74157_) {
         throw new IllegalArgumentException("Invalid range: [" + p_74156_ + "," + p_74157_ + "]");
      } else {
         this.f_74148_ = p_74154_;
         this.f_74149_ = p_74155_;
         this.f_74150_ = p_74156_;
         this.f_74151_ = p_74157_;
      }
   }

   public boolean m_7124_(BlockPos p_74164_, BlockPos p_74165_, BlockPos p_74166_, Random p_74167_) {
      int i = p_74165_.m_123333_(p_74166_);
      float f = p_74167_.nextFloat();
      return (double)f <= Mth.m_14085_((double)this.f_74148_, (double)this.f_74149_, Mth.m_14112_((double)i, (double)this.f_74150_, (double)this.f_74151_));
   }

   protected PosRuleTestType<?> m_6158_() {
      return PosRuleTestType.f_74206_;
   }
}