package net.minecraft.world.level.levelgen.placement.nether;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;
import net.minecraft.world.level.levelgen.placement.DecorationContext;
import net.minecraft.world.level.levelgen.placement.FeatureDecorator;

public class CountMultiLayerDecorator extends FeatureDecorator<CountConfiguration> {
   public CountMultiLayerDecorator(Codec<CountConfiguration> p_70892_) {
      super(p_70892_);
   }

   public Stream<BlockPos> m_7887_(DecorationContext p_70902_, Random p_70903_, CountConfiguration p_70904_, BlockPos p_70905_) {
      List<BlockPos> list = Lists.newArrayList();
      int i = 0;

      boolean flag;
      do {
         flag = false;

         for(int j = 0; j < p_70904_.m_160725_().m_142270_(p_70903_); ++j) {
            int k = p_70903_.nextInt(16) + p_70905_.m_123341_();
            int l = p_70903_.nextInt(16) + p_70905_.m_123343_();
            int i1 = p_70902_.m_70590_(Heightmap.Types.MOTION_BLOCKING, k, l);
            int j1 = m_70895_(p_70902_, k, i1, l, i);
            if (j1 != Integer.MAX_VALUE) {
               list.add(new BlockPos(k, j1, l));
               flag = true;
            }
         }

         ++i;
      } while(flag);

      return list.stream();
   }

   private static int m_70895_(DecorationContext p_70896_, int p_70897_, int p_70898_, int p_70899_, int p_70900_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(p_70897_, p_70898_, p_70899_);
      int i = 0;
      BlockState blockstate = p_70896_.m_70594_(blockpos$mutableblockpos);

      for(int j = p_70898_; j >= p_70896_.m_162167_() + 1; --j) {
         blockpos$mutableblockpos.m_142448_(j - 1);
         BlockState blockstate1 = p_70896_.m_70594_(blockpos$mutableblockpos);
         if (!m_70893_(blockstate1) && m_70893_(blockstate) && !blockstate1.m_60713_(Blocks.f_50752_)) {
            if (i == p_70900_) {
               return blockpos$mutableblockpos.m_123342_() + 1;
            }

            ++i;
         }

         blockstate = blockstate1;
      }

      return Integer.MAX_VALUE;
   }

   private static boolean m_70893_(BlockState p_70894_) {
      return p_70894_.m_60795_() || p_70894_.m_60713_(Blocks.f_49990_) || p_70894_.m_60713_(Blocks.f_49991_);
   }
}