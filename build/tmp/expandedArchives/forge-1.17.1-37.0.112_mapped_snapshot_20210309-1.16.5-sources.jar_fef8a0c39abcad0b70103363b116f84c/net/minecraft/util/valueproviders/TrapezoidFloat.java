package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.Function;

public class TrapezoidFloat extends FloatProvider {
   public static final Codec<TrapezoidFloat> f_146561_ = RecordCodecBuilder.<TrapezoidFloat>create((p_146578_) -> {
      return p_146578_.group(Codec.FLOAT.fieldOf("min").forGetter((p_146588_) -> {
         return p_146588_.f_146562_;
      }), Codec.FLOAT.fieldOf("max").forGetter((p_146586_) -> {
         return p_146586_.f_146563_;
      }), Codec.FLOAT.fieldOf("plateau").forGetter((p_146583_) -> {
         return p_146583_.f_146564_;
      })).apply(p_146578_, TrapezoidFloat::new);
   }).comapFlatMap((p_146576_) -> {
      if (p_146576_.f_146563_ < p_146576_.f_146562_) {
         return DataResult.error("Max must be larger than min: [" + p_146576_.f_146562_ + ", " + p_146576_.f_146563_ + "]");
      } else {
         return p_146576_.f_146564_ > p_146576_.f_146563_ - p_146576_.f_146562_ ? DataResult.error("Plateau can at most be the full span: [" + p_146576_.f_146562_ + ", " + p_146576_.f_146563_ + "]") : DataResult.success(p_146576_);
      }
   }, Function.identity());
   private final float f_146562_;
   private final float f_146563_;
   private final float f_146564_;

   public static TrapezoidFloat m_146571_(float p_146572_, float p_146573_, float p_146574_) {
      return new TrapezoidFloat(p_146572_, p_146573_, p_146574_);
   }

   private TrapezoidFloat(float p_146567_, float p_146568_, float p_146569_) {
      this.f_146562_ = p_146567_;
      this.f_146563_ = p_146568_;
      this.f_146564_ = p_146569_;
   }

   public float m_142269_(Random p_146580_) {
      float f = this.f_146563_ - this.f_146562_;
      float f1 = (f - this.f_146564_) / 2.0F;
      float f2 = f - f1;
      return this.f_146562_ + p_146580_.nextFloat() * f2 + p_146580_.nextFloat() * f1;
   }

   public float m_142735_() {
      return this.f_146562_;
   }

   public float m_142734_() {
      return this.f_146563_;
   }

   public FloatProviderType<?> m_141961_() {
      return FloatProviderType.f_146522_;
   }

   public String toString() {
      return "trapezoid(" + this.f_146564_ + ") in [" + this.f_146562_ + "-" + this.f_146563_ + "]";
   }
}