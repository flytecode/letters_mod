package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public class SmallDripstoneConfiguration implements FeatureConfiguration {
   public static final Codec<SmallDripstoneConfiguration> f_161169_ = RecordCodecBuilder.create((p_161181_) -> {
      return p_161181_.group(Codec.intRange(0, 100).fieldOf("max_placements").orElse(5).forGetter((p_161189_) -> {
         return p_161189_.f_161170_;
      }), Codec.intRange(0, 20).fieldOf("empty_space_search_radius").orElse(10).forGetter((p_161187_) -> {
         return p_161187_.f_161171_;
      }), Codec.intRange(0, 20).fieldOf("max_offset_from_origin").orElse(2).forGetter((p_161185_) -> {
         return p_161185_.f_161172_;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_taller_dripstone").orElse(0.2F).forGetter((p_161183_) -> {
         return p_161183_.f_161173_;
      })).apply(p_161181_, SmallDripstoneConfiguration::new);
   });
   public final int f_161170_;
   public final int f_161171_;
   public final int f_161172_;
   public final float f_161173_;

   public SmallDripstoneConfiguration(int p_161176_, int p_161177_, int p_161178_, float p_161179_) {
      this.f_161170_ = p_161176_;
      this.f_161171_ = p_161177_;
      this.f_161172_ = p_161178_;
      this.f_161173_ = p_161179_;
   }
}