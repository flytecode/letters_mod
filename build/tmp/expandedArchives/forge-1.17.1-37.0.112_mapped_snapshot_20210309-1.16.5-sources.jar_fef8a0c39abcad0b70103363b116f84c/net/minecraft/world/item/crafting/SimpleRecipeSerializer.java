package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import java.util.function.Function;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class SimpleRecipeSerializer<T extends Recipe<?>> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>>  implements RecipeSerializer<T> {
   private final Function<ResourceLocation, T> f_44397_;

   public SimpleRecipeSerializer(Function<ResourceLocation, T> p_44399_) {
      this.f_44397_ = p_44399_;
   }

   public T m_6729_(ResourceLocation p_44404_, JsonObject p_44405_) {
      return this.f_44397_.apply(p_44404_);
   }

   public T m_8005_(ResourceLocation p_44407_, FriendlyByteBuf p_44408_) {
      return this.f_44397_.apply(p_44407_);
   }

   public void m_6178_(FriendlyByteBuf p_44401_, T p_44402_) {
   }
}
