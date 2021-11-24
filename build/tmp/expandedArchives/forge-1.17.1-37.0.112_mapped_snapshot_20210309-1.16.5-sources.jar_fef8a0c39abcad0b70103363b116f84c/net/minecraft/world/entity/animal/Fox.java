package net.minecraft.world.entity.animal;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.StrollThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;

public class Fox extends Animal {
   private static final EntityDataAccessor<Integer> f_28437_ = SynchedEntityData.m_135353_(Fox.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Byte> f_28438_ = SynchedEntityData.m_135353_(Fox.class, EntityDataSerializers.f_135027_);
   private static final int f_148899_ = 1;
   public static final int f_148896_ = 4;
   public static final int f_148897_ = 8;
   public static final int f_148898_ = 16;
   private static final int f_148900_ = 32;
   private static final int f_148901_ = 64;
   private static final int f_148902_ = 128;
   private static final EntityDataAccessor<Optional<UUID>> f_28439_ = SynchedEntityData.m_135353_(Fox.class, EntityDataSerializers.f_135041_);
   private static final EntityDataAccessor<Optional<UUID>> f_28440_ = SynchedEntityData.m_135353_(Fox.class, EntityDataSerializers.f_135041_);
   static final Predicate<ItemEntity> f_28441_ = (p_28528_) -> {
      return !p_28528_.m_32063_() && p_28528_.m_6084_();
   };
   private static final Predicate<Entity> f_28442_ = (p_28521_) -> {
      if (!(p_28521_ instanceof LivingEntity)) {
         return false;
      } else {
         LivingEntity livingentity = (LivingEntity)p_28521_;
         return livingentity.m_21214_() != null && livingentity.m_21215_() < livingentity.f_19797_ + 600;
      }
   };
   static final Predicate<Entity> f_28443_ = (p_28498_) -> {
      return p_28498_ instanceof Chicken || p_28498_ instanceof Rabbit;
   };
   private static final Predicate<Entity> f_28444_ = (p_28463_) -> {
      return !p_28463_.m_20163_() && EntitySelector.f_20406_.test(p_28463_);
   };
   private static final int f_148903_ = 600;
   private Goal f_28445_;
   private Goal f_28446_;
   private Goal f_28447_;
   private float f_28448_;
   private float f_28433_;
   float f_28434_;
   float f_28435_;
   private int f_28436_;

   public Fox(EntityType<? extends Fox> p_28451_, Level p_28452_) {
      super(p_28451_, p_28452_);
      this.f_21365_ = new Fox.FoxLookControl();
      this.f_21342_ = new Fox.FoxMoveControl();
      this.m_21441_(BlockPathTypes.DANGER_OTHER, 0.0F);
      this.m_21441_(BlockPathTypes.DAMAGE_OTHER, 0.0F);
      this.m_21553_(true);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_28439_, Optional.empty());
      this.f_19804_.m_135372_(f_28440_, Optional.empty());
      this.f_19804_.m_135372_(f_28437_, 0);
      this.f_19804_.m_135372_(f_28438_, (byte)0);
   }

