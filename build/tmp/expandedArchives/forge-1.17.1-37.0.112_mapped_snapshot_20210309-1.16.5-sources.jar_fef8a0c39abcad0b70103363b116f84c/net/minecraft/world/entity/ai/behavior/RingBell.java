package net.minecraft.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.BellBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class RingBell extends Behavior<LivingEntity> {
   private static final float f_147863_ = 0.95F;
   public static final int f_147862_ = 3;

   public RingBell() {
      super(ImmutableMap.of(MemoryModuleType.f_26362_, MemoryStatus.VALUE_PRESENT));
   }

   protected boolean m_6114_(ServerLevel p_23789_, LivingEntity p_23790_) {
      return p_23789_.f_46441_.nextFloat() > 0.95F;
   }

   protected void m_6735_(ServerLevel p_23792_, LivingEntity p_23793_, long p_23794_) {
      Brain<?> brain = p_23793_.m_6274_();
      BlockPos blockpos = brain.m_21952_(MemoryModuleType.f_26362_).get().m_122646_();
      if (blockpos.m_123314_(p_23793_.m_142538_(), 3.0D)) {
         BlockState blockstate = p_23792_.m_8055_(blockpos);
         if (blockstate.m_60713_(Blocks.f_50680_)) {
            BellBlock bellblock = (BellBlock)blockstate.m_60734_();
            bellblock.m_152188_(p_23793_, p_23792_, blockpos, (Direction)null);
         }
      }

   }
}