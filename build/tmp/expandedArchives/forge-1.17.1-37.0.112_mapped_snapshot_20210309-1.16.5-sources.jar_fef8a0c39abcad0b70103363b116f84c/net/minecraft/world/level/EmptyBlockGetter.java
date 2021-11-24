package net.minecraft.world.level;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public enum EmptyBlockGetter implements BlockGetter {
   INSTANCE;

   @Nullable
   public BlockEntity m_7702_(BlockPos p_45867_) {
      return null;
   }

   public BlockState m_8055_(BlockPos p_45869_) {
      return Blocks.f_50016_.m_49966_();
   }

   public FluidState m_6425_(BlockPos p_45865_) {
      return Fluids.f_76191_.m_76145_();
   }

   public int m_141937_() {
      return 0;
   }

   public int m_141928_() {
      return 0;
   }
}