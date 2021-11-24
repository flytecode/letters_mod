package net.minecraft.world.level.dimension;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.TheEndBiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public final class LevelStem {
   public static final Codec<LevelStem> f_63970_ = RecordCodecBuilder.create((p_63986_) -> {
      return p_63986_.group(DimensionType.f_63853_.fieldOf("type").flatXmap(ExtraCodecs.m_181037_(), ExtraCodecs.m_181037_()).forGetter(LevelStem::m_63981_), ChunkGenerator.f_62136_.fieldOf("generator").forGetter(LevelStem::m_63990_)).apply(p_63986_, p_63986_.stable(LevelStem::new));
   });
   public static final ResourceKey<LevelStem> f_63971_ = ResourceKey.m_135785_(Registry.f_122820_, new ResourceLocation("overworld"));
   public static final ResourceKey<LevelStem> f_63972_ = ResourceKey.m_135785_(Registry.f_122820_, new ResourceLocation("the_nether"));
   public static final ResourceKey<LevelStem> f_63973_ = ResourceKey.m_135785_(Registry.f_122820_, new ResourceLocation("the_end"));
   private static final Set<ResourceKey<LevelStem>> f_63974_ = Sets.newLinkedHashSet(ImmutableList.of(f_63971_, f_63972_, f_63973_));
   private final Supplier<DimensionType> f_63975_;
   private final ChunkGenerator f_63976_;

   public LevelStem(Supplier<DimensionType> p_63979_, ChunkGenerator p_63980_) {
      this.f_63975_ = p_63979_;
      this.f_63976_ = p_63980_;
   }

   public Supplier<DimensionType> m_63981_() {
      return this.f_63975_;
   }

   public DimensionType m_63989_() {
      return this.f_63975_.get();
   }

   public ChunkGenerator m_63990_() {
      return this.f_63976_;
   }

   public static MappedRegistry<LevelStem> m_63987_(MappedRegistry<LevelStem> p_63988_) {
      MappedRegistry<LevelStem> mappedregistry = new MappedRegistry<>(Registry.f_122820_, Lifecycle.experimental());

      for(ResourceKey<LevelStem> resourcekey : f_63974_) {
         LevelStem levelstem = p_63988_.m_6246_(resourcekey);
         if (levelstem != null) {
            mappedregistry.m_7135_(resourcekey, levelstem, p_63988_.m_6228_(levelstem));
         }
      }

      for(Entry<ResourceKey<LevelStem>, LevelStem> entry : p_63988_.m_6579_()) {
         ResourceKey<LevelStem> resourcekey1 = entry.getKey();
         if (!f_63974_.contains(resourcekey1)) {
            mappedregistry.m_7135_(resourcekey1, entry.getValue(), p_63988_.m_6228_(entry.getValue()));
         }
      }

      return mappedregistry;
   }

   public static boolean m_63982_(long p_63983_, MappedRegistry<LevelStem> p_63984_) {
      List<Entry<ResourceKey<LevelStem>, LevelStem>> list = Lists.newArrayList(p_63984_.m_6579_());
      if (list.size() != f_63974_.size()) {
         return false;
      } else {
         Entry<ResourceKey<LevelStem>, LevelStem> entry = list.get(0);
         Entry<ResourceKey<LevelStem>, LevelStem> entry1 = list.get(1);
         Entry<ResourceKey<LevelStem>, LevelStem> entry2 = list.get(2);
         if (entry.getKey() == f_63971_ && entry1.getKey() == f_63972_ && entry2.getKey() == f_63973_) {
            if (!entry.getValue().m_63989_().m_63906_(DimensionType.f_63848_) && entry.getValue().m_63989_() != DimensionType.f_63852_) {
               return false;
            } else if (!entry1.getValue().m_63989_().m_63906_(DimensionType.f_63849_)) {
               return false;
            } else if (!entry2.getValue().m_63989_().m_63906_(DimensionType.f_63850_)) {
               return false;
            } else if (entry1.getValue().m_63990_() instanceof NoiseBasedChunkGenerator && entry2.getValue().m_63990_() instanceof NoiseBasedChunkGenerator) {
               NoiseBasedChunkGenerator noisebasedchunkgenerator = (NoiseBasedChunkGenerator)entry1.getValue().m_63990_();
               NoiseBasedChunkGenerator noisebasedchunkgenerator1 = (NoiseBasedChunkGenerator)entry2.getValue().m_63990_();
               if (!noisebasedchunkgenerator.m_64375_(p_63983_, NoiseGeneratorSettings.f_64434_)) {
                  return false;
               } else if (!noisebasedchunkgenerator1.m_64375_(p_63983_, NoiseGeneratorSettings.f_64435_)) {
                  return false;
               } else if (!(noisebasedchunkgenerator.m_62218_() instanceof MultiNoiseBiomeSource)) {
                  return false;
               } else {
                  MultiNoiseBiomeSource multinoisebiomesource = (MultiNoiseBiomeSource)noisebasedchunkgenerator.m_62218_();
                  if (!multinoisebiomesource.m_48482_(p_63983_)) {
                     return false;
                  } else if (!(noisebasedchunkgenerator1.m_62218_() instanceof TheEndBiomeSource)) {
                     return false;
                  } else {
                     TheEndBiomeSource theendbiomesource = (TheEndBiomeSource)noisebasedchunkgenerator1.m_62218_();
                     return theendbiomesource.m_48653_(p_63983_);
                  }
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }
}