package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class UsedTotemTrigger extends SimpleCriterionTrigger<UsedTotemTrigger.TriggerInstance> {
   static final ResourceLocation f_74427_ = new ResourceLocation("used_totem");

   public ResourceLocation m_7295_() {
      return f_74427_;
   }

   public UsedTotemTrigger.TriggerInstance m_7214_(JsonObject p_74438_, EntityPredicate.Composite p_74439_, DeserializationContext p_74440_) {
      ItemPredicate itempredicate = ItemPredicate.m_45051_(p_74438_.get("item"));
      return new UsedTotemTrigger.TriggerInstance(p_74439_, itempredicate);
   }

   public void m_74431_(ServerPlayer p_74432_, ItemStack p_74433_) {
      this.m_66234_(p_74432_, (p_74436_) -> {
         return p_74436_.m_74450_(p_74433_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate f_74446_;

      public TriggerInstance(EntityPredicate.Composite p_74448_, ItemPredicate p_74449_) {
         super(UsedTotemTrigger.f_74427_, p_74448_);
         this.f_74446_ = p_74449_;
      }

      public static UsedTotemTrigger.TriggerInstance m_163724_(ItemPredicate p_163725_) {
         return new UsedTotemTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_163725_);
      }

      public static UsedTotemTrigger.TriggerInstance m_74452_(ItemLike p_74453_) {
         return new UsedTotemTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, ItemPredicate.Builder.m_45068_().m_151445_(p_74453_).m_45077_());
      }

      public boolean m_74450_(ItemStack p_74451_) {
         return this.f_74446_.m_45049_(p_74451_);
      }

      public JsonObject m_7683_(SerializationContext p_74455_) {
         JsonObject jsonobject = super.m_7683_(p_74455_);
         jsonobject.add("item", this.f_74446_.m_45048_());
         return jsonobject;
      }
   }
}