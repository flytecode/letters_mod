package net.minecraft.world.item.crafting;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public abstract class CustomRecipe implements CraftingRecipe {
   private final ResourceLocation f_43831_;

   public CustomRecipe(ResourceLocation p_43833_) {
      this.f_43831_ = p_43833_;
   }

   public ResourceLocation m_6423_() {
      return this.f_43831_;
   }

   public boolean m_5598_() {
      return true;
   }

   public ItemStack m_8043_() {
      return ItemStack.f_41583_;
   }
}