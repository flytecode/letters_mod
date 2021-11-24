package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class LookAtTargetSink extends Behavior<Mob> {
   public LookAtTargetSink(int p_23478_, int p_23479_) {
      super(ImmutableMap.of(MemoryModuleType.f_26371_, MemoryStatus.VALUE_PRESENT), p_23478_, p_23479_);
   }

   protected boolean m_6737_(ServerLevel p_23481_, Mob p_23482_, long p_23483_) {
      return p_23482_.m_6274_().m_21952_(MemoryModuleType.f_26371_).filter((p_23497_) -> {
         return p_23497_.m_6826_(p_23482_);
      }).isPresent();
   }

   protected void m_6732_(ServerLevel p_23492_, Mob p_23493_, long p_23494_) {
      p_23493_.m_6274_().m_21936_(MemoryModuleType.f_26371_);
   }

   protected void m_6725_(ServerLevel p_23503_, Mob p_23504_, long p_23505_) {
      p_23504_.m_6274_().m_21952_(MemoryModuleType.f_26371_).ifPresent((p_23486_) -> {
         p_23504_.m_21563_().m_24964_(p_23486_.m_7024_());
      });
   }
}