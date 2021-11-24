package com.mojang.realmsclient.util;

import com.google.common.collect.Maps;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.util.UUIDTypeAdapter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsTextureManager {
   private static final Map<String, RealmsTextureManager.RealmsTexture> f_90178_ = Maps.newHashMap();
   static final Map<String, Boolean> f_90179_ = Maps.newHashMap();
   static final Map<String, String> f_90180_ = Maps.newHashMap();
   static final Logger f_90181_ = LogManager.getLogger();
   private static final ResourceLocation f_90182_ = new ResourceLocation("textures/gui/presets/isles.png");

   public static void m_90190_(String p_90191_, @Nullable String p_90192_) {
      if (p_90192_ == null) {
         RenderSystem.m_157456_(0, f_90182_);
      } else {
         int i = m_90196_(p_90191_, p_90192_);
         RenderSystem.m_157453_(0, i);
      }
   }

   public static void m_90187_(String p_90188_, Runnable p_90189_) {
      m_90185_(p_90188_);
      p_90189_.run();
   }

   private static void m_90193_(UUID p_90194_) {
      RenderSystem.m_157456_(0, DefaultPlayerSkin.m_118627_(p_90194_));
   }

   private static void m_90185_(final String p_90186_) {
      UUID uuid = UUIDTypeAdapter.fromString(p_90186_);
      if (f_90178_.containsKey(p_90186_)) {
         int j = (f_90178_.get(p_90186_)).f_90206_;
         RenderSystem.m_157453_(0, j);
      } else if (f_90179_.containsKey(p_90186_)) {
         if (!f_90179_.get(p_90186_)) {
            m_90193_(uuid);
         } else if (f_90180_.containsKey(p_90186_)) {
            int i = m_90196_(p_90186_, f_90180_.get(p_90186_));
            RenderSystem.m_157453_(0, i);
         } else {
            m_90193_(uuid);
         }

      } else {
         f_90179_.put(p_90186_, false);
         m_90193_(uuid);
         Thread thread = new Thread("Realms Texture Downloader") {
            public void run() {
               Map<Type, MinecraftProfileTexture> map = RealmsUtil.m_90225_(p_90186_);
               if (map.containsKey(Type.SKIN)) {
                  MinecraftProfileTexture minecraftprofiletexture = map.get(Type.SKIN);
                  String s = minecraftprofiletexture.getUrl();
                  HttpURLConnection httpurlconnection = null;
                  RealmsTextureManager.f_90181_.debug("Downloading http texture from {}", (Object)s);

                  try {
                     httpurlconnection = (HttpURLConnection)(new URL(s)).openConnection(Minecraft.m_91087_().m_91096_());
                     httpurlconnection.setDoInput(true);
                     httpurlconnection.setDoOutput(false);
                     httpurlconnection.connect();
                     if (httpurlconnection.getResponseCode() / 100 == 2) {
                        BufferedImage bufferedimage;
                        try {
                           bufferedimage = ImageIO.read(httpurlconnection.getInputStream());
                        } catch (Exception exception) {
                           RealmsTextureManager.f_90179_.remove(p_90186_);
                           return;
                        } finally {
                           IOUtils.closeQuietly(httpurlconnection.getInputStream());
                        }

                        bufferedimage = (new SkinProcessor()).m_90241_(bufferedimage);
                        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
                        ImageIO.write(bufferedimage, "png", bytearrayoutputstream);
                        RealmsTextureManager.f_90180_.put(p_90186_, (new Base64()).encodeToString(bytearrayoutputstream.toByteArray()));
                        RealmsTextureManager.f_90179_.put(p_90186_, true);
                        return;
                     }

                     RealmsTextureManager.f_90179_.remove(p_90186_);
                  } catch (Exception exception1) {
                     RealmsTextureManager.f_90181_.error("Couldn't download http texture", (Throwable)exception1);
                     RealmsTextureManager.f_90179_.remove(p_90186_);
                     return;
                  } finally {
                     if (httpurlconnection != null) {
                        httpurlconnection.disconnect();
                     }

                  }

               } else {
                  RealmsTextureManager.f_90179_.put(p_90186_, true);
               }
            }
         };
         thread.setDaemon(true);
         thread.start();
      }
   }

   private static int m_90196_(String p_90197_, String p_90198_) {
      RealmsTextureManager.RealmsTexture realmstexturemanager$realmstexture = f_90178_.get(p_90197_);
      if (realmstexturemanager$realmstexture != null && realmstexturemanager$realmstexture.f_90205_.equals(p_90198_)) {
         return realmstexturemanager$realmstexture.f_90206_;
      } else {
         int i;
         if (realmstexturemanager$realmstexture != null) {
            i = realmstexturemanager$realmstexture.f_90206_;
         } else {
            i = GlStateManager.m_84111_();
         }

         IntBuffer intbuffer = null;
         int j = 0;
         int k = 0;

         try {
            InputStream inputstream = new ByteArrayInputStream((new Base64()).decode(p_90198_));

            BufferedImage bufferedimage;
            try {
               bufferedimage = ImageIO.read(inputstream);
            } finally {
               IOUtils.closeQuietly(inputstream);
            }

            j = bufferedimage.getWidth();
            k = bufferedimage.getHeight();
            int[] aint = new int[j * k];
            bufferedimage.getRGB(0, 0, j, k, aint, 0, j);
            intbuffer = ByteBuffer.allocateDirect(4 * j * k).order(ByteOrder.nativeOrder()).asIntBuffer();
            intbuffer.put(aint);
            intbuffer.flip();
         } catch (IOException ioexception) {
            ioexception.printStackTrace();
         }

         RenderSystem.m_69388_(33984);
         RenderSystem.m_157184_(i);
         TextureUtil.m_85305_(intbuffer, j, k);
         f_90178_.put(p_90197_, new RealmsTextureManager.RealmsTexture(p_90198_, i));
         return i;
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class RealmsTexture {
      final String f_90205_;
      final int f_90206_;

      public RealmsTexture(String p_90208_, int p_90209_) {
         this.f_90205_ = p_90208_;
         this.f_90206_ = p_90209_;
      }
   }
}