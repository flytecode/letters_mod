package net.minecraft.world.entity.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class WitherSkull extends AbstractHurtingProjectile {
   private static final EntityDataAccessor<Boolean> f_37595_ = SynchedEntityData.m_135353_(WitherSkull.class, EntityDataSerializers.f_135035_);

   public WitherSkull(EntityType<? extends WitherSkull> p_37598_, Level p_37599_) {
      super(p_37598_, p_37599_);
   }

   public WitherSkull(Level p_37609_, LivingEntity p_37610_, double p_37611_, double p_37612_, double p_37613_) {
      super(EntityType.f_20498_, p_37610_, p_37611_, p_37612_, p_37613_, p_37609_);
   }

   protected float m_6884_() {
      return this.m_37635_() ? 0.73F : super.m_6884_();
   }

   public boolean m_6060_() {
      return false;
   }

   public float m_7077_(Explosion p_37619_, BlockGetter p_37620_, BlockPos p_37621_, BlockState p_37622_, FluidState p_37623_, float p_37624_) {
      return this.m_37635_() && p_37622_.canEntityDestroy(p_37620_, p_37621_, this) ? Math.min(0.8F, p_37624_) : p_37624_;
   }

   protected void m_5790_(EntityHitResult p_37626_) {
      super.m_5790_(p_37626_);
      if (!this.f_19853_.f_46443_) {
         Entity entity = p_37626_.m_82443_();
         Entity entity1 = this.m_37282_();
         boolean flag;
         if (entity1 instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity1;
            flag = entity.m_6469_(DamageSource.m_19355_(this, livingentity), 8.0F);
            if (flag) {
               if (entity.m_6084_()) {
                  this.m_19970_(livingentity, entity);
               } else {
                  livingentity.m_5634_(5.0F);
               }
            }
         } else {
            flag = entity.m_6469_(DamageSource.f_19319_, 5.0F);
         }

         if (flag && entity instanceof LivingEntity) {
            int i = 0;
            if (this.f_19853_.m_46791_() == Difficulty.NORMAL) {
               i = 10;
            } else if (this.f_19853_.m_46791_() == Difficulty.HARD) {
               i = 40;
            }

            if (i > 0) {
               ((LivingEntity)entity).m_147207_(new MobEffectInstance(MobEffects.f_19615_, 20 * i, 1), this.m_150173_());
            }
         }

      }
   }

   protected void m_6532_(HitResult p_37628_) {
      super.m_6532_(p_37628_);
      if (!this.f_19853_.f_46443_) {
         Explosion.BlockInteraction explosion$blockinteraction = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this.m_37282_()) ? Explosion.BlockInteraction.DESTROY : Explosion.BlockInteraction.NONE;
         this.f_19853_.m_46518_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), 1.0F, false, explosion$blockinteraction);
         this.m_146870_();
      }

   }

   public boolean m_6087_() {
      return false;
   }

   public boolean m_6469_(DamageSource p_37616_, float p_37617_) {
      return false;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(f_37595_, false);
   }

   public boolean m_37635_() {
      return this.f_19804_.m_135370_(f_37595_);
   }

   public void m_37629_(boolean p_37630_) {
      this.f_19804_.m_135381_(f_37595_, p_37630_);
   }

   protected boolean m_5931_() {
      return false;
   }
}
