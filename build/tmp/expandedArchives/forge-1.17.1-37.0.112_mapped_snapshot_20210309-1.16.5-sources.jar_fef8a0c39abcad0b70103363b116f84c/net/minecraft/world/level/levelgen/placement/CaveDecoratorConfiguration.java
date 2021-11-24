package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class CaveDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<CaveDecoratorConfiguration> f_162079_ = RecordCodecBuilder.create((p_162087_) -> {
      return p_162087_.group(CaveSurface.f_162094_.fieldOf("surface").forGetter((p_162091_) -> {
         return p_162091_.f_162080_;
      }), Codec.INT.fieldOf("floor_to_ceiling_search_range").forGetter((p_162089_) -> {
         return p_162089_.f_162081_;
      })).apply(p_162087_, CaveDecoratorConfiguration::new);
   });
   public final CaveSurface f_162080_;
   public final int f_162081_;

   public CaveDecoratorConfiguration(CaveSurface p_162084_, int p_162085_) {
      this.f_162080_ = p_162084_;
      this.f_162081_ = p_162085_;
   }
}