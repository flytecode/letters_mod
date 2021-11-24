package net.minecraft.world.level.levelgen.feature;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class CoralClawFeature extends CoralFeature {
   public CoralClawFeature(Codec<NoneFeatureConfiguration> p_65422_) {
      super(p_65422_);
   }

   protected boolean m_7014_(LevelAccessor p_65424_, Random p_65425_, BlockPos p_65426_, BlockState p_65427_) {
      if (!this.m_65446_(p_65424_, p_65425_, p_65426_, p_65427_)) {
         return false;
      } else {
         Direction direction = Direction.Plane.HORIZONTAL.m_122560_(p_65425_);
         int i = p_65425_.nextInt(2) + 2;
         List<Direction> list = Lists.newArrayList(direction, direction.m_122427_(), direction.m_122428_());
         Collections.shuffle(list, p_65425_);

         for(Direction direction1 : list.subList(0, i)) {
            BlockPos.MutableBlockPos blockpos$mutableblockpos = p_65426_.m_122032_();
            int j = p_65425_.nextInt(2) + 1;
            blockpos$mutableblockpos.m_122173_(direction1);
            int k;
            Direction direction2;
            if (direction1 == direction) {
               direction2 = direction;
               k = p_65425_.nextInt(3) + 2;
            } else {
               blockpos$mutableblockpos.m_122173_(Direction.UP);
               Direction[] adirection = new Direction[]{direction1, Direction.UP};
               direction2 = Util.m_137545_(adirection, p_65425_);
               k = p_65425_.nextInt(3) + 3;
            }

            for(int l = 0; l < j && this.m_65446_(p_65424_, p_65425_, blockpos$mutableblockpos, p_65427_); ++l) {
               blockpos$mutableblockpos.m_122173_(direction2);
            }

            blockpos$mutableblockpos.m_122173_(direction2.m_122424_());
            blockpos$mutableblockpos.m_122173_(Direction.UP);

            for(int i1 = 0; i1 < k; ++i1) {
               blockpos$mutableblockpos.m_122173_(direction);
               if (!this.m_65446_(p_65424_, p_65425_, blockpos$mutableblockpos, p_65427_)) {
                  break;
               }

               if (p_65425_.nextFloat() < 0.25F) {
                  blockpos$mutableblockpos.m_122173_(Direction.UP);
               }
            }
         }

         return true;
      }
   }
}