package net.minecraft.world.item.crafting;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public interface Recipe<C extends Container> {
   boolean m_5818_(C p_44002_, Level p_44003_);

   ItemStack m_5874_(C p_44001_);

   boolean m_8004_(int p_43999_, int p_44000_);

   ItemStack m_8043_();

   default NonNullList<ItemStack> m_7457_(C p_44004_) {
      NonNullList<ItemStack> nonnulllist = NonNullList.m_122780_(p_44004_.m_6643_(), ItemStack.f_41583_);

      for(int i = 0; i < nonnulllist.size(); ++i) {
         ItemStack item = p_44004_.m_8020_(i);
         if (item.hasContainerItem()) {
            nonnulllist.set(i, item.getContainerItem());
         }
      }

      return nonnulllist;
   }

   default NonNullList<Ingredient> m_7527_() {
      return NonNullList.m_122779_();
   }

   default boolean m_5598_() {
      return false;
   }

   default String m_6076_() {
      return "";
   }

   default ItemStack m_8042_() {
      return new ItemStack(Blocks.f_50091_);
   }

   ResourceLocation m_6423_();

   RecipeSerializer<?> m_7707_();

   RecipeType<?> m_6671_();

   default boolean m_142505_() {
      NonNullList<Ingredient> nonnulllist = this.m_7527_();
      return nonnulllist.isEmpty() || nonnulllist.stream().anyMatch((p_151268_) -> {
         return p_151268_.m_43908_().length == 0;
      });
   }
}
