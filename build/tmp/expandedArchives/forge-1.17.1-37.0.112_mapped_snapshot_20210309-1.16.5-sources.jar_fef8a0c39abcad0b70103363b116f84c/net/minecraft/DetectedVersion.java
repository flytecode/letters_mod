package net.minecraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.bridge.game.GameVersion;
import com.mojang.bridge.game.PackType;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import net.minecraft.util.GsonHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DetectedVersion implements GameVersion {
   private static final Logger f_132477_ = LogManager.getLogger();
   public static final GameVersion f_132476_ = new DetectedVersion();
   private final String f_132478_;
   private final String f_132479_;
   private final boolean f_132480_;
   private final int f_132481_;
   private final int f_132482_;
   private final int f_179761_;
   private final int f_179762_;
   private final Date f_132484_;
   private final String f_132485_;

   private DetectedVersion() {
      this.f_132478_ = UUID.randomUUID().toString().replaceAll("-", "");
      this.f_132479_ = "1.17.1";
      this.f_132480_ = true;
      this.f_132481_ = 2730;
      this.f_132482_ = SharedConstants.m_136192_();
      this.f_179761_ = 7;
      this.f_179762_ = 7;
      this.f_132484_ = new Date();
      this.f_132485_ = "1.17.1";
   }

   private DetectedVersion(JsonObject p_132489_) {
      this.f_132478_ = GsonHelper.m_13906_(p_132489_, "id");
      this.f_132479_ = GsonHelper.m_13906_(p_132489_, "name");
      this.f_132485_ = GsonHelper.m_13906_(p_132489_, "release_target");
      this.f_132480_ = GsonHelper.m_13912_(p_132489_, "stable");
      this.f_132481_ = GsonHelper.m_13927_(p_132489_, "world_version");
      this.f_132482_ = GsonHelper.m_13927_(p_132489_, "protocol_version");
      JsonObject jsonobject = GsonHelper.m_13930_(p_132489_, "pack_version");
      this.f_179761_ = GsonHelper.m_13927_(jsonobject, "resource");
      this.f_179762_ = GsonHelper.m_13927_(jsonobject, "data");
      this.f_132484_ = Date.from(ZonedDateTime.parse(GsonHelper.m_13906_(p_132489_, "build_time")).toInstant());
   }

   public static GameVersion m_132490_() {
      try {
         InputStream inputstream = DetectedVersion.class.getResourceAsStream("/version.json");

         GameVersion gameversion;
         label63: {
            DetectedVersion detectedversion;
            try {
               if (inputstream == null) {
                  f_132477_.warn("Missing version information!");
                  gameversion = f_132476_;
                  break label63;
               }

               InputStreamReader inputstreamreader = new InputStreamReader(inputstream);

               try {
                  detectedversion = new DetectedVersion(GsonHelper.m_13859_(inputstreamreader));
               } catch (Throwable throwable2) {
                  try {
                     inputstreamreader.close();
                  } catch (Throwable throwable1) {
                     throwable2.addSuppressed(throwable1);
                  }

                  throw throwable2;
               }

               inputstreamreader.close();
            } catch (Throwable throwable3) {
               if (inputstream != null) {
                  try {
                     inputstream.close();
                  } catch (Throwable throwable) {
                     throwable3.addSuppressed(throwable);
                  }
               }

               throw throwable3;
            }

            if (inputstream != null) {
               inputstream.close();
            }

            return detectedversion;
         }

         if (inputstream != null) {
            inputstream.close();
         }

         return gameversion;
      } catch (JsonParseException | IOException ioexception) {
         throw new IllegalStateException("Game version information is corrupt", ioexception);
      }
   }

   public String getId() {
      return this.f_132478_;
   }

   public String getName() {
      return this.f_132479_;
   }

   public String getReleaseTarget() {
      return this.f_132485_;
   }

   public int getWorldVersion() {
      return this.f_132481_;
   }

   public int getProtocolVersion() {
      return this.f_132482_;
   }

   public int getPackVersion(PackType p_179764_) {
      return p_179764_ == PackType.DATA ? this.f_179762_ : this.f_179761_;
   }

   public Date getBuildTime() {
      return this.f_132484_;
   }

   public boolean isStable() {
      return this.f_132480_;
   }
}