package net.minecraft.world.entity.monster.piglin;

import com.google.common.collect.ImmutableMap;
import java.util.Optional;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.item.ItemEntity;

public class StopAdmiringIfItemTooFarAway<E extends Piglin> extends Behavior<E> {
   private final int f_35210_;

   public StopAdmiringIfItemTooFarAway(int p_35212_) {
      super(ImmutableMap.of(MemoryModuleType.f_26336_, MemoryStatus.VALUE_PRESENT, MemoryModuleType.f_26332_, MemoryStatus.REGISTERED));
      this.f_35210_ = p_35212_;
   }

   protected boolean m_6114_(ServerLevel p_35221_, E p_35222_) {
      if (!p_35222_.m_21206_().m_41619_()) {
         return false;
      } else {
         Optional<ItemEntity> optional = p_35222_.m_6274_().m_21952_(MemoryModuleType.f_26332_);
         if (!optional.isPresent()) {
            return true;
         } else {
            return !optional.get().m_19950_(p_35222_, (double)this.f_35210_);
         }
      }
   }

   protected void m_6735_(ServerLevel p_35224_, E p_35225_, long p_35226_) {
      p_35225_.m_6274_().m_21936_(MemoryModuleType.f_26336_);
   }
}