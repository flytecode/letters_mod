package net.minecraft.world.level.levelgen.feature.featuresize;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.OptionalInt;

public class TwoLayersFeatureSize extends FeatureSize {
   public static final Codec<TwoLayersFeatureSize> f_68336_ = RecordCodecBuilder.create((p_68356_) -> {
      return p_68356_.group(Codec.intRange(0, 81).fieldOf("limit").orElse(1).forGetter((p_161341_) -> {
         return p_161341_.f_68337_;
      }), Codec.intRange(0, 16).fieldOf("lower_size").orElse(0).forGetter((p_161339_) -> {
         return p_161339_.f_68338_;
      }), Codec.intRange(0, 16).fieldOf("upper_size").orElse(1).forGetter((p_161337_) -> {
         return p_161337_.f_68339_;
      }), m_68286_()).apply(p_68356_, TwoLayersFeatureSize::new);
   });
   private final int f_68337_;
   private final int f_68338_;
   private final int f_68339_;

   public TwoLayersFeatureSize(int p_68342_, int p_68343_, int p_68344_) {
      this(p_68342_, p_68343_, p_68344_, OptionalInt.empty());
   }

   public TwoLayersFeatureSize(int p_68346_, int p_68347_, int p_68348_, OptionalInt p_68349_) {
      super(p_68349_);
      this.f_68337_ = p_68346_;
      this.f_68338_ = p_68347_;
      this.f_68339_ = p_68348_;
   }

   protected FeatureSizeType<?> m_7612_() {
      return FeatureSizeType.f_68296_;
   }

   public int m_6133_(int p_68351_, int p_68352_) {
      return p_68352_ < this.f_68337_ ? this.f_68338_ : this.f_68339_;
   }
}