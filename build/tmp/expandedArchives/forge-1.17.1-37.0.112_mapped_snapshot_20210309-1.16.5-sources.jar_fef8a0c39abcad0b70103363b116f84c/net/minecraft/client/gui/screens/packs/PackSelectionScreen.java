package net.minecraft.client.gui.screens.packs;

import com.google.common.collect.Maps;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class PackSelectionScreen extends Screen {
   static final Logger f_99969_ = LogManager.getLogger();
   private static final int f_169993_ = 200;
   private static final Component f_99970_ = (new TranslatableComponent("pack.dropInfo")).m_130940_(ChatFormatting.GRAY);
   static final Component f_99971_ = new TranslatableComponent("pack.folderInfo");
   private static final int f_169994_ = 20;
   private static final ResourceLocation f_99972_ = new ResourceLocation("textures/misc/unknown_pack.png");
   private final PackSelectionModel f_99973_;
   private final Screen f_99974_;
   @Nullable
   private PackSelectionScreen.Watcher f_99975_;
   private long f_99976_;
   private TransferableSelectionList f_99977_;
   private TransferableSelectionList f_99978_;
   private final File f_99979_;
   private Button f_99980_;
   private final Map<String, ResourceLocation> f_99981_ = Maps.newHashMap();

   public PackSelectionScreen(Screen p_99984_, PackRepository p_99985_, Consumer<PackRepository> p_99986_, File p_99987_, Component p_99988_) {
      super(p_99988_);
      this.f_99974_ = p_99984_;
      this.f_99973_ = new PackSelectionModel(this::m_100040_, this::m_99989_, p_99985_, p_99986_);
      this.f_99979_ = p_99987_;
      this.f_99975_ = PackSelectionScreen.Watcher.m_100047_(p_99987_);
   }

   public void m_7379_() {
      this.f_99973_.m_99923_();
      this.f_96541_.m_91152_(this.f_99974_);
      this.m_100039_();
   }

   private void m_100039_() {
      if (this.f_99975_ != null) {
         try {
            this.f_99975_.close();
            this.f_99975_ = null;
         } catch (Exception exception) {
         }
      }

   }

   protected void m_7856_() {
      this.f_99980_ = this.m_142416_(new Button(this.f_96543_ / 2 + 4, this.f_96544_ - 48, 150, 20, CommonComponents.f_130655_, (p_100036_) -> {
         this.m_7379_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 154, this.f_96544_ - 48, 150, 20, new TranslatableComponent("pack.openFolder"), (p_100004_) -> {
         Util.m_137581_().m_137644_(this.f_99979_);
      }, new Button.OnTooltip() {
         public void m_93752_(Button p_170019_, PoseStack p_170020_, int p_170021_, int p_170022_) {
            PackSelectionScreen.this.m_96602_(p_170020_, PackSelectionScreen.f_99971_, p_170021_, p_170022_);
         }

         public void m_142753_(Consumer<Component> p_170017_) {
            p_170017_.accept(PackSelectionScreen.f_99971_);
         }
      }));
      this.f_99977_ = new TransferableSelectionList(this.f_96541_, 200, this.f_96544_, new TranslatableComponent("pack.available.title"));
      this.f_99977_.m_93507_(this.f_96543_ / 2 - 4 - 200);
      this.m_7787_(this.f_99977_);
      this.f_99978_ = new TransferableSelectionList(this.f_96541_, 200, this.f_96544_, new TranslatableComponent("pack.selected.title"));
      this.f_99978_.m_93507_(this.f_96543_ / 2 + 4);
      this.m_7787_(this.f_99978_);
      this.m_100041_();
   }

   public void m_96624_() {
      if (this.f_99975_ != null) {
         try {
            if (this.f_99975_.m_100046_()) {
               this.f_99976_ = 20L;
            }
         } catch (IOException ioexception) {
            f_99969_.warn("Failed to poll for directory {} changes, stopping", (Object)this.f_99979_);
            this.m_100039_();
         }
      }

      if (this.f_99976_ > 0L && --this.f_99976_ == 0L) {
         this.m_100041_();
      }

   }

   private void m_100040_() {
      this.m_100013_(this.f_99978_, this.f_99973_.m_99918_());
      this.m_100013_(this.f_99977_, this.f_99973_.m_99913_());
      this.f_99980_.f_93623_ = !this.f_99978_.m_6702_().isEmpty();
   }

   private void m_100013_(TransferableSelectionList p_100014_, Stream<PackSelectionModel.Entry> p_100015_) {
      p_100014_.m_6702_().clear();
      p_100015_.filter(PackSelectionModel.Entry::notHidden).forEach((p_170000_) -> {
         p_100014_.m_6702_().add(new TransferableSelectionList.PackEntry(this.f_96541_, p_100014_, this, p_170000_));
      });
   }

   private void m_100041_() {
      this.f_99973_.m_99926_();
      this.m_100040_();
      this.f_99976_ = 0L;
      this.f_99981_.clear();
   }

   public void m_6305_(PoseStack p_99995_, int p_99996_, int p_99997_, float p_99998_) {
      this.m_96626_(0);
      this.f_99977_.m_6305_(p_99995_, p_99996_, p_99997_, p_99998_);
      this.f_99978_.m_6305_(p_99995_, p_99996_, p_99997_, p_99998_);
      m_93215_(p_99995_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 8, 16777215);
      m_93215_(p_99995_, this.f_96547_, f_99970_, this.f_96543_ / 2, 20, 16777215);
      super.m_6305_(p_99995_, p_99996_, p_99997_, p_99998_);
   }

   protected static void m_99999_(Minecraft p_100000_, List<Path> p_100001_, Path p_100002_) {
      MutableBoolean mutableboolean = new MutableBoolean();
      p_100001_.forEach((p_170009_) -> {
         try {
            Stream<Path> stream = Files.walk(p_170009_);

            try {
               stream.forEach((p_170005_) -> {
                  try {
                     Util.m_137563_(p_170009_.getParent(), p_100002_, p_170005_);
                  } catch (IOException ioexception1) {
                     f_99969_.warn("Failed to copy datapack file  from {} to {}", p_170005_, p_100002_, ioexception1);
                     mutableboolean.setTrue();
                  }

               });
            } catch (Throwable throwable1) {
               if (stream != null) {
                  try {
                     stream.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (stream != null) {
               stream.close();
            }
         } catch (IOException ioexception) {
            f_99969_.warn("Failed to copy datapack file from {} to {}", p_170009_, p_100002_);
            mutableboolean.setTrue();
         }

      });
      if (mutableboolean.isTrue()) {
         SystemToast.m_94875_(p_100000_, p_100002_.toString());
      }

   }

   public void m_7400_(List<Path> p_100029_) {
      String s = p_100029_.stream().map(Path::getFileName).map(Path::toString).collect(Collectors.joining(", "));
      this.f_96541_.m_91152_(new ConfirmScreen((p_170012_) -> {
         if (p_170012_) {
            m_99999_(this.f_96541_, p_100029_, this.f_99979_.toPath());
            this.m_100041_();
         }

         this.f_96541_.m_91152_(this);
      }, new TranslatableComponent("pack.dropConfirm"), new TextComponent(s)));
   }

   private ResourceLocation m_100016_(TextureManager p_100017_, Pack p_100018_) {
      try {
         PackResources packresources = p_100018_.m_10445_();

         ResourceLocation resourcelocation;
         label84: {
            ResourceLocation resourcelocation2;
            try {
               InputStream inputstream = packresources.m_5542_("pack.png");

               label86: {
                  try {
                     if (inputstream != null) {
                        String s = p_100018_.m_10446_();
                        ResourceLocation resourcelocation1 = new ResourceLocation("minecraft", "pack/" + Util.m_137483_(s, ResourceLocation::m_135828_) + "/" + Hashing.sha1().hashUnencodedChars(s) + "/icon");
                        NativeImage nativeimage = NativeImage.m_85058_(inputstream);
                        p_100017_.m_118495_(resourcelocation1, new DynamicTexture(nativeimage));
                        resourcelocation2 = resourcelocation1;
                        break label86;
                     }

                     resourcelocation = f_99972_;
                  } catch (Throwable throwable2) {
                     if (inputstream != null) {
                        try {
                           inputstream.close();
                        } catch (Throwable throwable1) {
                           throwable2.addSuppressed(throwable1);
                        }
                     }

                     throw throwable2;
                  }

                  if (inputstream != null) {
                     inputstream.close();
                  }
                  break label84;
               }

               if (inputstream != null) {
                  inputstream.close();
               }
            } catch (Throwable throwable3) {
               if (packresources != null) {
                  try {
                     packresources.close();
                  } catch (Throwable throwable) {
                     throwable3.addSuppressed(throwable);
                  }
               }

               throw throwable3;
            }

            if (packresources != null) {
               packresources.close();
            }

            return resourcelocation2;
         }

         if (packresources != null) {
            packresources.close();
         }

         return resourcelocation;
      } catch (FileNotFoundException filenotfoundexception) {
      } catch (Exception exception) {
         f_99969_.warn("Failed to load icon from pack {}", p_100018_.m_10446_(), exception);
      }

      return f_99972_;
   }

   private ResourceLocation m_99989_(Pack p_99990_) {
      return this.f_99981_.computeIfAbsent(p_99990_.m_10446_(), (p_169997_) -> {
         return this.m_100016_(this.f_96541_.m_91097_(), p_99990_);
      });
   }

   @OnlyIn(Dist.CLIENT)
   static class Watcher implements AutoCloseable {
      private final WatchService f_100042_;
      private final Path f_100043_;

      public Watcher(File p_100045_) throws IOException {
         this.f_100043_ = p_100045_.toPath();
         this.f_100042_ = this.f_100043_.getFileSystem().newWatchService();

         try {
            this.m_100049_(this.f_100043_);
            DirectoryStream<Path> directorystream = Files.newDirectoryStream(this.f_100043_);

            try {
               for(Path path : directorystream) {
                  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                     this.m_100049_(path);
                  }
               }
            } catch (Throwable throwable1) {
               if (directorystream != null) {
                  try {
                     directorystream.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (directorystream != null) {
               directorystream.close();
            }

         } catch (Exception exception) {
            this.f_100042_.close();
            throw exception;
         }
      }

      @Nullable
      public static PackSelectionScreen.Watcher m_100047_(File p_100048_) {
         try {
            return new PackSelectionScreen.Watcher(p_100048_);
         } catch (IOException ioexception) {
            PackSelectionScreen.f_99969_.warn("Failed to initialize pack directory {} monitoring", p_100048_, ioexception);
            return null;
         }
      }

      private void m_100049_(Path p_100050_) throws IOException {
         p_100050_.register(this.f_100042_, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
      }

      public boolean m_100046_() throws IOException {
         boolean flag = false;

         WatchKey watchkey;
         while((watchkey = this.f_100042_.poll()) != null) {
            for(WatchEvent<?> watchevent : watchkey.pollEvents()) {
               flag = true;
               if (watchkey.watchable() == this.f_100043_ && watchevent.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                  Path path = this.f_100043_.resolve((Path)watchevent.context());
                  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
                     this.m_100049_(path);
                  }
               }
            }

            watchkey.reset();
         }

         return flag;
      }

      public void close() throws IOException {
         this.f_100042_.close();
      }
   }
}
