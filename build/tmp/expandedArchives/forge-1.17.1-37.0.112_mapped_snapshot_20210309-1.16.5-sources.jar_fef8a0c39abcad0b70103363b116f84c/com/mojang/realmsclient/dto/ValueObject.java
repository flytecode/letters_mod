package com.mojang.realmsclient.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class ValueObject {
   public String toString() {
      StringBuilder stringbuilder = new StringBuilder("{");

      for(Field field : this.getClass().getFields()) {
         if (!m_87715_(field)) {
            try {
               stringbuilder.append(m_87713_(field)).append("=").append(field.get(this)).append(" ");
            } catch (IllegalAccessException illegalaccessexception) {
            }
         }
      }

      stringbuilder.deleteCharAt(stringbuilder.length() - 1);
      stringbuilder.append('}');
      return stringbuilder.toString();
   }

   private static String m_87713_(Field p_87714_) {
      SerializedName serializedname = p_87714_.getAnnotation(SerializedName.class);
      return serializedname != null ? serializedname.value() : p_87714_.getName();
   }

   private static boolean m_87715_(Field p_87716_) {
      return Modifier.isStatic(p_87716_.getModifiers());
   }
}