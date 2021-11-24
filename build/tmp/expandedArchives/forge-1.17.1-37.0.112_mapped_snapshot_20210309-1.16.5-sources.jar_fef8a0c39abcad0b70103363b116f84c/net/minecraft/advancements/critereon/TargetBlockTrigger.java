package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.Vec3;

public class TargetBlockTrigger extends SimpleCriterionTrigger<TargetBlockTrigger.TriggerInstance> {
   static final ResourceLocation f_70207_ = new ResourceLocation("target_hit");

   public ResourceLocation m_7295_() {
      return f_70207_;
   }

   public TargetBlockTrigger.TriggerInstance m_7214_(JsonObject p_70217_, EntityPredicate.Composite p_70218_, DeserializationContext p_70219_) {
      MinMaxBounds.Ints minmaxbounds$ints = MinMaxBounds.Ints.m_55373_(p_70217_.get("signal_strength"));
      EntityPredicate.Composite entitypredicate$composite = EntityPredicate.Composite.m_36677_(p_70217_, "projectile", p_70219_);
      return new TargetBlockTrigger.TriggerInstance(p_70218_, minmaxbounds$ints, entitypredicate$composite);
   }

   public void m_70211_(ServerPlayer p_70212_, Entity p_70213_, Vec3 p_70214_, int p_70215_) {
      LootContext lootcontext = EntityPredicate.m_36616_(p_70212_, p_70213_);
      this.m_66234_(p_70212_, (p_70224_) -> {
         return p_70224_.m_70241_(lootcontext, p_70214_, p_70215_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final MinMaxBounds.Ints f_70230_;
      private final EntityPredicate.Composite f_70231_;

      public TriggerInstance(EntityPredicate.Composite p_70233_, MinMaxBounds.Ints p_70234_, EntityPredicate.Composite p_70235_) {
         super(TargetBlockTrigger.f_70207_, p_70233_);
         this.f_70230_ = p_70234_;
         this.f_70231_ = p_70235_;
      }

      public static TargetBlockTrigger.TriggerInstance m_70236_(MinMaxBounds.Ints p_70237_, EntityPredicate.Composite p_70238_) {
         return new TargetBlockTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_70237_, p_70238_);
      }

      public JsonObject m_7683_(SerializationContext p_70240_) {
         JsonObject jsonobject = super.m_7683_(p_70240_);
         jsonobject.add("signal_strength", this.f_70230_.m_55328_());
         jsonobject.add("projectile", this.f_70231_.m_36675_(p_70240_));
         return jsonobject;
      }

      public boolean m_70241_(LootContext p_70242_, Vec3 p_70243_, int p_70244_) {
         if (!this.f_70230_.m_55390_(p_70244_)) {
            return false;
         } else {
            return this.f_70231_.m_36681_(p_70242_);
         }
      }
   }
}