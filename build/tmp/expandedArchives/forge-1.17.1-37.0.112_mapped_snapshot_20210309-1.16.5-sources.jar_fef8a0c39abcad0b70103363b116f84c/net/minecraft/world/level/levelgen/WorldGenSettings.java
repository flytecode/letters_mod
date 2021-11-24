package net.minecraft.world.level.levelgen;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.OverworldBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenSettings {
   public static final Codec<WorldGenSettings> f_64600_ = RecordCodecBuilder.<WorldGenSettings>create((p_64626_) -> {
      return p_64626_.group(Codec.LONG.fieldOf("seed").stable().forGetter(WorldGenSettings::m_64619_), Codec.BOOL.fieldOf("generate_features").orElse(true).stable().forGetter(WorldGenSettings::m_64657_), Codec.BOOL.fieldOf("bonus_chest").orElse(false).stable().forGetter(WorldGenSettings::m_64660_), MappedRegistry.m_122747_(Registry.f_122820_, Lifecycle.stable(), LevelStem.f_63970_).xmap(LevelStem::m_63987_, Function.identity()).fieldOf("dimensions").forGetter(WorldGenSettings::m_64663_), Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter((p_158959_) -> {
         return p_158959_.f_64606_;
      })).apply(p_64626_, p_64626_.stable(WorldGenSettings::new));
   }).comapFlatMap(WorldGenSettings::m_64674_, Function.identity());
   private static final Logger f_64601_ = LogManager.getLogger();
   private final long f_64602_;
   private final boolean f_64603_;
   private final boolean f_64604_;
   private final MappedRegistry<LevelStem> f_64605_;
   private final Optional<String> f_64606_;

   private DataResult<WorldGenSettings> m_64674_() {
      LevelStem levelstem = this.f_64605_.m_6246_(LevelStem.f_63971_);
      if (levelstem == null) {
         return DataResult.error("Overworld settings missing");
      } else {
         return this.m_64675_() ? DataResult.success(this, Lifecycle.stable()) : DataResult.success(this);
      }
   }

   private boolean m_64675_() {
      return LevelStem.m_63982_(this.f_64602_, this.f_64605_);
   }

   public WorldGenSettings(long p_64609_, boolean p_64610_, boolean p_64611_, MappedRegistry<LevelStem> p_64612_) {
      this(p_64609_, p_64610_, p_64611_, p_64612_, Optional.empty());
      LevelStem levelstem = p_64612_.m_6246_(LevelStem.f_63971_);
      if (levelstem == null) {
         throw new IllegalStateException("Overworld settings missing");
      }
   }

   private WorldGenSettings(long p_64614_, boolean p_64615_, boolean p_64616_, MappedRegistry<LevelStem> p_64617_, Optional<String> p_64618_) {
      this.f_64602_ = p_64614_;
      this.f_64603_ = p_64615_;
      this.f_64604_ = p_64616_;
      this.f_64605_ = p_64617_;
      this.f_64606_ = p_64618_;
   }

   public static WorldGenSettings m_64645_(RegistryAccess p_64646_) {
      Registry<Biome> registry = p_64646_.m_175515_(Registry.f_122885_);
      int i = "North Carolina".hashCode();
      Registry<DimensionType> registry1 = p_64646_.m_175515_(Registry.f_122818_);
      Registry<NoiseGeneratorSettings> registry2 = p_64646_.m_175515_(Registry.f_122878_);
      return new WorldGenSettings((long)i, true, true, m_64633_(registry1, DimensionType.m_63921_(registry1, registry, registry2, (long)i), m_64637_(registry, registry2, (long)i)));
   }

   public static WorldGenSettings m_64641_(Registry<DimensionType> p_64642_, Registry<Biome> p_64643_, Registry<NoiseGeneratorSettings> p_64644_) {
      long i = (new Random()).nextLong();
      return new WorldGenSettings(i, true, false, m_64633_(p_64642_, DimensionType.m_63921_(p_64642_, p_64643_, p_64644_, i), m_64637_(p_64643_, p_64644_, i)));
   }

   public static NoiseBasedChunkGenerator m_64637_(Registry<Biome> p_64638_, Registry<NoiseGeneratorSettings> p_64639_, long p_64640_) {
      return new NoiseBasedChunkGenerator(new OverworldBiomeSource(p_64640_, false, false, p_64638_), p_64640_, () -> {
         return p_64639_.m_123013_(NoiseGeneratorSettings.f_64432_);
      });
   }

   public long m_64619_() {
      return this.f_64602_;
   }

   public boolean m_64657_() {
      return this.f_64603_;
   }

   public boolean m_64660_() {
      return this.f_64604_;
   }

   public static MappedRegistry<LevelStem> m_64633_(Registry<DimensionType> p_64634_, MappedRegistry<LevelStem> p_64635_, ChunkGenerator p_64636_) {
      LevelStem levelstem = p_64635_.m_6246_(LevelStem.f_63971_);
      Supplier<DimensionType> supplier = () -> {
         return levelstem == null ? p_64634_.m_123013_(DimensionType.f_63845_) : levelstem.m_63989_();
      };
      return m_64627_(p_64635_, supplier, p_64636_);
   }

   public static MappedRegistry<LevelStem> m_64627_(MappedRegistry<LevelStem> p_64628_, Supplier<DimensionType> p_64629_, ChunkGenerator p_64630_) {
      MappedRegistry<LevelStem> mappedregistry = new MappedRegistry<>(Registry.f_122820_, Lifecycle.experimental());
      mappedregistry.m_7135_(LevelStem.f_63971_, new LevelStem(p_64629_, p_64630_), Lifecycle.stable());

      for(Entry<ResourceKey<LevelStem>, LevelStem> entry : p_64628_.m_6579_()) {
         ResourceKey<LevelStem> resourcekey = entry.getKey();
         if (resourcekey != LevelStem.f_63971_) {
            mappedregistry.m_7135_(resourcekey, entry.getValue(), p_64628_.m_6228_(entry.getValue()));
         }
      }

      return mappedregistry;
   }

   public MappedRegistry<LevelStem> m_64663_() {
      return this.f_64605_;
   }

   public ChunkGenerator m_64666_() {
      LevelStem levelstem = this.f_64605_.m_6246_(LevelStem.f_63971_);
      if (levelstem == null) {
         throw new IllegalStateException("Overworld settings missing");
      } else {
         return levelstem.m_63990_();
      }
   }

   public ImmutableSet<ResourceKey<Level>> m_64667_() {
      return this.m_64663_().m_6579_().stream().map((p_64653_) -> {
         return ResourceKey.m_135785_(Registry.f_122819_, p_64653_.getKey().m_135782_());
      }).collect(ImmutableSet.toImmutableSet());
   }

   public boolean m_64668_() {
      return this.m_64666_() instanceof DebugLevelSource;
   }

   public boolean m_64669_() {
      return this.m_64666_() instanceof FlatLevelSource;
   }

   public boolean m_64670_() {
      return this.f_64606_.isPresent();
   }

   public WorldGenSettings m_64671_() {
      return new WorldGenSettings(this.f_64602_, this.f_64603_, true, this.f_64605_, this.f_64606_);
   }

   public WorldGenSettings m_64672_() {
      return new WorldGenSettings(this.f_64602_, !this.f_64603_, this.f_64604_, this.f_64605_);
   }

   public WorldGenSettings m_64673_() {
      return new WorldGenSettings(this.f_64602_, this.f_64603_, !this.f_64604_, this.f_64605_);
   }

   public static WorldGenSettings m_64647_(RegistryAccess p_64648_, Properties p_64649_) {
      String s = MoreObjects.firstNonNull((String)p_64649_.get("generator-settings"), "");
      p_64649_.put("generator-settings", s);
      String s1 = MoreObjects.firstNonNull((String)p_64649_.get("level-seed"), "");
      p_64649_.put("level-seed", s1);
      String s2 = (String)p_64649_.get("generate-structures");
      boolean flag = s2 == null || Boolean.parseBoolean(s2);
      p_64649_.put("generate-structures", Objects.toString(flag));
      String s3 = (String)p_64649_.get("level-type");
      String s4 = Optional.ofNullable(s3).map((p_64651_) -> {
         return p_64651_.toLowerCase(Locale.ROOT);
      }).orElseGet(net.minecraftforge.common.ForgeHooks::getDefaultWorldType);
      p_64649_.put("level-type", s4);
      long i = (new Random()).nextLong();
      if (!s1.isEmpty()) {
         try {
            long j = Long.parseLong(s1);
            if (j != 0L) {
               i = j;
            }
         } catch (NumberFormatException numberformatexception) {
            i = (long)s1.hashCode();
         }
      }

      Registry<DimensionType> registry2 = p_64648_.m_175515_(Registry.f_122818_);
      Registry<Biome> registry = p_64648_.m_175515_(Registry.f_122885_);
      Registry<NoiseGeneratorSettings> registry1 = p_64648_.m_175515_(Registry.f_122878_);
      MappedRegistry<LevelStem> mappedregistry = DimensionType.m_63921_(registry2, registry, registry1, i);
      net.minecraftforge.common.world.ForgeWorldType type = net.minecraftforge.registries.ForgeRegistries.WORLD_TYPES.getValue(new net.minecraft.resources.ResourceLocation(s4));
      if (type != null) return type.createSettings(p_64648_, i, flag, false, s);
      switch(s4) {
      case "flat":
         JsonObject jsonobject = !s.isEmpty() ? GsonHelper.m_13864_(s) : new JsonObject();
         Dynamic<JsonElement> dynamic = new Dynamic<>(JsonOps.INSTANCE, jsonobject);
         return new WorldGenSettings(i, flag, false, m_64633_(registry2, mappedregistry, new FlatLevelSource(FlatLevelGeneratorSettings.f_70347_.parse(dynamic).resultOrPartial(f_64601_::error).orElseGet(() -> {
            return FlatLevelGeneratorSettings.m_70376_(registry);
         }))));
      case "debug_all_block_states":
         return new WorldGenSettings(i, flag, false, m_64633_(registry2, mappedregistry, new DebugLevelSource(registry)));
      case "amplified":
         return new WorldGenSettings(i, flag, false, m_64633_(registry2, mappedregistry, new NoiseBasedChunkGenerator(new OverworldBiomeSource(i, false, false, registry), i, () -> {
            return registry1.m_123013_(NoiseGeneratorSettings.f_64433_);
         })));
      case "largebiomes":
         return new WorldGenSettings(i, flag, false, m_64633_(registry2, mappedregistry, new NoiseBasedChunkGenerator(new OverworldBiomeSource(i, false, true, registry), i, () -> {
            return registry1.m_123013_(NoiseGeneratorSettings.f_64432_);
         })));
      default:
         return new WorldGenSettings(i, flag, false, m_64633_(registry2, mappedregistry, m_64637_(registry, registry1, i)));
      }
   }

   public WorldGenSettings m_64654_(boolean p_64655_, OptionalLong p_64656_) {
      long i = p_64656_.orElse(this.f_64602_);
      MappedRegistry<LevelStem> mappedregistry;
      if (p_64656_.isPresent()) {
         mappedregistry = new MappedRegistry<>(Registry.f_122820_, Lifecycle.experimental());
         long j = p_64656_.getAsLong();

         for(Entry<ResourceKey<LevelStem>, LevelStem> entry : this.f_64605_.m_6579_()) {
            ResourceKey<LevelStem> resourcekey = entry.getKey();
            mappedregistry.m_7135_(resourcekey, new LevelStem(entry.getValue().m_63981_(), entry.getValue().m_63990_().m_6819_(j)), this.f_64605_.m_6228_(entry.getValue()));
         }
      } else {
         mappedregistry = this.f_64605_;
      }

      WorldGenSettings worldgensettings;
      if (this.m_64668_()) {
         worldgensettings = new WorldGenSettings(i, false, false, mappedregistry);
      } else {
         worldgensettings = new WorldGenSettings(i, this.m_64657_(), this.m_64660_() && !p_64655_, mappedregistry);
      }

      return worldgensettings;
   }
}
