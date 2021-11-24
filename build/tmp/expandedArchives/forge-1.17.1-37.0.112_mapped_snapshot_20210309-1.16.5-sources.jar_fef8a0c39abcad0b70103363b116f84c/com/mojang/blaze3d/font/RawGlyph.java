package com.mojang.blaze3d.font;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface RawGlyph extends GlyphInfo {
   int m_5631_();

   int m_5629_();

   void m_6238_(int p_83831_, int p_83832_);

   boolean m_5633_();

   float m_5621_();

   default float m_83833_() {
      return this.m_5620_();
   }

   default float m_83834_() {
      return this.m_83833_() + (float)this.m_5631_() / this.m_5621_();
   }

   default float m_83835_() {
      return this.m_142672_();
   }

   default float m_83836_() {
      return this.m_83835_() + (float)this.m_5629_() / this.m_5621_();
   }

   default float m_142672_() {
      return 3.0F;
   }
}