package net.minecraft.gametest.framework;

import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTestReporter implements TestReporter {
   private static final Logger f_127793_ = LogManager.getLogger();

   public void m_8014_(GameTestInfo p_127797_) {
      if (p_127797_.m_127643_()) {
         f_127793_.error("{} failed! {}", p_127797_.m_127633_(), Util.m_137575_(p_127797_.m_127642_()));
      } else {
         f_127793_.warn("(optional) {} failed. {}", p_127797_.m_127633_(), Util.m_137575_(p_127797_.m_127642_()));
      }

   }

   public void m_142335_(GameTestInfo p_177676_) {
   }
}