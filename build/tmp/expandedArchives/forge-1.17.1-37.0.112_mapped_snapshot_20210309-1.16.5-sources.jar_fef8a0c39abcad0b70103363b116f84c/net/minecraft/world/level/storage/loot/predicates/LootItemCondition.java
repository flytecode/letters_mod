package net.minecraft.world.level.storage.loot.predicates;

import java.util.function.Predicate;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContextUser;

public interface LootItemCondition extends LootContextUser, Predicate<LootContext> {
   LootItemConditionType m_7940_();

   @FunctionalInterface
   public interface Builder {
      LootItemCondition m_6409_();

      default LootItemCondition.Builder m_81807_() {
         return InvertedLootItemCondition.m_81694_(this);
      }

      default AlternativeLootItemCondition.Builder m_7818_(LootItemCondition.Builder p_81808_) {
         return AlternativeLootItemCondition.m_81481_(this, p_81808_);
      }
   }
}