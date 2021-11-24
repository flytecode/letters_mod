package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.NoneDecoratorConfiguration;

public class IcebergPlacementDecorator extends FeatureDecorator<NoneDecoratorConfiguration> {
   public IcebergPlacementDecorator(Codec<NoneDecoratorConfiguration> p_70760_) {
      super(p_70760_);
   }

   public Stream<BlockPos> m_7887_(DecorationContext p_162243_, Random p_162244_, NoneDecoratorConfiguration p_162245_, BlockPos p_162246_) {
      int i = p_162244_.nextInt(8) + 4 + p_162246_.m_123341_();
      int j = p_162244_.nextInt(8) + 4 + p_162246_.m_123343_();
      return Stream.of(new BlockPos(i, p_162246_.m_123342_(), j));
   }
}