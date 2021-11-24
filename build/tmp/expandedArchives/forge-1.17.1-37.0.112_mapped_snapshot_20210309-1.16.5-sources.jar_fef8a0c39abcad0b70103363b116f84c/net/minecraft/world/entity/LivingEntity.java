package net.minecraft.world.entity;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.BlockUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddMobPacket;
import net.minecraft.network.protocol.game.ClientboundAnimatePacket;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.network.protocol.game.ClientboundSetEquipmentPacket;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.FrostWalkerEnchantment;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HoneyBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.PlayerTeam;

public abstract class LivingEntity extends Entity {
   private static final UUID f_20929_ = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
   private static final UUID f_20959_ = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e038");
   private static final UUID f_147184_ = UUID.fromString("1eaf83ff-7207-4596-b37a-d7a07b3ec4ce");
   private static final UUID SLOW_FALLING_ID = UUID.fromString("A5B6CF2A-2F7C-31EF-9022-7C3E7D5E6ABA");
   private static final AttributeModifier f_20960_ = new AttributeModifier(f_20929_, "Sprinting speed boost", (double)0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL);
   private static final AttributeModifier SLOW_FALLING = new AttributeModifier(SLOW_FALLING_ID, "Slow falling acceleration reduction", -0.07, AttributeModifier.Operation.ADDITION); // Add -0.07 to 0.08 so we get the vanilla default of 0.01
   public static final int f_147166_ = 2;
   public static final int f_147167_ = 4;
   public static final int f_147168_ = 98;
   public static final int f_147169_ = 100;
   public static final int f_147170_ = 6;
   public static final int f_147171_ = 100;
   private static final int f_147178_ = 40;
   public static final double f_147172_ = 0.003D;
   public static final double f_147173_ = 0.08D;
   public static final int f_147174_ = 20;
   private static final int f_147179_ = 7;
   private static final int f_147180_ = 10;
   private static final int f_147181_ = 2;
   public static final int f_147175_ = 4;
   private static final double f_147182_ = 128.0D;
   protected static final int f_147176_ = 1;
   protected static final int f_147177_ = 2;
   protected static final int f_147163_ = 4;
   protected static final EntityDataAccessor<Byte> f_20909_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135027_);
   private static final EntityDataAccessor<Float> f_20961_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135029_);
   private static final EntityDataAccessor<Integer> f_20962_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Boolean> f_20963_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135035_);
   private static final EntityDataAccessor<Integer> f_20940_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Integer> f_20941_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135028_);
   private static final EntityDataAccessor<Optional<BlockPos>> f_20942_ = SynchedEntityData.m_135353_(LivingEntity.class, EntityDataSerializers.f_135039_);
   protected static final float f_147164_ = 1.74F;
   protected static final EntityDimensions f_20910_ = EntityDimensions.m_20398_(0.2F, 0.2F);
   public static final float f_147165_ = 0.5F;
   private final AttributeMap f_20943_;
   private final CombatTracker f_20944_ = new CombatTracker(this);
   private final Map<MobEffect, MobEffectInstance> f_20945_ = Maps.newHashMap();
   private final NonNullList<ItemStack> f_20946_ = NonNullList.m_122780_(2, ItemStack.f_41583_);
   private final NonNullList<ItemStack> f_20947_ = NonNullList.m_122780_(4, ItemStack.f_41583_);
   public boolean f_20911_;
   private boolean f_147183_ = false;
   public InteractionHand f_20912_;
   public int f_20913_;
   public int f_20914_;
   public int f_20915_;
   public int f_20916_;
   public int f_20917_;
   public float f_20918_;
   public int f_20919_;
   public float f_20920_;
   public float f_20921_;
   protected int f_20922_;
   public float f_20923_;
   public float f_20924_;
   public float f_20925_;
   public final int f_20926_ = 20;
   public final float f_20927_;
   public final float f_20928_;
   public float f_20883_;
   public float f_20884_;
   public float f_20885_;
   public float f_20886_;
   public float f_20887_ = 0.02F;
   @Nullable
   protected Player f_20888_;
   protected int f_20889_;
   protected boolean f_20890_;
   protected int f_20891_;
   protected float f_20892_;
   protected float f_20893_;
   protected float f_20894_;
   protected float f_20895_;
   protected float f_20896_;
   protected int f_20897_;
   protected float f_20898_;
   protected boolean f_20899_;
   public float f_20900_;
   public float f_20901_;
   public float f_20902_;
   protected int f_20903_;
   protected double f_20904_;
   protected double f_20905_;
   protected double f_20906_;
   protected double f_20907_;
   protected double f_20908_;
   protected double f_20933_;
   protected int f_20934_;
   private boolean f_20948_ = true;
   @Nullable
   private LivingEntity f_20949_;
   private int f_20950_;
   private LivingEntity f_20951_;
   private int f_20952_;
   private float f_20953_;
   private int f_20954_;
   private float f_20955_;
   protected ItemStack f_20935_ = ItemStack.f_41583_;
   protected int f_20936_;
   protected int f_20937_;
   private BlockPos f_20956_;
   private Optional<BlockPos> f_20957_ = Optional.empty();
   @Nullable
   private DamageSource f_20958_;
   private long f_20930_;
   protected int f_20938_;
   private float f_20931_;
   private float f_20932_;
   protected Brain<?> f_20939_;

   protected LivingEntity(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
      super(p_20966_, p_20967_);
      this.f_20943_ = new AttributeMap(DefaultAttributes.m_22297_(p_20966_));
      this.m_21153_(this.m_21233_());
      this.f_19850_ = true;
      this.f_20928_ = (float)((Math.random() + 1.0D) * (double)0.01F);
      this.m_20090_();
      this.f_20927_ = (float)Math.random() * 12398.0F;
      this.m_146922_((float)(Math.random() * (double)((float)Math.PI * 2F)));
      this.f_20885_ = this.m_146908_();
      this.f_19793_ = 0.6F;
      NbtOps nbtops = NbtOps.f_128958_;
      this.f_20939_ = this.m_8075_(new Dynamic<>(nbtops, nbtops.createMap(ImmutableMap.of(nbtops.createString("memories"), nbtops.emptyMap()))));
   }

   public Brain<?> m_6274_() {
      return this.f_20939_;
   }

   protected Brain.Provider<?> m_5490_() {
      return Brain.m_21923_(ImmutableList.of(), ImmutableList.of());
   }

   protected Brain<?> m_8075_(Dynamic<?> p_21069_) {
      return this.m_5490_().m_22073_(p_21069_);
   }

   public void m_6074_() {
      this.m_6469_(DamageSource.f_19317_, Float.MAX_VALUE);
   }

   public boolean m_6549_(EntityType<?> p_21032_) {
      return true;
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(f_20909_, (byte)0);
      this.f_19804_.m_135372_(f_20962_, 0);
      this.f_19804_.m_135372_(f_20963_, false);
      this.f_19804_.m_135372_(f_20940_, 0);
      this.f_19804_.m_135372_(f_20941_, 0);
      this.f_19804_.m_135372_(f_20961_, 1.0F);
      this.f_19804_.m_135372_(f_20942_, Optional.empty());
   }

   public static AttributeSupplier.Builder m_21183_() {
      return AttributeSupplier.m_22244_().m_22266_(Attributes.f_22276_).m_22266_(Attributes.f_22278_).m_22266_(Attributes.f_22279_).m_22266_(Attributes.f_22284_).m_22266_(Attributes.f_22285_).m_22266_(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).m_22266_(net.minecraftforge.common.ForgeMod.NAMETAG_DISTANCE.get()).m_22266_(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
   }

   protected void m_7840_(double p_20990_, boolean p_20991_, BlockState p_20992_, BlockPos p_20993_) {
      if (!this.m_20069_()) {
         this.m_20074_();
      }

      if (!this.f_19853_.f_46443_ && p_20991_ && this.f_19789_ > 0.0F) {
         this.m_21185_();
         this.m_21186_();
      }

      if (!this.f_19853_.f_46443_ && this.f_19789_ > 3.0F && p_20991_) {
         float f = (float)Mth.m_14167_(this.f_19789_ - 3.0F);
         if (!p_20992_.m_60795_()) {
            double d0 = Math.min((double)(0.2F + f / 15.0F), 2.5D);
            int i = (int)(150.0D * d0);
            if (!p_20992_.addLandingEffects((ServerLevel)this.f_19853_, p_20993_, p_20992_, this, i))
            ((ServerLevel)this.f_19853_).m_8767_(new BlockParticleOption(ParticleTypes.f_123794_, p_20992_).setPos(p_20993_), this.m_20185_(), this.m_20186_(), this.m_20189_(), i, 0.0D, 0.0D, 0.0D, (double)0.15F);
         }
      }

      super.m_7840_(p_20990_, p_20991_, p_20992_, p_20993_);
   }

   public boolean m_6040_() {
      return this.m_6336_() == MobType.f_21641_;
   }

   public float m_20998_(float p_20999_) {
      return Mth.m_14179_(p_20999_, this.f_20932_, this.f_20931_);
   }

   public void m_6075_() {
      this.f_20920_ = this.f_20921_;
      if (this.f_19803_) {
         this.m_21257_().ifPresent(this::m_21080_);
      }

      if (this.m_6039_()) {
         this.m_21184_();
      }

      super.m_6075_();
      this.f_19853_.m_46473_().m_6180_("livingEntityBaseTick");
      boolean flag = this instanceof Player;
      if (this.m_6084_()) {
         if (this.m_5830_()) {
            this.m_6469_(DamageSource.f_19310_, 1.0F);
         } else if (flag && !this.f_19853_.m_6857_().m_61935_(this.m_142469_())) {
            double d0 = this.f_19853_.m_6857_().m_61925_(this) + this.f_19853_.m_6857_().m_61964_();
            if (d0 < 0.0D) {
               double d1 = this.f_19853_.m_6857_().m_61965_();
               if (d1 > 0.0D) {
                  this.m_6469_(DamageSource.f_19310_, (float)Math.max(1, Mth.m_14107_(-d0 * d1)));
               }
            }
         }
      }

      if (this.m_5825_() || this.f_19853_.f_46443_) {
         this.m_20095_();
      }

      boolean flag1 = flag && ((Player)this).m_150110_().f_35934_;
      if (this.m_6084_()) {
         if (this.m_19941_(FluidTags.f_13131_) && !this.f_19853_.m_8055_(new BlockPos(this.m_20185_(), this.m_20188_(), this.m_20189_())).m_60713_(Blocks.f_50628_)) {
            if (!this.m_6040_() && !MobEffectUtil.m_19588_(this) && !flag1) {
               this.m_20301_(this.m_7302_(this.m_20146_()));
               if (this.m_20146_() == -20) {
                  this.m_20301_(0);
                  Vec3 vec3 = this.m_20184_();

                  for(int i = 0; i < 8; ++i) {
                     double d2 = this.f_19796_.nextDouble() - this.f_19796_.nextDouble();
                     double d3 = this.f_19796_.nextDouble() - this.f_19796_.nextDouble();
                     double d4 = this.f_19796_.nextDouble() - this.f_19796_.nextDouble();
                     this.f_19853_.m_7106_(ParticleTypes.f_123795_, this.m_20185_() + d2, this.m_20186_() + d3, this.m_20189_() + d4, vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
                  }

                  this.m_6469_(DamageSource.f_19312_, 2.0F);
               }
            }

            if (!this.f_19853_.f_46443_ && this.m_20159_() && this.m_20202_() != null && !this.m_20202_().canBeRiddenInWater(this)) {
               this.m_8127_();
            }
         } else if (this.m_20146_() < this.m_6062_()) {
            this.m_20301_(this.m_7305_(this.m_20146_()));
         }

         if (!this.f_19853_.f_46443_) {
            BlockPos blockpos = this.m_142538_();
            if (!Objects.equal(this.f_20956_, blockpos)) {
               this.f_20956_ = blockpos;
               this.m_5806_(blockpos);
            }
         }
      }

      if (this.m_6084_() && (this.m_20071_() || this.f_146808_)) {
         if (!this.f_19853_.f_46443_ && this.f_146810_) {
            this.m_146873_();
         }

         this.m_20095_();
      }

      if (this.f_20916_ > 0) {
         --this.f_20916_;
      }

      if (this.f_19802_ > 0 && !(this instanceof ServerPlayer)) {
         --this.f_19802_;
      }

      if (this.m_21224_()) {
         this.m_6153_();
      }

      if (this.f_20889_ > 0) {
         --this.f_20889_;
      } else {
         this.f_20888_ = null;
      }

      if (this.f_20951_ != null && !this.f_20951_.m_6084_()) {
         this.f_20951_ = null;
      }

      if (this.f_20949_ != null) {
         if (!this.f_20949_.m_6084_()) {
            this.m_6703_((LivingEntity)null);
         } else if (this.f_19797_ - this.f_20950_ > 100) {
            this.m_6703_((LivingEntity)null);
         }
      }

      this.m_21217_();
      this.f_20895_ = this.f_20894_;
      this.f_20884_ = this.f_20883_;
      this.f_20886_ = this.f_20885_;
      this.f_19859_ = this.m_146908_();
      this.f_19860_ = this.m_146909_();
      this.f_19853_.m_46473_().m_7238_();
   }

   public boolean m_6039_() {
      return this.f_19797_ % 5 == 0 && this.m_20184_().f_82479_ != 0.0D && this.m_20184_().f_82481_ != 0.0D && !this.m_5833_() && EnchantmentHelper.m_44942_(this) && this.m_6046_();
   }

   protected void m_21184_() {
      Vec3 vec3 = this.m_20184_();
      this.f_19853_.m_7106_(ParticleTypes.f_123746_, this.m_20185_() + (this.f_19796_.nextDouble() - 0.5D) * (double)this.m_20205_(), this.m_20186_() + 0.1D, this.m_20189_() + (this.f_19796_.nextDouble() - 0.5D) * (double)this.m_20205_(), vec3.f_82479_ * -0.2D, 0.1D, vec3.f_82481_ * -0.2D);
      float f = this.f_19796_.nextFloat() * 0.4F + this.f_19796_.nextFloat() > 0.9F ? 0.6F : 0.0F;
      this.m_5496_(SoundEvents.f_12404_, f, 0.6F + this.f_19796_.nextFloat() * 0.4F);
   }

   protected boolean m_6046_() {
      return this.f_19853_.m_8055_(this.m_20099_()).m_60620_(BlockTags.f_13080_);
   }

   protected float m_6041_() {
      return this.m_6046_() && EnchantmentHelper.m_44836_(Enchantments.f_44976_, this) > 0 ? 1.0F : super.m_6041_();
   }

   protected boolean m_6757_(BlockState p_21140_) {
      return !p_21140_.m_60795_() || this.m_21255_();
   }

   protected void m_21185_() {
      AttributeInstance attributeinstance = this.m_21051_(Attributes.f_22279_);
      if (attributeinstance != null) {
         if (attributeinstance.m_22111_(f_20959_) != null) {
            attributeinstance.m_22120_(f_20959_);
         }

      }
   }

   protected void m_21186_() {
      if (!this.m_20075_().m_60795_()) {
         int i = EnchantmentHelper.m_44836_(Enchantments.f_44976_, this);
         if (i > 0 && this.m_6046_()) {
            AttributeInstance attributeinstance = this.m_21051_(Attributes.f_22279_);
            if (attributeinstance == null) {
               return;
            }

            attributeinstance.m_22118_(new AttributeModifier(f_20959_, "Soul speed boost", (double)(0.03F * (1.0F + (float)i * 0.35F)), AttributeModifier.Operation.ADDITION));
            if (this.m_21187_().nextFloat() < 0.04F) {
               ItemStack itemstack = this.m_6844_(EquipmentSlot.FEET);
               itemstack.m_41622_(1, this, (p_21301_) -> {
                  p_21301_.m_21166_(EquipmentSlot.FEET);
               });
            }
         }
      }

   }

   protected void m_147225_() {
      AttributeInstance attributeinstance = this.m_21051_(Attributes.f_22279_);
      if (attributeinstance != null) {
         if (attributeinstance.m_22111_(f_147184_) != null) {
            attributeinstance.m_22120_(f_147184_);
         }

      }
   }

   protected void m_147226_() {
      if (!this.m_20075_().m_60795_()) {
         int i = this.m_146888_();
         if (i > 0) {
            AttributeInstance attributeinstance = this.m_21051_(Attributes.f_22279_);
            if (attributeinstance == null) {
               return;
            }

            float f = -0.05F * this.m_146889_();
            attributeinstance.m_22118_(new AttributeModifier(f_147184_, "Powder snow slow", (double)f, AttributeModifier.Operation.ADDITION));
         }
      }

   }

   protected void m_5806_(BlockPos p_21175_) {
      int i = EnchantmentHelper.m_44836_(Enchantments.f_44974_, this);
      if (i > 0) {
         FrostWalkerEnchantment.m_45018_(this, this.f_19853_, p_21175_, i);
      }

      if (this.m_6757_(this.m_20075_())) {
         this.m_21185_();
      }

      this.m_21186_();
   }

   public boolean m_6162_() {
      return false;
   }

   public float m_6134_() {
      return this.m_6162_() ? 0.5F : 1.0F;
   }

   protected boolean m_6129_() {
      return true;
   }

   public boolean m_6146_() {
      return false;
   }

   protected void m_6153_() {
      ++this.f_20919_;
      if (this.f_20919_ == 20 && !this.f_19853_.m_5776_()) {
         this.f_19853_.m_7605_(this, (byte)60);
         this.m_142687_(Entity.RemovalReason.KILLED);
      }

   }

   protected boolean m_6149_() {
      return !this.m_6162_();
   }

   protected boolean m_6125_() {
      return !this.m_6162_();
   }

   protected int m_7302_(int p_21303_) {
      int i = EnchantmentHelper.m_44918_(this);
      return i > 0 && this.f_19796_.nextInt(i + 1) > 0 ? p_21303_ : p_21303_ - 1;
   }

   protected int m_7305_(int p_21307_) {
      return Math.min(p_21307_ + 4, this.m_6062_());
   }

   protected int m_6552_(Player p_21201_) {
      return 0;
   }

   protected boolean m_6124_() {
      return false;
   }

   public Random m_21187_() {
      return this.f_19796_;
   }

   @Nullable
   public LivingEntity m_142581_() {
      return this.f_20949_;
   }

   public int m_21213_() {
      return this.f_20950_;
   }

   public void m_6598_(@Nullable Player p_21248_) {
      this.f_20888_ = p_21248_;
      this.f_20889_ = this.f_19797_;
   }

   public void m_6703_(@Nullable LivingEntity p_21039_) {
      this.f_20949_ = p_21039_;
      this.f_20950_ = this.f_19797_;
   }

   @Nullable
   public LivingEntity m_21214_() {
      return this.f_20951_;
   }

   public int m_21215_() {
      return this.f_20952_;
   }

   public void m_21335_(Entity p_21336_) {
      if (p_21336_ instanceof LivingEntity) {
         this.f_20951_ = (LivingEntity)p_21336_;
      } else {
         this.f_20951_ = null;
      }

      this.f_20952_ = this.f_19797_;
   }

   public int m_21216_() {
      return this.f_20891_;
   }

   public void m_21310_(int p_21311_) {
      this.f_20891_ = p_21311_;
   }

   public boolean m_147223_() {
      return this.f_147183_;
   }

   public void m_147244_(boolean p_147245_) {
      this.f_147183_ = p_147245_;
   }

   protected void m_147218_(ItemStack p_147219_) {
      SoundEvent soundevent = p_147219_.m_150920_();
      if (!p_147219_.m_41619_() && soundevent != null && !this.m_5833_()) {
         this.m_146850_(GameEvent.f_157811_);
         this.m_5496_(soundevent, 1.0F, 1.0F);
      }
   }

   public void m_7380_(CompoundTag p_21145_) {
      p_21145_.m_128350_("Health", this.m_21223_());
      p_21145_.m_128376_("HurtTime", (short)this.f_20916_);
      p_21145_.m_128405_("HurtByTimestamp", this.f_20950_);
      p_21145_.m_128376_("DeathTime", (short)this.f_20919_);
      p_21145_.m_128350_("AbsorptionAmount", this.m_6103_());
      p_21145_.m_128365_("Attributes", this.m_21204_().m_22180_());
      if (!this.f_20945_.isEmpty()) {
         ListTag listtag = new ListTag();

         for(MobEffectInstance mobeffectinstance : this.f_20945_.values()) {
            listtag.add(mobeffectinstance.m_19555_(new CompoundTag()));
         }

         p_21145_.m_128365_("ActiveEffects", listtag);
      }

      p_21145_.m_128379_("FallFlying", this.m_21255_());
      this.m_21257_().ifPresent((p_21099_) -> {
         p_21145_.m_128405_("SleepingX", p_21099_.m_123341_());
         p_21145_.m_128405_("SleepingY", p_21099_.m_123342_());
         p_21145_.m_128405_("SleepingZ", p_21099_.m_123343_());
      });
      DataResult<Tag> dataresult = this.f_20939_.m_21914_(NbtOps.f_128958_);
      dataresult.resultOrPartial(f_19849_::error).ifPresent((p_21102_) -> {
         p_21145_.m_128365_("Brain", p_21102_);
      });
   }

   public void m_7378_(CompoundTag p_21096_) {
      this.m_7911_(p_21096_.m_128457_("AbsorptionAmount"));
      if (p_21096_.m_128425_("Attributes", 9) && this.f_19853_ != null && !this.f_19853_.f_46443_) {
         this.m_21204_().m_22168_(p_21096_.m_128437_("Attributes", 10));
      }

      if (p_21096_.m_128425_("ActiveEffects", 9)) {
         ListTag listtag = p_21096_.m_128437_("ActiveEffects", 10);

         for(int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.m_128728_(i);
            MobEffectInstance mobeffectinstance = MobEffectInstance.m_19560_(compoundtag);
            if (mobeffectinstance != null) {
               this.f_20945_.put(mobeffectinstance.m_19544_(), mobeffectinstance);
            }
         }
      }

      if (p_21096_.m_128425_("Health", 99)) {
         this.m_21153_(p_21096_.m_128457_("Health"));
      }

      this.f_20916_ = p_21096_.m_128448_("HurtTime");
      this.f_20919_ = p_21096_.m_128448_("DeathTime");
      this.f_20950_ = p_21096_.m_128451_("HurtByTimestamp");
      if (p_21096_.m_128425_("Team", 8)) {
         String s = p_21096_.m_128461_("Team");
         PlayerTeam playerteam = this.f_19853_.m_6188_().m_83489_(s);
         boolean flag = playerteam != null && this.f_19853_.m_6188_().m_6546_(this.m_20149_(), playerteam);
         if (!flag) {
            f_19849_.warn("Unable to add mob to team \"{}\" (that team probably doesn't exist)", (Object)s);
         }
      }

      if (p_21096_.m_128471_("FallFlying")) {
         this.m_20115_(7, true);
      }

      if (p_21096_.m_128425_("SleepingX", 99) && p_21096_.m_128425_("SleepingY", 99) && p_21096_.m_128425_("SleepingZ", 99)) {
         BlockPos blockpos = new BlockPos(p_21096_.m_128451_("SleepingX"), p_21096_.m_128451_("SleepingY"), p_21096_.m_128451_("SleepingZ"));
         this.m_21250_(blockpos);
         this.f_19804_.m_135381_(f_19806_, Pose.SLEEPING);
         if (!this.f_19803_) {
            this.m_21080_(blockpos);
         }
      }

      if (p_21096_.m_128425_("Brain", 10)) {
         this.f_20939_ = this.m_8075_(new Dynamic<>(NbtOps.f_128958_, p_21096_.m_128423_("Brain")));
      }

   }

   protected void m_21217_() {
      Iterator<MobEffect> iterator = this.f_20945_.keySet().iterator();

      try {
         while(iterator.hasNext()) {
            MobEffect mobeffect = iterator.next();
            MobEffectInstance mobeffectinstance = this.f_20945_.get(mobeffect);
            if (!mobeffectinstance.m_19552_(this, () -> {
               this.m_141973_(mobeffectinstance, true, (Entity)null);
            })) {
               if (!this.f_19853_.f_46443_ && !net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.PotionEvent.PotionExpiryEvent(this, mobeffectinstance))) {
                  iterator.remove();
                  this.m_7285_(mobeffectinstance);
               }
            } else if (mobeffectinstance.m_19557_() % 600 == 0) {
               this.m_141973_(mobeffectinstance, false, (Entity)null);
            }
         }
      } catch (ConcurrentModificationException concurrentmodificationexception) {
      }

      if (this.f_20948_) {
         if (!this.f_19853_.f_46443_) {
            this.m_8034_();
            this.m_147239_();
         }

         this.f_20948_ = false;
      }

      int i = this.f_19804_.m_135370_(f_20962_);
      boolean flag1 = this.f_19804_.m_135370_(f_20963_);
      if (i > 0) {
         boolean flag;
         if (this.m_20145_()) {
            flag = this.f_19796_.nextInt(15) == 0;
         } else {
            flag = this.f_19796_.nextBoolean();
         }

         if (flag1) {
            flag &= this.f_19796_.nextInt(5) == 0;
         }

         if (flag && i > 0) {
            double d0 = (double)(i >> 16 & 255) / 255.0D;
            double d1 = (double)(i >> 8 & 255) / 255.0D;
            double d2 = (double)(i >> 0 & 255) / 255.0D;
            this.f_19853_.m_7106_(flag1 ? ParticleTypes.f_123770_ : ParticleTypes.f_123811_, this.m_20208_(0.5D), this.m_20187_(), this.m_20262_(0.5D), d0, d1, d2);
         }
      }

   }

   protected void m_8034_() {
      if (this.f_20945_.isEmpty()) {
         this.m_21218_();
         this.m_6842_(false);
      } else {
         Collection<MobEffectInstance> collection = this.f_20945_.values();
         net.minecraftforge.event.entity.living.PotionColorCalculationEvent event = new net.minecraftforge.event.entity.living.PotionColorCalculationEvent(this, PotionUtils.m_43564_(collection), m_21179_(collection), collection);
         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
         this.f_19804_.m_135381_(f_20963_, event.areParticlesHidden());
         this.f_19804_.m_135381_(f_20962_, event.getColor());
         this.m_6842_(this.m_21023_(MobEffects.f_19609_));
      }

   }

   private void m_147239_() {
      boolean flag = this.m_142038_();
      if (this.m_20291_(6) != flag) {
         this.m_20115_(6, flag);
      }

   }

   public double m_20968_(@Nullable Entity p_20969_) {
      double d0 = 1.0D;
      if (this.m_20163_()) {
         d0 *= 0.8D;
      }

      if (this.m_20145_()) {
         float f = this.m_21207_();
         if (f < 0.1F) {
            f = 0.1F;
         }

         d0 *= 0.7D * (double)f;
      }

      if (p_20969_ != null) {
         ItemStack itemstack = this.m_6844_(EquipmentSlot.HEAD);
         EntityType<?> entitytype = p_20969_.m_6095_();
         if (entitytype == EntityType.f_20524_ && itemstack.m_150930_(Items.f_42678_) || entitytype == EntityType.f_20501_ && itemstack.m_150930_(Items.f_42681_) || entitytype == EntityType.f_20558_ && itemstack.m_150930_(Items.f_42682_)) {
            d0 *= 0.5D;
         }
      }
      d0 = net.minecraftforge.common.ForgeHooks.getEntityVisibilityMultiplier(this, p_20969_, d0);
      return d0;
   }

   public boolean m_6779_(LivingEntity p_21171_) {
      return p_21171_ instanceof Player && this.f_19853_.m_46791_() == Difficulty.PEACEFUL ? false : p_21171_.m_142066_();
   }

   public boolean m_21040_(LivingEntity p_21041_, TargetingConditions p_21042_) {
      return p_21042_.m_26885_(this, p_21041_);
   }

   public boolean m_142066_() {
      return !this.m_20147_() && this.m_142065_();
   }

   public boolean m_142065_() {
      return !this.m_5833_() && this.m_6084_();
   }

   public static boolean m_21179_(Collection<MobEffectInstance> p_21180_) {
      for(MobEffectInstance mobeffectinstance : p_21180_) {
         if (!mobeffectinstance.m_19571_()) {
            return false;
         }
      }

      return true;
   }

   protected void m_21218_() {
      this.f_19804_.m_135381_(f_20963_, false);
      this.f_19804_.m_135381_(f_20962_, 0);
   }

   public boolean m_21219_() {
      if (this.f_19853_.f_46443_) {
         return false;
      } else {
         Iterator<MobEffectInstance> iterator = this.f_20945_.values().iterator();

         boolean flag;
         for(flag = false; iterator.hasNext(); flag = true) {
            MobEffectInstance effect = iterator.next();
            if(net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent(this, effect))) continue;
            this.m_7285_(effect);
            iterator.remove();
         }

         return flag;
      }
   }

   public Collection<MobEffectInstance> m_21220_() {
      return this.f_20945_.values();
   }

   public Map<MobEffect, MobEffectInstance> m_21221_() {
      return this.f_20945_;
   }

   public boolean m_21023_(MobEffect p_21024_) {
      return this.f_20945_.containsKey(p_21024_);
   }

   @Nullable
   public MobEffectInstance m_21124_(MobEffect p_21125_) {
      return this.f_20945_.get(p_21125_);
   }

   public final boolean m_7292_(MobEffectInstance p_21165_) {
      return this.m_147207_(p_21165_, (Entity)null);
   }

   public boolean m_147207_(MobEffectInstance p_147208_, @Nullable Entity p_147209_) {
      if (!this.m_7301_(p_147208_)) {
         return false;
      } else {
         MobEffectInstance mobeffectinstance = this.f_20945_.get(p_147208_.m_19544_());
         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.PotionEvent.PotionAddedEvent(this, mobeffectinstance, p_147208_, p_147209_));
         if (mobeffectinstance == null) {
            this.f_20945_.put(p_147208_.m_19544_(), p_147208_);
            this.m_142540_(p_147208_, p_147209_);
            return true;
         } else if (mobeffectinstance.m_19558_(p_147208_)) {
            this.m_141973_(mobeffectinstance, true, p_147209_);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean m_7301_(MobEffectInstance p_21197_) {
      net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, p_21197_);
      net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
      if (event.getResult() != net.minecraftforge.eventbus.api.Event.Result.DEFAULT) return event.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW;
      if (this.m_6336_() == MobType.f_21641_) {
         MobEffect mobeffect = p_21197_.m_19544_();
         if (mobeffect == MobEffects.f_19605_ || mobeffect == MobEffects.f_19614_) {
            return false;
         }
      }

      return true;
   }

   public void m_147215_(MobEffectInstance p_147216_, @Nullable Entity p_147217_) {
      if (this.m_7301_(p_147216_)) {
         MobEffectInstance mobeffectinstance = this.f_20945_.put(p_147216_.m_19544_(), p_147216_);
         if (mobeffectinstance == null) {
            this.m_142540_(p_147216_, p_147217_);
         } else {
            this.m_141973_(p_147216_, true, p_147217_);
         }

      }
   }

   public boolean m_21222_() {
      return this.m_6336_() == MobType.f_21641_;
   }

   @Nullable
   public MobEffectInstance m_6234_(@Nullable MobEffect p_21164_) {
      return this.f_20945_.remove(p_21164_);
   }

   public boolean m_21195_(MobEffect p_21196_) {
      if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent(this, p_21196_))) return false;
      MobEffectInstance mobeffectinstance = this.m_6234_(p_21196_);
      if (mobeffectinstance != null) {
         this.m_7285_(mobeffectinstance);
         return true;
      } else {
         return false;
      }
   }

   protected void m_142540_(MobEffectInstance p_147190_, @Nullable Entity p_147191_) {
      this.f_20948_ = true;
      if (!this.f_19853_.f_46443_) {
         p_147190_.m_19544_().m_6385_(this, this.m_21204_(), p_147190_.m_19564_());
      }

   }

   protected void m_141973_(MobEffectInstance p_147192_, boolean p_147193_, @Nullable Entity p_147194_) {
      this.f_20948_ = true;
      if (p_147193_ && !this.f_19853_.f_46443_) {
         MobEffect mobeffect = p_147192_.m_19544_();
         mobeffect.m_6386_(this, this.m_21204_(), p_147192_.m_19564_());
         mobeffect.m_6385_(this, this.m_21204_(), p_147192_.m_19564_());
      }

   }

   protected void m_7285_(MobEffectInstance p_21126_) {
      this.f_20948_ = true;
      if (!this.f_19853_.f_46443_) {
         p_21126_.m_19544_().m_6386_(this, this.m_21204_(), p_21126_.m_19564_());
      }

   }

   public void m_5634_(float p_21116_) {
      p_21116_ = net.minecraftforge.event.ForgeEventFactory.onLivingHeal(this, p_21116_);
      if (p_21116_ <= 0) return;
      float f = this.m_21223_();
      if (f > 0.0F) {
         this.m_21153_(f + p_21116_);
      }

   }

   public float m_21223_() {
      return this.f_19804_.m_135370_(f_20961_);
   }

   public void m_21153_(float p_21154_) {
      this.f_19804_.m_135381_(f_20961_, Mth.m_14036_(p_21154_, 0.0F, this.m_21233_()));
   }

   public boolean m_21224_() {
      return this.m_21223_() <= 0.0F;
   }

   public boolean m_6469_(DamageSource p_21016_, float p_21017_) {
      if (!net.minecraftforge.common.ForgeHooks.onLivingAttack(this, p_21016_, p_21017_)) return false;
      if (this.m_6673_(p_21016_)) {
         return false;
      } else if (this.f_19853_.f_46443_) {
         return false;
      } else if (this.m_21224_()) {
         return false;
      } else if (p_21016_.m_19384_() && this.m_21023_(MobEffects.f_19607_)) {
         return false;
      } else {
         if (this.m_5803_() && !this.f_19853_.f_46443_) {
            this.m_5796_();
         }

         this.f_20891_ = 0;
         float f = p_21017_;
         boolean flag = false;
         float f1 = 0.0F;
         if (p_21017_ > 0.0F && this.m_21275_(p_21016_)) {
            this.m_7909_(p_21017_);
            f1 = p_21017_;
            p_21017_ = 0.0F;
            if (!p_21016_.m_19360_()) {
               Entity entity = p_21016_.m_7640_();
               if (entity instanceof LivingEntity) {
                  this.m_6728_((LivingEntity)entity);
               }
            }

            flag = true;
         }

         this.f_20924_ = 1.5F;
         boolean flag1 = true;
         if ((float)this.f_19802_ > 10.0F) {
            if (p_21017_ <= this.f_20898_) {
               return false;
            }

            this.m_6475_(p_21016_, p_21017_ - this.f_20898_);
            this.f_20898_ = p_21017_;
            flag1 = false;
         } else {
            this.f_20898_ = p_21017_;
            this.f_19802_ = 20;
            this.m_6475_(p_21016_, p_21017_);
            this.f_20917_ = 10;
            this.f_20916_ = this.f_20917_;
         }

         if (p_21016_.m_146705_() && !this.m_6844_(EquipmentSlot.HEAD).m_41619_()) {
            this.m_142642_(p_21016_, p_21017_);
            p_21017_ *= 0.75F;
         }

         this.f_20918_ = 0.0F;
         Entity entity1 = p_21016_.m_7639_();
         if (entity1 != null) {
            if (entity1 instanceof LivingEntity && !p_21016_.m_181121_()) {
               this.m_6703_((LivingEntity)entity1);
            }

            if (entity1 instanceof Player) {
               this.f_20889_ = 100;
               this.f_20888_ = (Player)entity1;
            } else if (entity1 instanceof net.minecraft.world.entity.TamableAnimal) {
               net.minecraft.world.entity.TamableAnimal tamableEntity = (net.minecraft.world.entity.TamableAnimal)entity1;
               if (tamableEntity.m_21824_()) {
                  this.f_20889_ = 100;
                  LivingEntity livingentity = tamableEntity.m_142480_();
                  if (livingentity != null && livingentity.m_6095_() == EntityType.f_20532_) {
                     this.f_20888_ = (Player)livingentity;
                  } else {
                     this.f_20888_ = null;
                  }
               }
            }
         }

         if (flag1) {
            if (flag) {
               this.f_19853_.m_7605_(this, (byte)29);
            } else if (p_21016_ instanceof EntityDamageSource && ((EntityDamageSource)p_21016_).m_19403_()) {
               this.f_19853_.m_7605_(this, (byte)33);
            } else {
               byte b0;
               if (p_21016_ == DamageSource.f_19312_) {
                  b0 = 36;
               } else if (p_21016_.m_19384_()) {
                  b0 = 37;
               } else if (p_21016_ == DamageSource.f_19325_) {
                  b0 = 44;
               } else if (p_21016_ == DamageSource.f_146701_) {
                  b0 = 57;
               } else {
                  b0 = 2;
               }

               this.f_19853_.m_7605_(this, b0);
            }

            if (p_21016_ != DamageSource.f_19312_ && (!flag || p_21017_ > 0.0F)) {
               this.m_5834_();
            }

            if (entity1 != null) {
               double d1 = entity1.m_20185_() - this.m_20185_();

               double d0;
               for(d0 = entity1.m_20189_() - this.m_20189_(); d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
                  d1 = (Math.random() - Math.random()) * 0.01D;
               }

               this.f_20918_ = (float)(Mth.m_14136_(d0, d1) * (double)(180F / (float)Math.PI) - (double)this.m_146908_());
               this.m_147240_((double)0.4F, d1, d0);
            } else {
               this.f_20918_ = (float)((int)(Math.random() * 2.0D) * 180);
            }
         }

         if (this.m_21224_()) {
            if (!this.m_21262_(p_21016_)) {
               SoundEvent soundevent = this.m_5592_();
               if (flag1 && soundevent != null) {
                  this.m_5496_(soundevent, this.m_6121_(), this.m_6100_());
               }

               this.m_6667_(p_21016_);
            }
         } else if (flag1) {
            this.m_6677_(p_21016_);
         }

         boolean flag2 = !flag || p_21017_ > 0.0F;
         if (flag2) {
            this.f_20958_ = p_21016_;
            this.f_20930_ = this.f_19853_.m_46467_();
         }

         if (this instanceof ServerPlayer) {
            CriteriaTriggers.f_10574_.m_35174_((ServerPlayer)this, p_21016_, f, p_21017_, flag);
            if (f1 > 0.0F && f1 < 3.4028235E37F) {
               ((ServerPlayer)this).m_6278_(Stats.f_12988_.m_12902_(Stats.f_12932_), Math.round(f1 * 10.0F));
            }
         }

         if (entity1 instanceof ServerPlayer) {
            CriteriaTriggers.f_10573_.m_60112_((ServerPlayer)entity1, this, p_21016_, f, p_21017_, flag);
         }

         return flag2;
      }
   }

   protected void m_6728_(LivingEntity p_21200_) {
      p_21200_.m_6731_(this);
   }

   protected void m_6731_(LivingEntity p_21246_) {
      p_21246_.m_147240_(0.5D, p_21246_.m_20185_() - this.m_20185_(), p_21246_.m_20189_() - this.m_20189_());
   }

   private boolean m_21262_(DamageSource p_21263_) {
      if (p_21263_.m_19378_()) {
         return false;
      } else {
         ItemStack itemstack = null;

         for(InteractionHand interactionhand : InteractionHand.values()) {
            ItemStack itemstack1 = this.m_21120_(interactionhand);
            if (itemstack1.m_150930_(Items.f_42747_)) {
               itemstack = itemstack1.m_41777_();
               itemstack1.m_41774_(1);
               break;
            }
         }

         if (itemstack != null) {
            if (this instanceof ServerPlayer) {
               ServerPlayer serverplayer = (ServerPlayer)this;
               serverplayer.m_6278_(Stats.f_12982_.m_12902_(Items.f_42747_), 1);
               CriteriaTriggers.f_10551_.m_74431_(serverplayer, itemstack);
            }

            this.m_21153_(1.0F);
            this.m_21219_();
            this.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 900, 1));
            this.m_7292_(new MobEffectInstance(MobEffects.f_19617_, 100, 1));
            this.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 800, 0));
            this.f_19853_.m_7605_(this, (byte)35);
         }

         return itemstack != null;
      }
   }

   @Nullable
   public DamageSource m_21225_() {
      if (this.f_19853_.m_46467_() - this.f_20930_ > 40L) {
         this.f_20958_ = null;
      }

      return this.f_20958_;
   }

   protected void m_6677_(DamageSource p_21160_) {
      SoundEvent soundevent = this.m_7975_(p_21160_);
      if (soundevent != null) {
         this.m_5496_(soundevent, this.m_6121_(), this.m_6100_());
      }

   }

   public boolean m_21275_(DamageSource p_21276_) {
      Entity entity = p_21276_.m_7640_();
      boolean flag = false;
      if (entity instanceof AbstractArrow) {
         AbstractArrow abstractarrow = (AbstractArrow)entity;
         if (abstractarrow.m_36796_() > 0) {
            flag = true;
         }
      }

      if (!p_21276_.m_19376_() && this.m_21254_() && !flag) {
         Vec3 vec32 = p_21276_.m_7270_();
         if (vec32 != null) {
            Vec3 vec3 = this.m_20252_(1.0F);
            Vec3 vec31 = vec32.m_82505_(this.m_20182_()).m_82541_();
            vec31 = new Vec3(vec31.f_82479_, 0.0D, vec31.f_82481_);
            if (vec31.m_82526_(vec3) < 0.0D) {
               return true;
            }
         }
      }

      return false;
   }

   private void m_21278_(ItemStack p_21279_) {
      if (!p_21279_.m_41619_()) {
         if (!this.m_20067_()) {
            this.f_19853_.m_7785_(this.m_20185_(), this.m_20186_(), this.m_20189_(), SoundEvents.f_12018_, this.m_5720_(), 0.8F, 0.8F + this.f_19853_.f_46441_.nextFloat() * 0.4F, false);
         }

         this.m_21060_(p_21279_, 5);
      }

   }

   public void m_6667_(DamageSource p_21014_) {
      if (net.minecraftforge.common.ForgeHooks.onLivingDeath(this, p_21014_)) return;
      if (!this.m_146910_() && !this.f_20890_) {
         Entity entity = p_21014_.m_7639_();
         LivingEntity livingentity = this.m_21232_();
         if (this.f_20897_ >= 0 && livingentity != null) {
            livingentity.m_5993_(this, this.f_20897_, p_21014_);
         }

         if (this.m_5803_()) {
            this.m_5796_();
         }

         if (!this.f_19853_.f_46443_ && this.m_8077_()) {
            f_19849_.info("Named entity {} died: {}", this, this.m_21231_().m_19293_().getString());
         }

         this.f_20890_ = true;
         this.m_21231_().m_19296_();
         if (this.f_19853_ instanceof ServerLevel) {
            if (entity != null) {
               entity.m_5837_((ServerLevel)this.f_19853_, this);
            }

            this.m_6668_(p_21014_);
            this.m_21268_(livingentity);
         }

         this.f_19853_.m_7605_(this, (byte)3);
         this.m_20124_(Pose.DYING);
      }
   }

   protected void m_21268_(@Nullable LivingEntity p_21269_) {
      if (!this.f_19853_.f_46443_) {
         boolean flag = false;
         if (p_21269_ instanceof WitherBoss) {
            if (net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
               BlockPos blockpos = this.m_142538_();
               BlockState blockstate = Blocks.f_50070_.m_49966_();
               if (this.f_19853_.m_46859_(blockpos) && blockstate.m_60710_(this.f_19853_, blockpos)) {
                  this.f_19853_.m_7731_(blockpos, blockstate, 3);
                  flag = true;
               }
            }

            if (!flag) {
               ItemEntity itementity = new ItemEntity(this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), new ItemStack(Items.f_41951_));
               this.f_19853_.m_7967_(itementity);
            }
         }

      }
   }

   protected void m_6668_(DamageSource p_21192_) {
      Entity entity = p_21192_.m_7639_();

      int i = net.minecraftforge.common.ForgeHooks.getLootingLevel(this, entity, p_21192_);
      this.captureDrops(new java.util.ArrayList<>());

      boolean flag = this.f_20889_ > 0;
      if (this.m_6125_() && this.f_19853_.m_46469_().m_46207_(GameRules.f_46135_)) {
         this.m_7625_(p_21192_, flag);
         this.m_7472_(p_21192_, i, flag);
      }

      this.m_5907_();
      this.m_21226_();

      Collection<ItemEntity> drops = captureDrops(null);
      if (!net.minecraftforge.common.ForgeHooks.onLivingDrops(this, p_21192_, drops, i, f_20889_ > 0))
         drops.forEach(e -> f_19853_.m_7967_(e));
   }

   protected void m_5907_() {
   }

   protected void m_21226_() {
      if (this.f_19853_ instanceof ServerLevel && (this.m_6124_() || this.f_20889_ > 0 && this.m_6149_() && this.f_19853_.m_46469_().m_46207_(GameRules.f_46135_))) {
         ExperienceOrb.m_147082_((ServerLevel)this.f_19853_, this.m_20182_(), this.m_6552_(this.f_20888_));
      }

   }

   protected void m_7472_(DamageSource p_21018_, int p_21019_, boolean p_21020_) {
   }

   public ResourceLocation m_5743_() {
      return this.m_6095_().m_20677_();
   }

   protected void m_7625_(DamageSource p_21021_, boolean p_21022_) {
      ResourceLocation resourcelocation = this.m_5743_();
      LootTable loottable = this.f_19853_.m_142572_().m_129898_().m_79217_(resourcelocation);
      LootContext.Builder lootcontext$builder = this.m_7771_(p_21022_, p_21021_);
      LootContext ctx = lootcontext$builder.m_78975_(LootContextParamSets.f_81415_);
      loottable.m_79129_(ctx).forEach(this::m_19983_);
   }

   protected LootContext.Builder m_7771_(boolean p_21105_, DamageSource p_21106_) {
      LootContext.Builder lootcontext$builder = (new LootContext.Builder((ServerLevel)this.f_19853_)).m_78977_(this.f_19796_).m_78972_(LootContextParams.f_81455_, this).m_78972_(LootContextParams.f_81460_, this.m_20182_()).m_78972_(LootContextParams.f_81457_, p_21106_).m_78984_(LootContextParams.f_81458_, p_21106_.m_7639_()).m_78984_(LootContextParams.f_81459_, p_21106_.m_7640_());
      if (p_21105_ && this.f_20888_ != null) {
         lootcontext$builder = lootcontext$builder.m_78972_(LootContextParams.f_81456_, this.f_20888_).m_78963_(this.f_20888_.m_36336_());
      }

      return lootcontext$builder;
   }

   public void m_147240_(double p_147241_, double p_147242_, double p_147243_) {
      net.minecraftforge.event.entity.living.LivingKnockBackEvent event = net.minecraftforge.common.ForgeHooks.onLivingKnockBack(this, (float) p_147241_, p_147242_, p_147243_);
      if(event.isCanceled()) return;
      p_147241_ = event.getStrength();
      p_147242_ = event.getRatioX();
      p_147243_ = event.getRatioZ();
      p_147241_ = p_147241_ * (1.0D - this.m_21133_(Attributes.f_22278_));
      if (!(p_147241_ <= 0.0D)) {
         this.f_19812_ = true;
         Vec3 vec3 = this.m_20184_();
         Vec3 vec31 = (new Vec3(p_147242_, 0.0D, p_147243_)).m_82541_().m_82490_(p_147241_);
         this.m_20334_(vec3.f_82479_ / 2.0D - vec31.f_82479_, this.f_19861_ ? Math.min(0.4D, vec3.f_82480_ / 2.0D + p_147241_) : vec3.f_82480_, vec3.f_82481_ / 2.0D - vec31.f_82481_);
      }
   }

   @Nullable
   protected SoundEvent m_7975_(DamageSource p_21239_) {
      return SoundEvents.f_11915_;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return SoundEvents.f_11910_;
   }

   protected SoundEvent m_5896_(int p_21313_) {
      return p_21313_ > 4 ? SoundEvents.f_11908_ : SoundEvents.f_11916_;
   }

   protected SoundEvent m_7838_(ItemStack p_21174_) {
      return p_21174_.m_41615_();
   }

   public SoundEvent m_7866_(ItemStack p_21202_) {
      return p_21202_.m_41616_();
   }

   public void m_6853_(boolean p_21182_) {
      super.m_6853_(p_21182_);
      if (p_21182_) {
         this.f_20957_ = Optional.empty();
      }

   }

   public Optional<BlockPos> m_21227_() {
      return this.f_20957_;
   }

   public boolean m_6147_() {
      if (this.m_5833_()) {
         return false;
      } else {
         BlockPos blockpos = this.m_142538_();
         BlockState blockstate = this.m_146900_();
         return net.minecraftforge.common.ForgeHooks.isLivingOnLadder(blockstate, f_19853_, blockpos, this);
      }
   }

   private boolean m_21176_(BlockPos p_21177_, BlockState p_21178_) {
      if (p_21178_.m_61143_(TrapDoorBlock.f_57514_)) {
         BlockState blockstate = this.f_19853_.m_8055_(p_21177_.m_7495_());
         if (blockstate.m_60713_(Blocks.f_50155_) && blockstate.m_61143_(LadderBlock.f_54337_) == p_21178_.m_61143_(TrapDoorBlock.f_54117_)) {
            return true;
         }
      }

      return false;
   }

   public boolean m_6084_() {
      return !this.m_146910_() && this.m_21223_() > 0.0F;
   }

   public boolean m_142535_(float p_147187_, float p_147188_, DamageSource p_147189_) {
      float[] ret = net.minecraftforge.common.ForgeHooks.onLivingFall(this, p_147187_, p_147188_);
      if (ret == null) return false;
      p_147187_ = ret[0];
      p_147188_ = ret[1];

      boolean flag = super.m_142535_(p_147187_, p_147188_, p_147189_);
      int i = this.m_5639_(p_147187_, p_147188_);
      if (i > 0) {
         this.m_5496_(this.m_5896_(i), 1.0F, 1.0F);
         this.m_21229_();
         this.m_6469_(p_147189_, (float)i);
         return true;
      } else {
         return flag;
      }
   }

   protected int m_5639_(float p_21237_, float p_21238_) {
      MobEffectInstance mobeffectinstance = this.m_21124_(MobEffects.f_19603_);
      float f = mobeffectinstance == null ? 0.0F : (float)(mobeffectinstance.m_19564_() + 1);
      return Mth.m_14167_((p_21237_ - 3.0F - f) * p_21238_);
   }

   protected void m_21229_() {
      if (!this.m_20067_()) {
         int i = Mth.m_14107_(this.m_20185_());
         int j = Mth.m_14107_(this.m_20186_() - (double)0.2F);
         int k = Mth.m_14107_(this.m_20189_());
         BlockPos pos = new BlockPos(i, j, k);
         BlockState blockstate = this.f_19853_.m_8055_(pos);
         if (!blockstate.m_60795_()) {
            SoundType soundtype = blockstate.getSoundType(f_19853_, pos, this);
            this.m_5496_(soundtype.m_56779_(), soundtype.m_56773_() * 0.5F, soundtype.m_56774_() * 0.75F);
         }

      }
   }

   public void m_6053_() {
      this.f_20917_ = 10;
      this.f_20916_ = this.f_20917_;
      this.f_20918_ = 0.0F;
   }

   public int m_21230_() {
      return Mth.m_14107_(this.m_21133_(Attributes.f_22284_));
   }

   protected void m_6472_(DamageSource p_21122_, float p_21123_) {
   }

   protected void m_142642_(DamageSource p_147213_, float p_147214_) {
   }

   protected void m_7909_(float p_21316_) {
   }

   protected float m_21161_(DamageSource p_21162_, float p_21163_) {
      if (!p_21162_.m_19376_()) {
         this.m_6472_(p_21162_, p_21163_);
         p_21163_ = CombatRules.m_19272_(p_21163_, (float)this.m_21230_(), (float)this.m_21133_(Attributes.f_22285_));
      }

      return p_21163_;
   }

   protected float m_6515_(DamageSource p_21193_, float p_21194_) {
      if (p_21193_.m_19379_()) {
         return p_21194_;
      } else {
         if (this.m_21023_(MobEffects.f_19606_) && p_21193_ != DamageSource.f_19317_) {
            int i = (this.m_21124_(MobEffects.f_19606_).m_19564_() + 1) * 5;
            int j = 25 - i;
            float f = p_21194_ * (float)j;
            float f1 = p_21194_;
            p_21194_ = Math.max(f / 25.0F, 0.0F);
            float f2 = f1 - p_21194_;
            if (f2 > 0.0F && f2 < 3.4028235E37F) {
               if (this instanceof ServerPlayer) {
                  ((ServerPlayer)this).m_6278_(Stats.f_12988_.m_12902_(Stats.f_12934_), Math.round(f2 * 10.0F));
               } else if (p_21193_.m_7639_() instanceof ServerPlayer) {
                  ((ServerPlayer)p_21193_.m_7639_()).m_6278_(Stats.f_12988_.m_12902_(Stats.f_12930_), Math.round(f2 * 10.0F));
               }
            }
         }

         if (p_21194_ <= 0.0F) {
            return 0.0F;
         } else {
            int k = EnchantmentHelper.m_44856_(this.m_6168_(), p_21193_);
            if (k > 0) {
               p_21194_ = CombatRules.m_19269_(p_21194_, (float)k);
            }

            return p_21194_;
         }
      }
   }

   protected void m_6475_(DamageSource p_21240_, float p_21241_) {
      if (!this.m_6673_(p_21240_)) {
         p_21241_ = net.minecraftforge.common.ForgeHooks.onLivingHurt(this, p_21240_, p_21241_);
         if (p_21241_ <= 0) return;
         p_21241_ = this.m_21161_(p_21240_, p_21241_);
         p_21241_ = this.m_6515_(p_21240_, p_21241_);
         float f2 = Math.max(p_21241_ - this.m_6103_(), 0.0F);
         this.m_7911_(this.m_6103_() - (p_21241_ - f2));
         float f = p_21241_ - f2;
         if (f > 0.0F && f < 3.4028235E37F && p_21240_.m_7639_() instanceof ServerPlayer) {
            ((ServerPlayer)p_21240_.m_7639_()).m_6278_(Stats.f_12988_.m_12902_(Stats.f_12929_), Math.round(f * 10.0F));
         }

         f2 = net.minecraftforge.common.ForgeHooks.onLivingDamage(this, p_21240_, f2);
         if (f2 != 0.0F) {
            float f1 = this.m_21223_();
            this.m_21231_().m_19289_(p_21240_, f1, f2);
            this.m_21153_(f1 - f2); // Forge: moved to fix MC-121048
            this.m_7911_(this.m_6103_() - f2);
            this.m_146852_(GameEvent.f_157808_, p_21240_.m_7639_());
         }
      }
   }

   public CombatTracker m_21231_() {
      return this.f_20944_;
   }

   @Nullable
   public LivingEntity m_21232_() {
      if (this.f_20944_.m_19294_() != null) {
         return this.f_20944_.m_19294_();
      } else if (this.f_20888_ != null) {
         return this.f_20888_;
      } else {
         return this.f_20949_ != null ? this.f_20949_ : null;
      }
   }

   public final float m_21233_() {
      return (float)this.m_21133_(Attributes.f_22276_);
   }

   public final int m_21234_() {
      return this.f_19804_.m_135370_(f_20940_);
   }

   public final void m_21317_(int p_21318_) {
      this.f_19804_.m_135381_(f_20940_, p_21318_);
   }

   public final int m_21235_() {
      return this.f_19804_.m_135370_(f_20941_);
   }

   public final void m_21321_(int p_21322_) {
      this.f_19804_.m_135381_(f_20941_, p_21322_);
   }

   private int m_21304_() {
      if (MobEffectUtil.m_19584_(this)) {
         return 6 - (1 + MobEffectUtil.m_19586_(this));
      } else {
         return this.m_21023_(MobEffects.f_19599_) ? 6 + (1 + this.m_21124_(MobEffects.f_19599_).m_19564_()) * 2 : 6;
      }
   }

   public void m_6674_(InteractionHand p_21007_) {
      this.m_21011_(p_21007_, false);
   }

   public void m_21011_(InteractionHand p_21012_, boolean p_21013_) {
      ItemStack stack = this.m_21120_(p_21012_);
      if (!stack.m_41619_() && stack.onEntitySwing(this)) return;
      if (!this.f_20911_ || this.f_20913_ >= this.m_21304_() / 2 || this.f_20913_ < 0) {
         this.f_20913_ = -1;
         this.f_20911_ = true;
         this.f_20912_ = p_21012_;
         if (this.f_19853_ instanceof ServerLevel) {
            ClientboundAnimatePacket clientboundanimatepacket = new ClientboundAnimatePacket(this, p_21012_ == InteractionHand.MAIN_HAND ? 0 : 3);
            ServerChunkCache serverchunkcache = ((ServerLevel)this.f_19853_).m_7726_();
            if (p_21013_) {
               serverchunkcache.m_8394_(this, clientboundanimatepacket);
            } else {
               serverchunkcache.m_8445_(this, clientboundanimatepacket);
            }
         }
      }

   }

   public void m_7822_(byte p_20975_) {
      switch(p_20975_) {
      case 2:
      case 33:
      case 36:
      case 37:
      case 44:
      case 57:
         this.f_20924_ = 1.5F;
         this.f_19802_ = 20;
         this.f_20917_ = 10;
         this.f_20916_ = this.f_20917_;
         this.f_20918_ = 0.0F;
         if (p_20975_ == 33) {
            this.m_5496_(SoundEvents.f_12511_, this.m_6121_(), (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F);
         }

         DamageSource damagesource;
         if (p_20975_ == 37) {
            damagesource = DamageSource.f_19307_;
         } else if (p_20975_ == 36) {
            damagesource = DamageSource.f_19312_;
         } else if (p_20975_ == 44) {
            damagesource = DamageSource.f_19325_;
         } else if (p_20975_ == 57) {
            damagesource = DamageSource.f_146701_;
         } else {
            damagesource = DamageSource.f_19318_;
         }

         SoundEvent soundevent1 = this.m_7975_(damagesource);
         if (soundevent1 != null) {
            this.m_5496_(soundevent1, this.m_6121_(), (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F);
         }

         this.m_6469_(DamageSource.f_19318_, 0.0F);
         this.f_20958_ = damagesource;
         this.f_20930_ = this.f_19853_.m_46467_();
         break;
      case 3:
         SoundEvent soundevent = this.m_5592_();
         if (soundevent != null) {
            this.m_5496_(soundevent, this.m_6121_(), (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F);
         }

         if (!(this instanceof Player)) {
            this.m_21153_(0.0F);
            this.m_6667_(DamageSource.f_19318_);
         }
         break;
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 16:
      case 17:
      case 18:
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 31:
      case 32:
      case 34:
      case 35:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 45:
      case 53:
      case 56:
      case 58:
      case 59:
      default:
         super.m_7822_(p_20975_);
         break;
      case 29:
         this.m_5496_(SoundEvents.f_12346_, 1.0F, 0.8F + this.f_19853_.f_46441_.nextFloat() * 0.4F);
         break;
      case 30:
         this.m_5496_(SoundEvents.f_12347_, 0.8F, 0.8F + this.f_19853_.f_46441_.nextFloat() * 0.4F);
         break;
      case 46:
         int i = 128;

         for(int j = 0; j < 128; ++j) {
            double d0 = (double)j / 127.0D;
            float f = (this.f_19796_.nextFloat() - 0.5F) * 0.2F;
            float f1 = (this.f_19796_.nextFloat() - 0.5F) * 0.2F;
            float f2 = (this.f_19796_.nextFloat() - 0.5F) * 0.2F;
            double d1 = Mth.m_14139_(d0, this.f_19854_, this.m_20185_()) + (this.f_19796_.nextDouble() - 0.5D) * (double)this.m_20205_() * 2.0D;
            double d2 = Mth.m_14139_(d0, this.f_19855_, this.m_20186_()) + this.f_19796_.nextDouble() * (double)this.m_20206_();
            double d3 = Mth.m_14139_(d0, this.f_19856_, this.m_20189_()) + (this.f_19796_.nextDouble() - 0.5D) * (double)this.m_20205_() * 2.0D;
            this.f_19853_.m_7106_(ParticleTypes.f_123760_, d1, d2, d3, (double)f, (double)f1, (double)f2);
         }
         break;
      case 47:
         this.m_21278_(this.m_6844_(EquipmentSlot.MAINHAND));
         break;
      case 48:
         this.m_21278_(this.m_6844_(EquipmentSlot.OFFHAND));
         break;
      case 49:
         this.m_21278_(this.m_6844_(EquipmentSlot.HEAD));
         break;
      case 50:
         this.m_21278_(this.m_6844_(EquipmentSlot.CHEST));
         break;
      case 51:
         this.m_21278_(this.m_6844_(EquipmentSlot.LEGS));
         break;
      case 52:
         this.m_21278_(this.m_6844_(EquipmentSlot.FEET));
         break;
      case 54:
         HoneyBlock.m_54010_(this);
         break;
      case 55:
         this.m_21312_();
         break;
      case 60:
         this.m_147246_();
      }

   }

   private void m_147246_() {
      for(int i = 0; i < 20; ++i) {
         double d0 = this.f_19796_.nextGaussian() * 0.02D;
         double d1 = this.f_19796_.nextGaussian() * 0.02D;
         double d2 = this.f_19796_.nextGaussian() * 0.02D;
         this.f_19853_.m_7106_(ParticleTypes.f_123759_, this.m_20208_(1.0D), this.m_20187_(), this.m_20262_(1.0D), d0, d1, d2);
      }

   }

   private void m_21312_() {
      ItemStack itemstack = this.m_6844_(EquipmentSlot.OFFHAND);
      this.m_8061_(EquipmentSlot.OFFHAND, this.m_6844_(EquipmentSlot.MAINHAND));
      this.m_8061_(EquipmentSlot.MAINHAND, itemstack);
   }

   protected void m_6088_() {
      this.m_6469_(DamageSource.f_19317_, 4.0F);
   }

   protected void m_21203_() {
      int i = this.m_21304_();
      if (this.f_20911_) {
         ++this.f_20913_;
         if (this.f_20913_ >= i) {
            this.f_20913_ = 0;
            this.f_20911_ = false;
         }
      } else {
         this.f_20913_ = 0;
      }

      this.f_20921_ = (float)this.f_20913_ / (float)i;
   }

   @Nullable
   public AttributeInstance m_21051_(Attribute p_21052_) {
      return this.m_21204_().m_22146_(p_21052_);
   }

   public double m_21133_(Attribute p_21134_) {
      return this.m_21204_().m_22181_(p_21134_);
   }

   public double m_21172_(Attribute p_21173_) {
      return this.m_21204_().m_22185_(p_21173_);
   }

   public AttributeMap m_21204_() {
      return this.f_20943_;
   }

   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public ItemStack m_21205_() {
      return this.m_6844_(EquipmentSlot.MAINHAND);
   }

   public ItemStack m_21206_() {
      return this.m_6844_(EquipmentSlot.OFFHAND);
   }

   public boolean m_21055_(Item p_21056_) {
      return this.m_21093_((p_147200_) -> {
         return p_147200_.m_150930_(p_21056_);
      });
   }

   public boolean m_21093_(Predicate<ItemStack> p_21094_) {
      return p_21094_.test(this.m_21205_()) || p_21094_.test(this.m_21206_());
   }

   public ItemStack m_21120_(InteractionHand p_21121_) {
      if (p_21121_ == InteractionHand.MAIN_HAND) {
         return this.m_6844_(EquipmentSlot.MAINHAND);
      } else if (p_21121_ == InteractionHand.OFF_HAND) {
         return this.m_6844_(EquipmentSlot.OFFHAND);
      } else {
         throw new IllegalArgumentException("Invalid hand " + p_21121_);
      }
   }

   public void m_21008_(InteractionHand p_21009_, ItemStack p_21010_) {
      if (p_21009_ == InteractionHand.MAIN_HAND) {
         this.m_8061_(EquipmentSlot.MAINHAND, p_21010_);
      } else {
         if (p_21009_ != InteractionHand.OFF_HAND) {
            throw new IllegalArgumentException("Invalid hand " + p_21009_);
         }

         this.m_8061_(EquipmentSlot.OFFHAND, p_21010_);
      }

   }

   public boolean m_21033_(EquipmentSlot p_21034_) {
      return !this.m_6844_(p_21034_).m_41619_();
   }

   public abstract Iterable<ItemStack> m_6168_();

   public abstract ItemStack m_6844_(EquipmentSlot p_21127_);

   public abstract void m_8061_(EquipmentSlot p_21036_, ItemStack p_21037_);

   protected void m_181122_(ItemStack p_181123_) {
      CompoundTag compoundtag = p_181123_.m_41783_();
      if (compoundtag != null) {
         p_181123_.m_41720_().m_142312_(compoundtag);
      }

   }

   public float m_21207_() {
      Iterable<ItemStack> iterable = this.m_6168_();
      int i = 0;
      int j = 0;

      for(ItemStack itemstack : iterable) {
         if (!itemstack.m_41619_()) {
            ++j;
         }

         ++i;
      }

      return i > 0 ? (float)j / (float)i : 0.0F;
   }

   public void m_6858_(boolean p_21284_) {
      super.m_6858_(p_21284_);
      AttributeInstance attributeinstance = this.m_21051_(Attributes.f_22279_);
      if (attributeinstance.m_22111_(f_20929_) != null) {
         attributeinstance.m_22130_(f_20960_);
      }

      if (p_21284_) {
         attributeinstance.m_22118_(f_20960_);
      }

   }

   protected float m_6121_() {
      return 1.0F;
   }

   public float m_6100_() {
      return this.m_6162_() ? (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.5F : (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F;
   }

   protected boolean m_6107_() {
      return this.m_21224_();
   }

   public void m_7334_(Entity p_21294_) {
      if (!this.m_5803_()) {
         super.m_7334_(p_21294_);
      }

   }

   private void m_21028_(Entity p_21029_) {
      Vec3 vec3;
      if (this.m_146910_()) {
         vec3 = this.m_20182_();
      } else if (!p_21029_.m_146910_() && !this.f_19853_.m_8055_(p_21029_.m_142538_()).m_60620_(BlockTags.f_13075_)) {
         vec3 = p_21029_.m_7688_(this);
      } else {
         double d0 = Math.max(this.m_20186_(), p_21029_.m_20186_());
         vec3 = new Vec3(this.m_20185_(), d0, this.m_20189_());
      }

      this.m_142098_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
   }

   public boolean m_6052_() {
      return this.m_20151_();
   }

   protected float m_6118_() {
      return 0.42F * this.m_20098_();
   }

   public double m_182332_() {
      return this.m_21023_(MobEffects.f_19603_) ? (double)(0.1F * (float)(this.m_21124_(MobEffects.f_19603_).m_19564_() + 1)) : 0.0D;
   }

   protected void m_6135_() {
      double d0 = (double)this.m_6118_() + this.m_182332_();
      Vec3 vec3 = this.m_20184_();
      this.m_20334_(vec3.f_82479_, d0, vec3.f_82481_);
      if (this.m_20142_()) {
         float f = this.m_146908_() * ((float)Math.PI / 180F);
         this.m_20256_(this.m_20184_().m_82520_((double)(-Mth.m_14031_(f) * 0.2F), 0.0D, (double)(Mth.m_14089_(f) * 0.2F)));
      }

      this.f_19812_ = true;
      net.minecraftforge.common.ForgeHooks.onLivingJump(this);
   }

   protected void m_21208_() {
      this.m_20256_(this.m_20184_().m_82520_(0.0D, (double)-0.04F * this.m_21051_(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).m_22135_(), 0.0D));
   }

   protected void m_6197_(net.minecraft.tags.Tag<Fluid> p_21158_) {
      this.m_20256_(this.m_20184_().m_82520_(0.0D, (double)0.04F * this.m_21051_(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).m_22135_(), 0.0D));
   }

   protected float m_6108_() {
      return 0.8F;
   }

   public boolean m_7479_(Fluid p_21070_) {
      return false;
   }

   public void m_7023_(Vec3 p_21280_) {
      if (this.m_6142_() || this.m_6109_()) {
         double d0 = 0.08D;
         AttributeInstance gravity = this.m_21051_(net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY.get());
         boolean flag = this.m_20184_().f_82480_ <= 0.0D;
         if (flag && this.m_21023_(MobEffects.f_19591_)) {
            if (!gravity.m_22109_(SLOW_FALLING)) gravity.m_22118_(SLOW_FALLING);
            this.f_19789_ = 0.0F;
         } else if (gravity.m_22109_(SLOW_FALLING)) {
            gravity.m_22130_(SLOW_FALLING);
         }
         d0 = gravity.m_22135_();

         FluidState fluidstate = this.f_19853_.m_6425_(this.m_142538_());
         if (this.m_20069_() && this.m_6129_() && !this.m_7479_(fluidstate.m_76152_())) {
            double d8 = this.m_20186_();
            float f5 = this.m_20142_() ? 0.9F : this.m_6108_();
            float f6 = 0.02F;
            float f7 = (float)EnchantmentHelper.m_44922_(this);
            if (f7 > 3.0F) {
               f7 = 3.0F;
            }

            if (!this.f_19861_) {
               f7 *= 0.5F;
            }

            if (f7 > 0.0F) {
               f5 += (0.54600006F - f5) * f7 / 3.0F;
               f6 += (this.m_6113_() - f6) * f7 / 3.0F;
            }

            if (this.m_21023_(MobEffects.f_19593_)) {
               f5 = 0.96F;
            }

            f6 *= (float)this.m_21051_(net.minecraftforge.common.ForgeMod.SWIM_SPEED.get()).m_22135_();
            this.m_19920_(f6, p_21280_);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            Vec3 vec36 = this.m_20184_();
            if (this.f_19862_ && this.m_6147_()) {
               vec36 = new Vec3(vec36.f_82479_, 0.2D, vec36.f_82481_);
            }

            this.m_20256_(vec36.m_82542_((double)f5, (double)0.8F, (double)f5));
            Vec3 vec32 = this.m_20994_(d0, flag, this.m_20184_());
            this.m_20256_(vec32);
            if (this.f_19862_ && this.m_20229_(vec32.f_82479_, vec32.f_82480_ + (double)0.6F - this.m_20186_() + d8, vec32.f_82481_)) {
               this.m_20334_(vec32.f_82479_, (double)0.3F, vec32.f_82481_);
            }
         } else if (this.m_20077_() && this.m_6129_() && !this.m_7479_(fluidstate.m_76152_())) {
            double d7 = this.m_20186_();
            this.m_19920_(0.02F, p_21280_);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            if (this.m_20120_(FluidTags.f_13132_) <= this.m_20204_()) {
               this.m_20256_(this.m_20184_().m_82542_(0.5D, (double)0.8F, 0.5D));
               Vec3 vec33 = this.m_20994_(d0, flag, this.m_20184_());
               this.m_20256_(vec33);
            } else {
               this.m_20256_(this.m_20184_().m_82490_(0.5D));
            }

            if (!this.m_20068_()) {
               this.m_20256_(this.m_20184_().m_82520_(0.0D, -d0 / 4.0D, 0.0D));
            }

            Vec3 vec34 = this.m_20184_();
            if (this.f_19862_ && this.m_20229_(vec34.f_82479_, vec34.f_82480_ + (double)0.6F - this.m_20186_() + d7, vec34.f_82481_)) {
               this.m_20334_(vec34.f_82479_, (double)0.3F, vec34.f_82481_);
            }
         } else if (this.m_21255_()) {
            Vec3 vec3 = this.m_20184_();
            if (vec3.f_82480_ > -0.5D) {
               this.f_19789_ = 1.0F;
            }

            Vec3 vec31 = this.m_20154_();
            float f = this.m_146909_() * ((float)Math.PI / 180F);
            double d1 = Math.sqrt(vec31.f_82479_ * vec31.f_82479_ + vec31.f_82481_ * vec31.f_82481_);
            double d3 = vec3.m_165924_();
            double d4 = vec31.m_82553_();
            float f1 = Mth.m_14089_(f);
            f1 = (float)((double)f1 * (double)f1 * Math.min(1.0D, d4 / 0.4D));
            vec3 = this.m_20184_().m_82520_(0.0D, d0 * (-1.0D + (double)f1 * 0.75D), 0.0D);
            if (vec3.f_82480_ < 0.0D && d1 > 0.0D) {
               double d5 = vec3.f_82480_ * -0.1D * (double)f1;
               vec3 = vec3.m_82520_(vec31.f_82479_ * d5 / d1, d5, vec31.f_82481_ * d5 / d1);
            }

            if (f < 0.0F && d1 > 0.0D) {
               double d9 = d3 * (double)(-Mth.m_14031_(f)) * 0.04D;
               vec3 = vec3.m_82520_(-vec31.f_82479_ * d9 / d1, d9 * 3.2D, -vec31.f_82481_ * d9 / d1);
            }

            if (d1 > 0.0D) {
               vec3 = vec3.m_82520_((vec31.f_82479_ / d1 * d3 - vec3.f_82479_) * 0.1D, 0.0D, (vec31.f_82481_ / d1 * d3 - vec3.f_82481_) * 0.1D);
            }

            this.m_20256_(vec3.m_82542_((double)0.99F, (double)0.98F, (double)0.99F));
            this.m_6478_(MoverType.SELF, this.m_20184_());
            if (this.f_19862_ && !this.f_19853_.f_46443_) {
               double d10 = this.m_20184_().m_165924_();
               double d6 = d3 - d10;
               float f2 = (float)(d6 * 10.0D - 3.0D);
               if (f2 > 0.0F) {
                  this.m_5496_(this.m_5896_((int)f2), 1.0F, 1.0F);
                  this.m_6469_(DamageSource.f_19316_, f2);
               }
            }

            if (this.f_19861_ && !this.f_19853_.f_46443_) {
               this.m_20115_(7, false);
            }
         } else {
            BlockPos blockpos = this.m_20099_();
            float f3 = this.f_19853_.m_8055_(this.m_20099_()).getFriction(f_19853_, this.m_20099_(), this);
            float f4 = this.f_19861_ ? f3 * 0.91F : 0.91F;
            Vec3 vec35 = this.m_21074_(p_21280_, f3);
            double d2 = vec35.f_82480_;
            if (this.m_21023_(MobEffects.f_19620_)) {
               d2 += (0.05D * (double)(this.m_21124_(MobEffects.f_19620_).m_19564_() + 1) - vec35.f_82480_) * 0.2D;
               this.f_19789_ = 0.0F;
            } else if (this.f_19853_.f_46443_ && !this.f_19853_.m_46805_(blockpos)) {
               if (this.m_20186_() > (double)this.f_19853_.m_141937_()) {
                  d2 = -0.1D;
               } else {
                  d2 = 0.0D;
               }
            } else if (!this.m_20068_()) {
               d2 -= d0;
            }

            if (this.m_147223_()) {
               this.m_20334_(vec35.f_82479_, d2, vec35.f_82481_);
            } else {
               this.m_20334_(vec35.f_82479_ * (double)f4, d2 * (double)0.98F, vec35.f_82481_ * (double)f4);
            }
         }
      }

      this.m_21043_(this, this instanceof FlyingAnimal);
   }

   public void m_21043_(LivingEntity p_21044_, boolean p_21045_) {
      p_21044_.f_20923_ = p_21044_.f_20924_;
      double d0 = p_21044_.m_20185_() - p_21044_.f_19854_;
      double d1 = p_21045_ ? p_21044_.m_20186_() - p_21044_.f_19855_ : 0.0D;
      double d2 = p_21044_.m_20189_() - p_21044_.f_19856_;
      float f = (float)Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2) * 4.0F;
      if (f > 1.0F) {
         f = 1.0F;
      }

      p_21044_.f_20924_ += (f - p_21044_.f_20924_) * 0.4F;
      p_21044_.f_20925_ += p_21044_.f_20924_;
   }

   public Vec3 m_21074_(Vec3 p_21075_, float p_21076_) {
      this.m_19920_(this.m_21330_(p_21076_), p_21075_);
      this.m_20256_(this.m_21297_(this.m_20184_()));
      this.m_6478_(MoverType.SELF, this.m_20184_());
      Vec3 vec3 = this.m_20184_();
      if ((this.f_19862_ || this.f_20899_) && (this.m_6147_() || this.m_146900_().m_60713_(Blocks.f_152499_) && PowderSnowBlock.m_154255_(this))) {
         vec3 = new Vec3(vec3.f_82479_, 0.2D, vec3.f_82481_);
      }

      return vec3;
   }

   public Vec3 m_20994_(double p_20995_, boolean p_20996_, Vec3 p_20997_) {
      if (!this.m_20068_() && !this.m_20142_()) {
         double d0;
         if (p_20996_ && Math.abs(p_20997_.f_82480_ - 0.005D) >= 0.003D && Math.abs(p_20997_.f_82480_ - p_20995_ / 16.0D) < 0.003D) {
            d0 = -0.003D;
         } else {
            d0 = p_20997_.f_82480_ - p_20995_ / 16.0D;
         }

         return new Vec3(p_20997_.f_82479_, d0, p_20997_.f_82481_);
      } else {
         return p_20997_;
      }
   }

   private Vec3 m_21297_(Vec3 p_21298_) {
      if (this.m_6147_()) {
         this.f_19789_ = 0.0F;
         float f = 0.15F;
         double d0 = Mth.m_14008_(p_21298_.f_82479_, (double)-0.15F, (double)0.15F);
         double d1 = Mth.m_14008_(p_21298_.f_82481_, (double)-0.15F, (double)0.15F);
         double d2 = Math.max(p_21298_.f_82480_, (double)-0.15F);
         if (d2 < 0.0D && !this.m_146900_().isScaffolding(this) && this.m_5791_() && this instanceof Player) {
            d2 = 0.0D;
         }

         p_21298_ = new Vec3(d0, d2, d1);
      }

      return p_21298_;
   }

   private float m_21330_(float p_21331_) {
      return this.f_19861_ ? this.m_6113_() * (0.21600002F / (p_21331_ * p_21331_ * p_21331_)) : this.f_20887_;
   }

   public float m_6113_() {
      return this.f_20953_;
   }

   public void m_7910_(float p_21320_) {
      this.f_20953_ = p_21320_;
   }

   public boolean m_7327_(Entity p_20970_) {
      this.m_21335_(p_20970_);
      return false;
   }

   public void m_8119_() {
      if (net.minecraftforge.common.ForgeHooks.onLivingUpdate(this)) return;
      super.m_8119_();
      this.m_21329_();
      this.m_21333_();
      if (!this.f_19853_.f_46443_) {
         int i = this.m_21234_();
         if (i > 0) {
            if (this.f_20914_ <= 0) {
               this.f_20914_ = 20 * (30 - i);
            }

            --this.f_20914_;
            if (this.f_20914_ <= 0) {
               this.m_21317_(i - 1);
            }
         }

         int j = this.m_21235_();
         if (j > 0) {
            if (this.f_20915_ <= 0) {
               this.f_20915_ = 20 * (30 - j);
            }

            --this.f_20915_;
            if (this.f_20915_ <= 0) {
               this.m_21321_(j - 1);
            }
         }

         this.m_21315_();
         if (this.f_19797_ % 20 == 0) {
            this.m_21231_().m_19296_();
         }

         if (this.m_5803_() && !this.m_21334_()) {
            this.m_5796_();
         }
      }

      this.m_8107_();
      double d1 = this.m_20185_() - this.f_19854_;
      double d0 = this.m_20189_() - this.f_19856_;
      float f = (float)(d1 * d1 + d0 * d0);
      float f1 = this.f_20883_;
      float f2 = 0.0F;
      this.f_20892_ = this.f_20893_;
      float f3 = 0.0F;
      if (f > 0.0025000002F) {
         f3 = 1.0F;
         f2 = (float)Math.sqrt((double)f) * 3.0F;
         float f4 = (float)Mth.m_14136_(d0, d1) * (180F / (float)Math.PI) - 90.0F;
         float f5 = Mth.m_14154_(Mth.m_14177_(this.m_146908_()) - f4);
         if (95.0F < f5 && f5 < 265.0F) {
            f1 = f4 - 180.0F;
         } else {
            f1 = f4;
         }
      }

      if (this.f_20921_ > 0.0F) {
         f1 = this.m_146908_();
      }

      if (!this.f_19861_) {
         f3 = 0.0F;
      }

      this.f_20893_ += (f3 - this.f_20893_) * 0.3F;
      this.f_19853_.m_46473_().m_6180_("headTurn");
      f2 = this.m_5632_(f1, f2);
      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("rangeChecks");

      while(this.m_146908_() - this.f_19859_ < -180.0F) {
         this.f_19859_ -= 360.0F;
      }

      while(this.m_146908_() - this.f_19859_ >= 180.0F) {
         this.f_19859_ += 360.0F;
      }

      while(this.f_20883_ - this.f_20884_ < -180.0F) {
         this.f_20884_ -= 360.0F;
      }

      while(this.f_20883_ - this.f_20884_ >= 180.0F) {
         this.f_20884_ += 360.0F;
      }

      while(this.m_146909_() - this.f_19860_ < -180.0F) {
         this.f_19860_ -= 360.0F;
      }

      while(this.m_146909_() - this.f_19860_ >= 180.0F) {
         this.f_19860_ += 360.0F;
      }

      while(this.f_20885_ - this.f_20886_ < -180.0F) {
         this.f_20886_ -= 360.0F;
      }

      while(this.f_20885_ - this.f_20886_ >= 180.0F) {
         this.f_20886_ += 360.0F;
      }

      this.f_19853_.m_46473_().m_7238_();
      this.f_20894_ += f2;
      if (this.m_21255_()) {
         ++this.f_20937_;
      } else {
         this.f_20937_ = 0;
      }

      if (this.m_5803_()) {
         this.m_146926_(0.0F);
      }

   }

   private void m_21315_() {
      Map<EquipmentSlot, ItemStack> map = this.m_21319_();
      if (map != null) {
         this.m_21091_(map);
         if (!map.isEmpty()) {
            this.m_21142_(map);
         }
      }

   }

   @Nullable
   private Map<EquipmentSlot, ItemStack> m_21319_() {
      Map<EquipmentSlot, ItemStack> map = null;

      for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
         ItemStack itemstack;
         switch(equipmentslot.m_20743_()) {
         case HAND:
            itemstack = this.m_21244_(equipmentslot);
            break;
         case ARMOR:
            itemstack = this.m_21198_(equipmentslot);
            break;
         default:
            continue;
         }

         ItemStack itemstack1 = this.m_6844_(equipmentslot);
         if (!ItemStack.m_41728_(itemstack1, itemstack)) {
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent(this, equipmentslot, itemstack, itemstack1));
            if (map == null) {
               map = Maps.newEnumMap(EquipmentSlot.class);
            }

            map.put(equipmentslot, itemstack1);
            if (!itemstack.m_41619_()) {
               this.m_21204_().m_22161_(itemstack.m_41638_(equipmentslot));
            }

            if (!itemstack1.m_41619_()) {
               this.m_21204_().m_22178_(itemstack1.m_41638_(equipmentslot));
            }
         }
      }

      return map;
   }

   private void m_21091_(Map<EquipmentSlot, ItemStack> p_21092_) {
      ItemStack itemstack = p_21092_.get(EquipmentSlot.MAINHAND);
      ItemStack itemstack1 = p_21092_.get(EquipmentSlot.OFFHAND);
      if (itemstack != null && itemstack1 != null && ItemStack.m_41728_(itemstack, this.m_21244_(EquipmentSlot.OFFHAND)) && ItemStack.m_41728_(itemstack1, this.m_21244_(EquipmentSlot.MAINHAND))) {
         ((ServerLevel)this.f_19853_).m_7726_().m_8445_(this, new ClientboundEntityEventPacket(this, (byte)55));
         p_21092_.remove(EquipmentSlot.MAINHAND);
         p_21092_.remove(EquipmentSlot.OFFHAND);
         this.m_21168_(EquipmentSlot.MAINHAND, itemstack.m_41777_());
         this.m_21168_(EquipmentSlot.OFFHAND, itemstack1.m_41777_());
      }

   }

   private void m_21142_(Map<EquipmentSlot, ItemStack> p_21143_) {
      List<Pair<EquipmentSlot, ItemStack>> list = Lists.newArrayListWithCapacity(p_21143_.size());
      p_21143_.forEach((p_147204_, p_147205_) -> {
         ItemStack itemstack = p_147205_.m_41777_();
         list.add(Pair.of(p_147204_, itemstack));
         switch(p_147204_.m_20743_()) {
         case HAND:
            this.m_21168_(p_147204_, itemstack);
            break;
         case ARMOR:
            this.m_21128_(p_147204_, itemstack);
         }

      });
      ((ServerLevel)this.f_19853_).m_7726_().m_8445_(this, new ClientboundSetEquipmentPacket(this.m_142049_(), list));
   }

   private ItemStack m_21198_(EquipmentSlot p_21199_) {
      return this.f_20947_.get(p_21199_.m_20749_());
   }

   private void m_21128_(EquipmentSlot p_21129_, ItemStack p_21130_) {
      this.f_20947_.set(p_21129_.m_20749_(), p_21130_);
   }

   private ItemStack m_21244_(EquipmentSlot p_21245_) {
      return this.f_20946_.get(p_21245_.m_20749_());
   }

   private void m_21168_(EquipmentSlot p_21169_, ItemStack p_21170_) {
      this.f_20946_.set(p_21169_.m_20749_(), p_21170_);
   }

   protected float m_5632_(float p_21260_, float p_21261_) {
      float f = Mth.m_14177_(p_21260_ - this.f_20883_);
      this.f_20883_ += f * 0.3F;
      float f1 = Mth.m_14177_(this.m_146908_() - this.f_20883_);
      boolean flag = f1 < -90.0F || f1 >= 90.0F;
      if (f1 < -75.0F) {
         f1 = -75.0F;
      }

      if (f1 >= 75.0F) {
         f1 = 75.0F;
      }

      this.f_20883_ = this.m_146908_() - f1;
      if (f1 * f1 > 2500.0F) {
         this.f_20883_ += f1 * 0.2F;
      }

      if (flag) {
         p_21261_ *= -1.0F;
      }

      return p_21261_;
   }

   public void m_8107_() {
      if (this.f_20954_ > 0) {
         --this.f_20954_;
      }

      if (this.m_6109_()) {
         this.f_20903_ = 0;
         this.m_20167_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

      if (this.f_20903_ > 0) {
         double d0 = this.m_20185_() + (this.f_20904_ - this.m_20185_()) / (double)this.f_20903_;
         double d2 = this.m_20186_() + (this.f_20905_ - this.m_20186_()) / (double)this.f_20903_;
         double d4 = this.m_20189_() + (this.f_20906_ - this.m_20189_()) / (double)this.f_20903_;
         double d6 = Mth.m_14175_(this.f_20907_ - (double)this.m_146908_());
         this.m_146922_(this.m_146908_() + (float)d6 / (float)this.f_20903_);
         this.m_146926_(this.m_146909_() + (float)(this.f_20908_ - (double)this.m_146909_()) / (float)this.f_20903_);
         --this.f_20903_;
         this.m_6034_(d0, d2, d4);
         this.m_19915_(this.m_146908_(), this.m_146909_());
      } else if (!this.m_6142_()) {
         this.m_20256_(this.m_20184_().m_82490_(0.98D));
      }

      if (this.f_20934_ > 0) {
         this.f_20885_ = (float)((double)this.f_20885_ + Mth.m_14175_(this.f_20933_ - (double)this.f_20885_) / (double)this.f_20934_);
         --this.f_20934_;
      }

      Vec3 vec3 = this.m_20184_();
      double d1 = vec3.f_82479_;
      double d3 = vec3.f_82480_;
      double d5 = vec3.f_82481_;
      if (Math.abs(vec3.f_82479_) < 0.003D) {
         d1 = 0.0D;
      }

      if (Math.abs(vec3.f_82480_) < 0.003D) {
         d3 = 0.0D;
      }

      if (Math.abs(vec3.f_82481_) < 0.003D) {
         d5 = 0.0D;
      }

      this.m_20334_(d1, d3, d5);
      this.f_19853_.m_46473_().m_6180_("ai");
      if (this.m_6107_()) {
         this.f_20899_ = false;
         this.f_20900_ = 0.0F;
         this.f_20902_ = 0.0F;
      } else if (this.m_6142_()) {
         this.f_19853_.m_46473_().m_6180_("newAi");
         this.m_6140_();
         this.f_19853_.m_46473_().m_7238_();
      }

      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("jump");
      if (this.f_20899_ && this.m_6129_()) {
         double d7;
         if (this.m_20077_()) {
            d7 = this.m_20120_(FluidTags.f_13132_);
         } else {
            d7 = this.m_20120_(FluidTags.f_13131_);
         }

         boolean flag1 = this.m_20069_() && d7 > 0.0D;
         double d8 = this.m_20204_();
         if (!flag1 || this.f_19861_ && !(d7 > d8)) {
            if (!this.m_20077_() || this.f_19861_ && !(d7 > d8)) {
               if ((this.f_19861_ || flag1 && d7 <= d8) && this.f_20954_ == 0) {
                  this.m_6135_();
                  this.f_20954_ = 10;
               }
            } else {
               this.m_6197_(FluidTags.f_13132_);
            }
         } else {
            this.m_6197_(FluidTags.f_13131_);
         }
      } else {
         this.f_20954_ = 0;
      }

      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("travel");
      this.f_20900_ *= 0.98F;
      this.f_20902_ *= 0.98F;
      this.m_21323_();
      AABB aabb = this.m_142469_();
      this.m_7023_(new Vec3((double)this.f_20900_, (double)this.f_20901_, (double)this.f_20902_));
      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("freezing");
      boolean flag = this.m_6095_().m_20609_(EntityTypeTags.f_144295_);
      if (!this.f_19853_.f_46443_ && !this.m_21224_()) {
         int i = this.m_146888_();
         if (this.f_146808_ && this.m_142079_()) {
            this.m_146917_(Math.min(this.m_146891_(), i + 1));
         } else {
            this.m_146917_(Math.max(0, i - 2));
         }
      }

      this.m_147225_();
      this.m_147226_();
      if (!this.f_19853_.f_46443_ && this.f_19797_ % 40 == 0 && this.m_146890_() && this.m_142079_()) {
         int j = flag ? 5 : 1;
         this.m_6469_(DamageSource.f_146701_, (float)j);
      }

      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("push");
      if (this.f_20938_ > 0) {
         --this.f_20938_;
         this.m_21071_(aabb, this.m_142469_());
      }

      this.m_6138_();
      this.f_19853_.m_46473_().m_7238_();
      if (!this.f_19853_.f_46443_ && this.m_6126_() && this.m_20071_()) {
         this.m_6469_(DamageSource.f_19312_, 1.0F);
      }

   }

   public boolean m_6126_() {
      return false;
   }

   private void m_21323_() {
      boolean flag = this.m_20291_(7);
      if (flag && !this.f_19861_ && !this.m_20159_() && !this.m_21023_(MobEffects.f_19620_)) {
         ItemStack itemstack = this.m_6844_(EquipmentSlot.CHEST);
         flag = itemstack.canElytraFly(this) && itemstack.elytraFlightTick(this, this.f_20937_);
         if (false) //Forge: Moved to ElytraItem
         if (itemstack.m_150930_(Items.f_42741_) && ElytraItem.m_41140_(itemstack)) {
            flag = true;
            int i = this.f_20937_ + 1;
            if (!this.f_19853_.f_46443_ && i % 10 == 0) {
               int j = i / 10;
               if (j % 2 == 0) {
                  itemstack.m_41622_(1, this, (p_147232_) -> {
                     p_147232_.m_21166_(EquipmentSlot.CHEST);
                  });
               }

               this.m_146850_(GameEvent.f_157807_);
            }
         } else {
            flag = false;
         }
      } else {
         flag = false;
      }

      if (!this.f_19853_.f_46443_) {
         this.m_20115_(7, flag);
      }

   }

   protected void m_6140_() {
   }

   protected void m_6138_() {
      List<Entity> list = this.f_19853_.m_6249_(this, this.m_142469_(), EntitySelector.m_20421_(this));
      if (!list.isEmpty()) {
         int i = this.f_19853_.m_46469_().m_46215_(GameRules.f_46149_);
         if (i > 0 && list.size() > i - 1 && this.f_19796_.nextInt(4) == 0) {
            int j = 0;

            for(int k = 0; k < list.size(); ++k) {
               if (!list.get(k).m_20159_()) {
                  ++j;
               }
            }

            if (j > i - 1) {
               this.m_6469_(DamageSource.f_19311_, 6.0F);
            }
         }

         for(int l = 0; l < list.size(); ++l) {
            Entity entity = list.get(l);
            this.m_7324_(entity);
         }
      }

   }

   protected void m_21071_(AABB p_21072_, AABB p_21073_) {
      AABB aabb = p_21072_.m_82367_(p_21073_);
      List<Entity> list = this.f_19853_.m_45933_(this, aabb);
      if (!list.isEmpty()) {
         for(int i = 0; i < list.size(); ++i) {
            Entity entity = list.get(i);
            if (entity instanceof LivingEntity) {
               this.m_6727_((LivingEntity)entity);
               this.f_20938_ = 0;
               this.m_20256_(this.m_20184_().m_82490_(-0.2D));
               break;
            }
         }
      } else if (this.f_19862_) {
         this.f_20938_ = 0;
      }

      if (!this.f_19853_.f_46443_ && this.f_20938_ <= 0) {
         this.m_21155_(4, false);
      }

   }

   protected void m_7324_(Entity p_20971_) {
      p_20971_.m_7334_(this);
   }

   protected void m_6727_(LivingEntity p_21277_) {
   }

   public void m_21326_(int p_21327_) {
      this.f_20938_ = p_21327_;
      if (!this.f_19853_.f_46443_) {
         this.m_21155_(4, true);
      }

   }

   public boolean m_21209_() {
      return (this.f_19804_.m_135370_(f_20909_) & 4) != 0;
   }

   public void m_8127_() {
      Entity entity = this.m_20202_();
      super.m_8127_();
      if (entity != null && entity != this.m_20202_() && !this.f_19853_.f_46443_) {
         this.m_21028_(entity);
      }

   }

   public void m_6083_() {
      super.m_6083_();
      this.f_20892_ = this.f_20893_;
      this.f_20893_ = 0.0F;
      this.f_19789_ = 0.0F;
   }

   public void m_6453_(double p_20977_, double p_20978_, double p_20979_, float p_20980_, float p_20981_, int p_20982_, boolean p_20983_) {
      this.f_20904_ = p_20977_;
      this.f_20905_ = p_20978_;
      this.f_20906_ = p_20979_;
      this.f_20907_ = (double)p_20980_;
      this.f_20908_ = (double)p_20981_;
      this.f_20903_ = p_20982_;
   }

   public void m_6541_(float p_21005_, int p_21006_) {
      this.f_20933_ = (double)p_21005_;
      this.f_20934_ = p_21006_;
   }

   public void m_6862_(boolean p_21314_) {
      this.f_20899_ = p_21314_;
   }

   public void m_21053_(ItemEntity p_21054_) {
      Player player = p_21054_.m_32057_() != null ? this.f_19853_.m_46003_(p_21054_.m_32057_()) : null;
      if (player instanceof ServerPlayer) {
         CriteriaTriggers.f_10564_.m_44363_((ServerPlayer)player, p_21054_.m_32055_(), this);
      }

   }

   public void m_7938_(Entity p_21030_, int p_21031_) {
      if (!p_21030_.m_146910_() && !this.f_19853_.f_46443_ && (p_21030_ instanceof ItemEntity || p_21030_ instanceof AbstractArrow || p_21030_ instanceof ExperienceOrb)) {
         ((ServerLevel)this.f_19853_).m_7726_().m_8445_(p_21030_, new ClientboundTakeItemEntityPacket(p_21030_.m_142049_(), this.m_142049_(), p_21031_));
      }

   }

   public boolean m_142582_(Entity p_147185_) {
      if (p_147185_.f_19853_ != this.f_19853_) {
         return false;
      } else {
         Vec3 vec3 = new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
         Vec3 vec31 = new Vec3(p_147185_.m_20185_(), p_147185_.m_20188_(), p_147185_.m_20189_());
         if (vec31.m_82554_(vec3) > 128.0D) {
            return false;
         } else {
            return this.f_19853_.m_45547_(new ClipContext(vec3, vec31, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)).m_6662_() == HitResult.Type.MISS;
         }
      }
   }

   public float m_5675_(float p_21286_) {
      return p_21286_ == 1.0F ? this.f_20885_ : Mth.m_14179_(p_21286_, this.f_20886_, this.f_20885_);
   }

   public float m_21324_(float p_21325_) {
      float f = this.f_20921_ - this.f_20920_;
      if (f < 0.0F) {
         ++f;
      }

      return this.f_20920_ + f * p_21325_;
   }

   public boolean m_6142_() {
      return !this.f_19853_.f_46443_;
   }

   public boolean m_6087_() {
      return !this.m_146910_();
   }

   public boolean m_6094_() {
      return this.m_6084_() && !this.m_5833_() && !this.m_6147_();
   }

   protected void m_5834_() {
      this.f_19864_ = this.f_19796_.nextDouble() >= this.m_21133_(Attributes.f_22278_);
   }

   public float m_6080_() {
      return this.f_20885_;
   }

   public void m_5616_(float p_21306_) {
      this.f_20885_ = p_21306_;
   }

   public void m_5618_(float p_21309_) {
      this.f_20883_ = p_21309_;
   }

   protected Vec3 m_7643_(Direction.Axis p_21085_, BlockUtil.FoundRectangle p_21086_) {
      return m_21289_(super.m_7643_(p_21085_, p_21086_));
   }

   public static Vec3 m_21289_(Vec3 p_21290_) {
      return new Vec3(p_21290_.f_82479_, p_21290_.f_82480_, 0.0D);
   }

   public float m_6103_() {
      return this.f_20955_;
   }

   public void m_7911_(float p_21328_) {
      if (p_21328_ < 0.0F) {
         p_21328_ = 0.0F;
      }

      this.f_20955_ = p_21328_;
   }

   public void m_8108_() {
   }

   public void m_8098_() {
   }

   protected void m_21210_() {
      this.f_20948_ = true;
   }

   public abstract HumanoidArm m_5737_();

   public boolean m_6117_() {
      return (this.f_19804_.m_135370_(f_20909_) & 1) > 0;
   }

   public InteractionHand m_7655_() {
      return (this.f_19804_.m_135370_(f_20909_) & 2) > 0 ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
   }

   private void m_21329_() {
      if (this.m_6117_()) {
         ItemStack itemStack = this.m_21120_(this.m_7655_());
         if (net.minecraftforge.common.ForgeHooks.canContinueUsing(this.f_20935_, itemStack)) this.f_20935_ = itemStack;
         if (itemStack == this.f_20935_) {

            if (!this.f_20935_.m_41619_()) {
              f_20936_ = net.minecraftforge.event.ForgeEventFactory.onItemUseTick(this, f_20935_, f_20936_);
              if (f_20936_ > 0)
                 f_20935_.onUsingTick(this, f_20936_);
            }

            this.m_142106_(this.f_20935_);
         } else {
            this.m_5810_();
         }
      }

   }

   protected void m_142106_(ItemStack p_147201_) {
      p_147201_.m_41731_(this.f_19853_, this, this.m_21212_());
      if (this.m_21332_()) {
         this.m_21137_(p_147201_, 5);
      }

      if (--this.f_20936_ == 0 && !this.f_19853_.f_46443_ && !p_147201_.m_41781_()) {
         this.m_8095_();
      }

   }

   private boolean m_21332_() {
      int i = this.m_21212_();
      FoodProperties foodproperties = this.f_20935_.m_41720_().m_41473_();
      boolean flag = foodproperties != null && foodproperties.m_38748_();
      flag = flag | i <= this.f_20935_.m_41779_() - 7;
      return flag && i % 4 == 0;
   }

   private void m_21333_() {
      this.f_20932_ = this.f_20931_;
      if (this.m_6067_()) {
         this.f_20931_ = Math.min(1.0F, this.f_20931_ + 0.09F);
      } else {
         this.f_20931_ = Math.max(0.0F, this.f_20931_ - 0.09F);
      }

   }

   protected void m_21155_(int p_21156_, boolean p_21157_) {
      int i = this.f_19804_.m_135370_(f_20909_);
      if (p_21157_) {
         i = i | p_21156_;
      } else {
         i = i & ~p_21156_;
      }

      this.f_19804_.m_135381_(f_20909_, (byte)i);
   }

   public void m_6672_(InteractionHand p_21159_) {
      ItemStack itemstack = this.m_21120_(p_21159_);
      if (!itemstack.m_41619_() && !this.m_6117_()) {
         int duration = net.minecraftforge.event.ForgeEventFactory.onItemUseStart(this, itemstack, itemstack.m_41779_());
         if (duration <= 0) return;
         this.f_20935_ = itemstack;
         this.f_20936_ = duration;
         if (!this.f_19853_.f_46443_) {
            this.m_21155_(1, true);
            this.m_21155_(2, p_21159_ == InteractionHand.OFF_HAND);
         }

      }
   }

   public void m_7350_(EntityDataAccessor<?> p_21104_) {
      super.m_7350_(p_21104_);
      if (f_20942_.equals(p_21104_)) {
         if (this.f_19853_.f_46443_) {
            this.m_21257_().ifPresent(this::m_21080_);
         }
      } else if (f_20909_.equals(p_21104_) && this.f_19853_.f_46443_) {
         if (this.m_6117_() && this.f_20935_.m_41619_()) {
            this.f_20935_ = this.m_21120_(this.m_7655_());
            if (!this.f_20935_.m_41619_()) {
               this.f_20936_ = this.f_20935_.m_41779_();
            }
         } else if (!this.m_6117_() && !this.f_20935_.m_41619_()) {
            this.f_20935_ = ItemStack.f_41583_;
            this.f_20936_ = 0;
         }
      }

   }

   public void m_7618_(EntityAnchorArgument.Anchor p_21078_, Vec3 p_21079_) {
      super.m_7618_(p_21078_, p_21079_);
      this.f_20886_ = this.f_20885_;
      this.f_20883_ = this.f_20885_;
      this.f_20884_ = this.f_20883_;
   }

   protected void m_21137_(ItemStack p_21138_, int p_21139_) {
      if (!p_21138_.m_41619_() && this.m_6117_()) {
         if (p_21138_.m_41780_() == UseAnim.DRINK) {
            this.m_5496_(this.m_7838_(p_21138_), 0.5F, this.f_19853_.f_46441_.nextFloat() * 0.1F + 0.9F);
         }

         if (p_21138_.m_41780_() == UseAnim.EAT) {
            this.m_21060_(p_21138_, p_21139_);
            this.m_5496_(this.m_7866_(p_21138_), 0.5F + 0.5F * (float)this.f_19796_.nextInt(2), (this.f_19796_.nextFloat() - this.f_19796_.nextFloat()) * 0.2F + 1.0F);
         }

      }
   }

   private void m_21060_(ItemStack p_21061_, int p_21062_) {
      for(int i = 0; i < p_21062_; ++i) {
         Vec3 vec3 = new Vec3(((double)this.f_19796_.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
         vec3 = vec3.m_82496_(-this.m_146909_() * ((float)Math.PI / 180F));
         vec3 = vec3.m_82524_(-this.m_146908_() * ((float)Math.PI / 180F));
         double d0 = (double)(-this.f_19796_.nextFloat()) * 0.6D - 0.3D;
         Vec3 vec31 = new Vec3(((double)this.f_19796_.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
         vec31 = vec31.m_82496_(-this.m_146909_() * ((float)Math.PI / 180F));
         vec31 = vec31.m_82524_(-this.m_146908_() * ((float)Math.PI / 180F));
         vec31 = vec31.m_82520_(this.m_20185_(), this.m_20188_(), this.m_20189_());
         if (this.f_19853_ instanceof ServerLevel) //Forge: Fix MC-2518 spawnParticle is nooped on server, need to use server specific variant
             ((ServerLevel)this.f_19853_).m_8767_(new ItemParticleOption(ParticleTypes.f_123752_, p_21061_), vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, 1, vec3.f_82479_, vec3.f_82480_ + 0.05D, vec3.f_82481_, 0.0D);
         else
         this.f_19853_.m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, p_21061_), vec31.f_82479_, vec31.f_82480_, vec31.f_82481_, vec3.f_82479_, vec3.f_82480_ + 0.05D, vec3.f_82481_);
      }

   }

   protected void m_8095_() {
      InteractionHand interactionhand = this.m_7655_();
      if (!this.f_20935_.equals(this.m_21120_(interactionhand))) {
         this.m_21253_();
      } else {
         if (!this.f_20935_.m_41619_() && this.m_6117_()) {
            this.m_21137_(this.f_20935_, 16);
            ItemStack copy = this.f_20935_.m_41777_();
            ItemStack itemstack = net.minecraftforge.event.ForgeEventFactory.onItemUseFinish(this, copy, m_21212_(), this.f_20935_.m_41671_(this.f_19853_, this));
            if (itemstack != this.f_20935_) {
               this.m_21008_(interactionhand, itemstack);
            }

            this.m_5810_();
         }

      }
   }

   public ItemStack m_21211_() {
      return this.f_20935_;
   }

   public int m_21212_() {
      return this.f_20936_;
   }

   public int m_21252_() {
      return this.m_6117_() ? this.f_20935_.m_41779_() - this.m_21212_() : 0;
   }

   public void m_21253_() {
      if (!this.f_20935_.m_41619_()) {
         if (!net.minecraftforge.event.ForgeEventFactory.onUseItemStop(this, f_20935_, this.m_21212_())) {
            ItemStack copy = this instanceof Player ? f_20935_.m_41777_() : null;
         this.f_20935_.m_41674_(this.f_19853_, this, this.m_21212_());
           if (copy != null && f_20935_.m_41619_()) net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem((Player)this, copy, m_7655_());
         }
         if (this.f_20935_.m_41781_()) {
            this.m_21329_();
         }
      }

      this.m_5810_();
   }

   public void m_5810_() {
      if (!this.f_19853_.f_46443_) {
         this.m_21155_(1, false);
      }

      this.f_20935_ = ItemStack.f_41583_;
      this.f_20936_ = 0;
   }

   public boolean m_21254_() {
      if (this.m_6117_() && !this.f_20935_.m_41619_()) {
         Item item = this.f_20935_.m_41720_();
         if (item.m_6164_(this.f_20935_) != UseAnim.BLOCK) {
            return false;
         } else {
            return item.m_8105_(this.f_20935_) - this.f_20936_ >= 5;
         }
      } else {
         return false;
      }
   }

   public boolean m_5791_() {
      return this.m_6144_();
   }

   public boolean m_21255_() {
      return this.m_20291_(7);
   }

   public boolean m_6067_() {
      return super.m_6067_() || !this.m_21255_() && this.m_20089_() == Pose.FALL_FLYING;
   }

   public int m_21256_() {
      return this.f_20937_;
   }

   public boolean m_20984_(double p_20985_, double p_20986_, double p_20987_, boolean p_20988_) {
      double d0 = this.m_20185_();
      double d1 = this.m_20186_();
      double d2 = this.m_20189_();
      double d3 = p_20986_;
      boolean flag = false;
      BlockPos blockpos = new BlockPos(p_20985_, p_20986_, p_20987_);
      Level level = this.f_19853_;
      if (level.m_46805_(blockpos)) {
         boolean flag1 = false;

         while(!flag1 && blockpos.m_123342_() > level.m_141937_()) {
            BlockPos blockpos1 = blockpos.m_7495_();
            BlockState blockstate = level.m_8055_(blockpos1);
            if (blockstate.m_60767_().m_76334_()) {
               flag1 = true;
            } else {
               --d3;
               blockpos = blockpos1;
            }
         }

         if (flag1) {
            this.m_6021_(p_20985_, d3, p_20987_);
            if (level.m_45786_(this) && !level.m_46855_(this.m_142469_())) {
               flag = true;
            }
         }
      }

      if (!flag) {
         this.m_6021_(d0, d1, d2);
         return false;
      } else {
         if (p_20988_) {
            level.m_7605_(this, (byte)46);
         }

         if (this instanceof PathfinderMob) {
            ((PathfinderMob)this).m_21573_().m_26573_();
         }

         return true;
      }
   }

   public boolean m_5801_() {
      return true;
   }

   public boolean m_5789_() {
      return true;
   }

   public void m_6818_(BlockPos p_21082_, boolean p_21083_) {
   }

   public boolean m_7066_(ItemStack p_21249_) {
      return false;
   }

   public Packet<?> m_5654_() {
      return new ClientboundAddMobPacket(this);
   }

   public EntityDimensions m_6972_(Pose p_21047_) {
      return p_21047_ == Pose.SLEEPING ? f_20910_ : super.m_6972_(p_21047_).m_20388_(this.m_6134_());
   }

   public ImmutableList<Pose> m_7431_() {
      return ImmutableList.of(Pose.STANDING);
   }

   public AABB m_21270_(Pose p_21271_) {
      EntityDimensions entitydimensions = this.m_6972_(p_21271_);
      return new AABB((double)(-entitydimensions.f_20377_ / 2.0F), 0.0D, (double)(-entitydimensions.f_20377_ / 2.0F), (double)(entitydimensions.f_20377_ / 2.0F), (double)entitydimensions.f_20378_, (double)(entitydimensions.f_20377_ / 2.0F));
   }

   public Optional<BlockPos> m_21257_() {
      return this.f_19804_.m_135370_(f_20942_);
   }

   public void m_21250_(BlockPos p_21251_) {
      this.f_19804_.m_135381_(f_20942_, Optional.of(p_21251_));
   }

   public void m_21258_() {
      this.f_19804_.m_135381_(f_20942_, Optional.empty());
   }

   public boolean m_5803_() {
      return this.m_21257_().isPresent();
   }

   public void m_5802_(BlockPos p_21141_) {
      if (this.m_20159_()) {
         this.m_8127_();
      }

      BlockState blockstate = this.f_19853_.m_8055_(p_21141_);
      if (blockstate.isBed(f_19853_, p_21141_, this)) {
         blockstate.setBedOccupied(f_19853_, p_21141_, this, true);
      }

      this.m_20124_(Pose.SLEEPING);
      this.m_21080_(p_21141_);
      this.m_21250_(p_21141_);
      this.m_20256_(Vec3.f_82478_);
      this.f_19812_ = true;
   }

   private void m_21080_(BlockPos p_21081_) {
      this.m_6034_((double)p_21081_.m_123341_() + 0.5D, (double)p_21081_.m_123342_() + 0.6875D, (double)p_21081_.m_123343_() + 0.5D);
   }

   private boolean m_21334_() {
      return this.m_21257_().map((p_147236_) -> {
         return net.minecraftforge.event.ForgeEventFactory.fireSleepingLocationCheck(this, p_147236_);
      }).orElse(false);
   }

   public void m_5796_() {
      this.m_21257_().filter(this.f_19853_::m_46805_).ifPresent((p_147228_) -> {
         BlockState blockstate = this.f_19853_.m_8055_(p_147228_);
         if (blockstate.isBed(f_19853_, p_147228_, this)) {
            blockstate.setBedOccupied(f_19853_, p_147228_, this, false);
            Vec3 vec31 = BedBlock.m_49458_(this.m_6095_(), this.f_19853_, p_147228_, this.m_146908_()).orElseGet(() -> {
               BlockPos blockpos = p_147228_.m_7494_();
               return new Vec3((double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_() + 0.1D, (double)blockpos.m_123343_() + 0.5D);
            });
            Vec3 vec32 = Vec3.m_82539_(p_147228_).m_82546_(vec31).m_82541_();
            float f = (float)Mth.m_14175_(Mth.m_14136_(vec32.f_82481_, vec32.f_82479_) * (double)(180F / (float)Math.PI) - 90.0D);
            this.m_6034_(vec31.f_82479_, vec31.f_82480_, vec31.f_82481_);
            this.m_146922_(f);
            this.m_146926_(0.0F);
         }

      });
      Vec3 vec3 = this.m_20182_();
      this.m_20124_(Pose.STANDING);
      this.m_6034_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_);
      this.m_21258_();
   }

   @Nullable
   public Direction m_21259_() {
      BlockPos blockpos = this.m_21257_().orElse((BlockPos)null);
      if (blockpos == null) return Direction.UP;
      BlockState state = this.f_19853_.m_8055_(blockpos);
      return !state.isBed(f_19853_, blockpos, this) ? Direction.UP : state.getBedDirection(f_19853_, blockpos);
   }

   public boolean m_5830_() {
      return !this.m_5803_() && super.m_5830_();
   }

   protected final float m_6380_(Pose p_21049_, EntityDimensions p_21050_) {
      return p_21049_ == Pose.SLEEPING ? 0.2F : this.m_6431_(p_21049_, p_21050_);
   }

   protected float m_6431_(Pose p_21131_, EntityDimensions p_21132_) {
      return super.m_6380_(p_21131_, p_21132_);
   }

   public ItemStack m_6298_(ItemStack p_21272_) {
      return ItemStack.f_41583_;
   }

   public ItemStack m_5584_(Level p_21067_, ItemStack p_21068_) {
      if (p_21068_.m_41614_()) {
         p_21067_.m_142346_(this, GameEvent.f_157806_, this.m_146901_());
         p_21067_.m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_7866_(p_21068_), SoundSource.NEUTRAL, 1.0F, 1.0F + (p_21067_.f_46441_.nextFloat() - p_21067_.f_46441_.nextFloat()) * 0.4F);
         this.m_21063_(p_21068_, p_21067_, this);
         if (!(this instanceof Player) || !((Player)this).m_150110_().f_35937_) {
            p_21068_.m_41774_(1);
         }

         this.m_146850_(GameEvent.f_157806_);
      }

      return p_21068_;
   }

   private void m_21063_(ItemStack p_21064_, Level p_21065_, LivingEntity p_21066_) {
      Item item = p_21064_.m_41720_();
      if (item.m_41472_()) {
         for(Pair<MobEffectInstance, Float> pair : item.m_41473_().m_38749_()) {
            if (!p_21065_.f_46443_ && pair.getFirst() != null && p_21065_.f_46441_.nextFloat() < pair.getSecond()) {
               p_21066_.m_7292_(new MobEffectInstance(pair.getFirst()));
            }
         }
      }

   }

   private static byte m_21266_(EquipmentSlot p_21267_) {
      switch(p_21267_) {
      case MAINHAND:
         return 47;
      case OFFHAND:
         return 48;
      case HEAD:
         return 49;
      case CHEST:
         return 50;
      case FEET:
         return 52;
      case LEGS:
         return 51;
      default:
         return 47;
      }
   }

   public void m_21166_(EquipmentSlot p_21167_) {
      this.f_19853_.m_7605_(this, m_21266_(p_21167_));
   }

   public void m_21190_(InteractionHand p_21191_) {
      this.m_21166_(p_21191_ == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
   }

   /* ==== FORGE START ==== */
   /***
    * Removes all potion effects that have curativeItem as a curative item for its effect
    * @param curativeItem The itemstack we are using to cure potion effects
    */
   public boolean curePotionEffects(ItemStack curativeItem) {
      if (this.f_19853_.f_46443_)
         return false;
      boolean ret = false;
      Iterator<MobEffectInstance> itr = this.f_20945_.values().iterator();
      while (itr.hasNext()) {
         MobEffectInstance effect = itr.next();
         if (effect.isCurativeItem(curativeItem) && !net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.entity.living.PotionEvent.PotionRemoveEvent(this, effect))) {
            this.m_7285_(effect);
            itr.remove();
            ret = true;
            this.f_20948_ = true;
         }
      }
      return ret;
   }

   /**
    * Returns true if the entity's rider (EntityPlayer) should face forward when mounted.
    * currently only used in vanilla code by pigs.
    *
    * @param player The player who is riding the entity.
    * @return If the player should orient the same direction as this entity.
    */
   public boolean shouldRiderFaceForward(Player player) {
      return this instanceof net.minecraft.world.entity.animal.Pig;
   }

   private net.minecraftforge.common.util.LazyOptional<?>[] handlers = net.minecraftforge.items.wrapper.EntityEquipmentInvWrapper.create(this);

   @Override
   public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @Nullable Direction facing) {
      if (this.m_6084_() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
         if (facing == null) return handlers[2].cast();
         else if (facing.m_122434_().m_122478_()) return handlers[0].cast();
         else if (facing.m_122434_().m_122479_()) return handlers[1].cast();
      }
      return super.getCapability(capability, facing);
   }

   @Override
   public void invalidateCaps() {
      super.invalidateCaps();
      for (int x = 0; x < handlers.length; x++)
         handlers[x].invalidate();
   }

   @Override
   public void reviveCaps() {
      super.reviveCaps();
      handlers = net.minecraftforge.items.wrapper.EntityEquipmentInvWrapper.create(this);
   }

   public AABB m_6921_() {
      if (this.m_6844_(EquipmentSlot.HEAD).m_150930_(Items.f_42683_)) {
         float f = 0.5F;
         return this.m_142469_().m_82377_(0.5D, 0.5D, 0.5D);
      } else {
         return super.m_6921_();
      }
   }

   public static EquipmentSlot m_147233_(ItemStack p_147234_) {
      final EquipmentSlot slot = p_147234_.getEquipmentSlot();
      if (slot != null) return slot; // FORGE: Allow modders to set a non-default equipment slot for a stack; e.g. a non-armor chestplate-slot item
      Item item = p_147234_.m_41720_();
      if (!p_147234_.m_150930_(Items.f_42047_) && (!(item instanceof BlockItem) || !(((BlockItem)item).m_40614_() instanceof AbstractSkullBlock))) {
         if (item instanceof ArmorItem) {
            return ((ArmorItem)item).m_40402_();
         } else if (p_147234_.m_150930_(Items.f_42741_)) {
            return EquipmentSlot.CHEST;
         } else {
            return p_147234_.canPerformAction(net.minecraftforge.common.ToolActions.SHIELD_BLOCK) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
         }
      } else {
         return EquipmentSlot.HEAD;
      }
   }

   private static SlotAccess m_147195_(LivingEntity p_147196_, EquipmentSlot p_147197_) {
      return p_147197_ != EquipmentSlot.HEAD && p_147197_ != EquipmentSlot.MAINHAND && p_147197_ != EquipmentSlot.OFFHAND ? SlotAccess.m_147302_(p_147196_, p_147197_, (p_147222_) -> {
         return p_147222_.m_41619_() || Mob.m_147233_(p_147222_) == p_147197_;
      }) : SlotAccess.m_147299_(p_147196_, p_147197_);
   }

   @Nullable
   private static EquipmentSlot m_147211_(int p_147212_) {
      if (p_147212_ == 100 + EquipmentSlot.HEAD.m_20749_()) {
         return EquipmentSlot.HEAD;
      } else if (p_147212_ == 100 + EquipmentSlot.CHEST.m_20749_()) {
         return EquipmentSlot.CHEST;
      } else if (p_147212_ == 100 + EquipmentSlot.LEGS.m_20749_()) {
         return EquipmentSlot.LEGS;
      } else if (p_147212_ == 100 + EquipmentSlot.FEET.m_20749_()) {
         return EquipmentSlot.FEET;
      } else if (p_147212_ == 98) {
         return EquipmentSlot.MAINHAND;
      } else {
         return p_147212_ == 99 ? EquipmentSlot.OFFHAND : null;
      }
   }

   public SlotAccess m_141942_(int p_147238_) {
      EquipmentSlot equipmentslot = m_147211_(p_147238_);
      return equipmentslot != null ? m_147195_(this, equipmentslot) : super.m_141942_(p_147238_);
   }

   public boolean m_142079_() {
      if (this.m_5833_()) {
         return false;
      } else {
         boolean flag = !this.m_6844_(EquipmentSlot.HEAD).m_150922_(ItemTags.f_144320_) && !this.m_6844_(EquipmentSlot.CHEST).m_150922_(ItemTags.f_144320_) && !this.m_6844_(EquipmentSlot.LEGS).m_150922_(ItemTags.f_144320_) && !this.m_6844_(EquipmentSlot.FEET).m_150922_(ItemTags.f_144320_);
         return flag && super.m_142079_();
      }
   }

   public boolean m_142038_() {
      return !this.f_19853_.m_5776_() && this.m_21023_(MobEffects.f_19619_) || super.m_142038_();
   }

   public void m_142223_(ClientboundAddMobPacket p_147206_) {
      double d0 = p_147206_.m_131557_();
      double d1 = p_147206_.m_131558_();
      double d2 = p_147206_.m_131559_();
      float f = (float)(p_147206_.m_131563_() * 360) / 256.0F;
      float f1 = (float)(p_147206_.m_131564_() * 360) / 256.0F;
      this.m_20167_(d0, d1, d2);
      this.f_20883_ = (float)(p_147206_.m_131565_() * 360) / 256.0F;
      this.f_20885_ = (float)(p_147206_.m_131565_() * 360) / 256.0F;
      this.m_20234_(p_147206_.m_131552_());
      this.m_20084_(p_147206_.m_131555_());
      this.m_19890_(d0, d1, d2, f, f1);
      this.m_20334_((double)((float)p_147206_.m_131560_() / 8000.0F), (double)((float)p_147206_.m_131561_() / 8000.0F), (double)((float)p_147206_.m_131562_() / 8000.0F));
   }
}
