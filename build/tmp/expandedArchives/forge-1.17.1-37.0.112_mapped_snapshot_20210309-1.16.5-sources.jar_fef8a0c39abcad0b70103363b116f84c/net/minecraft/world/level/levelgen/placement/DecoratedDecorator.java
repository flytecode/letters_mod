package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;

public class DecoratedDecorator extends FeatureDecorator<DecoratedDecoratorConfiguration> {
   public DecoratedDecorator(Codec<DecoratedDecoratorConfiguration> p_70554_) {
      super(p_70554_);
   }

   public Stream<BlockPos> m_7887_(DecorationContext p_70566_, Random p_70567_, DecoratedDecoratorConfiguration p_70568_, BlockPos p_70569_) {
      return p_70568_.m_70577_().m_70480_(p_70566_, p_70567_, p_70569_).flatMap((p_70559_) -> {
         return p_70568_.m_70580_().m_70480_(p_70566_, p_70567_, p_70559_);
      });
   }
}