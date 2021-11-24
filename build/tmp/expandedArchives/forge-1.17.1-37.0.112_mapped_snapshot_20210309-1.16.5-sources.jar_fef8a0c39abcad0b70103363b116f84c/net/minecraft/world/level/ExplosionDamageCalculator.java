package net.minecraft.world.level;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;

public class ExplosionDamageCalculator {
   public Optional<Float> m_6617_(Explosion p_46099_, BlockGetter p_46100_, BlockPos p_46101_, BlockState p_46102_, FluidState p_46103_) {
      return p_46102_.m_60795_() && p_46103_.m_76178_() ? Optional.empty() : Optional.of(Math.max(p_46102_.getExplosionResistance(p_46100_, p_46101_, p_46099_), p_46103_.getExplosionResistance(p_46100_, p_46101_, p_46099_)));
   }

   public boolean m_6714_(Explosion p_46094_, BlockGetter p_46095_, BlockPos p_46096_, BlockState p_46097_, float p_46098_) {
      return true;
   }
}
