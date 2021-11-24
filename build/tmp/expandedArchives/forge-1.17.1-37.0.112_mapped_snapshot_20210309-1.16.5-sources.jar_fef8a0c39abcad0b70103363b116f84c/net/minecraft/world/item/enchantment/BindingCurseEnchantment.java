package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class BindingCurseEnchantment extends Enchantment {
   public BindingCurseEnchantment(Enchantment.Rarity p_44612_, EquipmentSlot... p_44613_) {
      super(p_44612_, EnchantmentCategory.WEARABLE, p_44613_);
   }

   public int m_6183_(int p_44616_) {
      return 25;
   }

   public int m_6175_(int p_44619_) {
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