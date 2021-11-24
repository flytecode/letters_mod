package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import java.util.Date;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class PendingInvite extends ValueObject {
   private static final Logger f_87427_ = LogManager.getLogger();
   public String f_87422_;
   public String f_87423_;
   public String f_87424_;
   public String f_87425_;
   public Date f_87426_;

   public static PendingInvite m_87430_(JsonObject p_87431_) {
      PendingInvite pendinginvite = new PendingInvite();

      try {
         pendinginvite.f_87422_ = JsonUtils.m_90161_("invitationId", p_87431_, "");
         pendinginvite.f_87423_ = JsonUtils.m_90161_("worldName", p_87431_, "");
         pendinginvite.f_87424_ = JsonUtils.m_90161_("worldOwnerName", p_87431_, "");
         pendinginvite.f_87425_ = JsonUtils.m_90161_("worldOwnerUuid", p_87431_, "");
         pendinginvite.f_87426_ = JsonUtils.m_90150_("date", p_87431_);
      } catch (Exception exception) {
         f_87427_.error("Could not parse PendingInvite: {}", (Object)exception.getMessage());
      }

      return pendinginvite;
   }
}