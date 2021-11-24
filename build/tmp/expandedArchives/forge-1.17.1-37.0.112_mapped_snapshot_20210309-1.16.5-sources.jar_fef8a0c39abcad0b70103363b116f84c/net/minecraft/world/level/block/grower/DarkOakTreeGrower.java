package net.minecraft.world.level.block.grower;

import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.data.worldgen.Features;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class DarkOakTreeGrower extends AbstractMegaTreeGrower {
   @Nullable
   protected ConfiguredFeature<TreeConfiguration, ?> m_6486_(Random p_60028_, boolean p_60029_) {
      return null;
   }

   @Nullable
   protected ConfiguredFeature<TreeConfiguration, ?> m_8111_(Random p_60026_) {
      return Features.f_126949_;
   }
}