package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.Column;

public class CaveSurfaceDecorator extends FeatureDecorator<CaveDecoratorConfiguration> {
   public CaveSurfaceDecorator(Codec<CaveDecoratorConfiguration> p_162117_) {
      super(p_162117_);
   }

   public Stream<BlockPos> m_7887_(DecorationContext p_162126_, Random p_162127_, CaveDecoratorConfiguration p_162128_, BlockPos p_162129_) {
      Optional<Column> optional = Column.m_158175_(p_162126_.m_162168_(), p_162129_, p_162128_.f_162081_, BlockBehaviour.BlockStateBase::m_60795_, (p_162119_) -> {
         return p_162119_.m_60767_().m_76333_();
      });
      if (!optional.isPresent()) {
         return Stream.of();
      } else {
         OptionalInt optionalint = p_162128_.f_162080_ == CaveSurface.CEILING ? optional.get().m_142011_() : optional.get().m_142009_();
         return !optionalint.isPresent() ? Stream.of() : Stream.of(p_162129_.m_175288_(optionalint.getAsInt() - p_162128_.f_162080_.m_162110_()));
      }
   }
}