package net.minecraft.world.entity.animal;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class Squid extends WaterAnimal {
   public float f_29938_;
   public float f_29950_;
   public float f_29951_;
   public float f_29939_;
   public float f_29940_;
   public float f_29941_;
   public float f_29942_;
   public float f_29943_;
   private float f_29944_;
   private float f_29945_;
   private float f_29946_;
   private float f_29947_;
   private float f_29948_;
   private float f_29949_;

   public Squid(EntityType<? extends Squid> p_29953_, Level p_29954_) {
      super(p_29953_, p_29954_);
      this.f_19796_.setSeed((long)this.m_142049_());
      this.f_29945_ = 1.0F / (this.f_19796_.nextFloat() + 1.0F) * 0.2F;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new Squid.SquidRandomMovementGoal(this));
      this.f_21345_.m_25352_(1, new Squid.SquidFleeGoal());
   }

   public static AttributeSupplier.Builder m_29988_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D);
   }

   protected float m_6431_(Pose p_29975_, EntityDimensions p_29976_) {
      return p_29976_.f_20378_ * 0.5F;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12438_;
   }

   protected SoundEvent m_7975_(DamageSource p_29980_) {
      return SoundEvents.f_12440_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12439_;
   }

   protected SoundEvent m_142555_() {
      return SoundEvents.f_12441_;
   }

   public boolean m_6573_(Player p_149052_) {
      return !this.m_21523_();
   }

   protected float m_6121_() {
      return 0.4F;
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.EVENTS;
   }

   public void m_8107_() {
      super.m_8107_();
      this.f_29950_ = this.f_29938_;
      this.f_29939_ = this.f_29951_;
      this.f_29941_ = this.f_29940_;
      this.f_29943_ = this.f_29942_;
      this.f_29940_ += this.f_29945_;
      if ((double)this.f_29940_ > (Math.PI * 2D)) {
         if (this.f_19853_.f_46443_) {
            this.f_29940_ = ((float)Math.PI * 2F);
         } else {
            this.f_29940_ = (float)((double)this.f_29940_ - (Math.PI * 2D));
            if (this.f_19796_.nextInt(10) == 0) {
               this.f_29945_ = 1.0F / (this.f_19796_.nextFloat() + 1.0F) * 0.2F;
            }

            this.f_19853_.m_7605_(this, (byte)19);
         }
      }

      if (this.m_20072_()) {
         if (this.f_29940_ < (float)Math.PI) {
            float f = this.f_29940_ / (float)Math.PI;
            this.f_29942_ = Mth.m_14031_(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;
            if ((double)f > 0.75D) {
               this.f_29944_ = 1.0F;
               this.f_29946_ = 1.0F;
            } else {
               this.f_29946_ *= 0.8F;
            }
         } else {
            this.f_29942_ = 0.0F;
            this.f_29944_ *= 0.9F;
            this.f_29946_ *= 0.99F;
         }

         if (!this.f_19853_.f_46443_) {
            this.m_20334_((double)(this.f_29947_ * this.f_29944_), (double)(this.f_29948_ * this.f_29944_), (double)(this.f_29949_ * this.f_29944_));
         }

         Vec3 vec3 = this.m_20184_();
         double d0 = vec3.m_165924_();
         this.f_20883_ += (-((float)Mth.m_14136_(vec3.f_82479_, vec3.f_82481_)) * (180F / (float)Math.PI) - this.f_20883_) * 0.1F;
         this.m_146922_(this.f_20883_);
         this.f_29951_ = (float)((double)this.f_29951_ + Math.PI * (double)this.f_29946_ * 1.5D);
         this.f_29938_ += (-((float)Mth.m_14136_(d0, vec3.f_82480_)) * (180F / (float)Math.PI) - this.f_29938_) * 0.1F;
      } else {
         this.f_29942_ = Mth.m_14154_(Mth.m_14031_(this.f_29940_)) * (float)Math.PI * 0.25F;
         if (!this.f_19853_.f_46443_) {
            double d1 = this.m_20184_().f_82480_;
            if (this.m_21023_(MobEffects.f_19620_)) {
               d1 = 0.05D * (double)(this.m_21124_(MobEffects.f_19620_).m_19564_() + 1);
            } else if (!this.m_20068_()) {
               d1 -= 0.08D;
            }

            this.m_20334_(0.0D, d1 * (double)0.98F, 0.0D);
         }

         this.f_29938_ = (float)((double)this.f_29938_ + (double)(-90.0F - this.f_29938_) * 0.02D);
      }

   }

   public boolean m_6469_(DamageSource p_29963_, float p_29964_) {
      if (super.m_6469_(p_29963_, p_29964_) && this.m_142581_() != null) {
         this.m_29982_();
         return true;
      } else {
         return false;
      }
   }

   private Vec3 m_29985_(Vec3 p_29986_) {
      Vec3 vec3 = p_29986_.m_82496_(this.f_29950_ * ((float)Math.PI / 180F));
      return vec3.m_82524_(-this.f_20884_ * ((float)Math.PI / 180F));
   }

   private void m_29982_() {
      this.m_5496_(this.m_142555_(), this.m_6121_(), this.m_6100_());
      Vec3 vec3 = this.m_29985_(new Vec3(0.0D, -1.0D, 0.0D)).m_82520_(this.m_20185_(), this.m_20186_(), this.m_20189_());

      for(int i = 0; i < 30; ++i) {
         Vec3 vec31 = this.m_29985_(new Vec3((double)this.f_19796_.nextFloat() * 0.6D - 0.3D, -1.0D, (double)this.f_19796_.nextFloat() * 0.6D - 0.3D));
         Vec3 vec32 = vec31.m_82490_(0.3D + (double)(this.f_19796_.nextFloat() * 2.0F));
         ((ServerLevel)this.f_19853_).m_8767_(this.m_142033_(), vec3.f_82479_, vec3.f_82480_ + 0.5D, vec3.f_82481_, 0, vec32.f_82479_, vec32.f_82480_, vec32.f_82481_, (double)0.1F);
      }

   }

   protected ParticleOptions m_142033_() {
      return ParticleTypes.f_123765_;
   }

   public void m_7023_(Vec3 p_29984_) {
      this.m_6478_(MoverType.SELF, this.m_20184_());
   }

   public static boolean m_29968_(EntityType<Squid> p_29969_, LevelAccessor p_29970_, MobSpawnType p_29971_, BlockPos p_29972_, Random p_29973_) {
      return p_29972_.m_123342_() > 45 && p_29972_.m_123342_() < p_29970_.m_5736_();
   }

   public void m_7822_(byte p_29957_) {
      if (p_29957_ == 19) {
         this.f_29940_ = 0.0F;
      } else {
         super.m_7822_(p_29957_);
      }

   }

   public void m_29958_(float p_29959_, float p_29960_, float p_29961_) {
      this.f_29947_ = p_29959_;
      this.f_29948_ = p_29960_;
      this.f_29949_ = p_29961_;
   }

   public boolean m_29981_() {
      return this.f_29947_ != 0.0F || this.f_29948_ != 0.0F || this.f_29949_ != 0.0F;
   }

   class SquidFleeGoal extends Goal {
      private static final float f_149054_ = 3.0F;
      private static final float f_149055_ = 5.0F;
      private static final float f_149056_ = 10.0F;
      private int f_29991_;

      public boolean m_8036_() {
         LivingEntity livingentity = Squid.this.m_142581_();
         if (Squid.this.m_20069_() && livingentity != null) {
            return Squid.this.m_20280_(livingentity) < 100.0D;
         } else {
            return false;
         }
      }

      public void m_8056_() {
         this.f_29991_ = 0;
      }

      public void m_8037_() {
         ++this.f_29991_;
         LivingEntity livingentity = Squid.this.m_142581_();
         if (livingentity != null) {
            Vec3 vec3 = new Vec3(Squid.this.m_20185_() - livingentity.m_20185_(), Squid.this.m_20186_() - livingentity.m_20186_(), Squid.this.m_20189_() - livingentity.m_20189_());
            BlockState blockstate = Squid.this.f_19853_.m_8055_(new BlockPos(Squid.this.m_20185_() + vec3.f_82479_, Squid.this.m_20186_() + vec3.f_82480_, Squid.this.m_20189_() + vec3.f_82481_));
            FluidState fluidstate = Squid.this.f_19853_.m_6425_(new BlockPos(Squid.this.m_20185_() + vec3.f_82479_, Squid.this.m_20186_() + vec3.f_82480_, Squid.this.m_20189_() + vec3.f_82481_));
            if (fluidstate.m_76153_(FluidTags.f_13131_) || blockstate.m_60795_()) {
               double d0 = vec3.m_82553_();
               if (d0 > 0.0D) {
                  vec3.m_82541_();
                  float f = 3.0F;
                  if (d0 > 5.0D) {
                     f = (float)((double)f - (d0 - 5.0D) / 5.0D);
                  }

                  if (f > 0.0F) {
                     vec3 = vec3.m_82490_((double)f);
                  }
               }

               if (blockstate.m_60795_()) {
                  vec3 = vec3.m_82492_(0.0D, vec3.f_82480_, 0.0D);
               }

               Squid.this.m_29958_((float)vec3.f_82479_ / 20.0F, (float)vec3.f_82480_ / 20.0F, (float)vec3.f_82481_ / 20.0F);
            }

            if (this.f_29991_ % 10 == 5) {
               Squid.this.f_19853_.m_7106_(ParticleTypes.f_123795_, Squid.this.m_20185_(), Squid.this.m_20186_(), Squid.this.m_20189_(), 0.0D, 0.0D, 0.0D);
            }

         }
      }
   }

   class SquidRandomMovementGoal extends Goal {
      private final Squid f_30001_;

      public SquidRandomMovementGoal(Squid p_30004_) {
         this.f_30001_ = p_30004_;
      }

      public boolean m_8036_() {
         return true;
      }

      public void m_8037_() {
         int i = this.f_30001_.m_21216_();
         if (i > 100) {
            this.f_30001_.m_29958_(0.0F, 0.0F, 0.0F);
         } else if (this.f_30001_.m_21187_().nextInt(50) == 0 || !this.f_30001_.f_19798_ || !this.f_30001_.m_29981_()) {
            float f = this.f_30001_.m_21187_().nextFloat() * ((float)Math.PI * 2F);
            float f1 = Mth.m_14089_(f) * 0.2F;
            float f2 = -0.1F + this.f_30001_.m_21187_().nextFloat() * 0.2F;
            float f3 = Mth.m_14031_(f) * 0.2F;
            this.f_30001_.m_29958_(f1, f2, f3);
         }

      }
   }
}