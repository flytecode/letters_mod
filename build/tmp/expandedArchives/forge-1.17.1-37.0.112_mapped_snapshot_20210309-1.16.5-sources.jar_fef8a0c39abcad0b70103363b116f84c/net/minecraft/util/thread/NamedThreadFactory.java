package net.minecraft.util.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NamedThreadFactory implements ThreadFactory {
   private static final Logger f_146340_ = LogManager.getLogger();
   private final ThreadGroup f_146341_;
   private final AtomicInteger f_146342_ = new AtomicInteger(1);
   private final String f_146343_;

   public NamedThreadFactory(String p_146346_) {
      SecurityManager securitymanager = System.getSecurityManager();
      this.f_146341_ = securitymanager != null ? securitymanager.getThreadGroup() : Thread.currentThread().getThreadGroup();
      this.f_146343_ = p_146346_ + "-";
   }

   public Thread newThread(Runnable p_146352_) {
      Thread thread = new Thread(this.f_146341_, p_146352_, this.f_146343_ + this.f_146342_.getAndIncrement(), 0L);
      thread.setUncaughtExceptionHandler((p_146349_, p_146350_) -> {
         f_146340_.error("Caught exception in thread {} from {}", p_146349_, p_146352_);
         f_146340_.error("", p_146350_);
      });
      if (thread.getPriority() != 5) {
         thread.setPriority(5);
      }

      return thread;
   }
}