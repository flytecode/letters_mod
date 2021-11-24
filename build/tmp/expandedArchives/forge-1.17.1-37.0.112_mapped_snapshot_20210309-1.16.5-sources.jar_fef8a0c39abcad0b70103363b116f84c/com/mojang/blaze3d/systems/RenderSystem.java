package com.mojang.blaze3d.systems;

import com.google.common.collect.Queues;
import com.mojang.blaze3d.DontObfuscate;
import com.mojang.blaze3d.pipeline.RenderCall;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.GraphicsStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallbackI;

@OnlyIn(Dist.CLIENT)
@DontObfuscate
public class RenderSystem {
   static final Logger f_69376_ = LogManager.getLogger();
   private static final ConcurrentLinkedQueue<RenderCall> f_69384_ = Queues.newConcurrentLinkedQueue();
   private static final Tesselator f_69379_ = new Tesselator();
   private static final int f_69378_ = 1024;
   private static boolean f_69382_;
   @Nullable
   private static Thread f_69380_;
   @Nullable
   private static Thread f_69385_;
   private static int f_69377_ = -1;
   private static boolean f_69381_;
   private static double f_69383_ = Double.MIN_VALUE;
   private static final RenderSystem.AutoStorageIndexBuffer f_157153_ = new RenderSystem.AutoStorageIndexBuffer(1, 1, IntConsumer::accept);
   private static final RenderSystem.AutoStorageIndexBuffer f_157155_ = new RenderSystem.AutoStorageIndexBuffer(4, 6, (p_157398_, p_157399_) -> {
      p_157398_.accept(p_157399_ + 0);
      p_157398_.accept(p_157399_ + 1);
      p_157398_.accept(p_157399_ + 2);
      p_157398_.accept(p_157399_ + 2);
      p_157398_.accept(p_157399_ + 3);
      p_157398_.accept(p_157399_ + 0);
   });
   private static final RenderSystem.AutoStorageIndexBuffer f_157154_ = new RenderSystem.AutoStorageIndexBuffer(4, 6, (p_157401_, p_157402_) -> {
      p_157401_.accept(p_157402_ + 0);
      p_157401_.accept(p_157402_ + 1);
      p_157401_.accept(p_157402_ + 2);
      p_157401_.accept(p_157402_ + 3);
      p_157401_.accept(p_157402_ + 2);
      p_157401_.accept(p_157402_ + 1);
   });
   private static Matrix4f f_157142_ = new Matrix4f();
   private static Matrix4f f_157143_ = new Matrix4f();
   private static PoseStack f_157141_ = new PoseStack();
   private static Matrix4f f_157140_ = new Matrix4f();
   private static Matrix4f f_157156_ = new Matrix4f();
   private static final int[] f_157152_ = new int[12];
   private static final float[] f_157145_ = new float[]{1.0F, 1.0F, 1.0F, 1.0F};
   private static float f_157148_;
   private static float f_157147_ = 1.0F;
   private static final float[] f_157146_ = new float[]{0.0F, 0.0F, 0.0F, 0.0F};
   private static final Vector3f[] f_157150_ = new Vector3f[2];
   private static float f_157149_;
   private static float f_157151_ = 1.0F;
   @Nullable
   private static ShaderInstance f_157144_;

   public static void m_69579_() {
      if (f_69385_ == null && f_69380_ != Thread.currentThread()) {
         f_69385_ = Thread.currentThread();
      } else {
         throw new IllegalStateException("Could not initialize render thread");
      }
   }

   public static boolean m_69586_() {
      return Thread.currentThread() == f_69385_;
   }

   public static boolean m_69587_() {
      return f_69381_ || m_69586_();
   }

   public static void m_69577_(boolean p_69578_) {
      boolean flag = f_69385_ == Thread.currentThread();
      if (f_69380_ == null && f_69385_ != null && flag != p_69578_) {
         f_69380_ = Thread.currentThread();
      } else {
         throw new IllegalStateException("Could not initialize tick thread");
      }
   }

   public static boolean m_69584_() {
      return true;
   }

   public static boolean m_69585_() {
      return f_69381_ || m_69584_();
   }

   public static void m_69393_(Supplier<Boolean> p_69394_) {
      if (!p_69394_.get()) {
         throw new IllegalStateException("Rendersystem called from wrong thread");
      }
   }

