package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWMonitorCallback;
import org.lwjgl.glfw.GLFWMonitorCallbackI;

@OnlyIn(Dist.CLIENT)
public class ScreenManager {
   private final Long2ObjectMap<Monitor> f_85262_ = new Long2ObjectOpenHashMap<>();
   private final MonitorCreator f_85263_;

   public ScreenManager(MonitorCreator p_85265_) {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      this.f_85263_ = p_85265_;
      GLFW.glfwSetMonitorCallback(this::m_85273_);
      PointerBuffer pointerbuffer = GLFW.glfwGetMonitors();
      if (pointerbuffer != null) {
         for(int i = 0; i < pointerbuffer.limit(); ++i) {
            long j = pointerbuffer.get(i);
            this.f_85262_.put(j, p_85265_.m_84956_(j));
         }
      }

   }

   private void m_85273_(long p_85274_, int p_85275_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      if (p_85275_ == 262145) {
         this.f_85262_.put(p_85274_, this.f_85263_.m_84956_(p_85274_));
      } else if (p_85275_ == 262146) {
         this.f_85262_.remove(p_85274_);
      }

   }

   @Nullable
   public Monitor m_85271_(long p_85272_) {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      return this.f_85262_.get(p_85272_);
   }

   @Nullable
   public Monitor m_85276_(Window p_85277_) {
      long i = GLFW.glfwGetWindowMonitor(p_85277_.m_85439_());
      if (i != 0L) {
         return this.m_85271_(i);
      } else {
         int j = p_85277_.m_85447_();
         int k = j + p_85277_.m_85443_();
         int l = p_85277_.m_85448_();
         int i1 = l + p_85277_.m_85444_();
         int j1 = -1;
         Monitor monitor = null;

         for(Monitor monitor1 : this.f_85262_.values()) {
            int k1 = monitor1.m_84951_();
            int l1 = k1 + monitor1.m_84950_().m_85332_();
            int i2 = monitor1.m_84952_();
            int j2 = i2 + monitor1.m_84950_().m_85335_();
            int k2 = m_85267_(j, k1, l1);
            int l2 = m_85267_(k, k1, l1);
            int i3 = m_85267_(l, i2, j2);
            int j3 = m_85267_(i1, i2, j2);
            int k3 = Math.max(0, l2 - k2);
            int l3 = Math.max(0, j3 - i3);
            int i4 = k3 * l3;
            if (i4 > j1) {
               monitor = monitor1;
               j1 = i4;
            }
         }

         return monitor;
      }
   }

   public static int m_85267_(int p_85268_, int p_85269_, int p_85270_) {
      if (p_85268_ < p_85269_) {
         return p_85269_;
      } else {
         return p_85268_ > p_85270_ ? p_85270_ : p_85268_;
      }
   }

   public void m_85266_() {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      GLFWMonitorCallback glfwmonitorcallback = GLFW.glfwSetMonitorCallback((GLFWMonitorCallbackI)null);
      if (glfwmonitorcallback != null) {
         glfwmonitorcallback.free();
      }

   }
}