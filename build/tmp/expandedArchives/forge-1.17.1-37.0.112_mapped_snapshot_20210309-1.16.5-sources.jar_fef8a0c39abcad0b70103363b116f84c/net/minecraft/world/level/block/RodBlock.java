package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RodBlock extends DirectionalBlock {
   protected static final float f_154332_ = 6.0F;
   protected static final float f_154333_ = 10.0F;
   protected static final VoxelShape f_154334_ = Block.m_49796_(6.0D, 0.0D, 6.0D, 10.0D, 16.0D, 10.0D);
   protected static final VoxelShape f_154335_ = Block.m_49796_(6.0D, 6.0D, 0.0D, 10.0D, 10.0D, 16.0D);
   protected static final VoxelShape f_154336_ = Block.m_49796_(0.0D, 6.0D, 6.0D, 16.0D, 10.0D, 10.0D);

   public RodBlock(BlockBehaviour.Properties p_154339_) {
      super(p_154339_);
   }

   public VoxelShape m_5940_(BlockState p_154346_, BlockGetter p_154347_, BlockPos p_154348_, CollisionContext p_154349_) {
      switch(p_154346_.m_61143_(f_52588_).m_122434_()) {
      case X:
      default:
         return f_154336_;
      case Z:
         return f_154335_;
      case Y:
         return f_154334_;
      }
   }

   public BlockState m_6843_(BlockState p_154354_, Rotation p_154355_) {
      return p_154354_.m_61124_(f_52588_, p_154355_.m_55954_(p_154354_.m_61143_(f_52588_)));
   }

   public BlockState m_6943_(BlockState p_154351_, Mirror p_154352_) {
      return p_154351_.m_61124_(f_52588_, p_154352_.m_54848_(p_154351_.m_61143_(f_52588_)));
   }

   public boolean m_7357_(BlockState p_154341_, BlockGetter p_154342_, BlockPos p_154343_, PathComputationType p_154344_) {
      return false;
   }
}