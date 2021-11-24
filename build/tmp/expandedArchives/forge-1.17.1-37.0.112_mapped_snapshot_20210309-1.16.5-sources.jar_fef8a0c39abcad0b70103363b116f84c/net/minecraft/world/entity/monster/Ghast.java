package net.minecraft.world.entity.monster;

import java.util.EnumSet;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Ghast extends FlyingMob implements Enemy {
   private static final EntityDataAccessor<Boolean> f_32721_ = SynchedEntityData.m_135353_(Ghast.class, EntityDataSerializers.f_135035_);
   private int f_32722_ = 1;

   public Ghast(EntityType<? extends Ghast> p_32725_, Level p_32726_) {
      super(p_32725_, p_32726_);
      this.f_21364_ = 5;
      this.f_21342_ = new Ghast.GhastMoveControl(this);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(5, new Ghast.RandomFloatAroundGoal(this));
      this.f_21345_.m_25352_(7, new Ghast.GhastLookGoal(this));
      this.f_21345_.m_25352_(7, new Ghast.GhastShootFireballGoal(this));
      this.f_21346_.m_25352_(1, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, (p_32755_) -> {
         return Math.abs(p_32755_.m_20186_() - this.m_20186_()) <= 4.0D;
      }));
   }

   public boolean m_32756_() {
      return this.f_19804_.m_135370_(f_32721_);
   }

   public void m_32758_(boolean p_32759_) {
      this.f_19804_.m_135381_(f_32721_, p_32759_);
   }

   public int m_32751_() {
      return this.f_32722_;
   }

   protected boolean m_8028_() {
      return true;
   }

   public boolean m_6469_(DamageSource p_32730_, float p_32731_) {
      if (this.m_6673_(p_32730_)) {
         return false;
      } else if (p_32730_.m_7640_() instanceof LargeFireball && p_32730_.m_7639_() instanceof Player) {
         super.m_6469_(p_32730_, 1000.0F);
         return true;
      } else {
         return super.m_6469_(p_32730_, p_32731_);
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_32721_, false);
   }

   public static AttributeSupplier.Builder m_32752_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22277_, 100.0D);
   }

   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_11919_;
   }

   protected SoundEvent m_7975_(DamageSource p_32750_) {
      return SoundEvents.f_11921_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11920_;
   }

   protected float m_6121_() {
      return 5.0F;
   }

   public static boolean m_32734_(EntityType<Ghast> p_32735_, LevelAccessor p_32736_, MobSpawnType p_32737_, BlockPos p_32738_, Random p_32739_) {
      return p_32736_.m_46791_() != Difficulty.PEACEFUL && p_32739_.nextInt(20) == 0 && m_21400_(p_32735_, p_32736_, p_32737_, p_32738_, p_32739_);
   }

   public int m_5792_() {
      return 1;
   }

   public void m_7380_(CompoundTag p_32744_) {
      super.m_7380_(p_32744_);
      p_32744_.m_128344_("ExplosionPower", (byte)this.f_32722_);
   }

   public void m_7378_(CompoundTag p_32733_) {
      super.m_7378_(p_32733_);
      if (p_32733_.m_128425_("ExplosionPower", 99)) {
         this.f_32722_ = p_32733_.m_128445_("ExplosionPower");
      }

   }

   protected float m_6431_(Pose p_32741_, EntityDimensions p_32742_) {
      return 2.6F;
   }

   static class GhastLookGoal extends Goal {
      private final Ghast f_32760_;

      public GhastLookGoal(Ghast p_32762_) {
         this.f_32760_ = p_32762_;
         this.m_7021_(EnumSet.of(Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         return true;
      }

      public void m_8037_() {
         if (this.f_32760_.m_5448_() == null) {
            Vec3 vec3 = this.f_32760_.m_20184_();
            this.f_32760_.m_146922_(-((float)Mth.m_14136_(vec3.f_82479_, vec3.f_82481_)) * (180F / (float)Math.PI));
            this.f_32760_.f_20883_ = this.f_32760_.m_146908_();
         } else {
            LivingEntity livingentity = this.f_32760_.m_5448_();
            double d0 = 64.0D;
            if (livingentity.m_20280_(this.f_32760_) < 4096.0D) {
               double d1 = livingentity.m_20185_() - this.f_32760_.m_20185_();
               double d2 = livingentity.m_20189_() - this.f_32760_.m_20189_();
               this.f_32760_.m_146922_(-((float)Mth.m_14136_(d1, d2)) * (180F / (float)Math.PI));
               this.f_32760_.f_20883_ = this.f_32760_.m_146908_();
            }
         }

      }
   }

   static class GhastMoveControl extends MoveControl {
      private final Ghast f_32765_;
      private int f_32766_;

      public GhastMoveControl(Ghast p_32768_) {
         super(p_32768_);
         this.f_32765_ = p_32768_;
      }

      public void m_8126_() {
         if (this.f_24981_ == MoveControl.Operation.MOVE_TO) {
            if (this.f_32766_-- <= 0) {
               this.f_32766_ += this.f_32765_.m_21187_().nextInt(5) + 2;
               Vec3 vec3 = new Vec3(this.f_24975_ - this.f_32765_.m_20185_(), this.f_24976_ - this.f_32765_.m_20186_(), this.f_24977_ - this.f_32765_.m_20189_());
               double d0 = vec3.m_82553_();
               vec3 = vec3.m_82541_();
               if (this.m_32770_(vec3, Mth.m_14165_(d0))) {
                  this.f_32765_.m_20256_(this.f_32765_.m_20184_().m_82549_(vec3.m_82490_(0.1D)));
               } else {
                  this.f_24981_ = MoveControl.Operation.WAIT;
               }
            }

         }
      }

      private boolean m_32770_(Vec3 p_32771_, int p_32772_) {
         AABB aabb = this.f_32765_.m_142469_();

         for(int i = 1; i < p_32772_; ++i) {
            aabb = aabb.m_82383_(p_32771_);
            if (!this.f_32765_.f_19853_.m_45756_(this.f_32765_, aabb)) {
               return false;
            }
         }

         return true;
      }
   }

   static class GhastShootFireballGoal extends Goal {
      private final Ghast f_32774_;
      public int f_32773_;

      public GhastShootFireballGoal(Ghast p_32776_) {
         this.f_32774_ = p_32776_;
      }

      public boolean m_8036_() {
         return this.f_32774_.m_5448_() != null;
      }

      public void m_8056_() {
         this.f_32773_ = 0;
      }

      public void m_8041_() {
         this.f_32774_.m_32758_(false);
      }

      public void m_8037_() {
         LivingEntity livingentity = this.f_32774_.m_5448_();
         double d0 = 64.0D;
         if (livingentity.m_20280_(this.f_32774_) < 4096.0D && this.f_32774_.m_142582_(livingentity)) {
            Level level = this.f_32774_.f_19853_;
            ++this.f_32773_;
            if (this.f_32773_ == 10 && !this.f_32774_.m_20067_()) {
               level.m_5898_((Player)null, 1015, this.f_32774_.m_142538_(), 0);
            }

            if (this.f_32773_ == 20) {
               double d1 = 4.0D;
               Vec3 vec3 = this.f_32774_.m_20252_(1.0F);
               double d2 = livingentity.m_20185_() - (this.f_32774_.m_20185_() + vec3.f_82479_ * 4.0D);
               double d3 = livingentity.m_20227_(0.5D) - (0.5D + this.f_32774_.m_20227_(0.5D));
               double d4 = livingentity.m_20189_() - (this.f_32774_.m_20189_() + vec3.f_82481_ * 4.0D);
               if (!this.f_32774_.m_20067_()) {
                  level.m_5898_((Player)null, 1016, this.f_32774_.m_142538_(), 0);
               }

               LargeFireball largefireball = new LargeFireball(level, this.f_32774_, d2, d3, d4, this.f_32774_.m_32751_());
               largefireball.m_6034_(this.f_32774_.m_20185_() + vec3.f_82479_ * 4.0D, this.f_32774_.m_20227_(0.5D) + 0.5D, largefireball.m_20189_() + vec3.f_82481_ * 4.0D);
               level.m_7967_(largefireball);
               this.f_32773_ = -40;
            }
         } else if (this.f_32773_ > 0) {
            --this.f_32773_;
         }

         this.f_32774_.m_32758_(this.f_32773_ > 10);
      }
   }

   static class RandomFloatAroundGoal extends Goal {
      private final Ghast f_32781_;

      public RandomFloatAroundGoal(Ghast p_32783_) {
         this.f_32781_ = p_32783_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         MoveControl movecontrol = this.f_32781_.m_21566_();
         if (!movecontrol.m_24995_()) {
            return true;
         } else {
            double d0 = movecontrol.m_25000_() - this.f_32781_.m_20185_();
            double d1 = movecontrol.m_25001_() - this.f_32781_.m_20186_();
            double d2 = movecontrol.m_25002_() - this.f_32781_.m_20189_();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0D || d3 > 3600.0D;
         }
      }

      public boolean m_8045_() {
         return false;
      }

      public void m_8056_() {
         Random random = this.f_32781_.m_21187_();
         double d0 = this.f_32781_.m_20185_() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double d1 = this.f_32781_.m_20186_() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
         double d2 = this.f_32781_.m_20189_() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
         this.f_32781_.m_21566_().m_6849_(d0, d1, d2, 1.0D);
      }
   }
}