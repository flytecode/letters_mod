package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public class TradeTrigger extends SimpleCriterionTrigger<TradeTrigger.TriggerInstance> {
   static final ResourceLocation f_70955_ = new ResourceLocation("villager_trade");

   public ResourceLocation m_7295_() {
      return f_70955_;
   }

   public TradeTrigger.TriggerInstance m_7214_(JsonObject p_70964_, EntityPredicate.Composite p_70965_, DeserializationContext p_70966_) {
      EntityPredicate.Composite entitypredicate$composite = EntityPredicate.Composite.m_36677_(p_70964_, "villager", p_70966_);
      ItemPredicate itempredicate = ItemPredicate.m_45051_(p_70964_.get("item"));
      return new TradeTrigger.TriggerInstance(p_70965_, entitypredicate$composite, itempredicate);
   }

   public void m_70959_(ServerPlayer p_70960_, AbstractVillager p_70961_, ItemStack p_70962_) {
      LootContext lootcontext = EntityPredicate.m_36616_(p_70960_, p_70961_);
      this.m_66234_(p_70960_, (p_70970_) -> {
         return p_70970_.m_70984_(lootcontext, p_70962_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite f_70976_;
      private final ItemPredicate f_70977_;

      public TriggerInstance(EntityPredicate.Composite p_70979_, EntityPredicate.Composite p_70980_, ItemPredicate p_70981_) {
         super(TradeTrigger.f_70955_, p_70979_);
         this.f_70976_ = p_70980_;
         this.f_70977_ = p_70981_;
      }

      public static TradeTrigger.TriggerInstance m_70987_() {
         return new TradeTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, ItemPredicate.f_45028_);
      }

      public boolean m_70984_(LootContext p_70985_, ItemStack p_70986_) {
         if (!this.f_70976_.m_36681_(p_70985_)) {
            return false;
         } else {
            return this.f_70977_.m_45049_(p_70986_);
         }
      }

      public JsonObject m_7683_(SerializationContext p_70983_) {
         JsonObject jsonobject = super.m_7683_(p_70983_);
         jsonobject.add("item", this.f_70977_.m_45048_());
         jsonobject.add("villager", this.f_70976_.m_36675_(p_70983_));
         return jsonobject;
      }
   }
}