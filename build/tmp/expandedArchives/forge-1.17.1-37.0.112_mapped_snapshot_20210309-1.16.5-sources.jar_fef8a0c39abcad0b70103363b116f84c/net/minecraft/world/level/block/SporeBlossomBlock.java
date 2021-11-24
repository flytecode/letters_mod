package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SporeBlossomBlock extends Block {
   private static final VoxelShape f_154691_ = Block.m_49796_(2.0D, 13.0D, 2.0D, 14.0D, 16.0D, 14.0D);
   private static final int f_154692_ = 14;
   private static final int f_154693_ = 10;
   private static final int f_154694_ = 10;

   public SporeBlossomBlock(BlockBehaviour.Properties p_154697_) {
      super(p_154697_);
   }

   public boolean m_7898_(BlockState p_154709_, LevelReader p_154710_, BlockPos p_154711_) {
      return Block.m_49863_(p_154710_, p_154711_.m_7494_(), Direction.DOWN) && !p_154710_.m_46801_(p_154711_);
   }

   public BlockState m_7417_(BlockState p_154713_, Direction p_154714_, BlockState p_154715_, LevelAccessor p_154716_, BlockPos p_154717_, BlockPos p_154718_) {
      return p_154714_ == Direction.UP && !this.m_7898_(p_154713_, p_154716_, p_154717_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_154713_, p_154714_, p_154715_, p_154716_, p_154717_, p_154718_);
   }

   public void m_7100_(BlockState p_154704_, Level p_154705_, BlockPos p_154706_, Random p_154707_) {
      int i = p_154706_.m_123341_();
      int j = p_154706_.m_123342_();
      int k = p_154706_.m_123343_();
      double d0 = (double)i + p_154707_.nextDouble();
      double d1 = (double)j + 0.7D;
      double d2 = (double)k + p_154707_.nextDouble();
      p_154705_.m_7106_(ParticleTypes.f_175832_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int l = 0; l < 14; ++l) {
         blockpos$mutableblockpos.m_122178_(i + Mth.m_14072_(p_154707_, -10, 10), j - p_154707_.nextInt(10), k + Mth.m_14072_(p_154707_, -10, 10));
         BlockState blockstate = p_154705_.m_8055_(blockpos$mutableblockpos);
         if (!blockstate.m_60838_(p_154705_, blockpos$mutableblockpos)) {
            p_154705_.m_7106_(ParticleTypes.f_175833_, (double)blockpos$mutableblockpos.m_123341_() + p_154707_.nextDouble(), (double)blockpos$mutableblockpos.m_123342_() + p_154707_.nextDouble(), (double)blockpos$mutableblockpos.m_123343_() + p_154707_.nextDouble(), 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public VoxelShape m_5940_(BlockState p_154699_, BlockGetter p_154700_, BlockPos p_154701_, CollisionContext p_154702_) {
      return f_154691_;
   }
}