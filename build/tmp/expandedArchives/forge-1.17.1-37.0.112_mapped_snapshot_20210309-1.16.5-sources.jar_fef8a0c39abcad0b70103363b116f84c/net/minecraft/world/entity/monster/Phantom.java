package net.minecraft.world.entity.monster;

import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

public class Phantom extends FlyingMob implements Enemy {
   public static final float f_149721_ = 7.448451F;
   public static final int f_149722_ = Mth.m_14167_(24.166098F);
   private static final EntityDataAccessor<Integer> f_33095_ = SynchedEntityData.m_135353_(Phantom.class, EntityDataSerializers.f_135028_);
   Vec3 f_33097_ = Vec3.f_82478_;
   BlockPos f_33098_ = BlockPos.f_121853_;
   Phantom.AttackPhase f_33096_ = Phantom.AttackPhase.CIRCLE;

   public Phantom(EntityType<? extends Phantom> p_33101_, Level p_33102_) {
      super(p_33101_, p_33102_);
      this.f_21364_ = 5;
      this.f_21342_ = new Phantom.PhantomMoveControl(this);
      this.f_21365_ = new Phantom.PhantomLookControl(this);
   }

   public boolean m_142039_() {
      return (this.m_149736_() + this.f_19797_) % f_149722_ == 0;
   }

