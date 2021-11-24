package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.configurations.LayerConfiguration;

public class FillLayerFeature extends Feature<LayerConfiguration> {
   public FillLayerFeature(Codec<LayerConfiguration> p_65818_) {
      super(p_65818_);
   }

   public boolean m_142674_(FeaturePlaceContext<LayerConfiguration> p_159780_) {
      BlockPos blockpos = p_159780_.m_159777_();
      LayerConfiguration layerconfiguration = p_159780_.m_159778_();
      WorldGenLevel worldgenlevel = p_159780_.m_159774_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            int k = blockpos.m_123341_() + i;
            int l = blockpos.m_123343_() + j;
            int i1 = worldgenlevel.m_141937_() + layerconfiguration.f_67768_;
            blockpos$mutableblockpos.m_122178_(k, i1, l);
            if (worldgenlevel.m_8055_(blockpos$mutableblockpos).m_60795_()) {
               worldgenlevel.m_7731_(blockpos$mutableblockpos, layerconfiguration.f_67769_, 2);
            }
         }
      }

      return true;
   }
}