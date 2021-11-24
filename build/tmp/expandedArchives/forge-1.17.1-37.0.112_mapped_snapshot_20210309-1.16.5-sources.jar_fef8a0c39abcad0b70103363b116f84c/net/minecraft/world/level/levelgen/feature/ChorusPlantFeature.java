package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ChorusPlantFeature extends Feature<NoneFeatureConfiguration> {
   public ChorusPlantFeature(Codec<NoneFeatureConfiguration> p_65360_) {
      super(p_65360_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159521_) {
      WorldGenLevel worldgenlevel = p_159521_.m_159774_();
      BlockPos blockpos = p_159521_.m_159777_();
      Random random = p_159521_.m_159776_();
      if (worldgenlevel.m_46859_(blockpos) && worldgenlevel.m_8055_(blockpos.m_7495_()).m_60713_(Blocks.f_50259_)) {
         ChorusFlowerBlock.m_51665_(worldgenlevel, blockpos, random, 8);
         return true;
      } else {
         return false;
      }
   }
}