package net.minecraft.world.level.levelgen.synth;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleListIterator;
import net.minecraft.world.level.levelgen.RandomSource;

public class NormalNoise {
   private static final double f_164344_ = 1.0181268882175227D;
   private static final double f_164345_ = 0.3333333333333333D;
   private final double f_75373_;
   private final PerlinNoise f_75374_;
   private final PerlinNoise f_75375_;

   public static NormalNoise m_164354_(RandomSource p_164355_, int p_164356_, double... p_164357_) {
      return new NormalNoise(p_164355_, p_164356_, new DoubleArrayList(p_164357_));
   }

   public static NormalNoise m_164350_(RandomSource p_164351_, int p_164352_, DoubleList p_164353_) {
      return new NormalNoise(p_164351_, p_164352_, p_164353_);
   }

   private NormalNoise(RandomSource p_164347_, int p_164348_, DoubleList p_164349_) {
      this.f_75374_ = PerlinNoise.m_164381_(p_164347_, p_164348_, p_164349_);
      this.f_75375_ = PerlinNoise.m_164381_(p_164347_, p_164348_, p_164349_);
      int i = Integer.MAX_VALUE;
      int j = Integer.MIN_VALUE;
      DoubleListIterator doublelistiterator = p_164349_.iterator();

      while(doublelistiterator.hasNext()) {
         int k = doublelistiterator.nextIndex();
         double d0 = doublelistiterator.nextDouble();
         if (d0 != 0.0D) {
            i = Math.min(i, k);
            j = Math.max(j, k);
         }
      }

      this.f_75373_ = 0.16666666666666666D / m_75384_(j - i);
   }

   private static double m_75384_(int p_75385_) {
      return 0.1D * (1.0D + 1.0D / (double)(p_75385_ + 1));
   }

   public double m_75380_(double p_75381_, double p_75382_, double p_75383_) {
      double d0 = p_75381_ * 1.0181268882175227D;
      double d1 = p_75382_ * 1.0181268882175227D;
      double d2 = p_75383_ * 1.0181268882175227D;
      return (this.f_75374_.m_75408_(p_75381_, p_75382_, p_75383_) + this.f_75375_.m_75408_(d0, d1, d2)) * this.f_75373_;
   }
}