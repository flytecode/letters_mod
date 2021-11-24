package net.minecraft.world.level.levelgen.synth;

import java.util.stream.IntStream;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.RandomSource;

public class BlendedNoise {
   private final PerlinNoise f_164288_;
   private final PerlinNoise f_164289_;
   private final PerlinNoise f_164290_;

   public BlendedNoise(PerlinNoise p_164294_, PerlinNoise p_164295_, PerlinNoise p_164296_) {
      this.f_164288_ = p_164294_;
      this.f_164289_ = p_164295_;
      this.f_164290_ = p_164296_;
   }

   public BlendedNoise(RandomSource p_164292_) {
      this(new PerlinNoise(p_164292_, IntStream.rangeClosed(-15, 0)), new PerlinNoise(p_164292_, IntStream.rangeClosed(-15, 0)), new PerlinNoise(p_164292_, IntStream.rangeClosed(-7, 0)));
   }

   public double m_164297_(int p_164298_, int p_164299_, int p_164300_, double p_164301_, double p_164302_, double p_164303_, double p_164304_) {
      double d0 = 0.0D;
      double d1 = 0.0D;
      double d2 = 0.0D;
      boolean flag = true;
      double d3 = 1.0D;

      for(int i = 0; i < 8; ++i) {
         ImprovedNoise improvednoise = this.f_164290_.m_75424_(i);
         if (improvednoise != null) {
            d2 += improvednoise.m_75327_(PerlinNoise.m_75406_((double)p_164298_ * p_164303_ * d3), PerlinNoise.m_75406_((double)p_164299_ * p_164304_ * d3), PerlinNoise.m_75406_((double)p_164300_ * p_164303_ * d3), p_164304_ * d3, (double)p_164299_ * p_164304_ * d3) / d3;
         }

         d3 /= 2.0D;
      }

      double d8 = (d2 / 10.0D + 1.0D) / 2.0D;
      boolean flag1 = d8 >= 1.0D;
      boolean flag2 = d8 <= 0.0D;
      d3 = 1.0D;

      for(int j = 0; j < 16; ++j) {
         double d4 = PerlinNoise.m_75406_((double)p_164298_ * p_164301_ * d3);
         double d5 = PerlinNoise.m_75406_((double)p_164299_ * p_164302_ * d3);
         double d6 = PerlinNoise.m_75406_((double)p_164300_ * p_164301_ * d3);
         double d7 = p_164302_ * d3;
         if (!flag1) {
            ImprovedNoise improvednoise1 = this.f_164288_.m_75424_(j);
            if (improvednoise1 != null) {
               d0 += improvednoise1.m_75327_(d4, d5, d6, d7, (double)p_164299_ * d7) / d3;
            }
         }

         if (!flag2) {
            ImprovedNoise improvednoise2 = this.f_164289_.m_75424_(j);
            if (improvednoise2 != null) {
               d1 += improvednoise2.m_75327_(d4, d5, d6, d7, (double)p_164299_ * d7) / d3;
            }
         }

         d3 /= 2.0D;
      }

      return Mth.m_14085_(d0 / 512.0D, d1 / 512.0D, d8);
   }
}