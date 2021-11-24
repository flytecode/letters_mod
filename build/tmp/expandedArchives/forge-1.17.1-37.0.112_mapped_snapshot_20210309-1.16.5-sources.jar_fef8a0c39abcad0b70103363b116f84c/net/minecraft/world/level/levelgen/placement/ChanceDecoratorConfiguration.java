package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class ChanceDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<ChanceDecoratorConfiguration> f_70464_ = Codec.INT.fieldOf("chance").xmap(ChanceDecoratorConfiguration::new, (p_70470_) -> {
      return p_70470_.f_70465_;
   }).codec();
   public final int f_70465_;

   public ChanceDecoratorConfiguration(int p_70468_) {
      this.f_70465_ = p_70468_;
   }
}