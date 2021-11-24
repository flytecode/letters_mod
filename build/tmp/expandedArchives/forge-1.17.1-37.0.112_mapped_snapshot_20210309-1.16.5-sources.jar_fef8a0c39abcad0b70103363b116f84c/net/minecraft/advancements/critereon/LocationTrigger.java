package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class LocationTrigger extends SimpleCriterionTrigger<LocationTrigger.TriggerInstance> {
   final ResourceLocation f_53641_;

   public LocationTrigger(ResourceLocation p_53643_) {
      this.f_53641_ = p_53643_;
   }

   public ResourceLocation m_7295_() {
      return this.f_53641_;
   }

   public LocationTrigger.TriggerInstance m_7214_(JsonObject p_53653_, EntityPredicate.Composite p_53654_, DeserializationContext p_53655_) {
      JsonObject jsonobject = GsonHelper.m_13841_(p_53653_, "location", p_53653_);
      LocationPredicate locationpredicate = LocationPredicate.m_52629_(jsonobject);
      return new LocationTrigger.TriggerInstance(this.f_53641_, p_53654_, locationpredicate);
   }

   public void m_53645_(ServerPlayer p_53646_) {
      this.m_66234_(p_53646_, (p_53649_) -> {
         return p_53649_.m_53665_(p_53646_.m_9236_(), p_53646_.m_20185_(), p_53646_.m_20186_(), p_53646_.m_20189_());
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final LocationPredicate f_53660_;

      public TriggerInstance(ResourceLocation p_53662_, EntityPredicate.Composite p_53663_, LocationPredicate p_53664_) {
         super(p_53662_, p_53663_);
         this.f_53660_ = p_53664_;
      }

      public static LocationTrigger.TriggerInstance m_53670_(LocationPredicate p_53671_) {
         return new LocationTrigger.TriggerInstance(CriteriaTriggers.f_10582_.f_53641_, EntityPredicate.Composite.f_36667_, p_53671_);
      }

      public static LocationTrigger.TriggerInstance m_154320_(EntityPredicate p_154321_) {
         return new LocationTrigger.TriggerInstance(CriteriaTriggers.f_10582_.f_53641_, EntityPredicate.Composite.m_36673_(p_154321_), LocationPredicate.f_52592_);
      }

      public static LocationTrigger.TriggerInstance m_53674_() {
         return new LocationTrigger.TriggerInstance(CriteriaTriggers.f_10583_.f_53641_, EntityPredicate.Composite.f_36667_, LocationPredicate.f_52592_);
      }

      public static LocationTrigger.TriggerInstance m_53675_() {
         return new LocationTrigger.TriggerInstance(CriteriaTriggers.f_10557_.f_53641_, EntityPredicate.Composite.f_36667_, LocationPredicate.f_52592_);
      }

      public static LocationTrigger.TriggerInstance m_154322_(Block p_154323_, Item p_154324_) {
         return m_154320_(EntityPredicate.Builder.m_36633_().m_36640_(EntityEquipmentPredicate.Builder.m_32204_().m_32212_(ItemPredicate.Builder.m_45068_().m_151445_(p_154324_).m_45077_()).m_32207_()).m_150330_(LocationPredicate.Builder.m_52651_().m_52652_(BlockPredicate.Builder.m_17924_().m_146726_(p_154323_).m_17931_()).m_52658_()).m_36662_());
      }

      public boolean m_53665_(ServerLevel p_53666_, double p_53667_, double p_53668_, double p_53669_) {
         return this.f_53660_.m_52617_(p_53666_, p_53667_, p_53668_, p_53669_);
      }

      public JsonObject m_7683_(SerializationContext p_53673_) {
         JsonObject jsonobject = super.m_7683_(p_53673_);
         jsonobject.add("location", this.f_53660_.m_52616_());
         return jsonobject;
      }
   }
}