package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RootsBlock extends BushBlock {
   protected static final float f_154375_ = 6.0F;
   protected static final VoxelShape f_55909_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

   public RootsBlock(BlockBehaviour.Properties p_55912_) {
      super(p_55912_);
   }

   public VoxelShape m_5940_(BlockState p_55915_, BlockGetter p_55916_, BlockPos p_55917_, CollisionContext p_55918_) {
      return f_55909_;
   }

   protected boolean m_6266_(BlockState p_55920_, BlockGetter p_55921_, BlockPos p_55922_) {
      return p_55920_.m_60620_(BlockTags.f_13077_) || p_55920_.m_60713_(Blocks.f_50136_) || super.m_6266_(p_55920_, p_55921_, p_55922_);
   }

   public BlockBehaviour.OffsetType m_5858_() {
      return BlockBehaviour.OffsetType.XZ;
   }
}