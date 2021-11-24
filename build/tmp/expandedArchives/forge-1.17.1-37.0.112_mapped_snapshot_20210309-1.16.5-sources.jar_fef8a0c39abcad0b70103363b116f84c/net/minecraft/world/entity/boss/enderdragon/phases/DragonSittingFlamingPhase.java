package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.phys.Vec3;

public class DragonSittingFlamingPhase extends AbstractDragonSittingPhase {
   private static final int f_149579_ = 200;
   private static final int f_149580_ = 4;
   private static final int f_149581_ = 10;
   private int f_31326_;
   private int f_31327_;
   private AreaEffectCloud f_31328_;

   public DragonSittingFlamingPhase(EnderDragon p_31330_) {
      super(p_31330_);
   }

   public void m_6991_() {
      ++this.f_31326_;
      if (this.f_31326_ % 2 == 0 && this.f_31326_ < 10) {
         Vec3 vec3 = this.f_31176_.m_31174_(1.0F).m_82541_();
         vec3.m_82524_((-(float)Math.PI / 4F));
         double d0 = this.f_31176_.f_31080_.m_20185_();
         double d1 = this.f_31176_.f_31080_.m_20227_(0.5D);
         double d2 = this.f_31176_.f_31080_.m_20189_();

         for(int i = 0; i < 8; ++i) {
            double d3 = d0 + this.f_31176_.m_21187_().nextGaussian() / 2.0D;
            double d4 = d1 + this.f_31176_.m_21187_().nextGaussian() / 2.0D;
            double d5 = d2 + this.f_31176_.m_21187_().nextGaussian() / 2.0D;

            for(int j = 0; j < 6; ++j) {
               this.f_31176_.f_19853_.m_7106_(ParticleTypes.f_123799_, d3, d4, d5, -vec3.f_82479_ * (double)0.08F * (double)j, -vec3.f_82480_ * (double)0.6F, -vec3.f_82481_ * (double)0.08F * (double)j);
            }

            vec3.m_82524_(0.19634955F);
         }
      }

   }

   public void m_6989_() {
      ++this.f_31326_;
      if (this.f_31326_ >= 200) {
         if (this.f_31327_ >= 4) {
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31381_);
         } else {
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31383_);
         }
      } else if (this.f_31326_ == 10) {
         Vec3 vec3 = (new Vec3(this.f_31176_.f_31080_.m_20185_() - this.f_31176_.m_20185_(), 0.0D, this.f_31176_.f_31080_.m_20189_() - this.f_31176_.m_20189_())).m_82541_();
         float f = 5.0F;
         double d0 = this.f_31176_.f_31080_.m_20185_() + vec3.f_82479_ * 5.0D / 2.0D;
         double d1 = this.f_31176_.f_31080_.m_20189_() + vec3.f_82481_ * 5.0D / 2.0D;
         double d2 = this.f_31176_.f_31080_.m_20227_(0.5D);
         double d3 = d2;
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(d0, d2, d1);

         while(this.f_31176_.f_19853_.m_46859_(blockpos$mutableblockpos)) {
            --d3;
            if (d3 < 0.0D) {
               d3 = d2;
               break;
            }

            blockpos$mutableblockpos.m_122169_(d0, d3, d1);
         }

         d3 = (double)(Mth.m_14107_(d3) + 1);
         this.f_31328_ = new AreaEffectCloud(this.f_31176_.f_19853_, d0, d3, d1);
         this.f_31328_.m_19718_(this.f_31176_);
         this.f_31328_.m_19712_(5.0F);
         this.f_31328_.m_19734_(200);
         this.f_31328_.m_19724_(ParticleTypes.f_123799_);
         this.f_31328_.m_19716_(new MobEffectInstance(MobEffects.f_19602_));
         this.f_31176_.f_19853_.m_7967_(this.f_31328_);
      }

   }

   public void m_7083_() {
      this.f_31326_ = 0;
      ++this.f_31327_;
   }

   public void m_7081_() {
      if (this.f_31328_ != null) {
         this.f_31328_.m_146870_();
         this.f_31328_ = null;
      }

   }

   public EnderDragonPhase<DragonSittingFlamingPhase> m_7309_() {
      return EnderDragonPhase.f_31382_;
   }

   public void m_31336_() {
      this.f_31327_ = 0;
   }
}