   protected BodyRotationControl m_7560_() {
      return new Phantom.PhantomBodyRotationControl(this);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new Phantom.PhantomAttackStrategyGoal());
      this.f_21345_.m_25352_(2, new Phantom.PhantomSweepAttackGoal());
      this.f_21345_.m_25352_(3, new Phantom.PhantomCircleAroundAnchorGoal());
      this.f_21346_.m_25352_(1, new Phantom.PhantomAttackPlayerTargetGoal());
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_33095_, 0);
   }

   public void m_33108_(int p_33109_) {
      this.f_19804_.m_135381_(f_33095_, Mth.m_14045_(p_33109_, 0, 64));
   }

   private void m_33155_() {
      this.m_6210_();
      this.m_21051_(Attributes.f_22281_).m_22100_((double)(6 + this.m_33172_()));
   }

   public int m_33172_() {
      return this.f_19804_.m_135370_(f_33095_);
   }

   protected float m_6431_(Pose p_33136_, EntityDimensions p_33137_) {
      return p_33137_.f_20378_ * 0.35F;
   }

   public void m_7350_(EntityDataAccessor<?> p_33134_) {
      if (f_33095_.equals(p_33134_)) {
         this.m_33155_();
      }

      super.m_7350_(p_33134_);
   }

   public int m_149736_() {
      return this.m_142049_() * 3;
   }

   protected boolean m_8028_() {
      return true;
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_) {
         float f = Mth.m_14089_((float)(this.m_149736_() + this.f_19797_) * 7.448451F * ((float)Math.PI / 180F) + (float)Math.PI);
         float f1 = Mth.m_14089_((float)(this.m_149736_() + this.f_19797_ + 1) * 7.448451F * ((float)Math.PI / 180F) + (float)Math.PI);
         if (f > 0.0F && f1 <= 0.0F) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12230_, this.m_5720_(), 0.95F + this.f_19796_.nextFloat() * 0.05F, 0.95F + this.f_19796_.nextFloat() * 0.05F, false);
         }

         int i = this.m_33172_();
         float f2 = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F)) * (1.3F + 0.21F * (float)i);
         float f3 = Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F)) * (1.3F + 0.21F * (float)i);
         float f4 = (0.3F + f * 0.45F) * ((float)i * 0.2F + 1.0F);
         this.f_19853_.m_7106_(ParticleTypes.f_123757_, this.m_20185_() + (double)f2, this.m_20186_() + (double)f4, this.m_20189_() + (double)f3, 0.0D, 0.0D, 0.0D);
         this.f_19853_.m_7106_(ParticleTypes.f_123757_, this.m_20185_() - (double)f2, this.m_20186_() + (double)f4, this.m_20189_() - (double)f3, 0.0D, 0.0D, 0.0D);
      }

   }

   public void m_8107_() {
      if (this.m_6084_() && this.m_21527_()) {
         this.m_20254_(8);
      }

      super.m_8107_();
   }

   protected void m_8024_() {
      super.m_8024_();
   }

   public SpawnGroupData m_6518_(ServerLevelAccessor p_33126_, DifficultyInstance p_33127_, MobSpawnType p_33128_, @Nullable SpawnGroupData p_33129_, @Nullable CompoundTag p_33130_) {
      this.f_33098_ = this.m_142538_().m_6630_(5);
      this.m_33108_(0);
      return super.m_6518_(p_33126_, p_33127_, p_33128_, p_33129_, p_33130_);
   }

   public void m_7378_(CompoundTag p_33132_) {
      super.m_7378_(p_33132_);
      if (p_33132_.m_128441_("AX")) {
         this.f_33098_ = new BlockPos(p_33132_.m_128451_("AX"), p_33132_.m_128451_("AY"), p_33132_.m_128451_("AZ"));
      }

      this.m_33108_(p_33132_.m_128451_("Size"));
   }

   public void m_7380_(CompoundTag p_33141_) {
      super.m_7380_(p_33141_);
      p_33141_.m_128405_("AX", this.f_33098_.m_123341_());
      p_33141_.m_128405_("AY", this.f_33098_.m_123342_());
      p_33141_.m_128405_("AZ", this.f_33098_.m_123343_());
      p_33141_.m_128405_("Size", this.m_33172_());
   }

   public boolean m_6783_(double p_33107_) {
      return true;
   }

   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12227_;
   }

   protected SoundEvent m_7975_(DamageSource p_33152_) {
      return SoundEvents.f_12231_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12229_;
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   protected float m_6121_() {
      return 1.0F;
   }

   public boolean m_6549_(EntityType<?> p_33111_) {
      return true;
   }

   public EntityDimensions m_6972_(Pose p_33113_) {
      int i = this.m_33172_();
      EntityDimensions entitydimensions = super.m_6972_(p_33113_);
      float f = (entitydimensions.f_20377_ + 0.2F * (float)i) / entitydimensions.f_20377_;
      return entitydimensions.m_20388_(f);
   }

   static enum AttackPhase {
      CIRCLE,
      SWOOP;
   }

   class PhantomAttackPlayerTargetGoal extends Goal {
      private final TargetingConditions f_33192_ = TargetingConditions.m_148352_().m_26883_(64.0D);
      private int f_33193_ = 20;

      public boolean m_8036_() {
         if (this.f_33193_ > 0) {
            --this.f_33193_;
            return false;
         } else {
            this.f_33193_ = 60;
            List<Player> list = Phantom.this.f_19853_.m_45955_(this.f_33192_, Phantom.this, Phantom.this.m_142469_().m_82377_(16.0D, 64.0D, 16.0D));
            if (!list.isEmpty()) {
               list.sort(Comparator.<Entity, Double>comparing(Entity::m_20186_).reversed());

               for(Player player : list) {
                  if (Phantom.this.m_21040_(player, TargetingConditions.f_26872_)) {
                     Phantom.this.m_6710_(player);
                     return true;
                  }
               }
            }

            return false;
         }
      }

      public boolean m_8045_() {
         LivingEntity livingentity = Phantom.this.m_5448_();
         return livingentity != null ? Phantom.this.m_21040_(livingentity, TargetingConditions.f_26872_) : false;
      }
   }

   class PhantomAttackStrategyGoal extends Goal {
      private int f_33202_;

      public boolean m_8036_() {
         LivingEntity livingentity = Phantom.this.m_5448_();
         return livingentity != null ? Phantom.this.m_21040_(Phantom.this.m_5448_(), TargetingConditions.f_26872_) : false;
      }

      public void m_8056_() {
         this.f_33202_ = 10;
         Phantom.this.f_33096_ = Phantom.AttackPhase.CIRCLE;
         this.m_33212_();
      }

      public void m_8041_() {
         Phantom.this.f_33098_ = Phantom.this.f_19853_.m_5452_(Heightmap.Types.MOTION_BLOCKING, Phantom.this.f_33098_).m_6630_(10 + Phantom.this.f_19796_.nextInt(20));
      }

      public void m_8037_() {
         if (Phantom.this.f_33096_ == Phantom.AttackPhase.CIRCLE) {
            --this.f_33202_;
            if (this.f_33202_ <= 0) {
               Phantom.this.f_33096_ = Phantom.AttackPhase.SWOOP;
               this.m_33212_();
               this.f_33202_ = (8 + Phantom.this.f_19796_.nextInt(4)) * 20;
               Phantom.this.m_5496_(SoundEvents.f_12232_, 10.0F, 0.95F + Phantom.this.f_19796_.nextFloat() * 0.1F);
            }
         }

      }

      private void m_33212_() {
         Phantom.this.f_33098_ = Phantom.this.m_5448_().m_142538_().m_6630_(20 + Phantom.this.f_19796_.nextInt(20));
         if (Phantom.this.f_33098_.m_123342_() < Phantom.this.f_19853_.m_5736_()) {
            Phantom.this.f_33098_ = new BlockPos(Phantom.this.f_33098_.m_123341_(), Phantom.this.f_19853_.m_5736_() + 1, Phantom.this.f_33098_.m_123343_());
         }

      }
   }

   class PhantomBodyRotationControl extends BodyRotationControl {
      public PhantomBodyRotationControl(Mob p_33216_) {
         super(p_33216_);
      }

      public void m_8121_() {
         Phantom.this.f_20885_ = Phantom.this.f_20883_;
         Phantom.this.f_20883_ = Phantom.this.m_146908_();
      }
   }

   class PhantomCircleAroundAnchorGoal extends Phantom.PhantomMoveTargetGoal {
      private float f_33219_;
      private float f_33220_;
      private float f_33221_;
      private float f_33222_;

      public boolean m_8036_() {
         return Phantom.this.m_5448_() == null || Phantom.this.f_33096_ == Phantom.AttackPhase.CIRCLE;
      }

      public void m_8056_() {
         this.f_33220_ = 5.0F + Phantom.this.f_19796_.nextFloat() * 10.0F;
         this.f_33221_ = -4.0F + Phantom.this.f_19796_.nextFloat() * 9.0F;
         this.f_33222_ = Phantom.this.f_19796_.nextBoolean() ? 1.0F : -1.0F;
         this.m_33231_();
      }

      public void m_8037_() {
         if (Phantom.this.f_19796_.nextInt(350) == 0) {
            this.f_33221_ = -4.0F + Phantom.this.f_19796_.nextFloat() * 9.0F;
         }

         if (Phantom.this.f_19796_.nextInt(250) == 0) {
            ++this.f_33220_;
            if (this.f_33220_ > 15.0F) {
               this.f_33220_ = 5.0F;
               this.f_33222_ = -this.f_33222_;
            }
         }

         if (Phantom.this.f_19796_.nextInt(450) == 0) {
            this.f_33219_ = Phantom.this.f_19796_.nextFloat() * 2.0F * (float)Math.PI;
            this.m_33231_();
         }

         if (this.m_33246_()) {
            this.m_33231_();
         }

         if (Phantom.this.f_33097_.f_82480_ < Phantom.this.m_20186_() && !Phantom.this.f_19853_.m_46859_(Phantom.this.m_142538_().m_6625_(1))) {
            this.f_33221_ = Math.max(1.0F, this.f_33221_);
            this.m_33231_();
         }

         if (Phantom.this.f_33097_.f_82480_ > Phantom.this.m_20186_() && !Phantom.this.f_19853_.m_46859_(Phantom.this.m_142538_().m_6630_(1))) {
            this.f_33221_ = Math.min(-1.0F, this.f_33221_);
            this.m_33231_();
         }

      }

      private void m_33231_() {
         if (BlockPos.f_121853_.equals(Phantom.this.f_33098_)) {
            Phantom.this.f_33098_ = Phantom.this.m_142538_();
         }

         this.f_33219_ += this.f_33222_ * 15.0F * ((float)Math.PI / 180F);
         Phantom.this.f_33097_ = Vec3.m_82528_(Phantom.this.f_33098_).m_82520_((double)(this.f_33220_ * Mth.m_14089_(this.f_33219_)), (double)(-4.0F + this.f_33221_), (double)(this.f_33220_ * Mth.m_14031_(this.f_33219_)));
      }
   }

   class PhantomLookControl extends LookControl {
      public PhantomLookControl(Mob p_33235_) {
         super(p_33235_);
      }

      public void m_8128_() {
      }
   }

   class PhantomMoveControl extends MoveControl {
      private float f_33238_ = 0.1F;

      public PhantomMoveControl(Mob p_33241_) {
         super(p_33241_);
      }

      public void m_8126_() {
         if (Phantom.this.f_19862_) {
            Phantom.this.m_146922_(Phantom.this.m_146908_() + 180.0F);
            this.f_33238_ = 0.1F;
         }

         float f = (float)(Phantom.this.f_33097_.f_82479_ - Phantom.this.m_20185_());
         float f1 = (float)(Phantom.this.f_33097_.f_82480_ - Phantom.this.m_20186_());
         float f2 = (float)(Phantom.this.f_33097_.f_82481_ - Phantom.this.m_20189_());
         double d0 = (double)Mth.m_14116_(f * f + f2 * f2);
         if (Math.abs(d0) > (double)1.0E-5F) {
            double d1 = 1.0D - (double)Mth.m_14154_(f1 * 0.7F) / d0;
            f = (float)((double)f * d1);
            f2 = (float)((double)f2 * d1);
            d0 = (double)Mth.m_14116_(f * f + f2 * f2);
            double d2 = (double)Mth.m_14116_(f * f + f2 * f2 + f1 * f1);
            float f3 = Phantom.this.m_146908_();
            float f4 = (float)Mth.m_14136_((double)f2, (double)f);
            float f5 = Mth.m_14177_(Phantom.this.m_146908_() + 90.0F);
            float f6 = Mth.m_14177_(f4 * (180F / (float)Math.PI));
            Phantom.this.m_146922_(Mth.m_14148_(f5, f6, 4.0F) - 90.0F);
            Phantom.this.f_20883_ = Phantom.this.m_146908_();
            if (Mth.m_14145_(f3, Phantom.this.m_146908_()) < 3.0F) {
               this.f_33238_ = Mth.m_14121_(this.f_33238_, 1.8F, 0.005F * (1.8F / this.f_33238_));
            } else {
               this.f_33238_ = Mth.m_14121_(this.f_33238_, 0.2F, 0.025F);
            }

            float f7 = (float)(-(Mth.m_14136_((double)(-f1), d0) * (double)(180F / (float)Math.PI)));
            Phantom.this.m_146926_(f7);
            float f8 = Phantom.this.m_146908_() + 90.0F;
            double d3 = (double)(this.f_33238_ * Mth.m_14089_(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f / d2);
            double d4 = (double)(this.f_33238_ * Mth.m_14031_(f8 * ((float)Math.PI / 180F))) * Math.abs((double)f2 / d2);
            double d5 = (double)(this.f_33238_ * Mth.m_14031_(f7 * ((float)Math.PI / 180F))) * Math.abs((double)f1 / d2);
            Vec3 vec3 = Phantom.this.m_20184_();
            Phantom.this.m_20256_(vec3.m_82549_((new Vec3(d3, d5, d4)).m_82546_(vec3).m_82490_(0.2D)));
         }

      }
   }

   abstract class PhantomMoveTargetGoal extends Goal {
      public PhantomMoveTargetGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      protected boolean m_33246_() {
         return Phantom.this.f_33097_.m_82531_(Phantom.this.m_20185_(), Phantom.this.m_20186_(), Phantom.this.m_20189_()) < 4.0D;
      }
   }

   class PhantomSweepAttackGoal extends Phantom.PhantomMoveTargetGoal {
      public boolean m_8036_() {
         return Phantom.this.m_5448_() != null && Phantom.this.f_33096_ == Phantom.AttackPhase.SWOOP;
      }

      public boolean m_8045_() {
         LivingEntity livingentity = Phantom.this.m_5448_();
         if (livingentity == null) {
            return false;
         } else if (!livingentity.m_6084_()) {
            return false;
         } else if (!(livingentity instanceof Player) || !((Player)livingentity).m_5833_() && !((Player)livingentity).m_7500_()) {
            if (!this.m_8036_()) {
               return false;
            } else {
               if (Phantom.this.f_19797_ % 20 == 0) {
                  List<Cat> list = Phantom.this.f_19853_.m_6443_(Cat.class, Phantom.this.m_142469_().m_82400_(16.0D), EntitySelector.f_20402_);
                  if (!list.isEmpty()) {
                     for(Cat cat : list) {
                        cat.m_28167_();
                     }

                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }

      public void m_8056_() {
      }

      public void m_8041_() {
         Phantom.this.m_6710_((LivingEntity)null);
         Phantom.this.f_33096_ = Phantom.AttackPhase.CIRCLE;
      }

      public void m_8037_() {
         LivingEntity livingentity = Phantom.this.m_5448_();
         Phantom.this.f_33097_ = new Vec3(livingentity.m_20185_(), livingentity.m_20227_(0.5D), livingentity.m_20189_());
         if (Phantom.this.m_142469_().m_82400_((double)0.2F).m_82381_(livingentity.m_142469_())) {
            Phantom.this.m_7327_(livingentity);
            Phantom.this.f_33096_ = Phantom.AttackPhase.CIRCLE;
            if (!Phantom.this.m_20067_()) {
               Phantom.this.f_19853_.m_46796_(1039, Phantom.this.m_142538_(), 0);
            }
         } else if (Phantom.this.f_19862_ || Phantom.this.f_20916_ > 0) {
            Phantom.this.f_33096_ = Phantom.AttackPhase.CIRCLE;
         }

      }
   }
}