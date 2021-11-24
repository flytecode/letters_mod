package com.mojang.blaze3d.platform;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Optional;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.SilentInitException;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.glfw.GLFWImage.Buffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

@OnlyIn(Dist.CLIENT)
public final class Window implements AutoCloseable {
   private static final Logger f_85345_ = LogManager.getLogger();
   private final GLFWErrorCallback f_85346_ = GLFWErrorCallback.create(this::m_85382_);
   private final WindowEventHandler f_85347_;
   private final ScreenManager f_85348_;
   private final long f_85349_;
   private int f_85350_;
   private int f_85351_;
   private int f_85352_;
   private int f_85353_;
   private Optional<VideoMode> f_85354_;
   private boolean f_85355_;
   private boolean f_85356_;
   private int f_85357_;
   private int f_85358_;
   private int f_85359_;
   private int f_85360_;
   private int f_85361_;
   private int f_85362_;
   private int f_85363_;
   private int f_85364_;
   private double f_85365_;
   private String f_85366_ = "";
   private boolean f_85367_;
   private int f_85368_;
   private boolean f_85369_;

   public Window(WindowEventHandler p_85372_, ScreenManager p_85373_, DisplayData p_85374_, @Nullable String p_85375_, String p_85376_) {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      this.f_85348_ = p_85373_;
      this.m_85451_();
      this.m_85403_("Pre startup");
      this.f_85347_ = p_85372_;
      Optional<VideoMode> optional = VideoMode.m_85333_(p_85375_);
      if (optional.isPresent()) {
         this.f_85354_ = optional;
      } else if (p_85374_.f_84007_.isPresent() && p_85374_.f_84008_.isPresent()) {
         this.f_85354_ = Optional.of(new VideoMode(p_85374_.f_84007_.getAsInt(), p_85374_.f_84008_.getAsInt(), 8, 8, 8, 60));
      } else {
         this.f_85354_ = Optional.empty();
      }

      this.f_85356_ = this.f_85355_ = p_85374_.f_84009_;
      Monitor monitor = p_85373_.m_85271_(GLFW.glfwGetPrimaryMonitor());
      this.f_85352_ = this.f_85359_ = p_85374_.f_84005_ > 0 ? p_85374_.f_84005_ : 1;
      this.f_85353_ = this.f_85360_ = p_85374_.f_84006_ > 0 ? p_85374_.f_84006_ : 1;
      GLFW.glfwDefaultWindowHints();
      GLFW.glfwWindowHint(139265, 196609);
      GLFW.glfwWindowHint(139275, 221185);
      GLFW.glfwWindowHint(139266, 3);
      GLFW.glfwWindowHint(139267, 2);
      GLFW.glfwWindowHint(139272, 204801);
      GLFW.glfwWindowHint(139270, 1);
      this.f_85349_ = net.minecraftforge.fml.loading.progress.EarlyProgressVisualization.INSTANCE.handOffWindow(()->this.f_85359_, ()->this.f_85360_, ()->p_85376_, ()->this.f_85355_ && monitor != null ? monitor.m_84954_() : 0L);
      if (monitor != null) {
         VideoMode videomode = monitor.m_84948_(this.f_85355_ ? this.f_85354_ : Optional.empty());
         this.f_85350_ = this.f_85357_ = monitor.m_84951_() + videomode.m_85332_() / 2 - this.f_85359_ / 2;
         this.f_85351_ = this.f_85358_ = monitor.m_84952_() + videomode.m_85335_() / 2 - this.f_85360_ / 2;
      } else {
         int[] aint1 = new int[1];
         int[] aint = new int[1];
         GLFW.glfwGetWindowPos(this.f_85349_, aint1, aint);
         this.f_85350_ = this.f_85357_ = aint1[0];
         this.f_85351_ = this.f_85358_ = aint[0];
      }

      GLFW.glfwMakeContextCurrent(this.f_85349_);
      GL.createCapabilities();
      this.m_85453_();
      this.m_85452_();
      GLFW.glfwSetFramebufferSizeCallback(this.f_85349_, this::m_85415_);
      GLFW.glfwSetWindowPosCallback(this.f_85349_, this::m_85388_);
      GLFW.glfwSetWindowSizeCallback(this.f_85349_, this::m_85427_);
      GLFW.glfwSetWindowFocusCallback(this.f_85349_, this::m_85392_);
      GLFW.glfwSetCursorEnterCallback(this.f_85349_, this::m_85419_);
   }

