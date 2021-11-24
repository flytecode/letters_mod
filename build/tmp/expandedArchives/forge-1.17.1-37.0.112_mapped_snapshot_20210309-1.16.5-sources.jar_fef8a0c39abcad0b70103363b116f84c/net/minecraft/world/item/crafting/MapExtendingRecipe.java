package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class MapExtendingRecipe extends ShapedRecipe {
   public MapExtendingRecipe(ResourceLocation p_43984_) {
      super(p_43984_, "", 3, 3, NonNullList.m_122783_(Ingredient.f_43901_, Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42573_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_), Ingredient.m_43929_(Items.f_42516_)), new ItemStack(Items.f_42676_));
   }

   public boolean m_5818_(CraftingContainer p_43993_, Level p_43994_) {
      if (!super.m_5818_(p_43993_, p_43994_)) {
         return false;
      } else {
         ItemStack itemstack = ItemStack.f_41583_;

         for(int i = 0; i < p_43993_.m_6643_() && itemstack.m_41619_(); ++i) {
            ItemStack itemstack1 = p_43993_.m_8020_(i);
            if (itemstack1.m_150930_(Items.f_42573_)) {
               itemstack = itemstack1;
            }
         }

         if (itemstack.m_41619_()) {
            return false;
         } else {
            MapItemSavedData mapitemsaveddata = MapItem.m_42853_(itemstack, p_43994_);
            if (mapitemsaveddata == null) {
               return false;
            } else if (mapitemsaveddata.m_164810_()) {
               return false;
            } else {
               return mapitemsaveddata.f_77890_ < 4;
            }
         }
      }
   }

   public ItemStack m_5874_(CraftingContainer p_43991_) {
      ItemStack itemstack = ItemStack.f_41583_;

      for(int i = 0; i < p_43991_.m_6643_() && itemstack.m_41619_(); ++i) {
         ItemStack itemstack1 = p_43991_.m_8020_(i);
         if (itemstack1.m_150930_(Items.f_42573_)) {
            itemstack = itemstack1;
         }
      }

      itemstack = itemstack.m_41777_();
      itemstack.m_41764_(1);
      itemstack.m_41784_().m_128405_("map_scale_direction", 1);
      return itemstack;
   }

   public boolean m_5598_() {
      return true;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44081_;
   }
}