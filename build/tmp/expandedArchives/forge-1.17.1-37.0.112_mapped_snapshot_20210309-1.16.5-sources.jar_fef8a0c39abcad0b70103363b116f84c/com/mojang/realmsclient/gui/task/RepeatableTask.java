package com.mojang.realmsclient.gui.task;

import java.time.Duration;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BooleanSupplier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RepeatableTask implements Runnable {
   private final BooleanSupplier f_167572_;
   private final RestartDelayCalculator f_167573_;
   private final Duration f_167574_;
   private final Runnable f_167575_;

   private RepeatableTask(Runnable p_167577_, Duration p_167578_, BooleanSupplier p_167579_, RestartDelayCalculator p_167580_) {
      this.f_167575_ = p_167577_;
      this.f_167574_ = p_167578_;
      this.f_167572_ = p_167579_;
      this.f_167573_ = p_167580_;
   }

   public void run() {
      if (this.f_167572_.getAsBoolean()) {
         this.f_167573_.m_142685_();
         this.f_167575_.run();
      }

   }

   public ScheduledFuture<?> m_167585_(ScheduledExecutorService p_167586_) {
      return p_167586_.scheduleAtFixedRate(this, this.f_167573_.m_142678_(), this.f_167574_.toMillis(), TimeUnit.MILLISECONDS);
   }

   public static RepeatableTask m_167581_(Runnable p_167582_, Duration p_167583_, BooleanSupplier p_167584_) {
      return new RepeatableTask(p_167582_, p_167583_, p_167584_, new IntervalBasedStartupDelay(p_167583_));
   }

   public static RepeatableTask m_167587_(Runnable p_167588_, Duration p_167589_, BooleanSupplier p_167590_) {
      return new RepeatableTask(p_167588_, p_167589_, p_167590_, new NoStartupDelay());
   }
}