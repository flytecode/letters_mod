package net.minecraft.world.level.levelgen;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import java.util.function.Function;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.dimension.DimensionType;

public abstract class VerticalAnchor {
   public static final Codec<VerticalAnchor> f_158914_ = ExtraCodecs.m_144639_(VerticalAnchor.Absolute.f_158944_, ExtraCodecs.m_144639_(VerticalAnchor.AboveBottom.f_158937_, VerticalAnchor.BelowTop.f_158951_)).xmap(VerticalAnchor::m_158924_, VerticalAnchor::m_158926_);
   private static final VerticalAnchor f_158915_ = m_158930_(0);
   private static final VerticalAnchor f_158916_ = m_158935_(0);
   private final int f_158917_;

   protected VerticalAnchor(int p_158920_) {
      this.f_158917_ = p_158920_;
   }

   public static VerticalAnchor m_158922_(int p_158923_) {
      return new VerticalAnchor.Absolute(p_158923_);
   }

   public static VerticalAnchor m_158930_(int p_158931_) {
      return new VerticalAnchor.AboveBottom(p_158931_);
   }

   public static VerticalAnchor m_158935_(int p_158936_) {
      return new VerticalAnchor.BelowTop(p_158936_);
   }

   public static VerticalAnchor m_158921_() {
      return f_158915_;
   }

   public static VerticalAnchor m_158929_() {
      return f_158916_;
   }

   private static VerticalAnchor m_158924_(Either<VerticalAnchor.Absolute, Either<VerticalAnchor.AboveBottom, VerticalAnchor.BelowTop>> p_158925_) {
      return p_158925_.map(Function.identity(), (p_158933_) -> {
         return p_158933_.map(Function.identity(), Function.identity());
      });
   }

   private static Either<VerticalAnchor.Absolute, Either<VerticalAnchor.AboveBottom, VerticalAnchor.BelowTop>> m_158926_(VerticalAnchor p_158927_) {
      return p_158927_ instanceof VerticalAnchor.Absolute ? Either.left((VerticalAnchor.Absolute)p_158927_) : Either.right(p_158927_ instanceof VerticalAnchor.AboveBottom ? Either.left((VerticalAnchor.AboveBottom)p_158927_) : Either.right((VerticalAnchor.BelowTop)p_158927_));
   }

   protected int m_158934_() {
      return this.f_158917_;
   }

   public abstract int m_142322_(WorldGenerationContext p_158928_);

   static final class AboveBottom extends VerticalAnchor {
      public static final Codec<VerticalAnchor.AboveBottom> f_158937_ = Codec.intRange(DimensionType.f_156653_, DimensionType.f_156652_).fieldOf("above_bottom").xmap(VerticalAnchor.AboveBottom::new, VerticalAnchor::m_158934_).codec();

      protected AboveBottom(int p_158940_) {
         super(p_158940_);
      }

      public int m_142322_(WorldGenerationContext p_158942_) {
         return p_158942_.m_142201_() + this.m_158934_();
      }

      public String toString() {
         return this.m_158934_() + " above bottom";
      }
   }

   static final class Absolute extends VerticalAnchor {
      public static final Codec<VerticalAnchor.Absolute> f_158944_ = Codec.intRange(DimensionType.f_156653_, DimensionType.f_156652_).fieldOf("absolute").xmap(VerticalAnchor.Absolute::new, VerticalAnchor::m_158934_).codec();

      protected Absolute(int p_158947_) {
         super(p_158947_);
      }

      public int m_142322_(WorldGenerationContext p_158949_) {
         return this.m_158934_();
      }

      public String toString() {
         return this.m_158934_() + " absolute";
      }
   }

   static final class BelowTop extends VerticalAnchor {
      public static final Codec<VerticalAnchor.BelowTop> f_158951_ = Codec.intRange(DimensionType.f_156653_, DimensionType.f_156652_).fieldOf("below_top").xmap(VerticalAnchor.BelowTop::new, VerticalAnchor::m_158934_).codec();

      protected BelowTop(int p_158954_) {
         super(p_158954_);
      }

      public int m_142322_(WorldGenerationContext p_158956_) {
         return p_158956_.m_142208_() - 1 + p_158956_.m_142201_() - this.m_158934_();
      }

      public String toString() {
         return this.m_158934_() + " below top";
      }
   }
}