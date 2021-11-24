package net.minecraft.world.entity.boss.enderdragon.phases;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;

public class DragonLandingPhase extends AbstractDragonPhaseInstance {
   private Vec3 f_31303_;

   public DragonLandingPhase(EnderDragon p_31305_) {
      super(p_31305_);
   }

   public void m_6991_() {
      Vec3 vec3 = this.f_31176_.m_31174_(1.0F).m_82541_();
      vec3.m_82524_((-(float)Math.PI / 4F));
      double d0 = this.f_31176_.f_31080_.m_20185_();
      double d1 = this.f_31176_.f_31080_.m_20227_(0.5D);
      double d2 = this.f_31176_.f_31080_.m_20189_();

      for(int i = 0; i < 8; ++i) {
         Random random = this.f_31176_.m_21187_();
         double d3 = d0 + random.nextGaussian() / 2.0D;
         double d4 = d1 + random.nextGaussian() / 2.0D;
         double d5 = d2 + random.nextGaussian() / 2.0D;
         Vec3 vec31 = this.f_31176_.m_20184_();
         this.f_31176_.f_19853_.m_7106_(ParticleTypes.f_123799_, d3, d4, d5, -vec3.f_82479_ * (double)0.08F + vec31.f_82479_, -vec3.f_82480_ * (double)0.3F + vec31.f_82480_, -vec3.f_82481_ * (double)0.08F + vec31.f_82481_);
         vec3.m_82524_(0.19634955F);
      }

   }

   public void m_6989_() {
      if (this.f_31303_ == null) {
         this.f_31303_ = Vec3.m_82539_(this.f_31176_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EndPodiumFeature.f_65714_));
      }

      if (this.f_31303_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_()) < 1.0D) {
         this.f_31176_.m_31157_().m_31418_(EnderDragonPhase.f_31382_).m_31336_();
         this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31383_);
      }

   }

   public float m_7072_() {
      return 1.5F;
   }

   public float m_7089_() {
      float f = (float)this.f_31176_.m_20184_().m_165924_() + 1.0F;
      float f1 = Math.min(f, 40.0F);
      return f1 / f;
   }

   public void m_7083_() {
      this.f_31303_ = null;
   }

   @Nullable
   public Vec3 m_5535_() {
      return this.f_31303_;
   }

   public EnderDragonPhase<DragonLandingPhase> m_7309_() {
      return EnderDragonPhase.f_31380_;
   }
}