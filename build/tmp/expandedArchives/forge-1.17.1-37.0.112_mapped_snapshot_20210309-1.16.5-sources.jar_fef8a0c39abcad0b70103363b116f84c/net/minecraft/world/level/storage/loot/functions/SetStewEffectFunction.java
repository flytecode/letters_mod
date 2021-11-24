package net.minecraft.world.level.storage.loot.functions;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class SetStewEffectFunction extends LootItemConditionalFunction {
   final Map<MobEffect, NumberProvider> f_81214_;

   SetStewEffectFunction(LootItemCondition[] p_81216_, Map<MobEffect, NumberProvider> p_81217_) {
      super(p_81216_);
      this.f_81214_ = ImmutableMap.copyOf(p_81217_);
   }

   public LootItemFunctionType m_7162_() {
      return LootItemFunctions.f_80746_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return this.f_81214_.values().stream().flatMap((p_165470_) -> {
         return p_165470_.m_6231_().stream();
      }).collect(ImmutableSet.toImmutableSet());
   }

   public ItemStack m_7372_(ItemStack p_81223_, LootContext p_81224_) {
      if (p_81223_.m_150930_(Items.f_42718_) && !this.f_81214_.isEmpty()) {
         Random random = p_81224_.m_78933_();
         int i = random.nextInt(this.f_81214_.size());
         Entry<MobEffect, NumberProvider> entry = Iterables.get(this.f_81214_.entrySet(), i);
         MobEffect mobeffect = entry.getKey();
         int j = entry.getValue().m_142683_(p_81224_);
         if (!mobeffect.m_8093_()) {
            j *= 20;
         }

         SuspiciousStewItem.m_43258_(p_81223_, mobeffect, j);
         return p_81223_;
      } else {
         return p_81223_;
      }
   }

   public static SetStewEffectFunction.Builder m_81228_() {
      return new SetStewEffectFunction.Builder();
   }

   public static class Builder extends LootItemConditionalFunction.Builder<SetStewEffectFunction.Builder> {
      private final Map<MobEffect, NumberProvider> f_81229_ = Maps.newHashMap();

      protected SetStewEffectFunction.Builder m_6477_() {
         return this;
      }

      public SetStewEffectFunction.Builder m_165472_(MobEffect p_165473_, NumberProvider p_165474_) {
         this.f_81229_.put(p_165473_, p_165474_);
         return this;
      }

      public LootItemFunction m_7453_() {
         return new SetStewEffectFunction(this.m_80699_(), this.f_81229_);
      }
   }

   public static class Serializer extends LootItemConditionalFunction.Serializer<SetStewEffectFunction> {
      public void m_6170_(JsonObject p_81247_, SetStewEffectFunction p_81248_, JsonSerializationContext p_81249_) {
         super.m_6170_(p_81247_, p_81248_, p_81249_);
         if (!p_81248_.f_81214_.isEmpty()) {
            JsonArray jsonarray = new JsonArray();

            for(MobEffect mobeffect : p_81248_.f_81214_.keySet()) {
               JsonObject jsonobject = new JsonObject();
               ResourceLocation resourcelocation = Registry.f_122823_.m_7981_(mobeffect);
               if (resourcelocation == null) {
                  throw new IllegalArgumentException("Don't know how to serialize mob effect " + mobeffect);
               }

               jsonobject.add("type", new JsonPrimitive(resourcelocation.toString()));
               jsonobject.add("duration", p_81249_.serialize(p_81248_.f_81214_.get(mobeffect)));
               jsonarray.add(jsonobject);
            }

            p_81247_.add("effects", jsonarray);
         }

      }

      public SetStewEffectFunction m_6821_(JsonObject p_81239_, JsonDeserializationContext p_81240_, LootItemCondition[] p_81241_) {
         Map<MobEffect, NumberProvider> map = Maps.newHashMap();
         if (p_81239_.has("effects")) {
            for(JsonElement jsonelement : GsonHelper.m_13933_(p_81239_, "effects")) {
               String s = GsonHelper.m_13906_(jsonelement.getAsJsonObject(), "type");
               MobEffect mobeffect = Registry.f_122823_.m_6612_(new ResourceLocation(s)).orElseThrow(() -> {
                  return new JsonSyntaxException("Unknown mob effect '" + s + "'");
               });
               NumberProvider numberprovider = GsonHelper.m_13836_(jsonelement.getAsJsonObject(), "duration", p_81240_, NumberProvider.class);
               map.put(mobeffect, numberprovider);
            }
         }

         return new SetStewEffectFunction(p_81241_, map);
      }
   }
}