package com.mojang.realmsclient.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.gui.screens.UploadResult;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import net.minecraft.client.User;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.Args;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class FileUpload {
   private static final Logger f_87057_ = LogManager.getLogger();
   private static final int f_167233_ = 5;
   private static final String f_167234_ = "/upload";
   private final File f_87058_;
   private final long f_87059_;
   private final int f_87060_;
   private final UploadInfo f_87061_;
   private final String f_87062_;
   private final String f_87063_;
   private final String f_87064_;
   private final UploadStatus f_87065_;
   private final AtomicBoolean f_87066_ = new AtomicBoolean(false);
   private CompletableFuture<UploadResult> f_87067_;
   private final RequestConfig f_87068_ = RequestConfig.custom().setSocketTimeout((int)TimeUnit.MINUTES.toMillis(10L)).setConnectTimeout((int)TimeUnit.SECONDS.toMillis(15L)).build();

   public FileUpload(File p_87071_, long p_87072_, int p_87073_, UploadInfo p_87074_, User p_87075_, String p_87076_, UploadStatus p_87077_) {
      this.f_87058_ = p_87071_;
      this.f_87059_ = p_87072_;
      this.f_87060_ = p_87073_;
      this.f_87061_ = p_87074_;
      this.f_87062_ = p_87075_.m_92544_();
      this.f_87063_ = p_87075_.m_92546_();
      this.f_87064_ = p_87076_;
      this.f_87065_ = p_87077_;
   }

   public void m_87084_(Consumer<UploadResult> p_87085_) {
      if (this.f_87067_ == null) {
         this.f_87067_ = CompletableFuture.supplyAsync(() -> {
            return this.m_87079_(0);
         });
         this.f_87067_.thenAccept(p_87085_);
      }
   }

   public void m_87078_() {
      this.f_87066_.set(true);
      if (this.f_87067_ != null) {
         this.f_87067_.cancel(false);
         this.f_87067_ = null;
      }

   }

   private UploadResult m_87079_(int p_87080_) {
      UploadResult.Builder uploadresult$builder = new UploadResult.Builder();
      if (this.f_87066_.get()) {
         return uploadresult$builder.m_90145_();
      } else {
         this.f_87065_.f_87387_ = this.f_87058_.length();
         HttpPost httppost = new HttpPost(this.f_87061_.m_87708_().resolve("/upload/" + this.f_87059_ + "/" + this.f_87060_));
         CloseableHttpClient closeablehttpclient = HttpClientBuilder.create().setDefaultRequestConfig(this.f_87068_).build();

         UploadResult uploadresult;
         try {
            this.m_87091_(httppost);
            HttpResponse httpresponse = closeablehttpclient.execute(httppost);
            long i = this.m_87086_(httpresponse);
            if (!this.m_87081_(i, p_87080_)) {
               this.m_87088_(httpresponse, uploadresult$builder);
               return uploadresult$builder.m_90145_();
            }

            uploadresult = this.m_87097_(i, p_87080_);
         } catch (Exception exception) {
            if (!this.f_87066_.get()) {
               f_87057_.error("Caught exception while uploading: ", (Throwable)exception);
            }

            return uploadresult$builder.m_90145_();
         } finally {
            this.m_87093_(httppost, closeablehttpclient);
         }

         return uploadresult;
      }
   }

   private void m_87093_(HttpPost p_87094_, CloseableHttpClient p_87095_) {
      p_87094_.releaseConnection();
      if (p_87095_ != null) {
         try {
            p_87095_.close();
         } catch (IOException ioexception) {
            f_87057_.error("Failed to close Realms upload client");
         }
      }

   }

   private void m_87091_(HttpPost p_87092_) throws FileNotFoundException {
      p_87092_.setHeader("Cookie", "sid=" + this.f_87062_ + ";token=" + this.f_87061_.m_87696_() + ";user=" + this.f_87063_ + ";version=" + this.f_87064_);
      FileUpload.CustomInputStreamEntity fileupload$custominputstreamentity = new FileUpload.CustomInputStreamEntity(new FileInputStream(this.f_87058_), this.f_87058_.length(), this.f_87065_);
      fileupload$custominputstreamentity.setContentType("application/octet-stream");
      p_87092_.setEntity(fileupload$custominputstreamentity);
   }

   private void m_87088_(HttpResponse p_87089_, UploadResult.Builder p_87090_) throws IOException {
      int i = p_87089_.getStatusLine().getStatusCode();
      if (i == 401) {
         f_87057_.debug("Realms server returned 401: {}", (Object)p_87089_.getFirstHeader("WWW-Authenticate"));
      }

      p_87090_.m_90146_(i);
      if (p_87089_.getEntity() != null) {
         String s = EntityUtils.toString(p_87089_.getEntity(), "UTF-8");
         if (s != null) {
            try {
               JsonParser jsonparser = new JsonParser();
               JsonElement jsonelement = jsonparser.parse(s).getAsJsonObject().get("errorMsg");
               Optional<String> optional = Optional.ofNullable(jsonelement).map(JsonElement::getAsString);
               p_87090_.m_90148_(optional.orElse((String)null));
            } catch (Exception exception) {
            }
         }
      }

   }

   private boolean m_87081_(long p_87082_, int p_87083_) {
      return p_87082_ > 0L && p_87083_ + 1 < 5;
   }

   private UploadResult m_87097_(long p_87098_, int p_87099_) throws InterruptedException {
      Thread.sleep(Duration.ofSeconds(p_87098_).toMillis());
      return this.m_87079_(p_87099_ + 1);
   }

   private long m_87086_(HttpResponse p_87087_) {
      return Optional.ofNullable(p_87087_.getFirstHeader("Retry-After")).map(Header::getValue).map(Long::valueOf).orElse(0L);
   }

   public boolean m_87096_() {
      return this.f_87067_.isDone() || this.f_87067_.isCancelled();
   }

   @OnlyIn(Dist.CLIENT)
   static class CustomInputStreamEntity extends InputStreamEntity {
      private final long f_87101_;
      private final InputStream f_87102_;
      private final UploadStatus f_87103_;

      public CustomInputStreamEntity(InputStream p_87105_, long p_87106_, UploadStatus p_87107_) {
         super(p_87105_);
         this.f_87102_ = p_87105_;
         this.f_87101_ = p_87106_;
         this.f_87103_ = p_87107_;
      }

      public void writeTo(OutputStream p_87109_) throws IOException {
         Args.notNull(p_87109_, "Output stream");
         InputStream inputstream = this.f_87102_;

         try {
            byte[] abyte = new byte[4096];
            int j;
            if (this.f_87101_ < 0L) {
               while((j = inputstream.read(abyte)) != -1) {
                  p_87109_.write(abyte, 0, j);
                  this.f_87103_.f_87386_ += (long)j;
               }
            } else {
               long i = this.f_87101_;

               while(i > 0L) {
                  j = inputstream.read(abyte, 0, (int)Math.min(4096L, i));
                  if (j == -1) {
                     break;
                  }

                  p_87109_.write(abyte, 0, j);
                  this.f_87103_.f_87386_ += (long)j;
                  i -= (long)j;
                  p_87109_.flush();
               }
            }
         } finally {
            inputstream.close();
         }

      }
   }
}