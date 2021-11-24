package net.minecraft.world.entity.monster;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class Spider extends Monster {
   private static final EntityDataAccessor<Byte> f_33783_ = SynchedEntityData.m_135353_(Spider.class, EntityDataSerializers.f_135027_);
   private static final float f_149853_ = 0.1F;

   public Spider(EntityType<? extends Spider> p_33786_, Level p_33787_) {
      super(p_33786_, p_33787_);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(3, new LeapAtTargetGoal(this, 0.4F));
      this.f_21345_.m_25352_(4, new Spider.SpiderAttackGoal(this));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new Spider.SpiderTargetGoal<>(this, Player.class));
      this.f_21346_.m_25352_(3, new Spider.SpiderTargetGoal<>(this, IronGolem.class));
   }

   public double m_6048_() {
      return (double)(this.m_20206_() * 0.5F);
   }

   protected PathNavigation m_6037_(Level p_33802_) {
      return new WallClimberNavigation(this, p_33802_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_33783_, (byte)0);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         this.m_33819_(this.f_19862_);
      }

   }

   public static AttributeSupplier.Builder m_33815_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22276_, 16.0D).m_22268_(Attributes.f_22279_, (double)0.3F);
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12432_;
   }

   protected SoundEvent m_7975_(DamageSource p_33814_) {
      return SoundEvents.f_12434_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12433_;
   }

   protected void m_7355_(BlockPos p_33804_, BlockState p_33805_) {
      this.m_5496_(SoundEvents.f_12435_, 0.15F, 1.0F);
   }

   public boolean m_6147_() {
      return this.m_33816_();
   }

   public void m_7601_(BlockState p_33796_, Vec3 p_33797_) {
      if (!p_33796_.m_60713_(Blocks.f_50033_)) {
         super.m_7601_(p_33796_, p_33797_);
      }

   }

   public MobType m_6336_() {
      return MobType.f_21642_;
   }

   public boolean m_7301_(MobEffectInstance p_33809_) {
      if (p_33809_.m_19544_() == MobEffects.f_19614_) {
         net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, p_33809_);
         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
         return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
      }
      return super.m_7301_(p_33809_);
   }

   public boolean m_33816_() {
      return (this.f_19804_.m_135370_(f_33783_) & 1) != 0;
   }

   public void m_33819_(boolean p_33820_) {
      byte b0 = this.f_19804_.m_135370_(f_33783_);
      if (p_33820_) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 = (byte)(b0 & -2);
      }

      this.f_19804_.m_135381_(f_33783_, b0);
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_33790_, DifficultyInstance p_33791_, MobSpawnType p_33792_, @Nullable SpawnGroupData p_33793_, @Nullable CompoundTag p_33794_) {
      p_33793_ = super.m_6518_(p_33790_, p_33791_, p_33792_, p_33793_, p_33794_);
      if (p_33790_.m_5822_().nextInt(100) == 0) {
         Skeleton skeleton = EntityType.f_20524_.m_20615_(this.f_19853_);
         skeleton.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0F);
         skeleton.m_6518_(p_33790_, p_33791_, p_33792_, (SpawnGroupData)null, (CompoundTag)null);
         skeleton.m_20329_(this);
      }

      if (p_33793_ == null) {
         p_33793_ = new Spider.SpiderEffectsGroupData();
         if (p_33790_.m_46791_() == Difficulty.HARD && p_33790_.m_5822_().nextFloat() < 0.1F * p_33791_.m_19057_()) {
            ((Spider.SpiderEffectsGroupData)p_33793_).m_33829_(p_33790_.m_5822_());
         }
      }

      if (p_33793_ instanceof Spider.SpiderEffectsGroupData) {
         MobEffect mobeffect = ((Spider.SpiderEffectsGroupData)p_33793_).f_33827_;
         if (mobeffect != null) {
            this.m_7292_(new MobEffectInstance(mobeffect, Integer.MAX_VALUE));
         }
      }

      return p_33793_;
   }

   protected float m_6431_(Pose p_33799_, EntityDimensions p_33800_) {
      return 0.65F;
   }

   static class SpiderAttackGoal extends MeleeAttackGoal {
      public SpiderAttackGoal(Spider p_33822_) {
         super(p_33822_, 1.0D, true);
      }

      public boolean m_8036_() {
         return super.m_8036_() && !this.f_25540_.m_20160_();
      }

      public boolean m_8045_() {
         float f = this.f_25540_.m_6073_();
         if (f >= 0.5F && this.f_25540_.m_21187_().nextInt(100) == 0) {
            this.f_25540_.m_6710_((LivingEntity)null);
            return false;
         } else {
            return super.m_8045_();
         }
      }

      protected double m_6639_(LivingEntity p_33825_) {
         return (double)(4.0F + p_33825_.m_20205_());
      }
   }

   public static class SpiderEffectsGroupData implements SpawnGroupData {
      public MobEffect f_33827_;

      public void m_33829_(Random p_33830_) {
         int i = p_33830_.nextInt(5);
         if (i <= 1) {
            this.f_33827_ = MobEffects.f_19596_;
         } else if (i <= 2) {
            this.f_33827_ = MobEffects.f_19600_;
         } else if (i <= 3) {
            this.f_33827_ = MobEffects.f_19605_;
         } else if (i <= 4) {
            this.f_33827_ = MobEffects.f_19609_;
         }

      }
   }

   static class SpiderTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
      public SpiderTargetGoal(Spider p_33832_, Class<T> p_33833_) {
         super(p_33832_, p_33833_, true);
      }

      public boolean m_8036_() {
         float f = this.f_26135_.m_6073_();
         return f >= 0.5F ? false : super.m_8036_();
      }
   }
}