   public static boolean m_69583_() {
      return true;
   }

   public static void m_69879_(RenderCall p_69880_) {
      f_69384_.add(p_69880_);
   }

   public static void m_69495_(long p_69496_) {
      GLFW.glfwPollEvents();
      m_69884_();
      Tesselator.m_85913_().m_85915_().m_85729_();
      GLFW.glfwSwapBuffers(p_69496_);
      GLFW.glfwPollEvents();
   }

   public static void m_69884_() {
      f_69382_ = true;

      while(!f_69384_.isEmpty()) {
         RenderCall rendercall = f_69384_.poll();
         rendercall.m_83909_();
      }

      f_69382_ = false;
   }

   public static void m_69830_(int p_69831_) {
      double d0 = f_69383_ + 1.0D / (double)p_69831_;

      double d1;
      for(d1 = GLFW.glfwGetTime(); d1 < d0; d1 = GLFW.glfwGetTime()) {
         GLFW.glfwWaitEventsTimeout(d0 - d1);
      }

      f_69383_ = d1;
   }

   public static void m_69465_() {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84507_();
   }

   public static void m_69482_() {
      m_69393_(RenderSystem::m_69585_);
      GlStateManager.m_84513_();
   }

   public static void m_69488_(int p_69489_, int p_69490_, int p_69491_, int p_69492_) {
      m_69393_(RenderSystem::m_69585_);
      GlStateManager.m_84501_();
      GlStateManager.m_84168_(p_69489_, p_69490_, p_69491_, p_69492_);
   }

   public static void m_69471_() {
      m_69393_(RenderSystem::m_69585_);
      GlStateManager.m_84495_();
   }

   public static void m_69456_(int p_69457_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84323_(p_69457_);
   }

   public static void m_69458_(boolean p_69459_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84298_(p_69459_);
   }

   public static void m_69478_() {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84525_();
   }

   public static void m_69461_() {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84519_();
   }

   public static void m_69408_(GlStateManager.SourceFactor p_69409_, GlStateManager.DestFactor p_69410_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84328_(p_69409_.f_84751_, p_69410_.f_84646_);
   }

   public static void m_69405_(int p_69406_, int p_69407_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84328_(p_69406_, p_69407_);
   }

   public static void m_69416_(GlStateManager.SourceFactor p_69417_, GlStateManager.DestFactor p_69418_, GlStateManager.SourceFactor p_69419_, GlStateManager.DestFactor p_69420_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84335_(p_69417_.f_84751_, p_69418_.f_84646_, p_69419_.f_84751_, p_69420_.f_84646_);
   }

   public static void m_69411_(int p_69412_, int p_69413_, int p_69414_, int p_69415_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84335_(p_69412_, p_69413_, p_69414_, p_69415_);
   }

   public static void m_69403_(int p_69404_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84379_(p_69404_);
   }

   public static void m_69481_() {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84091_();
   }

   public static void m_69464_() {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84094_();
   }

   public static void m_69860_(int p_69861_, int p_69862_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84516_(p_69861_, p_69862_);
   }

   public static void m_69486_() {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84097_();
   }

   public static void m_69469_() {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84100_();
   }

   public static void m_69863_(float p_69864_, float p_69865_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84136_(p_69864_, p_69865_);
   }

   public static void m_69479_() {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84107_();
   }

   public static void m_69462_() {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84108_();
   }

   public static void m_69835_(GlStateManager.LogicOp p_69836_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84532_(p_69836_.f_84715_);
   }

   public static void m_69388_(int p_69389_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84538_(p_69389_);
   }

   public static void m_69493_() {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84109_();
   }

   public static void m_69472_() {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84110_();
   }

   public static void m_69937_(int p_69938_, int p_69939_, int p_69940_) {
      GlStateManager.m_84331_(p_69938_, p_69939_, p_69940_);
   }

   public static void m_69454_(int p_69455_) {
      m_69393_(RenderSystem::m_69585_);
      GlStateManager.m_84541_(p_69455_);
   }

   public static void m_157184_(int p_157185_) {
      m_69396_(p_157185_);
   }

   public static void m_69396_(int p_69397_) {
      GlStateManager.m_84544_(p_69397_);
   }

