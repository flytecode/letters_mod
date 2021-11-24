package net.minecraft.world.entity.ai.goal;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.ShoulderRidingEntity;

public class LandOnOwnersShoulderGoal extends Goal {
   private final ShoulderRidingEntity f_25479_;
   private ServerPlayer f_25480_;
   private boolean f_25481_;

   public LandOnOwnersShoulderGoal(ShoulderRidingEntity p_25483_) {
      this.f_25479_ = p_25483_;
   }

   public boolean m_8036_() {
      ServerPlayer serverplayer = (ServerPlayer)this.f_25479_.m_142480_();
      boolean flag = serverplayer != null && !serverplayer.m_5833_() && !serverplayer.m_150110_().f_35935_ && !serverplayer.m_20069_() && !serverplayer.f_146808_;
      return !this.f_25479_.m_21827_() && flag && this.f_25479_.m_29897_();
   }

   public boolean m_6767_() {
      return !this.f_25481_;
   }

   public void m_8056_() {
      this.f_25480_ = (ServerPlayer)this.f_25479_.m_142480_();
      this.f_25481_ = false;
   }

   public void m_8037_() {
      if (!this.f_25481_ && !this.f_25479_.m_21825_() && !this.f_25479_.m_21523_()) {
         if (this.f_25479_.m_142469_().m_82381_(this.f_25480_.m_142469_())) {
            this.f_25481_ = this.f_25479_.m_29895_(this.f_25480_);
         }

      }
   }
}