package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;

public class DeltaFeature extends Feature<DeltaFeatureConfiguration> {
   private static final ImmutableList<Block> f_65546_ = ImmutableList.of(Blocks.f_50752_, Blocks.f_50197_, Blocks.f_50198_, Blocks.f_50199_, Blocks.f_50200_, Blocks.f_50087_, Blocks.f_50085_);
   private static final Direction[] f_65547_ = Direction.values();
   private static final double f_159546_ = 0.9D;

   public DeltaFeature(Codec<DeltaFeatureConfiguration> p_65550_) {
      super(p_65550_);
   }

   public boolean m_142674_(FeaturePlaceContext<DeltaFeatureConfiguration> p_159548_) {
      boolean flag = false;
      Random random = p_159548_.m_159776_();
      WorldGenLevel worldgenlevel = p_159548_.m_159774_();
      DeltaFeatureConfiguration deltafeatureconfiguration = p_159548_.m_159778_();
      BlockPos blockpos = p_159548_.m_159777_();
      boolean flag1 = random.nextDouble() < 0.9D;
      int i = flag1 ? deltafeatureconfiguration.m_160744_().m_142270_(random) : 0;
      int j = flag1 ? deltafeatureconfiguration.m_160744_().m_142270_(random) : 0;
      boolean flag2 = flag1 && i != 0 && j != 0;
      int k = deltafeatureconfiguration.m_160741_().m_142270_(random);
      int l = deltafeatureconfiguration.m_160741_().m_142270_(random);
      int i1 = Math.max(k, l);

      for(BlockPos blockpos1 : BlockPos.m_121925_(blockpos, k, 0, l)) {
         if (blockpos1.m_123333_(blockpos) > i1) {
            break;
         }

         if (m_65551_(worldgenlevel, blockpos1, deltafeatureconfiguration)) {
            if (flag2) {
               flag = true;
               this.m_5974_(worldgenlevel, blockpos1, deltafeatureconfiguration.m_67611_());
            }

            BlockPos blockpos2 = blockpos1.m_142082_(i, 0, j);
            if (m_65551_(worldgenlevel, blockpos2, deltafeatureconfiguration)) {
               flag = true;
               this.m_5974_(worldgenlevel, blockpos2, deltafeatureconfiguration.m_67608_());
            }
         }
      }

      return flag;
   }

   private static boolean m_65551_(LevelAccessor p_65552_, BlockPos p_65553_, DeltaFeatureConfiguration p_65554_) {
      BlockState blockstate = p_65552_.m_8055_(p_65553_);
      if (blockstate.m_60713_(p_65554_.m_67608_().m_60734_())) {
         return false;
      } else if (f_65546_.contains(blockstate.m_60734_())) {
         return false;
      } else {
         for(Direction direction : f_65547_) {
            boolean flag = p_65552_.m_8055_(p_65553_.m_142300_(direction)).m_60795_();
            if (flag && direction != Direction.UP || !flag && direction == Direction.UP) {
               return false;
            }
         }

         return true;
      }
   }
}