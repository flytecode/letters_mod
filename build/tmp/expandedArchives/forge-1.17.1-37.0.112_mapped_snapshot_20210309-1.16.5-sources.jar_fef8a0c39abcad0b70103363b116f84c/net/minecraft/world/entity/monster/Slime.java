package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.Vec3;

public class Slime extends Mob implements Enemy {
   private static final EntityDataAccessor<Integer> f_33582_ = SynchedEntityData.m_135353_(Slime.class, EntityDataSerializers.f_135028_);
   public static final int f_149844_ = 1;
   public static final int f_149845_ = 127;
   public float f_33581_;
   public float f_33584_;
   public float f_33585_;
   private boolean f_33583_;

   public Slime(EntityType<? extends Slime> p_33588_, Level p_33589_) {
      super(p_33588_, p_33589_);
      this.f_21342_ = new Slime.SlimeMoveControl(this);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new Slime.SlimeFloatGoal(this));
      this.f_21345_.m_25352_(2, new Slime.SlimeAttackGoal(this));
      this.f_21345_.m_25352_(3, new Slime.SlimeRandomDirectionGoal(this));
      this.f_21345_.m_25352_(5, new Slime.SlimeKeepOnJumpingGoal(this));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_33641_) -> {
         return Math.abs(p_33641_.m_20186_() - this.m_20186_()) <= 4.0D;
      }));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_33582_, 1);
   }

   protected void m_7839_(int p_33594_, boolean p_33595_) {
      int i = Mth.m_14045_(p_33594_, 1, 127);
      this.f_19804_.m_135381_(f_33582_, i);
      this.m_20090_();
      this.m_6210_();
      this.m_21051_(Attributes.f_22276_).m_22100_((double)(i * i));
      this.m_21051_(Attributes.f_22279_).m_22100_((double)(0.2F + 0.1F * (float)i));
      this.m_21051_(Attributes.f_22281_).m_22100_((double)i);
      if (p_33595_) {
         this.m_21153_(this.m_21233_());
      }

      this.f_21364_ = i;
   }

   public int m_33632_() {
      return this.f_19804_.m_135370_(f_33582_);
   }

   public void m_7380_(CompoundTag p_33619_) {
      super.m_7380_(p_33619_);
      p_33619_.m_128405_("Size", this.m_33632_() - 1);
      p_33619_.m_128379_("wasOnGround", this.f_33583_);
   }

   public void m_7378_(CompoundTag p_33607_) {
      this.m_7839_(p_33607_.m_128451_("Size") + 1, false);
      super.m_7378_(p_33607_);
      this.f_33583_ = p_33607_.m_128471_("wasOnGround");
   }

   public boolean m_33633_() {
      return this.m_33632_() <= 1;
   }

   protected ParticleOptions m_6300_() {
      return ParticleTypes.f_123753_;
   }

   protected boolean m_8028_() {
      return this.m_33632_() > 0;
   }

   public void m_8119_() {
      this.f_33584_ += (this.f_33581_ - this.f_33584_) * 0.5F;
      this.f_33585_ = this.f_33584_;
      super.m_8119_();
      if (this.f_19861_ && !this.f_33583_) {
         int i = this.m_33632_();

         if (spawnCustomParticles()) i = 0; // don't spawn particles if it's handled by the implementation itself
         for(int j = 0; j < i * 8; ++j) {
            float f = this.f_19796_.nextFloat() * ((float)Math.PI * 2F);
            float f1 = this.f_19796_.nextFloat() * 0.5F + 0.5F;
            float f2 = Mth.m_14031_(f) * (float)i * 0.5F * f1;
            float f3 = Mth.m_14089_(f) * (float)i * 0.5F * f1;
            this.f_19853_.m_7106_(this.m_6300_(), this.m_20185_() + (double)f2, this.m_20186_(), this.m_20189_() + (double)f3, 0.0D, 0.0D, 0.0D);
         }

         this.m_5496_(this.m_7905_(), this.m_6121_(), ((this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F) / 0.8F);
         this.f_33581_ = -0.5F;
      } else if (!this.f_19861_ && this.f_33583_) {
         this.f_33581_ = 1.0F;
      }

      this.f_33583_ = this.f_19861_;
      this.m_7480_();
   }

   protected void m_7480_() {
      this.f_33581_ *= 0.6F;
   }

   protected int m_7549_() {
      return this.f_19796_.nextInt(20) + 10;
   }

   public void m_6210_() {
      double d0 = this.m_20185_();
      double d1 = this.m_20186_();
      double d2 = this.m_20189_();
      super.m_6210_();
      this.m_6034_(d0, d1, d2);
   }

   public void m_7350_(EntityDataAccessor<?> p_33609_) {
      if (f_33582_.equals(p_33609_)) {
         this.m_6210_();
         this.m_146922_(this.f_20885_);
         this.f_20883_ = this.f_20885_;
         if (this.m_20069_() && this.f_19796_.nextInt(20) == 0) {
            this.m_5841_();
         }
      }

      super.m_7350_(p_33609_);
   }

   public EntityType<? extends Slime> m_6095_() {
      return (EntityType<? extends Slime>)super.m_6095_();
   }

   public void m_142687_(Entity.RemovalReason p_149847_) {
      int i = this.m_33632_();
      if (!this.f_19853_.f_46443_ && i > 1 && this.m_21224_()) {
         Component component = this.m_7770_();
         boolean flag = this.m_21525_();
         float f = (float)i / 4.0F;
         int j = i / 2;
         int k = 2 + this.f_19796_.nextInt(3);

         for(int l = 0; l < k; ++l) {
            float f1 = ((float)(l % 2) - 0.5F) * f;
            float f2 = ((float)(l / 2) - 0.5F) * f;
            Slime slime = this.m_6095_().m_20615_(this.f_19853_);
            if (this.m_21532_()) {
               slime.m_21530_();
            }

            slime.m_6593_(component);
            slime.m_21557_(flag);
            slime.m_20331_(this.m_20147_());
            slime.m_7839_(j, true);
            slime.m_7678_(this.m_20185_() + (double)f1, this.m_20186_() + 0.5D, this.m_20189_() + (double)f2, this.f_19796_.nextFloat() * 360.0F, 0.0F);
            this.f_19853_.m_7967_(slime);
         }
      }

      super.m_142687_(p_149847_);
   }

   public void m_7334_(Entity p_33636_) {
      super.m_7334_(p_33636_);
      if (p_33636_ instanceof IronGolem && this.m_7483_()) {
         this.m_33637_((LivingEntity)p_33636_);
      }

   }

   public void m_6123_(Player p_33611_) {
      if (this.m_7483_()) {
         this.m_33637_(p_33611_);
      }

   }

   protected void m_33637_(LivingEntity p_33638_) {
      if (this.m_6084_()) {
         int i = this.m_33632_();
         if (this.m_20280_(p_33638_) < 0.6D * (double)i * 0.6D * (double)i && this.m_142582_(p_33638_) && p_33638_.m_6469_(DamageSource.m_19370_(this), this.m_7566_())) {
            this.m_5496_(SoundEvents.f_12384_, 1.0F, (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F);
            this.m_19970_(this, p_33638_);
         }
      }

   }

   protected float m_6431_(Pose p_33614_, EntityDimensions p_33615_) {
      return 0.625F * p_33615_.f_20378_;
   }

   protected boolean m_7483_() {
      return !this.m_33633_() && this.m_6142_();
   }

   protected float m_7566_() {
      return (float)this.m_21133_(Attributes.f_22281_);
   }

   protected SoundEvent m_7975_(DamageSource p_33631_) {
      return this.m_33633_() ? SoundEvents.f_12468_ : SoundEvents.f_12386_;
   }

   protected SoundEvent m_5592_() {
      return this.m_33633_() ? SoundEvents.f_12467_ : SoundEvents.f_12385_;
   }

   protected SoundEvent m_7905_() {
      return this.m_33633_() ? SoundEvents.f_12470_ : SoundEvents.f_12388_;
   }

   protected ResourceLocation m_7582_() {
      return this.m_33632_() == 1 ? this.m_6095_().m_20677_() : BuiltInLootTables.f_78712_;
   }

   public static boolean m_33620_(EntityType<Slime> p_33621_, LevelAccessor p_33622_, MobSpawnType p_33623_, BlockPos p_33624_, Random p_33625_) {
      if (p_33622_.m_46791_() != Difficulty.PEACEFUL) {
         if (Objects.equals(p_33622_.m_45837_(p_33624_), Optional.of(Biomes.f_48207_)) && p_33624_.m_123342_() > 50 && p_33624_.m_123342_() < 70 && p_33625_.nextFloat() < 0.5F && p_33625_.nextFloat() < p_33622_.m_46940_() && p_33622_.m_46803_(p_33624_) <= p_33625_.nextInt(8)) {
            return m_21400_(p_33621_, p_33622_, p_33623_, p_33624_, p_33625_);
         }

         if (!(p_33622_ instanceof WorldGenLevel)) {
            return false;
         }

         ChunkPos chunkpos = new ChunkPos(p_33624_);
         boolean flag = WorldgenRandom.m_64685_(chunkpos.f_45578_, chunkpos.f_45579_, ((WorldGenLevel)p_33622_).m_7328_(), 987234911L).nextInt(10) == 0;
         if (p_33625_.nextInt(10) == 0 && flag && p_33624_.m_123342_() < 40) {
            return m_21400_(p_33621_, p_33622_, p_33623_, p_33624_, p_33625_);
         }
      }

      return false;
   }

   protected float m_6121_() {
      return 0.4F * (float)this.m_33632_();
   }

   public int m_8132_() {
      return 0;
   }

   protected boolean m_33634_() {
      return this.m_33632_() > 0;
   }

   protected void m_6135_() {
      Vec3 vec3 = this.m_20184_();
      this.m_20334_(vec3.f_82479_, (double)this.m_6118_(), vec3.f_82481_);
      this.f_19812_ = true;
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_33601_, DifficultyInstance p_33602_, MobSpawnType p_33603_, @Nullable SpawnGroupData p_33604_, @Nullable CompoundTag p_33605_) {
      int i = this.f_19796_.nextInt(3);
      if (i < 2 && this.f_19796_.nextFloat() < 0.5F * p_33602_.m_19057_()) {
         ++i;
      }

      int j = 1 << i;
      this.m_7839_(j, true);
      return super.m_6518_(p_33601_, p_33602_, p_33603_, p_33604_, p_33605_);
   }

   float m_33642_() {
      float f = this.m_33633_() ? 1.4F : 0.8F;
      return ((this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F) * f;
   }

   protected SoundEvent m_7903_() {
      return this.m_33633_() ? SoundEvents.f_12469_ : SoundEvents.f_12387_;
   }

   public EntityDimensions m_6972_(Pose p_33597_) {
      return super.m_6972_(p_33597_).m_20388_(0.255F * (float)this.m_33632_());
   }

   /**
    * Called when the slime spawns particles on landing, see onUpdate.
    * Return true to prevent the spawning of the default particles.
    */
   protected boolean spawnCustomParticles() { return false; }

   static class SlimeAttackGoal extends Goal {
      private final Slime f_33645_;
      private int f_33646_;

      public SlimeAttackGoal(Slime p_33648_) {
         this.f_33645_ = p_33648_;
         this.m_7021_(EnumSet.of(Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         LivingEntity livingentity = this.f_33645_.m_5448_();
         if (livingentity == null) {
            return false;
         } else {
            return !this.f_33645_.m_6779_(livingentity) ? false : this.f_33645_.m_21566_() instanceof Slime.SlimeMoveControl;
         }
      }

      public void m_8056_() {
         this.f_33646_ = 300;
         super.m_8056_();
      }

      public boolean m_8045_() {
         LivingEntity livingentity = this.f_33645_.m_5448_();
         if (livingentity == null) {
            return false;
         } else if (!this.f_33645_.m_6779_(livingentity)) {
            return false;
         } else {
            return --this.f_33646_ > 0;
         }
      }

      public void m_8037_() {
         this.f_33645_.m_21391_(this.f_33645_.m_5448_(), 10.0F, 10.0F);
         ((Slime.SlimeMoveControl)this.f_33645_.m_21566_()).m_33672_(this.f_33645_.m_146908_(), this.f_33645_.m_7483_());
      }
   }

   static class SlimeFloatGoal extends Goal {
      private final Slime f_33653_;

      public SlimeFloatGoal(Slime p_33655_) {
         this.f_33653_ = p_33655_;
         this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
         p_33655_.m_21573_().m_7008_(true);
      }

      public boolean m_8036_() {
         return (this.f_33653_.m_20069_() || this.f_33653_.m_20077_()) && this.f_33653_.m_21566_() instanceof Slime.SlimeMoveControl;
      }

      public void m_8037_() {
         if (this.f_33653_.m_21187_().nextFloat() < 0.8F) {
            this.f_33653_.m_21569_().m_24901_();
         }

         ((Slime.SlimeMoveControl)this.f_33653_.m_21566_()).m_33670_(1.2D);
      }
   }

   static class SlimeKeepOnJumpingGoal extends Goal {
      private final Slime f_33658_;

      public SlimeKeepOnJumpingGoal(Slime p_33660_) {
         this.f_33658_ = p_33660_;
         this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         return !this.f_33658_.m_20159_();
      }

      public void m_8037_() {
         ((Slime.SlimeMoveControl)this.f_33658_.m_21566_()).m_33670_(1.0D);
      }
   }

   static class SlimeMoveControl extends MoveControl {
      private float f_33663_;
      private int f_33664_;
      private final Slime f_33665_;
      private boolean f_33666_;

      public SlimeMoveControl(Slime p_33668_) {
         super(p_33668_);
         this.f_33665_ = p_33668_;
         this.f_33663_ = 180.0F * p_33668_.m_146908_() / (float)Math.PI;
      }

      public void m_33672_(float p_33673_, boolean p_33674_) {
         this.f_33663_ = p_33673_;
         this.f_33666_ = p_33674_;
      }

      public void m_33670_(double p_33671_) {
         this.f_24978_ = p_33671_;
         this.f_24981_ = MoveControl.Operation.MOVE_TO;
      }

      public void m_8126_() {
         this.f_24974_.m_146922_(this.m_24991_(this.f_24974_.m_146908_(), this.f_33663_, 90.0F));
         this.f_24974_.f_20885_ = this.f_24974_.m_146908_();
         this.f_24974_.f_20883_ = this.f_24974_.m_146908_();
         if (this.f_24981_ != MoveControl.Operation.MOVE_TO) {
            this.f_24974_.m_21564_(0.0F);
         } else {
            this.f_24981_ = MoveControl.Operation.WAIT;
            if (this.f_24974_.m_20096_()) {
               this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_)));
               if (this.f_33664_-- <= 0) {
                  this.f_33664_ = this.f_33665_.m_7549_();
                  if (this.f_33666_) {
                     this.f_33664_ /= 3;
                  }

                  this.f_33665_.m_21569_().m_24901_();
                  if (this.f_33665_.m_33634_()) {
                     this.f_33665_.m_5496_(this.f_33665_.m_7903_(), this.f_33665_.m_6121_(), this.f_33665_.m_33642_());
                  }
               } else {
                  this.f_33665_.f_20900_ = 0.0F;
                  this.f_33665_.f_20902_ = 0.0F;
                  this.f_24974_.m_7910_(0.0F);
               }
            } else {
               this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_)));
            }

         }
      }
   }

   static class SlimeRandomDirectionGoal extends Goal {
      private final Slime f_33675_;
      private float f_33676_;
      private int f_33677_;

      public SlimeRandomDirectionGoal(Slime p_33679_) {
         this.f_33675_ = p_33679_;
         this.m_7021_(EnumSet.of(Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         return this.f_33675_.m_5448_() == null && (this.f_33675_.f_19861_ || this.f_33675_.m_20069_() || this.f_33675_.m_20077_() || this.f_33675_.m_21023_(MobEffects.f_19620_)) && this.f_33675_.m_21566_() instanceof Slime.SlimeMoveControl;
      }

      public void m_8037_() {
         if (--this.f_33677_ <= 0) {
            this.f_33677_ = 40 + this.f_33675_.m_21187_().nextInt(60);
            this.f_33676_ = (float)this.f_33675_.m_21187_().nextInt(360);
         }

         ((Slime.SlimeMoveControl)this.f_33675_.m_21566_()).m_33672_(this.f_33676_, false);
      }
   }
}
