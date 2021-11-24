package net.minecraft.world.item.crafting;

import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;

public interface RecipeType<T extends Recipe<?>> {
   RecipeType<CraftingRecipe> f_44107_ = m_44119_("crafting");
   RecipeType<SmeltingRecipe> f_44108_ = m_44119_("smelting");
   RecipeType<BlastingRecipe> f_44109_ = m_44119_("blasting");
   RecipeType<SmokingRecipe> f_44110_ = m_44119_("smoking");
   RecipeType<CampfireCookingRecipe> f_44111_ = m_44119_("campfire_cooking");
   RecipeType<StonecutterRecipe> f_44112_ = m_44119_("stonecutting");
   RecipeType<UpgradeRecipe> f_44113_ = m_44119_("smithing");

   static <T extends Recipe<?>> RecipeType<T> m_44119_(final String p_44120_) {
      return Registry.m_122965_(Registry.f_122864_, new ResourceLocation(p_44120_), new RecipeType<T>() {
         public String toString() {
            return p_44120_;
         }
      });
   }

   default <C extends Container> Optional<T> m_44115_(Recipe<C> p_44116_, Level p_44117_, C p_44118_) {
      return p_44116_.m_5818_(p_44118_, p_44117_) ? Optional.of((T)p_44116_) : Optional.empty();
   }
}