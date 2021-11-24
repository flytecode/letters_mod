package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;

public class LayerConfiguration implements FeatureConfiguration {
   public static final Codec<LayerConfiguration> f_67767_ = RecordCodecBuilder.create((p_67777_) -> {
      return p_67777_.group(Codec.intRange(0, DimensionType.f_156651_).fieldOf("height").forGetter((p_160988_) -> {
         return p_160988_.f_67768_;
      }), BlockState.f_61039_.fieldOf("state").forGetter((p_160986_) -> {
         return p_160986_.f_67769_;
      })).apply(p_67777_, LayerConfiguration::new);
   });
   public final int f_67768_;
   public final BlockState f_67769_;

   public LayerConfiguration(int p_67772_, BlockState p_67773_) {
      this.f_67768_ = p_67772_;
      this.f_67769_ = p_67773_;
   }
}