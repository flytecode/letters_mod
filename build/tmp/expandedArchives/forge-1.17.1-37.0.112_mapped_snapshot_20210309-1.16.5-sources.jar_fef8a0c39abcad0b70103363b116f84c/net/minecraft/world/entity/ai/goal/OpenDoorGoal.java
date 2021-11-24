package net.minecraft.world.entity.ai.goal;

import net.minecraft.world.entity.Mob;

public class OpenDoorGoal extends DoorInteractGoal {
   private final boolean f_25675_;
   private int f_25676_;

   public OpenDoorGoal(Mob p_25678_, boolean p_25679_) {
      super(p_25678_);
      this.f_25189_ = p_25678_;
      this.f_25675_ = p_25679_;
   }

   public boolean m_8045_() {
      return this.f_25675_ && this.f_25676_ > 0 && super.m_8045_();
   }

   public void m_8056_() {
      this.f_25676_ = 20;
      this.m_25195_(true);
   }

   public void m_8041_() {
      this.m_25195_(false);
   }

   public void m_8037_() {
      --this.f_25676_;
      super.m_8037_();
   }
}