package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class KelpFeature extends Feature<NoneFeatureConfiguration> {
   public KelpFeature(Codec<NoneFeatureConfiguration> p_66219_) {
      super(p_66219_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159956_) {
      int i = 0;
      WorldGenLevel worldgenlevel = p_159956_.m_159774_();
      BlockPos blockpos = p_159956_.m_159777_();
      Random random = p_159956_.m_159776_();
      int j = worldgenlevel.m_6924_(Heightmap.Types.OCEAN_FLOOR, blockpos.m_123341_(), blockpos.m_123343_());
      BlockPos blockpos1 = new BlockPos(blockpos.m_123341_(), j, blockpos.m_123343_());
      if (worldgenlevel.m_8055_(blockpos1).m_60713_(Blocks.f_49990_)) {
         BlockState blockstate = Blocks.f_50575_.m_49966_();
         BlockState blockstate1 = Blocks.f_50576_.m_49966_();
         int k = 1 + random.nextInt(10);

         for(int l = 0; l <= k; ++l) {
            if (worldgenlevel.m_8055_(blockpos1).m_60713_(Blocks.f_49990_) && worldgenlevel.m_8055_(blockpos1.m_7494_()).m_60713_(Blocks.f_49990_) && blockstate1.m_60710_(worldgenlevel, blockpos1)) {
               if (l == k) {
                  worldgenlevel.m_7731_(blockpos1, blockstate.m_61124_(KelpBlock.f_53924_, Integer.valueOf(random.nextInt(4) + 20)), 2);
                  ++i;
               } else {
                  worldgenlevel.m_7731_(blockpos1, blockstate1, 2);
               }
            } else if (l > 0) {
               BlockPos blockpos2 = blockpos1.m_7495_();
               if (blockstate.m_60710_(worldgenlevel, blockpos2) && !worldgenlevel.m_8055_(blockpos2.m_7495_()).m_60713_(Blocks.f_50575_)) {
                  worldgenlevel.m_7731_(blockpos2, blockstate.m_61124_(KelpBlock.f_53924_, Integer.valueOf(random.nextInt(4) + 20)), 2);
                  ++i;
               }
               break;
            }

            blockpos1 = blockpos1.m_7494_();
         }
      }

      return i > 0;
   }
}