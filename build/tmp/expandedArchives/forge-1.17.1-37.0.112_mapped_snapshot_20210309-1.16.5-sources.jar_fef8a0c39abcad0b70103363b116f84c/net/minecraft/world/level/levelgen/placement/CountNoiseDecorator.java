package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.configurations.NoiseDependantDecoratorConfiguration;

public class CountNoiseDecorator extends RepeatingDecorator<NoiseDependantDecoratorConfiguration> {
   public CountNoiseDecorator(Codec<NoiseDependantDecoratorConfiguration> p_70504_) {
      super(p_70504_);
   }

   protected int m_142262_(Random p_162151_, NoiseDependantDecoratorConfiguration p_162152_, BlockPos p_162153_) {
      double d0 = Biome.f_47433_.m_75449_((double)p_162153_.m_123341_() / 200.0D, (double)p_162153_.m_123343_() / 200.0D, false);
      return d0 < p_162152_.f_67794_ ? p_162152_.f_67795_ : p_162152_.f_67796_;
   }
}