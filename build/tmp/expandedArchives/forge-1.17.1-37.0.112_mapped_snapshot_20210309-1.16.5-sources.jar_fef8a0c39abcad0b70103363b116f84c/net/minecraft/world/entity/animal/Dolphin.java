package net.minecraft.world.entity.animal;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreathAirGoal;
import net.minecraft.world.entity.ai.goal.DolphinJumpGoal;
import net.minecraft.world.entity.ai.goal.FollowBoatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.TryFindWaterGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Guardian;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;

public class Dolphin extends WaterAnimal {
   private static final EntityDataAccessor<BlockPos> f_28312_ = SynchedEntityData.m_135353_(Dolphin.class, EntityDataSerializers.f_135038_);
   private static final EntityDataAccessor<Boolean> f_28313_ = SynchedEntityData.m_135353_(Dolphin.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> f_28310_ = SynchedEntityData.m_135353_(Dolphin.class, EntityDataSerializers.f_135028_);
   static final TargetingConditions f_28311_ = TargetingConditions.m_148353_().m_26883_(10.0D).m_148355_();
   public static final int f_148892_ = 4800;
   private static final int f_148893_ = 2400;
   public static final Predicate<ItemEntity> f_28309_ = (p_28369_) -> {
      return !p_28369_.m_32063_() && p_28369_.m_6084_() && p_28369_.m_20069_();
   };

   public Dolphin(EntityType<? extends Dolphin> p_28316_, Level p_28317_) {
      super(p_28316_, p_28317_);
      this.f_21342_ = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
      this.f_21365_ = new SmoothSwimmingLookControl(this, 10);
      this.m_21553_(true);
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_28332_, DifficultyInstance p_28333_, MobSpawnType p_28334_, @Nullable SpawnGroupData p_28335_, @Nullable CompoundTag p_28336_) {
      this.m_20301_(this.m_6062_());
      this.m_146926_(0.0F);
      return super.m_6518_(p_28332_, p_28333_, p_28334_, p_28335_, p_28336_);
   }

   public boolean m_6040_() {
      return false;
   }

   protected void m_6229_(int p_28326_) {
   }

   public void m_28384_(BlockPos p_28385_) {
      this.f_19804_.m_135381_(f_28312_, p_28385_);
   }

   public BlockPos m_28387_() {
      return this.f_19804_.m_135370_(f_28312_);
   }

   public boolean m_28377_() {
      return this.f_19804_.m_135370_(f_28313_);
   }

   public void m_28393_(boolean p_28394_) {
      this.f_19804_.m_135381_(f_28313_, p_28394_);
   }

   public int m_28378_() {
      return this.f_19804_.m_135370_(f_28310_);
   }

   public void m_28343_(int p_28344_) {
      this.f_19804_.m_135381_(f_28310_, p_28344_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_28312_, BlockPos.f_121853_);
      this.f_19804_.m_135372_(f_28313_, false);
      this.f_19804_.m_135372_(f_28310_, 2400);
   }

   public void m_7380_(CompoundTag p_28364_) {
      super.m_7380_(p_28364_);
      p_28364_.m_128405_("TreasurePosX", this.m_28387_().m_123341_());
      p_28364_.m_128405_("TreasurePosY", this.m_28387_().m_123342_());
      p_28364_.m_128405_("TreasurePosZ", this.m_28387_().m_123343_());
      p_28364_.m_128379_("GotFish", this.m_28377_());
      p_28364_.m_128405_("Moistness", this.m_28378_());
   }

   public void m_7378_(CompoundTag p_28340_) {
      int i = p_28340_.m_128451_("TreasurePosX");
      int j = p_28340_.m_128451_("TreasurePosY");
      int k = p_28340_.m_128451_("TreasurePosZ");
      this.m_28384_(new BlockPos(i, j, k));
      super.m_7378_(p_28340_);
      this.m_28393_(p_28340_.m_128471_("GotFish"));
      this.m_28343_(p_28340_.m_128451_("Moistness"));
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new BreathAirGoal(this));
      this.f_21345_.m_25352_(0, new TryFindWaterGoal(this));
      this.f_21345_.m_25352_(1, new Dolphin.DolphinSwimToTreasureGoal(this));
      this.f_21345_.m_25352_(2, new Dolphin.DolphinSwimWithPlayerGoal(this, 4.0D));
      this.f_21345_.m_25352_(4, new RandomSwimmingGoal(this, 1.0D, 10));
      this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(5, new DolphinJumpGoal(this, 10));
      this.f_21345_.m_25352_(6, new MeleeAttackGoal(this, (double)1.2F, true));
      this.f_21345_.m_25352_(8, new Dolphin.PlayWithItemsGoal());
      this.f_21345_.m_25352_(8, new FollowBoatGoal(this));
      this.f_21345_.m_25352_(9, new AvoidEntityGoal<>(this, Guardian.class, 8.0F, 1.0D, 1.0D));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this, Guardian.class)).m_26044_());
   }

   public static AttributeSupplier.Builder m_28379_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22279_, (double)1.2F).m_22268_(Attributes.f_22281_, 3.0D);
   }

   protected PathNavigation m_6037_(Level p_28362_) {
      return new WaterBoundPathNavigation(this, p_28362_);
   }

   public boolean m_7327_(Entity p_28319_) {
      boolean flag = p_28319_.m_6469_(DamageSource.m_19370_(this), (float)((int)this.m_21133_(Attributes.f_22281_)));
      if (flag) {
         this.m_19970_(this, p_28319_);
         this.m_5496_(SoundEvents.f_11801_, 1.0F, 1.0F);
      }

      return flag;
   }

   public int m_6062_() {
      return 4800;
   }

   protected int m_7305_(int p_28389_) {
      return this.m_6062_();
   }

   protected float m_6431_(Pose p_28352_, EntityDimensions p_28353_) {
      return 0.3F;
   }

   public int m_8132_() {
      return 1;
   }

   public int m_8085_() {
      return 1;
   }

   protected boolean m_7341_(Entity p_28391_) {
      return true;
   }

   public boolean m_7066_(ItemStack p_28376_) {
      EquipmentSlot equipmentslot = Mob.m_147233_(p_28376_);
      if (!this.m_6844_(equipmentslot).m_41619_()) {
         return false;
      } else {
         return equipmentslot == EquipmentSlot.MAINHAND && super.m_7066_(p_28376_);
      }
   }

   protected void m_7581_(ItemEntity p_28357_) {
      if (this.m_6844_(EquipmentSlot.MAINHAND).m_41619_()) {
         ItemStack itemstack = p_28357_.m_32055_();
         if (this.m_7252_(itemstack)) {
            this.m_21053_(p_28357_);
            this.m_8061_(EquipmentSlot.MAINHAND, itemstack);
            this.f_21347_[EquipmentSlot.MAINHAND.m_20749_()] = 2.0F;
            this.m_7938_(p_28357_, itemstack.m_41613_());
            p_28357_.m_146870_();
         }
      }

   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_21525_()) {
         this.m_20301_(this.m_6062_());
      } else {
         if (this.m_20071_()) {
            this.m_28343_(2400);
         } else {
            this.m_28343_(this.m_28378_() - 1);
            if (this.m_28378_() <= 0) {
               this.m_6469_(DamageSource.f_19324_, 1.0F);
            }

            if (this.f_19861_) {
               this.m_20256_(this.m_20184_().m_82520_((double)((this.f_19796_.nextFloat() * 2.0F - 1.0F) * 0.2F), 0.5D, (double)((this.f_19796_.nextFloat() * 2.0F - 1.0F) * 0.2F)));
               this.m_146922_(this.f_19796_.nextFloat() * 360.0F);
               this.f_19861_ = false;
               this.f_19812_ = true;
            }
         }

         if (this.f_19853_.f_46443_ && this.m_20069_() && this.m_20184_().m_82556_() > 0.03D) {
            Vec3 vec3 = this.m_20252_(0.0F);
            float f = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F)) * 0.3F;
            float f1 = Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F)) * 0.3F;
            float f2 = 1.2F - this.f_19796_.nextFloat() * 0.7F;

            for(int i = 0; i < 2; ++i) {
               this.f_19853_.m_7106_(ParticleTypes.f_123776_, this.m_20185_() - vec3.f_82479_ * (double)f2 + (double)f, this.m_20186_() - vec3.f_82480_, this.m_20189_() - vec3.f_82481_ * (double)f2 + (double)f1, 0.0D, 0.0D, 0.0D);
               this.f_19853_.m_7106_(ParticleTypes.f_123776_, this.m_20185_() - vec3.f_82479_ * (double)f2 - (double)f, this.m_20186_() - vec3.f_82480_, this.m_20189_() - vec3.f_82481_ * (double)f2 - (double)f1, 0.0D, 0.0D, 0.0D);
            }
         }

      }
   }

   public void m_7822_(byte p_28324_) {
      if (p_28324_ == 38) {
         this.m_28337_(ParticleTypes.f_123748_);
      } else {
         super.m_7822_(p_28324_);
      }

   }

   private void m_28337_(ParticleOptions p_28338_) {
      for(int i = 0; i < 7; ++i) {
         double d0 = this.f_19796_.nextGaussian() * 0.01D;
         double d1 = this.f_19796_.nextGaussian() * 0.01D;
         double d2 = this.f_19796_.nextGaussian() * 0.01D;
         this.f_19853_.m_7106_(p_28338_, this.m_20208_(1.0D), this.m_20187_() + 0.2D, this.m_20262_(1.0D), d0, d1, d2);
      }

   }

   protected InteractionResult m_6071_(Player p_28359_, InteractionHand p_28360_) {
      ItemStack itemstack = p_28359_.m_21120_(p_28360_);
      if (!itemstack.m_41619_() && itemstack.m_150922_(ItemTags.f_13156_)) {
         if (!this.f_19853_.f_46443_) {
            this.m_5496_(SoundEvents.f_11803_, 1.0F, 1.0F);
         }

         this.m_28393_(true);
         if (!p_28359_.m_150110_().f_35937_) {
            itemstack.m_41774_(1);
         }

         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else {
         return super.m_6071_(p_28359_, p_28360_);
      }
   }

   public static boolean m_28345_(EntityType<Dolphin> p_28346_, LevelAccessor p_28347_, MobSpawnType p_28348_, BlockPos p_28349_, Random p_28350_) {
      if (p_28349_.m_123342_() > 45 && p_28349_.m_123342_() < p_28347_.m_5736_()) {
         Optional<ResourceKey<Biome>> optional = p_28347_.m_45837_(p_28349_);
         return (!Objects.equals(optional, Optional.of(Biomes.f_48174_)) || !Objects.equals(optional, Optional.of(Biomes.f_48225_))) && p_28347_.m_6425_(p_28349_).m_76153_(FluidTags.f_13131_);
      } else {
         return false;
      }
   }

   protected SoundEvent m_7975_(DamageSource p_28374_) {
      return SoundEvents.f_11804_;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return SoundEvents.f_11802_;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return this.m_20069_() ? SoundEvents.f_11800_ : SoundEvents.f_11799_;
   }

   protected SoundEvent m_5509_() {
      return SoundEvents.f_11807_;
   }

   protected SoundEvent m_5501_() {
      return SoundEvents.f_11808_;
   }

   protected boolean m_28380_() {
      BlockPos blockpos = this.m_21573_().m_26567_();
      return blockpos != null ? blockpos.m_123306_(this.m_20182_(), 12.0D) : false;
   }

   public void m_7023_(Vec3 p_28383_) {
      if (this.m_6142_() && this.m_20069_()) {
         this.m_19920_(this.m_6113_(), p_28383_);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9D));
         if (this.m_5448_() == null) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.005D, 0.0D));
         }
      } else {
         super.m_7023_(p_28383_);
      }

   }

   public boolean m_6573_(Player p_28330_) {
      return true;
   }

   static class DolphinSwimToTreasureGoal extends Goal {
      private final Dolphin f_28399_;
      private boolean f_28400_;

      DolphinSwimToTreasureGoal(Dolphin p_28402_) {
         this.f_28399_ = p_28402_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      public boolean m_6767_() {
         return false;
      }

      public boolean m_8036_() {
         return this.f_28399_.m_28377_() && this.f_28399_.m_20146_() >= 100;
      }

      public boolean m_8045_() {
         BlockPos blockpos = this.f_28399_.m_28387_();
         return !(new BlockPos((double)blockpos.m_123341_(), this.f_28399_.m_20186_(), (double)blockpos.m_123343_())).m_123306_(this.f_28399_.m_20182_(), 4.0D) && !this.f_28400_ && this.f_28399_.m_20146_() >= 100;
      }

      public void m_8056_() {
         if (this.f_28399_.f_19853_ instanceof ServerLevel) {
            ServerLevel serverlevel = (ServerLevel)this.f_28399_.f_19853_;
            this.f_28400_ = false;
            this.f_28399_.m_21573_().m_26573_();
            BlockPos blockpos = this.f_28399_.m_142538_();
            StructureFeature<?> structurefeature = (double)serverlevel.f_46441_.nextFloat() >= 0.5D ? StructureFeature.f_67024_ : StructureFeature.f_67020_;
            BlockPos blockpos1 = serverlevel.m_8717_(structurefeature, blockpos, 50, false);
            if (blockpos1 == null) {
               StructureFeature<?> structurefeature1 = structurefeature.equals(StructureFeature.f_67024_) ? StructureFeature.f_67020_ : StructureFeature.f_67024_;
               BlockPos blockpos2 = serverlevel.m_8717_(structurefeature1, blockpos, 50, false);
               if (blockpos2 == null) {
                  this.f_28400_ = true;
                  return;
               }

               this.f_28399_.m_28384_(blockpos2);
            } else {
               this.f_28399_.m_28384_(blockpos1);
            }

            serverlevel.m_7605_(this.f_28399_, (byte)38);
         }
      }

      public void m_8041_() {
         BlockPos blockpos = this.f_28399_.m_28387_();
         if ((new BlockPos((double)blockpos.m_123341_(), this.f_28399_.m_20186_(), (double)blockpos.m_123343_())).m_123306_(this.f_28399_.m_20182_(), 4.0D) || this.f_28400_) {
            this.f_28399_.m_28393_(false);
         }

      }

      public void m_8037_() {
         Level level = this.f_28399_.f_19853_;
         if (this.f_28399_.m_28380_() || this.f_28399_.m_21573_().m_26571_()) {
            Vec3 vec3 = Vec3.m_82512_(this.f_28399_.m_28387_());
            Vec3 vec31 = DefaultRandomPos.m_148412_(this.f_28399_, 16, 1, vec3, (double)((float)Math.PI / 8F));
            if (vec31 == null) {
               vec31 = DefaultRandomPos.m_148412_(this.f_28399_, 8, 4, vec3, (double)((float)Math.PI / 2F));
            }

            if (vec31 != null) {
               BlockPos blockpos = new BlockPos(vec31);
               if (!level.m_6425_(blockpos).m_76153_(FluidTags.f_13131_) || !level.m_8055_(blockpos).m_60647_(level, blockpos, PathComputationType.WATER)) {
                  vec31 = DefaultRandomPos.m_148412_(this.f_28399_, 8, 5, vec3, (double)((float)Math.PI / 2F));
               }
            }

            if (vec31 == null) {
               this.f_28400_ = true;
               return;
            }

            this.f_28399_.m_21563_().m_24950_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, (float)(this.f_28399_.m_8085_() + 20), (float)this.f_28399_.m_8132_());
            this.f_28399_.m_21573_().m_26519_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, 1.3D);
            if (level.f_46441_.nextInt(80) == 0) {
               level.m_7605_(this.f_28399_, (byte)38);
            }
         }

      }
   }

   static class DolphinSwimWithPlayerGoal extends Goal {
      private final Dolphin f_28409_;
      private final double f_28410_;
      private Player f_28411_;

      DolphinSwimWithPlayerGoal(Dolphin p_28413_, double p_28414_) {
         this.f_28409_ = p_28413_;
         this.f_28410_ = p_28414_;
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         this.f_28411_ = this.f_28409_.f_19853_.m_45946_(Dolphin.f_28311_, this.f_28409_);
         if (this.f_28411_ == null) {
            return false;
         } else {
            return this.f_28411_.m_6069_() && this.f_28409_.m_5448_() != this.f_28411_;
         }
      }

      public boolean m_8045_() {
         return this.f_28411_ != null && this.f_28411_.m_6069_() && this.f_28409_.m_20280_(this.f_28411_) < 256.0D;
      }

      public void m_8056_() {
         this.f_28411_.m_147207_(new MobEffectInstance(MobEffects.f_19593_, 100), this.f_28409_);
      }

      public void m_8041_() {
         this.f_28411_ = null;
         this.f_28409_.m_21573_().m_26573_();
      }

      public void m_8037_() {
         this.f_28409_.m_21563_().m_24960_(this.f_28411_, (float)(this.f_28409_.m_8085_() + 20), (float)this.f_28409_.m_8132_());
         if (this.f_28409_.m_20280_(this.f_28411_) < 6.25D) {
            this.f_28409_.m_21573_().m_26573_();
         } else {
            this.f_28409_.m_21573_().m_5624_(this.f_28411_, this.f_28410_);
         }

         if (this.f_28411_.m_6069_() && this.f_28411_.f_19853_.f_46441_.nextInt(6) == 0) {
            this.f_28411_.m_147207_(new MobEffectInstance(MobEffects.f_19593_, 100), this.f_28409_);
         }

      }
   }

   class PlayWithItemsGoal extends Goal {
      private int f_28421_;

      public boolean m_8036_() {
         if (this.f_28421_ > Dolphin.this.f_19797_) {
            return false;
         } else {
            List<ItemEntity> list = Dolphin.this.f_19853_.m_6443_(ItemEntity.class, Dolphin.this.m_142469_().m_82377_(8.0D, 8.0D, 8.0D), Dolphin.f_28309_);
            return !list.isEmpty() || !Dolphin.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_();
         }
      }

      public void m_8056_() {
         List<ItemEntity> list = Dolphin.this.f_19853_.m_6443_(ItemEntity.class, Dolphin.this.m_142469_().m_82377_(8.0D, 8.0D, 8.0D), Dolphin.f_28309_);
         if (!list.isEmpty()) {
            Dolphin.this.m_21573_().m_5624_(list.get(0), (double)1.2F);
            Dolphin.this.m_5496_(SoundEvents.f_11806_, 1.0F, 1.0F);
         }

         this.f_28421_ = 0;
      }

      public void m_8041_() {
         ItemStack itemstack = Dolphin.this.m_6844_(EquipmentSlot.MAINHAND);
         if (!itemstack.m_41619_()) {
            this.m_28428_(itemstack);
            Dolphin.this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
            this.f_28421_ = Dolphin.this.f_19797_ + Dolphin.this.f_19796_.nextInt(100);
         }

      }

      public void m_8037_() {
         List<ItemEntity> list = Dolphin.this.f_19853_.m_6443_(ItemEntity.class, Dolphin.this.m_142469_().m_82377_(8.0D, 8.0D, 8.0D), Dolphin.f_28309_);
         ItemStack itemstack = Dolphin.this.m_6844_(EquipmentSlot.MAINHAND);
         if (!itemstack.m_41619_()) {
            this.m_28428_(itemstack);
            Dolphin.this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
         } else if (!list.isEmpty()) {
            Dolphin.this.m_21573_().m_5624_(list.get(0), (double)1.2F);
         }

      }

      private void m_28428_(ItemStack p_28429_) {
         if (!p_28429_.m_41619_()) {
            double d0 = Dolphin.this.m_20188_() - (double)0.3F;
            ItemEntity itementity = new ItemEntity(Dolphin.this.f_19853_, Dolphin.this.m_20185_(), d0, Dolphin.this.m_20189_(), p_28429_);
            itementity.m_32010_(40);
            itementity.m_32052_(Dolphin.this.m_142081_());
            float f = 0.3F;
            float f1 = Dolphin.this.f_19796_.nextFloat() * ((float)Math.PI * 2F);
            float f2 = 0.02F * Dolphin.this.f_19796_.nextFloat();
            itementity.m_20334_((double)(0.3F * -Mth.m_14031_(Dolphin.this.m_146908_() * ((float)Math.PI / 180F)) * Mth.m_14089_(Dolphin.this.m_146909_() * ((float)Math.PI / 180F)) + Mth.m_14089_(f1) * f2), (double)(0.3F * Mth.m_14031_(Dolphin.this.m_146909_() * ((float)Math.PI / 180F)) * 1.5F), (double)(0.3F * Mth.m_14089_(Dolphin.this.m_146908_() * ((float)Math.PI / 180F)) * Mth.m_14089_(Dolphin.this.m_146909_() * ((float)Math.PI / 180F)) + Mth.m_14031_(f1) * f2));
            Dolphin.this.f_19853_.m_7967_(itementity);
         }
      }
   }
}