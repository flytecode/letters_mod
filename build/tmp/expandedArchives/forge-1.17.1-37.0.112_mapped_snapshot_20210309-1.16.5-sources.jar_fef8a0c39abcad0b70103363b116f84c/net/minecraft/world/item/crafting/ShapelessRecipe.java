package net.minecraft.world.item.crafting;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ShapelessRecipe implements CraftingRecipe {
   private final ResourceLocation f_44241_;
   final String f_44242_;
   final ItemStack f_44243_;
   final NonNullList<Ingredient> f_44244_;
   private final boolean isSimple;

   public ShapelessRecipe(ResourceLocation p_44246_, String p_44247_, ItemStack p_44248_, NonNullList<Ingredient> p_44249_) {
      this.f_44241_ = p_44246_;
      this.f_44242_ = p_44247_;
      this.f_44243_ = p_44248_;
      this.f_44244_ = p_44249_;
      this.isSimple = p_44249_.stream().allMatch(Ingredient::isSimple);
   }

   public ResourceLocation m_6423_() {
      return this.f_44241_;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44077_;
   }

   public String m_6076_() {
      return this.f_44242_;
   }

   public ItemStack m_8043_() {
      return this.f_44243_;
   }

   public NonNullList<Ingredient> m_7527_() {
      return this.f_44244_;
   }

   public boolean m_5818_(CraftingContainer p_44262_, Level p_44263_) {
      StackedContents stackedcontents = new StackedContents();
      java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
      int i = 0;

      for(int j = 0; j < p_44262_.m_6643_(); ++j) {
         ItemStack itemstack = p_44262_.m_8020_(j);
         if (!itemstack.m_41619_()) {
            ++i;
            if (isSimple)
            stackedcontents.m_36468_(itemstack, 1);
            else inputs.add(itemstack);
         }
      }

      return i == this.f_44244_.size() && (isSimple ? stackedcontents.m_36475_(this, (IntList)null) : net.minecraftforge.common.util.RecipeMatcher.findMatches(inputs,  this.f_44244_) != null);
   }

   public ItemStack m_5874_(CraftingContainer p_44260_) {
      return this.f_44243_.m_41777_();
   }

   public boolean m_8004_(int p_44252_, int p_44253_) {
      return p_44252_ * p_44253_ >= this.f_44244_.size();
   }

   public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<ShapelessRecipe> {
      private static final ResourceLocation NAME = new ResourceLocation("minecraft", "crafting_shapeless");
      public ShapelessRecipe m_6729_(ResourceLocation p_44290_, JsonObject p_44291_) {
         String s = GsonHelper.m_13851_(p_44291_, "group", "");
         NonNullList<Ingredient> nonnulllist = m_44275_(GsonHelper.m_13933_(p_44291_, "ingredients"));
         if (nonnulllist.isEmpty()) {
            throw new JsonParseException("No ingredients for shapeless recipe");
         } else if (nonnulllist.size() > ShapedRecipe.MAX_WIDTH * ShapedRecipe.MAX_HEIGHT) {
            throw new JsonParseException("Too many ingredients for shapeless recipe. The maximum is " + (ShapedRecipe.MAX_WIDTH * ShapedRecipe.MAX_HEIGHT));
         } else {
            ItemStack itemstack = ShapedRecipe.m_151274_(GsonHelper.m_13930_(p_44291_, "result"));
            return new ShapelessRecipe(p_44290_, s, itemstack, nonnulllist);
         }
      }

      private static NonNullList<Ingredient> m_44275_(JsonArray p_44276_) {
         NonNullList<Ingredient> nonnulllist = NonNullList.m_122779_();

         for(int i = 0; i < p_44276_.size(); ++i) {
            Ingredient ingredient = Ingredient.m_43917_(p_44276_.get(i));
            if (!ingredient.m_43947_()) {
               nonnulllist.add(ingredient);
            }
         }

         return nonnulllist;
      }

      public ShapelessRecipe m_8005_(ResourceLocation p_44293_, FriendlyByteBuf p_44294_) {
         String s = p_44294_.m_130277_();
         int i = p_44294_.m_130242_();
         NonNullList<Ingredient> nonnulllist = NonNullList.m_122780_(i, Ingredient.f_43901_);

         for(int j = 0; j < nonnulllist.size(); ++j) {
            nonnulllist.set(j, Ingredient.m_43940_(p_44294_));
         }

         ItemStack itemstack = p_44294_.m_130267_();
         return new ShapelessRecipe(p_44293_, s, itemstack, nonnulllist);
      }

      public void m_6178_(FriendlyByteBuf p_44281_, ShapelessRecipe p_44282_) {
         p_44281_.m_130070_(p_44282_.f_44242_);
         p_44281_.m_130130_(p_44282_.f_44244_.size());

         for(Ingredient ingredient : p_44282_.f_44244_) {
            ingredient.m_43923_(p_44281_);
         }

         p_44281_.m_130055_(p_44282_.f_44243_);
      }
   }
}
