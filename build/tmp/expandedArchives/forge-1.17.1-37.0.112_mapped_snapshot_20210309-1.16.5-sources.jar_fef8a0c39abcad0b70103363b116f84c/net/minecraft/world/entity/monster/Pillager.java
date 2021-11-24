package net.minecraft.world.entity.monster;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Container;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class Pillager extends AbstractIllager implements CrossbowAttackMob, InventoryCarrier {
   private static final EntityDataAccessor<Boolean> f_33258_ = SynchedEntityData.m_135353_(Pillager.class, EntityDataSerializers.f_135035_);
   private static final int f_149740_ = 5;
   private static final int f_149738_ = 300;
   private static final float f_149739_ = 1.6F;
   private final SimpleContainer f_33259_ = new SimpleContainer(5);

   public Pillager(EntityType<? extends Pillager> p_33262_, Level p_33263_) {
      super(p_33262_, p_33263_);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new Raider.HoldGroundAttackGoal(this, 10.0F));
      this.f_21345_.m_25352_(3, new RangedCrossbowAttackGoal<>(this, 1.0D, 8.0F));
      this.f_21345_.m_25352_(8, new RandomStrollGoal(this, 0.6D));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
      this.f_21345_.m_25352_(10, new LookAtPlayerGoal(this, Mob.class, 15.0F));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this, Raider.class)).m_26044_());
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
   }

   public static AttributeSupplier.Builder m_33307_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22279_, (double)0.35F).m_22268_(Attributes.f_22276_, 24.0D).m_22268_(Attributes.f_22281_, 5.0D).m_22268_(Attributes.f_22277_, 32.0D);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(f_33258_, false);
   }

   public boolean m_5886_(ProjectileWeaponItem p_33280_) {
      return p_33280_ == Items.f_42717_;
   }

   public boolean m_33309_() {
      return this.f_19804_.m_135370_(f_33258_);
   }

   public void m_6136_(boolean p_33302_) {
      this.f_19804_.m_135381_(f_33258_, p_33302_);
   }

   public void m_5847_() {
      this.f_20891_ = 0;
   }

   public void m_7380_(CompoundTag p_33300_) {
      super.m_7380_(p_33300_);
      ListTag listtag = new ListTag();

      for(int i = 0; i < this.f_33259_.m_6643_(); ++i) {
         ItemStack itemstack = this.f_33259_.m_8020_(i);
         if (!itemstack.m_41619_()) {
            listtag.add(itemstack.m_41739_(new CompoundTag()));
         }
      }

      p_33300_.m_128365_("Inventory", listtag);
   }

   public AbstractIllager.IllagerArmPose m_6768_() {
      if (this.m_33309_()) {
         return AbstractIllager.IllagerArmPose.CROSSBOW_CHARGE;
      } else if (this.m_21093_(is -> is.m_41720_() instanceof net.minecraft.world.item.CrossbowItem)) {
         return AbstractIllager.IllagerArmPose.CROSSBOW_HOLD;
      } else {
         return this.m_5912_() ? AbstractIllager.IllagerArmPose.ATTACKING : AbstractIllager.IllagerArmPose.NEUTRAL;
      }
   }

   public void m_7378_(CompoundTag p_33291_) {
      super.m_7378_(p_33291_);
      ListTag listtag = p_33291_.m_128437_("Inventory", 10);

      for(int i = 0; i < listtag.size(); ++i) {
         ItemStack itemstack = ItemStack.m_41712_(listtag.m_128728_(i));
         if (!itemstack.m_41619_()) {
            this.f_33259_.m_19173_(itemstack);
         }
      }

      this.m_21553_(true);
   }

   public float m_5610_(BlockPos p_33288_, LevelReader p_33289_) {
      BlockState blockstate = p_33289_.m_8055_(p_33288_.m_7495_());
      return !blockstate.m_60713_(Blocks.f_50440_) && !blockstate.m_60713_(Blocks.f_49992_) ? 0.5F - p_33289_.m_46863_(p_33288_) : 10.0F;
   }

   public int m_5792_() {
      return 1;
   }

   @Nullable
   public SpawnGroupData m_6518_(ServerLevelAccessor p_33282_, DifficultyInstance p_33283_, MobSpawnType p_33284_, @Nullable SpawnGroupData p_33285_, @Nullable CompoundTag p_33286_) {
      this.m_6851_(p_33283_);
      this.m_6850_(p_33283_);
      return super.m_6518_(p_33282_, p_33283_, p_33284_, p_33285_, p_33286_);
   }

   protected void m_6851_(DifficultyInstance p_33270_) {
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42717_));
   }

   protected void m_7908_(float p_33316_) {
      super.m_7908_(p_33316_);
      if (this.f_19796_.nextInt(300) == 0) {
         ItemStack itemstack = this.m_21205_();
         if (itemstack.m_150930_(Items.f_42717_)) {
            Map<Enchantment, Integer> map = EnchantmentHelper.m_44831_(itemstack);
            map.putIfAbsent(Enchantments.f_44961_, 1);
            EnchantmentHelper.m_44865_(map, itemstack);
            this.m_8061_(EquipmentSlot.MAINHAND, itemstack);
         }
      }

   }

   public boolean m_7307_(Entity p_33314_) {
      if (super.m_7307_(p_33314_)) {
         return true;
      } else if (p_33314_ instanceof LivingEntity && ((LivingEntity)p_33314_).m_6336_() == MobType.f_21643_) {
         return this.m_5647_() == null && p_33314_.m_5647_() == null;
      } else {
         return false;
      }
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12307_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12309_;
   }

   protected SoundEvent m_7975_(DamageSource p_33306_) {
      return SoundEvents.f_12310_;
   }

   public void m_6504_(LivingEntity p_33272_, float p_33273_) {
      this.m_32336_(this, 1.6F);
   }

   public void m_5811_(LivingEntity p_33275_, ItemStack p_33276_, Projectile p_33277_, float p_33278_) {
      this.m_32322_(this, p_33275_, p_33277_, p_33278_, 1.6F);
   }

   public Container m_141944_() {
      return this.f_33259_;
   }

   protected void m_7581_(ItemEntity p_33296_) {
      ItemStack itemstack = p_33296_.m_32055_();
      if (itemstack.m_41720_() instanceof BannerItem) {
         super.m_7581_(p_33296_);
      } else if (this.m_149744_(itemstack)) {
         this.m_21053_(p_33296_);
         ItemStack itemstack1 = this.f_33259_.m_19173_(itemstack);
         if (itemstack1.m_41619_()) {
            p_33296_.m_146870_();
         } else {
            itemstack.m_41764_(itemstack1.m_41613_());
         }
      }

   }

   private boolean m_149744_(ItemStack p_149745_) {
      return this.m_37886_() && p_149745_.m_150930_(Items.f_42660_);
   }

   public SlotAccess m_141942_(int p_149743_) {
      int i = p_149743_ - 300;
      return i >= 0 && i < this.f_33259_.m_6643_() ? SlotAccess.m_147292_(this.f_33259_, i) : super.m_141942_(p_149743_);
   }

   public void m_7895_(int p_33267_, boolean p_33268_) {
      Raid raid = this.m_37885_();
      boolean flag = this.f_19796_.nextFloat() <= raid.m_37783_();
      if (flag) {
         ItemStack itemstack = new ItemStack(Items.f_42717_);
         Map<Enchantment, Integer> map = Maps.newHashMap();
         if (p_33267_ > raid.m_37724_(Difficulty.NORMAL)) {
            map.put(Enchantments.f_44960_, 2);
         } else if (p_33267_ > raid.m_37724_(Difficulty.EASY)) {
            map.put(Enchantments.f_44960_, 1);
         }

         map.put(Enchantments.f_44959_, 1);
         EnchantmentHelper.m_44865_(map, itemstack);
         this.m_8061_(EquipmentSlot.MAINHAND, itemstack);
      }

   }

   public SoundEvent m_7930_() {
      return SoundEvents.f_12308_;
   }
}
