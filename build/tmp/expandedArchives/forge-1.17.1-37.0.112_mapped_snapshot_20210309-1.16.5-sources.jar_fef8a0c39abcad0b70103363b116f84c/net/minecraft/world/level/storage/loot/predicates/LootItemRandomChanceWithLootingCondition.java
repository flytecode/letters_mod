package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class LootItemRandomChanceWithLootingCondition implements LootItemCondition {
   final float f_81953_;
   final float f_81954_;

   LootItemRandomChanceWithLootingCondition(float p_81956_, float p_81957_) {
      this.f_81953_ = p_81956_;
      this.f_81954_ = p_81957_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81814_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81458_);
   }

   public boolean test(LootContext p_81967_) {
      int i = p_81967_.getLootingModifier();
      return p_81967_.m_78933_().nextFloat() < this.f_81953_ + (float)i * this.f_81954_;
   }

   public static LootItemCondition.Builder m_81963_(float p_81964_, float p_81965_) {
      return () -> {
         return new LootItemRandomChanceWithLootingCondition(p_81964_, p_81965_);
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootItemRandomChanceWithLootingCondition> {
      public void m_6170_(JsonObject p_81983_, LootItemRandomChanceWithLootingCondition p_81984_, JsonSerializationContext p_81985_) {
         p_81983_.addProperty("chance", p_81984_.f_81953_);
         p_81983_.addProperty("looting_multiplier", p_81984_.f_81954_);
      }

      public LootItemRandomChanceWithLootingCondition m_7561_(JsonObject p_81991_, JsonDeserializationContext p_81992_) {
         return new LootItemRandomChanceWithLootingCondition(GsonHelper.m_13915_(p_81991_, "chance"), GsonHelper.m_13915_(p_81991_, "looting_multiplier"));
      }
   }
}
