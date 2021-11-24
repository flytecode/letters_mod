package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class FireworkStarFadeRecipe extends CustomRecipe {
   private static final Ingredient f_43858_ = Ingredient.m_43929_(Items.f_42689_);

   public FireworkStarFadeRecipe(ResourceLocation p_43861_) {
      super(p_43861_);
   }

   public boolean m_5818_(CraftingContainer p_43873_, Level p_43874_) {
      boolean flag = false;
      boolean flag1 = false;

      for(int i = 0; i < p_43873_.m_6643_(); ++i) {
         ItemStack itemstack = p_43873_.m_8020_(i);
         if (!itemstack.m_41619_()) {
            if (itemstack.m_41720_() instanceof DyeItem) {
               flag = true;
            } else {
               if (!f_43858_.test(itemstack)) {
                  return false;
               }

               if (flag1) {
                  return false;
               }

               flag1 = true;
            }
         }
      }

      return flag1 && flag;
   }

   public ItemStack m_5874_(CraftingContainer p_43871_) {
      List<Integer> list = Lists.newArrayList();
      ItemStack itemstack = null;

      for(int i = 0; i < p_43871_.m_6643_(); ++i) {
         ItemStack itemstack1 = p_43871_.m_8020_(i);
         Item item = itemstack1.m_41720_();
         if (item instanceof DyeItem) {
            list.add(((DyeItem)item).m_41089_().m_41070_());
         } else if (f_43858_.test(itemstack1)) {
            itemstack = itemstack1.m_41777_();
            itemstack.m_41764_(1);
         }
      }

      if (itemstack != null && !list.isEmpty()) {
         itemstack.m_41698_("Explosion").m_128408_("FadeColors", list);
         return itemstack;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public boolean m_8004_(int p_43863_, int p_43864_) {
      return p_43863_ * p_43864_ >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44084_;
   }
}