   public static void m_69949_(int p_69950_, int p_69951_, int p_69952_, int p_69953_) {
      m_69393_(RenderSystem::m_69585_);
      GlStateManager.m_84430_(p_69950_, p_69951_, p_69952_, p_69953_);
   }

   public static void m_69444_(boolean p_69445_, boolean p_69446_, boolean p_69447_, boolean p_69448_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84300_(p_69445_, p_69446_, p_69447_, p_69448_);
   }

   public static void m_69925_(int p_69926_, int p_69927_, int p_69928_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84426_(p_69926_, p_69927_, p_69928_);
   }

   public static void m_69929_(int p_69930_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84550_(p_69930_);
   }

   public static void m_69931_(int p_69932_, int p_69933_, int p_69934_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84452_(p_69932_, p_69933_, p_69934_);
   }

   public static void m_69430_(double p_69431_) {
      m_69393_(RenderSystem::m_69585_);
      GlStateManager.m_84121_(p_69431_);
   }

   public static void m_69424_(float p_69425_, float p_69426_, float p_69427_, float p_69428_) {
      m_69393_(RenderSystem::m_69585_);
      GlStateManager.m_84318_(p_69425_, p_69426_, p_69427_, p_69428_);
   }

   public static void m_69432_(int p_69433_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84553_(p_69433_);
   }

   public static void m_69421_(int p_69422_, boolean p_69423_) {
      m_69393_(RenderSystem::m_69585_);
      GlStateManager.m_84266_(p_69422_, p_69423_);
   }

   public static void m_157445_(float p_157446_) {
      m_69393_(RenderSystem::m_69584_);
      m_157171_(p_157446_);
   }

   private static void m_157171_(float p_157172_) {
      f_157148_ = p_157172_;
   }

   public static float m_157200_() {
      m_69393_(RenderSystem::m_69586_);
      return f_157148_;
   }

   public static void m_157443_(float p_157444_) {
      m_69393_(RenderSystem::m_69584_);
      m_157169_(p_157444_);
   }

   private static void m_157169_(float p_157170_) {
      f_157147_ = p_157170_;
   }

   public static float m_157199_() {
      m_69393_(RenderSystem::m_69586_);
      return f_157147_;
   }

   public static void m_157438_(float p_157439_, float p_157440_, float p_157441_, float p_157442_) {
      m_69393_(RenderSystem::m_69584_);
      m_157164_(p_157439_, p_157440_, p_157441_, p_157442_);
   }

   public static void m_157434_(float p_157435_, float p_157436_, float p_157437_) {
      m_157438_(p_157435_, p_157436_, p_157437_, 1.0F);
   }

   private static void m_157164_(float p_157165_, float p_157166_, float p_157167_, float p_157168_) {
      f_157146_[0] = p_157165_;
      f_157146_[1] = p_157166_;
      f_157146_[2] = p_157167_;
      f_157146_[3] = p_157168_;
   }

   public static float[] m_157198_() {
      m_69393_(RenderSystem::m_69586_);
      return f_157146_;
   }

   public static void m_157450_(Vector3f p_157451_, Vector3f p_157452_) {
      m_69393_(RenderSystem::m_69584_);
      m_157173_(p_157451_, p_157452_);
   }

   public static void m_157173_(Vector3f p_157174_, Vector3f p_157175_) {
      f_157150_[0] = p_157174_;
      f_157150_[1] = p_157175_;
   }

   public static void m_157461_(ShaderInstance p_157462_) {
      m_69393_(RenderSystem::m_69586_);
      if (p_157462_.f_173313_ != null) {
         p_157462_.f_173313_.m_142276_(f_157150_[0]);
      }

      if (p_157462_.f_173314_ != null) {
         p_157462_.f_173314_.m_142276_(f_157150_[1]);
      }

   }

   public static void m_157429_(float p_157430_, float p_157431_, float p_157432_, float p_157433_) {
      if (!m_69586_()) {
         m_69879_(() -> {
            m_157159_(p_157430_, p_157431_, p_157432_, p_157433_);
         });
      } else {
         m_157159_(p_157430_, p_157431_, p_157432_, p_157433_);
      }

   }

