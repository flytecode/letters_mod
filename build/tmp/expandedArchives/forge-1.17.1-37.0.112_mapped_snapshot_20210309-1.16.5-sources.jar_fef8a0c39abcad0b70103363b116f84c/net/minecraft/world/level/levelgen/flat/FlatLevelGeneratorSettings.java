package net.minecraft.world.level.levelgen.flat;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.Features;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlatLevelGeneratorSettings {
   private static final Logger f_70348_ = LogManager.getLogger();
   public static final Codec<FlatLevelGeneratorSettings> f_70347_ = RecordCodecBuilder.<FlatLevelGeneratorSettings>create((p_70373_) -> {
      return p_70373_.group(RegistryLookupCodec.m_135622_(Registry.f_122885_).forGetter((p_161916_) -> {
         return p_161916_.f_70350_;
      }), StructureSettings.f_64579_.fieldOf("structures").forGetter(FlatLevelGeneratorSettings::m_70395_), FlatLayerInfo.f_70329_.listOf().fieldOf("layers").forGetter(FlatLevelGeneratorSettings::m_70401_), Codec.BOOL.fieldOf("lakes").orElse(false).forGetter((p_161914_) -> {
         return p_161914_.f_70357_;
      }), Codec.BOOL.fieldOf("features").orElse(false).forGetter((p_161912_) -> {
         return p_161912_.f_70356_;
      }), Biome.f_47431_.optionalFieldOf("biome").orElseGet(Optional::empty).forGetter((p_161908_) -> {
         return Optional.of(p_161908_.f_70353_);
      })).apply(p_70373_, FlatLevelGeneratorSettings::new);
   }).comapFlatMap(FlatLevelGeneratorSettings::m_161905_, Function.identity()).stable();
   private static final Map<StructureFeature<?>, ConfiguredStructureFeature<?, ?>> f_70349_ = Util.m_137469_(Maps.newHashMap(), (p_70379_) -> {
      p_70379_.put(StructureFeature.f_67014_, StructureFeatures.f_127240_);
      p_70379_.put(StructureFeature.f_67028_, StructureFeatures.f_127258_);
      p_70379_.put(StructureFeature.f_67022_, StructureFeatures.f_127249_);
      p_70379_.put(StructureFeature.f_67021_, StructureFeatures.f_127248_);
      p_70379_.put(StructureFeature.f_67017_, StructureFeatures.f_127244_);
      p_70379_.put(StructureFeature.f_67016_, StructureFeatures.f_127243_);
      p_70379_.put(StructureFeature.f_67018_, StructureFeatures.f_127245_);
      p_70379_.put(StructureFeature.f_67024_, StructureFeatures.f_127251_);
      p_70379_.put(StructureFeature.f_67020_, StructureFeatures.f_127246_);
      p_70379_.put(StructureFeature.f_67023_, StructureFeatures.f_127250_);
      p_70379_.put(StructureFeature.f_67026_, StructureFeatures.f_127255_);
      p_70379_.put(StructureFeature.f_67015_, StructureFeatures.f_127242_);
      p_70379_.put(StructureFeature.f_67025_, StructureFeatures.f_127253_);
      p_70379_.put(StructureFeature.f_67013_, StructureFeatures.f_127239_);
      p_70379_.put(StructureFeature.f_67019_, StructureFeatures.f_127263_);
      p_70379_.put(StructureFeature.f_67030_, StructureFeatures.f_127257_);
   });
   private final Registry<Biome> f_70350_;
   private final StructureSettings f_70351_;
   private final List<FlatLayerInfo> f_70352_ = Lists.newArrayList();
   private Supplier<Biome> f_70353_;
   private final List<BlockState> f_70354_;
   private boolean f_70355_;
   private boolean f_70356_;
   private boolean f_70357_;

   private static DataResult<FlatLevelGeneratorSettings> m_161905_(FlatLevelGeneratorSettings p_161906_) {
      int i = p_161906_.f_70352_.stream().mapToInt(FlatLayerInfo::m_70337_).sum();
      return i > DimensionType.f_156651_ ? DataResult.error("Sum of layer heights is > " + DimensionType.f_156651_, p_161906_) : DataResult.success(p_161906_);
   }

   private FlatLevelGeneratorSettings(Registry<Biome> p_70363_, StructureSettings p_70364_, List<FlatLayerInfo> p_70365_, boolean p_70366_, boolean p_70367_, Optional<Supplier<Biome>> p_70368_) {
      this(p_70364_, p_70363_);
      if (p_70366_) {
         this.m_70385_();
      }

      if (p_70367_) {
         this.m_70369_();
      }

      this.f_70352_.addAll(p_70365_);
      this.m_70403_();
      if (!p_70368_.isPresent()) {
         f_70348_.error("Unknown biome, defaulting to plains");
         this.f_70353_ = () -> {
            return p_70363_.m_123013_(Biomes.f_48202_);
         };
      } else {
         this.f_70353_ = p_70368_.get();
      }

   }

   public FlatLevelGeneratorSettings(StructureSettings p_70360_, Registry<Biome> p_70361_) {
      this.f_70350_ = p_70361_;
      this.f_70351_ = p_70360_;
      this.f_70353_ = () -> {
         return p_70361_.m_123013_(Biomes.f_48202_);
      };
      this.f_70354_ = Lists.newArrayList();
   }

   public FlatLevelGeneratorSettings m_70370_(StructureSettings p_70371_) {
      return this.m_70380_(this.f_70352_, p_70371_);
   }

   public FlatLevelGeneratorSettings m_70380_(List<FlatLayerInfo> p_70381_, StructureSettings p_70382_) {
      FlatLevelGeneratorSettings flatlevelgeneratorsettings = new FlatLevelGeneratorSettings(p_70382_, this.f_70350_);

      for(FlatLayerInfo flatlayerinfo : p_70381_) {
         flatlevelgeneratorsettings.f_70352_.add(new FlatLayerInfo(flatlayerinfo.m_70337_(), flatlayerinfo.m_70344_().m_60734_()));
         flatlevelgeneratorsettings.m_70403_();
      }

      flatlevelgeneratorsettings.m_70383_(this.f_70353_);
      if (this.f_70356_) {
         flatlevelgeneratorsettings.m_70369_();
      }

      if (this.f_70357_) {
         flatlevelgeneratorsettings.m_70385_();
      }

      return flatlevelgeneratorsettings;
   }

   public void m_70369_() {
      this.f_70356_ = true;
   }

   public void m_70385_() {
      this.f_70357_ = true;
   }

   public Biome m_70390_() {
      Biome biome = this.m_70400_();
      BiomeGenerationSettings biomegenerationsettings = biome.m_47536_();
      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).m_47855_(biomegenerationsettings.m_47821_());
      if (this.f_70357_) {
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.LAKES, Features.f_126875_);
         biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.LAKES, Features.f_126876_);
      }

      Map<StructureFeature<?>, ConfiguredStructureFeature<?, ?>> map = new java.util.HashMap<>(f_70349_);
      net.minecraft.data.BuiltinRegistries.f_123862_.m_123024_().filter(f -> !map.containsKey(f.f_65403_)).forEach(f -> map.put(f.f_65403_, f));
      for(Entry<StructureFeature<?>, StructureFeatureConfiguration> entry : this.f_70351_.m_64590_().entrySet()) {
         if (!map.containsKey(entry.getKey())) {
            f_70348_.error("FORGE: There's no known StructureFeature for {} when preparing the {} flatworld biome." +
                    " The structure will be skipped and may not spawn." +
                    " Please register your StructureFeatures in the WorldGenRegistries!", entry.getKey().m_67098_(), biome.getRegistryName());
            continue;
         }
         biomegenerationsettings$builder.m_47849_(biomegenerationsettings.m_47803_(map.get(entry.getKey())));
      }

      boolean flag = (!this.f_70355_ || this.f_70350_.m_7854_(biome).equals(Optional.of(Biomes.f_48173_))) && this.f_70356_;
      if (flag) {
         List<List<Supplier<ConfiguredFeature<?, ?>>>> list = biomegenerationsettings.m_47818_();

         for(int i = 0; i < list.size(); ++i) {
            if (i != GenerationStep.Decoration.UNDERGROUND_STRUCTURES.ordinal() && i != GenerationStep.Decoration.SURFACE_STRUCTURES.ordinal()) {
               for(Supplier<ConfiguredFeature<?, ?>> supplier : list.get(i)) {
                  biomegenerationsettings$builder.m_47834_(i, supplier);
               }
            }
         }
      }

      List<BlockState> list1 = this.m_161917_();

      for(int j = 0; j < list1.size(); ++j) {
         BlockState blockstate = list1.get(j);
         if (!Heightmap.Types.MOTION_BLOCKING.m_64299_().test(blockstate)) {
            list1.set(j, (BlockState)null);
            biomegenerationsettings$builder.m_47842_(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, Feature.f_65750_.m_65815_(new LayerConfiguration(j, blockstate)));
         }
      }

      return (new Biome.BiomeBuilder()).m_47597_(biome.m_47530_()).m_47595_(biome.m_47567_()).m_47593_(biome.m_47545_()).m_47607_(biome.m_47551_()).m_47609_(biome.m_47554_()).m_47611_(biome.m_47548_()).m_47603_(biome.m_47557_()).m_47601_(biomegenerationsettings$builder.m_47831_()).m_47605_(biome.m_47518_()).m_47592_().setRegistryName(biome.getRegistryName());
   }

   public StructureSettings m_70395_() {
      return this.f_70351_;
   }

   public Biome m_70400_() {
      return this.f_70353_.get();
   }

   public void m_70383_(Supplier<Biome> p_70384_) {
      this.f_70353_ = p_70384_;
   }

   public List<FlatLayerInfo> m_70401_() {
      return this.f_70352_;
   }

   public List<BlockState> m_161917_() {
      return this.f_70354_;
   }

   public void m_70403_() {
      this.f_70354_.clear();

      for(FlatLayerInfo flatlayerinfo : this.f_70352_) {
         for(int i = 0; i < flatlayerinfo.m_70337_(); ++i) {
            this.f_70354_.add(flatlayerinfo.m_70344_());
         }
      }

      this.f_70355_ = this.f_70354_.stream().allMatch((p_161904_) -> {
         return p_161904_.m_60713_(Blocks.f_50016_);
      });
   }

   public static FlatLevelGeneratorSettings m_70376_(Registry<Biome> p_70377_) {
      StructureSettings structuresettings = new StructureSettings(Optional.of(StructureSettings.f_64581_), Maps.newHashMap(ImmutableMap.of(StructureFeature.f_67028_, StructureSettings.f_64580_.get(StructureFeature.f_67028_))));
      FlatLevelGeneratorSettings flatlevelgeneratorsettings = new FlatLevelGeneratorSettings(structuresettings, p_70377_);
      flatlevelgeneratorsettings.f_70353_ = () -> {
         return p_70377_.m_123013_(Biomes.f_48202_);
      };
      flatlevelgeneratorsettings.m_70401_().add(new FlatLayerInfo(1, Blocks.f_50752_));
      flatlevelgeneratorsettings.m_70401_().add(new FlatLayerInfo(2, Blocks.f_50493_));
      flatlevelgeneratorsettings.m_70401_().add(new FlatLayerInfo(1, Blocks.f_50440_));
      flatlevelgeneratorsettings.m_70403_();
      return flatlevelgeneratorsettings;
   }
}
