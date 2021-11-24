package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Random;
import java.util.Set;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class ExplosionCondition implements LootItemCondition {
   static final ExplosionCondition f_81654_ = new ExplosionCondition();

   private ExplosionCondition() {
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81821_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81464_);
   }

   public boolean test(LootContext p_81659_) {
      Float f = p_81659_.m_78953_(LootContextParams.f_81464_);
      if (f != null) {
         Random random = p_81659_.m_78933_();
         float f1 = 1.0F / f;
         return random.nextFloat() <= f1;
      } else {
         return true;
      }
   }

   public static LootItemCondition.Builder m_81661_() {
      return () -> {
         return f_81654_;
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ExplosionCondition> {
      public void m_6170_(JsonObject p_81671_, ExplosionCondition p_81672_, JsonSerializationContext p_81673_) {
      }

      public ExplosionCondition m_7561_(JsonObject p_81679_, JsonDeserializationContext p_81680_) {
         return ExplosionCondition.f_81654_;
      }
   }
}