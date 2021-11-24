package net.minecraft.world.damagesource;

import net.minecraft.util.Mth;

public class CombatRules {
   public static final float f_146688_ = 20.0F;
   public static final float f_146689_ = 25.0F;
   public static final float f_146690_ = 2.0F;
   public static final float f_146691_ = 0.2F;
   private static final int f_146692_ = 4;

   public static float m_19272_(float p_19273_, float p_19274_, float p_19275_) {
      float f = 2.0F + p_19275_ / 4.0F;
      float f1 = Mth.m_14036_(p_19274_ - p_19273_ / f, p_19274_ * 0.2F, 20.0F);
      return p_19273_ * (1.0F - f1 / 25.0F);
   }

   public static float m_19269_(float p_19270_, float p_19271_) {
      float f = Mth.m_14036_(p_19271_, 0.0F, 20.0F);
      return p_19270_ * (1.0F - f / 25.0F);
   }
}