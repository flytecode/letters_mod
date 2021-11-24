package net.minecraft.world.level.levelgen.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.feature.configurations.DecoratorConfiguration;

public class FrequencyWithExtraChanceDecoratorConfiguration implements DecoratorConfiguration {
   public static final Codec<FrequencyWithExtraChanceDecoratorConfiguration> f_70723_ = RecordCodecBuilder.create((p_70733_) -> {
      return p_70733_.group(Codec.INT.fieldOf("count").forGetter((p_162186_) -> {
         return p_162186_.f_70724_;
      }), Codec.FLOAT.fieldOf("extra_chance").forGetter((p_162184_) -> {
         return p_162184_.f_70725_;
      }), Codec.INT.fieldOf("extra_count").forGetter((p_162182_) -> {
         return p_162182_.f_70726_;
      })).apply(p_70733_, FrequencyWithExtraChanceDecoratorConfiguration::new);
   });
   public final int f_70724_;
   public final float f_70725_;
   public final int f_70726_;

   public FrequencyWithExtraChanceDecoratorConfiguration(int p_70729_, float p_70730_, int p_70731_) {
      this.f_70724_ = p_70729_;
      this.f_70725_ = p_70730_;
      this.f_70726_ = p_70731_;
   }
}