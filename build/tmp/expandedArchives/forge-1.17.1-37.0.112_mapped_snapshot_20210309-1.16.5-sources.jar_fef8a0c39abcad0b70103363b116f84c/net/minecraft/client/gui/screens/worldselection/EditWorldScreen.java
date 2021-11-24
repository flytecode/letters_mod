package net.minecraft.client.gui.screens.worldselection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonWriter;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.DataResult.PartialResult;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.RegistryWriteOps;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class EditWorldScreen extends Screen {
   private static final Logger f_101243_ = LogManager.getLogger();
   private static final Gson f_101244_ = (new GsonBuilder()).setPrettyPrinting().serializeNulls().disableHtmlEscaping().create();
   private static final Component f_101245_ = new TranslatableComponent("selectWorld.enterName");
   private Button f_101246_;
   private final BooleanConsumer f_101247_;
   private EditBox f_101248_;
   private final LevelStorageSource.LevelStorageAccess f_101249_;

   public EditWorldScreen(BooleanConsumer p_101252_, LevelStorageSource.LevelStorageAccess p_101253_) {
      super(new TranslatableComponent("selectWorld.edit.title"));
      this.f_101247_ = p_101252_;
      this.f_101249_ = p_101253_;
   }

   public void m_96624_() {
      this.f_101248_.m_94120_();
   }

   protected void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      Button button = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 0 + 5, 200, 20, new TranslatableComponent("selectWorld.edit.resetIcon"), (p_101297_) -> {
         this.f_101249_.m_182514_().ifPresent((p_182594_) -> {
            FileUtils.deleteQuietly(p_182594_.toFile());
         });
         p_101297_.f_93623_ = false;
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 24 + 5, 200, 20, new TranslatableComponent("selectWorld.edit.openFolder"), (p_101294_) -> {
         Util.m_137581_().m_137644_(this.f_101249_.m_78283_(LevelResource.f_78182_).toFile());
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 48 + 5, 200, 20, new TranslatableComponent("selectWorld.edit.backup"), (p_101292_) -> {
         boolean flag = m_101258_(this.f_101249_);
         this.f_101247_.accept(!flag);
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 72 + 5, 200, 20, new TranslatableComponent("selectWorld.edit.backupFolder"), (p_101290_) -> {
         LevelStorageSource levelstoragesource = this.f_96541_.m_91392_();
         Path path = levelstoragesource.m_78262_();

         try {
            Files.createDirectories(Files.exists(path) ? path.toRealPath() : path);
         } catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
         }

         Util.m_137581_().m_137644_(path.toFile());
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 96 + 5, 200, 20, new TranslatableComponent("selectWorld.edit.optimize"), (p_101287_) -> {
         this.f_96541_.m_91152_(new BackupConfirmScreen(this, (p_170235_, p_170236_) -> {
            if (p_170235_) {
               m_101258_(this.f_101249_);
            }

            this.f_96541_.m_91152_(OptimizeWorldScreen.m_101315_(this.f_96541_, this.f_101247_, this.f_96541_.m_91295_(), this.f_101249_, p_170236_));
         }, new TranslatableComponent("optimizeWorld.confirm.title"), new TranslatableComponent("optimizeWorld.confirm.description"), true));
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 120 + 5, 200, 20, new TranslatableComponent("selectWorld.edit.export_worldgen_settings"), (p_101284_) -> {
         RegistryAccess.RegistryHolder registryaccess$registryholder = RegistryAccess.m_123086_();

         DataResult<String> dataresult;
         try {
            Minecraft.ServerStem minecraft$serverstem = this.f_96541_.m_91190_(registryaccess$registryholder, Minecraft::m_91133_, Minecraft::m_91135_, false, this.f_101249_);

            try {
               DynamicOps<JsonElement> dynamicops = RegistryWriteOps.m_135767_(JsonOps.INSTANCE, registryaccess$registryholder);
               DataResult<JsonElement> dataresult1 = WorldGenSettings.f_64600_.encodeStart(dynamicops, minecraft$serverstem.m_91434_().m_5961_());
               dataresult = dataresult1.flatMap((p_170231_) -> {
                  Path path = this.f_101249_.m_78283_(LevelResource.f_78182_).resolve("worldgen_settings_export.json");

                  try {
                     JsonWriter jsonwriter = f_101244_.newJsonWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8));

                     try {
                        f_101244_.toJson(p_170231_, jsonwriter);
                     } catch (Throwable throwable3) {
                        if (jsonwriter != null) {
                           try {
                              jsonwriter.close();
                           } catch (Throwable throwable2) {
                              throwable3.addSuppressed(throwable2);
                           }
                        }

                        throw throwable3;
                     }

                     if (jsonwriter != null) {
                        jsonwriter.close();
                     }
                  } catch (JsonIOException | IOException ioexception) {
                     return DataResult.error("Error writing file: " + ioexception.getMessage());
                  }

                  return DataResult.success(path.toString());
               });
            } catch (Throwable throwable1) {
               if (minecraft$serverstem != null) {
                  try {
                     minecraft$serverstem.close();
                  } catch (Throwable throwable) {
                     throwable1.addSuppressed(throwable);
                  }
               }

               throw throwable1;
            }

            if (minecraft$serverstem != null) {
               minecraft$serverstem.close();
            }
         } catch (Exception exception) {
            f_101243_.warn("Could not parse level data", (Throwable)exception);
            dataresult = DataResult.error("Could not parse level data: " + exception.getMessage());
         }

         Component component = new TextComponent(dataresult.get().map(Function.identity(), PartialResult::message));
         Component component1 = new TranslatableComponent(dataresult.result().isPresent() ? "selectWorld.edit.export_worldgen_settings.success" : "selectWorld.edit.export_worldgen_settings.failure");
         dataresult.error().ifPresent((p_170233_) -> {
            f_101243_.error("Error exporting world settings: {}", (Object)p_170233_);
         });
         this.f_96541_.m_91300_().m_94922_(SystemToast.m_94847_(this.f_96541_, SystemToast.SystemToastIds.WORLD_GEN_SETTINGS_TRANSFER, component1, component));
      }));
      this.f_101246_ = this.m_142416_(new Button(this.f_96543_ / 2 - 100, this.f_96544_ / 4 + 144 + 5, 98, 20, new TranslatableComponent("selectWorld.edit.save"), (p_101280_) -> {
         this.m_101295_();
      }));
      this.m_142416_(new Button(this.f_96543_ / 2 + 2, this.f_96544_ / 4 + 144 + 5, 98, 20, CommonComponents.f_130656_, (p_101273_) -> {
         this.f_101247_.accept(false);
      }));
      button.f_93623_ = this.f_101249_.m_182514_().filter((p_182587_) -> {
         return Files.isRegularFile(p_182587_);
      }).isPresent();
      LevelSummary levelsummary = this.f_101249_.m_78308_();
      String s = levelsummary == null ? "" : levelsummary.m_78361_();
      this.f_101248_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 100, 38, 200, 20, new TranslatableComponent("selectWorld.enterName"));
      this.f_101248_.m_94144_(s);
      this.f_101248_.m_94151_((p_101282_) -> {
         this.f_101246_.f_93623_ = !p_101282_.trim().isEmpty();
      });
      this.m_7787_(this.f_101248_);
      this.m_94718_(this.f_101248_);
   }

   public void m_6574_(Minecraft p_101269_, int p_101270_, int p_101271_) {
      String s = this.f_101248_.m_94155_();
      this.m_6575_(p_101269_, p_101270_, p_101271_);
      this.f_101248_.m_94144_(s);
   }

   public void m_7379_() {
      this.f_101247_.accept(false);
   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   private void m_101295_() {
      try {
         this.f_101249_.m_78297_(this.f_101248_.m_94155_().trim());
         this.f_101247_.accept(true);
      } catch (IOException ioexception) {
         f_101243_.error("Failed to access world '{}'", this.f_101249_.m_78277_(), ioexception);
         SystemToast.m_94852_(this.f_96541_, this.f_101249_.m_78277_());
         this.f_101247_.accept(true);
      }

   }

   public static void m_101260_(LevelStorageSource p_101261_, String p_101262_) {
      boolean flag = false;

      try {
         LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = p_101261_.m_78260_(p_101262_);

         try {
            flag = true;
            m_101258_(levelstoragesource$levelstorageaccess);
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
         if (!flag) {
            SystemToast.m_94852_(Minecraft.m_91087_(), p_101262_);
         }

         f_101243_.warn("Failed to create backup of level {}", p_101262_, ioexception);
      }

   }

   public static boolean m_101258_(LevelStorageSource.LevelStorageAccess p_101259_) {
      long i = 0L;
      IOException ioexception = null;

      try {
         i = p_101259_.m_78312_();
      } catch (IOException ioexception1) {
         ioexception = ioexception1;
      }

      if (ioexception != null) {
         Component component2 = new TranslatableComponent("selectWorld.edit.backupFailed");
         Component component3 = new TextComponent(ioexception.getMessage());
         Minecraft.m_91087_().m_91300_().m_94922_(new SystemToast(SystemToast.SystemToastIds.WORLD_BACKUP, component2, component3));
         return false;
      } else {
         Component component = new TranslatableComponent("selectWorld.edit.backupCreated", p_101259_.m_78277_());
         Component component1 = new TranslatableComponent("selectWorld.edit.backupSize", Mth.m_14165_((double)i / 1048576.0D));
         Minecraft.m_91087_().m_91300_().m_94922_(new SystemToast(SystemToast.SystemToastIds.WORLD_BACKUP, component, component1));
         return true;
      }
   }

   public void m_6305_(PoseStack p_101264_, int p_101265_, int p_101266_, float p_101267_) {
      this.m_7333_(p_101264_);
      m_93215_(p_101264_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 15, 16777215);
      m_93243_(p_101264_, this.f_96547_, f_101245_, this.f_96543_ / 2 - 100, 24, 10526880);
      this.f_101248_.m_6305_(p_101264_, p_101265_, p_101266_, p_101267_);
      super.m_6305_(p_101264_, p_101265_, p_101266_, p_101267_);
   }
}