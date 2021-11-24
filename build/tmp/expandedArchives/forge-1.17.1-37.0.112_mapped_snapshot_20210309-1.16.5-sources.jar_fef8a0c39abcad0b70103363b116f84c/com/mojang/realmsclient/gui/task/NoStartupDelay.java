package com.mojang.realmsclient.gui.task;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NoStartupDelay implements RestartDelayCalculator {
   public void m_142685_() {
   }

   public long m_142678_() {
      return 0L;
   }
}