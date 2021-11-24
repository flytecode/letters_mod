package net.minecraft.world.entity;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.control.JumpControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.sensing.Sensing;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.storage.loot.LootContext;

public abstract class Mob extends LivingEntity {
   private static final EntityDataAccessor<Byte> f_21340_ = SynchedEntityData.m_135353_(Mob.class, EntityDataSerializers.f_135027_);
   private static final int f_147266_ = 1;
   private static final int f_147267_ = 2;
   private static final int f_147268_ = 4;
   public static final float f_147269_ = 0.15F;
   public static final float f_147261_ = 0.55F;
   public static final float f_147262_ = 0.5F;
   public static final float f_147263_ = 0.25F;
   public static final String f_147264_ = "Leash";
   private static final int f_147265_ = 1;
   public static final float f_182333_ = 0.085F;
   public int f_21363_;
   protected int f_21364_;
   protected LookControl f_21365_;
   protected MoveControl f_21342_;
   protected JumpControl f_21343_;
   private final BodyRotationControl f_21361_;
   protected PathNavigation f_21344_;
   public final GoalSelector f_21345_;
   public final GoalSelector f_21346_;
   private LivingEntity f_21362_;
   private final Sensing f_21349_;
   private final NonNullList<ItemStack> f_21350_ = NonNullList.m_122780_(2, ItemStack.f_41583_);
   protected final float[] f_21347_ = new float[2];
   private final NonNullList<ItemStack> f_21351_ = NonNullList.m_122780_(4, ItemStack.f_41583_);
   protected final float[] f_21348_ = new float[4];
   private boolean f_21352_;
   private boolean f_21353_;
   private final Map<BlockPathTypes, Float> f_21354_ = Maps.newEnumMap(BlockPathTypes.class);
   private ResourceLocation f_21355_;
   private long f_21356_;
   @Nullable
   private Entity f_21357_;
   private int f_21358_;
   @Nullable
   private CompoundTag f_21359_;
   private BlockPos f_21360_ = BlockPos.f_121853_;
   private float f_21341_ = -1.0F;

   protected Mob(EntityType<? extends Mob> p_21368_, Level p_21369_) {
      super(p_21368_, p_21369_);
      this.f_21345_ = new GoalSelector(p_21369_.m_46658_());
      this.f_21346_ = new GoalSelector(p_21369_.m_46658_());
      this.f_21365_ = new LookControl(this);
      this.f_21342_ = new MoveControl(this);
      this.f_21343_ = new JumpControl(this);
      this.f_21361_ = this.m_7560_();
      this.f_21344_ = this.m_6037_(p_21369_);
      this.f_21349_ = new Sensing(this);
      Arrays.fill(this.f_21348_, 0.085F);
      Arrays.fill(this.f_21347_, 0.085F);
      if (p_21369_ != null && !p_21369_.f_46443_) {
         this.m_8099_();
      }

   }

   protected void m_8099_() {
   }

   public static AttributeSupplier.Builder m_21552_() {
      return LivingEntity.m_21183_().m_22268_(Attributes.f_22277_, 16.0D).m_22266_(Attributes.f_22282_);
   }

   protected PathNavigation m_6037_(Level p_21480_) {
      return new GroundPathNavigation(this, p_21480_);
   }

   protected boolean m_8091_() {
      return false;
   }

   public float m_21439_(BlockPathTypes p_21440_) {
      Mob mob;
      if (this.m_20202_() instanceof Mob && ((Mob)this.m_20202_()).m_8091_()) {
         mob = (Mob)this.m_20202_();
      } else {
         mob = this;
      }

      Float f = mob.f_21354_.get(p_21440_);
      return f == null ? p_21440_.m_77124_() : f;
   }

   public void m_21441_(BlockPathTypes p_21442_, float p_21443_) {
      this.f_21354_.put(p_21442_, p_21443_);
   }

   public boolean m_21481_(BlockPathTypes p_21482_) {
      return p_21482_ != BlockPathTypes.DANGER_FIRE && p_21482_ != BlockPathTypes.DANGER_CACTUS && p_21482_ != BlockPathTypes.DANGER_OTHER && p_21482_ != BlockPathTypes.WALKABLE_DOOR;
   }

   protected BodyRotationControl m_7560_() {
      return new BodyRotationControl(this);
   }

   public LookControl m_21563_() {
      return this.f_21365_;
   }

   public MoveControl m_21566_() {
      if (this.m_20159_() && this.m_20202_() instanceof Mob) {
         Mob mob = (Mob)this.m_20202_();
         return mob.m_21566_();
      } else {
         return this.f_21342_;
      }
   }

   public JumpControl m_21569_() {
      return this.f_21343_;
   }

   public PathNavigation m_21573_() {
      if (this.m_20159_() && this.m_20202_() instanceof Mob) {
         Mob mob = (Mob)this.m_20202_();
         return mob.m_21573_();
      } else {
         return this.f_21344_;
      }
   }

   public Sensing m_21574_() {
      return this.f_21349_;
   }

   @Nullable
   public LivingEntity m_5448_() {
      return this.f_21362_;
   }

   public void m_6710_(@Nullable LivingEntity p_21544_) {
      this.f_21362_ = p_21544_;
      net.minecraftforge.common.ForgeHooks.onLivingSetAttackTarget(this, p_21544_);
   }

   public boolean m_6549_(EntityType<?> p_21399_) {
      return p_21399_ != EntityType.f_20453_;
   }

   public boolean m_5886_(ProjectileWeaponItem p_21430_) {
      return false;
   }

