package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;

public class VillagerBabiesSensor extends Sensor<LivingEntity> {
   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_26366_);
   }

   protected void m_5578_(ServerLevel p_26834_, LivingEntity p_26835_) {
      p_26835_.m_6274_().m_21879_(MemoryModuleType.f_26366_, this.m_26836_(p_26835_));
   }

   private List<LivingEntity> m_26836_(LivingEntity p_26837_) {
      return this.m_26840_(p_26837_).stream().filter(this::m_26838_).collect(Collectors.toList());
   }

   private boolean m_26838_(LivingEntity p_26839_) {
      return p_26839_.m_6095_() == EntityType.f_20492_ && p_26839_.m_6162_();
   }

   private List<LivingEntity> m_26840_(LivingEntity p_26841_) {
      return p_26841_.m_6274_().m_21952_(MemoryModuleType.f_148205_).orElse(Lists.newArrayList());
   }
}