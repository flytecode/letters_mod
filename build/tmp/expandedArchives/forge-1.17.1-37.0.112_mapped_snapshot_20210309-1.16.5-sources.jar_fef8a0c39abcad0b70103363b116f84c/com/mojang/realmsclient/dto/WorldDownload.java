package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class WorldDownload extends ValueObject {
   private static final Logger f_87721_ = LogManager.getLogger();
   public String f_87718_;
   public String f_87719_;
   public String f_87720_;

   public static WorldDownload m_87724_(String p_87725_) {
      JsonParser jsonparser = new JsonParser();
      JsonObject jsonobject = jsonparser.parse(p_87725_).getAsJsonObject();
      WorldDownload worlddownload = new WorldDownload();

      try {
         worlddownload.f_87718_ = JsonUtils.m_90161_("downloadLink", jsonobject, "");
         worlddownload.f_87719_ = JsonUtils.m_90161_("resourcePackUrl", jsonobject, "");
         worlddownload.f_87720_ = JsonUtils.m_90161_("resourcePackHash", jsonobject, "");
      } catch (Exception exception) {
         f_87721_.error("Could not parse WorldDownload: {}", (Object)exception.getMessage());
      }

      return worlddownload;
   }
}