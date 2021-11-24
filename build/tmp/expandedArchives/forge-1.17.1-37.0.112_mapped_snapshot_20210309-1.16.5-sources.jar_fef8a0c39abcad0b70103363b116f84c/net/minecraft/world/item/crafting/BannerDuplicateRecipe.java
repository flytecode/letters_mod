package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BannerBlockEntity;

public class BannerDuplicateRecipe extends CustomRecipe {
   public BannerDuplicateRecipe(ResourceLocation p_43773_) {
      super(p_43773_);
   }

   public boolean m_5818_(CraftingContainer p_43785_, Level p_43786_) {
      DyeColor dyecolor = null;
      ItemStack itemstack = null;
      ItemStack itemstack1 = null;

      for(int i = 0; i < p_43785_.m_6643_(); ++i) {
         ItemStack itemstack2 = p_43785_.m_8020_(i);
         if (!itemstack2.m_41619_()) {
            Item item = itemstack2.m_41720_();
            if (!(item instanceof BannerItem)) {
               return false;
            }

            BannerItem banneritem = (BannerItem)item;
            if (dyecolor == null) {
               dyecolor = banneritem.m_40545_();
            } else if (dyecolor != banneritem.m_40545_()) {
               return false;
            }

            int j = BannerBlockEntity.m_58504_(itemstack2);
            if (j > 6) {
               return false;
            }

            if (j > 0) {
               if (itemstack != null) {
                  return false;
               }

               itemstack = itemstack2;
            } else {
               if (itemstack1 != null) {
                  return false;
               }

               itemstack1 = itemstack2;
            }
         }
      }

      return itemstack != null && itemstack1 != null;
   }

   public ItemStack m_5874_(CraftingContainer p_43783_) {
      for(int i = 0; i < p_43783_.m_6643_(); ++i) {
         ItemStack itemstack = p_43783_.m_8020_(i);
         if (!itemstack.m_41619_()) {
            int j = BannerBlockEntity.m_58504_(itemstack);
            if (j > 0 && j <= 6) {
               ItemStack itemstack1 = itemstack.m_41777_();
               itemstack1.m_41764_(1);
               return itemstack1;
            }
         }
      }

      return ItemStack.f_41583_;
   }

   public NonNullList<ItemStack> m_7457_(CraftingContainer p_43791_) {
      NonNullList<ItemStack> nonnulllist = NonNullList.m_122780_(p_43791_.m_6643_(), ItemStack.f_41583_);

      for(int i = 0; i < nonnulllist.size(); ++i) {
         ItemStack itemstack = p_43791_.m_8020_(i);
         if (!itemstack.m_41619_()) {
            if (itemstack.hasContainerItem()) {
               nonnulllist.set(i, itemstack.getContainerItem());
            } else if (itemstack.m_41782_() && BannerBlockEntity.m_58504_(itemstack) > 0) {
               ItemStack itemstack1 = itemstack.m_41777_();
               itemstack1.m_41764_(1);
               nonnulllist.set(i, itemstack1);
            }
         }
      }

      return nonnulllist;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44086_;
   }

   public boolean m_8004_(int p_43775_, int p_43776_) {
      return p_43775_ * p_43776_ >= 2;
   }
}
