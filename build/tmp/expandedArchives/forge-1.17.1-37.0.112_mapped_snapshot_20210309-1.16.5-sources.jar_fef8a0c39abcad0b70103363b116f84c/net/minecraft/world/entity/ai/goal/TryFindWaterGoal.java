package net.minecraft.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;

public class TryFindWaterGoal extends Goal {
   private final PathfinderMob f_25962_;

   public TryFindWaterGoal(PathfinderMob p_25964_) {
      this.f_25962_ = p_25964_;
   }

   public boolean m_8036_() {
      return this.f_25962_.m_20096_() && !this.f_25962_.f_19853_.m_6425_(this.f_25962_.m_142538_()).m_76153_(FluidTags.f_13131_);
   }

   public void m_8056_() {
      BlockPos blockpos = null;

      for(BlockPos blockpos1 : BlockPos.m_121976_(Mth.m_14107_(this.f_25962_.m_20185_() - 2.0D), Mth.m_14107_(this.f_25962_.m_20186_() - 2.0D), Mth.m_14107_(this.f_25962_.m_20189_() - 2.0D), Mth.m_14107_(this.f_25962_.m_20185_() + 2.0D), this.f_25962_.m_146904_(), Mth.m_14107_(this.f_25962_.m_20189_() + 2.0D))) {
         if (this.f_25962_.f_19853_.m_6425_(blockpos1).m_76153_(FluidTags.f_13131_)) {
            blockpos = blockpos1;
            break;
         }
      }

      if (blockpos != null) {
         this.f_25962_.m_21566_().m_6849_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_(), 1.0D);
      }

   }
}