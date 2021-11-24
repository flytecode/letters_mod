package net.minecraft.client.gui.font.glyphs;

import com.mojang.blaze3d.font.RawGlyph;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.Util;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public enum WhiteGlyph implements RawGlyph {
   INSTANCE;

   private static final int f_169105_ = 5;
   private static final int f_169106_ = 8;
   private static final NativeImage f_95309_ = Util.m_137469_(new NativeImage(NativeImage.Format.RGBA, 5, 8, false), (p_95319_) -> {
      for(int i = 0; i < 8; ++i) {
         for(int j = 0; j < 5; ++j) {
            if (j != 0 && j + 1 != 5 && i != 0 && i + 1 != 8) {
               boolean flag = false;
            } else {
               boolean flag1 = true;
            }

            p_95319_.m_84988_(j, i, -1);
         }
      }

      p_95319_.m_85123_();
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

   public void m_6238_(int p_95316_, int p_95317_) {
      f_95309_.m_85040_(0, p_95316_, p_95317_, false);
   }

   public boolean m_5633_() {
      return true;
   }
}