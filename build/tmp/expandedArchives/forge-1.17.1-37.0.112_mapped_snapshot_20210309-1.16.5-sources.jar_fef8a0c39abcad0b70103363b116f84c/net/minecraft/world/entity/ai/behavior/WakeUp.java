package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.schedule.Activity;

public class WakeUp extends Behavior<LivingEntity> {
   public WakeUp() {
      super(ImmutableMap.of());
   }

   protected boolean m_6114_(ServerLevel p_24709_, LivingEntity p_24710_) {
      return !p_24710_.m_6274_().m_21954_(Activity.f_37982_) && p_24710_.m_5803_();
   }

   protected void m_6735_(ServerLevel p_24712_, LivingEntity p_24713_, long p_24714_) {
      p_24713_.m_5796_();
   }
}