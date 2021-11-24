package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.SerializationTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.ItemLike;

public class ItemPredicate {
   private static final Map<ResourceLocation, java.util.function.Function<JsonObject, ItemPredicate>> custom_predicates = new java.util.HashMap<>();
   private static final Map<ResourceLocation, java.util.function.Function<JsonObject, ItemPredicate>> unmod_predicates = java.util.Collections.unmodifiableMap(custom_predicates);
   public static final ItemPredicate f_45028_ = new ItemPredicate();
   @Nullable
   private final Tag<Item> f_45029_;
   @Nullable
   private final Set<Item> f_151427_;
   private final MinMaxBounds.Ints f_45031_;
   private final MinMaxBounds.Ints f_45032_;
   private final EnchantmentPredicate[] f_45033_;
   private final EnchantmentPredicate[] f_45034_;
   @Nullable
   private final Potion f_45035_;
   private final NbtPredicate f_45036_;

   public ItemPredicate() {
      this.f_45029_ = null;
      this.f_151427_ = null;
      this.f_45035_ = null;
      this.f_45031_ = MinMaxBounds.Ints.f_55364_;
      this.f_45032_ = MinMaxBounds.Ints.f_55364_;
      this.f_45033_ = EnchantmentPredicate.f_30465_;
      this.f_45034_ = EnchantmentPredicate.f_30465_;
      this.f_45036_ = NbtPredicate.f_57471_;
   }

   public ItemPredicate(@Nullable Tag<Item> p_151429_, @Nullable Set<Item> p_151430_, MinMaxBounds.Ints p_151431_, MinMaxBounds.Ints p_151432_, EnchantmentPredicate[] p_151433_, EnchantmentPredicate[] p_151434_, @Nullable Potion p_151435_, NbtPredicate p_151436_) {
      this.f_45029_ = p_151429_;
      this.f_151427_ = p_151430_;
      this.f_45031_ = p_151431_;
      this.f_45032_ = p_151432_;
      this.f_45033_ = p_151433_;
      this.f_45034_ = p_151434_;
      this.f_45035_ = p_151435_;
      this.f_45036_ = p_151436_;
   }

   public boolean m_45049_(ItemStack p_45050_) {
      if (this == f_45028_) {
         return true;
      } else if (this.f_45029_ != null && !p_45050_.m_150922_(this.f_45029_)) {
         return false;
      } else if (this.f_151427_ != null && !this.f_151427_.contains(p_45050_.m_41720_())) {
         return false;
      } else if (!this.f_45031_.m_55390_(p_45050_.m_41613_())) {
         return false;
      } else if (!this.f_45032_.m_55327_() && !p_45050_.m_41763_()) {
         return false;
      } else if (!this.f_45032_.m_55390_(p_45050_.m_41776_() - p_45050_.m_41773_())) {
         return false;
      } else if (!this.f_45036_.m_57479_(p_45050_)) {
         return false;
      } else {
         if (this.f_45033_.length > 0) {
            Map<Enchantment, Integer> map = EnchantmentHelper.m_44882_(p_45050_.m_41785_());

            for(EnchantmentPredicate enchantmentpredicate : this.f_45033_) {
               if (!enchantmentpredicate.m_30476_(map)) {
                  return false;
               }
            }
         }

         if (this.f_45034_.length > 0) {
            Map<Enchantment, Integer> map1 = EnchantmentHelper.m_44882_(EnchantedBookItem.m_41163_(p_45050_));

            for(EnchantmentPredicate enchantmentpredicate1 : this.f_45034_) {
               if (!enchantmentpredicate1.m_30476_(map1)) {
                  return false;
               }
            }
         }

         Potion potion = PotionUtils.m_43579_(p_45050_);
         return this.f_45035_ == null || this.f_45035_ == potion;
      }
   }

