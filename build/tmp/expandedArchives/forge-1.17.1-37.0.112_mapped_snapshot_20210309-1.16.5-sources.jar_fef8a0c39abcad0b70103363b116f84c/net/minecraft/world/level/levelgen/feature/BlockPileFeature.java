package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;

public class BlockPileFeature extends Feature<BlockPileConfiguration> {
   public BlockPileFeature(Codec<BlockPileConfiguration> p_65262_) {
      super(p_65262_);
   }

   public boolean m_142674_(FeaturePlaceContext<BlockPileConfiguration> p_159473_) {
      BlockPos blockpos = p_159473_.m_159777_();
      WorldGenLevel worldgenlevel = p_159473_.m_159774_();
      Random random = p_159473_.m_159776_();
      BlockPileConfiguration blockpileconfiguration = p_159473_.m_159778_();
      if (blockpos.m_123342_() < worldgenlevel.m_141937_() + 5) {
         return false;
      } else {
         int i = 2 + random.nextInt(2);
         int j = 2 + random.nextInt(2);

         for(BlockPos blockpos1 : BlockPos.m_121940_(blockpos.m_142082_(-i, 0, -j), blockpos.m_142082_(i, 1, j))) {
            int k = blockpos.m_123341_() - blockpos1.m_123341_();
            int l = blockpos.m_123343_() - blockpos1.m_123343_();
            if ((float)(k * k + l * l) <= random.nextFloat() * 10.0F - random.nextFloat() * 6.0F) {
               this.m_65267_(worldgenlevel, blockpos1, random, blockpileconfiguration);
            } else if ((double)random.nextFloat() < 0.031D) {
               this.m_65267_(worldgenlevel, blockpos1, random, blockpileconfiguration);
            }
         }

         return true;
      }
   }

   private boolean m_65263_(LevelAccessor p_65264_, BlockPos p_65265_, Random p_65266_) {
      BlockPos blockpos = p_65265_.m_7495_();
      BlockState blockstate = p_65264_.m_8055_(blockpos);
      return blockstate.m_60713_(Blocks.f_152481_) ? p_65266_.nextBoolean() : blockstate.m_60783_(p_65264_, blockpos, Direction.UP);
   }

   private void m_65267_(LevelAccessor p_65268_, BlockPos p_65269_, Random p_65270_, BlockPileConfiguration p_65271_) {
      if (p_65268_.m_46859_(p_65269_) && this.m_65263_(p_65268_, p_65269_, p_65270_)) {
         p_65268_.m_7731_(p_65269_, p_65271_.f_67540_.m_7112_(p_65270_, p_65269_), 4);
      }

   }
}