package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

public class ValueCheckCondition implements LootItemCondition {
   final NumberProvider f_165520_;
   final IntRange f_165521_;

   ValueCheckCondition(NumberProvider p_165523_, IntRange p_165524_) {
      this.f_165520_ = p_165523_;
      this.f_165521_ = p_165524_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_165504_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return Sets.union(this.f_165520_.m_6231_(), this.f_165521_.m_165008_());
   }

   public boolean test(LootContext p_165527_) {
      return this.f_165521_.m_165028_(p_165527_, this.f_165520_.m_142683_(p_165527_));
   }

   public static LootItemCondition.Builder m_165528_(NumberProvider p_165529_, IntRange p_165530_) {
      return () -> {
         return new ValueCheckCondition(p_165529_, p_165530_);
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ValueCheckCondition> {
      public void m_6170_(JsonObject p_165542_, ValueCheckCondition p_165543_, JsonSerializationContext p_165544_) {
         p_165542_.add("value", p_165544_.serialize(p_165543_.f_165520_));
         p_165542_.add("range", p_165544_.serialize(p_165543_.f_165521_));
      }

      public ValueCheckCondition m_7561_(JsonObject p_165550_, JsonDeserializationContext p_165551_) {
         NumberProvider numberprovider = GsonHelper.m_13836_(p_165550_, "value", p_165551_, NumberProvider.class);
         IntRange intrange = GsonHelper.m_13836_(p_165550_, "range", p_165551_, IntRange.class);
         return new ValueCheckCondition(numberprovider, intrange);
      }
   }
}