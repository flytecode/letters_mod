package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;

public class NoiseBasedDecorator extends RepeatingDecorator<NoiseCountFactorDecoratorConfiguration> {
   public NoiseBasedDecorator(Codec<NoiseCountFactorDecoratorConfiguration> p_70794_) {
      super(p_70794_);
   }

   protected int m_142262_(Random p_162260_, NoiseCountFactorDecoratorConfiguration p_162261_, BlockPos p_162262_) {
      double d0 = Biome.f_47433_.m_75449_((double)p_162262_.m_123341_() / p_162261_.f_70808_, (double)p_162262_.m_123343_() / p_162261_.f_70808_, false);
      return (int)Math.ceil((d0 + p_162261_.f_70809_) * (double)p_162261_.f_70807_);
   }
}