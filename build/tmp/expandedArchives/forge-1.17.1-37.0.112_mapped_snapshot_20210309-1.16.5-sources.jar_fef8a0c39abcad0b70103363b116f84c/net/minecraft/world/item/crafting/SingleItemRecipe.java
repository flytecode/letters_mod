package net.minecraft.world.item.crafting;

import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public abstract class SingleItemRecipe implements Recipe<Container> {
   protected final Ingredient f_44409_;
   protected final ItemStack f_44410_;
   private final RecipeType<?> f_44413_;
   private final RecipeSerializer<?> f_44414_;
   protected final ResourceLocation f_44411_;
   protected final String f_44412_;

   public SingleItemRecipe(RecipeType<?> p_44416_, RecipeSerializer<?> p_44417_, ResourceLocation p_44418_, String p_44419_, Ingredient p_44420_, ItemStack p_44421_) {
      this.f_44413_ = p_44416_;
      this.f_44414_ = p_44417_;
      this.f_44411_ = p_44418_;
      this.f_44412_ = p_44419_;
      this.f_44409_ = p_44420_;
      this.f_44410_ = p_44421_;
   }

   public RecipeType<?> m_6671_() {
      return this.f_44413_;
   }

   public RecipeSerializer<?> m_7707_() {
      return this.f_44414_;
   }

   public ResourceLocation m_6423_() {
      return this.f_44411_;
   }

   public String m_6076_() {
      return this.f_44412_;
   }

   public ItemStack m_8043_() {
      return this.f_44410_;
   }

   public NonNullList<Ingredient> m_7527_() {
      NonNullList<Ingredient> nonnulllist = NonNullList.m_122779_();
      nonnulllist.add(this.f_44409_);
      return nonnulllist;
   }

   public boolean m_8004_(int p_44424_, int p_44425_) {
      return true;
   }

   public ItemStack m_5874_(Container p_44427_) {
      return this.f_44410_.m_41777_();
   }

   public static class Serializer<T extends SingleItemRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {
      final SingleItemRecipe.Serializer.SingleItemMaker<T> f_44433_;

      protected Serializer(SingleItemRecipe.Serializer.SingleItemMaker<T> p_44435_) {
         this.f_44433_ = p_44435_;
      }

      public T m_6729_(ResourceLocation p_44449_, JsonObject p_44450_) {
         String s = GsonHelper.m_13851_(p_44450_, "group", "");
         Ingredient ingredient;
         if (GsonHelper.m_13885_(p_44450_, "ingredient")) {
            ingredient = Ingredient.m_43917_(GsonHelper.m_13933_(p_44450_, "ingredient"));
         } else {
            ingredient = Ingredient.m_43917_(GsonHelper.m_13930_(p_44450_, "ingredient"));
         }

         String s1 = GsonHelper.m_13906_(p_44450_, "result");
         int i = GsonHelper.m_13927_(p_44450_, "count");
         ItemStack itemstack = new ItemStack(Registry.f_122827_.m_7745_(new ResourceLocation(s1)), i);
         return this.f_44433_.m_44454_(p_44449_, s, ingredient, itemstack);
      }

      public T m_8005_(ResourceLocation p_44452_, FriendlyByteBuf p_44453_) {
         String s = p_44453_.m_130277_();
         Ingredient ingredient = Ingredient.m_43940_(p_44453_);
         ItemStack itemstack = p_44453_.m_130267_();
         return this.f_44433_.m_44454_(p_44452_, s, ingredient, itemstack);
      }

      public void m_6178_(FriendlyByteBuf p_44440_, T p_44441_) {
         p_44440_.m_130070_(p_44441_.f_44412_);
         p_44441_.f_44409_.m_43923_(p_44440_);
         p_44440_.m_130055_(p_44441_.f_44410_);
      }

      interface SingleItemMaker<T extends SingleItemRecipe> {
         T m_44454_(ResourceLocation p_44455_, String p_44456_, Ingredient p_44457_, ItemStack p_44458_);
      }
   }
}
