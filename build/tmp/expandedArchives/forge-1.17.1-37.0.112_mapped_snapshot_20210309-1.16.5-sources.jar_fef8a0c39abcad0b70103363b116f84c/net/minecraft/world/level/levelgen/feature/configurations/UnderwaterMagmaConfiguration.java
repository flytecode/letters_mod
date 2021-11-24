package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class UnderwaterMagmaConfiguration implements FeatureConfiguration {
   public static final Codec<UnderwaterMagmaConfiguration> f_161263_ = RecordCodecBuilder.create((p_161273_) -> {
      return p_161273_.group(Codec.intRange(0, 512).fieldOf("floor_search_range").forGetter((p_161279_) -> {
         return p_161279_.f_161264_;
      }), Codec.intRange(0, 64).fieldOf("placement_radius_around_floor").forGetter((p_161277_) -> {
         return p_161277_.f_161265_;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("placement_probability_per_valid_position").forGetter((p_161275_) -> {
         return p_161275_.f_161266_;
      })).apply(p_161273_, UnderwaterMagmaConfiguration::new);
   });
   public final int f_161264_;
   public final int f_161265_;
   public final float f_161266_;

   public UnderwaterMagmaConfiguration(int p_161269_, int p_161270_, float p_161271_) {
      this.f_161264_ = p_161269_;
      this.f_161265_ = p_161270_;
      this.f_161266_ = p_161271_;
   }
}