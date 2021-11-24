package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;

public class InvertedLootItemCondition implements LootItemCondition {
   final LootItemCondition f_81681_;

   InvertedLootItemCondition(LootItemCondition p_81683_) {
      this.f_81681_ = p_81683_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81811_;
   }

   public final boolean test(LootContext p_81689_) {
      return !this.f_81681_.test(p_81689_);
   }

   public Set<LootContextParam<?>> m_6231_() {
      return this.f_81681_.m_6231_();
   }

   public void m_6169_(ValidationContext p_81691_) {
      LootItemCondition.super.m_6169_(p_81691_);
      this.f_81681_.m_6169_(p_81691_);
   }

   public static LootItemCondition.Builder m_81694_(LootItemCondition.Builder p_81695_) {
      InvertedLootItemCondition invertedlootitemcondition = new InvertedLootItemCondition(p_81695_.m_6409_());
      return () -> {
         return invertedlootitemcondition;
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<InvertedLootItemCondition> {
      public void m_6170_(JsonObject p_81706_, InvertedLootItemCondition p_81707_, JsonSerializationContext p_81708_) {
         p_81706_.add("term", p_81708_.serialize(p_81707_.f_81681_));
      }

      public InvertedLootItemCondition m_7561_(JsonObject p_81714_, JsonDeserializationContext p_81715_) {
         LootItemCondition lootitemcondition = GsonHelper.m_13836_(p_81714_, "term", p_81715_, LootItemCondition.class);
         return new InvertedLootItemCondition(lootitemcondition);
      }
   }
}