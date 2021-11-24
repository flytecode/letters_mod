package net.minecraft;

import java.lang.Thread.UncaughtExceptionHandler;
import org.apache.logging.log4j.Logger;

public class DefaultUncaughtExceptionHandler implements UncaughtExceptionHandler {
   private final Logger f_131075_;

   public DefaultUncaughtExceptionHandler(Logger p_131077_) {
      this.f_131075_ = p_131077_;
   }

   public void uncaughtException(Thread p_131079_, Throwable p_131080_) {
      this.f_131075_.error("Caught previously unhandled exception :", p_131080_);
   }
}