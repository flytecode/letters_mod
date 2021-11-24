package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class MyceliumBlock extends SpreadingSnowyDirtBlock {
   public MyceliumBlock(BlockBehaviour.Properties p_54898_) {
      super(p_54898_);
   }

   public void m_7100_(BlockState p_54900_, Level p_54901_, BlockPos p_54902_, Random p_54903_) {
      super.m_7100_(p_54900_, p_54901_, p_54902_, p_54903_);
      if (p_54903_.nextInt(10) == 0) {
         p_54901_.m_7106_(ParticleTypes.f_123757_, (double)p_54902_.m_123341_() + p_54903_.nextDouble(), (double)p_54902_.m_123342_() + 1.1D, (double)p_54902_.m_123343_() + p_54903_.nextDouble(), 0.0D, 0.0D, 0.0D);
      }

   }
}