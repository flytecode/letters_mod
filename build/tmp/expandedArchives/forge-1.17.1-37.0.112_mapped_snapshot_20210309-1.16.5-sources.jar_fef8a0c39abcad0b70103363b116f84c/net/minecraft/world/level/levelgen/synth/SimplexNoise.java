package net.minecraft.world.level.levelgen.synth;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.RandomSource;

public class SimplexNoise {
   protected static final int[][] f_75453_ = new int[][]{{1, 1, 0}, {-1, 1, 0}, {1, -1, 0}, {-1, -1, 0}, {1, 0, 1}, {-1, 0, 1}, {1, 0, -1}, {-1, 0, -1}, {0, 1, 1}, {0, -1, 1}, {0, 1, -1}, {0, -1, -1}, {1, 1, 0}, {0, -1, 1}, {-1, 1, 0}, {0, -1, -1}};
   private static final double f_75457_ = Math.sqrt(3.0D);
   private static final double f_75458_ = 0.5D * (f_75457_ - 1.0D);
   private static final double f_75459_ = (3.0D - f_75457_) / 6.0D;
   private final int[] f_75460_ = new int[512];
   public final double f_75454_;
   public final double f_75455_;
   public final double f_75456_;

   public SimplexNoise(RandomSource p_164399_) {
      this.f_75454_ = p_164399_.nextDouble() * 256.0D;
      this.f_75455_ = p_164399_.nextDouble() * 256.0D;
      this.f_75456_ = p_164399_.nextDouble() * 256.0D;

      for(int i = 0; i < 256; this.f_75460_[i] = i++) {
      }

      for(int l = 0; l < 256; ++l) {
         int j = p_164399_.nextInt(256 - l);
         int k = this.f_75460_[l];
         this.f_75460_[l] = this.f_75460_[j + l];
         this.f_75460_[j + l] = k;
      }

   }

   private int m_75471_(int p_75472_) {
      return this.f_75460_[p_75472_ & 255];
   }

   protected static double m_75479_(int[] p_75480_, double p_75481_, double p_75482_, double p_75483_) {
      return (double)p_75480_[0] * p_75481_ + (double)p_75480_[1] * p_75482_ + (double)p_75480_[2] * p_75483_;
   }

   private double m_75473_(int p_75474_, double p_75475_, double p_75476_, double p_75477_, double p_75478_) {
      double d1 = p_75478_ - p_75475_ * p_75475_ - p_75476_ * p_75476_ - p_75477_ * p_75477_;
      double d0;
      if (d1 < 0.0D) {
         d0 = 0.0D;
      } else {
         d1 = d1 * d1;
         d0 = d1 * d1 * m_75479_(f_75453_[p_75474_], p_75475_, p_75476_, p_75477_);
      }

      return d0;
   }

   public double m_75464_(double p_75465_, double p_75466_) {
      double d0 = (p_75465_ + p_75466_) * f_75458_;
      int i = Mth.m_14107_(p_75465_ + d0);
      int j = Mth.m_14107_(p_75466_ + d0);
      double d1 = (double)(i + j) * f_75459_;
      double d2 = (double)i - d1;
      double d3 = (double)j - d1;
      double d4 = p_75465_ - d2;
      double d5 = p_75466_ - d3;
      int k;
      int l;
      if (d4 > d5) {
         k = 1;
         l = 0;
      } else {
         k = 0;
         l = 1;
      }

      double d6 = d4 - (double)k + f_75459_;
      double d7 = d5 - (double)l + f_75459_;
      double d8 = d4 - 1.0D + 2.0D * f_75459_;
      double d9 = d5 - 1.0D + 2.0D * f_75459_;
      int i1 = i & 255;
      int j1 = j & 255;
      int k1 = this.m_75471_(i1 + this.m_75471_(j1)) % 12;
      int l1 = this.m_75471_(i1 + k + this.m_75471_(j1 + l)) % 12;
      int i2 = this.m_75471_(i1 + 1 + this.m_75471_(j1 + 1)) % 12;
      double d10 = this.m_75473_(k1, d4, d5, 0.0D, 0.5D);
      double d11 = this.m_75473_(l1, d6, d7, 0.0D, 0.5D);
      double d12 = this.m_75473_(i2, d8, d9, 0.0D, 0.5D);
      return 70.0D * (d10 + d11 + d12);
   }

