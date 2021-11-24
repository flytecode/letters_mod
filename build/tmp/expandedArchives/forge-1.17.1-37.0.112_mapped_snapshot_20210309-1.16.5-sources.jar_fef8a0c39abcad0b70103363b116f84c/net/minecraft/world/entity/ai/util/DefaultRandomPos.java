package net.minecraft.world.entity.ai.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class DefaultRandomPos {
   @Nullable
   public static Vec3 m_148403_(PathfinderMob p_148404_, int p_148405_, int p_148406_) {
      boolean flag = GoalUtils.m_148442_(p_148404_, p_148405_);
      return RandomPos.m_148542_(p_148404_, () -> {
         BlockPos blockpos = RandomPos.m_148549_(p_148404_.m_21187_(), p_148405_, p_148406_);
         return m_148436_(p_148404_, p_148405_, flag, blockpos);
      });
   }

   @Nullable
   public static Vec3 m_148412_(PathfinderMob p_148413_, int p_148414_, int p_148415_, Vec3 p_148416_, double p_148417_) {
      Vec3 vec3 = p_148416_.m_82492_(p_148413_.m_20185_(), p_148413_.m_20186_(), p_148413_.m_20189_());
      boolean flag = GoalUtils.m_148442_(p_148413_, p_148414_);
      return RandomPos.m_148542_(p_148413_, () -> {
         BlockPos blockpos = RandomPos.m_148553_(p_148413_.m_21187_(), p_148414_, p_148415_, 0, vec3.f_82479_, vec3.f_82481_, p_148417_);
         return blockpos == null ? null : m_148436_(p_148413_, p_148414_, flag, blockpos);
      });
   }

   @Nullable
   public static Vec3 m_148407_(PathfinderMob p_148408_, int p_148409_, int p_148410_, Vec3 p_148411_) {
      Vec3 vec3 = p_148408_.m_20182_().m_82546_(p_148411_);
      boolean flag = GoalUtils.m_148442_(p_148408_, p_148409_);
      return RandomPos.m_148542_(p_148408_, () -> {
         BlockPos blockpos = RandomPos.m_148553_(p_148408_.m_21187_(), p_148409_, p_148410_, 0, vec3.f_82479_, vec3.f_82481_, (double)((float)Math.PI / 2F));
         return blockpos == null ? null : m_148436_(p_148408_, p_148409_, flag, blockpos);
      });
   }

   @Nullable
   private static BlockPos m_148436_(PathfinderMob p_148437_, int p_148438_, boolean p_148439_, BlockPos p_148440_) {
      BlockPos blockpos = RandomPos.m_148537_(p_148437_, p_148438_, p_148437_.m_21187_(), p_148440_);
      return !GoalUtils.m_148451_(blockpos, p_148437_) && !GoalUtils.m_148454_(p_148439_, p_148437_, blockpos) && !GoalUtils.m_148448_(p_148437_.m_21573_(), blockpos) && !GoalUtils.m_148458_(p_148437_, blockpos) ? blockpos : null;
   }
}