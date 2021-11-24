package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;

public class NoneDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<NoneDecoratorConfiguration> f_67810_;
   public static final NoneDecoratorConfiguration f_67811_ = new NoneDecoratorConfiguration();

   static {
      f_67810_ = Codec.unit(() -> {
         return f_67811_;
      });
   }
}