package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WetSpongeBlock extends Block {
   public WetSpongeBlock(BlockBehaviour.Properties p_58222_) {
      super(p_58222_);
   }

   public void m_6807_(BlockState p_58229_, Level p_58230_, BlockPos p_58231_, BlockState p_58232_, boolean p_58233_) {
      if (p_58230_.m_6042_().m_63951_()) {
         p_58230_.m_7731_(p_58231_, Blocks.f_50056_.m_49966_(), 3);
         p_58230_.m_46796_(2009, p_58231_, 0);
         p_58230_.m_5594_((Player)null, p_58231_, SoundEvents.f_11937_, SoundSource.BLOCKS, 1.0F, (1.0F + p_58230_.m_5822_().nextFloat() * 0.2F) * 0.7F);
      }

   }

   public void m_7100_(BlockState p_58224_, Level p_58225_, BlockPos p_58226_, Random p_58227_) {
      Direction direction = Direction.m_122404_(p_58227_);
      if (direction != Direction.UP) {
         BlockPos blockpos = p_58226_.m_142300_(direction);
         BlockState blockstate = p_58225_.m_8055_(blockpos);
         if (!p_58224_.m_60815_() || !blockstate.m_60783_(p_58225_, blockpos, direction.m_122424_())) {
            double d0 = (double)p_58226_.m_123341_();
            double d1 = (double)p_58226_.m_123342_();
            double d2 = (double)p_58226_.m_123343_();
            if (direction == Direction.DOWN) {
               d1 = d1 - 0.05D;
               d0 += p_58227_.nextDouble();
               d2 += p_58227_.nextDouble();
            } else {
               d1 = d1 + p_58227_.nextDouble() * 0.8D;
               if (direction.m_122434_() == Direction.Axis.X) {
                  d2 += p_58227_.nextDouble();
                  if (direction == Direction.EAST) {
                     ++d0;
                  } else {
                     d0 += 0.05D;
                  }
               } else {
                  d0 += p_58227_.nextDouble();
                  if (direction == Direction.SOUTH) {
                     ++d2;
                  } else {
                     d2 += 0.05D;
                  }
               }
            }

            p_58225_.m_7106_(ParticleTypes.f_123803_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
         }
      }
   }
}