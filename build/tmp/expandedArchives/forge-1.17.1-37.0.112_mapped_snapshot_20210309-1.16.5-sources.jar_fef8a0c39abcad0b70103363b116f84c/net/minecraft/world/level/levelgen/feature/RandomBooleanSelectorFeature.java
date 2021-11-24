package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;

public class RandomBooleanSelectorFeature extends Feature<RandomBooleanFeatureConfiguration> {
   public RandomBooleanSelectorFeature(Codec<RandomBooleanFeatureConfiguration> p_66591_) {
      super(p_66591_);
   }

   public boolean m_142674_(FeaturePlaceContext<RandomBooleanFeatureConfiguration> p_160208_) {
      Random random = p_160208_.m_159776_();
      RandomBooleanFeatureConfiguration randombooleanfeatureconfiguration = p_160208_.m_159778_();
      WorldGenLevel worldgenlevel = p_160208_.m_159774_();
      ChunkGenerator chunkgenerator = p_160208_.m_159775_();
      BlockPos blockpos = p_160208_.m_159777_();
      boolean flag = random.nextBoolean();
      return flag ? randombooleanfeatureconfiguration.f_67868_.get().m_65385_(worldgenlevel, chunkgenerator, random, blockpos) : randombooleanfeatureconfiguration.f_67869_.get().m_65385_(worldgenlevel, chunkgenerator, random, blockpos);
   }
}