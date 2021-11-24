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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FurnaceBlock extends AbstractFurnaceBlock {
   public FurnaceBlock(BlockBehaviour.Properties p_53627_) {
      super(p_53627_);
   }

   public BlockEntity m_142194_(BlockPos p_153277_, BlockState p_153278_) {
      return new FurnaceBlockEntity(p_153277_, p_153278_);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153273_, BlockState p_153274_, BlockEntityType<T> p_153275_) {
      return m_151987_(p_153273_, p_153275_, BlockEntityType.f_58917_);
   }

   protected void m_7137_(Level p_53631_, BlockPos p_53632_, Player p_53633_) {
      BlockEntity blockentity = p_53631_.m_7702_(p_53632_);
      if (blockentity instanceof FurnaceBlockEntity) {
         p_53633_.m_5893_((MenuProvider)blockentity);
         p_53633_.m_36220_(Stats.f_12966_);
      }

   }

   public void m_7100_(BlockState p_53635_, Level p_53636_, BlockPos p_53637_, Random p_53638_) {
      if (p_53635_.m_61143_(f_48684_)) {
         double d0 = (double)p_53637_.m_123341_() + 0.5D;
         double d1 = (double)p_53637_.m_123342_();
         double d2 = (double)p_53637_.m_123343_() + 0.5D;
         if (p_53638_.nextDouble() < 0.1D) {
            p_53636_.m_7785_(d0, d1, d2, SoundEvents.f_11907_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         }

         Direction direction = p_53635_.m_61143_(f_48683_);
         Direction.Axis direction$axis = direction.m_122434_();
         double d3 = 0.52D;
         double d4 = p_53638_.nextDouble() * 0.6D - 0.3D;
         double d5 = direction$axis == Direction.Axis.X ? (double)direction.m_122429_() * 0.52D : d4;
         double d6 = p_53638_.nextDouble() * 6.0D / 16.0D;
         double d7 = direction$axis == Direction.Axis.Z ? (double)direction.m_122431_() * 0.52D : d4;
         p_53636_.m_7106_(ParticleTypes.f_123762_, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
         p_53636_.m_7106_(ParticleTypes.f_123744_, d0 + d5, d1 + d6, d2 + d7, 0.0D, 0.0D, 0.0D);
      }
   }
}