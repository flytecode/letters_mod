package net.minecraft.util.datafix.schemas;

import com.mojang.datafixers.DSL.TypeReference;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.types.templates.Const.PrimitiveType;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.minecraft.resources.ResourceLocation;

public class NamespacedSchema extends Schema {
   public static final PrimitiveCodec<String> f_17304_ = new PrimitiveCodec<String>() {
      public <T> DataResult<String> read(DynamicOps<T> p_17321_, T p_17322_) {
         return p_17321_.getStringValue(p_17322_).map(NamespacedSchema::m_17311_);
      }

      public <T> T write(DynamicOps<T> p_17318_, String p_17319_) {
         return p_17318_.createString(p_17319_);
      }

      public String toString() {
         return "NamespacedString";
      }
   };
   private static final Type<String> f_17305_ = new PrimitiveType<>(f_17304_);

   public NamespacedSchema(int p_17308_, Schema p_17309_) {
      super(p_17308_, p_17309_);
   }

   public static String m_17311_(String p_17312_) {
      ResourceLocation resourcelocation = ResourceLocation.m_135820_(p_17312_);
      return resourcelocation != null ? resourcelocation.toString() : p_17312_;
   }

   public static Type<String> m_17310_() {
      return f_17305_;
   }

   public Type<?> getChoiceType(TypeReference p_17314_, String p_17315_) {
      return super.getChoiceType(p_17314_, m_17311_(p_17315_));
   }
}