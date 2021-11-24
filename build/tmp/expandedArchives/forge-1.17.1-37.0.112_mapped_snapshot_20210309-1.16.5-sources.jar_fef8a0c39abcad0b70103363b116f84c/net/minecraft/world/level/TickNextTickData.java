package net.minecraft.world.level;

import java.util.Comparator;
import net.minecraft.core.BlockPos;

public class TickNextTickData<T> {
   private static long f_47326_;
   private final T f_47327_;
   public final BlockPos f_47323_;
   public final long f_47324_;
   public final TickPriority f_47325_;
   private final long f_47328_;

   public TickNextTickData(BlockPos p_47330_, T p_47331_) {
      this(p_47330_, p_47331_, 0L, TickPriority.NORMAL);
   }

   public TickNextTickData(BlockPos p_47333_, T p_47334_, long p_47335_, TickPriority p_47336_) {
      this.f_47328_ = (long)(f_47326_++);
      this.f_47323_ = p_47333_.m_7949_();
      this.f_47327_ = p_47334_;
      this.f_47324_ = p_47335_;
      this.f_47325_ = p_47336_;
   }

   public boolean equals(Object p_47346_) {
      if (!(p_47346_ instanceof TickNextTickData)) {
         return false;
      } else {
         TickNextTickData<?> ticknexttickdata = (TickNextTickData)p_47346_;
         return this.f_47323_.equals(ticknexttickdata.f_47323_) && this.f_47327_ == ticknexttickdata.f_47327_;
      }
   }

   public int hashCode() {
      return this.f_47323_.hashCode();
   }

   public static <T> Comparator<TickNextTickData<T>> m_47337_() {
      return Comparator.<TickNextTickData<T>>comparingLong((p_47344_) -> {
         return p_47344_.f_47324_;
      }).thenComparing((p_47342_) -> {
         return p_47342_.f_47325_;
      }).thenComparingLong((p_47339_) -> {
         return p_47339_.f_47328_;
      });
   }

   public String toString() {
      return this.f_47327_ + ": " + this.f_47323_ + ", " + this.f_47324_ + ", " + this.f_47325_ + ", " + this.f_47328_;
   }

   public T m_47340_() {
      return this.f_47327_;
   }
}