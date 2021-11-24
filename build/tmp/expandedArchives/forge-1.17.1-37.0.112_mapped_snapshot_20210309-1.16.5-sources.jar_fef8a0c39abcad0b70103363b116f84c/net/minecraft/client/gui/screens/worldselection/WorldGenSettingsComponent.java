package net.minecraft.client.gui.screens.worldselection;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.MultiLineLabel;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.RegistryWriteOps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.PointerBuffer;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

@OnlyIn(Dist.CLIENT)
public class WorldGenSettingsComponent implements Widget {
   private static final Logger f_101381_ = LogManager.getLogger();
   private static final Component f_101382_ = new TranslatableComponent("generator.custom");
   private static final Component f_101383_ = new TranslatableComponent("generator.amplified.info");
   private static final Component f_101384_ = new TranslatableComponent("selectWorld.mapFeatures.info");
   private static final Component f_170243_ = new TranslatableComponent("selectWorld.import_worldgen_settings.select_file");
   private MultiLineLabel f_101385_ = MultiLineLabel.f_94331_;
   private Font f_101386_;
   private int f_101387_;
   private EditBox f_101388_;
   private CycleButton<Boolean> f_101389_;
   private CycleButton<Boolean> f_101380_;
   private CycleButton<WorldPreset> f_101390_;
   private Button f_170244_;
   private Button f_101391_;
   private Button f_101392_;
   private RegistryAccess.RegistryHolder f_101393_;
   private WorldGenSettings f_101394_;
   private Optional<WorldPreset> f_101395_;
   private OptionalLong f_101396_;

   public WorldGenSettingsComponent(RegistryAccess.RegistryHolder p_101399_, WorldGenSettings p_101400_, Optional<WorldPreset> p_101401_, OptionalLong p_101402_) {
      this.f_101393_ = p_101399_;
      this.f_101394_ = p_101400_;
      this.f_101395_ = p_101401_;
      this.f_101396_ = p_101402_;
   }

