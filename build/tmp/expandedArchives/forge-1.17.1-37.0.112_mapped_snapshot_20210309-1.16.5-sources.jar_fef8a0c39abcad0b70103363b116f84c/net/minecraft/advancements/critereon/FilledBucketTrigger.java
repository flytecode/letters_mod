package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class FilledBucketTrigger extends SimpleCriterionTrigger<FilledBucketTrigger.TriggerInstance> {
   static final ResourceLocation f_38768_ = new ResourceLocation("filled_bucket");

   public ResourceLocation m_7295_() {
      return f_38768_;
   }

   public FilledBucketTrigger.TriggerInstance m_7214_(JsonObject p_38779_, EntityPredicate.Composite p_38780_, DeserializationContext p_38781_) {
      ItemPredicate itempredicate = ItemPredicate.m_45051_(p_38779_.get("item"));
      return new FilledBucketTrigger.TriggerInstance(p_38780_, itempredicate);
   }

   public void m_38772_(ServerPlayer p_38773_, ItemStack p_38774_) {
      this.m_66234_(p_38773_, (p_38777_) -> {
         return p_38777_.m_38791_(p_38774_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate f_38787_;

      public TriggerInstance(EntityPredicate.Composite p_38789_, ItemPredicate p_38790_) {
         super(FilledBucketTrigger.f_38768_, p_38789_);
         this.f_38787_ = p_38790_;
      }

      public static FilledBucketTrigger.TriggerInstance m_38793_(ItemPredicate p_38794_) {
         return new FilledBucketTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_38794_);
      }

      public boolean m_38791_(ItemStack p_38792_) {
         return this.f_38787_.m_45049_(p_38792_);
      }

      public JsonObject m_7683_(SerializationContext p_38796_) {
         JsonObject jsonobject = super.m_7683_(p_38796_);
         jsonobject.add("item", this.f_38787_.m_45048_());
         return jsonobject;
      }
   }
}