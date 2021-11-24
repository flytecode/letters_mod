package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class LevitationTrigger extends SimpleCriterionTrigger<LevitationTrigger.TriggerInstance> {
   static final ResourceLocation f_49112_ = new ResourceLocation("levitation");

   public ResourceLocation m_7295_() {
      return f_49112_;
   }

   public LevitationTrigger.TriggerInstance m_7214_(JsonObject p_49126_, EntityPredicate.Composite p_49127_, DeserializationContext p_49128_) {
      DistancePredicate distancepredicate = DistancePredicate.m_26264_(p_49126_.get("distance"));
      MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(p_49126_.get("duration"));
      return new LevitationTrigger.TriggerInstance(p_49127_, distancepredicate, minmaxbounds$ints);
   }

   public void m_49116_(ServerPlayer p_49117_, Vec3 p_49118_, int p_49119_) {
      this.m_66234_(p_49117_, (p_49124_) -> {
         return p_49124_.m_49140_(p_49117_, p_49118_, p_49119_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final DistancePredicate f_49134_;
      private final MinMaxBounds.Ints f_49135_;

      public TriggerInstance(EntityPredicate.Composite p_49137_, DistancePredicate p_49138_, MinMaxBounds.Ints p_49139_) {
         super(LevitationTrigger.f_49112_, p_49137_);
         this.f_49134_ = p_49138_;
         this.f_49135_ = p_49139_;
      }

      public static LevitationTrigger.TriggerInstance m_49144_(DistancePredicate p_49145_) {
         return new LevitationTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_49145_, MinMaxBounds.Ints.f_55364_);
      }

      public boolean m_49140_(ServerPlayer p_49141_, Vec3 p_49142_, int p_49143_) {
         if (!this.f_49134_.m_26255_(p_49142_.f_82479_, p_49142_.f_82480_, p_49142_.f_82481_, p_49141_.m_20185_(), p_49141_.m_20186_(), p_49141_.m_20189_())) {
            return false;
         } else {
            return this.f_49135_.m_55390_(p_49143_);
         }
      }

      public JsonObject m_7683_(SerializationContext p_49147_) {
         JsonObject jsonobject = super.m_7683_(p_49147_);
         jsonobject.add("distance", this.f_49134_.m_26254_());
         jsonobject.add("duration", this.f_49135_.m_55328_());
         return jsonobject;
      }
   }
}