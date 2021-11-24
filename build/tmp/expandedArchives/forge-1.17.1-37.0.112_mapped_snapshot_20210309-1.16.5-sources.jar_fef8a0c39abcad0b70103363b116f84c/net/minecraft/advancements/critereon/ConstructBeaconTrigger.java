package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ConstructBeaconTrigger extends SimpleCriterionTrigger<ConstructBeaconTrigger.TriggerInstance> {
   static final ResourceLocation f_22742_ = new ResourceLocation("construct_beacon");

   public ResourceLocation m_7295_() {
      return f_22742_;
   }

   public ConstructBeaconTrigger.TriggerInstance m_7214_(JsonObject p_22753_, EntityPredicate.Composite p_22754_, DeserializationContext p_22755_) {
      MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(p_22753_.get("level"));
      return new ConstructBeaconTrigger.TriggerInstance(p_22754_, minmaxbounds$ints);
   }

   public void m_148029_(ServerPlayer p_148030_, int p_148031_) {
      this.m_66234_(p_148030_, (p_148028_) -> {
         return p_148028_.m_148032_(p_148031_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final MinMaxBounds.Ints f_22761_;

      public TriggerInstance(EntityPredicate.Composite p_22763_, MinMaxBounds.Ints p_22764_) {
         super(ConstructBeaconTrigger.f_22742_, p_22763_);
         this.f_22761_ = p_22764_;
      }

      public static ConstructBeaconTrigger.TriggerInstance m_148034_() {
         return new ConstructBeaconTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, MinMaxBounds.Ints.f_55364_);
      }

      public static ConstructBeaconTrigger.TriggerInstance m_22765_(MinMaxBounds.Ints p_22766_) {
         return new ConstructBeaconTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_22766_);
      }

      public boolean m_148032_(int p_148033_) {
         return this.f_22761_.m_55390_(p_148033_);
      }

      public JsonObject m_7683_(SerializationContext p_22770_) {
         JsonObject jsonobject = super.m_7683_(p_22770_);
         jsonobject.add("level", this.f_22761_.m_55328_());
         return jsonobject;
      }
   }
}