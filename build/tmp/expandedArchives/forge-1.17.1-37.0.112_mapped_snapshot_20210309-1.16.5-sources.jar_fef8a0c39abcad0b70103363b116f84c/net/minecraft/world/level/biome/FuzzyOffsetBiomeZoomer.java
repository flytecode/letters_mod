package net.minecraft.world.level.biome;

import net.minecraft.util.LinearCongruentialGenerator;

public enum FuzzyOffsetBiomeZoomer implements BiomeZoomer {
   INSTANCE;

   private static final int f_151791_ = 2;
   private static final int f_151792_ = 4;
   private static final int f_151793_ = 3;

   public Biome m_7782_(long p_48302_, int p_48303_, int p_48304_, int p_48305_, BiomeManager.NoiseBiomeSource p_48306_) {
      int i = p_48303_ - 2;
      int j = p_48304_ - 2;
      int k = p_48305_ - 2;
      int l = i >> 2;
      int i1 = j >> 2;
      int j1 = k >> 2;
      double d0 = (double)(i & 3) / 4.0D;
      double d1 = (double)(j & 3) / 4.0D;
      double d2 = (double)(k & 3) / 4.0D;
      int k1 = 0;
      double d3 = Double.POSITIVE_INFINITY;

      for(int l1 = 0; l1 < 8; ++l1) {
         boolean flag = (l1 & 4) == 0;
         boolean flag1 = (l1 & 2) == 0;
         boolean flag2 = (l1 & 1) == 0;
         int i2 = flag ? l : l + 1;
         int j2 = flag1 ? i1 : i1 + 1;
         int k2 = flag2 ? j1 : j1 + 1;
         double d4 = flag ? d0 : d0 - 1.0D;
         double d5 = flag1 ? d1 : d1 - 1.0D;
         double d6 = flag2 ? d2 : d2 - 1.0D;
         double d7 = m_48293_(p_48302_, i2, j2, k2, d4, d5, d6);
         if (d3 > d7) {
            k1 = l1;
            d3 = d7;
         }
      }

      int l2 = (k1 & 4) == 0 ? l : l + 1;
      int i3 = (k1 & 2) == 0 ? i1 : i1 + 1;
      int j3 = (k1 & 1) == 0 ? j1 : j1 + 1;
      return p_48306_.m_7158_(l2, i3, j3);
   }

   private static double m_48293_(long p_48294_, int p_48295_, int p_48296_, int p_48297_, double p_48298_, double p_48299_, double p_48300_) {
      long i = LinearCongruentialGenerator.m_13972_(p_48294_, (long)p_48295_);
      i = LinearCongruentialGenerator.m_13972_(i, (long)p_48296_);
      i = LinearCongruentialGenerator.m_13972_(i, (long)p_48297_);
      i = LinearCongruentialGenerator.m_13972_(i, (long)p_48295_);
      i = LinearCongruentialGenerator.m_13972_(i, (long)p_48296_);
      i = LinearCongruentialGenerator.m_13972_(i, (long)p_48297_);
      double d0 = m_48291_(i);
      i = LinearCongruentialGenerator.m_13972_(i, p_48294_);
      double d1 = m_48291_(i);
      i = LinearCongruentialGenerator.m_13972_(i, p_48294_);
      double d2 = m_48291_(i);
      return m_48289_(p_48300_ + d2) + m_48289_(p_48299_ + d1) + m_48289_(p_48298_ + d0);
   }

   private static double m_48291_(long p_48292_) {
      double d0 = (double)Math.floorMod(p_48292_ >> 24, 1024) / 1024.0D;
      return (d0 - 0.5D) * 0.9D;
   }

   private static double m_48289_(double p_48290_) {
      return p_48290_ * p_48290_;
   }
}