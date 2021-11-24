package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ItemCombinerMenu extends AbstractContainerMenu {
   public static final int f_150595_ = 0;
   public static final int f_150596_ = 1;
   public static final int f_150597_ = 2;
   private static final int f_150594_ = 3;
   private static final int f_150598_ = 30;
   private static final int f_150599_ = 30;
   private static final int f_150600_ = 39;
   protected final ResultContainer f_39768_ = new ResultContainer();
   protected final Container f_39769_ = new SimpleContainer(2) {
      public void m_6596_() {
         super.m_6596_();
         ItemCombinerMenu.this.m_6199_(this);
      }
   };
   protected final ContainerLevelAccess f_39770_;
   protected final Player f_39771_;

   protected abstract boolean m_6560_(Player p_39798_, boolean p_39799_);

   protected abstract void m_142365_(Player p_150601_, ItemStack p_150602_);

   protected abstract boolean m_8039_(BlockState p_39788_);

   public ItemCombinerMenu(@Nullable MenuType<?> p_39773_, int p_39774_, Inventory p_39775_, ContainerLevelAccess p_39776_) {
      super(p_39773_, p_39774_);
      this.f_39770_ = p_39776_;
      this.f_39771_ = p_39775_.f_35978_;
      this.m_38897_(new Slot(this.f_39769_, 0, 27, 47));
      this.m_38897_(new Slot(this.f_39769_, 1, 76, 47));
      this.m_38897_(new Slot(this.f_39768_, 2, 134, 47) {
         public boolean m_5857_(ItemStack p_39818_) {
            return false;
         }

         public boolean m_8010_(Player p_39813_) {
            return ItemCombinerMenu.this.m_6560_(p_39813_, this.m_6657_());
         }

         public void m_142406_(Player p_150604_, ItemStack p_150605_) {
            ItemCombinerMenu.this.m_142365_(p_150604_, p_150605_);
         }
      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot(p_39775_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.m_38897_(new Slot(p_39775_, k, 8 + k * 18, 142));
      }

   }

   public abstract void m_6640_();

   public void m_6199_(Container p_39778_) {
      super.m_6199_(p_39778_);
      if (p_39778_ == this.f_39769_) {
         this.m_6640_();
      }

   }

   public void m_6877_(Player p_39790_) {
      super.m_6877_(p_39790_);
      this.f_39770_.m_39292_((p_39796_, p_39797_) -> {
         this.m_150411_(p_39790_, this.f_39769_);
      });
   }

   public boolean m_6875_(Player p_39780_) {
      return this.f_39770_.m_39299_((p_39785_, p_39786_) -> {
         return !this.m_8039_(p_39785_.m_8055_(p_39786_)) ? false : p_39780_.m_20275_((double)p_39786_.m_123341_() + 0.5D, (double)p_39786_.m_123342_() + 0.5D, (double)p_39786_.m_123343_() + 0.5D) <= 64.0D;
      }, true);
   }

   protected boolean m_5861_(ItemStack p_39787_) {
      return false;
   }

   public ItemStack m_7648_(Player p_39792_, int p_39793_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39793_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_39793_ == 2) {
            if (!this.m_38903_(itemstack1, 3, 39, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (p_39793_ != 0 && p_39793_ != 1) {
            if (p_39793_ >= 3 && p_39793_ < 39) {
               int i = this.m_5861_(itemstack) ? 1 : 0;
               if (!this.m_38903_(itemstack1, i, 2, false)) {
                  return ItemStack.f_41583_;
               }
            }
         } else if (!this.m_38903_(itemstack1, 3, 39, false)) {
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

         slot.m_142406_(p_39792_, itemstack1);
      }

      return itemstack;
   }
}