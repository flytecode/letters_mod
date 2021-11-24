package net.minecraft.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TwistingVinesPlantBlock extends GrowingPlantBodyBlock {
   public static final VoxelShape f_154870_ = Block.m_49796_(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

   public TwistingVinesPlantBlock(BlockBehaviour.Properties p_154873_) {
      super(p_154873_, Direction.UP, f_154870_, false);
   }

   protected GrowingPlantHeadBlock m_7272_() {
      return (GrowingPlantHeadBlock)Blocks.f_50704_;
   }
}