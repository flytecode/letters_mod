package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class WaterDepthThresholdConfiguration implements DecoratorConfiguration {
   public static final Codec<WaterDepthThresholdConfiguration> f_162333_ = RecordCodecBuilder.create((p_162339_) -> {
      return p_162339_.group(Codec.INT.fieldOf("max_water_depth").forGetter((p_162341_) -> {
         return p_162341_.f_162334_;
      })).apply(p_162339_, WaterDepthThresholdConfiguration::new);
   });
   public final int f_162334_;

   public WaterDepthThresholdConfiguration(int p_162337_) {
      this.f_162334_ = p_162337_;
   }
}