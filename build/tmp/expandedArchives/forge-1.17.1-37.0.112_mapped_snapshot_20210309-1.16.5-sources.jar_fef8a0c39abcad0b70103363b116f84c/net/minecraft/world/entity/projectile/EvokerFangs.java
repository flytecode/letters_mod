package net.minecraft.world.entity.projectile;

import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class EvokerFangs extends Entity {
   public static final int f_150133_ = 20;
   public static final int f_150134_ = 2;
   public static final int f_150135_ = 14;
   private int f_36916_;
   private boolean f_36917_;
   private int f_36918_ = 22;
   private boolean f_36919_;
   @Nullable
   private LivingEntity f_36920_;
   @Nullable
   private UUID f_36921_;

   public EvokerFangs(EntityType<? extends EvokerFangs> p_36923_, Level p_36924_) {
      super(p_36923_, p_36924_);
   }

   public EvokerFangs(Level p_36926_, double p_36927_, double p_36928_, double p_36929_, float p_36930_, int p_36931_, LivingEntity p_36932_) {
      this(EntityType.f_20569_, p_36926_);
      this.f_36916_ = p_36931_;
      this.m_36938_(p_36932_);
      this.m_146922_(p_36930_ * (180F / (float)Math.PI));
      this.m_6034_(p_36927_, p_36928_, p_36929_);
   }

   protected void m_8097_() {
   }

   public void m_36938_(@Nullable LivingEntity p_36939_) {
      this.f_36920_ = p_36939_;
      this.f_36921_ = p_36939_ == null ? null : p_36939_.m_142081_();
   }

   @Nullable
   public LivingEntity m_36947_() {
      if (this.f_36920_ == null && this.f_36921_ != null && this.f_19853_ instanceof ServerLevel) {
         Entity entity = ((ServerLevel)this.f_19853_).m_8791_(this.f_36921_);
         if (entity instanceof LivingEntity) {
            this.f_36920_ = (LivingEntity)entity;
         }
      }

      return this.f_36920_;
   }

   protected void m_7378_(CompoundTag p_36941_) {
      this.f_36916_ = p_36941_.m_128451_("Warmup");
      if (p_36941_.m_128403_("Owner")) {
         this.f_36921_ = p_36941_.m_128342_("Owner");
      }

   }

   protected void m_7380_(CompoundTag p_36943_) {
      p_36943_.m_128405_("Warmup", this.f_36916_);
      if (this.f_36921_ != null) {
         p_36943_.m_128362_("Owner", this.f_36921_);
      }

   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_) {
         if (this.f_36919_) {
            --this.f_36918_;
            if (this.f_36918_ == 14) {
               for(int i = 0; i < 12; ++i) {
                  double d0 = this.m_20185_() + (this.f_19796_.nextDouble() * 2.0D - 1.0D) * (double)this.m_20205_() * 0.5D;
                  double d1 = this.m_20186_() + 0.05D + this.f_19796_.nextDouble();
                  double d2 = this.m_20189_() + (this.f_19796_.nextDouble() * 2.0D - 1.0D) * (double)this.m_20205_() * 0.5D;
                  double d3 = (this.f_19796_.nextDouble() * 2.0D - 1.0D) * 0.3D;
                  double d4 = 0.3D + this.f_19796_.nextDouble() * 0.3D;
                  double d5 = (this.f_19796_.nextDouble() * 2.0D - 1.0D) * 0.3D;
                  this.f_19853_.m_7106_(ParticleTypes.f_123797_, d0, d1 + 1.0D, d2, d3, d4, d5);
               }
            }
         }
      } else if (--this.f_36916_ < 0) {
         if (this.f_36916_ == -8) {
            for(LivingEntity livingentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_142469_().m_82377_(0.2D, 0.0D, 0.2D))) {
               this.m_36944_(livingentity);
            }
         }

         if (!this.f_36917_) {
            this.f_19853_.m_7605_(this, (byte)4);
            this.f_36917_ = true;
         }

         if (--this.f_36918_ < 0) {
            this.m_146870_();
         }
      }

   }

   private void m_36944_(LivingEntity p_36945_) {
      LivingEntity livingentity = this.m_36947_();
      if (p_36945_.m_6084_() && !p_36945_.m_20147_() && p_36945_ != livingentity) {
         if (livingentity == null) {
            p_36945_.m_6469_(DamageSource.f_19319_, 6.0F);
         } else {
            if (livingentity.m_7307_(p_36945_)) {
               return;
            }

            p_36945_.m_6469_(DamageSource.m_19367_(this, livingentity), 6.0F);
         }

      }
   }

   public void m_7822_(byte p_36935_) {
      super.m_7822_(p_36935_);
      if (p_36935_ == 4) {
         this.f_36919_ = true;
         if (!this.m_20067_()) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_11865_, this.m_5720_(), 1.0F, this.f_19796_.nextFloat() * 0.2F + 0.85F, false);
         }
      }

   }

   public float m_36936_(float p_36937_) {
      if (!this.f_36919_) {
         return 0.0F;
      } else {
         int i = this.f_36918_ - 2;
         return i <= 0 ? 1.0F : 1.0F - ((float)i - p_36937_) / 20.0F;
      }
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddEntityPacket(this);
   }
}