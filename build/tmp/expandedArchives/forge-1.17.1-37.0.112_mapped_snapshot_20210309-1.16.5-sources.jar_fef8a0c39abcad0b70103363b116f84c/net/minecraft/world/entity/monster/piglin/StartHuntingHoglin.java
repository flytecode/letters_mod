package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.hoglin.Hoglin;

public class StartHuntingHoglin<E extends Piglin> extends Behavior<E> {
   public StartHuntingHoglin() {
      super(ImmutableMap.of(MemoryModuleType.f_26343_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26334_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26340_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26347_, MemoryStatus.REGISTERED));
   }

   protected boolean m_6114_(ServerLevel p_35164_, Piglin p_35165_) {
      return !p_35165_.m_6162_() && !PiglinAi.m_34965_(p_35165_);
   }

   protected void m_6735_(ServerLevel p_35167_, E p_35168_, long p_35169_) {
      Hoglin hoglin = p_35168_.m_6274_().m_21952_(MemoryModuleType.f_26343_).get();
      PiglinAi.m_34924_(p_35168_, hoglin);
      PiglinAi.m_34922_(p_35168_);
      PiglinAi.m_34895_(p_35168_, hoglin);
      PiglinAi.m_34977_(p_35168_);
   }
}