package com.mojang.blaze3d.vertex;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Matrix4f;
import java.nio.ByteBuffer;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class VertexBuffer implements AutoCloseable {
   private int f_166859_;
   private int f_166860_;
   private VertexFormat.IndexType f_166861_;
   private int f_166862_;
   private int f_166863_;
   private VertexFormat.Mode f_166864_;
   private boolean f_166865_;
   private VertexFormat f_85917_;

   public VertexBuffer() {
      RenderSystem.m_69531_((p_85928_) -> {
         this.f_166859_ = p_85928_;
      });
      RenderSystem.m_157215_((p_166881_) -> {
         this.f_166862_ = p_166881_;
      });
      RenderSystem.m_69531_((p_166872_) -> {
         this.f_166860_ = p_166872_;
      });
   }

   public void m_85921_() {
      RenderSystem.m_157208_(34962, () -> {
         return this.f_166859_;
      });
      if (this.f_166865_) {
         RenderSystem.m_157208_(34963, () -> {
            RenderSystem.AutoStorageIndexBuffer rendersystem$autostorageindexbuffer = RenderSystem.m_157193_(this.f_166864_, this.f_166863_);
            this.f_166861_ = rendersystem$autostorageindexbuffer.m_157483_();
            return rendersystem$autostorageindexbuffer.m_157475_();
         });
      } else {
         RenderSystem.m_157208_(34963, () -> {
            return this.f_166860_;
         });
      }

   }

   public void m_85925_(BufferBuilder p_85926_) {
      if (!RenderSystem.m_69586_()) {
         RenderSystem.m_69879_(() -> {
            this.m_85935_(p_85926_);
         });
      } else {
         this.m_85935_(p_85926_);
      }

   }

   public CompletableFuture<Void> m_85932_(BufferBuilder p_85933_) {
      if (!RenderSystem.m_69586_()) {
         return CompletableFuture.runAsync(() -> {
            this.m_85935_(p_85933_);
         }, (p_166874_) -> {
            RenderSystem.m_69879_(p_166874_::run);
         });
      } else {
         this.m_85935_(p_85933_);
         return CompletableFuture.completedFuture((Void)null);
      }
   }

   private void m_85935_(BufferBuilder p_85936_) {
      Pair<BufferBuilder.DrawState, ByteBuffer> pair = p_85936_.m_85728_();
      if (this.f_166859_ != 0) {
         BufferUploader.m_166835_();
         BufferBuilder.DrawState bufferbuilder$drawstate = pair.getFirst();
         ByteBuffer bytebuffer = pair.getSecond();
         int i = bufferbuilder$drawstate.m_166812_();
         this.f_166863_ = bufferbuilder$drawstate.m_166809_();
         this.f_166861_ = bufferbuilder$drawstate.m_166811_();
         this.f_85917_ = bufferbuilder$drawstate.m_85745_();
         this.f_166864_ = bufferbuilder$drawstate.m_166810_();
         this.f_166865_ = bufferbuilder$drawstate.m_166815_();
         this.m_166893_();
         this.m_85921_();
         if (!bufferbuilder$drawstate.m_166814_()) {
            bytebuffer.limit(i);
            RenderSystem.m_69525_(34962, bytebuffer, 35044);
            bytebuffer.position(i);
         }

         if (!this.f_166865_) {
            bytebuffer.limit(bufferbuilder$drawstate.m_166813_());
            RenderSystem.m_69525_(34963, bytebuffer, 35044);
            bytebuffer.position(0);
         } else {
            bytebuffer.limit(bufferbuilder$drawstate.m_166813_());
            bytebuffer.position(0);
         }

         m_85931_();
         m_166875_();
      }
   }

   private void m_166893_() {
      RenderSystem.m_157211_(() -> {
         return this.f_166862_;
      });
   }

   public static void m_166875_() {
      RenderSystem.m_157211_(() -> {
         return 0;
      });
   }

   public void m_166882_() {
      if (this.f_166863_ != 0) {
         RenderSystem.m_157186_(this.f_166864_.f_166946_, this.f_166863_, this.f_166861_.f_166923_);
      }
   }

   public void m_166867_(Matrix4f p_166868_, Matrix4f p_166869_, ShaderInstance p_166870_) {
      if (!RenderSystem.m_69586_()) {
         RenderSystem.m_69879_(() -> {
            this.m_166876_(p_166868_.m_27658_(), p_166869_.m_27658_(), p_166870_);
         });
      } else {
         this.m_166876_(p_166868_, p_166869_, p_166870_);
      }

   }

   public void m_166876_(Matrix4f p_166877_, Matrix4f p_166878_, ShaderInstance p_166879_) {
      if (this.f_166863_ != 0) {
         RenderSystem.m_69393_(RenderSystem::m_69586_);
         BufferUploader.m_166835_();

         for(int i = 0; i < 12; ++i) {
            int j = RenderSystem.m_157203_(i);
            p_166879_.m_173350_("Sampler" + i, j);
         }

         if (p_166879_.f_173308_ != null) {
            p_166879_.f_173308_.m_5679_(p_166877_);
         }

         if (p_166879_.f_173309_ != null) {
            p_166879_.f_173309_.m_5679_(p_166878_);
         }

         if (p_166879_.f_173312_ != null) {
            p_166879_.f_173312_.m_5941_(RenderSystem.m_157197_());
         }

         if (p_166879_.f_173315_ != null) {
            p_166879_.f_173315_.m_5985_(RenderSystem.m_157200_());
         }

         if (p_166879_.f_173316_ != null) {
            p_166879_.f_173316_.m_5985_(RenderSystem.m_157199_());
         }

         if (p_166879_.f_173317_ != null) {
            p_166879_.f_173317_.m_5941_(RenderSystem.m_157198_());
         }

         if (p_166879_.f_173310_ != null) {
            p_166879_.f_173310_.m_5679_(RenderSystem.m_157207_());
         }

         if (p_166879_.f_173319_ != null) {
            p_166879_.f_173319_.m_5985_(RenderSystem.m_157201_());
         }

         if (p_166879_.f_173311_ != null) {
            Window window = Minecraft.m_91087_().m_91268_();
            p_166879_.f_173311_.m_7971_((float)window.m_85441_(), (float)window.m_85442_());
         }

         if (p_166879_.f_173318_ != null && (this.f_166864_ == VertexFormat.Mode.LINES || this.f_166864_ == VertexFormat.Mode.LINE_STRIP)) {
            p_166879_.f_173318_.m_5985_(RenderSystem.m_157202_());
         }

         RenderSystem.m_157461_(p_166879_);
         this.m_166893_();
         this.m_85921_();
         this.m_166892_().m_166912_();
         p_166879_.m_173363_();
         RenderSystem.m_157186_(this.f_166864_.f_166946_, this.f_166863_, this.f_166861_.f_166923_);
         p_166879_.m_173362_();
         this.m_166892_().m_86024_();
         m_85931_();
         m_166875_();
      }
   }

   public void m_166887_() {
      if (this.f_166863_ != 0) {
         RenderSystem.m_69393_(RenderSystem::m_69586_);
         this.m_166893_();
         this.m_85921_();
         this.f_85917_.m_166912_();
         RenderSystem.m_157186_(this.f_166864_.f_166946_, this.f_166863_, this.f_166861_.f_166923_);
      }
   }

   public static void m_85931_() {
      RenderSystem.m_157208_(34962, () -> {
         return 0;
      });
      RenderSystem.m_157208_(34963, () -> {
         return 0;
      });
   }

   public void close() {
      if (this.f_166860_ >= 0) {
         RenderSystem.m_69529_(this.f_166860_);
         this.f_166860_ = -1;
      }

      if (this.f_166859_ > 0) {
         RenderSystem.m_69529_(this.f_166859_);
         this.f_166859_ = 0;
      }

      if (this.f_166862_ > 0) {
         RenderSystem.m_157213_(this.f_166862_);
         this.f_166862_ = 0;
      }

   }

   public VertexFormat m_166892_() {
      return this.f_85917_;
   }
}