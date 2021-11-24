package net.minecraft.world.entity.monster;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

public class Stray extends AbstractSkeleton {
   public Stray(EntityType<? extends Stray> p_33836_, Level p_33837_) {
      super(p_33836_, p_33837_);
   }

   public static boolean m_33839_(EntityType<Stray> p_33840_, ServerLevelAccessor p_33841_, MobSpawnType p_33842_, BlockPos p_33843_, Random p_33844_) {
      BlockPos blockpos = p_33843_;

      do {
         blockpos = blockpos.m_7494_();
      } while(p_33841_.m_8055_(blockpos).m_60713_(Blocks.f_152499_));

      return m_33017_(p_33840_, p_33841_, p_33842_, p_33843_, p_33844_) && (p_33842_ == MobSpawnType.SPAWNER || p_33841_.m_45527_(blockpos.m_7495_()));
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12451_;
   }

   protected SoundEvent m_7975_(DamageSource p_33850_) {
      return SoundEvents.f_12453_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12452_;
   }

   protected SoundEvent m_7878_() {
      return SoundEvents.f_12454_;
   }

   protected AbstractArrow m_7932_(ItemStack p_33846_, float p_33847_) {
      AbstractArrow abstractarrow = super.m_7932_(p_33846_, p_33847_);
      if (abstractarrow instanceof Arrow) {
         ((Arrow)abstractarrow).m_36870_(new MobEffectInstance(MobEffects.f_19597_, 600));
      }

      return abstractarrow;
   }
}