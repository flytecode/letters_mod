package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class SoulSpeedEnchantment extends Enchantment {
   public SoulSpeedEnchantment(Enchantment.Rarity p_45175_, EquipmentSlot... p_45176_) {
      super(p_45175_, EnchantmentCategory.ARMOR_FEET, p_45176_);
   }

   public int m_6183_(int p_45179_) {
      return p_45179_ * 10;
   }

   public int m_6175_(int p_45182_) {
      return this.m_6183_(p_45182_) + 15;
   }

   public boolean m_6591_() {
      return true;
   }

   public boolean m_6594_() {
      return false;
   }

   public boolean m_6592_() {
      return false;
   }

   public int m_6586_() {
      return 3;
   }
}