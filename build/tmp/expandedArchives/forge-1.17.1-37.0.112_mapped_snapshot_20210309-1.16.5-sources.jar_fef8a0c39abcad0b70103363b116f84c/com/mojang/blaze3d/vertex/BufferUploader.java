package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import java.nio.ByteBuffer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BufferUploader {
   private static int f_166830_;
   private static int f_166831_;
   private static int f_166832_;
   @Nullable
   private static VertexFormat f_166833_;

   public static void m_166835_() {
      if (f_166833_ != null) {
         f_166833_.m_86024_();
         f_166833_ = null;
      }

      GlStateManager.m_84480_(34963, 0);
      f_166832_ = 0;
      GlStateManager.m_84480_(34962, 0);
      f_166831_ = 0;
      GlStateManager.m_157068_(0);
      f_166830_ = 0;
   }

   public static void m_166846_() {
      GlStateManager.m_84480_(34963, 0);
      f_166832_ = 0;
   }

   public static void m_85761_(BufferBuilder p_85762_) {
      if (!RenderSystem.m_69587_()) {
         RenderSystem.m_69879_(() -> {
            Pair<BufferBuilder.DrawState, ByteBuffer> pair1 = p_85762_.m_85728_();
            BufferBuilder.DrawState bufferbuilder$drawstate1 = pair1.getFirst();
            m_166838_(pair1.getSecond(), bufferbuilder$drawstate1.m_166810_(), bufferbuilder$drawstate1.m_85745_(), bufferbuilder$drawstate1.m_85746_(), bufferbuilder$drawstate1.m_166811_(), bufferbuilder$drawstate1.m_166809_(), bufferbuilder$drawstate1.m_166815_());
         });
      } else {
         Pair<BufferBuilder.DrawState, ByteBuffer> pair = p_85762_.m_85728_();
         BufferBuilder.DrawState bufferbuilder$drawstate = pair.getFirst();
         m_166838_(pair.getSecond(), bufferbuilder$drawstate.m_166810_(), bufferbuilder$drawstate.m_85745_(), bufferbuilder$drawstate.m_85746_(), bufferbuilder$drawstate.m_166811_(), bufferbuilder$drawstate.m_166809_(), bufferbuilder$drawstate.m_166815_());
      }

   }

   private static void m_166838_(ByteBuffer p_166839_, VertexFormat.Mode p_166840_, VertexFormat p_166841_, int p_166842_, VertexFormat.IndexType p_166843_, int p_166844_, boolean p_166845_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      p_166839_.clear();
      if (p_166842_ > 0) {
         int i = p_166842_ * p_166841_.m_86020_();
         m_166836_(p_166841_);
         p_166839_.position(0);
         p_166839_.limit(i);
         GlStateManager.m_84256_(34962, p_166839_, 35048);
         int j;
         if (p_166845_) {
            RenderSystem.AutoStorageIndexBuffer rendersystem$autostorageindexbuffer = RenderSystem.m_157193_(p_166840_, p_166844_);
            int k = rendersystem$autostorageindexbuffer.m_157475_();
            if (k != f_166832_) {
               GlStateManager.m_84480_(34963, k);
               f_166832_ = k;
            }

            j = rendersystem$autostorageindexbuffer.m_157483_().f_166923_;
         } else {
            int i1 = p_166841_.m_166915_();
            if (i1 != f_166832_) {
               GlStateManager.m_84480_(34963, i1);
               f_166832_ = i1;
            }

            p_166839_.position(i);
            p_166839_.limit(i + p_166844_ * p_166843_.f_166924_);
            GlStateManager.m_84256_(34963, p_166839_, 35048);
            j = p_166843_.f_166923_;
         }

         ShaderInstance shaderinstance = RenderSystem.m_157196_();

         for(int j1 = 0; j1 < 8; ++j1) {
            int l = RenderSystem.m_157203_(j1);
            shaderinstance.m_173350_("Sampler" + j1, l);
         }

         if (shaderinstance.f_173308_ != null) {
            shaderinstance.f_173308_.m_5679_(RenderSystem.m_157190_());
         }

         if (shaderinstance.f_173309_ != null) {
            shaderinstance.f_173309_.m_5679_(RenderSystem.m_157192_());
         }

         if (shaderinstance.f_173312_ != null) {
            shaderinstance.f_173312_.m_5941_(RenderSystem.m_157197_());
         }

         if (shaderinstance.f_173315_ != null) {
            shaderinstance.f_173315_.m_5985_(RenderSystem.m_157200_());
         }

         if (shaderinstance.f_173316_ != null) {
            shaderinstance.f_173316_.m_5985_(RenderSystem.m_157199_());
         }

         if (shaderinstance.f_173317_ != null) {
            shaderinstance.f_173317_.m_5941_(RenderSystem.m_157198_());
         }

         if (shaderinstance.f_173310_ != null) {
            shaderinstance.f_173310_.m_5679_(RenderSystem.m_157207_());
         }

         if (shaderinstance.f_173319_ != null) {
            shaderinstance.f_173319_.m_5985_(RenderSystem.m_157201_());
         }

         if (shaderinstance.f_173311_ != null) {
            Window window = Minecraft.m_91087_().m_91268_();
            shaderinstance.f_173311_.m_7971_((float)window.m_85441_(), (float)window.m_85442_());
         }

         if (shaderinstance.f_173318_ != null && (p_166840_ == VertexFormat.Mode.LINES || p_166840_ == VertexFormat.Mode.LINE_STRIP)) {
            shaderinstance.f_173318_.m_5985_(RenderSystem.m_157202_());
         }

         RenderSystem.m_157461_(shaderinstance);
         shaderinstance.m_173363_();
         GlStateManager.m_157053_(p_166840_.f_166946_, p_166844_, j, 0L);
         shaderinstance.m_173362_();
         p_166839_.position(0);
      }
   }

   public static void m_166847_(BufferBuilder p_166848_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      Pair<BufferBuilder.DrawState, ByteBuffer> pair = p_166848_.m_85728_();
      BufferBuilder.DrawState bufferbuilder$drawstate = pair.getFirst();
      ByteBuffer bytebuffer = pair.getSecond();
      VertexFormat vertexformat = bufferbuilder$drawstate.m_85745_();
      int i = bufferbuilder$drawstate.m_85746_();
      bytebuffer.clear();
      if (i > 0) {
         int j = i * vertexformat.m_86020_();
         m_166836_(vertexformat);
         bytebuffer.position(0);
         bytebuffer.limit(j);
         GlStateManager.m_84256_(34962, bytebuffer, 35048);
         RenderSystem.AutoStorageIndexBuffer rendersystem$autostorageindexbuffer = RenderSystem.m_157193_(bufferbuilder$drawstate.m_166810_(), bufferbuilder$drawstate.m_166809_());
         int k = rendersystem$autostorageindexbuffer.m_157475_();
         if (k != f_166832_) {
            GlStateManager.m_84480_(34963, k);
            f_166832_ = k;
         }

         int l = rendersystem$autostorageindexbuffer.m_157483_().f_166923_;
         GlStateManager.m_157053_(bufferbuilder$drawstate.m_166810_().f_166946_, bufferbuilder$drawstate.m_166809_(), l, 0L);
         bytebuffer.position(0);
      }
   }

   private static void m_166836_(VertexFormat p_166837_) {
      int i = p_166837_.m_166913_();
      int j = p_166837_.m_166914_();
      boolean flag = p_166837_ != f_166833_;
      if (flag) {
         m_166835_();
      }

      if (i != f_166830_) {
         GlStateManager.m_157068_(i);
         f_166830_ = i;
      }

      if (j != f_166831_) {
         GlStateManager.m_84480_(34962, j);
         f_166831_ = j;
      }

      if (flag) {
         p_166837_.m_166912_();
         f_166833_ = p_166837_;
      }

   }
}