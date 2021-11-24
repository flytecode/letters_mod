package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class HttpTexture extends SimpleTexture {
   private static final Logger f_117993_ = LogManager.getLogger();
   private static final int f_181889_ = 64;
   private static final int f_181890_ = 64;
   private static final int f_181891_ = 32;
   @Nullable
   private final File f_117994_;
   private final String f_117995_;
   private final boolean f_117996_;
   @Nullable
   private final Runnable f_117997_;
   @Nullable
   private CompletableFuture<?> f_117998_;
   private boolean f_117999_;

   public HttpTexture(@Nullable File p_118002_, String p_118003_, ResourceLocation p_118004_, boolean p_118005_, @Nullable Runnable p_118006_) {
      super(p_118004_);
      this.f_117994_ = p_118002_;
      this.f_117995_ = p_118003_;
      this.f_117996_ = p_118005_;
      this.f_117997_ = p_118006_;
   }

   private void m_118010_(NativeImage p_118011_) {
      if (this.f_117997_ != null) {
         this.f_117997_.run();
      }

      Minecraft.m_91087_().execute(() -> {
         this.f_117999_ = true;
         if (!RenderSystem.m_69586_()) {
            RenderSystem.m_69879_(() -> {
               this.m_118020_(p_118011_);
            });
         } else {
            this.m_118020_(p_118011_);
         }

      });
   }

   private void m_118020_(NativeImage p_118021_) {
      TextureUtil.m_85283_(this.m_117963_(), p_118021_.m_84982_(), p_118021_.m_85084_());
      p_118021_.m_85040_(0, 0, 0, true);
   }

   public void m_6704_(ResourceManager p_118009_) throws IOException {
      Minecraft.m_91087_().execute(() -> {
         if (!this.f_117999_) {
            try {
               super.m_6704_(p_118009_);
            } catch (IOException ioexception) {
               f_117993_.warn("Failed to load texture: {}", this.f_118129_, ioexception);
            }

            this.f_117999_ = true;
         }

      });
      if (this.f_117998_ == null) {
         NativeImage nativeimage;
         if (this.f_117994_ != null && this.f_117994_.isFile()) {
            f_117993_.debug("Loading http texture from local cache ({})", (Object)this.f_117994_);
            FileInputStream fileinputstream = new FileInputStream(this.f_117994_);
            nativeimage = this.m_118018_(fileinputstream);
         } else {
            nativeimage = null;
         }

         if (nativeimage != null) {
            this.m_118010_(nativeimage);
         } else {
            this.f_117998_ = CompletableFuture.runAsync(() -> {
               HttpURLConnection httpurlconnection = null;
               f_117993_.debug("Downloading http texture from {} to {}", this.f_117995_, this.f_117994_);

               try {
                  httpurlconnection = (HttpURLConnection)(new URL(this.f_117995_)).openConnection(Minecraft.m_91087_().m_91096_());
                  httpurlconnection.setDoInput(true);
                  httpurlconnection.setDoOutput(false);
                  httpurlconnection.connect();
                  if (httpurlconnection.getResponseCode() / 100 == 2) {
                     InputStream inputstream;
                     if (this.f_117994_ != null) {
                        FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), this.f_117994_);
                        inputstream = new FileInputStream(this.f_117994_);
                     } else {
                        inputstream = httpurlconnection.getInputStream();
                     }

                     Minecraft.m_91087_().execute(() -> {
                        NativeImage nativeimage1 = this.m_118018_(inputstream);
                        if (nativeimage1 != null) {
                           this.m_118010_(nativeimage1);
                        }

                     });
                     return;
                  }
               } catch (Exception exception) {
                  f_117993_.error("Couldn't download http texture", (Throwable)exception);
                  return;
               } finally {
                  if (httpurlconnection != null) {
                     httpurlconnection.disconnect();
                  }

               }

            }, Util.m_137578_());
         }
      }
   }

   @Nullable
   private NativeImage m_118018_(InputStream p_118019_) {
      NativeImage nativeimage = null;

      try {
         nativeimage = NativeImage.m_85058_(p_118019_);
         if (this.f_117996_) {
            nativeimage = this.m_118032_(nativeimage);
         }
      } catch (Exception exception) {
         f_117993_.warn("Error while loading the skin texture", (Throwable)exception);
      }

      return nativeimage;
   }

   @Nullable
   private NativeImage m_118032_(NativeImage p_118033_) {
      int i = p_118033_.m_85084_();
      int j = p_118033_.m_84982_();
      if (j == 64 && (i == 32 || i == 64)) {
         boolean flag = i == 32;
         if (flag) {
            NativeImage nativeimage = new NativeImage(64, 64, true);
            nativeimage.m_85054_(p_118033_);
            p_118033_.close();
            p_118033_ = nativeimage;
            nativeimage.m_84997_(0, 32, 64, 32, 0);
            nativeimage.m_85025_(4, 16, 16, 32, 4, 4, true, false);
            nativeimage.m_85025_(8, 16, 16, 32, 4, 4, true, false);
            nativeimage.m_85025_(0, 20, 24, 32, 4, 12, true, false);
            nativeimage.m_85025_(4, 20, 16, 32, 4, 12, true, false);
            nativeimage.m_85025_(8, 20, 8, 32, 4, 12, true, false);
            nativeimage.m_85025_(12, 20, 16, 32, 4, 12, true, false);
            nativeimage.m_85025_(44, 16, -8, 32, 4, 4, true, false);
            nativeimage.m_85025_(48, 16, -8, 32, 4, 4, true, false);
            nativeimage.m_85025_(40, 20, 0, 32, 4, 12, true, false);
            nativeimage.m_85025_(44, 20, -8, 32, 4, 12, true, false);
            nativeimage.m_85025_(48, 20, -16, 32, 4, 12, true, false);
            nativeimage.m_85025_(52, 20, -8, 32, 4, 12, true, false);
         }

         m_118022_(p_118033_, 0, 0, 32, 16);
         if (flag) {
            m_118012_(p_118033_, 32, 0, 64, 32);
         }

         m_118022_(p_118033_, 0, 16, 64, 32);
         m_118022_(p_118033_, 16, 48, 48, 64);
         return p_118033_;
      } else {
         p_118033_.close();
         f_117993_.warn("Discarding incorrectly sized ({}x{}) skin texture from {}", j, i, this.f_117995_);
         return null;
      }
   }

   private static void m_118012_(NativeImage p_118013_, int p_118014_, int p_118015_, int p_118016_, int p_118017_) {
      for(int i = p_118014_; i < p_118016_; ++i) {
         for(int j = p_118015_; j < p_118017_; ++j) {
            int k = p_118013_.m_84985_(i, j);
            if ((k >> 24 & 255) < 128) {
               return;
            }
         }
      }

      for(int l = p_118014_; l < p_118016_; ++l) {
         for(int i1 = p_118015_; i1 < p_118017_; ++i1) {
            p_118013_.m_84988_(l, i1, p_118013_.m_84985_(l, i1) & 16777215);
         }
      }

   }

   private static void m_118022_(NativeImage p_118023_, int p_118024_, int p_118025_, int p_118026_, int p_118027_) {
      for(int i = p_118024_; i < p_118026_; ++i) {
         for(int j = p_118025_; j < p_118027_; ++j) {
            p_118023_.m_84988_(i, j, p_118023_.m_84985_(i, j) | -16777216);
         }
      }

   }
}