   public int m_85377_() {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      return GLX.m_69341_(this);
   }

   public boolean m_85411_() {
      return GLX.m_69355_(this);
   }

   public static void m_85407_(BiConsumer<Integer, String> p_85408_) {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      MemoryStack memorystack = MemoryStack.stackPush();

      try {
         PointerBuffer pointerbuffer = memorystack.mallocPointer(1);
         int i = GLFW.glfwGetError(pointerbuffer);
         if (i != 0) {
            long j = pointerbuffer.get();
            String s = j == 0L ? "" : MemoryUtil.memUTF8(j);
            p_85408_.accept(i, s);
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

   public void m_85395_(InputStream p_85396_, InputStream p_85397_) {
      RenderSystem.m_69393_(RenderSystem::m_69583_);

      try {
         MemoryStack memorystack = MemoryStack.stackPush();

         try {
            if (p_85396_ == null) {
               throw new FileNotFoundException("icons/icon_16x16.png");
            }

            if (p_85397_ == null) {
               throw new FileNotFoundException("icons/icon_32x32.png");
            }

            IntBuffer intbuffer = memorystack.mallocInt(1);
            IntBuffer intbuffer1 = memorystack.mallocInt(1);
            IntBuffer intbuffer2 = memorystack.mallocInt(1);
            Buffer buffer = GLFWImage.mallocStack(2, memorystack);
            ByteBuffer bytebuffer = this.m_85398_(p_85396_, intbuffer, intbuffer1, intbuffer2);
            if (bytebuffer == null) {
               throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }

            buffer.position(0);
            buffer.width(intbuffer.get(0));
            buffer.height(intbuffer1.get(0));
            buffer.pixels(bytebuffer);
            ByteBuffer bytebuffer1 = this.m_85398_(p_85397_, intbuffer, intbuffer1, intbuffer2);
            if (bytebuffer1 == null) {
               throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }

            buffer.position(1);
            buffer.width(intbuffer.get(0));
            buffer.height(intbuffer1.get(0));
            buffer.pixels(bytebuffer1);
            buffer.position(0);
            GLFW.glfwSetWindowIcon(this.f_85349_, buffer);
            STBImage.stbi_image_free(bytebuffer);
            STBImage.stbi_image_free(bytebuffer1);
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
      } catch (IOException ioexception) {
         f_85345_.error("Couldn't set icon", (Throwable)ioexception);
      }

   }

   @Nullable
   private ByteBuffer m_85398_(InputStream p_85399_, IntBuffer p_85400_, IntBuffer p_85401_, IntBuffer p_85402_) throws IOException {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      ByteBuffer bytebuffer = null;

      ByteBuffer bytebuffer1;
      try {
         bytebuffer = TextureUtil.m_85303_(p_85399_);
         bytebuffer.rewind();
         bytebuffer1 = STBImage.stbi_load_from_memory(bytebuffer, p_85400_, p_85401_, p_85402_, 0);
      } finally {
         if (bytebuffer != null) {
            MemoryUtil.memFree(bytebuffer);
         }

      }

      return bytebuffer1;
   }

   public void m_85403_(String p_85404_) {
      this.f_85366_ = p_85404_;
   }

   private void m_85451_() {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      GLFW.glfwSetErrorCallback(Window::m_85412_);
   }

   private static void m_85412_(int p_85413_, long p_85414_) {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      String s = "GLFW error " + p_85413_ + ": " + MemoryUtil.memUTF8(p_85414_);
      TinyFileDialogs.tinyfd_messageBox("Minecraft", s + ".\n\nPlease make sure you have up-to-date drivers (see aka.ms/mcdriver for instructions).", "ok", "error", false);
      throw new Window.WindowInitFailed(s);
   }

   public void m_85382_(int p_85383_, long p_85384_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      String s = MemoryUtil.memUTF8(p_85384_);
      f_85345_.error("########## GL ERROR ##########");
      f_85345_.error("@ {}", (Object)this.f_85366_);
      f_85345_.error("{}: {}", p_85383_, s);
   }

   public void m_85426_() {
      GLFWErrorCallback glfwerrorcallback = GLFW.glfwSetErrorCallback(this.f_85346_);
      if (glfwerrorcallback != null) {
         glfwerrorcallback.free();
      }

   }

   public void m_85409_(boolean p_85410_) {
      RenderSystem.m_69393_(RenderSystem::m_69587_);
      this.f_85369_ = p_85410_;
      GLFW.glfwSwapInterval(p_85410_ ? 1 : 0);
   }

   public void close() {
      RenderSystem.m_69393_(RenderSystem::m_69586_);
      Callbacks.glfwFreeCallbacks(this.f_85349_);
      this.f_85346_.close();
      GLFW.glfwDestroyWindow(this.f_85349_);
      GLFW.glfwTerminate();
   }

   private void m_85388_(long p_85389_, int p_85390_, int p_85391_) {
      this.f_85357_ = p_85390_;
      this.f_85358_ = p_85391_;
   }

   private void m_85415_(long p_85416_, int p_85417_, int p_85418_) {
      if (p_85416_ == this.f_85349_) {
         int i = this.m_85441_();
         int j = this.m_85442_();
         if (p_85417_ != 0 && p_85418_ != 0) {
            this.f_85361_ = p_85417_;
            this.f_85362_ = p_85418_;
            if (this.m_85441_() != i || this.m_85442_() != j) {
               this.f_85347_.m_5741_();
            }

         }
      }
   }

   private void m_85452_() {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      int[] aint = new int[1];
      int[] aint1 = new int[1];
      GLFW.glfwGetFramebufferSize(this.f_85349_, aint, aint1);
      this.f_85361_ = aint[0] > 0 ? aint[0] : 1;
      this.f_85362_ = aint1[0] > 0 ? aint1[0] : 1;
      if (this.f_85362_ == 0 || this.f_85361_==0) net.minecraftforge.fml.loading.progress.EarlyProgressVisualization.INSTANCE.updateFBSize(w->this.f_85361_=w, h->this.f_85362_=h);
   }

   private void m_85427_(long p_85428_, int p_85429_, int p_85430_) {
      this.f_85359_ = p_85429_;
      this.f_85360_ = p_85430_;
   }

   private void m_85392_(long p_85393_, boolean p_85394_) {
      if (p_85393_ == this.f_85349_) {
         this.f_85347_.m_7440_(p_85394_);
      }

   }

   private void m_85419_(long p_85420_, boolean p_85421_) {
      if (p_85421_) {
         this.f_85347_.m_5740_();
      }

   }

   public void m_85380_(int p_85381_) {
      this.f_85368_ = p_85381_;
   }

   public int m_85434_() {
      return this.f_85368_;
   }

   public void m_85435_() {
      RenderSystem.m_69495_(this.f_85349_);
      if (this.f_85355_ != this.f_85356_) {
         this.f_85356_ = this.f_85355_;
         this.m_85431_(this.f_85369_);
      }

   }

   public Optional<VideoMode> m_85436_() {
      return this.f_85354_;
   }

   public void m_85405_(Optional<VideoMode> p_85406_) {
      boolean flag = !p_85406_.equals(this.f_85354_);
      this.f_85354_ = p_85406_;
      if (flag) {
         this.f_85367_ = true;
      }

   }

   public void m_85437_() {
      if (this.f_85355_ && this.f_85367_) {
         this.f_85367_ = false;
         this.m_85453_();
         this.f_85347_.m_5741_();
      }

   }

   private void m_85453_() {
      RenderSystem.m_69393_(RenderSystem::m_69583_);
      boolean flag = GLFW.glfwGetWindowMonitor(this.f_85349_) != 0L;
      if (this.f_85355_) {
         Monitor monitor = this.f_85348_.m_85276_(this);
         if (monitor == null) {
            f_85345_.warn("Failed to find suitable monitor for fullscreen mode");
            this.f_85355_ = false;
         } else {
            if (Minecraft.f_91002_) {
               MacosUtil.m_182517_(this.f_85349_);
            }

            VideoMode videomode = monitor.m_84948_(this.f_85354_);
            if (!flag) {
               this.f_85350_ = this.f_85357_;
               this.f_85351_ = this.f_85358_;
               this.f_85352_ = this.f_85359_;
               this.f_85353_ = this.f_85360_;
            }

            this.f_85357_ = 0;
            this.f_85358_ = 0;
            this.f_85359_ = videomode.m_85332_();
            this.f_85360_ = videomode.m_85335_();
            GLFW.glfwSetWindowMonitor(this.f_85349_, monitor.m_84954_(), this.f_85357_, this.f_85358_, this.f_85359_, this.f_85360_, videomode.m_85341_());
         }
      } else {
         this.f_85357_ = this.f_85350_;
         this.f_85358_ = this.f_85351_;
         this.f_85359_ = this.f_85352_;
         this.f_85360_ = this.f_85353_;
         GLFW.glfwSetWindowMonitor(this.f_85349_, 0L, this.f_85357_, this.f_85358_, this.f_85359_, this.f_85360_, -1);
      }

   }

   public void m_85438_() {
      this.f_85355_ = !this.f_85355_;
   }

   public void m_166447_(int p_166448_, int p_166449_) {
      this.f_85352_ = p_166448_;
      this.f_85353_ = p_166449_;
      this.f_85355_ = false;
      this.m_85453_();
   }

   private void m_85431_(boolean p_85432_) {
      RenderSystem.m_69393_(RenderSystem::m_69586_);

      try {
         this.m_85453_();
         this.f_85347_.m_5741_();
         this.m_85409_(p_85432_);
         this.m_85435_();
      } catch (Exception exception) {
         f_85345_.error("Couldn't toggle fullscreen", (Throwable)exception);
      }

   }

   public int m_85385_(int p_85386_, boolean p_85387_) {
      int i;
      for(i = 1; i != p_85386_ && i < this.f_85361_ && i < this.f_85362_ && this.f_85361_ / (i + 1) >= 320 && this.f_85362_ / (i + 1) >= 240; ++i) {
      }

      if (p_85387_ && i % 2 != 0) {
         ++i;
      }

      return i;
   }

   public void m_85378_(double p_85379_) {
      this.f_85365_ = p_85379_;
      int i = (int)((double)this.f_85361_ / p_85379_);
      this.f_85363_ = (double)this.f_85361_ / p_85379_ > (double)i ? i + 1 : i;
      int j = (int)((double)this.f_85362_ / p_85379_);
      this.f_85364_ = (double)this.f_85362_ / p_85379_ > (double)j ? j + 1 : j;
   }

   public void m_85422_(String p_85423_) {
      GLFW.glfwSetWindowTitle(this.f_85349_, p_85423_);
   }

   public long m_85439_() {
      return this.f_85349_;
   }

   public boolean m_85440_() {
      return this.f_85355_;
   }

   public int m_85441_() {
      return this.f_85361_;
   }

   public int m_85442_() {
      return this.f_85362_;
   }

   public void m_166450_(int p_166451_) {
      this.f_85361_ = p_166451_;
   }

   public void m_166452_(int p_166453_) {
      this.f_85362_ = p_166453_;
   }

   public int m_85443_() {
      return this.f_85359_;
   }

   public int m_85444_() {
      return this.f_85360_;
   }

   public int m_85445_() {
      return this.f_85363_;
   }

   public int m_85446_() {
      return this.f_85364_;
   }

   public int m_85447_() {
      return this.f_85357_;
   }

   public int m_85448_() {
      return this.f_85358_;
   }

   public double m_85449_() {
      return this.f_85365_;
   }

   @Nullable
   public Monitor m_85450_() {
      return this.f_85348_.m_85276_(this);
   }

   public void m_85424_(boolean p_85425_) {
      InputConstants.m_84848_(this.f_85349_, p_85425_);
   }

   @OnlyIn(Dist.CLIENT)
   public static class WindowInitFailed extends SilentInitException {
      WindowInitFailed(String p_85455_) {
         super(p_85455_);
      }
   }
}
