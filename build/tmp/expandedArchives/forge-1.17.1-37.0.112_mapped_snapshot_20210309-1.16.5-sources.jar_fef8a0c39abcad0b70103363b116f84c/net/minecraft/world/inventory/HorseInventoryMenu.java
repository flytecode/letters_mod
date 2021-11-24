package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class HorseInventoryMenu extends AbstractContainerMenu {
   private final Container f_39653_;
   private final AbstractHorse f_39654_;

   public HorseInventoryMenu(int p_39656_, Inventory p_39657_, Container p_39658_, final AbstractHorse p_39659_) {
      super((MenuType<?>)null, p_39656_);
      this.f_39653_ = p_39658_;
      this.f_39654_ = p_39659_;
      int i = 3;
      p_39658_.m_5856_(p_39657_.f_35978_);
      int j = -18;
      this.m_38897_(new Slot(p_39658_, 0, 8, 18) {
         public boolean m_5857_(ItemStack p_39677_) {
            return p_39677_.m_150930_(Items.f_42450_) && !this.m_6657_() && p_39659_.m_6741_();
         }

         public boolean m_6659_() {
            return p_39659_.m_6741_();
         }
      });
      this.m_38897_(new Slot(p_39658_, 1, 8, 36) {
         public boolean m_5857_(ItemStack p_39690_) {
            return p_39659_.m_6010_(p_39690_);
         }

         public boolean m_6659_() {
            return p_39659_.m_7482_();
         }

         public int m_6641_() {
            return 1;
         }
      });
      if (this.m_150577_(p_39659_)) {
         for(int k = 0; k < 3; ++k) {
            for(int l = 0; l < ((AbstractChestedHorse)p_39659_).m_7488_(); ++l) {
               this.m_38897_(new Slot(p_39658_, 2 + l + k * ((AbstractChestedHorse)p_39659_).m_7488_(), 80 + l * 18, 18 + k * 18));
            }
         }
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int k1 = 0; k1 < 9; ++k1) {
            this.m_38897_(new Slot(p_39657_, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.m_38897_(new Slot(p_39657_, j1, 8 + j1 * 18, 142));
      }

   }

   public boolean m_6875_(Player p_39661_) {
      return !this.f_39654_.m_149511_(this.f_39653_) && this.f_39653_.m_6542_(p_39661_) && this.f_39654_.m_6084_() && this.f_39654_.m_20270_(p_39661_) < 8.0F;
   }

   private boolean m_150577_(AbstractHorse p_150578_) {
      return p_150578_ instanceof AbstractChestedHorse && ((AbstractChestedHorse)p_150578_).m_30502_();
   }

   public ItemStack m_7648_(Player p_39665_, int p_39666_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39666_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         int i = this.f_39653_.m_6643_();
         if (p_39666_ < i) {
            if (!this.m_38903_(itemstack1, i, this.f_38839_.size(), true)) {
               return ItemStack.f_41583_;
            }
         } else if (this.m_38853_(1).m_5857_(itemstack1) && !this.m_38853_(1).m_6657_()) {
            if (!this.m_38903_(itemstack1, 1, 2, false)) {
               return ItemStack.f_41583_;
            }
         } else if (this.m_38853_(0).m_5857_(itemstack1)) {
            if (!this.m_38903_(itemstack1, 0, 1, false)) {
               return ItemStack.f_41583_;
            }
         } else if (i <= 2 || !this.m_38903_(itemstack1, 2, i, false)) {
            int j = i + 27;
            int k = j + 9;
            if (p_39666_ >= j && p_39666_ < k) {
               if (!this.m_38903_(itemstack1, i, j, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (p_39666_ >= i && p_39666_ < j) {
               if (!this.m_38903_(itemstack1, j, k, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (!this.m_38903_(itemstack1, j, j, false)) {
               return ItemStack.f_41583_;
            }

            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }
      }

      return itemstack;
   }

   public void m_6877_(Player p_39663_) {
      super.m_6877_(p_39663_);
      this.f_39653_.m_5785_(p_39663_);
   }
}