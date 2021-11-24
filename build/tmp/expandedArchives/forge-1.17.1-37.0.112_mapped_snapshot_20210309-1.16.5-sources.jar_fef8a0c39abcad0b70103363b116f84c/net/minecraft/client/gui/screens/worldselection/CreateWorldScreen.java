package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.GenericDirtMessageScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.commands.Commands;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class CreateWorldScreen extends Screen {
   private static final Logger f_100848_ = LogManager.getLogger();
   private static final String f_170147_ = "mcworld-";
   private static final Component f_100849_ = new TranslatableComponent("selectWorld.gameMode");
   private static final Component f_100850_ = new TranslatableComponent("selectWorld.enterSeed");
   private static final Component f_100851_ = new TranslatableComponent("selectWorld.seedInfo");
   private static final Component f_100852_ = new TranslatableComponent("selectWorld.enterName");
   private static final Component f_100853_ = new TranslatableComponent("selectWorld.resultFolder");
   private static final Component f_100854_ = new TranslatableComponent("selectWorld.allowCommands.info");
   private final Screen f_100855_;
   private EditBox f_100856_;
   String f_100857_;
   private CreateWorldScreen.SelectedGameMode f_100858_ = CreateWorldScreen.SelectedGameMode.SURVIVAL;
   @Nullable
   private CreateWorldScreen.SelectedGameMode f_100826_;
   private Difficulty f_170145_ = Difficulty.NORMAL;
   private boolean f_100829_;
   private boolean f_100830_;
   public boolean f_100845_;
   protected DataPackConfig f_100846_;
   @Nullable
   private Path f_100831_;
   @Nullable
   private PackRepository f_100832_;
   private boolean f_170146_;
   private Button f_100834_;
   private CycleButton<CreateWorldScreen.SelectedGameMode> f_100835_;
   private CycleButton<Difficulty> f_100836_;
   private Button f_100837_;
   private Button f_100838_;
   private Button f_100839_;
   private CycleButton<Boolean> f_100840_;
   private Component f_100841_;
   private Component f_100842_;
   private String f_100843_;
   private GameRules f_100844_ = new GameRules();
   public final WorldGenSettingsComponent f_100847_;

   public CreateWorldScreen(@Nullable Screen p_100865_, LevelSettings p_100866_, WorldGenSettings p_100867_, @Nullable Path p_100868_, DataPackConfig p_100869_, RegistryAccess.RegistryHolder p_100870_) {
      this(p_100865_, p_100869_, new WorldGenSettingsComponent(p_100870_, p_100867_, WorldPreset.m_101524_(p_100867_), OptionalLong.of(p_100867_.m_64619_())));
      this.f_100843_ = p_100866_.m_46917_();
      this.f_100829_ = p_100866_.m_46932_();
      this.f_100830_ = true;
      this.f_170145_ = p_100866_.m_46931_();
      this.f_100844_.m_46176_(p_100866_.m_46933_(), (MinecraftServer)null);
      if (p_100866_.m_46930_()) {
         this.f_100858_ = CreateWorldScreen.SelectedGameMode.HARDCORE;
      } else if (p_100866_.m_46929_().m_46409_()) {
         this.f_100858_ = CreateWorldScreen.SelectedGameMode.SURVIVAL;
      } else if (p_100866_.m_46929_().m_46408_()) {
         this.f_100858_ = CreateWorldScreen.SelectedGameMode.CREATIVE;
      }

      this.f_100831_ = p_100868_;
   }

   public static CreateWorldScreen m_100898_(@Nullable Screen p_100899_) {
      RegistryAccess.RegistryHolder registryaccess$registryholder = RegistryAccess.m_123086_();
      return new CreateWorldScreen(p_100899_, DataPackConfig.f_45842_, new WorldGenSettingsComponent(registryaccess$registryholder, net.minecraftforge.client.ForgeHooksClient.getDefaultWorldType().map(type -> type.m_7012_(registryaccess$registryholder, new java.util.Random().nextLong(), true, false)).orElseGet(() -> WorldGenSettings.m_64641_(registryaccess$registryholder.m_175512_(Registry.f_122818_), registryaccess$registryholder.m_175512_(Registry.f_122885_), registryaccess$registryholder.m_175512_(Registry.f_122878_))), net.minecraftforge.client.ForgeHooksClient.getDefaultWorldType(), OptionalLong.empty()));
   }

   private CreateWorldScreen(@Nullable Screen p_100861_, DataPackConfig p_100862_, WorldGenSettingsComponent p_100863_) {
      super(new TranslatableComponent("selectWorld.create"));
      this.f_100855_ = p_100861_;
      this.f_100843_ = I18n.m_118938_("selectWorld.newWorld");
      this.f_100846_ = p_100862_;
      this.f_100847_ = p_100863_;
   }

   public void m_96624_() {
      this.f_100856_.m_94120_();
      this.f_100847_.m_101469_();
   }

   protected void m_7856_() {
      this.f_96541_.f_91068_.m_90926_(true);
      this.f_100856_ = new EditBox(this.f_96547_, this.f_96543_ / 2 - 100, 60, 200, 20, new TranslatableComponent("selectWorld.enterName")) {
         protected MutableComponent m_5646_() {
            return CommonComponents.m_178398_(super.m_5646_(), new TranslatableComponent("selectWorld.resultFolder")).m_130946_(" ").m_130946_(CreateWorldScreen.this.f_100857_);
         }
      };
      this.f_100856_.m_94144_(this.f_100843_);
      this.f_100856_.m_94151_((p_100932_) -> {
         this.f_100843_ = p_100932_;
         this.f_100834_.f_93623_ = !this.f_100856_.m_94155_().isEmpty();
         this.m_100971_();
      });
      this.m_7787_(this.f_100856_);
      int i = this.f_96543_ / 2 - 155;
      int j = this.f_96543_ / 2 + 5;
      this.f_100835_ = this.m_142416_(CycleButton.m_168894_(CreateWorldScreen.SelectedGameMode::m_170207_).m_168961_(CreateWorldScreen.SelectedGameMode.SURVIVAL, CreateWorldScreen.SelectedGameMode.HARDCORE, CreateWorldScreen.SelectedGameMode.CREATIVE).m_168948_(this.f_100858_).m_168959_((p_170190_) -> {
         return AbstractWidget.m_168799_(p_170190_.m_6035_()).m_7220_(CommonComponents.f_178389_).m_7220_(this.f_100841_).m_130946_(" ").m_7220_(this.f_100842_);
      }).m_168936_(i, 100, 150, 20, f_100849_, (p_170165_, p_170166_) -> {
         this.m_100900_(p_170166_);
      }));
      this.f_100836_ = this.m_142416_(CycleButton.m_168894_(Difficulty::m_19033_).m_168961_(Difficulty.values()).m_168948_(this.m_170205_()).m_168936_(j, 100, 150, 20, new TranslatableComponent("options.difficulty"), (p_170162_, p_170163_) -> {
         this.f_170145_ = p_170163_;
      }));
      this.f_100840_ = this.m_142416_(CycleButton.m_168916_(this.f_100829_ && !this.f_100845_).m_168959_((p_170160_) -> {
         return CommonComponents.m_178398_(p_170160_.m_168904_(), new TranslatableComponent("selectWorld.allowCommands.info"));
      }).m_168936_(i, 151, 150, 20, new TranslatableComponent("selectWorld.allowCommands"), (p_170168_, p_170169_) -> {
         this.f_100830_ = true;
         this.f_100829_ = p_170169_;
      }));
      this.f_100839_ = this.m_142416_(new Button(j, 151, 150, 20, new TranslatableComponent("selectWorld.dataPacks"), (p_170201_) -> {
         this.m_100975_();
      }));
      this.f_100838_ = this.m_142416_(new Button(i, 185, 150, 20, new TranslatableComponent("selectWorld.gameRules"), (p_100928_) -> {
         this.f_96541_.m_91152_(new EditGameRulesScreen(this.f_100844_.m_46202_(), (p_170182_) -> {
            this.f_96541_.m_91152_(this);
            p_170182_.ifPresent((p_170156_) -> {
               this.f_100844_ = p_170156_;
            });
         }));
      }));
      this.f_100847_.m_101429_(this, this.f_96541_, this.f_96547_);
      this.f_100837_ = this.m_142416_(new Button(j, 185, 150, 20, new TranslatableComponent("selectWorld.moreWorldOptions"), (p_100897_) -> {
         this.m_170148_();
      }));
      this.f_100834_ = this.m_142416_(new Button(i, this.f_96544_ - 28, 150, 20, new TranslatableComponent("selectWorld.create"), (p_170188_) -> {
         this.m_100972_();
      }));
      this.f_100834_.f_93623_ = !this.f_100843_.isEmpty();
      this.m_142416_(new Button(j, this.f_96544_ - 28, 150, 20, CommonComponents.f_130656_, (p_170158_) -> {
         this.m_100967_();
      }));
      this.m_170204_();
      this.m_94718_(this.f_100856_);
      this.m_100900_(this.f_100858_);
      this.m_100971_();
   }

   private Difficulty m_170205_() {
      return this.f_100858_ == CreateWorldScreen.SelectedGameMode.HARDCORE ? Difficulty.HARD : this.f_170145_;
   }

   private void m_100970_() {
      this.f_100841_ = new TranslatableComponent("selectWorld.gameMode." + this.f_100858_.f_101028_ + ".line1");
      this.f_100842_ = new TranslatableComponent("selectWorld.gameMode." + this.f_100858_.f_101028_ + ".line2");
   }

   private void m_100971_() {
      this.f_100857_ = this.f_100856_.m_94155_().trim();
      if (this.f_100857_.isEmpty()) {
         this.f_100857_ = "World";
      }

      try {
         this.f_100857_ = FileUtil.m_133730_(this.f_96541_.m_91392_().m_78257_(), this.f_100857_, "");
      } catch (Exception exception1) {
         this.f_100857_ = "World";

         try {
            this.f_100857_ = FileUtil.m_133730_(this.f_96541_.m_91392_().m_78257_(), this.f_100857_, "");
         } catch (Exception exception) {
            throw new RuntimeException("Could not create save folder", exception);
         }
      }

   }

   public void m_7861_() {
      this.f_96541_.f_91068_.m_90926_(false);
   }

   private void m_100972_() {
      this.f_96541_.m_91346_(new GenericDirtMessageScreen(new TranslatableComponent("createWorld.preparing")));
      if (this.m_100977_()) {
         this.m_100974_();
         WorldGenSettings worldgensettings = this.f_100847_.m_101454_(this.f_100845_);
         LevelSettings levelsettings;
         if (worldgensettings.m_64668_()) {
            GameRules gamerules = new GameRules();
            gamerules.m_46170_(GameRules.f_46140_).m_46246_(false, (MinecraftServer)null);
            levelsettings = new LevelSettings(this.f_100856_.m_94155_().trim(), GameType.SPECTATOR, false, Difficulty.PEACEFUL, true, gamerules, DataPackConfig.f_45842_);
         } else {
            levelsettings = new LevelSettings(this.f_100856_.m_94155_().trim(), this.f_100858_.f_101029_, this.f_100845_, this.m_170205_(), this.f_100829_ && !this.f_100845_, this.f_100844_, this.f_100846_);
         }

         this.f_96541_.m_91202_(this.f_100857_, levelsettings, this.f_100847_.m_101456_(), worldgensettings);
      }
   }

   private void m_170148_() {
      this.m_170196_(!this.f_170146_);
   }

   private void m_100900_(CreateWorldScreen.SelectedGameMode p_100901_) {
      if (!this.f_100830_) {
         this.f_100829_ = p_100901_ == CreateWorldScreen.SelectedGameMode.CREATIVE;
         this.f_100840_.m_168892_(this.f_100829_);
      }

      if (p_100901_ == CreateWorldScreen.SelectedGameMode.HARDCORE) {
         this.f_100845_ = true;
         this.f_100840_.f_93623_ = false;
         this.f_100840_.m_168892_(false);
         this.f_100847_.m_170291_();
         this.f_100836_.m_168892_(Difficulty.HARD);
         this.f_100836_.f_93623_ = false;
      } else {
         this.f_100845_ = false;
         this.f_100840_.f_93623_ = true;
         this.f_100840_.m_168892_(this.f_100829_);
         this.f_100847_.m_170292_();
         this.f_100836_.m_168892_(this.f_170145_);
         this.f_100836_.f_93623_ = true;
      }

      this.f_100858_ = p_100901_;
      this.m_100970_();
   }

   public void m_170204_() {
      this.m_170196_(this.f_170146_);
   }

   private void m_170196_(boolean p_170197_) {
      this.f_170146_ = p_170197_;
      this.f_100835_.f_93624_ = !p_170197_;
      this.f_100836_.f_93624_ = !p_170197_;
      if (this.f_100847_.m_101403_()) {
         this.f_100839_.f_93624_ = false;
         this.f_100835_.f_93623_ = false;
         if (this.f_100826_ == null) {
            this.f_100826_ = this.f_100858_;
         }

         this.m_100900_(CreateWorldScreen.SelectedGameMode.DEBUG);
         this.f_100840_.f_93624_ = false;
      } else {
         this.f_100835_.f_93623_ = true;
         if (this.f_100826_ != null) {
            this.m_100900_(this.f_100826_);
         }

         this.f_100840_.f_93624_ = !p_170197_;
         this.f_100839_.f_93624_ = !p_170197_;
      }

      this.f_100847_.m_170287_(p_170197_);
      this.f_100856_.m_94194_(!p_170197_);
      if (p_170197_) {
         this.f_100837_.m_93666_(CommonComponents.f_130655_);
      } else {
         this.f_100837_.m_93666_(new TranslatableComponent("selectWorld.moreWorldOptions"));
      }

      this.f_100838_.f_93624_ = !p_170197_;
   }

   public boolean m_7933_(int p_100875_, int p_100876_, int p_100877_) {
      if (super.m_7933_(p_100875_, p_100876_, p_100877_)) {
         return true;
      } else if (p_100875_ != 257 && p_100875_ != 335) {
         return false;
      } else {
         this.m_100972_();
         return true;
      }
   }

   public void m_7379_() {
      if (this.f_170146_) {
         this.m_170196_(false);
      } else {
         this.m_100967_();
      }

   }

   public void m_100967_() {
      this.f_96541_.m_91152_(this.f_100855_);
      this.m_100974_();
   }

   private void m_100974_() {
      if (this.f_100832_ != null) {
         this.f_100832_.close();
      }

      this.m_100976_();
   }

   public void m_6305_(PoseStack p_100890_, int p_100891_, int p_100892_, float p_100893_) {
      this.m_7333_(p_100890_);
      m_93215_(p_100890_, this.f_96547_, this.f_96539_, this.f_96543_ / 2, 20, -1);
      if (this.f_170146_) {
         m_93243_(p_100890_, this.f_96547_, f_100850_, this.f_96543_ / 2 - 100, 47, -6250336);
         m_93243_(p_100890_, this.f_96547_, f_100851_, this.f_96543_ / 2 - 100, 85, -6250336);
         this.f_100847_.m_6305_(p_100890_, p_100891_, p_100892_, p_100893_);
      } else {
         m_93243_(p_100890_, this.f_96547_, f_100852_, this.f_96543_ / 2 - 100, 47, -6250336);
         m_93243_(p_100890_, this.f_96547_, (new TextComponent("")).m_7220_(f_100853_).m_130946_(" ").m_130946_(this.f_100857_), this.f_96543_ / 2 - 100, 85, -6250336);
         this.f_100856_.m_6305_(p_100890_, p_100891_, p_100892_, p_100893_);
         m_93243_(p_100890_, this.f_96547_, this.f_100841_, this.f_96543_ / 2 - 150, 122, -6250336);
         m_93243_(p_100890_, this.f_96547_, this.f_100842_, this.f_96543_ / 2 - 150, 134, -6250336);
         if (this.f_100840_.f_93624_) {
            m_93243_(p_100890_, this.f_96547_, f_100854_, this.f_96543_ / 2 - 150, 172, -6250336);
         }
      }

      super.m_6305_(p_100890_, p_100891_, p_100892_, p_100893_);
   }

   protected <T extends GuiEventListener & NarratableEntry> T m_7787_(T p_100948_) {
      return super.m_7787_(p_100948_);
   }

   protected <T extends GuiEventListener & Widget & NarratableEntry> T m_142416_(T p_170199_) {
      return super.m_142416_(p_170199_);
   }

   @Nullable
   protected Path m_100968_() {
      if (this.f_100831_ == null) {
         try {
            this.f_100831_ = Files.createTempDirectory("mcworld-");
         } catch (IOException ioexception) {
            f_100848_.warn("Failed to create temporary dir", (Throwable)ioexception);
            SystemToast.m_94875_(this.f_96541_, this.f_100857_);
            this.m_100967_();
         }
      }

      return this.f_100831_;
   }

   private void m_100975_() {
      Pair<File, PackRepository> pair = this.m_100871_();
      if (pair != null) {
         this.f_96541_.m_91152_(new PackSelectionScreen(this, pair.getSecond(), this::m_100878_, pair.getFirst(), new TranslatableComponent("dataPack.title")));
      }

   }

   private void m_100878_(PackRepository p_100879_) {
      List<String> list = ImmutableList.copyOf(p_100879_.m_10523_());
      List<String> list1 = p_100879_.m_10514_().stream().filter((p_170180_) -> {
         return !list.contains(p_170180_);
      }).collect(ImmutableList.toImmutableList());
      DataPackConfig datapackconfig = new DataPackConfig(list, list1);
      if (list.equals(this.f_100846_.m_45850_())) {
         this.f_100846_ = datapackconfig;
      } else {
         this.f_96541_.m_6937_(() -> {
            this.f_96541_.m_91152_(new GenericDirtMessageScreen(new TranslatableComponent("dataPack.validation.working")));
         });
         ServerResources.m_180005_(p_100879_.m_10525_(), this.f_100847_.m_101456_(), Commands.CommandSelection.INTEGRATED, 2, Util.m_137578_(), this.f_96541_).thenAcceptAsync((p_170154_) -> {
            this.f_100846_ = datapackconfig;
            this.f_100847_.m_101452_(p_170154_);
            p_170154_.close();
         }, this.f_96541_).handle((p_170171_, p_170172_) -> {
            if (p_170172_ != null) {
               f_100848_.warn("Failed to validate datapack", p_170172_);
               this.f_96541_.m_6937_(() -> {
                  this.f_96541_.m_91152_(new ConfirmScreen((p_170203_) -> {
                     if (p_170203_) {
                        this.m_100975_();
                     } else {
                        this.f_100846_ = DataPackConfig.f_45842_;
                        this.f_96541_.m_91152_(this);
                     }

                  }, new TranslatableComponent("dataPack.validation.failed"), TextComponent.f_131282_, new TranslatableComponent("dataPack.validation.back"), new TranslatableComponent("dataPack.validation.reset")));
               });
            } else {
               this.f_96541_.m_6937_(() -> {
                  this.f_96541_.m_91152_(this);
               });
            }

            return null;
         });
      }
   }

   private void m_100976_() {
      if (this.f_100831_ != null) {
         try {
            Stream<Path> stream = Files.walk(this.f_100831_);

            try {
               stream.sorted(Comparator.reverseOrder()).forEach((p_170192_) -> {
                  try {
                     Files.delete(p_170192_);
                  } catch (IOException ioexception1) {
                     f_100848_.warn("Failed to remove temporary file {}", p_170192_, ioexception1);
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
            f_100848_.warn("Failed to list temporary dir {}", (Object)this.f_100831_);
         }

         this.f_100831_ = null;
      }

   }

   private static void m_100912_(Path p_100913_, Path p_100914_, Path p_100915_) {
      try {
         Util.m_137563_(p_100913_, p_100914_, p_100915_);
      } catch (IOException ioexception) {
         f_100848_.warn("Failed to copy datapack file from {} to {}", p_100915_, p_100914_);
         throw new CreateWorldScreen.OperationFailedException(ioexception);
      }
   }

   private boolean m_100977_() {
      if (this.f_100831_ != null) {
         try {
            LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = this.f_96541_.m_91392_().m_78260_(this.f_100857_);

            try {
               Stream<Path> stream = Files.walk(this.f_100831_);

               try {
                  Path path = levelstoragesource$levelstorageaccess.m_78283_(LevelResource.f_78180_);
                  Files.createDirectories(path);
                  stream.filter((p_170174_) -> {
                     return !p_170174_.equals(this.f_100831_);
                  }).forEach((p_170195_) -> {
                     m_100912_(this.f_100831_, path, p_170195_);
                  });
               } catch (Throwable throwable2) {
                  if (stream != null) {
                     try {
                        stream.close();
                     } catch (Throwable throwable1) {
                        throwable2.addSuppressed(throwable1);
                     }
                  }

                  throw throwable2;
               }

               if (stream != null) {
                  stream.close();
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
         } catch (CreateWorldScreen.OperationFailedException | IOException ioexception) {
            f_100848_.warn("Failed to copy datapacks to world {}", this.f_100857_, ioexception);
            SystemToast.m_94875_(this.f_96541_, this.f_100857_);
            this.m_100967_();
            return false;
         }
      }

      return true;
   }

   @Nullable
   public static Path m_100906_(Path p_100907_, Minecraft p_100908_) {
      MutableObject<Path> mutableobject = new MutableObject<>();

      try {
         Stream<Path> stream = Files.walk(p_100907_);

         try {
            stream.filter((p_170177_) -> {
               return !p_170177_.equals(p_100907_);
            }).forEach((p_170186_) -> {
               Path path = mutableobject.getValue();
               if (path == null) {
                  try {
                     path = Files.createTempDirectory("mcworld-");
                  } catch (IOException ioexception1) {
                     f_100848_.warn("Failed to create temporary dir");
                     throw new CreateWorldScreen.OperationFailedException(ioexception1);
                  }

                  mutableobject.setValue(path);
               }

               m_100912_(p_100907_, path, p_170186_);
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
      } catch (CreateWorldScreen.OperationFailedException | IOException ioexception) {
         f_100848_.warn("Failed to copy datapacks from world {}", p_100907_, ioexception);
         SystemToast.m_94875_(p_100908_, p_100907_.toString());
         return null;
      }

      return mutableobject.getValue();
   }

   @Nullable
   private Pair<File, PackRepository> m_100871_() {
      Path path = this.m_100968_();
      if (path != null) {
         File file1 = path.toFile();
         if (this.f_100832_ == null) {
            this.f_100832_ = new PackRepository(PackType.SERVER_DATA, new ServerPacksSource(), new FolderRepositorySource(file1, PackSource.f_10527_));
            net.minecraftforge.fmllegacy.packs.ResourcePackLoader.loadResourcePacks(this.f_100832_, net.minecraftforge.fmllegacy.server.ServerLifecycleHooks::buildPackFinderNew);
            this.f_100832_.m_10506_();
         }

         this.f_100832_.m_10509_(this.f_100846_.m_45850_());
         return Pair.of(file1, this.f_100832_);
      } else {
         return null;
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class OperationFailedException extends RuntimeException {
      public OperationFailedException(Throwable p_101023_) {
         super(p_101023_);
      }
   }

   @OnlyIn(Dist.CLIENT)
   static enum SelectedGameMode {
      SURVIVAL("survival", GameType.SURVIVAL),
      HARDCORE("hardcore", GameType.SURVIVAL),
      CREATIVE("creative", GameType.CREATIVE),
      DEBUG("spectator", GameType.SPECTATOR);

      final String f_101028_;
      final GameType f_101029_;
      private final Component f_170206_;

      private SelectedGameMode(String p_101035_, GameType p_101036_) {
         this.f_101028_ = p_101035_;
         this.f_101029_ = p_101036_;
         this.f_170206_ = new TranslatableComponent("selectWorld.gameMode." + p_101035_);
      }

      public Component m_170207_() {
         return this.f_170206_;
      }
   }
}
