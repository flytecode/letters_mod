package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BasaltPillarFeature extends Feature<NoneFeatureConfiguration> {
   public BasaltPillarFeature(Codec<NoneFeatureConfiguration> p_65190_) {
      super(p_65190_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159446_) {
      BlockPos blockpos = p_159446_.m_159777_();
      WorldGenLevel worldgenlevel = p_159446_.m_159774_();
      Random random = p_159446_.m_159776_();
      if (worldgenlevel.m_46859_(blockpos) && !worldgenlevel.m_46859_(blockpos.m_7494_())) {
         BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.m_122032_();
         BlockPos.MutableBlockPos blockpos$mutableblockpos1 = blockpos.m_122032_();
         boolean flag = true;
         boolean flag1 = true;
         boolean flag2 = true;
         boolean flag3 = true;

         while(worldgenlevel.m_46859_(blockpos$mutableblockpos)) {
            if (worldgenlevel.m_151570_(blockpos$mutableblockpos)) {
               return true;
            }

            worldgenlevel.m_7731_(blockpos$mutableblockpos, Blocks.f_50137_.m_49966_(), 2);
            flag = flag && this.m_65207_(worldgenlevel, random, blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos, Direction.NORTH));
            flag1 = flag1 && this.m_65207_(worldgenlevel, random, blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos, Direction.SOUTH));
            flag2 = flag2 && this.m_65207_(worldgenlevel, random, blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos, Direction.WEST));
            flag3 = flag3 && this.m_65207_(worldgenlevel, random, blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos, Direction.EAST));
            blockpos$mutableblockpos.m_122173_(Direction.DOWN);
         }

         blockpos$mutableblockpos.m_122173_(Direction.UP);
         this.m_65191_(worldgenlevel, random, blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos, Direction.NORTH));
         this.m_65191_(worldgenlevel, random, blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos, Direction.SOUTH));
         this.m_65191_(worldgenlevel, random, blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos, Direction.WEST));
         this.m_65191_(worldgenlevel, random, blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos, Direction.EAST));
         blockpos$mutableblockpos.m_122173_(Direction.DOWN);
         BlockPos.MutableBlockPos blockpos$mutableblockpos2 = new BlockPos.MutableBlockPos();

         for(int i = -3; i < 4; ++i) {
            for(int j = -3; j < 4; ++j) {
               int k = Mth.m_14040_(i) * Mth.m_14040_(j);
               if (random.nextInt(10) < 10 - k) {
                  blockpos$mutableblockpos2.m_122190_(blockpos$mutableblockpos.m_142082_(i, 0, j));
                  int l = 3;

                  while(worldgenlevel.m_46859_(blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos2, Direction.DOWN))) {
                     blockpos$mutableblockpos2.m_122173_(Direction.DOWN);
                     --l;
                     if (l <= 0) {
                        break;
                     }
                  }

                  if (!worldgenlevel.m_46859_(blockpos$mutableblockpos1.m_122159_(blockpos$mutableblockpos2, Direction.DOWN))) {
                     worldgenlevel.m_7731_(blockpos$mutableblockpos2, Blocks.f_50137_.m_49966_(), 2);
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private void m_65191_(LevelAccessor p_65192_, Random p_65193_, BlockPos p_65194_) {
      if (p_65193_.nextBoolean()) {
         p_65192_.m_7731_(p_65194_, Blocks.f_50137_.m_49966_(), 2);
      }

   }

   private boolean m_65207_(LevelAccessor p_65208_, Random p_65209_, BlockPos p_65210_) {
      if (p_65209_.nextInt(10) != 0) {
         p_65208_.m_7731_(p_65210_, Blocks.f_50137_.m_49966_(), 2);
         return true;
      } else {
         return false;
      }
   }
}