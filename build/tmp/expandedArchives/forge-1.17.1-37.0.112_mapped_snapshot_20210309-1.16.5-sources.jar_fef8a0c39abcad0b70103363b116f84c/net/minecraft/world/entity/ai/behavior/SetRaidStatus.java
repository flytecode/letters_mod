package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;

public class SetRaidStatus extends Behavior<LivingEntity> {
   public SetRaidStatus() {
      super(ImmutableMap.of());
   }

   protected boolean m_6114_(ServerLevel p_23976_, LivingEntity p_23977_) {
      return p_23976_.f_46441_.nextInt(20) == 0;
   }

   protected void m_6735_(ServerLevel p_23979_, LivingEntity p_23980_, long p_23981_) {
      Brain<?> brain = p_23980_.m_6274_();
      Raid raid = p_23979_.m_8832_(p_23980_.m_142538_());
      if (raid != null) {
         if (raid.m_37757_() && !raid.m_37749_()) {
            brain.m_21944_(Activity.f_37985_);
            brain.m_21889_(Activity.f_37985_);
         } else {
            brain.m_21944_(Activity.f_37986_);
            brain.m_21889_(Activity.f_37986_);
         }
      }

   }
}