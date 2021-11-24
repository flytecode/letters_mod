package net.minecraft.world.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;

public class CraftingContainer implements Container, StackedContentsCompatible {
   private final NonNullList<ItemStack> f_39320_;
   private final int f_39321_;
   private final int f_39322_;
   private final AbstractContainerMenu f_39323_;

   public CraftingContainer(AbstractContainerMenu p_39325_, int p_39326_, int p_39327_) {
      this.f_39320_ = NonNullList.m_122780_(p_39326_ * p_39327_, ItemStack.f_41583_);
      this.f_39323_ = p_39325_;
      this.f_39321_ = p_39326_;
      this.f_39322_ = p_39327_;
   }

   public int m_6643_() {
      return this.f_39320_.size();
   }

   public boolean m_7983_() {
      for(ItemStack itemstack : this.f_39320_) {
         if (!itemstack.m_41619_()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack m_8020_(int p_39332_) {
      return p_39332_ >= this.m_6643_() ? ItemStack.f_41583_ : this.f_39320_.get(p_39332_);
   }

   public ItemStack m_8016_(int p_39344_) {
      return ContainerHelper.m_18966_(this.f_39320_, p_39344_);
   }

   public ItemStack m_7407_(int p_39334_, int p_39335_) {
      ItemStack itemstack = ContainerHelper.m_18969_(this.f_39320_, p_39334_, p_39335_);
      if (!itemstack.m_41619_()) {
         this.f_39323_.m_6199_(this);
      }

      return itemstack;
   }

   public void m_6836_(int p_39337_, ItemStack p_39338_) {
      this.f_39320_.set(p_39337_, p_39338_);
      this.f_39323_.m_6199_(this);
   }

   public void m_6596_() {
   }

   public boolean m_6542_(Player p_39340_) {
      return true;
   }

   public void m_6211_() {
      this.f_39320_.clear();
   }

   public int m_39346_() {
      return this.f_39322_;
   }

   public int m_39347_() {
      return this.f_39321_;
   }

   public void m_5809_(StackedContents p_39342_) {
      for(ItemStack itemstack : this.f_39320_) {
         p_39342_.m_36466_(itemstack);
      }

   }
}