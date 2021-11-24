package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class HopperMenu extends AbstractContainerMenu {
   public static final int f_150576_ = 5;
   private final Container f_39638_;

   public HopperMenu(int p_39640_, Inventory p_39641_) {
      this(p_39640_, p_39641_, new SimpleContainer(5));
   }

   public HopperMenu(int p_39643_, Inventory p_39644_, Container p_39645_) {
      super(MenuType.f_39972_, p_39643_);
      this.f_39638_ = p_39645_;
      m_38869_(p_39645_, 5);
      p_39645_.m_5856_(p_39644_.f_35978_);
      int i = 51;

      for(int j = 0; j < 5; ++j) {
         this.m_38897_(new Slot(p_39645_, j, 44 + j * 18, 20));
      }

      for(int l = 0; l < 3; ++l) {
         for(int k = 0; k < 9; ++k) {
            this.m_38897_(new Slot(p_39644_, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
         }
      }

      for(int i1 = 0; i1 < 9; ++i1) {
         this.m_38897_(new Slot(p_39644_, i1, 8 + i1 * 18, 109));
      }

   }

   public boolean m_6875_(Player p_39647_) {
      return this.f_39638_.m_6542_(p_39647_);
   }

   public ItemStack m_7648_(Player p_39651_, int p_39652_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39652_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_39652_ < this.f_39638_.m_6643_()) {
            if (!this.m_38903_(itemstack1, this.f_39638_.m_6643_(), this.f_38839_.size(), true)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 0, this.f_39638_.m_6643_(), false)) {
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

   public void m_6877_(Player p_39649_) {
      super.m_6877_(p_39649_);
      this.f_39638_.m_5785_(p_39649_);
   }
}