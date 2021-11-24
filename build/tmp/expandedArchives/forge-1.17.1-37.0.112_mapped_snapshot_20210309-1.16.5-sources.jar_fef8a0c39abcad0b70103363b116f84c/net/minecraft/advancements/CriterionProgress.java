package net.minecraft.advancements;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.minecraft.network.FriendlyByteBuf;

public class CriterionProgress {
   private static final SimpleDateFormat f_12907_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
   private Date f_12908_;

   public boolean m_12911_() {
      return this.f_12908_ != null;
   }

   public void m_12916_() {
      this.f_12908_ = new Date();
   }

   public void m_12919_() {
      this.f_12908_ = null;
   }

   public Date m_12920_() {
      return this.f_12908_;
   }

   public String toString() {
      return "CriterionProgress{obtained=" + (this.f_12908_ == null ? "false" : this.f_12908_) + "}";
   }

   public void m_12914_(FriendlyByteBuf p_12915_) {
      p_12915_.writeBoolean(this.f_12908_ != null);
      if (this.f_12908_ != null) {
         p_12915_.m_130075_(this.f_12908_);
      }

   }

   public JsonElement m_12921_() {
      return (JsonElement)(this.f_12908_ != null ? new JsonPrimitive(f_12907_.format(this.f_12908_)) : JsonNull.INSTANCE);
   }

   public static CriterionProgress m_12917_(FriendlyByteBuf p_12918_) {
      CriterionProgress criterionprogress = new CriterionProgress();
      if (p_12918_.readBoolean()) {
         criterionprogress.f_12908_ = p_12918_.m_130282_();
      }

      return criterionprogress;
   }

   public static CriterionProgress m_12912_(String p_12913_) {
      CriterionProgress criterionprogress = new CriterionProgress();

      try {
         criterionprogress.f_12908_ = f_12907_.parse(p_12913_);
         return criterionprogress;
      } catch (ParseException parseexception) {
         throw new JsonSyntaxException("Invalid datetime: " + p_12913_, parseexception);
      }
   }
}