package net.minecraft.world.entity.monster;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Skeleton extends AbstractSkeleton {
   private static final EntityDataAccessor<Boolean> f_149826_ = SynchedEntityData.m_135353_(Skeleton.class, EntityDataSerializers.f_135035_);
   public static final String f_149825_ = "StrayConversionTime";
   private int f_149827_;
   private int f_149828_;

   public Skeleton(EntityType<? extends Skeleton> p_33570_, Level p_33571_) {
      super(p_33570_, p_33571_);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(f_149826_, false);
   }

   public boolean m_149839_() {
      return this.m_20088_().m_135370_(f_149826_);
   }

   public void m_149842_(boolean p_149843_) {
      this.f_19804_.m_135381_(f_149826_, p_149843_);
   }

   public boolean m_142548_() {
      return this.m_149839_();
   }

   public void m_8119_() {
      if (!this.f_19853_.f_46443_ && this.m_6084_() && !this.m_21525_()) {
         if (this.m_149839_()) {
            --this.f_149828_;
            if (this.f_149828_ < 0) {
               this.m_149840_();
            }
         } else if (this.f_146808_) {
            ++this.f_149827_;
            if (this.f_149827_ >= 140) {
               this.m_149830_(300);
            }
         } else {
            this.f_149827_ = -1;
         }
      }

      super.m_8119_();
   }

   public void m_7380_(CompoundTag p_149836_) {
      super.m_7380_(p_149836_);
      p_149836_.m_128405_("StrayConversionTime", this.m_149839_() ? this.f_149828_ : -1);
   }

   public void m_7378_(CompoundTag p_149833_) {
      super.m_7378_(p_149833_);
      if (p_149833_.m_128425_("StrayConversionTime", 99) && p_149833_.m_128451_("StrayConversionTime") > -1) {
         this.m_149830_(p_149833_.m_128451_("StrayConversionTime"));
      }

   }

   private void m_149830_(int p_149831_) {
      this.f_149828_ = p_149831_;
      this.f_19804_.m_135381_(f_149826_, true);
   }

   protected void m_149840_() {
      this.m_21406_(EntityType.f_20481_, true);
      if (!this.m_20067_()) {
         this.f_19853_.m_5898_((Player)null, 1048, this.m_142538_(), 0);
      }

   }

   public boolean m_142079_() {
      return false;
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12423_;
   }

   protected SoundEvent m_7975_(DamageSource p_33579_) {
      return SoundEvents.f_12381_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12424_;
   }

   protected SoundEvent m_7878_() {
      return SoundEvents.f_12383_;
   }

   protected void m_7472_(DamageSource p_33574_, int p_33575_, boolean p_33576_) {
      super.m_7472_(p_33574_, p_33575_, p_33576_);
      Entity entity = p_33574_.m_7639_();
      if (entity instanceof Creeper) {
         Creeper creeper = (Creeper)entity;
         if (creeper.m_32313_()) {
            creeper.m_32314_();
            this.m_19998_(Items.f_42678_);
         }
      }

   }
}