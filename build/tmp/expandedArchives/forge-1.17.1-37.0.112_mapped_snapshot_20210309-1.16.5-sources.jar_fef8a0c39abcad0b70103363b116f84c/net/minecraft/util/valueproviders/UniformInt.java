package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.Mth;

public class UniformInt extends IntProvider {
   public static final Codec<UniformInt> f_146614_ = RecordCodecBuilder.<UniformInt>create((p_146628_) -> {
      return p_146628_.group(Codec.INT.fieldOf("min_inclusive").forGetter((p_146636_) -> {
         return p_146636_.f_146615_;
      }), Codec.INT.fieldOf("max_inclusive").forGetter((p_146633_) -> {
         return p_146633_.f_146616_;
      })).apply(p_146628_, UniformInt::new);
   }).comapFlatMap((p_146626_) -> {
      return p_146626_.f_146616_ < p_146626_.f_146615_ ? DataResult.error("Max must be at least min, min_inclusive: " + p_146626_.f_146615_ + ", max_inclusive: " + p_146626_.f_146616_) : DataResult.success(p_146626_);
   }, Function.identity());
   private final int f_146615_;
   private final int f_146616_;

   private UniformInt(int p_146619_, int p_146620_) {
      this.f_146615_ = p_146619_;
      this.f_146616_ = p_146620_;
   }

   public static UniformInt m_146622_(int p_146623_, int p_146624_) {
      return new UniformInt(p_146623_, p_146624_);
   }

   public int m_142270_(Random p_146630_) {
      return Mth.m_144928_(p_146630_, this.f_146615_, this.f_146616_);
   }

   public int m_142739_() {
      return this.f_146615_;
   }

   public int m_142737_() {
      return this.f_146616_;
   }

   public IntProviderType<?> m_141948_() {
      return IntProviderType.f_146551_;
   }

   public String toString() {
      return "[" + this.f_146615_ + "-" + this.f_146616_ + "]";
   }
}