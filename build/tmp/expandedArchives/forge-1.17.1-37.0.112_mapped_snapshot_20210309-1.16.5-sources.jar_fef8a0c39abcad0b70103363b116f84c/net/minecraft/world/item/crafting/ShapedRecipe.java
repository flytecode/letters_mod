package net.minecraft.world.item.crafting;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ShapedRecipe implements CraftingRecipe, net.minecraftforge.common.crafting.IShapedRecipe<CraftingContainer> {
   static int MAX_WIDTH = 3;
   static int MAX_HEIGHT = 3;
   /**
    * Expand the max width and height allowed in the deserializer.
    * This should be called by modders who add custom crafting tables that are larger than the vanilla 3x3.
    * @param width your max recipe width
    * @param height your max recipe height
    */
   public static void setCraftingSize(int width, int height) {
      if (MAX_WIDTH < width) MAX_WIDTH = width;
      if (MAX_HEIGHT < height) MAX_HEIGHT = height;
   }

   final int f_44146_;
   final int f_44147_;
   final NonNullList<Ingredient> f_44148_;
   final ItemStack f_44149_;
   private final ResourceLocation f_44150_;
   final String f_44151_;

   public ShapedRecipe(ResourceLocation p_44153_, String p_44154_, int p_44155_, int p_44156_, NonNullList<Ingredient> p_44157_, ItemStack p_44158_) {
      this.f_44150_ = p_44153_;
      this.f_44151_ = p_44154_;
      this.f_44146_ = p_44155_;
      this.f_44147_ = p_44156_;
      this.f_44148_ = p_44157_;
      this.f_44149_ = p_44158_;
   }

   public ResourceLocation m_6423_() {
      return this.f_44150_;
   }

   public RecipeSerializer<?> m_7707_() {
      return RecipeSerializer.f_44076_;
   }

   public String m_6076_() {
      return this.f_44151_;
   }

   public ItemStack m_8043_() {
      return this.f_44149_;
   }

   public NonNullList<Ingredient> m_7527_() {
      return this.f_44148_;
   }

   public boolean m_8004_(int p_44161_, int p_44162_) {
      return p_44161_ >= this.f_44146_ && p_44162_ >= this.f_44147_;
   }

   public boolean m_5818_(CraftingContainer p_44176_, Level p_44177_) {
      for(int i = 0; i <= p_44176_.m_39347_() - this.f_44146_; ++i) {
         for(int j = 0; j <= p_44176_.m_39346_() - this.f_44147_; ++j) {
            if (this.m_44170_(p_44176_, i, j, true)) {
               return true;
            }

            if (this.m_44170_(p_44176_, i, j, false)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean m_44170_(CraftingContainer p_44171_, int p_44172_, int p_44173_, boolean p_44174_) {
      for(int i = 0; i < p_44171_.m_39347_(); ++i) {
         for(int j = 0; j < p_44171_.m_39346_(); ++j) {
            int k = i - p_44172_;
            int l = j - p_44173_;
            Ingredient ingredient = Ingredient.f_43901_;
            if (k >= 0 && l >= 0 && k < this.f_44146_ && l < this.f_44147_) {
               if (p_44174_) {
                  ingredient = this.f_44148_.get(this.f_44146_ - k - 1 + l * this.f_44146_);
               } else {
                  ingredient = this.f_44148_.get(k + l * this.f_44146_);
               }
            }

            if (!ingredient.test(p_44171_.m_8020_(i + j * p_44171_.m_39347_()))) {
               return false;
            }
         }
      }

      return true;
   }

   public ItemStack m_5874_(CraftingContainer p_44169_) {
      return this.m_8043_().m_41777_();
   }

   public int m_44220_() {
      return this.f_44146_;
   }

   @Override
   public int getRecipeWidth() {
      return m_44220_();
   }

   public int m_44221_() {
      return this.f_44147_;
   }

   @Override
   public int getRecipeHeight() {
      return m_44221_();
   }

   static NonNullList<Ingredient> m_44202_(String[] p_44203_, Map<String, Ingredient> p_44204_, int p_44205_, int p_44206_) {
      NonNullList<Ingredient> nonnulllist = NonNullList.m_122780_(p_44205_ * p_44206_, Ingredient.f_43901_);
      Set<String> set = Sets.newHashSet(p_44204_.keySet());
      set.remove(" ");

      for(int i = 0; i < p_44203_.length; ++i) {
         for(int j = 0; j < p_44203_[i].length(); ++j) {
            String s = p_44203_[i].substring(j, j + 1);
            Ingredient ingredient = p_44204_.get(s);
            if (ingredient == null) {
               throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
            }

            set.remove(s);
            nonnulllist.set(j + p_44205_ * i, ingredient);
         }
      }

      if (!set.isEmpty()) {
         throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
      } else {
         return nonnulllist;
      }
   }

   @VisibleForTesting
   static String[] m_44186_(String... p_44187_) {
      int i = Integer.MAX_VALUE;
      int j = 0;
      int k = 0;
      int l = 0;

      for(int i1 = 0; i1 < p_44187_.length; ++i1) {
         String s = p_44187_[i1];
         i = Math.min(i, m_44184_(s));
         int j1 = m_44200_(s);
         j = Math.max(j, j1);
         if (j1 < 0) {
            if (k == i1) {
               ++k;
            }

            ++l;
         } else {
            l = 0;
         }
      }

      if (p_44187_.length == l) {
         return new String[0];
      } else {
         String[] astring = new String[p_44187_.length - l - k];

         for(int k1 = 0; k1 < astring.length; ++k1) {
            astring[k1] = p_44187_[k1 + k].substring(i, j + 1);
         }

         return astring;
      }
   }

   public boolean m_142505_() {
      NonNullList<Ingredient> nonnulllist = this.m_7527_();
      return nonnulllist.isEmpty() || nonnulllist.stream().filter((p_151277_) -> {
         return !p_151277_.m_43947_();
      }).anyMatch((p_151273_) -> {
         return p_151273_.m_43908_().length == 0;
      });
   }

   private static int m_44184_(String p_44185_) {
      int i;
      for(i = 0; i < p_44185_.length() && p_44185_.charAt(i) == ' '; ++i) {
      }

      return i;
   }

   private static int m_44200_(String p_44201_) {
      int i;
      for(i = p_44201_.length() - 1; i >= 0 && p_44201_.charAt(i) == ' '; --i) {
      }

      return i;
   }

   static String[] m_44196_(JsonArray p_44197_) {
      String[] astring = new String[p_44197_.size()];
      if (astring.length > MAX_HEIGHT) {
         throw new JsonSyntaxException("Invalid pattern: too many rows, " + MAX_HEIGHT + " is maximum");
      } else if (astring.length == 0) {
         throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
      } else {
         for(int i = 0; i < astring.length; ++i) {
            String s = GsonHelper.m_13805_(p_44197_.get(i), "pattern[" + i + "]");
            if (s.length() > MAX_WIDTH) {
               throw new JsonSyntaxException("Invalid pattern: too many columns, " + MAX_WIDTH + " is maximum");
            }

            if (i > 0 && astring[0].length() != s.length()) {
               throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }

            astring[i] = s;
         }

         return astring;
      }
   }

   static Map<String, Ingredient> m_44210_(JsonObject p_44211_) {
      Map<String, Ingredient> map = Maps.newHashMap();

      for(Entry<String, JsonElement> entry : p_44211_.entrySet()) {
         if (entry.getKey().length() != 1) {
            throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
         }

         if (" ".equals(entry.getKey())) {
            throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
         }

         map.put(entry.getKey(), Ingredient.m_43917_(entry.getValue()));
      }

      map.put(" ", Ingredient.f_43901_);
      return map;
   }

   public static ItemStack m_151274_(JsonObject p_151275_) {
      return net.minecraftforge.common.crafting.CraftingHelper.getItemStack(p_151275_, true, false); // TODO: Swap false to true in 1.18 to mimic vanilla behavior
   }

   public static Item m_151278_(JsonObject p_151279_) {
      String s = GsonHelper.m_13906_(p_151279_, "item");
      Item item = Registry.f_122827_.m_6612_(new ResourceLocation(s)).orElseThrow(() -> {
         return new JsonSyntaxException("Unknown item '" + s + "'");
      });
      if (item == Items.f_41852_) {
         throw new JsonSyntaxException("Invalid item: " + s);
      } else {
         return item;
      }
   }

   public static class Serializer extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>>  implements RecipeSerializer<ShapedRecipe> {
      private static final ResourceLocation NAME = new ResourceLocation("minecraft", "crafting_shaped");
      public ShapedRecipe m_6729_(ResourceLocation p_44236_, JsonObject p_44237_) {
         String s = GsonHelper.m_13851_(p_44237_, "group", "");
         Map<String, Ingredient> map = ShapedRecipe.m_44210_(GsonHelper.m_13930_(p_44237_, "key"));
         String[] astring = ShapedRecipe.m_44186_(ShapedRecipe.m_44196_(GsonHelper.m_13933_(p_44237_, "pattern")));
         int i = astring[0].length();
         int j = astring.length;
         NonNullList<Ingredient> nonnulllist = ShapedRecipe.m_44202_(astring, map, i, j);
         ItemStack itemstack = ShapedRecipe.m_151274_(GsonHelper.m_13930_(p_44237_, "result"));
         return new ShapedRecipe(p_44236_, s, i, j, nonnulllist, itemstack);
      }

      public ShapedRecipe m_8005_(ResourceLocation p_44239_, FriendlyByteBuf p_44240_) {
         int i = p_44240_.m_130242_();
         int j = p_44240_.m_130242_();
         String s = p_44240_.m_130277_();
         NonNullList<Ingredient> nonnulllist = NonNullList.m_122780_(i * j, Ingredient.f_43901_);

         for(int k = 0; k < nonnulllist.size(); ++k) {
            nonnulllist.set(k, Ingredient.m_43940_(p_44240_));
         }

         ItemStack itemstack = p_44240_.m_130267_();
         return new ShapedRecipe(p_44239_, s, i, j, nonnulllist, itemstack);
      }

      public void m_6178_(FriendlyByteBuf p_44227_, ShapedRecipe p_44228_) {
         p_44227_.m_130130_(p_44228_.f_44146_);
         p_44227_.m_130130_(p_44228_.f_44147_);
         p_44227_.m_130070_(p_44228_.f_44151_);

         for(Ingredient ingredient : p_44228_.f_44148_) {
            ingredient.m_43923_(p_44227_);
         }

         p_44227_.m_130055_(p_44228_.f_44149_);
      }
   }
}
