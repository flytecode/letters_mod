package net.minecraft.util.thread;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.Util;
import net.minecraft.util.profiling.metrics.MetricCategory;
import net.minecraft.util.profiling.metrics.MetricSampler;
import net.minecraft.util.profiling.metrics.MetricsRegistry;
import net.minecraft.util.profiling.metrics.ProfilerMeasured;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProcessorMailbox<T> implements ProfilerMeasured, ProcessorHandle<T>, AutoCloseable, Runnable {
   private static final Logger f_18735_ = LogManager.getLogger();
   private static final int f_146353_ = 1;
   private static final int f_146354_ = 2;
   private final AtomicInteger f_18736_ = new AtomicInteger(0);
   private final StrictQueue<? super T, ? extends Runnable> f_18734_;
   private final Executor f_18737_;
   private final String f_18738_;

   public static ProcessorMailbox<Runnable> m_18751_(Executor p_18752_, String p_18753_) {
      return new ProcessorMailbox<>(new StrictQueue.QueueStrictQueue<>(new ConcurrentLinkedQueue<>()), p_18752_, p_18753_);
   }

   public ProcessorMailbox(StrictQueue<? super T, ? extends Runnable> p_18741_, Executor p_18742_, String p_18743_) {
      this.f_18737_ = p_18742_;
      this.f_18734_ = p_18741_;
      this.f_18738_ = p_18743_;
      MetricsRegistry.f_146067_.m_146072_(this);
   }

   private boolean m_18744_() {
      int i;
      do {
         i = this.f_18736_.get();
         if ((i & 3) != 0) {
            return false;
         }
      } while(!this.f_18736_.compareAndSet(i, i | 2));

      return true;
   }

   private void m_18754_() {
      int i;
      do {
         i = this.f_18736_.get();
      } while(!this.f_18736_.compareAndSet(i, i & -3));

   }

   private boolean m_18756_() {
      if ((this.f_18736_.get() & 1) != 0) {
         return false;
      } else {
         return !this.f_18734_.m_7263_();
      }
   }

   public void close() {
      int i;
      do {
         i = this.f_18736_.get();
      } while(!this.f_18736_.compareAndSet(i, i | 1));

   }

   private boolean m_18758_() {
      return (this.f_18736_.get() & 2) != 0;
   }

   private boolean m_18759_() {
      if (!this.m_18758_()) {
         return false;
      } else {
         Runnable runnable = this.f_18734_.m_6610_();
         if (runnable == null) {
            return false;
         } else {
            Util.m_143787_(this.f_18738_, runnable).run();
            return true;
         }
      }
   }

   public void run() {
      try {
         this.m_18747_((p_18746_) -> {
            return p_18746_ == 0;
         });
      } finally {
         this.m_18754_();
         this.m_18760_();
      }

   }

   public void m_182329_() {
      try {
         this.m_18747_((p_182331_) -> {
            return true;
         });
      } finally {
         this.m_18754_();
         this.m_18760_();
      }

   }

   public void m_6937_(T p_18750_) {
      this.f_18734_.m_6944_(p_18750_);
      this.m_18760_();
   }

   private void m_18760_() {
      if (this.m_18756_() && this.m_18744_()) {
         try {
            this.f_18737_.execute(this);
         } catch (RejectedExecutionException rejectedexecutionexception1) {
            try {
               this.f_18737_.execute(this);
            } catch (RejectedExecutionException rejectedexecutionexception) {
               f_18735_.error("Cound not schedule mailbox", (Throwable)rejectedexecutionexception);
            }
         }
      }

   }

   private int m_18747_(Int2BooleanFunction p_18748_) {
      int i;
      for(i = 0; p_18748_.get(i) && this.m_18759_(); ++i) {
      }

      return i;
   }

   public int m_146355_() {
      return this.f_18734_.m_142732_();
   }

   public String toString() {
      return this.f_18738_ + " " + this.f_18736_.get() + " " + this.f_18734_.m_7263_();
   }

   public String m_7326_() {
      return this.f_18738_;
   }

   public List<MetricSampler> m_142754_() {
      return ImmutableList.of(MetricSampler.m_146009_(this.f_18738_ + "-queue-size", MetricCategory.MAIL_BOXES, this::m_146355_));
   }
}