package net.minecraft.world.item.enchantment;

import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class ThornsEnchantment extends Enchantment {
   private static final float f_151302_ = 0.15F;

   public ThornsEnchantment(Enchantment.Rarity p_45196_, EquipmentSlot... p_45197_) {
      super(p_45196_, EnchantmentCategory.ARMOR_CHEST, p_45197_);
   }

   public int m_6183_(int p_45200_) {
      return 10 + 20 * (p_45200_ - 1);
   }

   public int m_6175_(int p_45210_) {
      return super.m_6183_(p_45210_) + 50;
   }

   public int m_6586_() {
      return 3;
   }

   public boolean m_6081_(ItemStack p_45205_) {
      return p_45205_.m_41720_() instanceof ArmorItem ? true : super.m_6081_(p_45205_);
   }

   public void m_7675_(LivingEntity p_45215_, Entity p_45216_, int p_45217_) {
      Random random = p_45215_.m_21187_();
      Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.m_44906_(Enchantments.f_44972_, p_45215_);
      if (m_45201_(p_45217_, random)) {
         if (p_45216_ != null) {
            p_45216_.m_6469_(DamageSource.m_19335_(p_45215_), (float)m_45211_(p_45217_, random));
         }

         if (entry != null) {
            entry.getValue().m_41622_(2, p_45215_, (p_45208_) -> {
               p_45208_.m_21166_(entry.getKey());
            });
         }
      }

   }

   public static boolean m_45201_(int p_45202_, Random p_45203_) {
      if (p_45202_ <= 0) {
         return false;
      } else {
         return p_45203_.nextFloat() < 0.15F * (float)p_45202_;
      }
   }

   public static int m_45211_(int p_45212_, Random p_45213_) {
      return p_45212_ > 10 ? p_45212_ - 10 : 1 + p_45213_.nextInt(4);
   }
}