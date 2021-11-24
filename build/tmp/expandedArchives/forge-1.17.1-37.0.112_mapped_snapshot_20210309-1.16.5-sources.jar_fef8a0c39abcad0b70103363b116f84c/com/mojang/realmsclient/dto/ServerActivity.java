package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.mojang.realmsclient.util.JsonUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ServerActivity extends ValueObject {
   public String f_167312_;
   public long f_167313_;
   public long f_167314_;

   public static ServerActivity m_167316_(JsonObject p_167317_) {
      ServerActivity serveractivity = new ServerActivity();

      try {
         serveractivity.f_167312_ = JsonUtils.m_90161_("profileUuid", p_167317_, (String)null);
         serveractivity.f_167313_ = JsonUtils.m_90157_("joinTime", p_167317_, Long.MIN_VALUE);
         serveractivity.f_167314_ = JsonUtils.m_90157_("leaveTime", p_167317_, Long.MIN_VALUE);
      } catch (Exception exception) {
      }

      return serveractivity;
   }
}