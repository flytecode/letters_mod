package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;

public class LootItemRandomChanceCondition implements LootItemCondition {
   final float f_81921_;

   LootItemRandomChanceCondition(float p_81923_) {
      this.f_81921_ = p_81923_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81813_;
   }

   public boolean test(LootContext p_81930_) {
      int i = p_81930_.getLootingModifier();
      return p_81930_.m_78933_().nextFloat() < this.f_81921_ + (float) i;
   }

   public static LootItemCondition.Builder m_81927_(float p_81928_) {
      return () -> {
         return new LootItemRandomChanceCondition(p_81928_);
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootItemRandomChanceCondition> {
      public void m_6170_(JsonObject p_81943_, LootItemRandomChanceCondition p_81944_, JsonSerializationContext p_81945_) {
         p_81943_.addProperty("chance", p_81944_.f_81921_);
      }

      public LootItemRandomChanceCondition m_7561_(JsonObject p_81951_, JsonDeserializationContext p_81952_) {
         return new LootItemRandomChanceCondition(GsonHelper.m_13915_(p_81951_, "chance"));
      }
   }
}
