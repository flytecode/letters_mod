package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class MultiShotEnchantment extends Enchantment {
   public MultiShotEnchantment(Enchantment.Rarity p_45107_, EquipmentSlot... p_45108_) {
      super(p_45107_, EnchantmentCategory.CROSSBOW, p_45108_);
   }

   public int m_6183_(int p_45111_) {
      return 20;
   }

   public int m_6175_(int p_45115_) {
      return 50;
   }

   public int m_6586_() {
      return 1;
   }

   public boolean m_5975_(Enchantment p_45113_) {
      return super.m_5975_(p_45113_) && p_45113_ != Enchantments.f_44961_;
   }
}