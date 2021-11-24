package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public class LocationCheck implements LootItemCondition {
   final LocationPredicate f_81716_;
   final BlockPos f_81717_;

   LocationCheck(LocationPredicate p_81719_, BlockPos p_81720_) {
      this.f_81716_ = p_81719_;
      this.f_81717_ = p_81720_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81823_;
   }

   public boolean test(LootContext p_81731_) {
      Vec3 vec3 = p_81731_.m_78953_(LootContextParams.f_81460_);
      return vec3 != null && this.f_81716_.m_52617_(p_81731_.m_78952_(), vec3.m_7096_() + (double)this.f_81717_.m_123341_(), vec3.m_7098_() + (double)this.f_81717_.m_123342_(), vec3.m_7094_() + (double)this.f_81717_.m_123343_());
   }

   public static LootItemCondition.Builder m_81725_(LocationPredicate.Builder p_81726_) {
      return () -> {
         return new LocationCheck(p_81726_.m_52658_(), BlockPos.f_121853_);
      };
   }

   public static LootItemCondition.Builder m_81727_(LocationPredicate.Builder p_81728_, BlockPos p_81729_) {
      return () -> {
         return new LocationCheck(p_81728_.m_52658_(), p_81729_);
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<LocationCheck> {
      public void m_6170_(JsonObject p_81749_, LocationCheck p_81750_, JsonSerializationContext p_81751_) {
         p_81749_.add("predicate", p_81750_.f_81716_.m_52616_());
         if (p_81750_.f_81717_.m_123341_() != 0) {
            p_81749_.addProperty("offsetX", p_81750_.f_81717_.m_123341_());
         }

         if (p_81750_.f_81717_.m_123342_() != 0) {
            p_81749_.addProperty("offsetY", p_81750_.f_81717_.m_123342_());
         }

         if (p_81750_.f_81717_.m_123343_() != 0) {
            p_81749_.addProperty("offsetZ", p_81750_.f_81717_.m_123343_());
         }

      }

      public LocationCheck m_7561_(JsonObject p_81757_, JsonDeserializationContext p_81758_) {
         LocationPredicate locationpredicate = LocationPredicate.m_52629_(p_81757_.get("predicate"));
         int i = GsonHelper.m_13824_(p_81757_, "offsetX", 0);
         int j = GsonHelper.m_13824_(p_81757_, "offsetY", 0);
         int k = GsonHelper.m_13824_(p_81757_, "offsetZ", 0);
         return new LocationCheck(locationpredicate, new BlockPos(i, j, k));
      }
   }
}