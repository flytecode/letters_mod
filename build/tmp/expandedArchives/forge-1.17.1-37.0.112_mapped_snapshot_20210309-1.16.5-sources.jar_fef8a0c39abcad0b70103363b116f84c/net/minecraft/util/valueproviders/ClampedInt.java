package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.Mth;

public class ClampedInt extends IntProvider {
   public static final Codec<ClampedInt> f_146383_ = RecordCodecBuilder.<ClampedInt>create((p_146400_) -> {
      return p_146400_.group(IntProvider.f_146531_.fieldOf("source").forGetter((p_146410_) -> {
         return p_146410_.f_146384_;
      }), Codec.INT.fieldOf("min_inclusive").forGetter((p_146408_) -> {
         return p_146408_.f_146385_;
      }), Codec.INT.fieldOf("max_inclusive").forGetter((p_146405_) -> {
         return p_146405_.f_146386_;
      })).apply(p_146400_, ClampedInt::new);
   }).comapFlatMap((p_146394_) -> {
      return p_146394_.f_146386_ < p_146394_.f_146385_ ? DataResult.error("Max must be at least min, min_inclusive: " + p_146394_.f_146385_ + ", max_inclusive: " + p_146394_.f_146386_) : DataResult.success(p_146394_);
   }, Function.identity());
   private final IntProvider f_146384_;
   private int f_146385_;
   private int f_146386_;

   public static ClampedInt m_146395_(IntProvider p_146396_, int p_146397_, int p_146398_) {
      return new ClampedInt(p_146396_, p_146397_, p_146398_);
   }

   public ClampedInt(IntProvider p_146389_, int p_146390_, int p_146391_) {
      this.f_146384_ = p_146389_;
      this.f_146385_ = p_146390_;
      this.f_146386_ = p_146391_;
   }

   public int m_142270_(Random p_146402_) {
      return Mth.m_14045_(this.f_146384_.m_142270_(p_146402_), this.f_146385_, this.f_146386_);
   }

   public int m_142739_() {
      return Math.max(this.f_146385_, this.f_146384_.m_142739_());
   }

   public int m_142737_() {
      return Math.min(this.f_146386_, this.f_146384_.m_142737_());
   }

   public IntProviderType<?> m_141948_() {
      return IntProviderType.f_146553_;
   }
}