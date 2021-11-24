package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class QuickChargeEnchantment extends Enchantment {
   public QuickChargeEnchantment(Enchantment.Rarity p_45167_, EquipmentSlot... p_45168_) {
      super(p_45167_, EnchantmentCategory.CROSSBOW, p_45168_);
   }

   public int m_6183_(int p_45171_) {
      return 12 + (p_45171_ - 1) * 20;
   }

   public int m_6175_(int p_45173_) {
      return 50;
   }

   public int m_6586_() {
      return 3;
   }
}