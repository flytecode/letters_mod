package net.minecraft.world.entity.monster;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreakDoorGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.ZombieAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.util.GoalUtils;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Zombie extends Monster {
   private static final UUID f_34259_ = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
   private static final AttributeModifier f_34267_ = new AttributeModifier(f_34259_, "Baby speed boost", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE);
   private static final EntityDataAccessor<Boolean> f_34268_ = SynchedEntityData.m_135353_(Zombie.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> f_34260_ = SynchedEntityData.m_135353_(Zombie.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> f_34261_ = SynchedEntityData.m_135353_(Zombie.class, EntityDataSerializers.f_135035_);
   public static final float f_149884_ = 0.05F;
   public static final int f_149880_ = 50;
   public static final int f_149881_ = 40;
   public static final int f_149882_ = 7;
   private static final float f_149883_ = 0.1F;
   private static final Predicate<Difficulty> f_34262_ = (p_34284_) -> {
      return p_34284_ == Difficulty.HARD;
   };
   private final BreakDoorGoal f_34263_ = new BreakDoorGoal(this, f_34262_);
   private boolean f_34264_;
   private int f_34265_;
   private int f_34266_;

   public Zombie(EntityType<? extends Zombie> p_34271_, Level p_34272_) {
      super(p_34271_, p_34272_);
   }

   public Zombie(Level p_34274_) {
      this(EntityType.f_20501_, p_34274_);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(4, new Zombie.ZombieAttackTurtleEggGoal(this, 1.0D, 3));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.m_6878_();
   }

   protected void m_6878_() {
      this.f_21345_.m_25352_(2, new ZombieAttackGoal(this, 1.0D, false));
      this.f_21345_.m_25352_(6, new MoveThroughVillageGoal(this, 1.0D, true, 4, this::m_34330_));
      this.f_21345_.m_25352_(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this)).m_26044_(ZombifiedPiglin.class));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.f_30122_));
   }

   public static AttributeSupplier.Builder m_34328_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22277_, 35.0D).m_22268_(Attributes.f_22279_, (double)0.23F).m_22268_(Attributes.f_22281_, 3.0D).m_22268_(Attributes.f_22284_, 2.0D).m_22266_(Attributes.f_22287_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(f_34268_, false);
      this.m_20088_().m_135372_(f_34260_, 0);
      this.m_20088_().m_135372_(f_34261_, false);
   }

   public boolean m_34329_() {
      return this.m_20088_().m_135370_(f_34261_);
   }

   public boolean m_34330_() {
      return this.f_34264_;
   }

   public void m_34336_(boolean p_34337_) {
      if (this.m_7586_() && GoalUtils.m_26894_(this)) {
         if (this.f_34264_ != p_34337_) {
            this.f_34264_ = p_34337_;
            ((GroundPathNavigation)this.m_21573_()).m_26477_(p_34337_);
            if (p_34337_) {
               this.f_21345_.m_25352_(1, this.f_34263_);
            } else {
               this.f_21345_.m_25363_(this.f_34263_);
            }
         }
      } else if (this.f_34264_) {
         this.f_21345_.m_25363_(this.f_34263_);
         this.f_34264_ = false;
      }

   }

   protected boolean m_7586_() {
      return true;
   }

   public boolean m_6162_() {
      return this.m_20088_().m_135370_(f_34268_);
   }

   protected int m_6552_(Player p_34322_) {
      if (this.m_6162_()) {
         this.f_21364_ = (int)((float)this.f_21364_ * 2.5F);
      }

      return super.m_6552_(p_34322_);
   }

   public void m_6863_(boolean p_34309_) {
      this.m_20088_().m_135381_(f_34268_, p_34309_);
      if (this.f_19853_ != null && !this.f_19853_.f_46443_) {
         AttributeInstance attributeinstance = this.m_21051_(Attributes.f_22279_);
         attributeinstance.m_22130_(f_34267_);
         if (p_34309_) {
            attributeinstance.m_22118_(f_34267_);
         }
      }

   }

   public void m_7350_(EntityDataAccessor<?> p_34307_) {
      if (f_34268_.equals(p_34307_)) {
         this.m_6210_();
      }

      super.m_7350_(p_34307_);
   }

   protected boolean m_7593_() {
      return true;
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_ && this.m_6084_() && !this.m_21525_()) {
         if (this.m_34329_()) {
            --this.f_34266_;
            if (this.f_34266_ < 0 && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(this, EntityType.f_20501_, (timer) -> this.f_34266_ = timer)) {
               this.m_7595_();
            }
         } else if (this.m_7593_()) {
            if (this.m_19941_(FluidTags.f_13131_)) {
               ++this.f_34265_;
               if (this.f_34265_ >= 600) {
                  this.m_34278_(300);
               }
            } else {
               this.f_34265_ = -1;
            }
         }
      }

      super.m_8119_();
   }

   public void m_8107_() {
      if (this.m_6084_()) {
         boolean flag = this.m_5884_() && this.m_21527_();
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
      }

      super.m_8107_();
   }

   private void m_34278_(int p_34279_) {
      this.f_34266_ = p_34279_;
      this.m_20088_().m_135381_(f_34261_, true);
   }

   protected void m_7595_() {
      this.m_34310_(EntityType.f_20562_);
      if (!this.m_20067_()) {
         this.f_19853_.m_5898_((Player)null, 1040, this.m_142538_(), 0);
      }

   }

   protected void m_34310_(EntityType<? extends Zombie> p_34311_) {
      Zombie zombie = this.m_21406_(p_34311_, true);
      if (zombie != null) {
         zombie.m_34339_(zombie.f_19853_.m_6436_(zombie.m_142538_()).m_19057_());
         zombie.m_34336_(zombie.m_7586_() && this.m_34330_());
         net.minecraftforge.event.ForgeEventFactory.onLivingConvert(this, zombie);
      }

   }

   protected boolean m_5884_() {
      return true;
   }

   public boolean m_6469_(DamageSource p_34288_, float p_34289_) {
      if (!super.m_6469_(p_34288_, p_34289_)) {
         return false;
      } else if (!(this.f_19853_ instanceof ServerLevel)) {
         return false;
      } else {
         ServerLevel serverlevel = (ServerLevel)this.f_19853_;
         LivingEntity livingentity = this.m_5448_();
         if (livingentity == null && p_34288_.m_7639_() instanceof LivingEntity) {
            livingentity = (LivingEntity)p_34288_.m_7639_();
         }

            int i = Mth.m_14107_(this.m_20185_());
            int j = Mth.m_14107_(this.m_20186_());
            int k = Mth.m_14107_(this.m_20189_());
         net.minecraftforge.event.entity.living.ZombieEvent.SummonAidEvent event = net.minecraftforge.event.ForgeEventFactory.fireZombieSummonAid(this, f_19853_, i, j, k, livingentity, this.m_21051_(Attributes.f_22287_).m_22135_());
         if (event.getResult() == net.minecraftforge.eventbus.api.Event.Result.DENY) return true;
         if (event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW  ||
            livingentity != null && this.f_19853_.m_46791_() == Difficulty.HARD && (double)this.f_19796_.nextFloat() < this.m_21051_(Attributes.f_22287_).m_22135_() && this.f_19853_.m_46469_().m_46207_(GameRules.f_46134_)) {
            Zombie zombie = event.getCustomSummonedAid() != null && event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW ? event.getCustomSummonedAid() : EntityType.f_20501_.m_20615_(this.f_19853_);

            for(int l = 0; l < 50; ++l) {
               int i1 = i + Mth.m_14072_(this.f_19796_, 7, 40) * Mth.m_14072_(this.f_19796_, -1, 1);
               int j1 = j + Mth.m_14072_(this.f_19796_, 7, 40) * Mth.m_14072_(this.f_19796_, -1, 1);
               int k1 = k + Mth.m_14072_(this.f_19796_, 7, 40) * Mth.m_14072_(this.f_19796_, -1, 1);
               BlockPos blockpos = new BlockPos(i1, j1, k1);
               EntityType<?> entitytype = zombie.m_6095_();
               SpawnPlacements.Type spawnplacements$type = SpawnPlacements.m_21752_(entitytype);
               if (NaturalSpawner.m_47051_(spawnplacements$type, this.f_19853_, blockpos, entitytype) && SpawnPlacements.m_21759_(entitytype, serverlevel, MobSpawnType.REINFORCEMENT, blockpos, this.f_19853_.f_46441_)) {
                  zombie.m_6034_((double)i1, (double)j1, (double)k1);
                  if (!this.f_19853_.m_45914_((double)i1, (double)j1, (double)k1, 7.0D) && this.f_19853_.m_45784_(zombie) && this.f_19853_.m_45786_(zombie) && !this.f_19853_.m_46855_(zombie.m_142469_())) {
                     if (livingentity != null)
                     zombie.m_6710_(livingentity);
                     zombie.m_6518_(serverlevel, this.f_19853_.m_6436_(zombie.m_142538_()), MobSpawnType.REINFORCEMENT, (SpawnGroupData)null, (CompoundTag)null);
                     serverlevel.m_47205_(zombie);
                     this.m_21051_(Attributes.f_22287_).m_22125_(new AttributeModifier("Zombie reinforcement caller charge", (double)-0.05F, AttributeModifier.Operation.ADDITION));
                     zombie.m_21051_(Attributes.f_22287_).m_22125_(new AttributeModifier("Zombie reinforcement callee charge", (double)-0.05F, AttributeModifier.Operation.ADDITION));
                     break;
                  }
               }
            }
         }

         return true;
      }
   }

   public boolean m_7327_(Entity p_34276_) {
      boolean flag = super.m_7327_(p_34276_);
      if (flag) {
         float f = this.f_19853_.m_6436_(this.m_142538_()).m_19056_();
         if (this.m_21205_().m_41619_() && this.m_6060_() && this.f_19796_.nextFloat() < f * 0.3F) {
            p_34276_.m_20254_(2 * (int)f);
         }
      }

      return flag;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12598_;
   }

   protected SoundEvent m_7975_(DamageSource p_34327_) {
      return SoundEvents.f_12608_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12603_;
   }

   protected SoundEvent m_7660_() {
      return SoundEvents.f_12614_;
   }

   protected void m_7355_(BlockPos p_34316_, BlockState p_34317_) {
      this.m_5496_(this.m_7660_(), 0.15F, 1.0F);
   }

   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   protected void m_6851_(DifficultyInstance p_34286_) {
      super.m_6851_(p_34286_);
      if (this.f_19796_.nextFloat() < (this.f_19853_.m_46791_() == Difficulty.HARD ? 0.05F : 0.01F)) {
         int i = this.f_19796_.nextInt(3);
         if (i == 0) {
            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42383_));
         } else {
            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42384_));
         }
      }

   }

   public void m_7380_(CompoundTag p_34319_) {
      super.m_7380_(p_34319_);
      p_34319_.m_128379_("IsBaby", this.m_6162_());
      p_34319_.m_128379_("CanBreakDoors", this.m_34330_());
      p_34319_.m_128405_("InWaterTime", this.m_20069_() ? this.f_34265_ : -1);
      p_34319_.m_128405_("DrownedConversionTime", this.m_34329_() ? this.f_34266_ : -1);
   }

   public void m_7378_(CompoundTag p_34305_) {
      super.m_7378_(p_34305_);
      this.m_6863_(p_34305_.m_128471_("IsBaby"));
      this.m_34336_(p_34305_.m_128471_("CanBreakDoors"));
      this.f_34265_ = p_34305_.m_128451_("InWaterTime");
      if (p_34305_.m_128425_("DrownedConversionTime", 99) && p_34305_.m_128451_("DrownedConversionTime") > -1) {
         this.m_34278_(p_34305_.m_128451_("DrownedConversionTime"));
      }

   }

   public void m_5837_(ServerLevel p_34281_, LivingEntity p_34282_) {
      super.m_5837_(p_34281_, p_34282_);
      if ((p_34281_.m_46791_() == Difficulty.NORMAL || p_34281_.m_46791_() == Difficulty.HARD) && p_34282_ instanceof Villager && net.minecraftforge.event.ForgeEventFactory.canLivingConvert(p_34282_, EntityType.f_20530_, (timer) -> {})) {
         if (p_34281_.m_46791_() != Difficulty.HARD && this.f_19796_.nextBoolean()) {
            return;
         }

         Villager villager = (Villager)p_34282_;
         ZombieVillager zombievillager = villager.m_21406_(EntityType.f_20530_, false);
         zombievillager.m_6518_(p_34281_, p_34281_.m_6436_(zombievillager.m_142538_()), MobSpawnType.CONVERSION, new Zombie.ZombieGroupData(false, true), (CompoundTag)null);
         zombievillager.m_141967_(villager.m_7141_());
         zombievillager.m_34391_(villager.m_35517_().m_26179_(NbtOps.f_128958_).getValue());
         zombievillager.m_34411_(villager.m_6616_().m_45388_());
         zombievillager.m_34373_(villager.m_7809_());
         net.minecraftforge.event.ForgeEventFactory.onLivingConvert(p_34282_, zombievillager);
         if (!this.m_20067_()) {
            p_34281_.m_5898_((Player)null, 1026, this.m_142538_(), 0);
         }
      }

   }

   protected float m_6431_(Pose p_34313_, EntityDimensions p_34314_) {
      return this.m_6162_() ? 0.93F : 1.74F;
   }

   public boolean m_7252_(ItemStack p_34332_) {
      return p_34332_.m_150930_(Items.f_42521_) && this.m_6162_() && this.m_20159_() ? false : super.m_7252_(p_34332_);
   }

   public boolean m_7243_(ItemStack p_182400_) {
      return p_182400_.m_150930_(Items.f_151056_) ? false : super.m_7243_(p_182400_);
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_34297_, DifficultyInstance p_34298_, MobSpawnType p_34299_, @Nullable SpawnGroupData p_34300_, @Nullable CompoundTag p_34301_) {
      p_34300_ = super.m_6518_(p_34297_, p_34298_, p_34299_, p_34300_, p_34301_);
      float f = p_34298_.m_19057_();
      this.m_21553_(this.f_19796_.nextFloat() < 0.55F * f);
      if (p_34300_ == null) {
         p_34300_ = new Zombie.ZombieGroupData(m_34302_(p_34297_.m_5822_()), true);
      }

      if (p_34300_ instanceof Zombie.ZombieGroupData) {
         Zombie.ZombieGroupData zombie$zombiegroupdata = (Zombie.ZombieGroupData)p_34300_;
         if (zombie$zombiegroupdata.f_34354_) {
            this.m_6863_(true);
            if (zombie$zombiegroupdata.f_34355_) {
               if ((double)p_34297_.m_5822_().nextFloat() < 0.05D) {
                  List<Chicken> list = p_34297_.m_6443_(Chicken.class, this.m_142469_().m_82377_(5.0D, 3.0D, 5.0D), EntitySelector.f_20404_);
                  if (!list.isEmpty()) {
                     Chicken chicken = list.get(0);
                     chicken.m_28273_(true);
                     this.m_20329_(chicken);
                  }
               } else if ((double)p_34297_.m_5822_().nextFloat() < 0.05D) {
                  Chicken chicken1 = EntityType.f_20555_.m_20615_(this.f_19853_);
                  chicken1.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0F);
                  chicken1.m_6518_(p_34297_, p_34298_, MobSpawnType.JOCKEY, (SpawnGroupData)null, (CompoundTag)null);
                  chicken1.m_28273_(true);
                  this.m_20329_(chicken1);
                  p_34297_.m_7967_(chicken1);
               }
            }
         }

         this.m_34336_(this.m_7586_() && this.f_19796_.nextFloat() < f * 0.1F);
         this.m_6851_(p_34298_);
         this.m_6850_(p_34298_);
      }

      if (this.m_6844_(EquipmentSlot.HEAD).m_41619_()) {
         LocalDate localdate = LocalDate.now();
         int i = localdate.get(ChronoField.DAY_OF_MONTH);
         int j = localdate.get(ChronoField.MONTH_OF_YEAR);
         if (j == 10 && i == 31 && this.f_19796_.nextFloat() < 0.25F) {
            this.m_8061_(EquipmentSlot.HEAD, new ItemStack(this.f_19796_.nextFloat() < 0.1F ? Blocks.f_50144_ : Blocks.f_50143_));
            this.f_21348_[EquipmentSlot.HEAD.m_20749_()] = 0.0F;
         }
      }

      this.m_34339_(f);
      return p_34300_;
   }

   public static boolean m_34302_(Random p_34303_) {
      return p_34303_.nextFloat() < net.minecraftforge.common.ForgeConfig.SERVER.zombieBabyChance.get();
   }

   protected void m_34339_(float p_34340_) {
      this.m_7572_();
      this.m_21051_(Attributes.f_22278_).m_22125_(new AttributeModifier("Random spawn bonus", this.f_19796_.nextDouble() * (double)0.05F, AttributeModifier.Operation.ADDITION));
      double d0 = this.f_19796_.nextDouble() * 1.5D * (double)p_34340_;
      if (d0 > 1.0D) {
         this.m_21051_(Attributes.f_22277_).m_22125_(new AttributeModifier("Random zombie-spawn bonus", d0, AttributeModifier.Operation.MULTIPLY_TOTAL));
      }

      if (this.f_19796_.nextFloat() < p_34340_ * 0.05F) {
         this.m_21051_(Attributes.f_22287_).m_22125_(new AttributeModifier("Leader zombie bonus", this.f_19796_.nextDouble() * 0.25D + 0.5D, AttributeModifier.Operation.ADDITION));
         this.m_21051_(Attributes.f_22276_).m_22125_(new AttributeModifier("Leader zombie bonus", this.f_19796_.nextDouble() * 3.0D + 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
         this.m_34336_(this.m_7586_());
      }

   }

   protected void m_7572_() {
      this.m_21051_(Attributes.f_22287_).m_22100_(this.f_19796_.nextDouble() * net.minecraftforge.common.ForgeConfig.SERVER.zombieBaseSummonChance.get());
   }

   public double m_6049_() {
      return this.m_6162_() ? 0.0D : -0.45D;
   }

   protected void m_7472_(DamageSource p_34291_, int p_34292_, boolean p_34293_) {
      super.m_7472_(p_34291_, p_34292_, p_34293_);
      Entity entity = p_34291_.m_7639_();
      if (entity instanceof Creeper) {
         Creeper creeper = (Creeper)entity;
         if (creeper.m_32313_()) {
            ItemStack itemstack = this.m_5728_();
            if (!itemstack.m_41619_()) {
               creeper.m_32314_();
               this.m_19983_(itemstack);
            }
         }
      }

   }

   protected ItemStack m_5728_() {
      return new ItemStack(Items.f_42681_);
   }

   class ZombieAttackTurtleEggGoal extends RemoveBlockGoal {
      ZombieAttackTurtleEggGoal(PathfinderMob p_34344_, double p_34345_, int p_34346_) {
         super(Blocks.f_50578_, p_34344_, p_34345_, p_34346_);
      }

      public void m_7659_(LevelAccessor p_34351_, BlockPos p_34352_) {
         p_34351_.m_5594_((Player)null, p_34352_, SoundEvents.f_12604_, SoundSource.HOSTILE, 0.5F, 0.9F + Zombie.this.f_19796_.nextFloat() * 0.2F);
      }

      public void m_5777_(Level p_34348_, BlockPos p_34349_) {
         p_34348_.m_5594_((Player)null, p_34349_, SoundEvents.f_12533_, SoundSource.BLOCKS, 0.7F, 0.9F + p_34348_.f_46441_.nextFloat() * 0.2F);
      }

      public double m_8052_() {
         return 1.14D;
      }
   }

   public static class ZombieGroupData implements SpawnGroupData {
      public final boolean f_34354_;
      public final boolean f_34355_;

      public ZombieGroupData(boolean p_34357_, boolean p_34358_) {
         this.f_34354_ = p_34357_;
         this.f_34355_ = p_34358_;
      }
   }
}
