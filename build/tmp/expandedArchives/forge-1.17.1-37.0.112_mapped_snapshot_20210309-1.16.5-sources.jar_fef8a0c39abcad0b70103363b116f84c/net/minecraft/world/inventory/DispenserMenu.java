package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class DispenserMenu extends AbstractContainerMenu {
   private static final int f_150557_ = 9;
   private static final int f_150558_ = 9;
   private static final int f_150559_ = 36;
   private static final int f_150560_ = 36;
   private static final int f_150561_ = 45;
   private final Container f_39431_;

   public DispenserMenu(int p_39433_, Inventory p_39434_) {
      this(p_39433_, p_39434_, new SimpleContainer(9));
   }

   public DispenserMenu(int p_39436_, Inventory p_39437_, Container p_39438_) {
      super(MenuType.f_39963_, p_39436_);
      m_38869_(p_39438_, 9);
      this.f_39431_ = p_39438_;
      p_39438_.m_5856_(p_39437_.f_35978_);

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 3; ++j) {
            this.m_38897_(new Slot(p_39438_, j + i * 3, 62 + j * 18, 17 + i * 18));
         }
      }

      for(int k = 0; k < 3; ++k) {
         for(int i1 = 0; i1 < 9; ++i1) {
            this.m_38897_(new Slot(p_39437_, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
         }
      }

      for(int l = 0; l < 9; ++l) {
         this.m_38897_(new Slot(p_39437_, l, 8 + l * 18, 142));
      }

   }

   public boolean m_6875_(Player p_39440_) {
      return this.f_39431_.m_6542_(p_39440_);
   }

   public ItemStack m_7648_(Player p_39444_, int p_39445_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39445_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_39445_ < 9) {
            if (!this.m_38903_(itemstack1, 9, 45, true)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 0, 9, false)) {
            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }

         if (itemstack1.m_41613_() == itemstack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         slot.m_142406_(p_39444_, itemstack1);
      }

      return itemstack;
   }

   public void m_6877_(Player p_39442_) {
      super.m_6877_(p_39442_);
      this.f_39431_.m_5785_(p_39442_);
   }
}