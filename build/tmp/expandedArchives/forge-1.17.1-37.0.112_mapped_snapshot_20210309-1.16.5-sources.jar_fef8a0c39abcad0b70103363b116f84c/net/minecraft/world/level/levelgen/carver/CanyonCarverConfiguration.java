package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CanyonCarverConfiguration extends CarverConfiguration {
   public static final Codec<CanyonCarverConfiguration> f_158966_ = RecordCodecBuilder.create((p_158984_) -> {
      return p_158984_.group(CarverConfiguration.f_159087_.forGetter((p_158990_) -> {
         return p_158990_;
      }), FloatProvider.f_146502_.fieldOf("vertical_rotation").forGetter((p_158988_) -> {
         return p_158988_.f_158967_;
      }), CanyonCarverConfiguration.CanyonShapeConfiguration.f_158991_.fieldOf("shape").forGetter((p_158986_) -> {
         return p_158986_.f_158968_;
      })).apply(p_158984_, CanyonCarverConfiguration::new);
   });
   public final FloatProvider f_158967_;
   public final CanyonCarverConfiguration.CanyonShapeConfiguration f_158968_;

   public CanyonCarverConfiguration(float p_158971_, HeightProvider p_158972_, FloatProvider p_158973_, VerticalAnchor p_158974_, boolean p_158975_, CarverDebugSettings p_158976_, FloatProvider p_158977_, CanyonCarverConfiguration.CanyonShapeConfiguration p_158978_) {
      super(p_158971_, p_158972_, p_158973_, p_158974_, p_158975_, p_158976_);
      this.f_158967_ = p_158977_;
      this.f_158968_ = p_158978_;
   }

   public CanyonCarverConfiguration(CarverConfiguration p_158980_, FloatProvider p_158981_, CanyonCarverConfiguration.CanyonShapeConfiguration p_158982_) {
      this(p_158980_.f_67859_, p_158980_.f_159088_, p_158980_.f_159089_, p_158980_.f_159090_, p_158980_.f_159091_, p_158980_.f_159092_, p_158981_, p_158982_);
   }

   public static class CanyonShapeConfiguration {
      public static final Codec<CanyonCarverConfiguration.CanyonShapeConfiguration> f_158991_ = RecordCodecBuilder.create((p_159007_) -> {
         return p_159007_.group(FloatProvider.f_146502_.fieldOf("distance_factor").forGetter((p_159019_) -> {
            return p_159019_.f_158992_;
         }), FloatProvider.f_146502_.fieldOf("thickness").forGetter((p_159017_) -> {
            return p_159017_.f_158993_;
         }), ExtraCodecs.f_144628_.fieldOf("width_smoothness").forGetter((p_159015_) -> {
            return p_159015_.f_158994_;
         }), FloatProvider.f_146502_.fieldOf("horizontal_radius_factor").forGetter((p_159013_) -> {
            return p_159013_.f_158995_;
         }), Codec.FLOAT.fieldOf("vertical_radius_default_factor").forGetter((p_159011_) -> {
            return p_159011_.f_158996_;
         }), Codec.FLOAT.fieldOf("vertical_radius_center_factor").forGetter((p_159009_) -> {
            return p_159009_.f_158997_;
         })).apply(p_159007_, CanyonCarverConfiguration.CanyonShapeConfiguration::new);
      });
      public final FloatProvider f_158992_;
      public final FloatProvider f_158993_;
      public final int f_158994_;
      public final FloatProvider f_158995_;
      public final float f_158996_;
      public final float f_158997_;

      public CanyonShapeConfiguration(FloatProvider p_159000_, FloatProvider p_159001_, int p_159002_, FloatProvider p_159003_, float p_159004_, float p_159005_) {
         this.f_158994_ = p_159002_;
         this.f_158995_ = p_159003_;
         this.f_158996_ = p_159004_;
         this.f_158997_ = p_159005_;
         this.f_158992_ = p_159000_;
         this.f_158993_ = p_159001_;
      }
   }
}