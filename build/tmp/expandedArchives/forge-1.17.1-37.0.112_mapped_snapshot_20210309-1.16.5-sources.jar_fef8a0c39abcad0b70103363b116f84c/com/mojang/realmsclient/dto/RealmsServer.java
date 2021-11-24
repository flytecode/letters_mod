package com.mojang.realmsclient.dto;

import com.google.common.base.Joiner;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import com.mojang.realmsclient.util.RealmsUtil;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsServer extends ValueObject {
   private static final Logger f_87491_ = LogManager.getLogger();
   public long f_87473_;
   public String f_87474_;
   public String f_87475_;
   public String f_87476_;
   public RealmsServer.State f_87477_;
   public String f_87478_;
   public String f_87479_;
   public List<PlayerInfo> f_87480_;
   public Map<Integer, RealmsWorldOptions> f_87481_;
   public boolean f_87482_;
   public boolean f_87483_;
   public int f_87484_;
   public RealmsServer.WorldType f_87485_;
   public int f_87486_;
   public String f_87487_;
   public int f_87488_;
   public String f_87489_;
   public RealmsServerPing f_87490_ = new RealmsServerPing();

   public String m_87494_() {
      return this.f_87476_;
   }

   public String m_87512_() {
      return this.f_87475_;
   }

   public String m_87517_() {
      return this.f_87487_;
   }

   public void m_87508_(String p_87509_) {
      this.f_87475_ = p_87509_;
   }

   public void m_87515_(String p_87516_) {
      this.f_87476_ = p_87516_;
   }

   public void m_87506_(RealmsServerPlayerList p_87507_) {
      List<String> list = Lists.newArrayList();
      int i = 0;

      for(String s : p_87507_.f_87583_) {
         if (!s.equals(Minecraft.m_91087_().m_91094_().m_92545_())) {
            String s1 = "";

            try {
               s1 = RealmsUtil.m_90221_(s);
            } catch (Exception exception) {
               f_87491_.error("Could not get name for {}", s, exception);
               continue;
            }

            list.add(s1);
            ++i;
         }
      }

      this.f_87490_.f_87579_ = String.valueOf(i);
      this.f_87490_.f_87580_ = Joiner.on('\n').join(list);
   }

   public static RealmsServer m_87499_(JsonObject p_87500_) {
      RealmsServer realmsserver = new RealmsServer();

      try {
         realmsserver.f_87473_ = JsonUtils.m_90157_("id", p_87500_, -1L);
         realmsserver.f_87474_ = JsonUtils.m_90161_("remoteSubscriptionId", p_87500_, (String)null);
         realmsserver.f_87475_ = JsonUtils.m_90161_("name", p_87500_, (String)null);
         realmsserver.f_87476_ = JsonUtils.m_90161_("motd", p_87500_, (String)null);
         realmsserver.f_87477_ = m_87525_(JsonUtils.m_90161_("state", p_87500_, RealmsServer.State.CLOSED.name()));
         realmsserver.f_87478_ = JsonUtils.m_90161_("owner", p_87500_, (String)null);
         if (p_87500_.get("players") != null && p_87500_.get("players").isJsonArray()) {
            realmsserver.f_87480_ = m_87497_(p_87500_.get("players").getAsJsonArray());
            m_87504_(realmsserver);
         } else {
            realmsserver.f_87480_ = Lists.newArrayList();
         }

         realmsserver.f_87484_ = JsonUtils.m_90153_("daysLeft", p_87500_, 0);
         realmsserver.f_87482_ = JsonUtils.m_90165_("expired", p_87500_, false);
         realmsserver.f_87483_ = JsonUtils.m_90165_("expiredTrial", p_87500_, false);
         realmsserver.f_87485_ = m_87529_(JsonUtils.m_90161_("worldType", p_87500_, RealmsServer.WorldType.NORMAL.name()));
         realmsserver.f_87479_ = JsonUtils.m_90161_("ownerUUID", p_87500_, "");
         if (p_87500_.get("slots") != null && p_87500_.get("slots").isJsonArray()) {
            realmsserver.f_87481_ = m_87513_(p_87500_.get("slots").getAsJsonArray());
         } else {
            realmsserver.f_87481_ = m_87524_();
         }

         realmsserver.f_87487_ = JsonUtils.m_90161_("minigameName", p_87500_, (String)null);
         realmsserver.f_87486_ = JsonUtils.m_90153_("activeSlot", p_87500_, -1);
         realmsserver.f_87488_ = JsonUtils.m_90153_("minigameId", p_87500_, -1);
         realmsserver.f_87489_ = JsonUtils.m_90161_("minigameImage", p_87500_, (String)null);
      } catch (Exception exception) {
         f_87491_.error("Could not parse McoServer: {}", (Object)exception.getMessage());
      }

      return realmsserver;
   }

   private static void m_87504_(RealmsServer p_87505_) {
      p_87505_.f_87480_.sort((p_87502_, p_87503_) -> {
         return ComparisonChain.start().compareFalseFirst(p_87503_.m_87460_(), p_87502_.m_87460_()).compare(p_87502_.m_87447_().toLowerCase(Locale.ROOT), p_87503_.m_87447_().toLowerCase(Locale.ROOT)).result();
      });
   }

   private static List<PlayerInfo> m_87497_(JsonArray p_87498_) {
      List<PlayerInfo> list = Lists.newArrayList();

      for(JsonElement jsonelement : p_87498_) {
         try {
            JsonObject jsonobject = jsonelement.getAsJsonObject();
            PlayerInfo playerinfo = new PlayerInfo();
            playerinfo.m_87448_(JsonUtils.m_90161_("name", jsonobject, (String)null));
            playerinfo.m_87453_(JsonUtils.m_90161_("uuid", jsonobject, (String)null));
            playerinfo.m_87450_(JsonUtils.m_90165_("operator", jsonobject, false));
            playerinfo.m_87455_(JsonUtils.m_90165_("accepted", jsonobject, false));
            playerinfo.m_87458_(JsonUtils.m_90165_("online", jsonobject, false));
            list.add(playerinfo);
         } catch (Exception exception) {
         }
      }

      return list;
   }

   private static Map<Integer, RealmsWorldOptions> m_87513_(JsonArray p_87514_) {
      Map<Integer, RealmsWorldOptions> map = Maps.newHashMap();

      for(JsonElement jsonelement : p_87514_) {
         try {
            JsonObject jsonobject = jsonelement.getAsJsonObject();
            JsonParser jsonparser = new JsonParser();
            JsonElement jsonelement1 = jsonparser.parse(jsonobject.get("options").getAsString());
            RealmsWorldOptions realmsworldoptions;
            if (jsonelement1 == null) {
               realmsworldoptions = RealmsWorldOptions.m_87625_();
            } else {
               realmsworldoptions = RealmsWorldOptions.m_87628_(jsonelement1.getAsJsonObject());
            }

            int i = JsonUtils.m_90153_("slotId", jsonobject, -1);
            map.put(i, realmsworldoptions);
         } catch (Exception exception) {
         }
      }

      for(int j = 1; j <= 3; ++j) {
         if (!map.containsKey(j)) {
            map.put(j, RealmsWorldOptions.m_87632_());
         }
      }

      return map;
   }

   private static Map<Integer, RealmsWorldOptions> m_87524_() {
      Map<Integer, RealmsWorldOptions> map = Maps.newHashMap();
      map.put(1, RealmsWorldOptions.m_87632_());
      map.put(2, RealmsWorldOptions.m_87632_());
      map.put(3, RealmsWorldOptions.m_87632_());
      return map;
   }

   public static RealmsServer m_87518_(String p_87519_) {
      try {
         return m_87499_((new JsonParser()).parse(p_87519_).getAsJsonObject());
      } catch (Exception exception) {
         f_87491_.error("Could not parse McoServer: {}", (Object)exception.getMessage());
         return new RealmsServer();
      }
   }

   private static RealmsServer.State m_87525_(String p_87526_) {
      try {
         return RealmsServer.State.valueOf(p_87526_);
      } catch (Exception exception) {
         return RealmsServer.State.CLOSED;
      }
   }

   private static RealmsServer.WorldType m_87529_(String p_87530_) {
      try {
         return RealmsServer.WorldType.valueOf(p_87530_);
      } catch (Exception exception) {
         return RealmsServer.WorldType.NORMAL;
      }
   }

   public int hashCode() {
      return Objects.hash(this.f_87473_, this.f_87475_, this.f_87476_, this.f_87477_, this.f_87478_, this.f_87482_);
   }

   public boolean equals(Object p_87528_) {
      if (p_87528_ == null) {
         return false;
      } else if (p_87528_ == this) {
         return true;
      } else if (p_87528_.getClass() != this.getClass()) {
         return false;
      } else {
         RealmsServer realmsserver = (RealmsServer)p_87528_;
         return (new EqualsBuilder()).append(this.f_87473_, realmsserver.f_87473_).append((Object)this.f_87475_, (Object)realmsserver.f_87475_).append((Object)this.f_87476_, (Object)realmsserver.f_87476_).append((Object)this.f_87477_, (Object)realmsserver.f_87477_).append((Object)this.f_87478_, (Object)realmsserver.f_87478_).append(this.f_87482_, realmsserver.f_87482_).append((Object)this.f_87485_, (Object)this.f_87485_).isEquals();
      }
   }

   public RealmsServer clone() {
      RealmsServer realmsserver = new RealmsServer();
      realmsserver.f_87473_ = this.f_87473_;
      realmsserver.f_87474_ = this.f_87474_;
      realmsserver.f_87475_ = this.f_87475_;
      realmsserver.f_87476_ = this.f_87476_;
      realmsserver.f_87477_ = this.f_87477_;
      realmsserver.f_87478_ = this.f_87478_;
      realmsserver.f_87480_ = this.f_87480_;
      realmsserver.f_87481_ = this.m_87510_(this.f_87481_);
      realmsserver.f_87482_ = this.f_87482_;
      realmsserver.f_87483_ = this.f_87483_;
      realmsserver.f_87484_ = this.f_87484_;
      realmsserver.f_87490_ = new RealmsServerPing();
      realmsserver.f_87490_.f_87579_ = this.f_87490_.f_87579_;
      realmsserver.f_87490_.f_87580_ = this.f_87490_.f_87580_;
      realmsserver.f_87485_ = this.f_87485_;
      realmsserver.f_87479_ = this.f_87479_;
      realmsserver.f_87487_ = this.f_87487_;
      realmsserver.f_87486_ = this.f_87486_;
      realmsserver.f_87488_ = this.f_87488_;
      realmsserver.f_87489_ = this.f_87489_;
      return realmsserver;
   }

   public Map<Integer, RealmsWorldOptions> m_87510_(Map<Integer, RealmsWorldOptions> p_87511_) {
      Map<Integer, RealmsWorldOptions> map = Maps.newHashMap();

      for(Entry<Integer, RealmsWorldOptions> entry : p_87511_.entrySet()) {
         map.put(entry.getKey(), entry.getValue().clone());
      }

      return map;
   }

   public String m_87495_(int p_87496_) {
      return this.f_87475_ + " (" + this.f_87481_.get(p_87496_).m_87626_(p_87496_) + ")";
   }

   public ServerData m_87522_(String p_87523_) {
      return new ServerData(this.f_87475_, p_87523_, false);
   }

   @OnlyIn(Dist.CLIENT)
   public static class McoServerComparator implements Comparator<RealmsServer> {
      private final String f_87532_;

      public McoServerComparator(String p_87534_) {
         this.f_87532_ = p_87534_;
      }

      public int compare(RealmsServer p_87536_, RealmsServer p_87537_) {
         return ComparisonChain.start().compareTrueFirst(p_87536_.f_87477_ == RealmsServer.State.UNINITIALIZED, p_87537_.f_87477_ == RealmsServer.State.UNINITIALIZED).compareTrueFirst(p_87536_.f_87483_, p_87537_.f_87483_).compareTrueFirst(p_87536_.f_87478_.equals(this.f_87532_), p_87537_.f_87478_.equals(this.f_87532_)).compareFalseFirst(p_87536_.f_87482_, p_87537_.f_87482_).compareTrueFirst(p_87536_.f_87477_ == RealmsServer.State.OPEN, p_87537_.f_87477_ == RealmsServer.State.OPEN).compare(p_87536_.f_87473_, p_87537_.f_87473_).result();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum State {
      CLOSED,
      OPEN,
      UNINITIALIZED;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum WorldType {
      NORMAL,
      MINIGAME,
      ADVENTUREMAP,
      EXPERIENCE,
      INSPIRATION;
   }
}