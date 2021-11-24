package com.mojang.blaze3d.vertex;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class DefaultedVertexConsumer implements VertexConsumer {
   protected boolean f_85824_;
   protected int f_85825_ = 255;
   protected int f_85826_ = 255;
   protected int f_85827_ = 255;
   protected int f_85828_ = 255;

   public void m_142461_(int p_85830_, int p_85831_, int p_85832_, int p_85833_) {
      this.f_85825_ = p_85830_;
      this.f_85826_ = p_85831_;
      this.f_85827_ = p_85832_;
      this.f_85828_ = p_85833_;
      this.f_85824_ = true;
   }

   public void m_141991_() {
      this.f_85824_ = false;
   }
}