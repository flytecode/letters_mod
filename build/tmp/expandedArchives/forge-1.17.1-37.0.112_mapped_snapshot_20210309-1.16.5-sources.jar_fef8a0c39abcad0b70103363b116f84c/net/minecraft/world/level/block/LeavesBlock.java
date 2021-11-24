package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LeavesBlock extends Block implements net.minecraftforge.common.IForgeShearable {
   public static final int f_153563_ = 7;
   public static final IntegerProperty f_54418_ = BlockStateProperties.f_61414_;
   public static final BooleanProperty f_54419_ = BlockStateProperties.f_61447_;
   private static final int f_153564_ = 1;

   public LeavesBlock(BlockBehaviour.Properties p_54422_) {
      super(p_54422_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_54418_, Integer.valueOf(7)).m_61124_(f_54419_, Boolean.valueOf(false)));
   }

   public VoxelShape m_7947_(BlockState p_54456_, BlockGetter p_54457_, BlockPos p_54458_) {
      return Shapes.m_83040_();
   }

   public boolean m_6724_(BlockState p_54449_) {
      return p_54449_.m_61143_(f_54418_) == 7 && !p_54449_.m_61143_(f_54419_);
   }

   public void m_7455_(BlockState p_54451_, ServerLevel p_54452_, BlockPos p_54453_, Random p_54454_) {
      if (!p_54451_.m_61143_(f_54419_) && p_54451_.m_61143_(f_54418_) == 7) {
         m_49950_(p_54451_, p_54452_, p_54453_);
         p_54452_.m_7471_(p_54453_, false);
      }

   }

   public void m_7458_(BlockState p_54426_, ServerLevel p_54427_, BlockPos p_54428_, Random p_54429_) {
      p_54427_.m_7731_(p_54428_, m_54435_(p_54426_, p_54427_, p_54428_), 3);
   }

   public int m_7753_(BlockState p_54460_, BlockGetter p_54461_, BlockPos p_54462_) {
      return 1;
   }

   public BlockState m_7417_(BlockState p_54440_, Direction p_54441_, BlockState p_54442_, LevelAccessor p_54443_, BlockPos p_54444_, BlockPos p_54445_) {
      int i = m_54463_(p_54442_) + 1;
      if (i != 1 || p_54440_.m_61143_(f_54418_) != i) {
         p_54443_.m_6219_().m_5945_(p_54444_, this, 1);
      }

      return p_54440_;
   }

   private static BlockState m_54435_(BlockState p_54436_, LevelAccessor p_54437_, BlockPos p_54438_) {
      int i = 7;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(Direction direction : Direction.values()) {
         blockpos$mutableblockpos.m_122159_(p_54438_, direction);
         i = Math.min(i, m_54463_(p_54437_.m_8055_(blockpos$mutableblockpos)) + 1);
         if (i == 1) {
            break;
         }
      }

      return p_54436_.m_61124_(f_54418_, Integer.valueOf(i));
   }

   private static int m_54463_(BlockState p_54464_) {
      if (p_54464_.m_60620_(BlockTags.f_13106_)) {
         return 0;
      } else {
         return p_54464_.m_60734_() instanceof LeavesBlock ? p_54464_.m_61143_(f_54418_) : 7;
      }
   }

   public void m_7100_(BlockState p_54431_, Level p_54432_, BlockPos p_54433_, Random p_54434_) {
      if (p_54432_.m_46758_(p_54433_.m_7494_())) {
         if (p_54434_.nextInt(15) == 1) {
            BlockPos blockpos = p_54433_.m_7495_();
            BlockState blockstate = p_54432_.m_8055_(blockpos);
            if (!blockstate.m_60815_() || !blockstate.m_60783_(p_54432_, blockpos, Direction.UP)) {
               double d0 = (double)p_54433_.m_123341_() + p_54434_.nextDouble();
               double d1 = (double)p_54433_.m_123342_() - 0.05D;
               double d2 = (double)p_54433_.m_123343_() + p_54434_.nextDouble();
               p_54432_.m_7106_(ParticleTypes.f_123803_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
         }
      }
   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_54447_) {
      p_54447_.m_61104_(f_54418_, f_54419_);
   }

   public BlockState m_5573_(BlockPlaceContext p_54424_) {
      return m_54435_(this.m_49966_().m_61124_(f_54419_, Boolean.valueOf(true)), p_54424_.m_43725_(), p_54424_.m_8083_());
   }
}
