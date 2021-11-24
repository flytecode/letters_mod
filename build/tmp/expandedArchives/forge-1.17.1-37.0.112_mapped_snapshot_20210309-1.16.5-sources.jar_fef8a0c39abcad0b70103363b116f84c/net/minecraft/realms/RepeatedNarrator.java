package net.minecraft.realms;

import com.google.common.util.concurrent.RateLimiter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RepeatedNarrator {
   private final float f_120785_;
   private final AtomicReference<RepeatedNarrator.Params> f_120786_ = new AtomicReference<>();

   public RepeatedNarrator(Duration p_120788_) {
      this.f_120785_ = 1000.0F / (float)p_120788_.toMillis();
   }

   public void m_175076_(Component p_175077_) {
      RepeatedNarrator.Params repeatednarrator$params = this.f_120786_.updateAndGet((p_175080_) -> {
         return p_175080_ != null && p_175077_.equals(p_175080_.f_120794_) ? p_175080_ : new RepeatedNarrator.Params(p_175077_, RateLimiter.create((double)this.f_120785_));
      });
      if (repeatednarrator$params.f_120795_.tryAcquire(1)) {
         NarratorChatListener.f_93311_.m_168785_(p_175077_);
      }

   }

   @OnlyIn(Dist.CLIENT)
   static class Params {
      final Component f_120794_;
      final RateLimiter f_120795_;

      Params(Component p_175082_, RateLimiter p_175083_) {
         this.f_120794_ = p_175082_;
         this.f_120795_ = p_175083_;
      }
   }
}