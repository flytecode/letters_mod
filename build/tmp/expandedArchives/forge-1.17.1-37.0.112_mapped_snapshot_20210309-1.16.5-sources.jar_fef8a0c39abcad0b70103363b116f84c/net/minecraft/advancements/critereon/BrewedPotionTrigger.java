package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.alchemy.Potion;

public class BrewedPotionTrigger extends SimpleCriterionTrigger<BrewedPotionTrigger.TriggerInstance> {
   static final ResourceLocation f_19116_ = new ResourceLocation("brewed_potion");

   public ResourceLocation m_7295_() {
      return f_19116_;
   }

   public BrewedPotionTrigger.TriggerInstance m_7214_(JsonObject p_19127_, EntityPredicate.Composite p_19128_, DeserializationContext p_19129_) {
      Potion potion = null;
      if (p_19127_.has("potion")) {
         ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_19127_, "potion"));
         potion = Registry.f_122828_.m_6612_(resourcelocation).orElseThrow(() -> {
            return new JsonSyntaxException("Unknown potion '" + resourcelocation + "'");
         });
      }

      return new BrewedPotionTrigger.TriggerInstance(p_19128_, potion);
   }

   public void m_19120_(ServerPlayer p_19121_, Potion p_19122_) {
      this.m_66234_(p_19121_, (p_19125_) -> {
         return p_19125_.m_19141_(p_19122_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      private final Potion f_19137_;

      public TriggerInstance(EntityPredicate.Composite p_19139_, @Nullable Potion p_19140_) {
         super(BrewedPotionTrigger.f_19116_, p_19139_);
         this.f_19137_ = p_19140_;
      }

      public static BrewedPotionTrigger.TriggerInstance m_19145_() {
         return new BrewedPotionTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, (Potion)null);
      }

      public boolean m_19141_(Potion p_19142_) {
         return this.f_19137_ == null || this.f_19137_ == p_19142_;
      }

      public JsonObject m_7683_(SerializationContext p_19144_) {
         JsonObject jsonobject = super.m_7683_(p_19144_);
         if (this.f_19137_ != null) {
            jsonobject.addProperty("potion", Registry.f_122828_.m_7981_(this.f_19137_).toString());
         }

         return jsonobject;
      }
   }
}