package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;

public class RandomSelectorFeature extends Feature<RandomFeatureConfiguration> {
   public RandomSelectorFeature(Codec<RandomFeatureConfiguration> p_66619_) {
      super(p_66619_);
   }

   public boolean m_142674_(FeaturePlaceContext<RandomFeatureConfiguration> p_160212_) {
      RandomFeatureConfiguration randomfeatureconfiguration = p_160212_.m_159778_();
      Random random = p_160212_.m_159776_();
      WorldGenLevel worldgenlevel = p_160212_.m_159774_();
      ChunkGenerator chunkgenerator = p_160212_.m_159775_();
      BlockPos blockpos = p_160212_.m_159777_();

      for(WeightedConfiguredFeature weightedconfiguredfeature : randomfeatureconfiguration.f_67882_) {
         if (random.nextFloat() < weightedconfiguredfeature.f_67405_) {
            return weightedconfiguredfeature.m_67413_(worldgenlevel, chunkgenerator, random, blockpos);
         }
      }

      return randomfeatureconfiguration.f_67883_.get().m_65385_(worldgenlevel, chunkgenerator, random, blockpos);
   }
}