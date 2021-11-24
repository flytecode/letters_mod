package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class DamageSourceCondition implements LootItemCondition {
   final DamageSourcePredicate f_81582_;

   DamageSourceCondition(DamageSourcePredicate p_81584_) {
      this.f_81582_ = p_81584_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81822_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81460_, LootContextParams.f_81457_);
   }

   public boolean test(LootContext p_81592_) {
      DamageSource damagesource = p_81592_.m_78953_(LootContextParams.f_81457_);
      Vec3 vec3 = p_81592_.m_78953_(LootContextParams.f_81460_);
      return vec3 != null && damagesource != null && this.f_81582_.m_25444_(p_81592_.m_78952_(), vec3, damagesource);
   }

   public static LootItemCondition.Builder m_81589_(DamageSourcePredicate.Builder p_81590_) {
      return () -> {
         return new DamageSourceCondition(p_81590_.m_25476_());
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<DamageSourceCondition> {
      public void m_6170_(JsonObject p_81605_, DamageSourceCondition p_81606_, JsonSerializationContext p_81607_) {
         p_81605_.add("predicate", p_81606_.f_81582_.m_25443_());
      }

      public DamageSourceCondition m_7561_(JsonObject p_81613_, JsonDeserializationContext p_81614_) {
         DamageSourcePredicate damagesourcepredicate = DamageSourcePredicate.m_25451_(p_81613_.get("predicate"));
         return new DamageSourceCondition(damagesourcepredicate);
      }
   }
}