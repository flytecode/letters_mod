package net.minecraft.advancements.critereon;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;

public class ChangeDimensionTrigger extends SimpleCriterionTrigger<ChangeDimensionTrigger.TriggerInstance> {
   static final ResourceLocation f_19753_ = new ResourceLocation("changed_dimension");

   public ResourceLocation m_7295_() {
      return f_19753_;
   }

   public ChangeDimensionTrigger.TriggerInstance m_7214_(JsonObject p_19762_, EntityPredicate.Composite p_19763_, DeserializationContext p_19764_) {
      ResourceKey<Level> resourcekey = p_19762_.has("from") ? ResourceKey.m_135785_(Registry.f_122819_, new ResourceLocation(GsonHelper.m_13906_(p_19762_, "from"))) : null;
      ResourceKey<Level> resourcekey1 = p_19762_.has("to") ? ResourceKey.m_135785_(Registry.f_122819_, new ResourceLocation(GsonHelper.m_13906_(p_19762_, "to"))) : null;
      return new ChangeDimensionTrigger.TriggerInstance(p_19763_, resourcekey, resourcekey1);
   }

   public void m_19757_(ServerPlayer p_19758_, ResourceKey<Level> p_19759_, ResourceKey<Level> p_19760_) {
      this.m_66234_(p_19758_, (p_19768_) -> {
         return p_19768_.m_19784_(p_19759_, p_19760_);
      });
   }

   public static class TriggerInstance extends AbstractCriterionTriggerInstance {
      @Nullable
      private final ResourceKey<Level> f_19774_;
      @Nullable
      private final ResourceKey<Level> f_19775_;

      public TriggerInstance(EntityPredicate.Composite p_19777_, @Nullable ResourceKey<Level> p_19778_, @Nullable ResourceKey<Level> p_19779_) {
         super(ChangeDimensionTrigger.f_19753_, p_19777_);
         this.f_19774_ = p_19778_;
         this.f_19775_ = p_19779_;
      }

      public static ChangeDimensionTrigger.TriggerInstance m_147565_() {
         return new ChangeDimensionTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, (ResourceKey<Level>)null, (ResourceKey<Level>)null);
      }

      public static ChangeDimensionTrigger.TriggerInstance m_147560_(ResourceKey<Level> p_147561_, ResourceKey<Level> p_147562_) {
         return new ChangeDimensionTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_147561_, p_147562_);
      }

      public static ChangeDimensionTrigger.TriggerInstance m_19782_(ResourceKey<Level> p_19783_) {
         return new ChangeDimensionTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, (ResourceKey<Level>)null, p_19783_);
      }

      public static ChangeDimensionTrigger.TriggerInstance m_147563_(ResourceKey<Level> p_147564_) {
         return new ChangeDimensionTrigger.TriggerInstance(EntityPredicate.Composite.f_36667_, p_147564_, (ResourceKey<Level>)null);
      }

      public boolean m_19784_(ResourceKey<Level> p_19785_, ResourceKey<Level> p_19786_) {
         if (this.f_19774_ != null && this.f_19774_ != p_19785_) {
            return false;
         } else {
            return this.f_19775_ == null || this.f_19775_ == p_19786_;
         }
      }

      public JsonObject m_7683_(SerializationContext p_19781_) {
         JsonObject jsonobject = super.m_7683_(p_19781_);
         if (this.f_19774_ != null) {
            jsonobject.addProperty("from", this.f_19774_.m_135782_().toString());
         }

         if (this.f_19775_ != null) {
            jsonobject.addProperty("to", this.f_19775_.m_135782_().toString());
         }

         return jsonobject;
      }
   }
}