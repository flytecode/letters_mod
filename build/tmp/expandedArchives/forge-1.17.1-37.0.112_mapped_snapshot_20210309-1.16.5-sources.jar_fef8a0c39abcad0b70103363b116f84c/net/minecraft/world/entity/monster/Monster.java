package net.minecraft.world.entity.monster;

import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;

public abstract class Monster extends PathfinderMob implements Enemy {
   protected Monster(EntityType<? extends Monster> p_33002_, Level p_33003_) {
      super(p_33002_, p_33003_);
      this.f_21364_ = 5;
   }

   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   public void m_8107_() {
      this.m_21203_();
      this.m_7562_();
      super.m_8107_();
   }

   protected void m_7562_() {
      float f = this.m_6073_();
      if (f > 0.5F) {
         this.f_20891_ += 2;
      }

   }

   protected boolean m_8028_() {
      return true;
   }

   protected SoundEvent m_5501_() {
      return SoundEvents.f_12042_;
   }

   protected SoundEvent m_5509_() {
      return SoundEvents.f_12041_;
   }

   protected SoundEvent m_7975_(DamageSource p_33034_) {
      return SoundEvents.f_12039_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12038_;
   }

   protected SoundEvent m_5896_(int p_33041_) {
      return p_33041_ > 4 ? SoundEvents.f_12037_ : SoundEvents.f_12040_;
   }

   public float m_5610_(BlockPos p_33013_, LevelReader p_33014_) {
      return 0.5F - p_33014_.m_46863_(p_33013_);
   }

   public static boolean m_33008_(ServerLevelAccessor p_33009_, BlockPos p_33010_, Random p_33011_) {
      if (p_33009_.m_45517_(LightLayer.SKY, p_33010_) > p_33011_.nextInt(32)) {
         return false;
      } else {
         int i = p_33009_.m_6018_().m_46470_() ? p_33009_.m_46849_(p_33010_, 10) : p_33009_.m_46803_(p_33010_);
         return i <= p_33011_.nextInt(8);
      }
   }

   public static boolean m_33017_(EntityType<? extends Monster> p_33018_, ServerLevelAccessor p_33019_, MobSpawnType p_33020_, BlockPos p_33021_, Random p_33022_) {
      return p_33019_.m_46791_() != Difficulty.PEACEFUL && m_33008_(p_33019_, p_33021_, p_33022_) && m_21400_(p_33018_, p_33019_, p_33020_, p_33021_, p_33022_);
   }

   public static boolean m_33023_(EntityType<? extends Monster> p_33024_, LevelAccessor p_33025_, MobSpawnType p_33026_, BlockPos p_33027_, Random p_33028_) {
      return p_33025_.m_46791_() != Difficulty.PEACEFUL && m_21400_(p_33024_, p_33025_, p_33026_, p_33027_, p_33028_);
   }

   public static AttributeSupplier.Builder m_33035_() {
      return Mob.m_21552_().m_22266_(Attributes.f_22281_);
   }

   protected boolean m_6149_() {
      return true;
   }

   protected boolean m_6125_() {
      return true;
   }

   public boolean m_6935_(Player p_33036_) {
      return true;
   }

   public ItemStack m_6298_(ItemStack p_33038_) {
      if (p_33038_.m_41720_() instanceof ProjectileWeaponItem) {
         Predicate<ItemStack> predicate = ((ProjectileWeaponItem)p_33038_.m_41720_()).m_6442_();
         ItemStack itemstack = ProjectileWeaponItem.m_43010_(this, predicate);
         return itemstack.m_41619_() ? new ItemStack(Items.f_42412_) : itemstack;
      } else {
         return ItemStack.f_41583_;
      }
   }
}