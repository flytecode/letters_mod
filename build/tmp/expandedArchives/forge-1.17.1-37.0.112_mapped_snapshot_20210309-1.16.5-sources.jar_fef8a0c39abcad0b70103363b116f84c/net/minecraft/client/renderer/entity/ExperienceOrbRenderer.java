package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ExperienceOrbRenderer extends EntityRenderer<ExperienceOrb> {
   private static final ResourceLocation f_114579_ = new ResourceLocation("textures/entity/experience_orb.png");
   private static final RenderType f_114580_ = RenderType.m_110467_(f_114579_);

   public ExperienceOrbRenderer(EntityRendererProvider.Context p_174110_) {
      super(p_174110_);
      this.f_114477_ = 0.15F;
      this.f_114478_ = 0.75F;
   }

   protected int m_6086_(ExperienceOrb p_114606_, BlockPos p_114607_) {
      return Mth.m_14045_(super.m_6086_(p_114606_, p_114607_) + 7, 0, 15);
   }

   public void m_7392_(ExperienceOrb p_114599_, float p_114600_, float p_114601_, PoseStack p_114602_, MultiBufferSource p_114603_, int p_114604_) {
      p_114602_.m_85836_();
      int i = p_114599_.m_20802_();
      float f = (float)(i % 4 * 16 + 0) / 64.0F;
      float f1 = (float)(i % 4 * 16 + 16) / 64.0F;
      float f2 = (float)(i / 4 * 16 + 0) / 64.0F;
      float f3 = (float)(i / 4 * 16 + 16) / 64.0F;
      float f4 = 1.0F;
      float f5 = 0.5F;
      float f6 = 0.25F;
      float f7 = 255.0F;
      float f8 = ((float)p_114599_.f_19797_ + p_114601_) / 2.0F;
      int j = (int)((Mth.m_14031_(f8 + 0.0F) + 1.0F) * 0.5F * 255.0F);
      int k = 255;
      int l = (int)((Mth.m_14031_(f8 + 4.1887903F) + 1.0F) * 0.1F * 255.0F);
      p_114602_.m_85837_(0.0D, (double)0.1F, 0.0D);
      p_114602_.m_85845_(this.f_114476_.m_114470_());
      p_114602_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
      float f9 = 0.3F;
      p_114602_.m_85841_(0.3F, 0.3F, 0.3F);
      VertexConsumer vertexconsumer = p_114603_.m_6299_(f_114580_);
      PoseStack.Pose posestack$pose = p_114602_.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      m_114608_(vertexconsumer, matrix4f, matrix3f, -0.5F, -0.25F, j, 255, l, f, f3, p_114604_);
      m_114608_(vertexconsumer, matrix4f, matrix3f, 0.5F, -0.25F, j, 255, l, f1, f3, p_114604_);
      m_114608_(vertexconsumer, matrix4f, matrix3f, 0.5F, 0.75F, j, 255, l, f1, f2, p_114604_);
      m_114608_(vertexconsumer, matrix4f, matrix3f, -0.5F, 0.75F, j, 255, l, f, f2, p_114604_);
      p_114602_.m_85849_();
      super.m_7392_(p_114599_, p_114600_, p_114601_, p_114602_, p_114603_, p_114604_);
   }

   private static void m_114608_(VertexConsumer p_114609_, Matrix4f p_114610_, Matrix3f p_114611_, float p_114612_, float p_114613_, int p_114614_, int p_114615_, int p_114616_, float p_114617_, float p_114618_, int p_114619_) {
      p_114609_.m_85982_(p_114610_, p_114612_, p_114613_, 0.0F).m_6122_(p_114614_, p_114615_, p_114616_, 128).m_7421_(p_114617_, p_114618_).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114619_).m_85977_(p_114611_, 0.0F, 1.0F, 0.0F).m_5752_();
   }

   public ResourceLocation m_5478_(ExperienceOrb p_114597_) {
      return f_114579_;
   }
}