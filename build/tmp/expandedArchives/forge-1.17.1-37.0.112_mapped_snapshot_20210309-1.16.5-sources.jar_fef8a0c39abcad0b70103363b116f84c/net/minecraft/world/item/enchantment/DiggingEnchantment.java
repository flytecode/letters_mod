package net.minecraft.world.item.enchantment;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class DiggingEnchantment extends Enchantment {
   protected DiggingEnchantment(Enchantment.Rarity p_44662_, EquipmentSlot... p_44663_) {
      super(p_44662_, EnchantmentCategory.DIGGER, p_44663_);
   }

   public int m_6183_(int p_44666_) {
      return 1 + 10 * (p_44666_ - 1);
   }

   public int m_6175_(int p_44670_) {
      return super.m_6183_(p_44670_) + 50;
   }

   public int m_6586_() {
      return 5;
   }

   public boolean m_6081_(ItemStack p_44668_) {
      return p_44668_.m_41720_() instanceof net.minecraft.world.item.ShearsItem ? true : super.m_6081_(p_44668_);
   }
}
