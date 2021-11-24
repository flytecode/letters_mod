package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class MapCloningRecipe extends CustomRecipe {
   public MapCloningRecipe(ResourceLocation p_43968_) {
      super(p_43968_);
   }

   public boolean m_5818_(CraftingContainer p_43980_, Level p_43981_) {
      int i = 0;
      ItemStack itemstack = ItemStack.f_41583_;

      for(int j = 0; j < p_43980_.m_6643_(); ++j) {
         ItemStack itemstack1 = p_43980_.m_8020_(j);
         if (!itemstack1.m_41619_()) {
            if (itemstack1.m_150930_(Items.f_42573_)) {
               if (!itemstack.m_41619_()) {
                  return false;
               }

               itemstack = itemstack1;
            } else {
               if (!itemstack1.m_150930_(Items.f_42676_)) {
                  return false;
               }

               ++i;
            }
         }
      }

      return !itemstack.m_41619_() && i > 0;
   }

   public ItemStack m_5874_(CraftingContainer p_43978_) {
      int i = 0;
      ItemStack itemstack = ItemStack.f_41583_;

      for(int j = 0; j < p_43978_.m_6643_(); ++j) {
         ItemStack itemstack1 = p_43978_.m_8020_(j);
         if (!itemstack1.m_41619_()) {
            if (itemstack1.m_150930_(Items.f_42573_)) {
               if (!itemstack.m_41619_()) {
                  return ItemStack.f_41583_;
               }

               itemstack = itemstack1;
            } else {
               if (!itemstack1.m_150930_(Items.f_42676_)) {
                  return ItemStack.f_41583_;
               }

               ++i;
            }
         }
      }

      if (!itemstack.m_41619_() && i >= 1) {
         ItemStack itemstack2 = itemstack.m_41777_();
         itemstack2.m_41764_(i + 1);
         return itemstack2;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public boolean m_8004_(int p_43970_, int p_43971_) {
      return p_43970_ >= 3 && p_43971_ >= 3;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44080_;
   }
}