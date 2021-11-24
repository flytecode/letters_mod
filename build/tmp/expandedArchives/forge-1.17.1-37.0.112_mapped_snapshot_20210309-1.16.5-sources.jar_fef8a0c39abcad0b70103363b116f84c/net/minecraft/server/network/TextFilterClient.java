package net.minecraft.server.network;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.authlib.GameProfile;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.thread.ProcessorMailbox;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextFilterClient implements AutoCloseable {
   private static final Logger f_10098_ = LogManager.getLogger();
   private static final AtomicInteger f_10099_ = new AtomicInteger(1);
   private static final ThreadFactory f_10100_ = (p_10148_) -> {
      Thread thread = new Thread(p_10148_);
      thread.setName("Chat-Filter-Worker-" + f_10099_.getAndIncrement());
      return thread;
   };
   private final URL f_10101_;
   final URL f_10102_;
   final URL f_10103_;
   private final String f_10104_;
   private final int f_10105_;
   private final String f_10106_;
   final TextFilterClient.IgnoreStrategy f_10107_;
   final ExecutorService f_10108_;

   private TextFilterClient(URI p_143726_, String p_143727_, int p_143728_, String p_143729_, TextFilterClient.IgnoreStrategy p_143730_, int p_143731_) throws MalformedURLException {
      this.f_10104_ = p_143727_;
      this.f_10105_ = p_143728_;
      this.f_10106_ = p_143729_;
      this.f_10107_ = p_143730_;
      this.f_10101_ = p_143726_.resolve("/v1/chat").toURL();
      this.f_10102_ = p_143726_.resolve("/v1/join").toURL();
      this.f_10103_ = p_143726_.resolve("/v1/leave").toURL();
      this.f_10108_ = Executors.newFixedThreadPool(p_143731_, f_10100_);
   }

   @Nullable
   public static TextFilterClient m_143736_(String p_143737_) {
      if (Strings.isNullOrEmpty(p_143737_)) {
         return null;
      } else {
         try {
            JsonObject jsonobject = GsonHelper.m_13864_(p_143737_);
            URI uri = new URI(GsonHelper.m_13906_(jsonobject, "apiServer"));
            String s = GsonHelper.m_13906_(jsonobject, "apiKey");
            if (s.isEmpty()) {
               throw new IllegalArgumentException("Missing API key");
            } else {
               int i = GsonHelper.m_13824_(jsonobject, "ruleId", 1);
               String s1 = GsonHelper.m_13851_(jsonobject, "serverId", "");
               int j = GsonHelper.m_13824_(jsonobject, "hashesToDrop", -1);
               int k = GsonHelper.m_13824_(jsonobject, "maxConcurrentRequests", 7);
               TextFilterClient.IgnoreStrategy textfilterclient$ignorestrategy = TextFilterClient.IgnoreStrategy.m_143744_(j);
               return new TextFilterClient(uri, (new Base64()).encodeToString(s.getBytes(StandardCharsets.US_ASCII)), i, s1, textfilterclient$ignorestrategy, k);
            }
         } catch (Exception exception) {
            f_10098_.warn("Failed to parse chat filter config {}", p_143737_, exception);
            return null;
         }
      }
   }

   void m_10141_(GameProfile p_10142_, URL p_10143_, Executor p_10144_) {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("server", this.f_10106_);
      jsonobject.addProperty("room", "Chat");
      jsonobject.addProperty("user_id", p_10142_.getId().toString());
      jsonobject.addProperty("user_display_name", p_10142_.getName());
      p_10144_.execute(() -> {
         try {
            this.m_10151_(jsonobject, p_10143_);
         } catch (Exception exception) {
            f_10098_.warn("Failed to send join/leave packet to {} for player {}", p_10143_, p_10142_, exception);
         }

      });
   }

   CompletableFuture<TextFilter.FilteredText> m_10136_(GameProfile p_10137_, String p_10138_, TextFilterClient.IgnoreStrategy p_10139_, Executor p_10140_) {
      if (p_10138_.isEmpty()) {
         return CompletableFuture.completedFuture(TextFilter.FilteredText.f_143712_);
      } else {
         JsonObject jsonobject = new JsonObject();
         jsonobject.addProperty("rule", this.f_10105_);
         jsonobject.addProperty("server", this.f_10106_);
         jsonobject.addProperty("room", "Chat");
         jsonobject.addProperty("player", p_10137_.getId().toString());
         jsonobject.addProperty("player_display_name", p_10137_.getName());
         jsonobject.addProperty("text", p_10138_);
         return CompletableFuture.supplyAsync(() -> {
            try {
               JsonObject jsonobject1 = this.m_10127_(jsonobject, this.f_10101_);
               boolean flag = GsonHelper.m_13855_(jsonobject1, "response", false);
               if (flag) {
                  return TextFilter.FilteredText.m_143720_(p_10138_);
               } else {
                  String s = GsonHelper.m_13851_(jsonobject1, "hashed", (String)null);
                  if (s == null) {
                     return TextFilter.FilteredText.m_143723_(p_10138_);
                  } else {
                     int i = GsonHelper.m_13933_(jsonobject1, "hashes").size();
                     return p_10139_.m_10171_(s, i) ? TextFilter.FilteredText.m_143723_(p_10138_) : new TextFilter.FilteredText(p_10138_, s);
                  }
               }
            } catch (Exception exception) {
               f_10098_.warn("Failed to validate message '{}'", p_10138_, exception);
               return TextFilter.FilteredText.m_143723_(p_10138_);
            }
         }, p_10140_);
      }
   }

   public void close() {
      this.f_10108_.shutdownNow();
   }

   private void m_10145_(InputStream p_10146_) throws IOException {
      byte[] abyte = new byte[1024];

      while(p_10146_.read(abyte) != -1) {
      }

   }

   private JsonObject m_10127_(JsonObject p_10128_, URL p_10129_) throws IOException {
      HttpURLConnection httpurlconnection = this.m_10156_(p_10128_, p_10129_);
      InputStream inputstream = httpurlconnection.getInputStream();

      JsonObject jsonobject;
      label89: {
         try {
            if (httpurlconnection.getResponseCode() == 204) {
               jsonobject = new JsonObject();
               break label89;
            }

            try {
               jsonobject = Streams.parse(new JsonReader(new InputStreamReader(inputstream))).getAsJsonObject();
            } finally {
               this.m_10145_(inputstream);
            }
         } catch (Throwable throwable1) {
            if (inputstream != null) {
               try {
                  inputstream.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (inputstream != null) {
            inputstream.close();
         }

         return jsonobject;
      }

      if (inputstream != null) {
         inputstream.close();
      }

      return jsonobject;
   }

   private void m_10151_(JsonObject p_10152_, URL p_10153_) throws IOException {
      HttpURLConnection httpurlconnection = this.m_10156_(p_10152_, p_10153_);
      InputStream inputstream = httpurlconnection.getInputStream();

      try {
         this.m_10145_(inputstream);
      } catch (Throwable throwable1) {
         if (inputstream != null) {
            try {
               inputstream.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (inputstream != null) {
         inputstream.close();
      }

   }

   private HttpURLConnection m_10156_(JsonObject p_10157_, URL p_10158_) throws IOException {
      HttpURLConnection httpurlconnection = (HttpURLConnection)p_10158_.openConnection();
      httpurlconnection.setConnectTimeout(15000);
      httpurlconnection.setReadTimeout(2000);
      httpurlconnection.setUseCaches(false);
      httpurlconnection.setDoOutput(true);
      httpurlconnection.setDoInput(true);
      httpurlconnection.setRequestMethod("POST");
      httpurlconnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
      httpurlconnection.setRequestProperty("Accept", "application/json");
      httpurlconnection.setRequestProperty("Authorization", "Basic " + this.f_10104_);
      httpurlconnection.setRequestProperty("User-Agent", "Minecraft server" + SharedConstants.m_136187_().getName());
      OutputStreamWriter outputstreamwriter = new OutputStreamWriter(httpurlconnection.getOutputStream(), StandardCharsets.UTF_8);

      try {
         JsonWriter jsonwriter = new JsonWriter(outputstreamwriter);

         try {
            Streams.write(p_10157_, jsonwriter);
         } catch (Throwable throwable2) {
            try {
               jsonwriter.close();
            } catch (Throwable throwable1) {
               throwable2.addSuppressed(throwable1);
            }

            throw throwable2;
         }

         jsonwriter.close();
      } catch (Throwable throwable3) {
         try {
            outputstreamwriter.close();
         } catch (Throwable throwable) {
            throwable3.addSuppressed(throwable);
         }

         throw throwable3;
      }

      outputstreamwriter.close();
      int i = httpurlconnection.getResponseCode();
      if (i >= 200 && i < 300) {
         return httpurlconnection;
      } else {
         throw new TextFilterClient.RequestFailedException(i + " " + httpurlconnection.getResponseMessage());
      }
   }

   public TextFilter m_10134_(GameProfile p_10135_) {
      return new TextFilterClient.PlayerContext(p_10135_);
   }

   @FunctionalInterface
   public interface IgnoreStrategy {
      TextFilterClient.IgnoreStrategy f_10162_ = (p_10169_, p_10170_) -> {
         return false;
      };
      TextFilterClient.IgnoreStrategy f_10163_ = (p_10166_, p_10167_) -> {
         return p_10166_.length() == p_10167_;
      };

      static TextFilterClient.IgnoreStrategy m_143738_(int p_143739_) {
         return (p_143742_, p_143743_) -> {
            return p_143743_ >= p_143739_;
         };
      }

      static TextFilterClient.IgnoreStrategy m_143744_(int p_143745_) {
         switch(p_143745_) {
         case -1:
            return f_10162_;
         case 0:
            return f_10163_;
         default:
            return m_143738_(p_143745_);
         }
      }

      boolean m_10171_(String p_10172_, int p_10173_);
   }

   class PlayerContext implements TextFilter {
      private final GameProfile f_10175_;
      private final Executor f_10176_;

      PlayerContext(GameProfile p_10179_) {
         this.f_10175_ = p_10179_;
         ProcessorMailbox<Runnable> processormailbox = ProcessorMailbox.m_18751_(TextFilterClient.this.f_10108_, "chat stream for " + p_10179_.getName());
         this.f_10176_ = processormailbox::m_6937_;
      }

      public void m_7674_() {
         TextFilterClient.this.m_10141_(this.f_10175_, TextFilterClient.this.f_10102_, this.f_10176_);
      }

      public void m_7670_() {
         TextFilterClient.this.m_10141_(this.f_10175_, TextFilterClient.this.f_10103_, this.f_10176_);
      }

      public CompletableFuture<List<TextFilter.FilteredText>> m_5925_(List<String> p_10190_) {
         List<CompletableFuture<TextFilter.FilteredText>> list = p_10190_.stream().map((p_10195_) -> {
            return TextFilterClient.this.m_10136_(this.f_10175_, p_10195_, TextFilterClient.this.f_10107_, this.f_10176_);
         }).collect(ImmutableList.toImmutableList());
         return Util.m_143840_(list).exceptionally((p_143747_) -> {
            return ImmutableList.of();
         });
      }

      public CompletableFuture<TextFilter.FilteredText> m_6770_(String p_10186_) {
         return TextFilterClient.this.m_10136_(this.f_10175_, p_10186_, TextFilterClient.this.f_10107_, this.f_10176_);
      }
   }

   public static class RequestFailedException extends RuntimeException {
      RequestFailedException(String p_10199_) {
         super(p_10199_);
      }
   }
}