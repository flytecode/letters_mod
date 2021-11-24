package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.PushReaction;

public class EndRodBlock extends RodBlock {
   public EndRodBlock(BlockBehaviour.Properties p_53085_) {
      super(p_53085_);
      this.m_49959_(this.f_49792_.m_61090_().m_61124_(f_52588_, Direction.UP));
   }

   public BlockState m_5573_(BlockPlaceContext p_53087_) {
      Direction direction = p_53087_.m_43719_();
      BlockState blockstate = p_53087_.m_43725_().m_8055_(p_53087_.m_8083_().m_142300_(direction.m_122424_()));
      return blockstate.m_60713_(this) && blockstate.m_61143_(f_52588_) == direction ? this.m_49966_().m_61124_(f_52588_, direction.m_122424_()) : this.m_49966_().m_61124_(f_52588_, direction);
   }

   public void m_7100_(BlockState p_53094_, Level p_53095_, BlockPos p_53096_, Random p_53097_) {
      Direction direction = p_53094_.m_61143_(f_52588_);
      double d0 = (double)p_53096_.m_123341_() + 0.55D - (double)(p_53097_.nextFloat() * 0.1F);
      double d1 = (double)p_53096_.m_123342_() + 0.55D - (double)(p_53097_.nextFloat() * 0.1F);
      double d2 = (double)p_53096_.m_123343_() + 0.55D - (double)(p_53097_.nextFloat() * 0.1F);
      double d3 = (double)(0.4F - (p_53097_.nextFloat() + p_53097_.nextFloat()) * 0.4F);
      if (p_53097_.nextInt(5) == 0) {
         p_53095_.m_7106_(ParticleTypes.f_123810_, d0 + (double)direction.m_122429_() * d3, d1 + (double)direction.m_122430_() * d3, d2 + (double)direction.m_122431_() * d3, p_53097_.nextGaussian() * 0.005D, p_53097_.nextGaussian() * 0.005D, p_53097_.nextGaussian() * 0.005D);
      }

   }

   protected void m_7926_(StateDefinition.Builder<Block, BlockState> p_53105_) {
      p_53105_.m_61104_(f_52588_);
   }

   public PushReaction m_5537_(BlockState p_53112_) {
      return PushReaction.NORMAL;
   }
}