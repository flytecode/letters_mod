package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;

public class ImpossibleTrigger implements CriterionTrigger<ImpossibleTrigger.TriggerInstance> {
   static final ResourceLocation f_41555_ = new ResourceLocation("impossible");

   public ResourceLocation m_7295_() {
      return f_41555_;
   }

   public void m_6467_(PlayerAdvancements p_41565_, CriterionTrigger.Listener<ImpossibleTrigger.TriggerInstance> p_41566_) {
   }

   public void m_6468_(PlayerAdvancements p_41572_, CriterionTrigger.Listener<ImpossibleTrigger.TriggerInstance> p_41573_) {
   }

   public void m_5656_(PlayerAdvancements p_41563_) {
   }

   public ImpossibleTrigger.TriggerInstance m_5868_(JsonObject p_41569_, DeserializationContext p_41570_) {
      return new ImpossibleTrigger.TriggerInstance();
   }

   public static class TriggerInstance implements CriterionTriggerInstance {
      public ResourceLocation m_7294_() {
         return ImpossibleTrigger.f_41555_;
      }

      public JsonObject m_7683_(SerializationContext p_41577_) {
         return new JsonObject();
      }
   }
}