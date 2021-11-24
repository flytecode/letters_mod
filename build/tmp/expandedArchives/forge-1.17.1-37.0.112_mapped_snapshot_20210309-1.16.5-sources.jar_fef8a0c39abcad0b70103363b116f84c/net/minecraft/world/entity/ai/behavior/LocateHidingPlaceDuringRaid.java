package net.minecraft.world.entity.ai.behavior;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raid;

public class LocateHidingPlaceDuringRaid extends LocateHidingPlace {
   public LocateHidingPlaceDuringRaid(int p_23427_, float p_23428_) {
      super(p_23427_, p_23428_, 1);
   }

   protected boolean m_6114_(ServerLevel p_23430_, LivingEntity p_23431_) {
      Raid raid = p_23430_.m_8832_(p_23431_.m_142538_());
      return super.m_6114_(p_23430_, p_23431_) && raid != null && raid.m_37782_() && !raid.m_37767_() && !raid.m_37768_();
   }
}