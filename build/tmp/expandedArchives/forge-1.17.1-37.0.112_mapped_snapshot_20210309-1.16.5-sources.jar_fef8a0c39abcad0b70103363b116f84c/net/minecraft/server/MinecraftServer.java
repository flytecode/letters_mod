package net.minecraft.server;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import it.unimi.dsi.fastutil.longs.LongIterator;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.net.Proxy;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import javax.imageio.ImageIO;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.worldgen.Features;
import net.minecraft.gametest.framework.GameTestTicker;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundChangeDifficultyPacket;
import net.minecraft.network.protocol.game.ClientboundSetTimePacket;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.bossevents.CustomBossEvents;
import net.minecraft.server.level.DemoMode;
import net.minecraft.server.level.PlayerRespawnLogic;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.level.TicketType;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.network.ServerConnectionListener;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.ServerOpListEntry;
import net.minecraft.server.players.UserWhiteList;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagContainer;
import net.minecraft.util.Crypt;
import net.minecraft.util.CryptException;
import net.minecraft.util.FrameTimer;
import net.minecraft.util.Mth;
import net.minecraft.util.ProgressListener;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.EmptyProfileResults;
import net.minecraft.util.profiling.ProfileResults;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.ResultField;
import net.minecraft.util.profiling.SingleTickProfiler;
import net.minecraft.util.profiling.metrics.profiling.ActiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.InactiveMetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.MetricsRecorder;
import net.minecraft.util.profiling.metrics.profiling.ServerMetricsSamplersProvider;
import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import net.minecraft.world.Difficulty;
import net.minecraft.world.Snooper;
import net.minecraft.world.SnooperPopulator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.village.VillageSiege;
import net.minecraft.world.entity.npc.CatSpawner;
import net.minecraft.world.entity.npc.WanderingTraderSpawner;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.ForcedChunksSavedData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.BiomeManager;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.border.BorderChangeListener;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import net.minecraft.world.level.levelgen.PhantomSpawner;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.storage.CommandStorage;
import net.minecraft.world.level.storage.DerivedLevelData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelData;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WorldData;
import net.minecraft.world.level.storage.loot.ItemModifierManager;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.PredicateManager;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class MinecraftServer extends ReentrantBlockableEventLoop<TickTask> implements SnooperPopulator, CommandSource, AutoCloseable {
   static final Logger f_129750_ = LogManager.getLogger();
   private static final float f_177884_ = 0.8F;
   private static final int f_177885_ = 100;
   public static final int f_177878_ = 50;
   private static final int f_177886_ = 6000;
   private static final int f_177887_ = 2000;
   private static final int f_177888_ = 15000;
   public static final String f_177879_ = "level";
   public static final String f_177880_ = "level://";
   private static final long f_177889_ = 5000000000L;
   private static final int f_177890_ = 12;
   public static final String f_177881_ = "resources.zip";
   public static final File f_129742_ = new File("usercache.json");
   public static final int f_177882_ = 11;
   private static final int f_177891_ = 441;
   private static final int f_177892_ = 6000;
   private static final int f_177893_ = 3;
   public static final int f_177883_ = 29999984;
   public static final LevelSettings f_129743_ = new LevelSettings("Demo World", GameType.SURVIVAL, false, Difficulty.NORMAL, false, new GameRules(), DataPackConfig.f_45842_);
   private static final long f_177871_ = 50L;
   protected final LevelStorageSource.LevelStorageAccess f_129744_;
   protected final PlayerDataStorage f_129745_;
   private final Snooper f_129751_ = new Snooper("server", this, Util.m_137550_());
   private final List<Runnable> f_129752_ = Lists.newArrayList();
   private MetricsRecorder f_177872_ = InactiveMetricsRecorder.f_146153_;
   private ProfilerFiller f_129754_ = this.f_177872_.m_142610_();
   private Consumer<ProfileResults> f_177873_ = (p_177903_) -> {
      this.m_177928_();
   };
   private Consumer<Path> f_177874_ = (p_177954_) -> {
   };
   private boolean f_177875_;
   @Nullable
   private MinecraftServer.TimeProfiler f_177876_;
   private boolean f_177877_;
   private final ServerConnectionListener f_129755_;
   private final ChunkProgressListenerFactory f_129756_;
   private final ServerStatus f_129757_ = new ServerStatus();
   private final Random f_129758_ = new Random();
   private final DataFixer f_129759_;
   private String f_129760_;
   private int f_129761_ = -1;
   protected final RegistryAccess.RegistryHolder f_129746_;
   private final Map<ResourceKey<Level>, ServerLevel> f_129762_ = Maps.newLinkedHashMap();
   private PlayerList f_129763_;
   private volatile boolean f_129764_ = true;
   private boolean f_129765_;
   private int f_129766_;
   protected final Proxy f_129747_;
   private boolean f_129705_;
   private boolean f_129706_;
   private boolean f_129707_;
   private boolean f_129708_;
   @Nullable
   private String f_129709_;
   private int f_129711_;
   public final long[] f_129748_ = new long[100];
   @Nullable
   private KeyPair f_129712_;
   @Nullable
   private String f_129713_;
   private boolean f_129714_;
   private String f_129715_ = "";
   private String f_129716_ = "";
   private volatile boolean f_129717_;
   private long f_129718_;
   private final MinecraftSessionService f_129721_;
   @Nullable
   private final GameProfileRepository f_129722_;
   @Nullable
   private final GameProfileCache f_129723_;
   private long f_129724_;
   private final Thread f_129725_;
   protected long f_129726_ = Util.m_137550_();
   private long f_129727_;
   private boolean f_129728_;
   private final PackRepository f_129730_;
   private final ServerScoreboard f_129731_ = new ServerScoreboard(this);
   @Nullable
   private CommandStorage f_129732_;
   private final CustomBossEvents f_129733_ = new CustomBossEvents();
   private final ServerFunctionManager f_129734_;
   private final FrameTimer f_129735_ = new FrameTimer();
   private boolean f_129736_;
   private float f_129737_;
   private final Executor f_129738_;
   @Nullable
   private String f_129739_;
   private ServerResources f_129740_;
   private final StructureManager f_129741_;
   protected final WorldData f_129749_;

   public static <S extends MinecraftServer> S m_129872_(Function<Thread, S> p_129873_) {
      AtomicReference<S> atomicreference = new AtomicReference<>();
      Thread thread = new Thread(net.minecraftforge.fml.util.thread.SidedThreadGroups.SERVER, () -> {
         atomicreference.get().m_130011_();
      }, "Server thread");
      thread.setUncaughtExceptionHandler((p_177909_, p_177910_) -> {
         f_129750_.error(p_177910_);
      });
      S s = p_129873_.apply(thread);
      atomicreference.set(s);
      thread.start();
      return s;
   }

   public MinecraftServer(Thread p_129769_, RegistryAccess.RegistryHolder p_129770_, LevelStorageSource.LevelStorageAccess p_129771_, WorldData p_129772_, PackRepository p_129773_, Proxy p_129774_, DataFixer p_129775_, ServerResources p_129776_, @Nullable MinecraftSessionService p_129777_, @Nullable GameProfileRepository p_129778_, @Nullable GameProfileCache p_129779_, ChunkProgressListenerFactory p_129780_) {
      super("Server");
      this.f_129746_ = p_129770_;
      this.f_129749_ = p_129772_;
      this.f_129747_ = p_129774_;
      this.f_129730_ = p_129773_;
      this.f_129740_ = p_129776_;
      this.f_129721_ = p_129777_;
      this.f_129722_ = p_129778_;
      this.f_129723_ = p_129779_;
      if (p_129779_ != null) {
         p_129779_.m_143974_(this);
      }

      this.f_129755_ = new ServerConnectionListener(this);
      this.f_129756_ = p_129780_;
      this.f_129744_ = p_129771_;
      this.f_129745_ = p_129771_.m_78301_();
      this.f_129759_ = p_129775_;
      this.f_129734_ = new ServerFunctionManager(this, p_129776_.m_136157_());
      this.f_129741_ = new StructureManager(p_129776_.m_136178_(), p_129771_, p_129775_);
      this.f_129725_ = p_129769_;
      this.f_129738_ = Util.m_137578_();
   }

   private void m_129841_(DimensionDataStorage p_129842_) {
      p_129842_.m_164861_(this.m_129896_()::m_180013_, this.m_129896_()::m_180015_, "scoreboard");
   }

   protected abstract boolean m_7038_() throws IOException;

   public static void m_129845_(LevelStorageSource.LevelStorageAccess p_129846_) {
      if (p_129846_.m_78306_()) {
         f_129750_.info("Converting map!");
         p_129846_.m_78278_(new ProgressListener() {
            private long f_130015_ = Util.m_137550_();

            public void m_6309_(Component p_130021_) {
            }

            public void m_6308_(Component p_130023_) {
            }

            public void m_6952_(int p_130019_) {
               if (Util.m_137550_() - this.f_130015_ >= 1000L) {
                  this.f_130015_ = Util.m_137550_();
                  MinecraftServer.f_129750_.info("Converting... {}%", (int)p_130019_);
               }

            }

            public void m_7730_() {
            }

            public void m_6307_(Component p_130025_) {
            }
         });
      }

   }

   protected void m_130006_() {
      this.m_130007_();
      this.f_129749_.m_7955_(this.m_130001_(), this.m_6730_().isPresent());
      ChunkProgressListener chunkprogresslistener = this.f_129756_.m_9620_(11);
      this.m_129815_(chunkprogresslistener);
      this.m_7044_();
      this.m_129940_(chunkprogresslistener);
   }

   protected void m_7044_() {
   }

   protected void m_129815_(ChunkProgressListener p_129816_) {
      ServerLevelData serverleveldata = this.f_129749_.m_5996_();
      WorldGenSettings worldgensettings = this.f_129749_.m_5961_();
      boolean flag = worldgensettings.m_64668_();
      long i = worldgensettings.m_64619_();
      long j = BiomeManager.m_47877_(i);
      List<CustomSpawner> list = ImmutableList.of(new PhantomSpawner(), new PatrolSpawner(), new CatSpawner(), new VillageSiege(), new WanderingTraderSpawner(serverleveldata));
      MappedRegistry<LevelStem> mappedregistry = worldgensettings.m_64663_();
      LevelStem levelstem = mappedregistry.m_6246_(LevelStem.f_63971_);
      ChunkGenerator chunkgenerator;
      DimensionType dimensiontype;
      if (levelstem == null) {
         dimensiontype = this.f_129746_.<DimensionType>m_175515_(Registry.f_122818_).m_123013_(DimensionType.f_63845_);
         chunkgenerator = WorldGenSettings.m_64637_(this.f_129746_.m_175515_(Registry.f_122885_), this.f_129746_.m_175515_(Registry.f_122878_), (new Random()).nextLong());
      } else {
         dimensiontype = levelstem.m_63989_();
         chunkgenerator = levelstem.m_63990_();
      }

      ServerLevel serverlevel = new ServerLevel(this, this.f_129738_, this.f_129744_, serverleveldata, Level.f_46428_, dimensiontype, p_129816_, chunkgenerator, flag, j, list, true);
      this.f_129762_.put(Level.f_46428_, serverlevel);
      DimensionDataStorage dimensiondatastorage = serverlevel.m_8895_();
      this.m_129841_(dimensiondatastorage);
      this.f_129732_ = new CommandStorage(dimensiondatastorage);
      WorldBorder worldborder = serverlevel.m_6857_();
      worldborder.m_61931_(serverleveldata.m_5813_());
      if (!serverleveldata.m_6535_()) {
         try {
            m_177896_(serverlevel, serverleveldata, worldgensettings.m_64660_(), flag);
            serverleveldata.m_5555_(true);
            if (flag) {
               this.m_129847_(this.f_129749_);
            }
         } catch (Throwable throwable1) {
            CrashReport crashreport = CrashReport.m_127521_(throwable1, "Exception initializing level");

            try {
               serverlevel.m_6026_(crashreport);
            } catch (Throwable throwable) {
            }

            throw new ReportedException(crashreport);
         }

         serverleveldata.m_5555_(true);
      }

      this.m_6846_().m_11219_(serverlevel);
      if (this.f_129749_.m_6587_() != null) {
         this.m_129901_().m_136295_(this.f_129749_.m_6587_());
      }

      for(Entry<ResourceKey<LevelStem>, LevelStem> entry : mappedregistry.m_6579_()) {
         ResourceKey<LevelStem> resourcekey = entry.getKey();
         if (resourcekey != LevelStem.f_63971_) {
            ResourceKey<Level> resourcekey1 = ResourceKey.m_135785_(Registry.f_122819_, resourcekey.m_135782_());
            DimensionType dimensiontype1 = entry.getValue().m_63989_();
            ChunkGenerator chunkgenerator1 = entry.getValue().m_63990_();
            DerivedLevelData derivedleveldata = new DerivedLevelData(this.f_129749_, serverleveldata);
            ServerLevel serverlevel1 = new ServerLevel(this, this.f_129738_, this.f_129744_, derivedleveldata, resourcekey1, dimensiontype1, p_129816_, chunkgenerator1, flag, j, ImmutableList.of(), false);
            worldborder.m_61929_(new BorderChangeListener.DelegateBorderChangeListener(serverlevel1.m_6857_()));
            this.f_129762_.put(resourcekey1, serverlevel1);
         }
         net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.WorldEvent.Load(f_129762_.get(resourcekey)));
      }

   }

   private static void m_177896_(ServerLevel p_177897_, ServerLevelData p_177898_, boolean p_177899_, boolean p_177900_) {
      if (p_177900_) {
         p_177898_.m_7250_(BlockPos.f_121853_.m_6630_(80), 0.0F);
      } else {
         ChunkGenerator chunkgenerator = p_177897_.m_7726_().m_8481_();
         if (net.minecraftforge.event.ForgeEventFactory.onCreateWorldSpawn(p_177897_, p_177898_)) return;
         BiomeSource biomesource = chunkgenerator.m_62218_();
         Random random = new Random(p_177897_.m_7328_());
         BlockPos blockpos = biomesource.m_47909_(0, p_177897_.m_5736_(), 0, 256, (p_177905_) -> {
            return p_177905_.m_47518_().m_48353_();
         }, random);
         ChunkPos chunkpos = blockpos == null ? new ChunkPos(0, 0) : new ChunkPos(blockpos);
         if (blockpos == null) {
            f_129750_.warn("Unable to find spawn biome");
         }

         boolean flag = false;

         for(Block block : BlockTags.f_13048_.m_6497_()) {
            if (biomesource.m_47925_().contains(block.m_49966_())) {
               flag = true;
               break;
            }
         }

         int j1 = chunkgenerator.m_142051_(p_177897_);
         if (j1 < p_177897_.m_141937_()) {
            BlockPos blockpos2 = chunkpos.m_45615_();
            j1 = p_177897_.m_6924_(Heightmap.Types.WORLD_SURFACE, blockpos2.m_123341_() + 8, blockpos2.m_123343_() + 8);
         }

         p_177898_.m_7250_(chunkpos.m_45615_().m_142082_(8, j1, 8), 0.0F);
         int k1 = 0;
         int i = 0;
         int j = 0;
         int k = -1;
         int l = 32;

         for(int i1 = 0; i1 < 1024; ++i1) {
            if (k1 > -16 && k1 <= 16 && i > -16 && i <= 16) {
               BlockPos blockpos1 = PlayerRespawnLogic.m_8269_(p_177897_, new ChunkPos(chunkpos.f_45578_ + k1, chunkpos.f_45579_ + i), flag);
               if (blockpos1 != null) {
                  p_177898_.m_7250_(blockpos1, 0.0F);
                  break;
               }
            }

            if (k1 == i || k1 < 0 && k1 == -i || k1 > 0 && k1 == 1 - i) {
               int l1 = j;
               j = -k;
               k = l1;
            }

            k1 += j;
            i += k;
         }

         if (p_177899_) {
            ConfiguredFeature<?, ?> configuredfeature = Features.f_126881_;
            configuredfeature.m_65385_(p_177897_, chunkgenerator, p_177897_.f_46441_, new BlockPos(p_177898_.m_6789_(), p_177898_.m_6527_(), p_177898_.m_6526_()));
         }

      }
   }

   private void m_129847_(WorldData p_129848_) {
      p_129848_.m_6166_(Difficulty.PEACEFUL);
      p_129848_.m_5560_(true);
      ServerLevelData serverleveldata = p_129848_.m_5996_();
      serverleveldata.m_5565_(false);
      serverleveldata.m_5557_(false);
      serverleveldata.m_6393_(1000000000);
      serverleveldata.m_6247_(6000L);
      serverleveldata.m_5458_(GameType.SPECTATOR);
   }

   private void m_129940_(ChunkProgressListener p_129941_) {
      net.minecraftforge.common.world.StructureSpawnManager.gatherEntitySpawns();
      ServerLevel serverlevel = this.m_129783_();
      f_129750_.info("Preparing start region for dimension {}", (Object)serverlevel.m_46472_().m_135782_());
      BlockPos blockpos = serverlevel.m_8900_();
      p_129941_.m_7647_(new ChunkPos(blockpos));
      ServerChunkCache serverchunkcache = serverlevel.m_7726_();
      serverchunkcache.m_7827_().m_9310_(500);
      this.f_129726_ = Util.m_137550_();
      serverchunkcache.m_8387_(TicketType.f_9442_, new ChunkPos(blockpos), 11, Unit.INSTANCE);

      while(serverchunkcache.m_8427_() != 441) {
         this.f_129726_ = Util.m_137550_() + 10L;
         this.m_130012_();
      }

      this.f_129726_ = Util.m_137550_() + 10L;
      this.m_130012_();

      for(ServerLevel serverlevel1 : this.f_129762_.values()) {
         ForcedChunksSavedData forcedchunkssaveddata = serverlevel1.m_8895_().m_164858_(ForcedChunksSavedData::m_151483_, "chunks");
         if (forcedchunkssaveddata != null) {
            LongIterator longiterator = forcedchunkssaveddata.m_46116_().iterator();

            while(longiterator.hasNext()) {
               long i = longiterator.nextLong();
               ChunkPos chunkpos = new ChunkPos(i);
               serverlevel1.m_7726_().m_6692_(chunkpos, true);
            }
            net.minecraftforge.common.world.ForgeChunkManager.reinstatePersistentChunks(serverlevel1, forcedchunkssaveddata);
         }
      }

      this.f_129726_ = Util.m_137550_() + 10L;
      this.m_130012_();
      p_129941_.m_7646_();
      serverchunkcache.m_7827_().m_9310_(5);
      this.m_129962_();
   }

   protected void m_130007_() {
      File file1 = this.f_129744_.m_78283_(LevelResource.f_78181_).toFile();
      if (file1.isFile()) {
         String s = this.f_129744_.m_78277_();

         try {
            this.m_129853_("level://" + URLEncoder.encode(s, StandardCharsets.UTF_8.toString()) + "/resources.zip", "");
         } catch (UnsupportedEncodingException unsupportedencodingexception) {
            f_129750_.warn("Something went wrong url encoding {}", (Object)s);
         }
      }

   }

   public GameType m_130008_() {
      return this.f_129749_.m_5464_();
   }

   public boolean m_7035_() {
      return this.f_129749_.m_5466_();
   }

   public abstract int m_7022_();

   public abstract int m_7034_();

   public abstract boolean m_6983_();

   public boolean m_129885_(boolean p_129886_, boolean p_129887_, boolean p_129888_) {
      boolean flag = false;

      for(ServerLevel serverlevel : this.m_129785_()) {
         if (!p_129886_) {
            f_129750_.info("Saving chunks for level '{}'/{}", serverlevel, serverlevel.m_46472_().m_135782_());
         }

         serverlevel.m_8643_((ProgressListener)null, p_129887_, serverlevel.f_8564_ && !p_129888_);
         flag = true;
      }

      ServerLevel serverlevel2 = this.m_129783_();
      ServerLevelData serverleveldata = this.f_129749_.m_5996_();
      serverleveldata.m_7831_(serverlevel2.m_6857_().m_61970_());
      this.f_129749_.m_5917_(this.m_129901_().m_136307_());
      this.f_129744_.m_78290_(this.f_129746_, this.f_129749_, this.m_6846_().m_6960_());
      if (p_129887_) {
         for(ServerLevel serverlevel1 : this.m_129785_()) {
            f_129750_.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", (Object)serverlevel1.m_7726_().f_8325_.m_182285_());
         }

         f_129750_.info("ThreadedAnvilChunkStorage: All dimensions are saved");
      }

      return flag;
   }

   public void close() {
      this.m_7041_();
   }

   public void m_7041_() {
      f_129750_.info("Stopping server");
      if (this.m_129919_() != null) {
         this.m_129919_().m_9718_();
      }

      if (this.f_129763_ != null) {
         f_129750_.info("Saving players");
         this.f_129763_.m_11302_();
         this.f_129763_.m_11313_();
      }

      f_129750_.info("Saving worlds");

      for(ServerLevel serverlevel : this.m_129785_()) {
         if (serverlevel != null) {
            serverlevel.f_8564_ = false;
         }
      }

      this.m_129885_(false, true, false);

      for(ServerLevel serverlevel1 : this.m_129785_()) {
         if (serverlevel1 != null) {
            try {
               net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.world.WorldEvent.Unload(serverlevel1));
               serverlevel1.close();
            } catch (IOException ioexception1) {
               f_129750_.error("Exception closing the level", (Throwable)ioexception1);
            }
         }
      }

      if (this.f_129751_.m_19230_()) {
         this.f_129751_.m_19231_();
      }

      this.f_129740_.close();

      try {
         this.f_129744_.close();
      } catch (IOException ioexception) {
         f_129750_.error("Failed to unlock level {}", this.f_129744_.m_78277_(), ioexception);
      }

   }

   public String m_130009_() {
      return this.f_129760_;
   }

   public void m_129913_(String p_129914_) {
      this.f_129760_ = p_129914_;
   }

   public boolean m_130010_() {
      return this.f_129764_;
   }

   public void m_7570_(boolean p_129884_) {
      this.f_129764_ = false;
      if (p_129884_) {
         try {
            this.f_129725_.join();
         } catch (InterruptedException interruptedexception) {
            f_129750_.error("Error while shutting down", (Throwable)interruptedexception);
         }
      }

   }

   protected void m_130011_() {
      try {
         if (this.m_7038_()) {
            net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleServerStarted(this);
            this.f_129726_ = Util.m_137550_();
            this.f_129757_.m_134908_(new TextComponent(this.f_129709_));
            this.f_129757_.m_134912_(new ServerStatus.Version(SharedConstants.m_136187_().getName(), SharedConstants.m_136187_().getProtocolVersion()));
            this.m_129878_(this.f_129757_);

            while(this.f_129764_) {
               long i = Util.m_137550_() - this.f_129726_;
               if (i > 2000L && this.f_129726_ - this.f_129718_ >= 15000L) {
                  long j = i / 50L;
                  f_129750_.warn("Can't keep up! Is the server overloaded? Running {}ms or {} ticks behind", i, j);
                  this.f_129726_ += j * 50L;
                  this.f_129718_ = this.f_129726_;
               }

               if (this.f_177877_) {
                  this.f_177877_ = false;
                  this.f_177876_ = new MinecraftServer.TimeProfiler(Util.m_137569_(), this.f_129766_);
               }

               this.f_129726_ += 50L;
               this.m_177945_();
               this.f_129754_.m_6180_("tick");
               this.m_5705_(this::m_129960_);
               this.f_129754_.m_6182_("nextTickWait");
               this.f_129728_ = true;
               this.f_129727_ = Math.max(Util.m_137550_() + 50L, this.f_129726_);
               this.m_130012_();
               this.f_129754_.m_7238_();
               this.m_177946_();
               this.f_129717_ = true;
            }
            net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleServerStopping(this);
            net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.expectServerStopped(); // has to come before finalTick to avoid race conditions
         } else {
            net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.expectServerStopped(); // has to come before finalTick to avoid race conditions
            this.m_7268_((CrashReport)null);
         }
      } catch (Throwable throwable1) {
         f_129750_.error("Encountered an unexpected exception", throwable1);
         CrashReport crashreport;
         if (throwable1 instanceof ReportedException) {
            crashreport = ((ReportedException)throwable1).m_134761_();
         } else {
            crashreport = new CrashReport("Exception in server tick loop", throwable1);
         }

         this.m_177935_(crashreport.m_178626_());
         File file1 = new File(new File(this.m_6237_(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
         if (crashreport.m_127512_(file1)) {
            f_129750_.error("This crash report has been saved to: {}", (Object)file1.getAbsolutePath());
         } else {
            f_129750_.error("We were unable to save this crash report to disk.");
         }

         net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.expectServerStopped(); // has to come before finalTick to avoid race conditions
         this.m_7268_(crashreport);
      } finally {
         try {
            this.f_129765_ = true;
            this.m_7041_();
         } catch (Throwable throwable) {
            f_129750_.error("Exception stopping the server", throwable);
         } finally {
            net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleServerStopped(this);
            this.m_6988_();
         }

      }

   }

   private boolean m_129960_() {
      return this.m_18767_() || Util.m_137550_() < (this.f_129728_ ? this.f_129727_ : this.f_129726_);
   }

   protected void m_130012_() {
      this.m_18699_();
      this.m_18701_(() -> {
         return !this.m_129960_();
      });
   }

   protected TickTask m_6681_(Runnable p_129852_) {
      return new TickTask(this.f_129766_, p_129852_);
   }

   protected boolean m_6362_(TickTask p_129883_) {
      return p_129883_.m_136254_() + 3 < this.f_129766_ || this.m_129960_();
   }

   public boolean m_7245_() {
      boolean flag = this.m_129961_();
      this.f_129728_ = flag;
      return flag;
   }

   private boolean m_129961_() {
      if (super.m_7245_()) {
         return true;
      } else {
         if (this.m_129960_()) {
            for(ServerLevel serverlevel : this.m_129785_()) {
               if (serverlevel.m_7726_().m_8466_()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void m_6367_(TickTask p_129957_) {
      this.m_129905_().m_6174_("runTask");
      super.m_6367_(p_129957_);
   }

   private void m_129878_(ServerStatus p_129879_) {
      Optional<File> optional = Optional.of(this.m_129971_("server-icon.png")).filter(File::isFile);
      if (!optional.isPresent()) {
         optional = this.f_129744_.m_182514_().map(Path::toFile).filter(File::isFile);
      }

      optional.ifPresent((p_182663_) -> {
         try {
            BufferedImage bufferedimage = ImageIO.read(p_182663_);
            Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide");
            Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high");
            ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
            ImageIO.write(bufferedimage, "PNG", bytearrayoutputstream);
            byte[] abyte = Base64.getEncoder().encode(bytearrayoutputstream.toByteArray());
            p_129879_.m_134906_("data:image/png;base64," + new String(abyte, StandardCharsets.UTF_8));
         } catch (Exception exception) {
            f_129750_.error("Couldn't load server icon", (Throwable)exception);
         }

      });
   }

   public Optional<Path> m_182649_() {
      return this.f_129744_.m_182514_();
   }

   public File m_6237_() {
      return new File(".");
   }

   protected void m_7268_(CrashReport p_129874_) {
   }

   public void m_6988_() {
   }

   public void m_5705_(BooleanSupplier p_129871_) {
      long i = Util.m_137569_();
      net.minecraftforge.fmllegacy.hooks.BasicEventHooks.onPreServerTick();
      ++this.f_129766_;
      this.m_5703_(p_129871_);
      if (i - this.f_129724_ >= 5000000000L) {
         this.f_129724_ = i;
         this.f_129757_.m_134910_(new ServerStatus.Players(this.m_7418_(), this.m_7416_()));
         GameProfile[] agameprofile = new GameProfile[Math.min(this.m_7416_(), 12)];
         int j = Mth.m_14072_(this.f_129758_, 0, this.m_7416_() - agameprofile.length);

         for(int k = 0; k < agameprofile.length; ++k) {
            agameprofile[k] = this.f_129763_.m_11314_().get(j + k).m_36316_();
         }

         Collections.shuffle(Arrays.asList(agameprofile));
         this.f_129757_.m_134914_().m_134924_(agameprofile);
         this.f_129757_.invalidateJson();
      }

      if (this.f_129766_ % 6000 == 0) {
         f_129750_.debug("Autosave started");
         this.f_129754_.m_6180_("save");
         this.f_129763_.m_11302_();
         this.m_129885_(true, false, false);
         this.f_129754_.m_7238_();
         f_129750_.debug("Autosave finished");
      }

      this.f_129754_.m_6180_("snooper");
      if (!this.f_129751_.m_19230_() && this.f_129766_ > 100) {
         this.f_129751_.m_19222_();
      }

      if (this.f_129766_ % 6000 == 0) {
         this.f_129751_.m_19226_();
      }

      this.f_129754_.m_7238_();
      this.f_129754_.m_6180_("tallying");
      long l = this.f_129748_[this.f_129766_ % 100] = Util.m_137569_() - i;
      this.f_129737_ = this.f_129737_ * 0.8F + (float)l / 1000000.0F * 0.19999999F;
      long i1 = Util.m_137569_();
      this.f_129735_.m_13755_(i1 - i);
      this.f_129754_.m_7238_();
      net.minecraftforge.fmllegacy.hooks.BasicEventHooks.onPostServerTick();
   }

   public void m_5703_(BooleanSupplier p_129954_) {
      this.f_129754_.m_6180_("commandFunctions");
      this.m_129890_().m_136128_();
      this.f_129754_.m_6182_("levels");

      for(ServerLevel serverlevel : this.getWorldArray()) {
         long tickStart = Util.m_137569_();
         this.f_129754_.m_6521_(() -> {
            return serverlevel + " " + serverlevel.m_46472_().m_135782_();
         });
         if (this.f_129766_ % 20 == 0) {
            this.f_129754_.m_6180_("timeSync");
            this.f_129763_.m_11270_(new ClientboundSetTimePacket(serverlevel.m_46467_(), serverlevel.m_46468_(), serverlevel.m_46469_().m_46207_(GameRules.f_46140_)), serverlevel.m_46472_());
            this.f_129754_.m_7238_();
         }

         this.f_129754_.m_6180_("tick");
         net.minecraftforge.fmllegacy.hooks.BasicEventHooks.onPreWorldTick(serverlevel);

         try {
            serverlevel.m_8793_(p_129954_);
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.m_127521_(throwable, "Exception ticking world");
            serverlevel.m_6026_(crashreport);
            throw new ReportedException(crashreport);
         }
         net.minecraftforge.fmllegacy.hooks.BasicEventHooks.onPostWorldTick(serverlevel);

         this.f_129754_.m_7238_();
         this.f_129754_.m_7238_();
         perWorldTickTimes.computeIfAbsent(serverlevel.m_46472_(), k -> new long[100])[this.f_129766_ % 100] = Util.m_137569_() - tickStart;
      }

      this.f_129754_.m_6182_("connection");
      this.m_129919_().m_9721_();
      this.f_129754_.m_6182_("players");
      this.f_129763_.m_11288_();
      if (SharedConstants.f_136183_) {
         GameTestTicker.f_177648_.m_127790_();
      }

      this.f_129754_.m_6182_("server gui refresh");

      for(int i = 0; i < this.f_129752_.size(); ++i) {
         this.f_129752_.get(i).run();
      }

      this.f_129754_.m_7238_();
   }

   public boolean m_7079_() {
      return true;
   }

   public void m_129946_(Runnable p_129947_) {
      this.f_129752_.add(p_129947_);
   }

   protected void m_129948_(String p_129949_) {
      this.f_129739_ = p_129949_;
   }

   public boolean m_129782_() {
      return !this.f_129725_.isAlive();
   }

   public File m_129971_(String p_129972_) {
      return new File(this.m_6237_(), p_129972_);
   }

   public final ServerLevel m_129783_() {
      return this.f_129762_.get(Level.f_46428_);
   }

   @Nullable
   public ServerLevel m_129880_(ResourceKey<Level> p_129881_) {
      return this.f_129762_.get(p_129881_);
   }

   public Set<ResourceKey<Level>> m_129784_() {
      return this.f_129762_.keySet();
   }

   public Iterable<ServerLevel> m_129785_() {
      return this.f_129762_.values();
   }

   public String m_7630_() {
      return SharedConstants.m_136187_().getName();
   }

   public int m_7416_() {
      return this.f_129763_.m_11309_();
   }

   public int m_7418_() {
      return this.f_129763_.m_11310_();
   }

   public String[] m_7641_() {
      return this.f_129763_.m_11291_();
   }

   @DontObfuscate
   public String m_130001_() {
      return net.minecraftforge.fmllegacy.BrandingControl.getServerBranding();
   }

   public SystemReport m_177935_(SystemReport p_177936_) {
      if (this.f_129763_ != null) {
         p_177936_.m_143522_("Player Count", () -> {
            return this.f_129763_.m_11309_() + " / " + this.f_129763_.m_11310_() + "; " + this.f_129763_.m_11314_();
         });
      }

      p_177936_.m_143522_("Data Packs", () -> {
         StringBuilder stringbuilder = new StringBuilder();

            LogManager.shutdown(); // we're manually managing the logging shutdown on the server. Make sure we do it here at the end.
         for(Pack pack : this.f_129730_.m_10524_()) {
            if (stringbuilder.length() > 0) {
               stringbuilder.append(", ");
            }

            stringbuilder.append(pack.m_10446_());
            if (!pack.m_10443_().m_10489_()) {
               stringbuilder.append(" (incompatible)");
            }
         }

         return stringbuilder.toString();
      });
      if (this.f_129739_ != null) {
         p_177936_.m_143522_("Server Id", () -> {
            return this.f_129739_;
         });
      }

      return this.m_142424_(p_177936_);
   }

   public abstract SystemReport m_142424_(SystemReport p_177901_);

   public abstract Optional<String> m_6730_();

   public void m_6352_(Component p_129876_, UUID p_129877_) {
      f_129750_.info(p_129876_.getString());
   }

   public KeyPair m_129790_() {
      return this.f_129712_;
   }

   public int m_7010_() {
      return this.f_129761_;
   }

   public void m_129801_(int p_129802_) {
      this.f_129761_ = p_129802_;
   }

   public String m_129791_() {
      return this.f_129713_;
   }

   public void m_129981_(String p_129982_) {
      this.f_129713_ = p_129982_;
   }

   public boolean m_129792_() {
      return this.f_129713_ != null;
   }

   protected void m_129793_() {
      f_129750_.info("Generating keypair");

      try {
         this.f_129712_ = Crypt.m_13604_();
      } catch (CryptException cryptexception) {
         throw new IllegalStateException("Failed to generate key pair", cryptexception);
      }
   }

   public void m_129827_(Difficulty p_129828_, boolean p_129829_) {
      if (p_129829_ || !this.f_129749_.m_5474_()) {
         this.f_129749_.m_6166_(this.f_129749_.m_5466_() ? Difficulty.HARD : p_129828_);
         this.m_129962_();
         this.m_6846_().m_11314_().forEach(this::m_129938_);
      }
   }

   public int m_7186_(int p_129935_) {
      return p_129935_;
   }

   private void m_129962_() {
      for(ServerLevel serverlevel : this.m_129785_()) {
         serverlevel.m_46703_(this.m_7004_(), this.m_6998_());
      }

   }

   public void m_129958_(boolean p_129959_) {
      this.f_129749_.m_5560_(p_129959_);
      this.m_6846_().m_11314_().forEach(this::m_129938_);
   }

   private void m_129938_(ServerPlayer p_129939_) {
      LevelData leveldata = p_129939_.m_9236_().m_6106_();
      p_129939_.f_8906_.m_141995_(new ClientboundChangeDifficultyPacket(leveldata.m_5472_(), leveldata.m_5474_()));
   }

   public boolean m_7004_() {
      return this.f_129749_.m_5472_() != Difficulty.PEACEFUL;
   }

   public boolean m_129794_() {
      return this.f_129714_;
   }

   public void m_129975_(boolean p_129976_) {
      this.f_129714_ = p_129976_;
   }

   public String m_129795_() {
      return this.f_129715_;
   }

   public String m_129796_() {
      return this.f_129716_;
   }

   public void m_129853_(String p_129854_, String p_129855_) {
      this.f_129715_ = p_129854_;
      this.f_129716_ = p_129855_;
   }

   public void m_7108_(Snooper p_129831_) {
      p_129831_.m_19223_("whitelist_enabled", false);
      p_129831_.m_19223_("whitelist_count", 0);
      if (this.f_129763_ != null) {
         p_129831_.m_19223_("players_current", this.m_7416_());
         p_129831_.m_19223_("players_max", this.m_7418_());
         p_129831_.m_19223_("players_seen", this.f_129745_.m_78432_().length);
      }

      p_129831_.m_19223_("uses_auth", this.f_129705_);
      p_129831_.m_19223_("gui_state", this.m_6370_() ? "enabled" : "disabled");
      p_129831_.m_19223_("run_time", (Util.m_137550_() - p_129831_.m_19233_()) / 60L * 1000L);
      p_129831_.m_19223_("avg_tick_ms", (int)(Mth.m_14078_(this.f_129748_) * 1.0E-6D));
      int i = 0;

      for(ServerLevel serverlevel : this.m_129785_()) {
         if (serverlevel != null) {
            p_129831_.m_19223_("world[" + i + "][dimension]", serverlevel.m_46472_().m_135782_());
            p_129831_.m_19223_("world[" + i + "][mode]", this.f_129749_.m_5464_());
            p_129831_.m_19223_("world[" + i + "][difficulty]", serverlevel.m_46791_());
            p_129831_.m_19223_("world[" + i + "][hardcore]", this.f_129749_.m_5466_());
            p_129831_.m_19223_("world[" + i + "][height]", serverlevel.m_151558_());
            p_129831_.m_19223_("world[" + i + "][chunks_loaded]", serverlevel.m_7726_().m_142061_());
            ++i;
         }
      }

      p_129831_.m_19223_("worlds", i);
   }

   public void m_142423_(Snooper p_177938_) {
      p_177938_.m_19227_("singleplayer", this.m_129792_());
      p_177938_.m_19227_("server_brand", this.m_130001_());
      p_177938_.m_19227_("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
      p_177938_.m_19227_("dedicated", this.m_6982_());
   }

   public boolean m_142725_() {
      return true;
   }

   public abstract boolean m_6982_();

   public abstract int m_7032_();

   public boolean m_129797_() {
      return this.f_129705_;
   }

   public void m_129985_(boolean p_129986_) {
      this.f_129705_ = p_129986_;
   }

   public boolean m_129798_() {
      return this.f_129706_;
   }

   public void m_129993_(boolean p_129994_) {
      this.f_129706_ = p_129994_;
   }

   public boolean m_6998_() {
      return true;
   }

   public boolean m_6997_() {
      return true;
   }

   public abstract boolean m_6994_();

   public boolean m_129799_() {
      return this.f_129707_;
   }

   public void m_129997_(boolean p_129998_) {
      this.f_129707_ = p_129998_;
   }

   public boolean m_129915_() {
      return this.f_129708_;
   }

   public void m_129999_(boolean p_130000_) {
      this.f_129708_ = p_130000_;
   }

   public abstract boolean m_6993_();

   public String m_129916_() {
      return this.f_129709_;
   }

   public void m_129989_(String p_129990_) {
      this.f_129709_ = p_129990_;
   }

   public boolean m_129918_() {
      return this.f_129765_;
   }

   public PlayerList m_6846_() {
      return this.f_129763_;
   }

   public void m_129823_(PlayerList p_129824_) {
      this.f_129763_ = p_129824_;
   }

   public abstract boolean m_6992_();

   public void m_7835_(GameType p_129832_) {
      this.f_129749_.m_5458_(p_129832_);
   }

   @Nullable
   public ServerConnectionListener m_129919_() {
      return this.f_129755_;
   }

   public boolean m_129920_() {
      return this.f_129717_;
   }

   public boolean m_6370_() {
      return false;
   }

   public boolean m_7386_(@Nullable GameType p_129833_, boolean p_129834_, int p_129835_) {
      return false;
   }

   public int m_129921_() {
      return this.f_129766_;
   }

   public Snooper m_129922_() {
      return this.f_129751_;
   }

   public int m_6396_() {
      return 16;
   }

   public boolean m_7762_(ServerLevel p_129811_, BlockPos p_129812_, Player p_129813_) {
      return false;
   }

   public boolean m_6373_() {
      return true;
   }

   public Proxy m_177930_() {
      return this.f_129747_;
   }

   public int m_129924_() {
      return this.f_129711_;
   }

   public void m_7196_(int p_129978_) {
      this.f_129711_ = p_129978_;
   }

   public MinecraftSessionService m_129925_() {
      return this.f_129721_;
   }

   public GameProfileRepository m_129926_() {
      return this.f_129722_;
   }

   public GameProfileCache m_129927_() {
      return this.f_129723_;
   }

   public ServerStatus m_129928_() {
      return this.f_129757_;
   }

   public void m_129929_() {
      this.f_129724_ = 0L;
   }

   public int m_6329_() {
      return 29999984;
   }

   public boolean m_5660_() {
      return super.m_5660_() && !this.m_129918_();
   }

   public Thread m_6304_() {
      return this.f_129725_;
   }

   public int m_6328_() {
      return 256;
   }

   public long m_129932_() {
      return this.f_129726_;
   }

   public DataFixer m_129933_() {
      return this.f_129759_;
   }

   public int m_129803_(@Nullable ServerLevel p_129804_) {
      return p_129804_ != null ? p_129804_.m_46469_().m_46215_(GameRules.f_46147_) : 10;
   }

   public ServerAdvancementManager m_129889_() {
      return this.f_129740_.m_136177_();
   }

   public ServerFunctionManager m_129890_() {
      return this.f_129734_;
   }

   public CompletableFuture<Void> m_129861_(Collection<String> p_129862_) {
      CompletableFuture<Void> completablefuture = CompletableFuture.supplyAsync(() -> {
         return p_129862_.stream().map(this.f_129730_::m_10507_).filter(Objects::nonNull).map(Pack::m_10445_).collect(ImmutableList.toImmutableList());
      }, this).thenCompose((p_177907_) -> {
         return ServerResources.m_180005_(p_177907_, this.f_129746_, this.m_6982_() ? Commands.CommandSelection.DEDICATED : Commands.CommandSelection.INTEGRATED, this.m_7034_(), this.f_129738_, this);
      }).thenAcceptAsync((p_177917_) -> {
         this.f_129740_.close();
         this.f_129740_ = p_177917_;
         this.f_129730_.m_10509_(p_129862_);
         this.f_129749_.m_6645_(m_129817_(this.f_129730_));
         p_177917_.m_136179_();
         this.m_6846_().m_11302_();
         this.m_6846_().m_11315_();
         this.f_129734_.m_136120_(this.f_129740_.m_136157_());
         this.f_129741_.m_74335_(this.f_129740_.m_136178_());
         this.m_6846_().m_11314_().forEach(this.m_6846_()::m_11289_); //Forge: Fix newly added/modified commands not being sent to the client when commands reload.
      }, this);
      if (this.m_18695_()) {
         this.m_18701_(completablefuture::isDone);
      }

      return completablefuture;
   }

   public static DataPackConfig m_129819_(PackRepository p_129820_, DataPackConfig p_129821_, boolean p_129822_) {
      net.minecraftforge.fmllegacy.packs.ResourcePackLoader.loadResourcePacks(p_129820_, net.minecraftforge.fmllegacy.server.ServerLifecycleHooks::buildPackFinderNew);
      p_129820_.m_10506_();
      DataPackConfig.f_45842_.addModPacks(net.minecraftforge.common.ForgeHooks.getModPacks());
      p_129821_.addModPacks(net.minecraftforge.common.ForgeHooks.getModPacks());
      if (p_129822_) {
         p_129820_.m_10509_(net.minecraftforge.common.ForgeHooks.getModPacksWithVanilla());
         return new DataPackConfig(net.minecraftforge.common.ForgeHooks.getModPacksWithVanilla(), ImmutableList.of());
      } else {
         Set<String> set = Sets.newLinkedHashSet();

         for(String s : p_129821_.m_45850_()) {
            if (p_129820_.m_10515_(s)) {
               set.add(s);
            } else {
               f_129750_.warn("Missing data pack {}", (Object)s);
            }
         }

         for(Pack pack : p_129820_.m_10519_()) {
            String s1 = pack.m_10446_();
            if (!p_129821_.m_45855_().contains(s1) && !set.contains(s1)) {
               f_129750_.info("Found new data pack {}, loading it automatically", (Object)s1);
               set.add(s1);
            }
         }

         if (set.isEmpty()) {
            f_129750_.info("No datapacks selected, forcing vanilla");
            set.add("vanilla");
         }

         p_129820_.m_10509_(set);
         return m_129817_(p_129820_);
      }
   }

   private static DataPackConfig m_129817_(PackRepository p_129818_) {
      Collection<String> collection = p_129818_.m_10523_();
      List<String> list = ImmutableList.copyOf(collection);
      List<String> list1 = p_129818_.m_10514_().stream().filter((p_177914_) -> {
         return !collection.contains(p_177914_);
      }).collect(ImmutableList.toImmutableList());
      return new DataPackConfig(list, list1);
   }

   public void m_129849_(CommandSourceStack p_129850_) {
      if (this.m_129902_()) {
         PlayerList playerlist = p_129850_.m_81377_().m_6846_();
         UserWhiteList userwhitelist = playerlist.m_11305_();

         for(ServerPlayer serverplayer : Lists.newArrayList(playerlist.m_11314_())) {
            if (!userwhitelist.m_11453_(serverplayer.m_36316_())) {
               serverplayer.f_8906_.m_9942_(new TranslatableComponent("multiplayer.disconnect.not_whitelisted"));
            }
         }

      }
   }

   public PackRepository m_129891_() {
      return this.f_129730_;
   }

   public Commands m_129892_() {
      return this.f_129740_.m_136176_();
   }

   public CommandSourceStack m_129893_() {
      ServerLevel serverlevel = this.m_129783_();
      return new CommandSourceStack(this, serverlevel == null ? Vec3.f_82478_ : Vec3.m_82528_(serverlevel.m_8900_()), Vec2.f_82462_, serverlevel, 4, "Server", new TextComponent("Server"), this, (Entity)null);
   }

   public boolean m_6999_() {
      return true;
   }

   public boolean m_7028_() {
      return true;
   }

   public abstract boolean m_6102_();

   public RecipeManager m_129894_() {
      return this.f_129740_.m_136175_();
   }

   public TagContainer m_129895_() {
      return this.f_129740_.m_136174_();
   }

   public ServerScoreboard m_129896_() {
      return this.f_129731_;
   }

   public CommandStorage m_129897_() {
      if (this.f_129732_ == null) {
         throw new NullPointerException("Called before server init");
      } else {
         return this.f_129732_;
      }
   }

   public LootTables m_129898_() {
      return this.f_129740_.m_136172_();
   }

   public PredicateManager m_129899_() {
      return this.f_129740_.m_136171_();
   }

   public ItemModifierManager m_177926_() {
      return this.f_129740_.m_180012_();
   }

   public GameRules m_129900_() {
      return this.m_129783_().m_46469_();
   }

   public CustomBossEvents m_129901_() {
      return this.f_129733_;
   }

   public boolean m_129902_() {
      return this.f_129736_;
   }

   public void m_130004_(boolean p_130005_) {
      this.f_129736_ = p_130005_;
   }

   public float m_129903_() {
      return this.f_129737_;
   }

   public int m_129944_(GameProfile p_129945_) {
      if (this.m_6846_().m_11303_(p_129945_)) {
         ServerOpListEntry serveroplistentry = this.m_6846_().m_11307_().m_11388_(p_129945_);
         if (serveroplistentry != null) {
            return serveroplistentry.m_11363_();
         } else if (this.m_7779_(p_129945_)) {
            return 4;
         } else if (this.m_129792_()) {
            return this.m_6846_().m_11316_() ? 4 : 0;
         } else {
            return this.m_7022_();
         }
      } else {
         return 0;
      }
   }

   public FrameTimer m_129904_() {
      return this.f_129735_;
   }

   public ProfilerFiller m_129905_() {
      return this.f_129754_;
   }

   public abstract boolean m_7779_(GameProfile p_129840_);

   private Map<ResourceKey<Level>, long[]> perWorldTickTimes = Maps.newIdentityHashMap();
   @Nullable
   public long[] getTickTime(ResourceKey<Level> dim) {
      return perWorldTickTimes.get(dim);
   }

   @Deprecated //Forge Internal use Only, You can screw up a lot of things if you mess with this map.
   public synchronized Map<ResourceKey<Level>, ServerLevel> forgeGetWorldMap() {
      return this.f_129762_;
   }
   private int worldArrayMarker = 0;
   private int worldArrayLast = -1;
   private ServerLevel[] worldArray;
   @Deprecated //Forge Internal use Only, use to protect against concurrent modifications in the world tick loop.
   public synchronized void markWorldsDirty() {
      worldArrayMarker++;
   }
   private ServerLevel[] getWorldArray() {
      if (worldArrayMarker == worldArrayLast && worldArray != null)
         return worldArray;
      worldArray = this.f_129762_.values().stream().toArray(x -> new ServerLevel[x]);
      worldArrayLast = worldArrayMarker;
      return worldArray;
   }

   public void m_142116_(Path p_177911_) throws IOException {
   }

   private void m_129859_(Path p_129860_) {
      Path path = p_129860_.resolve("levels");

      try {
         for(Entry<ResourceKey<Level>, ServerLevel> entry : this.f_129762_.entrySet()) {
            ResourceLocation resourcelocation = entry.getKey().m_135782_();
            Path path1 = path.resolve(resourcelocation.m_135827_()).resolve(resourcelocation.m_135815_());
            Files.createDirectories(path1);
            entry.getValue().m_8786_(path1);
         }

         this.m_129983_(p_129860_.resolve("gamerules.txt"));
         this.m_129991_(p_129860_.resolve("classpath.txt"));
         this.m_129950_(p_129860_.resolve("stats.txt"));
         this.m_129995_(p_129860_.resolve("threads.txt"));
         this.m_142116_(p_129860_.resolve("server.properties.txt"));
      } catch (IOException ioexception) {
         f_129750_.warn("Failed to save debug report", (Throwable)ioexception);
      }

   }

   private void m_129950_(Path p_129951_) throws IOException {
      Writer writer = Files.newBufferedWriter(p_129951_);

      try {
         writer.write(String.format("pending_tasks: %d\n", this.m_18696_()));
         writer.write(String.format("average_tick_time: %f\n", this.m_129903_()));
         writer.write(String.format("tick_times: %s\n", Arrays.toString(this.f_129748_)));
         writer.write(String.format("queue: %s\n", Util.m_137578_()));
      } catch (Throwable throwable1) {
         if (writer != null) {
            try {
               writer.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (writer != null) {
         writer.close();
      }

   }

   private void m_129983_(Path p_129984_) throws IOException {
      Writer writer = Files.newBufferedWriter(p_129984_);

      try {
         final List<String> list = Lists.newArrayList();
         final GameRules gamerules = this.m_129900_();
         GameRules.m_46164_(new GameRules.GameRuleTypeVisitor() {
            public <T extends GameRules.Value<T>> void m_6889_(GameRules.Key<T> p_130034_, GameRules.Type<T> p_130035_) {
               list.add(String.format("%s=%s\n", p_130034_.m_46328_(), gamerules.<T>m_46170_(p_130034_)));
            }
         });

         for(String s : list) {
            writer.write(s);
         }
      } catch (Throwable throwable1) {
         if (writer != null) {
            try {
               writer.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (writer != null) {
         writer.close();
      }

   }

   private void m_129991_(Path p_129992_) throws IOException {
      Writer writer = Files.newBufferedWriter(p_129992_);

      try {
         String s = System.getProperty("java.class.path");
         String s1 = System.getProperty("path.separator");

         for(String s2 : Splitter.on(s1).split(s)) {
            writer.write(s2);
            writer.write("\n");
         }
      } catch (Throwable throwable1) {
         if (writer != null) {
            try {
               writer.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (writer != null) {
         writer.close();
      }

   }

   private void m_129995_(Path p_129996_) throws IOException {
      ThreadMXBean threadmxbean = ManagementFactory.getThreadMXBean();
      ThreadInfo[] athreadinfo = threadmxbean.dumpAllThreads(true, true);
      Arrays.sort(athreadinfo, Comparator.comparing(ThreadInfo::getThreadName));
      Writer writer = Files.newBufferedWriter(p_129996_);

      try {
         for(ThreadInfo threadinfo : athreadinfo) {
            writer.write(threadinfo.toString());
            writer.write(10);
         }
      } catch (Throwable throwable1) {
         if (writer != null) {
            try {
               writer.close();
            } catch (Throwable throwable) {
               throwable1.addSuppressed(throwable);
            }
         }

         throw throwable1;
      }

      if (writer != null) {
         writer.close();
      }

   }

   private void m_177945_() {
      if (this.f_177875_) {
         this.f_177872_ = ActiveMetricsRecorder.m_146132_(new ServerMetricsSamplersProvider(Util.f_137440_, this.m_6982_()), Util.f_137440_, Util.m_137579_(), new MetricsPersister("server"), this.f_177873_, (p_177952_) -> {
            this.m_18709_(() -> {
               this.m_129859_(p_177952_.resolve("server"));
            });
            this.f_177874_.accept(p_177952_);
         });
         this.f_177875_ = false;
      }

      this.f_129754_ = SingleTickProfiler.m_18629_(this.f_177872_.m_142610_(), SingleTickProfiler.m_18632_("Server"));
      this.f_177872_.m_142759_();
      this.f_129754_.m_7242_();
   }

   private void m_177946_() {
      this.f_129754_.m_7241_();
      this.f_177872_.m_142758_();
   }

   public boolean m_177927_() {
      return this.f_177872_.m_142763_();
   }

   public void m_177923_(Consumer<ProfileResults> p_177924_, Consumer<Path> p_177925_) {
      this.f_177873_ = (p_177922_) -> {
         this.m_177928_();
         p_177924_.accept(p_177922_);
      };
      this.f_177874_ = p_177925_;
      this.f_177875_ = true;
   }

   public void m_177928_() {
      this.f_177872_ = InactiveMetricsRecorder.f_146153_;
   }

   public void m_177929_() {
      this.f_177872_.m_142760_();
   }

   public Path m_129843_(LevelResource p_129844_) {
      return this.f_129744_.m_78283_(p_129844_);
   }

   public boolean m_6365_() {
      return true;
   }

   public StructureManager m_129909_() {
      return this.f_129741_;
   }

   public WorldData m_129910_() {
      return this.f_129749_;
   }

   public ServerResources getServerResources() {
       return f_129740_;
   }

   public RegistryAccess m_129911_() {
      return this.f_129746_;
   }

   public TextFilter m_7950_(ServerPlayer p_129814_) {
      return TextFilter.f_143703_;
   }

   public boolean m_142205_() {
      return false;
   }

   public ServerPlayerGameMode m_177933_(ServerPlayer p_177934_) {
      return (ServerPlayerGameMode)(this.m_129794_() ? new DemoMode(p_177934_) : new ServerPlayerGameMode(p_177934_));
   }

   @Nullable
   public GameType m_142359_() {
      return null;
   }

   public ResourceManager m_177941_() {
      return this.f_129740_.m_136178_();
   }

   @Nullable
   public Component m_141958_() {
      return null;
   }

   public boolean m_177942_() {
      return this.f_177877_ || this.f_177876_ != null;
   }

   public void m_177943_() {
      this.f_177877_ = true;
   }

   public ProfileResults m_177944_() {
      if (this.f_177876_ == null) {
         return EmptyProfileResults.f_18441_;
      } else {
         ProfileResults profileresults = this.f_177876_.m_177960_(Util.m_137569_(), this.f_129766_);
         this.f_177876_ = null;
         return profileresults;
      }
   }

   static class TimeProfiler {
      final long f_177955_;
      final int f_177956_;

      TimeProfiler(long p_177958_, int p_177959_) {
         this.f_177955_ = p_177958_;
         this.f_177956_ = p_177959_;
      }

      ProfileResults m_177960_(final long p_177961_, final int p_177962_) {
         return new ProfileResults() {
            public List<ResultField> m_6412_(String p_177972_) {
               return Collections.emptyList();
            }

            public boolean m_142444_(Path p_177974_) {
               return false;
            }

            public long m_7229_() {
               return TimeProfiler.this.f_177955_;
            }

            public int m_7230_() {
               return TimeProfiler.this.f_177956_;
            }

            public long m_7236_() {
               return p_177961_;
            }

            public int m_7317_() {
               return p_177962_;
            }

            public String m_142368_() {
               return "";
            }
         };
      }
   }
}
