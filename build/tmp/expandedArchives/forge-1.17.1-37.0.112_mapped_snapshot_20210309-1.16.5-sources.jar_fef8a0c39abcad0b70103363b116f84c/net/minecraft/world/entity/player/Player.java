package net.minecraft.world.entity.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.Unit;
import net.minecraft.world.Container;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.boss.EnderDragonPart;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.BaseCommandBlock;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;

public abstract class Player extends LivingEntity {
   public static final String PERSISTED_NBT_TAG = "PlayerPersisted";
   public static final String f_150081_ = "OfflinePlayer:";
   public static final int f_150082_ = 16;
   public static final int f_150083_ = 20;
   public static final int f_150084_ = 100;
   public static final int f_150085_ = 10;
   public static final int f_150086_ = 200;
   public static final float f_150087_ = 1.5F;
   public static final float f_150088_ = 0.6F;
   public static final float f_150089_ = 0.6F;
   public static final float f_150090_ = 1.62F;
   public static final EntityDimensions f_36088_ = EntityDimensions.m_20395_(0.6F, 1.8F);
   private static final Map<Pose, EntityDimensions> f_36074_ = ImmutableMap.<Pose, EntityDimensions>builder().put(Pose.STANDING, f_36088_).put(Pose.SLEEPING, f_20910_).put(Pose.FALL_FLYING, EntityDimensions.m_20395_(0.6F, 0.6F)).put(Pose.SWIMMING, EntityDimensions.m_20395_(0.6F, 0.6F)).put(Pose.SPIN_ATTACK, EntityDimensions.m_20395_(0.6F, 0.6F)).put(Pose.CROUCHING, EntityDimensions.m_20395_(0.6F, 1.5F)).put(Pose.DYING, EntityDimensions.m_20398_(0.2F, 0.2F)).build();
   private static final int f_150091_ = 25;
   private static final EntityDataAccessor<Float> f_36107_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> f_36108_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135028_);
   protected static final EntityDataAccessor<Byte> f_36089_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135027_);
   protected static final EntityDataAccessor<Byte> f_36090_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135027_);
   protected static final EntityDataAccessor<CompoundTag> f_36091_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135042_);
   protected static final EntityDataAccessor<CompoundTag> f_36092_ = SynchedEntityData.m_135353_(Player.class, EntityDataSerializers.f_135042_);
   private long f_36109_;
   private final Inventory f_36093_ = new Inventory(this);
   protected PlayerEnderChestContainer f_36094_ = new PlayerEnderChestContainer();
   public final InventoryMenu f_36095_;
   public AbstractContainerMenu f_36096_;
   protected FoodData f_36097_ = new FoodData();
   protected int f_36098_;
   public float f_36099_;
   public float f_36100_;
   public int f_36101_;
   public double f_36102_;
   public double f_36103_;
   public double f_36104_;
   public double f_36105_;
   public double f_36106_;
   public double f_36075_;
   private int f_36110_;
   protected boolean f_36076_;
   private final Abilities f_36077_ = new Abilities();
   public int f_36078_;
   public int f_36079_;
   public float f_36080_;
   protected int f_36081_;
   protected final float f_36082_ = 0.02F;
   private int f_36111_;
   private final GameProfile f_36084_;
   private boolean f_36085_;
   private ItemStack f_36086_ = ItemStack.f_41583_;
   private final ItemCooldowns f_36087_ = this.m_7478_();
   @Nullable
   public FishingHook f_36083_;
   private final java.util.Collection<MutableComponent> prefixes = new java.util.LinkedList<>();
   private final java.util.Collection<MutableComponent> suffixes = new java.util.LinkedList<>();
   @Nullable private Pose forcedPose;

   public Player(Level p_36114_, BlockPos p_36115_, float p_36116_, GameProfile p_36117_) {
      super(EntityType.f_20532_, p_36114_);
      this.m_20084_(m_36198_(p_36117_));
      this.f_36084_ = p_36117_;
      this.f_36095_ = new InventoryMenu(this.f_36093_, !p_36114_.f_46443_, this);
      this.f_36096_ = this.f_36095_;
      this.m_7678_((double)p_36115_.m_123341_() + 0.5D, (double)(p_36115_.m_123342_() + 1), (double)p_36115_.m_123343_() + 0.5D, p_36116_, 0.0F);
      this.f_20896_ = 180.0F;
   }

   public boolean m_36187_(Level p_36188_, BlockPos p_36189_, GameType p_36190_) {
      if (!p_36190_.m_46407_()) {
         return false;
      } else if (p_36190_ == GameType.SPECTATOR) {
         return true;
      } else if (this.m_36326_()) {
         return false;
      } else {
         ItemStack itemstack = this.m_21205_();
         return itemstack.m_41619_() || !itemstack.m_41633_(p_36188_.m_5999_(), new BlockInWorld(p_36188_, p_36189_, false));
      }
   }

   public static AttributeSupplier.Builder m_36340_() {
       return LivingEntity.m_21183_().m_22268_(Attributes.f_22281_, 1.0D).m_22268_(Attributes.f_22279_, (double) 0.1F).m_22266_(Attributes.f_22283_).m_22266_(Attributes.f_22286_).m_22266_(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).m_22266_(Attributes.f_22282_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_36107_, 0.0F);
      this.f_19804_.m_135372_(f_36108_, 0);
      this.f_19804_.m_135372_(f_36089_, (byte)0);
      this.f_19804_.m_135372_(f_36090_, (byte)1);
      this.f_19804_.m_135372_(f_36091_, new CompoundTag());
      this.f_19804_.m_135372_(f_36092_, new CompoundTag());
   }

   public void m_8119_() {
      net.minecraftforge.fmllegacy.hooks.BasicEventHooks.onPlayerPreTick(this);
      this.f_19794_ = this.m_5833_();
      if (this.m_5833_()) {
         this.f_19861_ = false;
      }

      if (this.f_36101_ > 0) {
         --this.f_36101_;
      }

      if (this.m_5803_()) {
         ++this.f_36110_;
         if (this.f_36110_ > 100) {
            this.f_36110_ = 100;
         }

         if (!this.f_19853_.f_46443_ && !net.minecraftforge.event.ForgeEventFactory.fireSleepingTimeCheck(this, m_21257_())) {
            this.m_6145_(false, true);
         }
      } else if (this.f_36110_ > 0) {
         ++this.f_36110_;
         if (this.f_36110_ >= 110) {
            this.f_36110_ = 0;
         }
      }

      this.m_7602_();
      super.m_8119_();
      if (!this.f_19853_.f_46443_ && this.f_36096_ != null && !this.f_36096_.m_6875_(this)) {
         this.m_6915_();
         this.f_36096_ = this.f_36095_;
      }

      this.m_36377_();
      if (!this.f_19853_.f_46443_) {
         this.f_36097_.m_38710_(this);
         this.m_36220_(Stats.f_144255_);
         this.m_36220_(Stats.f_144256_);
         if (this.m_6084_()) {
            this.m_36220_(Stats.f_12991_);
         }

         if (this.m_20163_()) {
            this.m_36220_(Stats.f_12993_);
         }

         if (!this.m_5803_()) {
            this.m_36220_(Stats.f_12992_);
         }
      }

      int i = 29999999;
      double d0 = Mth.m_14008_(this.m_20185_(), -2.9999999E7D, 2.9999999E7D);
      double d1 = Mth.m_14008_(this.m_20189_(), -2.9999999E7D, 2.9999999E7D);
      if (d0 != this.m_20185_() || d1 != this.m_20189_()) {
         this.m_6034_(d0, this.m_20186_(), d1);
      }

      ++this.f_20922_;
      ItemStack itemstack = this.m_21205_();
      if (!ItemStack.m_41728_(this.f_36086_, itemstack)) {
         if (!ItemStack.m_41758_(this.f_36086_, itemstack)) {
            this.m_36334_();
         }

         this.f_36086_ = itemstack.m_41777_();
      }

      this.m_36372_();
      this.f_36087_.m_41518_();
      this.m_7594_();
      net.minecraftforge.fmllegacy.hooks.BasicEventHooks.onPlayerPostTick(this);
   }

   public boolean m_36341_() {
      return this.m_6144_();
   }

   protected boolean m_36342_() {
      return this.m_6144_();
   }

   protected boolean m_36343_() {
      return this.m_6144_();
   }

   protected boolean m_7602_() {
      this.f_36076_ = this.m_19941_(FluidTags.f_13131_);
      return this.f_36076_;
   }

   private void m_36372_() {
      ItemStack itemstack = this.m_6844_(EquipmentSlot.HEAD);
      if (itemstack.m_150930_(Items.f_42354_) && !this.m_19941_(FluidTags.f_13131_)) {
         this.m_7292_(new MobEffectInstance(MobEffects.f_19608_, 200, 0, false, false, true));
      }

   }

   protected ItemCooldowns m_7478_() {
      return new ItemCooldowns();
   }

   private void m_36377_() {
      this.f_36102_ = this.f_36105_;
      this.f_36103_ = this.f_36106_;
      this.f_36104_ = this.f_36075_;
      double d0 = this.m_20185_() - this.f_36105_;
      double d1 = this.m_20186_() - this.f_36106_;
      double d2 = this.m_20189_() - this.f_36075_;
      double d3 = 10.0D;
      if (d0 > 10.0D) {
         this.f_36105_ = this.m_20185_();
         this.f_36102_ = this.f_36105_;
      }

      if (d2 > 10.0D) {
         this.f_36075_ = this.m_20189_();
         this.f_36104_ = this.f_36075_;
      }

      if (d1 > 10.0D) {
         this.f_36106_ = this.m_20186_();
         this.f_36103_ = this.f_36106_;
      }

      if (d0 < -10.0D) {
         this.f_36105_ = this.m_20185_();
         this.f_36102_ = this.f_36105_;
      }

      if (d2 < -10.0D) {
         this.f_36075_ = this.m_20189_();
         this.f_36104_ = this.f_36075_;
      }

      if (d1 < -10.0D) {
         this.f_36106_ = this.m_20186_();
         this.f_36103_ = this.f_36106_;
      }

      this.f_36105_ += d0 * 0.25D;
      this.f_36075_ += d2 * 0.25D;
      this.f_36106_ += d1 * 0.25D;
   }

   protected void m_7594_() {
      if(forcedPose != null) {
         this.m_20124_(forcedPose);
         return;
      }
      if (this.m_20175_(Pose.SWIMMING)) {
         Pose pose;
         if (this.m_21255_()) {
            pose = Pose.FALL_FLYING;
         } else if (this.m_5803_()) {
            pose = Pose.SLEEPING;
         } else if (this.m_6069_()) {
            pose = Pose.SWIMMING;
         } else if (this.m_21209_()) {
            pose = Pose.SPIN_ATTACK;
         } else if (this.m_6144_() && !this.f_36077_.f_35935_) {
            pose = Pose.CROUCHING;
         } else {
            pose = Pose.STANDING;
         }

         Pose pose1;
         if (!this.m_5833_() && !this.m_20159_() && !this.m_20175_(pose)) {
            if (this.m_20175_(Pose.CROUCHING)) {
               pose1 = Pose.CROUCHING;
            } else {
               pose1 = Pose.SWIMMING;
            }
         } else {
            pose1 = pose;
         }

         this.m_20124_(pose1);
      }
   }

   public int m_6078_() {
      return this.f_36077_.f_35934_ ? 1 : 80;
   }

   protected SoundEvent m_5501_() {
      return SoundEvents.f_12279_;
   }

   protected SoundEvent m_5509_() {
      return SoundEvents.f_12277_;
   }

   protected SoundEvent m_5508_() {
      return SoundEvents.f_12278_;
   }

   public int m_6045_() {
      return 10;
   }

   public void m_5496_(SoundEvent p_36137_, float p_36138_, float p_36139_) {
      this.f_19853_.m_6263_(this, this.m_20185_(), this.m_20186_(), this.m_20189_(), p_36137_, this.m_5720_(), p_36138_, p_36139_);
   }

   public void m_6330_(SoundEvent p_36140_, SoundSource p_36141_, float p_36142_, float p_36143_) {
   }

   public SoundSource m_5720_() {
      return SoundSource.PLAYERS;
   }

   protected int m_6101_() {
      return 20;
   }

   public void m_7822_(byte p_36120_) {
      if (p_36120_ == 9) {
         this.m_8095_();
      } else if (p_36120_ == 23) {
         this.f_36085_ = false;
      } else if (p_36120_ == 22) {
         this.f_36085_ = true;
      } else if (p_36120_ == 43) {
         this.m_36208_(ParticleTypes.f_123796_);
      } else {
         super.m_7822_(p_36120_);
      }

   }

   private void m_36208_(ParticleOptions p_36209_) {
      for(int i = 0; i < 5; ++i) {
         double d0 = this.f_19796_.nextGaussian() * 0.02D;
         double d1 = this.f_19796_.nextGaussian() * 0.02D;
         double d2 = this.f_19796_.nextGaussian() * 0.02D;
         this.f_19853_.m_7106_(p_36209_, this.m_20208_(1.0D), this.m_20187_() + 1.0D, this.m_20262_(1.0D), d0, d1, d2);
      }

   }

   public void m_6915_() {
      this.f_36096_ = this.f_36095_;
   }

   public void m_6083_() {
      if (!this.f_19853_.f_46443_ && this.m_36342_() && this.m_20159_()) {
         this.m_8127_();
         this.m_20260_(false);
      } else {
         double d0 = this.m_20185_();
         double d1 = this.m_20186_();
         double d2 = this.m_20189_();
         super.m_6083_();
         this.f_36099_ = this.f_36100_;
         this.f_36100_ = 0.0F;
         this.m_36387_(this.m_20185_() - d0, this.m_20186_() - d1, this.m_20189_() - d2);
      }
   }

   protected void m_6140_() {
      super.m_6140_();
      this.m_21203_();
      this.f_20885_ = this.m_146908_();
   }

   public void m_8107_() {
      if (this.f_36098_ > 0) {
         --this.f_36098_;
      }

      if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL && this.f_19853_.m_46469_().m_46207_(GameRules.f_46139_)) {
         if (this.m_21223_() < this.m_21233_() && this.f_19797_ % 20 == 0) {
            this.m_5634_(1.0F);
         }

         if (this.f_36097_.m_38721_() && this.f_19797_ % 10 == 0) {
            this.f_36097_.m_38705_(this.f_36097_.m_38702_() + 1);
         }
      }

      this.f_36093_.m_36068_();
      this.f_36099_ = this.f_36100_;
      super.m_8107_();
      this.f_20887_ = 0.02F;
      if (this.m_20142_()) {
         this.f_20887_ = (float)((double)this.f_20887_ + 0.005999999865889549D);
      }

      this.m_7910_((float)this.m_21133_(Attributes.f_22279_));
      float f;
      if (this.f_19861_ && !this.m_21224_() && !this.m_6069_()) {
         f = Math.min(0.1F, (float)this.m_20184_().m_165924_());
      } else {
         f = 0.0F;
      }

      this.f_36100_ += (f - this.f_36100_) * 0.4F;
      if (this.m_21223_() > 0.0F && !this.m_5833_()) {
         AABB aabb;
         if (this.m_20159_() && !this.m_20202_().m_146910_()) {
            aabb = this.m_142469_().m_82367_(this.m_20202_().m_142469_()).m_82377_(1.0D, 0.0D, 1.0D);
         } else {
            aabb = this.m_142469_().m_82377_(1.0D, 0.5D, 1.0D);
         }

         List<Entity> list = this.f_19853_.m_45933_(this, aabb);
         List<Entity> list1 = Lists.newArrayList();

         for(int i = 0; i < list.size(); ++i) {
            Entity entity = list.get(i);
            if (entity.m_6095_() == EntityType.f_20570_) {
               list1.add(entity);
            } else if (!entity.m_146910_()) {
               this.m_36277_(entity);
            }
         }

         if (!list1.isEmpty()) {
            this.m_36277_(Util.m_143804_(list1, this.f_19796_));
         }
      }

      this.m_36367_(this.m_36331_());
      this.m_36367_(this.m_36332_());
      if (!this.f_19853_.f_46443_ && (this.f_19789_ > 0.5F || this.m_20069_()) || this.f_36077_.f_35935_ || this.m_5803_() || this.f_146808_) {
         this.m_36328_();
      }

   }

   private void m_36367_(@Nullable CompoundTag p_36368_) {
      if (p_36368_ != null && (!p_36368_.m_128441_("Silent") || !p_36368_.m_128471_("Silent")) && this.f_19853_.f_46441_.nextInt(200) == 0) {
         String s = p_36368_.m_128461_("id");
         EntityType.m_20632_(s).filter((p_36280_) -> {
            return p_36280_ == EntityType.f_20508_;
         }).ifPresent((p_36255_) -> {
            if (!Parrot.m_29382_(this.f_19853_, this)) {
               this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), Parrot.m_29385_(this.f_19853_, this.f_19853_.f_46441_), this.m_5720_(), 1.0F, Parrot.m_29399_(this.f_19853_.f_46441_));
            }

         });
      }

   }

   private void m_36277_(Entity p_36278_) {
      p_36278_.m_6123_(this);
   }

   public int m_36344_() {
      return this.f_19804_.m_135370_(f_36108_);
   }

   public void m_36397_(int p_36398_) {
      this.f_19804_.m_135381_(f_36108_, p_36398_);
   }

   public void m_36401_(int p_36402_) {
      int i = this.m_36344_();
      this.f_19804_.m_135381_(f_36108_, i + p_36402_);
   }

   public void m_6667_(DamageSource p_36152_) {
      if (net.minecraftforge.common.ForgeHooks.onLivingDeath(this, p_36152_)) return;
      super.m_6667_(p_36152_);
      this.m_20090_();
      if (!this.m_5833_()) {
         this.m_6668_(p_36152_);
      }

      if (p_36152_ != null) {
         this.m_20334_((double)(-Mth.m_14089_((this.f_20918_ + this.m_146908_()) * ((float)Math.PI / 180F)) * 0.1F), (double)0.1F, (double)(-Mth.m_14031_((this.f_20918_ + this.m_146908_()) * ((float)Math.PI / 180F)) * 0.1F));
      } else {
         this.m_20334_(0.0D, 0.1D, 0.0D);
      }

      this.m_36220_(Stats.f_12935_);
      this.m_7166_(Stats.f_12988_.m_12902_(Stats.f_12991_));
      this.m_7166_(Stats.f_12988_.m_12902_(Stats.f_12992_));
      this.m_20095_();
      this.m_146868_(false);
   }

   protected void m_5907_() {
      super.m_5907_();
      if (!this.f_19853_.m_46469_().m_46207_(GameRules.f_46133_)) {
         this.m_36345_();
         this.f_36093_.m_36071_();
      }

   }

   protected void m_36345_() {
      for(int i = 0; i < this.f_36093_.m_6643_(); ++i) {
         ItemStack itemstack = this.f_36093_.m_8020_(i);
         if (!itemstack.m_41619_() && EnchantmentHelper.m_44924_(itemstack)) {
            this.f_36093_.m_8016_(i);
         }
      }

   }

   protected SoundEvent m_7975_(DamageSource p_36310_) {
      if (p_36310_ == DamageSource.f_19307_) {
         return SoundEvents.f_12273_;
      } else if (p_36310_ == DamageSource.f_19312_) {
         return SoundEvents.f_12324_;
      } else if (p_36310_ == DamageSource.f_19325_) {
         return SoundEvents.f_12274_;
      } else {
         return p_36310_ == DamageSource.f_146701_ ? SoundEvents.f_144205_ : SoundEvents.f_12323_;
      }
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12322_;
   }

   @Nullable
   public ItemEntity m_36176_(ItemStack p_36177_, boolean p_36178_) {
      return net.minecraftforge.common.ForgeHooks.onPlayerTossEvent(this, p_36177_, p_36178_);
   }

   @Nullable
   public ItemEntity m_7197_(ItemStack p_36179_, boolean p_36180_, boolean p_36181_) {
      if (p_36179_.m_41619_()) {
         return null;
      } else {
         if (this.f_19853_.f_46443_) {
            this.m_6674_(InteractionHand.MAIN_HAND);
         }

         double d0 = this.m_20188_() - (double)0.3F;
         ItemEntity itementity = new ItemEntity(this.f_19853_, this.m_20185_(), d0, this.m_20189_(), p_36179_);
         itementity.m_32010_(40);
         if (p_36181_) {
            itementity.m_32052_(this.m_142081_());
         }

         if (p_36180_) {
            float f = this.f_19796_.nextFloat() * 0.5F;
            float f1 = this.f_19796_.nextFloat() * ((float)Math.PI * 2F);
            itementity.m_20334_((double)(-Mth.m_14031_(f1) * f), (double)0.2F, (double)(Mth.m_14089_(f1) * f));
         } else {
            float f7 = 0.3F;
            float f8 = Mth.m_14031_(this.m_146909_() * ((float)Math.PI / 180F));
            float f2 = Mth.m_14089_(this.m_146909_() * ((float)Math.PI / 180F));
            float f3 = Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F));
            float f4 = Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F));
            float f5 = this.f_19796_.nextFloat() * ((float)Math.PI * 2F);
            float f6 = 0.02F * this.f_19796_.nextFloat();
            itementity.m_20334_((double)(-f3 * f2 * 0.3F) + Math.cos((double)f5) * (double)f6, (double)(-f8 * 0.3F + 0.1F + (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.1F), (double)(f4 * f2 * 0.3F) + Math.sin((double)f5) * (double)f6);
         }

         return itementity;
      }
   }

   @Deprecated //Use location sensitive version below
   public float m_36281_(BlockState p_36282_) {
      return getDigSpeed(p_36282_, null);
   }

   public float getDigSpeed(BlockState p_36282_, @Nullable BlockPos pos) {
      float f = this.f_36093_.m_36020_(p_36282_);
      if (f > 1.0F) {
         int i = EnchantmentHelper.m_44926_(this);
         ItemStack itemstack = this.m_21205_();
         if (i > 0 && !itemstack.m_41619_()) {
            f += (float)(i * i + 1);
         }
      }

      if (MobEffectUtil.m_19584_(this)) {
         f *= 1.0F + (float)(MobEffectUtil.m_19586_(this) + 1) * 0.2F;
      }

      if (this.m_21023_(MobEffects.f_19599_)) {
         float f1;
         switch(this.m_21124_(MobEffects.f_19599_).m_19564_()) {
         case 0:
            f1 = 0.3F;
            break;
         case 1:
            f1 = 0.09F;
            break;
         case 2:
            f1 = 0.0027F;
            break;
         case 3:
         default:
            f1 = 8.1E-4F;
         }

         f *= f1;
      }

      if (this.m_19941_(FluidTags.f_13131_) && !EnchantmentHelper.m_44934_(this)) {
         f /= 5.0F;
      }

      if (!this.f_19861_) {
         f /= 5.0F;
      }

      f = net.minecraftforge.event.ForgeEventFactory.getBreakSpeed(this, p_36282_, f, pos);
      return f;
   }

   public boolean m_36298_(BlockState p_36299_) {
      return net.minecraftforge.event.ForgeEventFactory.doPlayerHarvestCheck(this, p_36299_, !p_36299_.m_60834_() || this.f_36093_.m_36056_().m_41735_(p_36299_));
   }

   public void m_7378_(CompoundTag p_36215_) {
      super.m_7378_(p_36215_);
      this.m_20084_(m_36198_(this.f_36084_));
      ListTag listtag = p_36215_.m_128437_("Inventory", 10);
      this.f_36093_.m_36035_(listtag);
      this.f_36093_.f_35977_ = p_36215_.m_128451_("SelectedItemSlot");
      this.f_36110_ = p_36215_.m_128448_("SleepTimer");
      this.f_36080_ = p_36215_.m_128457_("XpP");
      this.f_36078_ = p_36215_.m_128451_("XpLevel");
      this.f_36079_ = p_36215_.m_128451_("XpTotal");
      this.f_36081_ = p_36215_.m_128451_("XpSeed");
      if (this.f_36081_ == 0) {
         this.f_36081_ = this.f_19796_.nextInt();
      }

      this.m_36397_(p_36215_.m_128451_("Score"));
      this.f_36097_.m_38715_(p_36215_);
      this.f_36077_.m_35950_(p_36215_);
      this.m_21051_(Attributes.f_22279_).m_22100_((double)this.f_36077_.m_35947_());
      if (p_36215_.m_128425_("EnderItems", 9)) {
         this.f_36094_.m_7797_(p_36215_.m_128437_("EnderItems", 10));
      }

      if (p_36215_.m_128425_("ShoulderEntityLeft", 10)) {
         this.m_36362_(p_36215_.m_128469_("ShoulderEntityLeft"));
      }

      if (p_36215_.m_128425_("ShoulderEntityRight", 10)) {
         this.m_36364_(p_36215_.m_128469_("ShoulderEntityRight"));
      }

   }

   public void m_7380_(CompoundTag p_36265_) {
      super.m_7380_(p_36265_);
      p_36265_.m_128405_("DataVersion", SharedConstants.m_136187_().getWorldVersion());
      p_36265_.m_128365_("Inventory", this.f_36093_.m_36026_(new ListTag()));
      p_36265_.m_128405_("SelectedItemSlot", this.f_36093_.f_35977_);
      p_36265_.m_128376_("SleepTimer", (short)this.f_36110_);
      p_36265_.m_128350_("XpP", this.f_36080_);
      p_36265_.m_128405_("XpLevel", this.f_36078_);
      p_36265_.m_128405_("XpTotal", this.f_36079_);
      p_36265_.m_128405_("XpSeed", this.f_36081_);
      p_36265_.m_128405_("Score", this.m_36344_());
      this.f_36097_.m_38719_(p_36265_);
      this.f_36077_.m_35945_(p_36265_);
      p_36265_.m_128365_("EnderItems", this.f_36094_.m_7927_());
      if (!this.m_36331_().m_128456_()) {
         p_36265_.m_128365_("ShoulderEntityLeft", this.m_36331_());
      }

      if (!this.m_36332_().m_128456_()) {
         p_36265_.m_128365_("ShoulderEntityRight", this.m_36332_());
      }

   }

   public boolean m_6673_(DamageSource p_36249_) {
      if (super.m_6673_(p_36249_)) {
         return true;
      } else if (p_36249_ == DamageSource.f_19312_) {
         return !this.f_19853_.m_46469_().m_46207_(GameRules.f_46121_);
      } else if (p_36249_.m_146707_()) {
         return !this.f_19853_.m_46469_().m_46207_(GameRules.f_46122_);
      } else if (p_36249_.m_19384_()) {
         return !this.f_19853_.m_46469_().m_46207_(GameRules.f_46123_);
      } else if (p_36249_ == DamageSource.f_146701_) {
         return !this.f_19853_.m_46469_().m_46207_(GameRules.f_151485_);
      } else {
         return false;
      }
   }

   public boolean m_6469_(DamageSource p_36154_, float p_36155_) {
      if (!net.minecraftforge.common.ForgeHooks.onPlayerAttack(this, p_36154_, p_36155_)) return false;
      if (this.m_6673_(p_36154_)) {
         return false;
      } else if (this.f_36077_.f_35934_ && !p_36154_.m_19378_()) {
         return false;
      } else {
         this.f_20891_ = 0;
         if (this.m_21224_()) {
            return false;
         } else {
            this.m_36328_();
            if (p_36154_.m_7986_()) {
               if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL) {
                  p_36155_ = 0.0F;
               }

               if (this.f_19853_.m_46791_() == Difficulty.EASY) {
                  p_36155_ = Math.min(p_36155_ / 2.0F + 1.0F, p_36155_);
               }

               if (this.f_19853_.m_46791_() == Difficulty.HARD) {
                  p_36155_ = p_36155_ * 3.0F / 2.0F;
               }
            }

            return p_36155_ == 0.0F ? false : super.m_6469_(p_36154_, p_36155_);
         }
      }
   }

   protected void m_6728_(LivingEntity p_36295_) {
      super.m_6728_(p_36295_);
      if (p_36295_.m_21205_().canDisableShield(this.f_20935_, this, p_36295_)) {
         this.m_36384_(true);
      }

   }

   public boolean m_142066_() {
      return !this.m_150110_().f_35934_ && super.m_142066_();
   }

   public boolean m_7099_(Player p_36169_) {
      Team team = this.m_5647_();
      Team team1 = p_36169_.m_5647_();
      if (team == null) {
         return true;
      } else {
         return !team.m_83536_(team1) ? true : team.m_6260_();
      }
   }

   protected void m_6472_(DamageSource p_36251_, float p_36252_) {
      this.f_36093_.m_150072_(p_36251_, p_36252_, Inventory.f_150068_);
   }

   protected void m_142642_(DamageSource p_150103_, float p_150104_) {
      this.f_36093_.m_150072_(p_150103_, p_150104_, Inventory.f_150069_);
   }

   protected void m_7909_(float p_36383_) {
      if (this.f_20935_.canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK)) {
         if (!this.f_19853_.f_46443_) {
            this.m_36246_(Stats.f_12982_.m_12902_(this.f_20935_.m_41720_()));
         }

         if (p_36383_ >= 3.0F) {
            int i = 1 + Mth.m_14143_(p_36383_);
            InteractionHand interactionhand = this.m_7655_();
            this.f_20935_.m_41622_(i, this, (p_36149_) -> {
               p_36149_.m_21190_(interactionhand);
               net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(this, this.f_20935_, interactionhand);
            });
            if (this.f_20935_.m_41619_()) {
               if (interactionhand == InteractionHand.MAIN_HAND) {
                  this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
               } else {
                  this.m_8061_(EquipmentSlot.OFFHAND, ItemStack.f_41583_);
               }

               this.f_20935_ = ItemStack.f_41583_;
               this.m_5496_(SoundEvents.f_12347_, 0.8F, 0.8F + this.f_19853_.f_46441_.nextFloat() * 0.4F);
            }
         }

      }
   }

   protected void m_6475_(DamageSource p_36312_, float p_36313_) {
      if (!this.m_6673_(p_36312_)) {
         p_36313_ = net.minecraftforge.common.ForgeHooks.onLivingHurt(this, p_36312_, p_36313_);
         if (p_36313_ <= 0) return;
         p_36313_ = this.m_21161_(p_36312_, p_36313_);
         p_36313_ = this.m_6515_(p_36312_, p_36313_);
         float f2 = Math.max(p_36313_ - this.m_6103_(), 0.0F);
         this.m_7911_(this.m_6103_() - (p_36313_ - f2));
         f2 = net.minecraftforge.common.ForgeHooks.onLivingDamage(this, p_36312_, f2);
         float f = p_36313_ - f2;
         if (f > 0.0F && f < 3.4028235E37F) {
            this.m_36222_(Stats.f_12933_, Math.round(f * 10.0F));
         }

         if (f2 != 0.0F) {
            this.m_36399_(p_36312_.m_19377_());
            float f1 = this.m_21223_();
            this.m_21231_().m_19289_(p_36312_, f1, f2);
            this.m_21153_(f1 - f2); // Forge: moved to fix MC-121048
            if (f2 < 3.4028235E37F) {
               this.m_36222_(Stats.f_12931_, Math.round(f2 * 10.0F));
            }

         }
      }
   }

   protected boolean m_6046_() {
      return !this.f_36077_.f_35935_ && super.m_6046_();
   }

   public void m_7739_(SignBlockEntity p_36193_) {
   }

   public void m_7907_(BaseCommandBlock p_36182_) {
   }

   public void m_7698_(CommandBlockEntity p_36191_) {
   }

   public void m_5966_(StructureBlockEntity p_36194_) {
   }

   public void m_7569_(JigsawBlockEntity p_36192_) {
   }

   public void m_6658_(AbstractHorse p_36167_, Container p_36168_) {
   }

   public OptionalInt m_5893_(@Nullable MenuProvider p_36150_) {
      return OptionalInt.empty();
   }

   public void m_7662_(int p_36121_, MerchantOffers p_36122_, int p_36123_, int p_36124_, boolean p_36125_, boolean p_36126_) {
   }

   public void m_6986_(ItemStack p_36174_, InteractionHand p_36175_) {
   }

   public InteractionResult m_36157_(Entity p_36158_, InteractionHand p_36159_) {
      if (this.m_5833_()) {
         if (p_36158_ instanceof MenuProvider) {
            this.m_5893_((MenuProvider)p_36158_);
         }

         return InteractionResult.PASS;
      } else {
         InteractionResult cancelResult = net.minecraftforge.common.ForgeHooks.onInteractEntity(this, p_36158_, p_36159_);
         if (cancelResult != null) return cancelResult;
         ItemStack itemstack = this.m_21120_(p_36159_);
         ItemStack itemstack1 = itemstack.m_41777_();
         InteractionResult interactionresult = p_36158_.m_6096_(this, p_36159_);
         if (interactionresult.m_19077_()) {
            if (this.f_36077_.f_35937_ && itemstack == this.m_21120_(p_36159_) && itemstack.m_41613_() < itemstack1.m_41613_()) {
               itemstack.m_41764_(itemstack1.m_41613_());
            }

            if (!this.f_36077_.f_35937_ && itemstack.m_41619_()) {
               net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(this, itemstack1, p_36159_);
            }
            return interactionresult;
         } else {
            if (!itemstack.m_41619_() && p_36158_ instanceof LivingEntity) {
               if (this.f_36077_.f_35937_) {
                  itemstack = itemstack1;
               }

               InteractionResult interactionresult1 = itemstack.m_41647_(this, (LivingEntity)p_36158_, p_36159_);
               if (interactionresult1.m_19077_()) {
                  if (itemstack.m_41619_() && !this.f_36077_.f_35937_) {
                     net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(this, itemstack1, p_36159_);
                     this.m_21008_(p_36159_, ItemStack.f_41583_);
                  }

                  return interactionresult1;
               }
            }

            return InteractionResult.PASS;
         }
      }
   }

   public double m_6049_() {
      return -0.35D;
   }

   public void m_6038_() {
      super.m_6038_();
      this.f_19851_ = 0;
   }

   protected boolean m_6107_() {
      return super.m_6107_() || this.m_5803_();
   }

   public boolean m_6129_() {
      return !this.f_36077_.f_35935_;
   }

   protected Vec3 m_5763_(Vec3 p_36201_, MoverType p_36202_) {
      if (!this.f_36077_.f_35935_ && (p_36202_ == MoverType.SELF || p_36202_ == MoverType.PLAYER) && this.m_36343_() && this.m_36386_()) {
         double d0 = p_36201_.f_82479_;
         double d1 = p_36201_.f_82481_;
         double d2 = 0.05D;

         while(d0 != 0.0D && this.f_19853_.m_45756_(this, this.m_142469_().m_82386_(d0, (double)(-this.f_19793_), 0.0D))) {
            if (d0 < 0.05D && d0 >= -0.05D) {
               d0 = 0.0D;
            } else if (d0 > 0.0D) {
               d0 -= 0.05D;
            } else {
               d0 += 0.05D;
            }
         }

         while(d1 != 0.0D && this.f_19853_.m_45756_(this, this.m_142469_().m_82386_(0.0D, (double)(-this.f_19793_), d1))) {
            if (d1 < 0.05D && d1 >= -0.05D) {
               d1 = 0.0D;
            } else if (d1 > 0.0D) {
               d1 -= 0.05D;
            } else {
               d1 += 0.05D;
            }
         }

         while(d0 != 0.0D && d1 != 0.0D && this.f_19853_.m_45756_(this, this.m_142469_().m_82386_(d0, (double)(-this.f_19793_), d1))) {
            if (d0 < 0.05D && d0 >= -0.05D) {
               d0 = 0.0D;
            } else if (d0 > 0.0D) {
               d0 -= 0.05D;
            } else {
               d0 += 0.05D;
            }

            if (d1 < 0.05D && d1 >= -0.05D) {
               d1 = 0.0D;
            } else if (d1 > 0.0D) {
               d1 -= 0.05D;
            } else {
               d1 += 0.05D;
            }
         }

         p_36201_ = new Vec3(d0, p_36201_.f_82480_, d1);
      }

      return p_36201_;
   }

   private boolean m_36386_() {
      return this.f_19861_ || this.f_19789_ < this.f_19793_ && !this.f_19853_.m_45756_(this, this.m_142469_().m_82386_(0.0D, (double)(this.f_19789_ - this.f_19793_), 0.0D));
   }

   public void m_5706_(Entity p_36347_) {
      if (!net.minecraftforge.common.ForgeHooks.onPlayerAttackTarget(this, p_36347_)) return;
      if (p_36347_.m_6097_()) {
         if (!p_36347_.m_7313_(this)) {
            float f = (float)this.m_21133_(Attributes.f_22281_);
            float f1;
            if (p_36347_ instanceof LivingEntity) {
               f1 = EnchantmentHelper.m_44833_(this.m_21205_(), ((LivingEntity)p_36347_).m_6336_());
            } else {
               f1 = EnchantmentHelper.m_44833_(this.m_21205_(), MobType.f_21640_);
            }

            float f2 = this.m_36403_(0.5F);
            f = f * (0.2F + f2 * f2 * 0.8F);
            f1 = f1 * f2;
            this.m_36334_();
            if (f > 0.0F || f1 > 0.0F) {
               boolean flag = f2 > 0.9F;
               boolean flag1 = false;
               float i = (float)this.m_21133_(Attributes.f_22282_); // Forge: Initialize this value to the attack knockback attribute of the player, which is by default 0
               i = i + EnchantmentHelper.m_44894_(this);
               if (this.m_20142_() && flag) {
                  this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12314_, this.m_5720_(), 1.0F, 1.0F);
                  ++i;
                  flag1 = true;
               }

               boolean flag2 = flag && this.f_19789_ > 0.0F && !this.f_19861_ && !this.m_6147_() && !this.m_20069_() && !this.m_21023_(MobEffects.f_19610_) && !this.m_20159_() && p_36347_ instanceof LivingEntity;
               flag2 = flag2 && !this.m_20142_();
               net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(this, p_36347_, flag2, flag2 ? 1.5F : 1.0F);
               flag2 = hitResult != null;
               if (flag2) {
                  f *= hitResult.getDamageModifier();
               }

               f = f + f1;
               boolean flag3 = false;
               double d0 = (double)(this.f_19787_ - this.f_19867_);
               if (flag && !flag2 && !flag1 && this.f_19861_ && d0 < (double)this.m_6113_()) {
                  ItemStack itemstack = this.m_21120_(InteractionHand.MAIN_HAND);
                  flag3 = itemstack.canPerformAction(net.minecraftforge.common.ToolActions.SWORD_SWEEP);
               }

               float f4 = 0.0F;
               boolean flag4 = false;
               int j = EnchantmentHelper.m_44914_(this);
               if (p_36347_ instanceof LivingEntity) {
                  f4 = ((LivingEntity)p_36347_).m_21223_();
                  if (j > 0 && !p_36347_.m_6060_()) {
                     flag4 = true;
                     p_36347_.m_20254_(1);
                  }
               }

               Vec3 vec3 = p_36347_.m_20184_();
               boolean flag5 = p_36347_.m_6469_(DamageSource.m_19344_(this), f);
               if (flag5) {
                  if (i > 0) {
                     if (p_36347_ instanceof LivingEntity) {
                        ((LivingEntity)p_36347_).m_147240_((double)((float)i * 0.5F), (double)Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F)), (double)(-Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F))));
                     } else {
                        p_36347_.m_5997_((double)(-Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F)) * (float)i * 0.5F), 0.1D, (double)(Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F)) * (float)i * 0.5F));
                     }

                     this.m_20256_(this.m_20184_().m_82542_(0.6D, 1.0D, 0.6D));
                     this.m_6858_(false);
                  }

                  if (flag3) {
                     float f3 = 1.0F + EnchantmentHelper.m_44821_(this) * f;

                     for(LivingEntity livingentity : this.f_19853_.m_45976_(LivingEntity.class, this.m_21120_(InteractionHand.MAIN_HAND).getSweepHitBox(this, p_36347_))) {
                        if (livingentity != this && livingentity != p_36347_ && !this.m_7307_(livingentity) && (!(livingentity instanceof ArmorStand) || !((ArmorStand)livingentity).m_31677_()) && this.m_20280_(livingentity) < 9.0D) {
                           livingentity.m_147240_((double)0.4F, (double)Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F)), (double)(-Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F))));
                           livingentity.m_6469_(DamageSource.m_19344_(this), f3);
                        }
                     }

                     this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12317_, this.m_5720_(), 1.0F, 1.0F);
                     this.m_36346_();
                  }

                  if (p_36347_ instanceof ServerPlayer && p_36347_.f_19864_) {
                     ((ServerPlayer)p_36347_).f_8906_.m_141995_(new ClientboundSetEntityMotionPacket(p_36347_));
                     p_36347_.f_19864_ = false;
                     p_36347_.m_20256_(vec3);
                  }

                  if (flag2) {
                     this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12313_, this.m_5720_(), 1.0F, 1.0F);
                     this.m_5704_(p_36347_);
                  }

                  if (!flag2 && !flag3) {
                     if (flag) {
                        this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12316_, this.m_5720_(), 1.0F, 1.0F);
                     } else {
                        this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12318_, this.m_5720_(), 1.0F, 1.0F);
                     }
                  }

                  if (f1 > 0.0F) {
                     this.m_5700_(p_36347_);
                  }

                  this.m_21335_(p_36347_);
                  if (p_36347_ instanceof LivingEntity) {
                     EnchantmentHelper.m_44823_((LivingEntity)p_36347_, this);
                  }

                  EnchantmentHelper.m_44896_(this, p_36347_);
                  ItemStack itemstack1 = this.m_21205_();
                  Entity entity = p_36347_;
                  if (p_36347_ instanceof net.minecraftforge.entity.PartEntity) {
                     entity = ((net.minecraftforge.entity.PartEntity<?>) p_36347_).getParent();
                  }

                  if (!this.f_19853_.f_46443_ && !itemstack1.m_41619_() && entity instanceof LivingEntity) {
                     ItemStack copy = itemstack1.m_41777_();
                     itemstack1.m_41640_((LivingEntity)entity, this);
                     if (itemstack1.m_41619_()) {
                        net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(this, copy, InteractionHand.MAIN_HAND);
                        this.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
                     }
                  }

                  if (p_36347_ instanceof LivingEntity) {
                     float f5 = f4 - ((LivingEntity)p_36347_).m_21223_();
                     this.m_36222_(Stats.f_12928_, Math.round(f5 * 10.0F));
                     if (j > 0) {
                        p_36347_.m_20254_(j * 4);
                     }

                     if (this.f_19853_ instanceof ServerLevel && f5 > 2.0F) {
                        int k = (int)((double)f5 * 0.5D);
                        ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123798_, p_36347_.m_20185_(), p_36347_.m_20227_(0.5D), p_36347_.m_20189_(), k, 0.1D, 0.0D, 0.1D, 0.2D);
                     }
                  }

                  this.m_36399_(0.1F);
               } else {
                  this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12315_, this.m_5720_(), 1.0F, 1.0F);
                  if (flag4) {
                     p_36347_.m_20095_();
                  }
               }
            }

         }
      }
   }

   protected void m_6727_(LivingEntity p_36355_) {
      this.m_5706_(p_36355_);
   }

   public void m_36384_(boolean p_36385_) {
      float f = 0.25F + (float)EnchantmentHelper.m_44926_(this) * 0.05F;
      if (p_36385_) {
         f += 0.75F;
      }

      if (this.f_19796_.nextFloat() < f) {
         this.m_36335_().m_41524_(this.m_21211_().m_41720_(), 100);
         this.m_5810_();
         this.f_19853_.m_7605_(this, (byte)30);
      }

   }

   public void m_5704_(Entity p_36156_) {
   }

   public void m_5700_(Entity p_36253_) {
   }

   public void m_36346_() {
      double d0 = (double)(-Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F)));
      double d1 = (double)Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F));
      if (this.f_19853_ instanceof ServerLevel) {
         ((ServerLevel)this.f_19853_).m_8767_(ParticleTypes.f_123766_, this.m_20185_() + d0, this.m_20227_(0.5D), this.m_20189_() + d1, 0, d0, 0.0D, d1, 0.0D);
      }

   }

   public void m_7583_() {
   }

   public void m_142687_(Entity.RemovalReason p_150097_) {
      super.m_142687_(p_150097_);
      this.f_36095_.m_6877_(this);
      if (this.f_36096_ != null) {
         this.f_36096_.m_6877_(this);
      }

   }

   public boolean m_7578_() {
      return false;
   }

   public GameProfile m_36316_() {
      return this.f_36084_;
   }

   public Inventory m_150109_() {
      return this.f_36093_;
   }

   public Abilities m_150110_() {
      return this.f_36077_;
   }

   public void m_141945_(ItemStack p_150098_, ItemStack p_150099_, ClickAction p_150100_) {
   }

   public Either<Player.BedSleepingProblem, Unit> m_7720_(BlockPos p_36203_) {
      this.m_5802_(p_36203_);
      this.f_36110_ = 0;
      return Either.right(Unit.INSTANCE);
   }

   public void m_6145_(boolean p_36226_, boolean p_36227_) {
      net.minecraftforge.event.ForgeEventFactory.onPlayerWakeup(this, p_36226_, p_36227_);
      super.m_5796_();
      if (this.f_19853_ instanceof ServerLevel && p_36227_) {
         ((ServerLevel)this.f_19853_).m_8878_();
      }

      this.f_36110_ = p_36226_ ? 0 : 100;
   }

   public void m_5796_() {
      this.m_6145_(true, true);
   }

   public static Optional<Vec3> m_36130_(ServerLevel p_36131_, BlockPos p_36132_, float p_36133_, boolean p_36134_, boolean p_36135_) {
      BlockState blockstate = p_36131_.m_8055_(p_36132_);
      Block block = blockstate.m_60734_();
      if (block instanceof RespawnAnchorBlock && blockstate.m_61143_(RespawnAnchorBlock.f_55833_) > 0 && RespawnAnchorBlock.m_55850_(p_36131_)) {
         Optional<Vec3> optional = RespawnAnchorBlock.m_55839_(EntityType.f_20532_, p_36131_, p_36132_);
         if (!p_36135_ && optional.isPresent()) {
            p_36131_.m_7731_(p_36132_, blockstate.m_61124_(RespawnAnchorBlock.f_55833_, Integer.valueOf(blockstate.m_61143_(RespawnAnchorBlock.f_55833_) - 1)), 3);
         }

         return optional;
      } else if (block instanceof BedBlock && BedBlock.m_49488_(p_36131_)) {
         return BedBlock.m_49458_(EntityType.f_20532_, p_36131_, p_36132_, p_36133_);
      } else if (!p_36134_) {
         return blockstate.getRespawnPosition(EntityType.f_20532_, p_36131_, p_36132_, p_36133_, null);
      } else {
         boolean flag = block.m_5568_();
         boolean flag1 = p_36131_.m_8055_(p_36132_.m_7494_()).m_60734_().m_5568_();
         return flag && flag1 ? Optional.of(new Vec3((double)p_36132_.m_123341_() + 0.5D, (double)p_36132_.m_123342_() + 0.1D, (double)p_36132_.m_123343_() + 0.5D)) : Optional.empty();
      }
   }

   public boolean m_36317_() {
      return this.m_5803_() && this.f_36110_ >= 100;
   }

   public int m_36318_() {
      return this.f_36110_;
   }

   public void m_5661_(Component p_36216_, boolean p_36217_) {
   }

   public void m_36220_(ResourceLocation p_36221_) {
      this.m_36246_(Stats.f_12988_.m_12902_(p_36221_));
   }

   public void m_36222_(ResourceLocation p_36223_, int p_36224_) {
      this.m_6278_(Stats.f_12988_.m_12902_(p_36223_), p_36224_);
   }

   public void m_36246_(Stat<?> p_36247_) {
      this.m_6278_(p_36247_, 1);
   }

   public void m_6278_(Stat<?> p_36145_, int p_36146_) {
   }

   public void m_7166_(Stat<?> p_36144_) {
   }

   public int m_7281_(Collection<Recipe<?>> p_36213_) {
      return 0;
   }

   public void m_7902_(ResourceLocation[] p_36228_) {
   }

   public int m_7279_(Collection<Recipe<?>> p_36263_) {
      return 0;
   }

   public void m_6135_() {
      super.m_6135_();
      this.m_36220_(Stats.f_12926_);
      if (this.m_20142_()) {
         this.m_36399_(0.2F);
      } else {
         this.m_36399_(0.05F);
      }

   }

   public void m_7023_(Vec3 p_36359_) {
      double d0 = this.m_20185_();
      double d1 = this.m_20186_();
      double d2 = this.m_20189_();
      if (this.m_6069_() && !this.m_20159_()) {
         double d3 = this.m_20154_().f_82480_;
         double d4 = d3 < -0.2D ? 0.085D : 0.06D;
         if (d3 <= 0.0D || this.f_20899_ || !this.f_19853_.m_8055_(new BlockPos(this.m_20185_(), this.m_20186_() + 1.0D - 0.1D, this.m_20189_())).m_60819_().m_76178_()) {
            Vec3 vec31 = this.m_20184_();
            this.m_20256_(vec31.m_82520_(0.0D, (d3 - vec31.f_82480_) * d4, 0.0D));
         }
      }

      if (this.f_36077_.f_35935_ && !this.m_20159_()) {
         double d5 = this.m_20184_().f_82480_;
         float f = this.f_20887_;
         this.f_20887_ = this.f_36077_.m_35942_() * (float)(this.m_20142_() ? 2 : 1);
         super.m_7023_(p_36359_);
         Vec3 vec3 = this.m_20184_();
         this.m_20334_(vec3.f_82479_, d5 * 0.6D, vec3.f_82481_);
         this.f_20887_ = f;
         this.f_19789_ = 0.0F;
         this.m_20115_(7, false);
      } else {
         super.m_7023_(p_36359_);
      }

      this.m_36378_(this.m_20185_() - d0, this.m_20186_() - d1, this.m_20189_() - d2);
   }

   public void m_5844_() {
      if (this.f_36077_.f_35935_) {
         this.m_20282_(false);
      } else {
         super.m_5844_();
      }

   }

   protected boolean m_36350_(BlockPos p_36351_) {
      return !this.f_19853_.m_8055_(p_36351_).m_60828_(this.f_19853_, p_36351_);
   }

   public float m_6113_() {
      return (float)this.m_21133_(Attributes.f_22279_);
   }

   public void m_36378_(double p_36379_, double p_36380_, double p_36381_) {
      if (!this.m_20159_()) {
         if (this.m_6069_()) {
            int i = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36380_ * p_36380_ + p_36381_ * p_36381_) * 100.0F);
            if (i > 0) {
               this.m_36222_(Stats.f_12924_, i);
               this.m_36399_(0.01F * (float)i * 0.01F);
            }
         } else if (this.m_19941_(FluidTags.f_13131_)) {
            int j = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36380_ * p_36380_ + p_36381_ * p_36381_) * 100.0F);
            if (j > 0) {
               this.m_36222_(Stats.f_13001_, j);
               this.m_36399_(0.01F * (float)j * 0.01F);
            }
         } else if (this.m_20069_()) {
            int k = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36381_ * p_36381_) * 100.0F);
            if (k > 0) {
               this.m_36222_(Stats.f_12997_, k);
               this.m_36399_(0.01F * (float)k * 0.01F);
            }
         } else if (this.m_6147_()) {
            if (p_36380_ > 0.0D) {
               this.m_36222_(Stats.f_12999_, (int)Math.round(p_36380_ * 100.0D));
            }
         } else if (this.f_19861_) {
            int l = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36381_ * p_36381_) * 100.0F);
            if (l > 0) {
               if (this.m_20142_()) {
                  this.m_36222_(Stats.f_12996_, l);
                  this.m_36399_(0.1F * (float)l * 0.01F);
               } else if (this.m_6047_()) {
                  this.m_36222_(Stats.f_12995_, l);
                  this.m_36399_(0.0F * (float)l * 0.01F);
               } else {
                  this.m_36222_(Stats.f_12994_, l);
                  this.m_36399_(0.0F * (float)l * 0.01F);
               }
            }
         } else if (this.m_21255_()) {
            int i1 = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36380_ * p_36380_ + p_36381_ * p_36381_) * 100.0F);
            this.m_36222_(Stats.f_12923_, i1);
         } else {
            int j1 = Math.round((float)Math.sqrt(p_36379_ * p_36379_ + p_36381_ * p_36381_) * 100.0F);
            if (j1 > 25) {
               this.m_36222_(Stats.f_13000_, j1);
            }
         }

      }
   }

   private void m_36387_(double p_36388_, double p_36389_, double p_36390_) {
      if (this.m_20159_()) {
         int i = Math.round((float)Math.sqrt(p_36388_ * p_36388_ + p_36389_ * p_36389_ + p_36390_ * p_36390_) * 100.0F);
         if (i > 0) {
            Entity entity = this.m_20202_();
            if (entity instanceof AbstractMinecart) {
               this.m_36222_(Stats.f_13002_, i);
            } else if (entity instanceof Boat) {
               this.m_36222_(Stats.f_13003_, i);
            } else if (entity instanceof Pig) {
               this.m_36222_(Stats.f_13004_, i);
            } else if (entity instanceof AbstractHorse) {
               this.m_36222_(Stats.f_13005_, i);
            } else if (entity instanceof Strider) {
               this.m_36222_(Stats.f_12925_, i);
            }
         }
      }

   }

   public boolean m_142535_(float p_150093_, float p_150094_, DamageSource p_150095_) {
      if (this.f_36077_.f_35936_) {
         net.minecraftforge.event.ForgeEventFactory.onPlayerFall(this, p_150093_, p_150093_);
         return false;
      } else {
         if (p_150093_ >= 2.0F) {
            this.m_36222_(Stats.f_12998_, (int)Math.round((double)p_150093_ * 100.0D));
         }

         return super.m_142535_(p_150093_, p_150094_, p_150095_);
      }
   }

   public boolean m_36319_() {
      if (!this.f_19861_ && !this.m_21255_() && !this.m_20069_() && !this.m_21023_(MobEffects.f_19620_)) {
         ItemStack itemstack = this.m_6844_(EquipmentSlot.CHEST);
         if (itemstack.canElytraFly(this)) {
            this.m_36320_();
            return true;
         }
      }

      return false;
   }

   public void m_36320_() {
      this.m_20115_(7, true);
   }

   public void m_36321_() {
      this.m_20115_(7, true);
      this.m_20115_(7, false);
   }

   protected void m_5841_() {
      if (!this.m_5833_()) {
         super.m_5841_();
      }

   }

   protected SoundEvent m_5896_(int p_36376_) {
      return p_36376_ > 4 ? SoundEvents.f_12319_ : SoundEvents.f_12276_;
   }

   public void m_5837_(ServerLevel p_36128_, LivingEntity p_36129_) {
      this.m_36246_(Stats.f_12986_.m_12902_(p_36129_.m_6095_()));
   }

   public void m_7601_(BlockState p_36196_, Vec3 p_36197_) {
      if (!this.f_36077_.f_35935_) {
         super.m_7601_(p_36196_, p_36197_);
      }

   }

   public void m_6756_(int p_36291_) {
      net.minecraftforge.event.entity.player.PlayerXpEvent.XpChange event = new net.minecraftforge.event.entity.player.PlayerXpEvent.XpChange(this, p_36291_);
      if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;
      p_36291_ = event.getAmount();

      this.m_36401_(p_36291_);
      this.f_36080_ += (float)p_36291_ / (float)this.m_36323_();
      this.f_36079_ = Mth.m_14045_(this.f_36079_ + p_36291_, 0, Integer.MAX_VALUE);

      while(this.f_36080_ < 0.0F) {
         float f = this.f_36080_ * (float)this.m_36323_();
         if (this.f_36078_ > 0) {
            this.m_6749_(-1);
            this.f_36080_ = 1.0F + f / (float)this.m_36323_();
         } else {
            this.m_6749_(-1);
            this.f_36080_ = 0.0F;
         }
      }

      while(this.f_36080_ >= 1.0F) {
         this.f_36080_ = (this.f_36080_ - 1.0F) * (float)this.m_36323_();
         this.m_6749_(1);
         this.f_36080_ /= (float)this.m_36323_();
      }

   }

   public int m_36322_() {
      return this.f_36081_;
   }

   public void m_7408_(ItemStack p_36172_, int p_36173_) {
      m_6749_(-p_36173_);
      if (this.f_36078_ < 0) {
         this.f_36078_ = 0;
         this.f_36080_ = 0.0F;
         this.f_36079_ = 0;
      }

      this.f_36081_ = this.f_19796_.nextInt();
   }

   public void m_6749_(int p_36276_) {
      net.minecraftforge.event.entity.player.PlayerXpEvent.LevelChange event = new net.minecraftforge.event.entity.player.PlayerXpEvent.LevelChange(this, p_36276_);
      if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;
      p_36276_ = event.getLevels();

      this.f_36078_ += p_36276_;
      if (this.f_36078_ < 0) {
         this.f_36078_ = 0;
         this.f_36080_ = 0.0F;
         this.f_36079_ = 0;
      }

      if (p_36276_ > 0 && this.f_36078_ % 5 == 0 && (float)this.f_36111_ < (float)this.f_19797_ - 100.0F) {
         float f = this.f_36078_ > 30 ? 1.0F : (float)this.f_36078_ / 30.0F;
         this.f_19853_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12275_, this.m_5720_(), f * 0.75F, 1.0F);
         this.f_36111_ = this.f_19797_;
      }

   }

   public int m_36323_() {
      if (this.f_36078_ >= 30) {
         return 112 + (this.f_36078_ - 30) * 9;
      } else {
         return this.f_36078_ >= 15 ? 37 + (this.f_36078_ - 15) * 5 : 7 + this.f_36078_ * 2;
      }
   }

   public void m_36399_(float p_36400_) {
      if (!this.f_36077_.f_35934_) {
         if (!this.f_19853_.f_46443_) {
            this.f_36097_.m_38703_(p_36400_);
         }

      }
   }

   public FoodData m_36324_() {
      return this.f_36097_;
   }

   public boolean m_36391_(boolean p_36392_) {
      return this.f_36077_.f_35934_ || p_36392_ || this.f_36097_.m_38721_();
   }

   public boolean m_36325_() {
      return this.m_21223_() > 0.0F && this.m_21223_() < this.m_21233_();
   }

   public boolean m_36326_() {
      return this.f_36077_.f_35938_;
   }

   public boolean m_36204_(BlockPos p_36205_, Direction p_36206_, ItemStack p_36207_) {
      if (this.f_36077_.f_35938_) {
         return true;
      } else {
         BlockPos blockpos = p_36205_.m_142300_(p_36206_.m_122424_());
         BlockInWorld blockinworld = new BlockInWorld(this.f_19853_, blockpos, false);
         return p_36207_.m_41723_(this.f_19853_.m_5999_(), blockinworld);
      }
   }

   protected int m_6552_(Player p_36297_) {
      if (!this.f_19853_.m_46469_().m_46207_(GameRules.f_46133_) && !this.m_5833_()) {
         int i = this.f_36078_ * 7;
         return i > 100 ? 100 : i;
      } else {
         return 0;
      }
   }

   protected boolean m_6124_() {
      return true;
   }

   public boolean m_6052_() {
      return true;
   }

   protected Entity.MovementEmission m_142319_() {
      return this.f_36077_.f_35935_ || this.f_19861_ && this.m_20163_() ? Entity.MovementEmission.NONE : Entity.MovementEmission.ALL;
   }

   public void m_6885_() {
   }

   public Component m_7755_() {
      return new TextComponent(this.f_36084_.getName());
   }

   public PlayerEnderChestContainer m_36327_() {
      return this.f_36094_;
   }

   public ItemStack m_6844_(EquipmentSlot p_36257_) {
      if (p_36257_ == EquipmentSlot.MAINHAND) {
         return this.f_36093_.m_36056_();
      } else if (p_36257_ == EquipmentSlot.OFFHAND) {
         return this.f_36093_.f_35976_.get(0);
      } else {
         return p_36257_.m_20743_() == EquipmentSlot.Type.ARMOR ? this.f_36093_.f_35975_.get(p_36257_.m_20749_()) : ItemStack.f_41583_;
      }
   }

   public void m_8061_(EquipmentSlot p_36161_, ItemStack p_36162_) {
      this.m_181122_(p_36162_);
      if (p_36161_ == EquipmentSlot.MAINHAND) {
         this.m_147218_(p_36162_);
         this.f_36093_.f_35974_.set(this.f_36093_.f_35977_, p_36162_);
      } else if (p_36161_ == EquipmentSlot.OFFHAND) {
         this.m_147218_(p_36162_);
         this.f_36093_.f_35976_.set(0, p_36162_);
      } else if (p_36161_.m_20743_() == EquipmentSlot.Type.ARMOR) {
         this.m_147218_(p_36162_);
         this.f_36093_.f_35975_.set(p_36161_.m_20749_(), p_36162_);
      }

   }

   public boolean m_36356_(ItemStack p_36357_) {
      this.m_147218_(p_36357_);
      return this.f_36093_.m_36054_(p_36357_);
   }

   public Iterable<ItemStack> m_6167_() {
      return Lists.newArrayList(this.m_21205_(), this.m_21206_());
   }

   public Iterable<ItemStack> m_6168_() {
      return this.f_36093_.f_35975_;
   }

   public boolean m_36360_(CompoundTag p_36361_) {
      if (!this.m_20159_() && this.f_19861_ && !this.m_20069_() && !this.f_146808_) {
         if (this.m_36331_().m_128456_()) {
            this.m_36362_(p_36361_);
            this.f_36109_ = this.f_19853_.m_46467_();
            return true;
         } else if (this.m_36332_().m_128456_()) {
            this.m_36364_(p_36361_);
            this.f_36109_ = this.f_19853_.m_46467_();
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   protected void m_36328_() {
      if (this.f_36109_ + 20L < this.f_19853_.m_46467_()) {
         this.m_36370_(this.m_36331_());
         this.m_36362_(new CompoundTag());
         this.m_36370_(this.m_36332_());
         this.m_36364_(new CompoundTag());
      }

   }

   private void m_36370_(CompoundTag p_36371_) {
      if (!this.f_19853_.f_46443_ && !p_36371_.m_128456_()) {
         EntityType.m_20642_(p_36371_, this.f_19853_).ifPresent((p_36293_) -> {
            if (p_36293_ instanceof TamableAnimal) {
               ((TamableAnimal)p_36293_).m_21816_(this.f_19820_);
            }

            p_36293_.m_6034_(this.m_20185_(), this.m_20186_() + (double)0.7F, this.m_20189_());
            ((ServerLevel)this.f_19853_).m_8847_(p_36293_);
         });
      }

   }

   public abstract boolean m_5833_();

   public boolean m_6069_() {
      return !this.f_36077_.f_35935_ && !this.m_5833_() && super.m_6069_();
   }

   public abstract boolean m_7500_();

   public boolean m_6063_() {
      return !this.f_36077_.f_35935_;
   }

   public Scoreboard m_36329_() {
      return this.f_19853_.m_6188_();
   }

   public Component m_5446_() {
      if (this.displayname == null) this.displayname = net.minecraftforge.event.ForgeEventFactory.getPlayerDisplayName(this, this.m_7755_());
      MutableComponent mutablecomponent = new TextComponent("");
      mutablecomponent = prefixes.stream().reduce(mutablecomponent, MutableComponent::m_7220_);
      mutablecomponent = mutablecomponent.m_7220_(PlayerTeam.m_83348_(this.m_5647_(), this.displayname));
      mutablecomponent = suffixes.stream().reduce(mutablecomponent, MutableComponent::m_7220_);
      return this.m_36218_(mutablecomponent);
   }

   private MutableComponent m_36218_(MutableComponent p_36219_) {
      String s = this.m_36316_().getName();
      return p_36219_.m_130938_((p_36212_) -> {
         return p_36212_.m_131142_(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tell " + s + " ")).m_131144_(this.m_20190_()).m_131138_(s);
      });
   }

   public String m_6302_() {
      return this.m_36316_().getName();
   }

   public float m_6431_(Pose p_36259_, EntityDimensions p_36260_) {
      switch(p_36259_) {
      case SWIMMING:
      case FALL_FLYING:
      case SPIN_ATTACK:
         return 0.4F;
      case CROUCHING:
         return 1.27F;
      default:
         return 1.62F;
      }
   }

   public void m_7911_(float p_36396_) {
      if (p_36396_ < 0.0F) {
         p_36396_ = 0.0F;
      }

      this.m_20088_().m_135381_(f_36107_, p_36396_);
   }

   public float m_6103_() {
      return this.m_20088_().m_135370_(f_36107_);
   }

   public static UUID m_36198_(GameProfile p_36199_) {
      UUID uuid = p_36199_.getId();
      if (uuid == null) {
         uuid = m_36283_(p_36199_.getName());
      }

      return uuid;
   }

   public static UUID m_36283_(String p_36284_) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + p_36284_).getBytes(StandardCharsets.UTF_8));
   }

   public boolean m_36170_(PlayerModelPart p_36171_) {
      return (this.m_20088_().m_135370_(f_36089_) & p_36171_.m_36445_()) == p_36171_.m_36445_();
   }

   public SlotAccess m_141942_(int p_150112_) {
      if (p_150112_ >= 0 && p_150112_ < this.f_36093_.f_35974_.size()) {
         return SlotAccess.m_147292_(this.f_36093_, p_150112_);
      } else {
         int i = p_150112_ - 200;
         return i >= 0 && i < this.f_36094_.m_6643_() ? SlotAccess.m_147292_(this.f_36094_, i) : super.m_141942_(p_150112_);
      }
   }

   public boolean m_36330_() {
      return this.f_36085_;
   }

   public void m_36393_(boolean p_36394_) {
      this.f_36085_ = p_36394_;
   }

   public void m_7311_(int p_36353_) {
      super.m_7311_(this.f_36077_.f_35934_ ? Math.min(p_36353_, 1) : p_36353_);
   }

   public HumanoidArm m_5737_() {
      return this.f_19804_.m_135370_(f_36090_) == 0 ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
   }

   public void m_36163_(HumanoidArm p_36164_) {
      this.f_19804_.m_135381_(f_36090_, (byte)(p_36164_ == HumanoidArm.LEFT ? 0 : 1));
   }

   public CompoundTag m_36331_() {
      return this.f_19804_.m_135370_(f_36091_);
   }

   protected void m_36362_(CompoundTag p_36363_) {
      this.f_19804_.m_135381_(f_36091_, p_36363_);
   }

   public CompoundTag m_36332_() {
      return this.f_19804_.m_135370_(f_36092_);
   }

   protected void m_36364_(CompoundTag p_36365_) {
      this.f_19804_.m_135381_(f_36092_, p_36365_);
   }

   public float m_36333_() {
      return (float)(1.0D / this.m_21133_(Attributes.f_22283_) * 20.0D);
   }

   public float m_36403_(float p_36404_) {
      return Mth.m_14036_(((float)this.f_20922_ + p_36404_) / this.m_36333_(), 0.0F, 1.0F);
   }

   public void m_36334_() {
      this.f_20922_ = 0;
   }

   public ItemCooldowns m_36335_() {
      return this.f_36087_;
   }

   protected float m_6041_() {
      return !this.f_36077_.f_35935_ && !this.m_21255_() ? super.m_6041_() : 1.0F;
   }

   public float m_36336_() {
      return (float)this.m_21133_(Attributes.f_22286_);
   }

   public boolean m_36337_() {
      return this.f_36077_.f_35937_ && this.m_8088_() >= 2;
   }

   public boolean m_7066_(ItemStack p_36315_) {
      EquipmentSlot equipmentslot = Mob.m_147233_(p_36315_);
      return this.m_6844_(equipmentslot).m_41619_();
   }

   public EntityDimensions m_6972_(Pose p_36166_) {
      return f_36074_.getOrDefault(p_36166_, f_36088_);
   }

   public ImmutableList<Pose> m_7431_() {
      return ImmutableList.of(Pose.STANDING, Pose.CROUCHING, Pose.SWIMMING);
   }

   public ItemStack m_6298_(ItemStack p_36349_) {
      if (!(p_36349_.m_41720_() instanceof ProjectileWeaponItem)) {
         return ItemStack.f_41583_;
      } else {
         Predicate<ItemStack> predicate = ((ProjectileWeaponItem)p_36349_.m_41720_()).m_6442_();
         ItemStack itemstack = ProjectileWeaponItem.m_43010_(this, predicate);
         if (!itemstack.m_41619_()) {
            return itemstack;
         } else {
            predicate = ((ProjectileWeaponItem)p_36349_.m_41720_()).m_6437_();

            for(int i = 0; i < this.f_36093_.m_6643_(); ++i) {
               ItemStack itemstack1 = this.f_36093_.m_8020_(i);
               if (predicate.test(itemstack1)) {
                  return itemstack1;
               }
            }

            return this.f_36077_.f_35937_ ? new ItemStack(Items.f_42412_) : ItemStack.f_41583_;
         }
      }
   }

   public ItemStack m_5584_(Level p_36185_, ItemStack p_36186_) {
      this.m_36324_().m_38712_(p_36186_.m_41720_(), p_36186_);
      this.m_36246_(Stats.f_12982_.m_12902_(p_36186_.m_41720_()));
      p_36185_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12321_, SoundSource.PLAYERS, 0.5F, p_36185_.f_46441_.nextFloat() * 0.1F + 0.9F);
      if (this instanceof ServerPlayer) {
         CriteriaTriggers.f_10592_.m_23682_((ServerPlayer)this, p_36186_);
      }

      return super.m_5584_(p_36185_, p_36186_);
   }

   protected boolean m_6757_(BlockState p_36262_) {
      return this.f_36077_.f_35935_ || super.m_6757_(p_36262_);
   }

   public Vec3 m_7398_(float p_36374_) {
      double d0 = 0.22D * (this.m_5737_() == HumanoidArm.RIGHT ? -1.0D : 1.0D);
      float f = Mth.m_14179_(p_36374_ * 0.5F, this.m_146909_(), this.f_19860_) * ((float)Math.PI / 180F);
      float f1 = Mth.m_14179_(p_36374_, this.f_20884_, this.f_20883_) * ((float)Math.PI / 180F);
      if (!this.m_21255_() && !this.m_21209_()) {
         if (this.m_6067_()) {
            return this.m_20318_(p_36374_).m_82549_((new Vec3(d0, 0.2D, -0.15D)).m_82496_(-f).m_82524_(-f1));
         } else {
            double d5 = this.m_142469_().m_82376_() - 1.0D;
            double d6 = this.m_6047_() ? -0.2D : 0.07D;
            return this.m_20318_(p_36374_).m_82549_((new Vec3(d0, d5, d6)).m_82524_(-f1));
         }
      } else {
         Vec3 vec3 = this.m_20252_(p_36374_);
         Vec3 vec31 = this.m_20184_();
         double d1 = vec31.m_165925_();
         double d2 = vec3.m_165925_();
         float f2;
         if (d1 > 0.0D && d2 > 0.0D) {
            double d3 = (vec31.f_82479_ * vec3.f_82479_ + vec31.f_82481_ * vec3.f_82481_) / Math.sqrt(d1 * d2);
            double d4 = vec31.f_82479_ * vec3.f_82481_ - vec31.f_82481_ * vec3.f_82479_;
            f2 = (float)(Math.signum(d4) * Math.acos(d3));
         } else {
            f2 = 0.0F;
         }

         return this.m_20318_(p_36374_).m_82549_((new Vec3(d0, -0.11D, 0.85D)).m_82535_(-f2).m_82496_(-f).m_82524_(-f1));
      }
   }

   public boolean m_142389_() {
      return true;
   }

   public boolean m_150108_() {
      return this.m_6117_() && this.m_21211_().m_150930_(Items.f_151059_);
   }

   public boolean m_142391_() {
      return false;
   }

   public static enum BedSleepingProblem {
      NOT_POSSIBLE_HERE,
      NOT_POSSIBLE_NOW(new TranslatableComponent("block.minecraft.bed.no_sleep")),
      TOO_FAR_AWAY(new TranslatableComponent("block.minecraft.bed.too_far_away")),
      OBSTRUCTED(new TranslatableComponent("block.minecraft.bed.obstructed")),
      OTHER_PROBLEM,
      NOT_SAFE(new TranslatableComponent("block.minecraft.bed.not_safe"));

      @Nullable
      private final Component f_36413_;

      private BedSleepingProblem() {
         this.f_36413_ = null;
      }

      private BedSleepingProblem(Component p_36422_) {
         this.f_36413_ = p_36422_;
      }

      @Nullable
      public Component m_36423_() {
         return this.f_36413_;
      }
   }

   // =========== FORGE START ==============//
   public Collection<MutableComponent> getPrefixes() {
       return this.prefixes;
   }

   public Collection<MutableComponent> getSuffixes() {
       return this.suffixes;
   }

   private Component displayname = null;
   /**
    * Force the displayed name to refresh, by firing {@link net.minecraftforge.event.entity.player.PlayerEvent.NameFormat}, using the real player name as event parameter.
    */
   public void refreshDisplayName() {
      this.displayname = net.minecraftforge.event.ForgeEventFactory.getPlayerDisplayName(this, this.m_7755_());
   }

   private final net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandler>
         playerMainHandler = net.minecraftforge.common.util.LazyOptional.of(
               () -> new net.minecraftforge.items.wrapper.PlayerMainInvWrapper(f_36093_));

   private final net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandler>
         playerEquipmentHandler = net.minecraftforge.common.util.LazyOptional.of(
               () -> new net.minecraftforge.items.wrapper.CombinedInvWrapper(
                     new net.minecraftforge.items.wrapper.PlayerArmorInvWrapper(f_36093_),
                     new net.minecraftforge.items.wrapper.PlayerOffhandInvWrapper(f_36093_)));

   private final net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandler>
         playerJoinedHandler = net.minecraftforge.common.util.LazyOptional.of(
               () -> new net.minecraftforge.items.wrapper.PlayerInvWrapper(f_36093_));

   @Override
   public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
      if (this.m_6084_() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
         if (facing == null) return playerJoinedHandler.cast();
         else if (facing.m_122434_().m_122478_()) return playerMainHandler.cast();
         else if (facing.m_122434_().m_122479_()) return playerEquipmentHandler.cast();
      }
      return super.getCapability(capability, facing);
   }

   /**
    * Force a pose for the player. If set, the vanilla pose determination and clearance check is skipped. Make sure the pose is clear yourself (e.g. in PlayerTick).
    * This has to be set just once, do not set it every tick.
    * Make sure to clear (null) the pose if not required anymore and only use if necessary.
    */
   public void setForcedPose(@Nullable Pose pose) {
      this.forcedPose = pose;
   }

   /**
    * @return The forced pose if set, null otherwise
    */
   @Nullable
   public Pose getForcedPose() {
      return this.forcedPose;
   }
}
