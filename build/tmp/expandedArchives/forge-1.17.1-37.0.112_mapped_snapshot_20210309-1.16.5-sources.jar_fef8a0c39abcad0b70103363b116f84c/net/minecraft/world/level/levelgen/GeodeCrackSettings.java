package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;

public class GeodeCrackSettings {
   public static final Codec<GeodeCrackSettings> f_158324_ = RecordCodecBuilder.create((p_158334_) -> {
      return p_158334_.group(GeodeConfiguration.f_160811_.fieldOf("generate_crack_chance").orElse(1.0D).forGetter((p_158340_) -> {
         return p_158340_.f_158325_;
      }), Codec.doubleRange(0.0D, 5.0D).fieldOf("base_crack_size").orElse(2.0D).forGetter((p_158338_) -> {
         return p_158338_.f_158326_;
      }), Codec.intRange(0, 10).fieldOf("crack_point_offset").orElse(2).forGetter((p_158336_) -> {
         return p_158336_.f_158327_;
      })).apply(p_158334_, GeodeCrackSettings::new);
   });
   public final double f_158325_;
   public final double f_158326_;
   public final int f_158327_;

   public GeodeCrackSettings(double p_158330_, double p_158331_, int p_158332_) {
      this.f_158325_ = p_158330_;
      this.f_158326_ = p_158331_;
      this.f_158327_ = p_158332_;
   }
}