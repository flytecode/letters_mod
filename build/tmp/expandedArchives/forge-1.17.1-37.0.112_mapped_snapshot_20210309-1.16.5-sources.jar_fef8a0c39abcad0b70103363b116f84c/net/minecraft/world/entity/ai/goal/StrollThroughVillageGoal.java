package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class StrollThroughVillageGoal extends Goal {
   private static final int f_148136_ = 10;
   private final PathfinderMob f_25903_;
   private final int f_25904_;
   @Nullable
   private BlockPos f_25905_;

   public StrollThroughVillageGoal(PathfinderMob p_25907_, int p_25908_) {
      this.f_25903_ = p_25907_;
      this.f_25904_ = p_25908_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.f_25903_.m_20160_()) {
         return false;
      } else if (this.f_25903_.f_19853_.m_46461_()) {
         return false;
      } else if (this.f_25903_.m_21187_().nextInt(this.f_25904_) != 0) {
         return false;
      } else {
         ServerLevel serverlevel = (ServerLevel)this.f_25903_.f_19853_;
         BlockPos blockpos = this.f_25903_.m_142538_();
         if (!serverlevel.m_8736_(blockpos, 6)) {
            return false;
         } else {
            Vec3 vec3 = LandRandomPos.m_148503_(this.f_25903_, 15, 7, (p_25912_) -> {
               return (double)(-serverlevel.m_8828_(SectionPos.m_123199_(p_25912_)));
            });
            this.f_25905_ = vec3 == null ? null : new BlockPos(vec3);
            return this.f_25905_ != null;
         }
      }
   }

   public boolean m_8045_() {
      return this.f_25905_ != null && !this.f_25903_.m_21573_().m_26571_() && this.f_25903_.m_21573_().m_26567_().equals(this.f_25905_);
   }

   public void m_8037_() {
      if (this.f_25905_ != null) {
         PathNavigation pathnavigation = this.f_25903_.m_21573_();
         if (pathnavigation.m_26571_() && !this.f_25905_.m_123306_(this.f_25903_.m_20182_(), 10.0D)) {
            Vec3 vec3 = Vec3.m_82539_(this.f_25905_);
            Vec3 vec31 = this.f_25903_.m_20182_();
            Vec3 vec32 = vec31.m_82546_(vec3);
            vec3 = vec32.m_82490_(0.4D).m_82549_(vec3);
            Vec3 vec33 = vec3.m_82546_(vec31).m_82541_().m_82490_(10.0D).m_82549_(vec31);
            BlockPos blockpos = new BlockPos(vec33);
            blockpos = this.f_25903_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockpos);
            if (!pathnavigation.m_26519_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_(), 1.0D)) {
               this.m_25915_();
            }
         }

      }
   }

   private void m_25915_() {
      Random random = this.f_25903_.m_21187_();
      BlockPos blockpos = this.f_25903_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, this.f_25903_.m_142538_().m_142082_(-8 + random.nextInt(16), 0, -8 + random.nextInt(16)));
      this.f_25903_.m_21573_().m_26519_((double)blockpos.m_123341_(), (double)blockpos.m_123342_(), (double)blockpos.m_123343_(), 1.0D);
   }
}