package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChainBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock {
   public static final BooleanProperty f_51446_ = BlockStateProperties.f_61362_;
   protected static final float f_153033_ = 6.5F;
   protected static final float f_153034_ = 9.5F;
   protected static final VoxelShape f_51447_ = Block.m_49796_(6.5D, 0.0D, 6.5D, 9.5D, 16.0D, 9.5D);
   protected static final VoxelShape f_51448_ = Block.m_49796_(6.5D, 6.5D, 0.0D, 9.5D, 9.5D, 16.0D);
   protected static final VoxelShape f_51449_ = Block.m_49796_(0.0D, 6.5D, 6.5D, 16.0D, 9.5D, 9.5D);

   public ChainBlock(BlockBehaviour.Properties p_51452_) {
      super(p_51452_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_51446_, Boolean.valueOf(false)).m_61124_(f_55923_, Direction.Axis.Y));
   }

   public VoxelShape m_5940_(BlockState p_51470_, BlockGetter p_51471_, BlockPos p_51472_, CollisionContext p_51473_) {
      switch((Direction.Axis)p_51470_.m_61143_(f_55923_)) {
      case X:
      default:
         return f_51449_;
      case Z:
         return f_51448_;
      case Y:
         return f_51447_;
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_51454_) {
      FluidState fluidstate = p_51454_.m_43725_().m_6425_(p_51454_.m_8083_());
      boolean flag = fluidstate.m_76152_() == Fluids.f_76193_;
      return super.m_5573_(p_51454_).m_61124_(f_51446_, Boolean.valueOf(flag));
   }

   public BlockState m_7417_(BlockState p_51461_, Direction p_51462_, BlockState p_51463_, LevelAccessor p_51464_, BlockPos p_51465_, BlockPos p_51466_) {
      if (p_51461_.m_61143_(f_51446_)) {
         p_51464_.m_6217_().m_5945_(p_51465_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_51464_));
      }

      return super.m_7417_(p_51461_, p_51462_, p_51463_, p_51464_, p_51465_, p_51466_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51468_) {
      p_51468_.m_61104_(f_51446_).m_61104_(f_55923_);
   }

   public FluidState m_5888_(BlockState p_51475_) {
      return p_51475_.m_61143_(f_51446_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_51475_);
   }

   public boolean m_7357_(BlockState p_51456_, BlockGetter p_51457_, BlockPos p_51458_, PathComputationType p_51459_) {
      return false;
   }
}