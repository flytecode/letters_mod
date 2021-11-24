package net.minecraft.world.level.levelgen.synth;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.IntRBTreeSet;
import it.unimi.dsi.fastutil.ints.IntSortedSet;
import java.util.List;
import java.util.stream.IntStream;
import net.minecraft.world.level.levelgen.RandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;

public class PerlinSimplexNoise implements SurfaceNoise {
   private final SimplexNoise[] f_75432_;
   private final double f_75433_;
   private final double f_75434_;

   public PerlinSimplexNoise(RandomSource p_164396_, IntStream p_164397_) {
      this(p_164396_, p_164397_.boxed().collect(ImmutableList.toImmutableList()));
   }

   public PerlinSimplexNoise(RandomSource p_164393_, List<Integer> p_164394_) {
      this(p_164393_, new IntRBTreeSet(p_164394_));
   }

   private PerlinSimplexNoise(RandomSource p_164390_, IntSortedSet p_164391_) {
      if (p_164391_.isEmpty()) {
         throw new IllegalArgumentException("Need some octaves!");
      } else {
         int i = -p_164391_.firstInt();
         int j = p_164391_.lastInt();
         int k = i + j + 1;
         if (k < 1) {
            throw new IllegalArgumentException("Total number of octaves needs to be >= 1");
         } else {
            SimplexNoise simplexnoise = new SimplexNoise(p_164390_);
            int l = j;
            this.f_75432_ = new SimplexNoise[k];
            if (j >= 0 && j < k && p_164391_.contains(0)) {
               this.f_75432_[j] = simplexnoise;
            }

            for(int i1 = j + 1; i1 < k; ++i1) {
               if (i1 >= 0 && p_164391_.contains(l - i1)) {
                  this.f_75432_[i1] = new SimplexNoise(p_164390_);
               } else {
                  p_164390_.m_158876_(262);
               }
            }

            if (j > 0) {
               long k1 = (long)(simplexnoise.m_75467_(simplexnoise.f_75454_, simplexnoise.f_75455_, simplexnoise.f_75456_) * (double)9.223372E18F);
               RandomSource randomsource = new WorldgenRandom(k1);

               for(int j1 = l - 1; j1 >= 0; --j1) {
                  if (j1 < k && p_164391_.contains(l - j1)) {
                     this.f_75432_[j1] = new SimplexNoise(randomsource);
                  } else {
                     randomsource.m_158876_(262);
                  }
               }
            }

            this.f_75434_ = Math.pow(2.0D, (double)j);
            this.f_75433_ = 1.0D / (Math.pow(2.0D, (double)k) - 1.0D);
         }
      }
   }

   public double m_75449_(double p_75450_, double p_75451_, boolean p_75452_) {
      double d0 = 0.0D;
      double d1 = this.f_75434_;
      double d2 = this.f_75433_;

      for(SimplexNoise simplexnoise : this.f_75432_) {
         if (simplexnoise != null) {
            d0 += simplexnoise.m_75464_(p_75450_ * d1 + (p_75452_ ? simplexnoise.f_75454_ : 0.0D), p_75451_ * d1 + (p_75452_ ? simplexnoise.f_75455_ : 0.0D)) * d2;
         }

         d1 /= 2.0D;
         d2 *= 2.0D;
      }

      return d0;
   }

   public double m_5495_(double p_75445_, double p_75446_, double p_75447_, double p_75448_) {
      return this.m_75449_(p_75445_, p_75446_, true) * 0.55D;
   }
}