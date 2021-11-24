package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrapezoidHeight extends HeightProvider {
   public static final Codec<TrapezoidHeight> f_161993_ = RecordCodecBuilder.create((p_162005_) -> {
      return p_162005_.group(VerticalAnchor.f_158914_.fieldOf("min_inclusive").forGetter((p_162021_) -> {
         return p_162021_.f_161995_;
      }), VerticalAnchor.f_158914_.fieldOf("max_inclusive").forGetter((p_162019_) -> {
         return p_162019_.f_161996_;
      }), Codec.INT.optionalFieldOf("plateau", Integer.valueOf(0)).forGetter((p_162014_) -> {
         return p_162014_.f_161997_;
      })).apply(p_162005_, TrapezoidHeight::new);
   });
   private static final Logger f_161994_ = LogManager.getLogger();
   private final VerticalAnchor f_161995_;
   private final VerticalAnchor f_161996_;
   private final int f_161997_;

   private TrapezoidHeight(VerticalAnchor p_162000_, VerticalAnchor p_162001_, int p_162002_) {
      this.f_161995_ = p_162000_;
      this.f_161996_ = p_162001_;
      this.f_161997_ = p_162002_;
   }

   public static TrapezoidHeight m_162009_(VerticalAnchor p_162010_, VerticalAnchor p_162011_, int p_162012_) {
      return new TrapezoidHeight(p_162010_, p_162011_, p_162012_);
   }

   public static TrapezoidHeight m_162006_(VerticalAnchor p_162007_, VerticalAnchor p_162008_) {
      return m_162009_(p_162007_, p_162008_, 0);
   }

   public int m_142233_(Random p_162016_, WorldGenerationContext p_162017_) {
      int i = this.f_161995_.m_142322_(p_162017_);
      int j = this.f_161996_.m_142322_(p_162017_);
      if (i > j) {
         f_161994_.warn("Empty height range: {}", (Object)this);
         return i;
      } else {
         int k = j - i;
         if (this.f_161997_ >= k) {
            return Mth.m_144928_(p_162016_, i, j);
         } else {
            int l = (k - this.f_161997_) / 2;
            int i1 = k - l;
            return i + Mth.m_144928_(p_162016_, 0, i1) + Mth.m_144928_(p_162016_, 0, l);
         }
      }
   }

   public HeightProviderType<?> m_142002_() {
      return HeightProviderType.f_161985_;
   }

   public String toString() {
      return this.f_161997_ == 0 ? "triangle (" + this.f_161995_ + "-" + this.f_161996_ + ")" : "trapezoid(" + this.f_161997_ + ") in [" + this.f_161995_ + "-" + this.f_161996_ + "]";
   }
}