package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import net.minecraft.util.HttpUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class ClientPackSource implements RepositorySource {
   public static final PackMetadataSection f_174791_ = new PackMetadataSection(new TranslatableComponent("resourcePack.vanilla.description"), PackType.CLIENT_RESOURCES.m_143756_(SharedConstants.m_136187_()));
   private static final Logger f_118543_ = LogManager.getLogger();
   private static final Pattern f_118544_ = Pattern.compile("^[a-fA-F0-9]{40}$");
   private static final int f_174792_ = 104857600;
   private static final int f_174793_ = 10;
   private static final String f_174794_ = "vanilla";
   private static final String f_174795_ = "server";
   private static final String f_174796_ = "programer_art";
   private static final String f_174797_ = "Programmer Art";
   private static final Component f_174798_ = new TranslatableComponent("multiplayer.applyingPack");
   private final VanillaPackResources f_118545_;
   private final File f_118546_;
   private final ReentrantLock f_118547_ = new ReentrantLock();
   private final AssetIndex f_118548_;
   @Nullable
   private CompletableFuture<?> f_118549_;
   @Nullable
   private Pack f_118550_;

   public ClientPackSource(File p_118553_, AssetIndex p_118554_) {
      this.f_118546_ = p_118553_;
      this.f_118548_ = p_118554_;
      this.f_118545_ = new DefaultClientPackResources(f_174791_, p_118554_);
   }

   public void m_7686_(Consumer<Pack> p_118584_, Pack.PackConstructor p_118585_) {
      Pack pack = Pack.m_10430_("vanilla", true, () -> {
         return this.f_118545_;
      }, p_118585_, Pack.Position.BOTTOM, PackSource.f_10528_);
      if (pack != null) {
         p_118584_.accept(pack);
      }

      if (this.f_118550_ != null) {
         p_118584_.accept(this.f_118550_);
      }

      Pack pack1 = this.m_118556_(p_118585_);
      if (pack1 != null) {
         p_118584_.accept(pack1);
      }

   }

   public VanillaPackResources m_118555_() {
      return this.f_118545_;
   }

   private static Map<String, String> m_118589_() {
      Map<String, String> map = Maps.newHashMap();
      map.put("X-Minecraft-Username", Minecraft.m_91087_().m_91094_().m_92546_());
      map.put("X-Minecraft-UUID", Minecraft.m_91087_().m_91094_().m_92545_());
      map.put("X-Minecraft-Version", SharedConstants.m_136187_().getName());
      map.put("X-Minecraft-Version-ID", SharedConstants.m_136187_().getId());
      map.put("X-Minecraft-Pack-Format", String.valueOf(PackType.CLIENT_RESOURCES.m_143756_(SharedConstants.m_136187_())));
      map.put("User-Agent", "Minecraft Java/" + SharedConstants.m_136187_().getName());
      return map;
   }

   public CompletableFuture<?> m_174813_(String p_174814_, String p_174815_, boolean p_174816_) {
      String s = DigestUtils.sha1Hex(p_174814_);
      String s1 = f_118544_.matcher(p_174815_).matches() ? p_174815_ : "";
      this.f_118547_.lock();

      CompletableFuture completablefuture1;
      try {
         this.m_118586_();
         this.m_118592_();
         File file1 = new File(this.f_118546_, s);
         CompletableFuture<?> completablefuture;
         if (file1.exists()) {
            completablefuture = CompletableFuture.completedFuture("");
         } else {
            ProgressScreen progressscreen = new ProgressScreen(p_174816_);
            Map<String, String> map = m_118589_();
            Minecraft minecraft = Minecraft.m_91087_();
            minecraft.m_18709_(() -> {
               minecraft.m_91152_(progressscreen);
            });
            completablefuture = HttpUtil.m_13947_(file1, p_174814_, map, 104857600, progressscreen, minecraft.m_91096_());
         }

         this.f_118549_ = completablefuture.thenCompose((p_174812_) -> {
            if (!this.m_118573_(s1, file1)) {
               return Util.m_137498_(new RuntimeException("Hash check failure for file " + file1 + ", see log"));
            } else {
               Minecraft minecraft1 = Minecraft.m_91087_();
               minecraft1.execute(() -> {
                  if (!p_174816_) {
                     minecraft1.m_91152_(new GenericDirtMessageScreen(f_174798_));
                  }

               });
               return this.m_118566_(file1, PackSource.f_10530_);
            }
         }).whenComplete((p_174806_, p_174807_) -> {
            if (p_174807_ != null) {
               f_118543_.warn("Pack application failed: {}, deleting file {}", p_174807_.getMessage(), file1);
               m_118564_(file1);
               Minecraft minecraft1 = Minecraft.m_91087_();
               minecraft1.execute(() -> {
                  minecraft1.m_91152_(new ConfirmScreen((p_174803_) -> {
                     if (p_174803_) {
                        minecraft1.m_91152_((Screen)null);
                     } else {
                        ClientPacketListener clientpacketlistener = minecraft1.m_91403_();
                        if (clientpacketlistener != null) {
                           clientpacketlistener.m_6198_().m_129507_(new TranslatableComponent("connect.aborted"));
                        }
                     }

                  }, new TranslatableComponent("multiplayer.texturePrompt.failure.line1"), new TranslatableComponent("multiplayer.texturePrompt.failure.line2"), CommonComponents.f_130659_, new TranslatableComponent("menu.disconnect")));
               });
            }

         });
         completablefuture1 = this.f_118549_;
      } finally {
         this.f_118547_.unlock();
      }

      return completablefuture1;
   }

   private static void m_118564_(File p_118565_) {
      try {
         Files.delete(p_118565_.toPath());
      } catch (IOException ioexception) {
         f_118543_.warn("Failed to delete file {}: {}", p_118565_, ioexception.getMessage());
      }

   }

   public void m_118586_() {
      this.f_118547_.lock();

      try {
         if (this.f_118549_ != null) {
            this.f_118549_.cancel(true);
         }

         this.f_118549_ = null;
         if (this.f_118550_ != null) {
            this.f_118550_ = null;
            Minecraft.m_91087_().m_91088_();
         }
      } finally {
         this.f_118547_.unlock();
      }

   }

   private boolean m_118573_(String p_118574_, File p_118575_) {
      try {
         FileInputStream fileinputstream = new FileInputStream(p_118575_);

         String s;
         try {
            s = DigestUtils.sha1Hex((InputStream)fileinputstream);
         } catch (Throwable throwable1) {
            try {
               fileinputstream.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }

            throw throwable1;
         }

         fileinputstream.close();
         if (p_118574_.isEmpty()) {
            f_118543_.info("Found file {} without verification hash", (Object)p_118575_);
            return true;
         }

         if (s.toLowerCase(Locale.ROOT).equals(p_118574_.toLowerCase(Locale.ROOT))) {
            f_118543_.info("Found file {} matching requested hash {}", p_118575_, p_118574_);
            return true;
         }

         f_118543_.warn("File {} had wrong hash (expected {}, found {}).", p_118575_, p_118574_, s);
      } catch (IOException ioexception) {
         f_118543_.warn("File {} couldn't be hashed.", p_118575_, ioexception);
      }

      return false;
   }

   private void m_118592_() {
      try {
         List<File> list = Lists.newArrayList(FileUtils.listFiles(this.f_118546_, TrueFileFilter.TRUE, (IOFileFilter)null));
         list.sort(LastModifiedFileComparator.LASTMODIFIED_REVERSE);
         int i = 0;

         for(File file1 : list) {
            if (i++ >= 10) {
               f_118543_.info("Deleting old server resource pack {}", (Object)file1.getName());
               FileUtils.deleteQuietly(file1);
            }
         }
      } catch (IllegalArgumentException illegalargumentexception) {
         f_118543_.error("Error while deleting old server resource pack : {}", (Object)illegalargumentexception.getMessage());
      }

   }

   public CompletableFuture<Void> m_118566_(File p_118567_, PackSource p_118568_) {
      PackMetadataSection packmetadatasection;
      try {
         FilePackResources filepackresources = new FilePackResources(p_118567_);

         try {
            packmetadatasection = filepackresources.m_5550_(PackMetadataSection.f_10366_);
         } catch (Throwable throwable1) {
            try {
               filepackresources.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }

            throw throwable1;
         }

         filepackresources.close();
      } catch (IOException ioexception) {
         return Util.m_137498_(new IOException(String.format("Invalid resourcepack at %s", p_118567_), ioexception));
      }

      f_118543_.info("Applying server pack {}", (Object)p_118567_);
      this.f_118550_ = new Pack("server", true, () -> {
         return new FilePackResources(p_118567_);
      }, new TranslatableComponent("resourcePack.server.name"), packmetadatasection.m_10373_(), PackCompatibility.m_143885_(packmetadatasection, PackType.CLIENT_RESOURCES), Pack.Position.TOP, true, p_118568_);
      return Minecraft.m_91087_().m_91088_();
   }

   @Nullable
   private Pack m_118556_(Pack.PackConstructor p_118557_) {
      Pack pack = null;
      File file1 = this.f_118548_.m_7879_(new ResourceLocation("resourcepacks/programmer_art.zip"));
      if (file1 != null && file1.isFile()) {
         pack = m_118558_(p_118557_, () -> {
            return m_118590_(file1);
         });
      }

      if (pack == null && SharedConstants.f_136183_) {
         File file2 = this.f_118548_.m_7974_("../resourcepacks/programmer_art");
         if (file2 != null && file2.isDirectory()) {
            pack = m_118558_(p_118557_, () -> {
               return m_118587_(file2);
            });
         }
      }

      return pack;
   }

   @Nullable
   private static Pack m_118558_(Pack.PackConstructor p_118559_, Supplier<PackResources> p_118560_) {
      return Pack.m_10430_("programer_art", false, p_118560_, p_118559_, Pack.Position.TOP, PackSource.f_10528_);
   }

   private static FolderPackResources m_118587_(File p_118588_) {
      return new FolderPackResources(p_118588_) {
         public String m_8017_() {
            return "Programmer Art";
         }
      };
   }

   private static PackResources m_118590_(File p_118591_) {
      return new FilePackResources(p_118591_) {
         public String m_8017_() {
            return "Programmer Art";
         }
      };
   }
}