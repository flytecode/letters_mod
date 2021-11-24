package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class UsedEnderEyeTrigger extends SimpleCriterionTrigger<UsedEnderEyeTrigger.TriggerInstance> {
   static final ResourceLocation f_73928_ = new ResourceLocation("used_ender_eye");

   public ResourceLocation m_7295_() {
      return f_73928_;
   }

   public UsedEnderEyeTrigger.TriggerInstance m_7214_(JsonObject p_73939_, EntityPredicate.Composite p_73940_, DeserializationContext p_73941_) {
      MinMaxBounds.Doubles minmaxbounds$doubles = MinMaxBounds.Doubles.m_154791_(p_73939_.get("distance"));
      return new UsedEnderEyeTrigger.TriggerInstance(p_73940_, minmaxbounds$doubles);
   }

   public void m_73935_(ServerPlayer p_73936_, BlockPos p_73937_) {
      double d0 = p_73936_.m_20185_() - (double)p_73937_.m_123341_();
      double d1 = p_73936_.m_20189_() - (double)p_73937_.m_123343_();
      double d2 = d0 * d0 + d1 * d1;
      this.m_66234_(p_73936_, (p_73934_) -> {
         return p_73934_.m_73951_(d2);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final MinMaxBounds.Doubles f_73947_;

      public TriggerInstance(EntityPredicate.Composite p_73949_, MinMaxBounds.Doubles p_73950_) {
         super(UsedEnderEyeTrigger.f_73928_, p_73949_);
         this.f_73947_ = p_73950_;
      }

      public boolean m_73951_(double p_73952_) {
         return this.f_73947_.m_154812_(p_73952_);
      }
   }
}