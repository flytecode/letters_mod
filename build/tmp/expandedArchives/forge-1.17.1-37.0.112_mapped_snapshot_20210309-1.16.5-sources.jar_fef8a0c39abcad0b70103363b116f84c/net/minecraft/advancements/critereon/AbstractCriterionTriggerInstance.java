package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractCriterionTriggerInstance implements CriterionTriggerInstance {
   private final ResourceLocation f_16972_;
   private final EntityPredicate.Composite f_16973_;

   public AbstractCriterionTriggerInstance(ResourceLocation p_16975_, EntityPredicate.Composite p_16976_) {
      this.f_16972_ = p_16975_;
      this.f_16973_ = p_16976_;
   }

   public ResourceLocation m_7294_() {
      return this.f_16972_;
   }

   protected EntityPredicate.Composite m_16980_() {
      return this.f_16973_;
   }

   public JsonObject m_7683_(SerializationContext p_16979_) {
      JsonObject jsonobject = new JsonObject();
      jsonobject.add("player", this.f_16973_.m_36675_(p_16979_));
      return jsonobject;
   }

   public String toString() {
      return "AbstractCriterionInstance{criterion=" + this.f_16972_ + "}";
   }
}