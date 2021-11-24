package net.minecraft.server.level;

import java.util.Objects;

public final class Ticket<T> implements Comparable<Ticket<?>> {
   private final TicketType<T> f_9420_;
   private final int f_9421_;
   private final T f_9422_;
   private long f_9423_;

   protected Ticket(TicketType<T> p_9425_, int p_9426_, T p_9427_) {
      this(p_9425_, p_9426_, p_9427_, false);
   }

   public Ticket(TicketType<T> p_9425_, int p_9426_, T p_9427_, boolean forceTicks) {
      this.f_9420_ = p_9425_;
      this.f_9421_ = p_9426_;
      this.f_9422_ = p_9427_;
      this.forceTicks = forceTicks;
   }

   public int compareTo(Ticket<?> p_9432_) {
      int i = Integer.compare(this.f_9421_, p_9432_.f_9421_);
      if (i != 0) {
         return i;
      } else {
         int j = Integer.compare(System.identityHashCode(this.f_9420_), System.identityHashCode(p_9432_.f_9420_));
         return j != 0 ? j : this.f_9420_.m_9458_().compare(this.f_9422_, (T)p_9432_.f_9422_);
      }
   }

   public boolean equals(Object p_9439_) {
      if (this == p_9439_) {
         return true;
      } else if (!(p_9439_ instanceof Ticket)) {
         return false;
      } else {
         Ticket<?> ticket = (Ticket)p_9439_;
         return this.f_9421_ == ticket.f_9421_ && Objects.equals(this.f_9420_, ticket.f_9420_) && Objects.equals(this.f_9422_, ticket.f_9422_) && this.forceTicks == ticket.forceTicks;
      }
   }

   public int hashCode() {
      return Objects.hash(this.f_9420_, this.f_9421_, this.f_9422_, forceTicks);
   }

   public String toString() {
      return "Ticket[" + this.f_9420_ + " " + this.f_9421_ + " (" + this.f_9422_ + ")] at " + this.f_9423_ + " force ticks " + forceTicks;
   }

   public TicketType<T> m_9428_() {
      return this.f_9420_;
   }

   public int m_9433_() {
      return this.f_9421_;
   }

   protected void m_9429_(long p_9430_) {
      this.f_9423_ = p_9430_;
   }

   protected boolean m_9434_(long p_9435_) {
      long i = this.f_9420_.m_9469_();
      return i != 0L && p_9435_ - this.f_9423_ > i;
   }

   /* ======================================== FORGE START =====================================*/
   private final boolean forceTicks;

   public boolean isForceTicks() {
      return forceTicks;
   }
}
