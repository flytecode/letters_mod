package net.minecraft.world.item.crafting;

import net.minecraft.world.inventory.CraftingContainer;

public interface CraftingRecipe extends Recipe<CraftingContainer> {
   default RecipeType<?> m_6671_() {
      return RecipeType.f_44107_;
   }
}