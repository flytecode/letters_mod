package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;

public class ThreeLayersFeatureSize extends FeatureSize {
   public static final Codec<ThreeLayersFeatureSize> f_68306_ = RecordCodecBuilder.create((p_68326_) -> {
      return p_68326_.group(Codec.intRange(0, 80).fieldOf("limit").orElse(1).forGetter((p_161335_) -> {
         return p_161335_.f_68307_;
      }), Codec.intRange(0, 80).fieldOf("upper_limit").orElse(1).forGetter((p_161333_) -> {
         return p_161333_.f_68308_;
      }), Codec.intRange(0, 16).fieldOf("lower_size").orElse(0).forGetter((p_161331_) -> {
         return p_161331_.f_68309_;
      }), Codec.intRange(0, 16).fieldOf("middle_size").orElse(1).forGetter((p_161329_) -> {
         return p_161329_.f_68310_;
      }), Codec.intRange(0, 16).fieldOf("upper_size").orElse(1).forGetter((p_161327_) -> {
         return p_161327_.f_68311_;
      }), m_68286_()).apply(p_68326_, ThreeLayersFeatureSize::new);
   });
   private final int f_68307_;
   private final int f_68308_;
   private final int f_68309_;
   private final int f_68310_;
   private final int f_68311_;

   public ThreeLayersFeatureSize(int p_68314_, int p_68315_, int p_68316_, int p_68317_, int p_68318_, OptionalInt p_68319_) {
      super(p_68319_);
      this.f_68307_ = p_68314_;
      this.f_68308_ = p_68315_;
      this.f_68309_ = p_68316_;
      this.f_68310_ = p_68317_;
      this.f_68311_ = p_68318_;
   }

   protected FeatureSizeType<?> m_7612_() {
      return FeatureSizeType.f_68297_;
   }

   public int m_6133_(int p_68321_, int p_68322_) {
      if (p_68322_ < this.f_68307_) {
         return this.f_68309_;
      } else {
         return p_68322_ >= p_68321_ - this.f_68308_ ? this.f_68311_ : this.f_68310_;
      }
   }
}