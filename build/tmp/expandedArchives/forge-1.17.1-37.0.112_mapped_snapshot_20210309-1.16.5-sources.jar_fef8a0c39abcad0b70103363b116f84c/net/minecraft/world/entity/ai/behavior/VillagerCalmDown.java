package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;

public class VillagerCalmDown extends Behavior<Villager> {
   private static final int f_148039_ = 36;

   public VillagerCalmDown() {
      super(ImmutableMap.of());
   }

   protected void m_6735_(ServerLevel p_24574_, Villager p_24575_, long p_24576_) {
      boolean flag = VillagerPanicTrigger.m_24697_(p_24575_) || VillagerPanicTrigger.m_24687_(p_24575_) || m_24577_(p_24575_);
      if (!flag) {
         p_24575_.m_6274_().m_21936_(MemoryModuleType.f_26381_);
         p_24575_.m_6274_().m_21936_(MemoryModuleType.f_26382_);
         p_24575_.m_6274_().m_21862_(p_24574_.m_46468_(), p_24574_.m_46467_());
      }

   }

   private static boolean m_24577_(Villager p_24578_) {
      return p_24578_.m_6274_().m_21952_(MemoryModuleType.f_26382_).filter((p_24581_) -> {
         return p_24581_.m_20280_(p_24578_) <= 36.0D;
      }).isPresent();
   }
}