   public void m_101429_(CreateWorldScreen p_101430_, Minecraft p_101431_, Font p_101432_) {
      this.f_101386_ = p_101432_;
      this.f_101387_ = p_101430_.f_96543_;
      this.f_101388_ = new EditBox(this.f_101386_, this.f_101387_ / 2 - 100, 60, 200, 20, new TranslatableComponent("selectWorld.enterSeed"));
      this.f_101388_.m_94144_(m_101447_(this.f_101396_));
      this.f_101388_.m_94151_((p_101465_) -> {
         this.f_101396_ = this.m_101471_();
      });
      p_101430_.m_7787_(this.f_101388_);
      int i = this.f_101387_ / 2 - 155;
      int j = this.f_101387_ / 2 + 5;
      this.f_101389_ = p_101430_.m_142416_(CycleButton.m_168916_(this.f_101394_.m_64657_()).m_168959_((p_170280_) -> {
         return CommonComponents.m_178398_(p_170280_.m_168904_(), new TranslatableComponent("selectWorld.mapFeatures.info"));
      }).m_168936_(i, 100, 150, 20, new TranslatableComponent("selectWorld.mapFeatures"), (p_170282_, p_170283_) -> {
         this.f_101394_ = this.f_101394_.m_64672_();
      }));
      this.f_101389_.f_93624_ = false;
      this.f_101390_ = p_101430_.m_142416_(CycleButton.m_168894_(WorldPreset::m_101523_).m_168952_(WorldPreset.f_101508_.stream().filter(WorldPreset::m_170301_).collect(Collectors.toList()), WorldPreset.f_101508_).m_168959_((p_170264_) -> {
         return p_170264_.m_168883_() == WorldPreset.f_101507_ ? CommonComponents.m_178398_(p_170264_.m_168904_(), f_101383_) : p_170264_.m_168904_();
      }).m_168936_(j, 100, 150, 20, new TranslatableComponent("selectWorld.mapType"), (p_170274_, p_170275_) -> {
         this.f_101395_ = Optional.of(p_170275_);
         this.f_101394_ = p_170275_.m_7012_(this.f_101393_, this.f_101394_.m_64619_(), this.f_101394_.m_64657_(), this.f_101394_.m_64660_());
         p_101430_.m_170204_();
      }));
      this.f_101395_.ifPresent(this.f_101390_::m_168892_);
      this.f_101390_.f_93624_ = false;
      this.f_170244_ = p_101430_.m_142416_(new Button(j, 100, 150, 20, CommonComponents.m_178393_(new TranslatableComponent("selectWorld.mapType"), f_101382_), (p_170262_) -> {
      }));
      this.f_170244_.f_93623_ = false;
      this.f_170244_.f_93624_ = false;
      this.f_101391_ = p_101430_.m_142416_(new Button(j, 120, 150, 20, new TranslatableComponent("selectWorld.customizeType"), (p_170248_) -> {
         WorldPreset.PresetEditor worldpreset$preseteditor = WorldPreset.f_101509_.get(this.f_101395_);
         worldpreset$preseteditor = net.minecraftforge.client.ForgeHooksClient.getBiomeGeneratorTypeScreenFactory(this.f_101395_, worldpreset$preseteditor);
         if (worldpreset$preseteditor != null) {
            p_101431_.m_91152_(worldpreset$preseteditor.m_101642_(p_101430_, this.f_101394_));
         }

      }));
      this.f_101391_.f_93624_ = false;
      this.f_101380_ = p_101430_.m_142416_(CycleButton.m_168916_(this.f_101394_.m_64660_() && !p_101430_.f_100845_).m_168936_(i, 151, 150, 20, new TranslatableComponent("selectWorld.bonusItems"), (p_170266_, p_170267_) -> {
         this.f_101394_ = this.f_101394_.m_64673_();
      }));
      this.f_101380_.f_93624_ = false;
      this.f_101392_ = p_101430_.m_142416_(new Button(i, 185, 150, 20, new TranslatableComponent("selectWorld.import_worldgen_settings"), (p_170271_) -> {
         String s = TinyFileDialogs.tinyfd_openFileDialog(f_170243_.getString(), (CharSequence)null, (PointerBuffer)null, (CharSequence)null, false);
         if (s != null) {
            RegistryAccess.RegistryHolder registryaccess$registryholder = RegistryAccess.m_123086_();
            PackRepository packrepository = new PackRepository(PackType.SERVER_DATA, new ServerPacksSource(), new FolderRepositorySource(p_101430_.m_100968_().toFile(), PackSource.f_10529_));

            ServerResources serverresources;
            try {
               MinecraftServer.m_129819_(packrepository, p_101430_.f_100846_, false);
               CompletableFuture<ServerResources> completablefuture = ServerResources.m_180005_(packrepository.m_10525_(), registryaccess$registryholder, Commands.CommandSelection.INTEGRATED, 2, Util.m_137578_(), p_101431_);
               p_101431_.m_18701_(completablefuture::isDone);
               serverresources = completablefuture.get();
            } catch (ExecutionException | InterruptedException interruptedexception) {
               f_101381_.error("Error loading data packs when importing world settings", (Throwable)interruptedexception);
               Component component = new TranslatableComponent("selectWorld.import_worldgen_settings.failure");
               Component component1 = new TextComponent(interruptedexception.getMessage());
               p_101431_.m_91300_().m_94922_(SystemToast.m_94847_(p_101431_, SystemToast.SystemToastIds.WORLD_GEN_SETTINGS_TRANSFER, component, component1));
               packrepository.close();
               return;
            }

            RegistryReadOps<JsonElement> registryreadops = RegistryReadOps.m_179866_(JsonOps.INSTANCE, serverresources.m_136178_(), registryaccess$registryholder);
            JsonParser jsonparser = new JsonParser();

            DataResult<WorldGenSettings> dataresult;
            try {
               BufferedReader bufferedreader = Files.newBufferedReader(Paths.get(s));

               try {
                  JsonElement jsonelement = jsonparser.parse(bufferedreader);
                  dataresult = WorldGenSettings.f_64600_.parse(registryreadops, jsonelement);
               } catch (Throwable throwable1) {
                  if (bufferedreader != null) {
                     try {
                        bufferedreader.close();
                     } catch (Throwable throwable) {
                        throwable1.addSuppressed(throwable);
                     }
                  }

                  throw throwable1;
               }

               if (bufferedreader != null) {
                  bufferedreader.close();
               }
            } catch (JsonIOException | JsonSyntaxException | IOException ioexception) {
               dataresult = DataResult.error("Failed to parse file: " + ioexception.getMessage());
            }

            if (dataresult.error().isPresent()) {
               Component component3 = new TranslatableComponent("selectWorld.import_worldgen_settings.failure");
               String s1 = dataresult.error().get().message();
               f_101381_.error("Error parsing world settings: {}", (Object)s1);
               Component component2 = new TextComponent(s1);
               p_101431_.m_91300_().m_94922_(SystemToast.m_94847_(p_101431_, SystemToast.SystemToastIds.WORLD_GEN_SETTINGS_TRANSFER, component3, component2));
            }

            serverresources.close();
            Lifecycle lifecycle = dataresult.lifecycle();
            dataresult.resultOrPartial(f_101381_::error).ifPresent((p_170254_) -> {
               BooleanConsumer booleanconsumer = (p_170260_) -> {
                  p_101431_.m_91152_(p_101430_);
                  if (p_170260_) {
                     this.m_101442_(registryaccess$registryholder, p_170254_);
                  }

               };
               if (lifecycle == Lifecycle.stable()) {
                  this.m_101442_(registryaccess$registryholder, p_170254_);
               } else if (lifecycle == Lifecycle.experimental()) {
                  p_101431_.m_91152_(new ConfirmScreen(booleanconsumer, new TranslatableComponent("selectWorld.import_worldgen_settings.experimental.title"), new TranslatableComponent("selectWorld.import_worldgen_settings.experimental.question")));
               } else {
                  p_101431_.m_91152_(new ConfirmScreen(booleanconsumer, new TranslatableComponent("selectWorld.import_worldgen_settings.deprecated.title"), new TranslatableComponent("selectWorld.import_worldgen_settings.deprecated.question")));
               }

            });
         }
      }));
      this.f_101392_.f_93624_ = false;
      this.f_101385_ = MultiLineLabel.m_94341_(p_101432_, f_101383_, this.f_101390_.m_5711_());
   }

