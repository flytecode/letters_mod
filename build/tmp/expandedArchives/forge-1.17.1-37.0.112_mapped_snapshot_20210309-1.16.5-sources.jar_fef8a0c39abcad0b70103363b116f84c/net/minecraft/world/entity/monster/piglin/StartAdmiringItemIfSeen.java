package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.item.ItemEntity;

public class StartAdmiringItemIfSeen<E extends Piglin> extends Behavior<E> {
   private final int f_35138_;

   public StartAdmiringItemIfSeen(int p_35140_) {
      super(ImmutableMap.of(MemoryModuleType.f_26332_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26336_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26339_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26338_, MemoryStatus.VALUE_ABSENT));
      this.f_35138_ = p_35140_;
   }

   protected boolean m_6114_(ServerLevel p_35149_, E p_35150_) {
      ItemEntity itementity = p_35150_.m_6274_().m_21952_(MemoryModuleType.f_26332_).get();
      return PiglinAi.m_149965_(itementity.m_32055_());
   }

   protected void m_6735_(ServerLevel p_35152_, E p_35153_, long p_35154_) {
      p_35153_.m_6274_().m_21882_(MemoryModuleType.f_26336_, true, (long)this.f_35138_);
   }
}