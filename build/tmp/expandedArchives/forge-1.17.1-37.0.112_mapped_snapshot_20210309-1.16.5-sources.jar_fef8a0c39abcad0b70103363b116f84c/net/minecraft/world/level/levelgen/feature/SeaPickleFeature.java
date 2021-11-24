package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;

public class SeaPickleFeature extends Feature<CountConfiguration> {
   public SeaPickleFeature(Codec<CountConfiguration> p_66754_) {
      super(p_66754_);
   }

   public boolean m_142674_(FeaturePlaceContext<CountConfiguration> p_160316_) {
      int i = 0;
      Random random = p_160316_.m_159776_();
      WorldGenLevel worldgenlevel = p_160316_.m_159774_();
      BlockPos blockpos = p_160316_.m_159777_();
      int j = p_160316_.m_159778_().m_160725_().m_142270_(random);

      for(int k = 0; k < j; ++k) {
         int l = random.nextInt(8) - random.nextInt(8);
         int i1 = random.nextInt(8) - random.nextInt(8);
         int j1 = worldgenlevel.m_6924_(Heightmap.Types.OCEAN_FLOOR, blockpos.m_123341_() + l, blockpos.m_123343_() + i1);
         BlockPos blockpos1 = new BlockPos(blockpos.m_123341_() + l, j1, blockpos.m_123343_() + i1);
         BlockState blockstate = Blocks.f_50567_.m_49966_().m_61124_(SeaPickleBlock.f_56074_, Integer.valueOf(random.nextInt(4) + 1));
         if (worldgenlevel.m_8055_(blockpos1).m_60713_(Blocks.f_49990_) && blockstate.m_60710_(worldgenlevel, blockpos1)) {
            worldgenlevel.m_7731_(blockpos1, blockstate, 2);
            ++i;
         }
      }

      return i > 0;
   }
}