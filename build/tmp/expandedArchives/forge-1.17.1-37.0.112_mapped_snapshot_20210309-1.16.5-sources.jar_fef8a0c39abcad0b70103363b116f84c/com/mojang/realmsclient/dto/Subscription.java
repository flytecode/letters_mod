package com.mojang.realmsclient.dto;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class Subscription extends ValueObject {
   private static final Logger f_87669_ = LogManager.getLogger();
   public long f_87666_;
   public int f_87667_;
   public Subscription.SubscriptionType f_87668_ = Subscription.SubscriptionType.NORMAL;

   public static Subscription m_87672_(String p_87673_) {
      Subscription subscription = new Subscription();

      try {
         JsonParser jsonparser = new JsonParser();
         JsonObject jsonobject = jsonparser.parse(p_87673_).getAsJsonObject();
         subscription.f_87666_ = JsonUtils.m_90157_("startDate", jsonobject, 0L);
         subscription.f_87667_ = JsonUtils.m_90153_("daysLeft", jsonobject, 0);
         subscription.f_87668_ = m_87674_(JsonUtils.m_90161_("subscriptionType", jsonobject, Subscription.SubscriptionType.NORMAL.name()));
      } catch (Exception exception) {
         f_87669_.error("Could not parse Subscription: {}", (Object)exception.getMessage());
      }

      return subscription;
   }

   private static Subscription.SubscriptionType m_87674_(String p_87675_) {
      try {
         return Subscription.SubscriptionType.valueOf(p_87675_);
      } catch (Exception exception) {
         return Subscription.SubscriptionType.NORMAL;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum SubscriptionType {
      NORMAL,
      RECURRING;
   }
}