   protected void m_8099_() {
      this.f_28445_ = new NearestAttackableTargetGoal<>(this, Animal.class, 10, false, false, (p_28604_) -> {
         return p_28604_ instanceof Chicken || p_28604_ instanceof Rabbit;
      });
      this.f_28446_ = new NearestAttackableTargetGoal<>(this, Turtle.class, 10, false, false, Turtle.f_30122_);
      this.f_28447_ = new NearestAttackableTargetGoal<>(this, AbstractFish.class, 20, false, false, (p_28600_) -> {
         return p_28600_ instanceof AbstractSchoolingFish;
      });
      this.f_21345_.m_25352_(0, new Fox.FoxFloatGoal());
      this.f_21345_.m_25352_(1, new Fox.FaceplantGoal());
      this.f_21345_.m_25352_(2, new Fox.FoxPanicGoal(2.2D));
      this.f_21345_.m_25352_(3, new Fox.FoxBreedGoal(1.0D));
      this.f_21345_.m_25352_(4, new AvoidEntityGoal<>(this, Player.class, 16.0F, 1.6D, 1.4D, (p_28596_) -> {
         return f_28444_.test(p_28596_) && !this.m_28529_(p_28596_.m_142081_()) && !this.m_28567_();
      }));
      this.f_21345_.m_25352_(4, new AvoidEntityGoal<>(this, Wolf.class, 8.0F, 1.6D, 1.4D, (p_28590_) -> {
         return !((Wolf)p_28590_).m_21824_() && !this.m_28567_();
      }));
      this.f_21345_.m_25352_(4, new AvoidEntityGoal<>(this, PolarBear.class, 8.0F, 1.6D, 1.4D, (p_28585_) -> {
         return !this.m_28567_();
      }));
      this.f_21345_.m_25352_(5, new Fox.StalkPreyGoal());
      this.f_21345_.m_25352_(6, new Fox.FoxPounceGoal());
      this.f_21345_.m_25352_(6, new Fox.SeekShelterGoal(1.25D));
      this.f_21345_.m_25352_(7, new Fox.FoxMeleeAttackGoal((double)1.2F, true));
      this.f_21345_.m_25352_(7, new Fox.SleepGoal());
      this.f_21345_.m_25352_(8, new Fox.FoxFollowParentGoal(this, 1.25D));
      this.f_21345_.m_25352_(9, new Fox.FoxStrollThroughVillageGoal(32, 200));
      this.f_21345_.m_25352_(10, new Fox.FoxEatBerriesGoal((double)1.2F, 12, 1));
      this.f_21345_.m_25352_(10, new LeapAtTargetGoal(this, 0.4F));
      this.f_21345_.m_25352_(11, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(11, new Fox.FoxSearchForItemsGoal());
      this.f_21345_.m_25352_(12, new Fox.FoxLookAtPlayerGoal(this, Player.class, 24.0F));
      this.f_21345_.m_25352_(13, new Fox.PerchAndSearchGoal());
      this.f_21346_.m_25352_(3, new Fox.DefendTrustedTargetGoal(LivingEntity.class, false, false, (p_28580_) -> {
         return f_28442_.test(p_28580_) && !this.m_28529_(p_28580_.m_142081_());
      }));
   }

   public SoundEvent m_7866_(ItemStack p_28540_) {
      return SoundEvents.f_11947_;
   }

   public void m_8107_() {
      if (!this.f_19853_.f_46443_ && this.m_6084_() && this.m_6142_()) {
         ++this.f_28436_;
         ItemStack itemstack = this.m_6844_(EquipmentSlot.MAINHAND);
         if (this.m_28597_(itemstack)) {
            if (this.f_28436_ > 600) {
               ItemStack itemstack1 = itemstack.m_41671_(this.f_19853_, this);
               if (!itemstack1.m_41619_()) {
                  this.m_8061_(EquipmentSlot.MAINHAND, itemstack1);
               }

               this.f_28436_ = 0;
            } else if (this.f_28436_ > 560 && this.f_19796_.nextFloat() < 0.1F) {
               this.m_5496_(this.m_7866_(itemstack), 1.0F, 1.0F);
               this.f_19853_.m_7605_(this, (byte)45);
            }
         }

         LivingEntity livingentity = this.m_5448_();
         if (livingentity == null || !livingentity.m_6084_()) {
            this.m_28614_(false);
            this.m_28616_(false);
         }
      }

      if (this.m_5803_() || this.m_6107_()) {
         this.f_20899_ = false;
         this.f_20900_ = 0.0F;
         this.f_20902_ = 0.0F;
      }

      super.m_8107_();
      if (this.m_28567_() && this.f_19796_.nextFloat() < 0.05F) {
         this.m_5496_(SoundEvents.f_11943_, 1.0F, 1.0F);
      }

   }

   protected boolean m_6107_() {
      return this.m_21224_();
   }

   private boolean m_28597_(ItemStack p_28598_) {
      return p_28598_.m_41720_().m_41472_() && this.m_5448_() == null && this.f_19861_ && !this.m_5803_();
   }

   protected void m_6851_(DifficultyInstance p_28461_) {
      if (this.f_19796_.nextFloat() < 0.2F) {
         float f = this.f_19796_.nextFloat();
         ItemStack itemstack;
         if (f < 0.05F) {
            itemstack = new ItemStack(Items.f_42616_);
         } else if (f < 0.2F) {
            itemstack = new ItemStack(Items.f_42521_);
         } else if (f < 0.4F) {
            itemstack = this.f_19796_.nextBoolean() ? new ItemStack(Items.f_42648_) : new ItemStack(Items.f_42649_);
         } else if (f < 0.6F) {
            itemstack = new ItemStack(Items.f_42405_);
         } else if (f < 0.8F) {
            itemstack = new ItemStack(Items.f_42454_);
         } else {
            itemstack = new ItemStack(Items.f_42402_);
         }

         this.m_8061_(EquipmentSlot.MAINHAND, itemstack);
      }

   }

   public void m_7822_(byte p_28456_) {
      if (p_28456_ == 45) {
         ItemStack itemstack = this.m_6844_(EquipmentSlot.MAINHAND);
         if (!itemstack.m_41619_()) {
            for(int i = 0; i < 8; ++i) {
               Vec3 vec3 = (new Vec3(((double)this.f_19796_.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D)).m_82496_(-this.m_146909_() * ((float)Math.PI / 180F)).m_82524_(-this.m_146908_() * ((float)Math.PI / 180F));
               this.f_19853_.m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, itemstack), this.m_20185_() + this.m_20154_().f_82479_ / 2.0D, this.m_20186_(), this.m_20189_() + this.m_20154_().f_82481_ / 2.0D, vec3.f_82479_, vec3.f_82480_ + 0.05D, vec3.f_82481_);
            }
         }
      } else {
         super.m_7822_(p_28456_);
      }

   }

