package net.minecraft.server.dedicated;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.InetAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.DefaultUncaughtExceptionHandler;
import net.minecraft.DefaultUncaughtExceptionHandlerWithName;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.ConsoleInput;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerInterface;
import net.minecraft.server.ServerResources;
import net.minecraft.server.gui.MinecraftServerGui;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.network.TextFilter;
import net.minecraft.server.network.TextFilterClient;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.server.rcon.RconConsoleSource;
import net.minecraft.server.rcon.thread.QueryThreadGs4;
import net.minecraft.server.rcon.thread.RconThread;
import net.minecraft.util.Mth;
import net.minecraft.util.monitoring.jmx.MinecraftServerStatistics;
import net.minecraft.world.Snooper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedServer extends MinecraftServer implements ServerInterface {
   static final Logger f_139598_ = LogManager.getLogger();
   private static final int f_142864_ = 5000;
   private static final int f_142865_ = 2;
   private static final Pattern f_139599_ = Pattern.compile("^[a-fA-F0-9]{40}$");
   public final List<ConsoleInput> f_139600_ = Collections.synchronizedList(Lists.newArrayList());
   private QueryThreadGs4 f_139601_;
   private final RconConsoleSource f_139602_;
   private RconThread f_139603_;
   private final DedicatedServerSettings f_139604_;
   @Nullable
   private MinecraftServerGui f_139605_;
   @Nullable
   private final TextFilterClient f_139606_;
   @Nullable
   private final Component f_142863_;

   public DedicatedServer(Thread p_139609_, RegistryAccess.RegistryHolder p_139610_, LevelStorageSource.LevelStorageAccess p_139611_, PackRepository p_139612_, ServerResources p_139613_, WorldData p_139614_, DedicatedServerSettings p_139615_, DataFixer p_139616_, MinecraftSessionService p_139617_, GameProfileRepository p_139618_, GameProfileCache p_139619_, ChunkProgressListenerFactory p_139620_) {
      super(p_139609_, p_139610_, p_139611_, p_139614_, p_139612_, Proxy.NO_PROXY, p_139616_, p_139613_, p_139617_, p_139618_, p_139619_, p_139620_);
      this.f_139604_ = p_139615_;
      this.f_139602_ = new RconConsoleSource(this);
      this.f_139606_ = TextFilterClient.m_143736_(p_139615_.m_139777_().f_139724_);
      this.f_142863_ = m_142867_(p_139615_);
   }

   public boolean m_7038_() throws IOException {
      Thread thread = new Thread("Server console handler") {
         public void run() {
            if (net.minecraftforge.server.console.TerminalHandler.handleCommands(DedicatedServer.this)) return;
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            String s1;
            try {
               while(!DedicatedServer.this.m_129918_() && DedicatedServer.this.m_130010_() && (s1 = bufferedreader.readLine()) != null) {
                  DedicatedServer.this.m_139645_(s1, DedicatedServer.this.m_129893_());
               }
            } catch (IOException ioexception1) {
               DedicatedServer.f_139598_.error("Exception handling console input", (Throwable)ioexception1);
            }

         }
      };
      thread.setDaemon(true);
      thread.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(f_139598_));
      thread.start();
      f_139598_.info("Starting minecraft server version {}", (Object)SharedConstants.m_136187_().getName());
      if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
         f_139598_.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
      }

      f_139598_.info("Loading properties");
      DedicatedServerProperties dedicatedserverproperties = this.f_139604_.m_139777_();
      if (this.m_129792_()) {
         this.m_129913_("127.0.0.1");
      } else {
         this.m_129985_(dedicatedserverproperties.f_139728_);
         this.m_129993_(dedicatedserverproperties.f_139729_);
         this.m_129913_(dedicatedserverproperties.f_139730_);
      }

      this.m_129997_(dedicatedserverproperties.f_139733_);
      this.m_129999_(dedicatedserverproperties.f_139734_);
      this.m_129853_(dedicatedserverproperties.f_139735_, this.m_139664_());
      this.m_129989_(dedicatedserverproperties.f_139736_);
      super.m_7196_(dedicatedserverproperties.f_139725_.get());
      this.m_130004_(dedicatedserverproperties.f_139738_);
      this.f_129749_.m_5458_(dedicatedserverproperties.f_139740_);
      f_139598_.info("Default game type: {}", (Object)dedicatedserverproperties.f_139740_);
      InetAddress inetaddress = null;
      if (!this.m_130009_().isEmpty()) {
         inetaddress = InetAddress.getByName(this.m_130009_());
      }

      if (this.m_7010_() < 0) {
         this.m_129801_(dedicatedserverproperties.f_139742_);
      }

      this.m_129793_();
      f_139598_.info("Starting Minecraft server on {}:{}", this.m_130009_().isEmpty() ? "*" : this.m_130009_(), this.m_7010_());

      try {
         this.m_129919_().m_9711_(inetaddress, this.m_7010_());
      } catch (IOException ioexception) {
         f_139598_.warn("**** FAILED TO BIND TO PORT!");
         f_139598_.warn("The exception was: {}", (Object)ioexception.toString());
         f_139598_.warn("Perhaps a server is already running on that port?");
         return false;
      }

      if (!this.m_129797_()) {
         f_139598_.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
         f_139598_.warn("The server will make no attempt to authenticate usernames. Beware.");
         f_139598_.warn("While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose.");
         f_139598_.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
      }

      if (this.m_139668_()) {
         this.m_129927_().m_11006_();
      }

      if (!OldUsersConverter.m_11106_(this)) {
         return false;
      } else {
         this.m_129823_(new DedicatedPlayerList(this, this.f_129746_, this.f_129745_));
         long i = Util.m_137569_();
         SkullBlockEntity.m_59764_(this.m_129927_());
         SkullBlockEntity.m_59771_(this.m_129925_());
         SkullBlockEntity.m_182462_(this);
         GameProfileCache.m_11004_(this.m_129797_());
         if (!net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleServerAboutToStart(this)) return false;
         f_139598_.info("Preparing level \"{}\"", (Object)this.m_7123_());
         this.m_130006_();
         long j = Util.m_137569_() - i;
         String s = String.format(Locale.ROOT, "%.3fs", (double)j / 1.0E9D);
         f_139598_.info("Done ({})! For help, type \"help\"", (Object)s);
         this.f_129726_ = Util.m_137550_(); //Forge: Update server time to prevent watchdog/spaming during long load.
         if (dedicatedserverproperties.f_139744_ != null) {
            this.m_129900_().m_46170_(GameRules.f_46153_).m_46246_(dedicatedserverproperties.f_139744_, this);
         }

         if (dedicatedserverproperties.f_139745_) {
            f_139598_.info("Starting GS4 status listener");
            this.f_139601_ = QueryThreadGs4.m_11553_(this);
         }

         if (dedicatedserverproperties.f_139747_) {
            f_139598_.info("Starting remote control listener");
            this.f_139603_ = RconThread.m_11615_(this);
         }

         if (this.m_139669_() > 0L) {
            Thread thread1 = new Thread(new ServerWatchdog(this));
            thread1.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandlerWithName(f_139598_));
            thread1.setName("Server Watchdog");
            thread1.setDaemon(true);
            thread1.start();
         }

         Items.f_41852_.m_6787_(CreativeModeTab.f_40754_, NonNullList.m_122779_());
         // <3 you Grum for this, saves us ~30 patch files! --^
         if (dedicatedserverproperties.f_139721_) {
            MinecraftServerStatistics.m_18328_(this);
            f_139598_.info("JMX monitoring enabled");
         }

         return net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleServerStarting(this);
      }
   }

   public boolean m_6998_() {
      return this.m_7913_().f_139731_ && super.m_6998_();
   }

   public boolean m_7004_() {
      return this.f_139604_.m_139777_().f_139705_ && super.m_7004_();
   }

   public boolean m_6997_() {
      return this.f_139604_.m_139777_().f_139732_ && super.m_6997_();
   }

   public String m_139664_() {
      DedicatedServerProperties dedicatedserverproperties = this.f_139604_.m_139777_();
      String s;
      if (!dedicatedserverproperties.f_139751_.isEmpty()) {
         s = dedicatedserverproperties.f_139751_;
         if (!Strings.isNullOrEmpty(dedicatedserverproperties.f_139750_)) {
            f_139598_.warn("resource-pack-hash is deprecated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
         }
      } else if (!Strings.isNullOrEmpty(dedicatedserverproperties.f_139750_)) {
         f_139598_.warn("resource-pack-hash is deprecated. Please use resource-pack-sha1 instead.");
         s = dedicatedserverproperties.f_139750_;
      } else {
         s = "";
      }

      if (!s.isEmpty() && !f_139599_.matcher(s).matches()) {
         f_139598_.warn("Invalid sha1 for ressource-pack-sha1");
      }

      if (!dedicatedserverproperties.f_139735_.isEmpty() && s.isEmpty()) {
         f_139598_.warn("You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if you change the name of the pack.");
      }

      return s;
   }

   public DedicatedServerProperties m_7913_() {
      return this.f_139604_.m_139777_();
   }

   public void m_7044_() {
      this.m_129827_(this.m_7913_().f_139739_, true);
   }

   public boolean m_7035_() {
      return this.m_7913_().f_139752_;
   }

   public SystemReport m_142424_(SystemReport p_142870_) {
      p_142870_.m_143522_("Is Modded", () -> {
         return this.m_6730_().orElse("Unknown (can't tell)");
      });
      p_142870_.m_143522_("Type", () -> {
         return "Dedicated Server (map_server.txt)";
      });
      return p_142870_;
   }

   public void m_142116_(Path p_142872_) throws IOException {
      DedicatedServerProperties dedicatedserverproperties = this.m_7913_();
      Writer writer = Files.newBufferedWriter(p_142872_);

      try {
         writer.write(String.format("sync-chunk-writes=%s%n", dedicatedserverproperties.f_139720_));
         writer.write(String.format("gamemode=%s%n", dedicatedserverproperties.f_139740_));
         writer.write(String.format("spawn-monsters=%s%n", dedicatedserverproperties.f_139705_));
         writer.write(String.format("entity-broadcast-range-percentage=%d%n", dedicatedserverproperties.f_139723_));
         writer.write(String.format("max-world-size=%d%n", dedicatedserverproperties.f_139719_));
         writer.write(String.format("spawn-npcs=%s%n", dedicatedserverproperties.f_139732_));
         writer.write(String.format("view-distance=%d%n", dedicatedserverproperties.f_139714_));
         writer.write(String.format("spawn-animals=%s%n", dedicatedserverproperties.f_139731_));
         writer.write(String.format("generate-structures=%s%n", dedicatedserverproperties.m_180927_(this.f_129746_).m_64657_()));
         writer.write(String.format("use-native=%s%n", dedicatedserverproperties.f_139707_));
         writer.write(String.format("rate-limit=%d%n", dedicatedserverproperties.f_139713_));
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

   public Optional<String> m_6730_() {
      String s = this.m_130001_();
      return !"vanilla".equals(s) ? Optional.of("Definitely; Server brand changed to '" + s + "'") : Optional.empty();
   }

   public void m_6988_() {
      if (this.f_139606_ != null) {
         this.f_139606_.close();
      }

      if (this.f_139605_ != null) {
         this.f_139605_.m_139925_();
      }

      if (this.f_139603_ != null) {
         this.f_139603_.m_7530_();
      }

      if (this.f_139601_ != null) {
         this.f_139601_.m_7530_();
      }

   }

   public void m_5703_(BooleanSupplier p_139661_) {
      super.m_5703_(p_139661_);
      this.m_139665_();
   }

   public boolean m_7079_() {
      return this.m_7913_().f_139753_;
   }

   public void m_7108_(Snooper p_139636_) {
      p_139636_.m_19223_("whitelist_enabled", this.m_6846_().m_11311_());
      p_139636_.m_19223_("whitelist_count", this.m_6846_().m_11306_().length);
      super.m_7108_(p_139636_);
   }

   public boolean m_142725_() {
      return this.m_7913_().f_139706_;
   }

   public void m_139645_(String p_139646_, CommandSourceStack p_139647_) {
      this.f_139600_.add(new ConsoleInput(p_139646_, p_139647_));
   }

   public void m_139665_() {
      while(!this.f_139600_.isEmpty()) {
         ConsoleInput consoleinput = this.f_139600_.remove(0);
         this.m_129892_().m_82117_(consoleinput.f_135929_, consoleinput.f_135928_);
      }

   }

   public boolean m_6982_() {
      return true;
   }

   public int m_7032_() {
      return this.m_7913_().f_139713_;
   }

   public boolean m_6994_() {
      return this.m_7913_().f_139707_;
   }

   public DedicatedPlayerList m_6846_() {
      return (DedicatedPlayerList)super.m_6846_();
   }

   public boolean m_6992_() {
      return true;
   }

   public String m_6866_() {
      return this.m_130009_();
   }

   public int m_7448_() {
      return this.m_7010_();
   }

   public String m_6995_() {
      return this.m_129916_();
   }

   public void m_139667_() {
      if (this.f_139605_ == null) {
         this.f_139605_ = MinecraftServerGui.m_139921_(this);
      }

   }

   public boolean m_6370_() {
      return this.f_139605_ != null;
   }

   public boolean m_6993_() {
      return this.m_7913_().f_139708_;
   }

   public int m_6396_() {
      return this.m_7913_().f_139709_;
   }

   public boolean m_7762_(ServerLevel p_139630_, BlockPos p_139631_, Player p_139632_) {
      if (p_139630_.m_46472_() != Level.f_46428_) {
         return false;
      } else if (this.m_6846_().m_11307_().m_11390_()) {
         return false;
      } else if (this.m_6846_().m_11303_(p_139632_.m_36316_())) {
         return false;
      } else if (this.m_6396_() <= 0) {
         return false;
      } else {
         BlockPos blockpos = p_139630_.m_8900_();
         int i = Mth.m_14040_(p_139631_.m_123341_() - blockpos.m_123341_());
         int j = Mth.m_14040_(p_139631_.m_123343_() - blockpos.m_123343_());
         int k = Math.max(i, j);
         return k <= this.m_6396_();
      }
   }

   public boolean m_6373_() {
      return this.m_7913_().f_139722_;
   }

   public int m_7022_() {
      return this.m_7913_().f_139710_;
   }

   public int m_7034_() {
      return this.m_7913_().f_139711_;
   }

   public void m_7196_(int p_139676_) {
      super.m_7196_(p_139676_);
      this.f_139604_.m_139778_((p_139628_) -> {
         return p_139628_.f_139725_.m_139895_(this.m_129911_(), p_139676_);
      });
   }

   public boolean m_6983_() {
      return this.m_7913_().f_139717_;
   }

   public boolean m_6102_() {
      return this.m_7913_().f_139718_;
   }

   public int m_6329_() {
      return this.m_7913_().f_139719_;
   }

   public int m_6328_() {
      return this.m_7913_().f_139716_;
   }

   protected boolean m_139668_() {
      boolean flag = false;

      for(int i = 0; !flag && i <= 2; ++i) {
         if (i > 0) {
            f_139598_.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
            this.m_139671_();
         }

         flag = OldUsersConverter.m_11081_(this);
      }

      boolean flag1 = false;

      for(int j = 0; !flag1 && j <= 2; ++j) {
         if (j > 0) {
            f_139598_.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
            this.m_139671_();
         }

         flag1 = OldUsersConverter.m_11098_(this);
      }

      boolean flag2 = false;

      for(int k = 0; !flag2 && k <= 2; ++k) {
         if (k > 0) {
            f_139598_.warn("Encountered a problem while converting the op list, retrying in a few seconds");
            this.m_139671_();
         }

         flag2 = OldUsersConverter.m_11102_(this);
      }

      boolean flag3 = false;

      for(int l = 0; !flag3 && l <= 2; ++l) {
         if (l > 0) {
            f_139598_.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
            this.m_139671_();
         }

         flag3 = OldUsersConverter.m_11104_(this);
      }

      boolean flag4 = false;

      for(int i1 = 0; !flag4 && i1 <= 2; ++i1) {
         if (i1 > 0) {
            f_139598_.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
            this.m_139671_();
         }

         flag4 = OldUsersConverter.m_11090_(this);
      }

      return flag || flag1 || flag2 || flag3 || flag4;
   }

   private void m_139671_() {
      try {
         Thread.sleep(5000L);
      } catch (InterruptedException interruptedexception) {
      }
   }

   public long m_139669_() {
      return this.m_7913_().f_139712_;
   }

   public String m_7138_() {
      return "";
   }

   public String m_7261_(String p_139644_) {
      this.f_139602_.m_11512_();
      this.m_18709_(() -> {
         this.m_129892_().m_82117_(this.f_139602_.m_11514_(), p_139644_);
      });
      return this.f_139602_.m_11513_();
   }

   public void m_139688_(boolean p_139689_) {
      this.f_139604_.m_139778_((p_139650_) -> {
         return p_139650_.f_139726_.m_139895_(this.m_129911_(), p_139689_);
      });
   }

   public void m_7041_() {
      super.m_7041_();
      Util.m_137580_();
   }

   public boolean m_7779_(GameProfile p_139642_) {
      return false;
   }

   @Override //Forge: Enable formated text for colors in console.
   public void m_6352_(net.minecraft.network.chat.Component message, java.util.UUID p_108776_) {
      f_139598_.info(message.getString());
   }

   public int m_7186_(int p_139659_) {
      return this.m_7913_().f_139723_ * p_139659_ / 100;
   }

   public String m_7123_() {
      return this.f_129744_.m_78277_();
   }

   public boolean m_6365_() {
      return this.f_139604_.m_139777_().f_139720_;
   }

   public TextFilter m_7950_(ServerPlayer p_139634_) {
      return this.f_139606_ != null ? this.f_139606_.m_10134_(p_139634_.m_36316_()) : TextFilter.f_143703_;
   }

   public boolean m_142205_() {
      return this.f_139604_.m_139777_().f_142878_;
   }

   @Nullable
   public GameType m_142359_() {
      return this.f_139604_.m_139777_().f_139737_ ? this.f_129749_.m_5464_() : null;
   }

   @Nullable
   private static Component m_142867_(DedicatedServerSettings p_142868_) {
      String s = p_142868_.m_139777_().f_142879_;
      if (!Strings.isNullOrEmpty(s)) {
         try {
            return Component.Serializer.m_130701_(s);
         } catch (Exception exception) {
            f_139598_.warn("Failed to parse resource pack prompt '{}'", s, exception);
         }
      }

      return null;
   }

   @Nullable
   public Component m_141958_() {
      return this.f_142863_;
   }
}
