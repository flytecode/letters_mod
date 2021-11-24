package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MineshaftConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OceanRuinConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RuinedPortalConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ShipwreckConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.NetherFossilFeature;
import net.minecraft.world.level.levelgen.structure.OceanRuinFeature;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class StructureFeature<C extends FeatureConfiguration> extends net.minecraftforge.registries.ForgeRegistryEntry<StructureFeature<?>> implements net.minecraftforge.common.extensions.IForgeStructureFeature {
   public static final BiMap<String, StructureFeature<?>> f_67012_ = HashBiMap.create();
   private static final Map<StructureFeature<?>, GenerationStep.Decoration> f_67032_ = Maps.newHashMap();
   private static final Logger f_67033_ = LogManager.getLogger();
   public static final StructureFeature<JigsawConfiguration> f_67013_ = m_67089_("Pillager_Outpost", new PillagerOutpostFeature(JigsawConfiguration.f_67756_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final StructureFeature<MineshaftConfiguration> f_67014_ = m_67089_("Mineshaft", new MineshaftFeature(MineshaftConfiguration.f_67780_), GenerationStep.Decoration.UNDERGROUND_STRUCTURES);
   public static final StructureFeature<NoneFeatureConfiguration> f_67015_ = m_67089_("Mansion", new WoodlandMansionFeature(NoneFeatureConfiguration.f_67815_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final StructureFeature<NoneFeatureConfiguration> f_67016_ = m_67089_("Jungle_Pyramid", new JunglePyramidFeature(NoneFeatureConfiguration.f_67815_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final StructureFeature<NoneFeatureConfiguration> f_67017_ = m_67089_("Desert_Pyramid", new DesertPyramidFeature(NoneFeatureConfiguration.f_67815_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final StructureFeature<NoneFeatureConfiguration> f_67018_ = m_67089_("Igloo", new IglooFeature(NoneFeatureConfiguration.f_67815_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final StructureFeature<RuinedPortalConfiguration> f_67019_ = m_67089_("Ruined_Portal", new RuinedPortalFeature(RuinedPortalConfiguration.f_68054_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final StructureFeature<ShipwreckConfiguration> f_67020_ = m_67089_("Shipwreck", new ShipwreckFeature(ShipwreckConfiguration.f_68061_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final SwamplandHutFeature f_67021_ = m_67089_("Swamp_Hut", new SwamplandHutFeature(NoneFeatureConfiguration.f_67815_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final StructureFeature<NoneFeatureConfiguration> f_67022_ = m_67089_("Stronghold", new StrongholdFeature(NoneFeatureConfiguration.f_67815_), GenerationStep.Decoration.STRONGHOLDS);
   public static final StructureFeature<NoneFeatureConfiguration> f_67023_ = m_67089_("Monument", new OceanMonumentFeature(NoneFeatureConfiguration.f_67815_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final StructureFeature<OceanRuinConfiguration> f_67024_ = m_67089_("Ocean_Ruin", new OceanRuinFeature(OceanRuinConfiguration.f_67820_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final StructureFeature<NoneFeatureConfiguration> f_67025_ = m_67089_("Fortress", new NetherFortressFeature(NoneFeatureConfiguration.f_67815_), GenerationStep.Decoration.UNDERGROUND_DECORATION);
   public static final StructureFeature<NoneFeatureConfiguration> f_67026_ = m_67089_("EndCity", new EndCityFeature(NoneFeatureConfiguration.f_67815_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final StructureFeature<ProbabilityFeatureConfiguration> f_67027_ = m_67089_("Buried_Treasure", new BuriedTreasureFeature(ProbabilityFeatureConfiguration.f_67858_), GenerationStep.Decoration.UNDERGROUND_STRUCTURES);
   public static final StructureFeature<JigsawConfiguration> f_67028_ = m_67089_("Village", new VillageFeature(JigsawConfiguration.f_67756_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final StructureFeature<RangeDecoratorConfiguration> f_67029_ = m_67089_("Nether_Fossil", new NetherFossilFeature(RangeDecoratorConfiguration.f_68006_), GenerationStep.Decoration.UNDERGROUND_DECORATION);
   public static final StructureFeature<JigsawConfiguration> f_67030_ = m_67089_("Bastion_Remnant", new BastionFeature(JigsawConfiguration.f_67756_), GenerationStep.Decoration.SURFACE_STRUCTURES);
   public static final List<StructureFeature<?>> f_67031_ = ImmutableList.of(f_67013_, f_67028_, f_67029_, f_67022_);
   private static final ResourceLocation f_67034_ = new ResourceLocation("jigsaw");
   private static final Map<ResourceLocation, ResourceLocation> f_67035_ = ImmutableMap.<ResourceLocation, ResourceLocation>builder().put(new ResourceLocation("nvi"), f_67034_).put(new ResourceLocation("pcp"), f_67034_).put(new ResourceLocation("bastionremnant"), f_67034_).put(new ResourceLocation("runtime"), f_67034_).build();
   public static final int f_160446_ = 8;
   private final Codec<ConfiguredStructureFeature<C, StructureFeature<C>>> f_67036_;

   private static <F extends StructureFeature<?>> F m_67089_(String p_67090_, F p_67091_, GenerationStep.Decoration p_67092_) {
      f_67012_.put(p_67090_.toLowerCase(Locale.ROOT), p_67091_);
      f_67032_.put(p_67091_, p_67092_);
      return Registry.m_122961_(Registry.f_122841_, p_67090_.toLowerCase(Locale.ROOT), p_67091_);
   }

   public StructureFeature(Codec<C> p_67039_) {
      this.f_67036_ = p_67039_.fieldOf("config").xmap((p_67094_) -> {
         return new ConfiguredStructureFeature<>(this, p_67094_);
      }, (p_67064_) -> {
         return p_67064_.f_65404_;
      }).codec();
   }

   public GenerationStep.Decoration m_67095_() {
      return f_67032_.get(this);
   }

   public static void m_67096_() {
   }

   @Nullable
   public static StructureStart<?> m_160447_(ServerLevel p_160448_, CompoundTag p_160449_, long p_160450_) {
      String s = p_160449_.m_128461_("id");
      if ("INVALID".equals(s)) {
         return StructureStart.f_73561_;
      } else {
         StructureFeature<?> structurefeature = Registry.f_122841_.m_7745_(new ResourceLocation(s.toLowerCase(Locale.ROOT)));
         if (structurefeature == null) {
            f_67033_.error("Unknown feature id: {}", (Object)s);
            return null;
         } else {
            ChunkPos chunkpos = new ChunkPos(p_160449_.m_128451_("ChunkX"), p_160449_.m_128451_("ChunkZ"));
            int i = p_160449_.m_128451_("references");
            ListTag listtag = p_160449_.m_128437_("Children", 10);

            try {
               StructureStart<?> structurestart = structurefeature.m_160451_(chunkpos, i, p_160450_);

               for(int j = 0; j < listtag.size(); ++j) {
                  CompoundTag compoundtag = listtag.m_128728_(j);
                  String s1 = compoundtag.m_128461_("id").toLowerCase(Locale.ROOT);
                  ResourceLocation resourcelocation = new ResourceLocation(s1);
                  ResourceLocation resourcelocation1 = f_67035_.getOrDefault(resourcelocation, resourcelocation);
                  StructurePieceType structurepiecetype = Registry.f_122843_.m_7745_(resourcelocation1);
                  if (structurepiecetype == null) {
                     f_67033_.error("Unknown structure piece id: {}", (Object)resourcelocation1);
                  } else {
                     try {
                        StructurePiece structurepiece = structurepiecetype.m_160483_(p_160448_, compoundtag);
                        structurestart.m_142679_(structurepiece);
                     } catch (Exception exception) {
                        f_67033_.error("Exception loading structure piece with id {}", resourcelocation1, exception);
                     }
                  }
               }

               return structurestart;
            } catch (Exception exception1) {
               f_67033_.error("Failed Start with id {}", s, exception1);
               return null;
            }
         }
      }
   }

   public Codec<ConfiguredStructureFeature<C, StructureFeature<C>>> m_67097_() {
      return this.f_67036_;
   }

   public ConfiguredStructureFeature<C, ? extends StructureFeature<C>> m_67065_(C p_67066_) {
      return new ConfiguredStructureFeature<>(this, p_67066_);
   }

   @Nullable
   public BlockPos m_67046_(LevelReader p_67047_, StructureFeatureManager p_67048_, BlockPos p_67049_, int p_67050_, boolean p_67051_, long p_67052_, StructureFeatureConfiguration p_67053_) {
      int i = p_67053_.m_68171_();
      int j = SectionPos.m_123171_(p_67049_.m_123341_());
      int k = SectionPos.m_123171_(p_67049_.m_123343_());
      int l = 0;

      for(WorldgenRandom worldgenrandom = new WorldgenRandom(); l <= p_67050_; ++l) {
         for(int i1 = -l; i1 <= l; ++i1) {
            boolean flag = i1 == -l || i1 == l;

            for(int j1 = -l; j1 <= l; ++j1) {
               boolean flag1 = j1 == -l || j1 == l;
               if (flag || flag1) {
                  int k1 = j + i * i1;
                  int l1 = k + i * j1;
                  ChunkPos chunkpos = this.m_67067_(p_67053_, p_67052_, worldgenrandom, k1, l1);
                  boolean flag2 = p_67047_.m_7062_().m_151752_(chunkpos).m_47536_().m_47808_(this);
                  if (flag2) {
                     ChunkAccess chunkaccess = p_67047_.m_46819_(chunkpos.f_45578_, chunkpos.f_45579_, ChunkStatus.f_62315_);
                     StructureStart<?> structurestart = p_67048_.m_47297_(SectionPos.m_175562_(chunkaccess), this, chunkaccess);
                     if (structurestart != null && structurestart.m_73603_()) {
                        if (p_67051_ && structurestart.m_73606_()) {
                           structurestart.m_73607_();
                           return structurestart.m_7148_();
                        }

                        if (!p_67051_) {
                           return structurestart.m_7148_();
                        }
                     }
                  }

                  if (l == 0) {
                     break;
                  }
               }
            }

            if (l == 0) {
               break;
            }
         }
      }

      return null;
   }

   protected boolean m_5910_() {
      return true;
   }

   public final ChunkPos m_67067_(StructureFeatureConfiguration p_67068_, long p_67069_, WorldgenRandom p_67070_, int p_67071_, int p_67072_) {
      int i = p_67068_.m_68171_();
      int j = p_67068_.m_68176_();
      int k = Math.floorDiv(p_67071_, i);
      int l = Math.floorDiv(p_67072_, i);
      p_67070_.m_64694_(p_67069_, k, l, p_67068_.m_68179_());
      int i1;
      int j1;
      if (this.m_5910_()) {
         i1 = p_67070_.nextInt(i - j);
         j1 = p_67070_.nextInt(i - j);
      } else {
         i1 = (p_67070_.nextInt(i - j) + p_67070_.nextInt(i - j)) / 2;
         j1 = (p_67070_.nextInt(i - j) + p_67070_.nextInt(i - j)) / 2;
      }

      return new ChunkPos(k * i + i1, l * i + j1);
   }

   protected boolean m_142290_(ChunkGenerator p_160455_, BiomeSource p_160456_, long p_160457_, WorldgenRandom p_160458_, ChunkPos p_160459_, Biome p_160460_, ChunkPos p_160461_, C p_160462_, LevelHeightAccessor p_160463_) {
      return true;
   }

   private StructureStart<C> m_160451_(ChunkPos p_160452_, int p_160453_, long p_160454_) {
      return this.m_6258_().m_160478_(this, p_160452_, p_160453_, p_160454_);
   }

   public StructureStart<?> m_160464_(RegistryAccess p_160465_, ChunkGenerator p_160466_, BiomeSource p_160467_, StructureManager p_160468_, long p_160469_, ChunkPos p_160470_, Biome p_160471_, int p_160472_, WorldgenRandom p_160473_, StructureFeatureConfiguration p_160474_, C p_160475_, LevelHeightAccessor p_160476_) {
      ChunkPos chunkpos = this.m_67067_(p_160474_, p_160469_, p_160473_, p_160470_.f_45578_, p_160470_.f_45579_);
      if (p_160470_.f_45578_ == chunkpos.f_45578_ && p_160470_.f_45579_ == chunkpos.f_45579_ && this.m_142290_(p_160466_, p_160467_, p_160469_, p_160473_, p_160470_, p_160471_, chunkpos, p_160475_, p_160476_)) {
         StructureStart<C> structurestart = this.m_160451_(p_160470_, p_160472_, p_160469_);
         structurestart.m_142743_(p_160465_, p_160466_, p_160468_, p_160470_, p_160471_, p_160475_, p_160476_);
         if (structurestart.m_73603_()) {
            return structurestart;
         }
      }

      return StructureStart.f_73561_;
   }

   public abstract StructureFeature.StructureStartFactory<C> m_6258_();

   public String m_67098_() {
      return f_67012_.inverse().get(this);
   }

   public WeightedRandomList<MobSpawnSettings.SpawnerData> m_142494_() {
      return getSpawnList(net.minecraft.world.entity.MobCategory.MONSTER);
   }

   public WeightedRandomList<MobSpawnSettings.SpawnerData> m_142498_() {
      return getSpawnList(net.minecraft.world.entity.MobCategory.CREATURE);
   }

   public WeightedRandomList<MobSpawnSettings.SpawnerData> m_160477_() {
      return getSpawnList(net.minecraft.world.entity.MobCategory.UNDERGROUND_WATER_CREATURE);
   }

   @Override
   public final WeightedRandomList<MobSpawnSettings.SpawnerData> getSpawnList(net.minecraft.world.entity.MobCategory classification) {
      return net.minecraftforge.common.world.StructureSpawnManager.getSpawnList(this, classification);
   }

   public interface StructureStartFactory<C extends FeatureConfiguration> {
      StructureStart<C> m_160478_(StructureFeature<C> p_160479_, ChunkPos p_160480_, int p_160481_, long p_160482_);
   }
}
