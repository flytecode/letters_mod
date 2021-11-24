package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlastFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class BlastFurnaceBlock extends AbstractFurnaceBlock {
   public BlastFurnaceBlock(BlockBehaviour.Properties p_49773_) {
      super(p_49773_);
   }

   public BlockEntity m_142194_(BlockPos p_152386_, BlockState p_152387_) {
      return new BlastFurnaceBlockEntity(p_152386_, p_152387_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_152382_, BlockState p_152383_, BlockEntityType<T> p_152384_) {
      return m_151987_(p_152382_, p_152384_, BlockEntityType.f_58907_);
   }

   protected void m_7137_(Level p_49777_, BlockPos p_49778_, Player p_49779_) {
      BlockEntity blockentity = p_49777_.m_7702_(p_49778_);
      if (blockentity instanceof BlastFurnaceBlockEntity) {
         p_49779_.m_5893_((MenuProvider)blockentity);
         p_49779_.m_36220_(Stats.f_12972_);
      }

   }

   public void m_7100_(BlockState p_49781_, Level p_49782_, BlockPos p_49783_, Random p_49784_) {
      if (p_49781_.m_61143_(f_48684_)) {
         double d0 = (double)p_49783_.m_123341_() + 0.5D;
         double d1 = (double)p_49783_.m_123342_();
         double d2 = (double)p_49783_.m_123343_() + 0.5D;
         if (p_49784_.nextDouble() < 0.1D) {
            p_49782_.m_7785_(d0, d1, d2, SoundEvents.f_11715_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         }

         Direction direction = p_49781_.m_61143_(f_48683_);
         Direction.Axis direction$axis = direction.m_122434_();
         double d3 = 0.52D;
         double d4 = p_49784_.nextDouble() * 0.6D - 0.3D;
         double d5 = direction$axis == Direction.Axis.X ? (double)direction.m_122429_() * 0.52D : d4;
         double d6 = p_49784_.nextDouble() * 9.0D / 16.0D;
         double d7 = direction$axis == Direction.Axis.Z ? (double)direction.m_122431_() * 0.52D : d4;
         p_49782_.m_7106_(ParticleTypes.f_123762_, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
      }
   }
}