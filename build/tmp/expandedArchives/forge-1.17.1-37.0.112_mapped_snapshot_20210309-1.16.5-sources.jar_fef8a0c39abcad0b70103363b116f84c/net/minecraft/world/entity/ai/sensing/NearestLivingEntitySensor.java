package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.phys.AABB;

public class NearestLivingEntitySensor extends Sensor<LivingEntity> {
   protected void m_5578_(ServerLevel p_26710_, LivingEntity p_26711_) {
      AABB aabb = p_26711_.m_142469_().m_82377_(16.0D, 16.0D, 16.0D);
      List<LivingEntity> list = p_26710_.m_6443_(LivingEntity.class, aabb, (p_26717_) -> {
         return p_26717_ != p_26711_ && p_26717_.m_6084_();
      });
      list.sort(Comparator.comparingDouble(p_26711_::m_20280_));
      Brain<?> brain = p_26711_.m_6274_();
      brain.m_21879_(MemoryModuleType.f_148204_, list);
      brain.m_21879_(MemoryModuleType.f_148205_, list.stream().filter((p_26714_) -> {
         return m_26803_(p_26711_, p_26714_);
      }).collect(Collectors.toList()));
   }

   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_148204_, MemoryModuleType.f_148205_);
   }
}