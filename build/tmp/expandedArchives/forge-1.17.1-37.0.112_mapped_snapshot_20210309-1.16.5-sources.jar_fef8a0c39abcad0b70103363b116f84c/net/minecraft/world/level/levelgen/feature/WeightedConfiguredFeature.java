package net.minecraft.world.level.levelgen.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;

public class WeightedConfiguredFeature {
   public static final Codec<WeightedConfiguredFeature> f_67403_ = RecordCodecBuilder.create((p_67423_) -> {
      return p_67423_.group(ConfiguredFeature.f_65374_.fieldOf("feature").flatXmap(ExtraCodecs.m_181037_(), ExtraCodecs.m_181037_()).forGetter((p_160665_) -> {
         return p_160665_.f_67404_;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance").forGetter((p_160663_) -> {
         return p_160663_.f_67405_;
      })).apply(p_67423_, WeightedConfiguredFeature::new);
   });
   public final Supplier<ConfiguredFeature<?, ?>> f_67404_;
   public final float f_67405_;

   public WeightedConfiguredFeature(ConfiguredFeature<?, ?> p_67408_, float p_67409_) {
      this(() -> {
         return p_67408_;
      }, p_67409_);
   }

   private WeightedConfiguredFeature(Supplier<ConfiguredFeature<?, ?>> p_67411_, float p_67412_) {
      this.f_67404_ = p_67411_;
      this.f_67405_ = p_67412_;
   }

   public boolean m_67413_(WorldGenLevel p_67414_, ChunkGenerator p_67415_, Random p_67416_, BlockPos p_67417_) {
      return this.f_67404_.get().m_65385_(p_67414_, p_67415_, p_67416_, p_67417_);
   }
}