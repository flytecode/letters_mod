package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class HugeMushroomFeatureConfiguration implements FeatureConfiguration {
   public static final Codec<HugeMushroomFeatureConfiguration> f_67739_ = RecordCodecBuilder.create((p_67751_) -> {
      return p_67751_.group(BlockStateProvider.f_68747_.fieldOf("cap_provider").forGetter((p_160943_) -> {
         return p_160943_.f_67740_;
      }), BlockStateProvider.f_68747_.fieldOf("stem_provider").forGetter((p_160941_) -> {
         return p_160941_.f_67741_;
      }), Codec.INT.fieldOf("foliage_radius").orElse(2).forGetter((p_160939_) -> {
         return p_160939_.f_67742_;
      })).apply(p_67751_, HugeMushroomFeatureConfiguration::new);
   });
   public final BlockStateProvider f_67740_;
   public final BlockStateProvider f_67741_;
   public final int f_67742_;

   public HugeMushroomFeatureConfiguration(BlockStateProvider p_67745_, BlockStateProvider p_67746_, int p_67747_) {
      this.f_67740_ = p_67745_;
      this.f_67741_ = p_67746_;
      this.f_67742_ = p_67747_;
   }
}