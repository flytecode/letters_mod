package com.mojang.realmsclient.dto;

import com.google.common.annotations.VisibleForTesting;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.util.JsonUtils;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class UploadInfo extends ValueObject {
   private static final Logger f_87686_ = LogManager.getLogger();
   private static final String f_167324_ = "http://";
   private static final int f_167325_ = 8080;
   private static final Pattern f_87687_ = Pattern.compile("^[a-zA-Z][-a-zA-Z0-9+.]+:");
   private final boolean f_87688_;
   @Nullable
   private final String f_87689_;
   private final URI f_87690_;

   private UploadInfo(boolean p_87693_, @Nullable String p_87694_, URI p_87695_) {
      this.f_87688_ = p_87693_;
      this.f_87689_ = p_87694_;
      this.f_87690_ = p_87695_;
   }

   @Nullable
   public static UploadInfo m_87700_(String p_87701_) {
      try {
         JsonParser jsonparser = new JsonParser();
         JsonObject jsonobject = jsonparser.parse(p_87701_).getAsJsonObject();
         String s = JsonUtils.m_90161_("uploadEndpoint", jsonobject, (String)null);
         if (s != null) {
            int i = JsonUtils.m_90153_("port", jsonobject, -1);
            URI uri = m_87702_(s, i);
            if (uri != null) {
               boolean flag = JsonUtils.m_90165_("worldClosed", jsonobject, false);
               String s1 = JsonUtils.m_90161_("token", jsonobject, (String)null);
               return new UploadInfo(flag, s1, uri);
            }
         }
      } catch (Exception exception) {
         f_87686_.error("Could not parse UploadInfo: {}", (Object)exception.getMessage());
      }

      return null;
   }

   @Nullable
   @VisibleForTesting
   public static URI m_87702_(String p_87703_, int p_87704_) {
      Matcher matcher = f_87687_.matcher(p_87703_);
      String s = m_87705_(p_87703_, matcher);

      try {
         URI uri = new URI(s);
         int i = m_87697_(p_87704_, uri.getPort());
         return i != uri.getPort() ? new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), i, uri.getPath(), uri.getQuery(), uri.getFragment()) : uri;
      } catch (URISyntaxException urisyntaxexception) {
         f_87686_.warn("Failed to parse URI {}", s, urisyntaxexception);
         return null;
      }
   }

   private static int m_87697_(int p_87698_, int p_87699_) {
      if (p_87698_ != -1) {
         return p_87698_;
      } else {
         return p_87699_ != -1 ? p_87699_ : 8080;
      }
   }

   private static String m_87705_(String p_87706_, Matcher p_87707_) {
      return p_87707_.find() ? p_87706_ : "http://" + p_87706_;
   }

   public static String m_87709_(@Nullable String p_87710_) {
      JsonObject jsonobject = new JsonObject();
      if (p_87710_ != null) {
         jsonobject.addProperty("token", p_87710_);
      }

      return jsonobject.toString();
   }

   @Nullable
   public String m_87696_() {
      return this.f_87689_;
   }

   public URI m_87708_() {
      return this.f_87690_;
   }

   public boolean m_87711_() {
      return this.f_87688_;
   }
}