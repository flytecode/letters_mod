package net.minecraft.world.entity.monster;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.Vec3;

public class MagmaCube extends Slime {
   public MagmaCube(EntityType<? extends MagmaCube> p_32968_, Level p_32969_) {
      super(p_32968_, p_32969_);
   }

   public static AttributeSupplier.Builder m_33000_() {
      return Monster.m_33035_().m_22268_(Attributes.f_22279_, (double)0.2F);
   }

   public static boolean m_32980_(EntityType<MagmaCube> p_32981_, LevelAccessor p_32982_, MobSpawnType p_32983_, BlockPos p_32984_, Random p_32985_) {
      return p_32982_.m_46791_() != Difficulty.PEACEFUL;
   }

   public boolean m_6914_(LevelReader p_32975_) {
      return p_32975_.m_45784_(this) && !p_32975_.m_46855_(this.m_142469_());
   }

   protected void m_7839_(int p_32972_, boolean p_32973_) {
      super.m_7839_(p_32972_, p_32973_);
      this.m_21051_(Attributes.f_22284_).m_22100_((double)(p_32972_ * 3));
   }

   public float m_6073_() {
      return 1.0F;
   }

   protected ParticleOptions m_6300_() {
      return ParticleTypes.f_123744_;
   }

   protected ResourceLocation m_7582_() {
      return this.m_33633_() ? BuiltInLootTables.f_78712_ : this.m_6095_().m_20677_();
   }

   public boolean m_6060_() {
      return false;
   }

   protected int m_7549_() {
      return super.m_7549_() * 4;
   }

   protected void m_7480_() {
      this.f_33581_ *= 0.9F;
   }

   protected void m_6135_() {
      Vec3 vec3 = this.m_20184_();
      this.m_20334_(vec3.f_82479_, (double)(this.m_6118_() + (float)this.m_33632_() * 0.1F), vec3.f_82481_);
      this.f_19812_ = true;
      net.minecraftforge.common.ForgeHooks.onLivingJump(this);
   }

   protected void m_6197_(Tag<Fluid> p_32988_) {
      if (p_32988_ == FluidTags.f_13132_) {
         Vec3 vec3 = this.m_20184_();
         this.m_20334_(vec3.f_82479_, (double)(0.22F + (float)this.m_33632_() * 0.05F), vec3.f_82481_);
         this.f_19812_ = true;
      } else {
         super.m_6197_(p_32988_);
      }

   }

   public boolean m_142535_(float p_149717_, float p_149718_, DamageSource p_149719_) {
      return false;
   }

   protected boolean m_7483_() {
      return this.m_6142_();
   }

   protected float m_7566_() {
      return super.m_7566_() + 2.0F;
   }

   protected SoundEvent m_7975_(DamageSource p_32992_) {
      return this.m_33633_() ? SoundEvents.f_12110_ : SoundEvents.f_12109_;
   }

   protected SoundEvent m_5592_() {
      return this.m_33633_() ? SoundEvents.f_12101_ : SoundEvents.f_12108_;
   }

   protected SoundEvent m_7905_() {
      return this.m_33633_() ? SoundEvents.f_12061_ : SoundEvents.f_12112_;
   }

   protected SoundEvent m_7903_() {
      return SoundEvents.f_12111_;
   }
}
