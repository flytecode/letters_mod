package net.minecraft.server.rcon.thread;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.DefaultUncaughtExceptionHandlerWithName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class GenericThread implements Runnable {
   private static final Logger f_11518_ = LogManager.getLogger();
   private static final AtomicInteger f_11519_ = new AtomicInteger(0);
   private static final int f_144023_ = 5;
   protected volatile boolean f_11515_;
   protected final String f_11516_;
   @Nullable
   protected Thread f_11517_;

   protected GenericThread(String p_11522_) {
      this.f_11516_ = p_11522_;
   }

   public synchronized boolean m_7528_() {
      if (this.f_11515_) {
         return true;
      } else {
         this.f_11515_ = true;
         this.f_11517_ = new Thread(this, this.f_11516_ + " #" + f_11519_.incrementAndGet());
         this.f_11517_.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandlerWithName(f_11518_));
         this.f_11517_.start();
         f_11518_.info("Thread {} started", (Object)this.f_11516_);
         return true;
      }
   }

   public synchronized void m_7530_() {
      this.f_11515_ = false;
      if (null != this.f_11517_) {
         int i = 0;

         while(this.f_11517_.isAlive()) {
            try {
               this.f_11517_.join(1000L);
               ++i;
               if (i >= 5) {
                  f_11518_.warn("Waited {} seconds attempting force stop!", (int)i);
               } else if (this.f_11517_.isAlive()) {
                  f_11518_.warn("Thread {} ({}) failed to exit after {} second(s)", this, this.f_11517_.getState(), i, new Exception("Stack:"));
                  this.f_11517_.interrupt();
               }
            } catch (InterruptedException interruptedexception) {
            }
         }

         f_11518_.info("Thread {} stopped", (Object)this.f_11516_);
         this.f_11517_ = null;
      }
   }

   public boolean m_11523_() {
      return this.f_11515_;
   }
}