package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ItemDurabilityTrigger extends SimpleCriterionTrigger<ItemDurabilityTrigger.TriggerInstance> {
   static final ResourceLocation f_43665_ = new ResourceLocation("item_durability_changed");

   public ResourceLocation m_7295_() {
      return f_43665_;
   }

   public ItemDurabilityTrigger.TriggerInstance m_7214_(JsonObject p_43678_, EntityPredicate.Composite p_43679_, DeserializationContext p_43680_) {
      ItemPredicate itempredicate = ItemPredicate.m_45051_(p_43678_.get("item"));
      MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(p_43678_.get("durability"));
      MinMaxBounds.Ints minmaxbounds$ints1 = MinMaxBounds.Ints.m_55373_(p_43678_.get("delta"));
      return new ItemDurabilityTrigger.TriggerInstance(p_43679_, itempredicate, minmaxbounds$ints, minmaxbounds$ints1);
   }

   public void m_43669_(ServerPlayer p_43670_, ItemStack p_43671_, int p_43672_) {
      this.m_66234_(p_43670_, (p_43676_) -> {
         return p_43676_.m_43698_(p_43671_, p_43672_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate f_43686_;
      private final MinMaxBounds.Ints f_43687_;
      private final MinMaxBounds.Ints f_43688_;

      public TriggerInstance(EntityPredicate.Composite p_43690_, ItemPredicate p_43691_, MinMaxBounds.Ints p_43692_, MinMaxBounds.Ints p_43693_) {
         super(ItemDurabilityTrigger.f_43665_, p_43690_);
         this.f_43686_ = p_43691_;
         this.f_43687_ = p_43692_;
         this.f_43688_ = p_43693_;
      }

      public static ItemDurabilityTrigger.TriggerInstance m_151286_(ItemPredicate p_151287_, MinMaxBounds.Ints p_151288_) {
         return m_43694_(EntityPredicate.Composite.f_36667_, p_151287_, p_151288_);
      }

      public static ItemDurabilityTrigger.TriggerInstance m_43694_(EntityPredicate.Composite p_43695_, ItemPredicate p_43696_, MinMaxBounds.Ints p_43697_) {
         return new ItemDurabilityTrigger.TriggerInstance(p_43695_, p_43696_, p_43697_, MinMaxBounds.Ints.f_55364_);
      }

      public boolean m_43698_(ItemStack p_43699_, int p_43700_) {
         if (!this.f_43686_.m_45049_(p_43699_)) {
            return false;
         } else if (!this.f_43687_.m_55390_(p_43699_.m_41776_() - p_43700_)) {
            return false;
         } else {
            return this.f_43688_.m_55390_(p_43699_.m_41773_() - p_43700_);
         }
      }

      public JsonObject m_7683_(SerializationContext p_43702_) {
         JsonObject jsonobject = super.m_7683_(p_43702_);
         jsonobject.add("item", this.f_43686_.m_45048_());
         jsonobject.add("durability", this.f_43687_.m_55328_());
         jsonobject.add("delta", this.f_43688_.m_55328_());
         return jsonobject;
      }
   }
}