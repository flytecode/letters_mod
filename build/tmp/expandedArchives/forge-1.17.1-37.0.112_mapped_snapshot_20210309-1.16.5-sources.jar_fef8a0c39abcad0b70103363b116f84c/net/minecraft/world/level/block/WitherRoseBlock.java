package net.minecraft.world.level.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WitherRoseBlock extends FlowerBlock {
   public WitherRoseBlock(MobEffect p_58235_, BlockBehaviour.Properties p_58236_) {
      super(p_58235_, 8, p_58236_);
   }

   protected boolean m_6266_(BlockState p_58248_, BlockGetter p_58249_, BlockPos p_58250_) {
      return super.m_6266_(p_58248_, p_58249_, p_58250_) || p_58248_.m_60713_(Blocks.f_50134_) || p_58248_.m_60713_(Blocks.f_50135_) || p_58248_.m_60713_(Blocks.f_50136_);
   }

   public void m_7100_(BlockState p_58243_, Level p_58244_, BlockPos p_58245_, Random p_58246_) {
      VoxelShape voxelshape = this.m_5940_(p_58243_, p_58244_, p_58245_, CollisionContext.m_82749_());
      Vec3 vec3 = voxelshape.m_83215_().m_82399_();
      double d0 = (double)p_58245_.m_123341_() + vec3.f_82479_;
      double d1 = (double)p_58245_.m_123343_() + vec3.f_82481_;

      for(int i = 0; i < 3; ++i) {
         if (p_58246_.nextBoolean()) {
            p_58244_.m_7106_(ParticleTypes.f_123762_, d0 + p_58246_.nextDouble() / 5.0D, (double)p_58245_.m_123342_() + (0.5D - p_58246_.nextDouble()), d1 + p_58246_.nextDouble() / 5.0D, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public void m_7892_(BlockState p_58238_, Level p_58239_, BlockPos p_58240_, Entity p_58241_) {
      if (!p_58239_.f_46443_ && p_58239_.m_46791_() != Difficulty.PEACEFUL) {
         if (p_58241_ instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)p_58241_;
            if (!livingentity.m_6673_(DamageSource.f_19320_)) {
               livingentity.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 40));
            }
         }

      }
   }
}