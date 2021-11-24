package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TryFindWater extends Behavior<PathfinderMob> {
   private final int f_147998_;
   private final float f_147999_;
   private long f_148000_;

   public TryFindWater(int p_148002_, float p_148003_) {
      super(ImmutableMap.of(MemoryModuleType.f_26372_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26370_, MemoryStatus.VALUE_ABSENT, MemoryModuleType.f_26371_, MemoryStatus.REGISTERED));
      this.f_147998_ = p_148002_;
      this.f_147999_ = p_148003_;
   }

   protected void m_6732_(ServerLevel p_148015_, PathfinderMob p_148016_, long p_148017_) {
      this.f_148000_ = p_148017_ + 20L + 2L;
   }

   protected boolean m_6114_(ServerLevel p_148012_, PathfinderMob p_148013_) {
      return !p_148013_.f_19853_.m_6425_(p_148013_.m_142538_()).m_76153_(FluidTags.f_13131_);
   }

   protected void m_6735_(ServerLevel p_148019_, PathfinderMob p_148020_, long p_148021_) {
      if (p_148021_ >= this.f_148000_) {
         BlockPos blockpos = null;
         BlockPos blockpos1 = null;
         BlockPos blockpos2 = p_148020_.m_142538_();

         for(BlockPos blockpos3 : BlockPos.m_121925_(blockpos2, this.f_147998_, this.f_147998_, this.f_147998_)) {
            if (blockpos3.m_123341_() != blockpos2.m_123341_() || blockpos3.m_123343_() != blockpos2.m_123343_()) {
               BlockState blockstate = p_148020_.f_19853_.m_8055_(blockpos3.m_7494_());
               BlockState blockstate1 = p_148020_.f_19853_.m_8055_(blockpos3);
               if (blockstate1.m_60713_(Blocks.f_49990_)) {
                  if (blockstate.m_60795_()) {
                     blockpos = blockpos3.m_7949_();
                     break;
                  }

                  if (blockpos1 == null && !blockpos3.m_123306_(p_148020_.m_20182_(), 1.5D)) {
                     blockpos1 = blockpos3.m_7949_();
                  }
               }
            }
         }

         if (blockpos == null) {
            blockpos = blockpos1;
         }

         if (blockpos != null) {
            this.f_148000_ = p_148021_ + 40L;
            BehaviorUtils.m_22617_(p_148020_, blockpos, this.f_147999_, 0);
         }

      }
   }
}