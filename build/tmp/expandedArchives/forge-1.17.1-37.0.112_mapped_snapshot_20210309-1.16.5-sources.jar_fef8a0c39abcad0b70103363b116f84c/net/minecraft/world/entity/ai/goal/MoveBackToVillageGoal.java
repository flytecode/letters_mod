package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class MoveBackToVillageGoal extends RandomStrollGoal {
   private static final int f_148126_ = 10;
   private static final int f_148127_ = 7;

   public MoveBackToVillageGoal(PathfinderMob p_25568_, double p_25569_, boolean p_25570_) {
      super(p_25568_, p_25569_, 10, p_25570_);
   }

   public boolean m_8036_() {
      ServerLevel serverlevel = (ServerLevel)this.f_25725_.f_19853_;
      BlockPos blockpos = this.f_25725_.m_142538_();
      return serverlevel.m_8802_(blockpos) ? false : super.m_8036_();
   }

   @Nullable
   protected Vec3 m_7037_() {
      ServerLevel serverlevel = (ServerLevel)this.f_25725_.f_19853_;
      BlockPos blockpos = this.f_25725_.m_142538_();
      SectionPos sectionpos = SectionPos.m_123199_(blockpos);
      SectionPos sectionpos1 = BehaviorUtils.m_22581_(serverlevel, sectionpos, 2);
      return sectionpos1 != sectionpos ? DefaultRandomPos.m_148412_(this.f_25725_, 10, 7, Vec3.m_82539_(sectionpos1.m_123250_()), (double)((float)Math.PI / 2F)) : null;
   }
}