package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;

public class TradeWithPlayerGoal extends Goal {
   private final AbstractVillager f_25956_;

   public TradeWithPlayerGoal(AbstractVillager p_25958_) {
      this.f_25956_ = p_25958_;
      this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   public boolean m_8036_() {
      if (!this.f_25956_.m_6084_()) {
         return false;
      } else if (this.f_25956_.m_20069_()) {
         return false;
      } else if (!this.f_25956_.m_20096_()) {
         return false;
      } else if (this.f_25956_.f_19864_) {
         return false;
      } else {
         Player player = this.f_25956_.m_7962_();
         if (player == null) {
            return false;
         } else if (this.f_25956_.m_20280_(player) > 16.0D) {
            return false;
         } else {
            return player.f_36096_ != null;
         }
      }
   }

   public void m_8056_() {
      this.f_25956_.m_21573_().m_26573_();
   }

   public void m_8041_() {
      this.f_25956_.m_7189_((Player)null);
   }
}