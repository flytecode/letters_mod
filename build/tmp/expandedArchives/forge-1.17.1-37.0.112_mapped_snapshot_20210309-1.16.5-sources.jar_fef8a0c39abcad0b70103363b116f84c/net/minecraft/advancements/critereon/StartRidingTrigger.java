package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class StartRidingTrigger extends SimpleCriterionTrigger<StartRidingTrigger.TriggerInstance> {
   static final ResourceLocation f_160383_ = new ResourceLocation("started_riding");

   public ResourceLocation m_7295_() {
      return f_160383_;
   }

   public StartRidingTrigger.TriggerInstance m_7214_(JsonObject p_160390_, EntityPredicate.Composite p_160391_, DeserializationContext p_160392_) {
      return new StartRidingTrigger.TriggerInstance(p_160391_);
   }

   public void m_160387_(ServerPlayer p_160388_) {
      this.m_66234_(p_160388_, (p_160394_) -> {
         return true;
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      public TriggerInstance(EntityPredicate.Composite p_160400_) {
         super(StartRidingTrigger.f_160383_, p_160400_);
      }

      public static StartRidingTrigger.TriggerInstance m_160401_(EntityPredicate.Builder p_160402_) {
         return new StartRidingTrigger.TriggerInstance(EntityPredicate.Composite.m_36673_(p_160402_.m_36662_()));
      }
   }
}