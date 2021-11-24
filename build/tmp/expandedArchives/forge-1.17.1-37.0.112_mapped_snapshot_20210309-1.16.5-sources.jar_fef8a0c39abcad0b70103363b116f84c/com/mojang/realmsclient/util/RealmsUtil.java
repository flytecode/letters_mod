package com.mojang.realmsclient.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.util.UUIDTypeAdapter;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RealmsUtil {
   private static final YggdrasilAuthenticationService f_90215_ = new YggdrasilAuthenticationService(Minecraft.m_91087_().m_91096_());
   static final MinecraftSessionService f_90216_ = f_90215_.createMinecraftSessionService();
   public static LoadingCache<String, GameProfile> f_90214_ = CacheBuilder.newBuilder().expireAfterWrite(60L, TimeUnit.MINUTES).build(new CacheLoader<String, GameProfile>() {
      public GameProfile load(String p_90229_) throws Exception {
         GameProfile gameprofile = RealmsUtil.f_90216_.fillProfileProperties(new GameProfile(UUIDTypeAdapter.fromString(p_90229_), (String)null), false);
         if (gameprofile == null) {
            throw new Exception("Couldn't get profile");
         } else {
            return gameprofile;
         }
      }
   });
   private static final int f_167619_ = 60;
   private static final int f_167620_ = 3600;
   private static final int f_167621_ = 86400;

   public static String m_90221_(String p_90222_) throws Exception {
      GameProfile gameprofile = f_90214_.get(p_90222_);
      return gameprofile.getName();
   }

   public static Map<Type, MinecraftProfileTexture> m_90225_(String p_90226_) {
      try {
         GameProfile gameprofile = f_90214_.get(p_90226_);
         return f_90216_.getTextures(gameprofile, false);
      } catch (Exception exception) {
         return Maps.newHashMap();
      }
   }

   public static String m_90219_(long p_90220_) {
      if (p_90220_ < 0L) {
         return "right now";
      } else {
         long i = p_90220_ / 1000L;
         if (i < 60L) {
            return (i == 1L ? "1 second" : i + " seconds") + " ago";
         } else if (i < 3600L) {
            long l = i / 60L;
            return (l == 1L ? "1 minute" : l + " minutes") + " ago";
         } else if (i < 86400L) {
            long k = i / 3600L;
            return (k == 1L ? "1 hour" : k + " hours") + " ago";
         } else {
            long j = i / 86400L;
            return (j == 1L ? "1 day" : j + " days") + " ago";
         }
      }
   }

   public static String m_90223_(Date p_90224_) {
      return m_90219_(System.currentTimeMillis() - p_90224_.getTime());
   }
}