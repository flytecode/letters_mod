package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetEnchantmentsFunction extends LootItemConditionalFunction {
   final Map<Enchantment, NumberProvider> f_165334_;
   final boolean f_165335_;

   SetEnchantmentsFunction(LootItemCondition[] p_165337_, Map<Enchantment, NumberProvider> p_165338_, boolean p_165339_) {
      super(p_165337_);
      this.f_165334_ = ImmutableMap.copyOf(p_165338_);
      this.f_165335_ = p_165339_;
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_165221_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return this.f_165334_.values().stream().flatMap((p_165349_) -> {
         return p_165349_.m_6231_().stream();
      }).collect(ImmutableSet.toImmutableSet());
   }

   public ItemStack m_7372_(ItemStack p_165346_, LootContext p_165347_) {
      Object2IntMap<Enchantment> object2intmap = new Object2IntOpenHashMap<>();
      this.f_165334_.forEach((p_165353_, p_165354_) -> {
         object2intmap.put(p_165353_, p_165354_.m_142683_(p_165347_));
      });
      if (p_165346_.m_41720_() == Items.f_42517_) {
         ItemStack itemstack = new ItemStack(Items.f_42690_);
         object2intmap.forEach((p_165343_, p_165344_) -> {
            EnchantedBookItem.m_41153_(itemstack, new EnchantmentInstance(p_165343_, p_165344_));
         });
         return itemstack;
      } else {
         Map<Enchantment, Integer> map = EnchantmentHelper.m_44831_(p_165346_);
         if (this.f_165335_) {
            object2intmap.forEach((p_165366_, p_165367_) -> {
               m_165355_(map, p_165366_, Math.max(map.getOrDefault(p_165366_, 0) + p_165367_, 0));
            });
         } else {
            object2intmap.forEach((p_165361_, p_165362_) -> {
               m_165355_(map, p_165361_, Math.max(p_165362_, 0));
            });
         }

         EnchantmentHelper.m_44865_(map, p_165346_);
         return p_165346_;
      }
   }

   private static void m_165355_(Map<Enchantment, Integer> p_165356_, Enchantment p_165357_, int p_165358_) {
      if (p_165358_ == 0) {
         p_165356_.remove(p_165357_);
      } else {
         p_165356_.put(p_165357_, p_165358_);
      }

   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetEnchantmentsFunction.Builder> {
      private final Map<Enchantment, NumberProvider> f_165368_ = Maps.newHashMap();
      private final boolean f_165369_;

      public Builder() {
         this(false);
      }

      public Builder(boolean p_165372_) {
         this.f_165369_ = p_165372_;
      }

      protected SetEnchantmentsFunction.Builder m_6477_() {
         return this;
      }

      public SetEnchantmentsFunction.Builder m_165374_(Enchantment p_165375_, NumberProvider p_165376_) {
         this.f_165368_.put(p_165375_, p_165376_);
         return this;
      }

      public LootItemFunction m_7453_() {
         return new SetEnchantmentsFunction(this.m_80699_(), this.f_165368_, this.f_165369_);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetEnchantmentsFunction> {
      public void m_6170_(JsonObject p_165394_, SetEnchantmentsFunction p_165395_, JsonSerializationContext p_165396_) {
         super.m_6170_(p_165394_, p_165395_, p_165396_);
         JsonObject jsonobject = new JsonObject();
         p_165395_.f_165334_.forEach((p_165387_, p_165388_) -> {
            ResourceLocation resourcelocation = Registry.f_122825_.m_7981_(p_165387_);
            if (resourcelocation == null) {
               throw new IllegalArgumentException("Don't know how to serialize enchantment " + p_165387_);
            } else {
               jsonobject.add(resourcelocation.toString(), p_165396_.serialize(p_165388_));
            }
         });
         p_165394_.add("enchantments", jsonobject);
         p_165394_.addProperty("add", p_165395_.f_165335_);
      }

      public SetEnchantmentsFunction m_6821_(JsonObject p_165381_, JsonDeserializationContext p_165382_, LootItemCondition[] p_165383_) {
         Map<Enchantment, NumberProvider> map = Maps.newHashMap();
         if (p_165381_.has("enchantments")) {
            JsonObject jsonobject = GsonHelper.m_13930_(p_165381_, "enchantments");

            for(Entry<String, JsonElement> entry : jsonobject.entrySet()) {
               String s = entry.getKey();
               JsonElement jsonelement = entry.getValue();
               Enchantment enchantment = Registry.f_122825_.m_6612_(new ResourceLocation(s)).orElseThrow(() -> {
                  return new JsonSyntaxException("Unknown enchantment '" + s + "'");
               });
               NumberProvider numberprovider = p_165382_.deserialize(jsonelement, NumberProvider.class);
               map.put(enchantment, numberprovider);
            }
         }

         boolean flag = GsonHelper.m_13855_(p_165381_, "add", false);
         return new SetEnchantmentsFunction(p_165383_, map, flag);
      }
   }
}