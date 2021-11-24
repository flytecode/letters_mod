package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;

public class PillagerOutpostFeature extends JigsawFeature {
   private static final WeightedRandomList<MobSpawnSettings.SpawnerData> f_66559_ = WeightedRandomList.m_146330_(new MobSpawnSettings.SpawnerData(EntityType.f_20513_, 1, 1, 1));

   public PillagerOutpostFeature(Codec<JigsawConfiguration> p_66562_) {
      super(p_66562_, 0, true, true);
   }

   @Override
   public java.util.List<MobSpawnSettings.SpawnerData> getDefaultSpawnList(net.minecraft.world.entity.MobCategory category) {
      if (category == net.minecraft.world.entity.MobCategory.MONSTER)
         return f_66559_.m_146338_();
      return java.util.Collections.emptyList();
   }

   protected boolean m_142290_(ChunkGenerator p_160197_, BiomeSource p_160198_, long p_160199_, WorldgenRandom p_160200_, ChunkPos p_160201_, Biome p_160202_, ChunkPos p_160203_, JigsawConfiguration p_160204_, LevelHeightAccessor p_160205_) {
      int i = p_160201_.f_45578_ >> 4;
      int j = p_160201_.f_45579_ >> 4;
      p_160200_.setSeed((long)(i ^ j << 4) ^ p_160199_);
      p_160200_.nextInt();
      if (p_160200_.nextInt(5) != 0) {
         return false;
      } else {
         return !this.m_160181_(p_160197_, p_160199_, p_160200_, p_160201_);
      }
   }

   private boolean m_160181_(ChunkGenerator p_160182_, long p_160183_, WorldgenRandom p_160184_, ChunkPos p_160185_) {
      StructureFeatureConfiguration structurefeatureconfiguration = p_160182_.m_62205_().m_64593_(StructureFeature.f_67028_);
      if (structurefeatureconfiguration == null) {
         return false;
      } else {
         int i = p_160185_.f_45578_;
         int j = p_160185_.f_45579_;

         for(int k = i - 10; k <= i + 10; ++k) {
            for(int l = j - 10; l <= j + 10; ++l) {
               ChunkPos chunkpos = StructureFeature.f_67028_.m_67067_(structurefeatureconfiguration, p_160183_, p_160184_, k, l);
               if (k == chunkpos.f_45578_ && l == chunkpos.f_45579_) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
