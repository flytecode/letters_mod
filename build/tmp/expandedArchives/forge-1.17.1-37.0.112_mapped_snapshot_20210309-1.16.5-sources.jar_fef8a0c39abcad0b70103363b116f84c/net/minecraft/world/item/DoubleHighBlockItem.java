package net.minecraft.world.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DoubleHighBlockItem extends BlockItem {
   public DoubleHighBlockItem(Block p_41010_, Item.Properties p_41011_) {
      super(p_41010_, p_41011_);
   }

   protected boolean m_7429_(BlockPlaceContext p_41013_, BlockState p_41014_) {
      Level level = p_41013_.m_43725_();
      BlockPos blockpos = p_41013_.m_8083_().m_7494_();
      BlockState blockstate = level.m_46801_(blockpos) ? Blocks.f_49990_.m_49966_() : Blocks.f_50016_.m_49966_();
      level.m_7731_(blockpos, blockstate, 27);
      return super.m_7429_(p_41013_, p_41014_);
   }
}