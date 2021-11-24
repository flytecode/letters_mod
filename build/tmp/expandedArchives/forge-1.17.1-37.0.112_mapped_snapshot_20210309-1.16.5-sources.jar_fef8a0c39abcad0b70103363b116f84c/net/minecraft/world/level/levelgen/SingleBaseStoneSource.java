package net.minecraft.world.level.levelgen;

import net.minecraft.world.level.block.state.BlockState;

public class SingleBaseStoneSource implements BaseStoneSource {
   private final BlockState f_158903_;

   public SingleBaseStoneSource(BlockState p_158905_) {
      this.f_158903_ = p_158905_;
   }

   public BlockState m_142722_(int p_158907_, int p_158908_, int p_158909_) {
      return this.f_158903_;
   }
}