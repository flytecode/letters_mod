package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.HeightmapConfiguration;

public class HeightmapDecorator extends FeatureDecorator<HeightmapConfiguration> {
   public HeightmapDecorator(Codec<HeightmapConfiguration> p_70747_) {
      super(p_70747_);
   }

   public Stream<BlockPos> m_7887_(DecorationContext p_162193_, Random p_162194_, HeightmapConfiguration p_162195_, BlockPos p_162196_) {
      int i = p_162196_.m_123341_();
      int j = p_162196_.m_123343_();
      int k = p_162193_.m_70590_(p_162195_.f_160930_, i, j);
      return k > p_162193_.m_162167_() ? Stream.of(new BlockPos(i, k, j)) : Stream.of();
   }
}