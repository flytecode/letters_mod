package net.minecraft.data.recipes;

import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public interface RecipeBuilder {
   RecipeBuilder m_142284_(String p_176496_, CriterionTriggerInstance p_176497_);

   RecipeBuilder m_142409_(@Nullable String p_176495_);

   Item m_142372_();

   void m_142700_(Consumer<FinishedRecipe> p_176503_, ResourceLocation p_176504_);

   default void m_176498_(Consumer<FinishedRecipe> p_176499_) {
      this.m_142700_(p_176499_, m_176493_(this.m_142372_()));
   }

   default void m_176500_(Consumer<FinishedRecipe> p_176501_, String p_176502_) {
      ResourceLocation resourcelocation = m_176493_(this.m_142372_());
      ResourceLocation resourcelocation1 = new ResourceLocation(p_176502_);
      if (resourcelocation1.equals(resourcelocation)) {
         throw new IllegalStateException("Recipe " + p_176502_ + " should remove its 'save' argument as it is equal to default one");
      } else {
         this.m_142700_(p_176501_, resourcelocation1);
      }
   }

   static ResourceLocation m_176493_(ItemLike p_176494_) {
      return Registry.f_122827_.m_7981_(p_176494_.m_5456_());
   }
}