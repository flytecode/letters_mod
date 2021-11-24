package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;

public class ShipwreckConfiguration implements FeatureConfiguration {
   public static final Codec<ShipwreckConfiguration> f_68061_ = Codec.BOOL.fieldOf("is_beached").orElse(false).xmap(ShipwreckConfiguration::new, (p_68067_) -> {
      return p_68067_.f_68062_;
   }).codec();
   public final boolean f_68062_;

   public ShipwreckConfiguration(boolean p_68065_) {
      this.f_68062_ = p_68065_;
   }
}