package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public class NetherTravelTrigger extends SimpleCriterionTrigger<NetherTravelTrigger.TriggerInstance> {
   static final ResourceLocation f_58435_ = new ResourceLocation("nether_travel");

   public ResourceLocation m_7295_() {
      return f_58435_;
   }

   public NetherTravelTrigger.TriggerInstance m_7214_(JsonObject p_58447_, EntityPredicate.Composite p_58448_, DeserializationContext p_58449_) {
      LocationPredicate locationpredicate = LocationPredicate.m_52629_(p_58447_.get("entered"));
      LocationPredicate locationpredicate1 = LocationPredicate.m_52629_(p_58447_.get("exited"));
      DistancePredicate distancepredicate = DistancePredicate.m_26264_(p_58447_.get("distance"));
      return new NetherTravelTrigger.TriggerInstance(p_58448_, locationpredicate, locationpredicate1, distancepredicate);
   }

   public void m_58439_(ServerPlayer p_58440_, Vec3 p_58441_) {
      this.m_66234_(p_58440_, (p_58445_) -> {
         return p_58445_.m_58463_(p_58440_.m_9236_(), p_58441_, p_58440_.m_20185_(), p_58440_.m_20186_(), p_58440_.m_20189_());
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final LocationPredicate f_58455_;
      private final LocationPredicate f_58456_;
      private final DistancePredicate f_58457_;

      public TriggerInstance(EntityPredicate.Composite p_58459_, LocationPredicate p_58460_, LocationPredicate p_58461_, DistancePredicate p_58462_) {
         super(NetherTravelTrigger.f_58435_, p_58459_);
         this.f_58455_ = p_58460_;
         this.f_58456_ = p_58461_;
         this.f_58457_ = p_58462_;
      }

      public static NetherTravelTrigger.TriggerInstance m_58469_(DistancePredicate p_58470_) {
         return new NetherTravelTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, LocationPredicate.f_52592_, LocationPredicate.f_52592_, p_58470_);
      }

      public boolean m_58463_(ServerLevel p_58464_, Vec3 p_58465_, double p_58466_, double p_58467_, double p_58468_) {
         if (!this.f_58455_.m_52617_(p_58464_, p_58465_.f_82479_, p_58465_.f_82480_, p_58465_.f_82481_)) {
            return false;
         } else if (!this.f_58456_.m_52617_(p_58464_, p_58466_, p_58467_, p_58468_)) {
            return false;
         } else {
            return this.f_58457_.m_26255_(p_58465_.f_82479_, p_58465_.f_82480_, p_58465_.f_82481_, p_58466_, p_58467_, p_58468_);
         }
      }

      public JsonObject m_7683_(SerializationContext p_58472_) {
         JsonObject jsonobject = super.m_7683_(p_58472_);
         jsonobject.add("entered", this.f_58455_.m_52616_());
         jsonobject.add("exited", this.f_58456_.m_52616_());
         jsonobject.add("distance", this.f_58457_.m_26254_());
         return jsonobject;
      }
   }
}