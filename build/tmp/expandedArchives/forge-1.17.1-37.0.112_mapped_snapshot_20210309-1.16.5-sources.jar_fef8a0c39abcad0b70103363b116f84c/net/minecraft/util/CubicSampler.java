package net.minecraft.util;

import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.world.phys.Vec3;

public class CubicSampler {
   private static final int f_177979_ = 2;
   private static final int f_177980_ = 6;
   private static final double[] f_130036_ = new double[]{0.0D, 1.0D, 4.0D, 6.0D, 4.0D, 1.0D, 0.0D};

   private CubicSampler() {
   }

   public static Vec3 m_130038_(Vec3 p_130039_, CubicSampler.Vec3Fetcher p_130040_) {
      int i = Mth.m_14107_(p_130039_.m_7096_());
      int j = Mth.m_14107_(p_130039_.m_7098_());
      int k = Mth.m_14107_(p_130039_.m_7094_());
      double d0 = p_130039_.m_7096_() - (double)i;
      double d1 = p_130039_.m_7098_() - (double)j;
      double d2 = p_130039_.m_7094_() - (double)k;
      double d3 = 0.0D;
      Vec3 vec3 = Vec3.f_82478_;

      for(int l = 0; l < 6; ++l) {
         double d4 = Mth.m_14139_(d0, f_130036_[l + 1], f_130036_[l]);
         int i1 = i - 2 + l;

         for(int j1 = 0; j1 < 6; ++j1) {
            double d5 = Mth.m_14139_(d1, f_130036_[j1 + 1], f_130036_[j1]);
            int k1 = j - 2 + j1;

            for(int l1 = 0; l1 < 6; ++l1) {
               double d6 = Mth.m_14139_(d2, f_130036_[l1 + 1], f_130036_[l1]);
               int i2 = k - 2 + l1;
               double d7 = d4 * d5 * d6;
               d3 += d7;
               vec3 = vec3.m_82549_(p_130040_.m_130041_(i1, k1, i2).m_82490_(d7));
            }
         }
      }

      return vec3.m_82490_(1.0D / d3);
   }

   @DontObfuscate
   public interface Vec3Fetcher {
      Vec3 m_130041_(int p_130042_, int p_130043_, int p_130044_);
   }
}