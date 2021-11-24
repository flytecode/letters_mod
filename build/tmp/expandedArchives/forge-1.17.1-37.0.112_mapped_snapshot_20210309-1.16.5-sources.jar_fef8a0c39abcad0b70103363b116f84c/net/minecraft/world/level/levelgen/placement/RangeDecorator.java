package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.world.level.levelgen.feature.configurations.RangeDecoratorConfiguration;

public class RangeDecorator extends VerticalDecorator<RangeDecoratorConfiguration> {
   public RangeDecorator(Codec<RangeDecoratorConfiguration> p_70834_) {
      super(p_70834_);
   }

   protected int m_142757_(DecorationContext p_162285_, Random p_162286_, RangeDecoratorConfiguration p_162287_, int p_162288_) {
      return p_162287_.f_161076_.m_142233_(p_162286_, p_162285_);
   }
}