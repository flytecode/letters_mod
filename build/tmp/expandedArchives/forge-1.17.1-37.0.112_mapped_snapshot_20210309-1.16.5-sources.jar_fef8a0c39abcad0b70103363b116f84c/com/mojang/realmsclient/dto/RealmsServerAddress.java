package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsServerAddress extends ValueObject {
   private static final Logger f_87568_ = LogManager.getLogger();
   public String f_87565_;
   public String f_87566_;
   public String f_87567_;

   public static RealmsServerAddress m_87571_(String p_87572_) {
      JsonParser jsonparser = new JsonParser();
      RealmsServerAddress realmsserveraddress = new RealmsServerAddress();

      try {
         JsonObject jsonobject = jsonparser.parse(p_87572_).getAsJsonObject();
         realmsserveraddress.f_87565_ = JsonUtils.m_90161_("address", jsonobject, (String)null);
         realmsserveraddress.f_87566_ = JsonUtils.m_90161_("resourcePackUrl", jsonobject, (String)null);
         realmsserveraddress.f_87567_ = JsonUtils.m_90161_("resourcePackHash", jsonobject, (String)null);
      } catch (Exception exception) {
         f_87568_.error("Could not parse RealmsServerAddress: {}", (Object)exception.getMessage());
      }

      return realmsserveraddress;
   }
}