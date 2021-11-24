package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.storage.loot.LootContext;

public class LightningStrikeTrigger extends SimpleCriterionTrigger<LightningStrikeTrigger.TriggerInstance> {
   static final ResourceLocation f_153384_ = new ResourceLocation("lightning_strike");

   public ResourceLocation m_7295_() {
      return f_153384_;
   }

   public LightningStrikeTrigger.TriggerInstance m_7214_(JsonObject p_153396_, EntityPredicate.Composite p_153397_, DeserializationContext p_153398_) {
      EntityPredicate.Composite entitypredicate$composite = EntityPredicate.Composite.m_36677_(p_153396_, "lightning", p_153398_);
      EntityPredicate.Composite entitypredicate$composite1 = EntityPredicate.Composite.m_36677_(p_153396_, "bystander", p_153398_);
      return new LightningStrikeTrigger.TriggerInstance(p_153397_, entitypredicate$composite, entitypredicate$composite1);
   }

   public void m_153391_(ServerPlayer p_153392_, LightningBolt p_153393_, List<Entity> p_153394_) {
      List<LootContext> list = p_153394_.stream().map((p_153390_) -> {
         return EntityPredicate.m_36616_(p_153392_, p_153390_);
      }).collect(Collectors.toList());
      LootContext lootcontext = EntityPredicate.m_36616_(p_153392_, p_153393_);
      this.m_66234_(p_153392_, (p_153402_) -> {
         return p_153402_.m_153418_(lootcontext, list);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite f_153407_;
      private final EntityPredicate.Composite f_153408_;

      public TriggerInstance(EntityPredicate.Composite p_153410_, EntityPredicate.Composite p_153411_, EntityPredicate.Composite p_153412_) {
         super(LightningStrikeTrigger.f_153384_, p_153410_);
         this.f_153407_ = p_153411_;
         this.f_153408_ = p_153412_;
      }

      public static LightningStrikeTrigger.TriggerInstance m_153413_(EntityPredicate p_153414_, EntityPredicate p_153415_) {
         return new LightningStrikeTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_153414_), EntityPredicate.Composite.m_36673_(p_153415_));
      }

      public boolean m_153418_(LootContext p_153419_, List<LootContext> p_153420_) {
         if (!this.f_153407_.m_36681_(p_153419_)) {
            return false;
         } else {
            return this.f_153408_ == EntityPredicate.Composite.f_36667_ || !p_153420_.stream().noneMatch(this.f_153408_::m_36681_);
         }
      }

      public JsonObject m_7683_(SerializationContext p_153417_) {
         JsonObject jsonobject = super.m_7683_(p_153417_);
         jsonobject.add("lightning", this.f_153407_.m_36675_(p_153417_));
         jsonobject.add("bystander", this.f_153408_.m_36675_(p_153417_));
         return jsonobject;
      }
   }
}