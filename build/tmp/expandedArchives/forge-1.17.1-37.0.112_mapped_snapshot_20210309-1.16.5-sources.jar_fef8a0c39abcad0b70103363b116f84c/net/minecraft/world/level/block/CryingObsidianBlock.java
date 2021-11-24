package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class CryingObsidianBlock extends Block {
   public CryingObsidianBlock(BlockBehaviour.Properties p_52371_) {
      super(p_52371_);
   }

   public void m_7100_(BlockState p_52373_, Level p_52374_, BlockPos p_52375_, Random p_52376_) {
      if (p_52376_.nextInt(5) == 0) {
         Direction direction = Direction.m_122404_(p_52376_);
         if (direction != Direction.UP) {
            BlockPos blockpos = p_52375_.m_142300_(direction);
            BlockState blockstate = p_52374_.m_8055_(blockpos);
            if (!p_52373_.m_60815_() || !blockstate.m_60783_(p_52374_, blockpos, direction.m_122424_())) {
               double d0 = direction.m_122429_() == 0 ? p_52376_.nextDouble() : 0.5D + (double)direction.m_122429_() * 0.6D;
               double d1 = direction.m_122430_() == 0 ? p_52376_.nextDouble() : 0.5D + (double)direction.m_122430_() * 0.6D;
               double d2 = direction.m_122431_() == 0 ? p_52376_.nextDouble() : 0.5D + (double)direction.m_122431_() * 0.6D;
               p_52374_.m_7106_(ParticleTypes.f_123786_, (double)p_52375_.m_123341_() + d0, (double)p_52375_.m_123342_() + d1, (double)p_52375_.m_123343_() + d2, 0.0D, 0.0D, 0.0D);
            }
         }
      }
   }
}