package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class UsingItemTrigger extends SimpleCriterionTrigger<UsingItemTrigger.TriggerInstance> {
   static final ResourceLocation f_163861_ = new ResourceLocation("using_item");

   public ResourceLocation m_7295_() {
      return f_163861_;
   }

   public UsingItemTrigger.TriggerInstance m_7214_(JsonObject p_163872_, EntityPredicate.Composite p_163873_, DeserializationContext p_163874_) {
      ItemPredicate itempredicate = ItemPredicate.m_45051_(p_163872_.get("item"));
      return new UsingItemTrigger.TriggerInstance(p_163873_, itempredicate);
   }

   public void m_163865_(ServerPlayer p_163866_, ItemStack p_163867_) {
      this.m_66234_(p_163866_, (p_163870_) -> {
         return p_163870_.m_163886_(p_163867_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate f_163879_;

      public TriggerInstance(EntityPredicate.Composite p_163881_, ItemPredicate p_163882_) {
         super(UsingItemTrigger.f_163861_, p_163881_);
         this.f_163879_ = p_163882_;
      }

      public static UsingItemTrigger.TriggerInstance m_163883_(EntityPredicate.Builder p_163884_, ItemPredicate.Builder p_163885_) {
         return new UsingItemTrigger.TriggerInstance(EntityPredicate.Composite.m_36673_(p_163884_.m_36662_()), p_163885_.m_45077_());
      }

      public boolean m_163886_(ItemStack p_163887_) {
         return this.f_163879_.m_45049_(p_163887_);
      }

      public JsonObject m_7683_(SerializationContext p_163889_) {
         JsonObject jsonobject = super.m_7683_(p_163889_);
         jsonobject.add("item", this.f_163879_.m_45048_());
         return jsonobject;
      }
   }
}