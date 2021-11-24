package net.minecraft.world.item.crafting;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class Ingredient implements Predicate<ItemStack> {
   //Because Mojang caches things... we need to invalidate them.. so... here we go..
   private static final java.util.Set<Ingredient> INSTANCES = java.util.Collections.newSetFromMap(new java.util.WeakHashMap<Ingredient, Boolean>());
   public static void invalidateAll() {
      INSTANCES.stream().filter(e -> e != null).forEach(i -> i.invalidate());
   }

   public static final Ingredient f_43901_ = new Ingredient(Stream.empty());
   private final Ingredient.Value[] f_43902_;
   private ItemStack[] f_43903_;
   private IntList f_43904_;
   private final boolean isSimple;

   protected Ingredient(Stream<? extends Ingredient.Value> p_43907_) {
      this.f_43902_ = p_43907_.toArray((p_43933_) -> {
         return new Ingredient.Value[p_43933_];
      });
      this.isSimple = !net.minecraftforge.fmllegacy.DatagenModLoader.isRunningDataGen() && !Arrays.stream(f_43902_).anyMatch(list -> list.m_6223_().stream().anyMatch(stack -> stack.m_41720_().isDamageable(stack)));
      Ingredient.INSTANCES.add(this);
   }

   public ItemStack[] m_43908_() {
      this.m_43948_();
      return this.f_43903_;
   }

   private void m_43948_() {
      if (this.f_43903_ == null) {
         this.f_43903_ = Arrays.stream(this.f_43902_).flatMap((p_43916_) -> {
            return p_43916_.m_6223_().stream();
         }).distinct().toArray((p_43910_) -> {
            return new ItemStack[p_43910_];
         });
      }

   }

   public boolean test(@Nullable ItemStack p_43914_) {
      if (p_43914_ == null) {
         return false;
      } else {
         this.m_43948_();
         if (this.f_43903_.length == 0) {
            return p_43914_.m_41619_();
         } else {
            for(ItemStack itemstack : this.f_43903_) {
               if (itemstack.m_150930_(p_43914_.m_41720_())) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public IntList m_43931_() {
      if (this.f_43904_ == null) {
         this.m_43948_();
         this.f_43904_ = new IntArrayList(this.f_43903_.length);

         for(ItemStack itemstack : this.f_43903_) {
            this.f_43904_.add(StackedContents.m_36496_(itemstack));
         }

         this.f_43904_.sort(IntComparators.NATURAL_COMPARATOR);
      }

      return this.f_43904_;
   }

   public final void m_43923_(FriendlyByteBuf p_43924_) {
      this.m_43948_();
      if (!this.isVanilla()) {
         net.minecraftforge.common.crafting.CraftingHelper.write(p_43924_, this);
         return;
      }
      p_43924_.m_178352_(Arrays.asList(this.f_43903_), FriendlyByteBuf::m_130055_);
   }

   public JsonElement m_43942_() {
      if (this.f_43902_.length == 1) {
         return this.f_43902_[0].m_6544_();
      } else {
         JsonArray jsonarray = new JsonArray();

         for(Ingredient.Value ingredient$value : this.f_43902_) {
            jsonarray.add(ingredient$value.m_6544_());
         }

         return jsonarray;
      }
   }

   public boolean m_43947_() {
      return this.f_43902_.length == 0 && (this.f_43903_ == null || this.f_43903_.length == 0) && (this.f_43904_ == null || this.f_43904_.isEmpty());
   }

   protected void invalidate() {
      this.f_43903_ = null;
      this.f_43904_ = null;
   }

   public boolean isSimple() {
      return isSimple || this == f_43901_;
   }

   private final boolean isVanilla = this.getClass() == Ingredient.class;
   public final boolean isVanilla() {
       return isVanilla;
   }

   public net.minecraftforge.common.crafting.IIngredientSerializer<? extends Ingredient> getSerializer() {
      if (!isVanilla()) throw new IllegalStateException("Modders must implement Ingredient.getSerializer in their custom Ingredients: " + this);
      return net.minecraftforge.common.crafting.VanillaIngredientSerializer.INSTANCE;
   }

   public static Ingredient m_43938_(Stream<? extends Ingredient.Value> p_43939_) {
      Ingredient ingredient = new Ingredient(p_43939_);
      return ingredient.f_43902_.length == 0 ? f_43901_ : ingredient;
   }

   public static Ingredient m_151265_() {
      return f_43901_;
   }

   public static Ingredient m_43929_(ItemLike... p_43930_) {
      return m_43921_(Arrays.stream(p_43930_).map(ItemStack::new));
   }

   public static Ingredient m_43927_(ItemStack... p_43928_) {
      return m_43921_(Arrays.stream(p_43928_));
   }

   public static Ingredient m_43921_(Stream<ItemStack> p_43922_) {
      return m_43938_(p_43922_.filter((p_43944_) -> {
         return !p_43944_.m_41619_();
      }).map(Ingredient.ItemValue::new));
   }

   public static Ingredient m_43911_(Tag<Item> p_43912_) {
      return m_43938_(Stream.of(new Ingredient.TagValue(p_43912_)));
   }

   public static Ingredient m_43940_(FriendlyByteBuf p_43941_) {
      var size = p_43941_.m_130242_();
      if (size == -1) return net.minecraftforge.common.crafting.CraftingHelper.getIngredient(p_43941_.m_130281_(), p_43941_);
      return m_43938_(Stream.generate(() -> new Ingredient.ItemValue(p_43941_.m_130267_())).limit(size));
   }

   public static Ingredient m_43917_(@Nullable JsonElement p_43918_) {
      if (p_43918_ != null && !p_43918_.isJsonNull()) {
         Ingredient ret = net.minecraftforge.common.crafting.CraftingHelper.getIngredient(p_43918_);
         if (ret != null) return ret;
         if (p_43918_.isJsonObject()) {
            return m_43938_(Stream.of(m_43919_(p_43918_.getAsJsonObject())));
         } else if (p_43918_.isJsonArray()) {
            JsonArray jsonarray = p_43918_.getAsJsonArray();
            if (jsonarray.size() == 0) {
               throw new JsonSyntaxException("Item array cannot be empty, at least one item must be defined");
            } else {
               return m_43938_(StreamSupport.stream(jsonarray.spliterator(), false).map((p_151264_) -> {
                  return m_43919_(GsonHelper.m_13918_(p_151264_, "item"));
               }));
            }
         } else {
            throw new JsonSyntaxException("Expected item to be object or array of objects");
         }
      } else {
         throw new JsonSyntaxException("Item cannot be null");
      }
   }

   public static Ingredient.Value m_43919_(JsonObject p_43920_) {
      if (p_43920_.has("item") && p_43920_.has("tag")) {
         throw new JsonParseException("An ingredient entry is either a tag or an item, not both");
      } else if (p_43920_.has("item")) {
         Item item = ShapedRecipe.m_151278_(p_43920_);
         return new Ingredient.ItemValue(new ItemStack(item));
      } else if (p_43920_.has("tag")) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_43920_, "tag"));
         Tag<Item> tag = SerializationTags.m_13199_().m_144458_(Registry.f_122904_, resourcelocation, (p_151262_) -> {
            return new JsonSyntaxException("Unknown item tag '" + p_151262_ + "'");
         });
         return new Ingredient.TagValue(tag);
      } else {
         throw new JsonParseException("An ingredient entry needs either a tag or an item");
      }
   }

   //Merges several vanilla Ingredients together. As a quirk of how the json is structured, we can't tell if its a single Ingredient type or multiple so we split per item and re-merge here.
   //Only public for internal use, so we can access a private field in here.
   public static Ingredient merge(Collection<Ingredient> parts) {
      return m_43938_(parts.stream().flatMap(i -> Arrays.stream(i.f_43902_)));
   }

   public static class ItemValue implements Ingredient.Value {
      private final ItemStack f_43951_;

      public ItemValue(ItemStack p_43953_) {
         this.f_43951_ = p_43953_;
      }

      public Collection<ItemStack> m_6223_() {
         return Collections.singleton(this.f_43951_);
      }

      public JsonObject m_6544_() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("item", Registry.f_122827_.m_7981_(this.f_43951_.m_41720_()).toString());
         return jsonobject;
      }
   }

   public static class TagValue implements Ingredient.Value {
      private final Tag<Item> f_43959_;

      public TagValue(Tag<Item> p_43961_) {
         this.f_43959_ = p_43961_;
      }

      public Collection<ItemStack> m_6223_() {
         List<ItemStack> list = Lists.newArrayList();

         for(Item item : this.f_43959_.m_6497_()) {
            list.add(new ItemStack(item));
         }

         if (list.size() == 0 && !net.minecraftforge.common.ForgeConfig.SERVER.treatEmptyTagsAsAir.get()) {
            list.add(new ItemStack(net.minecraft.world.level.block.Blocks.f_50375_).m_41714_(new net.minecraft.network.chat.TextComponent("Empty Tag: " + SerializationTags.m_13199_().m_144454_(Registry.f_122904_, this.f_43959_, () -> new IllegalStateException("Unrecognized tag")))));
         }
         return list;
      }

      public JsonObject m_6544_() {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("tag", SerializationTags.m_13199_().m_144454_(Registry.f_122904_, this.f_43959_, () -> {
            return new IllegalStateException("Unknown item tag");
         }).toString());
         return jsonobject;
      }
   }

   public interface Value {
      Collection<ItemStack> m_6223_();

      JsonObject m_6544_();
   }
}
