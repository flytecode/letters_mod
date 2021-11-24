package net.minecraft.world.entity.animal;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.CatLieOnBedGoal;
import net.minecraft.world.entity.ai.goal.CatSitOnBlockGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.OcelotAttackGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NonTameRandomTargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;

public class Cat extends TamableAnimal {
   public static final double f_148842_ = 0.6D;
   public static final double f_148843_ = 0.8D;
   public static final double f_148844_ = 1.33D;
   private static final Ingredient f_28103_ = Ingredient.m_43929_(Items.f_42526_, Items.f_42527_);
   private static final EntityDataAccessor<Integer> f_28104_ = SynchedEntityData.m_135353_(Cat.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> f_28105_ = SynchedEntityData.m_135353_(Cat.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Boolean> f_28106_ = SynchedEntityData.m_135353_(Cat.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> f_28107_ = SynchedEntityData.m_135353_(Cat.class, EntityDataSerializers.f_135028_);
   public static final int f_148845_ = 0;
   public static final int f_148846_ = 1;
   public static final int f_148847_ = 2;
   public static final int f_148848_ = 3;
   public static final int f_148849_ = 4;
   public static final int f_148850_ = 5;
   public static final int f_148851_ = 6;
   public static final int f_148852_ = 7;
   public static final int f_148853_ = 8;
   public static final int f_148854_ = 9;
   public static final int f_148855_ = 10;
   private static final int f_148856_ = 11;
   private static final int f_148857_ = 10;
   public static final Map<Integer, ResourceLocation> f_28102_ = Util.m_137469_(Maps.newHashMap(), (p_28140_) -> {
      p_28140_.put(0, new ResourceLocation("textures/entity/cat/tabby.png"));
      p_28140_.put(1, new ResourceLocation("textures/entity/cat/black.png"));
      p_28140_.put(2, new ResourceLocation("textures/entity/cat/red.png"));
      p_28140_.put(3, new ResourceLocation("textures/entity/cat/siamese.png"));
      p_28140_.put(4, new ResourceLocation("textures/entity/cat/british_shorthair.png"));
      p_28140_.put(5, new ResourceLocation("textures/entity/cat/calico.png"));
      p_28140_.put(6, new ResourceLocation("textures/entity/cat/persian.png"));
      p_28140_.put(7, new ResourceLocation("textures/entity/cat/ragdoll.png"));
      p_28140_.put(8, new ResourceLocation("textures/entity/cat/white.png"));
      p_28140_.put(9, new ResourceLocation("textures/entity/cat/jellie.png"));
      p_28140_.put(10, new ResourceLocation("textures/entity/cat/all_black.png"));
   });
   private Cat.CatAvoidEntityGoal<Player> f_28108_;
   private TemptGoal f_28109_;
   private float f_28110_;
   private float f_28111_;
   private float f_28098_;
   private float f_28099_;
   private float f_28100_;
   private float f_28101_;

   public Cat(EntityType<? extends Cat> p_28114_, Level p_28115_) {
      super(p_28114_, p_28115_);
   }

   public ResourceLocation m_28162_() {
      return f_28102_.getOrDefault(this.m_28163_(), f_28102_.get(0));
   }

   protected void m_8099_() {
      this.f_28109_ = new Cat.CatTemptGoal(this, 0.6D, f_28103_, true);
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(2, new Cat.CatRelaxOnOwnerGoal(this));
      this.f_21345_.m_25352_(3, this.f_28109_);
      this.f_21345_.m_25352_(5, new CatLieOnBedGoal(this, 1.1D, 8));
      this.f_21345_.m_25352_(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 5.0F, false));
      this.f_21345_.m_25352_(7, new CatSitOnBlockGoal(this, 0.8D));
      this.f_21345_.m_25352_(8, new LeapAtTargetGoal(this, 0.3F));
      this.f_21345_.m_25352_(9, new OcelotAttackGoal(this));
      this.f_21345_.m_25352_(10, new BreedGoal(this, 0.8D));
      this.f_21345_.m_25352_(11, new WaterAvoidingRandomStrollGoal(this, 0.8D, 1.0000001E-5F));
      this.f_21345_.m_25352_(12, new LookAtPlayerGoal(this, Player.class, 10.0F));
      this.f_21346_.m_25352_(1, new NonTameRandomTargetGoal<>(this, Rabbit.class, false, (Predicate<LivingEntity>)null));
      this.f_21346_.m_25352_(1, new NonTameRandomTargetGoal<>(this, Turtle.class, false, Turtle.f_30122_));
   }

   public int m_28163_() {
      return this.f_19804_.m_135370_(f_28104_);
   }

   public void m_28179_(int p_28180_) {
      if (p_28180_ < 0 || p_28180_ >= 11) {
         p_28180_ = this.f_19796_.nextInt(10);
      }

      this.f_19804_.m_135381_(f_28104_, p_28180_);
   }

   public void m_28181_(boolean p_28182_) {
      this.f_19804_.m_135381_(f_28105_, p_28182_);
   }

   public boolean m_28164_() {
      return this.f_19804_.m_135370_(f_28105_);
   }

   public void m_28185_(boolean p_28186_) {
      this.f_19804_.m_135381_(f_28106_, p_28186_);
   }

   public boolean m_28165_() {
      return this.f_19804_.m_135370_(f_28106_);
   }

   public DyeColor m_28166_() {
      return DyeColor.m_41053_(this.f_19804_.m_135370_(f_28107_));
   }

   public void m_28131_(DyeColor p_28132_) {
      this.f_19804_.m_135381_(f_28107_, p_28132_.m_41060_());
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_28104_, 1);
      this.f_19804_.m_135372_(f_28105_, false);
      this.f_19804_.m_135372_(f_28106_, false);
      this.f_19804_.m_135372_(f_28107_, DyeColor.RED.m_41060_());
   }

   public void m_7380_(CompoundTag p_28156_) {
      super.m_7380_(p_28156_);
      p_28156_.m_128405_("CatType", this.m_28163_());
      p_28156_.m_128344_("CollarColor", (byte)this.m_28166_().m_41060_());
   }

   public void m_7378_(CompoundTag p_28142_) {
      super.m_7378_(p_28142_);
      this.m_28179_(p_28142_.m_128451_("CatType"));
      if (p_28142_.m_128425_("CollarColor", 99)) {
         this.m_28131_(DyeColor.m_41053_(p_28142_.m_128451_("CollarColor")));
      }

   }

   public void m_8024_() {
      if (this.m_21566_().m_24995_()) {
         double d0 = this.m_21566_().m_24999_();
         if (d0 == 0.6D) {
            this.m_20124_(Pose.CROUCHING);
            this.m_6858_(false);
         } else if (d0 == 1.33D) {
            this.m_20124_(Pose.STANDING);
            this.m_6858_(true);
         } else {
            this.m_20124_(Pose.STANDING);
            this.m_6858_(false);
         }
      } else {
         this.m_20124_(Pose.STANDING);
         this.m_6858_(false);
      }

   }

   @Nullable
   protected SoundEvent m_7515_() {
      if (this.m_21824_()) {
         if (this.m_27593_()) {
            return SoundEvents.f_11792_;
         } else {
            return this.f_19796_.nextInt(4) == 0 ? SoundEvents.f_11793_ : SoundEvents.f_11785_;
         }
      } else {
         return SoundEvents.f_11786_;
      }
   }

   public int m_8100_() {
      return 120;
   }

   public void m_28167_() {
      this.m_5496_(SoundEvents.f_11789_, this.m_6121_(), this.m_6100_());
   }

   protected SoundEvent m_7975_(DamageSource p_28160_) {
      return SoundEvents.f_11791_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_11787_;
   }

   public static AttributeSupplier.Builder m_28168_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22281_, 3.0D);
   }

   public boolean m_142535_(float p_148859_, float p_148860_, DamageSource p_148861_) {
      return false;
   }

   protected void m_142075_(Player p_148866_, InteractionHand p_148867_, ItemStack p_148868_) {
      if (this.m_6898_(p_148868_)) {
         this.m_5496_(SoundEvents.f_11788_, 1.0F, 1.0F);
      }

      super.m_142075_(p_148866_, p_148867_, p_148868_);
   }

   private float m_28169_() {
      return (float)this.m_21133_(Attributes.f_22281_);
   }

   public boolean m_7327_(Entity p_28119_) {
      return p_28119_.m_6469_(DamageSource.m_19370_(this), this.m_28169_());
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_28109_ != null && this.f_28109_.m_25955_() && !this.m_21824_() && this.f_19797_ % 100 == 0) {
         this.m_5496_(SoundEvents.f_11790_, 1.0F, 1.0F);
      }

      this.m_28170_();
   }

   private void m_28170_() {
      if ((this.m_28164_() || this.m_28165_()) && this.f_19797_ % 5 == 0) {
         this.m_5496_(SoundEvents.f_11792_, 0.6F + 0.4F * (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()), 1.0F);
      }

      this.m_28171_();
      this.m_28172_();
   }

   private void m_28171_() {
      this.f_28111_ = this.f_28110_;
      this.f_28099_ = this.f_28098_;
      if (this.m_28164_()) {
         this.f_28110_ = Math.min(1.0F, this.f_28110_ + 0.15F);
         this.f_28098_ = Math.min(1.0F, this.f_28098_ + 0.08F);
      } else {
         this.f_28110_ = Math.max(0.0F, this.f_28110_ - 0.22F);
         this.f_28098_ = Math.max(0.0F, this.f_28098_ - 0.13F);
      }

   }

   private void m_28172_() {
      this.f_28101_ = this.f_28100_;
      if (this.m_28165_()) {
         this.f_28100_ = Math.min(1.0F, this.f_28100_ + 0.1F);
      } else {
         this.f_28100_ = Math.max(0.0F, this.f_28100_ - 0.13F);
      }

   }

   public float m_28183_(float p_28184_) {
      return Mth.m_14179_(p_28184_, this.f_28111_, this.f_28110_);
   }

   public float m_28187_(float p_28188_) {
      return Mth.m_14179_(p_28188_, this.f_28099_, this.f_28098_);
   }

   public float m_28116_(float p_28117_) {
      return Mth.m_14179_(p_28117_, this.f_28101_, this.f_28100_);
   }

   public Cat m_142606_(ServerLevel p_148870_, AgeableMob p_148871_) {
      Cat cat = EntityType.f_20553_.m_20615_(p_148870_);
      if (p_148871_ instanceof Cat) {
         if (this.f_19796_.nextBoolean()) {
            cat.m_28179_(this.m_28163_());
         } else {
            cat.m_28179_(((Cat)p_148871_).m_28163_());
         }

         if (this.m_21824_()) {
            cat.m_21816_(this.m_142504_());
            cat.m_7105_(true);
            if (this.f_19796_.nextBoolean()) {
               cat.m_28131_(this.m_28166_());
            } else {
               cat.m_28131_(((Cat)p_148871_).m_28166_());
            }
         }
      }

      return cat;
   }

   public boolean m_7848_(Animal p_28127_) {
      if (!this.m_21824_()) {
         return false;
      } else if (!(p_28127_ instanceof Cat)) {
         return false;
      } else {
         Cat cat = (Cat)p_28127_;
         return cat.m_21824_() && super.m_7848_(p_28127_);
      }
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_28134_, DifficultyInstance p_28135_, MobSpawnType p_28136_, @Nullable SpawnGroupData p_28137_, @Nullable CompoundTag p_28138_) {
      p_28137_ = super.m_6518_(p_28134_, p_28135_, p_28136_, p_28137_, p_28138_);
      if (p_28134_.m_46940_() > 0.9F) {
         this.m_28179_(this.f_19796_.nextInt(11));
      } else {
         this.m_28179_(this.f_19796_.nextInt(10));
      }

      Level level = p_28134_.m_6018_();
      if (level instanceof ServerLevel && ((ServerLevel)level).m_8595_().m_47285_(this.m_142538_(), true, StructureFeature.f_67021_).m_73603_()) {
         this.m_28179_(10);
         this.m_21530_();
      }

      return p_28137_;
   }

   public InteractionResult m_6071_(Player p_28153_, InteractionHand p_28154_) {
      ItemStack itemstack = p_28153_.m_21120_(p_28154_);
      Item item = itemstack.m_41720_();
      if (this.f_19853_.f_46443_) {
         if (this.m_21824_() && this.m_21830_(p_28153_)) {
            return InteractionResult.SUCCESS;
         } else {
            return !this.m_6898_(itemstack) || !(this.m_21223_() < this.m_21233_()) && this.m_21824_() ? InteractionResult.PASS : InteractionResult.SUCCESS;
         }
      } else {
         if (this.m_21824_()) {
            if (this.m_21830_(p_28153_)) {
               if (!(item instanceof DyeItem)) {
                  if (item.m_41472_() && this.m_6898_(itemstack) && this.m_21223_() < this.m_21233_()) {
                     this.m_142075_(p_28153_, p_28154_, itemstack);
                     this.m_5634_((float)item.m_41473_().m_38744_());
                     return InteractionResult.CONSUME;
                  }

                  InteractionResult interactionresult = super.m_6071_(p_28153_, p_28154_);
                  if (!interactionresult.m_19077_() || this.m_6162_()) {
                     this.m_21839_(!this.m_21827_());
                  }

                  return interactionresult;
               }

               DyeColor dyecolor = ((DyeItem)item).m_41089_();
               if (dyecolor != this.m_28166_()) {
                  this.m_28131_(dyecolor);
                  if (!p_28153_.m_150110_().f_35937_) {
                     itemstack.m_41774_(1);
                  }

                  this.m_21530_();
                  return InteractionResult.CONSUME;
               }
            }
         } else if (this.m_6898_(itemstack)) {
            this.m_142075_(p_28153_, p_28154_, itemstack);
            if (this.f_19796_.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, p_28153_)) {
               this.m_21828_(p_28153_);
               this.m_21839_(true);
               this.f_19853_.m_7605_(this, (byte)7);
            } else {
               this.f_19853_.m_7605_(this, (byte)6);
            }

            this.m_21530_();
            return InteractionResult.CONSUME;
         }

         InteractionResult interactionresult1 = super.m_6071_(p_28153_, p_28154_);
         if (interactionresult1.m_19077_()) {
            this.m_21530_();
         }

         return interactionresult1;
      }
   }

   public boolean m_6898_(ItemStack p_28177_) {
      return f_28103_.test(p_28177_);
   }

   protected float m_6431_(Pose p_28150_, EntityDimensions p_28151_) {
      return p_28151_.f_20378_ * 0.5F;
   }

   public boolean m_6785_(double p_28174_) {
      return !this.m_21824_() && this.f_19797_ > 2400;
   }

   protected void m_5849_() {
      if (this.f_28108_ == null) {
         this.f_28108_ = new Cat.CatAvoidEntityGoal<>(this, Player.class, 16.0F, 0.8D, 1.33D);
      }

      this.f_21345_.m_25363_(this.f_28108_);
      if (!this.m_21824_()) {
         this.f_21345_.m_25352_(4, this.f_28108_);
      }

   }

   public boolean m_20161_() {
      return this.m_20089_() == Pose.CROUCHING || super.m_20161_();
   }

   static class CatAvoidEntityGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {
      private final Cat f_28189_;

      public CatAvoidEntityGoal(Cat p_28191_, Class<T> p_28192_, float p_28193_, double p_28194_, double p_28195_) {
         super(p_28191_, p_28192_, p_28193_, p_28194_, p_28195_, EntitySelector.f_20406_::test);
         this.f_28189_ = p_28191_;
      }

      public boolean m_8036_() {
         return !this.f_28189_.m_21824_() && super.m_8036_();
      }

      public boolean m_8045_() {
         return !this.f_28189_.m_21824_() && super.m_8045_();
      }
   }

   static class CatRelaxOnOwnerGoal extends Goal {
      private final Cat f_28198_;
      private Player f_28199_;
      private BlockPos f_28200_;
      private int f_28201_;

      public CatRelaxOnOwnerGoal(Cat p_28203_) {
         this.f_28198_ = p_28203_;
      }

      public boolean m_8036_() {
         if (!this.f_28198_.m_21824_()) {
            return false;
         } else if (this.f_28198_.m_21827_()) {
            return false;
         } else {
            LivingEntity livingentity = this.f_28198_.m_142480_();
            if (livingentity instanceof Player) {
               this.f_28199_ = (Player)livingentity;
               if (!livingentity.m_5803_()) {
                  return false;
               }

               if (this.f_28198_.m_20280_(this.f_28199_) > 100.0D) {
                  return false;
               }

               BlockPos blockpos = this.f_28199_.m_142538_();
               BlockState blockstate = this.f_28198_.f_19853_.m_8055_(blockpos);
               if (blockstate.m_60620_(BlockTags.f_13038_)) {
                  this.f_28200_ = blockstate.m_61145_(BedBlock.f_54117_).map((p_28209_) -> {
                     return blockpos.m_142300_(p_28209_.m_122424_());
                  }).orElseGet(() -> {
                     return new BlockPos(blockpos);
                  });
                  return !this.m_28214_();
               }
            }

            return false;
         }
      }

      private boolean m_28214_() {
         for(Cat cat : this.f_28198_.f_19853_.m_45976_(Cat.class, (new AABB(this.f_28200_)).m_82400_(2.0D))) {
            if (cat != this.f_28198_ && (cat.m_28164_() || cat.m_28165_())) {
               return true;
            }
         }

         return false;
      }

      public boolean m_8045_() {
         return this.f_28198_.m_21824_() && !this.f_28198_.m_21827_() && this.f_28199_ != null && this.f_28199_.m_5803_() && this.f_28200_ != null && !this.m_28214_();
      }

      public void m_8056_() {
         if (this.f_28200_ != null) {
            this.f_28198_.m_21837_(false);
            this.f_28198_.m_21573_().m_26519_((double)this.f_28200_.m_123341_(), (double)this.f_28200_.m_123342_(), (double)this.f_28200_.m_123343_(), (double)1.1F);
         }

      }

      public void m_8041_() {
         this.f_28198_.m_28181_(false);
         float f = this.f_28198_.f_19853_.m_46942_(1.0F);
         if (this.f_28199_.m_36318_() >= 100 && (double)f > 0.77D && (double)f < 0.8D && (double)this.f_28198_.f_19853_.m_5822_().nextFloat() < 0.7D) {
            this.m_28215_();
         }

         this.f_28201_ = 0;
         this.f_28198_.m_28185_(false);
         this.f_28198_.m_21573_().m_26573_();
      }

      private void m_28215_() {
         Random random = this.f_28198_.m_21187_();
         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
         blockpos$mutableblockpos.m_122190_(this.f_28198_.m_142538_());
         this.f_28198_.m_20984_((double)(blockpos$mutableblockpos.m_123341_() + random.nextInt(11) - 5), (double)(blockpos$mutableblockpos.m_123342_() + random.nextInt(5) - 2), (double)(blockpos$mutableblockpos.m_123343_() + random.nextInt(11) - 5), false);
         blockpos$mutableblockpos.m_122190_(this.f_28198_.m_142538_());
         LootTable loottable = this.f_28198_.f_19853_.m_142572_().m_129898_().m_79217_(BuiltInLootTables.f_78724_);
         LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel)this.f_28198_.f_19853_)).m_78972_(LootContextParams.f_81460_, this.f_28198_.m_20182_()).m_78972_(LootContextParams.f_81455_, this.f_28198_).m_78977_(random);

         for(ItemStack itemstack : loottable.m_79129_(lootcontext$builder.m_78975_(LootContextParamSets.f_81416_))) {
            this.f_28198_.f_19853_.m_7967_(new ItemEntity(this.f_28198_.f_19853_, (double)blockpos$mutableblockpos.m_123341_() - (double)Mth.m_14031_(this.f_28198_.f_20883_ * ((float)Math.PI / 180F)), (double)blockpos$mutableblockpos.m_123342_(), (double)blockpos$mutableblockpos.m_123343_() + (double)Mth.m_14089_(this.f_28198_.f_20883_ * ((float)Math.PI / 180F)), itemstack));
         }

      }

      public void m_8037_() {
         if (this.f_28199_ != null && this.f_28200_ != null) {
            this.f_28198_.m_21837_(false);
            this.f_28198_.m_21573_().m_26519_((double)this.f_28200_.m_123341_(), (double)this.f_28200_.m_123342_(), (double)this.f_28200_.m_123343_(), (double)1.1F);
            if (this.f_28198_.m_20280_(this.f_28199_) < 2.5D) {
               ++this.f_28201_;
               if (this.f_28201_ > 16) {
                  this.f_28198_.m_28181_(true);
                  this.f_28198_.m_28185_(false);
               } else {
                  this.f_28198_.m_21391_(this.f_28199_, 45.0F, 45.0F);
                  this.f_28198_.m_28185_(true);
               }
            } else {
               this.f_28198_.m_28181_(false);
            }
         }

      }
   }

   static class CatTemptGoal extends TemptGoal {
      @Nullable
      private Player f_28216_;
      private final Cat f_28217_;

      public CatTemptGoal(Cat p_28219_, double p_28220_, Ingredient p_28221_, boolean p_28222_) {
         super(p_28219_, p_28220_, p_28221_, p_28222_);
         this.f_28217_ = p_28219_;
      }

      public void m_8037_() {
         super.m_8037_();
         if (this.f_28216_ == null && this.f_25924_.m_21187_().nextInt(600) == 0) {
            this.f_28216_ = this.f_25925_;
         } else if (this.f_25924_.m_21187_().nextInt(500) == 0) {
            this.f_28216_ = null;
         }

      }

      protected boolean m_7497_() {
         return this.f_28216_ != null && this.f_28216_.equals(this.f_25925_) ? false : super.m_7497_();
      }

      public boolean m_8036_() {
         return super.m_8036_() && !this.f_28217_.m_21824_();
      }
   }
}
