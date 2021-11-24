package net.minecraft.world.entity.monster;

import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class Illusioner extends SpellcasterIllager implements RangedAttackMob {
   private static final int f_149715_ = 4;
   private static final int f_149713_ = 3;
   private static final int f_149714_ = 3;
   private int f_32908_;
   private final Vec3[][] f_32909_;

   public Illusioner(EntityType<? extends Illusioner> p_32911_, Level p_32912_) {
      super(p_32911_, p_32912_);
      this.f_21364_ = 5;
      this.f_32909_ = new Vec3[2][4];

      for(int i = 0; i < 4; ++i) {
         this.f_32909_[0][i] = Vec3.f_82478_;
         this.f_32909_[1][i] = Vec3.f_82478_;
      }

   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new SpellcasterIllager.SpellcasterCastingSpellGoal());
      this.f_21345_.m_25352_(4, new Illusioner.IllusionerMirrorSpellGoal());
      this.f_21345_.m_25352_(5, new Illusioner.IllusionerBlindnessSpellGoal());
      this.f_21345_.m_25352_(6, new RangedBowAttackGoal<>(this, 0.5D, 20, 15.0F));
      this.f_21345_.m_25352_(8, new RandomStrollGoal(this, 0.6D));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 3.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this, Raider.class)).m_26044_());
      this.f_21346_.m_25352_(2, (new NearestAttackableTargetGoal<>(this, Player.class, true)).m_26146_(300));
      this.f_21346_.m_25352_(3, (new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false)).m_26146_(300));
      this.f_21346_.m_25352_(3, (new NearestAttackableTargetGoal<>(this, IronGolem.class, false)).m_26146_(300));
   }

   public static AttributeSupplier.Builder m_32931_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22279_, 0.5D).m_22268_(Attributes.f_22277_, 18.0D).m_22268_(Attributes.f_22276_, 32.0D);
   }

   public SpawnGroupData m_6518_(ServerLevelAccessor p_32921_, DifficultyInstance p_32922_, MobSpawnType p_32923_, @Nullable SpawnGroupData p_32924_, @Nullable CompoundTag p_32925_) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42411_));
      return super.m_6518_(p_32921_, p_32922_, p_32923_, p_32924_, p_32925_);
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   public AABB m_6921_() {
      return this.m_142469_().m_82377_(3.0D, 0.0D, 3.0D);
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.f_19853_.f_46443_ && this.m_20145_()) {
         --this.f_32908_;
         if (this.f_32908_ < 0) {
            this.f_32908_ = 0;
         }

         if (this.f_20916_ != 1 && this.f_19797_ % 1200 != 0) {
            if (this.f_20916_ == this.f_20917_ - 1) {
               this.f_32908_ = 3;

               for(int k = 0; k < 4; ++k) {
                  this.f_32909_[0][k] = this.f_32909_[1][k];
                  this.f_32909_[1][k] = new Vec3(0.0D, 0.0D, 0.0D);
               }
            }
         } else {
            this.f_32908_ = 3;
            float f = -6.0F;
            int i = 13;

            for(int j = 0; j < 4; ++j) {
               this.f_32909_[0][j] = this.f_32909_[1][j];
               this.f_32909_[1][j] = new Vec3((double)(-6.0F + (float)this.f_19796_.nextInt(13)) * 0.5D, (double)Math.max(0, this.f_19796_.nextInt(6) - 4), (double)(-6.0F + (float)this.f_19796_.nextInt(13)) * 0.5D);
            }

            for(int l = 0; l < 16; ++l) {
               this.f_19853_.m_7106_(ParticleTypes.f_123796_, this.m_20208_(0.5D), this.m_20187_(), this.m_20246_(0.5D), 0.0D, 0.0D, 0.0D);
            }

            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12052_, this.m_5720_(), 1.0F, 1.0F, false);
         }
      }

   }

   public SoundEvent m_7930_() {
      return SoundEvents.f_12048_;
   }

   public Vec3[] m_32939_(float p_32940_) {
      if (this.f_32908_ <= 0) {
         return this.f_32909_[1];
      } else {
         double d0 = (double)(((float)this.f_32908_ - p_32940_) / 3.0F);
         d0 = Math.pow(d0, 0.25D);
         Vec3[] avec3 = new Vec3[4];

         for(int i = 0; i < 4; ++i) {
            avec3[i] = this.f_32909_[1][i].m_82490_(1.0D - d0).m_82549_(this.f_32909_[0][i].m_82490_(d0));
         }

         return avec3;
      }
   }

   public boolean m_7307_(Entity p_32938_) {
      if (super.m_7307_(p_32938_)) {
         return true;
      } else if (p_32938_ instanceof LivingEntity && ((LivingEntity)p_32938_).m_6336_() == MobType.f_21643_) {
         return this.m_5647_() == null && p_32938_.m_5647_() == null;
      } else {
         return false;
      }
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12048_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12050_;
   }

   protected SoundEvent m_7975_(DamageSource p_32930_) {
      return SoundEvents.f_12051_;
   }

   protected SoundEvent m_7894_() {
      return SoundEvents.f_12049_;
   }

   public void m_7895_(int p_32915_, boolean p_32916_) {
   }

   public void m_6504_(LivingEntity p_32918_, float p_32919_) {
      ItemStack itemstack = this.m_6298_(this.m_21120_(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem)));
      AbstractArrow abstractarrow = ProjectileUtil.m_37300_(this, itemstack, p_32919_);
      if (this.m_21205_().m_41720_() instanceof net.minecraft.world.item.BowItem)
         abstractarrow = ((net.minecraft.world.item.BowItem)this.m_21205_().m_41720_()).customArrow(abstractarrow);
      double d0 = p_32918_.m_20185_() - this.m_20185_();
      double d1 = p_32918_.m_20227_(0.3333333333333333D) - abstractarrow.m_20186_();
      double d2 = p_32918_.m_20189_() - this.m_20189_();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      abstractarrow.m_6686_(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.f_19853_.m_46791_().m_19028_() * 4));
      this.m_5496_(SoundEvents.f_12382_, 1.0F, 1.0F / (this.m_21187_().nextFloat() * 0.4F + 0.8F));
      this.f_19853_.m_7967_(abstractarrow);
   }

   public AbstractIllager.IllagerArmPose m_6768_() {
      if (this.m_33736_()) {
         return AbstractIllager.IllagerArmPose.SPELLCASTING;
      } else {
         return this.m_5912_() ? AbstractIllager.IllagerArmPose.BOW_AND_ARROW : AbstractIllager.IllagerArmPose.CROSSED;
      }
   }

   class IllusionerBlindnessSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
      private int f_32942_;

      public boolean m_8036_() {
         if (!super.m_8036_()) {
            return false;
         } else if (Illusioner.this.m_5448_() == null) {
            return false;
         } else if (Illusioner.this.m_5448_().m_142049_() == this.f_32942_) {
            return false;
         } else {
            return Illusioner.this.f_19853_.m_6436_(Illusioner.this.m_142538_()).m_19049_((float)Difficulty.NORMAL.ordinal());
         }
      }

      public void m_8056_() {
         super.m_8056_();
         this.f_32942_ = Illusioner.this.m_5448_().m_142049_();
      }

      protected int m_8089_() {
         return 20;
      }

      protected int m_8067_() {
         return 180;
      }

      protected void m_8130_() {
         Illusioner.this.m_5448_().m_147207_(new MobEffectInstance(MobEffects.f_19610_, 400), Illusioner.this);
      }

      protected SoundEvent m_7030_() {
         return SoundEvents.f_12053_;
      }

      protected SpellcasterIllager.IllagerSpell m_7269_() {
         return SpellcasterIllager.IllagerSpell.BLINDNESS;
      }
   }

   class IllusionerMirrorSpellGoal extends SpellcasterIllager.SpellcasterUseSpellGoal {
      public boolean m_8036_() {
         if (!super.m_8036_()) {
            return false;
         } else {
            return !Illusioner.this.m_21023_(MobEffects.f_19609_);
         }
      }

      protected int m_8089_() {
         return 20;
      }

      protected int m_8067_() {
         return 340;
      }

      protected void m_8130_() {
         Illusioner.this.m_7292_(new MobEffectInstance(MobEffects.f_19609_, 1200));
      }

      @Nullable
      protected SoundEvent m_7030_() {
         return SoundEvents.f_12054_;
      }

      protected SpellcasterIllager.IllagerSpell m_7269_() {
         return SpellcasterIllager.IllagerSpell.DISAPPEAR;
      }
   }
}
