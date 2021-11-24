package net.minecraft.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;

public class SecondaryPoiSensor extends Sensor<Villager> {
   private static final int f_148305_ = 40;

   public SecondaryPoiSensor() {
      super(40);
   }

   protected void m_5578_(ServerLevel p_26754_, Villager p_26755_) {
      ResourceKey<Level> resourcekey = p_26754_.m_46472_();
      BlockPos blockpos = p_26755_.m_142538_();
      List<GlobalPos> list = Lists.newArrayList();
      int i = 4;

      for(int j = -4; j <= 4; ++j) {
         for(int k = -2; k <= 2; ++k) {
            for(int l = -4; l <= 4; ++l) {
               BlockPos blockpos1 = blockpos.m_142082_(j, k, l);
               if (p_26755_.m_7141_().m_35571_().m_35624_().contains(p_26754_.m_8055_(blockpos1).m_60734_())) {
                  list.add(GlobalPos.m_122643_(resourcekey, blockpos1));
               }
            }
         }
      }

      Brain<?> brain = p_26755_.m_6274_();
      if (!list.isEmpty()) {
         brain.m_21879_(MemoryModuleType.f_26363_, list);
      } else {
         brain.m_21936_(MemoryModuleType.f_26363_);
      }

   }

   public Set<MemoryModuleType<?>> m_7163_() {
      return ImmutableSet.of(MemoryModuleType.f_26363_);
   }
}