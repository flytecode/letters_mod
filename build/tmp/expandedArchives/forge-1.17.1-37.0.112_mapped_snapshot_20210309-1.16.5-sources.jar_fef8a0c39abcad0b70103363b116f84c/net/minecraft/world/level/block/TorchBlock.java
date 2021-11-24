package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TorchBlock extends Block {
   protected static final int f_154831_ = 2;
   protected static final VoxelShape f_57487_ = Block.m_49796_(6.0D, 0.0D, 6.0D, 10.0D, 10.0D, 10.0D);
   protected final ParticleOptions f_57488_;

   public TorchBlock(BlockBehaviour.Properties p_57491_, ParticleOptions p_57492_) {
      super(p_57491_);
      this.f_57488_ = p_57492_;
   }

   public VoxelShape m_5940_(BlockState p_57510_, BlockGetter p_57511_, BlockPos p_57512_, CollisionContext p_57513_) {
      return f_57487_;
   }

   public BlockState m_7417_(BlockState p_57503_, Direction p_57504_, BlockState p_57505_, LevelAccessor p_57506_, BlockPos p_57507_, BlockPos p_57508_) {
      return p_57504_ == Direction.DOWN && !this.m_7898_(p_57503_, p_57506_, p_57507_) ? Blocks.f_50016_.m_49966_() : super.m_7417_(p_57503_, p_57504_, p_57505_, p_57506_, p_57507_, p_57508_);
   }

   public boolean m_7898_(BlockState p_57499_, LevelReader p_57500_, BlockPos p_57501_) {
      return m_49863_(p_57500_, p_57501_.m_7495_(), Direction.UP);
   }

   public void m_7100_(BlockState p_57494_, Level p_57495_, BlockPos p_57496_, Random p_57497_) {
      double d0 = (double)p_57496_.m_123341_() + 0.5D;
      double d1 = (double)p_57496_.m_123342_() + 0.7D;
      double d2 = (double)p_57496_.m_123343_() + 0.5D;
      p_57495_.m_7106_(ParticleTypes.f_123762_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
      p_57495_.m_7106_(this.f_57488_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
   }
}