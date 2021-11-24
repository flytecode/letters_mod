package com.mojang.blaze3d.platform;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWErrorCallbackI;
import org.lwjgl.glfw.GLFWVidMode;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

@OnlyIn(Dist.CLIENT)
@DontObfuscate
public class GLX {
   private static final Logger f_69332_ = LogManager.getLogger();
   private static String f_69335_;

   public static String m_69359_() {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      return GLFW.glfwGetCurrentContext() == 0L ? "NO CONTEXT" : GlStateManager.m_84089_(7937) + " GL version " + GlStateManager.m_84089_(7938) + ", " + GlStateManager.m_84089_(7936);
   }

   public static int m_69341_(Window p_69342_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      long i = GLFW.glfwGetWindowMonitor(p_69342_.m_85439_());
      if (i == 0L) {
         i = GLFW.glfwGetPrimaryMonitor();
      }

      GLFWVidMode glfwvidmode = i == 0L ? null : GLFW.glfwGetVideoMode(i);
      return glfwvidmode == null ? 0 : glfwvidmode.refreshRate();
   }

   public static String m_69340_() {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      return Version.getVersion();
   }

   public static LongSupplier m_69346_() {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      Window.m_85407_((p_69361_, p_69362_) -> {
         throw new IllegalStateException(String.format("GLFW error before init: [0x%X]%s", p_69361_, p_69362_));
      });
      List<String> list = Lists.newArrayList();
      GLFWErrorCallback glfwerrorcallback = GLFW.glfwSetErrorCallback((p_69365_, p_69366_) -> {
         list.add(String.format("GLFW error during init: [0x%X]%s", p_69365_, p_69366_));
      });
      if (!GLFW.glfwInit()) {
         throw new IllegalStateException("Failed to initialize GLFW, errors: " + Joiner.on(",").join(list));
      } else {
         LongSupplier longsupplier = () -> {
            return (long)(GLFW.glfwGetTime() * 1.0E9D);
         };

         for(String s : list) {
            f_69332_.error("GLFW error collected during initialization: {}", (Object)s);
         }

         RenderSystem.m_69900_(glfwerrorcallback);
         return longsupplier;
      }
   }

   public static void m_69352_(GLFWErrorCallbackI p_69353_) {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      GLFWErrorCallback glfwerrorcallback = GLFW.glfwSetErrorCallback(p_69353_);
      if (glfwerrorcallback != null) {
         glfwerrorcallback.free();
      }

   }

   public static boolean m_69355_(Window p_69356_) {
      return GLFW.glfwWindowShouldClose(p_69356_.m_85439_());
   }

   public static void m_69343_(int p_69344_, boolean p_69345_) {
      RenderSystem.m_69393_(RenderSystem::m_69583_);

      try {
         CentralProcessor centralprocessor = (new SystemInfo()).getHardware().getProcessor();
         f_69335_ = String.format("%dx %s", centralprocessor.getLogicalProcessorCount(), centralprocessor.getProcessorIdentifier().getName()).replaceAll("\\s+", " ");
      } catch (Throwable throwable) {
      }

      GlDebug.m_84049_(p_69344_, p_69345_);
   }

   public static String m_69339_() {
      return f_69335_ == null ? "<unknown>" : f_69335_;
   }

   public static void m_69347_(int p_69348_, boolean p_69349_, boolean p_69350_, boolean p_69351_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      GlStateManager.m_84110_();
      GlStateManager.m_84298_(false);
      GlStateManager.m_84094_();
      RenderSystem.m_157427_(GameRenderer::m_172757_);
      Tesselator tesselator = RenderSystem.m_69883_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      RenderSystem.m_69832_(4.0F);
      bufferbuilder.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_166851_);
      if (p_69349_) {
         bufferbuilder.m_5483_(0.0D, 0.0D, 0.0D).m_6122_(0, 0, 0, 255).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_((double)p_69348_, 0.0D, 0.0D).m_6122_(0, 0, 0, 255).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
      }

      if (p_69350_) {
         bufferbuilder.m_5483_(0.0D, 0.0D, 0.0D).m_6122_(0, 0, 0, 255).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_(0.0D, (double)p_69348_, 0.0D).m_6122_(0, 0, 0, 255).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
      }

      if (p_69351_) {
         bufferbuilder.m_5483_(0.0D, 0.0D, 0.0D).m_6122_(0, 0, 0, 255).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(0.0D, 0.0D, (double)p_69348_).m_6122_(0, 0, 0, 255).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
      }

      tesselator.m_85914_();
      RenderSystem.m_69832_(2.0F);
      bufferbuilder.m_166779_(VertexFormat.Mode.LINES, DefaultVertexFormat.f_166851_);
      if (p_69349_) {
         bufferbuilder.m_5483_(0.0D, 0.0D, 0.0D).m_6122_(255, 0, 0, 255).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_((double)p_69348_, 0.0D, 0.0D).m_6122_(255, 0, 0, 255).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
      }

      if (p_69350_) {
         bufferbuilder.m_5483_(0.0D, 0.0D, 0.0D).m_6122_(0, 255, 0, 255).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
         bufferbuilder.m_5483_(0.0D, (double)p_69348_, 0.0D).m_6122_(0, 255, 0, 255).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
      }

      if (p_69351_) {
         bufferbuilder.m_5483_(0.0D, 0.0D, 0.0D).m_6122_(127, 127, 255, 255).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
         bufferbuilder.m_5483_(0.0D, 0.0D, (double)p_69348_).m_6122_(127, 127, 255, 255).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
      }

      tesselator.m_85914_();
      RenderSystem.m_69832_(1.0F);
      GlStateManager.m_84091_();
      GlStateManager.m_84298_(true);
      GlStateManager.m_84109_();
   }

   public static <T> T m_69373_(Supplier<T> p_69374_) {
      return p_69374_.get();
   }

   public static <T> T m_69370_(T p_69371_, Consumer<T> p_69372_) {
      p_69372_.accept(p_69371_);
      return p_69371_;
   }
}