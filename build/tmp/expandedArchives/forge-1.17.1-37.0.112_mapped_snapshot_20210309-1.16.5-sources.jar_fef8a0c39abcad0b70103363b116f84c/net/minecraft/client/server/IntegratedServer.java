package net.minecraft.client.server;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import javax.annotation.Nullable;
import net.minecraft.CrashReport;
import net.minecraft.SharedConstants;
import net.minecraft.SystemReport;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerResources;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.progress.ChunkProgressListenerFactory;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.players.GameProfileCache;
import net.minecraft.stats.Stats;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Snooper;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.storage.LevelStorageSource;
import net.minecraft.world.level.storage.WorldData;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class IntegratedServer extends MinecraftServer {
   public static final int f_174965_ = -1;
   private static final Logger f_120014_ = LogManager.getLogger();
   private final Minecraft f_120015_;
   private boolean f_120016_;
   private int f_120017_ = -1;
   @Nullable
   private GameType f_174966_;
   private LanServerPinger f_120018_;
   private UUID f_120019_;

   public IntegratedServer(Thread p_120022_, Minecraft p_120023_, RegistryAccess.RegistryHolder p_120024_, LevelStorageSource.LevelStorageAccess p_120025_, PackRepository p_120026_, ServerResources p_120027_, WorldData p_120028_, MinecraftSessionService p_120029_, GameProfileRepository p_120030_, GameProfileCache p_120031_, ChunkProgressListenerFactory p_120032_) {
      super(p_120022_, p_120024_, p_120025_, p_120028_, p_120026_, p_120023_.m_91096_(), p_120023_.m_91295_(), p_120027_, p_120029_, p_120030_, p_120031_, p_120032_);
      this.m_129981_(p_120023_.m_91094_().m_92546_());
      this.m_129975_(p_120023_.m_91402_());
      this.m_129823_(new IntegratedPlayerList(this, this.f_129746_, this.f_129745_));
      this.f_120015_ = p_120023_;
   }

   public boolean m_7038_() {
      f_120014_.info("Starting integrated minecraft server version {}", (Object)SharedConstants.m_136187_().getName());
      this.m_129985_(true);
      this.m_129997_(true);
      this.m_129999_(true);
      this.m_129793_();
      if (!net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleServerAboutToStart(this)) return false;
      this.m_130006_();
      this.m_129989_(this.m_129791_() + " - " + this.m_129910_().m_5462_());
      return net.minecraftforge.fmllegacy.server.ServerLifecycleHooks.handleServerStarting(this);
   }

   public void m_5705_(BooleanSupplier p_120049_) {
      boolean flag = this.f_120016_;
      this.f_120016_ = Minecraft.m_91087_().m_91403_() != null && Minecraft.m_91087_().m_91104_();
      ProfilerFiller profilerfiller = this.m_129905_();
      if (!flag && this.f_120016_) {
         profilerfiller.m_6180_("autoSave");
         f_120014_.info("Saving and pausing game...");
         this.m_6846_().m_11302_();
         this.m_129885_(false, false, false);
         profilerfiller.m_7238_();
      }

      if (this.f_120016_) {
         this.m_174968_();
      } else {
         super.m_5705_(p_120049_);
         int i = Math.max(2, this.f_120015_.f_91066_.f_92106_ + -1);
         if (i != this.m_6846_().m_11312_()) {
            f_120014_.info("Changing view distance to {}, from {}", i, this.m_6846_().m_11312_());
            this.m_6846_().m_11217_(i);
         }

      }
   }

   private void m_174968_() {
      for(ServerPlayer serverplayer : this.m_6846_().m_11314_()) {
         serverplayer.m_36220_(Stats.f_144256_);
      }

   }

   public boolean m_6983_() {
      return true;
   }

   public boolean m_6102_() {
      return true;
   }

   public File m_6237_() {
      return this.f_120015_.f_91069_;
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

   public void m_7268_(CrashReport p_120051_) {
      this.f_120015_.m_91253_(p_120051_);
   }

   public SystemReport m_142424_(SystemReport p_174970_) {
      p_174970_.m_143519_("Type", "Integrated Server (map_client.txt)");
      p_174970_.m_143522_("Is Modded", () -> {
         return this.m_6730_().orElse("Probably not. Jar signature remains and both client + server brands are untouched.");
      });
      return p_174970_;
   }

   public Optional<String> m_6730_() {
      String s = ClientBrandRetriever.m_129629_();
      if (!s.equals("vanilla")) {
         return Optional.of("Definitely; Client brand changed to '" + s + "'");
      } else {
         s = this.m_130001_();
         if (!"vanilla".equals(s)) {
            return Optional.of("Definitely; Server brand changed to '" + s + "'");
         } else {
            return Minecraft.class.getSigners() == null ? Optional.of("Very likely; Jar signature invalidated") : Optional.empty();
         }
      }
   }

   public void m_7108_(Snooper p_120037_) {
      super.m_7108_(p_120037_);
      p_120037_.m_19223_("snooper_partner", this.f_120015_.m_91093_().m_19232_());
   }

   public boolean m_142725_() {
      return Minecraft.m_91087_().m_142725_();
   }

   public boolean m_7386_(@Nullable GameType p_120041_, boolean p_120042_, int p_120043_) {
      try {
         this.m_129919_().m_9711_((InetAddress)null, p_120043_);
         f_120014_.info("Started serving on {}", (int)p_120043_);
         this.f_120017_ = p_120043_;
         this.f_120018_ = new LanServerPinger(this.m_129916_(), "" + p_120043_);
         this.f_120018_.start();
         this.f_174966_ = p_120041_;
         this.m_6846_().m_11284_(p_120042_);
         int i = this.m_129944_(this.f_120015_.f_91074_.m_36316_());
         this.f_120015_.f_91074_.m_108648_(i);

         for(ServerPlayer serverplayer : this.m_6846_().m_11314_()) {
            this.m_129892_().m_82095_(serverplayer);
         }

         return true;
      } catch (IOException ioexception) {
         return false;
      }
   }

   public void m_7041_() {
      super.m_7041_();
      if (this.f_120018_ != null) {
         this.f_120018_.interrupt();
         this.f_120018_ = null;
      }

   }

   public void m_7570_(boolean p_120053_) {
      if (m_130010_())
      this.m_18709_(() -> {
         for(ServerPlayer serverplayer : Lists.newArrayList(this.m_6846_().m_11314_())) {
            if (!serverplayer.m_142081_().equals(this.f_120019_)) {
               this.m_6846_().m_11286_(serverplayer);
            }
         }

      });
      super.m_7570_(p_120053_);
      if (this.f_120018_ != null) {
         this.f_120018_.interrupt();
         this.f_120018_ = null;
      }

   }

   public boolean m_6992_() {
      return this.f_120017_ > -1;
   }

   public int m_7010_() {
      return this.f_120017_;
   }

   public void m_7835_(GameType p_120039_) {
      super.m_7835_(p_120039_);
      this.f_174966_ = null;
   }

   public boolean m_6993_() {
      return true;
   }

   public int m_7022_() {
      return 2;
   }

   public int m_7034_() {
      return 2;
   }

   public void m_120046_(UUID p_120047_) {
      this.f_120019_ = p_120047_;
   }

   public boolean m_7779_(GameProfile p_120045_) {
      return p_120045_.getName().equalsIgnoreCase(this.m_129791_());
   }

   public int m_7186_(int p_120056_) {
      return (int)(this.f_120015_.f_91066_.f_92112_ * (float)p_120056_);
   }

   public boolean m_6365_() {
      return this.f_120015_.f_91066_.f_92076_;
   }

   @Nullable
   public GameType m_142359_() {
      return this.m_6992_() ? MoreObjects.firstNonNull(this.f_174966_, this.f_129749_.m_5464_()) : null;
   }
}
