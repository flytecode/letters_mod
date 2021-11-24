package com.mojang.realmsclient.gui.screens;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UploadResult {
   public final int f_90133_;
   public final String f_90134_;

   UploadResult(int p_90136_, String p_90137_) {
      this.f_90133_ = p_90136_;
      this.f_90134_ = p_90137_;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Builder {
      private int f_90142_ = -1;
      private String f_90143_;

      public UploadResult.Builder m_90146_(int p_90147_) {
         this.f_90142_ = p_90147_;
         return this;
      }

      public UploadResult.Builder m_90148_(String p_90149_) {
         this.f_90143_ = p_90149_;
         return this;
      }

      public UploadResult m_90145_() {
         return new UploadResult(this.f_90142_, this.f_90143_);
      }
   }
}