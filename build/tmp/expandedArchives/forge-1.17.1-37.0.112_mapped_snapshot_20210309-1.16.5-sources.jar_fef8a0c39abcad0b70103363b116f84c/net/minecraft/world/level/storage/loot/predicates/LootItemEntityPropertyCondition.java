package net.minecraft.world.level.storage.loot.predicates;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.util.Set;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class LootItemEntityPropertyCondition implements LootItemCondition {
   final EntityPredicate f_81846_;
   final LootContext.EntityTarget f_81847_;

   LootItemEntityPropertyCondition(EntityPredicate p_81849_, LootContext.EntityTarget p_81850_) {
      this.f_81846_ = p_81849_;
      this.f_81847_ = p_81850_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81815_;
   }

   public Set<LootContextParam<?>> m_6231_() {
      return ImmutableSet.of(LootContextParams.f_81460_, this.f_81847_.m_79003_());
   }

   public boolean test(LootContext p_81871_) {
      Entity entity = p_81871_.m_78953_(this.f_81847_.m_79003_());
      Vec3 vec3 = p_81871_.m_78953_(LootContextParams.f_81460_);
      return this.f_81846_.m_36607_(p_81871_.m_78952_(), vec3, entity);
   }

   public static LootItemCondition.Builder m_81862_(LootContext.EntityTarget p_81863_) {
      return m_81864_(p_81863_, EntityPredicate.Builder.m_36633_());
   }

   public static LootItemCondition.Builder m_81864_(LootContext.EntityTarget p_81865_, EntityPredicate.Builder p_81866_) {
      return () -> {
         return new LootItemEntityPropertyCondition(p_81866_.m_36662_(), p_81865_);
      };
   }

   public static LootItemCondition.Builder m_81867_(LootContext.EntityTarget p_81868_, EntityPredicate p_81869_) {
      return () -> {
         return new LootItemEntityPropertyCondition(p_81869_, p_81868_);
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LootItemEntityPropertyCondition> {
      public void m_6170_(JsonObject p_81884_, LootItemEntityPropertyCondition p_81885_, JsonSerializationContext p_81886_) {
         p_81884_.add("predicate", p_81885_.f_81846_.m_36606_());
         p_81884_.add("entity", p_81886_.serialize(p_81885_.f_81847_));
      }

      public LootItemEntityPropertyCondition m_7561_(JsonObject p_81892_, JsonDeserializationContext p_81893_) {
         EntityPredicate entitypredicate = EntityPredicate.m_36614_(p_81892_.get("predicate"));
         return new LootItemEntityPropertyCondition(entitypredicate, GsonHelper.m_13836_(p_81892_, "entity", p_81893_, LootContext.EntityTarget.class));
      }
   }
}