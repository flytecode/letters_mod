package net.minecraft.world.level.storage.loot.predicates;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.ValidationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConditionReference implements LootItemCondition {
   private static final Logger f_81549_ = LogManager.getLogger();
   final ResourceLocation f_81550_;

   ConditionReference(ResourceLocation p_81553_) {
      this.f_81550_ = p_81553_;
   }

   public LootItemConditionType m_7940_() {
      return LootItemConditions.f_81825_;
   }

   public void m_6169_(ValidationContext p_81560_) {
      if (p_81560_.m_79370_(this.f_81550_)) {
         p_81560_.m_79357_("Condition " + this.f_81550_ + " is recursively called");
      } else {
         LootItemCondition.super.m_6169_(p_81560_);
         LootItemCondition lootitemcondition = p_81560_.m_79379_(this.f_81550_);
         if (lootitemcondition == null) {
            p_81560_.m_79357_("Unknown condition table called " + this.f_81550_);
         } else {
            lootitemcondition.m_6169_(p_81560_.m_79359_(".{" + this.f_81550_ + "}", this.f_81550_));
         }

      }
   }

   public boolean test(LootContext p_81558_) {
      LootItemCondition lootitemcondition = p_81558_.m_78950_(this.f_81550_);
      if (p_81558_.m_78938_(lootitemcondition)) {
         boolean flag;
         try {
            flag = lootitemcondition.test(p_81558_);
         } finally {
            p_81558_.m_78948_(lootitemcondition);
         }

         return flag;
      } else {
         f_81549_.warn("Detected infinite loop in loot tables");
         return false;
      }
   }

   public static LootItemCondition.Builder m_165480_(ResourceLocation p_165481_) {
      return () -> {
         return new ConditionReference(p_165481_);
      };
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ConditionReference> {
      public void m_6170_(JsonObject p_81571_, ConditionReference p_81572_, JsonSerializationContext p_81573_) {
         p_81571_.addProperty("name", p_81572_.f_81550_.toString());
      }

      public ConditionReference m_7561_(JsonObject p_81579_, JsonDeserializationContext p_81580_) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_81579_, "name"));
         return new ConditionReference(resourcelocation);
      }
   }
}