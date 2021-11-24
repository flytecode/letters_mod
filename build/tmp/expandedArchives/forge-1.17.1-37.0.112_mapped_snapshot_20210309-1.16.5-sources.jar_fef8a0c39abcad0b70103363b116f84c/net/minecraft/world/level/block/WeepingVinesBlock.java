package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WeepingVinesBlock extends GrowingPlantHeadBlock {
   protected static final VoxelShape f_154963_ = Block.m_49796_(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

   public WeepingVinesBlock(BlockBehaviour.Properties p_154966_) {
      super(p_154966_, Direction.DOWN, f_154963_, false, 0.1D);
   }

   protected int m_7363_(Random p_154968_) {
      return NetherVines.m_54965_(p_154968_);
   }

   protected Block m_7777_() {
      return Blocks.f_50703_;
   }

   protected boolean m_5971_(BlockState p_154971_) {
      return NetherVines.m_54963_(p_154971_);
   }
}