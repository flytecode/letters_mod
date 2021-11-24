package net.minecraft.world.entity.projectile;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class ThrownExperienceBottle extends ThrowableItemProjectile {
   public ThrownExperienceBottle(EntityType<? extends ThrownExperienceBottle> p_37510_, Level p_37511_) {
      super(p_37510_, p_37511_);
   }

   public ThrownExperienceBottle(Level p_37518_, LivingEntity p_37519_) {
      super(EntityType.f_20485_, p_37519_, p_37518_);
   }

   public ThrownExperienceBottle(Level p_37513_, double p_37514_, double p_37515_, double p_37516_) {
      super(EntityType.f_20485_, p_37514_, p_37515_, p_37516_, p_37513_);
   }

   protected Item m_7881_() {
      return Items.f_42612_;
   }

   protected float m_7139_() {
      return 0.07F;
   }

   protected void m_6532_(HitResult p_37521_) {
      super.m_6532_(p_37521_);
      if (this.f_19853_ instanceof ServerLevel) {
         this.f_19853_.m_46796_(2002, this.m_142538_(), PotionUtils.m_43559_(Potions.f_43599_));
         int i = 3 + this.f_19853_.f_46441_.nextInt(5) + this.f_19853_.f_46441_.nextInt(5);
         ExperienceOrb.m_147082_((ServerLevel)this.f_19853_, this.m_20182_(), i);
         this.m_146870_();
      }

   }
}