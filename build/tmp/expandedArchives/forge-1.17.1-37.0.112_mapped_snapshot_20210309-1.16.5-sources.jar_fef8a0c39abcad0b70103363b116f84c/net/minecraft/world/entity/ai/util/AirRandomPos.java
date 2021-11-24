package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class AirRandomPos {
   @Nullable
   public static Vec3 m_148387_(PathfinderMob p_148388_, int p_148389_, int p_148390_, int p_148391_, Vec3 p_148392_, double p_148393_) {
      Vec3 vec3 = p_148392_.m_82492_(p_148388_.m_20185_(), p_148388_.m_20186_(), p_148388_.m_20189_());
      boolean flag = GoalUtils.m_148442_(p_148388_, p_148389_);
      return RandomPos.m_148542_(p_148388_, () -> {
         BlockPos blockpos = AirAndWaterRandomPos.m_148365_(p_148388_, p_148389_, p_148390_, p_148391_, vec3.f_82479_, vec3.f_82481_, p_148393_, flag);
         return blockpos != null && !GoalUtils.m_148445_(p_148388_, blockpos) ? blockpos : null;
      });
   }
}