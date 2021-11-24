package com.mojang.realmsclient.dto;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class Backup extends ValueObject {
   private static final Logger f_87394_ = LogManager.getLogger();
   public String f_87389_;
   public Date f_87390_;
   public long f_87391_;
   private boolean f_87395_;
   public Map<String, String> f_87392_ = Maps.newHashMap();
   public Map<String, String> f_87393_ = Maps.newHashMap();

   public static Backup m_87399_(JsonElement p_87400_) {
      JsonObject jsonobject = p_87400_.getAsJsonObject();
      Backup backup = new Backup();

      try {
         backup.f_87389_ = JsonUtils.m_90161_("backupId", jsonobject, "");
         backup.f_87390_ = JsonUtils.m_90150_("lastModifiedDate", jsonobject);
         backup.f_87391_ = JsonUtils.m_90157_("size", jsonobject, 0L);
         if (jsonobject.has("metadata")) {
            JsonObject jsonobject1 = jsonobject.getAsJsonObject("metadata");

            for(Entry<String, JsonElement> entry : jsonobject1.entrySet()) {
               if (!entry.getValue().isJsonNull()) {
                  backup.f_87392_.put(m_87401_(entry.getKey()), entry.getValue().getAsString());
               }
            }
         }
      } catch (Exception exception) {
         f_87394_.error("Could not parse Backup: {}", (Object)exception.getMessage());
      }

      return backup;
   }

   private static String m_87401_(String p_87402_) {
      String[] astring = p_87402_.split("_");
      StringBuilder stringbuilder = new StringBuilder();

      for(String s : astring) {
         if (s != null && s.length() >= 1) {
            if ("of".equals(s)) {
               stringbuilder.append(s).append(" ");
            } else {
               char c0 = Character.toUpperCase(s.charAt(0));
               stringbuilder.append(c0).append(s.substring(1)).append(" ");
            }
         }
      }

      return stringbuilder.toString();
   }

   public boolean m_87398_() {
      return this.f_87395_;
   }

   public void m_87403_(boolean p_87404_) {
      this.f_87395_ = p_87404_;
   }
}