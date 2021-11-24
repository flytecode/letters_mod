package net.minecraft.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class AmethystBlock extends Block {
   public AmethystBlock(BlockBehaviour.Properties p_151999_) {
      super(p_151999_);
   }

   public void m_5581_(Level p_152001_, BlockState p_152002_, BlockHitResult p_152003_, Projectile p_152004_) {
      if (!p_152001_.f_46443_) {
         BlockPos blockpos = p_152003_.m_82425_();
         p_152001_.m_5594_((Player)null, blockpos, SoundEvents.f_144245_, SoundSource.BLOCKS, 1.0F, 0.5F + p_152001_.f_46441_.nextFloat() * 1.2F);
         p_152001_.m_5594_((Player)null, blockpos, SoundEvents.f_144243_, SoundSource.BLOCKS, 1.0F, 0.5F + p_152001_.f_46441_.nextFloat() * 1.2F);
      }

   }
}