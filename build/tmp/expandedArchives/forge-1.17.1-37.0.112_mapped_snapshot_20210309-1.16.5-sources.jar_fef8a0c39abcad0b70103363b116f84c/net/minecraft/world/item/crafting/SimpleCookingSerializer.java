package net.minecraft.world.item.crafting;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;

public class SimpleCookingSerializer<T extends AbstractCookingRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
   private final int f_44327_;
   private final SimpleCookingSerializer.CookieBaker<T> f_44328_;

   public SimpleCookingSerializer(SimpleCookingSerializer.CookieBaker<T> p_44330_, int p_44331_) {
      this.f_44327_ = p_44331_;
      this.f_44328_ = p_44330_;
   }

   public T m_6729_(ResourceLocation p_44347_, JsonObject p_44348_) {
      String s = GsonHelper.m_13851_(p_44348_, "group", "");
      JsonElement jsonelement = (JsonElement)(GsonHelper.m_13885_(p_44348_, "ingredient") ? GsonHelper.m_13933_(p_44348_, "ingredient") : GsonHelper.m_13930_(p_44348_, "ingredient"));
      Ingredient ingredient = Ingredient.m_43917_(jsonelement);
      //Forge: Check if primitive string to keep vanilla or a object which can contain a count field.
      if (!p_44348_.has("result")) throw new com.google.gson.JsonSyntaxException("Missing result, expected to find a string or object");
      ItemStack itemstack;
      if (p_44348_.get("result").isJsonObject()) itemstack = ShapedRecipe.m_151274_(GsonHelper.m_13930_(p_44348_, "result"));
      else {
      String s1 = GsonHelper.m_13906_(p_44348_, "result");
      ResourceLocation resourcelocation = new ResourceLocation(s1);
      itemstack = new ItemStack(Registry.f_122827_.m_6612_(resourcelocation).orElseThrow(() -> {
         return new IllegalStateException("Item: " + s1 + " does not exist");
      }));
      }
      float f = GsonHelper.m_13820_(p_44348_, "experience", 0.0F);
      int i = GsonHelper.m_13824_(p_44348_, "cookingtime", this.f_44327_);
      return this.f_44328_.m_44352_(p_44347_, s, ingredient, itemstack, f, i);
   }

   public T m_8005_(ResourceLocation p_44350_, FriendlyByteBuf p_44351_) {
      String s = p_44351_.m_130277_();
      Ingredient ingredient = Ingredient.m_43940_(p_44351_);
      ItemStack itemstack = p_44351_.m_130267_();
      float f = p_44351_.readFloat();
      int i = p_44351_.m_130242_();
      return this.f_44328_.m_44352_(p_44350_, s, ingredient, itemstack, f, i);
   }

   public void m_6178_(FriendlyByteBuf p_44335_, T p_44336_) {
      p_44335_.m_130070_(p_44336_.f_43728_);
      p_44336_.f_43729_.m_43923_(p_44335_);
      p_44335_.m_130055_(p_44336_.f_43730_);
      p_44335_.writeFloat(p_44336_.f_43731_);
      p_44335_.m_130130_(p_44336_.f_43732_);
   }

   interface CookieBaker<T extends AbstractCookingRecipe> {
      T m_44352_(ResourceLocation p_44353_, String p_44354_, Ingredient p_44355_, ItemStack p_44356_, float p_44357_, int p_44358_);
   }
}
