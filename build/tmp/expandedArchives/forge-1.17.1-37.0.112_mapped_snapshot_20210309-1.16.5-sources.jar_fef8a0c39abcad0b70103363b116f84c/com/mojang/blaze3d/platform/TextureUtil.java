package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
@DontObfuscate
public class TextureUtil {
   private static final Logger f_85278_ = LogManager.getLogger();
   public static final int f_157132_ = 0;
   private static final int f_157131_ = 8192;

   public static int m_85280_() {
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      if (SharedConstants.f_136183_) {
         int[] aint = new int[ThreadLocalRandom.current().nextInt(15) + 1];
         GlStateManager.m_84305_(aint);
         int i = GlStateManager.m_84111_();
         GlStateManager.m_84365_(aint);
         return i;
      } else {
         return GlStateManager.m_84111_();
      }
   }

   public static void m_85281_(int p_85282_) {
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      GlStateManager.m_84541_(p_85282_);
   }

   public static void m_85283_(int p_85284_, int p_85285_, int p_85286_) {
      m_85297_(NativeImage.InternalGlFormat.RGBA, p_85284_, 0, p_85285_, p_85286_);
   }

   public static void m_85292_(NativeImage.InternalGlFormat p_85293_, int p_85294_, int p_85295_, int p_85296_) {
      m_85297_(p_85293_, p_85294_, 0, p_85295_, p_85296_);
   }

   public static void m_85287_(int p_85288_, int p_85289_, int p_85290_, int p_85291_) {
      m_85297_(NativeImage.InternalGlFormat.RGBA, p_85288_, p_85289_, p_85290_, p_85291_);
   }

   public static void m_85297_(NativeImage.InternalGlFormat p_85298_, int p_85299_, int p_85300_, int p_85301_, int p_85302_) {
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      m_85309_(p_85299_);
      if (p_85300_ >= 0) {
         GlStateManager.m_84331_(3553, 33085, p_85300_);
         GlStateManager.m_84331_(3553, 33082, 0);
         GlStateManager.m_84331_(3553, 33083, p_85300_);
         GlStateManager.m_84160_(3553, 34049, 0.0F);
      }

      for(int i = 0; i <= p_85300_; ++i) {
         GlStateManager.m_84209_(3553, i, p_85298_.m_85191_(), p_85301_ >> i, p_85302_ >> i, 0, 6408, 5121, (IntBuffer)null);
      }

   }

   private static void m_85309_(int p_85310_) {
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      GlStateManager.m_84544_(p_85310_);
   }

   public static ByteBuffer m_85303_(InputStream p_85304_) throws IOException {
      ByteBuffer bytebuffer;
      if (p_85304_ instanceof FileInputStream) {
         FileInputStream fileinputstream = (FileInputStream)p_85304_;
         FileChannel filechannel = fileinputstream.getChannel();
         bytebuffer = MemoryUtil.memAlloc((int)filechannel.size() + 1);

         while(filechannel.read(bytebuffer) != -1) {
         }
      } else {
         bytebuffer = MemoryUtil.memAlloc(8192);
         ReadableByteChannel readablebytechannel = Channels.newChannel(p_85304_);

         while(readablebytechannel.read(bytebuffer) != -1) {
            if (bytebuffer.remaining() == 0) {
               bytebuffer = MemoryUtil.memRealloc(bytebuffer, bytebuffer.capacity() * 2);
            }
         }
      }

      return bytebuffer;
   }

   @Nullable
   public static String m_85311_(InputStream p_85312_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      ByteBuffer bytebuffer = null;

      try {
         bytebuffer = m_85303_(p_85312_);
         int i = bytebuffer.position();
         bytebuffer.rewind();
         return MemoryUtil.memASCII(bytebuffer, i);
      } catch (IOException ioexception) {
      } finally {
         if (bytebuffer != null) {
            MemoryUtil.memFree(bytebuffer);
         }

      }

      return null;
   }

   public static void m_157134_(String p_157135_, int p_157136_, int p_157137_, int p_157138_, int p_157139_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      m_85309_(p_157136_);

      for(int i = 0; i <= p_157137_; ++i) {
         String s = p_157135_ + "_" + i + ".png";
         int j = p_157138_ >> i;
         int k = p_157139_ >> i;

         try {
            NativeImage nativeimage = new NativeImage(j, k, false);

            try {
               nativeimage.m_85045_(i, false);
               nativeimage.m_166406_(s);
               f_85278_.debug("Exported png to: {}", (Object)(new File(s)).getAbsolutePath());
            } catch (Throwable throwable1) {
               try {
                  nativeimage.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }

               throw throwable1;
            }

            nativeimage.close();
         } catch (IOException ioexception) {
            f_85278_.debug("Unable to write: ", (Throwable)ioexception);
         }
      }

   }

   public static void m_85305_(IntBuffer p_85306_, int p_85307_, int p_85308_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      GL11.glPixelStorei(3312, 0);
      GL11.glPixelStorei(3313, 0);
      GL11.glPixelStorei(3314, 0);
      GL11.glPixelStorei(3315, 0);
      GL11.glPixelStorei(3316, 0);
      GL11.glPixelStorei(3317, 4);
      GL11.glTexImage2D(3553, 0, 6408, p_85307_, p_85308_, 0, 32993, 33639, p_85306_);
      GL11.glTexParameteri(3553, 10240, 9728);
      GL11.glTexParameteri(3553, 10241, 9729);
   }
}