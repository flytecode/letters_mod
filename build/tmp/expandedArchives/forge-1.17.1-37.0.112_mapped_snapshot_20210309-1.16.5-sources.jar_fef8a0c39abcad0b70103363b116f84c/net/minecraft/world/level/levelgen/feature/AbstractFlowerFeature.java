package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public abstract class AbstractFlowerFeature<U extends FeatureConfiguration> extends Feature<U> {
   public AbstractFlowerFeature(Codec<U> p_65075_) {
      super(p_65075_);
   }

   public boolean m_142674_(FeaturePlaceContext<U> p_159434_) {
      Random random = p_159434_.m_159776_();
      BlockPos blockpos = p_159434_.m_159777_();
      WorldGenLevel worldgenlevel = p_159434_.m_159774_();
      U u = p_159434_.m_159778_();
      BlockState blockstate = this.m_7973_(random, blockpos, u);
      int i = 0;

      for(int j = 0; j < this.m_6340_(u); ++j) {
         BlockPos blockpos1 = this.m_5543_(random, blockpos, u);
         if (worldgenlevel.m_46859_(blockpos1) && blockstate.m_60710_(worldgenlevel, blockpos1) && this.m_7007_(worldgenlevel, blockpos1, u)) {
            worldgenlevel.m_7731_(blockpos1, blockstate, 2);
            ++i;
         }
      }

      return i > 0;
   }

   public abstract boolean m_7007_(LevelAccessor p_65076_, BlockPos p_65077_, U p_65078_);

   public abstract int m_6340_(U p_65085_);

   public abstract BlockPos m_5543_(Random p_65086_, BlockPos p_65087_, U p_65088_);

   public abstract BlockState m_7973_(Random p_65089_, BlockPos p_65090_, U p_65091_);
}