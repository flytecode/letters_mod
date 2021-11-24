package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class ArrowFireEnchantment extends Enchantment {
   public ArrowFireEnchantment(Enchantment.Rarity p_44576_, EquipmentSlot... p_44577_) {
      super(p_44576_, EnchantmentCategory.BOW, p_44577_);
   }

   public int m_6183_(int p_44580_) {
      return 20;
   }

   public int m_6175_(int p_44582_) {
      return 50;
   }

   public int m_6586_() {
      return 1;
   }
}