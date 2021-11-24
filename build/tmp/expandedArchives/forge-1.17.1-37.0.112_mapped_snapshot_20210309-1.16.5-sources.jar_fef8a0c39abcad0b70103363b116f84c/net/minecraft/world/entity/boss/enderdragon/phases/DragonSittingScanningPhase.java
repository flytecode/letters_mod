package net.minecraft.world.entity.boss.enderdragon.phases;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.phys.Vec3;

public class DragonSittingScanningPhase extends AbstractDragonSittingPhase {
   private static final int f_149582_ = 100;
   private static final int f_149583_ = 10;
   private static final int f_149584_ = 20;
   private static final int f_149585_ = 150;
   private static final TargetingConditions f_31337_ = TargetingConditions.m_148352_().m_26883_(150.0D);
   private final TargetingConditions f_31338_;
   private int f_31339_;

   public DragonSittingScanningPhase(EnderDragon p_31342_) {
      super(p_31342_);
      this.f_31338_ = TargetingConditions.m_148352_().m_26883_(20.0D).m_26888_((p_31345_) -> {
         return Math.abs(p_31345_.m_20186_() - p_31342_.m_20186_()) <= 10.0D;
      });
   }

   public void m_6989_() {
      ++this.f_31339_;
      LivingEntity livingentity = this.f_31176_.f_19853_.m_45949_(this.f_31338_, this.f_31176_, this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
      if (livingentity != null) {
         if (this.f_31339_ > 25) {
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31384_);
         } else {
            Vec3 vec3 = (new Vec3(livingentity.m_20185_() - this.f_31176_.m_20185_(), 0.0D, livingentity.m_20189_() - this.f_31176_.m_20189_())).m_82541_();
            Vec3 vec31 = (new Vec3((double)Mth.m_14031_(this.f_31176_.m_146908_() * ((float)Math.PI / 180F)), 0.0D, (double)(-Mth.m_14089_(this.f_31176_.m_146908_() * ((float)Math.PI / 180F))))).m_82541_();
            float f = (float)vec31.m_82526_(vec3);
            float f1 = (float)(Math.acos((double)f) * (double)(180F / (float)Math.PI)) + 0.5F;
            if (f1 < 0.0F || f1 > 10.0F) {
               double d0 = livingentity.m_20185_() - this.f_31176_.f_31080_.m_20185_();
               double d1 = livingentity.m_20189_() - this.f_31176_.f_31080_.m_20189_();
               double d2 = Mth.m_14008_(Mth.m_14175_(180.0D - Mth.m_14136_(d0, d1) * (double)(180F / (float)Math.PI) - (double)this.f_31176_.m_146908_()), -100.0D, 100.0D);
               this.f_31176_.f_31085_ *= 0.8F;
               float f2 = (float)Math.sqrt(d0 * d0 + d1 * d1) + 1.0F;
               float f3 = f2;
               if (f2 > 40.0F) {
                  f2 = 40.0F;
               }

               this.f_31176_.f_31085_ = (float)((double)this.f_31176_.f_31085_ + d2 * (double)(0.7F / f2 / f3));
               this.f_31176_.m_146922_(this.f_31176_.m_146908_() + this.f_31176_.f_31085_);
            }
         }
      } else if (this.f_31339_ >= 100) {
         livingentity = this.f_31176_.f_19853_.m_45949_(f_31337_, this.f_31176_, this.f_31176_.m_20185_(), this.f_31176_.m_20186_(), this.f_31176_.m_20189_());
         this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31381_);
         if (livingentity != null) {
            this.f_31176_.m_31157_().m_31416_(EnderDragonPhase.f_31385_);
            this.f_31176_.m_31157_().m_31418_(EnderDragonPhase.f_31385_).m_31207_(new Vec3(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_()));
         }
      }

   }

   public void m_7083_() {
      this.f_31339_ = 0;
   }

   public EnderDragonPhase<DragonSittingScanningPhase> m_7309_() {
      return EnderDragonPhase.f_31383_;
   }
}