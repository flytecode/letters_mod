package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VinesFeature extends Feature<NoneFeatureConfiguration> {
   public VinesFeature(Codec<NoneFeatureConfiguration> p_67337_) {
      super(p_67337_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_160628_) {
      WorldGenLevel worldgenlevel = p_160628_.m_159774_();
      BlockPos blockpos = p_160628_.m_159777_();
      p_160628_.m_159778_();
      if (!worldgenlevel.m_46859_(blockpos)) {
         return false;
      } else {
         for(Direction direction : Direction.values()) {
            if (direction != Direction.DOWN && VineBlock.m_57853_(worldgenlevel, blockpos.m_142300_(direction), direction)) {
               worldgenlevel.m_7731_(blockpos, Blocks.f_50191_.m_49966_().m_61124_(VineBlock.m_57883_(direction), Boolean.valueOf(true)), 2);
               return true;
            }
         }

         return false;
      }
   }
}