package net.minecraft.world.entity.boss.enderdragon.phases;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;
import net.minecraft.world.phys.Vec3;

public class DragonDeathPhase extends AbstractDragonPhaseInstance {
   private Vec3 f_31214_;
   private int f_31215_;

   public DragonDeathPhase(EnderDragon p_31217_) {
      super(p_31217_);
   }

   public void m_6991_() {
      if (this.f_31215_++ % 10 == 0) {
         float f = (this.f_31176_.m_21187_().nextFloat() - 0.5F) * 8.0F;
         float f1 = (this.f_31176_.m_21187_().nextFloat() - 0.5F) * 4.0F;
         float f2 = (this.f_31176_.m_21187_().nextFloat() - 0.5F) * 8.0F;
         this.f_31176_.f_19853_.m_7106_(ParticleTypes.f_123812_, this.f_31176_.m_20185_() + (double)f, this.f_31176_.m_20186_() + 2.0D + (double)f1, this.f_31176_.m_20189_() + (double)f2, 0.0D, 0.0D, 0.0D);
      }

   }

   public void m_6989_() {
      ++this.f_31215_;
      if (this.f_31214_ == null) {
         BlockPos blockpos = this.f_31176_.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING, EndPodiumFeature.f_65714_);
         this.f_31214_ = Vec3.m_82539_(blockpos);
      }

      double d0 = this.f_31214_.m_82531_(this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
      if (!(d0 < 100.0D) && !(d0 > 22500.0D) && !this.f_31176_.f_19862_ && !this.f_31176_.f_19863_) {
         this.f_31176_.m_21153_(1.0F);
      } else {
         this.f_31176_.m_21153_(0.0F);
      }

   }

   public void m_7083_() {
      this.f_31214_ = null;
      this.f_31215_ = 0;
   }

   public float m_7072_() {
      return 3.0F;
   }

   @Nullable
   public Vec3 m_5535_() {
      return this.f_31214_;
   }

   public EnderDragonPhase<DragonDeathPhase> m_7309_() {
      return EnderDragonPhase.f_31386_;
   }
}