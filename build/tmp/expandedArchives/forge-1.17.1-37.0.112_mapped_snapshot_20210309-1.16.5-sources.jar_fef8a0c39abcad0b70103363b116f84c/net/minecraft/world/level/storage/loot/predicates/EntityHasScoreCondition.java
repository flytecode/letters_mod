package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Stream;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.IntRange;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.Scoreboard;

public class EntityHasScoreCondition implements LootItemCondition {
   final Map<String, IntRange> f_81615_;
   final LootContext.EntityTarget f_81616_;

   EntityHasScoreCondition(Map<String, IntRange> p_81618_, LootContext.EntityTarget p_81619_) {
      this.f_81615_ = ImmutableMap.copyOf(p_81618_);
      this.f_81616_ = p_81619_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81817_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return Stream.concat(Stream.of(this.f_81616_.m_79003_()), this.f_81615_.values().stream().flatMap((p_165487_) -> {
         return p_165487_.m_165008_().stream();
      })).collect(ImmutableSet.toImmutableSet());
   }

   public boolean test(LootContext p_81631_) {
      Entity entity = p_81631_.m_78953_(this.f_81616_.m_79003_());
      if (entity == null) {
         return false;
      } else {
         Scoreboard scoreboard = entity.f_19853_.m_6188_();

         for(Entry<String, IntRange> entry : this.f_81615_.entrySet()) {
            if (!this.m_165490_(p_81631_, entity, scoreboard, entry.getKey(), entry.getValue())) {
               return false;
            }
         }

         return true;
      }
   }

   protected boolean m_165490_(LootContext p_165491_, Entity p_165492_, Scoreboard p_165493_, String p_165494_, IntRange p_165495_) {
      Objective objective = p_165493_.m_83477_(p_165494_);
      if (objective == null) {
         return false;
      } else {
         String s = p_165492_.m_6302_();
         return !p_165493_.m_83461_(s, objective) ? false : p_165495_.m_165028_(p_165491_, p_165493_.m_83471_(s, objective).m_83400_());
      }
   }

   public static EntityHasScoreCondition.Builder m_165488_(LootContext.EntityTarget p_165489_) {
      return new EntityHasScoreCondition.Builder(p_165489_);
   }

   public static class Builder implements LootItemCondition.Builder {
      private final Map<String, IntRange> f_165496_ = Maps.newHashMap();
      private final LootContext.EntityTarget f_165497_;

      public Builder(LootContext.EntityTarget p_165499_) {
         this.f_165497_ = p_165499_;
      }

      public EntityHasScoreCondition.Builder m_165500_(String p_165501_, IntRange p_165502_) {
         this.f_165496_.put(p_165501_, p_165502_);
         return this;
      }

      public LootItemCondition m_6409_() {
         return new EntityHasScoreCondition(this.f_165496_, this.f_165497_);
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<EntityHasScoreCondition> {
      public void m_6170_(JsonObject p_81644_, EntityHasScoreCondition p_81645_, JsonSerializationContext p_81646_) {
         JsonObject jsonobject = new JsonObject();

         for(Entry<String, IntRange> entry : p_81645_.f_81615_.entrySet()) {
            jsonobject.add(entry.getKey(), p_81646_.serialize(entry.getValue()));
         }

         p_81644_.add("scores", jsonobject);
         p_81644_.add("entity", p_81646_.serialize(p_81645_.f_81616_));
      }

      public EntityHasScoreCondition m_7561_(JsonObject p_81652_, JsonDeserializationContext p_81653_) {
         Set<Entry<String, JsonElement>> set = GsonHelper.m_13930_(p_81652_, "scores").entrySet();
         Map<String, IntRange> map = Maps.newLinkedHashMap();

         for(Entry<String, JsonElement> entry : set) {
            map.put(entry.getKey(), GsonHelper.m_13808_(entry.getValue(), "score", p_81653_, IntRange.class));
         }

         return new EntityHasScoreCondition(map, GsonHelper.m_13836_(p_81652_, "entity", p_81653_, LootContext.EntityTarget.class));
      }
   }
}