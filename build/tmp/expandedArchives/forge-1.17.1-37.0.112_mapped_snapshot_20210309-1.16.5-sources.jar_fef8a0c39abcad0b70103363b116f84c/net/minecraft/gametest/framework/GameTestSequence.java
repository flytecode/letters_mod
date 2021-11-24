package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class GameTestSequence {
   final GameTestInfo f_127774_;
   private final List<GameTestEvent> f_127775_ = Lists.newArrayList();
   private long f_127776_;

   GameTestSequence(GameTestInfo p_177542_) {
      this.f_127774_ = p_177542_;
      this.f_127776_ = p_177542_.m_177488_();
   }

   public GameTestSequence m_177552_(Runnable p_177553_) {
      this.f_127775_.add(GameTestEvent.m_177097_(p_177553_));
      return this;
   }

   public GameTestSequence m_177549_(long p_177550_, Runnable p_177551_) {
      this.f_127775_.add(GameTestEvent.m_177094_(p_177550_, p_177551_));
      return this;
   }

   public GameTestSequence m_177544_(int p_177545_) {
      return this.m_177546_(p_177545_, () -> {
      });
   }

   public GameTestSequence m_177562_(Runnable p_177563_) {
      this.f_127775_.add(GameTestEvent.m_177097_(() -> {
         this.m_177570_(p_177563_);
      }));
      return this;
   }

   public GameTestSequence m_177546_(int p_177547_, Runnable p_177548_) {
      this.f_127775_.add(GameTestEvent.m_177097_(() -> {
         if (this.f_127774_.m_177488_() < this.f_127776_ + (long)p_177547_) {
            throw new GameTestAssertException("Waiting");
         } else {
            this.m_177570_(p_177548_);
         }
      }));
      return this;
   }

   public GameTestSequence m_177559_(int p_177560_, Runnable p_177561_) {
      this.f_127775_.add(GameTestEvent.m_177097_(() -> {
         if (this.f_127774_.m_177488_() < this.f_127776_ + (long)p_177560_) {
            this.m_177570_(p_177561_);
            throw new GameTestAssertException("Waiting");
         }
      }));
      return this;
   }

   public void m_177543_() {
      this.f_127775_.add(GameTestEvent.m_177097_(this.f_127774_::m_177486_));
   }

   public void m_177554_(Supplier<Exception> p_177555_) {
      this.f_127775_.add(GameTestEvent.m_177097_(() -> {
         this.f_127774_.m_127622_(p_177555_.get());
      }));
   }

   public GameTestSequence.Condition m_177558_() {
      GameTestSequence.Condition gametestsequence$condition = new GameTestSequence.Condition();
      this.f_127775_.add(GameTestEvent.m_177097_(() -> {
         gametestsequence$condition.m_177583_(this.f_127774_.m_177488_());
      }));
      return gametestsequence$condition;
   }

   public void m_127777_(long p_127778_) {
      try {
         this.m_127781_(p_127778_);
      } catch (GameTestAssertException gametestassertexception) {
      }

   }

   public void m_127779_(long p_127780_) {
      try {
         this.m_127781_(p_127780_);
      } catch (GameTestAssertException gametestassertexception) {
         this.f_127774_.m_127622_(gametestassertexception);
      }

   }

   private void m_177570_(Runnable p_177571_) {
      try {
         p_177571_.run();
      } catch (GameTestAssertException gametestassertexception) {
         this.f_127774_.m_127622_(gametestassertexception);
      }

   }

   private void m_127781_(long p_127782_) {
      Iterator<GameTestEvent> iterator = this.f_127775_.iterator();

      while(iterator.hasNext()) {
         GameTestEvent gametestevent = iterator.next();
         gametestevent.f_127594_.run();
         iterator.remove();
         long i = p_127782_ - this.f_127776_;
         long j = this.f_127776_;
         this.f_127776_ = p_127782_;
         if (gametestevent.f_127593_ != null && gametestevent.f_127593_ != i) {
            this.f_127774_.m_127622_(new GameTestAssertException("Succeeded in invalid tick: expected " + (j + gametestevent.f_127593_) + ", but current tick is " + p_127782_));
            break;
         }
      }

   }

   public class Condition {
      private static final long f_177578_ = -1L;
      private long f_177579_ = -1L;

      void m_177583_(long p_177584_) {
         if (this.f_177579_ != -1L) {
            throw new IllegalStateException("Condition already triggered at " + this.f_177579_);
         } else {
            this.f_177579_ = p_177584_;
         }
      }

      public void m_177582_() {
         long i = GameTestSequence.this.f_127774_.m_177488_();
         if (this.f_177579_ != i) {
            if (this.f_177579_ == -1L) {
               throw new GameTestAssertException("Condition not triggered (t=" + i + ")");
            } else {
               throw new GameTestAssertException("Condition triggered at " + this.f_177579_ + ", (t=" + i + ")");
            }
         }
      }
   }
}