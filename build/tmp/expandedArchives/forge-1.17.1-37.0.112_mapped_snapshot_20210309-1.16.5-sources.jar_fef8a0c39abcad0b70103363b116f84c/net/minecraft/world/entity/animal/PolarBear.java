package net.minecraft.world.entity.animal;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PolarBear extends Animal implements NeutralMob {
   private static final EntityDataAccessor<Boolean> f_29510_ = SynchedEntityData.m_135353_(PolarBear.class, EntityDataSerializers.f_135035_);
   private static final float f_149003_ = 6.0F;
   private float f_29511_;
   private float f_29512_;
   private int f_29513_;
   private static final UniformInt f_29514_ = TimeUtil.m_145020_(20, 39);
   private int f_29515_;
   private UUID f_29516_;

   public PolarBear(EntityType<? extends PolarBear> p_29519_, Level p_29520_) {
      super(p_29519_, p_29520_);
   }

   public AgeableMob m_142606_(ServerLevel p_149005_, AgeableMob p_149006_) {
      return EntityType.f_20514_.m_20615_(p_149005_);
   }

   public boolean m_6898_(ItemStack p_29565_) {
      return false;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new PolarBear.PolarBearMeleeAttackGoal());
      this.f_21345_.m_25352_(1, new PolarBear.PolarBearPanicGoal());
      this.f_21345_.m_25352_(4, new FollowParentGoal(this, 1.25D));
      this.f_21345_.m_25352_(5, new RandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new PolarBear.PolarBearHurtByTargetGoal());
      this.f_21346_.m_25352_(2, new PolarBear.PolarBearAttackPlayersGoal());
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::m_21674_));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal<>(this, Fox.class, 10, true, true, (Predicate<LivingEntity>)null));
      this.f_21346_.m_25352_(5, new ResetUniversalAngerTargetGoal<>(this, false));
   }

   public static AttributeSupplier.Builder m_29560_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 30.0D).m_22268_(Attributes.f_22277_, 20.0D).m_22268_(Attributes.f_22279_, 0.25D).m_22268_(Attributes.f_22281_, 6.0D);
   }

   public static boolean m_29549_(EntityType<PolarBear> p_29550_, LevelAccessor p_29551_, MobSpawnType p_29552_, BlockPos p_29553_, Random p_29554_) {
      Optional<ResourceKey<Biome>> optional = p_29551_.m_45837_(p_29553_);
      if (!Objects.equals(optional, Optional.of(Biomes.f_48211_)) && !Objects.equals(optional, Optional.of(Biomes.f_48172_))) {
         return m_27577_(p_29550_, p_29551_, p_29552_, p_29553_, p_29554_);
      } else {
         return p_29551_.m_45524_(p_29553_, 0) > 8 && p_29551_.m_8055_(p_29553_.m_7495_()).m_60713_(Blocks.f_50126_);
      }
   }

   public void m_7378_(CompoundTag p_29541_) {
      super.m_7378_(p_29541_);
      this.m_147285_(this.f_19853_, p_29541_);
   }

   public void m_7380_(CompoundTag p_29548_) {
      super.m_7380_(p_29548_);
      this.m_21678_(p_29548_);
   }

   public void m_6825_() {
      this.m_7870_(f_29514_.m_142270_(this.f_19796_));
   }

   public void m_7870_(int p_29543_) {
      this.f_29515_ = p_29543_;
   }

   public int m_6784_() {
      return this.f_29515_;
   }

   public void m_6925_(@Nullable UUID p_29539_) {
      this.f_29516_ = p_29539_;
   }

   public UUID m_6120_() {
      return this.f_29516_;
   }

   protected SoundEvent m_7515_() {
      return this.m_6162_() ? SoundEvents.f_12281_ : SoundEvents.f_12280_;
   }

   protected SoundEvent m_7975_(DamageSource p_29559_) {
      return SoundEvents.f_12283_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12282_;
   }

   protected void m_7355_(BlockPos p_29545_, BlockState p_29546_) {
      this.m_5496_(SoundEvents.f_12284_, 0.15F, 1.0F);
   }

   protected void m_29561_() {
      if (this.f_29513_ <= 0) {
         this.m_5496_(SoundEvents.f_12285_, 1.0F, this.m_6100_());
         this.f_29513_ = 40;
      }

   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_29510_, false);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19853_.f_46443_) {
         if (this.f_29512_ != this.f_29511_) {
            this.m_6210_();
         }

         this.f_29511_ = this.f_29512_;
         if (this.m_29562_()) {
            this.f_29512_ = Mth.m_14036_(this.f_29512_ + 1.0F, 0.0F, 6.0F);
         } else {
            this.f_29512_ = Mth.m_14036_(this.f_29512_ - 1.0F, 0.0F, 6.0F);
         }
      }

      if (this.f_29513_ > 0) {
         --this.f_29513_;
      }

      if (!this.f_19853_.f_46443_) {
         this.m_21666_((ServerLevel)this.f_19853_, true);
      }

   }

   public EntityDimensions m_6972_(Pose p_29531_) {
      if (this.f_29512_ > 0.0F) {
         float f = this.f_29512_ / 6.0F;
         float f1 = 1.0F + f;
         return super.m_6972_(p_29531_).m_20390_(1.0F, f1);
      } else {
         return super.m_6972_(p_29531_);
      }
   }

   public boolean m_7327_(Entity p_29522_) {
      boolean flag = p_29522_.m_6469_(DamageSource.m_19370_(this), (float)((int)this.m_21133_(Attributes.f_22281_)));
      if (flag) {
         this.m_19970_(this, p_29522_);
      }

      return flag;
   }

   public boolean m_29562_() {
      return this.f_19804_.m_135370_(f_29510_);
   }

   public void m_29567_(boolean p_29568_) {
      this.f_19804_.m_135381_(f_29510_, p_29568_);
   }

   public float m_29569_(float p_29570_) {
      return Mth.m_14179_(p_29570_, this.f_29511_, this.f_29512_) / 6.0F;
   }

   protected float m_6108_() {
      return 0.98F;
   }

   public SpawnGroupData m_6518_(ServerLevelAccessor p_29533_, DifficultyInstance p_29534_, MobSpawnType p_29535_, @Nullable SpawnGroupData p_29536_, @Nullable CompoundTag p_29537_) {
      if (p_29536_ == null) {
         p_29536_ = new AgeableMob.AgeableMobGroupData(1.0F);
      }

      return super.m_6518_(p_29533_, p_29534_, p_29535_, p_29536_, p_29537_);
   }

   class PolarBearAttackPlayersGoal extends NearestAttackableTargetGoal<Player> {
      public PolarBearAttackPlayersGoal() {
         super(PolarBear.this, Player.class, 20, true, true, (Predicate<LivingEntity>)null);
      }

      public boolean m_8036_() {
         if (PolarBear.this.m_6162_()) {
            return false;
         } else {
            if (super.m_8036_()) {
               for(PolarBear polarbear : PolarBear.this.f_19853_.m_45976_(PolarBear.class, PolarBear.this.m_142469_().m_82377_(8.0D, 4.0D, 8.0D))) {
                  if (polarbear.m_6162_()) {
                     return true;
                  }
               }
            }

            return false;
         }
      }

      protected double m_7623_() {
         return super.m_7623_() * 0.5D;
      }
   }

   class PolarBearHurtByTargetGoal extends HurtByTargetGoal {
      public PolarBearHurtByTargetGoal() {
         super(PolarBear.this);
      }

      public void m_8056_() {
         super.m_8056_();
         if (PolarBear.this.m_6162_()) {
            this.m_26047_();
            this.m_8041_();
         }

      }

      protected void m_5766_(Mob p_29580_, LivingEntity p_29581_) {
         if (p_29580_ instanceof PolarBear && !p_29580_.m_6162_()) {
            super.m_5766_(p_29580_, p_29581_);
         }

      }
   }

   class PolarBearMeleeAttackGoal extends MeleeAttackGoal {
      public PolarBearMeleeAttackGoal() {
         super(PolarBear.this, 1.25D, true);
      }

      protected void m_6739_(LivingEntity p_29589_, double p_29590_) {
         double d0 = this.m_6639_(p_29589_);
         if (p_29590_ <= d0 && this.m_25564_()) {
            this.m_25563_();
            this.f_25540_.m_7327_(p_29589_);
            PolarBear.this.m_29567_(false);
         } else if (p_29590_ <= d0 * 2.0D) {
            if (this.m_25564_()) {
               PolarBear.this.m_29567_(false);
               this.m_25563_();
            }

            if (this.m_25565_() <= 10) {
               PolarBear.this.m_29567_(true);
               PolarBear.this.m_29561_();
            }
         } else {
            this.m_25563_();
            PolarBear.this.m_29567_(false);
         }

      }

      public void m_8041_() {
         PolarBear.this.m_29567_(false);
         super.m_8041_();
      }

      protected double m_6639_(LivingEntity p_29587_) {
         return (double)(4.0F + p_29587_.m_20205_());
      }
   }

   class PolarBearPanicGoal extends PanicGoal {
      public PolarBearPanicGoal() {
         super(PolarBear.this, 2.0D);
      }

      public boolean m_8036_() {
         return !PolarBear.this.m_6162_() && !PolarBear.this.m_6060_() ? false : super.m_8036_();
      }
   }
}