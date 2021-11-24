package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;

public class ResetRaidStatus extends Behavior<LivingEntity> {
   public ResetRaidStatus() {
      super(ImmutableMap.of());
   }

   protected boolean m_6114_(ServerLevel p_23781_, LivingEntity p_23782_) {
      return p_23781_.f_46441_.nextInt(20) == 0;
   }

   protected void m_6735_(ServerLevel p_23784_, LivingEntity p_23785_, long p_23786_) {
      Brain<?> brain = p_23785_.m_6274_();
      Raid raid = p_23784_.m_8832_(p_23785_.m_142538_());
      if (raid == null || raid.m_37762_() || raid.m_37768_()) {
         brain.m_21944_(Activity.f_37979_);
         brain.m_21862_(p_23784_.m_46468_(), p_23784_.m_46467_());
      }

   }
}