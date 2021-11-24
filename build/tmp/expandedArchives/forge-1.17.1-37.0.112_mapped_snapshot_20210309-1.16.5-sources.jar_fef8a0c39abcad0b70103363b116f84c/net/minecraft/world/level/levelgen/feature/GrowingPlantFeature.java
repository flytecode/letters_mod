package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.GrowingPlantConfiguration;

public class GrowingPlantFeature extends Feature<GrowingPlantConfiguration> {
   public GrowingPlantFeature(Codec<GrowingPlantConfiguration> p_159863_) {
      super(p_159863_);
   }

   public boolean m_142674_(FeaturePlaceContext<GrowingPlantConfiguration> p_159865_) {
      LevelAccessor levelaccessor = p_159865_.m_159774_();
      GrowingPlantConfiguration growingplantconfiguration = p_159865_.m_159778_();
      Random random = p_159865_.m_159776_();
      int i = growingplantconfiguration.f_160905_.m_146266_(random).orElseThrow(IllegalStateException::new).m_142270_(random);
      BlockPos.MutableBlockPos blockpos$mutableblockpos = p_159865_.m_159777_().m_122032_();
      BlockPos.MutableBlockPos blockpos$mutableblockpos1 = blockpos$mutableblockpos.m_122032_().m_122173_(growingplantconfiguration.f_160906_);
      BlockState blockstate = levelaccessor.m_8055_(blockpos$mutableblockpos);

      for(int j = 1; j <= i; ++j) {
         BlockState blockstate1 = blockstate;
         blockstate = levelaccessor.m_8055_(blockpos$mutableblockpos1);
         if (blockstate1.m_60795_() || growingplantconfiguration.f_160909_ && blockstate1.m_60819_().m_76153_(FluidTags.f_13131_)) {
            if (j == i || !blockstate.m_60795_()) {
               levelaccessor.m_7731_(blockpos$mutableblockpos, growingplantconfiguration.f_160908_.m_7112_(random, blockpos$mutableblockpos), 2);
               break;
            }

            levelaccessor.m_7731_(blockpos$mutableblockpos, growingplantconfiguration.f_160907_.m_7112_(random, blockpos$mutableblockpos), 2);
         }

         blockpos$mutableblockpos1.m_122173_(growingplantconfiguration.f_160906_);
         blockpos$mutableblockpos.m_122173_(growingplantconfiguration.f_160906_);
      }

      return true;
   }
}