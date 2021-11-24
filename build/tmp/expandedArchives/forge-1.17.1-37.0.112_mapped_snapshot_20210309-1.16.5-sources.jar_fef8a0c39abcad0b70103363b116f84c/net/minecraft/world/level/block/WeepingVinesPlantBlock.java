package net.minecraft.world.level.block;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WeepingVinesPlantBlock extends GrowingPlantBodyBlock {
   public static final VoxelShape f_154972_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

   public WeepingVinesPlantBlock(BlockBehaviour.Properties p_154975_) {
      super(p_154975_, Direction.DOWN, f_154972_, false);
   }

   protected GrowingPlantHeadBlock m_7272_() {
      return (GrowingPlantHeadBlock)Blocks.f_50702_;
   }
}