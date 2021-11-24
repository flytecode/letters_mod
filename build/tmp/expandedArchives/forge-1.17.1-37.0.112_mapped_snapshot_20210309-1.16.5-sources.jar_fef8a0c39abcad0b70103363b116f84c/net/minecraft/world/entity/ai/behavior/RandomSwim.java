package net.minecraft.world.entity.ai.behavior;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.phys.Vec3;

public class RandomSwim extends RandomStroll {
   public static final int[][] f_182355_ = new int[][]{{1, 1}, {3, 3}, {5, 5}, {6, 5}, {7, 7}, {10, 7}};

   public RandomSwim(float p_147853_) {
      super(p_147853_);
   }

   protected boolean m_6114_(ServerLevel p_147858_, PathfinderMob p_147859_) {
      return p_147859_.m_20072_();
   }

   @Nullable
   protected Vec3 m_142622_(PathfinderMob p_147861_) {
      Vec3 vec3 = null;
      Vec3 vec31 = null;

      for(int[] aint : f_182355_) {
         if (vec3 == null) {
            vec31 = BehaviorUtils.m_147444_(p_147861_, aint[0], aint[1]);
         } else {
            vec31 = p_147861_.m_20182_().m_82549_(p_147861_.m_20182_().m_82505_(vec3).m_82541_().m_82542_((double)aint[0], (double)aint[1], (double)aint[0]));
         }

         if (vec31 == null || p_147861_.f_19853_.m_6425_(new BlockPos(vec31)).m_76178_()) {
            return vec3;
         }

         vec3 = vec31;
      }

      return vec31;
   }
}