package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Function;
import net.minecraft.world.level.dimension.DimensionType;

public class NoiseSettings {
   public static final Codec<NoiseSettings> f_64507_ = RecordCodecBuilder.<NoiseSettings>create((p_64536_) -> {
      return p_64536_.group(Codec.intRange(DimensionType.f_156653_, DimensionType.f_156652_).fieldOf("min_y").forGetter(NoiseSettings::m_158703_), Codec.intRange(0, DimensionType.f_156651_).fieldOf("height").forGetter(NoiseSettings::m_64534_), NoiseSamplingSettings.f_64489_.fieldOf("sampling").forGetter(NoiseSettings::m_64537_), NoiseSlideSettings.f_64548_.fieldOf("top_slide").forGetter(NoiseSettings::m_64538_), NoiseSlideSettings.f_64548_.fieldOf("bottom_slide").forGetter(NoiseSettings::m_64539_), Codec.intRange(1, 4).fieldOf("size_horizontal").forGetter(NoiseSettings::m_64540_), Codec.intRange(1, 4).fieldOf("size_vertical").forGetter(NoiseSettings::m_64541_), Codec.DOUBLE.fieldOf("density_factor").forGetter(NoiseSettings::m_64542_), Codec.DOUBLE.fieldOf("density_offset").forGetter(NoiseSettings::m_64543_), Codec.BOOL.fieldOf("simplex_surface_noise").forGetter(NoiseSettings::m_64544_), Codec.BOOL.optionalFieldOf("random_density_offset", Boolean.valueOf(false), Lifecycle.experimental()).forGetter(NoiseSettings::m_64545_), Codec.BOOL.optionalFieldOf("island_noise_override", Boolean.valueOf(false), Lifecycle.experimental()).forGetter(NoiseSettings::m_64546_), Codec.BOOL.optionalFieldOf("amplified", Boolean.valueOf(false), Lifecycle.experimental()).forGetter(NoiseSettings::m_64547_)).apply(p_64536_, NoiseSettings::new);
   }).comapFlatMap(NoiseSettings::m_158720_, Function.identity());
   private final int f_158688_;
   private final int f_64508_;
   private final NoiseSamplingSettings f_64509_;
   private final NoiseSlideSettings f_64510_;
   private final NoiseSlideSettings f_64511_;
   private final int f_64512_;
   private final int f_64513_;
   private final double f_64514_;
   private final double f_64515_;
   private final boolean f_64516_;
   private final boolean f_64517_;
   private final boolean f_64518_;
   private final boolean f_64519_;

   private static DataResult<NoiseSettings> m_158720_(NoiseSettings p_158721_) {
      if (p_158721_.m_158703_() + p_158721_.m_64534_() > DimensionType.f_156652_ + 1) {
         return DataResult.error("min_y + height cannot be higher than: " + (DimensionType.f_156652_ + 1));
      } else if (p_158721_.m_64534_() % 16 != 0) {
         return DataResult.error("height has to be a multiple of 16");
      } else {
         return p_158721_.m_158703_() % 16 != 0 ? DataResult.error("min_y has to be a multiple of 16") : DataResult.success(p_158721_);
      }
   }

   private NoiseSettings(int p_158690_, int p_158691_, NoiseSamplingSettings p_158692_, NoiseSlideSettings p_158693_, NoiseSlideSettings p_158694_, int p_158695_, int p_158696_, double p_158697_, double p_158698_, boolean p_158699_, boolean p_158700_, boolean p_158701_, boolean p_158702_) {
      this.f_158688_ = p_158690_;
      this.f_64508_ = p_158691_;
      this.f_64509_ = p_158692_;
      this.f_64510_ = p_158693_;
      this.f_64511_ = p_158694_;
      this.f_64512_ = p_158695_;
      this.f_64513_ = p_158696_;
      this.f_64514_ = p_158697_;
      this.f_64515_ = p_158698_;
      this.f_64516_ = p_158699_;
      this.f_64517_ = p_158700_;
      this.f_64518_ = p_158701_;
      this.f_64519_ = p_158702_;
   }

   public static NoiseSettings m_158704_(int p_158705_, int p_158706_, NoiseSamplingSettings p_158707_, NoiseSlideSettings p_158708_, NoiseSlideSettings p_158709_, int p_158710_, int p_158711_, double p_158712_, double p_158713_, boolean p_158714_, boolean p_158715_, boolean p_158716_, boolean p_158717_) {
      NoiseSettings noisesettings = new NoiseSettings(p_158705_, p_158706_, p_158707_, p_158708_, p_158709_, p_158710_, p_158711_, p_158712_, p_158713_, p_158714_, p_158715_, p_158716_, p_158717_);
      m_158720_(noisesettings).error().ifPresent((p_158719_) -> {
         throw new IllegalStateException(p_158719_.message());
      });
      return noisesettings;
   }

   public int m_158703_() {
      return this.f_158688_;
   }

   public int m_64534_() {
      return this.f_64508_;
   }

   public NoiseSamplingSettings m_64537_() {
      return this.f_64509_;
   }

   public NoiseSlideSettings m_64538_() {
      return this.f_64510_;
   }

   public NoiseSlideSettings m_64539_() {
      return this.f_64511_;
   }

   public int m_64540_() {
      return this.f_64512_;
   }

   public int m_64541_() {
      return this.f_64513_;
   }

   public double m_64542_() {
      return this.f_64514_;
   }

   public double m_64543_() {
      return this.f_64515_;
   }

   @Deprecated
   public boolean m_64544_() {
      return this.f_64516_;
   }

   @Deprecated
   public boolean m_64545_() {
      return this.f_64517_;
   }

   @Deprecated
   public boolean m_64546_() {
      return this.f_64518_;
   }

   @Deprecated
   public boolean m_64547_() {
      return this.f_64519_;
   }
}