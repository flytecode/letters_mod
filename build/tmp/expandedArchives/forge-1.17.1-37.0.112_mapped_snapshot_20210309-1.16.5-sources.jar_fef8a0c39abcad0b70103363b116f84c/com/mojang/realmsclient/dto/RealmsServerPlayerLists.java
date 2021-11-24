package com.mojang.realmsclient.dto;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
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
public class RealmsServerPlayerLists extends ValueObject {
   private static final Logger f_87593_ = LogManager.getLogger();
   public List<RealmsServerPlayerList> f_87592_;

   public static RealmsServerPlayerLists m_87596_(String p_87597_) {
      RealmsServerPlayerLists realmsserverplayerlists = new RealmsServerPlayerLists();
      realmsserverplayerlists.f_87592_ = Lists.newArrayList();

      try {
         JsonParser jsonparser = new JsonParser();
         JsonObject jsonobject = jsonparser.parse(p_87597_).getAsJsonObject();
         if (jsonobject.get("lists").isJsonArray()) {
            JsonArray jsonarray = jsonobject.get("lists").getAsJsonArray();
            Iterator<JsonElement> iterator = jsonarray.iterator();

            while(iterator.hasNext()) {
               realmsserverplayerlists.f_87592_.add(RealmsServerPlayerList.m_87590_(iterator.next().getAsJsonObject()));
            }
         }
      } catch (Exception exception) {
         f_87593_.error("Could not parse RealmsServerPlayerLists: {}", (Object)exception.getMessage());
      }

      return realmsserverplayerlists;
   }
}