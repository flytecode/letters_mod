package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class HoverRandomPos {
   @Nullable
   public static Vec3 m_148465_(PathfinderMob p_148466_, int p_148467_, int p_148468_, double p_148469_, double p_148470_, float p_148471_, int p_148472_, int p_148473_) {
      boolean flag = GoalUtils.m_148442_(p_148466_, p_148467_);
      return RandomPos.m_148542_(p_148466_, () -> {
         BlockPos blockpos = RandomPos.m_148553_(p_148466_.m_21187_(), p_148467_, p_148468_, 0, p_148469_, p_148470_, (double)p_148471_);
         if (blockpos == null) {
            return null;
         } else {
            BlockPos blockpos1 = LandRandomPos.m_148513_(p_148466_, p_148467_, flag, blockpos);
            if (blockpos1 == null) {
               return null;
            } else {
               blockpos1 = RandomPos.m_26947_(blockpos1, p_148466_.m_21187_().nextInt(p_148472_ - p_148473_ + 1) + p_148473_, p_148466_.f_19853_.m_151558_(), (p_148486_) -> {
                  return GoalUtils.m_148461_(p_148466_, p_148486_);
               });
               return !GoalUtils.m_148445_(p_148466_, blockpos1) && !GoalUtils.m_148458_(p_148466_, blockpos1) ? blockpos1 : null;
            }
         }
      });
   }
}