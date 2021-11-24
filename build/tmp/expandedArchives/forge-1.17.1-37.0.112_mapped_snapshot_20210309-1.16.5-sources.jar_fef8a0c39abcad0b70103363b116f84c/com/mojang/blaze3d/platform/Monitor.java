package com.mojang.blaze3d.platform;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Optional;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWVidMode.Buffer;

@OnlyIn(Dist.CLIENT)
public final class Monitor {
   private final long f_84936_;
   private final List<VideoMode> f_84937_;
   private VideoMode f_84938_;
   private int f_84939_;
   private int f_84940_;

   public Monitor(long p_84942_) {
      this.f_84936_ = p_84942_;
      this.f_84937_ = Lists.newArrayList();
      this.m_84943_();
   }

   public void m_84943_() {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      this.f_84937_.clear();
      Buffer buffer = GLFW.glfwGetVideoModes(this.f_84936_);

      for(int i = buffer.limit() - 1; i >= 0; --i) {
         buffer.position(i);
         VideoMode videomode = new VideoMode(buffer);
         if (videomode.m_85336_() >= 8 && videomode.m_85337_() >= 8 && videomode.m_85338_() >= 8) {
            this.f_84937_.add(videomode);
         }
      }

      int[] aint = new int[1];
      int[] aint1 = new int[1];
      GLFW.glfwGetMonitorPos(this.f_84936_, aint, aint1);
      this.f_84939_ = aint[0];
      this.f_84940_ = aint1[0];
      GLFWVidMode glfwvidmode = GLFW.glfwGetVideoMode(this.f_84936_);
      this.f_84938_ = new VideoMode(glfwvidmode);
   }

   public VideoMode m_84948_(Optional<VideoMode> p_84949_) {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      if (p_84949_.isPresent()) {
         VideoMode videomode = p_84949_.get();

         for(VideoMode videomode1 : this.f_84937_) {
            if (videomode1.equals(videomode)) {
               return videomode1;
            }
         }
      }

      return this.m_84950_();
   }

   public int m_84946_(VideoMode p_84947_) {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      return this.f_84937_.indexOf(p_84947_);
   }

   public VideoMode m_84950_() {
      return this.f_84938_;
   }

   public int m_84951_() {
      return this.f_84939_;
   }

   public int m_84952_() {
      return this.f_84940_;
   }

   public VideoMode m_84944_(int p_84945_) {
      return this.f_84937_.get(p_84945_);
   }

   public int m_84953_() {
      return this.f_84937_.size();
   }

   public long m_84954_() {
      return this.f_84936_;
   }

   public String toString() {
      return String.format("Monitor[%s %sx%s %s]", this.f_84936_, this.f_84939_, this.f_84940_, this.f_84938_);
   }
}