   public void m_8035_() {
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_21340_, (byte)0);
   }

   public int m_8100_() {
      return 80;
   }

   public void m_8032_() {
      SoundEvent soundevent = this.m_7515_();
      if (soundevent != null) {
         this.m_5496_(soundevent, this.m_6121_(), this.m_6100_());
      }

   }

   public void m_6075_() {
      super.m_6075_();
      this.f_19853_.m_46473_().m_6180_("mobBaseTick");
      if (this.m_6084_() && this.f_19796_.nextInt(1000) < this.f_21363_++) {
         this.m_21551_();
         this.m_8032_();
      }

      this.f_19853_.m_46473_().m_7238_();
   }

   protected void m_6677_(DamageSource p_21493_) {
      this.m_21551_();
      super.m_6677_(p_21493_);
   }

   private void m_21551_() {
      this.f_21363_ = -this.m_8100_();
   }

   protected int m_6552_(Player p_21511_) {
      if (this.f_21364_ > 0) {
         int i = this.f_21364_;

         for(int j = 0; j < this.f_21351_.size(); ++j) {
            if (!this.f_21351_.get(j).m_41619_() && this.f_21348_[j] <= 1.0F) {
               i += 1 + this.f_19796_.nextInt(3);
            }
         }

         for(int k = 0; k < this.f_21350_.size(); ++k) {
            if (!this.f_21350_.get(k).m_41619_() && this.f_21347_[k] <= 1.0F) {
               i += 1 + this.f_19796_.nextInt(3);
            }
         }

         return i;
      } else {
         return this.f_21364_;
      }
   }

   public void m_21373_() {
      if (this.f_19853_.f_46443_) {
         for(int i = 0; i < 20; ++i) {
            double d0 = this.f_19796_.nextGaussian() * 0.02D;
            double d1 = this.f_19796_.nextGaussian() * 0.02D;
            double d2 = this.f_19796_.nextGaussian() * 0.02D;
            double d3 = 10.0D;
            this.f_19853_.m_7106_(ParticleTypes.f_123759_, this.m_20165_(1.0D) - d0 * 10.0D, this.m_20187_() - d1 * 10.0D, this.m_20262_(1.0D) - d2 * 10.0D, d0, d1, d2);
         }
      } else {
         this.f_19853_.m_7605_(this, (byte)20);
      }

   }

   public void m_7822_(byte p_21375_) {
      if (p_21375_ == 20) {
         this.m_21373_();
      } else {
         super.m_7822_(p_21375_);
      }

   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.f_19853_.f_46443_) {
         this.m_6119_();
         if (this.f_19797_ % 5 == 0) {
            this.m_8022_();
         }
      }

   }

   protected void m_8022_() {
      boolean flag = !(this.m_6688_() instanceof Mob);
      boolean flag1 = !(this.m_20202_() instanceof Boat);
      this.f_21345_.m_25360_(Goal.Flag.MOVE, flag);
      this.f_21345_.m_25360_(Goal.Flag.JUMP, flag && flag1);
      this.f_21345_.m_25360_(Goal.Flag.LOOK, flag);
   }

   protected float m_5632_(float p_21538_, float p_21539_) {
      this.f_21361_.m_8121_();
      return p_21539_;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return null;
   }

   public void m_7380_(CompoundTag p_21484_) {
      super.m_7380_(p_21484_);
      p_21484_.m_128379_("CanPickUpLoot", this.m_21531_());
      p_21484_.m_128379_("PersistenceRequired", this.f_21353_);
      ListTag listtag = new ListTag();

      for(ItemStack itemstack : this.f_21351_) {
         CompoundTag compoundtag = new CompoundTag();
         if (!itemstack.m_41619_()) {
            itemstack.m_41739_(compoundtag);
         }

         listtag.add(compoundtag);
      }

      p_21484_.m_128365_("ArmorItems", listtag);
      ListTag listtag1 = new ListTag();

      for(ItemStack itemstack1 : this.f_21350_) {
         CompoundTag compoundtag1 = new CompoundTag();
         if (!itemstack1.m_41619_()) {
            itemstack1.m_41739_(compoundtag1);
         }

         listtag1.add(compoundtag1);
      }

      p_21484_.m_128365_("HandItems", listtag1);
      ListTag listtag2 = new ListTag();

      for(float f : this.f_21348_) {
         listtag2.add(FloatTag.m_128566_(f));
      }

      p_21484_.m_128365_("ArmorDropChances", listtag2);
      ListTag listtag3 = new ListTag();

      for(float f1 : this.f_21347_) {
         listtag3.add(FloatTag.m_128566_(f1));
      }

      p_21484_.m_128365_("HandDropChances", listtag3);
      if (this.f_21357_ != null) {
         CompoundTag compoundtag2 = new CompoundTag();
         if (this.f_21357_ instanceof LivingEntity) {
            UUID uuid = this.f_21357_.m_142081_();
            compoundtag2.m_128362_("UUID", uuid);
         } else if (this.f_21357_ instanceof HangingEntity) {
            BlockPos blockpos = ((HangingEntity)this.f_21357_).m_31748_();
            compoundtag2.m_128405_("X", blockpos.m_123341_());
            compoundtag2.m_128405_("Y", blockpos.m_123342_());
            compoundtag2.m_128405_("Z", blockpos.m_123343_());
         }

         p_21484_.m_128365_("Leash", compoundtag2);
      } else if (this.f_21359_ != null) {
         p_21484_.m_128365_("Leash", this.f_21359_.m_6426_());
      }

      p_21484_.m_128379_("LeftHanded", this.m_21526_());
      if (this.f_21355_ != null) {
         p_21484_.m_128359_("DeathLootTable", this.f_21355_.toString());
         if (this.f_21356_ != 0L) {
            p_21484_.m_128356_("DeathLootTableSeed", this.f_21356_);
         }
      }

      if (this.m_21525_()) {
         p_21484_.m_128379_("NoAI", this.m_21525_());
      }

   }

   public void m_7378_(CompoundTag p_21450_) {
      super.m_7378_(p_21450_);
      if (p_21450_.m_128425_("CanPickUpLoot", 1)) {
         this.m_21553_(p_21450_.m_128471_("CanPickUpLoot"));
      }

      this.f_21353_ = p_21450_.m_128471_("PersistenceRequired");
      if (p_21450_.m_128425_("ArmorItems", 9)) {
         ListTag listtag = p_21450_.m_128437_("ArmorItems", 10);

         for(int i = 0; i < this.f_21351_.size(); ++i) {
            this.f_21351_.set(i, ItemStack.m_41712_(listtag.m_128728_(i)));
         }
      }

      if (p_21450_.m_128425_("HandItems", 9)) {
         ListTag listtag1 = p_21450_.m_128437_("HandItems", 10);

         for(int j = 0; j < this.f_21350_.size(); ++j) {
            this.f_21350_.set(j, ItemStack.m_41712_(listtag1.m_128728_(j)));
         }
      }

      if (p_21450_.m_128425_("ArmorDropChances", 9)) {
         ListTag listtag2 = p_21450_.m_128437_("ArmorDropChances", 5);

         for(int k = 0; k < listtag2.size(); ++k) {
            this.f_21348_[k] = listtag2.m_128775_(k);
         }
      }

      if (p_21450_.m_128425_("HandDropChances", 9)) {
         ListTag listtag3 = p_21450_.m_128437_("HandDropChances", 5);

         for(int l = 0; l < listtag3.size(); ++l) {
            this.f_21347_[l] = listtag3.m_128775_(l);
         }
      }

      if (p_21450_.m_128425_("Leash", 10)) {
         this.f_21359_ = p_21450_.m_128469_("Leash");
      }

      this.m_21559_(p_21450_.m_128471_("LeftHanded"));
      if (p_21450_.m_128425_("DeathLootTable", 8)) {
         this.f_21355_ = new ResourceLocation(p_21450_.m_128461_("DeathLootTable"));
         this.f_21356_ = p_21450_.m_128454_("DeathLootTableSeed");
      }

      this.m_21557_(p_21450_.m_128471_("NoAI"));
   }

   protected void m_7625_(DamageSource p_21389_, boolean p_21390_) {
      super.m_7625_(p_21389_, p_21390_);
      this.f_21355_ = null;
   }

   protected LootContext.Builder m_7771_(boolean p_21453_, DamageSource p_21454_) {
      return super.m_7771_(p_21453_, p_21454_).m_78967_(this.f_21356_, this.f_19796_);
   }

   public final ResourceLocation m_5743_() {
      return this.f_21355_ == null ? this.m_7582_() : this.f_21355_;
   }

   protected ResourceLocation m_7582_() {
      return super.m_5743_();
   }

   public void m_21564_(float p_21565_) {
      this.f_20902_ = p_21565_;
   }

   public void m_21567_(float p_21568_) {
      this.f_20901_ = p_21568_;
   }

   public void m_21570_(float p_21571_) {
      this.f_20900_ = p_21571_;
   }

   public void m_7910_(float p_21556_) {
      super.m_7910_(p_21556_);
      this.m_21564_(p_21556_);
   }

   public void m_8107_() {
      super.m_8107_();
      this.f_19853_.m_46473_().m_6180_("looting");
      if (!this.f_19853_.f_46443_ && this.m_21531_() && this.m_6084_() && !this.f_20890_ && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.f_19853_, this)) {
         for(ItemEntity itementity : this.f_19853_.m_45976_(ItemEntity.class, this.m_142469_().m_82377_(1.0D, 0.0D, 1.0D))) {
            if (!itementity.m_146910_() && !itementity.m_32055_().m_41619_() && !itementity.m_32063_() && this.m_7243_(itementity.m_32055_())) {
               this.m_7581_(itementity);
            }
         }
      }

      this.f_19853_.m_46473_().m_7238_();
   }

   protected void m_7581_(ItemEntity p_21471_) {
      ItemStack itemstack = p_21471_.m_32055_();
      if (this.m_21540_(itemstack)) {
         this.m_21053_(p_21471_);
         this.m_7938_(p_21471_, itemstack.m_41613_());
         p_21471_.m_146870_();
      }

   }

   public boolean m_21540_(ItemStack p_21541_) {
      EquipmentSlot equipmentslot = m_147233_(p_21541_);
      ItemStack itemstack = this.m_6844_(equipmentslot);
      boolean flag = this.m_7808_(p_21541_, itemstack);
      if (flag && this.m_7252_(p_21541_)) {
         double d0 = (double)this.m_21519_(equipmentslot);
         if (!itemstack.m_41619_() && (double)Math.max(this.f_19796_.nextFloat() - 0.1F, 0.0F) < d0) {
            this.m_19983_(itemstack);
         }

         this.m_21468_(equipmentslot, p_21541_);
         this.m_147218_(p_21541_);
         return true;
      } else {
         return false;
      }
   }

   protected void m_21468_(EquipmentSlot p_21469_, ItemStack p_21470_) {
      this.m_8061_(p_21469_, p_21470_);
      this.m_21508_(p_21469_);
      this.f_21353_ = true;
   }

   public void m_21508_(EquipmentSlot p_21509_) {
      switch(p_21509_.m_20743_()) {
      case HAND:
         this.f_21347_[p_21509_.m_20749_()] = 2.0F;
         break;
      case ARMOR:
         this.f_21348_[p_21509_.m_20749_()] = 2.0F;
      }

   }

   protected boolean m_7808_(ItemStack p_21428_, ItemStack p_21429_) {
      if (p_21429_.m_41619_()) {
         return true;
      } else if (p_21428_.m_41720_() instanceof SwordItem) {
         if (!(p_21429_.m_41720_() instanceof SwordItem)) {
            return true;
         } else {
            SwordItem sworditem = (SwordItem)p_21428_.m_41720_();
            SwordItem sworditem1 = (SwordItem)p_21429_.m_41720_();
            if (sworditem.m_43299_() != sworditem1.m_43299_()) {
               return sworditem.m_43299_() > sworditem1.m_43299_();
            } else {
               return this.m_21477_(p_21428_, p_21429_);
            }
         }
      } else if (p_21428_.m_41720_() instanceof BowItem && p_21429_.m_41720_() instanceof BowItem) {
         return this.m_21477_(p_21428_, p_21429_);
      } else if (p_21428_.m_41720_() instanceof CrossbowItem && p_21429_.m_41720_() instanceof CrossbowItem) {
         return this.m_21477_(p_21428_, p_21429_);
      } else if (p_21428_.m_41720_() instanceof ArmorItem) {
         if (EnchantmentHelper.m_44920_(p_21429_)) {
            return false;
         } else if (!(p_21429_.m_41720_() instanceof ArmorItem)) {
            return true;
         } else {
            ArmorItem armoritem = (ArmorItem)p_21428_.m_41720_();
            ArmorItem armoritem1 = (ArmorItem)p_21429_.m_41720_();
            if (armoritem.m_40404_() != armoritem1.m_40404_()) {
               return armoritem.m_40404_() > armoritem1.m_40404_();
            } else if (armoritem.m_40405_() != armoritem1.m_40405_()) {
               return armoritem.m_40405_() > armoritem1.m_40405_();
            } else {
               return this.m_21477_(p_21428_, p_21429_);
            }
         }
      } else {
         if (p_21428_.m_41720_() instanceof DiggerItem) {
            if (p_21429_.m_41720_() instanceof BlockItem) {
               return true;
            }

            if (p_21429_.m_41720_() instanceof DiggerItem) {
               DiggerItem diggeritem = (DiggerItem)p_21428_.m_41720_();
               DiggerItem diggeritem1 = (DiggerItem)p_21429_.m_41720_();
               if (diggeritem.m_41008_() != diggeritem1.m_41008_()) {
                  return diggeritem.m_41008_() > diggeritem1.m_41008_();
               }

               return this.m_21477_(p_21428_, p_21429_);
            }
         }

         return false;
      }
   }

   public boolean m_21477_(ItemStack p_21478_, ItemStack p_21479_) {
      if (p_21478_.m_41773_() >= p_21479_.m_41773_() && (!p_21478_.m_41782_() || p_21479_.m_41782_())) {
         if (p_21478_.m_41782_() && p_21479_.m_41782_()) {
            return p_21478_.m_41783_().m_128431_().stream().anyMatch((p_21513_) -> {
               return !p_21513_.equals("Damage");
            }) && !p_21479_.m_41783_().m_128431_().stream().anyMatch((p_21503_) -> {
               return !p_21503_.equals("Damage");
            });
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public boolean m_7252_(ItemStack p_21545_) {
      return true;
   }

   public boolean m_7243_(ItemStack p_21546_) {
      return this.m_7252_(p_21546_);
   }

   public boolean m_6785_(double p_21542_) {
      return true;
   }

   public boolean m_8023_() {
      return this.m_20159_();
   }

   protected boolean m_8028_() {
      return false;
   }

   public void m_6043_() {
      if (this.f_19853_.m_46791_() == Difficulty.PEACEFUL && this.m_8028_()) {
         this.m_146870_();
      } else if (!this.m_21532_() && !this.m_8023_()) {
         Entity entity = this.f_19853_.m_45930_(this, -1.0D);
         net.minecraftforge.eventbus.api.Event.Result result = net.minecraftforge.event.ForgeEventFactory.canEntityDespawn(this);
         if (result == net.minecraftforge.eventbus.api.Event.Result.DENY) {
            f_20891_ = 0;
            entity = null;
         } else if (result == net.minecraftforge.eventbus.api.Event.Result.ALLOW) {
            this.m_146870_();
            entity = null;
         }
         if (entity != null) {
            double d0 = entity.m_20280_(this);
            int i = this.m_6095_().m_20674_().m_21611_();
            int j = i * i;
            if (d0 > (double)j && this.m_6785_(d0)) {
               this.m_146870_();
            }

            int k = this.m_6095_().m_20674_().m_21612_();
            int l = k * k;
            if (this.f_20891_ > 600 && this.f_19796_.nextInt(800) == 0 && d0 > (double)l && this.m_6785_(d0)) {
               this.m_146870_();
            } else if (d0 < (double)l) {
               this.f_20891_ = 0;
            }
         }

      } else {
         this.f_20891_ = 0;
      }
   }

   protected final void m_6140_() {
      ++this.f_20891_;
      this.f_19853_.m_46473_().m_6180_("sensing");
      this.f_21349_.m_26789_();
      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("targetSelector");
      this.f_21346_.m_25373_();
      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("goalSelector");
      this.f_21345_.m_25373_();
      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("navigation");
      this.f_21344_.m_7638_();
      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("mob tick");
      this.m_8024_();
      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_6180_("controls");
      this.f_19853_.m_46473_().m_6180_("move");
      this.f_21342_.m_8126_();
      this.f_19853_.m_46473_().m_6182_("look");
      this.f_21365_.m_8128_();
      this.f_19853_.m_46473_().m_6182_("jump");
      this.f_21343_.m_8124_();
      this.f_19853_.m_46473_().m_7238_();
      this.f_19853_.m_46473_().m_7238_();
      this.m_8025_();
   }

   protected void m_8025_() {
      DebugPackets.m_133699_(this.f_19853_, this, this.f_21345_);
   }

   protected void m_8024_() {
   }

   public int m_8132_() {
      return 40;
   }

   public int m_8085_() {
      return 75;
   }

   public int m_21529_() {
      return 10;
   }

   public void m_21391_(Entity p_21392_, float p_21393_, float p_21394_) {
      double d0 = p_21392_.m_20185_() - this.m_20185_();
      double d2 = p_21392_.m_20189_() - this.m_20189_();
      double d1;
      if (p_21392_ instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)p_21392_;
         d1 = livingentity.m_20188_() - this.m_20188_();
      } else {
         d1 = (p_21392_.m_142469_().f_82289_ + p_21392_.m_142469_().f_82292_) / 2.0D - this.m_20188_();
      }

      double d3 = Math.sqrt(d0 * d0 + d2 * d2);
      float f = (float)(Mth.m_14136_(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
      float f1 = (float)(-(Mth.m_14136_(d1, d3) * (double)(180F / (float)Math.PI)));
      this.m_146926_(this.m_21376_(this.m_146909_(), f1, p_21394_));
      this.m_146922_(this.m_21376_(this.m_146908_(), f, p_21393_));
   }

   private float m_21376_(float p_21377_, float p_21378_, float p_21379_) {
      float f = Mth.m_14177_(p_21378_ - p_21377_);
      if (f > p_21379_) {
         f = p_21379_;
      }

      if (f < -p_21379_) {
         f = -p_21379_;
      }

      return p_21377_ + f;
   }

   public static boolean m_21400_(EntityType<? extends Mob> p_21401_, LevelAccessor p_21402_, MobSpawnType p_21403_, BlockPos p_21404_, Random p_21405_) {
      BlockPos blockpos = p_21404_.m_7495_();
      return p_21403_ == MobSpawnType.SPAWNER || p_21402_.m_8055_(blockpos).m_60643_(p_21402_, blockpos, p_21401_);
   }

   public boolean m_5545_(LevelAccessor p_21431_, MobSpawnType p_21432_) {
      return true;
   }

   public boolean m_6914_(LevelReader p_21433_) {
      return !p_21433_.m_46855_(this.m_142469_()) && p_21433_.m_45784_(this);
   }

   public int m_5792_() {
      return 4;
   }

   public boolean m_7296_(int p_21489_) {
      return false;
   }

   public int m_6056_() {
      if (this.m_5448_() == null) {
         return 3;
      } else {
         int i = (int)(this.m_21223_() - this.m_21233_() * 0.33F);
         i = i - (3 - this.f_19853_.m_46791_().m_19028_()) * 4;
         if (i < 0) {
            i = 0;
         }

         return i + 3;
      }
   }

   public Iterable<ItemStack> m_6167_() {
      return this.f_21350_;
   }

   public Iterable<ItemStack> m_6168_() {
      return this.f_21351_;
   }

   public ItemStack m_6844_(EquipmentSlot p_21467_) {
      switch(p_21467_.m_20743_()) {
      case HAND:
         return this.f_21350_.get(p_21467_.m_20749_());
      case ARMOR:
         return this.f_21351_.get(p_21467_.m_20749_());
      default:
         return ItemStack.f_41583_;
      }
   }

   public void m_8061_(EquipmentSlot p_21416_, ItemStack p_21417_) {
      this.m_181122_(p_21417_);
      switch(p_21416_.m_20743_()) {
      case HAND:
         this.f_21350_.set(p_21416_.m_20749_(), p_21417_);
         break;
      case ARMOR:
         this.f_21351_.set(p_21416_.m_20749_(), p_21417_);
      }

   }

   protected void m_7472_(DamageSource p_21385_, int p_21386_, boolean p_21387_) {
      super.m_7472_(p_21385_, p_21386_, p_21387_);

      for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
         ItemStack itemstack = this.m_6844_(equipmentslot);
         float f = this.m_21519_(equipmentslot);
         boolean flag = f > 1.0F;
         if (!itemstack.m_41619_() && !EnchantmentHelper.m_44924_(itemstack) && (p_21387_ || flag) && Math.max(this.f_19796_.nextFloat() - (float)p_21386_ * 0.01F, 0.0F) < f) {
            if (!flag && itemstack.m_41763_()) {
               itemstack.m_41721_(itemstack.m_41776_() - this.f_19796_.nextInt(1 + this.f_19796_.nextInt(Math.max(itemstack.m_41776_() - 3, 1))));
            }

            this.m_19983_(itemstack);
            this.m_8061_(equipmentslot, ItemStack.f_41583_);
         }
      }

   }

   protected float m_21519_(EquipmentSlot p_21520_) {
      float f;
      switch(p_21520_.m_20743_()) {
      case HAND:
         f = this.f_21347_[p_21520_.m_20749_()];
         break;
      case ARMOR:
         f = this.f_21348_[p_21520_.m_20749_()];
         break;
      default:
         f = 0.0F;
      }

      return f;
   }

   protected void m_6851_(DifficultyInstance p_21383_) {
      if (this.f_19796_.nextFloat() < 0.15F * p_21383_.m_19057_()) {
         int i = this.f_19796_.nextInt(2);
         float f = this.f_19853_.m_46791_() == Difficulty.HARD ? 0.1F : 0.25F;
         if (this.f_19796_.nextFloat() < 0.095F) {
            ++i;
         }

         if (this.f_19796_.nextFloat() < 0.095F) {
            ++i;
         }

         if (this.f_19796_.nextFloat() < 0.095F) {
            ++i;
         }

         boolean flag = true;

         for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
            if (equipmentslot.m_20743_() == EquipmentSlot.Type.ARMOR) {
               ItemStack itemstack = this.m_6844_(equipmentslot);
               if (!flag && this.f_19796_.nextFloat() < f) {
                  break;
               }

               flag = false;
               if (itemstack.m_41619_()) {
                  Item item = m_21412_(equipmentslot, i);
                  if (item != null) {
                     this.m_8061_(equipmentslot, new ItemStack(item));
                  }
               }
            }
         }
      }

   }

   @Nullable
   public static Item m_21412_(EquipmentSlot p_21413_, int p_21414_) {
      switch(p_21413_) {
      case HEAD:
         if (p_21414_ == 0) {
            return Items.f_42407_;
         } else if (p_21414_ == 1) {
            return Items.f_42476_;
         } else if (p_21414_ == 2) {
            return Items.f_42464_;
         } else if (p_21414_ == 3) {
            return Items.f_42468_;
         } else if (p_21414_ == 4) {
            return Items.f_42472_;
         }
      case CHEST:
         if (p_21414_ == 0) {
            return Items.f_42408_;
         } else if (p_21414_ == 1) {
            return Items.f_42477_;
         } else if (p_21414_ == 2) {
            return Items.f_42465_;
         } else if (p_21414_ == 3) {
            return Items.f_42469_;
         } else if (p_21414_ == 4) {
            return Items.f_42473_;
         }
      case LEGS:
         if (p_21414_ == 0) {
            return Items.f_42462_;
         } else if (p_21414_ == 1) {
            return Items.f_42478_;
         } else if (p_21414_ == 2) {
            return Items.f_42466_;
         } else if (p_21414_ == 3) {
            return Items.f_42470_;
         } else if (p_21414_ == 4) {
            return Items.f_42474_;
         }
      case FEET:
         if (p_21414_ == 0) {
            return Items.f_42463_;
         } else if (p_21414_ == 1) {
            return Items.f_42479_;
         } else if (p_21414_ == 2) {
            return Items.f_42467_;
         } else if (p_21414_ == 3) {
            return Items.f_42471_;
         } else if (p_21414_ == 4) {
            return Items.f_42475_;
         }
      default:
         return null;
      }
   }

   protected void m_6850_(DifficultyInstance p_21462_) {
      float f = p_21462_.m_19057_();
      this.m_7908_(f);

      for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
         if (equipmentslot.m_20743_() == EquipmentSlot.Type.ARMOR) {
            this.m_21380_(f, equipmentslot);
         }
      }

   }

   protected void m_7908_(float p_21572_) {
      if (!this.m_21205_().m_41619_() && this.f_19796_.nextFloat() < 0.25F * p_21572_) {
         this.m_8061_(EquipmentSlot.MAINHAND, EnchantmentHelper.m_44877_(this.f_19796_, this.m_21205_(), (int)(5.0F + p_21572_ * (float)this.f_19796_.nextInt(18)), false));
      }

   }

   protected void m_21380_(float p_21381_, EquipmentSlot p_21382_) {
      ItemStack itemstack = this.m_6844_(p_21382_);
      if (!itemstack.m_41619_() && this.f_19796_.nextFloat() < 0.5F * p_21381_) {
         this.m_8061_(p_21382_, EnchantmentHelper.m_44877_(this.f_19796_, itemstack, (int)(5.0F + p_21381_ * (float)this.f_19796_.nextInt(18)), false));
      }

   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {
      this.m_21051_(Attributes.f_22277_).m_22125_(new AttributeModifier("Random spawn bonus", this.f_19796_.nextGaussian() * 0.05D, AttributeModifier.Operation.MULTIPLY_BASE));
      if (this.f_19796_.nextFloat() < 0.05F) {
         this.m_21559_(true);
      } else {
         this.m_21559_(false);
      }

      return p_21437_;
   }

   public boolean m_5807_() {
      return false;
   }

   public void m_21530_() {
      this.f_21353_ = true;
   }

   public void m_21409_(EquipmentSlot p_21410_, float p_21411_) {
      switch(p_21410_.m_20743_()) {
      case HAND:
         this.f_21347_[p_21410_.m_20749_()] = p_21411_;
         break;
      case ARMOR:
         this.f_21348_[p_21410_.m_20749_()] = p_21411_;
      }

   }

   public boolean m_21531_() {
      return this.f_21352_;
   }

   public void m_21553_(boolean p_21554_) {
      this.f_21352_ = p_21554_;
   }

   public boolean m_7066_(ItemStack p_21522_) {
      EquipmentSlot equipmentslot = m_147233_(p_21522_);
      return this.m_6844_(equipmentslot).m_41619_() && this.m_21531_();
   }

   public boolean m_21532_() {
      return this.f_21353_;
   }

   public final InteractionResult m_6096_(Player p_21420_, InteractionHand p_21421_) {
      if (!this.m_6084_()) {
         return InteractionResult.PASS;
      } else if (this.m_21524_() == p_21420_) {
         this.m_21455_(true, !p_21420_.m_150110_().f_35937_);
         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else {
         InteractionResult interactionresult = this.m_21499_(p_21420_, p_21421_);
         if (interactionresult.m_19077_()) {
            return interactionresult;
         } else {
            interactionresult = this.m_6071_(p_21420_, p_21421_);
            return interactionresult.m_19077_() ? interactionresult : super.m_6096_(p_21420_, p_21421_);
         }
      }
   }

   private InteractionResult m_21499_(Player p_21500_, InteractionHand p_21501_) {
      ItemStack itemstack = p_21500_.m_21120_(p_21501_);
      if (itemstack.m_150930_(Items.f_42655_) && this.m_6573_(p_21500_)) {
         this.m_21463_(p_21500_, true);
         itemstack.m_41774_(1);
         return InteractionResult.m_19078_(this.f_19853_.f_46443_);
      } else {
         if (itemstack.m_150930_(Items.f_42656_)) {
            InteractionResult interactionresult = itemstack.m_41647_(p_21500_, this, p_21501_);
            if (interactionresult.m_19077_()) {
               return interactionresult;
            }
         }

         if (itemstack.m_41720_() instanceof SpawnEggItem) {
            if (this.f_19853_ instanceof ServerLevel) {
               SpawnEggItem spawneggitem = (SpawnEggItem)itemstack.m_41720_();
               Optional<Mob> optional = spawneggitem.m_43215_(p_21500_, this, (EntityType<? extends Mob>)this.m_6095_(), (ServerLevel)this.f_19853_, this.m_20182_(), itemstack);
               optional.ifPresent((p_21476_) -> {
                  this.m_5502_(p_21500_, p_21476_);
               });
               return optional.isPresent() ? InteractionResult.SUCCESS : InteractionResult.PASS;
            } else {
               return InteractionResult.CONSUME;
            }
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   protected void m_5502_(Player p_21422_, Mob p_21423_) {
   }

   protected InteractionResult m_6071_(Player p_21472_, InteractionHand p_21473_) {
      return InteractionResult.PASS;
   }

   public boolean m_21533_() {
      return this.m_21444_(this.m_142538_());
   }

   public boolean m_21444_(BlockPos p_21445_) {
      if (this.f_21341_ == -1.0F) {
         return true;
      } else {
         return this.f_21360_.m_123331_(p_21445_) < (double)(this.f_21341_ * this.f_21341_);
      }
   }

   public void m_21446_(BlockPos p_21447_, int p_21448_) {
      this.f_21360_ = p_21447_;
      this.f_21341_ = (float)p_21448_;
   }

   public BlockPos m_21534_() {
      return this.f_21360_;
   }

   public float m_21535_() {
      return this.f_21341_;
   }

   public void m_147271_() {
      this.f_21341_ = -1.0F;
   }

   public boolean m_21536_() {
      return this.f_21341_ != -1.0F;
   }

   @Nullable
   public <T extends Mob> T m_21406_(EntityType<T> p_21407_, boolean p_21408_) {
      if (this.m_146910_()) {
         return (T)null;
      } else {
         T t = p_21407_.m_20615_(this.f_19853_);
         t.m_20359_(this);
         t.m_6863_(this.m_6162_());
         t.m_21557_(this.m_21525_());
         if (this.m_8077_()) {
            t.m_6593_(this.m_7770_());
            t.m_20340_(this.m_20151_());
         }

         if (this.m_21532_()) {
            t.m_21530_();
         }

         t.m_20331_(this.m_20147_());
         if (p_21408_) {
            t.m_21553_(this.m_21531_());

            for(EquipmentSlot equipmentslot : EquipmentSlot.values()) {
               ItemStack itemstack = this.m_6844_(equipmentslot);
               if (!itemstack.m_41619_()) {
                  t.m_8061_(equipmentslot, itemstack.m_41777_());
                  t.m_21409_(equipmentslot, this.m_21519_(equipmentslot));
                  itemstack.m_41764_(0);
               }
            }
         }

         this.f_19853_.m_7967_(t);
         if (this.m_20159_()) {
            Entity entity = this.m_20202_();
            this.m_8127_();
            t.m_7998_(entity, true);
         }

         this.m_146870_();
         return t;
      }
   }

   protected void m_6119_() {
      if (this.f_21359_ != null) {
         this.m_21528_();
      }

      if (this.f_21357_ != null) {
         if (!this.m_6084_() || !this.f_21357_.m_6084_()) {
            this.m_21455_(true, true);
         }

      }
   }

   public void m_21455_(boolean p_21456_, boolean p_21457_) {
      if (this.f_21357_ != null) {
         this.f_21357_ = null;
         this.f_21359_ = null;
         if (!this.f_19853_.f_46443_ && p_21457_) {
            this.m_19998_(Items.f_42655_);
         }

         if (!this.f_19853_.f_46443_ && p_21456_ && this.f_19853_ instanceof ServerLevel) {
            ((ServerLevel)this.f_19853_).m_7726_().m_8445_(this, new ClientboundSetEntityLinkPacket(this, (Entity)null));
         }
      }

   }

   public boolean m_6573_(Player p_21418_) {
      return !this.m_21523_() && !(this instanceof Enemy);
   }

   public boolean m_21523_() {
      return this.f_21357_ != null;
   }

   @Nullable
   public Entity m_21524_() {
      if (this.f_21357_ == null && this.f_21358_ != 0 && this.f_19853_.f_46443_) {
         this.f_21357_ = this.f_19853_.m_6815_(this.f_21358_);
      }

      return this.f_21357_;
   }

   public void m_21463_(Entity p_21464_, boolean p_21465_) {
      this.f_21357_ = p_21464_;
      this.f_21359_ = null;
      if (!this.f_19853_.f_46443_ && p_21465_ && this.f_19853_ instanceof ServerLevel) {
         ((ServerLevel)this.f_19853_).m_7726_().m_8445_(this, new ClientboundSetEntityLinkPacket(this, this.f_21357_));
      }

      if (this.m_20159_()) {
         this.m_8127_();
      }

   }

   public void m_21506_(int p_21507_) {
      this.f_21358_ = p_21507_;
      this.m_21455_(false, false);
   }

   public boolean m_7998_(Entity p_21396_, boolean p_21397_) {
      boolean flag = super.m_7998_(p_21396_, p_21397_);
      if (flag && this.m_21523_()) {
         this.m_21455_(true, true);
      }

      return flag;
   }

   private void m_21528_() {
      if (this.f_21359_ != null && this.f_19853_ instanceof ServerLevel) {
         if (this.f_21359_.m_128403_("UUID")) {
            UUID uuid = this.f_21359_.m_128342_("UUID");
            Entity entity = ((ServerLevel)this.f_19853_).m_8791_(uuid);
            if (entity != null) {
               this.m_21463_(entity, true);
               return;
            }
         } else if (this.f_21359_.m_128425_("X", 99) && this.f_21359_.m_128425_("Y", 99) && this.f_21359_.m_128425_("Z", 99)) {
            BlockPos blockpos = new BlockPos(this.f_21359_.m_128451_("X"), this.f_21359_.m_128451_("Y"), this.f_21359_.m_128451_("Z"));
            this.m_21463_(LeashFenceKnotEntity.m_31844_(this.f_19853_, blockpos), true);
            return;
         }

         if (this.f_19797_ > 100) {
            this.m_19998_(Items.f_42655_);
            this.f_21359_ = null;
         }
      }

   }

   public boolean m_6109_() {
      return this.m_5807_() && super.m_6109_();
   }

   public boolean m_6142_() {
      return super.m_6142_() && !this.m_21525_();
   }

   public void m_21557_(boolean p_21558_) {
      byte b0 = this.f_19804_.m_135370_(f_21340_);
      this.f_19804_.m_135381_(f_21340_, p_21558_ ? (byte)(b0 | 1) : (byte)(b0 & -2));
   }

   public void m_21559_(boolean p_21560_) {
      byte b0 = this.f_19804_.m_135370_(f_21340_);
      this.f_19804_.m_135381_(f_21340_, p_21560_ ? (byte)(b0 | 2) : (byte)(b0 & -3));
   }

   public void m_21561_(boolean p_21562_) {
      byte b0 = this.f_19804_.m_135370_(f_21340_);
      this.f_19804_.m_135381_(f_21340_, p_21562_ ? (byte)(b0 | 4) : (byte)(b0 & -5));
   }

   public boolean m_21525_() {
      return (this.f_19804_.m_135370_(f_21340_) & 1) != 0;
   }

   public boolean m_21526_() {
      return (this.f_19804_.m_135370_(f_21340_) & 2) != 0;
   }

   public boolean m_5912_() {
      return (this.f_19804_.m_135370_(f_21340_) & 4) != 0;
   }

   public void m_6863_(boolean p_21451_) {
   }

   public HumanoidArm m_5737_() {
      return this.m_21526_() ? HumanoidArm.LEFT : HumanoidArm.RIGHT;
   }

   public double m_142593_(LivingEntity p_147273_) {
      return (double)(this.m_20205_() * 2.0F * this.m_20205_() * 2.0F + p_147273_.m_20205_());
   }

   public boolean m_7327_(Entity p_21372_) {
      float f = (float)this.m_21133_(Attributes.f_22281_);
      float f1 = (float)this.m_21133_(Attributes.f_22282_);
      if (p_21372_ instanceof LivingEntity) {
         f += EnchantmentHelper.m_44833_(this.m_21205_(), ((LivingEntity)p_21372_).m_6336_());
         f1 += (float)EnchantmentHelper.m_44894_(this);
      }

      int i = EnchantmentHelper.m_44914_(this);
      if (i > 0) {
         p_21372_.m_20254_(i * 4);
      }

      boolean flag = p_21372_.m_6469_(DamageSource.m_19370_(this), f);
      if (flag) {
         if (f1 > 0.0F && p_21372_ instanceof LivingEntity) {
            ((LivingEntity)p_21372_).m_147240_((double)(f1 * 0.5F), (double)Mth.m_14031_(this.m_146908_() * ((float)Math.PI / 180F)), (double)(-Mth.m_14089_(this.m_146908_() * ((float)Math.PI / 180F))));
            this.m_20256_(this.m_20184_().m_82542_(0.6D, 1.0D, 0.6D));
         }

         if (p_21372_ instanceof Player) {
            Player player = (Player)p_21372_;
            this.m_21424_(player, this.m_21205_(), player.m_6117_() ? player.m_21211_() : ItemStack.f_41583_);
         }

         this.m_19970_(this, p_21372_);
         this.m_21335_(p_21372_);
      }

      return flag;
   }

   private void m_21424_(Player p_21425_, ItemStack p_21426_, ItemStack p_21427_) {
      if (!p_21426_.m_41619_() && !p_21427_.m_41619_() && p_21426_.m_41720_() instanceof AxeItem && p_21427_.m_150930_(Items.f_42740_)) {
         float f = 0.25F + (float)EnchantmentHelper.m_44926_(this) * 0.05F;
         if (this.f_19796_.nextFloat() < f) {
            p_21425_.m_36335_().m_41524_(Items.f_42740_, 100);
            this.f_19853_.m_7605_(p_21425_, (byte)30);
         }
      }

   }

   protected boolean m_21527_() {
      if (this.f_19853_.m_46461_() && !this.f_19853_.f_46443_) {
         float f = this.m_6073_();
         BlockPos blockpos = new BlockPos(this.m_20185_(), this.m_20188_(), this.m_20189_());
         boolean flag = this.m_20071_() || this.f_146808_ || this.f_146809_;
         if (f > 0.5F && this.f_19796_.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !flag && this.f_19853_.m_45527_(blockpos)) {
            return true;
         }
      }

      return false;
   }

   protected void m_6197_(Tag<Fluid> p_21491_) {
      if (this.m_21573_().m_26576_()) {
         super.m_6197_(p_21491_);
      } else {
         this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.3D, 0.0D));
      }

   }

   public void m_147272_() {
      this.f_21345_.m_148096_();
      this.m_6274_().m_147343_();
   }

   protected void m_6089_() {
      super.m_6089_();
      this.m_21455_(true, false);
      this.m_20158_().forEach((p_181125_) -> {
         p_181125_.m_41764_(0);
      });
   }

   @Nullable
   public ItemStack m_142340_() {
      SpawnEggItem spawneggitem = SpawnEggItem.m_43213_(this.m_6095_());
      return spawneggitem == null ? null : new ItemStack(spawneggitem);
   }
}
