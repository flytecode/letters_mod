package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

public class EntityHurtPlayerTrigger extends SimpleCriterionTrigger<EntityHurtPlayerTrigger.TriggerInstance> {
   static final ResourceLocation f_35170_ = new ResourceLocation("entity_hurt_player");

   public ResourceLocation m_7295_() {
      return f_35170_;
   }

   public EntityHurtPlayerTrigger.TriggerInstance m_7214_(JsonObject p_35188_, EntityPredicate.Composite p_35189_, DeserializationContext p_35190_) {
      DamagePredicate damagepredicate = DamagePredicate.m_24923_(p_35188_.get("damage"));
      return new EntityHurtPlayerTrigger.TriggerInstance(p_35189_, damagepredicate);
   }

   public void m_35174_(ServerPlayer p_35175_, DamageSource p_35176_, float p_35177_, float p_35178_, boolean p_35179_) {
      this.m_66234_(p_35175_, (p_35186_) -> {
         return p_35186_.m_35200_(p_35175_, p_35176_, p_35177_, p_35178_, p_35179_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final DamagePredicate f_35196_;

      public TriggerInstance(EntityPredicate.Composite p_35198_, DamagePredicate p_35199_) {
         super(EntityHurtPlayerTrigger.f_35170_, p_35198_);
         this.f_35196_ = p_35199_;
      }

      public static EntityHurtPlayerTrigger.TriggerInstance m_150189_() {
         return new EntityHurtPlayerTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, DamagePredicate.f_24902_);
      }

      public static EntityHurtPlayerTrigger.TriggerInstance m_150187_(DamagePredicate p_150188_) {
         return new EntityHurtPlayerTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_150188_);
      }

      public static EntityHurtPlayerTrigger.TriggerInstance m_35206_(DamagePredicate.Builder p_35207_) {
         return new EntityHurtPlayerTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_35207_.m_24936_());
      }

      public boolean m_35200_(ServerPlayer p_35201_, DamageSource p_35202_, float p_35203_, float p_35204_, boolean p_35205_) {
         return this.f_35196_.m_24917_(p_35201_, p_35202_, p_35203_, p_35204_, p_35205_);
      }

      public JsonObject m_7683_(SerializationContext p_35209_) {
         JsonObject jsonobject = super.m_7683_(p_35209_);
         jsonobject.add("damage", this.f_35196_.m_24916_());
         return jsonobject;
      }
   }
}