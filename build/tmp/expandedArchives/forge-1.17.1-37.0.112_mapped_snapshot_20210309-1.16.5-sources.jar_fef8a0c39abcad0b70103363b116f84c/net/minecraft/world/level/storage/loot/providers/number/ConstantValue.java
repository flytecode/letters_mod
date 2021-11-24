package net.minecraft.world.level.storage.loot.providers.number;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.GsonAdapterFactory;
import net.minecraft.world.level.storage.loot.LootContext;

public final class ConstantValue implements NumberProvider {
   final float f_165688_;

   ConstantValue(float p_165690_) {
      this.f_165688_ = p_165690_;
   }

   public LootNumberProviderType m_142587_() {
      return NumberProviders.f_165731_;
   }

   public float m_142688_(LootContext p_165695_) {
      return this.f_165688_;
   }

   public static ConstantValue m_165692_(float p_165693_) {
      return new ConstantValue(p_165693_);
   }

   public boolean equals(Object p_165697_) {
      if (this == p_165697_) {
         return true;
      } else if (p_165697_ != null && this.getClass() == p_165697_.getClass()) {
         return Float.compare(((ConstantValue)p_165697_).f_165688_, this.f_165688_) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.f_165688_ != 0.0F ? Float.floatToIntBits(this.f_165688_) : 0;
   }

   public static class InlineSerializer implements GsonAdapterFactory.InlineSerializer<ConstantValue> {
      public JsonElement m_142413_(ConstantValue p_165704_, JsonSerializationContext p_165705_) {
         return new JsonPrimitive(p_165704_.f_165688_);
      }

      public ConstantValue m_142268_(JsonElement p_165710_, JsonDeserializationContext p_165711_) {
         return new ConstantValue(GsonHelper.m_13888_(p_165710_, "value"));
      }
   }

   public static class Serializer implements net.minecraft.world.level.storage.loot.Serializer<ConstantValue> {
      public void m_6170_(JsonObject p_165717_, ConstantValue p_165718_, JsonSerializationContext p_165719_) {
         p_165717_.addProperty("value", p_165718_.f_165688_);
      }

      public ConstantValue m_7561_(JsonObject p_165725_, JsonDeserializationContext p_165726_) {
         float f = GsonHelper.m_13915_(p_165725_, "value");
         return new ConstantValue(f);
      }
   }
}