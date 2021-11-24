package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public class MerchantContainer implements Container {
   private final Merchant f_39997_;
   private final NonNullList<ItemStack> f_39998_ = NonNullList.m_122780_(3, ItemStack.f_41583_);
   @Nullable
   private MerchantOffer f_39999_;
   private int f_40000_;
   private int f_40001_;

   public MerchantContainer(Merchant p_40003_) {
      this.f_39997_ = p_40003_;
   }

   public int m_6643_() {
      return this.f_39998_.size();
   }

   public boolean m_7983_() {
      for(ItemStack itemstack : this.f_39998_) {
         if (!itemstack.m_41619_()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack m_8020_(int p_40008_) {
      return this.f_39998_.get(p_40008_);
   }

   public ItemStack m_7407_(int p_40010_, int p_40011_) {
      ItemStack itemstack = this.f_39998_.get(p_40010_);
      if (p_40010_ == 2 && !itemstack.m_41619_()) {
         return ContainerHelper.m_18969_(this.f_39998_, p_40010_, itemstack.m_41613_());
      } else {
         ItemStack itemstack1 = ContainerHelper.m_18969_(this.f_39998_, p_40010_, p_40011_);
         if (!itemstack1.m_41619_() && this.m_40022_(p_40010_)) {
            this.m_40024_();
         }

         return itemstack1;
      }
   }

   private boolean m_40022_(int p_40023_) {
      return p_40023_ == 0 || p_40023_ == 1;
   }

   public ItemStack m_8016_(int p_40018_) {
      return ContainerHelper.m_18966_(this.f_39998_, p_40018_);
   }

   public void m_6836_(int p_40013_, ItemStack p_40014_) {
      this.f_39998_.set(p_40013_, p_40014_);
      if (!p_40014_.m_41619_() && p_40014_.m_41613_() > this.m_6893_()) {
         p_40014_.m_41764_(this.m_6893_());
      }

      if (this.m_40022_(p_40013_)) {
         this.m_40024_();
      }

   }

   public boolean m_6542_(Player p_40016_) {
      return this.f_39997_.m_7962_() == p_40016_;
   }

   public void m_6596_() {
      this.m_40024_();
   }

   public void m_40024_() {
      this.f_39999_ = null;
      ItemStack itemstack;
      ItemStack itemstack1;
      if (this.f_39998_.get(0).m_41619_()) {
         itemstack = this.f_39998_.get(1);
         itemstack1 = ItemStack.f_41583_;
      } else {
         itemstack = this.f_39998_.get(0);
         itemstack1 = this.f_39998_.get(1);
      }

      if (itemstack.m_41619_()) {
         this.m_6836_(2, ItemStack.f_41583_);
         this.f_40001_ = 0;
      } else {
         MerchantOffers merchantoffers = this.f_39997_.m_6616_();
         if (!merchantoffers.isEmpty()) {
            MerchantOffer merchantoffer = merchantoffers.m_45389_(itemstack, itemstack1, this.f_40000_);
            if (merchantoffer == null || merchantoffer.m_45380_()) {
               this.f_39999_ = merchantoffer;
               merchantoffer = merchantoffers.m_45389_(itemstack1, itemstack, this.f_40000_);
            }

            if (merchantoffer != null && !merchantoffer.m_45380_()) {
               this.f_39999_ = merchantoffer;
               this.m_6836_(2, merchantoffer.m_45370_());
               this.f_40001_ = merchantoffer.m_45379_();
            } else {
               this.m_6836_(2, ItemStack.f_41583_);
               this.f_40001_ = 0;
            }
         }

         this.f_39997_.m_7713_(this.m_8020_(2));
      }
   }

   @Nullable
   public MerchantOffer m_40025_() {
      return this.f_39999_;
   }

   public void m_40020_(int p_40021_) {
      this.f_40000_ = p_40021_;
      this.m_40024_();
   }

   public void m_6211_() {
      this.f_39998_.clear();
   }

   public int m_40026_() {
      return this.f_40001_;
   }
}