package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;

public class SwellGoal extends Goal {
   private final Creeper f_25916_;
   private LivingEntity f_25917_;

   public SwellGoal(Creeper p_25919_) {
      this.f_25916_ = p_25919_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean m_8036_() {
      LivingEntity livingentity = this.f_25916_.m_5448_();
      return this.f_25916_.m_32310_() > 0 || livingentity != null && this.f_25916_.m_20280_(livingentity) < 9.0D;
   }

   public void m_8056_() {
      this.f_25916_.m_21573_().m_26573_();
      this.f_25917_ = this.f_25916_.m_5448_();
   }

   public void m_8041_() {
      this.f_25917_ = null;
   }

   public void m_8037_() {
      if (this.f_25917_ == null) {
         this.f_25916_.m_32283_(-1);
      } else if (this.f_25916_.m_20280_(this.f_25917_) > 49.0D) {
         this.f_25916_.m_32283_(-1);
      } else if (!this.f_25916_.m_21574_().m_148306_(this.f_25917_)) {
         this.f_25916_.m_32283_(-1);
      } else {
         this.f_25916_.m_32283_(1);
      }
   }
}