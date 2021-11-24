package com.mojang.blaze3d.platform;

import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

@OnlyIn(Dist.CLIENT)
public final class VideoMode {
   private final int f_85313_;
   private final int f_85314_;
   private final int f_85315_;
   private final int f_85316_;
   private final int f_85317_;
   private final int f_85318_;
   private static final Pattern f_85319_ = Pattern.compile("(\\d+)x(\\d+)(?:@(\\d+)(?::(\\d+))?)?");

   public VideoMode(int p_85322_, int p_85323_, int p_85324_, int p_85325_, int p_85326_, int p_85327_) {
      this.f_85313_ = p_85322_;
      this.f_85314_ = p_85323_;
      this.f_85315_ = p_85324_;
      this.f_85316_ = p_85325_;
      this.f_85317_ = p_85326_;
      this.f_85318_ = p_85327_;
   }

   public VideoMode(Buffer p_85329_) {
      this.f_85313_ = p_85329_.width();
      this.f_85314_ = p_85329_.height();
      this.f_85315_ = p_85329_.redBits();
      this.f_85316_ = p_85329_.greenBits();
      this.f_85317_ = p_85329_.blueBits();
      this.f_85318_ = p_85329_.refreshRate();
   }

   public VideoMode(GLFWVidMode p_85331_) {
      this.f_85313_ = p_85331_.width();
      this.f_85314_ = p_85331_.height();
      this.f_85315_ = p_85331_.redBits();
      this.f_85316_ = p_85331_.greenBits();
      this.f_85317_ = p_85331_.blueBits();
      this.f_85318_ = p_85331_.refreshRate();
   }

   public int m_85332_() {
      return this.f_85313_;
   }

   public int m_85335_() {
      return this.f_85314_;
   }

   public int m_85336_() {
      return this.f_85315_;
   }

   public int m_85337_() {
      return this.f_85316_;
   }

   public int m_85338_() {
      return this.f_85317_;
   }

   public int m_85341_() {
      return this.f_85318_;
   }

   public boolean equals(Object p_85340_) {
      if (this == p_85340_) {
         return true;
      } else if (p_85340_ != null && this.getClass() == p_85340_.getClass()) {
         VideoMode videomode = (VideoMode)p_85340_;
         return this.f_85313_ == videomode.f_85313_ && this.f_85314_ == videomode.f_85314_ && this.f_85315_ == videomode.f_85315_ && this.f_85316_ == videomode.f_85316_ && this.f_85317_ == videomode.f_85317_ && this.f_85318_ == videomode.f_85318_;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Objects.hash(this.f_85313_, this.f_85314_, this.f_85315_, this.f_85316_, this.f_85317_, this.f_85318_);
   }

   public String toString() {
      return String.format("%sx%s@%s (%sbit)", this.f_85313_, this.f_85314_, this.f_85318_, this.f_85315_ + this.f_85316_ + this.f_85317_);
   }

   public static Optional<VideoMode> m_85333_(@Nullable String p_85334_) {
      if (p_85334_ == null) {
         return Optional.empty();
      } else {
         try {
            Matcher matcher = f_85319_.matcher(p_85334_);
            if (matcher.matches()) {
               int i = Integer.parseInt(matcher.group(1));
               int j = Integer.parseInt(matcher.group(2));
               String s = matcher.group(3);
               int k;
               if (s == null) {
                  k = 60;
               } else {
                  k = Integer.parseInt(s);
               }

               String s1 = matcher.group(4);
               int l;
               if (s1 == null) {
                  l = 24;
               } else {
                  l = Integer.parseInt(s1);
               }

               int i1 = l / 3;
               return Optional.of(new VideoMode(i, j, i1, i1, i1, k));
            }
         } catch (Exception exception) {
         }

         return Optional.empty();
      }
   }

   public String m_85342_() {
      return String.format("%sx%s@%s:%s", this.f_85313_, this.f_85314_, this.f_85318_, this.f_85315_ + this.f_85316_ + this.f_85317_);
   }
}