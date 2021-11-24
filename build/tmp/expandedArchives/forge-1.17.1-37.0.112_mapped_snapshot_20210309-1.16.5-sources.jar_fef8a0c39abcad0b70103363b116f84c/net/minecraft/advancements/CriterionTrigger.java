package net.minecraft.advancements;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;

public interface CriterionTrigger<T extends CriterionTriggerInstance> {
   ResourceLocation m_7295_();

   void m_6467_(PlayerAdvancements p_13674_, CriterionTrigger.Listener<T> p_13675_);

   void m_6468_(PlayerAdvancements p_13676_, CriterionTrigger.Listener<T> p_13677_);

   void m_5656_(PlayerAdvancements p_13673_);

   T m_5868_(JsonObject p_13671_, DeserializationContext p_13672_);

   public static class Listener<T extends CriterionTriggerInstance> {
      private final T f_13678_;
      private final Advancement f_13679_;
      private final String f_13680_;

      public Listener(T p_13682_, Advancement p_13683_, String p_13684_) {
         this.f_13678_ = p_13682_;
         this.f_13679_ = p_13683_;
         this.f_13680_ = p_13684_;
      }

      public T m_13685_() {
         return this.f_13678_;
      }

      public void m_13686_(PlayerAdvancements p_13687_) {
         p_13687_.m_135988_(this.f_13679_, this.f_13680_);
      }

      public boolean equals(Object p_13689_) {
         if (this == p_13689_) {
            return true;
         } else if (p_13689_ != null && this.getClass() == p_13689_.getClass()) {
            CriterionTrigger.Listener<?> listener = (CriterionTrigger.Listener)p_13689_;
            if (!this.f_13678_.equals(listener.f_13678_)) {
               return false;
            } else {
               return !this.f_13679_.equals(listener.f_13679_) ? false : this.f_13680_.equals(listener.f_13680_);
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int i = this.f_13678_.hashCode();
         i = 31 * i + this.f_13679_.hashCode();
         return 31 * i + this.f_13680_.hashCode();
      }
   }
}