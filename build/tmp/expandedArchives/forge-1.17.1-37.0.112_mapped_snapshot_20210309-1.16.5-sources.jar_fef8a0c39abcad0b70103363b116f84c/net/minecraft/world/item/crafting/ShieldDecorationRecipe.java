package net.minecraft.world.item.crafting;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ShieldDecorationRecipe extends CustomRecipe {
   public ShieldDecorationRecipe(ResourceLocation p_44296_) {
      super(p_44296_);
   }

   public boolean m_5818_(CraftingContainer p_44308_, Level p_44309_) {
      ItemStack itemstack = ItemStack.f_41583_;
      ItemStack itemstack1 = ItemStack.f_41583_;

      for(int i = 0; i < p_44308_.m_6643_(); ++i) {
         ItemStack itemstack2 = p_44308_.m_8020_(i);
         if (!itemstack2.m_41619_()) {
            if (itemstack2.m_41720_() instanceof BannerItem) {
               if (!itemstack1.m_41619_()) {
                  return false;
               }

               itemstack1 = itemstack2;
            } else {
               if (!itemstack2.m_150930_(Items.f_42740_)) {
                  return false;
               }

               if (!itemstack.m_41619_()) {
                  return false;
               }

               if (itemstack2.m_41737_("BlockEntityTag") != null) {
                  return false;
               }

               itemstack = itemstack2;
            }
         }
      }

      return !itemstack.m_41619_() && !itemstack1.m_41619_();
   }

   public ItemStack m_5874_(CraftingContainer p_44306_) {
      ItemStack itemstack = ItemStack.f_41583_;
      ItemStack itemstack1 = ItemStack.f_41583_;

      for(int i = 0; i < p_44306_.m_6643_(); ++i) {
         ItemStack itemstack2 = p_44306_.m_8020_(i);
         if (!itemstack2.m_41619_()) {
            if (itemstack2.m_41720_() instanceof BannerItem) {
               itemstack = itemstack2;
            } else if (itemstack2.m_150930_(Items.f_42740_)) {
               itemstack1 = itemstack2.m_41777_();
            }
         }
      }

      if (itemstack1.m_41619_()) {
         return itemstack1;
      } else {
         CompoundTag compoundtag = itemstack.m_41737_("BlockEntityTag");
         CompoundTag compoundtag1 = compoundtag == null ? new CompoundTag() : compoundtag.m_6426_();
         compoundtag1.m_128405_("Base", ((BannerItem)itemstack.m_41720_()).m_40545_().m_41060_());
         itemstack1.m_41700_("BlockEntityTag", compoundtag1);
         return itemstack1;
      }
   }

   public boolean m_8004_(int p_44298_, int p_44299_) {
      return p_44298_ * p_44299_ >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44087_;
   }
}