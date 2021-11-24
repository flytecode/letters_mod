package net.minecraft.world.entity;

import java.util.function.Predicate;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public interface SlotAccess {
   SlotAccess f_147290_ = new SlotAccess() {
      public ItemStack m_142196_() {
         return ItemStack.f_41583_;
      }

      public boolean m_142104_(ItemStack p_147314_) {
         return false;
      }
   };

   static SlotAccess m_147295_(final Container p_147296_, final int p_147297_, final Predicate<ItemStack> p_147298_) {
      return new SlotAccess() {
         public ItemStack m_142196_() {
            return p_147296_.m_8020_(p_147297_);
         }

         public boolean m_142104_(ItemStack p_147324_) {
            if (!p_147298_.test(p_147324_)) {
               return false;
            } else {
               p_147296_.m_6836_(p_147297_, p_147324_);
               return true;
            }
         }
      };
   }

   static SlotAccess m_147292_(Container p_147293_, int p_147294_) {
      return m_147295_(p_147293_, p_147294_, (p_147310_) -> {
         return true;
      });
   }

   static SlotAccess m_147302_(final LivingEntity p_147303_, final EquipmentSlot p_147304_, final Predicate<ItemStack> p_147305_) {
      return new SlotAccess() {
         public ItemStack m_142196_() {
            return p_147303_.m_6844_(p_147304_);
         }

         public boolean m_142104_(ItemStack p_147334_) {
            if (!p_147305_.test(p_147334_)) {
               return false;
            } else {
               p_147303_.m_8061_(p_147304_, p_147334_);
               return true;
            }
         }
      };
   }

   static SlotAccess m_147299_(LivingEntity p_147300_, EquipmentSlot p_147301_) {
      return m_147302_(p_147300_, p_147301_, (p_147308_) -> {
         return true;
      });
   }

   ItemStack m_142196_();

   boolean m_142104_(ItemStack p_147306_);
}