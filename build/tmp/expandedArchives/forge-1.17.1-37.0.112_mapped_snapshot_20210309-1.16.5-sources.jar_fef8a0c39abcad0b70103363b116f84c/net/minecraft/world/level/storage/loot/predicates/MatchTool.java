package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class MatchTool implements LootItemCondition {
   final ItemPredicate f_81993_;

   public MatchTool(ItemPredicate p_81995_) {
      this.f_81993_ = p_81995_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81819_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81463_);
   }

   public boolean test(LootContext p_82000_) {
      ItemStack itemstack = p_82000_.m_78953_(LootContextParams.f_81463_);
      return itemstack != null && this.f_81993_.m_45049_(itemstack);
   }

   public static LootItemCondition.Builder m_81997_(ItemPredicate.Builder p_81998_) {
      return () -> {
         return new MatchTool(p_81998_.m_45077_());
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<MatchTool> {
      public void m_6170_(JsonObject p_82013_, MatchTool p_82014_, JsonSerializationContext p_82015_) {
         p_82013_.add("predicate", p_82014_.f_81993_.m_45048_());
      }

      public MatchTool m_7561_(JsonObject p_82021_, JsonDeserializationContext p_82022_) {
         ItemPredicate itempredicate = ItemPredicate.m_45051_(p_82021_.get("predicate"));
         return new MatchTool(itempredicate);
      }
   }
}