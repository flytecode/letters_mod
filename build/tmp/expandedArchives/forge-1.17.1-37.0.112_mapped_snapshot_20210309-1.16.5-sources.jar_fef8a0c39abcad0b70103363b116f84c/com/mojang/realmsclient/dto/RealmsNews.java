package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsNews extends ValueObject {
   private static final Logger f_87468_ = LogManager.getLogger();
   public String f_87467_;

   public static RealmsNews m_87471_(String p_87472_) {
      RealmsNews realmsnews = new RealmsNews();

      try {
         JsonParser jsonparser = new JsonParser();
         JsonObject jsonobject = jsonparser.parse(p_87472_).getAsJsonObject();
         realmsnews.f_87467_ = JsonUtils.m_90161_("newsLink", jsonobject, (String)null);
      } catch (Exception exception) {
         f_87468_.error("Could not parse RealmsNews: {}", (Object)exception.getMessage());
      }

      return realmsnews;
   }
}