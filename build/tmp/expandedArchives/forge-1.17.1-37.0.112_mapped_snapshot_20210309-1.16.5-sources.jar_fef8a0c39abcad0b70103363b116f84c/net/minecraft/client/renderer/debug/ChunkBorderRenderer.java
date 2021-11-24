package net.minecraft.client.renderer.debug;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ChunkBorderRenderer implements DebugRenderer.SimpleDebugRenderer {
   private final Minecraft f_113354_;

   public ChunkBorderRenderer(Minecraft p_113356_) {
      this.f_113354_ = p_113356_;
   }

   public void m_7790_(PoseStack p_113358_, MultiBufferSource p_113359_, double p_113360_, double p_113361_, double p_113362_) {
      RenderSystem.m_69482_();
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      Entity entity = this.f_113354_.f_91063_.m_109153_().m_90592_();
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      double d0 = (double)this.f_113354_.f_91073_.m_141937_() - p_113361_;
      double d1 = (double)this.f_113354_.f_91073_.m_151558_() - p_113361_;
      RenderSystem.m_69472_();
      RenderSystem.m_69461_();
      ChunkPos chunkpos = entity.m_146902_();
      double d2 = (double)chunkpos.m_45604_() - p_113360_;
      double d3 = (double)chunkpos.m_45605_() - p_113362_;
      RenderSystem.m_69832_(1.0F);
      bufferbuilder.m_166779_(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.f_85815_);

      for(int i = -16; i <= 32; i += 16) {
         for(int j = -16; j <= 32; j += 16) {
            bufferbuilder.m_5483_(d2 + (double)i, d0, d3 + (double)j).m_85950_(1.0F, 0.0F, 0.0F, 0.0F).m_5752_();
            bufferbuilder.m_5483_(d2 + (double)i, d0, d3 + (double)j).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
            bufferbuilder.m_5483_(d2 + (double)i, d1, d3 + (double)j).m_85950_(1.0F, 0.0F, 0.0F, 0.5F).m_5752_();
            bufferbuilder.m_5483_(d2 + (double)i, d1, d3 + (double)j).m_85950_(1.0F, 0.0F, 0.0F, 0.0F).m_5752_();
         }
      }

      for(int k = 2; k < 16; k += 2) {
         bufferbuilder.m_5483_(d2 + (double)k, d0, d3).m_85950_(1.0F, 1.0F, 0.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + (double)k, d0, d3).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + (double)k, d1, d3).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + (double)k, d1, d3).m_85950_(1.0F, 1.0F, 0.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + (double)k, d0, d3 + 16.0D).m_85950_(1.0F, 1.0F, 0.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + (double)k, d0, d3 + 16.0D).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + (double)k, d1, d3 + 16.0D).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + (double)k, d1, d3 + 16.0D).m_85950_(1.0F, 1.0F, 0.0F, 0.0F).m_5752_();
      }

      for(int l = 2; l < 16; l += 2) {
         bufferbuilder.m_5483_(d2, d0, d3 + (double)l).m_85950_(1.0F, 1.0F, 0.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_(d2, d0, d3 + (double)l).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2, d1, d3 + (double)l).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2, d1, d3 + (double)l).m_85950_(1.0F, 1.0F, 0.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + 16.0D, d0, d3 + (double)l).m_85950_(1.0F, 1.0F, 0.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + 16.0D, d0, d3 + (double)l).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + 16.0D, d1, d3 + (double)l).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + 16.0D, d1, d3 + (double)l).m_85950_(1.0F, 1.0F, 0.0F, 0.0F).m_5752_();
      }

      for(int i1 = this.f_113354_.f_91073_.m_141937_(); i1 <= this.f_113354_.f_91073_.m_151558_(); i1 += 2) {
         double d4 = (double)i1 - p_113361_;
         bufferbuilder.m_5483_(d2, d4, d3).m_85950_(1.0F, 1.0F, 0.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_(d2, d4, d3).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2, d4, d3 + 16.0D).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + 16.0D, d4, d3 + 16.0D).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + 16.0D, d4, d3).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2, d4, d3).m_85950_(1.0F, 1.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2, d4, d3).m_85950_(1.0F, 1.0F, 0.0F, 0.0F).m_5752_();
      }

      tesselator.m_85914_();
      RenderSystem.m_69832_(2.0F);
      bufferbuilder.m_166779_(VertexFormat.Mode.DEBUG_LINE_STRIP, DefaultVertexFormat.f_85815_);

      for(int j1 = 0; j1 <= 16; j1 += 16) {
         for(int l1 = 0; l1 <= 16; l1 += 16) {
            bufferbuilder.m_5483_(d2 + (double)j1, d0, d3 + (double)l1).m_85950_(0.25F, 0.25F, 1.0F, 0.0F).m_5752_();
            bufferbuilder.m_5483_(d2 + (double)j1, d0, d3 + (double)l1).m_85950_(0.25F, 0.25F, 1.0F, 1.0F).m_5752_();
            bufferbuilder.m_5483_(d2 + (double)j1, d1, d3 + (double)l1).m_85950_(0.25F, 0.25F, 1.0F, 1.0F).m_5752_();
            bufferbuilder.m_5483_(d2 + (double)j1, d1, d3 + (double)l1).m_85950_(0.25F, 0.25F, 1.0F, 0.0F).m_5752_();
         }
      }

      for(int k1 = this.f_113354_.f_91073_.m_141937_(); k1 <= this.f_113354_.f_91073_.m_151558_(); k1 += 16) {
         double d5 = (double)k1 - p_113361_;
         bufferbuilder.m_5483_(d2, d5, d3).m_85950_(0.25F, 0.25F, 1.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_(d2, d5, d3).m_85950_(0.25F, 0.25F, 1.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2, d5, d3 + 16.0D).m_85950_(0.25F, 0.25F, 1.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + 16.0D, d5, d3 + 16.0D).m_85950_(0.25F, 0.25F, 1.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2 + 16.0D, d5, d3).m_85950_(0.25F, 0.25F, 1.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2, d5, d3).m_85950_(0.25F, 0.25F, 1.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(d2, d5, d3).m_85950_(0.25F, 0.25F, 1.0F, 0.0F).m_5752_();
      }

      tesselator.m_85914_();
      RenderSystem.m_69832_(1.0F);
      RenderSystem.m_69478_();
      RenderSystem.m_69493_();
   }
}