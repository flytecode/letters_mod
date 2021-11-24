package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.Heightmap;

public class HeightmapConfiguration implements DecoratorConfiguration {
   public static final Codec<HeightmapConfiguration> f_160929_ = RecordCodecBuilder.create((p_160935_) -> {
      return p_160935_.group(Heightmap.Types.f_64274_.fieldOf("heightmap").forGetter((p_160937_) -> {
         return p_160937_.f_160930_;
      })).apply(p_160935_, HeightmapConfiguration::new);
   });
   public final Heightmap.Types f_160930_;

   public HeightmapConfiguration(Heightmap.Types p_160933_) {
      this.f_160930_ = p_160933_;
   }
}