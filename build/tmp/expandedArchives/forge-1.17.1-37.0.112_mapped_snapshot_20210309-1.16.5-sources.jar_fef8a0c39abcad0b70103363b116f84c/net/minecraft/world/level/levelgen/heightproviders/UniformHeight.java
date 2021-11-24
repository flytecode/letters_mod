package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UniformHeight extends HeightProvider {
   public static final Codec<UniformHeight> f_162023_ = RecordCodecBuilder.create((p_162033_) -> {
      return p_162033_.group(VerticalAnchor.f_158914_.fieldOf("min_inclusive").forGetter((p_162043_) -> {
         return p_162043_.f_162025_;
      }), VerticalAnchor.f_158914_.fieldOf("max_inclusive").forGetter((p_162038_) -> {
         return p_162038_.f_162026_;
      })).apply(p_162033_, UniformHeight::new);
   });
   private static final Logger f_162024_ = LogManager.getLogger();
   private final VerticalAnchor f_162025_;
   private final VerticalAnchor f_162026_;

   private UniformHeight(VerticalAnchor p_162029_, VerticalAnchor p_162030_) {
      this.f_162025_ = p_162029_;
      this.f_162026_ = p_162030_;
   }

   public static UniformHeight m_162034_(VerticalAnchor p_162035_, VerticalAnchor p_162036_) {
      return new UniformHeight(p_162035_, p_162036_);
   }

   public int m_142233_(Random p_162040_, WorldGenerationContext p_162041_) {
      int i = this.f_162025_.m_142322_(p_162041_);
      int j = this.f_162026_.m_142322_(p_162041_);
      if (i > j) {
         f_162024_.warn("Empty height range: {}", (Object)this);
         return i;
      } else {
         return Mth.m_144928_(p_162040_, i, j);
      }
   }

   public HeightProviderType<?> m_142002_() {
      return HeightProviderType.f_161982_;
   }

   public String toString() {
      return "[" + this.f_162025_ + "-" + this.f_162026_ + "]";
   }
}