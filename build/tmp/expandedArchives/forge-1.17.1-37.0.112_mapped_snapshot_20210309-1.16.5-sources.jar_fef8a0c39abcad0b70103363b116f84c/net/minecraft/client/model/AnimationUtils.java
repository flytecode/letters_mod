package net.minecraft.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.CrossbowItem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AnimationUtils {
   public static void m_102097_(ModelPart p_102098_, ModelPart p_102099_, ModelPart p_102100_, boolean p_102101_) {
      ModelPart modelpart = p_102101_ ? p_102098_ : p_102099_;
      ModelPart modelpart1 = p_102101_ ? p_102099_ : p_102098_;
      modelpart.f_104204_ = (p_102101_ ? -0.3F : 0.3F) + p_102100_.f_104204_;
      modelpart1.f_104204_ = (p_102101_ ? 0.6F : -0.6F) + p_102100_.f_104204_;
      modelpart.f_104203_ = (-(float)Math.PI / 2F) + p_102100_.f_104203_ + 0.1F;
      modelpart1.f_104203_ = -1.5F + p_102100_.f_104203_;
   }

   public static void m_102086_(ModelPart p_102087_, ModelPart p_102088_, LivingEntity p_102089_, boolean p_102090_) {
      ModelPart modelpart = p_102090_ ? p_102087_ : p_102088_;
      ModelPart modelpart1 = p_102090_ ? p_102088_ : p_102087_;
      modelpart.f_104204_ = p_102090_ ? -0.8F : 0.8F;
      modelpart.f_104203_ = -0.97079635F;
      modelpart1.f_104203_ = modelpart.f_104203_;
      float f = (float)CrossbowItem.m_40939_(p_102089_.m_21211_());
      float f1 = Mth.m_14036_((float)p_102089_.m_21252_(), 0.0F, f);
      float f2 = f1 / f;
      modelpart1.f_104204_ = Mth.m_14179_(f2, 0.4F, 0.85F) * (float)(p_102090_ ? 1 : -1);
      modelpart1.f_104203_ = Mth.m_14179_(f2, modelpart1.f_104203_, (-(float)Math.PI / 2F));
   }

   public static <T extends Mob> void m_102091_(ModelPart p_102092_, ModelPart p_102093_, T p_102094_, float p_102095_, float p_102096_) {
      float f = Mth.m_14031_(p_102095_ * (float)Math.PI);
      float f1 = Mth.m_14031_((1.0F - (1.0F - p_102095_) * (1.0F - p_102095_)) * (float)Math.PI);
      p_102092_.f_104205_ = 0.0F;
      p_102093_.f_104205_ = 0.0F;
      p_102092_.f_104204_ = 0.15707964F;
      p_102093_.f_104204_ = -0.15707964F;
      if (p_102094_.m_5737_() == HumanoidArm.RIGHT) {
         p_102092_.f_104203_ = -1.8849558F + Mth.m_14089_(p_102096_ * 0.09F) * 0.15F;
         p_102093_.f_104203_ = -0.0F + Mth.m_14089_(p_102096_ * 0.19F) * 0.5F;
         p_102092_.f_104203_ += f * 2.2F - f1 * 0.4F;
         p_102093_.f_104203_ += f * 1.2F - f1 * 0.4F;
      } else {
         p_102092_.f_104203_ = -0.0F + Mth.m_14089_(p_102096_ * 0.19F) * 0.5F;
         p_102093_.f_104203_ = -1.8849558F + Mth.m_14089_(p_102096_ * 0.09F) * 0.15F;
         p_102092_.f_104203_ += f * 1.2F - f1 * 0.4F;
         p_102093_.f_104203_ += f * 2.2F - f1 * 0.4F;
      }

      m_102082_(p_102092_, p_102093_, p_102096_);
   }

   public static void m_170341_(ModelPart p_170342_, float p_170343_, float p_170344_) {
      p_170342_.f_104205_ += p_170344_ * (Mth.m_14089_(p_170343_ * 0.09F) * 0.05F + 0.05F);
      p_170342_.f_104203_ += p_170344_ * Mth.m_14031_(p_170343_ * 0.067F) * 0.05F;
   }

   public static void m_102082_(ModelPart p_102083_, ModelPart p_102084_, float p_102085_) {
      m_170341_(p_102083_, p_102085_, 1.0F);
      m_170341_(p_102084_, p_102085_, -1.0F);
   }

   public static void m_102102_(ModelPart p_102103_, ModelPart p_102104_, boolean p_102105_, float p_102106_, float p_102107_) {
      float f = Mth.m_14031_(p_102106_ * (float)Math.PI);
      float f1 = Mth.m_14031_((1.0F - (1.0F - p_102106_) * (1.0F - p_102106_)) * (float)Math.PI);
      p_102104_.f_104205_ = 0.0F;
      p_102103_.f_104205_ = 0.0F;
      p_102104_.f_104204_ = -(0.1F - f * 0.6F);
      p_102103_.f_104204_ = 0.1F - f * 0.6F;
      float f2 = -(float)Math.PI / (p_102105_ ? 1.5F : 2.25F);
      p_102104_.f_104203_ = f2;
      p_102103_.f_104203_ = f2;
      p_102104_.f_104203_ += f * 1.2F - f1 * 0.4F;
      p_102103_.f_104203_ += f * 1.2F - f1 * 0.4F;
      m_102082_(p_102104_, p_102103_, p_102107_);
   }
}