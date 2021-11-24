package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WeepingVinesFeature extends Feature<NoneFeatureConfiguration> {
   private static final Direction[] f_67372_ = Direction.values();

   public WeepingVinesFeature(Codec<NoneFeatureConfiguration> p_67375_) {
      super(p_67375_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_160661_) {
      WorldGenLevel worldgenlevel = p_160661_.m_159774_();
      BlockPos blockpos = p_160661_.m_159777_();
      Random random = p_160661_.m_159776_();
      if (!worldgenlevel.m_46859_(blockpos)) {
         return false;
      } else {
         BlockState blockstate = worldgenlevel.m_8055_(blockpos.m_7494_());
         if (!blockstate.m_60713_(Blocks.f_50134_) && !blockstate.m_60713_(Blocks.f_50451_)) {
            return false;
         } else {
            this.m_67383_(worldgenlevel, random, blockpos);
            this.m_67399_(worldgenlevel, random, blockpos);
            return true;
         }
      }
   }

   private void m_67383_(LevelAccessor p_67384_, Random p_67385_, BlockPos p_67386_) {
      p_67384_.m_7731_(p_67386_, Blocks.f_50451_.m_49966_(), 2);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

      for(int i = 0; i < 200; ++i) {
         blockpos$mutableblockpos.m_122154_(p_67386_, p_67385_.nextInt(6) - p_67385_.nextInt(6), p_67385_.nextInt(2) - p_67385_.nextInt(5), p_67385_.nextInt(6) - p_67385_.nextInt(6));
         if (p_67384_.m_46859_(blockpos$mutableblockpos)) {
            int j = 0;

            for(Direction direction : f_67372_) {
               BlockState blockstate = p_67384_.m_8055_(blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos, direction));
               if (blockstate.m_60713_(Blocks.f_50134_) || blockstate.m_60713_(Blocks.f_50451_)) {
                  ++j;
               }

               if (j > 1) {
                  break;
               }
            }

            if (j == 1) {
               p_67384_.m_7731_(blockpos$mutableblockpos, Blocks.f_50451_.m_49966_(), 2);
            }
         }
      }

   }

   private void m_67399_(LevelAccessor p_67400_, Random p_67401_, BlockPos p_67402_) {
      BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

      for(int i = 0; i < 100; ++i) {
         blockpos$mutableblockpos.m_122154_(p_67402_, p_67401_.nextInt(8) - p_67401_.nextInt(8), p_67401_.nextInt(2) - p_67401_.nextInt(7), p_67401_.nextInt(8) - p_67401_.nextInt(8));
         if (p_67400_.m_46859_(blockpos$mutableblockpos)) {
            BlockState blockstate = p_67400_.m_8055_(blockpos$mutableblockpos.m_7494_());
            if (blockstate.m_60713_(Blocks.f_50134_) || blockstate.m_60713_(Blocks.f_50451_)) {
               int j = Mth.m_14072_(p_67401_, 1, 8);
               if (p_67401_.nextInt(6) == 0) {
                  j *= 2;
               }

               if (p_67401_.nextInt(5) == 0) {
                  j = 1;
               }

               int k = 17;
               int l = 25;
               m_67376_(p_67400_, p_67401_, blockpos$mutableblockpos, j, 17, 25);
            }
         }
      }

   }

   public static void m_67376_(LevelAccessor p_67377_, Random p_67378_, BlockPos.MutableBlockPos p_67379_, int p_67380_, int p_67381_, int p_67382_) {
      for(int i = 0; i <= p_67380_; ++i) {
         if (p_67377_.m_46859_(p_67379_)) {
            if (i == p_67380_ || !p_67377_.m_46859_(p_67379_.m_7495_())) {
               p_67377_.m_7731_(p_67379_, Blocks.f_50702_.m_49966_().m_61124_(GrowingPlantHeadBlock.f_53924_, Integer.valueOf(Mth.m_14072_(p_67378_, p_67381_, p_67382_))), 2);
               break;
            }

            p_67377_.m_7731_(p_67379_, Blocks.f_50703_.m_49966_(), 2);
         }

         p_67379_.m_122173_(Direction.DOWN);
      }

   }
}