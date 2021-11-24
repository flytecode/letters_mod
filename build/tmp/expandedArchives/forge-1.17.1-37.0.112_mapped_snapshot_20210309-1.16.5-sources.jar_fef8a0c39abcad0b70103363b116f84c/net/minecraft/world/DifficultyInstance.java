package net.minecraft.world;

import javax.annotation.concurrent.Immutable;
import net.minecraft.util.Mth;

@Immutable
public class DifficultyInstance {
   private static final float f_146646_ = -72000.0F;
   private static final float f_146647_ = 1440000.0F;
   private static final float f_146648_ = 3600000.0F;
   private final Difficulty f_19041_;
   private final float f_19042_;

   public DifficultyInstance(Difficulty p_19044_, long p_19045_, long p_19046_, float p_19047_) {
      this.f_19041_ = p_19044_;
      this.f_19042_ = this.m_19051_(p_19044_, p_19045_, p_19046_, p_19047_);
   }

   public Difficulty m_19048_() {
      return this.f_19041_;
   }

   public float m_19056_() {
      return this.f_19042_;
   }

   public boolean m_146649_() {
      return this.f_19042_ >= (float)Difficulty.HARD.ordinal();
   }

   public boolean m_19049_(float p_19050_) {
      return this.f_19042_ > p_19050_;
   }

   public float m_19057_() {
      if (this.f_19042_ < 2.0F) {
         return 0.0F;
      } else {
         return this.f_19042_ > 4.0F ? 1.0F : (this.f_19042_ - 2.0F) / 2.0F;
      }
   }

   private float m_19051_(Difficulty p_19052_, long p_19053_, long p_19054_, float p_19055_) {
      if (p_19052_ == Difficulty.PEACEFUL) {
         return 0.0F;
      } else {
         boolean flag = p_19052_ == Difficulty.HARD;
         float f = 0.75F;
         float f1 = Mth.m_14036_(((float)p_19053_ + -72000.0F) / 1440000.0F, 0.0F, 1.0F) * 0.25F;
         f = f + f1;
         float f2 = 0.0F;
         f2 = f2 + Mth.m_14036_((float)p_19054_ / 3600000.0F, 0.0F, 1.0F) * (flag ? 1.0F : 0.75F);
         f2 = f2 + Mth.m_14036_(p_19055_ * 0.25F, 0.0F, f1);
         if (p_19052_ == Difficulty.EASY) {
            f2 *= 0.5F;
         }

         f = f + f2;
         return (float)p_19052_.m_19028_() * f;
      }
   }
}