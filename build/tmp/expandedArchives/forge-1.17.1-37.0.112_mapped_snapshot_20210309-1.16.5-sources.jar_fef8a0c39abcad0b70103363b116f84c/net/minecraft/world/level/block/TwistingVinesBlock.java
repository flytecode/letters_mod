package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TwistingVinesBlock extends GrowingPlantHeadBlock {
   public static final VoxelShape f_154861_ = Block.m_49796_(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);

   public TwistingVinesBlock(BlockBehaviour.Properties p_154864_) {
      super(p_154864_, Direction.UP, f_154861_, false, 0.1D);
   }

   protected int m_7363_(Random p_154866_) {
      return NetherVines.m_54965_(p_154866_);
   }

   protected Block m_7777_() {
      return Blocks.f_50653_;
   }

   protected boolean m_5971_(BlockState p_154869_) {
      return NetherVines.m_54963_(p_154869_);
   }
}