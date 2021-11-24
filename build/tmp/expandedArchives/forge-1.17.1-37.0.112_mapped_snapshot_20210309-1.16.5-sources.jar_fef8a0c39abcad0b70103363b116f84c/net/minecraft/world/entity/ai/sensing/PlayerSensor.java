package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;

public class PlayerSensor extends Sensor<LivingEntity> {
   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_26367_, MemoryModuleType.f_26368_, MemoryModuleType.f_148206_);
   }

   protected void m_5578_(ServerLevel p_26740_, LivingEntity p_26741_) {
      List<Player> list = p_26740_.m_6907_().stream().filter(EntitySelector.f_20408_).filter((p_26744_) -> {
         return p_26741_.m_19950_(p_26744_, 16.0D);
      }).sorted(Comparator.comparingDouble(p_26741_::m_20280_)).collect(Collectors.toList());
      Brain<?> brain = p_26741_.m_6274_();
      brain.m_21879_(MemoryModuleType.f_26367_, list);
      List<Player> list1 = list.stream().filter((p_26747_) -> {
         return m_26803_(p_26741_, p_26747_);
      }).collect(Collectors.toList());
      brain.m_21879_(MemoryModuleType.f_26368_, list1.isEmpty() ? null : list1.get(0));
      Optional<Player> optional = list1.stream().filter((p_148304_) -> {
         return m_148312_(p_26741_, p_148304_);
      }).findFirst();
      brain.m_21886_(MemoryModuleType.f_148206_, optional);
   }
}