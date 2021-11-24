package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.SmallDripstoneConfiguration;

public class SmallDripstoneFeature extends Feature<SmallDripstoneConfiguration> {
   public SmallDripstoneFeature(Codec<SmallDripstoneConfiguration> p_160345_) {
      super(p_160345_);
   }

   public boolean m_142674_(FeaturePlaceContext<SmallDripstoneConfiguration> p_160362_) {
      WorldGenLevel worldgenlevel = p_160362_.m_159774_();
      BlockPos blockpos = p_160362_.m_159777_();
      Random random = p_160362_.m_159776_();
      SmallDripstoneConfiguration smalldripstoneconfiguration = p_160362_.m_159778_();
      if (!DripstoneUtils.m_159628_(worldgenlevel, blockpos)) {
         return false;
      } else {
         int i = Mth.m_144928_(random, 1, smalldripstoneconfiguration.f_161170_);
         boolean flag = false;

         for(int j = 0; j < i; ++j) {
            BlockPos blockpos1 = m_160363_(random, blockpos, smalldripstoneconfiguration);
            if (m_160350_(worldgenlevel, random, blockpos1, smalldripstoneconfiguration)) {
               flag = true;
            }
         }

         return flag;
      }
   }

   private static boolean m_160350_(WorldGenLevel p_160351_, Random p_160352_, BlockPos p_160353_, SmallDripstoneConfiguration p_160354_) {
      Direction direction = Direction.m_122404_(p_160352_);
      Direction direction1 = p_160352_.nextBoolean() ? Direction.UP : Direction.DOWN;
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_160353_.m_122032_();

      for(int i = 0; i < p_160354_.f_161171_; ++i) {
         if (!DripstoneUtils.m_159628_(p_160351_, blockpos$mutableblockpos)) {
            return false;
         }

         if (m_160355_(p_160351_, p_160352_, blockpos$mutableblockpos, direction1, p_160354_)) {
            return true;
         }

         if (m_160355_(p_160351_, p_160352_, blockpos$mutableblockpos, direction1.m_122424_(), p_160354_)) {
            return true;
         }

         blockpos$mutableblockpos.m_122173_(direction);
      }

      return false;
   }

   private static boolean m_160355_(WorldGenLevel p_160356_, Random p_160357_, BlockPos p_160358_, Direction p_160359_, SmallDripstoneConfiguration p_160360_) {
      if (!DripstoneUtils.m_159628_(p_160356_, p_160358_)) {
         return false;
      } else {
         BlockPos blockpos = p_160358_.m_142300_(p_160359_.m_122424_());
         BlockState blockstate = p_160356_.m_8055_(blockpos);
         if (!DripstoneUtils.m_159662_(blockstate)) {
            return false;
         } else {
            m_160346_(p_160356_, p_160357_, blockpos);
            int i = p_160357_.nextFloat() < p_160360_.f_161173_ && DripstoneUtils.m_159628_(p_160356_, p_160358_.m_142300_(p_160359_)) ? 2 : 1;
            DripstoneUtils.m_159643_(p_160356_, p_160358_, p_160359_, i, false);
            return true;
         }
      }
   }

   private static void m_160346_(WorldGenLevel p_160347_, Random p_160348_, BlockPos p_160349_) {
      DripstoneUtils.m_159636_(p_160347_, p_160349_);

      for(Direction direction : Direction.Plane.HORIZONTAL) {
         if (!(p_160348_.nextFloat() < 0.3F)) {
            BlockPos blockpos = p_160349_.m_142300_(direction);
            DripstoneUtils.m_159636_(p_160347_, blockpos);
            if (!p_160348_.nextBoolean()) {
               BlockPos blockpos1 = blockpos.m_142300_(Direction.m_122404_(p_160348_));
               DripstoneUtils.m_159636_(p_160347_, blockpos1);
               if (!p_160348_.nextBoolean()) {
                  BlockPos blockpos2 = blockpos1.m_142300_(Direction.m_122404_(p_160348_));
                  DripstoneUtils.m_159636_(p_160347_, blockpos2);
               }
            }
         }
      }

   }

   private static BlockPos m_160363_(Random p_160364_, BlockPos p_160365_, SmallDripstoneConfiguration p_160366_) {
      return p_160365_.m_142082_(Mth.m_144928_(p_160364_, -p_160366_.f_161172_, p_160366_.f_161172_), Mth.m_144928_(p_160364_, -p_160366_.f_161172_, p_160366_.f_161172_), Mth.m_144928_(p_160364_, -p_160366_.f_161172_, p_160366_.f_161172_));
   }
}