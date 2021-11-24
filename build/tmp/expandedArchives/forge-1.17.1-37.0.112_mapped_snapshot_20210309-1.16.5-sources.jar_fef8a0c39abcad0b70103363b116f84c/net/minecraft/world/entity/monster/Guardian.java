package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.animal.Squid;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Guardian extends Monster {
   protected static final int f_149711_ = 80;
   private static final EntityDataAccessor<Boolean> f_32797_ = SynchedEntityData.m_135353_(Guardian.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> f_32807_ = SynchedEntityData.m_135353_(Guardian.class, EntityDataSerializers.f_135028_);
   private float f_32798_;
   private float f_32799_;
   private float f_32800_;
   private float f_32801_;
   private float f_32802_;
   private LivingEntity f_32803_;
   private int f_32804_;
   private boolean f_32805_;
   protected RandomStrollGoal f_32806_;

   public Guardian(EntityType<? extends Guardian> p_32810_, Level p_32811_) {
      super(p_32810_, p_32811_);
      this.f_21364_ = 10;
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.f_21342_ = new Guardian.GuardianMoveControl(this);
      this.f_32798_ = this.f_19796_.nextFloat();
      this.f_32799_ = this.f_32798_;
   }

   protected void m_8099_() {
      MoveTowardsRestrictionGoal movetowardsrestrictiongoal = new MoveTowardsRestrictionGoal(this, 1.0D);
      this.f_32806_ = new RandomStrollGoal(this, 1.0D, 80);
      this.f_21345_.m_25352_(4, new Guardian.GuardianAttackGoal(this));
      this.f_21345_.m_25352_(5, movetowardsrestrictiongoal);
      this.f_21345_.m_25352_(7, this.f_32806_);
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Guardian.class, 12.0F, 0.01F));
      this.f_21345_.m_25352_(9, new RandomLookAroundGoal(this));
      this.f_32806_.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      movetowardsrestrictiongoal.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10, true, false, new Guardian.GuardianAttackSelector(this)));
   }

   public static AttributeSupplier.Builder m_32853_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22281_, 6.0D).m_22268_(Attributes.f_22279_, 0.5D).m_22268_(Attributes.f_22277_, 16.0D).m_22268_(Attributes.f_22276_, 30.0D);
   }

   protected PathNavigation m_6037_(Level p_32846_) {
      return new WaterBoundPathNavigation(this, p_32846_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_32797_, false);
      this.f_19804_.m_135372_(f_32807_, 0);
   }

   public boolean m_6040_() {
      return true;
   }

   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public boolean m_32854_() {
      return this.f_19804_.m_135370_(f_32797_);
   }

   void m_32861_(boolean p_32862_) {
      this.f_19804_.m_135381_(f_32797_, p_32862_);
   }

   public int m_7552_() {
      return 80;
   }

   void m_32817_(int p_32818_) {
      this.f_19804_.m_135381_(f_32807_, p_32818_);
   }

   public boolean m_32855_() {
      return this.f_19804_.m_135370_(f_32807_) != 0;
   }

   @Nullable
   public LivingEntity m_32856_() {
      if (!this.m_32855_()) {
         return null;
      } else if (this.f_19853_.f_46443_) {
         if (this.f_32803_ != null) {
            return this.f_32803_;
         } else {
            Entity entity = this.f_19853_.m_6815_(this.f_19804_.m_135370_(f_32807_));
            if (entity instanceof LivingEntity) {
               this.f_32803_ = (LivingEntity)entity;
               return this.f_32803_;
            } else {
               return null;
            }
         }
      } else {
         return this.m_5448_();
      }
   }

   public void m_7350_(EntityDataAccessor<?> p_32834_) {
      super.m_7350_(p_32834_);
      if (f_32807_.equals(p_32834_)) {
         this.f_32804_ = 0;
         this.f_32803_ = null;
      }

   }

   public int m_8100_() {
      return 160;
   }

   protected SoundEvent m_7515_() {
      return this.m_20072_() ? SoundEvents.f_11999_ : SoundEvents.f_12000_;
   }

   protected SoundEvent m_7975_(DamageSource p_32852_) {
      return this.m_20072_() ? SoundEvents.f_12005_ : SoundEvents.f_12006_;
   }

   protected SoundEvent m_5592_() {
      return this.m_20072_() ? SoundEvents.f_12002_ : SoundEvents.f_12003_;
   }

   protected Entity.MovementEmission m_142319_() {
      return Entity.MovementEmission.EVENTS;
   }

   protected float m_6431_(Pose p_32843_, EntityDimensions p_32844_) {
      return p_32844_.f_20378_ * 0.5F;
   }

   public float m_5610_(BlockPos p_32831_, LevelReader p_32832_) {
      return p_32832_.m_6425_(p_32831_).m_76153_(FluidTags.f_13131_) ? 10.0F + p_32832_.m_46863_(p_32831_) - 0.5F : super.m_5610_(p_32831_, p_32832_);
   }

   public void m_8107_() {
      if (this.m_6084_()) {
         if (this.f_19853_.f_46443_) {
            this.f_32799_ = this.f_32798_;
            if (!this.m_20069_()) {
               this.f_32800_ = 2.0F;
               Vec3 vec3 = this.m_20184_();
               if (vec3.f_82480_ > 0.0D && this.f_32805_ && !this.m_20067_()) {
                  this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_7868_(), this.m_5720_(), 1.0F, 1.0F, false);
               }

               this.f_32805_ = vec3.f_82480_ < 0.0D && this.f_19853_.m_46575_(this.m_142538_().m_7495_(), this);
            } else if (this.m_32854_()) {
               if (this.f_32800_ < 0.5F) {
                  this.f_32800_ = 4.0F;
               } else {
                  this.f_32800_ += (0.5F - this.f_32800_) * 0.1F;
               }
            } else {
               this.f_32800_ += (0.125F - this.f_32800_) * 0.2F;
            }

            this.f_32798_ += this.f_32800_;
            this.f_32802_ = this.f_32801_;
            if (!this.m_20072_()) {
               this.f_32801_ = this.f_19796_.nextFloat();
            } else if (this.m_32854_()) {
               this.f_32801_ += (0.0F - this.f_32801_) * 0.25F;
            } else {
               this.f_32801_ += (1.0F - this.f_32801_) * 0.06F;
            }

            if (this.m_32854_() && this.m_20069_()) {
               Vec3 vec31 = this.m_20252_(0.0F);

               for(int i = 0; i < 2; ++i) {
                  this.f_19853_.m_7106_(ParticleTypes.f_123795_, this.m_20208_(0.5D) - vec31.f_82479_ * 1.5D, this.m_20187_() - vec31.f_82480_ * 1.5D, this.m_20262_(0.5D) - vec31.f_82481_ * 1.5D, 0.0D, 0.0D, 0.0D);
               }
            }

            if (this.m_32855_()) {
               if (this.f_32804_ < this.m_7552_()) {
                  ++this.f_32804_;
               }

               LivingEntity livingentity = this.m_32856_();
               if (livingentity != null) {
                  this.m_21563_().m_24960_(livingentity, 90.0F, 90.0F);
                  this.m_21563_().m_8128_();
                  double d5 = (double)this.m_32812_(0.0F);
                  double d0 = livingentity.m_20185_() - this.m_20185_();
                  double d1 = livingentity.m_20227_(0.5D) - this.m_20188_();
                  double d2 = livingentity.m_20189_() - this.m_20189_();
                  double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                  d0 = d0 / d3;
                  d1 = d1 / d3;
                  d2 = d2 / d3;
                  double d4 = this.f_19796_.nextDouble();

                  while(d4 < d3) {
                     d4 += 1.8D - d5 + this.f_19796_.nextDouble() * (1.7D - d5);
                     this.f_19853_.m_7106_(ParticleTypes.f_123795_, this.m_20185_() + d0 * d4, this.m_20188_() + d1 * d4, this.m_20189_() + d2 * d4, 0.0D, 0.0D, 0.0D);
                  }
               }
            }
         }

         if (this.m_20072_()) {
            this.m_20301_(300);
         } else if (this.f_19861_) {
            this.m_20256_(this.m_20184_().m_82520_((double)((this.f_19796_.nextFloat() * 2.0F - 1.0F) * 0.4F), 0.5D, (double)((this.f_19796_.nextFloat() * 2.0F - 1.0F) * 0.4F)));
            this.m_146922_(this.f_19796_.nextFloat() * 360.0F);
            this.f_19861_ = false;
            this.f_19812_ = true;
         }

         if (this.m_32855_()) {
            this.m_146922_(this.f_20885_);
         }
      }

      super.m_8107_();
   }

   protected SoundEvent m_7868_() {
      return SoundEvents.f_12004_;
   }

   public float m_32863_(float p_32864_) {
      return Mth.m_14179_(p_32864_, this.f_32799_, this.f_32798_);
   }

   public float m_32865_(float p_32866_) {
      return Mth.m_14179_(p_32866_, this.f_32802_, this.f_32801_);
   }

   public float m_32812_(float p_32813_) {
      return ((float)this.f_32804_ + p_32813_) / (float)this.m_7552_();
   }

   public boolean m_6914_(LevelReader p_32829_) {
      return p_32829_.m_45784_(this);
   }

   public static boolean m_32836_(EntityType<? extends Guardian> p_32837_, LevelAccessor p_32838_, MobSpawnType p_32839_, BlockPos p_32840_, Random p_32841_) {
      return (p_32841_.nextInt(20) == 0 || !p_32838_.m_46861_(p_32840_)) && p_32838_.m_46791_() != Difficulty.PEACEFUL && (p_32839_ == MobSpawnType.SPAWNER || p_32838_.m_6425_(p_32840_).m_76153_(FluidTags.f_13131_));
   }

   public boolean m_6469_(DamageSource p_32820_, float p_32821_) {
      if (!this.m_32854_() && !p_32820_.m_19387_() && p_32820_.m_7640_() instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)p_32820_.m_7640_();
         if (!p_32820_.m_19372_()) {
            livingentity.m_6469_(DamageSource.m_19335_(this), 2.0F);
         }
      }

      if (this.f_32806_ != null) {
         this.f_32806_.m_25751_();
      }

      return super.m_6469_(p_32820_, p_32821_);
   }

   public int m_8132_() {
      return 180;
   }

   public void m_7023_(Vec3 p_32858_) {
      if (this.m_6142_() && this.m_20069_()) {
         this.m_19920_(0.1F, p_32858_);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9D));
         if (!this.m_32854_() && this.m_5448_() == null) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.005D, 0.0D));
         }
      } else {
         super.m_7023_(p_32858_);
      }

   }

   static class GuardianAttackGoal extends Goal {
      private final Guardian f_32867_;
      private int f_32868_;
      private final boolean f_32869_;

      public GuardianAttackGoal(Guardian p_32871_) {
         this.f_32867_ = p_32871_;
         this.f_32869_ = p_32871_ instanceof ElderGuardian;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         LivingEntity livingentity = this.f_32867_.m_5448_();
         return livingentity != null && livingentity.m_6084_();
      }

      public boolean m_8045_() {
         return super.m_8045_() && (this.f_32869_ || this.f_32867_.m_20280_(this.f_32867_.m_5448_()) > 9.0D);
      }

      public void m_8056_() {
         this.f_32868_ = -10;
         this.f_32867_.m_21573_().m_26573_();
         this.f_32867_.m_21563_().m_24960_(this.f_32867_.m_5448_(), 90.0F, 90.0F);
         this.f_32867_.f_19812_ = true;
      }

      public void m_8041_() {
         this.f_32867_.m_32817_(0);
         this.f_32867_.m_6710_((LivingEntity)null);
         this.f_32867_.f_32806_.m_25751_();
      }

      public void m_8037_() {
         LivingEntity livingentity = this.f_32867_.m_5448_();
         this.f_32867_.m_21573_().m_26573_();
         this.f_32867_.m_21563_().m_24960_(livingentity, 90.0F, 90.0F);
         if (!this.f_32867_.m_142582_(livingentity)) {
            this.f_32867_.m_6710_((LivingEntity)null);
         } else {
            ++this.f_32868_;
            if (this.f_32868_ == 0) {
               this.f_32867_.m_32817_(this.f_32867_.m_5448_().m_142049_());
               if (!this.f_32867_.m_20067_()) {
                  this.f_32867_.f_19853_.m_7605_(this.f_32867_, (byte)21);
               }
            } else if (this.f_32868_ >= this.f_32867_.m_7552_()) {
               float f = 1.0F;
               if (this.f_32867_.f_19853_.m_46791_() == Difficulty.HARD) {
                  f += 2.0F;
               }

               if (this.f_32869_) {
                  f += 2.0F;
               }

               livingentity.m_6469_(DamageSource.m_19367_(this.f_32867_, this.f_32867_), f);
               livingentity.m_6469_(DamageSource.m_19370_(this.f_32867_), (float)this.f_32867_.m_21133_(Attributes.f_22281_));
               this.f_32867_.m_6710_((LivingEntity)null);
            }

            super.m_8037_();
         }
      }
   }

   static class GuardianAttackSelector implements Predicate<LivingEntity> {
      private final Guardian f_32877_;

      public GuardianAttackSelector(Guardian p_32879_) {
         this.f_32877_ = p_32879_;
      }

      public boolean test(@Nullable LivingEntity p_32881_) {
         return (p_32881_ instanceof Player || p_32881_ instanceof Squid || p_32881_ instanceof Axolotl) && p_32881_.m_20280_(this.f_32877_) > 9.0D;
      }
   }

   static class GuardianMoveControl extends MoveControl {
      private final Guardian f_32884_;

      public GuardianMoveControl(Guardian p_32886_) {
         super(p_32886_);
         this.f_32884_ = p_32886_;
      }

      public void m_8126_() {
         if (this.f_24981_ == MoveControl.Operation.MOVE_TO && !this.f_32884_.m_21573_().m_26571_()) {
            Vec3 vec3 = new Vec3(this.f_24975_ - this.f_32884_.m_20185_(), this.f_24976_ - this.f_32884_.m_20186_(), this.f_24977_ - this.f_32884_.m_20189_());
            double d0 = vec3.m_82553_();
            double d1 = vec3.f_82479_ / d0;
            double d2 = vec3.f_82480_ / d0;
            double d3 = vec3.f_82481_ / d0;
            float f = (float)(Mth.m_14136_(vec3.f_82481_, vec3.f_82479_) * (double)(180F / (float)Math.PI)) - 90.0F;
            this.f_32884_.m_146922_(this.m_24991_(this.f_32884_.m_146908_(), f, 90.0F));
            this.f_32884_.f_20883_ = this.f_32884_.m_146908_();
            float f1 = (float)(this.f_24978_ * this.f_32884_.m_21133_(Attributes.f_22279_));
            float f2 = Mth.m_14179_(0.125F, this.f_32884_.m_6113_(), f1);
            this.f_32884_.m_7910_(f2);
            double d4 = Math.sin((double)(this.f_32884_.f_19797_ + this.f_32884_.m_142049_()) * 0.5D) * 0.05D;
            double d5 = Math.cos((double)(this.f_32884_.m_146908_() * ((float)Math.PI / 180F)));
            double d6 = Math.sin((double)(this.f_32884_.m_146908_() * ((float)Math.PI / 180F)));
            double d7 = Math.sin((double)(this.f_32884_.f_19797_ + this.f_32884_.m_142049_()) * 0.75D) * 0.05D;
            this.f_32884_.m_20256_(this.f_32884_.m_20184_().m_82520_(d4 * d5, d7 * (d6 + d5) * 0.25D + (double)f2 * d2 * 0.1D, d4 * d6));
            LookControl lookcontrol = this.f_32884_.m_21563_();
            double d8 = this.f_32884_.m_20185_() + d1 * 2.0D;
            double d9 = this.f_32884_.m_20188_() + d2 / d0;
            double d10 = this.f_32884_.m_20189_() + d3 * 2.0D;
            double d11 = lookcontrol.m_24969_();
            double d12 = lookcontrol.m_24970_();
            double d13 = lookcontrol.m_24971_();
            if (!lookcontrol.m_24968_()) {
               d11 = d8;
               d12 = d9;
               d13 = d10;
            }

            this.f_32884_.m_21563_().m_24950_(Mth.m_14139_(0.125D, d11, d8), Mth.m_14139_(0.125D, d12, d9), Mth.m_14139_(0.125D, d13, d10), 10.0F, 40.0F);
            this.f_32884_.m_32861_(true);
         } else {
            this.f_32884_.m_7910_(0.0F);
            this.f_32884_.m_32861_(false);
         }
      }
   }
}