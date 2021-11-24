package net.minecraft.world.entity.projectile;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractHurtingProjectile extends Projectile {
   public double f_36813_;
   public double f_36814_;
   public double f_36815_;

   protected AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> p_36833_, Level p_36834_) {
      super(p_36833_, p_36834_);
   }

   public AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> p_36817_, double p_36818_, double p_36819_, double p_36820_, double p_36821_, double p_36822_, double p_36823_, Level p_36824_) {
      this(p_36817_, p_36824_);
      this.m_7678_(p_36818_, p_36819_, p_36820_, this.m_146908_(), this.m_146909_());
      this.m_20090_();
      double d0 = Math.sqrt(p_36821_ * p_36821_ + p_36822_ * p_36822_ + p_36823_ * p_36823_);
      if (d0 != 0.0D) {
         this.f_36813_ = p_36821_ / d0 * 0.1D;
         this.f_36814_ = p_36822_ / d0 * 0.1D;
         this.f_36815_ = p_36823_ / d0 * 0.1D;
      }

   }

   public AbstractHurtingProjectile(EntityType<? extends AbstractHurtingProjectile> p_36826_, LivingEntity p_36827_, double p_36828_, double p_36829_, double p_36830_, Level p_36831_) {
      this(p_36826_, p_36827_.m_20185_(), p_36827_.m_20186_(), p_36827_.m_20189_(), p_36828_, p_36829_, p_36830_, p_36831_);
      this.m_5602_(p_36827_);
      this.m_19915_(p_36827_.m_146908_(), p_36827_.m_146909_());
   }

   protected void m_8097_() {
   }

   public boolean m_6783_(double p_36837_) {
      double d0 = this.m_142469_().m_82309_() * 4.0D;
      if (Double.isNaN(d0)) {
         d0 = 4.0D;
      }

      d0 = d0 * 64.0D;
      return p_36837_ < d0 * d0;
   }

   public void m_8119_() {
      Entity entity = this.m_37282_();
      if (this.f_19853_.f_46443_ || (entity == null || !entity.m_146910_()) && this.f_19853_.m_46805_(this.m_142538_())) {
         super.m_8119_();
         if (this.m_5931_()) {
            this.m_20254_(1);
         }

         HitResult hitresult = ProjectileUtil.m_37294_(this, this::m_5603_);
         if (hitresult.m_6662_() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
            this.m_6532_(hitresult);
         }

         this.m_20101_();
         Vec3 vec3 = this.m_20184_();
         double d0 = this.m_20185_() + vec3.f_82479_;
         double d1 = this.m_20186_() + vec3.f_82480_;
         double d2 = this.m_20189_() + vec3.f_82481_;
         ProjectileUtil.m_37284_(this, 0.2F);
         float f = this.m_6884_();
         if (this.m_20069_()) {
            for(int i = 0; i < 4; ++i) {
               float f1 = 0.25F;
               this.f_19853_.m_7106_(ParticleTypes.f_123795_, d0 - vec3.f_82479_ * 0.25D, d1 - vec3.f_82480_ * 0.25D, d2 - vec3.f_82481_ * 0.25D, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
            }

            f = 0.8F;
         }

         this.m_20256_(vec3.m_82520_(this.f_36813_, this.f_36814_, this.f_36815_).m_82490_((double)f));
         this.f_19853_.m_7106_(this.m_5967_(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
         this.m_6034_(d0, d1, d2);
      } else {
         this.m_146870_();
      }
   }

   protected boolean m_5603_(Entity p_36842_) {
      return super.m_5603_(p_36842_) && !p_36842_.f_19794_;
   }

   protected boolean m_5931_() {
      return true;
   }

   protected ParticleOptions m_5967_() {
      return ParticleTypes.f_123762_;
   }

   protected float m_6884_() {
      return 0.95F;
   }

   public void m_7380_(CompoundTag p_36848_) {
      super.m_7380_(p_36848_);
      p_36848_.m_128365_("power", this.m_20063_(new double[]{this.f_36813_, this.f_36814_, this.f_36815_}));
   }

   public void m_7378_(CompoundTag p_36844_) {
      super.m_7378_(p_36844_);
      if (p_36844_.m_128425_("power", 9)) {
         ListTag listtag = p_36844_.m_128437_("power", 6);
         if (listtag.size() == 3) {
            this.f_36813_ = listtag.m_128772_(0);
            this.f_36814_ = listtag.m_128772_(1);
            this.f_36815_ = listtag.m_128772_(2);
         }
      }

   }

   public boolean m_6087_() {
      return true;
   }

   public float m_6143_() {
      return 1.0F;
   }

   public boolean m_6469_(DamageSource p_36839_, float p_36840_) {
      if (this.m_6673_(p_36839_)) {
         return false;
      } else {
         this.m_5834_();
         Entity entity = p_36839_.m_7639_();
         if (entity != null) {
            Vec3 vec3 = entity.m_20154_();
            this.m_20256_(vec3);
            this.f_36813_ = vec3.f_82479_ * 0.1D;
            this.f_36814_ = vec3.f_82480_ * 0.1D;
            this.f_36815_ = vec3.f_82481_ * 0.1D;
            this.m_5602_(entity);
            return true;
         } else {
            return false;
         }
      }
   }

   public float m_6073_() {
      return 1.0F;
   }

   public Packet<?> m_5654_() {
      Entity entity = this.m_37282_();
      int i = entity == null ? 0 : entity.m_142049_();
      return new ClientboundAddEntityPacket(this.m_142049_(), this.m_142081_(), this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146909_(), this.m_146908_(), this.m_6095_(), i, new Vec3(this.f_36813_, this.f_36814_, this.f_36815_));
   }

   public void m_141965_(ClientboundAddEntityPacket p_150128_) {
      super.m_141965_(p_150128_);
      double d0 = p_150128_.m_131503_();
      double d1 = p_150128_.m_131504_();
      double d2 = p_150128_.m_131505_();
      double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      if (d3 != 0.0D) {
         this.f_36813_ = d0 / d3 * 0.1D;
         this.f_36814_ = d1 / d3 * 0.1D;
         this.f_36815_ = d2 / d3 * 0.1D;
      }

   }
}
