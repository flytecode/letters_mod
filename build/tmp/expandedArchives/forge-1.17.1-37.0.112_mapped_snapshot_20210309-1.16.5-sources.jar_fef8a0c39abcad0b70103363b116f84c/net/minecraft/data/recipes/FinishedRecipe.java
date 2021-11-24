package net.minecraft.data.recipes;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface FinishedRecipe {
   void m_7917_(JsonObject p_125967_);

   default JsonObject m_125966_() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("type", Registry.f_122865_.m_7981_(this.m_6637_()).toString());
      this.m_7917_(jsonobject);
      return jsonobject;
   }

   ResourceLocation m_6445_();

   RecipeSerializer<?> m_6637_();

   @Nullable
   JsonObject m_5860_();

   @Nullable
   ResourceLocation m_6448_();
}