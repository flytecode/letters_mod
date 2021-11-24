package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class WaterWalkerEnchantment extends Enchantment {
   public WaterWalkerEnchantment(Enchantment.Rarity p_45280_, EquipmentSlot... p_45281_) {
      super(p_45280_, EnchantmentCategory.ARMOR_FEET, p_45281_);
   }

   public int m_6183_(int p_45284_) {
      return p_45284_ * 10;
   }

   public int m_6175_(int p_45288_) {
      return this.m_6183_(p_45288_) + 15;
   }

   public int m_6586_() {
      return 3;
   }

   public boolean m_5975_(Enchantment p_45286_) {
      return super.m_5975_(p_45286_) && p_45286_ != Enchantments.f_44974_;
   }
}