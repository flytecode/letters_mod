package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public abstract class VerticalDecorator<DC extends DecoratorConfiguration> extends FeatureDecorator<DC> {
   public VerticalDecorator(Codec<DC> p_162323_) {
      super(p_162323_);
   }

   protected abstract int m_142757_(DecorationContext p_162324_, Random p_162325_, DC p_162326_, int p_162327_);

   public final Stream<BlockPos> m_7887_(DecorationContext p_162329_, Random p_162330_, DC p_162331_, BlockPos p_162332_) {
      return Stream.of(new BlockPos(p_162332_.m_123341_(), this.m_142757_(p_162329_, p_162330_, p_162331_, p_162332_.m_123342_()), p_162332_.m_123343_()));
   }
}