package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;

public class SimpleRandomSelectorFeature extends Feature<SimpleRandomFeatureConfiguration> {
   public SimpleRandomSelectorFeature(Codec<SimpleRandomFeatureConfiguration> p_66822_) {
      super(p_66822_);
   }

   public boolean m_142674_(FeaturePlaceContext<SimpleRandomFeatureConfiguration> p_160343_) {
      Random random = p_160343_.m_159776_();
      SimpleRandomFeatureConfiguration simplerandomfeatureconfiguration = p_160343_.m_159778_();
      WorldGenLevel worldgenlevel = p_160343_.m_159774_();
      BlockPos blockpos = p_160343_.m_159777_();
      ChunkGenerator chunkgenerator = p_160343_.m_159775_();
      int i = random.nextInt(simplerandomfeatureconfiguration.f_68090_.size());
      ConfiguredFeature<?, ?> configuredfeature = simplerandomfeatureconfiguration.f_68090_.get(i).get();
      return configuredfeature.m_65385_(worldgenlevel, chunkgenerator, random, blockpos);
   }
}