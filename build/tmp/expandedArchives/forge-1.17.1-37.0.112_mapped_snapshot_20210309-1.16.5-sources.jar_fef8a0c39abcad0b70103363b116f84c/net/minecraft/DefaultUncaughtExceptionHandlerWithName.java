package net.minecraft;

import java.lang.Thread.UncaughtExceptionHandler;
import org.apache.logging.log4j.Logger;

public class DefaultUncaughtExceptionHandlerWithName implements UncaughtExceptionHandler {
   private final Logger f_131799_;

   public DefaultUncaughtExceptionHandlerWithName(Logger p_131801_) {
      this.f_131799_ = p_131801_;
   }

   public void uncaughtException(Thread p_131803_, Throwable p_131804_) {
      this.f_131799_.error("Caught previously unhandled exception :");
      this.f_131799_.error(p_131803_.getName(), p_131804_);
   }
}