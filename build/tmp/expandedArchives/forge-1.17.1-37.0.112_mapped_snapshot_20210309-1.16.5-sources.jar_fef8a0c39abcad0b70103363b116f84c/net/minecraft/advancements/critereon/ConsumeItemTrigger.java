package net.minecraft.advancements.critereon;

import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.level.ItemLike;

public class ConsumeItemTrigger extends SimpleCriterionTrigger<ConsumeItemTrigger.TriggerInstance> {
   static final ResourceLocation f_23678_ = new ResourceLocation("consume_item");

   public ResourceLocation m_7295_() {
      return f_23678_;
   }

   public ConsumeItemTrigger.TriggerInstance m_7214_(JsonObject p_23689_, EntityPredicate.Composite p_23690_, DeserializationContext p_23691_) {
      return new ConsumeItemTrigger.TriggerInstance(p_23690_, ItemPredicate.m_45051_(p_23689_.get("item")));
   }

   public void m_23682_(ServerPlayer p_23683_, ItemStack p_23684_) {
      this.m_66234_(p_23683_, (p_23687_) -> {
         return p_23687_.m_23701_(p_23684_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ItemPredicate f_23697_;

      public TriggerInstance(EntityPredicate.Composite p_23699_, ItemPredicate p_23700_) {
         super(ConsumeItemTrigger.f_23678_, p_23699_);
         this.f_23697_ = p_23700_;
      }

      public static ConsumeItemTrigger.TriggerInstance m_23707_() {
         return new ConsumeItemTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, ItemPredicate.f_45028_);
      }

      public static ConsumeItemTrigger.TriggerInstance m_148081_(ItemPredicate p_148082_) {
         return new ConsumeItemTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_148082_);
      }

      public static ConsumeItemTrigger.TriggerInstance m_23703_(ItemLike p_23704_) {
         return new ConsumeItemTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, new ItemPredicate((Tag<Item>)null, ImmutableSet.of(p_23704_.m_5456_()), MinMaxBounds.Ints.f_55364_, MinMaxBounds.Ints.f_55364_, EnchantmentPredicate.f_30465_, EnchantmentPredicate.f_30465_, (Potion)null, NbtPredicate.f_57471_));
      }

      public boolean m_23701_(ItemStack p_23702_) {
         return this.f_23697_.m_45049_(p_23702_);
      }

      public JsonObject m_7683_(SerializationContext p_23706_) {
         JsonObject jsonobject = super.m_7683_(p_23706_);
         jsonobject.add("item", this.f_23697_.m_45048_());
         return jsonobject;
      }
   }
}