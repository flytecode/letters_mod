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
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LanternBlock extends Block implements SimpleWaterloggedBlock {
   public static final BooleanProperty f_153459_ = BlockStateProperties.f_61435_;
   public static final BooleanProperty f_153460_ = BlockStateProperties.f_61362_;
   protected static final VoxelShape f_153461_ = Shapes.m_83110_(Block.m_49796_(5.0D, 0.0D, 5.0D, 11.0D, 7.0D, 11.0D), Block.m_49796_(6.0D, 7.0D, 6.0D, 10.0D, 9.0D, 10.0D));
   protected static final VoxelShape f_153462_ = Shapes.m_83110_(Block.m_49796_(5.0D, 1.0D, 5.0D, 11.0D, 8.0D, 11.0D), Block.m_49796_(6.0D, 8.0D, 6.0D, 10.0D, 10.0D, 10.0D));

   public LanternBlock(BlockBehaviour.Properties p_153465_) {
      super(p_153465_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_153459_, Boolean.valueOf(false)).m_61124_(f_153460_, Boolean.valueOf(false)));
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_153467_) {
      FluidState fluidstate = p_153467_.m_43725_().m_6425_(p_153467_.m_8083_());

      for(Direction direction : p_153467_.m_6232_()) {
         if (direction.m_122434_() == Direction.Axis.Y) {
            BlockState blockstate = this.m_49966_().m_61124_(f_153459_, Boolean.valueOf(direction == Direction.UP));
            if (blockstate.m_60710_(p_153467_.m_43725_(), p_153467_.m_8083_())) {
               return blockstate.m_61124_(f_153460_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
            }
         }
      }

      return null;
   }

   public VoxelShape m_5940_(BlockState p_153474_, BlockGetter p_153475_, BlockPos p_153476_, CollisionContext p_153477_) {
      return p_153474_.m_61143_(f_153459_) ? f_153462_ : f_153461_;
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_153490_) {
      p_153490_.m_61104_(f_153459_, f_153460_);
   }

   public boolean m_7898_(BlockState p_153479_, LevelReader p_153480_, BlockPos p_153481_) {
      Direction direction = m_153495_(p_153479_).m_122424_();
      return Block.m_49863_(p_153480_, p_153481_.m_142300_(direction), direction.m_122424_());
   }

   protected static Direction m_153495_(BlockState p_153496_) {
      return p_153496_.m_61143_(f_153459_) ? Direction.DOWN : Direction.UP;
   }

   public PushReaction m_5537_(BlockState p_153494_) {
      return PushReaction.DESTROY;
   }

   public BlockState m_7417_(BlockState p_153483_, Direction p_153484_, BlockState p_153485_, LevelAccessor p_153486_, BlockPos p_153487_, BlockPos p_153488_) {
      if (p_153483_.m_61143_(f_153460_)) {
         p_153486_.m_6217_().m_5945_(p_153487_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_153486_));
      }

      return m_153495_(p_153483_).m_122424_() == p_153484_ && !p_153483_.m_60710_(p_153486_, p_153487_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_153483_, p_153484_, p_153485_, p_153486_, p_153487_, p_153488_);
   }

   public FluidState m_5888_(BlockState p_153492_) {
      return p_153492_.m_61143_(f_153460_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_153492_);
   }

   public boolean m_7357_(BlockState p_153469_, BlockGetter p_153470_, BlockPos p_153471_, PathComputationType p_153472_) {
      return false;
   }
}