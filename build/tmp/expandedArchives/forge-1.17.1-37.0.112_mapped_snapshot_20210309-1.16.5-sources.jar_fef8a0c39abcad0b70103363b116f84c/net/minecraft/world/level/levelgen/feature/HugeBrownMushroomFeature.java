package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public class HugeBrownMushroomFeature extends AbstractHugeMushroomFeature {
   public HugeBrownMushroomFeature(Codec<HugeMushroomFeatureConfiguration> p_65879_) {
      super(p_65879_);
   }

   protected void m_6152_(LevelAccessor p_65886_, Random p_65887_, BlockPos p_65888_, int p_65889_, BlockPos.MutableBlockPos p_65890_, HugeMushroomFeatureConfiguration p_65891_) {
      int i = p_65891_.f_67742_;

      for(int j = -i; j <= i; ++j) {
         for(int k = -i; k <= i; ++k) {
            boolean flag = j == -i;
            boolean flag1 = j == i;
            boolean flag2 = k == -i;
            boolean flag3 = k == i;
            boolean flag4 = flag || flag1;
            boolean flag5 = flag2 || flag3;
            if (!flag4 || !flag5) {
               p_65890_.m_122154_(p_65888_, j, p_65889_, k);
               if (!p_65886_.m_8055_(p_65890_).m_60804_(p_65886_, p_65890_)) {
                  boolean flag6 = flag || flag5 && j == 1 - i;
                  boolean flag7 = flag1 || flag5 && j == i - 1;
                  boolean flag8 = flag2 || flag4 && k == 1 - i;
                  boolean flag9 = flag3 || flag4 && k == i - 1;
                  BlockState blockstate = p_65891_.f_67740_.m_7112_(p_65887_, p_65888_);
                  if (blockstate.m_61138_(HugeMushroomBlock.f_54130_) && blockstate.m_61138_(HugeMushroomBlock.f_54128_) && blockstate.m_61138_(HugeMushroomBlock.f_54127_) && blockstate.m_61138_(HugeMushroomBlock.f_54129_)) {
                     blockstate = blockstate.m_61124_(HugeMushroomBlock.f_54130_, Boolean.valueOf(flag6)).m_61124_(HugeMushroomBlock.f_54128_, Boolean.valueOf(flag7)).m_61124_(HugeMushroomBlock.f_54127_, Boolean.valueOf(flag8)).m_61124_(HugeMushroomBlock.f_54129_, Boolean.valueOf(flag9));
                  }

                  this.m_5974_(p_65886_, p_65890_, blockstate);
               }
            }
         }
      }

   }

   protected int m_6794_(int p_65881_, int p_65882_, int p_65883_, int p_65884_) {
      return p_65884_ <= 3 ? 0 : p_65883_;
   }
}