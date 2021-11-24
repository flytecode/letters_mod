package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class SeagrassFeature extends Feature<ProbabilityFeatureConfiguration> {
   public SeagrassFeature(Codec<ProbabilityFeatureConfiguration> p_66768_) {
      super(p_66768_);
   }

   public boolean m_142674_(FeaturePlaceContext<ProbabilityFeatureConfiguration> p_160318_) {
      boolean flag = false;
      Random random = p_160318_.m_159776_();
      WorldGenLevel worldgenlevel = p_160318_.m_159774_();
      BlockPos blockpos = p_160318_.m_159777_();
      ProbabilityFeatureConfiguration probabilityfeatureconfiguration = p_160318_.m_159778_();
      int i = random.nextInt(8) - random.nextInt(8);
      int j = random.nextInt(8) - random.nextInt(8);
      int k = worldgenlevel.m_6924_(Heightmap.Types.OCEAN_FLOOR, blockpos.m_123341_() + i, blockpos.m_123343_() + j);
      BlockPos blockpos1 = new BlockPos(blockpos.m_123341_() + i, k, blockpos.m_123343_() + j);
      if (worldgenlevel.m_8055_(blockpos1).m_60713_(Blocks.f_49990_)) {
         boolean flag1 = random.nextDouble() < (double)probabilityfeatureconfiguration.f_67859_;
         BlockState blockstate = flag1 ? Blocks.f_50038_.m_49966_() : Blocks.f_50037_.m_49966_();
         if (blockstate.m_60710_(worldgenlevel, blockpos1)) {
            if (flag1) {
               BlockState blockstate1 = blockstate.m_61124_(TallSeagrassBlock.f_154740_, DoubleBlockHalf.UPPER);
               BlockPos blockpos2 = blockpos1.m_7494_();
               if (worldgenlevel.m_8055_(blockpos2).m_60713_(Blocks.f_49990_)) {
                  worldgenlevel.m_7731_(blockpos1, blockstate, 2);
                  worldgenlevel.m_7731_(blockpos2, blockstate1, 2);
               }
            } else {
               worldgenlevel.m_7731_(blockpos1, blockstate, 2);
            }

            flag = true;
         }
      }

      return flag;
   }
}