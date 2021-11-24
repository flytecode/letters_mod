package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;

public class ShulkerBoxColoring extends CustomRecipe {
   public ShulkerBoxColoring(ResourceLocation p_44312_) {
      super(p_44312_);
   }

   public boolean m_5818_(CraftingContainer p_44324_, Level p_44325_) {
      int i = 0;
      int j = 0;

      for(int k = 0; k < p_44324_.m_6643_(); ++k) {
         ItemStack itemstack = p_44324_.m_8020_(k);
         if (!itemstack.m_41619_()) {
            if (Block.m_49814_(itemstack.m_41720_()) instanceof ShulkerBoxBlock) {
               ++i;
            } else {
               if (!itemstack.m_150922_(net.minecraftforge.common.Tags.Items.DYES)) {
                  return false;
               }

               ++j;
            }

            if (j > 1 || i > 1) {
               return false;
            }
         }
      }

      return i == 1 && j == 1;
   }

   public ItemStack m_5874_(CraftingContainer p_44322_) {
      ItemStack itemstack = ItemStack.f_41583_;
      net.minecraft.world.item.DyeColor dyecolor = net.minecraft.world.item.DyeColor.WHITE;

      for(int i = 0; i < p_44322_.m_6643_(); ++i) {
         ItemStack itemstack1 = p_44322_.m_8020_(i);
         if (!itemstack1.m_41619_()) {
            Item item = itemstack1.m_41720_();
            if (Block.m_49814_(item) instanceof ShulkerBoxBlock) {
               itemstack = itemstack1;
            } else {
               net.minecraft.world.item.DyeColor tmp = net.minecraft.world.item.DyeColor.getColor(itemstack1);
               if (tmp != null) dyecolor = tmp;
            }
         }
      }

      ItemStack itemstack2 = ShulkerBoxBlock.m_56250_(dyecolor);
      if (itemstack.m_41782_()) {
         itemstack2.m_41751_(itemstack.m_41783_().m_6426_());
      }

      return itemstack2;
   }

   public boolean m_8004_(int p_44314_, int p_44315_) {
      return p_44314_ * p_44315_ >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44088_;
   }
}
