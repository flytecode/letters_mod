package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.level.Level;

public class BookCloningRecipe extends CustomRecipe {
   public BookCloningRecipe(ResourceLocation p_43802_) {
      super(p_43802_);
   }

   public boolean m_5818_(CraftingContainer p_43814_, Level p_43815_) {
      int i = 0;
      ItemStack itemstack = ItemStack.f_41583_;

      for(int j = 0; j < p_43814_.m_6643_(); ++j) {
         ItemStack itemstack1 = p_43814_.m_8020_(j);
         if (!itemstack1.m_41619_()) {
            if (itemstack1.m_150930_(Items.f_42615_)) {
               if (!itemstack.m_41619_()) {
                  return false;
               }

               itemstack = itemstack1;
            } else {
               if (!itemstack1.m_150930_(Items.f_42614_)) {
                  return false;
               }

               ++i;
            }
         }
      }

      return !itemstack.m_41619_() && itemstack.m_41782_() && i > 0;
   }

   public ItemStack m_5874_(CraftingContainer p_43812_) {
      int i = 0;
      ItemStack itemstack = ItemStack.f_41583_;

      for(int j = 0; j < p_43812_.m_6643_(); ++j) {
         ItemStack itemstack1 = p_43812_.m_8020_(j);
         if (!itemstack1.m_41619_()) {
            if (itemstack1.m_150930_(Items.f_42615_)) {
               if (!itemstack.m_41619_()) {
                  return ItemStack.f_41583_;
               }

               itemstack = itemstack1;
            } else {
               if (!itemstack1.m_150930_(Items.f_42614_)) {
                  return ItemStack.f_41583_;
               }

               ++i;
            }
         }
      }

      if (!itemstack.m_41619_() && itemstack.m_41782_() && i >= 1 && WrittenBookItem.m_43473_(itemstack) < 2) {
         ItemStack itemstack2 = new ItemStack(Items.f_42615_, i);
         CompoundTag compoundtag = itemstack.m_41783_().m_6426_();
         compoundtag.m_128405_("generation", WrittenBookItem.m_43473_(itemstack) + 1);
         itemstack2.m_41751_(compoundtag);
         return itemstack2;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public NonNullList<ItemStack> m_7457_(CraftingContainer p_43820_) {
      NonNullList<ItemStack> nonnulllist = NonNullList.m_122780_(p_43820_.m_6643_(), ItemStack.f_41583_);

      for(int i = 0; i < nonnulllist.size(); ++i) {
         ItemStack itemstack = p_43820_.m_8020_(i);
         if (itemstack.hasContainerItem()) {
            nonnulllist.set(i, itemstack.getContainerItem());
         } else if (itemstack.m_41720_() instanceof WrittenBookItem) {
            ItemStack itemstack1 = itemstack.m_41777_();
            itemstack1.m_41764_(1);
            nonnulllist.set(i, itemstack1);
            break;
         }
      }

      return nonnulllist;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44079_;
   }

   public boolean m_8004_(int p_43804_, int p_43805_) {
      return p_43804_ >= 3 && p_43805_ >= 3;
   }
}
