package net.minecraft.world.level.block.grower;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public abstract class AbstractTreeGrower {
   @Nullable
   protected abstract ConfiguredFeature<TreeConfiguration, ?> m_6486_(Random p_60014_, boolean p_60015_);

   public boolean m_6334_(ServerLevel p_60006_, ChunkGenerator p_60007_, BlockPos p_60008_, BlockState p_60009_, Random p_60010_) {
      ConfiguredFeature<TreeConfiguration, ?> configuredfeature = this.m_6486_(p_60010_, this.m_60011_(p_60006_, p_60008_));
      if (configuredfeature == null) {
         return false;
      } else {
         p_60006_.m_7731_(p_60008_, Blocks.f_50016_.m_49966_(), 4);
         if (configuredfeature.m_65385_(p_60006_, p_60007_, p_60010_, p_60008_)) {
            return true;
         } else {
            p_60006_.m_7731_(p_60008_, p_60009_, 4);
            return false;
         }
      }
   }

   private boolean m_60011_(LevelAccessor p_60012_, BlockPos p_60013_) {
      for(BlockPos blockpos : BlockPos.MutableBlockPos.m_121940_(p_60013_.m_7495_().m_142390_(2).m_142386_(2), p_60013_.m_7494_().m_142383_(2).m_142385_(2))) {
         if (p_60012_.m_8055_(blockpos).m_60620_(BlockTags.f_13041_)) {
            return true;
         }
      }

      return false;
   }
}