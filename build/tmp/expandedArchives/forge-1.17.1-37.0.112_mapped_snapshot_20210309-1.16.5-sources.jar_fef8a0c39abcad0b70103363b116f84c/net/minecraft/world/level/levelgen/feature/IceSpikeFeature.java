package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class IceSpikeFeature extends Feature<NoneFeatureConfiguration> {
   public IceSpikeFeature(Codec<NoneFeatureConfiguration> p_66003_) {
      super(p_66003_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159882_) {
      BlockPos blockpos = p_159882_.m_159777_();
      Random random = p_159882_.m_159776_();

      WorldGenLevel worldgenlevel;
      for(worldgenlevel = p_159882_.m_159774_(); worldgenlevel.m_46859_(blockpos) && blockpos.m_123342_() > worldgenlevel.m_141937_() + 2; blockpos = blockpos.m_7495_()) {
      }

      if (!worldgenlevel.m_8055_(blockpos).m_60713_(Blocks.f_50127_)) {
         return false;
      } else {
         blockpos = blockpos.m_6630_(random.nextInt(4));
         int i = random.nextInt(4) + 7;
         int j = i / 4 + random.nextInt(2);
         if (j > 1 && random.nextInt(60) == 0) {
            blockpos = blockpos.m_6630_(10 + random.nextInt(30));
         }

         for(int k = 0; k < i; ++k) {
            float f = (1.0F - (float)k / (float)i) * (float)j;
            int l = Mth.m_14167_(f);

            for(int i1 = -l; i1 <= l; ++i1) {
               float f1 = (float)Mth.m_14040_(i1) - 0.25F;

               for(int j1 = -l; j1 <= l; ++j1) {
                  float f2 = (float)Mth.m_14040_(j1) - 0.25F;
                  if ((i1 == 0 && j1 == 0 || !(f1 * f1 + f2 * f2 > f * f)) && (i1 != -l && i1 != l && j1 != -l && j1 != l || !(random.nextFloat() > 0.75F))) {
                     BlockState blockstate = worldgenlevel.m_8055_(blockpos.m_142082_(i1, k, j1));
                     if (blockstate.m_60795_() || m_159759_(blockstate) || blockstate.m_60713_(Blocks.f_50127_) || blockstate.m_60713_(Blocks.f_50126_)) {
                        this.m_5974_(worldgenlevel, blockpos.m_142082_(i1, k, j1), Blocks.f_50354_.m_49966_());
                     }

                     if (k != 0 && l > 1) {
                        blockstate = worldgenlevel.m_8055_(blockpos.m_142082_(i1, -k, j1));
                        if (blockstate.m_60795_() || m_159759_(blockstate) || blockstate.m_60713_(Blocks.f_50127_) || blockstate.m_60713_(Blocks.f_50126_)) {
                           this.m_5974_(worldgenlevel, blockpos.m_142082_(i1, -k, j1), Blocks.f_50354_.m_49966_());
                        }
                     }
                  }
               }
            }
         }

         int k1 = j - 1;
         if (k1 < 0) {
            k1 = 0;
         } else if (k1 > 1) {
            k1 = 1;
         }

         for(int l1 = -k1; l1 <= k1; ++l1) {
            for(int i2 = -k1; i2 <= k1; ++i2) {
               BlockPos blockpos1 = blockpos.m_142082_(l1, -1, i2);
               int j2 = 50;
               if (Math.abs(l1) == 1 && Math.abs(i2) == 1) {
                  j2 = random.nextInt(5);
               }

               while(blockpos1.m_123342_() > 50) {
                  BlockState blockstate1 = worldgenlevel.m_8055_(blockpos1);
                  if (!blockstate1.m_60795_() && !m_159759_(blockstate1) && !blockstate1.m_60713_(Blocks.f_50127_) && !blockstate1.m_60713_(Blocks.f_50126_) && !blockstate1.m_60713_(Blocks.f_50354_)) {
                     break;
                  }

                  this.m_5974_(worldgenlevel, blockpos1, Blocks.f_50354_.m_49966_());
                  blockpos1 = blockpos1.m_7495_();
                  --j2;
                  if (j2 <= 0) {
                     blockpos1 = blockpos1.m_6625_(random.nextInt(5) + 1);
                     j2 = random.nextInt(5);
                  }
               }
            }
         }

         return true;
      }
   }
}