package net.minecraft.server.dedicated;

import com.google.common.collect.Streams;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.Util;
import net.minecraft.server.Bootstrap;
import net.minecraft.world.level.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerWatchdog implements Runnable {
   private static final Logger f_139781_ = LogManager.getLogger();
   private static final long f_142880_ = 10000L;
   private static final int f_142881_ = 1;
   private final DedicatedServer f_139782_;
   private final long f_139783_;

   public ServerWatchdog(DedicatedServer p_139786_) {
      this.f_139782_ = p_139786_;
      this.f_139783_ = p_139786_.m_139669_();
   }

   public void run() {
      while(this.f_139782_.m_130010_()) {
         long i = this.f_139782_.m_129932_();
         long j = Util.m_137550_();
         long k = j - i;
         if (k > this.f_139783_) {
            f_139781_.fatal("A single server tick took {} seconds (should be max {})", String.format(Locale.ROOT, "%.2f", (float)k / 1000.0F), String.format(Locale.ROOT, "%.2f", 0.05F));
            f_139781_.fatal("Considering it to be crashed, server will forcibly shutdown.");
            ThreadMXBean threadmxbean = ManagementFactory.getThreadMXBean();
            ThreadInfo[] athreadinfo = threadmxbean.dumpAllThreads(true, true);
            StringBuilder stringbuilder = new StringBuilder();
            Error error = new Error(String.format("ServerHangWatchdog detected that a single server tick took %.2f seconds (should be max 0.05)", k / 1000F)); // Forge: don't just make a crash report with a seemingly-inexplicable Error

            for(ThreadInfo threadinfo : athreadinfo) {
               if (threadinfo.getThreadId() == this.f_139782_.m_6304_().getId()) {
                  error.setStackTrace(threadinfo.getStackTrace());
               }

               stringbuilder.append((Object)threadinfo);
               stringbuilder.append("\n");
            }

            CrashReport crashreport = new CrashReport("Watching Server", error);
            this.f_139782_.m_177935_(crashreport.m_178626_());
            CrashReportCategory crashreportcategory = crashreport.m_127514_("Thread Dump");
            crashreportcategory.m_128159_("Threads", stringbuilder);
            CrashReportCategory crashreportcategory1 = crashreport.m_127514_("Performance stats");
            crashreportcategory1.m_128165_("Random tick rate", () -> {
               return this.f_139782_.m_129910_().m_5470_().m_46170_(GameRules.f_46143_).toString();
            });
            crashreportcategory1.m_128165_("Level stats", () -> {
               return Streams.stream(this.f_139782_.m_129785_()).map((p_142883_) -> {
                  return p_142883_.m_46472_() + ": " + p_142883_.m_8590_();
               }).collect(Collectors.joining(",\n"));
            });
            Bootstrap.m_135875_("Crash report:\n" + crashreport.m_127526_());
            File file1 = new File(new File(this.f_139782_.m_6237_(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
            if (crashreport.m_127512_(file1)) {
               f_139781_.error("This crash report has been saved to: {}", (Object)file1.getAbsolutePath());
            } else {
               f_139781_.error("We were unable to save this crash report to disk.");
            }

            this.m_139787_();
         }

         try {
            Thread.sleep(i + this.f_139783_ - j);
         } catch (InterruptedException interruptedexception) {
         }
      }

   }

   private void m_139787_() {
      try {
         Timer timer = new Timer();
         timer.schedule(new TimerTask() {
            public void run() {
               Runtime.getRuntime().halt(1);
            }
         }, 10000L);
         System.exit(1);
      } catch (Throwable throwable) {
         Runtime.getRuntime().halt(1);
      }

   }
}
