package net.minecraft.world.entity.animal;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Salmon extends AbstractSchoolingFish {
   public Salmon(EntityType<? extends Salmon> p_29790_, Level p_29791_) {
      super(p_29790_, p_29791_);
   }

   public int m_6031_() {
      return 5;
   }

   public ItemStack m_142563_() {
      return new ItemStack(Items.f_42457_);
   }

   protected SoundEvent m_7515_() {
      return SoundEvents.f_12327_;
   }

   protected SoundEvent m_5592_() {
      return SoundEvents.f_12328_;
   }

   protected SoundEvent m_7975_(DamageSource p_29795_) {
      return SoundEvents.f_12330_;
   }

   protected SoundEvent m_5699_() {
      return SoundEvents.f_12329_;
   }
}