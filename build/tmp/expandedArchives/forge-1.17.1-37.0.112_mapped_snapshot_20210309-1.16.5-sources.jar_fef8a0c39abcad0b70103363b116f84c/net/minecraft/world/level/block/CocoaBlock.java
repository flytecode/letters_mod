package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CocoaBlock extends HorizontalDirectionalBlock implements BonemealableBlock {
   public static final int f_153068_ = 2;
   public static final IntegerProperty f_51736_ = BlockStateProperties.f_61406_;
   protected static final int f_153069_ = 4;
   protected static final int f_153070_ = 5;
   protected static final int f_153071_ = 2;
   protected static final int f_153072_ = 6;
   protected static final int f_153073_ = 7;
   protected static final int f_153074_ = 3;
   protected static final int f_153075_ = 8;
   protected static final int f_153076_ = 9;
   protected static final int f_153077_ = 4;
   protected static final VoxelShape[] f_51737_ = new VoxelShape[]{Block.m_49796_(11.0D, 7.0D, 6.0D, 15.0D, 12.0D, 10.0D), Block.m_49796_(9.0D, 5.0D, 5.0D, 15.0D, 12.0D, 11.0D), Block.m_49796_(7.0D, 3.0D, 4.0D, 15.0D, 12.0D, 12.0D)};
   protected static final VoxelShape[] f_51738_ = new VoxelShape[]{Block.m_49796_(1.0D, 7.0D, 6.0D, 5.0D, 12.0D, 10.0D), Block.m_49796_(1.0D, 5.0D, 5.0D, 7.0D, 12.0D, 11.0D), Block.m_49796_(1.0D, 3.0D, 4.0D, 9.0D, 12.0D, 12.0D)};
   protected static final VoxelShape[] f_51739_ = new VoxelShape[]{Block.m_49796_(6.0D, 7.0D, 1.0D, 10.0D, 12.0D, 5.0D), Block.m_49796_(5.0D, 5.0D, 1.0D, 11.0D, 12.0D, 7.0D), Block.m_49796_(4.0D, 3.0D, 1.0D, 12.0D, 12.0D, 9.0D)};
   protected static final VoxelShape[] f_51740_ = new VoxelShape[]{Block.m_49796_(6.0D, 7.0D, 11.0D, 10.0D, 12.0D, 15.0D), Block.m_49796_(5.0D, 5.0D, 9.0D, 11.0D, 12.0D, 15.0D), Block.m_49796_(4.0D, 3.0D, 7.0D, 12.0D, 12.0D, 15.0D)};

   public CocoaBlock(BlockBehaviour.Properties p_51743_) {
      super(p_51743_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54117_, Direction.NORTH).m_61124_(f_51736_, Integer.valueOf(0)));
   }

   public boolean m_6724_(BlockState p_51780_) {
      return p_51780_.m_61143_(f_51736_) < 2;
   }

   public void m_7455_(BlockState p_51782_, ServerLevel p_51783_, BlockPos p_51784_, Random p_51785_) {
      if (true) {
         int i = p_51782_.m_61143_(f_51736_);
         if (i < 2 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_51783_, p_51784_, p_51782_, p_51783_.f_46441_.nextInt(5) == 0)) {
            p_51783_.m_7731_(p_51784_, p_51782_.m_61124_(f_51736_, Integer.valueOf(i + 1)), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_51783_, p_51784_, p_51782_);
         }
      }

   }

   public boolean m_7898_(BlockState p_51767_, LevelReader p_51768_, BlockPos p_51769_) {
      BlockState blockstate = p_51768_.m_8055_(p_51769_.m_142300_(p_51767_.m_61143_(f_54117_)));
      return blockstate.m_60620_(BlockTags.f_13111_);
   }

   public VoxelShape m_5940_(BlockState p_51787_, BlockGetter p_51788_, BlockPos p_51789_, CollisionContext p_51790_) {
      int i = p_51787_.m_61143_(f_51736_);
      switch((Direction)p_51787_.m_61143_(f_54117_)) {
      case SOUTH:
         return f_51740_[i];
      case NORTH:
      default:
         return f_51739_[i];
      case WEST:
         return f_51738_[i];
      case EAST:
         return f_51737_[i];
      }
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_51750_) {
      BlockState blockstate = this.m_49966_();
      LevelReader levelreader = p_51750_.m_43725_();
      BlockPos blockpos = p_51750_.m_8083_();

      for(Direction direction : p_51750_.m_6232_()) {
         if (direction.m_122434_().m_122479_()) {
            blockstate = blockstate.m_61124_(f_54117_, direction);
            if (blockstate.m_60710_(levelreader, blockpos)) {
               return blockstate;
            }
         }
      }

      return null;
   }

   public BlockState m_7417_(BlockState p_51771_, Direction p_51772_, BlockState p_51773_, LevelAccessor p_51774_, BlockPos p_51775_, BlockPos p_51776_) {
      return p_51772_ == p_51771_.m_61143_(f_54117_) && !p_51771_.m_60710_(p_51774_, p_51775_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_51771_, p_51772_, p_51773_, p_51774_, p_51775_, p_51776_);
   }

   public boolean m_7370_(BlockGetter p_51752_, BlockPos p_51753_, BlockState p_51754_, boolean p_51755_) {
      return p_51754_.m_61143_(f_51736_) < 2;
   }

   public boolean m_5491_(Level p_51757_, Random p_51758_, BlockPos p_51759_, BlockState p_51760_) {
      return true;
   }

   public void m_7719_(ServerLevel p_51745_, Random p_51746_, BlockPos p_51747_, BlockState p_51748_) {
      p_51745_.m_7731_(p_51747_, p_51748_.m_61124_(f_51736_, Integer.valueOf(p_51748_.m_61143_(f_51736_) + 1)), 2);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_51778_) {
      p_51778_.m_61104_(f_54117_, f_51736_);
   }

   public boolean m_7357_(BlockState p_51762_, BlockGetter p_51763_, BlockPos p_51764_, PathComputationType p_51765_) {
      return false;
   }
}
