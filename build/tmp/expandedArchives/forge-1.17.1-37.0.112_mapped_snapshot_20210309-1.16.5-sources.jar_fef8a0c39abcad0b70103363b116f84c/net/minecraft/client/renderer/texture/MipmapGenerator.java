package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MipmapGenerator {
   private static final int f_174686_ = 96;
   private static final float[] f_118038_ = Util.m_137469_(new float[256], (p_118058_) -> {
      for(int i = 0; i < p_118058_.length; ++i) {
         p_118058_[i] = (float)Math.pow((double)((float)i / 255.0F), 2.2D);
      }

   });

   private MipmapGenerator() {
   }

   public static NativeImage[] m_118054_(NativeImage p_118055_, int p_118056_) {
      NativeImage[] anativeimage = new NativeImage[p_118056_ + 1];
      anativeimage[0] = p_118055_;
      if (p_118056_ > 0) {
         boolean flag = false;

         label51:
         for(int i = 0; i < p_118055_.m_84982_(); ++i) {
            for(int j = 0; j < p_118055_.m_85084_(); ++j) {
               if (p_118055_.m_84985_(i, j) >> 24 == 0) {
                  flag = true;
                  break label51;
               }
            }
         }

         for(int k1 = 1; k1 <= p_118056_; ++k1) {
            NativeImage nativeimage1 = anativeimage[k1 - 1];
            NativeImage nativeimage = new NativeImage(nativeimage1.m_84982_() >> 1, nativeimage1.m_85084_() >> 1, false);
            int k = nativeimage.m_84982_();
            int l = nativeimage.m_85084_();

            for(int i1 = 0; i1 < k; ++i1) {
               for(int j1 = 0; j1 < l; ++j1) {
                  nativeimage.m_84988_(i1, j1, m_118048_(nativeimage1.m_84985_(i1 * 2 + 0, j1 * 2 + 0), nativeimage1.m_84985_(i1 * 2 + 1, j1 * 2 + 0), nativeimage1.m_84985_(i1 * 2 + 0, j1 * 2 + 1), nativeimage1.m_84985_(i1 * 2 + 1, j1 * 2 + 1), flag));
               }
            }

            anativeimage[k1] = nativeimage;
         }
      }

      return anativeimage;
   }

   private static int m_118048_(int p_118049_, int p_118050_, int p_118051_, int p_118052_, boolean p_118053_) {
      if (p_118053_) {
         float f = 0.0F;
         float f1 = 0.0F;
         float f2 = 0.0F;
         float f3 = 0.0F;
         if (p_118049_ >> 24 != 0) {
            f += m_118040_(p_118049_ >> 24);
            f1 += m_118040_(p_118049_ >> 16);
            f2 += m_118040_(p_118049_ >> 8);
            f3 += m_118040_(p_118049_ >> 0);
         }

         if (p_118050_ >> 24 != 0) {
            f += m_118040_(p_118050_ >> 24);
            f1 += m_118040_(p_118050_ >> 16);
            f2 += m_118040_(p_118050_ >> 8);
            f3 += m_118040_(p_118050_ >> 0);
         }

         if (p_118051_ >> 24 != 0) {
            f += m_118040_(p_118051_ >> 24);
            f1 += m_118040_(p_118051_ >> 16);
            f2 += m_118040_(p_118051_ >> 8);
            f3 += m_118040_(p_118051_ >> 0);
         }

         if (p_118052_ >> 24 != 0) {
            f += m_118040_(p_118052_ >> 24);
            f1 += m_118040_(p_118052_ >> 16);
            f2 += m_118040_(p_118052_ >> 8);
            f3 += m_118040_(p_118052_ >> 0);
         }

         f = f / 4.0F;
         f1 = f1 / 4.0F;
         f2 = f2 / 4.0F;
         f3 = f3 / 4.0F;
         int i1 = (int)(Math.pow((double)f, 0.45454545454545453D) * 255.0D);
         int j1 = (int)(Math.pow((double)f1, 0.45454545454545453D) * 255.0D);
         int k1 = (int)(Math.pow((double)f2, 0.45454545454545453D) * 255.0D);
         int l1 = (int)(Math.pow((double)f3, 0.45454545454545453D) * 255.0D);
         if (i1 < 96) {
            i1 = 0;
         }

         return i1 << 24 | j1 << 16 | k1 << 8 | l1;
      } else {
         int i = m_118042_(p_118049_, p_118050_, p_118051_, p_118052_, 24);
         int j = m_118042_(p_118049_, p_118050_, p_118051_, p_118052_, 16);
         int k = m_118042_(p_118049_, p_118050_, p_118051_, p_118052_, 8);
         int l = m_118042_(p_118049_, p_118050_, p_118051_, p_118052_, 0);
         return i << 24 | j << 16 | k << 8 | l;
      }
   }

   private static int m_118042_(int p_118043_, int p_118044_, int p_118045_, int p_118046_, int p_118047_) {
      float f = m_118040_(p_118043_ >> p_118047_);
      float f1 = m_118040_(p_118044_ >> p_118047_);
      float f2 = m_118040_(p_118045_ >> p_118047_);
      float f3 = m_118040_(p_118046_ >> p_118047_);
      float f4 = (float)((double)((float)Math.pow((double)(f + f1 + f2 + f3) * 0.25D, 0.45454545454545453D)));
      return (int)((double)f4 * 255.0D);
   }

   private static float m_118040_(int p_118041_) {
      return f_118038_[p_118041_ & 255];
   }
}