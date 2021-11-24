package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class VanishingCurseEnchantment extends Enchantment {
   public VanishingCurseEnchantment(Enchantment.Rarity p_45270_, EquipmentSlot... p_45271_) {
      super(p_45270_, EnchantmentCategory.VANISHABLE, p_45271_);
   }

   public int m_6183_(int p_45274_) {
      return 25;
   }

   public int m_6175_(int p_45277_) {
      return 50;
   }

   public int m_6586_() {
      return 1;
   }

   public boolean m_6591_() {
      return true;
   }

   public boolean m_6589_() {
      return true;
   }
}