   public double m_75467_(double p_75468_, double p_75469_, double p_75470_) {
      double d0 = 0.3333333333333333D;
      double d1 = (p_75468_ + p_75469_ + p_75470_) * 0.3333333333333333D;
      int i = Mth.m_14107_(p_75468_ + d1);
      int j = Mth.m_14107_(p_75469_ + d1);
      int k = Mth.m_14107_(p_75470_ + d1);
      double d2 = 0.16666666666666666D;
      double d3 = (double)(i + j + k) * 0.16666666666666666D;
      double d4 = (double)i - d3;
      double d5 = (double)j - d3;
      double d6 = (double)k - d3;
      double d7 = p_75468_ - d4;
      double d8 = p_75469_ - d5;
      double d9 = p_75470_ - d6;
      int l;
      int i1;
      int j1;
      int k1;
      int l1;
      int i2;
      if (d7 >= d8) {
         if (d8 >= d9) {
            l = 1;
            i1 = 0;
            j1 = 0;
            k1 = 1;
            l1 = 1;
            i2 = 0;
         } else if (d7 >= d9) {
            l = 1;
            i1 = 0;
            j1 = 0;
            k1 = 1;
            l1 = 0;
            i2 = 1;
         } else {
            l = 0;
            i1 = 0;
            j1 = 1;
            k1 = 1;
            l1 = 0;
            i2 = 1;
         }
      } else if (d8 < d9) {
         l = 0;
         i1 = 0;
         j1 = 1;
         k1 = 0;
         l1 = 1;
         i2 = 1;
      } else if (d7 < d9) {
         l = 0;
         i1 = 1;
         j1 = 0;
         k1 = 0;
         l1 = 1;
         i2 = 1;
      } else {
         l = 0;
         i1 = 1;
         j1 = 0;
         k1 = 1;
         l1 = 1;
         i2 = 0;
      }

      double d10 = d7 - (double)l + 0.16666666666666666D;
      double d11 = d8 - (double)i1 + 0.16666666666666666D;
      double d12 = d9 - (double)j1 + 0.16666666666666666D;
      double d13 = d7 - (double)k1 + 0.3333333333333333D;
      double d14 = d8 - (double)l1 + 0.3333333333333333D;
      double d15 = d9 - (double)i2 + 0.3333333333333333D;
      double d16 = d7 - 1.0D + 0.5D;
      double d17 = d8 - 1.0D + 0.5D;
      double d18 = d9 - 1.0D + 0.5D;
      int j2 = i & 255;
      int k2 = j & 255;
      int l2 = k & 255;
      int i3 = this.m_75471_(j2 + this.m_75471_(k2 + this.m_75471_(l2))) % 12;
      int j3 = this.m_75471_(j2 + l + this.m_75471_(k2 + i1 + this.m_75471_(l2 + j1))) % 12;
      int k3 = this.m_75471_(j2 + k1 + this.m_75471_(k2 + l1 + this.m_75471_(l2 + i2))) % 12;
      int l3 = this.m_75471_(j2 + 1 + this.m_75471_(k2 + 1 + this.m_75471_(l2 + 1))) % 12;
      double d19 = this.m_75473_(i3, d7, d8, d9, 0.6D);
      double d20 = this.m_75473_(j3, d10, d11, d12, 0.6D);
      double d21 = this.m_75473_(k3, d13, d14, d15, 0.6D);
      double d22 = this.m_75473_(l3, d16, d17, d18, 0.6D);
      return 32.0D * (d19 + d20 + d21 + d22);
   }
}