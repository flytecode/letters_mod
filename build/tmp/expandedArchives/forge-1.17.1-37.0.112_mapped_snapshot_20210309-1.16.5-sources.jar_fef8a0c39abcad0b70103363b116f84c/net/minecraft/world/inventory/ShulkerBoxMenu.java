package net.minecraft.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ShulkerBoxMenu extends AbstractContainerMenu {
   private static final int f_150640_ = 27;
   private final Container f_40186_;

   public ShulkerBoxMenu(int p_40188_, Inventory p_40189_) {
      this(p_40188_, p_40189_, new SimpleContainer(27));
   }

   public ShulkerBoxMenu(int p_40191_, Inventory p_40192_, Container p_40193_) {
      super(MenuType.f_39976_, p_40191_);
      m_38869_(p_40193_, 27);
      this.f_40186_ = p_40193_;
      p_40193_.m_5856_(p_40192_.f_35978_);
      int i = 3;
      int j = 9;

      for(int k = 0; k < 3; ++k) {
         for(int l = 0; l < 9; ++l) {
            this.m_38897_(new ShulkerBoxSlot(p_40193_, l + k * 9, 8 + l * 18, 18 + k * 18));
         }
      }

      for(int i1 = 0; i1 < 3; ++i1) {
         for(int k1 = 0; k1 < 9; ++k1) {
            this.m_38897_(new Slot(p_40192_, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
         }
      }

      for(int j1 = 0; j1 < 9; ++j1) {
         this.m_38897_(new Slot(p_40192_, j1, 8 + j1 * 18, 142));
      }

   }

   public boolean m_6875_(Player p_40195_) {
      return this.f_40186_.m_6542_(p_40195_);
   }

   public ItemStack m_7648_(Player p_40199_, int p_40200_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_40200_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_40200_ < this.f_40186_.m_6643_()) {
            if (!this.m_38903_(itemstack1, this.f_40186_.m_6643_(), this.f_38839_.size(), true)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 0, this.f_40186_.m_6643_(), false)) {
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

   public void m_6877_(Player p_40197_) {
      super.m_6877_(p_40197_);
      this.f_40186_.m_5785_(p_40197_);
   }
}