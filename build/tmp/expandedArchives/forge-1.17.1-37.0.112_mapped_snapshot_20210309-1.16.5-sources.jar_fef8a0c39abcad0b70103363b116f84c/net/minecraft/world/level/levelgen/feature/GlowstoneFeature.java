package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GlowstoneFeature extends Feature<NoneFeatureConfiguration> {
   public GlowstoneFeature(Codec<NoneFeatureConfiguration> p_65865_) {
      super(p_65865_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159861_) {
      WorldGenLevel worldgenlevel = p_159861_.m_159774_();
      BlockPos blockpos = p_159861_.m_159777_();
      Random random = p_159861_.m_159776_();
      if (!worldgenlevel.m_46859_(blockpos)) {
         return false;
      } else {
         BlockState blockstate = worldgenlevel.m_8055_(blockpos.m_7494_());
         if (!blockstate.m_60713_(Blocks.f_50134_) && !blockstate.m_60713_(Blocks.f_50137_) && !blockstate.m_60713_(Blocks.f_50730_)) {
            return false;
         } else {
            worldgenlevel.m_7731_(blockpos, Blocks.f_50141_.m_49966_(), 2);

            for(int i = 0; i < 1500; ++i) {
               BlockPos blockpos1 = blockpos.m_142082_(random.nextInt(8) - random.nextInt(8), -random.nextInt(12), random.nextInt(8) - random.nextInt(8));
               if (worldgenlevel.m_8055_(blockpos1).m_60795_()) {
                  int j = 0;

                  for(Direction direction : Direction.values()) {
                     if (worldgenlevel.m_8055_(blockpos1.m_142300_(direction)).m_60713_(Blocks.f_50141_)) {
                        ++j;
                     }

                     if (j > 1) {
                        break;
                     }
                  }

                  if (j == 1) {
                     worldgenlevel.m_7731_(blockpos1, Blocks.f_50141_.m_49966_(), 2);
                  }
               }
            }

            return true;
         }
      }
   }
}