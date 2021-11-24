package net.minecraft.world.level.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingRootsBlock extends Block implements SimpleWaterloggedBlock {
   private static final BooleanProperty f_153334_ = BlockStateProperties.f_61362_;
   protected static final VoxelShape f_153333_ = Block.m_49796_(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

   public HangingRootsBlock(BlockBehaviour.Properties p_153337_) {
      super(p_153337_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_153334_, Boolean.valueOf(false)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153358_) {
      p_153358_.m_61104_(f_153334_);
   }

   public FluidState m_5888_(BlockState p_153360_) {
      return p_153360_.m_61143_(f_153334_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_153360_);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_153340_) {
      BlockState blockstate = super.m_5573_(p_153340_);
      if (blockstate != null) {
         FluidState fluidstate = p_153340_.m_43725_().m_6425_(p_153340_.m_8083_());
         return blockstate.m_61124_(f_153334_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
      } else {
         return null;
      }
   }

   public boolean m_7898_(BlockState p_153347_, LevelReader p_153348_, BlockPos p_153349_) {
      BlockPos blockpos = p_153349_.m_7494_();
      BlockState blockstate = p_153348_.m_8055_(blockpos);
      return blockstate.m_60783_(p_153348_, blockpos, Direction.DOWN);
   }

   public VoxelShape m_5940_(BlockState p_153342_, BlockGetter p_153343_, BlockPos p_153344_, CollisionContext p_153345_) {
      return f_153333_;
   }

   public BlockState m_7417_(BlockState p_153351_, Direction p_153352_, BlockState p_153353_, LevelAccessor p_153354_, BlockPos p_153355_, BlockPos p_153356_) {
      if (p_153352_ == Direction.UP && !this.m_7898_(p_153351_, p_153354_, p_153355_)) {
         return Blocks.f_50016_.m_49966_();
      } else {
         if (p_153351_.m_61143_(f_153334_)) {
            p_153354_.m_6217_().m_5945_(p_153355_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_153354_));
         }

         return super.m_7417_(p_153351_, p_153352_, p_153353_, p_153354_, p_153355_, p_153356_);
      }
   }

   public BlockBehaviour.OffsetType m_5858_() {
      return BlockBehaviour.OffsetType.XZ;
   }
}