package net.minecraft.world.entity.monster;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableWitchTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestHealableRaiderTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class Witch extends Raider implements RangedAttackMob {
   private static final UUID f_34126_ = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
   private static final AttributeModifier f_34127_ = new AttributeModifier(f_34126_, "Drinking speed penalty", -0.25D, AttributeModifier.Operation.ADDITION);
   private static final EntityDataAccessor<Boolean> f_34128_ = SynchedEntityData.m_135353_(Witch.class, EntityDataSerializers.f_135035_);
   private int f_34129_;
   private NearestHealableRaiderTargetGoal<Raider> f_34130_;
   private NearestAttackableWitchTargetGoal<Player> f_34131_;

   public Witch(EntityType<? extends Witch> p_34134_, Level p_34135_) {
      super(p_34134_, p_34135_);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_34130_ = new NearestHealableRaiderTargetGoal<>(this, Raider.class, true, (p_34159_) -> {
         return p_34159_ != null && this.m_37886_() && p_34159_.m_6095_() != EntityType.f_20495_;
      });
      this.f_34131_ = new NearestAttackableWitchTargetGoal<>(this, Player.class, 10, true, false, (Predicate<LivingEntity>)null);
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new RangedAttackGoal(this, 1.0D, 60, 10.0F));
      this.f_21345_.m_25352_(2, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(3, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, Raider.class));
      this.f_21346_.m_25352_(2, this.f_34130_);
      this.f_21346_.m_25352_(3, this.f_34131_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(f_34128_, false);
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12548_;
   }

   protected SoundEvent m_7975_(DamageSource p_34154_) {
      return SoundEvents.f_12552_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12550_;
   }

   public void m_34163_(boolean p_34164_) {
      this.m_20088_().m_135381_(f_34128_, p_34164_);
   }

   public boolean m_34161_() {
      return this.m_20088_().m_135370_(f_34128_);
   }

   public static AttributeSupplier.Builder m_34155_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22276_, 26.0D).m_22268_(Attributes.f_22279_, 0.25D);
   }

   public void m_8107_() {
      if (!this.f_19853_.f_46443_ && this.m_6084_()) {
         this.f_34130_.m_26094_();
         if (this.f_34130_.m_26093_() <= 0) {
            this.f_34131_.m_26083_(true);
         } else {
            this.f_34131_.m_26083_(false);
         }

         if (this.m_34161_()) {
            if (this.f_34129_-- <= 0) {
               this.m_34163_(false);
               ItemStack itemstack = this.m_21205_();
               this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
               if (itemstack.m_150930_(Items.f_42589_)) {
                  List<MobEffectInstance> list = PotionUtils.m_43547_(itemstack);
                  if (list != null) {
                     for(MobEffectInstance mobeffectinstance : list) {
                        this.m_7292_(new MobEffectInstance(mobeffectinstance));
                     }
                  }
               }

               this.m_21051_(Attributes.f_22279_).m_22130_(f_34127_);
            }
         } else {
            Potion potion = null;
            if (this.f_19796_.nextFloat() < 0.15F && this.m_19941_(FluidTags.f_13131_) && !this.m_21023_(MobEffects.f_19608_)) {
               potion = Potions.f_43621_;
            } else if (this.f_19796_.nextFloat() < 0.15F && (this.m_6060_() || this.m_21225_() != null && this.m_21225_().m_19384_()) && !this.m_21023_(MobEffects.f_19607_)) {
               potion = Potions.f_43610_;
            } else if (this.f_19796_.nextFloat() < 0.05F && this.m_21223_() < this.m_21233_()) {
               potion = Potions.f_43623_;
            } else if (this.f_19796_.nextFloat() < 0.5F && this.m_5448_() != null && !this.m_21023_(MobEffects.f_19596_) && this.m_5448_().m_20280_(this) > 121.0D) {
               potion = Potions.f_43612_;
            }

            if (potion != null) {
               this.m_8061_(EquipmentSlot.MAINHAND, PotionUtils.m_43549_(new ItemStack(Items.f_42589_), potion));
               this.f_34129_ = this.m_21205_().m_41779_();
               this.m_34163_(true);
               if (!this.m_20067_()) {
                  this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12551_, this.m_5720_(), 1.0F, 0.8F + this.f_19796_.nextFloat() * 0.4F);
               }

               AttributeInstance attributeinstance = this.m_21051_(Attributes.f_22279_);
               attributeinstance.m_22130_(f_34127_);
               attributeinstance.m_22118_(f_34127_);
            }
         }

         if (this.f_19796_.nextFloat() < 7.5E-4F) {
            this.f_19853_.m_7605_(this, (byte)15);
         }
      }

      super.m_8107_();
   }

   public SoundEvent m_7930_() {
      return SoundEvents.f_12549_;
   }

   public void m_7822_(byte p_34138_) {
      if (p_34138_ == 15) {
         for(int i = 0; i < this.f_19796_.nextInt(35) + 10; ++i) {
            this.f_19853_.m_7106_(ParticleTypes.f_123771_, this.m_20185_() + this.f_19796_.nextGaussian() * (double)0.13F, this.m_142469_().f_82292_ + 0.5D + this.f_19796_.nextGaussian() * (double)0.13F, this.m_20189_() + this.f_19796_.nextGaussian() * (double)0.13F, 0.0D, 0.0D, 0.0D);
         }
      } else {
         super.m_7822_(p_34138_);
      }

   }

   protected float m_6515_(DamageSource p_34149_, float p_34150_) {
      p_34150_ = super.m_6515_(p_34149_, p_34150_);
      if (p_34149_.m_7639_() == this) {
         p_34150_ = 0.0F;
      }

      if (p_34149_.m_19387_()) {
         p_34150_ = (float)((double)p_34150_ * 0.15D);
      }

      return p_34150_;
   }

   public void m_6504_(LivingEntity p_34143_, float p_34144_) {
      if (!this.m_34161_()) {
         Vec3 vec3 = p_34143_.m_20184_();
         double d0 = p_34143_.m_20185_() + vec3.f_82479_ - this.m_20185_();
         double d1 = p_34143_.m_20188_() - (double)1.1F - this.m_20186_();
         double d2 = p_34143_.m_20189_() + vec3.f_82481_ - this.m_20189_();
         double d3 = Math.sqrt(d0 * d0 + d2 * d2);
         Potion potion = Potions.f_43582_;
         if (p_34143_ instanceof Raider) {
            if (p_34143_.m_21223_() <= 4.0F) {
               potion = Potions.f_43623_;
            } else {
               potion = Potions.f_43587_;
            }

            this.m_6710_((LivingEntity)null);
         } else if (d3 >= 8.0D && !p_34143_.m_21023_(MobEffects.f_19597_)) {
            potion = Potions.f_43615_;
         } else if (p_34143_.m_21223_() >= 8.0F && !p_34143_.m_21023_(MobEffects.f_19614_)) {
            potion = Potions.f_43584_;
         } else if (d3 <= 3.0D && !p_34143_.m_21023_(MobEffects.f_19613_) && this.f_19796_.nextFloat() < 0.25F) {
            potion = Potions.f_43593_;
         }

         ThrownPotion thrownpotion = new ThrownPotion(this.f_19853_, this);
         thrownpotion.m_37446_(PotionUtils.m_43549_(new ItemStack(Items.f_42736_), potion));
         thrownpotion.m_146926_(thrownpotion.m_146909_() - -20.0F);
         thrownpotion.m_6686_(d0, d1 + d3 * 0.2D, d2, 0.75F, 8.0F);
         if (!this.m_20067_()) {
            this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12553_, this.m_5720_(), 1.0F, 0.8F + this.f_19796_.nextFloat() * 0.4F);
         }

         this.f_19853_.m_7967_(thrownpotion);
      }
   }

   protected float m_6431_(Pose p_34146_, EntityDimensions p_34147_) {
      return 1.62F;
   }

   public void m_7895_(int p_34140_, boolean p_34141_) {
   }

   public boolean m_7490_() {
      return false;
   }
}