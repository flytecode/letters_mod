package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class UpdateActivityFromSchedule extends Behavior<LivingEntity> {
   public UpdateActivityFromSchedule() {
      super(ImmutableMap.of());
   }

   protected void m_6735_(ServerLevel p_24458_, LivingEntity p_24459_, long p_24460_) {
      p_24459_.m_6274_().m_21862_(p_24458_.m_46468_(), p_24458_.m_46467_());
   }
}