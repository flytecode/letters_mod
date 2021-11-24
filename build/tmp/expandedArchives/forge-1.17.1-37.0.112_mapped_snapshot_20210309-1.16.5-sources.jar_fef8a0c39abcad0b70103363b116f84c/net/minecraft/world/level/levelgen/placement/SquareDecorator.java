package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;

public class SquareDecorator extends FeatureDecorator<NoneDecoratorConfiguration> {
   public SquareDecorator(Codec<NoneDecoratorConfiguration> p_70866_) {
      super(p_70866_);
   }

   public Stream<BlockPos> m_7887_(DecorationContext p_162318_, Random p_162319_, NoneDecoratorConfiguration p_162320_, BlockPos p_162321_) {
      int i = p_162319_.nextInt(16) + p_162321_.m_123341_();
      int j = p_162319_.nextInt(16) + p_162321_.m_123343_();
      return Stream.of(new BlockPos(i, p_162321_.m_123342_(), j));
   }
}