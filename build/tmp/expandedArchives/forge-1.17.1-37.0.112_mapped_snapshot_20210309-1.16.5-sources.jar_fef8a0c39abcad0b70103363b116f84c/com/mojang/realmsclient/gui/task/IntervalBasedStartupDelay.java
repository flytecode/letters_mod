package com.mojang.realmsclient.gui.task;

import com.google.common.annotations.VisibleForTesting;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IntervalBasedStartupDelay implements RestartDelayCalculator {
   private final Duration f_167559_;
   private final Supplier<Clock> f_167560_;
   @Nullable
   private Instant f_167561_;

   public IntervalBasedStartupDelay(Duration p_167563_) {
      this.f_167559_ = p_167563_;
      this.f_167560_ = Clock::systemUTC;
   }

   @VisibleForTesting
   protected IntervalBasedStartupDelay(Duration p_167565_, Supplier<Clock> p_167566_) {
      this.f_167559_ = p_167565_;
      this.f_167560_ = p_167566_;
   }

   public void m_142685_() {
      this.f_167561_ = Instant.now(this.f_167560_.get());
   }

   public long m_142678_() {
      return this.f_167561_ == null ? 0L : Math.max(0L, Duration.between(Instant.now(this.f_167560_.get()), this.f_167561_.plus(this.f_167559_)).toMillis());
   }
}