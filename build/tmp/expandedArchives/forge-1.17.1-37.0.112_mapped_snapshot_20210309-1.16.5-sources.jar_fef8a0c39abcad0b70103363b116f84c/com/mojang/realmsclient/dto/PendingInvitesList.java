package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Iterator;
import java.util.List;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class PendingInvitesList extends ValueObject {
   private static final Logger f_87433_ = LogManager.getLogger();
   public List<PendingInvite> f_87432_ = Lists.newArrayList();

   public static PendingInvitesList m_87436_(String p_87437_) {
      PendingInvitesList pendinginviteslist = new PendingInvitesList();

      try {
         JsonParser jsonparser = new JsonParser();
         JsonObject jsonobject = jsonparser.parse(p_87437_).getAsJsonObject();
         if (jsonobject.get("invites").isJsonArray()) {
            Iterator<JsonElement> iterator = jsonobject.get("invites").getAsJsonArray().iterator();

            while(iterator.hasNext()) {
               pendinginviteslist.f_87432_.add(PendingInvite.m_87430_(iterator.next().getAsJsonObject()));
            }
         }
      } catch (Exception exception) {
         f_87433_.error("Could not parse PendingInvitesList: {}", (Object)exception.getMessage());
      }

      return pendinginviteslist;
   }
}