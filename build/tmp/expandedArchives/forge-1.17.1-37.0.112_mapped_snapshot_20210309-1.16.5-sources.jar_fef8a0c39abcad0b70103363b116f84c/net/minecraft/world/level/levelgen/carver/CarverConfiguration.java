package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CarverConfiguration extends ProbabilityFeatureConfiguration {
   public static final MapCodec<CarverConfiguration> f_159087_ = RecordCodecBuilder.mapCodec((p_159101_) -> {
      return p_159101_.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((p_159113_) -> {
         return p_159113_.f_67859_;
      }), HeightProvider.f_161970_.fieldOf("y").forGetter((p_159111_) -> {
         return p_159111_.f_159088_;
      }), FloatProvider.f_146502_.fieldOf("yScale").forGetter((p_159109_) -> {
         return p_159109_.f_159089_;
      }), VerticalAnchor.f_158914_.fieldOf("lava_level").forGetter((p_159107_) -> {
         return p_159107_.f_159090_;
      }), Codec.BOOL.fieldOf("aquifers_enabled").forGetter((p_159105_) -> {
         return p_159105_.f_159091_;
      }), CarverDebugSettings.f_159115_.optionalFieldOf("debug_settings", CarverDebugSettings.f_159114_).forGetter((p_159103_) -> {
         return p_159103_.f_159092_;
      })).apply(p_159101_, CarverConfiguration::new);
   });
   public final HeightProvider f_159088_;
   public final FloatProvider f_159089_;
   public final VerticalAnchor f_159090_;
   public final boolean f_159091_;
   public final CarverDebugSettings f_159092_;

   public CarverConfiguration(float p_159094_, HeightProvider p_159095_, FloatProvider p_159096_, VerticalAnchor p_159097_, boolean p_159098_, CarverDebugSettings p_159099_) {
      super(p_159094_);
      this.f_159088_ = p_159095_;
      this.f_159089_ = p_159096_;
      this.f_159090_ = p_159097_;
      this.f_159091_ = p_159098_;
      this.f_159092_ = p_159099_;
   }
}