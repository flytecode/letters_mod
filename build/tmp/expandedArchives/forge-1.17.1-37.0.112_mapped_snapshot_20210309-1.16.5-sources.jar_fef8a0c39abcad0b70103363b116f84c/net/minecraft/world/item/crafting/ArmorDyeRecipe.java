package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ArmorDyeRecipe extends CustomRecipe {
   public ArmorDyeRecipe(ResourceLocation p_43757_) {
      super(p_43757_);
   }

   public boolean m_5818_(CraftingContainer p_43769_, Level p_43770_) {
      ItemStack itemstack = ItemStack.f_41583_;
      List<ItemStack> list = Lists.newArrayList();

      for(int i = 0; i < p_43769_.m_6643_(); ++i) {
         ItemStack itemstack1 = p_43769_.m_8020_(i);
         if (!itemstack1.m_41619_()) {
            if (itemstack1.m_41720_() instanceof DyeableLeatherItem) {
               if (!itemstack.m_41619_()) {
                  return false;
               }

               itemstack = itemstack1;
            } else {
               if (!(itemstack1.m_41720_() instanceof DyeItem)) {
                  return false;
               }

               list.add(itemstack1);
            }
         }
      }

      return !itemstack.m_41619_() && !list.isEmpty();
   }

   public ItemStack m_5874_(CraftingContainer p_43767_) {
      List<DyeItem> list = Lists.newArrayList();
      ItemStack itemstack = ItemStack.f_41583_;

      for(int i = 0; i < p_43767_.m_6643_(); ++i) {
         ItemStack itemstack1 = p_43767_.m_8020_(i);
         if (!itemstack1.m_41619_()) {
            Item item = itemstack1.m_41720_();
            if (item instanceof DyeableLeatherItem) {
               if (!itemstack.m_41619_()) {
                  return ItemStack.f_41583_;
               }

               itemstack = itemstack1.m_41777_();
            } else {
               if (!(item instanceof DyeItem)) {
                  return ItemStack.f_41583_;
               }

               list.add((DyeItem)item);
            }
         }
      }

      return !itemstack.m_41619_() && !list.isEmpty() ? DyeableLeatherItem.m_41118_(itemstack, list) : ItemStack.f_41583_;
   }

   public boolean m_8004_(int p_43759_, int p_43760_) {
      return p_43759_ * p_43760_ >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44078_;
   }
}