package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.PaintingTextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.decoration.Motive;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PaintingRenderer extends EntityRenderer<Painting> {
   public PaintingRenderer(EntityRendererProvider.Context p_174332_) {
      super(p_174332_);
   }

   public void m_7392_(Painting p_115552_, float p_115553_, float p_115554_, PoseStack p_115555_, MultiBufferSource p_115556_, int p_115557_) {
      p_115555_.m_85836_();
      p_115555_.m_85845_(Vector3f.f_122225_.m_122240_(180.0F - p_115553_));
      Motive motive = p_115552_.f_31902_;
      float f = 0.0625F;
      p_115555_.m_85841_(0.0625F, 0.0625F, 0.0625F);
      VertexConsumer vertexconsumer = p_115556_.m_6299_(RenderType.m_110446_(this.m_5478_(p_115552_)));
      PaintingTextureManager paintingtexturemanager = Minecraft.m_91087_().m_91305_();
      this.m_115558_(p_115555_, vertexconsumer, p_115552_, motive.m_31896_(), motive.m_31901_(), paintingtexturemanager.m_118804_(motive), paintingtexturemanager.m_118806_());
      p_115555_.m_85849_();
      super.m_7392_(p_115552_, p_115553_, p_115554_, p_115555_, p_115556_, p_115557_);
   }

   public ResourceLocation m_5478_(Painting p_115550_) {
      return Minecraft.m_91087_().m_91305_().m_118806_().m_118414_().m_118330_();
   }

   private void m_115558_(PoseStack p_115559_, VertexConsumer p_115560_, Painting p_115561_, int p_115562_, int p_115563_, TextureAtlasSprite p_115564_, TextureAtlasSprite p_115565_) {
      PoseStack.Pose posestack$pose = p_115559_.m_85850_();
      Matrix4f matrix4f = posestack$pose.m_85861_();
      Matrix3f matrix3f = posestack$pose.m_85864_();
      float f = (float)(-p_115562_) / 2.0F;
      float f1 = (float)(-p_115563_) / 2.0F;
      float f2 = 0.5F;
      float f3 = p_115565_.m_118409_();
      float f4 = p_115565_.m_118410_();
      float f5 = p_115565_.m_118411_();
      float f6 = p_115565_.m_118412_();
      float f7 = p_115565_.m_118409_();
      float f8 = p_115565_.m_118410_();
      float f9 = p_115565_.m_118411_();
      float f10 = p_115565_.m_118393_(1.0D);
      float f11 = p_115565_.m_118409_();
      float f12 = p_115565_.m_118367_(1.0D);
      float f13 = p_115565_.m_118411_();
      float f14 = p_115565_.m_118412_();
      int i = p_115562_ / 16;
      int j = p_115563_ / 16;
      double d0 = 16.0D / (double)i;
      double d1 = 16.0D / (double)j;

      for(int k = 0; k < i; ++k) {
         for(int l = 0; l < j; ++l) {
            float f15 = f + (float)((k + 1) * 16);
            float f16 = f + (float)(k * 16);
            float f17 = f1 + (float)((l + 1) * 16);
            float f18 = f1 + (float)(l * 16);
            int i1 = p_115561_.m_146903_();
            int j1 = Mth.m_14107_(p_115561_.m_20186_() + (double)((f17 + f18) / 2.0F / 16.0F));
            int k1 = p_115561_.m_146907_();
            Direction direction = p_115561_.m_6350_();
            if (direction == Direction.NORTH) {
               i1 = Mth.m_14107_(p_115561_.m_20185_() + (double)((f15 + f16) / 2.0F / 16.0F));
            }

            if (direction == Direction.WEST) {
               k1 = Mth.m_14107_(p_115561_.m_20189_() - (double)((f15 + f16) / 2.0F / 16.0F));
            }

            if (direction == Direction.SOUTH) {
               i1 = Mth.m_14107_(p_115561_.m_20185_() - (double)((f15 + f16) / 2.0F / 16.0F));
            }

            if (direction == Direction.EAST) {
               k1 = Mth.m_14107_(p_115561_.m_20189_() + (double)((f15 + f16) / 2.0F / 16.0F));
            }

            int l1 = LevelRenderer.m_109541_(p_115561_.f_19853_, new BlockPos(i1, j1, k1));
            float f19 = p_115564_.m_118367_(d0 * (double)(i - k));
            float f20 = p_115564_.m_118367_(d0 * (double)(i - (k + 1)));
            float f21 = p_115564_.m_118393_(d1 * (double)(j - l));
            float f22 = p_115564_.m_118393_(d1 * (double)(j - (l + 1)));
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f18, f20, f21, -0.5F, 0, 0, -1, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f18, f19, f21, -0.5F, 0, 0, -1, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f17, f19, f22, -0.5F, 0, 0, -1, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f17, f20, f22, -0.5F, 0, 0, -1, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f17, f3, f5, 0.5F, 0, 0, 1, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f17, f4, f5, 0.5F, 0, 0, 1, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f18, f4, f6, 0.5F, 0, 0, 1, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f18, f3, f6, 0.5F, 0, 0, 1, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f17, f7, f9, -0.5F, 0, 1, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f17, f8, f9, -0.5F, 0, 1, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f17, f8, f10, 0.5F, 0, 1, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f17, f7, f10, 0.5F, 0, 1, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f18, f7, f9, 0.5F, 0, -1, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f18, f8, f9, 0.5F, 0, -1, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f18, f8, f10, -0.5F, 0, -1, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f18, f7, f10, -0.5F, 0, -1, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f17, f12, f13, 0.5F, -1, 0, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f18, f12, f14, 0.5F, -1, 0, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f18, f11, f14, -0.5F, -1, 0, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f15, f17, f11, f13, -0.5F, -1, 0, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f17, f12, f13, -0.5F, 1, 0, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f18, f12, f14, -0.5F, 1, 0, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f18, f11, f14, 0.5F, 1, 0, 0, l1);
            this.m_115536_(matrix4f, matrix3f, p_115560_, f16, f17, f11, f13, 0.5F, 1, 0, 0, l1);
         }
      }

   }

   private void m_115536_(Matrix4f p_115537_, Matrix3f p_115538_, VertexConsumer p_115539_, float p_115540_, float p_115541_, float p_115542_, float p_115543_, float p_115544_, int p_115545_, int p_115546_, int p_115547_, int p_115548_) {
      p_115539_.m_85982_(p_115537_, p_115540_, p_115541_, p_115544_).m_6122_(255, 255, 255, 255).m_7421_(p_115542_, p_115543_).m_86008_(OverlayTexture.f_118083_).m_85969_(p_115548_).m_85977_(p_115538_, (float)p_115545_, (float)p_115546_, (float)p_115547_).m_5752_();
   }
}