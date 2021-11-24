package net.minecraft.world.level.levelgen.synth;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.ints.IntBidirectionalIterator;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.function.LongFunction;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public class PerlinNoise implements SurfaceNoise {
   private static final int f_164358_ = 33554432;
   private final ImprovedNoise[] f_75390_;
   private final DoubleList f_75391_;
   private final double f_75392_;
   private final double f_75393_;

   public PerlinNoise(RandomSource p_164377_, IntStream p_164378_) {
      this(p_164377_, p_164378_.boxed().collect(ImmutableList.toImmutableList()));
   }

   public PerlinNoise(RandomSource p_164374_, List<Integer> p_164375_) {
      this(p_164374_, new IntRBTreeSet(p_164375_));
   }

   public static PerlinNoise m_164385_(RandomSource p_164386_, int p_164387_, double... p_164388_) {
      return m_164381_(p_164386_, p_164387_, new DoubleArrayList(p_164388_));
   }

   public static PerlinNoise m_164381_(RandomSource p_164382_, int p_164383_, DoubleList p_164384_) {
      return new PerlinNoise(p_164382_, Pair.of(p_164383_, p_164384_));
   }

   private static Pair<Integer, DoubleList> m_75430_(IntSortedSet p_75431_) {
      if (p_75431_.isEmpty()) {
         throw new IllegalArgumentException("Need some octaves!");
      } else {
         int i = -p_75431_.firstInt();
         int j = p_75431_.lastInt();
         int k = i + j + 1;
         if (k < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
         } else {
            DoubleList doublelist = new DoubleArrayList(new double[k]);
            IntBidirectionalIterator intbidirectionaliterator = p_75431_.iterator();

            while(intbidirectionaliterator.hasNext()) {
               int l = intbidirectionaliterator.nextInt();
               doublelist.set(l + i, 1.0D);
            }

            return Pair.of(-i, doublelist);
         }
      }
   }

   private PerlinNoise(RandomSource p_164367_, IntSortedSet p_164368_) {
      this(p_164367_, p_164368_, WorldgenRandom::new);
   }

   private PerlinNoise(RandomSource p_164370_, IntSortedSet p_164371_, LongFunction<RandomSource> p_164372_) {
      this(p_164370_, m_75430_(p_164371_), p_164372_);
   }

   protected PerlinNoise(RandomSource p_164360_, Pair<Integer, DoubleList> p_164361_) {
      this(p_164360_, p_164361_, WorldgenRandom::new);
   }

   protected PerlinNoise(RandomSource p_164363_, Pair<Integer, DoubleList> p_164364_, LongFunction<RandomSource> p_164365_) {
      int i = p_164364_.getFirst();
      this.f_75391_ = p_164364_.getSecond();
      ImprovedNoise improvednoise = new ImprovedNoise(p_164363_);
      int j = this.f_75391_.size();
      int k = -i;
      this.f_75390_ = new ImprovedNoise[j];
      if (k >= 0 && k < j) {
         double d0 = this.f_75391_.getDouble(k);
         if (d0 != 0.0D) {
            this.f_75390_[k] = improvednoise;
         }
      }

      for(int l = k - 1; l >= 0; --l) {
         if (l < j) {
            double d1 = this.f_75391_.getDouble(l);
            if (d1 != 0.0D) {
               this.f_75390_[l] = new ImprovedNoise(p_164363_);
            } else {
               m_164379_(p_164363_);
            }
         } else {
            m_164379_(p_164363_);
         }
      }

      if (k < j - 1) {
         throw new IllegalArgumentException("Positive octaves are temporarily disabled");
      } else {
         this.f_75393_ = Math.pow(2.0D, (double)(-k));
         this.f_75392_ = Math.pow(2.0D, (double)(j - 1)) / (Math.pow(2.0D, (double)j) - 1.0D);
      }
   }

   private static void m_164379_(RandomSource p_164380_) {
      p_164380_.m_158876_(262);
   }

   public double m_75408_(double p_75409_, double p_75410_, double p_75411_) {
      return this.m_75417_(p_75409_, p_75410_, p_75411_, 0.0D, 0.0D, false);
   }

   @Deprecated
   public double m_75417_(double p_75418_, double p_75419_, double p_75420_, double p_75421_, double p_75422_, boolean p_75423_) {
      double d0 = 0.0D;
      double d1 = this.f_75393_;
      double d2 = this.f_75392_;

      for(int i = 0; i < this.f_75390_.length; ++i) {
         ImprovedNoise improvednoise = this.f_75390_[i];
         if (improvednoise != null) {
            double d3 = improvednoise.m_75327_(m_75406_(p_75418_ * d1), p_75423_ ? -improvednoise.f_75322_ : m_75406_(p_75419_ * d1), m_75406_(p_75420_ * d1), p_75421_ * d1, p_75422_ * d1);
            d0 += this.f_75391_.getDouble(i) * d3 * d2;
         }

         d1 *= 2.0D;
         d2 /= 2.0D;
      }

      return d0;
   }

   @Nullable
   public ImprovedNoise m_75424_(int p_75425_) {
      return this.f_75390_[this.f_75390_.length - 1 - p_75425_];
   }

   public static double m_75406_(double p_75407_) {
      return p_75407_ - (double)Mth.m_14134_(p_75407_ / 3.3554432E7D + 0.5D) * 3.3554432E7D;
   }

   public double m_5495_(double p_75413_, double p_75414_, double p_75415_, double p_75416_) {
      return this.m_75417_(p_75413_, p_75414_, 0.0D, p_75415_, p_75416_, false);
   }
}