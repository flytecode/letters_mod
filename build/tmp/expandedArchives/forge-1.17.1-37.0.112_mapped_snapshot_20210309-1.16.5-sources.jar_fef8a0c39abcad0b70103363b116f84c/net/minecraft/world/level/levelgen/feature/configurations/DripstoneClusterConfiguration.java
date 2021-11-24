package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;

public class DripstoneClusterConfiguration implements FeatureConfiguration {
   public static final Codec<DripstoneClusterConfiguration> f_160758_ = RecordCodecBuilder.create((p_160784_) -> {
      return p_160784_.group(Codec.intRange(1, 512).fieldOf("floor_to_ceiling_search_range").forGetter((p_160806_) -> {
         return p_160806_.f_160759_;
      }), IntProvider.m_146545_(1, 128).fieldOf("height").forGetter((p_160804_) -> {
         return p_160804_.f_160760_;
      }), IntProvider.m_146545_(1, 128).fieldOf("radius").forGetter((p_160802_) -> {
         return p_160802_.f_160761_;
      }), Codec.intRange(0, 64).fieldOf("max_stalagmite_stalactite_height_diff").forGetter((p_160800_) -> {
         return p_160800_.f_160762_;
      }), Codec.intRange(1, 64).fieldOf("height_deviation").forGetter((p_160798_) -> {
         return p_160798_.f_160763_;
      }), IntProvider.m_146545_(0, 128).fieldOf("dripstone_block_layer_thickness").forGetter((p_160796_) -> {
         return p_160796_.f_160764_;
      }), FloatProvider.m_146505_(0.0F, 2.0F).fieldOf("density").forGetter((p_160794_) -> {
         return p_160794_.f_160765_;
      }), FloatProvider.m_146505_(0.0F, 2.0F).fieldOf("wetness").forGetter((p_160792_) -> {
         return p_160792_.f_160766_;
      }), Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_dripstone_column_at_max_distance_from_center").forGetter((p_160790_) -> {
         return p_160790_.f_160767_;
      }), Codec.intRange(1, 64).fieldOf("max_distance_from_edge_affecting_chance_of_dripstone_column").forGetter((p_160788_) -> {
         return p_160788_.f_160768_;
      }), Codec.intRange(1, 64).fieldOf("max_distance_from_center_affecting_height_bias").forGetter((p_160786_) -> {
         return p_160786_.f_160769_;
      })).apply(p_160784_, DripstoneClusterConfiguration::new);
   });
   public final int f_160759_;
   public final IntProvider f_160760_;
   public final IntProvider f_160761_;
   public final int f_160762_;
   public final int f_160763_;
   public final IntProvider f_160764_;
   public final FloatProvider f_160765_;
   public final FloatProvider f_160766_;
   public final float f_160767_;
   public final int f_160768_;
   public final int f_160769_;

   public DripstoneClusterConfiguration(int p_160772_, IntProvider p_160773_, IntProvider p_160774_, int p_160775_, int p_160776_, IntProvider p_160777_, FloatProvider p_160778_, FloatProvider p_160779_, float p_160780_, int p_160781_, int p_160782_) {
      this.f_160759_ = p_160772_;
      this.f_160760_ = p_160773_;
      this.f_160761_ = p_160774_;
      this.f_160762_ = p_160775_;
      this.f_160763_ = p_160776_;
      this.f_160764_ = p_160777_;
      this.f_160765_ = p_160778_;
      this.f_160766_ = p_160779_;
      this.f_160767_ = p_160780_;
      this.f_160768_ = p_160781_;
      this.f_160769_ = p_160782_;
   }
}