   private static void m_157159_(float p_157160_, float p_157161_, float p_157162_, float p_157163_) {
      f_157145_[0] = p_157160_;
      f_157145_[1] = p_157161_;
      f_157145_[2] = p_157162_;
      f_157145_[3] = p_157163_;
   }

   public static float[] m_157197_() {
      m_69393_(RenderSystem::m_69586_);
      return f_157145_;
   }

   public static void m_157186_(int p_157187_, int p_157188_, int p_157189_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_157053_(p_157187_, p_157188_, p_157189_, 0L);
   }

   public static void m_69832_(float p_69833_) {
      if (!m_69586_()) {
         m_69879_(() -> {
            f_157151_ = p_69833_;
         });
      } else {
         f_157151_ = p_69833_;
      }

   }

   public static float m_157202_() {
      m_69393_(RenderSystem::m_69586_);
      return f_157151_;
   }

   public static void m_69854_(int p_69855_, int p_69856_) {
      m_69393_(RenderSystem::m_69585_);
      GlStateManager.m_84522_(p_69855_, p_69856_);
   }

   public static void m_69871_(int p_69872_, int p_69873_, int p_69874_, int p_69875_, int p_69876_, int p_69877_, ByteBuffer p_69878_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84219_(p_69872_, p_69873_, p_69874_, p_69875_, p_69876_, p_69877_, p_69878_);
   }

   public static void m_69519_(int p_69520_, Consumer<String> p_69521_) {
      m_69393_(RenderSystem::m_69584_);
      p_69521_.accept(GlStateManager.m_84089_(p_69520_));
   }

   public static String m_69517_() {
      m_69393_(RenderSystem::m_69583_);
      return String.format("LWJGL version %s", GLX.m_69340_());
   }

   public static String m_69516_() {
      m_69393_(RenderSystem::m_69583_);
      return GLX.m_69359_();
   }

   public static LongSupplier m_69576_() {
      m_69393_(RenderSystem::m_69583_);
      return GLX.m_69346_();
   }

   public static void m_69580_(int p_69581_, boolean p_69582_) {
      m_69393_(RenderSystem::m_69583_);
      GLX.m_69343_(p_69581_, p_69582_);
   }

   public static void m_69900_(GLFWErrorCallbackI p_69901_) {
      m_69393_(RenderSystem::m_69583_);
      GLX.m_69352_(p_69901_);
   }

   public static void m_69881_(int p_69882_) {
      m_69393_(RenderSystem::m_69584_);
      GLX.m_69347_(p_69882_, true, true, true);
   }

   public static String m_69518_() {
      m_69393_(RenderSystem::m_69584_);
      return "Using framebuffer using OpenGL 3.2";
   }

   public static void m_69902_(int p_69903_, int p_69904_, int p_69905_, int p_69906_) {
      m_69393_(RenderSystem::m_69583_);
      GlStateManager.m_84109_();
      GlStateManager.m_84121_(1.0D);
      GlStateManager.m_84513_();
      GlStateManager.m_84323_(515);
      f_157142_.m_27624_();
      f_157143_.m_27624_();
      f_157140_.m_27624_();
      f_157156_.m_27624_();
      GlStateManager.m_84430_(p_69903_, p_69904_, p_69905_, p_69906_);
   }

   public static int m_69839_() {
      if (f_69377_ == -1) {
         m_69393_(RenderSystem::m_69587_);
         int i = GlStateManager.m_84092_(3379);

         for(int j = Math.max(32768, i); j >= 1024; j >>= 1) {
            GlStateManager.m_84209_(32868, 0, 6408, j, j, 0, 6408, 5121, (IntBuffer)null);
            int k = GlStateManager.m_84384_(32868, 0, 4096);
            if (k != 0) {
               f_69377_ = j;
               return j;
            }
         }

         f_69377_ = Math.max(i, 1024);
         f_69376_.info("Failed to determine maximum texture size by probing, trying GL_MAX_TEXTURE_SIZE = {}", (int)f_69377_);
      }

      return f_69377_;
   }

   public static void m_157208_(int p_157209_, IntSupplier p_157210_) {
      GlStateManager.m_84480_(p_157209_, p_157210_.getAsInt());
   }

   public static void m_157211_(Supplier<Integer> p_157212_) {
      GlStateManager.m_157068_(p_157212_.get());
   }

