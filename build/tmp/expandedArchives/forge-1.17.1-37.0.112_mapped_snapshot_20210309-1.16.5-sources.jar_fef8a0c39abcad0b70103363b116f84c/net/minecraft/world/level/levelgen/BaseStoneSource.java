package net.minecraft.world.level.levelgen;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

@FunctionalInterface
public interface BaseStoneSource {
   default BlockState m_158054_(BlockPos p_158055_) {
      return this.m_142722_(p_158055_.m_123341_(), p_158055_.m_123342_(), p_158055_.m_123343_());
   }

   BlockState m_142722_(int p_158056_, int p_158057_, int p_158058_);
}