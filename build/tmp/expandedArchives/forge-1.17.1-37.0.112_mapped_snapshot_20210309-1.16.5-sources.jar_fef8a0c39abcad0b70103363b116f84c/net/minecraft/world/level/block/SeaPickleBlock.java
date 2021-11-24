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
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SeaPickleBlock extends BushBlock implements BonemealableBlock, SimpleWaterloggedBlock {
   public static final int f_154491_ = 4;
   public static final IntegerProperty f_56074_ = BlockStateProperties.f_61425_;
   public static final BooleanProperty f_56075_ = BlockStateProperties.f_61362_;
   protected static final VoxelShape f_56076_ = Block.m_49796_(6.0D, 0.0D, 6.0D, 10.0D, 6.0D, 10.0D);
   protected static final VoxelShape f_56077_ = Block.m_49796_(3.0D, 0.0D, 3.0D, 13.0D, 6.0D, 13.0D);
   protected static final VoxelShape f_56078_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 6.0D, 14.0D);
   protected static final VoxelShape f_56079_ = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 7.0D, 14.0D);

   public SeaPickleBlock(BlockBehaviour.Properties p_56082_) {
      super(p_56082_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_56074_, Integer.valueOf(1)).m_61124_(f_56075_, Boolean.valueOf(true)));
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext p_56089_) {
      BlockState blockstate = p_56089_.m_43725_().m_8055_(p_56089_.m_8083_());
      if (blockstate.m_60713_(this)) {
         return blockstate.m_61124_(f_56074_, Integer.valueOf(Math.min(4, blockstate.m_61143_(f_56074_) + 1)));
      } else {
         FluidState fluidstate = p_56089_.m_43725_().m_6425_(p_56089_.m_8083_());
         boolean flag = fluidstate.m_76152_() == Fluids.f_76193_;
         return super.m_5573_(p_56089_).m_61124_(f_56075_, Boolean.valueOf(flag));
      }
   }

   public static boolean m_56132_(BlockState p_56133_) {
      return !p_56133_.m_61143_(f_56075_);
   }

   protected boolean m_6266_(BlockState p_56127_, BlockGetter p_56128_, BlockPos p_56129_) {
      return !p_56127_.m_60812_(p_56128_, p_56129_).m_83263_(Direction.UP).m_83281_() || p_56127_.m_60783_(p_56128_, p_56129_, Direction.UP);
   }

   public boolean m_7898_(BlockState p_56109_, LevelReader p_56110_, BlockPos p_56111_) {
      BlockPos blockpos = p_56111_.m_7495_();
      return this.m_6266_(p_56110_.m_8055_(blockpos), p_56110_, blockpos);
   }

   public BlockState m_7417_(BlockState p_56113_, Direction p_56114_, BlockState p_56115_, LevelAccessor p_56116_, BlockPos p_56117_, BlockPos p_56118_) {
      if (!p_56113_.m_60710_(p_56116_, p_56117_)) {
         return Blocks.f_50016_.m_49966_();
      } else {
         if (p_56113_.m_61143_(f_56075_)) {
            p_56116_.m_6217_().m_5945_(p_56117_, Fluids.f_76193_, Fluids.f_76193_.m_6718_(p_56116_));
         }

         return super.m_7417_(p_56113_, p_56114_, p_56115_, p_56116_, p_56117_, p_56118_);
      }
   }

   public boolean m_6864_(BlockState p_56101_, BlockPlaceContext p_56102_) {
      return !p_56102_.m_7078_() && p_56102_.m_43722_().m_150930_(this.m_5456_()) && p_56101_.m_61143_(f_56074_) < 4 ? true : super.m_6864_(p_56101_, p_56102_);
   }

   public VoxelShape m_5940_(BlockState p_56122_, BlockGetter p_56123_, BlockPos p_56124_, CollisionContext p_56125_) {
      switch(p_56122_.m_61143_(f_56074_)) {
      case 1:
      default:
         return f_56076_;
      case 2:
         return f_56077_;
      case 3:
         return f_56078_;
      case 4:
         return f_56079_;
      }
   }

   public FluidState m_5888_(BlockState p_56131_) {
      return p_56131_.m_61143_(f_56075_) ? Fluids.f_76193_.m_76068_(false) : super.m_5888_(p_56131_);
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_56120_) {
      p_56120_.m_61104_(f_56074_, f_56075_);
   }

   public boolean m_7370_(BlockGetter p_56091_, BlockPos p_56092_, BlockState p_56093_, boolean p_56094_) {
      return true;
   }

   public boolean m_5491_(Level p_56096_, Random p_56097_, BlockPos p_56098_, BlockState p_56099_) {
      return true;
   }

   public void m_7719_(ServerLevel p_56084_, Random p_56085_, BlockPos p_56086_, BlockState p_56087_) {
      if (!m_56132_(p_56087_) && p_56084_.m_8055_(p_56086_.m_7495_()).m_60620_(BlockTags.f_13051_)) {
         int i = 5;
         int j = 1;
         int k = 2;
         int l = 0;
         int i1 = p_56086_.m_123341_() - 2;
         int j1 = 0;

         for(int k1 = 0; k1 < 5; ++k1) {
            for(int l1 = 0; l1 < j; ++l1) {
               int i2 = 2 + p_56086_.m_123342_() - 1;

               for(int j2 = i2 - 2; j2 < i2; ++j2) {
                  BlockPos blockpos = new BlockPos(i1 + k1, j2, p_56086_.m_123343_() - j1 + l1);
                  if (blockpos != p_56086_ && p_56085_.nextInt(6) == 0 && p_56084_.m_8055_(blockpos).m_60713_(Blocks.f_49990_)) {
                     BlockState blockstate = p_56084_.m_8055_(blockpos.m_7495_());
                     if (blockstate.m_60620_(BlockTags.f_13051_)) {
                        p_56084_.m_7731_(blockpos, Blocks.f_50567_.m_49966_().m_61124_(f_56074_, Integer.valueOf(p_56085_.nextInt(4) + 1)), 3);
                     }
                  }
               }
            }

            if (l < 2) {
               j += 2;
               ++j1;
            } else {
               j -= 2;
               --j1;
            }

            ++l;
         }

         p_56084_.m_7731_(p_56086_, p_56087_.m_61124_(f_56074_, Integer.valueOf(4)), 2);
      }

   }

   public boolean m_7357_(BlockState p_56104_, BlockGetter p_56105_, BlockPos p_56106_, PathComputationType p_56107_) {
      return false;
   }
}