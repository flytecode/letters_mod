package com.mojang.realmsclient.client;

import java.net.Proxy;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsClientConfig {
   private static Proxy f_87291_;

   public static Proxy m_87292_() {
      return f_87291_;
   }

   public static void m_87293_(Proxy p_87294_) {
      if (f_87291_ == null) {
         f_87291_ = p_87294_;
      }

   }
}