   private void m_101442_(RegistryAccess.RegistryHolder p_101443_, WorldGenSettings p_101444_) {
      this.f_101393_ = p_101443_;
      this.f_101394_ = p_101444_;
      this.f_101395_ = WorldPreset.m_101524_(p_101444_);
      this.m_170289_(true);
      this.f_101396_ = OptionalLong.of(p_101444_.m_64619_());
      this.f_101388_.m_94144_(m_101447_(this.f_101396_));
   }

   public void m_101469_() {
      this.f_101388_.m_94120_();
   }

   public void m_6305_(PoseStack p_101407_, int p_101408_, int p_101409_, float p_101410_) {
      if (this.f_101389_.f_93624_) {
         this.f_101386_.m_92763_(p_101407_, f_101384_, (float)(this.f_101387_ / 2 - 150), 122.0F, -6250336);
      }

      this.f_101388_.m_6305_(p_101407_, p_101408_, p_101409_, p_101410_);
      if (this.f_101395_.equals(Optional.of(WorldPreset.f_101507_))) {
         this.f_101385_.m_6516_(p_101407_, this.f_101390_.f_93620_ + 2, this.f_101390_.f_93621_ + 22, 9, 10526880);
      }

   }

   public void m_101404_(WorldGenSettings p_101405_) {
      this.f_101394_ = p_101405_;
   }

