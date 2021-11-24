package net.minecraft.world.level.levelgen.feature.configurations;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;

public class GeodeConfiguration implements FeatureConfiguration {
   public static final Codec<Double> f_160811_ = Codec.doubleRange(0.0D, 1.0D);
   public static final Codec<GeodeConfiguration> f_160812_ = RecordCodecBuilder.create((p_160842_) -> {
      return p_160842_.group(GeodeBlockSettings.f_158295_.fieldOf("blocks").forGetter((p_160868_) -> {
         return p_160868_.f_160813_;
      }), GeodeLayerSettings.f_158341_.fieldOf("layers").forGetter((p_160866_) -> {
         return p_160866_.f_160814_;
      }), GeodeCrackSettings.f_158324_.fieldOf("crack").forGetter((p_160864_) -> {
         return p_160864_.f_160815_;
      }), f_160811_.fieldOf("use_potential_placements_chance").orElse(0.35D).forGetter((p_160862_) -> {
         return p_160862_.f_160816_;
      }), f_160811_.fieldOf("use_alternate_layer0_chance").orElse(0.0D).forGetter((p_160860_) -> {
         return p_160860_.f_160817_;
      }), Codec.BOOL.fieldOf("placements_require_layer0_alternate").orElse(true).forGetter((p_160858_) -> {
         return p_160858_.f_160818_;
      }), IntProvider.m_146545_(1, 20).fieldOf("outer_wall_distance").orElse(UniformInt.m_146622_(4, 5)).forGetter((p_160856_) -> {
         return p_160856_.f_160819_;
      }), IntProvider.m_146545_(1, 20).fieldOf("distribution_points").orElse(UniformInt.m_146622_(3, 4)).forGetter((p_160854_) -> {
         return p_160854_.f_160820_;
      }), IntProvider.m_146545_(0, 10).fieldOf("point_offset").orElse(UniformInt.m_146622_(1, 2)).forGetter((p_160852_) -> {
         return p_160852_.f_160821_;
      }), Codec.INT.fieldOf("min_gen_offset").orElse(-16).forGetter((p_160850_) -> {
         return p_160850_.f_160822_;
      }), Codec.INT.fieldOf("max_gen_offset").orElse(16).forGetter((p_160848_) -> {
         return p_160848_.f_160823_;
      }), f_160811_.fieldOf("noise_multiplier").orElse(0.05D).forGetter((p_160846_) -> {
         return p_160846_.f_160824_;
      }), Codec.INT.fieldOf("invalid_blocks_threshold").forGetter((p_160844_) -> {
         return p_160844_.f_160825_;
      })).apply(p_160842_, GeodeConfiguration::new);
   });
   public final GeodeBlockSettings f_160813_;
   public final GeodeLayerSettings f_160814_;
   public final GeodeCrackSettings f_160815_;
   public final double f_160816_;
   public final double f_160817_;
   public final boolean f_160818_;
   public final IntProvider f_160819_;
   public final IntProvider f_160820_;
   public final IntProvider f_160821_;
   public final int f_160822_;
   public final int f_160823_;
   public final double f_160824_;
   public final int f_160825_;

   public GeodeConfiguration(GeodeBlockSettings p_160828_, GeodeLayerSettings p_160829_, GeodeCrackSettings p_160830_, double p_160831_, double p_160832_, boolean p_160833_, IntProvider p_160834_, IntProvider p_160835_, IntProvider p_160836_, int p_160837_, int p_160838_, double p_160839_, int p_160840_) {
      this.f_160813_ = p_160828_;
      this.f_160814_ = p_160829_;
      this.f_160815_ = p_160830_;
      this.f_160816_ = p_160831_;
      this.f_160817_ = p_160832_;
      this.f_160818_ = p_160833_;
      this.f_160819_ = p_160834_;
      this.f_160820_ = p_160835_;
      this.f_160821_ = p_160836_;
      this.f_160822_ = p_160837_;
      this.f_160823_ = p_160838_;
      this.f_160824_ = p_160839_;
      this.f_160825_ = p_160840_;
   }
}