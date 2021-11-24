package net.minecraft.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CubeMap {
   private static final int f_172561_ = 6;
   private final ResourceLocation[] f_108846_ = new ResourceLocation[6];

   public CubeMap(ResourceLocation p_108848_) {
      for(int i = 0; i < 6; ++i) {
         this.f_108846_[i] = new ResourceLocation(p_108848_.m_135827_(), p_108848_.m_135815_() + "_" + i + ".png");
      }

   }

   public void m_108849_(Minecraft p_108850_, float p_108851_, float p_108852_, float p_108853_) {
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      Matrix4f matrix4f = Matrix4f.m_27625_(85.0D, (float)p_108850_.m_91268_().m_85441_() / (float)p_108850_.m_91268_().m_85442_(), 0.05F, 10.0F);
      RenderSystem.m_157183_();
      RenderSystem.m_157425_(matrix4f);
      PoseStack posestack = RenderSystem.m_157191_();
      posestack.m_85836_();
      posestack.m_166856_();
      posestack.m_85845_(Vector3f.f_122223_.m_122240_(180.0F));
      RenderSystem.m_157182_();
      RenderSystem.m_157427_(GameRenderer::m_172820_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69478_();
      RenderSystem.m_69464_();
      RenderSystem.m_69458_(false);
      RenderSystem.m_69453_();
      int i = 2;

      for(int j = 0; j < 4; ++j) {
         posestack.m_85836_();
         float f = ((float)(j % 2) / 2.0F - 0.5F) / 256.0F;
         float f1 = ((float)(j / 2) / 2.0F - 0.5F) / 256.0F;
         float f2 = 0.0F;
         posestack.m_85837_((double)f, (double)f1, 0.0D);
         posestack.m_85845_(Vector3f.f_122223_.m_122240_(p_108851_));
         posestack.m_85845_(Vector3f.f_122225_.m_122240_(p_108852_));
         RenderSystem.m_157182_();

         for(int k = 0; k < 6; ++k) {
            RenderSystem.m_157456_(0, this.f_108846_[k]);
            bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85819_);
            int l = Math.round(255.0F * p_108853_) / (j + 1);
            if (k == 0) {
               bufferbuilder.m_5483_(-1.0D, -1.0D, 1.0D).m_7421_(0.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(-1.0D, 1.0D, 1.0D).m_7421_(0.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(1.0D, 1.0D, 1.0D).m_7421_(1.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(1.0D, -1.0D, 1.0D).m_7421_(1.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
            }

            if (k == 1) {
               bufferbuilder.m_5483_(1.0D, -1.0D, 1.0D).m_7421_(0.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(1.0D, 1.0D, 1.0D).m_7421_(0.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(1.0D, 1.0D, -1.0D).m_7421_(1.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(1.0D, -1.0D, -1.0D).m_7421_(1.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
            }

            if (k == 2) {
               bufferbuilder.m_5483_(1.0D, -1.0D, -1.0D).m_7421_(0.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(1.0D, 1.0D, -1.0D).m_7421_(0.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(-1.0D, 1.0D, -1.0D).m_7421_(1.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(-1.0D, -1.0D, -1.0D).m_7421_(1.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
            }

            if (k == 3) {
               bufferbuilder.m_5483_(-1.0D, -1.0D, -1.0D).m_7421_(0.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(-1.0D, 1.0D, -1.0D).m_7421_(0.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(-1.0D, 1.0D, 1.0D).m_7421_(1.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(-1.0D, -1.0D, 1.0D).m_7421_(1.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
            }

            if (k == 4) {
               bufferbuilder.m_5483_(-1.0D, -1.0D, -1.0D).m_7421_(0.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(-1.0D, -1.0D, 1.0D).m_7421_(0.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(1.0D, -1.0D, 1.0D).m_7421_(1.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(1.0D, -1.0D, -1.0D).m_7421_(1.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
            }

            if (k == 5) {
               bufferbuilder.m_5483_(-1.0D, 1.0D, 1.0D).m_7421_(0.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(-1.0D, 1.0D, -1.0D).m_7421_(0.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(1.0D, 1.0D, -1.0D).m_7421_(1.0F, 1.0F).m_6122_(255, 255, 255, l).m_5752_();
               bufferbuilder.m_5483_(1.0D, 1.0D, 1.0D).m_7421_(1.0F, 0.0F).m_6122_(255, 255, 255, l).m_5752_();
            }

            tesselator.m_85914_();
         }

         posestack.m_85849_();
         RenderSystem.m_157182_();
         RenderSystem.m_69444_(true, true, true, false);
      }

      RenderSystem.m_69444_(true, true, true, true);
      RenderSystem.m_157424_();
      posestack.m_85849_();
      RenderSystem.m_157182_();
      RenderSystem.m_69458_(true);
      RenderSystem.m_69481_();
      RenderSystem.m_69482_();
   }

   public CompletableFuture<Void> m_108854_(TextureManager p_108855_, Executor p_108856_) {
      CompletableFuture<?>[] completablefuture = new CompletableFuture[6];

      for(int i = 0; i < completablefuture.length; ++i) {
         completablefuture[i] = p_108855_.m_118501_(this.f_108846_[i], p_108856_);
      }

      return CompletableFuture.allOf(completablefuture);
   }
}