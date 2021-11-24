package com.mojang.realmsclient.gui.screens;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.realmsclient.Unit;
import com.mojang.realmsclient.client.FileDownload;
import com.mojang.realmsclient.dto.WorldDownload;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsDownloadLatestWorldScreen extends RealmsScreen {
   private static final Logger f_88609_ = LogManager.getLogger();
   private static final ReentrantLock f_88610_ = new ReentrantLock();
   private final Screen f_88611_;
   private final WorldDownload f_88612_;
   private final Component f_88613_;
   private final RateLimiter f_88614_;
   private Button f_88615_;
   private final String f_88616_;
   private final RealmsDownloadLatestWorldScreen.DownloadStatus f_88617_;
   private volatile Component f_88618_;
   private volatile Component f_88619_ = new TranslatableComponent("mco.download.preparing");
   private volatile String f_88620_;
   private volatile boolean f_88621_;
   private volatile boolean f_88622_ = true;
   private volatile boolean f_88599_;
   private volatile boolean f_88600_;
   private Long f_88601_;
   private Long f_88602_;
   private long f_88603_;
   private int f_88604_;
   private static final String[] f_88605_ = new String[]{"", ".", ". .", ". . ."};
   private int f_88606_;
   private boolean f_88607_;
   private final BooleanConsumer f_88608_;

   public RealmsDownloadLatestWorldScreen(Screen p_88625_, WorldDownload p_88626_, String p_88627_, BooleanConsumer p_88628_) {
      super(NarratorChatListener.f_93310_);
      this.f_88608_ = p_88628_;
      this.f_88611_ = p_88625_;
      this.f_88616_ = p_88627_;
      this.f_88612_ = p_88626_;
      this.f_88617_ = new RealmsDownloadLatestWorldScreen.DownloadStatus();
      this.f_88613_ = new TranslatableComponent("mco.download.title");
      this.f_88614_ = RateLimiter.create((double)0.1F);
   }

   public void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_88615_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 42, 200, 20, CommonComponents.f_130656_, (p_88642_) -> {
         this.f_88621_ = true;
         this.m_88656_();
      }));
      this.m_88655_();
   }

   private void m_88655_() {
      if (!this.f_88599_) {
         if (!this.f_88607_ && this.m_88646_(this.f_88612_.f_87718_) >= 5368709120L) {
            Component component = new TranslatableComponent("mco.download.confirmation.line1", Unit.m_86945_(5368709120L));
            Component component1 = new TranslatableComponent("mco.download.confirmation.line2");
            this.f_96541_.m_91152_(new RealmsLongConfirmationScreen((p_88651_) -> {
               this.f_88607_ = true;
               this.f_96541_.m_91152_(this);
               this.m_88657_();
            }, RealmsLongConfirmationScreen.Type.Warning, component, component1, false));
         } else {
            this.m_88657_();
         }

      }
   }

   private long m_88646_(String p_88647_) {
      FileDownload filedownload = new FileDownload();
      return filedownload.m_86989_(p_88647_);
   }

   public void m_96624_() {
      super.m_96624_();
      ++this.f_88604_;
      if (this.f_88619_ != null && this.f_88614_.tryAcquire(1)) {
         Component component = this.m_167409_();
         NarratorChatListener.f_93311_.m_168785_(component);
      }

   }

   private Component m_167409_() {
      List<Component> list = Lists.newArrayList();
      list.add(this.f_88613_);
      list.add(this.f_88619_);
      if (this.f_88620_ != null) {
         list.add(new TextComponent(this.f_88620_ + "%"));
         list.add(new TextComponent(Unit.m_86945_(this.f_88603_) + "/s"));
      }

      if (this.f_88618_ != null) {
         list.add(this.f_88618_);
      }

      return CommonComponents.m_178391_(list);
   }

   public boolean m_7933_(int p_88630_, int p_88631_, int p_88632_) {
      if (p_88630_ == 256) {
         this.f_88621_ = true;
         this.m_88656_();
         return true;
      } else {
         return super.m_7933_(p_88630_, p_88631_, p_88632_);
      }
   }

   private void m_88656_() {
      if (this.f_88599_ && this.f_88608_ != null && this.f_88618_ == null) {
         this.f_88608_.accept(true);
      }

      this.f_96541_.m_91152_(this.f_88611_);
   }

   public void m_6305_(PoseStack p_88634_, int p_88635_, int p_88636_, float p_88637_) {
      this.m_7333_(p_88634_);
      m_93215_(p_88634_, this.f_96547_, this.f_88613_, this.f_96543_ / 2, 20, 16777215);
      m_93215_(p_88634_, this.f_96547_, this.f_88619_, this.f_96543_ / 2, 50, 16777215);
      if (this.f_88622_) {
         this.m_88644_(p_88634_);
      }

      if (this.f_88617_.f_88660_ != 0L && !this.f_88621_) {
         this.m_88648_(p_88634_);
         this.m_88653_(p_88634_);
      }

      if (this.f_88618_ != null) {
         m_93215_(p_88634_, this.f_96547_, this.f_88618_, this.f_96543_ / 2, 110, 16711680);
      }

      super.m_6305_(p_88634_, p_88635_, p_88636_, p_88637_);
   }

   private void m_88644_(PoseStack p_88645_) {
      int i = this.f_96547_.m_92852_(this.f_88619_);
      if (this.f_88604_ % 10 == 0) {
         ++this.f_88606_;
      }

      this.f_96547_.m_92883_(p_88645_, f_88605_[this.f_88606_ % f_88605_.length], (float)(this.f_96543_ / 2 + i / 2 + 5), 50.0F, 16777215);
   }

   private void m_88648_(PoseStack p_88649_) {
      double d0 = Math.min((double)this.f_88617_.f_88660_ / (double)this.f_88617_.f_88661_, 1.0D);
      this.f_88620_ = String.format(Locale.ROOT, "%.1f", d0 * 100.0D);
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69472_();
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
      double d1 = (double)(this.f_96543_ / 2 - 100);
      double d2 = 0.5D;
      bufferbuilder.m_5483_(d1 - 0.5D, 95.5D, 0.0D).m_6122_(217, 210, 210, 255).m_5752_();
      bufferbuilder.m_5483_(d1 + 200.0D * d0 + 0.5D, 95.5D, 0.0D).m_6122_(217, 210, 210, 255).m_5752_();
      bufferbuilder.m_5483_(d1 + 200.0D * d0 + 0.5D, 79.5D, 0.0D).m_6122_(217, 210, 210, 255).m_5752_();
      bufferbuilder.m_5483_(d1 - 0.5D, 79.5D, 0.0D).m_6122_(217, 210, 210, 255).m_5752_();
      bufferbuilder.m_5483_(d1, 95.0D, 0.0D).m_6122_(128, 128, 128, 255).m_5752_();
      bufferbuilder.m_5483_(d1 + 200.0D * d0, 95.0D, 0.0D).m_6122_(128, 128, 128, 255).m_5752_();
      bufferbuilder.m_5483_(d1 + 200.0D * d0, 80.0D, 0.0D).m_6122_(128, 128, 128, 255).m_5752_();
      bufferbuilder.m_5483_(d1, 80.0D, 0.0D).m_6122_(128, 128, 128, 255).m_5752_();
      tesselator.m_85914_();
      RenderSystem.m_69493_();
      m_93208_(p_88649_, this.f_96547_, this.f_88620_ + " %", this.f_96543_ / 2, 84, 16777215);
   }

   private void m_88653_(PoseStack p_88654_) {
      if (this.f_88604_ % 20 == 0) {
         if (this.f_88601_ != null) {
            long i = Util.m_137550_() - this.f_88602_;
            if (i == 0L) {
               i = 1L;
            }

            this.f_88603_ = 1000L * (this.f_88617_.f_88660_ - this.f_88601_) / i;
            this.m_88638_(p_88654_, this.f_88603_);
         }

         this.f_88601_ = this.f_88617_.f_88660_;
         this.f_88602_ = Util.m_137550_();
      } else {
         this.m_88638_(p_88654_, this.f_88603_);
      }

   }

   private void m_88638_(PoseStack p_88639_, long p_88640_) {
      if (p_88640_ > 0L) {
         int i = this.f_96547_.m_92895_(this.f_88620_);
         String s = "(" + Unit.m_86945_(p_88640_) + "/s)";
         this.f_96547_.m_92883_(p_88639_, s, (float)(this.f_96543_ / 2 + i / 2 + 15), 84.0F, 16777215);
      }

   }

   private void m_88657_() {
      (new Thread(() -> {
         try {
            if (f_88610_.tryLock(1L, TimeUnit.SECONDS)) {
               if (this.f_88621_) {
                  this.m_88658_();
                  return;
               }

               this.f_88619_ = new TranslatableComponent("mco.download.downloading", this.f_88616_);
               FileDownload filedownload = new FileDownload();
               filedownload.m_86989_(this.f_88612_.f_87718_);
               filedownload.m_86982_(this.f_88612_, this.f_88616_, this.f_88617_, this.f_96541_.m_91392_());

               while(!filedownload.m_86995_()) {
                  if (filedownload.m_87003_()) {
                     filedownload.m_86966_();
                     this.f_88618_ = new TranslatableComponent("mco.download.failed");
                     this.f_88615_.m_93666_(CommonComponents.f_130655_);
                     return;
                  }

                  if (filedownload.m_87009_()) {
                     if (!this.f_88600_) {
                        this.f_88619_ = new TranslatableComponent("mco.download.extracting");
                     }

                     this.f_88600_ = true;
                  }

                  if (this.f_88621_) {
                     filedownload.m_86966_();
                     this.m_88658_();
                     return;
                  }

                  try {
                     Thread.sleep(500L);
                  } catch (InterruptedException interruptedexception) {
                     f_88609_.error("Failed to check Realms backup download status");
                  }
               }

               this.f_88599_ = true;
               this.f_88619_ = new TranslatableComponent("mco.download.done");
               this.f_88615_.m_93666_(CommonComponents.f_130655_);
               return;
            }

            this.f_88619_ = new TranslatableComponent("mco.download.failed");
         } catch (InterruptedException interruptedexception1) {
            f_88609_.error("Could not acquire upload lock");
            return;
         } catch (Exception exception) {
            this.f_88618_ = new TranslatableComponent("mco.download.failed");
            exception.printStackTrace();
            return;
         } finally {
            if (!f_88610_.isHeldByCurrentThread()) {
               return;
            }

            f_88610_.unlock();
            this.f_88622_ = false;
            this.f_88599_ = true;
         }

      })).start();
   }

   private void m_88658_() {
      this.f_88619_ = new TranslatableComponent("mco.download.cancelled");
   }

   @OnlyIn(Dist.CLIENT)
   public class DownloadStatus {
      public volatile long f_88660_;
      public volatile long f_88661_;
   }
}