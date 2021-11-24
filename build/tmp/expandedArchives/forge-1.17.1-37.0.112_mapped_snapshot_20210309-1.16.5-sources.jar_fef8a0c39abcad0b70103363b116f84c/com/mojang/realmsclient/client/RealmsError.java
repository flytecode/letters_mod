package com.mojang.realmsclient.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsError {
   private static final Logger f_87295_ = LogManager.getLogger();
   private final String f_87296_;
   private final int f_87297_;

   private RealmsError(String p_87300_, int p_87301_) {
      this.f_87296_ = p_87300_;
      this.f_87297_ = p_87301_;
   }

   public static RealmsError m_87303_(String p_87304_) {
      try {
         JsonParser jsonparser = new JsonParser();
         JsonObject jsonobject = jsonparser.parse(p_87304_).getAsJsonObject();
         String s = JsonUtils.m_90161_("errorMsg", jsonobject, "");
         int i = JsonUtils.m_90153_("errorCode", jsonobject, -1);
         return new RealmsError(s, i);
      } catch (Exception exception) {
         f_87295_.error("Could not parse RealmsError: {}", (Object)exception.getMessage());
         f_87295_.error("The error was: {}", (Object)p_87304_);
         return new RealmsError("Failed to parse response from server", -1);
      }
   }

   public String m_87302_() {
      return this.f_87296_;
   }

   public int m_87305_() {
      return this.f_87297_;
   }
}