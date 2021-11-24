package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BiasedToBottomHeight extends HeightProvider {
   public static final Codec<BiasedToBottomHeight> f_161918_ = RecordCodecBuilder.create((p_161930_) -> {
      return p_161930_.group(VerticalAnchor.f_158914_.fieldOf("min_inclusive").forGetter((p_161943_) -> {
         return p_161943_.f_161920_;
      }), VerticalAnchor.f_158914_.fieldOf("max_inclusive").forGetter((p_161941_) -> {
         return p_161941_.f_161921_;
      }), Codec.intRange(1, Integer.MAX_VALUE).optionalFieldOf("inner", 1).forGetter((p_161936_) -> {
         return p_161936_.f_161922_;
      })).apply(p_161930_, BiasedToBottomHeight::new);
   });
   private static final Logger f_161919_ = LogManager.getLogger();
   private final VerticalAnchor f_161920_;
   private final VerticalAnchor f_161921_;
   private final int f_161922_;

   private BiasedToBottomHeight(VerticalAnchor p_161925_, VerticalAnchor p_161926_, int p_161927_) {
      this.f_161920_ = p_161925_;
      this.f_161921_ = p_161926_;
      this.f_161922_ = p_161927_;
   }

   public static BiasedToBottomHeight m_161931_(VerticalAnchor p_161932_, VerticalAnchor p_161933_, int p_161934_) {
      return new BiasedToBottomHeight(p_161932_, p_161933_, p_161934_);
   }

   public int m_142233_(Random p_161938_, WorldGenerationContext p_161939_) {
      int i = this.f_161920_.m_142322_(p_161939_);
      int j = this.f_161921_.m_142322_(p_161939_);
      if (j - i - this.f_161922_ + 1 <= 0) {
         f_161919_.warn("Empty height range: {}", (Object)this);
         return i;
      } else {
         int k = p_161938_.nextInt(j - i - this.f_161922_ + 1);
         return p_161938_.nextInt(k + this.f_161922_) + i;
      }
   }

   public HeightProviderType<?> m_142002_() {
      return HeightProviderType.f_161983_;
   }

   public String toString() {
      return "biased[" + this.f_161920_ + "-" + this.f_161921_ + " inner: " + this.f_161922_ + "]";
   }
}