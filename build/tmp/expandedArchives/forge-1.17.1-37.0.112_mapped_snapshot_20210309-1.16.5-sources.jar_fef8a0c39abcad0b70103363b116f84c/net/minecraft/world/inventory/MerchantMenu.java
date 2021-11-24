package net.minecraft.world.inventory;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.ClientSideMerchant;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffers;

public class MerchantMenu extends AbstractContainerMenu {
   protected static final int f_150619_ = 0;
   protected static final int f_150620_ = 1;
   protected static final int f_150621_ = 2;
   private static final int f_150622_ = 3;
   private static final int f_150623_ = 30;
   private static final int f_150624_ = 30;
   private static final int f_150625_ = 39;
   private static final int f_150626_ = 136;
   private static final int f_150627_ = 162;
   private static final int f_150628_ = 220;
   private static final int f_150629_ = 37;
   private final Merchant f_40027_;
   private final MerchantContainer f_40028_;
   private int f_40029_;
   private boolean f_40030_;
   private boolean f_40031_;

   public MerchantMenu(int p_40033_, Inventory p_40034_) {
      this(p_40033_, p_40034_, new ClientSideMerchant(p_40034_.f_35978_));
   }

   public MerchantMenu(int p_40036_, Inventory p_40037_, Merchant p_40038_) {
      super(MenuType.f_39975_, p_40036_);
      this.f_40027_ = p_40038_;
      this.f_40028_ = new MerchantContainer(p_40038_);
      this.m_38897_(new Slot(this.f_40028_, 0, 136, 37));
      this.m_38897_(new Slot(this.f_40028_, 1, 162, 37));
      this.m_38897_(new MerchantResultSlot(p_40037_.f_35978_, p_40038_, this.f_40028_, 2, 220, 37));

      for(int i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot(p_40037_, j + i * 9 + 9, 108 + j * 18, 84 + i * 18));
         }
      }

      for(int k = 0; k < 9; ++k) {
         this.m_38897_(new Slot(p_40037_, k, 108 + k * 18, 142));
      }

   }

   public void m_40048_(boolean p_40049_) {
      this.f_40030_ = p_40049_;
   }

   public void m_6199_(Container p_40040_) {
      this.f_40028_.m_40024_();
      super.m_6199_(p_40040_);
   }

   public void m_40063_(int p_40064_) {
      this.f_40028_.m_40020_(p_40064_);
   }

   public boolean m_6875_(Player p_40042_) {
      return this.f_40027_.m_7962_() == p_40042_;
   }

   public int m_40065_() {
      return this.f_40027_.m_7809_();
   }

   public int m_40068_() {
      return this.f_40028_.m_40026_();
   }

   public void m_40066_(int p_40067_) {
      this.f_40027_.m_6621_(p_40067_);
   }

   public int m_40071_() {
      return this.f_40029_;
   }

   public void m_40069_(int p_40070_) {
      this.f_40029_ = p_40070_;
   }

   public void m_40058_(boolean p_40059_) {
      this.f_40031_ = p_40059_;
   }

   public boolean m_40074_() {
      return this.f_40031_;
   }

   public boolean m_5882_(ItemStack p_40044_, Slot p_40045_) {
      return false;
   }

   public ItemStack m_7648_(Player p_40053_, int p_40054_) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = this.f_38839_.get(p_40054_);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (p_40054_ == 2) {
            if (!this.m_38903_(itemstack1, 3, 39, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
            this.m_40077_();
         } else if (p_40054_ != 0 && p_40054_ != 1) {
            if (p_40054_ >= 3 && p_40054_ < 30) {
               if (!this.m_38903_(itemstack1, 30, 39, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (p_40054_ >= 30 && p_40054_ < 39 && !this.m_38903_(itemstack1, 3, 30, false)) {
               return ItemStack.f_41583_;
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

         slot.m_142406_(p_40053_, itemstack1);
      }

      return itemstack;
   }

   private void m_40077_() {
      if (!this.f_40027_.m_7133_().f_46443_) {
         Entity entity = (Entity)this.f_40027_;
         this.f_40027_.m_7133_().m_7785_(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), this.f_40027_.m_7596_(), SoundSource.NEUTRAL, 1.0F, 1.0F, false);
      }

   }

   public void m_6877_(Player p_40051_) {
      super.m_6877_(p_40051_);
      this.f_40027_.m_7189_((Player)null);
      if (!this.f_40027_.m_7133_().f_46443_) {
         if (!p_40051_.m_6084_() || p_40051_ instanceof ServerPlayer && ((ServerPlayer)p_40051_).m_9232_()) {
            ItemStack itemstack = this.f_40028_.m_8016_(0);
            if (!itemstack.m_41619_()) {
               p_40051_.m_36176_(itemstack, false);
            }

            itemstack = this.f_40028_.m_8016_(1);
            if (!itemstack.m_41619_()) {
               p_40051_.m_36176_(itemstack, false);
            }
         } else if (p_40051_ instanceof ServerPlayer) {
            p_40051_.m_150109_().m_150079_(this.f_40028_.m_8016_(0));
            p_40051_.m_150109_().m_150079_(this.f_40028_.m_8016_(1));
         }

      }
   }

   public void m_40072_(int p_40073_) {
      if (this.m_40075_().size() > p_40073_) {
         ItemStack itemstack = this.f_40028_.m_8020_(0);
         if (!itemstack.m_41619_()) {
            if (!this.m_38903_(itemstack, 3, 39, true)) {
               return;
            }

            this.f_40028_.m_6836_(0, itemstack);
         }

         ItemStack itemstack1 = this.f_40028_.m_8020_(1);
         if (!itemstack1.m_41619_()) {
            if (!this.m_38903_(itemstack1, 3, 39, true)) {
               return;
            }

            this.f_40028_.m_6836_(1, itemstack1);
         }

         if (this.f_40028_.m_8020_(0).m_41619_() && this.f_40028_.m_8020_(1).m_41619_()) {
            ItemStack itemstack2 = this.m_40075_().get(p_40073_).m_45358_();
            this.m_40060_(0, itemstack2);
            ItemStack itemstack3 = this.m_40075_().get(p_40073_).m_45364_();
            this.m_40060_(1, itemstack3);
         }

      }
   }

   private void m_40060_(int p_40061_, ItemStack p_40062_) {
      if (!p_40062_.m_41619_()) {
         for(int i = 3; i < 39; ++i) {
            ItemStack itemstack = this.f_38839_.get(i).m_7993_();
            if (!itemstack.m_41619_() && ItemStack.m_150942_(p_40062_, itemstack)) {
               ItemStack itemstack1 = this.f_40028_.m_8020_(p_40061_);
               int j = itemstack1.m_41619_() ? 0 : itemstack1.m_41613_();
               int k = Math.min(p_40062_.m_41741_() - j, itemstack.m_41613_());
               ItemStack itemstack2 = itemstack.m_41777_();
               int l = j + k;
               itemstack.m_41774_(k);
               itemstack2.m_41764_(l);
               this.f_40028_.m_6836_(p_40061_, itemstack2);
               if (l >= p_40062_.m_41741_()) {
                  break;
               }
            }
         }
      }

   }

   public void m_40046_(MerchantOffers p_40047_) {
      this.f_40027_.m_6255_(p_40047_);
   }

   public MerchantOffers m_40075_() {
      return this.f_40027_.m_6616_();
   }

   public boolean m_40076_() {
      return this.f_40030_;
   }
}