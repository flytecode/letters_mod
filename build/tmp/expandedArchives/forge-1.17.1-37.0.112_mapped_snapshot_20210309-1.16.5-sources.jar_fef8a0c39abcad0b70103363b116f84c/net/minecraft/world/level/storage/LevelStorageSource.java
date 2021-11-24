package net.minecraft.world.level.storage;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Lifecycle;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.annotation.Nullable;
import net.minecraft.FileUtil;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.DirectoryLock;
import net.minecraft.util.MemoryReserve;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LevelStorageSource {
   static final Logger f_78191_ = LogManager.getLogger();
   static final DateTimeFormatter f_78192_ = (new DateTimeFormatterBuilder()).appendValue(ChronoField.YEAR, 4, 10, SignStyle.EXCEEDS_PAD).appendLiteral('-').appendValue(ChronoField.MONTH_OF_YEAR, 2).appendLiteral('-').appendValue(ChronoField.DAY_OF_MONTH, 2).appendLiteral('_').appendValue(ChronoField.HOUR_OF_DAY, 2).appendLiteral('-').appendValue(ChronoField.MINUTE_OF_HOUR, 2).appendLiteral('-').appendValue(ChronoField.SECOND_OF_MINUTE, 2).toFormatter();
   private static final String f_164908_ = "icon.png";
   private static final ImmutableList<String> f_78193_ = ImmutableList.of("RandomSeed", "generatorName", "generatorOptions", "generatorVersion", "legacy_custom_options", "MapFeatures", "BonusChest");
   final Path f_78194_;
   private final Path f_78195_;
   final DataFixer f_78196_;

   public LevelStorageSource(Path p_78199_, Path p_78200_, DataFixer p_78201_) {
      this.f_78196_ = p_78201_;

      try {
         Files.createDirectories(Files.exists(p_78199_) ? p_78199_.toRealPath() : p_78199_);
      } catch (IOException ioexception) {
         throw new RuntimeException(ioexception);
      }

      this.f_78194_ = p_78199_;
      this.f_78195_ = p_78200_;
   }

   public static LevelStorageSource m_78242_(Path p_78243_) {
      return new LevelStorageSource(p_78243_, p_78243_.resolve("../backups"), DataFixers.m_14512_());
   }

   private static <T> Pair<WorldGenSettings, Lifecycle> m_78204_(Dynamic<T> p_78205_, DataFixer p_78206_, int p_78207_) {
      Dynamic<T> dynamic = p_78205_.get("WorldGenSettings").orElseEmptyMap();

      for(String s : f_78193_) {
         Optional<? extends Dynamic<?>> optional = p_78205_.get(s).result();
         if (optional.isPresent()) {
            dynamic = dynamic.set(s, optional.get());
         }
      }

      Dynamic<T> dynamic1 = net.minecraftforge.common.ForgeHooks.fixUpDimensionsData(p_78206_.update(References.f_16795_, dynamic, p_78207_, SharedConstants.m_136187_().getWorldVersion()));
      DataResult<WorldGenSettings> dataresult = WorldGenSettings.f_64600_.parse(dynamic1);
      return Pair.of(dataresult.resultOrPartial(Util.m_137489_("WorldGenSettings: ", f_78191_::error)).orElseGet(() -> {
         Registry<DimensionType> registry = RegistryLookupCodec.m_135622_(Registry.f_122818_).codec().parse(dynamic1).resultOrPartial(Util.m_137489_("Dimension type registry: ", f_78191_::error)).orElseThrow(() -> {
            return new IllegalStateException("Failed to get dimension registry");
         });
         Registry<Biome> registry1 = RegistryLookupCodec.m_135622_(Registry.f_122885_).codec().parse(dynamic1).resultOrPartial(Util.m_137489_("Biome registry: ", f_78191_::error)).orElseThrow(() -> {
            return new IllegalStateException("Failed to get biome registry");
         });
         Registry<NoiseGeneratorSettings> registry2 = RegistryLookupCodec.m_135622_(Registry.f_122878_).codec().parse(dynamic1).resultOrPartial(Util.m_137489_("Noise settings registry: ", f_78191_::error)).orElseThrow(() -> {
            return new IllegalStateException("Failed to get noise settings registry");
         });
         return WorldGenSettings.m_64641_(registry, registry1, registry2);
      }), dataresult.lifecycle());
   }

   private static DataPackConfig m_78202_(Dynamic<?> p_78203_) {
      return DataPackConfig.f_45843_.parse(p_78203_).resultOrPartial(f_78191_::error).orElse(DataPackConfig.f_45842_);
   }

   public String m_164909_() {
      return "Anvil";
   }

   public List<LevelSummary> m_78244_() throws LevelStorageException {
      if (!Files.isDirectory(this.f_78194_)) {
         throw new LevelStorageException((new TranslatableComponent("selectWorld.load_folder_access")).getString());
      } else {
         List<LevelSummary> list = Lists.newArrayList();
         File[] afile = this.f_78194_.toFile().listFiles();

         for(File file1 : afile) {
            if (file1.isDirectory()) {
               boolean flag;
               try {
                  flag = DirectoryLock.m_13642_(file1.toPath());
               } catch (Exception exception) {
                  f_78191_.warn("Failed to read {} lock", file1, exception);
                  continue;
               }

               try {
                  LevelSummary levelsummary = this.m_78229_(file1, this.m_78232_(file1, flag));
                  if (levelsummary != null) {
                     list.add(levelsummary);
                  }
               } catch (OutOfMemoryError outofmemoryerror) {
                  MemoryReserve.m_182328_();
                  System.gc();
                  f_78191_.fatal("Ran out of memory trying to read summary of {}", (Object)file1);
                  throw outofmemoryerror;
               }
            }
         }

         return list;
      }
   }

   int m_78265_() {
      return 19133;
   }

   @Nullable
   <T> T m_78229_(File p_78230_, BiFunction<File, DataFixer, T> p_78231_) {
      if (!p_78230_.exists()) {
         return (T)null;
      } else {
         File file1 = new File(p_78230_, "level.dat");
         if (file1.exists()) {
            T t = p_78231_.apply(file1, this.f_78196_);
            if (t != null) {
               return t;
            }
         }

         file1 = new File(p_78230_, "level.dat_old");
         return (T)(file1.exists() ? p_78231_.apply(file1, this.f_78196_) : null);
      }
   }

   @Nullable
   private static DataPackConfig m_78252_(File p_78253_, DataFixer p_78254_) {
      try {
         CompoundTag compoundtag = NbtIo.m_128937_(p_78253_);
         CompoundTag compoundtag1 = compoundtag.m_128469_("Data");
         compoundtag1.m_128473_("Player");
         int i = compoundtag1.m_128425_("DataVersion", 99) ? compoundtag1.m_128451_("DataVersion") : -1;
         Dynamic<Tag> dynamic = p_78254_.update(DataFixTypes.LEVEL.m_14504_(), new Dynamic<>(NbtOps.f_128958_, compoundtag1), i, SharedConstants.m_136187_().getWorldVersion());
         return dynamic.get("DataPacks").result().map(LevelStorageSource::m_78202_).orElse(DataPackConfig.f_45842_);
      } catch (Exception exception) {
         f_78191_.error("Exception reading {}", p_78253_, exception);
         return null;
      }
   }

   static BiFunction<File, DataFixer, PrimaryLevelData> m_78247_(DynamicOps<Tag> p_78248_, DataPackConfig p_78249_) {
       return getReader(p_78248_, p_78249_, null);
   }

   private static BiFunction<File, DataFixer, PrimaryLevelData> getReader(DynamicOps<Tag> p_78248_, DataPackConfig p_78249_, @Nullable LevelStorageAccess levelSave) {
      return (p_78214_, p_78215_) -> {
         try {
            CompoundTag compoundtag = NbtIo.m_128937_(p_78214_);
            CompoundTag compoundtag1 = compoundtag.m_128469_("Data");
            CompoundTag compoundtag2 = compoundtag1.m_128425_("Player", 10) ? compoundtag1.m_128469_("Player") : null;
            compoundtag1.m_128473_("Player");
            int i = compoundtag1.m_128425_("DataVersion", 99) ? compoundtag1.m_128451_("DataVersion") : -1;
            Dynamic<Tag> dynamic = p_78215_.update(DataFixTypes.LEVEL.m_14504_(), new Dynamic<>(p_78248_, compoundtag1), i, SharedConstants.m_136187_().getWorldVersion());
            Pair<WorldGenSettings, Lifecycle> pair = m_78204_(dynamic, p_78215_, i);
            LevelVersion levelversion = LevelVersion.m_78390_(dynamic);
            LevelSettings levelsettings = LevelSettings.m_46924_(dynamic, p_78249_);
            PrimaryLevelData info = PrimaryLevelData.m_78530_(dynamic, p_78215_, i, compoundtag2, levelsettings, levelversion, pair.getFirst(), pair.getSecond());
            if (levelSave != null)
                net.minecraftforge.fmllegacy.WorldPersistenceHooks.handleWorldDataLoad(levelSave, info, compoundtag);
            return info;
         } catch (Exception exception) {
            f_78191_.error("Exception reading {}", p_78214_, exception);
            return null;
         }
      };
   }

   BiFunction<File, DataFixer, LevelSummary> m_78232_(File p_78233_, boolean p_78234_) {
      return (p_78238_, p_78239_) -> {
         try {
            CompoundTag compoundtag = NbtIo.m_128937_(p_78238_);
            CompoundTag compoundtag1 = compoundtag.m_128469_("Data");
            compoundtag1.m_128473_("Player");
            int i = compoundtag1.m_128425_("DataVersion", 99) ? compoundtag1.m_128451_("DataVersion") : -1;
            Dynamic<Tag> dynamic = p_78239_.update(DataFixTypes.LEVEL.m_14504_(), new Dynamic<>(NbtOps.f_128958_, compoundtag1), i, SharedConstants.m_136187_().getWorldVersion());
            LevelVersion levelversion = LevelVersion.m_78390_(dynamic);
            int j = levelversion.m_78389_();
            if (j != 19132 && j != 19133) {
               return null;
            } else {
               boolean flag = j != this.m_78265_();
               File file1 = new File(p_78233_, "icon.png");
               DataPackConfig datapackconfig = dynamic.get("DataPacks").result().map(LevelStorageSource::m_78202_).orElse(DataPackConfig.f_45842_);
               LevelSettings levelsettings = LevelSettings.m_46924_(dynamic, datapackconfig);
               return new LevelSummary(levelsettings, levelversion, p_78233_.getName(), flag, p_78234_, file1);
            }
         } catch (Exception exception) {
            f_78191_.error("Exception reading {}", p_78238_, exception);
            return null;
         }
      };
   }

   public boolean m_78240_(String p_78241_) {
      try {
         Path path = this.f_78194_.resolve(p_78241_);
         Files.createDirectory(path);
         Files.deleteIfExists(path);
         return true;
      } catch (IOException ioexception) {
         return false;
      }
   }

   public boolean m_78255_(String p_78256_) {
      return Files.isDirectory(this.f_78194_.resolve(p_78256_));
   }

   public Path m_78257_() {
      return this.f_78194_;
   }

   public Path m_78262_() {
      return this.f_78195_;
   }

   public LevelStorageSource.LevelStorageAccess m_78260_(String p_78261_) throws IOException {
      return new LevelStorageSource.LevelStorageAccess(p_78261_);
   }

   public class LevelStorageAccess implements AutoCloseable {
      final DirectoryLock f_78270_;
      final Path f_78271_;
      private final String f_78272_;
      private final Map<LevelResource, Path> f_78273_ = Maps.newHashMap();

      public LevelStorageAccess(String p_78276_) throws IOException {
         this.f_78272_ = p_78276_;
         this.f_78271_ = LevelStorageSource.this.f_78194_.resolve(p_78276_);
         this.f_78270_ = DirectoryLock.m_13640_(this.f_78271_);
      }

      public String m_78277_() {
         return this.f_78272_;
      }

      public Path m_78283_(LevelResource p_78284_) {
         return this.f_78273_.computeIfAbsent(p_78284_, (p_78303_) -> {
            return this.f_78271_.resolve(p_78303_.m_78187_());
         });
      }

      public File m_78299_(ResourceKey<Level> p_78300_) {
         return DimensionType.m_63932_(p_78300_, this.f_78271_.toFile());
      }

      private void m_78313_() {
         if (!this.f_78270_.m_13639_()) {
            throw new IllegalStateException("Lock is no longer valid");
         }
      }

      public PlayerDataStorage m_78301_() {
         this.m_78313_();
         return new PlayerDataStorage(this, LevelStorageSource.this.f_78196_);
      }

      public boolean m_78306_() {
         LevelSummary levelsummary = this.m_78308_();
         return levelsummary != null && levelsummary.m_78371_().m_78389_() != LevelStorageSource.this.m_78265_();
      }

      public boolean m_78278_(ProgressListener p_78279_) {
         this.m_78313_();
         return McRegionUpgrader.m_78400_(this, p_78279_);
      }

      @Nullable
      public LevelSummary m_78308_() {
         this.m_78313_();
         return LevelStorageSource.this.m_78229_(this.f_78271_.toFile(), LevelStorageSource.this.m_78232_(this.f_78271_.toFile(), false));
      }

      @Nullable
      public WorldData m_78280_(DynamicOps<Tag> p_78281_, DataPackConfig p_78282_) {
         this.m_78313_();
         return LevelStorageSource.this.m_78229_(this.f_78271_.toFile(), LevelStorageSource.getReader(p_78281_, p_78282_, this));
      }

      @Nullable
      public DataPackConfig m_78309_() {
         this.m_78313_();
         return LevelStorageSource.this.m_78229_(this.f_78271_.toFile(), LevelStorageSource::m_78252_);
      }

      public void m_78287_(RegistryAccess p_78288_, WorldData p_78289_) {
         this.m_78290_(p_78288_, p_78289_, (CompoundTag)null);
      }

      public void m_78290_(RegistryAccess p_78291_, WorldData p_78292_, @Nullable CompoundTag p_78293_) {
         File file1 = this.f_78271_.toFile();
         CompoundTag compoundtag = p_78292_.m_6626_(p_78291_, p_78293_);
         CompoundTag compoundtag1 = new CompoundTag();
         compoundtag1.m_128365_("Data", compoundtag);

         net.minecraftforge.fmllegacy.WorldPersistenceHooks.handleWorldDataSave(this, p_78292_, compoundtag1);

         try {
            File file2 = File.createTempFile("level", ".dat", file1);
            NbtIo.m_128944_(compoundtag1, file2);
            File file3 = new File(file1, "level.dat_old");
            File file4 = new File(file1, "level.dat");
            Util.m_137462_(file4, file2, file3);
         } catch (Exception exception) {
            LevelStorageSource.f_78191_.error("Failed to save level {}", file1, exception);
         }

      }

      public Optional<Path> m_182514_() {
         return !this.f_78270_.m_13639_() ? Optional.empty() : Optional.of(this.f_78271_.resolve("icon.png"));
      }

      public Path getWorldDir() {
         return f_78271_;
      }

      public void m_78311_() throws IOException {
         this.m_78313_();
         final Path path = this.f_78271_.resolve("session.lock");

         for(int i = 1; i <= 5; ++i) {
            LevelStorageSource.f_78191_.info("Attempt {}...", (int)i);

            try {
               Files.walkFileTree(this.f_78271_, new SimpleFileVisitor<Path>() {
                  public FileVisitResult visitFile(Path p_78323_, BasicFileAttributes p_78324_) throws IOException {
                     if (!p_78323_.equals(path)) {
                        LevelStorageSource.f_78191_.debug("Deleting {}", (Object)p_78323_);
                        Files.delete(p_78323_);
                     }

                     return FileVisitResult.CONTINUE;
                  }

                  public FileVisitResult postVisitDirectory(Path p_78320_, IOException p_78321_) throws IOException {
                     if (p_78321_ != null) {
                        throw p_78321_;
                     } else {
                        if (p_78320_.equals(LevelStorageAccess.this.f_78271_)) {
                           LevelStorageAccess.this.f_78270_.close();
                           Files.deleteIfExists(path);
                        }

                        Files.delete(p_78320_);
                        return FileVisitResult.CONTINUE;
                     }
                  }
               });
               break;
            } catch (IOException ioexception) {
               if (i >= 5) {
                  throw ioexception;
               }

               LevelStorageSource.f_78191_.warn("Failed to delete {}", this.f_78271_, ioexception);

               try {
                  Thread.sleep(500L);
               } catch (InterruptedException interruptedexception) {
               }
            }
         }

      }

      public void m_78297_(String p_78298_) throws IOException {
         this.m_78313_();
         File file1 = new File(LevelStorageSource.this.f_78194_.toFile(), this.f_78272_);
         if (file1.exists()) {
            File file2 = new File(file1, "level.dat");
            if (file2.exists()) {
               CompoundTag compoundtag = NbtIo.m_128937_(file2);
               CompoundTag compoundtag1 = compoundtag.m_128469_("Data");
               compoundtag1.m_128359_("LevelName", p_78298_);
               NbtIo.m_128944_(compoundtag, file2);
            }

         }
      }

      public long m_78312_() throws IOException {
         this.m_78313_();
         String s = LocalDateTime.now().format(LevelStorageSource.f_78192_) + "_" + this.f_78272_;
         Path path = LevelStorageSource.this.m_78262_();

         try {
            Files.createDirectories(Files.exists(path) ? path.toRealPath() : path);
         } catch (IOException ioexception) {
            throw new RuntimeException(ioexception);
         }

         Path path1 = path.resolve(FileUtil.m_133730_(path, s, ".zip"));
         final ZipOutputStream zipoutputstream = new ZipOutputStream(new BufferedOutputStream(Files.newOutputStream(path1)));

         try {
            final Path path2 = Paths.get(this.f_78272_);
            Files.walkFileTree(this.f_78271_, new SimpleFileVisitor<Path>() {
               public FileVisitResult visitFile(Path p_78339_, BasicFileAttributes p_78340_) throws IOException {
                  if (p_78339_.endsWith("session.lock")) {
                     return FileVisitResult.CONTINUE;
                  } else {
                     String s1 = path2.resolve(LevelStorageAccess.this.f_78271_.relativize(p_78339_)).toString().replace('\\', '/');
                     ZipEntry zipentry = new ZipEntry(s1);
                     zipoutputstream.putNextEntry(zipentry);
                     com.google.common.io.Files.asByteSource(p_78339_.toFile()).copyTo(zipoutputstream);
                     zipoutputstream.closeEntry();
                     return FileVisitResult.CONTINUE;
                  }
               }
            });
         } catch (Throwable throwable1) {
            try {
               zipoutputstream.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }

            throw throwable1;
         }

         zipoutputstream.close();
         return Files.size(path1);
      }

      public void close() throws IOException {
         this.f_78270_.close();
      }
   }
}
