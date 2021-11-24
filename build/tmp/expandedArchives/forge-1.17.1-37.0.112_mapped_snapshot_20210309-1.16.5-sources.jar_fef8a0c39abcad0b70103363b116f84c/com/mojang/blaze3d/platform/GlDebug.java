package com.mojang.blaze3d.platform;

import com.google.common.collect.EvictingQueue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Queue;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.ARBDebugOutput;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLDebugMessageARBCallback;
import org.lwjgl.opengl.GLDebugMessageCallback;
import org.lwjgl.opengl.KHRDebug;

@OnlyIn(Dist.CLIENT)
public class GlDebug {
   private static final Logger f_84028_ = LogManager.getLogger();
   private static final int f_166220_ = 10;
   private static final Queue<GlDebug.LogEntry> f_166221_ = EvictingQueue.create(10);
   @Nullable
   private static volatile GlDebug.LogEntry f_166222_;
   private static final List<Integer> f_84032_ = ImmutableList.of(37190, 37191, 37192, 33387);
   private static final List<Integer> f_84033_ = ImmutableList.of(37190, 37191, 37192);
   private static boolean f_166223_;

   private static String m_84036_(int p_84037_) {
      return "Unknown (0x" + Integer.toHexString(p_84037_).toUpperCase() + ")";
   }

   public static String m_84055_(int p_84056_) {
      switch(p_84056_) {
      case 33350:
         return "API";
      case 33351:
         return "WINDOW SYSTEM";
      case 33352:
         return "SHADER COMPILER";
      case 33353:
         return "THIRD PARTY";
      case 33354:
         return "APPLICATION";
      case 33355:
         return "OTHER";
      default:
         return m_84036_(p_84056_);
      }
   }

   public static String m_84057_(int p_84058_) {
      switch(p_84058_) {
      case 33356:
         return "ERROR";
      case 33357:
         return "DEPRECATED BEHAVIOR";
      case 33358:
         return "UNDEFINED BEHAVIOR";
      case 33359:
         return "PORTABILITY";
      case 33360:
         return "PERFORMANCE";
      case 33361:
         return "OTHER";
      case 33384:
         return "MARKER";
      default:
         return m_84036_(p_84058_);
      }
   }

   public static String m_84059_(int p_84060_) {
      switch(p_84060_) {
      case 33387:
         return "NOTIFICATION";
      case 37190:
         return "HIGH";
      case 37191:
         return "MEDIUM";
      case 37192:
         return "LOW";
      default:
         return m_84036_(p_84060_);
      }
   }

   private static void m_84038_(int p_84039_, int p_84040_, int p_84041_, int p_84042_, int p_84043_, long p_84044_, long p_84045_) {
      String s = GLDebugMessageCallback.getMessage(p_84043_, p_84044_);
      GlDebug.LogEntry gldebug$logentry;
      synchronized(f_166221_) {
         gldebug$logentry = f_166222_;
         if (gldebug$logentry != null && gldebug$logentry.m_166239_(p_84039_, p_84040_, p_84041_, p_84042_, s)) {
            ++gldebug$logentry.f_166232_;
         } else {
            gldebug$logentry = new GlDebug.LogEntry(p_84039_, p_84040_, p_84041_, p_84042_, s);
            f_166221_.add(gldebug$logentry);
            f_166222_ = gldebug$logentry;
         }
      }

      f_84028_.info("OpenGL debug message: {}", (Object)gldebug$logentry);
   }

   public static List<String> m_166225_() {
      synchronized(f_166221_) {
         List<String> list = Lists.newArrayListWithCapacity(f_166221_.size());

         for(GlDebug.LogEntry gldebug$logentry : f_166221_) {
            list.add(gldebug$logentry + " x " + gldebug$logentry.f_166232_);
         }

         return list;
      }
   }

   public static boolean m_166226_() {
      return f_166223_;
   }

   public static void m_84049_(int p_84050_, boolean p_84051_) {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      if (p_84050_ > 0) {
         GLCapabilities glcapabilities = GL.getCapabilities();
         if (glcapabilities.GL_KHR_debug) {
            f_166223_ = true;
            GL11.glEnable(37600);
            if (p_84051_) {
               GL11.glEnable(33346);
            }

            for(int i = 0; i < f_84032_.size(); ++i) {
               boolean flag = i < p_84050_;
               KHRDebug.glDebugMessageControl(4352, 4352, f_84032_.get(i), (int[])null, flag);
            }

            KHRDebug.glDebugMessageCallback(GLX.m_69370_(GLDebugMessageCallback.create(GlDebug::m_84038_), DebugMemoryUntracker::m_84003_), 0L);
         } else if (glcapabilities.GL_ARB_debug_output) {
            f_166223_ = true;
            if (p_84051_) {
               GL11.glEnable(33346);
            }

            for(int j = 0; j < f_84033_.size(); ++j) {
               boolean flag1 = j < p_84050_;
               ARBDebugOutput.glDebugMessageControlARB(4352, 4352, f_84033_.get(j), (int[])null, flag1);
            }

            ARBDebugOutput.glDebugMessageCallbackARB(GLX.m_69370_(GLDebugMessageARBCallback.create(GlDebug::m_84038_), DebugMemoryUntracker::m_84003_), 0L);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   static class LogEntry {
      private final int f_166227_;
      private final int f_166228_;
      private final int f_166229_;
      private final int f_166230_;
      private final String f_166231_;
      int f_166232_ = 1;

      LogEntry(int p_166234_, int p_166235_, int p_166236_, int p_166237_, String p_166238_) {
         this.f_166227_ = p_166236_;
         this.f_166228_ = p_166234_;
         this.f_166229_ = p_166235_;
         this.f_166230_ = p_166237_;
         this.f_166231_ = p_166238_;
      }

      boolean m_166239_(int p_166240_, int p_166241_, int p_166242_, int p_166243_, String p_166244_) {
         return p_166241_ == this.f_166229_ && p_166240_ == this.f_166228_ && p_166242_ == this.f_166227_ && p_166243_ == this.f_166230_ && p_166244_.equals(this.f_166231_);
      }

      public String toString() {
         return "id=" + this.f_166227_ + ", source=" + GlDebug.m_84055_(this.f_166228_) + ", type=" + GlDebug.m_84057_(this.f_166229_) + ", severity=" + GlDebug.m_84059_(this.f_166230_) + ", message='" + this.f_166231_ + "'";
      }
   }
}