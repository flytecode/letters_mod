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
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DragonFireballRenderer extends EntityRenderer<DragonFireball> {
   private static final ResourceLocation f_114060_ = new ResourceLocation("textures/entity/enderdragon/dragon_fireball.png");
   private static final RenderType f_114061_ = RenderType.m_110458_(f_114060_);

   public DragonFireballRenderer(EntityRendererProvider.Context p_173962_) {
      super(p_173962_);
   }

   protected int m_6086_(DragonFireball p_114087_, BlockPos p_114088_) {
      return 15;
   }

   public void m_7392_(DragonFireball p_114080_, float p_114081_, float p_114082_, PoseStack p_114083_, MultiBufferSource p_114084_, int p_114085_) {
      p_114083_.m_85836_();
      p_114083_.m_85841_(2.0F, 2.0F, 2.0F);
      p_114083_.m_85845_(this.f_114476_.m_114470_());
      p_114083_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F));
      PoseStack.Pose posestack$pose = p_114083_.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      VertexConsumer vertexconsumer = p_114084_.m_6299_(f_114061_);
      m_114089_(vertexconsumer, matrix4f, matrix3f, p_114085_, 0.0F, 0, 0, 1);
      m_114089_(vertexconsumer, matrix4f, matrix3f, p_114085_, 1.0F, 0, 1, 1);
      m_114089_(vertexconsumer, matrix4f, matrix3f, p_114085_, 1.0F, 1, 1, 0);
      m_114089_(vertexconsumer, matrix4f, matrix3f, p_114085_, 0.0F, 1, 0, 0);
      p_114083_.m_85849_();
      super.m_7392_(p_114080_, p_114081_, p_114082_, p_114083_, p_114084_, p_114085_);
   }

   private static void m_114089_(VertexConsumer p_114090_, Matrix4f p_114091_, Matrix3f p_114092_, int p_114093_, float p_114094_, int p_114095_, int p_114096_, int p_114097_) {
      p_114090_.m_85982_(p_114091_, p_114094_ - 0.5F, (float)p_114095_ - 0.25F, 0.0F).m_6122_(255, 255, 255, 255).m_7421_((float)p_114096_, (float)p_114097_).m_86008_(OverlayTexture.f_118083_).m_85969_(p_114093_).m_85977_(p_114092_, 0.0F, 1.0F, 0.0F).m_5752_();
   }

   public ResourceLocation m_5478_(DragonFireball p_114078_) {
      return f_114060_;
   }
}