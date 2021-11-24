package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;

public class SitWhenOrderedToGoal extends Goal {
   private final TamableAnimal f_25896_;

   public SitWhenOrderedToGoal(TamableAnimal p_25898_) {
      this.f_25896_ = p_25898_;
      this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   public boolean m_8045_() {
      return this.f_25896_.m_21827_();
   }

   public boolean m_8036_() {
      if (!this.f_25896_.m_21824_()) {
         return false;
      } else if (this.f_25896_.m_20072_()) {
         return false;
      } else if (!this.f_25896_.m_20096_()) {
         return false;
      } else {
         LivingEntity livingentity = this.f_25896_.m_142480_();
         if (livingentity == null) {
            return true;
         } else {
            return this.f_25896_.m_20280_(livingentity) < 144.0D && livingentity.m_142581_() != null ? false : this.f_25896_.m_21827_();
         }
      }
   }

   public void m_8056_() {
      this.f_25896_.m_21573_().m_26573_();
      this.f_25896_.m_21837_(true);
   }

   public void m_8041_() {
      this.f_25896_.m_21837_(false);
   }
}