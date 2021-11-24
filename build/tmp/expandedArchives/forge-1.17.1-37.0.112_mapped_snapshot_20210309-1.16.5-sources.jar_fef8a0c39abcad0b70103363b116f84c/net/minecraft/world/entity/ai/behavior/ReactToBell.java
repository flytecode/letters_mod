package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.schedule.Activity;

public class ReactToBell extends Behavior<LivingEntity> {
   public ReactToBell() {
      super(ImmutableMap.of(MemoryModuleType.f_26325_, MemoryStatus.VALUE_PRESENT));
   }

   protected void m_6735_(ServerLevel p_23761_, LivingEntity p_23762_, long p_23763_) {
      Brain<?> brain = p_23762_.m_6274_();
      Raid raid = p_23761_.m_8832_(p_23762_.m_142538_());
      if (raid == null) {
         brain.m_21889_(Activity.f_37987_);
      }

   }
}