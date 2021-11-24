package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class EndIslandFeature extends Feature<NoneFeatureConfiguration> {
   public EndIslandFeature(Codec<NoneFeatureConfiguration> p_65701_) {
      super(p_65701_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159717_) {
      WorldGenLevel worldgenlevel = p_159717_.m_159774_();
      Random random = p_159717_.m_159776_();
      BlockPos blockpos = p_159717_.m_159777_();
      float f = (float)(random.nextInt(3) + 4);

      for(int i = 0; f > 0.5F; --i) {
         for(int j = Mth.m_14143_(-f); j <= Mth.m_14167_(f); ++j) {
            for(int k = Mth.m_14143_(-f); k <= Mth.m_14167_(f); ++k) {
               if ((float)(j * j + k * k) <= (f + 1.0F) * (f + 1.0F)) {
                  this.m_5974_(worldgenlevel, blockpos.m_142082_(j, i, k), Blocks.f_50259_.m_49966_());
               }
            }
         }

         f = (float)((double)f - ((double)random.nextInt(2) + 0.5D));
      }

      return true;
   }
}