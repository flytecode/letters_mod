package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DesertWellFeature extends Feature<NoneFeatureConfiguration> {
   private static final BlockStatePredicate f_65593_ = BlockStatePredicate.m_61287_(Blocks.f_49992_);
   private final BlockState f_65594_ = Blocks.f_50406_.m_49966_();
   private final BlockState f_65595_ = Blocks.f_50062_.m_49966_();
   private final BlockState f_65596_ = Blocks.f_49990_.m_49966_();

   public DesertWellFeature(Codec<NoneFeatureConfiguration> p_65599_) {
      super(p_65599_);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> p_159571_) {
      WorldGenLevel worldgenlevel = p_159571_.m_159774_();
      BlockPos blockpos = p_159571_.m_159777_();

      for(blockpos = blockpos.m_7494_(); worldgenlevel.m_46859_(blockpos) && blockpos.m_123342_() > worldgenlevel.m_141937_() + 2; blockpos = blockpos.m_7495_()) {
      }

      if (!f_65593_.test(worldgenlevel.m_8055_(blockpos))) {
         return false;
      } else {
         for(int i = -2; i <= 2; ++i) {
            for(int j = -2; j <= 2; ++j) {
               if (worldgenlevel.m_46859_(blockpos.m_142082_(i, -1, j)) && worldgenlevel.m_46859_(blockpos.m_142082_(i, -2, j))) {
                  return false;
               }
            }
         }

         for(int l = -1; l <= 0; ++l) {
            for(int l1 = -2; l1 <= 2; ++l1) {
               for(int k = -2; k <= 2; ++k) {
                  worldgenlevel.m_7731_(blockpos.m_142082_(l1, l, k), this.f_65595_, 2);
               }
            }
         }

         worldgenlevel.m_7731_(blockpos, this.f_65596_, 2);

         for(Direction direction : Direction.Plane.HORIZONTAL) {
            worldgenlevel.m_7731_(blockpos.m_142300_(direction), this.f_65596_, 2);
         }

         for(int i1 = -2; i1 <= 2; ++i1) {
            for(int i2 = -2; i2 <= 2; ++i2) {
               if (i1 == -2 || i1 == 2 || i2 == -2 || i2 == 2) {
                  worldgenlevel.m_7731_(blockpos.m_142082_(i1, 1, i2), this.f_65595_, 2);
               }
            }
         }

         worldgenlevel.m_7731_(blockpos.m_142082_(2, 1, 0), this.f_65594_, 2);
         worldgenlevel.m_7731_(blockpos.m_142082_(-2, 1, 0), this.f_65594_, 2);
         worldgenlevel.m_7731_(blockpos.m_142082_(0, 1, 2), this.f_65594_, 2);
         worldgenlevel.m_7731_(blockpos.m_142082_(0, 1, -2), this.f_65594_, 2);

         for(int j1 = -1; j1 <= 1; ++j1) {
            for(int j2 = -1; j2 <= 1; ++j2) {
               if (j1 == 0 && j2 == 0) {
                  worldgenlevel.m_7731_(blockpos.m_142082_(j1, 4, j2), this.f_65595_, 2);
               } else {
                  worldgenlevel.m_7731_(blockpos.m_142082_(j1, 4, j2), this.f_65594_, 2);
               }
            }
         }

         for(int k1 = 1; k1 <= 3; ++k1) {
            worldgenlevel.m_7731_(blockpos.m_142082_(-1, k1, -1), this.f_65595_, 2);
            worldgenlevel.m_7731_(blockpos.m_142082_(-1, k1, 1), this.f_65595_, 2);
            worldgenlevel.m_7731_(blockpos.m_142082_(1, k1, -1), this.f_65595_, 2);
            worldgenlevel.m_7731_(blockpos.m_142082_(1, k1, 1), this.f_65595_, 2);
         }

         return true;
      }
   }
}