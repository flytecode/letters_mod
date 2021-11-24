package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ScaffoldingBlock extends Block implements SimpleWaterloggedBlock {
   private static final int f_154382_ = 1;
   private static final VoxelShape f_56015_;
   private static final VoxelShape f_56016_;
   private static final VoxelShape f_56017_ = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
   private static final VoxelShape f_56018_ = Shapes.m_83144_().m_83216_(0.0D, -1.0D, 0.0D);
   public static final int f_154381_ = 7;
   public static final IntegerProperty f_56012_ = BlockStateProperties.f_61388_;
   public static final BooleanProperty f_56013_ = BlockStateProperties.f_61362_;
   public static final BooleanProperty f_56014_ = BlockStateProperties.f_61427_;

   public ScaffoldingBlock(BlockBehaviour.Properties p_56021_) {
      super(p_56021_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_56012_, Integer.valueOf(7)).m_61124_(f_56013_, Boolean.valueOf(false)).m_61124_(f_56014_, Boolean.valueOf(false)));
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56051_) {
      p_56051_.m_61104_(f_56012_, f_56013_, f_56014_);
   }

   public VoxelShape m_5940_(BlockState p_56057_, BlockGetter p_56058_, BlockPos p_56059_, CollisionContext p_56060_) {
      if (!p_56060_.m_7142_(p_56057_.m_60734_().m_5456_())) {
         return p_56057_.m_61143_(f_56014_) ? f_56016_ : f_56015_;
      } else {
         return Shapes.m_83144_();
      }
   }

   public VoxelShape m_6079_(BlockState p_56053_, BlockGetter p_56054_, BlockPos p_56055_) {
      return Shapes.m_83144_();
   }

   public boolean m_6864_(BlockState p_56037_, BlockPlaceContext p_56038_) {
      return p_56038_.m_43722_().m_150930_(this.m_5456_());
   }

   public BlockState m_5573_(BlockPlaceContext p_56023_) {
      BlockPos blockpos = p_56023_.m_8083_();
      Level level = p_56023_.m_43725_();
      int i = m_56024_(level, blockpos);
      return this.m_49966_().m_61124_(f_56013_, Boolean.valueOf(level.m_6425_(blockpos).m_76152_() == Fluids.f_76193_)).m_61124_(f_56012_, Integer.valueOf(i)).m_61124_(f_56014_, Boolean.valueOf(this.m_56027_(level, blockpos, i)));
   }

   public void m_6807_(BlockState p_56062_, Level p_56063_, BlockPos p_56064_, BlockState p_56065_, boolean p_56066_) {
      if (!p_56063_.f_46443_) {
         p_56063_.m_6219_().m_5945_(p_56064_, this, 1);
      }

   }

   public BlockState m_7417_(BlockState p_56044_, Direction p_56045_, BlockState p_56046_, LevelAccessor p_56047_, BlockPos p_56048_, BlockPos p_56049_) {
      if (p_56044_.m_61143_(f_56013_)) {
         p_56047_.m_6217_().m_5945_(p_56048_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_56047_));
      }

      if (!p_56047_.m_5776_()) {
         p_56047_.m_6219_().m_5945_(p_56048_, this, 1);
      }

      return p_56044_;
   }

   public void m_7458_(BlockState p_56032_, ServerLevel p_56033_, BlockPos p_56034_, Random p_56035_) {
      int i = m_56024_(p_56033_, p_56034_);
      BlockState blockstate = p_56032_.m_61124_(f_56012_, Integer.valueOf(i)).m_61124_(f_56014_, Boolean.valueOf(this.m_56027_(p_56033_, p_56034_, i)));
      if (blockstate.m_61143_(f_56012_) == 7) {
         if (p_56032_.m_61143_(f_56012_) == 7) {
            p_56033_.m_7967_(new FallingBlockEntity(p_56033_, (double)p_56034_.m_123341_() + 0.5D, (double)p_56034_.m_123342_(), (double)p_56034_.m_123343_() + 0.5D, blockstate.m_61124_(f_56013_, Boolean.valueOf(false))));
         } else {
            p_56033_.m_46961_(p_56034_, true);
         }
      } else if (p_56032_ != blockstate) {
         p_56033_.m_7731_(p_56034_, blockstate, 3);
      }

   }

   public boolean m_7898_(BlockState p_56040_, LevelReader p_56041_, BlockPos p_56042_) {
      return m_56024_(p_56041_, p_56042_) < 7;
   }

   public VoxelShape m_5939_(BlockState p_56068_, BlockGetter p_56069_, BlockPos p_56070_, CollisionContext p_56071_) {
      if (p_56071_.m_6513_(Shapes.m_83144_(), p_56070_, true) && !p_56071_.m_6226_()) {
         return f_56015_;
      } else {
         return p_56068_.m_61143_(f_56012_) != 0 && p_56068_.m_61143_(f_56014_) && p_56071_.m_6513_(f_56018_, p_56070_, true) ? f_56017_ : Shapes.m_83040_();
      }
   }

   public FluidState m_5888_(BlockState p_56073_) {
      return p_56073_.m_61143_(f_56013_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_56073_);
   }

   private boolean m_56027_(BlockGetter p_56028_, BlockPos p_56029_, int p_56030_) {
      return p_56030_ > 0 && !p_56028_.m_8055_(p_56029_.m_7495_()).m_60713_(this);
   }

   public static int m_56024_(BlockGetter p_56025_, BlockPos p_56026_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_56026_.m_122032_().m_122173_(Direction.DOWN);
      BlockState blockstate = p_56025_.m_8055_(blockpos$mutableblockpos);
      int i = 7;
      if (blockstate.m_60713_(Blocks.f_50616_)) {
         i = blockstate.m_61143_(f_56012_);
      } else if (blockstate.m_60783_(p_56025_, blockpos$mutableblockpos, Direction.UP)) {
         return 0;
      }

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         BlockState blockstate1 = p_56025_.m_8055_(blockpos$mutableblockpos.m_122159_(p_56026_, direction));
         if (blockstate1.m_60713_(Blocks.f_50616_)) {
            i = Math.min(i, blockstate1.m_61143_(f_56012_) + 1);
            if (i == 1) {
               break;
            }
         }
      }

      return i;
   }

   static {
      VoxelShape voxelshape = Block.m_49796_(0.0D, 14.0D, 0.0D, 16.0D, 16.0D, 16.0D);
      VoxelShape voxelshape1 = Block.m_49796_(0.0D, 0.0D, 0.0D, 2.0D, 16.0D, 2.0D);
      VoxelShape voxelshape2 = Block.m_49796_(14.0D, 0.0D, 0.0D, 16.0D, 16.0D, 2.0D);
      VoxelShape voxelshape3 = Block.m_49796_(0.0D, 0.0D, 14.0D, 2.0D, 16.0D, 16.0D);
      VoxelShape voxelshape4 = Block.m_49796_(14.0D, 0.0D, 14.0D, 16.0D, 16.0D, 16.0D);
      f_56015_ = Shapes.m_83124_(voxelshape, voxelshape1, voxelshape2, voxelshape3, voxelshape4);
      VoxelShape voxelshape5 = Block.m_49796_(0.0D, 0.0D, 0.0D, 2.0D, 2.0D, 16.0D);
      VoxelShape voxelshape6 = Block.m_49796_(14.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D);
      VoxelShape voxelshape7 = Block.m_49796_(0.0D, 0.0D, 14.0D, 16.0D, 2.0D, 16.0D);
      VoxelShape voxelshape8 = Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 2.0D);
      f_56016_ = Shapes.m_83124_(ScaffoldingBlock.f_56017_, f_56015_, voxelshape6, voxelshape5, voxelshape8, voxelshape7);
   }
}