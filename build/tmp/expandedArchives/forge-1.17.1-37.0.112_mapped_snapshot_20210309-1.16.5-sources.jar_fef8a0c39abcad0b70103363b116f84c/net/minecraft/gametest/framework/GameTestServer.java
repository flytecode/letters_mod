package net.minecraft.gametest.framework;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.serialization.Lifecycle;
import java.net.Proxy;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.LoggerChunkProgressListener;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.PrimaryLevelData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GameTestServer extends MinecraftServer {
   private static final Logger f_177585_ = LogManager.getLogger();
   private static final int f_177586_ = 20;
   private final List<GameTestBatch> f_177587_;
   private final BlockPos f_177588_;
   private static final GameRules f_177589_ = Util.m_137469_(new GameRules(), (p_177615_) -> {
      p_177615_.m_46170_(GameRules.f_46134_).m_46246_(false, (MinecraftServer)null);
      p_177615_.m_46170_(GameRules.f_46150_).m_46246_(false, (MinecraftServer)null);
   });
   private static final LevelSettings f_177590_ = new LevelSettings("Test Level", GameType.CREATIVE, false, Difficulty.NORMAL, true, f_177589_, DataPackConfig.f_45842_);
   @Nullable
   private MultipleTestTracker f_177591_;

   public GameTestServer(Thread p_177594_, LevelStorageSource.LevelStorageAccess p_177595_, PackRepository p_177596_, ServerResources p_177597_, Collection<GameTestBatch> p_177598_, BlockPos p_177599_, RegistryAccess.RegistryHolder p_177600_) {
      this(p_177594_, p_177595_, p_177596_, p_177597_, p_177598_, p_177599_, p_177600_, p_177600_.m_175515_(Registry.f_122885_), p_177600_.m_175515_(Registry.f_122818_));
   }

   private GameTestServer(Thread p_177602_, LevelStorageSource.LevelStorageAccess p_177603_, PackRepository p_177604_, ServerResources p_177605_, Collection<GameTestBatch> p_177606_, BlockPos p_177607_, RegistryAccess.RegistryHolder p_177608_, Registry<Biome> p_177609_, Registry<DimensionType> p_177610_) {
      super(p_177602_, p_177608_, p_177603_, new PrimaryLevelData(f_177590_, new WorldGenSettings(0L, false, false, WorldGenSettings.m_64633_(p_177610_, DimensionType.m_63921_(p_177610_, p_177609_, p_177608_.m_175515_(Registry.f_122878_), 0L), new FlatLevelSource(FlatLevelGeneratorSettings.m_70376_(p_177609_)))), Lifecycle.stable()), p_177604_, Proxy.NO_PROXY, DataFixers.m_14512_(), p_177605_, (MinecraftSessionService)null, (GameProfileRepository)null, (GameProfileCache)null, LoggerChunkProgressListener::new);
      this.f_177587_ = Lists.newArrayList(p_177606_);
      this.f_177588_ = p_177607_;
      if (p_177606_.isEmpty()) {
         throw new IllegalArgumentException("No test batches were given!");
      }
   }

   public boolean m_7038_() {
      this.m_129823_(new PlayerList(this, this.f_129746_, this.f_129745_, 1) {
      });
      this.m_130006_();
      ServerLevel serverlevel = this.m_129783_();
      serverlevel.m_8733_(this.f_177588_, 0.0F);
      serverlevel.m_6106_().m_5565_(false);
      serverlevel.m_6106_().m_5565_(false);
      return true;
   }

   public void m_5705_(BooleanSupplier p_177619_) {
      super.m_5705_(p_177619_);
      ServerLevel serverlevel = this.m_129783_();
      if (!this.m_177628_()) {
         this.m_177624_(serverlevel);
      }

      if (serverlevel.m_46467_() % 20L == 0L) {
         f_177585_.info(this.f_177591_.m_127822_());
      }

      if (this.f_177591_.m_127821_()) {
         this.m_7570_(false);
         f_177585_.info(this.f_177591_.m_127822_());
         GlobalTestReporter.m_177652_();
         f_177585_.info("========= {} GAME TESTS COMPLETE ======================", (int)this.f_177591_.m_127820_());
         if (this.f_177591_.m_127818_()) {
            f_177585_.info("{} required tests failed :(", (int)this.f_177591_.m_127803_());
            this.f_177591_.m_177682_().forEach((p_177627_) -> {
               f_177585_.info("   - {}", (Object)p_177627_.m_127633_());
            });
         } else {
            f_177585_.info("All {} required tests passed :)", (int)this.f_177591_.m_127820_());
         }

         if (this.f_177591_.m_127819_()) {
            f_177585_.info("{} optional tests failed", (int)this.f_177591_.m_127816_());
            this.f_177591_.m_177683_().forEach((p_177621_) -> {
               f_177585_.info("   - {}", (Object)p_177621_.m_127633_());
            });
         }

         f_177585_.info("====================================================");
      }

   }

   public SystemReport m_142424_(SystemReport p_177613_) {
      p_177613_.m_143519_("Type", "Game test server");
      return p_177613_;
   }

   public void m_6988_() {
      super.m_6988_();
      System.exit(this.f_177591_.m_127803_());
   }

   public void m_7268_(CrashReport p_177623_) {
      System.exit(1);
   }

   private void m_177624_(ServerLevel p_177625_) {
      Collection<GameTestInfo> collection = GameTestRunner.m_127726_(this.f_177587_, new BlockPos(0, 4, 0), Rotation.NONE, p_177625_, GameTestTicker.f_177648_, 8);
      this.f_177591_ = new MultipleTestTracker(collection);
      f_177585_.info("{} tests are now running!", (int)this.f_177591_.m_127820_());
   }

   private boolean m_177628_() {
      return this.f_177591_ != null;
   }

   public boolean m_7035_() {
      return false;
   }

   public int m_7022_() {
      return 0;
   }

   public int m_7034_() {
      return 4;
   }

   public boolean m_6983_() {
      return false;
   }

   public boolean m_6982_() {
      return false;
   }

   public int m_7032_() {
      return 0;
   }

   public boolean m_6994_() {
      return false;
   }

   public boolean m_6993_() {
      return true;
   }

   public boolean m_6992_() {
      return false;
   }

   public boolean m_6102_() {
      return false;
   }

   public boolean m_7779_(GameProfile p_177617_) {
      return false;
   }

   public Optional<String> m_6730_() {
      return Optional.empty();
   }
}