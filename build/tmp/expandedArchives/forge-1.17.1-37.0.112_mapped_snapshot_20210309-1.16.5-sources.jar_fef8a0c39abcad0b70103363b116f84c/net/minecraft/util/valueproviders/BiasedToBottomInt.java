package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.Function;

public class BiasedToBottomInt extends IntProvider {
   public static final Codec<BiasedToBottomInt> f_146359_ = RecordCodecBuilder.<BiasedToBottomInt>create((p_146373_) -> {
      return p_146373_.group(Codec.INT.fieldOf("min_inclusive").forGetter((p_146381_) -> {
         return p_146381_.f_146360_;
      }), Codec.INT.fieldOf("max_inclusive").forGetter((p_146378_) -> {
         return p_146378_.f_146361_;
      })).apply(p_146373_, BiasedToBottomInt::new);
   }).comapFlatMap((p_146371_) -> {
      return p_146371_.f_146361_ < p_146371_.f_146360_ ? DataResult.error("Max must be at least min, min_inclusive: " + p_146371_.f_146360_ + ", max_inclusive: " + p_146371_.f_146361_) : DataResult.success(p_146371_);
   }, Function.identity());
   private final int f_146360_;
   private final int f_146361_;

   private BiasedToBottomInt(int p_146364_, int p_146365_) {
      this.f_146360_ = p_146364_;
      this.f_146361_ = p_146365_;
   }

   public static BiasedToBottomInt m_146367_(int p_146368_, int p_146369_) {
      return new BiasedToBottomInt(p_146368_, p_146369_);
   }

   public int m_142270_(Random p_146375_) {
      return this.f_146360_ + p_146375_.nextInt(p_146375_.nextInt(this.f_146361_ - this.f_146360_ + 1) + 1);
   }

   public int m_142739_() {
      return this.f_146360_;
   }

   public int m_142737_() {
      return this.f_146361_;
   }

   public IntProviderType<?> m_141948_() {
      return IntProviderType.f_146552_;
   }

   public String toString() {
      return "[" + this.f_146360_ + "-" + this.f_146361_ + "]";
   }
}