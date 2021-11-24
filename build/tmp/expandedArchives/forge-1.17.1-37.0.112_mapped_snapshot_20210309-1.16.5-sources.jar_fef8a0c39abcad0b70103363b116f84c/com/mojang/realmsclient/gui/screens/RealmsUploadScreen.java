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
import com.mojang.realmsclient.client.FileUpload;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.client.UploadStatus;
import com.mojang.realmsclient.dto.UploadInfo;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.exception.RetryCallException;
import com.mojang.realmsclient.util.UploadTokenCache;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.GZIPOutputStream;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class RealmsUploadScreen extends RealmsScreen {
   private static final Logger f_90067_ = LogManager.getLogger();
   private static final ReentrantLock f_90068_ = new ReentrantLock();
   private static final String[] f_90069_ = new String[]{"", ".", ". .", ". . ."};
   private static final Component f_90070_ = new TranslatableComponent("mco.upload.verifying");
   private final RealmsResetWorldScreen f_90071_;
   private final LevelSummary f_90072_;
   private final long f_90073_;
   private final int f_90074_;
   private final UploadStatus f_90075_;
   private final RateLimiter f_90076_;
   private volatile Component[] f_90077_;
   private volatile Component f_90078_ = new TranslatableComponent("mco.upload.preparing");
   private volatile String f_90079_;
   private volatile boolean f_90080_;
   private volatile boolean f_90057_;
   private volatile boolean f_90058_ = true;
   private volatile boolean f_90059_;
   private Button f_90060_;
   private Button f_90061_;
   private int f_90062_;
   private Long f_90063_;
   private Long f_90064_;
   private long f_90065_;
   private final Runnable f_90066_;

   public RealmsUploadScreen(long p_90083_, int p_90084_, RealmsResetWorldScreen p_90085_, LevelSummary p_90086_, Runnable p_90087_) {
      super(NarratorChatListener.f_93310_);
      this.f_90073_ = p_90083_;
      this.f_90074_ = p_90084_;
      this.f_90071_ = p_90085_;
      this.f_90072_ = p_90086_;
      this.f_90075_ = new UploadStatus();
      this.f_90076_ = RateLimiter.create((double)0.1F);
      this.f_90066_ = p_90087_;
   }

   public void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_90060_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 42, 200, 20, CommonComponents.f_130660_, (p_90118_) -> {
         this.m_90127_();
      }));
      this.f_90060_.f_93624_ = false;
      this.f_90061_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ - 42, 200, 20, CommonComponents.f_130656_, (p_90104_) -> {
         this.m_90128_();
      }));
      if (!this.f_90059_) {
         if (this.f_90071_.f_89313_ == -1) {
            this.m_90129_();
         } else {
            this.f_90071_.m_89382_(() -> {
               if (!this.f_90059_) {
                  this.f_90059_ = true;
                  this.f_96541_.m_91152_(this);
                  this.m_90129_();
               }

            });
         }
      }

   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   private void m_90127_() {
      this.f_90066_.run();
   }

   private void m_90128_() {
      this.f_90080_ = true;
      this.f_96541_.m_91152_(this.f_90071_);
   }

   public boolean m_7933_(int p_90089_, int p_90090_, int p_90091_) {
      if (p_90089_ == 256) {
         if (this.f_90058_) {
            this.m_90128_();
         } else {
            this.m_90127_();
         }

         return true;
      } else {
         return super.m_7933_(p_90089_, p_90090_, p_90091_);
      }
   }

   public void m_6305_(PoseStack p_90096_, int p_90097_, int p_90098_, float p_90099_) {
      this.m_7333_(p_90096_);
      if (!this.f_90057_ && this.f_90075_.f_87386_ != 0L && this.f_90075_.f_87386_ == this.f_90075_.f_87387_) {
         this.f_90078_ = f_90070_;
         this.f_90061_.f_93623_ = false;
      }

      m_93215_(p_90096_, this.f_96547_, this.f_90078_, this.f_96543_ / 2, 50, 16777215);
      if (this.f_90058_) {
         this.m_90115_(p_90096_);
      }

      if (this.f_90075_.f_87386_ != 0L && !this.f_90080_) {
         this.m_90121_(p_90096_);
         this.m_90124_(p_90096_);
      }

      if (this.f_90077_ != null) {
         for(int i = 0; i < this.f_90077_.length; ++i) {
            m_93215_(p_90096_, this.f_96547_, this.f_90077_[i], this.f_96543_ / 2, 110 + 12 * i, 16711680);
         }
      }

      super.m_6305_(p_90096_, p_90097_, p_90098_, p_90099_);
   }

   private void m_90115_(PoseStack p_90116_) {
      int i = this.f_96547_.m_92852_(this.f_90078_);
      this.f_96547_.m_92883_(p_90116_, f_90069_[this.f_90062_ / 10 % f_90069_.length], (float)(this.f_96543_ / 2 + i / 2 + 5), 50.0F, 16777215);
   }

   private void m_90121_(PoseStack p_90122_) {
      double d0 = Math.min((double)this.f_90075_.f_87386_ / (double)this.f_90075_.f_87387_, 1.0D);
      this.f_90079_ = String.format(Locale.ROOT, "%.1f", d0 * 100.0D);
      RenderSystem.m_157427_(GameRenderer::m_172811_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_69472_();
      double d1 = (double)(this.f_96543_ / 2 - 100);
      double d2 = 0.5D;
      Tesselator tesselator = Tesselator.m_85913_();
      BufferBuilder bufferbuilder = tesselator.m_85915_();
      bufferbuilder.m_166779_(VertexFormat.Mode.QUADS, DefaultVertexFormat.f_85815_);
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
      m_93208_(p_90122_, this.f_96547_, this.f_90079_ + " %", this.f_96543_ / 2, 84, 16777215);
   }

   private void m_90124_(PoseStack p_90125_) {
      if (this.f_90062_ % 20 == 0) {
         if (this.f_90063_ != null) {
            long i = Util.m_137550_() - this.f_90064_;
            if (i == 0L) {
               i = 1L;
            }

            this.f_90065_ = 1000L * (this.f_90075_.f_87386_ - this.f_90063_) / i;
            this.m_90100_(p_90125_, this.f_90065_);
         }

         this.f_90063_ = this.f_90075_.f_87386_;
         this.f_90064_ = Util.m_137550_();
      } else {
         this.m_90100_(p_90125_, this.f_90065_);
      }

   }

   private void m_90100_(PoseStack p_90101_, long p_90102_) {
      if (p_90102_ > 0L) {
         int i = this.f_96547_.m_92895_(this.f_90079_);
         String s = "(" + Unit.m_86945_(p_90102_) + "/s)";
         this.f_96547_.m_92883_(p_90101_, s, (float)(this.f_96543_ / 2 + i / 2 + 15), 84.0F, 16777215);
      }

   }

   public void m_96624_() {
      super.m_96624_();
      ++this.f_90062_;
      if (this.f_90078_ != null && this.f_90076_.tryAcquire(1)) {
         Component component = this.m_167558_();
         NarratorChatListener.f_93311_.m_168785_(component);
      }

   }

   private Component m_167558_() {
      List<Component> list = Lists.newArrayList();
      list.add(this.f_90078_);
      if (this.f_90079_ != null) {
         list.add(new TextComponent(this.f_90079_ + "%"));
      }

      if (this.f_90077_ != null) {
         list.addAll(Arrays.asList(this.f_90077_));
      }

      return CommonComponents.m_178391_(list);
   }

   private void m_90129_() {
      this.f_90059_ = true;
      (new Thread(() -> {
         File file1 = null;
         RealmsClient realmsclient = RealmsClient.m_87169_();
         long i = this.f_90073_;

         try {
            if (f_90068_.tryLock(1L, TimeUnit.SECONDS)) {
               UploadInfo uploadinfo = null;

               for(int j = 0; j < 20; ++j) {
                  try {
                     if (this.f_90080_) {
                        this.m_90130_();
                        return;
                     }

                     uploadinfo = realmsclient.m_87256_(i, UploadTokenCache.m_90292_(i));
                     if (uploadinfo != null) {
                        break;
                     }
                  } catch (RetryCallException retrycallexception) {
                     Thread.sleep((long)(retrycallexception.f_87787_ * 1000));
                  }
               }

               if (uploadinfo == null) {
                  this.f_90078_ = new TranslatableComponent("mco.upload.close.failure");
                  return;
               }

               UploadTokenCache.m_90294_(i, uploadinfo.m_87696_());
               if (!uploadinfo.m_87711_()) {
                  this.f_90078_ = new TranslatableComponent("mco.upload.close.failure");
                  return;
               }

               if (this.f_90080_) {
                  this.m_90130_();
                  return;
               }

               File file2 = new File(this.f_96541_.f_91069_.getAbsolutePath(), "saves");
               file1 = this.m_90119_(new File(file2, this.f_90072_.m_78358_()));
               if (this.f_90080_) {
                  this.m_90130_();
                  return;
               }

               if (this.m_90105_(file1)) {
                  this.f_90078_ = new TranslatableComponent("mco.upload.uploading", this.f_90072_.m_78361_());
                  FileUpload fileupload = new FileUpload(file1, this.f_90073_, this.f_90074_, uploadinfo, this.f_96541_.m_91094_(), SharedConstants.m_136187_().getName(), this.f_90075_);
                  fileupload.m_87084_((p_167557_) -> {
                     if (p_167557_.f_90133_ >= 200 && p_167557_.f_90133_ < 300) {
                        this.f_90057_ = true;
                        this.f_90078_ = new TranslatableComponent("mco.upload.done");
                        this.f_90060_.m_93666_(CommonComponents.f_130655_);
                        UploadTokenCache.m_90297_(i);
                     } else if (p_167557_.f_90133_ == 400 && p_167557_.f_90134_ != null) {
                        this.m_90112_(new TranslatableComponent("mco.upload.failed", p_167557_.f_90134_));
                     } else {
                        this.m_90112_(new TranslatableComponent("mco.upload.failed", p_167557_.f_90133_));
                     }

                  });

                  while(!fileupload.m_87096_()) {
                     if (this.f_90080_) {
                        fileupload.m_87078_();
                        this.m_90130_();
                        return;
                     }

                     try {
                        Thread.sleep(500L);
                     } catch (InterruptedException interruptedexception) {
                        f_90067_.error("Failed to check Realms file upload status");
                     }
                  }

                  return;
               }

               long k = file1.length();
               Unit unit = Unit.m_86940_(k);
               Unit unit1 = Unit.m_86940_(5368709120L);
               if (Unit.m_86947_(k, unit).equals(Unit.m_86947_(5368709120L, unit1)) && unit != Unit.B) {
                  Unit unit2 = Unit.values()[unit.ordinal() - 1];
                  this.m_90112_(new TranslatableComponent("mco.upload.size.failure.line1", this.f_90072_.m_78361_()), new TranslatableComponent("mco.upload.size.failure.line2", Unit.m_86947_(k, unit2), Unit.m_86947_(5368709120L, unit2)));
                  return;
               }

               this.m_90112_(new TranslatableComponent("mco.upload.size.failure.line1", this.f_90072_.m_78361_()), new TranslatableComponent("mco.upload.size.failure.line2", Unit.m_86947_(k, unit), Unit.m_86947_(5368709120L, unit1)));
               return;
            }

            this.f_90078_ = new TranslatableComponent("mco.upload.close.failure");
         } catch (IOException ioexception) {
            this.m_90112_(new TranslatableComponent("mco.upload.failed", ioexception.getMessage()));
            return;
         } catch (RealmsServiceException realmsserviceexception) {
            this.m_90112_(new TranslatableComponent("mco.upload.failed", realmsserviceexception.toString()));
            return;
         } catch (InterruptedException interruptedexception1) {
            f_90067_.error("Could not acquire upload lock");
            return;
         } finally {
            this.f_90057_ = true;
            if (f_90068_.isHeldByCurrentThread()) {
               f_90068_.unlock();
               this.f_90058_ = false;
               this.f_90060_.f_93624_ = true;
               this.f_90061_.f_93624_ = false;
               if (file1 != null) {
                  f_90067_.debug("Deleting file {}", (Object)file1.getAbsolutePath());
                  file1.delete();
               }

            }

            return;
         }

      })).start();
   }

   private void m_90112_(Component... p_90113_) {
      this.f_90077_ = p_90113_;
   }

   private void m_90130_() {
      this.f_90078_ = new TranslatableComponent("mco.upload.cancelled");
      f_90067_.debug("Upload was cancelled");
   }

   private boolean m_90105_(File p_90106_) {
      return p_90106_.length() < 5368709120L;
   }

   private File m_90119_(File p_90120_) throws IOException {
      TarArchiveOutputStream tararchiveoutputstream = null;

      File file2;
      try {
         File file1 = File.createTempFile("realms-upload-file", ".tar.gz");
         tararchiveoutputstream = new TarArchiveOutputStream(new GZIPOutputStream(new FileOutputStream(file1)));
         tararchiveoutputstream.setLongFileMode(3);
         this.m_90107_(tararchiveoutputstream, p_90120_.getAbsolutePath(), "world", true);
         tararchiveoutputstream.finish();
         file2 = file1;
      } finally {
         if (tararchiveoutputstream != null) {
            tararchiveoutputstream.close();
         }

      }

      return file2;
   }

   private void m_90107_(TarArchiveOutputStream p_90108_, String p_90109_, String p_90110_, boolean p_90111_) throws IOException {
      if (!this.f_90080_) {
         File file1 = new File(p_90109_);
         String s = p_90111_ ? p_90110_ : p_90110_ + file1.getName();
         TarArchiveEntry tararchiveentry = new TarArchiveEntry(file1, s);
         p_90108_.putArchiveEntry(tararchiveentry);
         if (file1.isFile()) {
            IOUtils.copy(new FileInputStream(file1), p_90108_);
            p_90108_.closeArchiveEntry();
         } else {
            p_90108_.closeArchiveEntry();
            File[] afile = file1.listFiles();
            if (afile != null) {
               for(File file2 : afile) {
                  this.m_90107_(p_90108_, file2.getAbsolutePath(), s + "/", false);
               }
            }
         }

      }
   }
}