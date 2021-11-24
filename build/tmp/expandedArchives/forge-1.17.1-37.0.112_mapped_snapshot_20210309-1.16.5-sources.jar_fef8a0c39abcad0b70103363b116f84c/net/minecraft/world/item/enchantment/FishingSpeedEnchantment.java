package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;

public class FishingSpeedEnchantment extends Enchantment {
   protected FishingSpeedEnchantment(Enchantment.Rarity p_45004_, EnchantmentCategory p_45005_, EquipmentSlot... p_45006_) {
      super(p_45004_, p_45005_, p_45006_);
   }

   public int m_6183_(int p_45009_) {
      return 15 + (p_45009_ - 1) * 9;
   }

   public int m_6175_(int p_45011_) {
      return super.m_6183_(p_45011_) + 50;
   }

   public int m_6586_() {
      return 3;
   }
}