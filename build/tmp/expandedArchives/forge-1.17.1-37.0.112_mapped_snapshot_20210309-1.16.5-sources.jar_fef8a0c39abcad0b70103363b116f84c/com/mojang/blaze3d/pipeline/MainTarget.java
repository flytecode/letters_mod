package com.mojang.blaze3d.pipeline;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.IntBuffer;
import java.util.List;
import java.util.Objects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MainTarget extends RenderTarget {
   public static final int f_166132_ = 854;
   public static final int f_166133_ = 480;
   static final MainTarget.Dimension f_166134_ = new MainTarget.Dimension(854, 480);

   public MainTarget(int p_166137_, int p_166138_) {
      super(true);
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      if (!RenderSystem.m_69586_()) {
         RenderSystem.m_69879_(() -> {
            this.m_166141_(p_166137_, p_166138_);
         });
      } else {
         this.m_166141_(p_166137_, p_166138_);
      }

   }

   private void m_166141_(int p_166142_, int p_166143_) {
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      MainTarget.Dimension maintarget$dimension = this.m_166146_(p_166142_, p_166143_);
      this.f_83920_ = GlStateManager.m_84543_();
      GlStateManager.m_84486_(36160, this.f_83920_);
      GlStateManager.m_84544_(this.f_83923_);
      GlStateManager.m_84331_(3553, 10241, 9728);
      GlStateManager.m_84331_(3553, 10240, 9728);
      GlStateManager.m_84331_(3553, 10242, 33071);
      GlStateManager.m_84331_(3553, 10243, 33071);
      GlStateManager.m_84173_(36160, 36064, 3553, this.f_83923_, 0);
      GlStateManager.m_84544_(this.f_83924_);
      GlStateManager.m_84331_(3553, 34892, 0);
      GlStateManager.m_84331_(3553, 10241, 9728);
      GlStateManager.m_84331_(3553, 10240, 9728);
      GlStateManager.m_84331_(3553, 10242, 33071);
      GlStateManager.m_84331_(3553, 10243, 33071);
      GlStateManager.m_84173_(36160, 36096, 3553, this.f_83924_, 0);
      GlStateManager.m_84544_(0);
      this.f_83917_ = maintarget$dimension.f_166168_;
      this.f_83918_ = maintarget$dimension.f_166169_;
      this.f_83915_ = maintarget$dimension.f_166168_;
      this.f_83916_ = maintarget$dimension.f_166169_;
      this.m_83949_();
      GlStateManager.m_84486_(36160, 0);
   }

   private MainTarget.Dimension m_166146_(int p_166147_, int p_166148_) {
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      this.f_83923_ = TextureUtil.m_85280_();
      this.f_83924_ = TextureUtil.m_85280_();
      MainTarget.AttachmentState maintarget$attachmentstate = MainTarget.AttachmentState.NONE;

      for(MainTarget.Dimension maintarget$dimension : MainTarget.Dimension.m_166173_(p_166147_, p_166148_)) {
         maintarget$attachmentstate = MainTarget.AttachmentState.NONE;
         if (this.m_166139_(maintarget$dimension)) {
            maintarget$attachmentstate = maintarget$attachmentstate.m_166163_(MainTarget.AttachmentState.COLOR);
         }

         if (this.m_166144_(maintarget$dimension)) {
            maintarget$attachmentstate = maintarget$attachmentstate.m_166163_(MainTarget.AttachmentState.DEPTH);
         }

         if (maintarget$attachmentstate == MainTarget.AttachmentState.COLOR_DEPTH) {
            return maintarget$dimension;
         }
      }

      throw new RuntimeException("Unrecoverable GL_OUT_OF_MEMORY (allocated attachments = " + maintarget$attachmentstate.name() + ")");
   }

   private boolean m_166139_(MainTarget.Dimension p_166140_) {
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      GlStateManager.m_84118_();
      GlStateManager.m_84544_(this.f_83923_);
      GlStateManager.m_84209_(3553, 0, 32856, p_166140_.f_166168_, p_166140_.f_166169_, 0, 6408, 5121, (IntBuffer)null);
      return GlStateManager.m_84118_() != 1285;
   }

   private boolean m_166144_(MainTarget.Dimension p_166145_) {
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      GlStateManager.m_84118_();
      GlStateManager.m_84544_(this.f_83924_);
      GlStateManager.m_84209_(3553, 0, 6402, p_166145_.f_166168_, p_166145_.f_166169_, 0, 6402, 5126, (IntBuffer)null);
      return GlStateManager.m_84118_() != 1285;
   }

   @OnlyIn(Dist.CLIENT)
   static enum AttachmentState {
      NONE,
      COLOR,
      DEPTH,
      COLOR_DEPTH;

      private static final MainTarget.AttachmentState[] f_166156_ = values();

      MainTarget.AttachmentState m_166163_(MainTarget.AttachmentState p_166164_) {
         return f_166156_[this.ordinal() | p_166164_.ordinal()];
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class Dimension {
      public final int f_166168_;
      public final int f_166169_;

      Dimension(int p_166171_, int p_166172_) {
         this.f_166168_ = p_166171_;
         this.f_166169_ = p_166172_;
      }

      static List<MainTarget.Dimension> m_166173_(int p_166174_, int p_166175_) {
         RenderSystem.m_69393_(RenderSystem::m_69587_);
         int i = RenderSystem.m_69839_();
         return p_166174_ > 0 && p_166174_ <= i && p_166175_ > 0 && p_166175_ <= i ? ImmutableList.of(new MainTarget.Dimension(p_166174_, p_166175_), MainTarget.f_166134_) : ImmutableList.of(MainTarget.f_166134_);
      }

      public boolean equals(Object p_166177_) {
         if (this == p_166177_) {
            return true;
         } else if (p_166177_ != null && this.getClass() == p_166177_.getClass()) {
            MainTarget.Dimension maintarget$dimension = (MainTarget.Dimension)p_166177_;
            return this.f_166168_ == maintarget$dimension.f_166168_ && this.f_166169_ == maintarget$dimension.f_166169_;
         } else {
            return false;
         }
      }

      public int hashCode() {
         return Objects.hash(this.f_166168_, this.f_166169_);
      }

      public String toString() {
         return this.f_166168_ + "x" + this.f_166169_;
      }
   }
}