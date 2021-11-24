package net.minecraft.client.gui.font.glyphs;

import com.mojang.blaze3d.font.RawGlyph;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum MissingGlyph implements RawGlyph {
   INSTANCE;

   private static final int f_169102_ = 5;
   private static final int f_169103_ = 8;
   private static final NativeImage f_95289_ = Util.m_137469_(new NativeImage(NativeImage.Format.RGBA, 5, 8, false), (p_95299_) -> {
      for(int i = 0; i < 8; ++i) {
         for(int j = 0; j < 5; ++j) {
            boolean flag = j == 0 || j + 1 == 5 || i == 0 || i + 1 == 8;
            p_95299_.m_84988_(j, i, flag ? -1 : 0);
         }
      }

      p_95299_.m_85123_();
   });

   public int m_5631_() {
      return 5;
   }

   public int m_5629_() {
      return 8;
   }

   public float m_7403_() {
      return 6.0F;
   }

   public float m_5621_() {
      return 1.0F;
   }

   public void m_6238_(int p_95296_, int p_95297_) {
      f_95289_.m_85040_(0, p_95296_, p_95297_, false);
   }

   public boolean m_5633_() {
      return true;
   }
}