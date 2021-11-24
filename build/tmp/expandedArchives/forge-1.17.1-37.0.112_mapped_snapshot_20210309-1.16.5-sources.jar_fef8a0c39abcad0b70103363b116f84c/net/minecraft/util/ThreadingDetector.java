package net.minecraft.util;

import com.mojang.datafixers.util.Pair;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;

public class ThreadingDetector {
   public static void m_145012_(Semaphore p_145013_, @Nullable DebugBuffer<Pair<Thread, StackTraceElement[]>> p_145014_, String p_145015_) {
      boolean flag = p_145013_.tryAcquire();
      if (!flag) {
         throw m_145007_(p_145015_, p_145014_);
      }
   }

   public static ReportedException m_145007_(String p_145008_, @Nullable DebugBuffer<Pair<Thread, StackTraceElement[]>> p_145009_) {
      String s = Thread.getAllStackTraces().keySet().stream().filter(Objects::nonNull).map((p_145011_) -> {
         return p_145011_.getName() + ": \n\tat " + (String)Arrays.stream(p_145011_.getStackTrace()).map(Object::toString).collect(Collectors.joining("\n\tat "));
      }).collect(Collectors.joining("\n"));
      CrashReport crashreport = new CrashReport("Accessing " + p_145008_ + " from multiple threads", new IllegalStateException());
      CrashReportCategory crashreportcategory = crashreport.m_127514_("Thread dumps");
      crashreportcategory.m_128159_("Thread dumps", s);
      if (p_145009_ != null) {
         StringBuilder stringbuilder = new StringBuilder();

         for(Pair<Thread, StackTraceElement[]> pair : p_145009_.m_144624_()) {
            stringbuilder.append("Thread ").append(pair.getFirst().getName()).append(": \n\tat ").append(Arrays.stream(pair.getSecond()).map(Object::toString).collect(Collectors.joining("\n\tat "))).append("\n");
         }

         crashreportcategory.m_128159_("Last threads", stringbuilder.toString());
      }

      return new ReportedException(crashreport);
   }
}