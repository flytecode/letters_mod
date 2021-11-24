package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.List;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsServerPlayerList extends ValueObject {
   private static final Logger f_87584_ = LogManager.getLogger();
   private static final JsonParser f_87585_ = new JsonParser();
   public long f_87582_;
   public List<String> f_87583_;

   public static RealmsServerPlayerList m_87590_(JsonObject p_87591_) {
      RealmsServerPlayerList realmsserverplayerlist = new RealmsServerPlayerList();

      try {
         realmsserverplayerlist.f_87582_ = JsonUtils.m_90157_("serverId", p_87591_, -1L);
         String s = JsonUtils.m_90161_("playerList", p_87591_, (String)null);
         if (s != null) {
            JsonElement jsonelement = f_87585_.parse(s);
            if (jsonelement.isJsonArray()) {
               realmsserverplayerlist.f_87583_ = m_87588_(jsonelement.getAsJsonArray());
            } else {
               realmsserverplayerlist.f_87583_ = Lists.newArrayList();
            }
         } else {
            realmsserverplayerlist.f_87583_ = Lists.newArrayList();
         }
      } catch (Exception exception) {
         f_87584_.error("Could not parse RealmsServerPlayerList: {}", (Object)exception.getMessage());
      }

      return realmsserverplayerlist;
   }

   private static List<String> m_87588_(JsonArray p_87589_) {
      List<String> list = Lists.newArrayList();

      for(JsonElement jsonelement : p_87589_) {
         try {
            list.add(jsonelement.getAsString());
         } catch (Exception exception) {
         }
      }

      return list;
   }
}