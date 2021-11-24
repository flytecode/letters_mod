package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.MineshaftFeature;

public class MineshaftConfiguration implements FeatureConfiguration {
   public static final Codec<MineshaftConfiguration> f_67780_ = RecordCodecBuilder.create((p_67790_) -> {
      return p_67790_.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((p_160992_) -> {
         return p_160992_.f_67781_;
      }), MineshaftFeature.Type.f_66320_.fieldOf("type").forGetter((p_160990_) -> {
         return p_160990_.f_67782_;
      })).apply(p_67790_, MineshaftConfiguration::new);
   });
   public final float f_67781_;
   public final MineshaftFeature.Type f_67782_;

   public MineshaftConfiguration(float p_67785_, MineshaftFeature.Type p_67786_) {
      this.f_67781_ = p_67785_;
      this.f_67782_ = p_67786_;
   }
}