package net.minecraft.data.recipes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;

public class ShapedRecipeBuilder implements RecipeBuilder {
   private final Item f_126106_;
   private final int f_126107_;
   private final List<String> f_126108_ = Lists.newArrayList();
   private final Map<Character, Ingredient> f_126109_ = Maps.newLinkedHashMap();
   private final Advancement.Builder f_126110_ = Advancement.Builder.m_138353_();
   @Nullable
   private String f_126111_;

   public ShapedRecipeBuilder(ItemLike p_126114_, int p_126115_) {
      this.f_126106_ = p_126114_.m_5456_();
      this.f_126107_ = p_126115_;
   }

   public static ShapedRecipeBuilder m_126116_(ItemLike p_126117_) {
      return m_126118_(p_126117_, 1);
   }

   public static ShapedRecipeBuilder m_126118_(ItemLike p_126119_, int p_126120_) {
      return new ShapedRecipeBuilder(p_126119_, p_126120_);
   }

   public ShapedRecipeBuilder m_126121_(Character p_126122_, Tag<Item> p_126123_) {
      return this.m_126124_(p_126122_, Ingredient.m_43911_(p_126123_));
   }

   public ShapedRecipeBuilder m_126127_(Character p_126128_, ItemLike p_126129_) {
      return this.m_126124_(p_126128_, Ingredient.m_43929_(p_126129_));
   }

   public ShapedRecipeBuilder m_126124_(Character p_126125_, Ingredient p_126126_) {
      if (this.f_126109_.containsKey(p_126125_)) {
         throw new IllegalArgumentException("Symbol '" + p_126125_ + "' is already defined!");
      } else if (p_126125_ == ' ') {
         throw new IllegalArgumentException("Symbol ' ' (whitespace) is reserved and cannot be defined");
      } else {
         this.f_126109_.put(p_126125_, p_126126_);
         return this;
      }
   }

   public ShapedRecipeBuilder m_126130_(String p_126131_) {
      if (!this.f_126108_.isEmpty() && p_126131_.length() != this.f_126108_.get(0).length()) {
         throw new IllegalArgumentException("Pattern must be the same width on every line!");
      } else {
         this.f_126108_.add(p_126131_);
         return this;
      }
   }

   public ShapedRecipeBuilder m_142284_(String p_126133_, CriterionTriggerInstance p_126134_) {
      this.f_126110_.m_138386_(p_126133_, p_126134_);
      return this;
   }

   public ShapedRecipeBuilder m_142409_(@Nullable String p_126146_) {
      this.f_126111_ = p_126146_;
      return this;
   }

   public Item m_142372_() {
      return this.f_126106_;
   }

   public void m_142700_(Consumer<FinishedRecipe> p_126141_, ResourceLocation p_126142_) {
      this.m_126143_(p_126142_);
      this.f_126110_.m_138396_(new ResourceLocation("recipes/root")).m_138386_("has_the_recipe", RecipeUnlockedTrigger.m_63728_(p_126142_)).m_138354_(AdvancementRewards.Builder.m_10009_(p_126142_)).m_138360_(RequirementsStrategy.f_15979_);
      p_126141_.accept(new ShapedRecipeBuilder.Result(p_126142_, this.f_126106_, this.f_126107_, this.f_126111_ == null ? "" : this.f_126111_, this.f_126108_, this.f_126109_, this.f_126110_, new ResourceLocation(p_126142_.m_135827_(), "recipes/" + this.f_126106_.m_41471_().m_40783_() + "/" + p_126142_.m_135815_())));
   }

   private void m_126143_(ResourceLocation p_126144_) {
      if (this.f_126108_.isEmpty()) {
         throw new IllegalStateException("No pattern is defined for shaped recipe " + p_126144_ + "!");
      } else {
         Set<Character> set = Sets.newHashSet(this.f_126109_.keySet());
         set.remove(' ');

         for(String s : this.f_126108_) {
            for(int i = 0; i < s.length(); ++i) {
               char c0 = s.charAt(i);
               if (!this.f_126109_.containsKey(c0) && c0 != ' ') {
                  throw new IllegalStateException("Pattern in recipe " + p_126144_ + " uses undefined symbol '" + c0 + "'");
               }

               set.remove(c0);
            }
         }

         if (!set.isEmpty()) {
            throw new IllegalStateException("Ingredients are defined but not used in pattern for recipe " + p_126144_);
         } else if (this.f_126108_.size() == 1 && this.f_126108_.get(0).length() == 1) {
            throw new IllegalStateException("Shaped recipe " + p_126144_ + " only takes in a single item - should it be a shapeless recipe instead?");
         } else if (this.f_126110_.m_138405_().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + p_126144_);
         }
      }
   }

   public static class Result implements FinishedRecipe {
      private final ResourceLocation f_126148_;
      private final Item f_126149_;
      private final int f_126150_;
      private final String f_126151_;
      private final List<String> f_126152_;
      private final Map<Character, Ingredient> f_126153_;
      private final Advancement.Builder f_126154_;
      private final ResourceLocation f_126155_;

      public Result(ResourceLocation p_176754_, Item p_176755_, int p_176756_, String p_176757_, List<String> p_176758_, Map<Character, Ingredient> p_176759_, Advancement.Builder p_176760_, ResourceLocation p_176761_) {
         this.f_126148_ = p_176754_;
         this.f_126149_ = p_176755_;
         this.f_126150_ = p_176756_;
         this.f_126151_ = p_176757_;
         this.f_126152_ = p_176758_;
         this.f_126153_ = p_176759_;
         this.f_126154_ = p_176760_;
         this.f_126155_ = p_176761_;
      }

      public void m_7917_(JsonObject p_126167_) {
         if (!this.f_126151_.isEmpty()) {
            p_126167_.addProperty("group", this.f_126151_);
         }

         JsonArray jsonarray = new JsonArray();

         for(String s : this.f_126152_) {
            jsonarray.add(s);
         }

         p_126167_.add("pattern", jsonarray);
         JsonObject jsonobject = new JsonObject();

         for(Entry<Character, Ingredient> entry : this.f_126153_.entrySet()) {
            jsonobject.add(String.valueOf(entry.getKey()), entry.getValue().m_43942_());
         }

         p_126167_.add("key", jsonobject);
         JsonObject jsonobject1 = new JsonObject();
         jsonobject1.addProperty("item", Registry.f_122827_.m_7981_(this.f_126149_).toString());
         if (this.f_126150_ > 1) {
            jsonobject1.addProperty("count", this.f_126150_);
         }

         p_126167_.add("result", jsonobject1);
      }

      public RecipeSerializer<?> m_6637_() {
         return RecipeSerializer.f_44076_;
      }

      public ResourceLocation m_6445_() {
         return this.f_126148_;
      }

      @Nullable
      public JsonObject m_5860_() {
         return this.f_126154_.m_138400_();
      }

      @Nullable
      public ResourceLocation m_6448_() {
         return this.f_126155_;
      }
   }
}