package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class AdultSensor extends Sensor<AgeableMob> {
   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_26331_, MemoryModuleType.f_148205_);
   }

   protected void m_5578_(ServerLevel p_148248_, AgeableMob p_148249_) {
      p_148249_.m_6274_().m_21952_(MemoryModuleType.f_148205_).ifPresent((p_148262_) -> {
         this.m_148255_(p_148249_, p_148262_);
      });
   }

   private void m_148255_(AgeableMob p_148256_, List<LivingEntity> p_148257_) {
      Optional<AgeableMob> optional = p_148257_.stream().filter((p_148254_) -> {
         return p_148254_.m_6095_() == p_148256_.m_6095_();
      }).map((p_148259_) -> {
         return (AgeableMob)p_148259_;
      }).filter((p_148251_) -> {
         return !p_148251_.m_6162_();
      }).findFirst();
      p_148256_.m_6274_().m_21886_(MemoryModuleType.f_26331_, optional);
   }
}