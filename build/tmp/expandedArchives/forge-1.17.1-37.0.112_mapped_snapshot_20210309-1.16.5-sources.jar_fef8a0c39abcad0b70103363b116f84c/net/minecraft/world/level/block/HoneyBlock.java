package net.minecraft.world.level.block;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HoneyBlock extends HalfTransparentBlock {
   private static final double f_153367_ = 0.13D;
   private static final double f_153368_ = 0.08D;
   private static final double f_153369_ = 0.05D;
   private static final int f_153370_ = 20;
   protected static final VoxelShape f_53982_ = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 15.0D, 15.0D);

   public HoneyBlock(BlockBehaviour.Properties p_53985_) {
      super(p_53985_);
   }

   private static boolean m_54012_(Entity p_54013_) {
      return p_54013_ instanceof LivingEntity || p_54013_ instanceof AbstractMinecart || p_54013_ instanceof PrimedTnt || p_54013_ instanceof Boat;
   }

   public VoxelShape m_5939_(BlockState p_54015_, BlockGetter p_54016_, BlockPos p_54017_, CollisionContext p_54018_) {
      return f_53982_;
   }

   public void m_142072_(Level p_153372_, BlockState p_153373_, BlockPos p_153374_, Entity p_153375_, float p_153376_) {
      p_153375_.m_5496_(SoundEvents.f_11968_, 1.0F, 1.0F);
      if (!p_153372_.f_46443_) {
         p_153372_.m_7605_(p_153375_, (byte)54);
      }

      if (p_153375_.m_142535_(p_153376_, 0.2F, DamageSource.f_19315_)) {
         p_153375_.m_5496_(this.f_60446_.m_56779_(), this.f_60446_.m_56773_() * 0.5F, this.f_60446_.m_56774_() * 0.75F);
      }

   }

   public void m_7892_(BlockState p_54003_, Level p_54004_, BlockPos p_54005_, Entity p_54006_) {
      if (this.m_54007_(p_54005_, p_54006_)) {
         this.m_53991_(p_54006_, p_54005_);
         this.m_54019_(p_54006_);
         this.m_53994_(p_54004_, p_54006_);
      }

      super.m_7892_(p_54003_, p_54004_, p_54005_, p_54006_);
   }

   private boolean m_54007_(BlockPos p_54008_, Entity p_54009_) {
      if (p_54009_.m_20096_()) {
         return false;
      } else if (p_54009_.m_20186_() > (double)p_54008_.m_123342_() + 0.9375D - 1.0E-7D) {
         return false;
      } else if (p_54009_.m_20184_().f_82480_ >= -0.08D) {
         return false;
      } else {
         double d0 = Math.abs((double)p_54008_.m_123341_() + 0.5D - p_54009_.m_20185_());
         double d1 = Math.abs((double)p_54008_.m_123343_() + 0.5D - p_54009_.m_20189_());
         double d2 = 0.4375D + (double)(p_54009_.m_20205_() / 2.0F);
         return d0 + 1.0E-7D > d2 || d1 + 1.0E-7D > d2;
      }
   }

   private void m_53991_(Entity p_53992_, BlockPos p_53993_) {
      if (p_53992_ instanceof ServerPlayer && p_53992_.f_19853_.m_46467_() % 20L == 0L) {
         CriteriaTriggers.f_10559_.m_66978_((ServerPlayer)p_53992_, p_53992_.f_19853_.m_8055_(p_53993_));
      }

   }

   private void m_54019_(Entity p_54020_) {
      Vec3 vec3 = p_54020_.m_20184_();
      if (vec3.f_82480_ < -0.13D) {
         double d0 = -0.05D / vec3.f_82480_;
         p_54020_.m_20256_(new Vec3(vec3.f_82479_ * d0, -0.05D, vec3.f_82481_ * d0));
      } else {
         p_54020_.m_20256_(new Vec3(vec3.f_82479_, -0.05D, vec3.f_82481_));
      }

      p_54020_.f_19789_ = 0.0F;
   }

   private void m_53994_(Level p_53995_, Entity p_53996_) {
      if (m_54012_(p_53996_)) {
         if (p_53995_.f_46441_.nextInt(5) == 0) {
            p_53996_.m_5496_(SoundEvents.f_11968_, 1.0F, 1.0F);
         }

         if (!p_53995_.f_46443_ && p_53995_.f_46441_.nextInt(5) == 0) {
            p_53995_.m_7605_(p_53996_, (byte)53);
         }
      }

   }

   public static void m_53986_(Entity p_53987_) {
      m_53988_(p_53987_, 5);
   }

   public static void m_54010_(Entity p_54011_) {
      m_53988_(p_54011_, 10);
   }

   private static void m_53988_(Entity p_53989_, int p_53990_) {
      if (p_53989_.f_19853_.f_46443_) {
         BlockState blockstate = Blocks.f_50719_.m_49966_();

         for(int i = 0; i < p_53990_; ++i) {
            p_53989_.f_19853_.m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, blockstate), p_53989_.m_20185_(), p_53989_.m_20186_(), p_53989_.m_20189_(), 0.0D, 0.0D, 0.0D);
         }

      }
   }
}