package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

public class LootTableTrigger extends SimpleCriterionTrigger<LootTableTrigger.TriggerInstance> {
   static final ResourceLocation f_54593_ = new ResourceLocation("player_generates_container_loot");

   public ResourceLocation m_7295_() {
      return f_54593_;
   }

   protected LootTableTrigger.TriggerInstance m_7214_(JsonObject p_54601_, EntityPredicate.Composite p_54602_, DeserializationContext p_54603_) {
      ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_54601_, "loot_table"));
      return new LootTableTrigger.TriggerInstance(p_54602_, resourcelocation);
   }

   public void m_54597_(ServerPlayer p_54598_, ResourceLocation p_54599_) {
      this.m_66234_(p_54598_, (p_54606_) -> {
         return p_54606_.m_54620_(p_54599_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final ResourceLocation f_54612_;

      public TriggerInstance(EntityPredicate.Composite p_54614_, ResourceLocation p_54615_) {
         super(LootTableTrigger.f_54593_, p_54614_);
         this.f_54612_ = p_54615_;
      }

      public static LootTableTrigger.TriggerInstance m_54618_(ResourceLocation p_54619_) {
         return new LootTableTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_54619_);
      }

      public boolean m_54620_(ResourceLocation p_54621_) {
         return this.f_54612_.equals(p_54621_);
      }

      public JsonObject m_7683_(SerializationContext p_54617_) {
         JsonObject jsonobject = super.m_7683_(p_54617_);
         jsonobject.addProperty("loot_table", this.f_54612_.toString());
         return jsonobject;
      }
   }
}