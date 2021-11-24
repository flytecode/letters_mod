package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ArrowRenderer<T extends AbstractArrow> extends EntityRenderer<T> {
   public ArrowRenderer(EntityRendererProvider.Context p_173917_) {
      super(p_173917_);
   }

   public void m_7392_(T p_113839_, float p_113840_, float p_113841_, PoseStack p_113842_, MultiBufferSource p_113843_, int p_113844_) {
      p_113842_.m_85836_();
      p_113842_.m_85845_(Vector3f.f_122225_.m_122240_(Mth.m_14179_(p_113841_, p_113839_.f_19859_, p_113839_.m_146908_()) - 90.0F));
      p_113842_.m_85845_(Vector3f.f_122227_.m_122240_(Mth.m_14179_(p_113841_, p_113839_.f_19860_, p_113839_.m_146909_())));
      int i = 0;
      float f = 0.0F;
      float f1 = 0.5F;
      float f2 = 0.0F;
      float f3 = 0.15625F;
      float f4 = 0.0F;
      float f5 = 0.15625F;
      float f6 = 0.15625F;
      float f7 = 0.3125F;
      float f8 = 0.05625F;
      float f9 = (float)p_113839_.f_36706_ - p_113841_;
      if (f9 > 0.0F) {
         float f10 = -Mth.m_14031_(f9 * 3.0F) * f9;
         p_113842_.m_85845_(Vector3f.f_122227_.m_122240_(f10));
      }

      p_113842_.m_85845_(Vector3f.f_122223_.m_122240_(45.0F));
      p_113842_.m_85841_(0.05625F, 0.05625F, 0.05625F);
      p_113842_.m_85837_(-4.0D, 0.0D, 0.0D);
      VertexConsumer vertexconsumer = p_113843_.m_6299_(RenderType.m_110452_(this.m_5478_(p_113839_)));
      PoseStack.Pose posestack$pose = p_113842_.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      this.m_113825_(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, 0.0F, 0.15625F, -1, 0, 0, p_113844_);
      this.m_113825_(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, 0.15625F, 0.15625F, -1, 0, 0, p_113844_);
      this.m_113825_(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, 0.15625F, 0.3125F, -1, 0, 0, p_113844_);
      this.m_113825_(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, 0.0F, 0.3125F, -1, 0, 0, p_113844_);
      this.m_113825_(matrix4f, matrix3f, vertexconsumer, -7, 2, -2, 0.0F, 0.15625F, 1, 0, 0, p_113844_);
      this.m_113825_(matrix4f, matrix3f, vertexconsumer, -7, 2, 2, 0.15625F, 0.15625F, 1, 0, 0, p_113844_);
      this.m_113825_(matrix4f, matrix3f, vertexconsumer, -7, -2, 2, 0.15625F, 0.3125F, 1, 0, 0, p_113844_);
      this.m_113825_(matrix4f, matrix3f, vertexconsumer, -7, -2, -2, 0.0F, 0.3125F, 1, 0, 0, p_113844_);

      for(int j = 0; j < 4; ++j) {
         p_113842_.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
         this.m_113825_(matrix4f, matrix3f, vertexconsumer, -8, -2, 0, 0.0F, 0.0F, 0, 1, 0, p_113844_);
         this.m_113825_(matrix4f, matrix3f, vertexconsumer, 8, -2, 0, 0.5F, 0.0F, 0, 1, 0, p_113844_);
         this.m_113825_(matrix4f, matrix3f, vertexconsumer, 8, 2, 0, 0.5F, 0.15625F, 0, 1, 0, p_113844_);
         this.m_113825_(matrix4f, matrix3f, vertexconsumer, -8, 2, 0, 0.0F, 0.15625F, 0, 1, 0, p_113844_);
      }

      p_113842_.m_85849_();
      super.m_7392_(p_113839_, p_113840_, p_113841_, p_113842_, p_113843_, p_113844_);
   }

   public void m_113825_(Matrix4f p_113826_, Matrix3f p_113827_, VertexConsumer p_113828_, int p_113829_, int p_113830_, int p_113831_, float p_113832_, float p_113833_, int p_113834_, int p_113835_, int p_113836_, int p_113837_) {
      p_113828_.m_85982_(p_113826_, (float)p_113829_, (float)p_113830_, (float)p_113831_).m_6122_(255, 255, 255, 255).m_7421_(p_113832_, p_113833_).m_86008_(OverlayTexture.f_118083_).m_85969_(p_113837_).m_85977_(p_113827_, (float)p_113834_, (float)p_113836_, (float)p_113835_).m_5752_();
   }
}