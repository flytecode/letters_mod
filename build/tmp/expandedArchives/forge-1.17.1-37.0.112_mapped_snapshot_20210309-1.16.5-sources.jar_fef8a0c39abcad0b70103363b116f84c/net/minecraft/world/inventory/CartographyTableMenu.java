package net.minecraft.world.inventory;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class CartographyTableMenu extends AbstractContainerMenu {
   public static final int f_150501_ = 0;
   public static final int f_150502_ = 1;
   public static final int f_150503_ = 2;
   private static final int f_150504_ = 3;
   private static final int f_150505_ = 30;
   private static final int f_150506_ = 30;
   private static final int f_150507_ = 39;
   private final ContainerLevelAccess f_39136_;
   long f_39137_;
   public final Container f_39135_ = new SimpleContainer(2) {
      public void m_6596_() {
         CartographyTableMenu.this.m_6199_(this);
         super.m_6596_();
      }
   };
   private final ResultContainer f_39138_ = new ResultContainer() {
      public void m_6596_() {
         CartographyTableMenu.this.m_6199_(this);
         super.m_6596_();
      }
   };

   public CartographyTableMenu(int p_39140_, Inventory p_39141_) {
      this(p_39140_, p_39141_, ContainerLevelAccess.f_39287_);
   }

   public CartographyTableMenu(int p_39143_, Inventory p_39144_, final ContainerLevelAccess p_39145_) {
      super(MenuType.f_39979_, p_39143_);
      this.f_39136_ = p_39145_;
      this.m_38897_(new Slot(this.f_39135_, 0, 15, 15) {
         public boolean m_5857_(ItemStack p_39194_) {
            return p_39194_.m_150930_(Items.f_42573_);
         }
      });
      this.m_38897_(new Slot(this.f_39135_, 1, 15, 52) {
         public boolean m_5857_(ItemStack p_39203_) {
            return p_39203_.m_150930_(Items.f_42516_) || p_39203_.m_150930_(Items.f_42676_) || p_39203_.m_150930_(Items.f_42027_);
         }
      });
      this.m_38897_(new Slot(this.f_39138_, 2, 145, 39) {
         public boolean m_5857_(ItemStack p_39217_) {
            return false;
         }

         public void m_142406_(Player p_150509_, ItemStack p_150510_) {
            CartographyTableMenu.this.f_38839_.get(0).m_6201_(1);
            CartographyTableMenu.this.f_38839_.get(1).m_6201_(1);
            p_150510_.m_41720_().m_7836_(p_150510_, p_150509_.f_19853_, p_150509_);
            p_39145_.m_39292_((p_39219_, p_39220_) -> {
               long l = p_39219_.m_46467_();
               if (CartographyTableMenu.this.f_39137_ != l) {
                  p_39219_.m_5594_((Player)null, p_39220_, SoundEvents.f_12493_, SoundSource.BLOCKS, 1.0F, 1.0F);
                  CartographyTableMenu.this.f_39137_ = l;
               }

            });
            super.m_142406_(p_150509_, p_150510_);
         }
      });

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot(p_39144_, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.m_38897_(new Slot(p_39144_, k, 8 + k * 18, 142));
      }

   }

   public boolean m_6875_(Player p_39149_) {
      return m_38889_(this.f_39136_, p_39149_, Blocks.f_50621_);
   }

   public void m_6199_(Container p_39147_) {
      ItemStack itemstack = this.f_39135_.m_8020_(0);
      ItemStack itemstack1 = this.f_39135_.m_8020_(1);
      ItemStack itemstack2 = this.f_39138_.m_8020_(2);
      if (itemstack2.m_41619_() || !itemstack.m_41619_() && !itemstack1.m_41619_()) {
         if (!itemstack.m_41619_() && !itemstack1.m_41619_()) {
            this.m_39162_(itemstack, itemstack1, itemstack2);
         }
      } else {
         this.f_39138_.m_8016_(2);
      }

   }

   private void m_39162_(ItemStack p_39163_, ItemStack p_39164_, ItemStack p_39165_) {
      this.f_39136_.m_39292_((p_39170_, p_39171_) -> {
         MapItemSavedData mapitemsaveddata = MapItem.m_42853_(p_39163_, p_39170_);
         if (mapitemsaveddata != null) {
            ItemStack itemstack;
            if (p_39164_.m_150930_(Items.f_42516_) && !mapitemsaveddata.f_77892_ && mapitemsaveddata.f_77890_ < 4) {
               itemstack = p_39163_.m_41777_();
               itemstack.m_41764_(1);
               itemstack.m_41784_().m_128405_("map_scale_direction", 1);
               this.m_38946_();
            } else if (p_39164_.m_150930_(Items.f_42027_) && !mapitemsaveddata.f_77892_) {
               itemstack = p_39163_.m_41777_();
               itemstack.m_41764_(1);
               itemstack.m_41784_().m_128379_("map_to_lock", true);
               this.m_38946_();
            } else {
               if (!p_39164_.m_150930_(Items.f_42676_)) {
                  this.f_39138_.m_8016_(2);
                  this.m_38946_();
                  return;
               }

               itemstack = p_39163_.m_41777_();
               itemstack.m_41764_(2);
               this.m_38946_();
            }

            if (!ItemStack.m_41728_(itemstack, p_39165_)) {
               this.f_39138_.m_6836_(2, itemstack);
               this.m_38946_();
            }

         }
      });
   }

   public boolean m_5882_(ItemStack p_39160_, Slot p_39161_) {
      return p_39161_.f_40218_ != this.f_39138_ && super.m_5882_(p_39160_, p_39161_);
   }

   public ItemStack m_7648_(Player p_39175_, int p_39176_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_39176_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_39176_ == 2) {
            itemstack1.m_41720_().m_7836_(itemstack1, p_39175_.f_19853_, p_39175_);
            if (!this.m_38903_(itemstack1, 3, 39, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (p_39176_ != 1 && p_39176_ != 0) {
            if (itemstack1.m_150930_(Items.f_42573_)) {
               if (!this.m_38903_(itemstack1, 0, 1, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (!itemstack1.m_150930_(Items.f_42516_) && !itemstack1.m_150930_(Items.f_42676_) && !itemstack1.m_150930_(Items.f_42027_)) {
               if (p_39176_ >= 3 && p_39176_ < 30) {
                  if (!this.m_38903_(itemstack1, 30, 39, false)) {
                     return ItemStack.f_41583_;
                  }
               } else if (p_39176_ >= 30 && p_39176_ < 39 && !this.m_38903_(itemstack1, 3, 30, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (!this.m_38903_(itemstack1, 1, 2, false)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 3, 39, false)) {
            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         }

         slot.m_6654_();
         if (itemstack1.m_41613_() == itemstack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         slot.m_142406_(p_39175_, itemstack1);
         this.m_38946_();
      }

      return itemstack;
   }

   public void m_6877_(Player p_39173_) {
      super.m_6877_(p_39173_);
      this.f_39138_.m_8016_(2);
      this.f_39136_.m_39292_((p_39152_, p_39153_) -> {
         this.m_150411_(p_39173_, this.f_39135_);
      });
   }
}