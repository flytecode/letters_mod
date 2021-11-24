package com.mojang.blaze3d.font;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface GlyphInfo {
   float m_7403_();

   default float m_83827_(boolean p_83828_) {
      return this.m_7403_() + (p_83828_ ? this.m_5619_() : 0.0F);
   }

   default float m_5620_() {
      return 0.0F;
   }

   default float m_142672_() {
      return 0.0F;
   }

   default float m_5619_() {
      return 1.0F;
   }

   default float m_5645_() {
      return 1.0F;
   }
}