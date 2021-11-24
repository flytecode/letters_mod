package com.mojang.blaze3d.platform;

import com.google.common.base.Charsets;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.EnumSet;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.stb.STBIWriteCallback;
import org.lwjgl.stb.STBImage;
import org.lwjgl.stb.STBImageResize;
import org.lwjgl.stb.STBImageWrite;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
public final class NativeImage implements AutoCloseable {
   private static final Logger f_84958_ = LogManager.getLogger();
   private static final int f_166396_ = 24;
   private static final int f_166397_ = 16;
   private static final int f_166398_ = 8;
   private static final int f_166399_ = 0;
   private static final Set<StandardOpenOption> f_84959_ = EnumSet.of(StandardOpenOption.WRITE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
   private final NativeImage.Format f_84960_;
   private final int f_84961_;
   private final int f_84962_;
   private final boolean f_84963_;
   private long f_84964_;
   private final long f_84965_;

   public NativeImage(int p_84968_, int p_84969_, boolean p_84970_) {
      this(NativeImage.Format.RGBA, p_84968_, p_84969_, p_84970_);
   }

   public NativeImage(NativeImage.Format p_84972_, int p_84973_, int p_84974_, boolean p_84975_) {
      if (p_84973_ > 0 && p_84974_ > 0) {
         this.f_84960_ = p_84972_;
         this.f_84961_ = p_84973_;
         this.f_84962_ = p_84974_;
         this.f_84965_ = (long)p_84973_ * (long)p_84974_ * (long)p_84972_.m_85161_();
         this.f_84963_ = false;
         if (p_84975_) {
            this.f_84964_ = MemoryUtil.nmemCalloc(1L, this.f_84965_);
         } else {
            this.f_84964_ = MemoryUtil.nmemAlloc(this.f_84965_);
         }

      } else {
         throw new IllegalArgumentException("Invalid texture size: " + p_84973_ + "x" + p_84974_);
      }
   }

   private NativeImage(NativeImage.Format p_84977_, int p_84978_, int p_84979_, boolean p_84980_, long p_84981_) {
      if (p_84978_ > 0 && p_84979_ > 0) {
         this.f_84960_ = p_84977_;
         this.f_84961_ = p_84978_;
         this.f_84962_ = p_84979_;
         this.f_84963_ = p_84980_;
         this.f_84964_ = p_84981_;
         this.f_84965_ = (long)p_84978_ * (long)p_84979_ * (long)p_84977_.m_85161_();
      } else {
         throw new IllegalArgumentException("Invalid texture size: " + p_84978_ + "x" + p_84979_);
      }
   }

   public String toString() {
      return "NativeImage[" + this.f_84960_ + " " + this.f_84961_ + "x" + this.f_84962_ + "@" + this.f_84964_ + (this.f_84963_ ? "S" : "N") + "]";
   }

   private boolean m_166422_(int p_166423_, int p_166424_) {
      return p_166423_ < 0 || p_166423_ >= this.f_84961_ || p_166424_ < 0 || p_166424_ >= this.f_84962_;
   }

   public static NativeImage m_85058_(InputStream p_85059_) throws IOException {
      return m_85048_(NativeImage.Format.RGBA, p_85059_);
   }

   public static NativeImage m_85048_(@Nullable NativeImage.Format p_85049_, InputStream p_85050_) throws IOException {
      ByteBuffer bytebuffer = null;

      NativeImage nativeimage;
      try {
         bytebuffer = TextureUtil.m_85303_(p_85050_);
         bytebuffer.rewind();
         nativeimage = m_85051_(p_85049_, bytebuffer);
      } finally {
         MemoryUtil.memFree(bytebuffer);
         IOUtils.closeQuietly(p_85050_);
      }

      return nativeimage;
   }

   public static NativeImage m_85062_(ByteBuffer p_85063_) throws IOException {
      return m_85051_(NativeImage.Format.RGBA, p_85063_);
   }

   public static NativeImage m_85051_(@Nullable NativeImage.Format p_85052_, ByteBuffer p_85053_) throws IOException {
      if (p_85052_ != null && !p_85052_.m_85175_()) {
         throw new UnsupportedOperationException("Don't know how to read format " + p_85052_);
      } else if (MemoryUtil.memAddress(p_85053_) == 0L) {
         throw new IllegalArgumentException("Invalid buffer");
      } else {
         MemoryStack memorystack = MemoryStack.stackPush();

         NativeImage nativeimage;
         try {
            IntBuffer intbuffer = memorystack.mallocInt(1);
            IntBuffer intbuffer1 = memorystack.mallocInt(1);
            IntBuffer intbuffer2 = memorystack.mallocInt(1);
            ByteBuffer bytebuffer = STBImage.stbi_load_from_memory(p_85053_, intbuffer, intbuffer1, intbuffer2, p_85052_ == null ? 0 : p_85052_.f_85130_);
            if (bytebuffer == null) {
               throw new IOException("Could not load image: " + STBImage.stbi_failure_reason());
            }

            nativeimage = new NativeImage(p_85052_ == null ? NativeImage.Format.m_85167_(intbuffer2.get(0)) : p_85052_, intbuffer.get(0), intbuffer1.get(0), true, MemoryUtil.memAddress(bytebuffer));
         } catch (Throwable throwable1) {
            if (memorystack != null) {
               try {
                  memorystack.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (memorystack != null) {
            memorystack.close();
         }

         return nativeimage;
      }
   }

   private static void m_85081_(boolean p_85082_, boolean p_85083_) {
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      if (p_85082_) {
         GlStateManager.m_84331_(3553, 10241, p_85083_ ? 9987 : 9729);
         GlStateManager.m_84331_(3553, 10240, 9729);
      } else {
         GlStateManager.m_84331_(3553, 10241, p_85083_ ? 9986 : 9728);
         GlStateManager.m_84331_(3553, 10240, 9728);
      }

   }

   private void m_85124_() {
      if (this.f_84964_ == 0L) {
         throw new IllegalStateException("Image is not allocated.");
      }
   }

   public void close() {
      if (this.f_84964_ != 0L) {
         if (this.f_84963_) {
            STBImage.nstbi_image_free(this.f_84964_);
         } else {
            MemoryUtil.nmemFree(this.f_84964_);
         }
      }

      this.f_84964_ = 0L;
   }

   public int m_84982_() {
      return this.f_84961_;
   }

   public int m_85084_() {
      return this.f_84962_;
   }

   public NativeImage.Format m_85102_() {
      return this.f_84960_;
   }

   public int m_84985_(int p_84986_, int p_84987_) {
      if (this.f_84960_ != NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.f_84960_));
      } else if (this.m_166422_(p_84986_, p_84987_)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", p_84986_, p_84987_, this.f_84961_, this.f_84962_));
      } else {
         this.m_85124_();
         long i = ((long)p_84986_ + (long)p_84987_ * (long)this.f_84961_) * 4L;
         return MemoryUtil.memGetInt(this.f_84964_ + i);
      }
   }

   public void m_84988_(int p_84989_, int p_84990_, int p_84991_) {
      if (this.f_84960_ != NativeImage.Format.RGBA) {
         throw new IllegalArgumentException(String.format("getPixelRGBA only works on RGBA images; have %s", this.f_84960_));
      } else if (this.m_166422_(p_84989_, p_84990_)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", p_84989_, p_84990_, this.f_84961_, this.f_84962_));
      } else {
         this.m_85124_();
         long i = ((long)p_84989_ + (long)p_84990_ * (long)this.f_84961_) * 4L;
         MemoryUtil.memPutInt(this.f_84964_ + i, p_84991_);
      }
   }

   public void m_166402_(int p_166403_, int p_166404_, byte p_166405_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      if (!this.f_84960_.m_166428_()) {
         throw new IllegalArgumentException(String.format("setPixelLuminance only works on image with luminance; have %s", this.f_84960_));
      } else if (this.m_166422_(p_166403_, p_166404_)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", p_166403_, p_166404_, this.f_84961_, this.f_84962_));
      } else {
         this.m_85124_();
         long i = ((long)p_166403_ + (long)p_166404_ * (long)this.f_84961_) * (long)this.f_84960_.m_85161_() + (long)(this.f_84960_.m_166432_() / 8);
         MemoryUtil.memPutByte(this.f_84964_ + i, p_166405_);
      }
   }

   public byte m_166408_(int p_166409_, int p_166410_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      if (!this.f_84960_.m_166433_()) {
         throw new IllegalArgumentException(String.format("no red or luminance in %s", this.f_84960_));
      } else if (this.m_166422_(p_166409_, p_166410_)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", p_166409_, p_166410_, this.f_84961_, this.f_84962_));
      } else {
         int i = (p_166409_ + p_166410_ * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_166436_() / 8;
         return MemoryUtil.memGetByte(this.f_84964_ + (long)i);
      }
   }

   public byte m_166415_(int p_166416_, int p_166417_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      if (!this.f_84960_.m_166434_()) {
         throw new IllegalArgumentException(String.format("no green or luminance in %s", this.f_84960_));
      } else if (this.m_166422_(p_166416_, p_166417_)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", p_166416_, p_166417_, this.f_84961_, this.f_84962_));
      } else {
         int i = (p_166416_ + p_166417_ * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_166437_() / 8;
         return MemoryUtil.memGetByte(this.f_84964_ + (long)i);
      }
   }

   public byte m_166418_(int p_166419_, int p_166420_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      if (!this.f_84960_.m_166435_()) {
         throw new IllegalArgumentException(String.format("no blue or luminance in %s", this.f_84960_));
      } else if (this.m_166422_(p_166419_, p_166420_)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", p_166419_, p_166420_, this.f_84961_, this.f_84962_));
      } else {
         int i = (p_166419_ + p_166420_ * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_166438_() / 8;
         return MemoryUtil.memGetByte(this.f_84964_ + (long)i);
      }
   }

   public byte m_85087_(int p_85088_, int p_85089_) {
      if (!this.f_84960_.m_85173_()) {
         throw new IllegalArgumentException(String.format("no luminance or alpha in %s", this.f_84960_));
      } else if (this.m_166422_(p_85088_, p_85089_)) {
         throw new IllegalArgumentException(String.format("(%s, %s) outside of image bounds (%s, %s)", p_85088_, p_85089_, this.f_84961_, this.f_84962_));
      } else {
         int i = (p_85088_ + p_85089_ * this.f_84961_) * this.f_84960_.m_85161_() + this.f_84960_.m_85174_() / 8;
         return MemoryUtil.memGetByte(this.f_84964_ + (long)i);
      }
   }

   public void m_166411_(int p_166412_, int p_166413_, int p_166414_) {
      if (this.f_84960_ != NativeImage.Format.RGBA) {
         throw new UnsupportedOperationException("Can only call blendPixel with RGBA format");
      } else {
         int i = this.m_84985_(p_166412_, p_166413_);
         float f = (float)m_84983_(p_166414_) / 255.0F;
         float f1 = (float)m_85119_(p_166414_) / 255.0F;
         float f2 = (float)m_85103_(p_166414_) / 255.0F;
         float f3 = (float)m_85085_(p_166414_) / 255.0F;
         float f4 = (float)m_84983_(i) / 255.0F;
         float f5 = (float)m_85119_(i) / 255.0F;
         float f6 = (float)m_85103_(i) / 255.0F;
         float f7 = (float)m_85085_(i) / 255.0F;
         float f8 = 1.0F - f;
         float f9 = f * f + f4 * f8;
         float f10 = f1 * f + f5 * f8;
         float f11 = f2 * f + f6 * f8;
         float f12 = f3 * f + f7 * f8;
         if (f9 > 1.0F) {
            f9 = 1.0F;
         }

         if (f10 > 1.0F) {
            f10 = 1.0F;
         }

         if (f11 > 1.0F) {
            f11 = 1.0F;
         }

         if (f12 > 1.0F) {
            f12 = 1.0F;
         }

         int j = (int)(f9 * 255.0F);
         int k = (int)(f10 * 255.0F);
         int l = (int)(f11 * 255.0F);
         int i1 = (int)(f12 * 255.0F);
         this.m_84988_(p_166412_, p_166413_, m_84992_(j, k, l, i1));
      }
   }

   @Deprecated
   public int[] m_85118_() {
      if (this.f_84960_ != NativeImage.Format.RGBA) {
         throw new UnsupportedOperationException("can only call makePixelArray for RGBA images.");
      } else {
         this.m_85124_();
         int[] aint = new int[this.m_84982_() * this.m_85084_()];

         for(int i = 0; i < this.m_85084_(); ++i) {
            for(int j = 0; j < this.m_84982_(); ++j) {
               int k = this.m_84985_(j, i);
               int l = m_84983_(k);
               int i1 = m_85119_(k);
               int j1 = m_85103_(k);
               int k1 = m_85085_(k);
               int l1 = l << 24 | k1 << 16 | j1 << 8 | i1;
               aint[j + i * this.m_84982_()] = l1;
            }
         }

         return aint;
      }
   }

   public void m_85040_(int p_85041_, int p_85042_, int p_85043_, boolean p_85044_) {
      this.m_85003_(p_85041_, p_85042_, p_85043_, 0, 0, this.f_84961_, this.f_84962_, false, p_85044_);
   }

   public void m_85003_(int p_85004_, int p_85005_, int p_85006_, int p_85007_, int p_85008_, int p_85009_, int p_85010_, boolean p_85011_, boolean p_85012_) {
      this.m_85013_(p_85004_, p_85005_, p_85006_, p_85007_, p_85008_, p_85009_, p_85010_, false, false, p_85011_, p_85012_);
   }

   public void m_85013_(int p_85014_, int p_85015_, int p_85016_, int p_85017_, int p_85018_, int p_85019_, int p_85020_, boolean p_85021_, boolean p_85022_, boolean p_85023_, boolean p_85024_) {
      if (!RenderSystem.m_69587_()) {
         RenderSystem.m_69879_(() -> {
            this.m_85090_(p_85014_, p_85015_, p_85016_, p_85017_, p_85018_, p_85019_, p_85020_, p_85021_, p_85022_, p_85023_, p_85024_);
         });
      } else {
         this.m_85090_(p_85014_, p_85015_, p_85016_, p_85017_, p_85018_, p_85019_, p_85020_, p_85021_, p_85022_, p_85023_, p_85024_);
      }

   }

   private void m_85090_(int p_85091_, int p_85092_, int p_85093_, int p_85094_, int p_85095_, int p_85096_, int p_85097_, boolean p_85098_, boolean p_85099_, boolean p_85100_, boolean p_85101_) {
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      this.m_85124_();
      m_85081_(p_85098_, p_85100_);
      if (p_85096_ == this.m_84982_()) {
         GlStateManager.m_84522_(3314, 0);
      } else {
         GlStateManager.m_84522_(3314, this.m_84982_());
      }

      GlStateManager.m_84522_(3316, p_85094_);
      GlStateManager.m_84522_(3315, p_85095_);
      this.f_84960_.m_85169_();
      GlStateManager.m_84199_(3553, p_85091_, p_85092_, p_85093_, p_85096_, p_85097_, this.f_84960_.m_85170_(), 5121, this.f_84964_);
      if (p_85099_) {
         GlStateManager.m_84331_(3553, 10242, 33071);
         GlStateManager.m_84331_(3553, 10243, 33071);
      }

      if (p_85101_) {
         this.close();
      }

   }

   public void m_85045_(int p_85046_, boolean p_85047_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      this.m_85124_();
      this.f_84960_.m_85166_();
      GlStateManager.m_84227_(3553, p_85046_, this.f_84960_.m_85170_(), 5121, this.f_84964_);
      if (p_85047_ && this.f_84960_.m_85171_()) {
         for(int i = 0; i < this.m_85084_(); ++i) {
            for(int j = 0; j < this.m_84982_(); ++j) {
               this.m_84988_(j, i, this.m_84985_(j, i) | 255 << this.f_84960_.m_85172_());
            }
         }
      }

   }

   public void m_166400_(float p_166401_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      if (this.f_84960_.m_85161_() != 1) {
         throw new IllegalStateException("Depth buffer must be stored in NativeImage with 1 component.");
      } else {
         this.m_85124_();
         this.f_84960_.m_85166_();
         GlStateManager.m_157100_(0, 0, this.f_84961_, this.f_84962_, 6402, 5121, this.f_84964_);
      }
   }

   public void m_166421_() {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      this.f_84960_.m_85169_();
      GlStateManager.m_157078_(this.f_84961_, this.f_84962_, this.f_84960_.m_85170_(), 5121, this.f_84964_);
   }

   public void m_166406_(String p_166407_) throws IOException {
      this.m_85066_(FileSystems.getDefault().getPath(p_166407_));
   }

   public void m_85056_(File p_85057_) throws IOException {
      this.m_85066_(p_85057_.toPath());
   }

   public void m_85068_(STBTTFontinfo p_85069_, int p_85070_, int p_85071_, int p_85072_, float p_85073_, float p_85074_, float p_85075_, float p_85076_, int p_85077_, int p_85078_) {
      if (p_85077_ >= 0 && p_85077_ + p_85071_ <= this.m_84982_() && p_85078_ >= 0 && p_85078_ + p_85072_ <= this.m_85084_()) {
         if (this.f_84960_.m_85161_() != 1) {
            throw new IllegalArgumentException("Can only write fonts into 1-component images.");
         } else {
            STBTruetype.nstbtt_MakeGlyphBitmapSubpixel(p_85069_.address(), this.f_84964_ + (long)p_85077_ + (long)(p_85078_ * this.m_84982_()), p_85071_, p_85072_, this.m_84982_(), p_85073_, p_85074_, p_85075_, p_85076_, p_85070_);
         }
      } else {
         throw new IllegalArgumentException(String.format("Out of bounds: start: (%s, %s) (size: %sx%s); size: %sx%s", p_85077_, p_85078_, p_85071_, p_85072_, this.m_84982_(), this.m_85084_()));
      }
   }

   public void m_85066_(Path p_85067_) throws IOException {
      if (!this.f_84960_.m_85175_()) {
         throw new UnsupportedOperationException("Don't know how to write format " + this.f_84960_);
      } else {
         this.m_85124_();
         WritableByteChannel writablebytechannel = Files.newByteChannel(p_85067_, f_84959_);

         try {
            if (!this.m_85064_(writablebytechannel)) {
               throw new IOException("Could not write image to the PNG file \"" + p_85067_.toAbsolutePath() + "\": " + STBImage.stbi_failure_reason());
            }
         } catch (Throwable throwable1) {
            if (writablebytechannel != null) {
               try {
                  writablebytechannel.close();
               } catch (Throwable throwable) {
                  throwable1.addSuppressed(throwable);
               }
            }

            throw throwable1;
         }

         if (writablebytechannel != null) {
            writablebytechannel.close();
         }

      }
   }

   public byte[] m_85121_() throws IOException {
      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();

      byte[] abyte;
      try {
         WritableByteChannel writablebytechannel = Channels.newChannel(bytearrayoutputstream);

         try {
            if (!this.m_85064_(writablebytechannel)) {
               throw new IOException("Could not write image to byte array: " + STBImage.stbi_failure_reason());
            }

            abyte = bytearrayoutputstream.toByteArray();
         } catch (Throwable throwable2) {
            if (writablebytechannel != null) {
               try {
                  writablebytechannel.close();
               } catch (Throwable throwable1) {
                  throwable2.addSuppressed(throwable1);
               }
            }

            throw throwable2;
         }

         if (writablebytechannel != null) {
            writablebytechannel.close();
         }
      } catch (Throwable throwable3) {
         try {
            bytearrayoutputstream.close();
         } catch (Throwable throwable) {
            throwable3.addSuppressed(throwable);
         }

         throw throwable3;
      }

      bytearrayoutputstream.close();
      return abyte;
   }

   private boolean m_85064_(WritableByteChannel p_85065_) throws IOException {
      NativeImage.WriteCallback nativeimage$writecallback = new NativeImage.WriteCallback(p_85065_);

      boolean flag;
      try {
         int i = Math.min(this.m_85084_(), Integer.MAX_VALUE / this.m_84982_() / this.f_84960_.m_85161_());
         if (i < this.m_85084_()) {
            f_84958_.warn("Dropping image height from {} to {} to fit the size into 32-bit signed int", this.m_85084_(), i);
         }

         if (STBImageWrite.nstbi_write_png_to_func(nativeimage$writecallback.address(), 0L, this.m_84982_(), i, this.f_84960_.m_85161_(), this.f_84964_, 0) != 0) {
            nativeimage$writecallback.m_85202_();
            return true;
         }

         flag = false;
      } finally {
         nativeimage$writecallback.free();
      }

      return flag;
   }

   public void m_85054_(NativeImage p_85055_) {
      if (p_85055_.m_85102_() != this.f_84960_) {
         throw new UnsupportedOperationException("Image formats don't match.");
      } else {
         int i = this.f_84960_.m_85161_();
         this.m_85124_();
         p_85055_.m_85124_();
         if (this.f_84961_ == p_85055_.f_84961_) {
            MemoryUtil.memCopy(p_85055_.f_84964_, this.f_84964_, Math.min(this.f_84965_, p_85055_.f_84965_));
         } else {
            int j = Math.min(this.m_84982_(), p_85055_.m_84982_());
            int k = Math.min(this.m_85084_(), p_85055_.m_85084_());

            for(int l = 0; l < k; ++l) {
               int i1 = l * p_85055_.m_84982_() * i;
               int j1 = l * this.m_84982_() * i;
               MemoryUtil.memCopy(p_85055_.f_84964_ + (long)i1, this.f_84964_ + (long)j1, (long)j);
            }
         }

      }
   }

   public void m_84997_(int p_84998_, int p_84999_, int p_85000_, int p_85001_, int p_85002_) {
      for(int i = p_84999_; i < p_84999_ + p_85001_; ++i) {
         for(int j = p_84998_; j < p_84998_ + p_85000_; ++j) {
            this.m_84988_(j, i, p_85002_);
         }
      }

   }

   public void m_85025_(int p_85026_, int p_85027_, int p_85028_, int p_85029_, int p_85030_, int p_85031_, boolean p_85032_, boolean p_85033_) {
      for(int i = 0; i < p_85031_; ++i) {
         for(int j = 0; j < p_85030_; ++j) {
            int k = p_85032_ ? p_85030_ - 1 - j : j;
            int l = p_85033_ ? p_85031_ - 1 - i : i;
            int i1 = this.m_84985_(p_85026_ + j, p_85027_ + i);
            this.m_84988_(p_85026_ + p_85028_ + k, p_85027_ + p_85029_ + l, i1);
         }
      }

   }

   public void m_85122_() {
      this.m_85124_();
      MemoryStack memorystack = MemoryStack.stackPush();

      try {
         int i = this.f_84960_.m_85161_();
         int j = this.m_84982_() * i;
         long k = memorystack.nmalloc(j);

         for(int l = 0; l < this.m_85084_() / 2; ++l) {
            int i1 = l * this.m_84982_() * i;
            int j1 = (this.m_85084_() - 1 - l) * this.m_84982_() * i;
            MemoryUtil.memCopy(this.f_84964_ + (long)i1, k, (long)j);
            MemoryUtil.memCopy(this.f_84964_ + (long)j1, this.f_84964_ + (long)i1, (long)j);
            MemoryUtil.memCopy(k, this.f_84964_ + (long)j1, (long)j);
         }
      } catch (Throwable throwable1) {
         if (memorystack != null) {
            try {
               memorystack.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (memorystack != null) {
         memorystack.close();
      }

   }

   public void m_85034_(int p_85035_, int p_85036_, int p_85037_, int p_85038_, NativeImage p_85039_) {
      this.m_85124_();
      if (p_85039_.m_85102_() != this.f_84960_) {
         throw new UnsupportedOperationException("resizeSubRectTo only works for images of the same format.");
      } else {
         int i = this.f_84960_.m_85161_();
         STBImageResize.nstbir_resize_uint8(this.f_84964_ + (long)((p_85035_ + p_85036_ * this.m_84982_()) * i), p_85037_, p_85038_, this.m_84982_() * i, p_85039_.f_84964_, p_85039_.m_84982_(), p_85039_.m_85084_(), 0, i);
      }
   }

   public void m_85123_() {
      DebugMemoryUntracker.m_84001_(this.f_84964_);
   }

   public static NativeImage m_85060_(String p_85061_) throws IOException {
      byte[] abyte = Base64.getDecoder().decode(p_85061_.replaceAll("\n", "").getBytes(Charsets.UTF_8));
      MemoryStack memorystack = MemoryStack.stackPush();

      NativeImage nativeimage;
      try {
         ByteBuffer bytebuffer = memorystack.malloc(abyte.length);
         bytebuffer.put(abyte);
         bytebuffer.rewind();
         nativeimage = m_85062_(bytebuffer);
      } catch (Throwable throwable1) {
         if (memorystack != null) {
            try {
               memorystack.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (memorystack != null) {
         memorystack.close();
      }

      return nativeimage;
   }

   public static int m_84983_(int p_84984_) {
      return p_84984_ >> 24 & 255;
   }

   public static int m_85085_(int p_85086_) {
      return p_85086_ >> 0 & 255;
   }

   public static int m_85103_(int p_85104_) {
      return p_85104_ >> 8 & 255;
   }

   public static int m_85119_(int p_85120_) {
      return p_85120_ >> 16 & 255;
   }

   public static int m_84992_(int p_84993_, int p_84994_, int p_84995_, int p_84996_) {
      return (p_84993_ & 255) << 24 | (p_84994_ & 255) << 16 | (p_84995_ & 255) << 8 | (p_84996_ & 255) << 0;
   }

   @OnlyIn(Dist.CLIENT)
   public static enum Format {
      RGBA(4, 6408, true, true, true, false, true, 0, 8, 16, 255, 24, true),
      RGB(3, 6407, true, true, true, false, false, 0, 8, 16, 255, 255, true),
      LUMINANCE_ALPHA(2, 33319, false, false, false, true, true, 255, 255, 255, 0, 8, true),
      LUMINANCE(1, 6403, false, false, false, true, false, 0, 0, 0, 0, 255, true);

      final int f_85130_;
      private final int f_85131_;
      private final boolean f_85132_;
      private final boolean f_85133_;
      private final boolean f_85134_;
      private final boolean f_85135_;
      private final boolean f_85136_;
      private final int f_85137_;
      private final int f_85138_;
      private final int f_85139_;
      private final int f_85140_;
      private final int f_85141_;
      private final boolean f_85142_;

      private Format(int p_85148_, int p_85149_, boolean p_85150_, boolean p_85151_, boolean p_85152_, boolean p_85153_, boolean p_85154_, int p_85155_, int p_85156_, int p_85157_, int p_85158_, int p_85159_, boolean p_85160_) {
         this.f_85130_ = p_85148_;
         this.f_85131_ = p_85149_;
         this.f_85132_ = p_85150_;
         this.f_85133_ = p_85151_;
         this.f_85134_ = p_85152_;
         this.f_85135_ = p_85153_;
         this.f_85136_ = p_85154_;
         this.f_85137_ = p_85155_;
         this.f_85138_ = p_85156_;
         this.f_85139_ = p_85157_;
         this.f_85140_ = p_85158_;
         this.f_85141_ = p_85159_;
         this.f_85142_ = p_85160_;
      }

      public int m_85161_() {
         return this.f_85130_;
      }

      public void m_85166_() {
         RenderSystem.m_69393_(RenderSystem::m_69586_);
         GlStateManager.m_84522_(3333, this.m_85161_());
      }

      public void m_85169_() {
         RenderSystem.m_69393_(RenderSystem::m_69587_);
         GlStateManager.m_84522_(3317, this.m_85161_());
      }

      public int m_85170_() {
         return this.f_85131_;
      }

      public boolean m_166425_() {
         return this.f_85132_;
      }

      public boolean m_166426_() {
         return this.f_85133_;
      }

      public boolean m_166427_() {
         return this.f_85134_;
      }

      public boolean m_166428_() {
         return this.f_85135_;
      }

      public boolean m_85171_() {
         return this.f_85136_;
      }

      public int m_166429_() {
         return this.f_85137_;
      }

      public int m_166430_() {
         return this.f_85138_;
      }

      public int m_166431_() {
         return this.f_85139_;
      }

      public int m_166432_() {
         return this.f_85140_;
      }

      public int m_85172_() {
         return this.f_85141_;
      }

      public boolean m_166433_() {
         return this.f_85135_ || this.f_85132_;
      }

      public boolean m_166434_() {
         return this.f_85135_ || this.f_85133_;
      }

      public boolean m_166435_() {
         return this.f_85135_ || this.f_85134_;
      }

      public boolean m_85173_() {
         return this.f_85135_ || this.f_85136_;
      }

      public int m_166436_() {
         return this.f_85135_ ? this.f_85140_ : this.f_85137_;
      }

      public int m_166437_() {
         return this.f_85135_ ? this.f_85140_ : this.f_85138_;
      }

      public int m_166438_() {
         return this.f_85135_ ? this.f_85140_ : this.f_85139_;
      }

      public int m_85174_() {
         return this.f_85135_ ? this.f_85140_ : this.f_85141_;
      }

      public boolean m_85175_() {
         return this.f_85142_;
      }

      static NativeImage.Format m_85167_(int p_85168_) {
         switch(p_85168_) {
         case 1:
            return LUMINANCE;
         case 2:
            return LUMINANCE_ALPHA;
         case 3:
            return RGB;
         case 4:
         default:
            return RGBA;
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static enum InternalGlFormat {
      RGBA(6408),
      RGB(6407),
      RG(33319),
      RED(6403);

      private final int f_85184_;

      private InternalGlFormat(int p_85190_) {
         this.f_85184_ = p_85190_;
      }

      public int m_85191_() {
         return this.f_85184_;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class WriteCallback extends STBIWriteCallback {
      private final WritableByteChannel f_85195_;
      @Nullable
      private IOException f_85196_;

      WriteCallback(WritableByteChannel p_85198_) {
         this.f_85195_ = p_85198_;
      }

      public void invoke(long p_85204_, long p_85205_, int p_85206_) {
         ByteBuffer bytebuffer = getData(p_85205_, p_85206_);

         try {
            this.f_85195_.write(bytebuffer);
         } catch (IOException ioexception) {
            this.f_85196_ = ioexception;
         }

      }

      public void m_85202_() throws IOException {
         if (this.f_85196_ != null) {
            throw this.f_85196_;
         }
      }
   }
}