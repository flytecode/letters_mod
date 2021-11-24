package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class AirAndWaterRandomPos {
   @Nullable
   public static Vec3 m_148357_(PathfinderMob p_148358_, int p_148359_, int p_148360_, int p_148361_, double p_148362_, double p_148363_, double p_148364_) {
      boolean flag = GoalUtils.m_148442_(p_148358_, p_148359_);
      return RandomPos.m_148542_(p_148358_, () -> {
         return m_148365_(p_148358_, p_148359_, p_148360_, p_148361_, p_148362_, p_148363_, p_148364_, flag);
      });
   }

   @Nullable
   public static BlockPos m_148365_(PathfinderMob p_148366_, int p_148367_, int p_148368_, int p_148369_, double p_148370_, double p_148371_, double p_148372_, boolean p_148373_) {
      BlockPos blockpos = RandomPos.m_148553_(p_148366_.m_21187_(), p_148367_, p_148368_, p_148369_, p_148370_, p_148371_, p_148372_);
      if (blockpos == null) {
         return null;
      } else {
         BlockPos blockpos1 = RandomPos.m_148537_(p_148366_, p_148367_, p_148366_.m_21187_(), blockpos);
         if (!GoalUtils.m_148451_(blockpos1, p_148366_) && !GoalUtils.m_148454_(p_148373_, p_148366_, blockpos1)) {
            blockpos1 = RandomPos.m_148545_(blockpos1, p_148366_.f_19853_.m_151558_(), (p_148376_) -> {
               return GoalUtils.m_148461_(p_148366_, p_148376_);
            });
            return GoalUtils.m_148458_(p_148366_, blockpos1) ? null : blockpos1;
         } else {
            return null;
         }
      }
   }
}