package net.minecraft.world.level.block.grower;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public abstract class AbstractMegaTreeGrower extends AbstractTreeGrower {
   public boolean m_6334_(ServerLevel p_59985_, ChunkGenerator p_59986_, BlockPos p_59987_, BlockState p_59988_, Random p_59989_) {
      for(int i = 0; i >= -1; --i) {
         for(int j = 0; j >= -1; --j) {
            if (m_59998_(p_59988_, p_59985_, p_59987_, i, j)) {
               return this.m_59990_(p_59985_, p_59986_, p_59987_, p_59988_, p_59989_, i, j);
            }
         }
      }

      return super.m_6334_(p_59985_, p_59986_, p_59987_, p_59988_, p_59989_);
   }

   @Nullable
   protected abstract ConfiguredFeature<TreeConfiguration, ?> m_8111_(Random p_60004_);

   public boolean m_59990_(ServerLevel p_59991_, ChunkGenerator p_59992_, BlockPos p_59993_, BlockState p_59994_, Random p_59995_, int p_59996_, int p_59997_) {
      ConfiguredFeature<TreeConfiguration, ?> configuredfeature = this.m_8111_(p_59995_);
      if (configuredfeature == null) {
         return false;
      } else {
         BlockState blockstate = Blocks.f_50016_.m_49966_();
         p_59991_.m_7731_(p_59993_.m_142082_(p_59996_, 0, p_59997_), blockstate, 4);
         p_59991_.m_7731_(p_59993_.m_142082_(p_59996_ + 1, 0, p_59997_), blockstate, 4);
         p_59991_.m_7731_(p_59993_.m_142082_(p_59996_, 0, p_59997_ + 1), blockstate, 4);
         p_59991_.m_7731_(p_59993_.m_142082_(p_59996_ + 1, 0, p_59997_ + 1), blockstate, 4);
         if (configuredfeature.m_65385_(p_59991_, p_59992_, p_59995_, p_59993_.m_142082_(p_59996_, 0, p_59997_))) {
            return true;
         } else {
            p_59991_.m_7731_(p_59993_.m_142082_(p_59996_, 0, p_59997_), p_59994_, 4);
            p_59991_.m_7731_(p_59993_.m_142082_(p_59996_ + 1, 0, p_59997_), p_59994_, 4);
            p_59991_.m_7731_(p_59993_.m_142082_(p_59996_, 0, p_59997_ + 1), p_59994_, 4);
            p_59991_.m_7731_(p_59993_.m_142082_(p_59996_ + 1, 0, p_59997_ + 1), p_59994_, 4);
            return false;
         }
      }
   }

   public static boolean m_59998_(BlockState p_59999_, BlockGetter p_60000_, BlockPos p_60001_, int p_60002_, int p_60003_) {
      Block block = p_59999_.m_60734_();
      return p_60000_.m_8055_(p_60001_.m_142082_(p_60002_, 0, p_60003_)).m_60713_(block) && p_60000_.m_8055_(p_60001_.m_142082_(p_60002_ + 1, 0, p_60003_)).m_60713_(block) && p_60000_.m_8055_(p_60001_.m_142082_(p_60002_, 0, p_60003_ + 1)).m_60713_(block) && p_60000_.m_8055_(p_60001_.m_142082_(p_60002_ + 1, 0, p_60003_ + 1)).m_60713_(block);
   }
}