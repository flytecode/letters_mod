package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Material;

public class BlueIceFeature extends Feature<NoneFeatureConfiguration> {
   public BlueIceFeature(Codec<NoneFeatureConfiguration> p_65285_) {
      super(p_65285_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159475_) {
      BlockPos blockpos = p_159475_.m_159777_();
      WorldGenLevel worldgenlevel = p_159475_.m_159774_();
      Random random = p_159475_.m_159776_();
      if (blockpos.m_123342_() > worldgenlevel.m_5736_() - 1) {
         return false;
      } else if (!worldgenlevel.m_8055_(blockpos).m_60713_(Blocks.f_49990_) && !worldgenlevel.m_8055_(blockpos.m_7495_()).m_60713_(Blocks.f_49990_)) {
         return false;
      } else {
         boolean flag = false;

         for(Direction direction : Direction.values()) {
            if (direction != Direction.DOWN && worldgenlevel.m_8055_(blockpos.m_142300_(direction)).m_60713_(Blocks.f_50354_)) {
               flag = true;
               break;
            }
         }

         if (!flag) {
            return false;
         } else {
            worldgenlevel.m_7731_(blockpos, Blocks.f_50568_.m_49966_(), 2);

            for(int i = 0; i < 200; ++i) {
               int j = random.nextInt(5) - random.nextInt(6);
               int k = 3;
               if (j < 2) {
                  k += j / 2;
               }

               if (k >= 1) {
                  BlockPos blockpos1 = blockpos.m_142082_(random.nextInt(k) - random.nextInt(k), j, random.nextInt(k) - random.nextInt(k));
                  BlockState blockstate = worldgenlevel.m_8055_(blockpos1);
                  if (blockstate.m_60767_() == Material.f_76296_ || blockstate.m_60713_(Blocks.f_49990_) || blockstate.m_60713_(Blocks.f_50354_) || blockstate.m_60713_(Blocks.f_50126_)) {
                     for(Direction direction1 : Direction.values()) {
                        BlockState blockstate1 = worldgenlevel.m_8055_(blockpos1.m_142300_(direction1));
                        if (blockstate1.m_60713_(Blocks.f_50568_)) {
                           worldgenlevel.m_7731_(blockpos1, Blocks.f_50568_.m_49966_(), 2);
                           break;
                        }
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}