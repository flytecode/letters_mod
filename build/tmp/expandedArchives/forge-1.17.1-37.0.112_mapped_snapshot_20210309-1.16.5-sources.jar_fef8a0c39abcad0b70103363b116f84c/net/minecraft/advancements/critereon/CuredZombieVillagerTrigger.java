package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.storage.loot.LootContext;

public class CuredZombieVillagerTrigger extends SimpleCriterionTrigger<CuredZombieVillagerTrigger.TriggerInstance> {
   static final ResourceLocation f_24270_ = new ResourceLocation("cured_zombie_villager");

   public ResourceLocation m_7295_() {
      return f_24270_;
   }

   public CuredZombieVillagerTrigger.TriggerInstance m_7214_(JsonObject p_24279_, EntityPredicate.Composite p_24280_, DeserializationContext p_24281_) {
      EntityPredicate.Composite entitypredicate$composite = EntityPredicate.Composite.m_36677_(p_24279_, "zombie", p_24281_);
      EntityPredicate.Composite entitypredicate$composite1 = EntityPredicate.Composite.m_36677_(p_24279_, "villager", p_24281_);
      return new CuredZombieVillagerTrigger.TriggerInstance(p_24280_, entitypredicate$composite, entitypredicate$composite1);
   }

   public void m_24274_(ServerPlayer p_24275_, Zombie p_24276_, Villager p_24277_) {
      LootContext lootcontext = EntityPredicate.m_36616_(p_24275_, p_24276_);
      LootContext lootcontext1 = EntityPredicate.m_36616_(p_24275_, p_24277_);
      this.m_66234_(p_24275_, (p_24285_) -> {
         return p_24285_.m_24299_(lootcontext, lootcontext1);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite f_24291_;
      private final EntityPredicate.Composite f_24292_;

      public TriggerInstance(EntityPredicate.Composite p_24294_, EntityPredicate.Composite p_24295_, EntityPredicate.Composite p_24296_) {
         super(CuredZombieVillagerTrigger.f_24270_, p_24294_);
         this.f_24291_ = p_24295_;
         this.f_24292_ = p_24296_;
      }

      public static CuredZombieVillagerTrigger.TriggerInstance m_24302_() {
         return new CuredZombieVillagerTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_);
      }

      public boolean m_24299_(LootContext p_24300_, LootContext p_24301_) {
         if (!this.f_24291_.m_36681_(p_24300_)) {
            return false;
         } else {
            return this.f_24292_.m_36681_(p_24301_);
         }
      }

      public JsonObject m_7683_(SerializationContext p_24298_) {
         JsonObject jsonobject = super.m_7683_(p_24298_);
         jsonobject.add("zombie", this.f_24291_.m_36675_(p_24298_));
         jsonobject.add("villager", this.f_24292_.m_36675_(p_24298_));
         return jsonobject;
      }
   }
}