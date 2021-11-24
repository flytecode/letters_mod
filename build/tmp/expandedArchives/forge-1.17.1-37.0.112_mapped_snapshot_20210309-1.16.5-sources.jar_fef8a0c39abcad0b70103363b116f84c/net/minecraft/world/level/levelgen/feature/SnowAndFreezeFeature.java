package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SnowAndFreezeFeature extends Feature<NoneFeatureConfiguration> {
   public SnowAndFreezeFeature(Codec<NoneFeatureConfiguration> p_66836_) {
      super(p_66836_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_160368_) {
      WorldGenLevel worldgenlevel = p_160368_.m_159774_();
      BlockPos blockpos = p_160368_.m_159777_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

      for(int i = 0; i < 16; ++i) {
         for(int j = 0; j < 16; ++j) {
            int k = blockpos.m_123341_() + i;
            int l = blockpos.m_123343_() + j;
            int i1 = worldgenlevel.m_6924_(Heightmap.Types.MOTION_BLOCKING, k, l);
            blockpos$mutableblockpos.m_122178_(k, i1, l);
            blockpos$mutableblockpos1.m_122190_(blockpos$mutableblockpos).m_122175_(Direction.DOWN, 1);
            Biome biome = worldgenlevel.m_46857_(blockpos$mutableblockpos);
            if (biome.m_47480_(worldgenlevel, blockpos$mutableblockpos1, false)) {
               worldgenlevel.m_7731_(blockpos$mutableblockpos1, Blocks.f_50126_.m_49966_(), 2);
            }

            if (biome.m_47519_(worldgenlevel, blockpos$mutableblockpos)) {
               worldgenlevel.m_7731_(blockpos$mutableblockpos, Blocks.f_50125_.m_49966_(), 2);
               BlockState blockstate = worldgenlevel.m_8055_(blockpos$mutableblockpos1);
               if (blockstate.m_61138_(SnowyDirtBlock.f_56637_)) {
                  worldgenlevel.m_7731_(blockpos$mutableblockpos1, blockstate.m_61124_(SnowyDirtBlock.f_56637_, Boolean.valueOf(true)), 2);
               }
            }
         }
      }

      return true;
   }
}