package net.minecraft.world.level.block.grower;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class SpruceTreeGrower extends AbstractMegaTreeGrower {
   @Nullable
   protected ConfiguredFeature<TreeConfiguration, ?> m_6486_(Random p_60044_, boolean p_60045_) {
      return Features.f_126952_;
   }

   @Nullable
   protected ConfiguredFeature<TreeConfiguration, ?> m_8111_(Random p_60042_) {
      return p_60042_.nextBoolean() ? Features.f_126958_ : Features.f_126959_;
   }
}