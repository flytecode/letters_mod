package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.storage.loot.LootContext;

public class BredAnimalsTrigger extends SimpleCriterionTrigger<BredAnimalsTrigger.TriggerInstance> {
   static final ResourceLocation f_18636_ = new ResourceLocation("bred_animals");

   public ResourceLocation m_7295_() {
      return f_18636_;
   }

   public BredAnimalsTrigger.TriggerInstance m_7214_(JsonObject p_18646_, EntityPredicate.Composite p_18647_, DeserializationContext p_18648_) {
      EntityPredicate.Composite entitypredicate$composite = EntityPredicate.Composite.m_36677_(p_18646_, "parent", p_18648_);
      EntityPredicate.Composite entitypredicate$composite1 = EntityPredicate.Composite.m_36677_(p_18646_, "partner", p_18648_);
      EntityPredicate.Composite entitypredicate$composite2 = EntityPredicate.Composite.m_36677_(p_18646_, "child", p_18648_);
      return new BredAnimalsTrigger.TriggerInstance(p_18647_, entitypredicate$composite, entitypredicate$composite1, entitypredicate$composite2);
   }

   public void m_147278_(ServerPlayer p_147279_, Animal p_147280_, Animal p_147281_, @Nullable AgeableMob p_147282_) {
      LootContext lootcontext = EntityPredicate.m_36616_(p_147279_, p_147280_);
      LootContext lootcontext1 = EntityPredicate.m_36616_(p_147279_, p_147281_);
      LootContext lootcontext2 = p_147282_ != null ? EntityPredicate.m_36616_(p_147279_, p_147282_) : null;
      this.m_66234_(p_147279_, (p_18653_) -> {
         return p_18653_.m_18675_(lootcontext, lootcontext1, lootcontext2);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final EntityPredicate.Composite f_18659_;
      private final EntityPredicate.Composite f_18660_;
      private final EntityPredicate.Composite f_18661_;

      public TriggerInstance(EntityPredicate.Composite p_18663_, EntityPredicate.Composite p_18664_, EntityPredicate.Composite p_18665_, EntityPredicate.Composite p_18666_) {
         super(BredAnimalsTrigger.f_18636_, p_18663_);
         this.f_18659_ = p_18664_;
         this.f_18660_ = p_18665_;
         this.f_18661_ = p_18666_;
      }

      public static BredAnimalsTrigger.TriggerInstance m_18679_() {
         return new BredAnimalsTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_);
      }

      public static BredAnimalsTrigger.TriggerInstance m_18667_(EntityPredicate.Builder p_18668_) {
         return new BredAnimalsTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_18668_.m_36662_()));
      }

      public static BredAnimalsTrigger.TriggerInstance m_18669_(EntityPredicate p_18670_, EntityPredicate p_18671_, EntityPredicate p_18672_) {
         return new BredAnimalsTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, EntityPredicate.Composite.m_36673_(p_18670_), EntityPredicate.Composite.m_36673_(p_18671_), EntityPredicate.Composite.m_36673_(p_18672_));
      }

      public boolean m_18675_(LootContext p_18676_, LootContext p_18677_, @Nullable LootContext p_18678_) {
         if (this.f_18661_ == EntityPredicate.Composite.f_36667_ || p_18678_ != null && this.f_18661_.m_36681_(p_18678_)) {
            return this.f_18659_.m_36681_(p_18676_) && this.f_18660_.m_36681_(p_18677_) || this.f_18659_.m_36681_(p_18677_) && this.f_18660_.m_36681_(p_18676_);
         } else {
            return false;
         }
      }

      public JsonObject m_7683_(SerializationContext p_18674_) {
         JsonObject jsonobject = super.m_7683_(p_18674_);
         jsonobject.add("parent", this.f_18659_.m_36675_(p_18674_));
         jsonobject.add("partner", this.f_18660_.m_36675_(p_18674_));
         jsonobject.add("child", this.f_18661_.m_36675_(p_18674_));
         return jsonobject;
      }
   }
}