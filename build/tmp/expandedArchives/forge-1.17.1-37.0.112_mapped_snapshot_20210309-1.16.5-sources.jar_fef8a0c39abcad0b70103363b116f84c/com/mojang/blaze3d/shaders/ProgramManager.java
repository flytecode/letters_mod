package com.mojang.blaze3d.shaders;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ProgramManager {
   private static final Logger f_85575_ = LogManager.getLogger();

   public static void m_85578_(int p_85579_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      GlStateManager.m_84478_(p_85579_);
   }

   public static void m_166621_(Shader p_166622_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      p_166622_.m_142736_().m_85543_();
      p_166622_.m_142733_().m_85543_();
      GlStateManager.m_84484_(p_166622_.m_142658_());
   }

   public static int m_85577_() throws IOException {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      int i = GlStateManager.m_84531_();
      if (i <= 0) {
         throw new IOException("Could not create shader program (returned program ID " + i + ")");
      } else {
         return i;
      }
   }

   public static void m_166623_(Shader p_166624_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      p_166624_.m_142662_();
      GlStateManager.m_84490_(p_166624_.m_142658_());
      int i = GlStateManager.m_84381_(p_166624_.m_142658_(), 35714);
      if (i == 0) {
         f_85575_.warn("Error encountered when linking program containing VS {} and FS {}. Log output:", p_166624_.m_142733_().m_85551_(), p_166624_.m_142736_().m_85551_());
         f_85575_.warn(GlStateManager.m_84498_(p_166624_.m_142658_(), 32768));
      }

   }
}