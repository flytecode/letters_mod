package net.minecraft.server;

import java.io.OutputStream;
import java.io.PrintStream;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggedPrintStream extends PrintStream {
   protected static final Logger f_135947_ = LogManager.getLogger();
   protected final String f_135948_;

   public LoggedPrintStream(String p_135951_, OutputStream p_135952_) {
      super(p_135952_);
      this.f_135948_ = p_135951_;
   }

   public void println(@Nullable String p_135957_) {
      this.m_6812_(p_135957_);
   }

   public void println(Object p_135955_) {
      this.m_6812_(String.valueOf(p_135955_));
   }

   protected void m_6812_(@Nullable String p_135953_) {
      f_135947_.info("[{}]: {}", this.f_135948_, p_135953_);
   }
}