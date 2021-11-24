package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Blaze extends Monster {
   private float f_32214_ = 0.5F;
   private int f_32215_;
   private static final EntityDataAccessor<Byte> f_32216_ = SynchedEntityData.m_135353_(Blaze.class, EntityDataSerializers.f_135027_);

   public Blaze(EntityType<? extends Blaze> p_32219_, Level p_32220_) {
      super(p_32219_, p_32220_);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      this.m_21441_(BlockPathTypes.LAVA, 8.0F);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_FIRE, 0.0F);
      this.f_21364_ = 10;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(4, new Blaze.BlazeAttackGoal(this));
      this.f_21345_.m_25352_(5, new MoveTowardsRestrictionGoal(this, 1.0D));
      this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal(this, 1.0D, 0.0F));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this)).m_26044_());
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
   }

   public static AttributeSupplier.Builder m_32238_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22281_, 6.0D).m_22268_(Attributes.f_22279_, (double)0.23F).m_22268_(Attributes.f_22277_, 48.0D);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_32216_, (byte)0);
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_11701_;
   }

   protected SoundEvent m_7975_(DamageSource p_32235_) {
      return SoundEvents.f_11704_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11703_;
   }

   public float m_6073_() {
      return 1.0F;
   }

   public void m_8107_() {
      if (!this.f_19861_ && this.m_20184_().f_82480_ < 0.0D) {
         this.m_20256_(this.m_20184_().m_82542_(1.0D, 0.6D, 1.0D));
      }

      if (this.f_19853_.f_46443_) {
         if (this.f_19796_.nextInt(24) == 0 && !this.m_20067_()) {
            this.f_19853_.m_7785_(this.m_20185_() + 0.5D, this.m_20186_() + 0.5D, this.m_20189_() + 0.5D, SoundEvents.f_11702_, this.m_5720_(), 1.0F + this.f_19796_.nextFloat(), this.f_19796_.nextFloat() * 0.7F + 0.3F, false);
         }

         for(int i = 0; i < 2; ++i) {
            this.f_19853_.m_7106_(ParticleTypes.f_123755_, this.m_20208_(0.5D), this.m_20187_(), this.m_20262_(0.5D), 0.0D, 0.0D, 0.0D);
         }
      }

      super.m_8107_();
   }

   public boolean m_6126_() {
      return true;
   }

   protected void m_8024_() {
      --this.f_32215_;
      if (this.f_32215_ <= 0) {
         this.f_32215_ = 100;
         this.f_32214_ = 0.5F + (float)this.f_19796_.nextGaussian() * 3.0F;
      }

      LivingEntity livingentity = this.m_5448_();
      if (livingentity != null && livingentity.m_20188_() > this.m_20188_() + (double)this.f_32214_ && this.m_6779_(livingentity)) {
         Vec3 vec3 = this.m_20184_();
         this.m_20256_(this.m_20184_().m_82520_(0.0D, ((double)0.3F - vec3.f_82480_) * (double)0.3F, 0.0D));
         this.f_19812_ = true;
      }

      super.m_8024_();
   }

   public boolean m_142535_(float p_149683_, float p_149684_, DamageSource p_149685_) {
      return false;
   }

   public boolean m_6060_() {
      return this.m_32236_();
   }

   private boolean m_32236_() {
      return (this.f_19804_.m_135370_(f_32216_) & 1) != 0;
   }

   void m_32240_(boolean p_32241_) {
      byte b0 = this.f_19804_.m_135370_(f_32216_);
      if (p_32241_) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 = (byte)(b0 & -2);
      }

      this.f_19804_.m_135381_(f_32216_, b0);
   }

   static class BlazeAttackGoal extends Goal {
      private final Blaze f_32242_;
      private int f_32243_;
      private int f_32244_;
      private int f_32245_;

      public BlazeAttackGoal(Blaze p_32247_) {
         this.f_32242_ = p_32247_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         LivingEntity livingentity = this.f_32242_.m_5448_();
         return livingentity != null && livingentity.m_6084_() && this.f_32242_.m_6779_(livingentity);
      }

      public void m_8056_() {
         this.f_32243_ = 0;
      }

      public void m_8041_() {
         this.f_32242_.m_32240_(false);
         this.f_32245_ = 0;
      }

      public void m_8037_() {
         --this.f_32244_;
         LivingEntity livingentity = this.f_32242_.m_5448_();
         if (livingentity != null) {
            boolean flag = this.f_32242_.m_21574_().m_148306_(livingentity);
            if (flag) {
               this.f_32245_ = 0;
            } else {
               ++this.f_32245_;
            }

            double d0 = this.f_32242_.m_20280_(livingentity);
            if (d0 < 4.0D) {
               if (!flag) {
                  return;
               }

               if (this.f_32244_ <= 0) {
                  this.f_32244_ = 20;
                  this.f_32242_.m_7327_(livingentity);
               }

               this.f_32242_.m_21566_().m_6849_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_(), 1.0D);
            } else if (d0 < this.m_32252_() * this.m_32252_() && flag) {
               double d1 = livingentity.m_20185_() - this.f_32242_.m_20185_();
               double d2 = livingentity.m_20227_(0.5D) - this.f_32242_.m_20227_(0.5D);
               double d3 = livingentity.m_20189_() - this.f_32242_.m_20189_();
               if (this.f_32244_ <= 0) {
                  ++this.f_32243_;
                  if (this.f_32243_ == 1) {
                     this.f_32244_ = 60;
                     this.f_32242_.m_32240_(true);
                  } else if (this.f_32243_ <= 4) {
                     this.f_32244_ = 6;
                  } else {
                     this.f_32244_ = 100;
                     this.f_32243_ = 0;
                     this.f_32242_.m_32240_(false);
                  }

                  if (this.f_32243_ > 1) {
                     double d4 = Math.sqrt(Math.sqrt(d0)) * 0.5D;
                     if (!this.f_32242_.m_20067_()) {
                        this.f_32242_.f_19853_.m_5898_((Player)null, 1018, this.f_32242_.m_142538_(), 0);
                     }

                     for(int i = 0; i < 1; ++i) {
                        SmallFireball smallfireball = new SmallFireball(this.f_32242_.f_19853_, this.f_32242_, d1 + this.f_32242_.m_21187_().nextGaussian() * d4, d2, d3 + this.f_32242_.m_21187_().nextGaussian() * d4);
                        smallfireball.m_6034_(smallfireball.m_20185_(), this.f_32242_.m_20227_(0.5D) + 0.5D, smallfireball.m_20189_());
                        this.f_32242_.f_19853_.m_7967_(smallfireball);
                     }
                  }
               }

               this.f_32242_.m_21563_().m_24960_(livingentity, 10.0F, 10.0F);
            } else if (this.f_32245_ < 5) {
               this.f_32242_.m_21566_().m_6849_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_(), 1.0D);
            }

            super.m_8037_();
         }
      }

      private double m_32252_() {
         return this.f_32242_.m_21133_(Attributes.f_22277_);
      }
   }
}