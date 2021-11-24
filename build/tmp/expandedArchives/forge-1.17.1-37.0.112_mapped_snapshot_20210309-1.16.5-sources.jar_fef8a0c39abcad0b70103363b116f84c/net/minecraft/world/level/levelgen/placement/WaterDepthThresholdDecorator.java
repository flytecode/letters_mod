package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.Heightmap;

public class WaterDepthThresholdDecorator extends FeatureDecorator<WaterDepthThresholdConfiguration> {
   public WaterDepthThresholdDecorator(Codec<WaterDepthThresholdConfiguration> p_162343_) {
      super(p_162343_);
   }

   public Stream<BlockPos> m_7887_(DecorationContext p_162350_, Random p_162351_, WaterDepthThresholdConfiguration p_162352_, BlockPos p_162353_) {
      int i = p_162350_.m_70590_(Heightmap.Types.OCEAN_FLOOR, p_162353_.m_123341_(), p_162353_.m_123343_());
      int j = p_162350_.m_70590_(Heightmap.Types.WORLD_SURFACE, p_162353_.m_123341_(), p_162353_.m_123343_());
      return j - i > p_162352_.f_162334_ ? Stream.of() : Stream.of(p_162353_);
   }
}