   public static AttributeSupplier.Builder m_28553_() {
      return Mob.m_21552_().m_22268_(Attributes.f_22279_, (double)0.3F).m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22277_, 32.0D).m_22268_(Attributes.f_22281_, 2.0D);
   }

   public Fox m_142606_(ServerLevel p_148912_, AgeableMob p_148913_) {
      Fox fox = EntityType.f_20452_.m_20615_(p_148912_);
      fox.m_28464_(this.f_19796_.nextBoolean() ? this.m_28554_() : ((Fox)p_148913_).m_28554_());
      return fox;
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_28487_, DifficultyInstance p_28488_, MobSpawnType p_28489_, @Nullable SpawnGroupData p_28490_, @Nullable CompoundTag p_28491_) {
      Optional<ResourceKey<Biome>> optional = p_28487_.m_45837_(this.m_142538_());
      Fox.Type fox$type = Fox.Type.m_28818_(optional);
      boolean flag = false;
      if (p_28490_ instanceof Fox.FoxGroupData) {
         fox$type = ((Fox.FoxGroupData)p_28490_).f_28701_;
         if (((Fox.FoxGroupData)p_28490_).m_146777_() >= 2) {
            flag = true;
         }
      } else {
         p_28490_ = new Fox.FoxGroupData(fox$type);
      }

      this.m_28464_(fox$type);
      if (flag) {
         this.m_146762_(-24000);
      }

      if (p_28487_ instanceof ServerLevel) {
         this.m_28562_();
      }

      this.m_6851_(p_28488_);
      return super.m_6518_(p_28487_, p_28488_, p_28489_, p_28490_, p_28491_);
   }

   private void m_28562_() {
      if (this.m_28554_() == Fox.Type.RED) {
         this.f_21346_.m_25352_(4, this.f_28445_);
         this.f_21346_.m_25352_(4, this.f_28446_);
         this.f_21346_.m_25352_(6, this.f_28447_);
      } else {
         this.f_21346_.m_25352_(4, this.f_28447_);
         this.f_21346_.m_25352_(6, this.f_28445_);
         this.f_21346_.m_25352_(6, this.f_28446_);
      }

   }

   protected void m_142075_(Player p_148908_, InteractionHand p_148909_, ItemStack p_148910_) {
      if (this.m_6898_(p_148910_)) {
         this.m_5496_(this.m_7866_(p_148910_), 1.0F, 1.0F);
      }

      super.m_142075_(p_148908_, p_148909_, p_148910_);
   }

   protected float m_6431_(Pose p_28500_, EntityDimensions p_28501_) {
      return this.m_6162_() ? p_28501_.f_20378_ * 0.85F : 0.4F;
   }

   public Fox.Type m_28554_() {
      return Fox.Type.m_28812_(this.f_19804_.m_135370_(f_28437_));
   }

   private void m_28464_(Fox.Type p_28465_) {
      this.f_19804_.m_135381_(f_28437_, p_28465_.m_28820_());
   }

   List<UUID> m_28566_() {
      List<UUID> list = Lists.newArrayList();
      list.add(this.f_19804_.m_135370_(f_28439_).orElse((UUID)null));
      list.add(this.f_19804_.m_135370_(f_28440_).orElse((UUID)null));
      return list;
   }

   void m_28515_(@Nullable UUID p_28516_) {
      if (this.f_19804_.m_135370_(f_28439_).isPresent()) {
         this.f_19804_.m_135381_(f_28440_, Optional.ofNullable(p_28516_));
      } else {
         this.f_19804_.m_135381_(f_28439_, Optional.ofNullable(p_28516_));
      }

   }

   public void m_7380_(CompoundTag p_28518_) {
      super.m_7380_(p_28518_);
      List<UUID> list = this.m_28566_();
      ListTag listtag = new ListTag();

      for(UUID uuid : list) {
         if (uuid != null) {
            listtag.add(NbtUtils.m_129226_(uuid));
         }
      }

      p_28518_.m_128365_("Trusted", listtag);
      p_28518_.m_128379_("Sleeping", this.m_5803_());
      p_28518_.m_128359_("Type", this.m_28554_().m_28811_());
      p_28518_.m_128379_("Sitting", this.m_28555_());
      p_28518_.m_128379_("Crouching", this.m_6047_());
   }

   public void m_7378_(CompoundTag p_28493_) {
      super.m_7378_(p_28493_);
      ListTag listtag = p_28493_.m_128437_("Trusted", 11);

      for(int i = 0; i < listtag.size(); ++i) {
         this.m_28515_(NbtUtils.m_129233_(listtag.get(i)));
      }

      this.m_28626_(p_28493_.m_128471_("Sleeping"));
      this.m_28464_(Fox.Type.m_28816_(p_28493_.m_128461_("Type")));
      this.m_28610_(p_28493_.m_128471_("Sitting"));
      this.m_28614_(p_28493_.m_128471_("Crouching"));
      if (this.f_19853_ instanceof ServerLevel) {
         this.m_28562_();
      }

   }

   public boolean m_28555_() {
      return this.m_28608_(1);
   }

   public void m_28610_(boolean p_28611_) {
      this.m_28532_(1, p_28611_);
   }

   public boolean m_28556_() {
      return this.m_28608_(64);
   }

   void m_28618_(boolean p_28619_) {
      this.m_28532_(64, p_28619_);
   }

   boolean m_28567_() {
      return this.m_28608_(128);
   }

   void m_28622_(boolean p_28623_) {
      this.m_28532_(128, p_28623_);
   }

   public boolean m_5803_() {
      return this.m_28608_(32);
   }

   void m_28626_(boolean p_28627_) {
      this.m_28532_(32, p_28627_);
   }

   private void m_28532_(int p_28533_, boolean p_28534_) {
      if (p_28534_) {
         this.f_19804_.m_135381_(f_28438_, (byte)(this.f_19804_.m_135370_(f_28438_) | p_28533_));
      } else {
         this.f_19804_.m_135381_(f_28438_, (byte)(this.f_19804_.m_135370_(f_28438_) & ~p_28533_));
      }

   }

   private boolean m_28608_(int p_28609_) {
      return (this.f_19804_.m_135370_(f_28438_) & p_28609_) != 0;
   }

   public boolean m_7066_(ItemStack p_28552_) {
      EquipmentSlot equipmentslot = Mob.m_147233_(p_28552_);
      if (!this.m_6844_(equipmentslot).m_41619_()) {
         return false;
      } else {
         return equipmentslot == EquipmentSlot.MAINHAND && super.m_7066_(p_28552_);
      }
   }

   public boolean m_7252_(ItemStack p_28578_) {
      Item item = p_28578_.m_41720_();
      ItemStack itemstack = this.m_6844_(EquipmentSlot.MAINHAND);
      return itemstack.m_41619_() || this.f_28436_ > 0 && item.m_41472_() && !itemstack.m_41720_().m_41472_();
   }

   private void m_28601_(ItemStack p_28602_) {
      if (!p_28602_.m_41619_() && !this.f_19853_.f_46443_) {
         ItemEntity itementity = new ItemEntity(this.f_19853_, this.m_20185_() + this.m_20154_().f_82479_, this.m_20186_() + 1.0D, this.m_20189_() + this.m_20154_().f_82481_, p_28602_);
         itementity.m_32010_(40);
         itementity.m_32052_(this.m_142081_());
         this.m_5496_(SoundEvents.f_11952_, 1.0F, 1.0F);
         this.f_19853_.m_7967_(itementity);
      }
   }

   private void m_28605_(ItemStack p_28606_) {
      ItemEntity itementity = new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), p_28606_);
      this.f_19853_.m_7967_(itementity);
   }

   protected void m_7581_(ItemEntity p_28514_) {
      ItemStack itemstack = p_28514_.m_32055_();
      if (this.m_7252_(itemstack)) {
         int i = itemstack.m_41613_();
         if (i > 1) {
            this.m_28605_(itemstack.m_41620_(i - 1));
         }

         this.m_28601_(this.m_6844_(EquipmentSlot.MAINHAND));
         this.m_21053_(p_28514_);
         this.m_8061_(EquipmentSlot.MAINHAND, itemstack.m_41620_(1));
         this.f_21347_[EquipmentSlot.MAINHAND.m_20749_()] = 2.0F;
         this.m_7938_(p_28514_, itemstack.m_41613_());
         p_28514_.m_146870_();
         this.f_28436_ = 0;
      }

   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_6142_()) {
         boolean flag = this.m_20069_();
         if (flag || this.m_5448_() != null || this.f_19853_.m_46470_()) {
            this.m_28568_();
         }

         if (flag || this.m_5803_()) {
            this.m_28610_(false);
         }

         if (this.m_28556_() && this.f_19853_.f_46441_.nextFloat() < 0.2F) {
            BlockPos blockpos = this.m_142538_();
            BlockState blockstate = this.f_19853_.m_8055_(blockpos);
            this.f_19853_.m_46796_(2001, blockpos, Block.m_49956_(blockstate));
         }
      }

      this.f_28433_ = this.f_28448_;
      if (this.m_28559_()) {
         this.f_28448_ += (1.0F - this.f_28448_) * 0.4F;
      } else {
         this.f_28448_ += (0.0F - this.f_28448_) * 0.4F;
      }

      this.f_28435_ = this.f_28434_;
      if (this.m_6047_()) {
         this.f_28434_ += 0.2F;
         if (this.f_28434_ > 3.0F) {
            this.f_28434_ = 3.0F;
         }
      } else {
         this.f_28434_ = 0.0F;
      }

   }

   public boolean m_6898_(ItemStack p_28594_) {
      return p_28594_.m_150922_(ItemTags.f_144311_);
   }

   protected void m_5502_(Player p_28481_, Mob p_28482_) {
      ((Fox)p_28482_).m_28515_(p_28481_.m_142081_());
   }

   public boolean m_28557_() {
      return this.m_28608_(16);
   }

   public void m_28612_(boolean p_28613_) {
      this.m_28532_(16, p_28613_);
   }

   public boolean m_148924_() {
      return this.f_20899_;
   }

   public boolean m_28558_() {
      return this.f_28434_ == 3.0F;
   }

   public void m_28614_(boolean p_28615_) {
      this.m_28532_(4, p_28615_);
   }

   public boolean m_6047_() {
      return this.m_28608_(4);
   }

   public void m_28616_(boolean p_28617_) {
      this.m_28532_(8, p_28617_);
   }

   public boolean m_28559_() {
      return this.m_28608_(8);
   }

   public float m_28620_(float p_28621_) {
      return Mth.m_14179_(p_28621_, this.f_28433_, this.f_28448_) * 0.11F * (float)Math.PI;
   }

   public float m_28624_(float p_28625_) {
      return Mth.m_14179_(p_28625_, this.f_28435_, this.f_28434_);
   }

   public void m_6710_(@Nullable LivingEntity p_28574_) {
      if (this.m_28567_() && p_28574_ == null) {
         this.m_28622_(false);
      }

      super.m_6710_(p_28574_);
   }

   protected int m_5639_(float p_28545_, float p_28546_) {
      return Mth.m_14167_((p_28545_ - 5.0F) * p_28546_);
   }

   void m_28568_() {
      this.m_28626_(false);
   }

   void m_28569_() {
      this.m_28616_(false);
      this.m_28614_(false);
      this.m_28610_(false);
      this.m_28626_(false);
      this.m_28622_(false);
      this.m_28618_(false);
   }

   boolean m_28570_() {
      return !this.m_5803_() && !this.m_28555_() && !this.m_28556_();
   }

   public void m_8032_() {
      SoundEvent soundevent = this.m_7515_();
      if (soundevent == SoundEvents.f_11949_) {
         this.m_5496_(soundevent, 2.0F, this.m_6100_());
      } else {
         super.m_8032_();
      }

   }

   @Nullable
   protected SoundEvent m_7515_() {
      if (this.m_5803_()) {
         return SoundEvents.f_11950_;
      } else {
         if (!this.f_19853_.m_46461_() && this.f_19796_.nextFloat() < 0.1F) {
            List<Player> list = this.f_19853_.m_6443_(Player.class, this.m_142469_().m_82377_(16.0D, 16.0D, 16.0D), EntitySelector.f_20408_);
            if (list.isEmpty()) {
               return SoundEvents.f_11949_;
            }
         }

         return SoundEvents.f_11944_;
      }
   }

   @Nullable
   protected SoundEvent m_7975_(DamageSource p_28548_) {
      return SoundEvents.f_11948_;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return SoundEvents.f_11946_;
   }

   boolean m_28529_(UUID p_28530_) {
      return this.m_28566_().contains(p_28530_);
   }

   protected void m_6668_(DamageSource p_28536_) {
      ItemStack itemstack = this.m_6844_(EquipmentSlot.MAINHAND);
      if (!itemstack.m_41619_()) {
         this.m_19983_(itemstack);
         this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
      }

      super.m_6668_(p_28536_);
   }

   public static boolean m_28471_(Fox p_28472_, LivingEntity p_28473_) {
      double d0 = p_28473_.m_20189_() - p_28472_.m_20189_();
      double d1 = p_28473_.m_20185_() - p_28472_.m_20185_();
      double d2 = d0 / d1;
      int i = 6;

      for(int j = 0; j < 6; ++j) {
         double d3 = d2 == 0.0D ? 0.0D : d0 * (double)((float)j / 6.0F);
         double d4 = d2 == 0.0D ? d1 * (double)((float)j / 6.0F) : d3 / d2;

         for(int k = 1; k < 4; ++k) {
            if (!p_28472_.f_19853_.m_8055_(new BlockPos(p_28472_.m_20185_() + d4, p_28472_.m_20186_() + (double)k, p_28472_.m_20189_() + d3)).m_60767_().m_76336_()) {
               return false;
            }
         }
      }

      return true;
   }

   public Vec3 m_7939_() {
      return new Vec3(0.0D, (double)(0.55F * this.m_20192_()), (double)(this.m_20205_() * 0.4F));
   }

   class DefendTrustedTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {
      @Nullable
      private LivingEntity f_28629_;
      private LivingEntity f_28630_;
      private int f_28631_;

      public DefendTrustedTargetGoal(Class<LivingEntity> p_28634_, boolean p_28635_, @Nullable boolean p_28636_, Predicate<LivingEntity> p_28637_) {
         super(Fox.this, p_28634_, 10, p_28635_, p_28636_, p_28637_);
      }

      public boolean m_8036_() {
         if (this.f_26049_ > 0 && this.f_26135_.m_21187_().nextInt(this.f_26049_) != 0) {
            return false;
         } else {
            for(UUID uuid : Fox.this.m_28566_()) {
               if (uuid != null && Fox.this.f_19853_ instanceof ServerLevel) {
                  Entity entity = ((ServerLevel)Fox.this.f_19853_).m_8791_(uuid);
                  if (entity instanceof LivingEntity) {
                     LivingEntity livingentity = (LivingEntity)entity;
                     this.f_28630_ = livingentity;
                     this.f_28629_ = livingentity.m_142581_();
                     int i = livingentity.m_21213_();
                     return i != this.f_28631_ && this.m_26150_(this.f_28629_, this.f_26051_);
                  }
               }
            }

            return false;
         }
      }

      public void m_8056_() {
         this.m_26070_(this.f_28629_);
         this.f_26050_ = this.f_28629_;
         if (this.f_28630_ != null) {
            this.f_28631_ = this.f_28630_.m_21213_();
         }

         Fox.this.m_5496_(SoundEvents.f_11943_, 1.0F, 1.0F);
         Fox.this.m_28622_(true);
         Fox.this.m_28568_();
         super.m_8056_();
      }
   }

   class FaceplantGoal extends Goal {
      int f_28640_;

      public FaceplantGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.LOOK, Goal.Flag.JUMP, Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         return Fox.this.m_28556_();
      }

      public boolean m_8045_() {
         return this.m_8036_() && this.f_28640_ > 0;
      }

      public void m_8056_() {
         this.f_28640_ = 40;
      }

      public void m_8041_() {
         Fox.this.m_28618_(false);
      }

      public void m_8037_() {
         --this.f_28640_;
      }
   }

   public class FoxAlertableEntitiesSelector implements Predicate<LivingEntity> {
      public boolean test(LivingEntity p_28653_) {
         if (p_28653_ instanceof Fox) {
            return false;
         } else if (!(p_28653_ instanceof Chicken) && !(p_28653_ instanceof Rabbit) && !(p_28653_ instanceof Monster)) {
            if (p_28653_ instanceof TamableAnimal) {
               return !((TamableAnimal)p_28653_).m_21824_();
            } else if (!(p_28653_ instanceof Player) || !p_28653_.m_5833_() && !((Player)p_28653_).m_7500_()) {
               if (Fox.this.m_28529_(p_28653_.m_142081_())) {
                  return false;
               } else {
                  return !p_28653_.m_5803_() && !p_28653_.m_20163_();
               }
            } else {
               return false;
            }
         } else {
            return true;
         }
      }
   }

   abstract class FoxBehaviorGoal extends Goal {
      private final TargetingConditions f_28657_ = TargetingConditions.m_148352_().m_26883_(12.0D).m_148355_().m_26888_(Fox.this.new FoxAlertableEntitiesSelector());

      protected boolean m_28663_() {
         BlockPos blockpos = new BlockPos(Fox.this.m_20185_(), Fox.this.m_142469_().f_82292_, Fox.this.m_20189_());
         return !Fox.this.f_19853_.m_45527_(blockpos) && Fox.this.m_21692_(blockpos) >= 0.0F;
      }

      protected boolean m_28664_() {
         return !Fox.this.f_19853_.m_45971_(LivingEntity.class, this.f_28657_, Fox.this, Fox.this.m_142469_().m_82377_(12.0D, 6.0D, 12.0D)).isEmpty();
      }
   }

   class FoxBreedGoal extends BreedGoal {
      public FoxBreedGoal(double p_28668_) {
         super(Fox.this, p_28668_);
      }

      public void m_8056_() {
         ((Fox)this.f_25113_).m_28569_();
         ((Fox)this.f_25115_).m_28569_();
         super.m_8056_();
      }

      protected void m_8026_() {
         ServerLevel serverlevel = (ServerLevel)this.f_25114_;
         Fox fox = (Fox)this.f_25113_.m_142606_(serverlevel, this.f_25115_);
         final net.minecraftforge.event.entity.living.BabyEntitySpawnEvent event = new net.minecraftforge.event.entity.living.BabyEntitySpawnEvent(f_25113_, f_25115_, fox);
         final boolean cancelled = net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
         fox = (Fox) event.getChild();
         if (cancelled) {
            //Reset the "inLove" state for the animals
            this.f_25113_.m_146762_(6000);
            this.f_25115_.m_146762_(6000);
            this.f_25113_.m_27594_();
            this.f_25115_.m_27594_();
            return;
         }
         if (fox != null) {
            ServerPlayer serverplayer = this.f_25113_.m_27592_();
            ServerPlayer serverplayer1 = this.f_25115_.m_27592_();
            ServerPlayer serverplayer2 = serverplayer;
            if (serverplayer != null) {
               fox.m_28515_(serverplayer.m_142081_());
            } else {
               serverplayer2 = serverplayer1;
            }

            if (serverplayer1 != null && serverplayer != serverplayer1) {
               fox.m_28515_(serverplayer1.m_142081_());
            }

            if (serverplayer2 != null) {
               serverplayer2.m_36220_(Stats.f_12937_);
               CriteriaTriggers.f_10581_.m_147278_(serverplayer2, this.f_25113_, this.f_25115_, fox);
            }

            this.f_25113_.m_146762_(6000);
            this.f_25115_.m_146762_(6000);
            this.f_25113_.m_27594_();
            this.f_25115_.m_27594_();
            fox.m_146762_(-24000);
            fox.m_7678_(this.f_25113_.m_20185_(), this.f_25113_.m_20186_(), this.f_25113_.m_20189_(), 0.0F, 0.0F);
            serverlevel.m_47205_(fox);
            this.f_25114_.m_7605_(this.f_25113_, (byte)18);
            if (this.f_25114_.m_46469_().m_46207_(GameRules.f_46135_)) {
               this.f_25114_.m_7967_(new ExperienceOrb(this.f_25114_, this.f_25113_.m_20185_(), this.f_25113_.m_20186_(), this.f_25113_.m_20189_(), this.f_25113_.m_21187_().nextInt(7) + 1));
            }

         }
      }
   }

   public class FoxEatBerriesGoal extends MoveToBlockGoal {
      private static final int f_148925_ = 40;
      protected int f_28671_;

      public FoxEatBerriesGoal(double p_28675_, int p_28676_, int p_28677_) {
         super(Fox.this, p_28675_, p_28676_, p_28677_);
      }

      public double m_8052_() {
         return 2.0D;
      }

      public boolean m_8064_() {
         return this.f_25601_ % 100 == 0;
      }

      protected boolean m_6465_(LevelReader p_28680_, BlockPos p_28681_) {
         BlockState blockstate = p_28680_.m_8055_(p_28681_);
         return blockstate.m_60713_(Blocks.f_50685_) && blockstate.m_61143_(SweetBerryBushBlock.f_57244_) >= 2 || CaveVines.m_152951_(blockstate);
      }

      public void m_8037_() {
         if (this.m_25625_()) {
            if (this.f_28671_ >= 40) {
               this.m_28686_();
            } else {
               ++this.f_28671_;
            }
         } else if (!this.m_25625_() && Fox.this.f_19796_.nextFloat() < 0.05F) {
            Fox.this.m_5496_(SoundEvents.f_11951_, 1.0F, 1.0F);
         }

         super.m_8037_();
      }

      protected void m_28686_() {
         if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(Fox.this.f_19853_, Fox.this)) {
            BlockState blockstate = Fox.this.f_19853_.m_8055_(this.f_25602_);
            if (blockstate.m_60713_(Blocks.f_50685_)) {
               this.m_148928_(blockstate);
            } else if (CaveVines.m_152951_(blockstate)) {
               this.m_148926_(blockstate);
            }

         }
      }

      private void m_148926_(BlockState p_148927_) {
         CaveVines.m_152953_(p_148927_, Fox.this.f_19853_, this.f_25602_);
      }

      private void m_148928_(BlockState p_148929_) {
         int i = p_148929_.m_61143_(SweetBerryBushBlock.f_57244_);
         p_148929_.m_61124_(SweetBerryBushBlock.f_57244_, Integer.valueOf(1));
         int j = 1 + Fox.this.f_19853_.f_46441_.nextInt(2) + (i == 3 ? 1 : 0);
         ItemStack itemstack = Fox.this.m_6844_(EquipmentSlot.MAINHAND);
         if (itemstack.m_41619_()) {
            Fox.this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42780_));
            --j;
         }

         if (j > 0) {
            Block.m_49840_(Fox.this.f_19853_, this.f_25602_, new ItemStack(Items.f_42780_, j));
         }

         Fox.this.m_5496_(SoundEvents.f_12457_, 1.0F, 1.0F);
         Fox.this.f_19853_.m_7731_(this.f_25602_, p_148929_.m_61124_(SweetBerryBushBlock.f_57244_, Integer.valueOf(1)), 2);
      }

      public boolean m_8036_() {
         return !Fox.this.m_5803_() && super.m_8036_();
      }

      public void m_8056_() {
         this.f_28671_ = 0;
         Fox.this.m_28610_(false);
         super.m_8056_();
      }
   }

   class FoxFloatGoal extends FloatGoal {
      public FoxFloatGoal() {
         super(Fox.this);
      }

      public void m_8056_() {
         super.m_8056_();
         Fox.this.m_28569_();
      }

      public boolean m_8036_() {
         return Fox.this.m_20069_() && Fox.this.m_20120_(FluidTags.f_13131_) > 0.25D || Fox.this.m_20077_();
      }
   }

   class FoxFollowParentGoal extends FollowParentGoal {
      private final Fox f_28693_;

      public FoxFollowParentGoal(Fox p_28696_, double p_28697_) {
         super(p_28696_, p_28697_);
         this.f_28693_ = p_28696_;
      }

      public boolean m_8036_() {
         return !this.f_28693_.m_28567_() && super.m_8036_();
      }

      public boolean m_8045_() {
         return !this.f_28693_.m_28567_() && super.m_8045_();
      }

      public void m_8056_() {
         this.f_28693_.m_28569_();
         super.m_8056_();
      }
   }

   public static class FoxGroupData extends AgeableMob.AgeableMobGroupData {
      public final Fox.Type f_28701_;

      public FoxGroupData(Fox.Type p_28703_) {
         super(false);
         this.f_28701_ = p_28703_;
      }
   }

   class FoxLookAtPlayerGoal extends LookAtPlayerGoal {
      public FoxLookAtPlayerGoal(Mob p_28707_, Class<? extends LivingEntity> p_28708_, float p_28709_) {
         super(p_28707_, p_28708_, p_28709_);
      }

      public boolean m_8036_() {
         return super.m_8036_() && !Fox.this.m_28556_() && !Fox.this.m_28559_();
      }

      public boolean m_8045_() {
         return super.m_8045_() && !Fox.this.m_28556_() && !Fox.this.m_28559_();
      }
   }

   public class FoxLookControl extends LookControl {
      public FoxLookControl() {
         super(Fox.this);
      }

      public void m_8128_() {
         if (!Fox.this.m_5803_()) {
            super.m_8128_();
         }

      }

      protected boolean m_8106_() {
         return !Fox.this.m_28557_() && !Fox.this.m_6047_() && !Fox.this.m_28559_() && !Fox.this.m_28556_();
      }
   }

   class FoxMeleeAttackGoal extends MeleeAttackGoal {
      public FoxMeleeAttackGoal(double p_28720_, boolean p_28721_) {
         super(Fox.this, p_28720_, p_28721_);
      }

      protected void m_6739_(LivingEntity p_28724_, double p_28725_) {
         double d0 = this.m_6639_(p_28724_);
         if (p_28725_ <= d0 && this.m_25564_()) {
            this.m_25563_();
            this.f_25540_.m_7327_(p_28724_);
            Fox.this.m_5496_(SoundEvents.f_11945_, 1.0F, 1.0F);
         }

      }

      public void m_8056_() {
         Fox.this.m_28616_(false);
         super.m_8056_();
      }

      public boolean m_8036_() {
         return !Fox.this.m_28555_() && !Fox.this.m_5803_() && !Fox.this.m_6047_() && !Fox.this.m_28556_() && super.m_8036_();
      }
   }

   class FoxMoveControl extends MoveControl {
      public FoxMoveControl() {
         super(Fox.this);
      }

      public void m_8126_() {
         if (Fox.this.m_28570_()) {
            super.m_8126_();
         }

      }
   }

   class FoxPanicGoal extends PanicGoal {
      public FoxPanicGoal(double p_28734_) {
         super(Fox.this, p_28734_);
      }

      public boolean m_8036_() {
         return !Fox.this.m_28567_() && super.m_8036_();
      }
   }

   public class FoxPounceGoal extends JumpGoal {
      public boolean m_8036_() {
         if (!Fox.this.m_28558_()) {
            return false;
         } else {
            LivingEntity livingentity = Fox.this.m_5448_();
            if (livingentity != null && livingentity.m_6084_()) {
               if (livingentity.m_6374_() != livingentity.m_6350_()) {
                  return false;
               } else {
                  boolean flag = Fox.m_28471_(Fox.this, livingentity);
                  if (!flag) {
                     Fox.this.m_21573_().m_6570_(livingentity, 0);
                     Fox.this.m_28614_(false);
                     Fox.this.m_28616_(false);
                  }

                  return flag;
               }
            } else {
               return false;
            }
         }
      }

      public boolean m_8045_() {
         LivingEntity livingentity = Fox.this.m_5448_();
         if (livingentity != null && livingentity.m_6084_()) {
            double d0 = Fox.this.m_20184_().f_82480_;
            return (!(d0 * d0 < (double)0.05F) || !(Math.abs(Fox.this.m_146909_()) < 15.0F) || !Fox.this.f_19861_) && !Fox.this.m_28556_();
         } else {
            return false;
         }
      }

      public boolean m_6767_() {
         return false;
      }

      public void m_8056_() {
         Fox.this.m_6862_(true);
         Fox.this.m_28612_(true);
         Fox.this.m_28616_(false);
         LivingEntity livingentity = Fox.this.m_5448_();
         Fox.this.m_21563_().m_24960_(livingentity, 60.0F, 30.0F);
         Vec3 vec3 = (new Vec3(livingentity.m_20185_() - Fox.this.m_20185_(), livingentity.m_20186_() - Fox.this.m_20186_(), livingentity.m_20189_() - Fox.this.m_20189_())).m_82541_();
         Fox.this.m_20256_(Fox.this.m_20184_().m_82520_(vec3.f_82479_ * 0.8D, 0.9D, vec3.f_82481_ * 0.8D));
         Fox.this.m_21573_().m_26573_();
      }

      public void m_8041_() {
         Fox.this.m_28614_(false);
         Fox.this.f_28434_ = 0.0F;
         Fox.this.f_28435_ = 0.0F;
         Fox.this.m_28616_(false);
         Fox.this.m_28612_(false);
      }

      public void m_8037_() {
         LivingEntity livingentity = Fox.this.m_5448_();
         if (livingentity != null) {
            Fox.this.m_21563_().m_24960_(livingentity, 60.0F, 30.0F);
         }

         if (!Fox.this.m_28556_()) {
            Vec3 vec3 = Fox.this.m_20184_();
            if (vec3.f_82480_ * vec3.f_82480_ < (double)0.03F && Fox.this.m_146909_() != 0.0F) {
               Fox.this.m_146926_(Mth.m_14201_(Fox.this.m_146909_(), 0.0F, 0.2F));
            } else {
               double d0 = vec3.m_165924_();
               double d1 = Math.signum(-vec3.f_82480_) * Math.acos(d0 / vec3.m_82553_()) * (double)(180F / (float)Math.PI);
               Fox.this.m_146926_((float)d1);
            }
         }

         if (livingentity != null && Fox.this.m_20270_(livingentity) <= 2.0F) {
            Fox.this.m_7327_(livingentity);
         } else if (Fox.this.m_146909_() > 0.0F && Fox.this.f_19861_ && (float)Fox.this.m_20184_().f_82480_ != 0.0F && Fox.this.f_19853_.m_8055_(Fox.this.m_142538_()).m_60713_(Blocks.f_50125_)) {
            Fox.this.m_146926_(60.0F);
            Fox.this.m_6710_((LivingEntity)null);
            Fox.this.m_28618_(true);
         }

      }
   }

   class FoxSearchForItemsGoal extends Goal {
      public FoxSearchForItemsGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      }

      public boolean m_8036_() {
         if (!Fox.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_()) {
            return false;
         } else if (Fox.this.m_5448_() == null && Fox.this.m_142581_() == null) {
            if (!Fox.this.m_28570_()) {
               return false;
            } else if (Fox.this.m_21187_().nextInt(10) != 0) {
               return false;
            } else {
               List<ItemEntity> list = Fox.this.f_19853_.m_6443_(ItemEntity.class, Fox.this.m_142469_().m_82377_(8.0D, 8.0D, 8.0D), Fox.f_28441_);
               return !list.isEmpty() && Fox.this.m_6844_(EquipmentSlot.MAINHAND).m_41619_();
            }
         } else {
            return false;
         }
      }

      public void m_8037_() {
         List<ItemEntity> list = Fox.this.f_19853_.m_6443_(ItemEntity.class, Fox.this.m_142469_().m_82377_(8.0D, 8.0D, 8.0D), Fox.f_28441_);
         ItemStack itemstack = Fox.this.m_6844_(EquipmentSlot.MAINHAND);
         if (itemstack.m_41619_() && !list.isEmpty()) {
            Fox.this.m_21573_().m_5624_(list.get(0), (double)1.2F);
         }

      }

      public void m_8056_() {
         List<ItemEntity> list = Fox.this.f_19853_.m_6443_(ItemEntity.class, Fox.this.m_142469_().m_82377_(8.0D, 8.0D, 8.0D), Fox.f_28441_);
         if (!list.isEmpty()) {
            Fox.this.m_21573_().m_5624_(list.get(0), (double)1.2F);
         }

      }
   }

   class FoxStrollThroughVillageGoal extends StrollThroughVillageGoal {
      public FoxStrollThroughVillageGoal(int p_28754_, int p_28755_) {
         super(Fox.this, p_28755_);
      }

      public void m_8056_() {
         Fox.this.m_28569_();
         super.m_8056_();
      }

      public boolean m_8036_() {
         return super.m_8036_() && this.m_28759_();
      }

      public boolean m_8045_() {
         return super.m_8045_() && this.m_28759_();
      }

      private boolean m_28759_() {
         return !Fox.this.m_5803_() && !Fox.this.m_28555_() && !Fox.this.m_28567_() && Fox.this.m_5448_() == null;
      }
   }

   class PerchAndSearchGoal extends Fox.FoxBehaviorGoal {
      private double f_28761_;
      private double f_28762_;
      private int f_28763_;
      private int f_28764_;

      public PerchAndSearchGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         return Fox.this.m_142581_() == null && Fox.this.m_21187_().nextFloat() < 0.02F && !Fox.this.m_5803_() && Fox.this.m_5448_() == null && Fox.this.m_21573_().m_26571_() && !this.m_28664_() && !Fox.this.m_28557_() && !Fox.this.m_6047_();
      }

      public boolean m_8045_() {
         return this.f_28764_ > 0;
      }

      public void m_8056_() {
         this.m_28772_();
         this.f_28764_ = 2 + Fox.this.m_21187_().nextInt(3);
         Fox.this.m_28610_(true);
         Fox.this.m_21573_().m_26573_();
      }

      public void m_8041_() {
         Fox.this.m_28610_(false);
      }

      public void m_8037_() {
         --this.f_28763_;
         if (this.f_28763_ <= 0) {
            --this.f_28764_;
            this.m_28772_();
         }

         Fox.this.m_21563_().m_24950_(Fox.this.m_20185_() + this.f_28761_, Fox.this.m_20188_(), Fox.this.m_20189_() + this.f_28762_, (float)Fox.this.m_8085_(), (float)Fox.this.m_8132_());
      }

      private void m_28772_() {
         double d0 = (Math.PI * 2D) * Fox.this.m_21187_().nextDouble();
         this.f_28761_ = Math.cos(d0);
         this.f_28762_ = Math.sin(d0);
         this.f_28763_ = 80 + Fox.this.m_21187_().nextInt(20);
      }
   }

   class SeekShelterGoal extends FleeSunGoal {
      private int f_28774_ = 100;

      public SeekShelterGoal(double p_28777_) {
         super(Fox.this, p_28777_);
      }

      public boolean m_8036_() {
         if (!Fox.this.m_5803_() && this.f_25214_.m_5448_() == null) {
            if (Fox.this.f_19853_.m_46470_()) {
               return true;
            } else if (this.f_28774_ > 0) {
               --this.f_28774_;
               return false;
            } else {
               this.f_28774_ = 100;
               BlockPos blockpos = this.f_25214_.m_142538_();
               return Fox.this.f_19853_.m_46461_() && Fox.this.f_19853_.m_45527_(blockpos) && !((ServerLevel)Fox.this.f_19853_).m_8802_(blockpos) && this.m_25226_();
            }
         } else {
            return false;
         }
      }

      public void m_8056_() {
         Fox.this.m_28569_();
         super.m_8056_();
      }
   }

   class SleepGoal extends Fox.FoxBehaviorGoal {
      private static final int f_148930_ = 140;
      private int f_28781_ = Fox.this.f_19796_.nextInt(140);

      public SleepGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
      }

      public boolean m_8036_() {
         if (Fox.this.f_20900_ == 0.0F && Fox.this.f_20901_ == 0.0F && Fox.this.f_20902_ == 0.0F) {
            return this.m_28788_() || Fox.this.m_5803_();
         } else {
            return false;
         }
      }

      public boolean m_8045_() {
         return this.m_28788_();
      }

      private boolean m_28788_() {
         if (this.f_28781_ > 0) {
            --this.f_28781_;
            return false;
         } else {
            return Fox.this.f_19853_.m_46461_() && this.m_28663_() && !this.m_28664_() && !Fox.this.f_146808_;
         }
      }

      public void m_8041_() {
         this.f_28781_ = Fox.this.f_19796_.nextInt(140);
         Fox.this.m_28569_();
      }

      public void m_8056_() {
         Fox.this.m_28610_(false);
         Fox.this.m_28614_(false);
         Fox.this.m_28616_(false);
         Fox.this.m_6862_(false);
         Fox.this.m_28626_(true);
         Fox.this.m_21573_().m_26573_();
         Fox.this.m_21566_().m_6849_(Fox.this.m_20185_(), Fox.this.m_20186_(), Fox.this.m_20189_(), 0.0D);
      }
   }

   class StalkPreyGoal extends Goal {
      public StalkPreyGoal() {
         this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      }

      public boolean m_8036_() {
         if (Fox.this.m_5803_()) {
            return false;
         } else {
            LivingEntity livingentity = Fox.this.m_5448_();
            return livingentity != null && livingentity.m_6084_() && Fox.f_28443_.test(livingentity) && Fox.this.m_20280_(livingentity) > 36.0D && !Fox.this.m_6047_() && !Fox.this.m_28559_() && !Fox.this.f_20899_;
         }
      }

      public void m_8056_() {
         Fox.this.m_28610_(false);
         Fox.this.m_28618_(false);
      }

      public void m_8041_() {
         LivingEntity livingentity = Fox.this.m_5448_();
         if (livingentity != null && Fox.m_28471_(Fox.this, livingentity)) {
            Fox.this.m_28616_(true);
            Fox.this.m_28614_(true);
            Fox.this.m_21573_().m_26573_();
            Fox.this.m_21563_().m_24960_(livingentity, (float)Fox.this.m_8085_(), (float)Fox.this.m_8132_());
         } else {
            Fox.this.m_28616_(false);
            Fox.this.m_28614_(false);
         }

      }

      public void m_8037_() {
         LivingEntity livingentity = Fox.this.m_5448_();
         Fox.this.m_21563_().m_24960_(livingentity, (float)Fox.this.m_8085_(), (float)Fox.this.m_8132_());
         if (Fox.this.m_20280_(livingentity) <= 36.0D) {
            Fox.this.m_28616_(true);
            Fox.this.m_28614_(true);
            Fox.this.m_21573_().m_26573_();
         } else {
            Fox.this.m_21573_().m_5624_(livingentity, 1.5D);
         }

      }
   }

   public static enum Type {
      RED(0, "red", Biomes.f_48206_, Biomes.f_48220_, Biomes.f_48180_, Biomes.f_48154_, Biomes.f_48189_, Biomes.f_48155_, Biomes.f_48190_),
      SNOW(1, "snow", Biomes.f_48152_, Biomes.f_48153_, Biomes.f_48188_);

      private static final Fox.Type[] f_28798_ = Arrays.stream(values()).sorted(Comparator.comparingInt(Fox.Type::m_28820_)).toArray((p_28822_) -> {
         return new Fox.Type[p_28822_];
      });
      private static final Map<String, Fox.Type> f_28799_ = Arrays.stream(values()).collect(Collectors.toMap(Fox.Type::m_28811_, (p_28815_) -> {
         return p_28815_;
      }));
      private final int f_28800_;
      private final String f_28801_;
      private final List<ResourceKey<Biome>> f_28802_;

      private Type(int p_28808_, String p_28809_, ResourceKey<Biome>... p_28810_) {
         this.f_28800_ = p_28808_;
         this.f_28801_ = p_28809_;
         this.f_28802_ = Arrays.asList(p_28810_);
      }

      public String m_28811_() {
         return this.f_28801_;
      }

      public int m_28820_() {
         return this.f_28800_;
      }

      public static Fox.Type m_28816_(String p_28817_) {
         return f_28799_.getOrDefault(p_28817_, RED);
      }

      public static Fox.Type m_28812_(int p_28813_) {
         if (p_28813_ < 0 || p_28813_ > f_28798_.length) {
            p_28813_ = 0;
         }

         return f_28798_[p_28813_];
      }

      public static Fox.Type m_28818_(Optional<ResourceKey<Biome>> p_28819_) {
         return p_28819_.isPresent() && SNOW.f_28802_.contains(p_28819_.get()) ? SNOW : RED;
      }
   }
}
