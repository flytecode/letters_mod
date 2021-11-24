package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class WaterAvoidingRandomFlyingGoal extends WaterAvoidingRandomStrollGoal {
   public WaterAvoidingRandomFlyingGoal(PathfinderMob p_25981_, double p_25982_) {
      super(p_25981_, p_25982_);
   }

   @Nullable
   protected Vec3 m_7037_() {
      Vec3 vec3 = null;
      if (this.f_25725_.m_20069_()) {
         vec3 = LandRandomPos.m_148488_(this.f_25725_, 15, 15);
      }

      if (this.f_25725_.m_21187_().nextFloat() >= this.f_25985_) {
         vec3 = this.m_25984_();
      }

      return vec3 == null ? super.m_7037_() : vec3;
   }

   @Nullable
   private Vec3 m_25984_() {
      BlockPos blockpos = this.f_25725_.m_142538_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

      for(BlockPos blockpos1 : BlockPos.m_121976_(Mth.m_14107_(this.f_25725_.m_20185_() - 3.0D), Mth.m_14107_(this.f_25725_.m_20186_() - 6.0D), Mth.m_14107_(this.f_25725_.m_20189_() - 3.0D), Mth.m_14107_(this.f_25725_.m_20185_() + 3.0D), Mth.m_14107_(this.f_25725_.m_20186_() + 6.0D), Mth.m_14107_(this.f_25725_.m_20189_() + 3.0D))) {
         if (!blockpos.equals(blockpos1)) {
            BlockState blockstate = this.f_25725_.f_19853_.m_8055_(blockpos$mutableblockpos1.m_122159_(blockpos1, Direction.DOWN));
            boolean flag = blockstate.m_60734_() instanceof LeavesBlock || blockstate.m_60620_(BlockTags.f_13106_);
            if (flag && this.f_25725_.f_19853_.m_46859_(blockpos1) && this.f_25725_.f_19853_.m_46859_(blockpos$mutableblockpos.m_122159_(blockpos1, Direction.UP))) {
               return Vec3.m_82539_(blockpos1);
            }
         }
      }

      return null;
   }
}