package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.monster.piglin.Piglin;

public class HoglinSpecificSensor extends Sensor<Hoglin> {
   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_148205_, MemoryModuleType.f_26356_, MemoryModuleType.f_26350_, MemoryModuleType.f_26348_, MemoryModuleType.f_26352_, MemoryModuleType.f_26353_);
   }

   protected void m_5578_(ServerLevel p_26659_, Hoglin p_26660_) {
      Brain<?> brain = p_26660_.m_6274_();
      brain.m_21886_(MemoryModuleType.f_26356_, this.m_26664_(p_26659_, p_26660_));
      Optional<Piglin> optional = Optional.empty();
      int i = 0;
      List<Hoglin> list = Lists.newArrayList();

      for(LivingEntity livingentity : brain.m_21952_(MemoryModuleType.f_148205_).orElse(Lists.newArrayList())) {
         if (livingentity instanceof Piglin && !livingentity.m_6162_()) {
            ++i;
            if (!optional.isPresent()) {
               optional = Optional.of((Piglin)livingentity);
            }
         }

         if (livingentity instanceof Hoglin && !livingentity.m_6162_()) {
            list.add((Hoglin)livingentity);
         }
      }

      brain.m_21886_(MemoryModuleType.f_26350_, optional);
      brain.m_21879_(MemoryModuleType.f_26348_, list);
      brain.m_21879_(MemoryModuleType.f_26352_, i);
      brain.m_21879_(MemoryModuleType.f_26353_, list.size());
   }

   private Optional<BlockPos> m_26664_(ServerLevel p_26665_, Hoglin p_26666_) {
      return BlockPos.m_121930_(p_26666_.m_142538_(), 8, 4, (p_26663_) -> {
         return p_26665_.m_8055_(p_26663_).m_60620_(BlockTags.f_13084_);
      });
   }
}