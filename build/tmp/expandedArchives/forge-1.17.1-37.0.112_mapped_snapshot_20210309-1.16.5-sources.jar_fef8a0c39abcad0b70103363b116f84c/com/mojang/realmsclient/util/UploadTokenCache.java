package com.mojang.realmsclient.util;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class UploadTokenCache {
   private static final Long2ObjectMap<String> f_90290_ = new Long2ObjectOpenHashMap<>();

   public static String m_90292_(long p_90293_) {
      return f_90290_.get(p_90293_);
   }

   public static void m_90297_(long p_90298_) {
      f_90290_.remove(p_90298_);
   }

   public static void m_90294_(long p_90295_, String p_90296_) {
      f_90290_.put(p_90295_, p_90296_);
   }
}