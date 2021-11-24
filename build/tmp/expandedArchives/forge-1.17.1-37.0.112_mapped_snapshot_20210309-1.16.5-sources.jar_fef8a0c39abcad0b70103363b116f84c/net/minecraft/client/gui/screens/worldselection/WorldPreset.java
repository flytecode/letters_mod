package net.minecraft.client.gui.screens.worldselection;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.CreateBuffetWorldScreen;
import net.minecraft.client.gui.screens.CreateFlatWorldScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.biome.OverworldBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.DebugLevelSource;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorSettings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class WorldPreset {
   public static final WorldPreset f_101506_ = new WorldPreset("default") {
      protected ChunkGenerator m_7474_(Registry<Biome> p_101580_, Registry<NoiseGeneratorSettings> p_101581_, long p_101582_) {
         return new NoiseBasedChunkGenerator(new OverworldBiomeSource(p_101582_, false, false, p_101580_), p_101582_, () -> {
            return p_101581_.m_123013_(NoiseGeneratorSettings.f_64432_);
         });
      }
   };
   private static final WorldPreset f_101510_ = new WorldPreset("flat") {
      protected ChunkGenerator m_7474_(Registry<Biome> p_101586_, Registry<NoiseGeneratorSettings> p_101587_, long p_101588_) {
         return new FlatLevelSource(FlatLevelGeneratorSettings.m_70376_(p_101586_));
      }
   };
   private static final WorldPreset f_101511_ = new WorldPreset("large_biomes") {
      protected ChunkGenerator m_7474_(Registry<Biome> p_101594_, Registry<NoiseGeneratorSettings> p_101595_, long p_101596_) {
         return new NoiseBasedChunkGenerator(new OverworldBiomeSource(p_101596_, false, true, p_101594_), p_101596_, () -> {
            return p_101595_.m_123013_(NoiseGeneratorSettings.f_64432_);
         });
      }
   };
   public static final WorldPreset f_101507_ = new WorldPreset("amplified") {
      protected ChunkGenerator m_7474_(Registry<Biome> p_101602_, Registry<NoiseGeneratorSettings> p_101603_, long p_101604_) {
         return new NoiseBasedChunkGenerator(new OverworldBiomeSource(p_101604_, false, false, p_101602_), p_101604_, () -> {
            return p_101603_.m_123013_(NoiseGeneratorSettings.f_64433_);
         });
      }
   };
   private static final WorldPreset f_101512_ = new WorldPreset("single_biome_surface") {
      protected ChunkGenerator m_7474_(Registry<Biome> p_101610_, Registry<NoiseGeneratorSettings> p_101611_, long p_101612_) {
         return new NoiseBasedChunkGenerator(new FixedBiomeSource(p_101610_.m_123013_(Biomes.f_48202_)), p_101612_, () -> {
            return p_101611_.m_123013_(NoiseGeneratorSettings.f_64432_);
         });
      }
   };
   private static final WorldPreset f_101513_ = new WorldPreset("single_biome_caves") {
      public WorldGenSettings m_7012_(RegistryAccess.RegistryHolder p_101622_, long p_101623_, boolean p_101624_, boolean p_101625_) {
         Registry<Biome> registry = p_101622_.m_175515_(Registry.f_122885_);
         Registry<DimensionType> registry1 = p_101622_.m_175515_(Registry.f_122818_);
         Registry<NoiseGeneratorSettings> registry2 = p_101622_.m_175515_(Registry.f_122878_);
         return new WorldGenSettings(p_101623_, p_101624_, p_101625_, WorldGenSettings.m_64627_(DimensionType.m_63921_(registry1, registry, registry2, p_101623_), () -> {
            return registry1.m_123013_(DimensionType.f_63851_);
         }, this.m_7474_(registry, registry2, p_101623_)));
      }

      protected ChunkGenerator m_7474_(Registry<Biome> p_101618_, Registry<NoiseGeneratorSettings> p_101619_, long p_101620_) {
         return new NoiseBasedChunkGenerator(new FixedBiomeSource(p_101618_.m_123013_(Biomes.f_48202_)), p_101620_, () -> {
            return p_101619_.m_123013_(NoiseGeneratorSettings.f_64436_);
         });
      }
   };
   private static final WorldPreset f_101514_ = new WorldPreset("single_biome_floating_islands") {
      protected ChunkGenerator m_7474_(Registry<Biome> p_101633_, Registry<NoiseGeneratorSettings> p_101634_, long p_101635_) {
         return new NoiseBasedChunkGenerator(new FixedBiomeSource(p_101633_.m_123013_(Biomes.f_48202_)), p_101635_, () -> {
            return p_101634_.m_123013_(NoiseGeneratorSettings.f_64437_);
         });
      }
   };
   private static final WorldPreset f_101515_ = new WorldPreset("debug_all_block_states") {
      protected ChunkGenerator m_7474_(Registry<Biome> p_101639_, Registry<NoiseGeneratorSettings> p_101640_, long p_101641_) {
         return new DebugLevelSource(p_101639_);
      }
   };
   protected static final List<WorldPreset> f_101508_ = Lists.newArrayList(f_101506_, f_101510_, f_101511_, f_101507_, f_101512_, f_101513_, f_101514_, f_101515_);
   protected static final Map<Optional<WorldPreset>, WorldPreset.PresetEditor> f_101509_ = ImmutableMap.of(Optional.of(f_101510_), (p_101573_, p_101574_) -> {
      ChunkGenerator chunkgenerator = p_101574_.m_64666_();
      return new CreateFlatWorldScreen(p_101573_, (p_170300_) -> {
         p_101573_.f_100847_.m_101404_(new WorldGenSettings(p_101574_.m_64619_(), p_101574_.m_64657_(), p_101574_.m_64660_(), WorldGenSettings.m_64633_(p_101573_.f_100847_.m_101456_().m_175515_(Registry.f_122818_), p_101574_.m_64663_(), new FlatLevelSource(p_170300_))));
      }, chunkgenerator instanceof FlatLevelSource ? ((FlatLevelSource)chunkgenerator).m_64191_() : FlatLevelGeneratorSettings.m_70376_(p_101573_.f_100847_.m_101456_().m_175515_(Registry.f_122885_)));
   }, Optional.of(f_101512_), (p_101564_, p_101565_) -> {
      return new CreateBuffetWorldScreen(p_101564_, p_101564_.f_100847_.m_101456_(), (p_170310_) -> {
         p_101564_.f_100847_.m_101404_(m_101549_(p_101564_.f_100847_.m_101456_(), p_101565_, f_101512_, p_170310_));
      }, m_101546_(p_101564_.f_100847_.m_101456_(), p_101565_));
   }, Optional.of(f_101513_), (p_101555_, p_101556_) -> {
      return new CreateBuffetWorldScreen(p_101555_, p_101555_.f_100847_.m_101456_(), (p_170306_) -> {
         p_101555_.f_100847_.m_101404_(m_101549_(p_101555_.f_100847_.m_101456_(), p_101556_, f_101513_, p_170306_));
      }, m_101546_(p_101555_.f_100847_.m_101456_(), p_101556_));
   }, Optional.of(f_101514_), (p_101527_, p_101528_) -> {
      return new CreateBuffetWorldScreen(p_101527_, p_101527_.f_100847_.m_101456_(), (p_170296_) -> {
         p_101527_.f_100847_.m_101404_(m_101549_(p_101527_.f_100847_.m_101456_(), p_101528_, f_101514_, p_170296_));
      }, m_101546_(p_101527_.f_100847_.m_101456_(), p_101528_));
   });
   private final Component f_101516_;

   WorldPreset(String p_101519_) {
      this.f_101516_ = new TranslatableComponent("generator." + p_101519_);
   }
   public WorldPreset(Component displayName) {
      this.f_101516_ = displayName;
   }

   private static WorldGenSettings m_101549_(RegistryAccess p_101550_, WorldGenSettings p_101551_, WorldPreset p_101552_, Biome p_101553_) {
      BiomeSource biomesource = new FixedBiomeSource(p_101553_);
      Registry<DimensionType> registry = p_101550_.m_175515_(Registry.f_122818_);
      Registry<NoiseGeneratorSettings> registry1 = p_101550_.m_175515_(Registry.f_122878_);
      Supplier<NoiseGeneratorSettings> supplier;
      if (p_101552_ == f_101513_) {
         supplier = () -> {
            return registry1.m_123013_(NoiseGeneratorSettings.f_64436_);
         };
      } else if (p_101552_ == f_101514_) {
         supplier = () -> {
            return registry1.m_123013_(NoiseGeneratorSettings.f_64437_);
         };
      } else {
         supplier = () -> {
            return registry1.m_123013_(NoiseGeneratorSettings.f_64432_);
         };
      }

      return new WorldGenSettings(p_101551_.m_64619_(), p_101551_.m_64657_(), p_101551_.m_64660_(), WorldGenSettings.m_64633_(registry, p_101551_.m_64663_(), new NoiseBasedChunkGenerator(biomesource, p_101551_.m_64619_(), supplier)));
   }

   private static Biome m_101546_(RegistryAccess p_101547_, WorldGenSettings p_101548_) {
      return p_101548_.m_64666_().m_62218_().m_47922_().stream().findFirst().orElse(p_101547_.m_175515_(Registry.f_122885_).m_123013_(Biomes.f_48202_));
   }

   public static Optional<WorldPreset> m_101524_(WorldGenSettings p_101525_) {
      ChunkGenerator chunkgenerator = p_101525_.m_64666_();
      if (chunkgenerator instanceof FlatLevelSource) {
         return Optional.of(f_101510_);
      } else {
         return chunkgenerator instanceof DebugLevelSource ? Optional.of(f_101515_) : Optional.empty();
      }
   }

   public Component m_101523_() {
      return this.f_101516_;
   }

   public WorldGenSettings m_7012_(RegistryAccess.RegistryHolder p_101542_, long p_101543_, boolean p_101544_, boolean p_101545_) {
      Registry<Biome> registry = p_101542_.m_175515_(Registry.f_122885_);
      Registry<DimensionType> registry1 = p_101542_.m_175515_(Registry.f_122818_);
      Registry<NoiseGeneratorSettings> registry2 = p_101542_.m_175515_(Registry.f_122878_);
      return new WorldGenSettings(p_101543_, p_101544_, p_101545_, WorldGenSettings.m_64633_(registry1, DimensionType.m_63921_(registry1, registry, registry2, p_101543_), this.m_7474_(registry, registry2, p_101543_)));
   }

   protected abstract ChunkGenerator m_7474_(Registry<Biome> p_101539_, Registry<NoiseGeneratorSettings> p_101540_, long p_101541_);

   public static boolean m_170301_(WorldPreset p_170302_) {
      return p_170302_ != f_101515_;
   }

   @OnlyIn(Dist.CLIENT)
   public interface PresetEditor {
      Screen m_101642_(CreateWorldScreen p_101643_, WorldGenSettings p_101644_);
   }

   // Forge start
   // For internal use only, automatically called for all ForgeWorldTypes. Register your ForgeWorldType in the forge registry!
   public static void registerGenerator(WorldPreset gen) { f_101508_.add(gen); }
}
