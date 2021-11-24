package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;

public class LargeDripstoneConfiguration implements FeatureConfiguration {
   public static final Codec<LargeDripstoneConfiguration> f_160944_ = RecordCodecBuilder.create((p_160966_) -> {
      return p_160966_.group(Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").orElse(30).forGetter((p_160984_) -> {
         return p_160984_.f_160945_;
      }), IntProvider.m_146545_(1, 60).fieldOf("column_radius").forGetter((p_160982_) -> {
         return p_160982_.f_160946_;
      }), FloatProvider.m_146505_(0.0F, 20.0F).fieldOf("height_scale").forGetter((p_160980_) -> {
         return p_160980_.f_160947_;
      }), Codec.floatRange(0.1F, 1.0F).fieldOf("max_column_radius_to_cave_height_ratio").forGetter((p_160978_) -> {
         return p_160978_.f_160948_;
      }), FloatProvider.m_146505_(0.1F, 10.0F).fieldOf("stalactite_bluntness").forGetter((p_160976_) -> {
         return p_160976_.f_160949_;
      }), FloatProvider.m_146505_(0.1F, 10.0F).fieldOf("stalagmite_bluntness").forGetter((p_160974_) -> {
         return p_160974_.f_160950_;
      }), FloatProvider.m_146505_(0.0F, 2.0F).fieldOf("wind_speed").forGetter((p_160972_) -> {
         return p_160972_.f_160951_;
      }), Codec.intRange(0, 100).fieldOf("min_radius_for_wind").forGetter((p_160970_) -> {
         return p_160970_.f_160952_;
      }), Codec.floatRange(0.0F, 5.0F).fieldOf("min_bluntness_for_wind").forGetter((p_160968_) -> {
         return p_160968_.f_160953_;
      })).apply(p_160966_, LargeDripstoneConfiguration::new);
   });
   public final int f_160945_;
   public final IntProvider f_160946_;
   public final FloatProvider f_160947_;
   public final float f_160948_;
   public final FloatProvider f_160949_;
   public final FloatProvider f_160950_;
   public final FloatProvider f_160951_;
   public final int f_160952_;
   public final float f_160953_;

   public LargeDripstoneConfiguration(int p_160956_, IntProvider p_160957_, FloatProvider p_160958_, float p_160959_, FloatProvider p_160960_, FloatProvider p_160961_, FloatProvider p_160962_, int p_160963_, float p_160964_) {
      this.f_160945_ = p_160956_;
      this.f_160946_ = p_160957_;
      this.f_160947_ = p_160958_;
      this.f_160948_ = p_160959_;
      this.f_160949_ = p_160960_;
      this.f_160950_ = p_160961_;
      this.f_160951_ = p_160962_;
      this.f_160952_ = p_160963_;
      this.f_160953_ = p_160964_;
   }
}