   public static void m_69525_(int p_69526_, ByteBuffer p_69527_, int p_69528_) {
      m_69393_(RenderSystem::m_69587_);
      GlStateManager.m_84256_(p_69526_, p_69527_, p_69528_);
   }

   public static void m_69529_(int p_69530_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84496_(p_69530_);
   }

   public static void m_157213_(int p_157214_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_157076_(p_157214_);
   }

   public static void m_69543_(int p_69544_, int p_69545_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84467_(p_69544_, p_69545_);
   }

   public static void m_69540_(int p_69541_, IntBuffer p_69542_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84263_(p_69541_, p_69542_);
   }

   public static void m_69549_(int p_69550_, IntBuffer p_69551_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84351_(p_69550_, p_69551_);
   }

   public static void m_69555_(int p_69556_, IntBuffer p_69557_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84404_(p_69556_, p_69557_);
   }

   public static void m_69561_(int p_69562_, IntBuffer p_69563_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84438_(p_69562_, p_69563_);
   }

   public static void m_69537_(int p_69538_, FloatBuffer p_69539_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84348_(p_69538_, p_69539_);
   }

   public static void m_69546_(int p_69547_, FloatBuffer p_69548_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84401_(p_69547_, p_69548_);
   }

   public static void m_69552_(int p_69553_, FloatBuffer p_69554_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84435_(p_69553_, p_69554_);
   }

   public static void m_69558_(int p_69559_, FloatBuffer p_69560_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84461_(p_69559_, p_69560_);
   }

   public static void m_69564_(int p_69565_, boolean p_69566_, FloatBuffer p_69567_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84269_(p_69565_, p_69566_, p_69567_);
   }

   public static void m_69568_(int p_69569_, boolean p_69570_, FloatBuffer p_69571_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84354_(p_69569_, p_69570_, p_69571_);
   }

   public static void m_69572_(int p_69573_, boolean p_69574_, FloatBuffer p_69575_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84407_(p_69573_, p_69574_, p_69575_);
   }

   public static void m_69920_(IntSupplier p_69921_, int p_69922_) {
      m_69393_(RenderSystem::m_69584_);
      int i = p_69921_.getAsInt();
      m_157453_(1, i);
   }

   public static void m_69936_() {
      m_69393_(RenderSystem::m_69584_);
      m_157453_(1, 0);
   }

   public static void m_69914_(Vector3f p_69915_, Vector3f p_69916_, Matrix4f p_69917_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84290_(p_69915_, p_69916_, p_69917_);
   }

   public static void m_69911_(Vector3f p_69912_, Vector3f p_69913_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84287_(p_69912_, p_69913_);
   }

   public static void m_69908_(Vector3f p_69909_, Vector3f p_69910_) {
      m_69393_(RenderSystem::m_69584_);
      GlStateManager.m_84360_(p_69909_, p_69910_);
   }

   public static void m_69395_() {
      f_69381_ = true;
   }

   public static void m_69494_() {
      f_69381_ = false;
      if (!f_69384_.isEmpty()) {
         m_69884_();
      }

      if (!f_69384_.isEmpty()) {
         throw new IllegalStateException("Recorded to render queue during initialization");
      }
   }

   public static void m_69531_(Consumer<Integer> p_69532_) {
      if (!m_69586_()) {
         m_69879_(() -> {
            p_69532_.accept(GlStateManager.m_84537_());
         });
      } else {
         p_69532_.accept(GlStateManager.m_84537_());
      }

   }

   public static void m_157215_(Consumer<Integer> p_157216_) {
      if (!m_69586_()) {
         m_69879_(() -> {
            p_157216_.accept(GlStateManager.m_157089_());
         });
      } else {
         p_157216_.accept(GlStateManager.m_157089_());
      }

   }

   public static Tesselator m_69883_() {
      m_69393_(RenderSystem::m_69586_);
      return f_69379_;
   }

