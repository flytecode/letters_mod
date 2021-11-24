package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class ItemUsedOnBlockTrigger extends SimpleCriterionTrigger<ItemUsedOnBlockTrigger.TriggerInstance> {
   static final ResourceLocation f_45478_ = new ResourceLocation("item_used_on_block");

   public ResourceLocation m_7295_() {
      return f_45478_;
   }

   public ItemUsedOnBlockTrigger.TriggerInstance m_7214_(JsonObject p_45493_, EntityPredicate.Composite p_45494_, DeserializationContext p_45495_) {
      LocationPredicate locationpredicate = LocationPredicate.m_52629_(p_45493_.get("location"));
      ItemPredicate itempredicate = ItemPredicate.m_45051_(p_45493_.get("item"));
      return new ItemUsedOnBlockTrigger.TriggerInstance(p_45494_, locationpredicate, itempredicate);
   }

   public void m_45482_(ServerPlayer p_45483_, BlockPos p_45484_, ItemStack p_45485_) {
      BlockState blockstate = p_45483_.m_9236_().m_8055_(p_45484_);
      this.m_66234_(p_45483_, (p_45491_) -> {
         return p_45491_.m_45510_(blockstate, p_45483_.m_9236_(), p_45484_, p_45485_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final LocationPredicate f_45501_;
      private final ItemPredicate f_45502_;

      public TriggerInstance(EntityPredicate.Composite p_45504_, LocationPredicate p_45505_, ItemPredicate p_45506_) {
         super(ItemUsedOnBlockTrigger.f_45478_, p_45504_);
         this.f_45501_ = p_45505_;
         this.f_45502_ = p_45506_;
      }

      public static ItemUsedOnBlockTrigger.TriggerInstance m_45507_(LocationPredicate.Builder p_45508_, ItemPredicate.Builder p_45509_) {
         return new ItemUsedOnBlockTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_45508_.m_52658_(), p_45509_.m_45077_());
      }

      public boolean m_45510_(BlockState p_45511_, ServerLevel p_45512_, BlockPos p_45513_, ItemStack p_45514_) {
         return !this.f_45501_.m_52617_(p_45512_, (double)p_45513_.m_123341_() + 0.5D, (double)p_45513_.m_123342_() + 0.5D, (double)p_45513_.m_123343_() + 0.5D) ? false : this.f_45502_.m_45049_(p_45514_);
      }

      public JsonObject m_7683_(SerializationContext p_45516_) {
         JsonObject jsonobject = super.m_7683_(p_45516_);
         jsonobject.add("location", this.f_45501_.m_52616_());
         jsonobject.add("item", this.f_45502_.m_45048_());
         return jsonobject;
      }
   }
}