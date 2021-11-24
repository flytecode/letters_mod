package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raid;

public class GoOutsideToCelebrate extends MoveToSkySeeingSpot {
   public GoOutsideToCelebrate(float p_23050_) {
      super(p_23050_);
   }

   protected boolean m_6114_(ServerLevel p_23052_, LivingEntity p_23053_) {
      Raid raid = p_23052_.m_8832_(p_23053_.m_142538_());
      return raid != null && raid.m_37767_() && super.m_6114_(p_23052_, p_23053_);
   }
}