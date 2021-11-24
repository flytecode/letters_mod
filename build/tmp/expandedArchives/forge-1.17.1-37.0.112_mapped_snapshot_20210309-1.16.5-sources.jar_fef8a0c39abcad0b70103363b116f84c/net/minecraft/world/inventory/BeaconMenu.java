package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;

public class BeaconMenu extends AbstractContainerMenu {
   private static final int f_150481_ = 0;
   private static final int f_150482_ = 1;
   private static final int f_150483_ = 3;
   private static final int f_150484_ = 1;
   private static final int f_150485_ = 28;
   private static final int f_150486_ = 28;
   private static final int f_150487_ = 37;
   private final Container f_39031_ = new SimpleContainer(1) {
      public boolean m_7013_(int p_39066_, ItemStack p_39067_) {
         return p_39067_.m_150922_(ItemTags.f_13164_);
      }

      public int m_6893_() {
         return 1;
      }
   };
   private final BeaconMenu.PaymentSlot f_39032_;
   private final ContainerLevelAccess f_39033_;
   private final ContainerData f_39034_;

   public BeaconMenu(int p_39036_, Container p_39037_) {
      this(p_39036_, p_39037_, new SimpleContainerData(3), ContainerLevelAccess.f_39287_);
   }

   public BeaconMenu(int p_39039_, Container p_39040_, ContainerData p_39041_, ContainerLevelAccess p_39042_) {
      super(MenuType.f_39965_, p_39039_);
      m_38886_(p_39041_, 3);
      this.f_39034_ = p_39041_;
      this.f_39033_ = p_39042_;
      this.f_39032_ = new BeaconMenu.PaymentSlot(this.f_39031_, 0, 136, 110);
      this.m_38897_(this.f_39032_);
      this.m_38884_(p_39041_);
      int i = 36;
      int j = 137;

      for(int k = 0; k < 3; ++k) {
         for(int l = 0; l < 9; ++l) {
            this.m_38897_(new Slot(p_39040_, l + k * 9 + 9, 36 + l * 18, 137 + k * 18));
         }
      }

      for(int i1 = 0; i1 < 9; ++i1) {
         this.m_38897_(new Slot(p_39040_, i1, 36 + i1 * 18, 195));
      }

   }

   public void m_6877_(Player p_39049_) {
      super.m_6877_(p_39049_);
      if (!p_39049_.f_19853_.f_46443_) {
         ItemStack itemstack = this.f_39032_.m_6201_(this.f_39032_.m_6641_());
         if (!itemstack.m_41619_()) {
            p_39049_.m_36176_(itemstack, false);
         }

      }
   }

   public boolean m_6875_(Player p_39047_) {
      return m_38889_(this.f_39033_, p_39047_, Blocks.f_50273_);
   }

   public void m_7511_(int p_39044_, int p_39045_) {
      super.m_7511_(p_39044_, p_39045_);
      this.m_38946_();
   }

   public ItemStack m_7648_(Player p_39051_, int p_39052_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39052_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_39052_ == 0) {
            if (!this.m_38903_(itemstack1, 1, 37, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (this.m_38903_(itemstack1, 0, 1, false)) { //Forge Fix Shift Clicking in beacons with stacks larger then 1.
            return ItemStack.f_41583_;
         } else if (p_39052_ >= 1 && p_39052_ < 28) {
            if (!this.m_38903_(itemstack1, 28, 37, false)) {
               return ItemStack.f_41583_;
            }
         } else if (p_39052_ >= 28 && p_39052_ < 37) {
            if (!this.m_38903_(itemstack1, 1, 28, false)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 1, 37, false)) {
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

         slot.m_142406_(p_39051_, itemstack1);
      }

      return itemstack;
   }

   public int m_39056_() {
      return this.f_39034_.m_6413_(0);
   }

   @Nullable
   public MobEffect m_39057_() {
      return MobEffect.m_19453_(this.f_39034_.m_6413_(1));
   }

   @Nullable
   public MobEffect m_39058_() {
      return MobEffect.m_19453_(this.f_39034_.m_6413_(2));
   }

   public void m_39053_(int p_39054_, int p_39055_) {
      if (this.f_39032_.m_6657_()) {
         this.f_39034_.m_8050_(1, p_39054_);
         this.f_39034_.m_8050_(2, p_39055_);
         this.f_39032_.m_6201_(1);
      }

   }

   public boolean m_39059_() {
      return !this.f_39031_.m_8020_(0).m_41619_();
   }

   class PaymentSlot extends Slot {
      public PaymentSlot(Container p_39071_, int p_39072_, int p_39073_, int p_39074_) {
         super(p_39071_, p_39072_, p_39073_, p_39074_);
      }

      public boolean m_5857_(ItemStack p_39077_) {
         return p_39077_.m_150922_(ItemTags.f_13164_);
      }

      public int m_6641_() {
         return 1;
      }
   }
}