   private static String m_101447_(OptionalLong p_101448_) {
      return p_101448_.isPresent() ? Long.toString(p_101448_.getAsLong()) : "";
   }

   private static OptionalLong m_101445_(String p_101446_) {
      try {
         return OptionalLong.of(Long.parseLong(p_101446_));
      } catch (NumberFormatException numberformatexception) {
         return OptionalLong.empty();
      }
   }

   public WorldGenSettings m_101454_(boolean p_101455_) {
      OptionalLong optionallong = this.m_101471_();
      return this.f_101394_.m_64654_(p_101455_, optionallong);
   }

   private OptionalLong m_101471_() {
      String s = this.f_101388_.m_94155_();
      OptionalLong optionallong;
      if (StringUtils.isEmpty(s)) {
         optionallong = OptionalLong.empty();
      } else {
         OptionalLong optionallong1 = m_101445_(s);
         if (optionallong1.isPresent() && optionallong1.getAsLong() != 0L) {
            optionallong = optionallong1;
         } else {
            optionallong = OptionalLong.of((long)s.hashCode());
         }
      }

      return optionallong;
   }

   public boolean m_101403_() {
      return this.f_101394_.m_64668_();
   }

   public void m_170287_(boolean p_170288_) {
      this.m_170289_(p_170288_);
      if (this.f_101394_.m_64668_()) {
         this.f_101389_.f_93624_ = false;
         this.f_101380_.f_93624_ = false;
         this.f_101391_.f_93624_ = false;
         this.f_101392_.f_93624_ = false;
      } else {
         this.f_101389_.f_93624_ = p_170288_;
         this.f_101380_.f_93624_ = p_170288_;
         this.f_101391_.f_93624_ = p_170288_ && (WorldPreset.f_101509_.containsKey(this.f_101395_) || net.minecraftforge.client.ForgeHooksClient.hasBiomeGeneratorSettingsOptionsScreen(this.f_101395_));
         this.f_101392_.f_93624_ = p_170288_;
      }

      this.f_101388_.m_94194_(p_170288_);
   }

   private void m_170289_(boolean p_170290_) {
      if (this.f_101395_.isPresent()) {
         this.f_101390_.f_93624_ = p_170290_;
         this.f_170244_.f_93624_ = false;
      } else {
         this.f_101390_.f_93624_ = false;
         this.f_170244_.f_93624_ = p_170290_;
      }

   }

   public RegistryAccess.RegistryHolder m_101456_() {
      return this.f_101393_;
   }

   void m_101452_(ServerResources p_101453_) {
      RegistryAccess.RegistryHolder registryaccess$registryholder = RegistryAccess.m_123086_();
      RegistryWriteOps<JsonElement> registrywriteops = RegistryWriteOps.m_135767_(JsonOps.INSTANCE, this.f_101393_);
      RegistryReadOps<JsonElement> registryreadops = RegistryReadOps.m_179866_(JsonOps.INSTANCE, p_101453_.m_136178_(), registryaccess$registryholder);
      DataResult<WorldGenSettings> dataresult = WorldGenSettings.f_64600_.encodeStart(registrywriteops, this.f_101394_).flatMap((p_170278_) -> {
         return WorldGenSettings.f_64600_.parse(registryreadops, p_170278_);
      });
      dataresult.resultOrPartial(Util.m_137489_("Error parsing worldgen settings after loading data packs: ", f_101381_::error)).ifPresent((p_170286_) -> {
         this.f_101394_ = p_170286_;
         this.f_101393_ = registryaccess$registryholder;
      });
   }

   public void m_170291_() {
      this.f_101380_.f_93623_ = false;
      this.f_101380_.m_168892_(false);
   }

   public void m_170292_() {
      this.f_101380_.f_93623_ = true;
      this.f_101380_.m_168892_(this.f_101394_.m_64660_());
   }
}
