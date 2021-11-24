package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;

public class PlayerHurtEntityTrigger extends SimpleCriterionTrigger<PlayerHurtEntityTrigger.TriggerInstance> {
   static final ResourceLocation f_60108_ = new ResourceLocation("player_hurt_entity");

   public ResourceLocation m_7295_() {
      return f_60108_;
   }

   public PlayerHurtEntityTrigger.TriggerInstance m_7214_(JsonObject p_60128_, EntityPredicate.Composite p_60129_, DeserializationContext p_60130_) {
      DamagePredicate damagepredicate = DamagePredicate.m_24923_(p_60128_.get("damage"));
      EntityPredicate.Composite entitypredicate$composite = EntityPredicate.Composite.m_36677_(p_60128_, "entity", p_60130_);
      return new PlayerHurtEntityTrigger.TriggerInstance(p_60129_, damagepredicate, entitypredicate$composite);
   }

   public void m_60112_(ServerPlayer p_60113_, Entity p_60114_, DamageSource p_60115_, float p_60116_, float p_60117_, boolean p_60118_) {
      LootContext lootcontext = EntityPredicate.m_36616_(p_60113_, p_60114_);
      this.m_66234_(p_60113_, (p_60126_) -> {
         return p_60126_.m_60142_(p_60113_, lootcontext, p_60115_, p_60116_, p_60117_, p_60118_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final DamagePredicate f_60136_;
      private final EntityPredicate.Composite f_60137_;

      public TriggerInstance(EntityPredicate.Composite p_60139_, DamagePredicate p_60140_, EntityPredicate.Composite p_60141_) {
         super(PlayerHurtEntityTrigger.f_60108_, p_60139_);
         this.f_60136_ = p_60140_;
         this.f_60137_ = p_60141_;
      }

      public static PlayerHurtEntityTrigger.TriggerInstance m_156068_() {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, DamagePredicate.f_24902_, EntityPredicate.Composite.f_36667_);
      }

      public static PlayerHurtEntityTrigger.TriggerInstance m_156061_(DamagePredicate p_156062_) {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_156062_, EntityPredicate.Composite.f_36667_);
      }

      public static PlayerHurtEntityTrigger.TriggerInstance m_60149_(DamagePredicate.Builder p_60150_) {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_60150_.m_24936_(), EntityPredicate.Composite.f_36667_);
      }

      public static PlayerHurtEntityTrigger.TriggerInstance m_156066_(EntityPredicate p_156067_) {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, DamagePredicate.f_24902_, EntityPredicate.Composite.m_36673_(p_156067_));
      }

      public static PlayerHurtEntityTrigger.TriggerInstance m_156063_(DamagePredicate p_156064_, EntityPredicate p_156065_) {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_156064_, EntityPredicate.Composite.m_36673_(p_156065_));
      }

      public static PlayerHurtEntityTrigger.TriggerInstance m_156058_(DamagePredicate.Builder p_156059_, EntityPredicate p_156060_) {
         return new PlayerHurtEntityTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_156059_.m_24936_(), EntityPredicate.Composite.m_36673_(p_156060_));
      }

      public boolean m_60142_(ServerPlayer p_60143_, LootContext p_60144_, DamageSource p_60145_, float p_60146_, float p_60147_, boolean p_60148_) {
         if (!this.f_60136_.m_24917_(p_60143_, p_60145_, p_60146_, p_60147_, p_60148_)) {
            return false;
         } else {
            return this.f_60137_.m_36681_(p_60144_);
         }
      }

      public JsonObject m_7683_(SerializationContext p_60152_) {
         JsonObject jsonobject = super.m_7683_(p_60152_);
         jsonobject.add("damage", this.f_60136_.m_24916_());
         jsonobject.add("entity", this.f_60137_.m_36675_(p_60152_));
         return jsonobject;
      }
   }
}