package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VeryBiasedToBottomHeight extends HeightProvider {
   public static final Codec<VeryBiasedToBottomHeight> f_162045_ = RecordCodecBuilder.create((p_162057_) -> {
      return p_162057_.group(VerticalAnchor.f_158914_.fieldOf("min_inclusive").forGetter((p_162070_) -> {
         return p_162070_.f_162047_;
      }), VerticalAnchor.f_158914_.fieldOf("max_inclusive").forGetter((p_162068_) -> {
         return p_162068_.f_162048_;
      }), Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("inner", 1).forGetter((p_162063_) -> {
         return p_162063_.f_162049_;
      })).apply(p_162057_, VeryBiasedToBottomHeight::new);
   });
   private static final Logger f_162046_ = LogManager.getLogger();
   private final VerticalAnchor f_162047_;
   private final VerticalAnchor f_162048_;
   private final int f_162049_;

   private VeryBiasedToBottomHeight(VerticalAnchor p_162052_, VerticalAnchor p_162053_, int p_162054_) {
      this.f_162047_ = p_162052_;
      this.f_162048_ = p_162053_;
      this.f_162049_ = p_162054_;
   }

   public static VeryBiasedToBottomHeight m_162058_(VerticalAnchor p_162059_, VerticalAnchor p_162060_, int p_162061_) {
      return new VeryBiasedToBottomHeight(p_162059_, p_162060_, p_162061_);
   }

   public int m_142233_(Random p_162065_, WorldGenerationContext p_162066_) {
      int i = this.f_162047_.m_142322_(p_162066_);
      int j = this.f_162048_.m_142322_(p_162066_);
      if (j - i - this.f_162049_ + 1 <= 0) {
         f_162046_.warn("Empty height range: {}", (Object)this);
         return i;
      } else {
         int k = Mth.m_14072_(p_162065_, i + this.f_162049_, j);
         int l = Mth.m_14072_(p_162065_, i, k - 1);
         return Mth.m_14072_(p_162065_, i, l - 1 + this.f_162049_);
      }
   }

   public HeightProviderType<?> m_142002_() {
      return HeightProviderType.f_161984_;
   }

   public String toString() {
      return "biased[" + this.f_162047_ + "-" + this.f_162048_ + " inner: " + this.f_162049_ + "]";
   }
}