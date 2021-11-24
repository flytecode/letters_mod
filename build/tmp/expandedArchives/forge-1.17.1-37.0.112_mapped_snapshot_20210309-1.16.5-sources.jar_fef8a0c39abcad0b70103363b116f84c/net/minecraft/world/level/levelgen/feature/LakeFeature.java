package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.BaseStoneSource;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.material.Material;

public class LakeFeature extends Feature<BlockStateConfiguration> {
   private static final BlockState f_66256_ = Blocks.f_50627_.m_49966_();

   public LakeFeature(Codec<BlockStateConfiguration> p_66259_) {
      super(p_66259_);
   }

   public boolean m_142674_(FeaturePlaceContext<BlockStateConfiguration> p_159958_) {
      BlockPos blockpos = p_159958_.m_159777_();
      WorldGenLevel worldgenlevel = p_159958_.m_159774_();
      Random random = p_159958_.m_159776_();

      BlockStateConfiguration blockstateconfiguration;
      for(blockstateconfiguration = p_159958_.m_159778_(); blockpos.m_123342_() > worldgenlevel.m_141937_() + 5 && worldgenlevel.m_46859_(blockpos); blockpos = blockpos.m_7495_()) {
      }

      if (blockpos.m_123342_() <= worldgenlevel.m_141937_() + 4) {
         return false;
      } else {
         blockpos = blockpos.m_6625_(4);
         if (worldgenlevel.m_7526_(SectionPos.m_123199_(blockpos), StructureFeature.f_67028_).findAny().isPresent()) {
            return false;
         } else {
            boolean[] aboolean = new boolean[2048];
            int i = random.nextInt(4) + 4;

            for(int j = 0; j < i; ++j) {
               double d0 = random.nextDouble() * 6.0D + 3.0D;
               double d1 = random.nextDouble() * 4.0D + 2.0D;
               double d2 = random.nextDouble() * 6.0D + 3.0D;
               double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
               double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
               double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

               for(int l = 1; l < 15; ++l) {
                  for(int i1 = 1; i1 < 15; ++i1) {
                     for(int j1 = 1; j1 < 7; ++j1) {
                        double d6 = ((double)l - d3) / (d0 / 2.0D);
                        double d7 = ((double)j1 - d4) / (d1 / 2.0D);
                        double d8 = ((double)i1 - d5) / (d2 / 2.0D);
                        double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                        if (d9 < 1.0D) {
                           aboolean[(l * 16 + i1) * 8 + j1] = true;
                        }
                     }
                  }
               }
            }

            for(int k1 = 0; k1 < 16; ++k1) {
               for(int k2 = 0; k2 < 16; ++k2) {
                  for(int k = 0; k < 8; ++k) {
                     boolean flag = !aboolean[(k1 * 16 + k2) * 8 + k] && (k1 < 15 && aboolean[((k1 + 1) * 16 + k2) * 8 + k] || k1 > 0 && aboolean[((k1 - 1) * 16 + k2) * 8 + k] || k2 < 15 && aboolean[(k1 * 16 + k2 + 1) * 8 + k] || k2 > 0 && aboolean[(k1 * 16 + (k2 - 1)) * 8 + k] || k < 7 && aboolean[(k1 * 16 + k2) * 8 + k + 1] || k > 0 && aboolean[(k1 * 16 + k2) * 8 + (k - 1)]);
                     if (flag) {
                        Material material = worldgenlevel.m_8055_(blockpos.m_142082_(k1, k, k2)).m_60767_();
                        if (k >= 4 && material.m_76332_()) {
                           return false;
                        }

                        if (k < 4 && !material.m_76333_() && worldgenlevel.m_8055_(blockpos.m_142082_(k1, k, k2)) != blockstateconfiguration.f_67547_) {
                           return false;
                        }
                     }
                  }
               }
            }

            for(int l1 = 0; l1 < 16; ++l1) {
               for(int l2 = 0; l2 < 16; ++l2) {
                  for(int l3 = 0; l3 < 8; ++l3) {
                     if (aboolean[(l1 * 16 + l2) * 8 + l3]) {
                        BlockPos blockpos2 = blockpos.m_142082_(l1, l3, l2);
                        boolean flag1 = l3 >= 4;
                        worldgenlevel.m_7731_(blockpos2, flag1 ? f_66256_ : blockstateconfiguration.f_67547_, 2);
                        if (flag1) {
                           worldgenlevel.m_6219_().m_5945_(blockpos2, f_66256_.m_60734_(), 0);
                           this.m_159739_(worldgenlevel, blockpos2);
                        }
                     }
                  }
               }
            }

            for(int i2 = 0; i2 < 16; ++i2) {
               for(int i3 = 0; i3 < 16; ++i3) {
                  for(int i4 = 4; i4 < 8; ++i4) {
                     if (aboolean[(i2 * 16 + i3) * 8 + i4]) {
                        BlockPos blockpos3 = blockpos.m_142082_(i2, i4 - 1, i3);
                        if (m_159759_(worldgenlevel.m_8055_(blockpos3)) && worldgenlevel.m_45517_(LightLayer.SKY, blockpos.m_142082_(i2, i4, i3)) > 0) {
                           Biome biome = worldgenlevel.m_46857_(blockpos3);
                           if (biome.m_47536_().m_47824_().m_6743_().m_60713_(Blocks.f_50195_)) {
                              worldgenlevel.m_7731_(blockpos3, Blocks.f_50195_.m_49966_(), 2);
                           } else {
                              worldgenlevel.m_7731_(blockpos3, Blocks.f_50440_.m_49966_(), 2);
                           }
                        }
                     }
                  }
               }
            }

            if (blockstateconfiguration.f_67547_.m_60767_() == Material.f_76307_) {
               BaseStoneSource basestonesource = p_159958_.m_159775_().m_142168_();

               for(int j3 = 0; j3 < 16; ++j3) {
                  for(int j4 = 0; j4 < 16; ++j4) {
                     for(int l4 = 0; l4 < 8; ++l4) {
                        boolean flag2 = !aboolean[(j3 * 16 + j4) * 8 + l4] && (j3 < 15 && aboolean[((j3 + 1) * 16 + j4) * 8 + l4] || j3 > 0 && aboolean[((j3 - 1) * 16 + j4) * 8 + l4] || j4 < 15 && aboolean[(j3 * 16 + j4 + 1) * 8 + l4] || j4 > 0 && aboolean[(j3 * 16 + (j4 - 1)) * 8 + l4] || l4 < 7 && aboolean[(j3 * 16 + j4) * 8 + l4 + 1] || l4 > 0 && aboolean[(j3 * 16 + j4) * 8 + (l4 - 1)]);
                        if (flag2 && (l4 < 4 || random.nextInt(2) != 0)) {
                           BlockState blockstate = worldgenlevel.m_8055_(blockpos.m_142082_(j3, l4, j4));
                           if (blockstate.m_60767_().m_76333_() && !blockstate.m_60620_(BlockTags.f_144288_)) {
                              BlockPos blockpos1 = blockpos.m_142082_(j3, l4, j4);
                              worldgenlevel.m_7731_(blockpos1, basestonesource.m_158054_(blockpos1), 2);
                              this.m_159739_(worldgenlevel, blockpos1);
                           }
                        }
                     }
                  }
               }
            }

            if (blockstateconfiguration.f_67547_.m_60767_() == Material.f_76305_) {
               for(int j2 = 0; j2 < 16; ++j2) {
                  for(int k3 = 0; k3 < 16; ++k3) {
                     int k4 = 4;
                     BlockPos blockpos4 = blockpos.m_142082_(j2, 4, k3);
                     if (worldgenlevel.m_46857_(blockpos4).m_47480_(worldgenlevel, blockpos4, false)) {
                        worldgenlevel.m_7731_(blockpos4, Blocks.f_50126_.m_49966_(), 2);
                     }
                  }
               }
            }

            return true;
         }
      }
   }
}