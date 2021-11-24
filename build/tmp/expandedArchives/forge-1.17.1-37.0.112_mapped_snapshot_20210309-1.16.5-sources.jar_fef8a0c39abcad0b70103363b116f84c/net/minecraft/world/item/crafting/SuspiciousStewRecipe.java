package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;

public class SuspiciousStewRecipe extends CustomRecipe {
   public SuspiciousStewRecipe(ResourceLocation p_44487_) {
      super(p_44487_);
   }

   public boolean m_5818_(CraftingContainer p_44499_, Level p_44500_) {
      boolean flag = false;
      boolean flag1 = false;
      boolean flag2 = false;
      boolean flag3 = false;

      for(int i = 0; i < p_44499_.m_6643_(); ++i) {
         ItemStack itemstack = p_44499_.m_8020_(i);
         if (!itemstack.m_41619_()) {
            if (itemstack.m_150930_(Blocks.f_50072_.m_5456_()) && !flag2) {
               flag2 = true;
            } else if (itemstack.m_150930_(Blocks.f_50073_.m_5456_()) && !flag1) {
               flag1 = true;
            } else if (itemstack.m_150922_(ItemTags.f_13145_) && !flag) {
               flag = true;
            } else {
               if (!itemstack.m_150930_(Items.f_42399_) || flag3) {
                  return false;
               }

               flag3 = true;
            }
         }
      }

      return flag && flag2 && flag1 && flag3;
   }

   public ItemStack m_5874_(CraftingContainer p_44497_) {
      ItemStack itemstack = ItemStack.f_41583_;

      for(int i = 0; i < p_44497_.m_6643_(); ++i) {
         ItemStack itemstack1 = p_44497_.m_8020_(i);
         if (!itemstack1.m_41619_() && itemstack1.m_150922_(ItemTags.f_13145_)) {
            itemstack = itemstack1;
            break;
         }
      }

      ItemStack itemstack2 = new ItemStack(Items.f_42718_, 1);
      if (itemstack.m_41720_() instanceof BlockItem && ((BlockItem)itemstack.m_41720_()).m_40614_() instanceof FlowerBlock) {
         FlowerBlock flowerblock = (FlowerBlock)((BlockItem)itemstack.m_41720_()).m_40614_();
         MobEffect mobeffect = flowerblock.m_53521_();
         SuspiciousStewItem.m_43258_(itemstack2, mobeffect, flowerblock.m_53522_());
      }

      return itemstack2;
   }

   public boolean m_8004_(int p_44489_, int p_44490_) {
      return p_44489_ >= 2 && p_44490_ >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44089_;
   }
}