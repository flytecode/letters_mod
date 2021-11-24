package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableList;
import com.google.common.hash.Hashing;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ErrorScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageException;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class WorldSelectionList extends ObjectSelectionList<WorldSelectionList.WorldListEntry> {
   static final Logger f_101645_ = LogManager.getLogger();
   static final DateFormat f_101646_ = new SimpleDateFormat();
   static final ResourceLocation f_101647_ = new ResourceLocation("textures/misc/unknown_server.png");
   static final ResourceLocation f_101648_ = new ResourceLocation("textures/gui/world_selection.png");
   static final Component f_101649_ = (new TranslatableComponent("selectWorld.tooltip.fromNewerVersion1")).m_130940_(ChatFormatting.RED);
   static final Component f_101650_ = (new TranslatableComponent("selectWorld.tooltip.fromNewerVersion2")).m_130940_(ChatFormatting.RED);
   static final Component f_101651_ = (new TranslatableComponent("selectWorld.tooltip.snapshot1")).m_130940_(ChatFormatting.GOLD);
   static final Component f_101652_ = (new TranslatableComponent("selectWorld.tooltip.snapshot2")).m_130940_(ChatFormatting.GOLD);
   static final Component f_101653_ = (new TranslatableComponent("selectWorld.locked")).m_130940_(ChatFormatting.RED);
   static final Component f_170311_ = (new TranslatableComponent("selectWorld.pre_worldheight")).m_130940_(ChatFormatting.RED);
   private final SelectWorldScreen f_101654_;
   @Nullable
   private List<LevelSummary> f_101655_;

   public WorldSelectionList(SelectWorldScreen p_101658_, Minecraft p_101659_, int p_101660_, int p_101661_, int p_101662_, int p_101663_, int p_101664_, Supplier<String> p_101665_, @Nullable WorldSelectionList p_101666_) {
      super(p_101659_, p_101660_, p_101661_, p_101662_, p_101663_, p_101664_);
      this.f_101654_ = p_101658_;
      if (p_101666_ != null) {
         this.f_101655_ = p_101666_.f_101655_;
      }

      this.m_101676_(p_101665_, false);
   }

   public void m_101676_(Supplier<String> p_101677_, boolean p_101678_) {
      this.m_93516_();
      LevelStorageSource levelstoragesource = this.f_93386_.m_91392_();
      if (this.f_101655_ == null || p_101678_) {
         try {
            this.f_101655_ = levelstoragesource.m_78244_();
         } catch (LevelStorageException levelstorageexception) {
            f_101645_.error("Couldn't load level list", (Throwable)levelstorageexception);
            this.f_93386_.m_91152_(new ErrorScreen(new TranslatableComponent("selectWorld.unable_to_load"), new TextComponent(levelstorageexception.getMessage())));
            return;
         }

         Collections.sort(this.f_101655_);
      }

      if (this.f_101655_.isEmpty()) {
         this.f_93386_.m_91152_(CreateWorldScreen.m_100898_((Screen)null));
      } else {
         String s = p_101677_.get().toLowerCase(Locale.ROOT);

         for(LevelSummary levelsummary : this.f_101655_) {
            if (levelsummary.m_78361_().toLowerCase(Locale.ROOT).contains(s) || levelsummary.m_78358_().toLowerCase(Locale.ROOT).contains(s)) {
               this.m_7085_(new WorldSelectionList.WorldListEntry(this, levelsummary));
            }
         }

      }
   }

   protected int m_5756_() {
      return super.m_5756_() + 20;
   }

   public int m_5759_() {
      return super.m_5759_() + 50;
   }

   protected boolean m_5694_() {
      return this.f_101654_.m_7222_() == this;
   }

   public void m_6987_(@Nullable WorldSelectionList.WorldListEntry p_101675_) {
      super.m_6987_(p_101675_);
      this.f_101654_.m_101369_(p_101675_ != null && !p_101675_.f_101695_.m_164916_());
   }

   protected void m_6778_(AbstractSelectionList.SelectionDirection p_101673_) {
      this.m_93464_(p_101673_, (p_101681_) -> {
         return !p_101681_.f_101695_.m_164916_();
      });
   }

   public Optional<WorldSelectionList.WorldListEntry> m_101684_() {
      return Optional.ofNullable(this.m_93511_());
   }

   public SelectWorldScreen m_101685_() {
      return this.f_101654_;
   }

   @OnlyIn(Dist.CLIENT)
   public final class WorldListEntry extends ObjectSelectionList.Entry<WorldSelectionList.WorldListEntry> implements AutoCloseable {
      private static final int f_170312_ = 32;
      private static final int f_170313_ = 32;
      private static final int f_170314_ = 0;
      private static final int f_170315_ = 32;
      private static final int f_170316_ = 64;
      private static final int f_170317_ = 96;
      private static final int f_170318_ = 0;
      private static final int f_170319_ = 32;
      private final Minecraft f_101693_;
      private final SelectWorldScreen f_101694_;
      final LevelSummary f_101695_;
      private final ResourceLocation f_101696_;
      private File f_101697_;
      @Nullable
      private final DynamicTexture f_101698_;
      private long f_101699_;

      public WorldListEntry(WorldSelectionList p_101702_, LevelSummary p_101703_) {
         this.f_101694_ = p_101702_.m_101685_();
         this.f_101695_ = p_101703_;
         this.f_101693_ = Minecraft.m_91087_();
         String s = p_101703_.m_78358_();
         this.f_101696_ = new ResourceLocation("minecraft", "worlds/" + Util.m_137483_(s, ResourceLocation::m_135828_) + "/" + Hashing.sha1().hashUnencodedChars(s) + "/icon");
         this.f_101697_ = p_101703_.m_78362_();
         if (!this.f_101697_.isFile()) {
            this.f_101697_ = null;
         }

         this.f_101698_ = this.m_101746_();
      }

      public Component m_142172_() {
         TranslatableComponent translatablecomponent = new TranslatableComponent("narrator.select.world", this.f_101695_.m_78361_(), new Date(this.f_101695_.m_78366_()), this.f_101695_.m_78368_() ? new TranslatableComponent("gameMode.hardcore") : new TranslatableComponent("gameMode." + this.f_101695_.m_78367_().m_46405_()), this.f_101695_.m_78369_() ? new TranslatableComponent("selectWorld.cheats") : TextComponent.f_131282_, this.f_101695_.m_78370_());
         Component component;
         if (this.f_101695_.m_78375_()) {
            component = CommonComponents.m_178398_(translatablecomponent, WorldSelectionList.f_101653_);
         } else if (this.f_101695_.m_164915_()) {
            component = CommonComponents.m_178398_(translatablecomponent, WorldSelectionList.f_170311_);
         } else {
            component = translatablecomponent;
         }

         return new TranslatableComponent("narrator.select", component);
      }

      public void m_6311_(PoseStack p_101721_, int p_101722_, int p_101723_, int p_101724_, int p_101725_, int p_101726_, int p_101727_, int p_101728_, boolean p_101729_, float p_101730_) {
         String s = this.f_101695_.m_78361_();
         String s1 = this.f_101695_.m_78358_() + " (" + WorldSelectionList.f_101646_.format(new Date(this.f_101695_.m_78366_())) + ")";
         if (StringUtils.isEmpty(s)) {
            s = I18n.m_118938_("selectWorld.world") + " " + (p_101722_ + 1);
         }

         Component component = this.f_101695_.m_78376_();
         this.f_101693_.f_91062_.m_92883_(p_101721_, s, (float)(p_101724_ + 32 + 3), (float)(p_101723_ + 1), 16777215);
         this.f_101693_.f_91062_.m_92883_(p_101721_, s1, (float)(p_101724_ + 32 + 3), (float)(p_101723_ + 9 + 3), 8421504);
         this.f_101693_.f_91062_.m_92889_(p_101721_, component, (float)(p_101724_ + 32 + 3), (float)(p_101723_ + 9 + 9 + 3), 8421504);
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_157456_(0, this.f_101698_ != null ? this.f_101696_ : WorldSelectionList.f_101647_);
         RenderSystem.m_69478_();
         GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 0.0F, 0.0F, 32, 32, 32, 32);
         RenderSystem.m_69461_();
         if (this.f_101693_.f_91066_.f_92051_ || p_101729_) {
            RenderSystem.m_157456_(0, WorldSelectionList.f_101648_);
            GuiComponent.m_93172_(p_101721_, p_101724_, p_101723_, p_101724_ + 32, p_101723_ + 32, -1601138544);
            RenderSystem.m_157427_(GameRenderer::m_172817_);
            RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
            int i = p_101727_ - p_101724_;
            boolean flag = i < 32;
            int j = flag ? 32 : 0;
            if (this.f_101695_.m_78375_()) {
               GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 96.0F, (float)j, 32, 32, 256, 256);
               if (flag) {
                  this.f_101694_.m_101363_(this.f_101693_.f_91062_.m_92923_(WorldSelectionList.f_101653_, 175));
               }
            } else if (this.f_101695_.m_164915_()) {
               GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 96.0F, 32.0F, 32, 32, 256, 256);
               if (flag) {
                  this.f_101694_.m_101363_(this.f_101693_.f_91062_.m_92923_(WorldSelectionList.f_170311_, 175));
               }
            } else if (this.f_101695_.m_78372_()) {
               GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 32.0F, (float)j, 32, 32, 256, 256);
               if (this.f_101695_.m_78373_()) {
                  GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 96.0F, (float)j, 32, 32, 256, 256);
                  if (flag) {
                     this.f_101694_.m_101363_(ImmutableList.of(WorldSelectionList.f_101649_.m_7532_(), WorldSelectionList.f_101650_.m_7532_()));
                  }
               } else if (!SharedConstants.m_136187_().isStable()) {
                  GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 64.0F, (float)j, 32, 32, 256, 256);
                  if (flag) {
                     this.f_101694_.m_101363_(ImmutableList.of(WorldSelectionList.f_101651_.m_7532_(), WorldSelectionList.f_101652_.m_7532_()));
                  }
               }
            } else {
               GuiComponent.m_93133_(p_101721_, p_101724_, p_101723_, 0.0F, (float)j, 32, 32, 256, 256);
            }
         }

      }

      public boolean m_6375_(double p_101706_, double p_101707_, int p_101708_) {
         if (this.f_101695_.m_164916_()) {
            return true;
         } else {
            WorldSelectionList.this.m_6987_(this);
            this.f_101694_.m_101369_(WorldSelectionList.this.m_101684_().isPresent());
            if (p_101706_ - (double)WorldSelectionList.this.m_5747_() <= 32.0D) {
               this.m_101704_();
               return true;
            } else if (Util.m_137550_() - this.f_101699_ < 250L) {
               this.m_101704_();
               return true;
            } else {
               this.f_101699_ = Util.m_137550_();
               return false;
            }
         }
      }

      public void m_101704_() {
         if (!this.f_101695_.m_164916_()) {
            LevelSummary.BackupStatus levelsummary$backupstatus = this.f_101695_.m_164914_();
            if (levelsummary$backupstatus.m_164931_()) {
               String s = "selectWorld.backupQuestion." + levelsummary$backupstatus.m_164933_();
               String s1 = "selectWorld.backupWarning." + levelsummary$backupstatus.m_164933_();
               MutableComponent mutablecomponent = new TranslatableComponent(s);
               if (levelsummary$backupstatus.m_164932_()) {
                  mutablecomponent.m_130944_(ChatFormatting.BOLD, ChatFormatting.RED);
               }

               Component component = new TranslatableComponent(s1, this.f_101695_.m_78370_(), SharedConstants.m_136187_().getName());
               this.f_101693_.m_91152_(new BackupConfirmScreen(this.f_101694_, (p_101736_, p_101737_) -> {
                  if (p_101736_) {
                     String s2 = this.f_101695_.m_78358_();

                     try {
                        LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = this.f_101693_.m_91392_().m_78260_(s2);

                        try {
                           EditWorldScreen.m_101258_(levelstoragesource$levelstorageaccess);
                        } catch (Throwable throwable1) {
                           if (levelstoragesource$levelstorageaccess != null) {
                              try {
                                 levelstoragesource$levelstorageaccess.close();
                              } catch (Throwable throwable) {
                                 throwable1.addSuppressed(throwable);
                              }
                           }

                           throw throwable1;
                        }

                        if (levelstoragesource$levelstorageaccess != null) {
                           levelstoragesource$levelstorageaccess.close();
                        }
                     } catch (IOException ioexception) {
                        SystemToast.m_94852_(this.f_101693_, s2);
                        WorldSelectionList.f_101645_.error("Failed to backup level {}", s2, ioexception);
                     }
                  }

                  this.m_101744_();
               }, mutablecomponent, component, false));
            } else if (this.f_101695_.m_78373_()) {
               this.f_101693_.m_91152_(new ConfirmScreen((p_101741_) -> {
                  if (p_101741_) {
                     try {
                        this.m_101744_();
                     } catch (Exception exception) {
                        WorldSelectionList.f_101645_.error("Failure to open 'future world'", (Throwable)exception);
                        this.f_101693_.m_91152_(new AlertScreen(() -> {
                           this.f_101693_.m_91152_(this.f_101694_);
                        }, new TranslatableComponent("selectWorld.futureworld.error.title"), new TranslatableComponent("selectWorld.futureworld.error.text")));
                     }
                  } else {
                     this.f_101693_.m_91152_(this.f_101694_);
                  }

               }, new TranslatableComponent("selectWorld.versionQuestion"), new TranslatableComponent("selectWorld.versionWarning", this.f_101695_.m_78370_()), new TranslatableComponent("selectWorld.versionJoinButton"), CommonComponents.f_130656_));
            } else {
               this.m_101744_();
            }

         }
      }

      public void m_101738_() {
         this.f_101693_.m_91152_(new ConfirmScreen((p_170322_) -> {
            if (p_170322_) {
               this.f_101693_.m_91152_(new ProgressScreen(true));
               this.m_170323_();
            }

            this.f_101693_.m_91152_(this.f_101694_);
         }, new TranslatableComponent("selectWorld.deleteQuestion"), new TranslatableComponent("selectWorld.deleteWarning", this.f_101695_.m_78361_()), new TranslatableComponent("selectWorld.deleteButton"), CommonComponents.f_130656_));
      }

      public void m_170323_() {
         LevelStorageSource levelstoragesource = this.f_101693_.m_91392_();
         String s = this.f_101695_.m_78358_();

         try {
            LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = levelstoragesource.m_78260_(s);

            try {
               levelstoragesource$levelstorageaccess.m_78311_();
            } catch (Throwable throwable1) {
               if (levelstoragesource$levelstorageaccess != null) {
                  try {
                     levelstoragesource$levelstorageaccess.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (levelstoragesource$levelstorageaccess != null) {
               levelstoragesource$levelstorageaccess.close();
            }
         } catch (IOException ioexception) {
            SystemToast.m_94866_(this.f_101693_, s);
            WorldSelectionList.f_101645_.error("Failed to delete world {}", s, ioexception);
         }

         WorldSelectionList.this.m_101676_(() -> {
            return this.f_101694_.f_101330_.m_94155_();
         }, true);
      }

      public void m_101739_() {
         String s = this.f_101695_.m_78358_();

         try {
            LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = this.f_101693_.m_91392_().m_78260_(s);
            this.f_101693_.m_91152_(new EditWorldScreen((p_101719_) -> {
               try {
                  levelstoragesource$levelstorageaccess.close();
               } catch (IOException ioexception1) {
                  WorldSelectionList.f_101645_.error("Failed to unlock level {}", s, ioexception1);
               }

               if (p_101719_) {
                  WorldSelectionList.this.m_101676_(() -> {
                     return this.f_101694_.f_101330_.m_94155_();
                  }, true);
               }

               this.f_101693_.m_91152_(this.f_101694_);
            }, levelstoragesource$levelstorageaccess));
         } catch (IOException ioexception) {
            SystemToast.m_94852_(this.f_101693_, s);
            WorldSelectionList.f_101645_.error("Failed to access level {}", s, ioexception);
            WorldSelectionList.this.m_101676_(() -> {
               return this.f_101694_.f_101330_.m_94155_();
            }, true);
         }

      }

      public void m_101743_() {
         this.m_101745_();
         RegistryAccess.RegistryHolder registryaccess$registryholder = RegistryAccess.m_123086_();

         try {
            LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = this.f_101693_.m_91392_().m_78260_(this.f_101695_.m_78358_());

            try {
               Minecraft.ServerStem minecraft$serverstem = this.f_101693_.m_91190_(registryaccess$registryholder, Minecraft::m_91133_, Minecraft::m_91135_, false, levelstoragesource$levelstorageaccess);

               try {
                  LevelSettings levelsettings = minecraft$serverstem.m_91434_().m_5926_();
                  DataPackConfig datapackconfig = levelsettings.m_46934_();
                  WorldGenSettings worldgensettings = minecraft$serverstem.m_91434_().m_5961_();
                  Path path = CreateWorldScreen.m_100906_(levelstoragesource$levelstorageaccess.m_78283_(LevelResource.f_78180_), this.f_101693_);
                  if (worldgensettings.m_64670_()) {
                     this.f_101693_.m_91152_(new ConfirmScreen((p_101715_) -> {
                        this.f_101693_.m_91152_((Screen)(p_101715_ ? new CreateWorldScreen(this.f_101694_, levelsettings, worldgensettings, path, datapackconfig, registryaccess$registryholder) : this.f_101694_));
                     }, new TranslatableComponent("selectWorld.recreate.customized.title"), new TranslatableComponent("selectWorld.recreate.customized.text"), CommonComponents.f_130659_, CommonComponents.f_130656_));
                  } else {
                     this.f_101693_.m_91152_(new CreateWorldScreen(this.f_101694_, levelsettings, worldgensettings, path, datapackconfig, registryaccess$registryholder));
                  }
               } catch (Throwable throwable2) {
                  if (minecraft$serverstem != null) {
                     try {
                        minecraft$serverstem.close();
                     } catch (Throwable throwable1) {
                        throwable2.addSuppressed(throwable1);
                     }
                  }

                  throw throwable2;
               }

               if (minecraft$serverstem != null) {
                  minecraft$serverstem.close();
               }
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
         } catch (Exception exception) {
            WorldSelectionList.f_101645_.error("Unable to recreate world", (Throwable)exception);
            this.f_101693_.m_91152_(new AlertScreen(() -> {
               this.f_101693_.m_91152_(this.f_101694_);
            }, new TranslatableComponent("selectWorld.recreate.error.title"), new TranslatableComponent("selectWorld.recreate.error.text")));
         }

      }

      private void m_101744_() {
         this.f_101693_.m_91106_().m_120367_(SimpleSoundInstance.m_119752_(SoundEvents.f_12490_, 1.0F));
         if (this.f_101693_.m_91392_().m_78255_(this.f_101695_.m_78358_())) {
            this.m_101745_();
            this.f_101693_.m_91200_(this.f_101695_.m_78358_());
         }

      }

      private void m_101745_() {
         this.f_101693_.m_91346_(new GenericDirtMessageScreen(new TranslatableComponent("selectWorld.data_read")));
      }

      @Nullable
      private DynamicTexture m_101746_() {
         boolean flag = this.f_101697_ != null && this.f_101697_.isFile();
         if (flag) {
            try {
               InputStream inputstream = new FileInputStream(this.f_101697_);

               DynamicTexture dynamictexture1;
               try {
                  NativeImage nativeimage = NativeImage.m_85058_(inputstream);
                  Validate.validState(nativeimage.m_84982_() == 64, "Must be 64 pixels wide");
                  Validate.validState(nativeimage.m_85084_() == 64, "Must be 64 pixels high");
                  DynamicTexture dynamictexture = new DynamicTexture(nativeimage);
                  this.f_101693_.m_91097_().m_118495_(this.f_101696_, dynamictexture);
                  dynamictexture1 = dynamictexture;
               } catch (Throwable throwable1) {
                  try {
                     inputstream.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }

                  throw throwable1;
               }

               inputstream.close();
               return dynamictexture1;
            } catch (Throwable throwable2) {
               WorldSelectionList.f_101645_.error("Invalid icon for world {}", this.f_101695_.m_78358_(), throwable2);
               this.f_101697_ = null;
               return null;
            }
         } else {
            this.f_101693_.m_91097_().m_118513_(this.f_101696_);
            return null;
         }
      }

      public void close() {
         if (this.f_101698_ != null) {
            this.f_101698_.close();
         }

      }

      public String m_170324_() {
         return this.f_101695_.m_78361_();
      }
   }
}