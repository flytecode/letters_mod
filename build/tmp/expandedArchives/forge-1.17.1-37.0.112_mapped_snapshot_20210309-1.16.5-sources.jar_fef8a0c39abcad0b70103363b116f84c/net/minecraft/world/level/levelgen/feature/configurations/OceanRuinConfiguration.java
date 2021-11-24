package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.structure.OceanRuinFeature;

public class OceanRuinConfiguration implements FeatureConfiguration {
   public static final Codec<OceanRuinConfiguration> f_67820_ = RecordCodecBuilder.create((p_67832_) -> {
      return p_67832_.group(OceanRuinFeature.Type.f_72501_.fieldOf("biome_temp").forGetter((p_161004_) -> {
         return p_161004_.f_67821_;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("large_probability").forGetter((p_161002_) -> {
         return p_161002_.f_67822_;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("cluster_probability").forGetter((p_161000_) -> {
         return p_161000_.f_67823_;
      })).apply(p_67832_, OceanRuinConfiguration::new);
   });
   public final OceanRuinFeature.Type f_67821_;
   public final float f_67822_;
   public final float f_67823_;

   public OceanRuinConfiguration(OceanRuinFeature.Type p_67826_, float p_67827_, float p_67828_) {
      this.f_67821_ = p_67826_;
      this.f_67822_ = p_67827_;
      this.f_67823_ = p_67828_;
   }
}