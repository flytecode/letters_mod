package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class LootItemKilledByPlayerCondition implements LootItemCondition {
   static final LootItemKilledByPlayerCondition f_81894_ = new LootItemKilledByPlayerCondition();

   private LootItemKilledByPlayerCondition() {
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81816_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81456_);
   }

   public boolean test(LootContext p_81899_) {
      return p_81899_.m_78936_(LootContextParams.f_81456_);
   }

   public static LootItemCondition.Builder m_81901_() {
      return () -> {
         return f_81894_;
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootItemKilledByPlayerCondition> {
      public void m_6170_(JsonObject p_81911_, LootItemKilledByPlayerCondition p_81912_, JsonSerializationContext p_81913_) {
      }

      public LootItemKilledByPlayerCondition m_7561_(JsonObject p_81919_, JsonDeserializationContext p_81920_) {
         return LootItemKilledByPlayerCondition.f_81894_;
      }
   }
}