package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public abstract class AbstractHugeMushroomFeature extends Feature<HugeMushroomFeatureConfiguration> {
   public AbstractHugeMushroomFeature(Codec<HugeMushroomFeatureConfiguration> p_65093_) {
      super(p_65093_);
   }

   protected void m_65110_(LevelAccessor p_65111_, Random p_65112_, BlockPos p_65113_, HugeMushroomFeatureConfiguration p_65114_, int p_65115_, BlockPos.MutableBlockPos p_65116_) {
      for(int i = 0; i < p_65115_; ++i) {
         p_65116_.m_122190_(p_65113_).m_122175_(Direction.UP, i);
         if (!p_65111_.m_8055_(p_65116_).m_60804_(p_65111_, p_65116_)) {
            this.m_5974_(p_65111_, p_65116_, p_65114_.f_67741_.m_7112_(p_65112_, p_65113_));
         }
      }

   }

   protected int m_65129_(Random p_65130_) {
      int i = p_65130_.nextInt(3) + 4;
      if (p_65130_.nextInt(12) == 0) {
         i *= 2;
      }

      return i;
   }

   protected boolean m_65098_(LevelAccessor p_65099_, BlockPos p_65100_, int p_65101_, BlockPos.MutableBlockPos p_65102_, HugeMushroomFeatureConfiguration p_65103_) {
      int i = p_65100_.m_123342_();
      if (i >= p_65099_.m_141937_() + 1 && i + p_65101_ + 1 < p_65099_.m_151558_()) {
         BlockState blockstate = p_65099_.m_8055_(p_65100_.m_7495_());
         if (!m_159759_(blockstate) && !blockstate.m_60620_(BlockTags.f_13057_)) {
            return false;
         } else {
            for(int j = 0; j <= p_65101_; ++j) {
               int k = this.m_6794_(-1, -1, p_65103_.f_67742_, j);

               for(int l = -k; l <= k; ++l) {
                  for(int i1 = -k; i1 <= k; ++i1) {
                     BlockState blockstate1 = p_65099_.m_8055_(p_65102_.m_122154_(p_65100_, l, j, i1));
                     if (!blockstate1.m_60795_() && !blockstate1.m_60620_(BlockTags.f_13035_)) {
                        return false;
                     }
                  }
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public boolean m_142674_(FeaturePlaceContext<HugeMushroomFeatureConfiguration> p_159436_) {
      WorldGenLevel worldgenlevel = p_159436_.m_159774_();
      BlockPos blockpos = p_159436_.m_159777_();
      Random random = p_159436_.m_159776_();
      HugeMushroomFeatureConfiguration hugemushroomfeatureconfiguration = p_159436_.m_159778_();
      int i = this.m_65129_(random);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      if (!this.m_65098_(worldgenlevel, blockpos, i, blockpos$mutableblockpos, hugemushroomfeatureconfiguration)) {
         return false;
      } else {
         this.m_6152_(worldgenlevel, random, blockpos, i, blockpos$mutableblockpos, hugemushroomfeatureconfiguration);
         this.m_65110_(worldgenlevel, random, blockpos, hugemushroomfeatureconfiguration, i, blockpos$mutableblockpos);
         return true;
      }
   }

   protected abstract int m_6794_(int p_65094_, int p_65095_, int p_65096_, int p_65097_);

   protected abstract void m_6152_(LevelAccessor p_65104_, Random p_65105_, BlockPos p_65106_, int p_65107_, BlockPos.MutableBlockPos p_65108_, HugeMushroomFeatureConfiguration p_65109_);
}