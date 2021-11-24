package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;

public class BaseDiskFeature extends Feature<DiskConfiguration> {
   public BaseDiskFeature(Codec<DiskConfiguration> p_65212_) {
      super(p_65212_);
   }

   public boolean m_142674_(FeaturePlaceContext<DiskConfiguration> p_159448_) {
      DiskConfiguration diskconfiguration = p_159448_.m_159778_();
      BlockPos blockpos = p_159448_.m_159777_();
      WorldGenLevel worldgenlevel = p_159448_.m_159774_();
      boolean flag = false;
      int i = blockpos.m_123342_();
      int j = i + diskconfiguration.f_67621_;
      int k = i - diskconfiguration.f_67621_ - 1;
      boolean flag1 = diskconfiguration.f_67619_.m_60734_() instanceof FallingBlock;
      int l = diskconfiguration.f_67620_.m_142270_(p_159448_.m_159776_());

      for(int i1 = blockpos.m_123341_() - l; i1 <= blockpos.m_123341_() + l; ++i1) {
         for(int j1 = blockpos.m_123343_() - l; j1 <= blockpos.m_123343_() + l; ++j1) {
            int k1 = i1 - blockpos.m_123341_();
            int l1 = j1 - blockpos.m_123343_();
            if (k1 * k1 + l1 * l1 <= l * l) {
               boolean flag2 = false;

               for(int i2 = j; i2 >= k; --i2) {
                  BlockPos blockpos1 = new BlockPos(i1, i2, j1);
                  BlockState blockstate = worldgenlevel.m_8055_(blockpos1);
                  Block block = blockstate.m_60734_();
                  boolean flag3 = false;
                  if (i2 > k) {
                     for(BlockState blockstate1 : diskconfiguration.f_67622_) {
                        if (blockstate1.m_60713_(block)) {
                           worldgenlevel.m_7731_(blockpos1, diskconfiguration.f_67619_, 2);
                           this.m_159739_(worldgenlevel, blockpos1);
                           flag = true;
                           flag3 = true;
                           break;
                        }
                     }
                  }

                  if (flag1 && flag2 && blockstate.m_60795_()) {
                     BlockState blockstate2 = diskconfiguration.f_67619_.m_60713_(Blocks.f_49993_) ? Blocks.f_50394_.m_49966_() : Blocks.f_50062_.m_49966_();
                     worldgenlevel.m_7731_(new BlockPos(i1, i2 + 1, j1), blockstate2, 2);
                  }

                  flag2 = flag3;
               }
            }
         }
      }

      return flag;
   }
}