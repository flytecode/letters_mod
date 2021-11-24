package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratedFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import org.apache.commons.lang3.mutable.MutableBoolean;

public class DecoratedFeature extends Feature<DecoratedFeatureConfiguration> {
   public DecoratedFeature(Codec<DecoratedFeatureConfiguration> p_65495_) {
      super(p_65495_);
   }

   public boolean m_142674_(FeaturePlaceContext<DecoratedFeatureConfiguration> p_159545_) {
      MutableBoolean mutableboolean = new MutableBoolean();
      WorldGenLevel worldgenlevel = p_159545_.m_159774_();
      DecoratedFeatureConfiguration decoratedfeatureconfiguration = p_159545_.m_159778_();
      ChunkGenerator chunkgenerator = p_159545_.m_159775_();
      Random random = p_159545_.m_159776_();
      BlockPos blockpos = p_159545_.m_159777_();
      ConfiguredFeature<?, ?> configuredfeature = decoratedfeatureconfiguration.f_67577_.get();
      decoratedfeatureconfiguration.f_67578_.m_70480_(new DecorationContext(worldgenlevel, chunkgenerator), random, blockpos).forEach((p_159543_) -> {
         if (configuredfeature.m_65385_(worldgenlevel, chunkgenerator, random, p_159543_)) {
            mutableboolean.setTrue();
         }

      });
      return mutableboolean.isTrue();
   }

   public String toString() {
      return String.format("< %s [%s] >", this.getClass().getSimpleName(), Registry.f_122839_.m_7981_(this));
   }
}