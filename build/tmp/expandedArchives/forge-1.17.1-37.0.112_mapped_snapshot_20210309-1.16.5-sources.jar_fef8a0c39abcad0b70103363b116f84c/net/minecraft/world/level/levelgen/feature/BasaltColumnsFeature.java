package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;

public class BasaltColumnsFeature extends Feature<ColumnFeatureConfiguration> {
   private static final ImmutableList<Block> f_65150_ = ImmutableList.of(Blocks.f_49991_, Blocks.f_50752_, Blocks.f_50450_, Blocks.f_50135_, Blocks.f_50197_, Blocks.f_50198_, Blocks.f_50199_, Blocks.f_50200_, Blocks.f_50087_, Blocks.f_50085_);
   private static final int f_159439_ = 5;
   private static final int f_159440_ = 50;
   private static final int f_159441_ = 8;
   private static final int f_159442_ = 15;

   public BasaltColumnsFeature(Codec<ColumnFeatureConfiguration> p_65153_) {
      super(p_65153_);
   }

   public boolean m_142674_(FeaturePlaceContext<ColumnFeatureConfiguration> p_159444_) {
      int i = p_159444_.m_159775_().m_6337_();
      BlockPos blockpos = p_159444_.m_159777_();
      WorldGenLevel worldgenlevel = p_159444_.m_159774_();
      Random random = p_159444_.m_159776_();
      ColumnFeatureConfiguration columnfeatureconfiguration = p_159444_.m_159778_();
      if (!m_65154_(worldgenlevel, i, blockpos.m_122032_())) {
         return false;
      } else {
         int j = columnfeatureconfiguration.m_160720_().m_142270_(random);
         boolean flag = random.nextFloat() < 0.9F;
         int k = Math.min(j, flag ? 5 : 8);
         int l = flag ? 50 : 15;
         boolean flag1 = false;

         for(BlockPos blockpos1 : BlockPos.m_121957_(random, l, blockpos.m_123341_() - k, blockpos.m_123342_(), blockpos.m_123343_() - k, blockpos.m_123341_() + k, blockpos.m_123342_(), blockpos.m_123343_() + k)) {
            int i1 = j - blockpos1.m_123333_(blockpos);
            if (i1 >= 0) {
               flag1 |= this.m_65167_(worldgenlevel, i, blockpos1, i1, columnfeatureconfiguration.m_160717_().m_142270_(random));
            }
         }

         return flag1;
      }
   }

   private boolean m_65167_(LevelAccessor p_65168_, int p_65169_, BlockPos p_65170_, int p_65171_, int p_65172_) {
      boolean flag = false;

      for(BlockPos blockpos : BlockPos.m_121976_(p_65170_.m_123341_() - p_65172_, p_65170_.m_123342_(), p_65170_.m_123343_() - p_65172_, p_65170_.m_123341_() + p_65172_, p_65170_.m_123342_(), p_65170_.m_123343_() + p_65172_)) {
         int i = blockpos.m_123333_(p_65170_);
         BlockPos blockpos1 = m_65163_(p_65168_, p_65169_, blockpos) ? m_65158_(p_65168_, p_65169_, blockpos.m_122032_(), i) : m_65173_(p_65168_, blockpos.m_122032_(), i);
         if (blockpos1 != null) {
            int j = p_65171_ - i / 2;

            for(BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos1.m_122032_(); j >= 0; --j) {
               if (m_65163_(p_65168_, p_65169_, blockpos$mutableblockpos)) {
                  this.m_5974_(p_65168_, blockpos$mutableblockpos, Blocks.f_50137_.m_49966_());
                  blockpos$mutableblockpos.m_122173_(Direction.UP);
                  flag = true;
               } else {
                  if (!p_65168_.m_8055_(blockpos$mutableblockpos).m_60713_(Blocks.f_50137_)) {
                     break;
                  }

                  blockpos$mutableblockpos.m_122173_(Direction.UP);
               }
            }
         }
      }

      return flag;
   }

   @Nullable
   private static BlockPos m_65158_(LevelAccessor p_65159_, int p_65160_, BlockPos.MutableBlockPos p_65161_, int p_65162_) {
      while(p_65161_.m_123342_() > p_65159_.m_141937_() + 1 && p_65162_ > 0) {
         --p_65162_;
         if (m_65154_(p_65159_, p_65160_, p_65161_)) {
            return p_65161_;
         }

         p_65161_.m_122173_(Direction.DOWN);
      }

      return null;
   }

   private static boolean m_65154_(LevelAccessor p_65155_, int p_65156_, BlockPos.MutableBlockPos p_65157_) {
      if (!m_65163_(p_65155_, p_65156_, p_65157_)) {
         return false;
      } else {
         BlockState blockstate = p_65155_.m_8055_(p_65157_.m_122173_(Direction.DOWN));
         p_65157_.m_122173_(Direction.UP);
         return !blockstate.m_60795_() && !f_65150_.contains(blockstate.m_60734_());
      }
   }

   @Nullable
   private static BlockPos m_65173_(LevelAccessor p_65174_, BlockPos.MutableBlockPos p_65175_, int p_65176_) {
      while(p_65175_.m_123342_() < p_65174_.m_151558_() && p_65176_ > 0) {
         --p_65176_;
         BlockState blockstate = p_65174_.m_8055_(p_65175_);
         if (f_65150_.contains(blockstate.m_60734_())) {
            return null;
         }

         if (blockstate.m_60795_()) {
            return p_65175_;
         }

         p_65175_.m_122173_(Direction.UP);
      }

      return null;
   }

   private static boolean m_65163_(LevelAccessor p_65164_, int p_65165_, BlockPos p_65166_) {
      BlockState blockstate = p_65164_.m_8055_(p_65166_);
      return blockstate.m_60795_() || blockstate.m_60713_(Blocks.f_49991_) && p_65166_.m_123342_() <= p_65165_;
   }
}