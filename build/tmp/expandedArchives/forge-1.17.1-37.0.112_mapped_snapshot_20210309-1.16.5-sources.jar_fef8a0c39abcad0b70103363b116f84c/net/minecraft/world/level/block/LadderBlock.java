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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LadderBlock extends Block implements SimpleWaterloggedBlock {
   public static final DirectionProperty f_54337_ = HorizontalDirectionalBlock.f_54117_;
   public static final BooleanProperty f_54338_ = BlockStateProperties.f_61362_;
   protected static final float f_153458_ = 3.0F;
   protected static final VoxelShape f_54339_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_54340_ = Block.m_49796_(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
   protected static final VoxelShape f_54341_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
   protected static final VoxelShape f_54342_ = Block.m_49796_(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);

   public LadderBlock(BlockBehaviour.Properties p_54345_) {
      super(p_54345_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54337_, Direction.NORTH).m_61124_(f_54338_, Boolean.valueOf(false)));
   }

   public VoxelShape m_5940_(BlockState p_54372_, BlockGetter p_54373_, BlockPos p_54374_, CollisionContext p_54375_) {
      switch((Direction)p_54372_.m_61143_(f_54337_)) {
      case NORTH:
         return f_54342_;
      case SOUTH:
         return f_54341_;
      case WEST:
         return f_54340_;
      case EAST:
      default:
         return f_54339_;
      }
   }

   private boolean m_54348_(BlockGetter p_54349_, BlockPos p_54350_, Direction p_54351_) {
      BlockState blockstate = p_54349_.m_8055_(p_54350_);
      return blockstate.m_60783_(p_54349_, p_54350_, p_54351_);
   }

   public boolean m_7898_(BlockState p_54353_, LevelReader p_54354_, BlockPos p_54355_) {
      Direction direction = p_54353_.m_61143_(f_54337_);
      return this.m_54348_(p_54354_, p_54355_.m_142300_(direction.m_122424_()), direction);
   }

   public BlockState m_7417_(BlockState p_54363_, Direction p_54364_, BlockState p_54365_, LevelAccessor p_54366_, BlockPos p_54367_, BlockPos p_54368_) {
      if (p_54364_.m_122424_() == p_54363_.m_61143_(f_54337_) && !p_54363_.m_60710_(p_54366_, p_54367_)) {
         return Blocks.f_50016_.m_49966_();
      } else {
         if (p_54363_.m_61143_(f_54338_)) {
            p_54366_.m_6217_().m_5945_(p_54367_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_54366_));
         }

         return super.m_7417_(p_54363_, p_54364_, p_54365_, p_54366_, p_54367_, p_54368_);
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_54347_) {
      if (!p_54347_.m_7058_()) {
         BlockState blockstate = p_54347_.m_43725_().m_8055_(p_54347_.m_8083_().m_142300_(p_54347_.m_43719_().m_122424_()));
         if (blockstate.m_60713_(this) && blockstate.m_61143_(f_54337_) == p_54347_.m_43719_()) {
            return null;
         }
      }

      BlockState blockstate1 = this.m_49966_();
      LevelReader levelreader = p_54347_.m_43725_();
      BlockPos blockpos = p_54347_.m_8083_();
      FluidState fluidstate = p_54347_.m_43725_().m_6425_(p_54347_.m_8083_());

      for(Direction direction : p_54347_.m_6232_()) {
         if (direction.m_122434_().m_122479_()) {
            blockstate1 = blockstate1.m_61124_(f_54337_, direction.m_122424_());
            if (blockstate1.m_60710_(levelreader, blockpos)) {
               return blockstate1.m_61124_(f_54338_, Boolean.valueOf(fluidstate.m_76152_() == Fluids.f_76193_));
            }
         }
      }

      return null;
   }

   public BlockState m_6843_(BlockState p_54360_, Rotation p_54361_) {
      return p_54360_.m_61124_(f_54337_, p_54361_.m_55954_(p_54360_.m_61143_(f_54337_)));
   }

   public BlockState m_6943_(BlockState p_54357_, Mirror p_54358_) {
      return p_54357_.m_60717_(p_54358_.m_54846_(p_54357_.m_61143_(f_54337_)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54370_) {
      p_54370_.m_61104_(f_54337_, f_54338_);
   }

   public FluidState m_5888_(BlockState p_54377_) {
      return p_54377_.m_61143_(f_54338_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_54377_);
   }
}