package com.mojang.blaze3d.vertex;

import com.google.common.collect.Queues;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import java.util.Deque;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PoseStack {
   private final Deque<PoseStack.Pose> f_85834_ = Util.m_137469_(Queues.newArrayDeque(), (p_85848_) -> {
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.m_27624_();
      Matrix3f matrix3f = new Matrix3f();
      matrix3f.m_8180_();
      p_85848_.add(new PoseStack.Pose(matrix4f, matrix3f));
   });

   public void m_85837_(double p_85838_, double p_85839_, double p_85840_) {
      PoseStack.Pose posestack$pose = this.f_85834_.getLast();
      posestack$pose.f_85852_.m_162199_((float)p_85838_, (float)p_85839_, (float)p_85840_);
   }

   public void m_85841_(float p_85842_, float p_85843_, float p_85844_) {
      PoseStack.Pose posestack$pose = this.f_85834_.getLast();
      posestack$pose.f_85852_.m_27644_(Matrix4f.m_27632_(p_85842_, p_85843_, p_85844_));
      if (p_85842_ == p_85843_ && p_85843_ == p_85844_) {
         if (p_85842_ > 0.0F) {
            return;
         }

         posestack$pose.f_85853_.m_8156_(-1.0F);
      }

      float f = 1.0F / p_85842_;
      float f1 = 1.0F / p_85843_;
      float f2 = 1.0F / p_85844_;
      float f3 = Mth.m_14199_(f * f1 * f2);
      posestack$pose.f_85853_.m_8178_(Matrix3f.m_8174_(f3 * f, f3 * f1, f3 * f2));
   }

   public void m_85845_(Quaternion p_85846_) {
      PoseStack.Pose posestack$pose = this.f_85834_.getLast();
      posestack$pose.f_85852_.m_27646_(p_85846_);
      posestack$pose.f_85853_.m_8171_(p_85846_);
   }

   public void m_85836_() {
      PoseStack.Pose posestack$pose = this.f_85834_.getLast();
      this.f_85834_.addLast(new PoseStack.Pose(posestack$pose.f_85852_.m_27658_(), posestack$pose.f_85853_.m_8183_()));
   }

   public void m_85849_() {
      this.f_85834_.removeLast();
   }

   public PoseStack.Pose m_85850_() {
      return this.f_85834_.getLast();
   }

   public boolean m_85851_() {
      return this.f_85834_.size() == 1;
   }

   public void m_166856_() {
      PoseStack.Pose posestack$pose = this.f_85834_.getLast();
      posestack$pose.f_85852_.m_27624_();
      posestack$pose.f_85853_.m_8180_();
   }

   public void m_166854_(Matrix4f p_166855_) {
      (this.f_85834_.getLast()).f_85852_.m_27644_(p_166855_);
   }

   @OnlyIn(Dist.CLIENT)
   public static final class Pose {
      final Matrix4f f_85852_;
      final Matrix3f f_85853_;

      Pose(Matrix4f p_85855_, Matrix3f p_85856_) {
         this.f_85852_ = p_85855_;
         this.f_85853_ = p_85856_;
      }

      public Matrix4f m_85861_() {
         return this.f_85852_;
      }

      public Matrix3f m_85864_() {
         return this.f_85853_;
      }
   }
}