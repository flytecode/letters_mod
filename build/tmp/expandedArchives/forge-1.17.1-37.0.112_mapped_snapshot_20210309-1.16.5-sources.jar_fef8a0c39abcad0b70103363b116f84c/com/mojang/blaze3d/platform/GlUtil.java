package com.mojang.blaze3d.platform;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
public class GlUtil {
   public static void m_166249_(SnooperAccess p_166250_) {
   }

   public static ByteBuffer m_166247_(int p_166248_) {
      return MemoryUtil.memAlloc(p_166248_);
   }

   public static void m_166251_(Buffer p_166252_) {
      MemoryUtil.memFree(p_166252_);
   }

   public static String m_84818_() {
      return GlStateManager.m_84089_(7936);
   }

   public static String m_84819_() {
      return GLX.m_69339_();
   }

   public static String m_84820_() {
      return GlStateManager.m_84089_(7937);
   }

   public static String m_84821_() {
      return GlStateManager.m_84089_(7938);
   }
}