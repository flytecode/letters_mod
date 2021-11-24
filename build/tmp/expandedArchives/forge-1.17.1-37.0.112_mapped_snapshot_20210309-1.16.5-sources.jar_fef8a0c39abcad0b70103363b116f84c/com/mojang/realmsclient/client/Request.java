package com.mojang.realmsclient.client;

import com.mojang.realmsclient.exception.RealmsHttpException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Request<T extends Request<T>> {
   protected HttpURLConnection f_87306_;
   private boolean f_87308_;
   protected String f_87307_;
   private static final int f_167283_ = 60000;
   private static final int f_167284_ = 5000;

   public Request(String p_87310_, int p_87311_, int p_87312_) {
      try {
         this.f_87307_ = p_87310_;
         Proxy proxy = RealmsClientConfig.m_87292_();
         if (proxy != null) {
            this.f_87306_ = (HttpURLConnection)(new URL(p_87310_)).openConnection(proxy);
         } else {
            this.f_87306_ = (HttpURLConnection)(new URL(p_87310_)).openConnection();
         }

         this.f_87306_.setConnectTimeout(p_87311_);
         this.f_87306_.setReadTimeout(p_87312_);
      } catch (MalformedURLException malformedurlexception) {
         throw new RealmsHttpException(malformedurlexception.getMessage(), malformedurlexception);
      } catch (IOException ioexception) {
         throw new RealmsHttpException(ioexception.getMessage(), ioexception);
      }
   }

   public void m_87322_(String p_87323_, String p_87324_) {
      m_87335_(this.f_87306_, p_87323_, p_87324_);
   }

   public static void m_87335_(HttpURLConnection p_87336_, String p_87337_, String p_87338_) {
      String s = p_87336_.getRequestProperty("Cookie");
      if (s == null) {
         p_87336_.setRequestProperty("Cookie", p_87337_ + "=" + p_87338_);
      } else {
         p_87336_.setRequestProperty("Cookie", s + ";" + p_87337_ + "=" + p_87338_);
      }

   }

   public T m_167285_(String p_167286_, String p_167287_) {
      this.f_87306_.addRequestProperty(p_167286_, p_167287_);
      return (T)this;
   }

   public int m_87313_() {
      return m_87330_(this.f_87306_);
   }

   public static int m_87330_(HttpURLConnection p_87331_) {
      String s = p_87331_.getHeaderField("Retry-After");

      try {
         return Integer.valueOf(s);
      } catch (Exception exception) {
         return 5;
      }
   }

   public int m_87339_() {
      try {
         this.m_87356_();
         return this.f_87306_.getResponseCode();
      } catch (Exception exception) {
         throw new RealmsHttpException(exception.getMessage(), exception);
      }
   }

   public String m_87350_() {
      try {
         this.m_87356_();
         String s = null;
         if (this.m_87339_() >= 400) {
            s = this.m_87314_(this.f_87306_.getErrorStream());
         } else {
            s = this.m_87314_(this.f_87306_.getInputStream());
         }

         this.m_87357_();
         return s;
      } catch (IOException ioexception) {
         throw new RealmsHttpException(ioexception.getMessage(), ioexception);
      }
   }

   private String m_87314_(InputStream p_87315_) throws IOException {
      if (p_87315_ == null) {
         return "";
      } else {
         InputStreamReader inputstreamreader = new InputStreamReader(p_87315_, "UTF-8");
         StringBuilder stringbuilder = new StringBuilder();

         for(int i = inputstreamreader.read(); i != -1; i = inputstreamreader.read()) {
            stringbuilder.append((char)i);
         }

         return stringbuilder.toString();
      }
   }

   private void m_87357_() {
      byte[] abyte = new byte[1024];

      try {
         InputStream inputstream = this.f_87306_.getInputStream();

         while(inputstream.read(abyte) > 0) {
         }

         inputstream.close();
         return;
      } catch (Exception exception) {
         try {
            InputStream inputstream1 = this.f_87306_.getErrorStream();
            if (inputstream1 != null) {
               while(inputstream1.read(abyte) > 0) {
               }

               inputstream1.close();
               return;
            }
         } catch (IOException ioexception) {
            return;
         }
      } finally {
         if (this.f_87306_ != null) {
            this.f_87306_.disconnect();
         }

      }

   }

   protected T m_87356_() {
      if (this.f_87308_) {
         return (T)this;
      } else {
         T t = this.m_7218_();
         this.f_87308_ = true;
         return t;
      }
   }

   protected abstract T m_7218_();

   public static Request<?> m_87316_(String p_87317_) {
      return new Request.Get(p_87317_, 5000, 60000);
   }

   public static Request<?> m_87318_(String p_87319_, int p_87320_, int p_87321_) {
      return new Request.Get(p_87319_, p_87320_, p_87321_);
   }

   public static Request<?> m_87342_(String p_87343_, String p_87344_) {
      return new Request.Post(p_87343_, p_87344_, 5000, 60000);
   }

   public static Request<?> m_87325_(String p_87326_, String p_87327_, int p_87328_, int p_87329_) {
      return new Request.Post(p_87326_, p_87327_, p_87328_, p_87329_);
   }

   public static Request<?> m_87340_(String p_87341_) {
      return new Request.Delete(p_87341_, 5000, 60000);
   }

   public static Request<?> m_87353_(String p_87354_, String p_87355_) {
      return new Request.Put(p_87354_, p_87355_, 5000, 60000);
   }

   public static Request<?> m_87345_(String p_87346_, String p_87347_, int p_87348_, int p_87349_) {
      return new Request.Put(p_87346_, p_87347_, p_87348_, p_87349_);
   }

   public String m_87351_(String p_87352_) {
      return m_87332_(this.f_87306_, p_87352_);
   }

   public static String m_87332_(HttpURLConnection p_87333_, String p_87334_) {
      try {
         return p_87333_.getHeaderField(p_87334_);
      } catch (Exception exception) {
         return "";
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Delete extends Request<Request.Delete> {
      public Delete(String p_87359_, int p_87360_, int p_87361_) {
         super(p_87359_, p_87360_, p_87361_);
      }

      public Request.Delete m_7218_() {
         try {
            this.f_87306_.setDoOutput(true);
            this.f_87306_.setRequestMethod("DELETE");
            this.f_87306_.connect();
            return this;
         } catch (Exception exception) {
            throw new RealmsHttpException(exception.getMessage(), exception);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Get extends Request<Request.Get> {
      public Get(String p_87365_, int p_87366_, int p_87367_) {
         super(p_87365_, p_87366_, p_87367_);
      }

      public Request.Get m_7218_() {
         try {
            this.f_87306_.setDoInput(true);
            this.f_87306_.setDoOutput(true);
            this.f_87306_.setUseCaches(false);
            this.f_87306_.setRequestMethod("GET");
            return this;
         } catch (Exception exception) {
            throw new RealmsHttpException(exception.getMessage(), exception);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Post extends Request<Request.Post> {
      private final String f_87370_;

      public Post(String p_87372_, String p_87373_, int p_87374_, int p_87375_) {
         super(p_87372_, p_87374_, p_87375_);
         this.f_87370_ = p_87373_;
      }

      public Request.Post m_7218_() {
         try {
            if (this.f_87370_ != null) {
               this.f_87306_.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            }

            this.f_87306_.setDoInput(true);
            this.f_87306_.setDoOutput(true);
            this.f_87306_.setUseCaches(false);
            this.f_87306_.setRequestMethod("POST");
            OutputStream outputstream = this.f_87306_.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream, "UTF-8");
            outputstreamwriter.write(this.f_87370_);
            outputstreamwriter.close();
            outputstream.flush();
            return this;
         } catch (Exception exception) {
            throw new RealmsHttpException(exception.getMessage(), exception);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class Put extends Request<Request.Put> {
      private final String f_87378_;

      public Put(String p_87380_, String p_87381_, int p_87382_, int p_87383_) {
         super(p_87380_, p_87382_, p_87383_);
         this.f_87378_ = p_87381_;
      }

      public Request.Put m_7218_() {
         try {
            if (this.f_87378_ != null) {
               this.f_87306_.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            }

            this.f_87306_.setDoOutput(true);
            this.f_87306_.setDoInput(true);
            this.f_87306_.setRequestMethod("PUT");
            OutputStream outputstream = this.f_87306_.getOutputStream();
            OutputStreamWriter outputstreamwriter = new OutputStreamWriter(outputstream, "UTF-8");
            outputstreamwriter.write(this.f_87378_);
            outputstreamwriter.close();
            outputstream.flush();
            return this;
         } catch (Exception exception) {
            throw new RealmsHttpException(exception.getMessage(), exception);
         }
      }
   }
}