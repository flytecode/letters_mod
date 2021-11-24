package net.minecraft.data.recipes;

import com.google.gson.JsonObject;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;

public class SpecialRecipeBuilder {
   final SimpleRecipeSerializer<?> f_126354_;

   public SpecialRecipeBuilder(SimpleRecipeSerializer<?> p_126356_) {
      this.f_126354_ = p_126356_;
   }

   public static SpecialRecipeBuilder m_126357_(SimpleRecipeSerializer<?> p_126358_) {
      return new SpecialRecipeBuilder(p_126358_);
   }

   public void m_126359_(Consumer<FinishedRecipe> p_126360_, final String p_126361_) {
      p_126360_.accept(new FinishedRecipe() {
         public void m_7917_(JsonObject p_126370_) {
         }

         public RecipeSerializer<?> m_6637_() {
            return SpecialRecipeBuilder.this.f_126354_;
         }

         public ResourceLocation m_6445_() {
            return new ResourceLocation(p_126361_);
         }

         @Nullable
         public JsonObject m_5860_() {
            return null;
         }

         public ResourceLocation m_6448_() {
            return new ResourceLocation("");
         }
      });
   }
}