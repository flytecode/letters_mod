package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.configurations.CountConfiguration;

public class CountDecorator extends RepeatingDecorator<CountConfiguration> {
   public CountDecorator(Codec<CountConfiguration> p_70491_) {
      super(p_70491_);
   }

   protected int m_142262_(Random p_162139_, CountConfiguration p_162140_, BlockPos p_162141_) {
      return p_162140_.m_160725_().m_142270_(p_162139_);
   }
}