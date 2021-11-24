package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BambooBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

public class BambooFeature extends Feature<ProbabilityFeatureConfiguration> {
   private static final BlockState f_65131_ = Blocks.f_50571_.m_49966_().m_61124_(BambooBlock.f_48869_, Integer.valueOf(1)).m_61124_(BambooBlock.f_48870_, BambooLeaves.NONE).m_61124_(BambooBlock.f_48871_, Integer.valueOf(0));
   private static final BlockState f_65132_ = f_65131_.m_61124_(BambooBlock.f_48870_, BambooLeaves.LARGE).m_61124_(BambooBlock.f_48871_, Integer.valueOf(1));
   private static final BlockState f_65133_ = f_65131_.m_61124_(BambooBlock.f_48870_, BambooLeaves.LARGE);
   private static final BlockState f_65134_ = f_65131_.m_61124_(BambooBlock.f_48870_, BambooLeaves.SMALL);

   public BambooFeature(Codec<ProbabilityFeatureConfiguration> p_65137_) {
      super(p_65137_);
   }

   public boolean m_142674_(FeaturePlaceContext<ProbabilityFeatureConfiguration> p_159438_) {
      int i = 0;
      BlockPos blockpos = p_159438_.m_159777_();
      WorldGenLevel worldgenlevel = p_159438_.m_159774_();
      Random random = p_159438_.m_159776_();
      ProbabilityFeatureConfiguration probabilityfeatureconfiguration = p_159438_.m_159778_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.m_122032_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = blockpos.m_122032_();
      if (worldgenlevel.m_46859_(blockpos$mutableblockpos)) {
         if (Blocks.f_50571_.m_49966_().m_60710_(worldgenlevel, blockpos$mutableblockpos)) {
            int j = random.nextInt(12) + 5;
            if (random.nextFloat() < probabilityfeatureconfiguration.f_67859_) {
               int k = random.nextInt(4) + 1;

               for(int l = blockpos.m_123341_() - k; l <= blockpos.m_123341_() + k; ++l) {
                  for(int i1 = blockpos.m_123343_() - k; i1 <= blockpos.m_123343_() + k; ++i1) {
                     int j1 = l - blockpos.m_123341_();
                     int k1 = i1 - blockpos.m_123343_();
                     if (j1 * j1 + k1 * k1 <= k * k) {
                        blockpos$mutableblockpos1.m_122178_(l, worldgenlevel.m_6924_(Heightmap.Types.WORLD_SURFACE, l, i1) - 1, i1);
                        if (m_159759_(worldgenlevel.m_8055_(blockpos$mutableblockpos1))) {
                           worldgenlevel.m_7731_(blockpos$mutableblockpos1, Blocks.f_50599_.m_49966_(), 2);
                        }
                     }
                  }
               }
            }

            for(int l1 = 0; l1 < j && worldgenlevel.m_46859_(blockpos$mutableblockpos); ++l1) {
               worldgenlevel.m_7731_(blockpos$mutableblockpos, f_65131_, 2);
               blockpos$mutableblockpos.m_122175_(Direction.UP, 1);
            }

            if (blockpos$mutableblockpos.m_123342_() - blockpos.m_123342_() >= 3) {
               worldgenlevel.m_7731_(blockpos$mutableblockpos, f_65132_, 2);
               worldgenlevel.m_7731_(blockpos$mutableblockpos.m_122175_(Direction.DOWN, 1), f_65133_, 2);
               worldgenlevel.m_7731_(blockpos$mutableblockpos.m_122175_(Direction.DOWN, 1), f_65134_, 2);
            }
         }

         ++i;
      }

      return i > 0;
   }
}