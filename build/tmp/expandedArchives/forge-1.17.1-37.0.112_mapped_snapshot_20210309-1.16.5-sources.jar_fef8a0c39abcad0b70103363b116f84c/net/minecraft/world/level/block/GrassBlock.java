package net.minecraft.world.level.block;

import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractFlowerFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class GrassBlock extends SpreadingSnowyDirtBlock implements BonemealableBlock {
   public GrassBlock(BlockBehaviour.Properties p_53685_) {
      super(p_53685_);
   }

   public boolean m_7370_(BlockGetter p_53692_, BlockPos p_53693_, BlockState p_53694_, boolean p_53695_) {
      return p_53692_.m_8055_(p_53693_.m_7494_()).m_60795_();
   }

   public boolean m_5491_(Level p_53697_, Random p_53698_, BlockPos p_53699_, BlockState p_53700_) {
      return true;
   }

   public void m_7719_(ServerLevel p_53687_, Random p_53688_, BlockPos p_53689_, BlockState p_53690_) {
      BlockPos blockpos = p_53689_.m_7494_();
      BlockState blockstate = Blocks.f_50034_.m_49966_();

      label48:
      for(int i = 0; i < 128; ++i) {
         BlockPos blockpos1 = blockpos;

         for(int j = 0; j < i / 16; ++j) {
            blockpos1 = blockpos1.m_142082_(p_53688_.nextInt(3) - 1, (p_53688_.nextInt(3) - 1) * p_53688_.nextInt(3) / 2, p_53688_.nextInt(3) - 1);
            if (!p_53687_.m_8055_(blockpos1.m_7495_()).m_60713_(this) || p_53687_.m_8055_(blockpos1).m_60838_(p_53687_, blockpos1)) {
               continue label48;
            }
         }

         BlockState blockstate2 = p_53687_.m_8055_(blockpos1);
         if (blockstate2.m_60713_(blockstate.m_60734_()) && p_53688_.nextInt(10) == 0) {
            ((BonemealableBlock)blockstate.m_60734_()).m_7719_(p_53687_, p_53688_, blockpos1, blockstate2);
         }

         if (blockstate2.m_60795_()) {
            BlockState blockstate1;
            if (p_53688_.nextInt(8) == 0) {
               List<ConfiguredFeature<?, ?>> list = p_53687_.m_46857_(blockpos1).m_47536_().m_47815_();
               if (list.isEmpty()) {
                  continue;
               }

               blockstate1 = m_153317_(p_53688_, blockpos1, list.get(0));
            } else {
               blockstate1 = blockstate;
            }

            if (blockstate1.m_60710_(p_53687_, blockpos1)) {
               p_53687_.m_7731_(blockpos1, blockstate1, 3);
            }
         }
      }

   }

   private static <U extends FeatureConfiguration> BlockState m_153317_(Random p_153318_, BlockPos p_153319_, ConfiguredFeature<U, ?> p_153320_) {
      AbstractFlowerFeature<U> abstractflowerfeature = (AbstractFlowerFeature)p_153320_.f_65377_;
      return abstractflowerfeature.m_7973_(p_153318_, p_153319_, p_153320_.m_65397_());
   }
}