package net.minecraft.util.profiling;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.LongSupplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SingleTickProfiler {
   private static final Logger f_18621_ = LogManager.getLogger();
   private final LongSupplier f_18622_;
   private final long f_18623_;
   private int f_18624_;
   private final File f_18625_;
   private ProfileCollector f_18626_ = InactiveProfiler.f_18554_;

   public SingleTickProfiler(LongSupplier p_145963_, String p_145964_, long p_145965_) {
      this.f_18622_ = p_145963_;
      this.f_18625_ = new File("debug", p_145964_);
      this.f_18623_ = p_145965_;
   }

   public ProfilerFiller m_18628_() {
      this.f_18626_ = new ActiveProfiler(this.f_18622_, () -> {
         return this.f_18624_;
      }, false);
      ++this.f_18624_;
      return this.f_18626_;
   }

   public void m_18634_() {
      if (this.f_18626_ != InactiveProfiler.f_18554_) {
         ProfileResults profileresults = this.f_18626_.m_5948_();
         this.f_18626_ = InactiveProfiler.f_18554_;
         if (profileresults.m_18577_() >= this.f_18623_) {
            File file1 = new File(this.f_18625_, "tick-results-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + ".txt");
            profileresults.m_142444_(file1.toPath());
            f_18621_.info("Recorded long tick -- wrote info to: {}", (Object)file1.getAbsolutePath());
         }

      }
   }

   @Nullable
   public static SingleTickProfiler m_18632_(String p_18633_) {
      return null;
   }

   public static ProfilerFiller m_18629_(ProfilerFiller p_18630_, @Nullable SingleTickProfiler p_18631_) {
      return p_18631_ != null ? ProfilerFiller.m_18578_(p_18631_.m_18628_(), p_18630_) : p_18630_;
   }
}