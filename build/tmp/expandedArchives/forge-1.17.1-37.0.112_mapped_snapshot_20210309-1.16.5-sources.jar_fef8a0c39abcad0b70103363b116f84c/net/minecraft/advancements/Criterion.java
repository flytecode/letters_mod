package net.minecraft.advancements;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

public class Criterion {
   private final CriterionTriggerInstance f_11412_;

   public Criterion(CriterionTriggerInstance p_11415_) {
      this.f_11412_ = p_11415_;
   }

   public Criterion() {
      this.f_11412_ = null;
   }

   public void m_11423_(FriendlyByteBuf p_11424_) {
   }

   public static Criterion m_11417_(JsonObject p_11418_, DeserializationContext p_11419_) {
      ResourceLocation resourcelocation = new ResourceLocation(GsonHelper.m_13906_(p_11418_, "trigger"));
      CriterionTrigger<?> criteriontrigger = CriteriaTriggers.m_10597_(resourcelocation);
      if (criteriontrigger == null) {
         throw new JsonSyntaxException("Invalid criterion trigger: " + resourcelocation);
      } else {
         CriterionTriggerInstance criteriontriggerinstance = criteriontrigger.m_5868_(GsonHelper.m_13841_(p_11418_, "conditions", new JsonObject()), p_11419_);
         return new Criterion(criteriontriggerinstance);
      }
   }

   public static Criterion m_11429_(FriendlyByteBuf p_11430_) {
      return new Criterion();
   }

   public static Map<String, Criterion> m_11426_(JsonObject p_11427_, DeserializationContext p_11428_) {
      Map<String, Criterion> map = Maps.newHashMap();

      for(Entry<String, JsonElement> entry : p_11427_.entrySet()) {
         map.put(entry.getKey(), m_11417_(GsonHelper.m_13918_(entry.getValue(), "criterion"), p_11428_));
      }

      return map;
   }

   public static Map<String, Criterion> m_11431_(FriendlyByteBuf p_11432_) {
      return p_11432_.m_178368_(FriendlyByteBuf::m_130277_, Criterion::m_11429_);
   }

   public static void m_11420_(Map<String, Criterion> p_11421_, FriendlyByteBuf p_11422_) {
      p_11422_.m_178355_(p_11421_, FriendlyByteBuf::m_130070_, (p_145258_, p_145259_) -> {
         p_145259_.m_11423_(p_145258_);
      });
   }

   @Nullable
   public CriterionTriggerInstance m_11416_() {
      return this.f_11412_;
   }

   public JsonElement m_11425_() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("trigger", this.f_11412_.m_7294_().toString());
      JsonObject jsonobject1 = this.f_11412_.m_7683_(SerializationContext.f_64768_);
      if (jsonobject1.size() != 0) {
         jsonobject.add("conditions", jsonobject1);
      }

      return jsonobject;
   }
}