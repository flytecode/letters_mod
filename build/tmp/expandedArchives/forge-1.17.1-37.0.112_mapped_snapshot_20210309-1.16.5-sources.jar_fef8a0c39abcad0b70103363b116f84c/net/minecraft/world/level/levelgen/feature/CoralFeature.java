package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseCoralWallFanBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SeaPickleBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public abstract class CoralFeature extends Feature<NoneFeatureConfiguration> {
   public CoralFeature(Codec<NoneFeatureConfiguration> p_65429_) {
      super(p_65429_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159536_) {
      Random random = p_159536_.m_159776_();
      WorldGenLevel worldgenlevel = p_159536_.m_159774_();
      BlockPos blockpos = p_159536_.m_159777_();
      BlockState blockstate = BlockTags.f_13051_.m_13288_(random).m_49966_();
      return this.m_7014_(worldgenlevel, random, blockpos, blockstate);
   }

   protected abstract boolean m_7014_(LevelAccessor p_65430_, Random p_65431_, BlockPos p_65432_, BlockState p_65433_);

   protected boolean m_65446_(LevelAccessor p_65447_, Random p_65448_, BlockPos p_65449_, BlockState p_65450_) {
      BlockPos blockpos = p_65449_.m_7494_();
      BlockState blockstate = p_65447_.m_8055_(p_65449_);
      if ((blockstate.m_60713_(Blocks.f_49990_) || blockstate.m_60620_(BlockTags.f_13064_)) && p_65447_.m_8055_(blockpos).m_60713_(Blocks.f_49990_)) {
         p_65447_.m_7731_(p_65449_, p_65450_, 3);
         if (p_65448_.nextFloat() < 0.25F) {
            p_65447_.m_7731_(blockpos, BlockTags.f_13064_.m_13288_(p_65448_).m_49966_(), 2);
         } else if (p_65448_.nextFloat() < 0.05F) {
            p_65447_.m_7731_(blockpos, Blocks.f_50567_.m_49966_().m_61124_(SeaPickleBlock.f_56074_, Integer.valueOf(p_65448_.nextInt(4) + 1)), 2);
         }

         for(Direction direction : Direction.Plane.HORIZONTAL) {
            if (p_65448_.nextFloat() < 0.2F) {
               BlockPos blockpos1 = p_65449_.m_142300_(direction);
               if (p_65447_.m_8055_(blockpos1).m_60713_(Blocks.f_49990_)) {
                  BlockState blockstate1 = BlockTags.f_13052_.m_13288_(p_65448_).m_49966_();
                  if (blockstate1.m_61138_(BaseCoralWallFanBlock.f_49192_)) {
                     blockstate1 = blockstate1.m_61124_(BaseCoralWallFanBlock.f_49192_, direction);
                  }

                  p_65447_.m_7731_(blockpos1, blockstate1, 2);
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}