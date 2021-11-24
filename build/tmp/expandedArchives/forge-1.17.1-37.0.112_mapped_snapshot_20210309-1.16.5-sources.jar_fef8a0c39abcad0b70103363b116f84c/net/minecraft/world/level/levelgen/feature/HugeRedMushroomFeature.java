package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class HugeRedMushroomFeature extends AbstractHugeMushroomFeature {
   public HugeRedMushroomFeature(Codec<HugeMushroomFeatureConfiguration> p_65975_) {
      super(p_65975_);
   }

   protected void m_6152_(LevelAccessor p_65982_, Random p_65983_, BlockPos p_65984_, int p_65985_, BlockPos.MutableBlockPos p_65986_, HugeMushroomFeatureConfiguration p_65987_) {
      for(int i = p_65985_ - 3; i <= p_65985_; ++i) {
         int j = i < p_65985_ ? p_65987_.f_67742_ : p_65987_.f_67742_ - 1;
         int k = p_65987_.f_67742_ - 2;

         for(int l = -j; l <= j; ++l) {
            for(int i1 = -j; i1 <= j; ++i1) {
               boolean flag = l == -j;
               boolean flag1 = l == j;
               boolean flag2 = i1 == -j;
               boolean flag3 = i1 == j;
               boolean flag4 = flag || flag1;
               boolean flag5 = flag2 || flag3;
               if (i >= p_65985_ || flag4 != flag5) {
                  p_65986_.m_122154_(p_65984_, l, i, i1);
                  if (!p_65982_.m_8055_(p_65986_).m_60804_(p_65982_, p_65986_)) {
                     BlockState blockstate = p_65987_.f_67740_.m_7112_(p_65983_, p_65984_);
                     if (blockstate.m_61138_(HugeMushroomBlock.f_54130_) && blockstate.m_61138_(HugeMushroomBlock.f_54128_) && blockstate.m_61138_(HugeMushroomBlock.f_54127_) && blockstate.m_61138_(HugeMushroomBlock.f_54129_) && blockstate.m_61138_(HugeMushroomBlock.f_54131_)) {
                        blockstate = blockstate.m_61124_(HugeMushroomBlock.f_54131_, Boolean.valueOf(i >= p_65985_ - 1)).m_61124_(HugeMushroomBlock.f_54130_, Boolean.valueOf(l < -k)).m_61124_(HugeMushroomBlock.f_54128_, Boolean.valueOf(l > k)).m_61124_(HugeMushroomBlock.f_54127_, Boolean.valueOf(i1 < -k)).m_61124_(HugeMushroomBlock.f_54129_, Boolean.valueOf(i1 > k));
                     }

                     this.m_5974_(p_65982_, p_65986_, blockstate);
                  }
               }
            }
         }
      }

   }

   protected int m_6794_(int p_65977_, int p_65978_, int p_65979_, int p_65980_) {
      int i = 0;
      if (p_65980_ < p_65978_ && p_65980_ >= p_65978_ - 3) {
         i = p_65979_;
      } else if (p_65980_ == p_65978_) {
         i = p_65979_;
      }

      return i;
   }
}