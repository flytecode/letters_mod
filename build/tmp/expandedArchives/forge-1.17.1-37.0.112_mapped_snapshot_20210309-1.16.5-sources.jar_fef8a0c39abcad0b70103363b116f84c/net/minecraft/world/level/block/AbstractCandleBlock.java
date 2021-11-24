package net.minecraft.world.level.block;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractCandleBlock extends Block {
   public static final int f_151894_ = 3;
   public static final BooleanProperty f_151895_ = BlockStateProperties.f_61443_;

   protected AbstractCandleBlock(BlockBehaviour.Properties p_151898_) {
      super(p_151898_);
   }

   protected abstract Iterable<Vec3> m_142199_(BlockState p_151927_);

   public static boolean m_151933_(BlockState p_151934_) {
      return p_151934_.m_61138_(f_151895_) && (p_151934_.m_60620_(BlockTags.f_144265_) || p_151934_.m_60620_(BlockTags.f_144268_)) && p_151934_.m_61143_(f_151895_);
   }

   public void m_5581_(Level p_151905_, BlockState p_151906_, BlockHitResult p_151907_, Projectile p_151908_) {
      if (!p_151905_.f_46443_ && p_151908_.m_6060_() && this.m_142595_(p_151906_)) {
         m_151918_(p_151905_, p_151906_, p_151907_.m_82425_(), true);
      }

   }

   protected boolean m_142595_(BlockState p_151935_) {
      return !p_151935_.m_61143_(f_151895_);
   }

   public void m_7100_(BlockState p_151929_, Level p_151930_, BlockPos p_151931_, Random p_151932_) {
      if (p_151929_.m_61143_(f_151895_)) {
         this.m_142199_(p_151929_).forEach((p_151917_) -> {
            m_151909_(p_151930_, p_151917_.m_82520_((double)p_151931_.m_123341_(), (double)p_151931_.m_123342_(), (double)p_151931_.m_123343_()), p_151932_);
         });
      }
   }

   private static void m_151909_(Level p_151910_, Vec3 p_151911_, Random p_151912_) {
      float f = p_151912_.nextFloat();
      if (f < 0.3F) {
         p_151910_.m_7106_(ParticleTypes.f_123762_, p_151911_.f_82479_, p_151911_.f_82480_, p_151911_.f_82481_, 0.0D, 0.0D, 0.0D);
         if (f < 0.17F) {
            p_151910_.m_7785_(p_151911_.f_82479_ + 0.5D, p_151911_.f_82480_ + 0.5D, p_151911_.f_82481_ + 0.5D, SoundEvents.f_144096_, SoundSource.BLOCKS, 1.0F + p_151912_.nextFloat(), p_151912_.nextFloat() * 0.7F + 0.3F, false);
         }
      }

      p_151910_.m_7106_(ParticleTypes.f_175834_, p_151911_.f_82479_, p_151911_.f_82480_, p_151911_.f_82481_, 0.0D, 0.0D, 0.0D);
   }

   public static void m_151899_(@Nullable Player p_151900_, BlockState p_151901_, LevelAccessor p_151902_, BlockPos p_151903_) {
      m_151918_(p_151902_, p_151901_, p_151903_, false);
      if (p_151901_.m_60734_() instanceof AbstractCandleBlock) {
         ((AbstractCandleBlock)p_151901_.m_60734_()).m_142199_(p_151901_).forEach((p_151926_) -> {
            p_151902_.m_7106_(ParticleTypes.f_123762_, (double)p_151903_.m_123341_() + p_151926_.m_7096_(), (double)p_151903_.m_123342_() + p_151926_.m_7098_(), (double)p_151903_.m_123343_() + p_151926_.m_7094_(), 0.0D, (double)0.1F, 0.0D);
         });
      }

      p_151902_.m_5594_((Player)null, p_151903_, SoundEvents.f_144098_, SoundSource.BLOCKS, 1.0F, 1.0F);
      p_151902_.m_142346_(p_151900_, GameEvent.f_157792_, p_151903_);
   }

   private static void m_151918_(LevelAccessor p_151919_, BlockState p_151920_, BlockPos p_151921_, boolean p_151922_) {
      p_151919_.m_7731_(p_151921_, p_151920_.m_61124_(f_151895_, Boolean.valueOf(p_151922_)), 11);
   }
}