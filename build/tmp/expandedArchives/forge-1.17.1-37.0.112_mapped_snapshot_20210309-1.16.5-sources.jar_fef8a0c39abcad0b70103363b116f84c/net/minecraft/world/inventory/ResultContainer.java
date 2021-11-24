package net.minecraft.world.inventory;

import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public class ResultContainer implements Container, RecipeHolder {
   private final NonNullList<ItemStack> f_40140_ = NonNullList.m_122780_(1, ItemStack.f_41583_);
   @Nullable
   private Recipe<?> f_40141_;

   public int m_6643_() {
      return 1;
   }

   public boolean m_7983_() {
      for(ItemStack itemstack : this.f_40140_) {
         if (!itemstack.m_41619_()) {
            return false;
         }
      }

      return true;
   }

   public ItemStack m_8020_(int p_40147_) {
      return this.f_40140_.get(0);
   }

   public ItemStack m_7407_(int p_40149_, int p_40150_) {
      return ContainerHelper.m_18966_(this.f_40140_, 0);
   }

   public ItemStack m_8016_(int p_40160_) {
      return ContainerHelper.m_18966_(this.f_40140_, 0);
   }

   public void m_6836_(int p_40152_, ItemStack p_40153_) {
      this.f_40140_.set(0, p_40153_);
   }

   public void m_6596_() {
   }

   public boolean m_6542_(Player p_40155_) {
      return true;
   }

   public void m_6211_() {
      this.f_40140_.clear();
   }

   public void m_6029_(@Nullable Recipe<?> p_40157_) {
      this.f_40141_ = p_40157_;
   }

   @Nullable
   public Recipe<?> m_7928_() {
      return this.f_40141_;
   }
}