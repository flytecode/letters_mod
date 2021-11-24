package net.minecraft.world.level;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class NoiseColumn {
   private final int f_151621_;
   private final BlockState[] f_47149_;

   public NoiseColumn(int p_151623_, BlockState[] p_151624_) {
      this.f_151621_ = p_151623_;
      this.f_47149_ = p_151624_;
   }

   public BlockState m_47156_(BlockPos p_47157_) {
      int i = p_47157_.m_123342_() - this.f_151621_;
      return i >= 0 && i < this.f_47149_.length ? this.f_47149_[i] : Blocks.f_50016_.m_49966_();
   }
}