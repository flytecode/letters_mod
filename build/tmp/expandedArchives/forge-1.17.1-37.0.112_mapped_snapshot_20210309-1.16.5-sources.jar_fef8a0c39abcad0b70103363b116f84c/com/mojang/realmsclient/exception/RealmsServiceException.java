package com.mojang.realmsclient.exception;

import com.mojang.realmsclient.client.RealmsError;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsServiceException extends Exception {
   public final int f_87773_;
   public final String f_87774_;
   public final int f_87775_;
   public final String f_87776_;

   public RealmsServiceException(int p_87783_, String p_87784_, RealmsError p_87785_) {
      super(p_87784_);
      this.f_87773_ = p_87783_;
      this.f_87774_ = p_87784_;
      this.f_87775_ = p_87785_.m_87305_();
      this.f_87776_ = p_87785_.m_87302_();
   }

   public RealmsServiceException(int p_87778_, String p_87779_, int p_87780_, String p_87781_) {
      super(p_87779_);
      this.f_87773_ = p_87778_;
      this.f_87774_ = p_87779_;
      this.f_87775_ = p_87780_;
      this.f_87776_ = p_87781_;
   }

   public String toString() {
      if (this.f_87775_ == -1) {
         return "Realms (" + this.f_87773_ + ") " + this.f_87774_;
      } else {
         String s = "mco.errorMessage." + this.f_87775_;
         String s1 = I18n.m_118938_(s);
         return (s1.equals(s) ? this.f_87776_ : s1) + " - " + this.f_87775_;
      }
   }
}