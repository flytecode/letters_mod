package net.minecraft.world.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractSkeleton extends Monster implements RangedAttackMob {
   private final RangedBowAttackGoal<AbstractSkeleton> f_32130_ = new RangedBowAttackGoal<>(this, 1.0D, 20, 15.0F);
   private final MeleeAttackGoal f_32131_ = new MeleeAttackGoal(this, 1.2D, false) {
      public void m_8041_() {
         super.m_8041_();
         AbstractSkeleton.this.m_21561_(false);
      }

      public void m_8056_() {
         super.m_8056_();
         AbstractSkeleton.this.m_21561_(true);
      }
   };

   protected AbstractSkeleton(EntityType<? extends AbstractSkeleton> p_32133_, Level p_32134_) {
      super(p_32133_, p_32134_);
      this.m_32164_();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(2, new RestrictSunGoal(this));
      this.f_21345_.m_25352_(3, new FleeSunGoal(this, 1.0D));
      this.f_21345_.m_25352_(3, new AvoidEntityGoal<>(this, Wolf.class, 6.0F, 1.0D, 1.2D));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.f_30122_));
   }

   public static AttributeSupplier.Builder m_32166_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22279_, 0.25D);
   }

   protected void m_7355_(BlockPos p_32159_, BlockState p_32160_) {
      this.m_5496_(this.m_7878_(), 0.15F, 1.0F);
   }

   protected abstract SoundEvent m_7878_();

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public void m_8107_() {
      boolean flag = this.m_21527_();
      if (flag) {
         ItemStack itemstack = this.m_6844_(EquipmentSlot.HEAD);
         if (!itemstack.m_41619_()) {
            if (itemstack.m_41763_()) {
               itemstack.m_41721_(itemstack.m_41773_() + this.f_19796_.nextInt(2));
               if (itemstack.m_41773_() >= itemstack.m_41776_()) {
                  this.m_21166_(EquipmentSlot.HEAD);
                  this.m_8061_(EquipmentSlot.HEAD, ItemStack.f_41583_);
               }
            }

            flag = false;
         }

         if (flag) {
            this.m_20254_(8);
         }
      }

      super.m_8107_();
   }

   public void m_6083_() {
      super.m_6083_();
      if (this.m_20202_() instanceof PathfinderMob) {
         PathfinderMob pathfindermob = (PathfinderMob)this.m_20202_();
         this.f_20883_ = pathfindermob.f_20883_;
      }

   }

   protected void m_6851_(DifficultyInstance p_32136_) {
      super.m_6851_(p_32136_);
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42411_));
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_32146_, DifficultyInstance p_32147_, MobSpawnType p_32148_, @Nullable SpawnGroupData p_32149_, @Nullable CompoundTag p_32150_) {
      p_32149_ = super.m_6518_(p_32146_, p_32147_, p_32148_, p_32149_, p_32150_);
      this.m_6851_(p_32147_);
      this.m_6850_(p_32147_);
      this.m_32164_();
      this.m_21553_(this.f_19796_.nextFloat() < 0.55F * p_32147_.m_19057_());
      if (this.m_6844_(EquipmentSlot.HEAD).m_41619_()) {
         LocalDate localdate = LocalDate.now();
         int i = localdate.get(ChronoField.DAY_OF_MONTH);
         int j = localdate.get(ChronoField.MONTH_OF_YEAR);
         if (j == 10 && i == 31 && this.f_19796_.nextFloat() < 0.25F) {
            this.m_8061_(EquipmentSlot.HEAD, new ItemStack(this.f_19796_.nextFloat() < 0.1F ? Blocks.f_50144_ : Blocks.f_50143_));
            this.f_21348_[EquipmentSlot.HEAD.m_20749_()] = 0.0F;
         }
      }

      return p_32149_;
   }

   public void m_32164_() {
      if (this.f_19853_ != null && !this.f_19853_.f_46443_) {
         this.f_21345_.m_25363_(this.f_32131_);
         this.f_21345_.m_25363_(this.f_32130_);
         ItemStack itemstack = this.m_21120_(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem));
         if (itemstack.m_150930_(Items.f_42411_)) {
            int i = 20;
            if (this.f_19853_.m_46791_() != Difficulty.HARD) {
               i = 40;
            }

            this.f_32130_.m_25797_(i);
            this.f_21345_.m_25352_(4, this.f_32130_);
         } else {
            this.f_21345_.m_25352_(4, this.f_32131_);
         }

      }
   }

   public void m_6504_(LivingEntity p_32141_, float p_32142_) {
      ItemStack itemstack = this.m_6298_(this.m_21120_(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem)));
      AbstractArrow abstractarrow = this.m_7932_(itemstack, p_32142_);
      if (this.m_21205_().m_41720_() instanceof net.minecraft.world.item.BowItem)
         abstractarrow = ((net.minecraft.world.item.BowItem)this.m_21205_().m_41720_()).customArrow(abstractarrow);
      double d0 = p_32141_.m_20185_() - this.m_20185_();
      double d1 = p_32141_.m_20227_(0.3333333333333333D) - abstractarrow.m_20186_();
      double d2 = p_32141_.m_20189_() - this.m_20189_();
      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      abstractarrow.m_6686_(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.f_19853_.m_46791_().m_19028_() * 4));
      this.m_5496_(SoundEvents.f_12382_, 1.0F, 1.0F / (this.m_21187_().nextFloat() * 0.4F + 0.8F));
      this.f_19853_.m_7967_(abstractarrow);
   }

   protected AbstractArrow m_7932_(ItemStack p_32156_, float p_32157_) {
      return ProjectileUtil.m_37300_(this, p_32156_, p_32157_);
   }

   public boolean m_5886_(ProjectileWeaponItem p_32144_) {
      return p_32144_ == Items.f_42411_;
   }

   public void m_7378_(CompoundTag p_32152_) {
      super.m_7378_(p_32152_);
      this.m_32164_();
   }

   public void m_8061_(EquipmentSlot p_32138_, ItemStack p_32139_) {
      super.m_8061_(p_32138_, p_32139_);
      if (!this.f_19853_.f_46443_) {
         this.m_32164_();
      }

   }

   protected float m_6431_(Pose p_32154_, EntityDimensions p_32155_) {
      return 1.74F;
   }

   public double m_6049_() {
      return -0.6D;
   }

   public boolean m_142548_() {
      return this.m_146890_();
   }
}
