package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DragonEggBlock extends FallingBlock {
   protected static final VoxelShape f_52908_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

   public DragonEggBlock(BlockBehaviour.Properties p_52911_) {
      super(p_52911_);
   }

   public VoxelShape m_5940_(BlockState p_52930_, BlockGetter p_52931_, BlockPos p_52932_, CollisionContext p_52933_) {
      return f_52908_;
   }

   public InteractionResult m_6227_(BlockState p_52923_, Level p_52924_, BlockPos p_52925_, Player p_52926_, InteractionHand p_52927_, BlockHitResult p_52928_) {
      this.m_52935_(p_52923_, p_52924_, p_52925_);
      return InteractionResult.m_19078_(p_52924_.f_46443_);
   }

   public void m_6256_(BlockState p_52918_, Level p_52919_, BlockPos p_52920_, Player p_52921_) {
      this.m_52935_(p_52918_, p_52919_, p_52920_);
   }

   private void m_52935_(BlockState p_52936_, Level p_52937_, BlockPos p_52938_) {
      for(int i = 0; i < 1000; ++i) {
         BlockPos blockpos = p_52938_.m_142082_(p_52937_.f_46441_.nextInt(16) - p_52937_.f_46441_.nextInt(16), p_52937_.f_46441_.nextInt(8) - p_52937_.f_46441_.nextInt(8), p_52937_.f_46441_.nextInt(16) - p_52937_.f_46441_.nextInt(16));
         if (p_52937_.m_8055_(blockpos).m_60795_()) {
            if (p_52937_.f_46443_) {
               for(int j = 0; j < 128; ++j) {
                  double d0 = p_52937_.f_46441_.nextDouble();
                  float f = (p_52937_.f_46441_.nextFloat() - 0.5F) * 0.2F;
                  float f1 = (p_52937_.f_46441_.nextFloat() - 0.5F) * 0.2F;
                  float f2 = (p_52937_.f_46441_.nextFloat() - 0.5F) * 0.2F;
                  double d1 = Mth.m_14139_(d0, (double)blockpos.m_123341_(), (double)p_52938_.m_123341_()) + (p_52937_.f_46441_.nextDouble() - 0.5D) + 0.5D;
                  double d2 = Mth.m_14139_(d0, (double)blockpos.m_123342_(), (double)p_52938_.m_123342_()) + p_52937_.f_46441_.nextDouble() - 0.5D;
                  double d3 = Mth.m_14139_(d0, (double)blockpos.m_123343_(), (double)p_52938_.m_123343_()) + (p_52937_.f_46441_.nextDouble() - 0.5D) + 0.5D;
                  p_52937_.m_7106_(ParticleTypes.f_123760_, d1, d2, d3, (double)f, (double)f1, (double)f2);
               }
            } else {
               p_52937_.m_7731_(blockpos, p_52936_, 2);
               p_52937_.m_7471_(p_52938_, false);
            }

            return;
         }
      }

   }

   protected int m_7198_() {
      return 5;
   }

   public boolean m_7357_(BlockState p_52913_, BlockGetter p_52914_, BlockPos p_52915_, PathComputationType p_52916_) {
      return false;
   }
}