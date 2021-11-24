package com.mojang.blaze3d.shaders;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface Shader {
   int m_142658_();

   void m_142660_();

   Program m_142733_();

   Program m_142736_();

   void m_142662_();
}