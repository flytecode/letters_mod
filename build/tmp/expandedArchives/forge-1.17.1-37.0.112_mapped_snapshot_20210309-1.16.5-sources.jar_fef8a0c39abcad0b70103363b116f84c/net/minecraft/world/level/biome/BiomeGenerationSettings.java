package net.minecraft.world.level.biome;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.data.worldgen.SurfaceBuilders;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.CarverConfiguration;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiomeGenerationSettings {
   public static final Logger f_47776_ = LogManager.getLogger();
   public static final BiomeGenerationSettings f_47777_ = new BiomeGenerationSettings(() -> {
      return SurfaceBuilders.f_127291_;
   }, ImmutableMap.of(), ImmutableList.of(), ImmutableList.of());
   public static final MapCodec<BiomeGenerationSettings> f_47778_ = RecordCodecBuilder.mapCodec((p_47814_) -> {
      return p_47814_.group(ConfiguredSurfaceBuilder.f_74763_.fieldOf("surface_builder").flatXmap(ExtraCodecs.m_181037_(), ExtraCodecs.m_181037_()).forGetter((p_151749_) -> {
         return p_151749_.f_47779_;
      }), Codec.simpleMap(GenerationStep.Carving.f_64194_, ConfiguredWorldCarver.f_64848_.promotePartial(Util.m_137489_("Carver: ", f_47776_::error)).flatXmap(ExtraCodecs.m_181036_(), ExtraCodecs.m_181036_()), StringRepresentable.m_14357_(GenerationStep.Carving.values())).fieldOf("carvers").forGetter((p_151747_) -> {
         return p_151747_.f_47780_;
      }), ConfiguredFeature.f_65375_.promotePartial(Util.m_137489_("Feature: ", f_47776_::error)).flatXmap(ExtraCodecs.m_181036_(), ExtraCodecs.m_181036_()).listOf().fieldOf("features").forGetter((p_151745_) -> {
         return p_151745_.f_47781_;
      }), ConfiguredStructureFeature.f_65402_.promotePartial(Util.m_137489_("Structure start: ", f_47776_::error)).fieldOf("starts").flatXmap(ExtraCodecs.m_181036_(), ExtraCodecs.m_181036_()).forGetter((p_151743_) -> {
         return p_151743_.f_47782_;
      })).apply(p_47814_, BiomeGenerationSettings::new);
   });
   private final Supplier<ConfiguredSurfaceBuilder<?>> f_47779_;
   private final Map<GenerationStep.Carving, List<Supplier<ConfiguredWorldCarver<?>>>> f_47780_;
   private final java.util.Set<GenerationStep.Carving> carversView;
   private final List<List<Supplier<ConfiguredFeature<?, ?>>>> f_47781_;
   private final List<Supplier<ConfiguredStructureFeature<?, ?>>> f_47782_;
   private final List<ConfiguredFeature<?, ?>> f_47783_;

   BiomeGenerationSettings(Supplier<ConfiguredSurfaceBuilder<?>> p_47786_, Map<GenerationStep.Carving, List<Supplier<ConfiguredWorldCarver<?>>>> p_47787_, List<List<Supplier<ConfiguredFeature<?, ?>>>> p_47788_, List<Supplier<ConfiguredStructureFeature<?, ?>>> p_47789_) {
      this.f_47779_ = p_47786_;
      this.f_47780_ = p_47787_;
      this.f_47781_ = p_47788_;
      this.f_47782_ = p_47789_;
      this.f_47783_ = p_47788_.stream().flatMap(Collection::stream).map(Supplier::get).flatMap(ConfiguredFeature::m_65398_).filter((p_47802_) -> {
         return p_47802_.f_65377_ == Feature.f_65761_;
      }).collect(ImmutableList.toImmutableList());
      this.carversView = java.util.Collections.unmodifiableSet(f_47780_.keySet());
   }

   public List<Supplier<ConfiguredWorldCarver<?>>> m_47799_(GenerationStep.Carving p_47800_) {
      return this.f_47780_.getOrDefault(p_47800_, ImmutableList.of());
   }

   public java.util.Set<GenerationStep.Carving> getCarvingStages() {
       return this.carversView;
   }

   public boolean m_47808_(StructureFeature<?> p_47809_) {
      return this.f_47782_.stream().anyMatch((p_47812_) -> {
         return (p_47812_.get()).f_65403_ == p_47809_;
      });
   }

   public Collection<Supplier<ConfiguredStructureFeature<?, ?>>> m_47796_() {
      return this.f_47782_;
   }

   public ConfiguredStructureFeature<?, ?> m_47803_(ConfiguredStructureFeature<?, ?> p_47804_) {
      return DataFixUtils.orElse(this.f_47782_.stream().map(Supplier::get).filter((p_47807_) -> {
         return p_47807_.f_65403_ == p_47804_.f_65403_;
      }).findAny(), p_47804_);
   }

   public List<ConfiguredFeature<?, ?>> m_47815_() {
      return this.f_47783_;
   }

   public List<List<Supplier<ConfiguredFeature<?, ?>>>> m_47818_() {
      return this.f_47781_;
   }

   public Supplier<ConfiguredSurfaceBuilder<?>> m_47821_() {
      return this.f_47779_;
   }

   public SurfaceBuilderConfiguration m_47824_() {
      return this.f_47779_.get().m_74770_();
   }

   public static class Builder {
      protected Optional<Supplier<ConfiguredSurfaceBuilder<?>>> f_47826_ = Optional.empty();
      protected final Map<GenerationStep.Carving, List<Supplier<ConfiguredWorldCarver<?>>>> f_47827_ = Maps.newLinkedHashMap();
      protected final List<List<Supplier<ConfiguredFeature<?, ?>>>> f_47828_ = Lists.newArrayList();
      protected final List<Supplier<ConfiguredStructureFeature<?, ?>>> f_47829_ = Lists.newArrayList();

      public BiomeGenerationSettings.Builder m_47851_(ConfiguredSurfaceBuilder<?> p_47852_) {
         return this.m_47855_(() -> {
            return p_47852_;
         });
      }

      public BiomeGenerationSettings.Builder m_47855_(Supplier<ConfiguredSurfaceBuilder<?>> p_47856_) {
         this.f_47826_ = Optional.of(p_47856_);
         return this;
      }

      public BiomeGenerationSettings.Builder m_47842_(GenerationStep.Decoration p_47843_, ConfiguredFeature<?, ?> p_47844_) {
         return this.m_47834_(p_47843_.ordinal(), () -> {
            return p_47844_;
         });
      }

      public BiomeGenerationSettings.Builder m_47834_(int p_47835_, Supplier<ConfiguredFeature<?, ?>> p_47836_) {
         this.m_47832_(p_47835_);
         this.f_47828_.get(p_47835_).add(p_47836_);
         return this;
      }

      public <C extends CarverConfiguration> BiomeGenerationSettings.Builder m_47839_(GenerationStep.Carving p_47840_, ConfiguredWorldCarver<C> p_47841_) {
         this.f_47827_.computeIfAbsent(p_47840_, (p_47838_) -> {
            return Lists.newArrayList();
         }).add(() -> {
            return p_47841_;
         });
         return this;
      }

      public BiomeGenerationSettings.Builder m_47849_(ConfiguredStructureFeature<?, ?> p_47850_) {
         this.f_47829_.add(() -> {
            return p_47850_;
         });
         return this;
      }

      protected void m_47832_(int p_47833_) {
         while(this.f_47828_.size() <= p_47833_) {
            this.f_47828_.add(Lists.newArrayList());
         }

      }

      public BiomeGenerationSettings m_47831_() {
         return new BiomeGenerationSettings(this.f_47826_.orElseThrow(() -> {
            return new IllegalStateException("Missing surface builder");
         }), this.f_47827_.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, (p_47854_) -> {
            return ImmutableList.copyOf(p_47854_.getValue());
         })), this.f_47828_.stream().map(ImmutableList::copyOf).collect(ImmutableList.toImmutableList()), ImmutableList.copyOf(this.f_47829_));
      }
   }
}
