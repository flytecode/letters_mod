package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class DefendVillageTargetGoal extends TargetGoal {
   private final IronGolem f_26025_;
   private LivingEntity f_26026_;
   private final TargetingConditions f_26027_ = TargetingConditions.m_148352_().m_26883_(64.0D);

   public DefendVillageTargetGoal(IronGolem p_26029_) {
      super(p_26029_, false, true);
      this.f_26025_ = p_26029_;
      this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
   }

   public boolean m_8036_() {
      AABB aabb = this.f_26025_.m_142469_().m_82377_(10.0D, 8.0D, 10.0D);
      List<? extends LivingEntity> list = this.f_26025_.f_19853_.m_45971_(Villager.class, this.f_26027_, this.f_26025_, aabb);
      List<Player> list1 = this.f_26025_.f_19853_.m_45955_(this.f_26027_, this.f_26025_, aabb);

      for(LivingEntity livingentity : list) {
         Villager villager = (Villager)livingentity;

         for(Player player : list1) {
            int i = villager.m_35532_(player);
            if (i <= -100) {
               this.f_26026_ = player;
            }
         }
      }

      if (this.f_26026_ == null) {
         return false;
      } else {
         return !(this.f_26026_ instanceof Player) || !this.f_26026_.m_5833_() && !((Player)this.f_26026_).m_7500_();
      }
   }

   public void m_8056_() {
      this.f_26025_.m_6710_(this.f_26026_);
      super.m_8056_();
   }
}