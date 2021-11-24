package net.minecraft.advancements;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public class AdvancementProgress implements Comparable<AdvancementProgress> {
   final Map<String, CriterionProgress> f_8190_;
   private String[][] f_8191_ = new String[0][];

   private AdvancementProgress(Map<String, CriterionProgress> p_144358_) {
      this.f_8190_ = p_144358_;
   }

   public AdvancementProgress() {
      this.f_8190_ = Maps.newHashMap();
   }

   public void m_8198_(Map<String, Criterion> p_8199_, String[][] p_8200_) {
      Set<String> set = p_8199_.keySet();
      this.f_8190_.entrySet().removeIf((p_8203_) -> {
         return !set.contains(p_8203_.getKey());
      });

      for(String s : set) {
         if (!this.f_8190_.containsKey(s)) {
            this.f_8190_.put(s, new CriterionProgress());
         }
      }

      this.f_8191_ = p_8200_;
   }

   public boolean m_8193_() {
      if (this.f_8191_.length == 0) {
         return false;
      } else {
         for(String[] astring : this.f_8191_) {
            boolean flag = false;

            for(String s : astring) {
               CriterionProgress criterionprogress = this.m_8214_(s);
               if (criterionprogress != null && criterionprogress.m_12911_()) {
                  flag = true;
                  break;
               }
            }

            if (!flag) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean m_8206_() {
      for(CriterionProgress criterionprogress : this.f_8190_.values()) {
         if (criterionprogress.m_12911_()) {
            return true;
         }
      }

      return false;
   }

   public boolean m_8196_(String p_8197_) {
      CriterionProgress criterionprogress = this.f_8190_.get(p_8197_);
      if (criterionprogress != null && !criterionprogress.m_12911_()) {
         criterionprogress.m_12916_();
         return true;
      } else {
         return false;
      }
   }

   public boolean m_8209_(String p_8210_) {
      CriterionProgress criterionprogress = this.f_8190_.get(p_8210_);
      if (criterionprogress != null && criterionprogress.m_12911_()) {
         criterionprogress.m_12919_();
         return true;
      } else {
         return false;
      }
   }

   public String toString() {
      return "AdvancementProgress{criteria=" + this.f_8190_ + ", requirements=" + Arrays.deepToString(this.f_8191_) + "}";
   }

   public void m_8204_(FriendlyByteBuf p_8205_) {
      p_8205_.m_178355_(this.f_8190_, FriendlyByteBuf::m_130070_, (p_144360_, p_144361_) -> {
         p_144361_.m_12914_(p_144360_);
      });
   }

   public static AdvancementProgress m_8211_(FriendlyByteBuf p_8212_) {
      Map<String, CriterionProgress> map = p_8212_.m_178368_(FriendlyByteBuf::m_130277_, CriterionProgress::m_12917_);
      return new AdvancementProgress(map);
   }

   @Nullable
   public CriterionProgress m_8214_(String p_8215_) {
      return this.f_8190_.get(p_8215_);
   }

   public float m_8213_() {
      if (this.f_8190_.isEmpty()) {
         return 0.0F;
      } else {
         float f = (float)this.f_8191_.length;
         float f1 = (float)this.m_8222_();
         return f1 / f;
      }
   }

   @Nullable
   public String m_8218_() {
      if (this.f_8190_.isEmpty()) {
         return null;
      } else {
         int i = this.f_8191_.length;
         if (i <= 1) {
            return null;
         } else {
            int j = this.m_8222_();
            return j + "/" + i;
         }
      }
   }

   private int m_8222_() {
      int i = 0;

      for(String[] astring : this.f_8191_) {
         boolean flag = false;

         for(String s : astring) {
            CriterionProgress criterionprogress = this.m_8214_(s);
            if (criterionprogress != null && criterionprogress.m_12911_()) {
               flag = true;
               break;
            }
         }

         if (flag) {
            ++i;
         }
      }

      return i;
   }

   public Iterable<String> m_8219_() {
      List<String> list = Lists.newArrayList();

      for(Entry<String, CriterionProgress> entry : this.f_8190_.entrySet()) {
         if (!entry.getValue().m_12911_()) {
            list.add(entry.getKey());
         }
      }

      return list;
   }

   public Iterable<String> m_8220_() {
      List<String> list = Lists.newArrayList();

      for(Entry<String, CriterionProgress> entry : this.f_8190_.entrySet()) {
         if (entry.getValue().m_12911_()) {
            list.add(entry.getKey());
         }
      }

      return list;
   }

   @Nullable
   public Date m_8221_() {
      Date date = null;

      for(CriterionProgress criterionprogress : this.f_8190_.values()) {
         if (criterionprogress.m_12911_() && (date == null || criterionprogress.m_12920_().before(date))) {
            date = criterionprogress.m_12920_();
         }
      }

      return date;
   }

   public int compareTo(AdvancementProgress p_8195_) {
      Date date = this.m_8221_();
      Date date1 = p_8195_.m_8221_();
      if (date == null && date1 != null) {
         return 1;
      } else if (date != null && date1 == null) {
         return -1;
      } else {
         return date == null && date1 == null ? 0 : date.compareTo(date1);
      }
   }

   public static class Serializer implements JsonDeserializer<AdvancementProgress>, JsonSerializer<AdvancementProgress> {
      public JsonElement serialize(AdvancementProgress p_8226_, Type p_8227_, JsonSerializationContext p_8228_) {
         JsonObject jsonobject = new JsonObject();
         JsonObject jsonobject1 = new JsonObject();

         for(Entry<String, CriterionProgress> entry : p_8226_.f_8190_.entrySet()) {
            CriterionProgress criterionprogress = entry.getValue();
            if (criterionprogress.m_12911_()) {
               jsonobject1.add(entry.getKey(), criterionprogress.m_12921_());
            }
         }

         if (!jsonobject1.entrySet().isEmpty()) {
            jsonobject.add("criteria", jsonobject1);
         }

         jsonobject.addProperty("done", p_8226_.m_8193_());
         return jsonobject;
      }

      public AdvancementProgress deserialize(JsonElement p_8230_, Type p_8231_, JsonDeserializationContext p_8232_) throws JsonParseException {
         JsonObject jsonobject = GsonHelper.m_13918_(p_8230_, "advancement");
         JsonObject jsonobject1 = GsonHelper.m_13841_(jsonobject, "criteria", new JsonObject());
         AdvancementProgress advancementprogress = new AdvancementProgress();

         for(Entry<String, JsonElement> entry : jsonobject1.entrySet()) {
            String s = entry.getKey();
            advancementprogress.f_8190_.put(s, CriterionProgress.m_12912_(GsonHelper.m_13805_(entry.getValue(), s)));
         }

         return advancementprogress;
      }
   }
}