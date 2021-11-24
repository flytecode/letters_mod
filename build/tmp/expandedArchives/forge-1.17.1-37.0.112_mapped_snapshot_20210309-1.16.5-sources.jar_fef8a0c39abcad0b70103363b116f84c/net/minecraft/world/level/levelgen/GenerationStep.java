package net.minecraft.world.level.levelgen;

import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.StringRepresentable;

public class GenerationStep {
   public static enum Carving implements StringRepresentable {
      AIR("air"),
      LIQUID("liquid");

      public static final Codec<GenerationStep.Carving> f_64194_ = StringRepresentable.m_14350_(GenerationStep.Carving::values, GenerationStep.Carving::m_64206_);
      private static final Map<String, GenerationStep.Carving> f_64195_ = Arrays.stream(values()).collect(Collectors.toMap(GenerationStep.Carving::m_64208_, (p_64205_) -> {
         return p_64205_;
      }));
      private final String f_64196_;

      private Carving(String p_64202_) {
         this.f_64196_ = p_64202_;
      }

      public String m_64208_() {
         return this.f_64196_;
      }

      @Nullable
      public static GenerationStep.Carving m_64206_(String p_64207_) {
         return f_64195_.get(p_64207_);
      }

      public String m_7912_() {
         return this.f_64196_;
      }
   }

   public static enum Decoration {
      RAW_GENERATION,
      LAKES,
      LOCAL_MODIFICATIONS,
      UNDERGROUND_STRUCTURES,
      SURFACE_STRUCTURES,
      STRONGHOLDS,
      UNDERGROUND_ORES,
      UNDERGROUND_DECORATION,
      VEGETAL_DECORATION,
      TOP_LAYER_MODIFICATION;
   }
}