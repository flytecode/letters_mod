package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.List;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ServerActivityList extends ValueObject {
   public long f_167318_;
   public List<ServerActivity> f_167319_ = Lists.newArrayList();

   public static ServerActivityList m_167321_(String p_167322_) {
      ServerActivityList serveractivitylist = new ServerActivityList();
      JsonParser jsonparser = new JsonParser();

      try {
         JsonElement jsonelement = jsonparser.parse(p_167322_);
         JsonObject jsonobject = jsonelement.getAsJsonObject();
         serveractivitylist.f_167318_ = JsonUtils.m_90157_("periodInMillis", jsonobject, -1L);
         JsonElement jsonelement1 = jsonobject.get("playerActivityDto");
         if (jsonelement1 != null && jsonelement1.isJsonArray()) {
            for(JsonElement jsonelement2 : jsonelement1.getAsJsonArray()) {
               ServerActivity serveractivity = ServerActivity.m_167316_(jsonelement2.getAsJsonObject());
               serveractivitylist.f_167319_.add(serveractivity);
            }
         }
      } catch (Exception exception) {
      }

      return serveractivitylist;
   }
}