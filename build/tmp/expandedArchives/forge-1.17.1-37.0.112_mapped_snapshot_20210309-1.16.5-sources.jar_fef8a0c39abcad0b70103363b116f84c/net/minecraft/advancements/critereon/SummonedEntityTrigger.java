package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class SummonedEntityTrigger extends SimpleCriterionTrigger<SummonedEntityTrigger.TriggerInstance> {
   static final ResourceLocation f_68252_ = new ResourceLocation("summoned_entity");

   public ResourceLocation m_7295_() {
      return f_68252_;
   }

   public SummonedEntityTrigger.TriggerInstance m_7214_(JsonObject p_68260_, EntityPredicate.Composite p_68261_, DeserializationContext p_68262_) {
      EntityPredicate.Composite entitypredicate$composite = EntityPredicate.Composite.m_36677_(p_68260_, "entity", p_68262_);
      return new SummonedEntityTrigger.TriggerInstance(p_68261_, entitypredicate$composite);
   }

   public void m_68256_(ServerPlayer p_68257_, Entity p_68258_) {
      LootContext lootcontext = EntityPredicate.m_36616_(p_68257_, p_68258_);
      this.m_66234_(p_68257_, (p_68265_) -> {
         return p_68265_.m_68279_(lootcontext);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite f_68271_;

      public TriggerInstance(EntityPredicate.Composite p_68273_, EntityPredicate.Composite p_68274_) {
         super(SummonedEntityTrigger.f_68252_, p_68273_);
         this.f_68271_ = p_68274_;
      }

      public static SummonedEntityTrigger.TriggerInstance m_68275_(EntityPredicate.Builder p_68276_) {
         return new SummonedEntityTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_68276_.m_36662_()));
      }

      public boolean m_68279_(LootContext p_68280_) {
         return this.f_68271_.m_36681_(p_68280_);
      }

      public JsonObject m_7683_(SerializationContext p_68278_) {
         JsonObject jsonobject = super.m_7683_(p_68278_);
         jsonobject.add("entity", this.f_68271_.m_36675_(p_68278_));
         return jsonobject;
      }
   }
}