package net.minecraft.world.entity.projectile;

import java.util.List;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class DragonFireball extends AbstractHurtingProjectile {
   public static final float f_150132_ = 4.0F;

   public DragonFireball(EntityType<? extends DragonFireball> p_36892_, Level p_36893_) {
      super(p_36892_, p_36893_);
   }

   public DragonFireball(Level p_36903_, LivingEntity p_36904_, double p_36905_, double p_36906_, double p_36907_) {
      super(EntityType.f_20561_, p_36904_, p_36905_, p_36906_, p_36907_, p_36903_);
   }

   protected void m_6532_(HitResult p_36913_) {
      super.m_6532_(p_36913_);
      if (p_36913_.m_6662_() != HitResult.Type.ENTITY || !this.m_150171_(((EntityHitResult)p_36913_).m_82443_())) {
         if (!this.f_19853_.f_46443_) {
            List<LivingEntity> list = this.f_19853_.m_45976_(LivingEntity.class, this.m_142469_().m_82377_(4.0D, 2.0D, 4.0D));
            AreaEffectCloud areaeffectcloud = new AreaEffectCloud(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_());
            Entity entity = this.m_37282_();
            if (entity instanceof LivingEntity) {
               areaeffectcloud.m_19718_((LivingEntity)entity);
            }

            areaeffectcloud.m_19724_(ParticleTypes.f_123799_);
            areaeffectcloud.m_19712_(3.0F);
            areaeffectcloud.m_19734_(600);
            areaeffectcloud.m_19738_((7.0F - areaeffectcloud.m_19743_()) / (float)areaeffectcloud.m_19748_());
            areaeffectcloud.m_19716_(new MobEffectInstance(MobEffects.f_19602_, 1, 1));
            if (!list.isEmpty()) {
               for(LivingEntity livingentity : list) {
                  double d0 = this.m_20280_(livingentity);
                  if (d0 < 16.0D) {
                     areaeffectcloud.m_6034_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_());
                     break;
                  }
               }
            }

            this.f_19853_.m_46796_(2006, this.m_142538_(), this.m_20067_() ? -1 : 1);
            this.f_19853_.m_7967_(areaeffectcloud);
            this.m_146870_();
         }

      }
   }

   public boolean m_6087_() {
      return false;
   }

   public boolean m_6469_(DamageSource p_36910_, float p_36911_) {
      return false;
   }

   protected ParticleOptions m_5967_() {
      return ParticleTypes.f_123799_;
   }

   protected boolean m_5931_() {
      return false;
   }
}