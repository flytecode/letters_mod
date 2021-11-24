package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class TickTrigger extends SimpleCriterionTrigger<TickTrigger.TriggerInstance> {
   public static final ResourceLocation f_70637_ = new ResourceLocation("tick");

   public ResourceLocation m_7295_() {
      return f_70637_;
   }

   public TickTrigger.TriggerInstance m_7214_(JsonObject p_70644_, EntityPredicate.Composite p_70645_, DeserializationContext p_70646_) {
      return new TickTrigger.TriggerInstance(p_70645_);
   }

   public void m_70641_(ServerPlayer p_70642_) {
      this.m_66234_(p_70642_, (p_70648_) -> {
         return true;
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      public TriggerInstance(EntityPredicate.Composite p_70654_) {
         super(TickTrigger.f_70637_, p_70654_);
      }
   }
}