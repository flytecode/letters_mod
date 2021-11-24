package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

public class StopAdmiringIfTiredOfTryingToReachItem<E extends Piglin> extends Behavior<E> {
   private final int f_35227_;
   private final int f_35228_;

   public StopAdmiringIfTiredOfTryingToReachItem(int p_35230_, int p_35231_) {
      super(ImmutableMap.of(MemoryModuleType.f_26336_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26332_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26337_, MemoryStatus.REGISTERED, MemoryModuleType.f_26338_, MemoryStatus.REGISTERED));
      this.f_35227_ = p_35230_;
      this.f_35228_ = p_35231_;
   }

   protected boolean m_6114_(ServerLevel p_35240_, E p_35241_) {
      return p_35241_.m_21206_().m_41619_();
   }

   protected void m_6735_(ServerLevel p_35243_, E p_35244_, long p_35245_) {
      Brain<Piglin> brain = p_35244_.m_6274_();
      Optional<Integer> optional = brain.m_21952_(MemoryModuleType.f_26337_);
      if (!optional.isPresent()) {
         brain.m_21879_(MemoryModuleType.f_26337_, 0);
      } else {
         int i = optional.get();
         if (i > this.f_35227_) {
            brain.m_21936_(MemoryModuleType.f_26336_);
            brain.m_21936_(MemoryModuleType.f_26337_);
            brain.m_21882_(MemoryModuleType.f_26338_, true, (long)this.f_35228_);
         } else {
            brain.m_21879_(MemoryModuleType.f_26337_, i + 1);
         }
      }

   }
}