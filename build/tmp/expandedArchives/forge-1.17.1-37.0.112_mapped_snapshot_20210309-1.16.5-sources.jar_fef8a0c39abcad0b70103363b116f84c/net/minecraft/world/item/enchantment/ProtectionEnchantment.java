package net.minecraft.world.item.enchantment;

import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

public class ProtectionEnchantment extends Enchantment {
   public final ProtectionEnchantment.Type f_45124_;

   public ProtectionEnchantment(Enchantment.Rarity p_45126_, ProtectionEnchantment.Type p_45127_, EquipmentSlot... p_45128_) {
      super(p_45126_, p_45127_ == ProtectionEnchantment.Type.FALL ? EnchantmentCategory.ARMOR_FEET : EnchantmentCategory.ARMOR, p_45128_);
      this.f_45124_ = p_45127_;
   }

   public int m_6183_(int p_45131_) {
      return this.f_45124_.m_45161_() + (p_45131_ - 1) * this.f_45124_.m_45162_();
   }

   public int m_6175_(int p_45144_) {
      return this.m_6183_(p_45144_) + this.f_45124_.m_45162_();
   }

   public int m_6586_() {
      return 4;
   }

   public int m_7205_(int p_45133_, DamageSource p_45134_) {
      if (p_45134_.m_19378_()) {
         return 0;
      } else if (this.f_45124_ == ProtectionEnchantment.Type.ALL) {
         return p_45133_;
      } else if (this.f_45124_ == ProtectionEnchantment.Type.FIRE && p_45134_.m_19384_()) {
         return p_45133_ * 2;
      } else if (this.f_45124_ == ProtectionEnchantment.Type.FALL && p_45134_.m_146707_()) {
         return p_45133_ * 3;
      } else if (this.f_45124_ == ProtectionEnchantment.Type.EXPLOSION && p_45134_.m_19372_()) {
         return p_45133_ * 2;
      } else {
         return this.f_45124_ == ProtectionEnchantment.Type.PROJECTILE && p_45134_.m_19360_() ? p_45133_ * 2 : 0;
      }
   }

   public boolean m_5975_(Enchantment p_45142_) {
      if (p_45142_ instanceof ProtectionEnchantment) {
         ProtectionEnchantment protectionenchantment = (ProtectionEnchantment)p_45142_;
         if (this.f_45124_ == protectionenchantment.f_45124_) {
            return false;
         } else {
            return this.f_45124_ == ProtectionEnchantment.Type.FALL || protectionenchantment.f_45124_ == ProtectionEnchantment.Type.FALL;
         }
      } else {
         return super.m_5975_(p_45142_);
      }
   }

   public static int m_45138_(LivingEntity p_45139_, int p_45140_) {
      int i = EnchantmentHelper.m_44836_(Enchantments.f_44966_, p_45139_);
      if (i > 0) {
         p_45140_ -= Mth.m_14143_((float)p_45140_ * (float)i * 0.15F);
      }

      return p_45140_;
   }

   public static double m_45135_(LivingEntity p_45136_, double p_45137_) {
      int i = EnchantmentHelper.m_44836_(Enchantments.f_44968_, p_45136_);
      if (i > 0) {
         p_45137_ -= (double)Mth.m_14107_(p_45137_ * (double)((float)i * 0.15F));
      }

      return p_45137_;
   }

   public static enum Type {
      ALL(1, 11),
      FIRE(10, 8),
      FALL(5, 6),
      EXPLOSION(5, 8),
      PROJECTILE(3, 6);

      private final int f_45151_;
      private final int f_45152_;

      private Type(int p_151299_, int p_151300_) {
         this.f_45151_ = p_151299_;
         this.f_45152_ = p_151300_;
      }

      public int m_45161_() {
         return this.f_45151_;
      }

      public int m_45162_() {
         return this.f_45152_;
      }
   }
}