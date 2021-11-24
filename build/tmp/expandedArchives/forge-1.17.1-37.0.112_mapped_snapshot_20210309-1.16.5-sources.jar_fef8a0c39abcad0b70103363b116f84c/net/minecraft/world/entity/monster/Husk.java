package net.minecraft.world.entity.monster;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Husk extends Zombie {
   public Husk(EntityType<? extends Husk> p_32889_, Level p_32890_) {
      super(p_32889_, p_32890_);
   }

   public static boolean m_32895_(EntityType<Husk> p_32896_, ServerLevelAccessor p_32897_, MobSpawnType p_32898_, BlockPos p_32899_, Random p_32900_) {
      return m_33017_(p_32896_, p_32897_, p_32898_, p_32899_, p_32900_) && (p_32898_ == MobSpawnType.SPAWNER || p_32897_.m_45527_(p_32899_));
   }

   protected boolean m_5884_() {
      return false;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12043_;
   }

   protected SoundEvent m_7975_(DamageSource p_32903_) {
      return SoundEvents.f_12046_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12045_;
   }

   protected SoundEvent m_7660_() {
      return SoundEvents.f_12047_;
   }

   public boolean m_7327_(Entity p_32892_) {
      boolean flag = super.m_7327_(p_32892_);
      if (flag && this.m_21205_().m_41619_() && p_32892_ instanceof LivingEntity) {
         float f = this.f_19853_.m_6436_(this.m_142538_()).m_19056_();
         ((LivingEntity)p_32892_).m_147207_(new MobEffectInstance(MobEffects.f_19612_, 140 * (int)f), this);
      }

      return flag;
   }

   protected boolean m_7593_() {
      return true;
   }

   protected void m_7595_() {
      this.m_34310_(EntityType.f_20501_);
      if (!this.m_20067_()) {
         this.f_19853_.m_5898_((Player)null, 1041, this.m_142538_(), 0);
      }

   }

   protected ItemStack m_5728_() {
      return ItemStack.f_41583_;
   }
}