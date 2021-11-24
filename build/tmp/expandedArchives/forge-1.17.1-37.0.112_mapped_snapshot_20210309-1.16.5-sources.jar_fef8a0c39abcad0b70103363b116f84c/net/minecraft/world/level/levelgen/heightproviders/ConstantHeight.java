package net.minecraft.world.level.levelgen.heightproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;

public class ConstantHeight extends HeightProvider {
   public static final ConstantHeight f_161945_ = new ConstantHeight(VerticalAnchor.m_158922_(0));
   public static final Codec<ConstantHeight> f_161946_ = Codec.either(VerticalAnchor.f_158914_, RecordCodecBuilder.<ConstantHeight>create((p_161955_) -> {
      return p_161955_.group(VerticalAnchor.f_158914_.fieldOf("value").forGetter((p_161967_) -> {
         return p_161967_.f_161947_;
      })).apply(p_161955_, ConstantHeight::new);
   })).xmap((p_161953_) -> {
      return p_161953_.map(ConstantHeight::m_161956_, (p_161965_) -> {
         return p_161965_;
      });
   }, (p_161959_) -> {
      return Either.left(p_161959_.f_161947_);
   });
   private final VerticalAnchor f_161947_;

   public static ConstantHeight m_161956_(VerticalAnchor p_161957_) {
      return new ConstantHeight(p_161957_);
   }

   private ConstantHeight(VerticalAnchor p_161950_) {
      this.f_161947_ = p_161950_;
   }

   public VerticalAnchor m_161963_() {
      return this.f_161947_;
   }

   public int m_142233_(Random p_161961_, WorldGenerationContext p_161962_) {
      return this.f_161947_.m_142322_(p_161962_);
   }

   public HeightProviderType<?> m_142002_() {
      return HeightProviderType.f_161981_;
   }

   public String toString() {
      return this.f_161947_.toString();
   }
}