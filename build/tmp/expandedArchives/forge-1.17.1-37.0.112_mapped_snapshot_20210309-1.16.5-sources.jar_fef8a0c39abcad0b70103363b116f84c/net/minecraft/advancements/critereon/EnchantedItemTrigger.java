package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class EnchantedItemTrigger extends SimpleCriterionTrigger<EnchantedItemTrigger.TriggerInstance> {
   static final ResourceLocation f_27664_ = new ResourceLocation("enchanted_item");

   public ResourceLocation m_7295_() {
      return f_27664_;
   }

   public EnchantedItemTrigger.TriggerInstance m_7214_(JsonObject p_27677_, EntityPredicate.Composite p_27678_, DeserializationContext p_27679_) {
      ItemPredicate itempredicate = ItemPredicate.m_45051_(p_27677_.get("item"));
      MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(p_27677_.get("levels"));
      return new EnchantedItemTrigger.TriggerInstance(p_27678_, itempredicate, minmaxbounds$ints);
   }

   public void m_27668_(ServerPlayer p_27669_, ItemStack p_27670_, int p_27671_) {
      this.m_66234_(p_27669_, (p_27675_) -> {
         return p_27675_.m_27691_(p_27670_, p_27671_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate f_27685_;
      private final MinMaxBounds.Ints f_27686_;

      public TriggerInstance(EntityPredicate.Composite p_27688_, ItemPredicate p_27689_, MinMaxBounds.Ints p_27690_) {
         super(EnchantedItemTrigger.f_27664_, p_27688_);
         this.f_27685_ = p_27689_;
         this.f_27686_ = p_27690_;
      }

      public static EnchantedItemTrigger.TriggerInstance m_27696_() {
         return new EnchantedItemTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, ItemPredicate.f_45028_, MinMaxBounds.Ints.f_55364_);
      }

      public boolean m_27691_(ItemStack p_27692_, int p_27693_) {
         if (!this.f_27685_.m_45049_(p_27692_)) {
            return false;
         } else {
            return this.f_27686_.m_55390_(p_27693_);
         }
      }

      public JsonObject m_7683_(SerializationContext p_27695_) {
         JsonObject jsonobject = super.m_7683_(p_27695_);
         jsonobject.add("item", this.f_27685_.m_45048_());
         jsonobject.add("levels", this.f_27686_.m_55328_());
         return jsonobject;
      }
   }
}