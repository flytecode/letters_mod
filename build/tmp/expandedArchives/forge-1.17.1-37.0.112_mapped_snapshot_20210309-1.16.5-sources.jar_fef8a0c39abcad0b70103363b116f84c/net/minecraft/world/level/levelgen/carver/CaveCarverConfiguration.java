package net.minecraft.world.level.levelgen.carver;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;

public class CaveCarverConfiguration extends CarverConfiguration {
   public static final Codec<CaveCarverConfiguration> f_159154_ = RecordCodecBuilder.create((p_159184_) -> {
      return p_159184_.group(CarverConfiguration.f_159087_.forGetter((p_159192_) -> {
         return p_159192_;
      }), FloatProvider.f_146502_.fieldOf("horizontal_radius_multiplier").forGetter((p_159190_) -> {
         return p_159190_.f_159155_;
      }), FloatProvider.f_146502_.fieldOf("vertical_radius_multiplier").forGetter((p_159188_) -> {
         return p_159188_.f_159156_;
      }), FloatProvider.m_146505_(-1.0F, 1.0F).fieldOf("floor_level").forGetter((p_159186_) -> {
         return p_159186_.f_159157_;
      })).apply(p_159184_, CaveCarverConfiguration::new);
   });
   public final FloatProvider f_159155_;
   public final FloatProvider f_159156_;
   final FloatProvider f_159157_;

   public CaveCarverConfiguration(float p_159169_, HeightProvider p_159170_, FloatProvider p_159171_, VerticalAnchor p_159172_, boolean p_159173_, CarverDebugSettings p_159174_, FloatProvider p_159175_, FloatProvider p_159176_, FloatProvider p_159177_) {
      super(p_159169_, p_159170_, p_159171_, p_159172_, p_159173_, p_159174_);
      this.f_159155_ = p_159175_;
      this.f_159156_ = p_159176_;
      this.f_159157_ = p_159177_;
   }

   public CaveCarverConfiguration(float p_159160_, HeightProvider p_159161_, FloatProvider p_159162_, VerticalAnchor p_159163_, boolean p_159164_, FloatProvider p_159165_, FloatProvider p_159166_, FloatProvider p_159167_) {
      this(p_159160_, p_159161_, p_159162_, p_159163_, p_159164_, CarverDebugSettings.f_159114_, p_159165_, p_159166_, p_159167_);
   }

   public CaveCarverConfiguration(CarverConfiguration p_159179_, FloatProvider p_159180_, FloatProvider p_159181_, FloatProvider p_159182_) {
      this(p_159179_.f_67859_, p_159179_.f_159088_, p_159179_.f_159089_, p_159179_.f_159090_, p_159179_.f_159091_, p_159179_.f_159092_, p_159180_, p_159181_, p_159182_);
   }
}