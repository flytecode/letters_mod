package net.minecraft.world.level.levelgen;

import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.levelgen.synth.BlendedNoise;
import net.minecraft.world.level.levelgen.synth.PerlinNoise;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public class NoiseSampler {
   private static final int f_158637_ = 32;
   protected static final float[] f_158638_ = Util.m_137469_(new float[25], (p_158687_) -> {
      for(int i = -2; i <= 2; ++i) {
         for(int j = -2; j <= 2; ++j) {
            float f = 10.0F / Mth.m_14116_((float)(i * i + j * j) + 0.2F);
            p_158687_[i + 2 + (j + 2) * 5] = f;
         }
      }

   });
   protected final BiomeSource f_158639_;
   protected final int f_158640_;
   protected final int f_158641_;
   protected final int f_158642_;
   protected final NoiseSettings f_158643_;
   public final BlendedNoise f_158644_;
   @Nullable
   public final SimplexNoise f_158645_;
   public final PerlinNoise f_158646_;
   private final double f_158647_;
   private final double f_158648_;
   private final double f_158649_;
   private final double f_158650_;
   private final double f_158651_;
   private final double f_158652_;
   private final double f_158653_;
   private final double f_158654_;
   public final NoiseModifier f_158655_;

   public NoiseSampler(BiomeSource p_158658_, int p_158659_, int p_158660_, int p_158661_, NoiseSettings p_158662_, BlendedNoise p_158663_, @Nullable SimplexNoise p_158664_, PerlinNoise p_158665_, NoiseModifier p_158666_) {
      this.f_158640_ = p_158659_;
      this.f_158641_ = p_158660_;
      this.f_158639_ = p_158658_;
      this.f_158642_ = p_158661_;
      this.f_158643_ = p_158662_;
      this.f_158644_ = p_158663_;
      this.f_158645_ = p_158664_;
      this.f_158646_ = p_158665_;
      this.f_158647_ = (double)p_158662_.m_64538_().m_64557_();
      this.f_158648_ = (double)p_158662_.m_64538_().m_64560_();
      this.f_158649_ = (double)p_158662_.m_64538_().m_64561_();
      this.f_158650_ = (double)p_158662_.m_64539_().m_64557_();
      this.f_158651_ = (double)p_158662_.m_64539_().m_64560_();
      this.f_158652_ = (double)p_158662_.m_64539_().m_64561_();
      this.f_158653_ = p_158662_.m_64542_();
      this.f_158654_ = p_158662_.m_64543_();
      this.f_158655_ = p_158666_;
   }

   public void m_158678_(double[] p_158679_, int p_158680_, int p_158681_, NoiseSettings p_158682_, int p_158683_, int p_158684_, int p_158685_) {
      double d0;
      double d1;
      if (this.f_158645_ != null) {
         d0 = (double)(TheEndBiomeSource.m_48645_(this.f_158645_, p_158680_, p_158681_) - 8.0F);
         if (d0 > 0.0D) {
            d1 = 0.25D;
         } else {
            d1 = 1.0D;
         }
      } else {
         float f = 0.0F;
         float f1 = 0.0F;
         float f2 = 0.0F;
         int i = 2;
         int j = p_158683_;
         float f3 = this.f_158639_.m_7158_(p_158680_, p_158683_, p_158681_).m_47545_();

         for(int k = -2; k <= 2; ++k) {
            for(int l = -2; l <= 2; ++l) {
               Biome biome = this.f_158639_.m_7158_(p_158680_ + k, j, p_158681_ + l);
               float f4 = biome.m_47545_();
               float f5 = biome.m_47551_();
               float f6;
               float f7;
               if (p_158682_.m_64547_() && f4 > 0.0F) {
                  f6 = 1.0F + f4 * 2.0F;
                  f7 = 1.0F + f5 * 4.0F;
               } else {
                  f6 = f4;
                  f7 = f5;
               }

               float f8 = f4 > f3 ? 0.5F : 1.0F;
               float f9 = f8 * f_158638_[k + 2 + (l + 2) * 5] / (f6 + 2.0F);
               f += f7 * f9;
               f1 += f6 * f9;
               f2 += f9;
            }
         }

         float f10 = f1 / f2;
         float f11 = f / f2;
         double d6 = (double)(f10 * 0.5F - 0.125F);
         double d8 = (double)(f11 * 0.9F + 0.1F);
         d0 = d6 * 0.265625D;
         d1 = 96.0D / d8;
      }

      double d2 = 684.412D * p_158682_.m_64537_().m_64501_();
      double d3 = 684.412D * p_158682_.m_64537_().m_64504_();
      double d4 = d2 / p_158682_.m_64537_().m_64505_();
      double d5 = d3 / p_158682_.m_64537_().m_64506_();
      double d7 = p_158682_.m_64545_() ? this.m_158675_(p_158680_, p_158681_) : 0.0D;

      for(int i1 = 0; i1 <= p_158685_; ++i1) {
         int j1 = i1 + p_158684_;
         double d9 = this.f_158644_.m_164297_(p_158680_, j1, p_158681_, d2, d3, d4, d5);
         double d10 = this.m_158670_(j1, d0, d1, d7) + d9;
         d10 = this.f_158655_.m_142124_(d10, j1 * this.f_158641_, p_158681_ * this.f_158640_, p_158680_ * this.f_158640_);
         d10 = this.m_158667_(d10, j1);
         p_158679_[i1] = d10;
      }

   }

   protected double m_158670_(int p_158671_, double p_158672_, double p_158673_, double p_158674_) {
      double d0 = 1.0D - (double)p_158671_ * 2.0D / 32.0D + p_158674_;
      double d1 = d0 * this.f_158653_ + this.f_158654_;
      double d2 = (d1 + p_158672_) * p_158673_;
      return d2 * (double)(d2 > 0.0D ? 4 : 1);
   }

   protected double m_158667_(double p_158668_, int p_158669_) {
      int i = Mth.m_14042_(this.f_158643_.m_158703_(), this.f_158641_);
      int j = p_158669_ - i;
      if (this.f_158648_ > 0.0D) {
         double d0 = ((double)(this.f_158642_ - j) - this.f_158649_) / this.f_158648_;
         p_158668_ = Mth.m_14085_(this.f_158647_, p_158668_, d0);
      }

      if (this.f_158651_ > 0.0D) {
         double d1 = ((double)j - this.f_158652_) / this.f_158651_;
         p_158668_ = Mth.m_14085_(this.f_158650_, p_158668_, d1);
      }

      return p_158668_;
   }

   protected double m_158675_(int p_158676_, int p_158677_) {
      double d0 = this.f_158646_.m_75417_((double)(p_158676_ * 200), 10.0D, (double)(p_158677_ * 200), 1.0D, 0.0D, true);
      double d1;
      if (d0 < 0.0D) {
         d1 = -d0 * 0.3D;
      } else {
         d1 = d0;
      }

      double d2 = d1 * 24.575625D - 2.0D;
      return d2 < 0.0D ? d2 * 0.009486607142857142D : Math.min(d2, 1.0D) * 0.006640625D;
   }
}