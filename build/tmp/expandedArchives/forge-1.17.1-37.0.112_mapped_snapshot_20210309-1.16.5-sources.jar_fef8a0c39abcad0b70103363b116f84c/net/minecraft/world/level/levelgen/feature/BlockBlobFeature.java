package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;

public class BlockBlobFeature extends Feature<BlockStateConfiguration> {
   public BlockBlobFeature(Codec<BlockStateConfiguration> p_65248_) {
      super(p_65248_);
   }

   public boolean m_142674_(FeaturePlaceContext<BlockStateConfiguration> p_159471_) {
      BlockPos blockpos = p_159471_.m_159777_();
      WorldGenLevel worldgenlevel = p_159471_.m_159774_();
      Random random = p_159471_.m_159776_();

      BlockStateConfiguration blockstateconfiguration;
      for(blockstateconfiguration = p_159471_.m_159778_(); blockpos.m_123342_() > worldgenlevel.m_141937_() + 3; blockpos = blockpos.m_7495_()) {
         if (!worldgenlevel.m_46859_(blockpos.m_7495_())) {
            BlockState blockstate = worldgenlevel.m_8055_(blockpos.m_7495_());
            if (m_159759_(blockstate) || m_159747_(blockstate)) {
               break;
            }
         }
      }

      if (blockpos.m_123342_() <= worldgenlevel.m_141937_() + 3) {
         return false;
      } else {
         for(int l = 0; l < 3; ++l) {
            int i = random.nextInt(2);
            int j = random.nextInt(2);
            int k = random.nextInt(2);
            float f = (float)(i + j + k) * 0.333F + 0.5F;

            for(BlockPos blockpos1 : BlockPos.m_121940_(blockpos.m_142082_(-i, -j, -k), blockpos.m_142082_(i, j, k))) {
               if (blockpos1.m_123331_(blockpos) <= (double)(f * f)) {
                  worldgenlevel.m_7731_(blockpos1, blockstateconfiguration.f_67547_, 4);
               }
            }

            blockpos = blockpos.m_142082_(-1 + random.nextInt(2), -random.nextInt(2), -1 + random.nextInt(2));
         }

         return true;
      }
   }
}