package com.mojang.realmsclient.client;

import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.mojang.realmsclient.dto.WorldDownload;
import com.mojang.realmsclient.exception.RealmsDefaultUncaughtExceptionHandler;
import com.mojang.realmsclient.gui.screens.RealmsDownloadLatestWorldScreen;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.CountingOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class FileDownload {
   static final Logger f_86953_ = LogManager.getLogger();
   volatile boolean f_86954_;
   volatile boolean f_86955_;
   volatile boolean f_86956_;
   volatile boolean f_86957_;
   private volatile File f_86958_;
   volatile File f_86959_;
   private volatile HttpGet f_86960_;
   private Thread f_86961_;
   private final RequestConfig f_86962_ = RequestConfig.custom().setSocketTimeout(120000).setConnectTimeout(120000).build();
   private static final String[] f_86963_ = new String[]{"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};

   public long m_86989_(String p_86990_) {
      CloseableHttpClient closeablehttpclient = null;
      HttpGet httpget = null;

      long i;
      try {
         httpget = new HttpGet(p_86990_);
         closeablehttpclient = HttpClientBuilder.create().setDefaultRequestConfig(this.f_86962_).build();
         CloseableHttpResponse closeablehttpresponse = closeablehttpclient.execute(httpget);
         return Long.parseLong(closeablehttpresponse.getFirstHeader("Content-Length").getValue());
      } catch (Throwable throwable) {
         f_86953_.error("Unable to get content length for download");
         i = 0L;
      } finally {
         if (httpget != null) {
            httpget.releaseConnection();
         }

         if (closeablehttpclient != null) {
            try {
               closeablehttpclient.close();
            } catch (IOException ioexception) {
               f_86953_.error("Could not close http client", (Throwable)ioexception);
            }
         }

      }

      return i;
   }

   public void m_86982_(WorldDownload p_86983_, String p_86984_, RealmsDownloadLatestWorldScreen.DownloadStatus p_86985_, LevelStorageSource p_86986_) {
      if (this.f_86961_ == null) {
         this.f_86961_ = new Thread(() -> {
            CloseableHttpClient closeablehttpclient = null;

            try {
               this.f_86958_ = File.createTempFile("backup", ".tar.gz");
               this.f_86960_ = new HttpGet(p_86983_.f_87718_);
               closeablehttpclient = HttpClientBuilder.create().setDefaultRequestConfig(this.f_86962_).build();
               HttpResponse httpresponse = closeablehttpclient.execute(this.f_86960_);
               p_86985_.f_88661_ = Long.parseLong(httpresponse.getFirstHeader("Content-Length").getValue());
               if (httpresponse.getStatusLine().getStatusCode() == 200) {
                  OutputStream outputstream = new FileOutputStream(this.f_86958_);
                  FileDownload.ProgressListener filedownload$progresslistener = new FileDownload.ProgressListener(p_86984_.trim(), this.f_86958_, p_86986_, p_86985_);
                  FileDownload.DownloadCountingOutputStream filedownload$downloadcountingoutputstream = new FileDownload.DownloadCountingOutputStream(outputstream);
                  filedownload$downloadcountingoutputstream.m_87016_(filedownload$progresslistener);
                  IOUtils.copy(httpresponse.getEntity().getContent(), filedownload$downloadcountingoutputstream);
                  return;
               }

               this.f_86956_ = true;
               this.f_86960_.abort();
            } catch (Exception exception1) {
               f_86953_.error("Caught exception while downloading: {}", (Object)exception1.getMessage());
               this.f_86956_ = true;
               return;
            } finally {
               this.f_86960_.releaseConnection();
               if (this.f_86958_ != null) {
                  this.f_86958_.delete();
               }

               if (!this.f_86956_) {
                  if (!p_86983_.f_87719_.isEmpty() && !p_86983_.f_87720_.isEmpty()) {
                     try {
                        this.f_86958_ = File.createTempFile("resources", ".tar.gz");
                        this.f_86960_ = new HttpGet(p_86983_.f_87719_);
                        HttpResponse httpresponse1 = closeablehttpclient.execute(this.f_86960_);
                        p_86985_.f_88661_ = Long.parseLong(httpresponse1.getFirstHeader("Content-Length").getValue());
                        if (httpresponse1.getStatusLine().getStatusCode() != 200) {
                           this.f_86956_ = true;
                           this.f_86960_.abort();
                           return;
                        }

                        OutputStream outputstream1 = new FileOutputStream(this.f_86958_);
                        FileDownload.ResourcePackProgressListener filedownload$resourcepackprogresslistener = new FileDownload.ResourcePackProgressListener(this.f_86958_, p_86985_, p_86983_);
                        FileDownload.DownloadCountingOutputStream filedownload$downloadcountingoutputstream1 = new FileDownload.DownloadCountingOutputStream(outputstream1);
                        filedownload$downloadcountingoutputstream1.m_87016_(filedownload$resourcepackprogresslistener);
                        IOUtils.copy(httpresponse1.getEntity().getContent(), filedownload$downloadcountingoutputstream1);
                     } catch (Exception exception) {
                        f_86953_.error("Caught exception while downloading: {}", (Object)exception.getMessage());
                        this.f_86956_ = true;
                     } finally {
                        this.f_86960_.releaseConnection();
                        if (this.f_86958_ != null) {
                           this.f_86958_.delete();
                        }

                     }
                  } else {
                     this.f_86955_ = true;
                  }
               }

               if (closeablehttpclient != null) {
                  try {
                     closeablehttpclient.close();
                  } catch (IOException ioexception) {
                     f_86953_.error("Failed to close Realms download client");
                  }
               }

            }

         });
         this.f_86961_.setUncaughtExceptionHandler(new RealmsDefaultUncaughtExceptionHandler(f_86953_));
         this.f_86961_.start();
      }
   }

   public void m_86966_() {
      if (this.f_86960_ != null) {
         this.f_86960_.abort();
      }

      if (this.f_86958_ != null) {
         this.f_86958_.delete();
      }

      this.f_86954_ = true;
   }

   public boolean m_86995_() {
      return this.f_86955_;
   }

   public boolean m_87003_() {
      return this.f_86956_;
   }

   public boolean m_87009_() {
      return this.f_86957_;
   }

   public static String m_87001_(String p_87002_) {
      p_87002_ = p_87002_.replaceAll("[\\./\"]", "_");

      for(String s : f_86963_) {
         if (p_87002_.equalsIgnoreCase(s)) {
            p_87002_ = "_" + p_87002_ + "_";
         }
      }

      return p_87002_;
   }

   void m_86991_(String p_86992_, File p_86993_, LevelStorageSource p_86994_) throws IOException {
      Pattern pattern = Pattern.compile(".*-([0-9]+)$");
      int i = 1;

      for(char c0 : SharedConstants.f_136184_) {
         p_86992_ = p_86992_.replace(c0, '_');
      }

      if (StringUtils.isEmpty(p_86992_)) {
         p_86992_ = "Realm";
      }

      p_86992_ = m_87001_(p_86992_);

      try {
         for(LevelSummary levelsummary : p_86994_.m_78244_()) {
            if (levelsummary.m_78358_().toLowerCase(Locale.ROOT).startsWith(p_86992_.toLowerCase(Locale.ROOT))) {
               Matcher matcher = pattern.matcher(levelsummary.m_78358_());
               if (matcher.matches()) {
                  if (Integer.valueOf(matcher.group(1)) > i) {
                     i = Integer.valueOf(matcher.group(1));
                  }
               } else {
                  ++i;
               }
            }
         }
      } catch (Exception exception1) {
         f_86953_.error("Error getting level list", (Throwable)exception1);
         this.f_86956_ = true;
         return;
      }

      String s;
      if (p_86994_.m_78240_(p_86992_) && i <= 1) {
         s = p_86992_;
      } else {
         s = p_86992_ + (i == 1 ? "" : "-" + i);
         if (!p_86994_.m_78240_(s)) {
            boolean flag = false;

            while(!flag) {
               ++i;
               s = p_86992_ + (i == 1 ? "" : "-" + i);
               if (p_86994_.m_78240_(s)) {
                  flag = true;
               }
            }
         }
      }

      TarArchiveInputStream tararchiveinputstream = null;
      File file1 = new File(Minecraft.m_91087_().f_91069_.getAbsolutePath(), "saves");

      try {
         file1.mkdir();
         tararchiveinputstream = new TarArchiveInputStream(new GzipCompressorInputStream(new BufferedInputStream(new FileInputStream(p_86993_))));

         for(TarArchiveEntry tararchiveentry = tararchiveinputstream.getNextTarEntry(); tararchiveentry != null; tararchiveentry = tararchiveinputstream.getNextTarEntry()) {
            File file2 = new File(file1, tararchiveentry.getName().replace("world", s));
            if (tararchiveentry.isDirectory()) {
               file2.mkdirs();
            } else {
               file2.createNewFile();
               FileOutputStream fileoutputstream = new FileOutputStream(file2);

               try {
                  IOUtils.copy(tararchiveinputstream, fileoutputstream);
               } catch (Throwable throwable2) {
                  try {
                     fileoutputstream.close();
                  } catch (Throwable throwable1) {
                     throwable2.addSuppressed(throwable1);
                  }

                  throw throwable2;
               }

               fileoutputstream.close();
            }
         }
      } catch (Exception exception) {
         f_86953_.error("Error extracting world", (Throwable)exception);
         this.f_86956_ = true;
      } finally {
         if (tararchiveinputstream != null) {
            tararchiveinputstream.close();
         }

         if (p_86993_ != null) {
            p_86993_.delete();
         }

         try {
            LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = p_86994_.m_78260_(s);

            try {
               levelstoragesource$levelstorageaccess.m_78297_(s.trim());
               Path path = levelstoragesource$levelstorageaccess.m_78283_(LevelResource.f_78178_);
               m_86987_(path.toFile());
            } catch (Throwable throwable3) {
               if (levelstoragesource$levelstorageaccess != null) {
                  try {
                     levelstoragesource$levelstorageaccess.close();
                  } catch (Throwable throwable) {
                     throwable3.addSuppressed(throwable);
                  }
               }

               throw throwable3;
            }

            if (levelstoragesource$levelstorageaccess != null) {
               levelstoragesource$levelstorageaccess.close();
            }
         } catch (IOException ioexception) {
            f_86953_.error("Failed to rename unpacked realms level {}", s, ioexception);
         }

         this.f_86959_ = new File(file1, s + File.separator + "resources.zip");
      }

   }

   private static void m_86987_(File p_86988_) {
      if (p_86988_.exists()) {
         try {
            CompoundTag compoundtag = NbtIo.m_128937_(p_86988_);
            CompoundTag compoundtag1 = compoundtag.m_128469_("Data");
            compoundtag1.m_128473_("Player");
            NbtIo.m_128944_(compoundtag, p_86988_);
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   class DownloadCountingOutputStream extends CountingOutputStream {
      private ActionListener f_87012_;

      public DownloadCountingOutputStream(OutputStream p_87015_) {
         super(p_87015_);
      }

      public void m_87016_(ActionListener p_87017_) {
         this.f_87012_ = p_87017_;
      }

      protected void afterWrite(int p_87019_) throws IOException {
         super.afterWrite(p_87019_);
         if (this.f_87012_ != null) {
            this.f_87012_.actionPerformed(new ActionEvent(this, 0, (String)null));
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   class ProgressListener implements ActionListener {
      private final String f_87021_;
      private final File f_87022_;
      private final LevelStorageSource f_87023_;
      private final RealmsDownloadLatestWorldScreen.DownloadStatus f_87024_;

      ProgressListener(String p_87027_, File p_87028_, LevelStorageSource p_87029_, RealmsDownloadLatestWorldScreen.DownloadStatus p_87030_) {
         this.f_87021_ = p_87027_;
         this.f_87022_ = p_87028_;
         this.f_87023_ = p_87029_;
         this.f_87024_ = p_87030_;
      }

      public void actionPerformed(ActionEvent p_87039_) {
         this.f_87024_.f_88660_ = ((FileDownload.DownloadCountingOutputStream)p_87039_.getSource()).getByteCount();
         if (this.f_87024_.f_88660_ >= this.f_87024_.f_88661_ && !FileDownload.this.f_86954_ && !FileDownload.this.f_86956_) {
            try {
               FileDownload.this.f_86957_ = true;
               FileDownload.this.m_86991_(this.f_87021_, this.f_87022_, this.f_87023_);
            } catch (IOException ioexception) {
               FileDownload.f_86953_.error("Error extracting archive", (Throwable)ioexception);
               FileDownload.this.f_86956_ = true;
            }
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   class ResourcePackProgressListener implements ActionListener {
      private final File f_87041_;
      private final RealmsDownloadLatestWorldScreen.DownloadStatus f_87042_;
      private final WorldDownload f_87043_;

      ResourcePackProgressListener(File p_87046_, RealmsDownloadLatestWorldScreen.DownloadStatus p_87047_, WorldDownload p_87048_) {
         this.f_87041_ = p_87046_;
         this.f_87042_ = p_87047_;
         this.f_87043_ = p_87048_;
      }

      public void actionPerformed(ActionEvent p_87056_) {
         this.f_87042_.f_88660_ = ((FileDownload.DownloadCountingOutputStream)p_87056_.getSource()).getByteCount();
         if (this.f_87042_.f_88660_ >= this.f_87042_.f_88661_ && !FileDownload.this.f_86954_) {
            try {
               String s = Hashing.sha1().hashBytes(Files.toByteArray(this.f_87041_)).toString();
               if (s.equals(this.f_87043_.f_87720_)) {
                  FileUtils.copyFile(this.f_87041_, FileDownload.this.f_86959_);
                  FileDownload.this.f_86955_ = true;
               } else {
                  FileDownload.f_86953_.error("Resourcepack had wrong hash (expected {}, found {}). Deleting it.", this.f_87043_.f_87720_, s);
                  FileUtils.deleteQuietly(this.f_87041_);
                  FileDownload.this.f_86956_ = true;
               }
            } catch (IOException ioexception) {
               FileDownload.f_86953_.error("Error copying resourcepack file: {}", (Object)ioexception.getMessage());
               FileDownload.this.f_86956_ = true;
            }
         }

      }
   }
}