package net.minecraft.server;

import java.io.OutputStream;

public class DebugLoggedPrintStream extends LoggedPrintStream {
   public DebugLoggedPrintStream(String p_135934_, OutputStream p_135935_) {
      super(p_135934_, p_135935_);
   }

   protected void m_6812_(String p_135937_) {
      StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
      StackTraceElement stacktraceelement = astacktraceelement[Math.min(3, astacktraceelement.length)];
      f_135947_.info("[{}]@.({}:{}): {}", this.f_135948_, stacktraceelement.getFileName(), stacktraceelement.getLineNumber(), p_135937_);
   }
}