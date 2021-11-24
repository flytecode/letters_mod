package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OverlayTexture implements AutoCloseable {
   private static final int f_174694_ = 16;
   public static final int f_174691_ = 0;
   public static final int f_174692_ = 3;
   public static final int f_174693_ = 10;
   public static final int f_118083_ = m_118093_(0, 10);
   private final DynamicTexture f_118084_ = new DynamicTexture(16, 16, false);

   public OverlayTexture() {
      NativeImage nativeimage = this.f_118084_.m_117991_();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            if (i < 8) {
               nativeimage.m_84988_(j, i, -1308622593);
            } else {
               int k = (int)((1.0F - (float)j / 15.0F * 0.75F) * 255.0F);
               nativeimage.m_84988_(j, i, k << 24 | 16777215);
            }
         }
      }

      RenderSystem.m_69388_(33985);
      this.f_118084_.m_117966_();
      nativeimage.m_85013_(0, 0, 0, 0, 0, nativeimage.m_84982_(), nativeimage.m_85084_(), false, true, false, false);
      RenderSystem.m_69388_(33984);
   }

   public void close() {
      this.f_118084_.close();
   }

   public void m_118087_() {
      RenderSystem.m_69920_(this.f_118084_::m_117963_, 16);
   }

   public static int m_118088_(float p_118089_) {
      return (int)(p_118089_ * 15.0F);
   }

   public static int m_118096_(boolean p_118097_) {
      return p_118097_ ? 3 : 10;
   }

   public static int m_118093_(int p_118094_, int p_118095_) {
      return p_118094_ | p_118095_ << 16;
   }

   public static int m_118090_(float p_118091_, boolean p_118092_) {
      return m_118093_(m_118088_(p_118091_), m_118096_(p_118092_));
   }

   public void m_118098_() {
      RenderSystem.m_69936_();
   }
}