   public static ItemPredicate m_45051_(@Nullable JsonElement p_45052_) {
      if (p_45052_ != null && !p_45052_.isJsonNull()) {
         JsonObject jsonobject = GsonHelper.m_13918_(p_45052_, "item");
         if (jsonobject.has("type")) {
            final ResourceLocation rl = new ResourceLocation(GsonHelper.m_13906_(jsonobject, "type"));
            if (custom_predicates.containsKey(rl)) return custom_predicates.get(rl).apply(jsonobject);
            else throw new JsonSyntaxException("There is no ItemPredicate of type "+rl);
         }
         MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(jsonobject.get("count"));
         MinMaxBounds.Ints minmaxbounds$ints1 = MinMaxBounds.Ints.m_55373_(jsonobject.get("durability"));
         if (jsonobject.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            NbtPredicate nbtpredicate = NbtPredicate.m_57481_(jsonobject.get("nbt"));
            Set<Item> set = null;
            JsonArray jsonarray = GsonHelper.m_13832_(jsonobject, "items", (JsonArray)null);
            if (jsonarray != null) {
               ImmutableSet.Builder<Item> builder = ImmutableSet.builder();

               for(JsonElement jsonelement : jsonarray) {
                  ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13805_(jsonelement, "item"));
                  builder.add(Registry.f_122827_.m_6612_(resourcelocation).orElseThrow(() -> {
                     return new JsonSyntaxException("Unknown item id '" + resourcelocation + "'");
                  }));
               }

               set = builder.build();
            }

            Tag<Item> tag = null;
            if (jsonobject.has("tag")) {
               ResourceLocation resourcelocation1 = new ResourceLocation(GsonHelper.m_13906_(jsonobject, "tag"));
               tag = SerializationTags.m_13199_().m_144458_(Registry.f_122904_, resourcelocation1, (p_45054_) -> {
                  return new JsonSyntaxException("Unknown item tag '" + p_45054_ + "'");
               });
            }

            Potion potion = null;
            if (jsonobject.has("potion")) {
               ResourceLocation resourcelocation2 = new ResourceLocation(GsonHelper.m_13906_(jsonobject, "potion"));
               potion = Registry.f_122828_.m_6612_(resourcelocation2).orElseThrow(() -> {
                  return new JsonSyntaxException("Unknown potion '" + resourcelocation2 + "'");
               });
            }

            EnchantmentPredicate[] aenchantmentpredicate = EnchantmentPredicate.m_30480_(jsonobject.get("enchantments"));
            EnchantmentPredicate[] aenchantmentpredicate1 = EnchantmentPredicate.m_30480_(jsonobject.get("stored_enchantments"));
            return new ItemPredicate(tag, set, minmaxbounds$ints, minmaxbounds$ints1, aenchantmentpredicate, aenchantmentpredicate1, potion, nbtpredicate);
         }
      } else {
         return f_45028_;
      }
   }

   public JsonElement m_45048_() {
      if (this == f_45028_) {
         return JsonNull.INSTANCE;
      } else {
         JsonObject jsonobject = new JsonObject();
         if (this.f_151427_ != null) {
            JsonArray jsonarray = new JsonArray();

            for(Item item : this.f_151427_) {
               jsonarray.add(Registry.f_122827_.m_7981_(item).toString());
            }

            jsonobject.add("items", jsonarray);
         }

         if (this.f_45029_ != null) {
            jsonobject.addProperty("tag", SerializationTags.m_13199_().m_144454_(Registry.f_122904_, this.f_45029_, () -> {
               return new IllegalStateException("Unknown item tag");
            }).toString());
         }

         jsonobject.add("count", this.f_45031_.m_55328_());
         jsonobject.add("durability", this.f_45032_.m_55328_());
         jsonobject.add("nbt", this.f_45036_.m_57476_());
         if (this.f_45033_.length > 0) {
            JsonArray jsonarray1 = new JsonArray();

            for(EnchantmentPredicate enchantmentpredicate : this.f_45033_) {
               jsonarray1.add(enchantmentpredicate.m_30473_());
            }

            jsonobject.add("enchantments", jsonarray1);
         }

         if (this.f_45034_.length > 0) {
            JsonArray jsonarray2 = new JsonArray();

            for(EnchantmentPredicate enchantmentpredicate1 : this.f_45034_) {
               jsonarray2.add(enchantmentpredicate1.m_30473_());
            }

            jsonobject.add("stored_enchantments", jsonarray2);
         }

         if (this.f_45035_ != null) {
            jsonobject.addProperty("potion", Registry.f_122828_.m_7981_(this.f_45035_).toString());
         }

         return jsonobject;
      }
   }

   public static ItemPredicate[] m_45055_(@Nullable JsonElement p_45056_) {
      if (p_45056_ != null && !p_45056_.isJsonNull()) {
         JsonArray jsonarray = GsonHelper.m_13924_(p_45056_, "items");
         ItemPredicate[] aitempredicate = new ItemPredicate[jsonarray.size()];

         for(int i = 0; i < aitempredicate.length; ++i) {
            aitempredicate[i] = m_45051_(jsonarray.get(i));
         }

         return aitempredicate;
      } else {
         return new ItemPredicate[0];
      }
   }

   public static void register(ResourceLocation name, java.util.function.Function<JsonObject, ItemPredicate> deserializer) {
      custom_predicates.put(name, deserializer);
   }

   public static Map<ResourceLocation, java.util.function.Function<JsonObject, ItemPredicate>> getPredicates() {
      return unmod_predicates;
   }

   public static class Builder {
      private final List<EnchantmentPredicate> f_45059_ = Lists.newArrayList();
      private final List<EnchantmentPredicate> f_45060_ = Lists.newArrayList();
      @Nullable
      private Set<Item> f_151440_;
      @Nullable
      private Tag<Item> f_45062_;
      private MinMaxBounds.Ints f_45063_ = MinMaxBounds.Ints.f_55364_;
      private MinMaxBounds.Ints f_45064_ = MinMaxBounds.Ints.f_55364_;
      @Nullable
      private Potion f_45065_;
      private NbtPredicate f_45066_ = NbtPredicate.f_57471_;

      private Builder() {
      }

      public static ItemPredicate.Builder m_45068_() {
         return new ItemPredicate.Builder();
      }

      public ItemPredicate.Builder m_151445_(ItemLike... p_151446_) {
         this.f_151440_ = Stream.of(p_151446_).map(ItemLike::m_5456_).collect(ImmutableSet.toImmutableSet());
         return this;
      }

      public ItemPredicate.Builder m_45069_(Tag<Item> p_45070_) {
         this.f_45062_ = p_45070_;
         return this;
      }

      public ItemPredicate.Builder m_151443_(MinMaxBounds.Ints p_151444_) {
         this.f_45063_ = p_151444_;
         return this;
      }

      public ItemPredicate.Builder m_151449_(MinMaxBounds.Ints p_151450_) {
         this.f_45064_ = p_151450_;
         return this;
      }

      public ItemPredicate.Builder m_151441_(Potion p_151442_) {
         this.f_45065_ = p_151442_;
         return this;
      }

      public ItemPredicate.Builder m_45075_(CompoundTag p_45076_) {
         this.f_45066_ = new NbtPredicate(p_45076_);
         return this;
      }

      public ItemPredicate.Builder m_45071_(EnchantmentPredicate p_45072_) {
         this.f_45059_.add(p_45072_);
         return this;
      }

      public ItemPredicate.Builder m_151447_(EnchantmentPredicate p_151448_) {
         this.f_45060_.add(p_151448_);
         return this;
      }

      public ItemPredicate m_45077_() {
         return new ItemPredicate(this.f_45062_, this.f_151440_, this.f_45063_, this.f_45064_, this.f_45059_.toArray(EnchantmentPredicate.f_30465_), this.f_45060_.toArray(EnchantmentPredicate.f_30465_), this.f_45065_, this.f_45066_);
      }
   }
}
