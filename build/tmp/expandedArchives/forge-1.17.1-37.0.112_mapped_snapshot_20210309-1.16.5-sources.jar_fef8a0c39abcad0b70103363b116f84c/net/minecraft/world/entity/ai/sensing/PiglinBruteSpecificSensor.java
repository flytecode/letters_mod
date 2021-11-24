package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;

public class PiglinBruteSpecificSensor extends Sensor<LivingEntity> {
   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_148205_, MemoryModuleType.f_26333_, MemoryModuleType.f_26346_);
   }

   protected void m_5578_(ServerLevel p_26721_, LivingEntity p_26722_) {
      Brain<?> brain = p_26722_.m_6274_();
      Optional<Mob> optional = Optional.empty();
      List<AbstractPiglin> list = Lists.newArrayList();

      for(LivingEntity livingentity : brain.m_21952_(MemoryModuleType.f_148205_).orElse(ImmutableList.of())) {
         if (livingentity instanceof WitherSkeleton || livingentity instanceof WitherBoss) {
            optional = Optional.of((Mob)livingentity);
            break;
         }
      }

      for(LivingEntity livingentity1 : brain.m_21952_(MemoryModuleType.f_148204_).orElse(ImmutableList.of())) {
         if (livingentity1 instanceof AbstractPiglin && ((AbstractPiglin)livingentity1).m_34667_()) {
            list.add((AbstractPiglin)livingentity1);
         }
      }

      brain.m_21886_(MemoryModuleType.f_26333_, optional);
      brain.m_21879_(MemoryModuleType.f_26346_, list);
   }
}