package net.minecraft.server;

import com.google.common.collect.ImmutableSet;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Lifecycle;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.net.Proxy;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BooleanSupplier;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.CrashReport;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.SharedConstants;
import net.minecraft.Util;
import net.minecraft.commands.Commands;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.obfuscate.DontObfuscate;
import net.minecraft.resources.RegistryReadOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.DedicatedServerProperties;
import net.minecraft.server.dedicated.DedicatedServerSettings;
import net.minecraft.server.level.progress.LoggerChunkProgressListener;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.ServerPacksSource;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.util.Mth;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.worldupdate.WorldUpgrader;
import net.minecraft.world.level.DataPackConfig;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.LevelSummary;
import net.minecraft.world.level.storage.PrimaryLevelData;
import net.minecraft.world.level.storage.WorldData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
   private static final Logger f_129670_ = LogManager.getLogger();

   @DontObfuscate
   public static void main(String[] p_129699_) {
      SharedConstants.m_142977_();
      OptionParser optionparser = new OptionParser();
      OptionSpec<Void> optionspec = optionparser.accepts("nogui");
      OptionSpec<Void> optionspec1 = optionparser.accepts("initSettings", "Initializes 'server.properties' and 'eula.txt', then quits");
      OptionSpec<Void> optionspec2 = optionparser.accepts("demo");
      OptionSpec<Void> optionspec3 = optionparser.accepts("bonusChest");
      OptionSpec<Void> optionspec4 = optionparser.accepts("forceUpgrade");
      OptionSpec<Void> optionspec5 = optionparser.accepts("eraseCache");
      OptionSpec<Void> optionspec6 = optionparser.accepts("safeMode", "Loads level with vanilla datapack only");
      OptionSpec<Void> optionspec7 = optionparser.accepts("help").forHelp();
      OptionSpec<String> optionspec8 = optionparser.accepts("singleplayer").withRequiredArg();
      OptionSpec<String> optionspec9 = optionparser.accepts("universe").withRequiredArg().defaultsTo(".");
      OptionSpec<String> optionspec10 = optionparser.accepts("world").withRequiredArg();
      OptionSpec<Integer> optionspec11 = optionparser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(-1);
      OptionSpec<String> optionspec12 = optionparser.accepts("serverId").withRequiredArg();
      OptionSpec<String> optionspec13 = optionparser.nonOptions();
      optionparser.accepts("allowUpdates").withRequiredArg().ofType(Boolean.class).defaultsTo(Boolean.TRUE); // Forge: allow mod updates to proceed
      optionparser.accepts("gameDir").withRequiredArg().ofType(File.class).defaultsTo(new File(".")); //Forge: Consume this argument, we use it in the launcher, and the client side.

      try {
         OptionSet optionset = optionparser.parse(p_129699_);
         if (optionset.has(optionspec7)) {
            optionparser.printHelpOn(System.err);
            return;
         }
         Path path1 = Paths.get("eula.txt");
         Eula eula = new Eula(path1);

         if (!eula.m_135944_()) {
            f_129670_.info("You need to agree to the EULA in order to run the server. Go to eula.txt for more info.");
            return;
         }

         CrashReport.m_127529_();
         Bootstrap.m_135870_();
         Bootstrap.m_135889_();
         Util.m_137584_();
         if (!optionset.has(optionspec1)) net.minecraftforge.fmllegacy.server.ServerModLoader.load(); // Load mods before we load almost anything else anymore. Single spot now. Only loads if they haven't passed the initserver param
         RegistryAccess.RegistryHolder registryaccess$registryholder = RegistryAccess.m_123086_();
         Path path = Paths.get("server.properties");
         DedicatedServerSettings dedicatedserversettings = new DedicatedServerSettings(path);
         dedicatedserversettings.m_139780_();
         if (optionset.has(optionspec1)) {
            f_129670_.info("Initialized '{}' and '{}'", path.toAbsolutePath(), path1.toAbsolutePath());
            return;
         }

         File file1 = new File(optionset.valueOf(optionspec9));
         YggdrasilAuthenticationService yggdrasilauthenticationservice = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
         MinecraftSessionService minecraftsessionservice = yggdrasilauthenticationservice.createMinecraftSessionService();
         GameProfileRepository gameprofilerepository = yggdrasilauthenticationservice.createProfileRepository();
         GameProfileCache gameprofilecache = new GameProfileCache(gameprofilerepository, new File(file1, MinecraftServer.f_129742_.getName()));
         String s = Optional.ofNullable(optionset.valueOf(optionspec10)).orElse(dedicatedserversettings.m_139777_().f_139741_);
         if (s == null || s.isEmpty() || new File(file1, s).getAbsolutePath().equals(new File(s).getAbsolutePath())) {
            f_129670_.error("Invalid world directory specified, must not be null, empty or the same directory as your universe! " + s);
            return;
         }
         LevelStorageSource levelstoragesource = LevelStorageSource.m_78242_(file1.toPath());
         LevelStorageSource.LevelStorageAccess levelstoragesource$levelstorageaccess = levelstoragesource.m_78260_(s);
         MinecraftServer.m_129845_(levelstoragesource$levelstorageaccess);
         LevelSummary levelsummary = levelstoragesource$levelstorageaccess.m_78308_();
         if (levelsummary != null && levelsummary.m_164915_()) {
            f_129670_.info("Loading of worlds with extended height is disabled.");
            return;
         }

         DataPackConfig datapackconfig = levelstoragesource$levelstorageaccess.m_78309_();
         boolean flag = optionset.has(optionspec6);
         if (flag) {
            f_129670_.warn("Safe mode active, only vanilla datapack will be loaded");
         }

         PackRepository packrepository = new PackRepository(PackType.SERVER_DATA, new ServerPacksSource(), new FolderRepositorySource(levelstoragesource$levelstorageaccess.m_78283_(LevelResource.f_78180_).toFile(), PackSource.f_10529_));
         DataPackConfig datapackconfig1 = MinecraftServer.m_129819_(packrepository, datapackconfig == null ? DataPackConfig.f_45842_ : datapackconfig, flag);
         CompletableFuture<ServerResources> completablefuture = ServerResources.m_180005_(packrepository.m_10525_(), registryaccess$registryholder, Commands.CommandSelection.DEDICATED, dedicatedserversettings.m_139777_().f_139711_, Util.m_137578_(), Runnable::run);

         ServerResources serverresources;
         try {
            serverresources = completablefuture.get();
         } catch (Exception exception) {
            f_129670_.warn("Failed to load datapacks, can't proceed with server load. You can either fix your datapacks or reset to vanilla with --safeMode", (Throwable)exception);
            packrepository.close();
            return;
         }

         serverresources.m_136179_();
         RegistryReadOps<Tag> registryreadops = RegistryReadOps.m_179866_(NbtOps.f_128958_, serverresources.m_136178_(), registryaccess$registryholder);
         dedicatedserversettings.m_139777_().m_180927_(registryaccess$registryholder);
         WorldData worlddata = levelstoragesource$levelstorageaccess.m_78280_(registryreadops, datapackconfig1);
         if (worlddata == null) {
            LevelSettings levelsettings;
            WorldGenSettings worldgensettings;
            if (optionset.has(optionspec2)) {
               levelsettings = MinecraftServer.f_129743_;
               worldgensettings = WorldGenSettings.m_64645_(registryaccess$registryholder);
            } else {
               DedicatedServerProperties dedicatedserverproperties = dedicatedserversettings.m_139777_();
               levelsettings = new LevelSettings(dedicatedserverproperties.f_139741_, dedicatedserverproperties.f_139740_, dedicatedserverproperties.f_139752_, dedicatedserverproperties.f_139739_, false, new GameRules(), datapackconfig1);
               worldgensettings = optionset.has(optionspec3) ? dedicatedserverproperties.m_180927_(registryaccess$registryholder).m_64671_() : dedicatedserverproperties.m_180927_(registryaccess$registryholder);
            }

            // Forge: Deserialize the DimensionGeneratorSettings to ensure modded dims are loaded on first server load (see SimpleRegistryCodec#decode). Vanilla behaviour only loads from the server.properties and deserializes only after the 2nd server load.
            worldgensettings = WorldGenSettings.f_64600_.encodeStart(net.minecraft.resources.RegistryWriteOps.m_135767_(NbtOps.f_128958_, registryaccess$registryholder), worldgensettings).flatMap(nbt -> WorldGenSettings.f_64600_.parse(registryreadops, nbt)).getOrThrow(false, errorMsg->{});
            worlddata = new PrimaryLevelData(levelsettings, worldgensettings, Lifecycle.stable());
         }

         if (optionset.has(optionspec4)) {
            m_129674_(levelstoragesource$levelstorageaccess, DataFixers.m_14512_(), optionset.has(optionspec5), () -> {
               return true;
            }, worlddata.m_5961_().m_64667_());
         }

         levelstoragesource$levelstorageaccess.m_78287_(registryaccess$registryholder, worlddata);
         WorldData worlddata1 = worlddata;
         final DedicatedServer dedicatedserver = MinecraftServer.m_129872_((p_129697_) -> {
            DedicatedServer dedicatedserver1 = new DedicatedServer(p_129697_, registryaccess$registryholder, levelstoragesource$levelstorageaccess, packrepository, serverresources, worlddata1, dedicatedserversettings, DataFixers.m_14512_(), minecraftsessionservice, gameprofilerepository, gameprofilecache, LoggerChunkProgressListener::new);
            dedicatedserver1.m_129981_(optionset.valueOf(optionspec8));
            dedicatedserver1.m_129801_(optionset.valueOf(optionspec11));
            dedicatedserver1.m_129975_(optionset.has(optionspec2));
            dedicatedserver1.m_129948_(optionset.valueOf(optionspec12));
            boolean flag1 = !optionset.has(optionspec) && !optionset.valuesOf(optionspec13).contains("nogui");
            if (flag1 && !GraphicsEnvironment.isHeadless()) {
               dedicatedserver1.m_139667_();
            }

            return dedicatedserver1;
         });
         Thread thread = new Thread("Server Shutdown Thread") {
            public void run() {
               dedicatedserver.m_7570_(true);
               LogManager.shutdown(); // we're manually managing the logging shutdown on the server. Make sure we do it here at the end.
            }
         };
         thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_129670_));
         Runtime.getRuntime().addShutdownHook(thread);
      } catch (Exception exception1) {
         f_129670_.fatal("Failed to start the minecraft server", (Throwable)exception1);
      }

   }

   private static void m_129674_(LevelStorageSource.LevelStorageAccess p_129675_, DataFixer p_129676_, boolean p_129677_, BooleanSupplier p_129678_, ImmutableSet<ResourceKey<Level>> p_129679_) {
      f_129670_.info("Forcing world upgrade!");
      WorldUpgrader worldupgrader = new WorldUpgrader(p_129675_, p_129676_, p_129679_, p_129677_);
      Component component = null;

      while(!worldupgrader.m_18829_()) {
         Component component1 = worldupgrader.m_18837_();
         if (component != component1) {
            component = component1;
            f_129670_.info(worldupgrader.m_18837_().getString());
         }

         int i = worldupgrader.m_18834_();
         if (i > 0) {
            int j = worldupgrader.m_18835_() + worldupgrader.m_18836_();
            f_129670_.info("{}% completed ({} / {} chunks)...", Mth.m_14143_((float)j / (float)i * 100.0F), j, i);
         }

         if (!p_129678_.getAsBoolean()) {
            worldupgrader.m_18820_();
         } else {
            try {
               Thread.sleep(1000L);
            } catch (InterruptedException interruptedexception) {
            }
         }
      }

   }
}
