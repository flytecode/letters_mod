package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.storage.loot.LootContext;

public class TameAnimalTrigger extends SimpleCriterionTrigger<TameAnimalTrigger.TriggerInstance> {
   static final ResourceLocation f_68825_ = new ResourceLocation("tame_animal");

   public ResourceLocation m_7295_() {
      return f_68825_;
   }

   public TameAnimalTrigger.TriggerInstance m_7214_(JsonObject p_68833_, EntityPredicate.Composite p_68834_, DeserializationContext p_68835_) {
      EntityPredicate.Composite entitypredicate$composite = EntityPredicate.Composite.m_36677_(p_68833_, "entity", p_68835_);
      return new TameAnimalTrigger.TriggerInstance(p_68834_, entitypredicate$composite);
   }

   public void m_68829_(ServerPlayer p_68830_, Animal p_68831_) {
      LootContext lootcontext = EntityPredicate.m_36616_(p_68830_, p_68831_);
      this.m_66234_(p_68830_, (p_68838_) -> {
         return p_68838_.m_68852_(lootcontext);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite f_68844_;

      public TriggerInstance(EntityPredicate.Composite p_68846_, EntityPredicate.Composite p_68847_) {
         super(TameAnimalTrigger.f_68825_, p_68846_);
         this.f_68844_ = p_68847_;
      }

      public static TameAnimalTrigger.TriggerInstance m_68854_() {
         return new TameAnimalTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_);
      }

      public static TameAnimalTrigger.TriggerInstance m_68848_(EntityPredicate p_68849_) {
         return new TameAnimalTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_68849_));
      }

      public boolean m_68852_(LootContext p_68853_) {
         return this.f_68844_.m_36681_(p_68853_);
      }

      public JsonObject m_7683_(SerializationContext p_68851_) {
         JsonObject jsonobject = super.m_7683_(p_68851_);
         jsonobject.add("entity", this.f_68844_.m_36675_(p_68851_));
         return jsonobject;
      }
   }
}