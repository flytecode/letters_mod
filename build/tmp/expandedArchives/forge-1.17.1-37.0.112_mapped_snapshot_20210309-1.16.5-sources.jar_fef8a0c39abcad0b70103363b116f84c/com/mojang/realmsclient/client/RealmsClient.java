package com.mojang.realmsclient.client;

import com.mojang.realmsclient.dto.BackupList;
import com.mojang.realmsclient.dto.GuardedSerializer;
import com.mojang.realmsclient.dto.Ops;
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.dto.PendingInvitesList;
import com.mojang.realmsclient.dto.PingResult;
import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.RealmsDescriptionDto;
import com.mojang.realmsclient.dto.RealmsNews;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsServerAddress;
import com.mojang.realmsclient.dto.RealmsServerList;
import com.mojang.realmsclient.dto.RealmsServerPlayerLists;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.dto.RealmsWorldResetDto;
import com.mojang.realmsclient.dto.ServerActivityList;
import com.mojang.realmsclient.dto.Subscription;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.dto.WorldTemplatePaginatedList;
import com.mojang.realmsclient.exception.RealmsHttpException;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.util.WorldGenerationInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsClient {
   public static RealmsClient.Environment f_87157_ = RealmsClient.Environment.PRODUCTION;
   private static boolean f_87158_;
   private static final Logger f_87159_ = LogManager.getLogger();
   private final String f_87160_;
   private final String f_87161_;
   private final Minecraft f_87162_;
   private static final String f_167255_ = "worlds";
   private static final String f_167256_ = "invites";
   private static final String f_167257_ = "mco";
   private static final String f_167258_ = "subscriptions";
   private static final String f_167259_ = "activities";
   private static final String f_167260_ = "ops";
   private static final String f_167261_ = "regions/ping/stat";
   private static final String f_167262_ = "trial";
   private static final String f_167263_ = "/$WORLD_ID/initialize";
   private static final String f_167264_ = "/$WORLD_ID";
   private static final String f_167265_ = "/liveplayerlist";
   private static final String f_167266_ = "/$WORLD_ID";
   private static final String f_167267_ = "/$WORLD_ID/$PROFILE_UUID";
   private static final String f_167268_ = "/minigames/$MINIGAME_ID/$WORLD_ID";
   private static final String f_167269_ = "/available";
   private static final String f_167270_ = "/templates/$WORLD_TYPE";
   private static final String f_167271_ = "/v1/$ID/join/pc";
   private static final String f_167272_ = "/$ID";
   private static final String f_167273_ = "/$WORLD_ID";
   private static final String f_167274_ = "/$WORLD_ID/invite/$UUID";
   private static final String f_167237_ = "/count/pending";
   private static final String f_167238_ = "/pending";
   private static final String f_167239_ = "/accept/$INVITATION_ID";
   private static final String f_167240_ = "/reject/$INVITATION_ID";
   private static final String f_167241_ = "/$WORLD_ID";
   private static final String f_167242_ = "/$WORLD_ID";
   private static final String f_167243_ = "/$WORLD_ID/slot/$SLOT_ID";
   private static final String f_167244_ = "/$WORLD_ID/open";
   private static final String f_167245_ = "/$WORLD_ID/close";
   private static final String f_167246_ = "/$WORLD_ID/reset";
   private static final String f_167247_ = "/$WORLD_ID";
   private static final String f_167248_ = "/$WORLD_ID/backups";
   private static final String f_167249_ = "/$WORLD_ID/slot/$SLOT_ID/download";
   private static final String f_167250_ = "/$WORLD_ID/backups/upload";
   private static final String f_167251_ = "/client/compatible";
   private static final String f_167252_ = "/tos/agreed";
   private static final String f_167253_ = "/v1/news";
   private static final String f_167254_ = "/stageAvailable";
   private static final GuardedSerializer f_87163_ = new GuardedSerializer();

   public static RealmsClient m_87169_() {
      Minecraft minecraft = Minecraft.m_91087_();
      String s = minecraft.m_91094_().m_92546_();
      String s1 = minecraft.m_91094_().m_92544_();
      if (!f_87158_) {
         f_87158_ = true;
         String s2 = System.getenv("realms.environment");
         if (s2 == null) {
            s2 = System.getProperty("realms.environment");
         }

         if (s2 != null) {
            if ("LOCAL".equals(s2)) {
               m_87229_();
            } else if ("STAGE".equals(s2)) {
               m_87206_();
            }
         }
      }

      return new RealmsClient(s1, s, minecraft);
   }

   public static void m_87206_() {
      f_87157_ = RealmsClient.Environment.STAGE;
   }

   public static void m_87221_() {
      f_87157_ = RealmsClient.Environment.PRODUCTION;
   }

   public static void m_87229_() {
      f_87157_ = RealmsClient.Environment.LOCAL;
   }

   public RealmsClient(String p_87166_, String p_87167_, Minecraft p_87168_) {
      this.f_87160_ = p_87166_;
      this.f_87161_ = p_87167_;
      this.f_87162_ = p_87168_;
      RealmsClientConfig.m_87293_(p_87168_.m_91096_());
   }

   public RealmsServerList m_87235_() throws RealmsServiceException {
      String s = this.m_87227_("worlds");
      String s1 = this.m_87195_(Request.m_87316_(s));
      return RealmsServerList.m_87577_(s1);
   }

   public RealmsServer m_87174_(long p_87175_) throws RealmsServiceException {
      String s = this.m_87227_("worlds" + "/$ID".replace("$ID", String.valueOf(p_87175_)));
      String s1 = this.m_87195_(Request.m_87316_(s));
      return RealmsServer.m_87518_(s1);
   }

   public ServerActivityList m_167278_(long p_167279_) throws RealmsServiceException {
      String s = this.m_87227_("activities" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_167279_)));
      String s1 = this.m_87195_(Request.m_87316_(s));
      return ServerActivityList.m_167321_(s1);
   }

   public RealmsServerPlayerLists m_87241_() throws RealmsServiceException {
      String s = this.m_87227_("activities/liveplayerlist");
      String s1 = this.m_87195_(Request.m_87316_(s));
      return RealmsServerPlayerLists.m_87596_(s1);
   }

   public RealmsServerAddress m_87207_(long p_87208_) throws RealmsServiceException {
      String s = this.m_87227_("worlds" + "/v1/$ID/join/pc".replace("$ID", "" + p_87208_));
      String s1 = this.m_87195_(Request.m_87318_(s, 5000, 30000));
      return RealmsServerAddress.m_87571_(s1);
   }

   public void m_87191_(long p_87192_, String p_87193_, String p_87194_) throws RealmsServiceException {
      RealmsDescriptionDto realmsdescriptiondto = new RealmsDescriptionDto(p_87193_, p_87194_);
      String s = this.m_87227_("worlds" + "/$WORLD_ID/initialize".replace("$WORLD_ID", String.valueOf(p_87192_)));
      String s1 = f_87163_.m_87413_(realmsdescriptiondto);
      this.m_87195_(Request.m_87325_(s, s1, 5000, 10000));
   }

   public Boolean m_87247_() throws RealmsServiceException {
      String s = this.m_87227_("mco/available");
      String s1 = this.m_87195_(Request.m_87316_(s));
      return Boolean.valueOf(s1);
   }

   public Boolean m_87253_() throws RealmsServiceException {
      String s = this.m_87227_("mco/stageAvailable");
      String s1 = this.m_87195_(Request.m_87316_(s));
      return Boolean.valueOf(s1);
   }

   public RealmsClient.CompatibleVersionResponse m_87259_() throws RealmsServiceException {
      String s = this.m_87227_("mco/client/compatible");
      String s1 = this.m_87195_(Request.m_87316_(s));

      try {
         return RealmsClient.CompatibleVersionResponse.valueOf(s1);
      } catch (IllegalArgumentException illegalargumentexception) {
         throw new RealmsServiceException(500, "Could not check compatible version, got response: " + s1, -1, "");
      }
   }

   public void m_87183_(long p_87184_, String p_87185_) throws RealmsServiceException {
      String s = this.m_87227_("invites" + "/$WORLD_ID/invite/$UUID".replace("$WORLD_ID", String.valueOf(p_87184_)).replace("$UUID", p_87185_));
      this.m_87195_(Request.m_87340_(s));
   }

   public void m_87222_(long p_87223_) throws RealmsServiceException {
      String s = this.m_87227_("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_87223_)));
      this.m_87195_(Request.m_87340_(s));
   }

   public RealmsServer m_87212_(long p_87213_, String p_87214_) throws RealmsServiceException {
      PlayerInfo playerinfo = new PlayerInfo();
      playerinfo.m_87448_(p_87214_);
      String s = this.m_87227_("invites" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_87213_)));
      String s1 = this.m_87195_(Request.m_87342_(s, f_87163_.m_87413_(playerinfo)));
      return RealmsServer.m_87518_(s1);
   }

   public BackupList m_87230_(long p_87231_) throws RealmsServiceException {
      String s = this.m_87227_("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(p_87231_)));
      String s1 = this.m_87195_(Request.m_87316_(s));
      return BackupList.m_87409_(s1);
   }

   public void m_87215_(long p_87216_, String p_87217_, String p_87218_) throws RealmsServiceException {
      RealmsDescriptionDto realmsdescriptiondto = new RealmsDescriptionDto(p_87217_, p_87218_);
      String s = this.m_87227_("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_87216_)));
      this.m_87195_(Request.m_87342_(s, f_87163_.m_87413_(realmsdescriptiondto)));
   }

   public void m_87179_(long p_87180_, int p_87181_, RealmsWorldOptions p_87182_) throws RealmsServiceException {
      String s = this.m_87227_("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(p_87180_)).replace("$SLOT_ID", String.valueOf(p_87181_)));
      String s1 = p_87182_.m_87635_();
      this.m_87195_(Request.m_87342_(s, s1));
   }

   public boolean m_87176_(long p_87177_, int p_87178_) throws RealmsServiceException {
      String s = this.m_87227_("worlds" + "/$WORLD_ID/slot/$SLOT_ID".replace("$WORLD_ID", String.valueOf(p_87177_)).replace("$SLOT_ID", String.valueOf(p_87178_)));
      String s1 = this.m_87195_(Request.m_87353_(s, ""));
      return Boolean.valueOf(s1);
   }

   public void m_87224_(long p_87225_, String p_87226_) throws RealmsServiceException {
      String s = this.m_87203_("worlds" + "/$WORLD_ID/backups".replace("$WORLD_ID", String.valueOf(p_87225_)), "backupId=" + p_87226_);
      this.m_87195_(Request.m_87345_(s, "", 40000, 600000));
   }

   public WorldTemplatePaginatedList m_87170_(int p_87171_, int p_87172_, RealmsServer.WorldType p_87173_) throws RealmsServiceException {
      String s = this.m_87203_("worlds" + "/templates/$WORLD_TYPE".replace("$WORLD_TYPE", p_87173_.toString()), String.format("page=%d&pageSize=%d", p_87171_, p_87172_));
      String s1 = this.m_87195_(Request.m_87316_(s));
      return WorldTemplatePaginatedList.m_87762_(s1);
   }

   public Boolean m_87232_(long p_87233_, String p_87234_) throws RealmsServiceException {
      String s = "/minigames/$MINIGAME_ID/$WORLD_ID".replace("$MINIGAME_ID", p_87234_).replace("$WORLD_ID", String.valueOf(p_87233_));
      String s1 = this.m_87227_("worlds" + s);
      return Boolean.valueOf(this.m_87195_(Request.m_87353_(s1, "")));
   }

   public Ops m_87238_(long p_87239_, String p_87240_) throws RealmsServiceException {
      String s = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(p_87239_)).replace("$PROFILE_UUID", p_87240_);
      String s1 = this.m_87227_("ops" + s);
      return Ops.m_87420_(this.m_87195_(Request.m_87342_(s1, "")));
   }

   public Ops m_87244_(long p_87245_, String p_87246_) throws RealmsServiceException {
      String s = "/$WORLD_ID/$PROFILE_UUID".replace("$WORLD_ID", String.valueOf(p_87245_)).replace("$PROFILE_UUID", p_87246_);
      String s1 = this.m_87227_("ops" + s);
      return Ops.m_87420_(this.m_87195_(Request.m_87340_(s1)));
   }

   public Boolean m_87236_(long p_87237_) throws RealmsServiceException {
      String s = this.m_87227_("worlds" + "/$WORLD_ID/open".replace("$WORLD_ID", String.valueOf(p_87237_)));
      String s1 = this.m_87195_(Request.m_87353_(s, ""));
      return Boolean.valueOf(s1);
   }

   public Boolean m_87242_(long p_87243_) throws RealmsServiceException {
      String s = this.m_87227_("worlds" + "/$WORLD_ID/close".replace("$WORLD_ID", String.valueOf(p_87243_)));
      String s1 = this.m_87195_(Request.m_87353_(s, ""));
      return Boolean.valueOf(s1);
   }

   public Boolean m_167275_(long p_167276_, WorldGenerationInfo p_167277_) throws RealmsServiceException {
      RealmsWorldResetDto realmsworldresetdto = new RealmsWorldResetDto(p_167277_.m_167634_(), -1L, p_167277_.m_167635_().m_167608_(), p_167277_.m_167636_());
      String s = this.m_87227_("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(p_167276_)));
      String s1 = this.m_87195_(Request.m_87325_(s, f_87163_.m_87413_(realmsworldresetdto), 30000, 80000));
      return Boolean.valueOf(s1);
   }

   public Boolean m_87250_(long p_87251_, String p_87252_) throws RealmsServiceException {
      RealmsWorldResetDto realmsworldresetdto = new RealmsWorldResetDto((String)null, Long.valueOf(p_87252_), -1, false);
      String s = this.m_87227_("worlds" + "/$WORLD_ID/reset".replace("$WORLD_ID", String.valueOf(p_87251_)));
      String s1 = this.m_87195_(Request.m_87325_(s, f_87163_.m_87413_(realmsworldresetdto), 30000, 80000));
      return Boolean.valueOf(s1);
   }

   public Subscription m_87248_(long p_87249_) throws RealmsServiceException {
      String s = this.m_87227_("subscriptions" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_87249_)));
      String s1 = this.m_87195_(Request.m_87316_(s));
      return Subscription.m_87672_(s1);
   }

   public int m_87260_() throws RealmsServiceException {
      return this.m_87261_().f_87432_.size();
   }

   public PendingInvitesList m_87261_() throws RealmsServiceException {
      String s = this.m_87227_("invites/pending");
      String s1 = this.m_87195_(Request.m_87316_(s));
      PendingInvitesList pendinginviteslist = PendingInvitesList.m_87436_(s1);
      pendinginviteslist.f_87432_.removeIf(this::m_87197_);
      return pendinginviteslist;
   }

   private boolean m_87197_(PendingInvite p_87198_) {
      try {
         UUID uuid = UUID.fromString(p_87198_.f_87425_);
         return this.f_87162_.m_91266_().m_100688_(uuid);
      } catch (IllegalArgumentException illegalargumentexception) {
         return false;
      }
   }

   public void m_87201_(String p_87202_) throws RealmsServiceException {
      String s = this.m_87227_("invites" + "/accept/$INVITATION_ID".replace("$INVITATION_ID", p_87202_));
      this.m_87195_(Request.m_87353_(s, ""));
   }

   public WorldDownload m_87209_(long p_87210_, int p_87211_) throws RealmsServiceException {
      String s = this.m_87227_("worlds" + "/$WORLD_ID/slot/$SLOT_ID/download".replace("$WORLD_ID", String.valueOf(p_87210_)).replace("$SLOT_ID", String.valueOf(p_87211_)));
      String s1 = this.m_87195_(Request.m_87316_(s));
      return WorldDownload.m_87724_(s1);
   }

   @Nullable
   public UploadInfo m_87256_(long p_87257_, @Nullable String p_87258_) throws RealmsServiceException {
      String s = this.m_87227_("worlds" + "/$WORLD_ID/backups/upload".replace("$WORLD_ID", String.valueOf(p_87257_)));
      return UploadInfo.m_87700_(this.m_87195_(Request.m_87353_(s, UploadInfo.m_87709_(p_87258_))));
   }

   public void m_87219_(String p_87220_) throws RealmsServiceException {
      String s = this.m_87227_("invites" + "/reject/$INVITATION_ID".replace("$INVITATION_ID", p_87220_));
      this.m_87195_(Request.m_87353_(s, ""));
   }

   public void m_87262_() throws RealmsServiceException {
      String s = this.m_87227_("mco/tos/agreed");
      this.m_87195_(Request.m_87342_(s, ""));
   }

   public RealmsNews m_87263_() throws RealmsServiceException {
      String s = this.m_87227_("mco/v1/news");
      String s1 = this.m_87195_(Request.m_87318_(s, 5000, 10000));
      return RealmsNews.m_87471_(s1);
   }

   public void m_87199_(PingResult p_87200_) throws RealmsServiceException {
      String s = this.m_87227_("regions/ping/stat");
      this.m_87195_(Request.m_87342_(s, f_87163_.m_87413_(p_87200_)));
   }

   public Boolean m_87264_() throws RealmsServiceException {
      String s = this.m_87227_("trial");
      String s1 = this.m_87195_(Request.m_87316_(s));
      return Boolean.valueOf(s1);
   }

   public void m_87254_(long p_87255_) throws RealmsServiceException {
      String s = this.m_87227_("worlds" + "/$WORLD_ID".replace("$WORLD_ID", String.valueOf(p_87255_)));
      this.m_87195_(Request.m_87340_(s));
   }

   @Nullable
   private String m_87227_(String p_87228_) {
      return this.m_87203_(p_87228_, (String)null);
   }

   @Nullable
   private String m_87203_(String p_87204_, @Nullable String p_87205_) {
      try {
         return (new URI(f_87157_.f_87280_, f_87157_.f_87279_, "/" + p_87204_, p_87205_, (String)null)).toASCIIString();
      } catch (URISyntaxException urisyntaxexception) {
         urisyntaxexception.printStackTrace();
         return null;
      }
   }

   private String m_87195_(Request<?> p_87196_) throws RealmsServiceException {
      p_87196_.m_87322_("sid", this.f_87160_);
      p_87196_.m_87322_("user", this.f_87161_);
      p_87196_.m_87322_("version", SharedConstants.m_136187_().getName());

      try {
         int i = p_87196_.m_87339_();
         if (i != 503 && i != 277) {
            String s = p_87196_.m_87350_();
            if (i >= 200 && i < 300) {
               return s;
            } else if (i == 401) {
               String s1 = p_87196_.m_87351_("WWW-Authenticate");
               f_87159_.info("Could not authorize you against Realms server: {}", (Object)s1);
               throw new RealmsServiceException(i, s1, -1, s1);
            } else if (s != null && s.length() != 0) {
               RealmsError realmserror = RealmsError.m_87303_(s);
               f_87159_.error("Realms http code: {} -  error code: {} -  message: {} - raw body: {}", i, realmserror.m_87305_(), realmserror.m_87302_(), s);
               throw new RealmsServiceException(i, s, realmserror);
            } else {
               f_87159_.error("Realms error code: {} message: {}", i, s);
               throw new RealmsServiceException(i, s, i, "");
            }
         } else {
            int j = p_87196_.m_87313_();
            throw new RetryCallException(j, i);
         }
      } catch (RealmsHttpException realmshttpexception) {
         throw new RealmsServiceException(500, "Could not connect to Realms: " + realmshttpexception.getMessage(), -1, "");
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum CompatibleVersionResponse {
      COMPATIBLE,
      OUTDATED,
      OTHER;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Environment {
      PRODUCTION("pc.realms.minecraft.net", "https"),
      STAGE("pc-stage.realms.minecraft.net", "https"),
      LOCAL("localhost:8080", "http");

      public String f_87279_;
      public String f_87280_;

      private Environment(String p_87286_, String p_87287_) {
         this.f_87279_ = p_87286_;
         this.f_87280_ = p_87287_;
      }
   }
}