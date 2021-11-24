package com.mojang.realmsclient.gui.task;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface RestartDelayCalculator {
   void m_142685_();

   long m_142678_();
}