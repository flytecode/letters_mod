package net.minecraft.gametest.framework;

import com.google.common.escape.Escaper;
import com.google.common.escape.Escapers;
import net.minecraft.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TeamcityTestReporter implements TestReporter {
   private static final Logger f_177778_ = LogManager.getLogger();
   private static final Escaper f_177779_ = Escapers.builder().addEscape('\'', "|'").addEscape('\n', "|n").addEscape('\r', "|r").addEscape('|', "||").addEscape('[', "|[").addEscape(']', "|]").build();

   public void m_8014_(GameTestInfo p_177783_) {
      String s = f_177779_.escape(p_177783_.m_127633_());
      String s1 = f_177779_.escape(p_177783_.m_127642_().getMessage());
      String s2 = f_177779_.escape(Util.m_137575_(p_177783_.m_127642_()));
      f_177778_.info("##teamcity[testStarted name='{}']", (Object)s);
      if (p_177783_.m_127643_()) {
         f_177778_.info("##teamcity[testFailed name='{}' message='{}' details='{}']", s, s1, s2);
      } else {
         f_177778_.info("##teamcity[testIgnored name='{}' message='{}' details='{}']", s, s1, s2);
      }

      f_177778_.info("##teamcity[testFinished name='{}' duration='{}']", s, p_177783_.m_177485_());
   }

   public void m_142335_(GameTestInfo p_177785_) {
      String s = f_177779_.escape(p_177785_.m_127633_());
      f_177778_.info("##teamcity[testStarted name='{}']", (Object)s);
      f_177778_.info("##teamcity[testFinished name='{}' duration='{}']", s, p_177785_.m_177485_());
   }
}