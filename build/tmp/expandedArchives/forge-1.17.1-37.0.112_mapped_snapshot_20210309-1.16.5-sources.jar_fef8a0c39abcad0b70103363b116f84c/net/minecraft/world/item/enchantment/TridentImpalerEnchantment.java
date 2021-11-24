package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;

public class TridentImpalerEnchantment extends Enchantment {
   public TridentImpalerEnchantment(Enchantment.Rarity p_45229_, EquipmentSlot... p_45230_) {
      super(p_45229_, EnchantmentCategory.TRIDENT, p_45230_);
   }

   public int m_6183_(int p_45233_) {
      return 1 + (p_45233_ - 1) * 8;
   }

   public int m_6175_(int p_45238_) {
      return this.m_6183_(p_45238_) + 20;
   }

   public int m_6586_() {
      return 5;
   }

   public float m_7335_(int p_45235_, MobType p_45236_) {
      return p_45236_ == MobType.f_21644_ ? (float)p_45235_ * 2.5F : 0.0F;
   }
}