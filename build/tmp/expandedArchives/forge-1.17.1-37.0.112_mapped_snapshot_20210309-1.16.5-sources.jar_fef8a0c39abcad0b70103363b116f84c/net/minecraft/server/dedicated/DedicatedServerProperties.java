package net.minecraft.server.dedicated;

import java.nio.file.Path;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import net.minecraft.core.RegistryAccess;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.levelgen.WorldGenSettings;

public class DedicatedServerProperties extends Settings<DedicatedServerProperties> {
   public final boolean f_139728_ = this.m_139836_("online-mode", true);
   public final boolean f_139729_ = this.m_139836_("prevent-proxy-connections", false);
   public final String f_139730_ = this.m_139811_("server-ip", "");
   public final boolean f_139731_ = this.m_139836_("spawn-animals", true);
   public final boolean f_139732_ = this.m_139836_("spawn-npcs", true);
   public final boolean f_139733_ = this.m_139836_("pvp", true);
   public final boolean f_139734_ = this.m_139836_("allow-flight", false);
   public final String f_139735_ = this.m_139811_("resource-pack", "");
   public final boolean f_142878_ = this.m_139836_("require-resource-pack", false);
   public final String f_142879_ = this.m_139811_("resource-pack-prompt", "");
   public final String f_139736_ = this.m_139811_("motd", "A Minecraft Server");
   public final boolean f_139737_ = this.m_139836_("force-gamemode", false);
   public final boolean f_139738_ = this.m_139836_("enforce-whitelist", false);
   public final Difficulty f_139739_ = this.m_139821_("difficulty", m_139850_(Difficulty::m_19029_, Difficulty::m_19031_), Difficulty::m_19036_, Difficulty.EASY);
   public final GameType f_139740_ = this.m_139821_("gamemode", m_139850_(GameType::m_46393_, GameType::m_46400_), GameType::m_46405_, GameType.SURVIVAL);
   public final String f_139741_ = this.m_139811_("level-name", "world");
   public final int f_139742_ = this.m_139805_("server-port", 25565);
   public final Boolean f_139744_ = this.m_139859_("announce-player-achievements");
   public final boolean f_139745_ = this.m_139836_("enable-query", false);
   public final int f_139746_ = this.m_139805_("query.port", 25565);
   public final boolean f_139747_ = this.m_139836_("enable-rcon", false);
   public final int f_139748_ = this.m_139805_("rcon.port", 25575);
   public final String f_139749_ = this.m_139811_("rcon.password", "");
   public final String f_139750_ = this.m_139803_("resource-pack-hash");
   public final String f_139751_ = this.m_139811_("resource-pack-sha1", "");
   public final boolean f_139752_ = this.m_139836_("hardcore", false);
   public final boolean f_139753_ = this.m_139836_("allow-nether", true);
   public final boolean f_139705_ = this.m_139836_("spawn-monsters", true);
   public final boolean f_139706_;
   public final boolean f_139707_;
   public final boolean f_139708_;
   public final int f_139709_;
   public final int f_139710_;
   public final int f_139711_;
   public final long f_139712_;
   public final int f_139713_;
   public final int f_139714_;
   public final int f_139715_;
   public final int f_139716_;
   public final boolean f_139717_;
   public final boolean f_139718_;
   public final int f_139719_;
   public final boolean f_139720_;
   public final boolean f_139721_;
   public final boolean f_139722_;
   public final int f_139723_;
   public final String f_139724_;
   public final Settings<DedicatedServerProperties>.MutableValue<Integer> f_139725_;
   public final Settings<DedicatedServerProperties>.MutableValue<Boolean> f_139726_;
   @Nullable
   private WorldGenSettings f_139727_;

   public DedicatedServerProperties(Properties p_180926_) {
      super(p_180926_);
      if (this.m_139836_("snooper-enabled", true)) {
      }

      this.f_139706_ = false;
      this.f_139707_ = this.m_139836_("use-native-transport", true);
      this.f_139708_ = this.m_139836_("enable-command-block", false);
      this.f_139709_ = this.m_139805_("spawn-protection", 16);
      this.f_139710_ = this.m_139805_("op-permission-level", 4);
      this.f_139711_ = this.m_139805_("function-permission-level", 2);
      this.f_139712_ = this.m_139808_("max-tick-time", TimeUnit.MINUTES.toMillis(1L));
      this.f_139713_ = this.m_139805_("rate-limit", 0);
      this.f_139714_ = this.m_139805_("view-distance", 10);
      this.f_139715_ = this.m_139805_("max-players", 20);
      this.f_139716_ = this.m_139805_("network-compression-threshold", 256);
      this.f_139717_ = this.m_139836_("broadcast-rcon-to-ops", true);
      this.f_139718_ = this.m_139836_("broadcast-console-to-ops", true);
      this.f_139719_ = this.m_139832_("max-world-size", (p_139771_) -> {
         return Mth.m_14045_(p_139771_, 1, 29999984);
      }, 29999984);
      this.f_139720_ = this.m_139836_("sync-chunk-writes", true);
      this.f_139721_ = this.m_139836_("enable-jmx-monitoring", false);
      this.f_139722_ = this.m_139836_("enable-status", true);
      this.f_139723_ = this.m_139832_("entity-broadcast-range-percentage", (p_139769_) -> {
         return Mth.m_14045_(p_139769_, 10, 1000);
      }, 100);
      this.f_139724_ = this.m_139811_("text-filtering-config", "");
      this.f_139725_ = this.m_139861_("player-idle-timeout", 0);
      this.f_139726_ = this.m_139873_("white-list", false);
   }

   public static DedicatedServerProperties m_180929_(Path p_180930_) {
      return new DedicatedServerProperties(m_139839_(p_180930_));
   }

   protected DedicatedServerProperties m_6764_(RegistryAccess p_139761_, Properties p_139762_) {
      DedicatedServerProperties dedicatedserverproperties = new DedicatedServerProperties(p_139762_);
      dedicatedserverproperties.m_180927_(p_139761_);
      return dedicatedserverproperties;
   }

   public WorldGenSettings m_180927_(RegistryAccess p_180928_) {
      if (this.f_139727_ == null) {
         this.f_139727_ = WorldGenSettings.m_64647_(p_180928_, this.f_139798_);
      }

      return this.f_139727_;
   }
}