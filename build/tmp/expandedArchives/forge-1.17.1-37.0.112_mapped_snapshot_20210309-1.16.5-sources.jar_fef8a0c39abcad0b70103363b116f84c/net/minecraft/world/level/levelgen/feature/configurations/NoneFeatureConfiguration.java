package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;

public class NoneFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<NoneFeatureConfiguration> f_67815_;
   public static final NoneFeatureConfiguration f_67816_ = new NoneFeatureConfiguration();

   static {
      f_67815_ = Codec.unit(() -> {
         return f_67816_;
      });
   }
}