   public static void m_69453_() {
      m_69416_(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
   }

   @Deprecated
   public static void m_69890_(Runnable p_69891_) {
      boolean flag = Minecraft.m_91085_();
      if (!flag) {
         p_69891_.run();
      } else {
         Options options = Minecraft.m_91087_().f_91066_;
         GraphicsStatus graphicsstatus = options.f_92115_;
         options.f_92115_ = GraphicsStatus.FANCY;
         p_69891_.run();
         options.f_92115_ = graphicsstatus;
      }
   }

   public static void m_157427_(Supplier<ShaderInstance> p_157428_) {
      if (!m_69586_()) {
         m_69879_(() -> {
            f_157144_ = p_157428_.get();
         });
      } else {
         f_157144_ = p_157428_.get();
      }

   }

   @Nullable
   public static ShaderInstance m_157196_() {
      m_69393_(RenderSystem::m_69586_);
      return f_157144_;
   }

   public static int m_157205_(int p_157206_) {
      return GlStateManager.m_157059_(p_157206_);
   }

   public static void m_157456_(int p_157457_, ResourceLocation p_157458_) {
      if (!m_69586_()) {
         m_69879_(() -> {
            m_157179_(p_157457_, p_157458_);
         });
      } else {
         m_157179_(p_157457_, p_157458_);
      }

   }

   public static void m_157179_(int p_157180_, ResourceLocation p_157181_) {
      if (p_157180_ >= 0 && p_157180_ < f_157152_.length) {
         TextureManager texturemanager = Minecraft.m_91087_().m_91097_();
         AbstractTexture abstracttexture = texturemanager.m_118506_(p_157181_);
         f_157152_[p_157180_] = abstracttexture.m_117963_();
      }

   }

   public static void m_157453_(int p_157454_, int p_157455_) {
      if (!m_69586_()) {
         m_69879_(() -> {
            m_157176_(p_157454_, p_157455_);
         });
      } else {
         m_157176_(p_157454_, p_157455_);
      }

   }

   public static void m_157176_(int p_157177_, int p_157178_) {
      if (p_157177_ >= 0 && p_157177_ < f_157152_.length) {
         f_157152_[p_157177_] = p_157178_;
      }

   }

   public static int m_157203_(int p_157204_) {
      m_69393_(RenderSystem::m_69586_);
      return p_157204_ >= 0 && p_157204_ < f_157152_.length ? f_157152_[p_157204_] : 0;
   }

   public static void m_157425_(Matrix4f p_157426_) {
      Matrix4f matrix4f = p_157426_.m_27658_();
      if (!m_69586_()) {
         m_69879_(() -> {
            f_157142_ = matrix4f;
         });
      } else {
         f_157142_ = matrix4f;
      }

   }

   public static void m_157459_(Matrix4f p_157460_) {
      Matrix4f matrix4f = p_157460_.m_27658_();
      if (!m_69586_()) {
         m_69879_(() -> {
            f_157156_ = matrix4f;
         });
      } else {
         f_157156_ = matrix4f;
      }

   }

   public static void m_157423_() {
      if (!m_69586_()) {
         m_69879_(() -> {
            f_157156_.m_27624_();
         });
      } else {
         f_157156_.m_27624_();
      }

   }

   public static void m_157182_() {
      Matrix4f matrix4f = f_157141_.m_85850_().m_85861_().m_27658_();
      if (!m_69586_()) {
         m_69879_(() -> {
            f_157140_ = matrix4f;
         });
      } else {
         f_157140_ = matrix4f;
      }

   }

   public static void m_157183_() {
      if (!m_69586_()) {
         m_69879_(() -> {
            m_157157_();
         });
      } else {
         m_157157_();
      }

   }

   private static void m_157157_() {
      f_157143_ = f_157142_;
   }

   public static void m_157424_() {
      if (!m_69586_()) {
         m_69879_(() -> {
            m_157158_();
         });
      } else {
         m_157158_();
      }

   }

   private static void m_157158_() {
      f_157142_ = f_157143_;
   }

   public static Matrix4f m_157192_() {
      m_69393_(RenderSystem::m_69586_);
      return f_157142_;
   }

   public static Matrix4f m_157190_() {
      m_69393_(RenderSystem::m_69586_);
      return f_157140_;
   }

   public static PoseStack m_157191_() {
      return f_157141_;
   }

   public static Matrix4f m_157207_() {
      m_69393_(RenderSystem::m_69586_);
      return f_157156_;
   }

   public static RenderSystem.AutoStorageIndexBuffer m_157193_(VertexFormat.Mode p_157194_, int p_157195_) {
      m_69393_(RenderSystem::m_69586_);
      RenderSystem.AutoStorageIndexBuffer rendersystem$autostorageindexbuffer;
      if (p_157194_ == VertexFormat.Mode.QUADS) {
         rendersystem$autostorageindexbuffer = f_157155_;
      } else if (p_157194_ == VertexFormat.Mode.LINES) {
         rendersystem$autostorageindexbuffer = f_157154_;
      } else {
         rendersystem$autostorageindexbuffer = f_157153_;
      }

      rendersystem$autostorageindexbuffer.m_157476_(p_157195_);
      return rendersystem$autostorageindexbuffer;
   }

   public static void m_157447_(long p_157448_, float p_157449_) {
      float f = ((float)(p_157448_ % 24000L) + p_157449_) / 24000.0F;
      if (!m_69586_()) {
         m_69879_(() -> {
            f_157149_ = f;
         });
      } else {
         f_157149_ = f;
      }

   }

   public static float m_157201_() {
      m_69393_(RenderSystem::m_69586_);
      return f_157149_;
   }

   static {
      f_157142_.m_27624_();
      f_157143_.m_27624_();
      f_157140_.m_27624_();
      f_157156_.m_27624_();
   }

   @OnlyIn(Dist.CLIENT)
   public static final class AutoStorageIndexBuffer {
      private final int f_157465_;
      private final int f_157466_;
      private final RenderSystem.AutoStorageIndexBuffer.IndexGenerator f_157467_;
      private int f_157468_;
      private VertexFormat.IndexType f_157469_ = VertexFormat.IndexType.BYTE;
      private int f_157470_;

      AutoStorageIndexBuffer(int p_157472_, int p_157473_, RenderSystem.AutoStorageIndexBuffer.IndexGenerator p_157474_) {
         this.f_157465_ = p_157472_;
         this.f_157466_ = p_157473_;
         this.f_157467_ = p_157474_;
      }

      void m_157476_(int p_157477_) {
         if (p_157477_ > this.f_157470_) {
            p_157477_ = Mth.m_144941_(p_157477_ * 2, this.f_157466_);
            RenderSystem.f_69376_.debug("Growing IndexBuffer: Old limit {}, new limit {}.", this.f_157470_, p_157477_);
            if (this.f_157468_ == 0) {
               this.f_157468_ = GlStateManager.m_84537_();
            }

            VertexFormat.IndexType vertexformat$indextype = VertexFormat.IndexType.m_166933_(p_157477_);
            int i = Mth.m_144941_(p_157477_ * vertexformat$indextype.f_166924_, 4);
            GlStateManager.m_84480_(34963, this.f_157468_);
            GlStateManager.m_157070_(34963, (long)i, 35048);
            ByteBuffer bytebuffer = GlStateManager.m_157090_(34963, 35001);
            if (bytebuffer == null) {
               throw new RuntimeException("Failed to map GL buffer");
            } else {
               this.f_157469_ = vertexformat$indextype;
               it.unimi.dsi.fastutil.ints.IntConsumer intconsumer = this.m_157478_(bytebuffer);

               for(int j = 0; j < p_157477_; j += this.f_157466_) {
                  this.f_157467_.m_157487_(intconsumer, j * this.f_157465_ / this.f_157466_);
               }

               GlStateManager.m_157098_(34963);
               GlStateManager.m_84480_(34963, 0);
               this.f_157470_ = p_157477_;
               BufferUploader.m_166846_();
            }
         }
      }

      private it.unimi.dsi.fastutil.ints.IntConsumer m_157478_(ByteBuffer p_157479_) {
         switch(this.f_157469_) {
         case BYTE:
            return (p_157486_) -> {
               p_157479_.put((byte)p_157486_);
            };
         case SHORT:
            return (p_157482_) -> {
               p_157479_.putShort((short)p_157482_);
            };
         case INT:
         default:
            return p_157479_::putInt;
         }
      }

      public int m_157475_() {
         return this.f_157468_;
      }

      public VertexFormat.IndexType m_157483_() {
         return this.f_157469_;
      }

      @OnlyIn(Dist.CLIENT)
      interface IndexGenerator {
         void m_157487_(it.unimi.dsi.fastutil.ints.IntConsumer p_157488_, int p_157489_);
      }
   }
}