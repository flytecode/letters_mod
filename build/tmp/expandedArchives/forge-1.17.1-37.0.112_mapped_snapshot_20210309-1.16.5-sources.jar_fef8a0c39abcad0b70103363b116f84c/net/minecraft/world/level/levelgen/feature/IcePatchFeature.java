package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;

public class IcePatchFeature extends BaseDiskFeature {
   public IcePatchFeature(Codec<DiskConfiguration> p_65989_) {
      super(p_65989_);
   }

   public boolean m_142674_(FeaturePlaceContext<DiskConfiguration> p_159880_) {
      WorldGenLevel worldgenlevel = p_159880_.m_159774_();
      ChunkGenerator chunkgenerator = p_159880_.m_159775_();
      Random random = p_159880_.m_159776_();
      DiskConfiguration diskconfiguration = p_159880_.m_159778_();

      BlockPos blockpos;
      for(blockpos = p_159880_.m_159777_(); worldgenlevel.m_46859_(blockpos) && blockpos.m_123342_() > worldgenlevel.m_141937_() + 2; blockpos = blockpos.m_7495_()) {
      }

      return !worldgenlevel.m_8055_(blockpos).m_60713_(Blocks.f_50127_) ? false : super.m_142674_(new FeaturePlaceContext<>(worldgenlevel, chunkgenerator, random, blockpos, diskconfiguration));
   }
}