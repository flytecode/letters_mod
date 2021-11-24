package net.minecraft.world.entity.ai.goal;

import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;

public class LookAtTradingPlayerGoal extends LookAtPlayerGoal {
   private final AbstractVillager f_25536_;

   public LookAtTradingPlayerGoal(AbstractVillager p_25538_) {
      super(p_25538_, Player.class, 8.0F);
      this.f_25536_ = p_25538_;
   }

   public boolean m_8036_() {
      if (this.f_25536_.m_35306_()) {
         this.f_25513_ = this.f_25536_.m_7962_();
         return true;
      } else {
         return false;
      }
   }
}