package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.Mth;

public class UniformFloat extends FloatProvider {
   public static final Codec<UniformFloat> f_146590_ = RecordCodecBuilder.<UniformFloat>create((p_146601_) -> {
      return p_146601_.group(Codec.FLOAT.fieldOf("min_inclusive").forGetter((p_146612_) -> {
         return p_146612_.f_146591_;
      }), Codec.FLOAT.fieldOf("max_exclusive").forGetter((p_146609_) -> {
         return p_146609_.f_146592_;
      })).apply(p_146601_, UniformFloat::new);
   }).comapFlatMap((p_146599_) -> {
      return p_146599_.f_146592_ <= p_146599_.f_146591_ ? DataResult.error("Max must be larger than min, min_inclusive: " + p_146599_.f_146591_ + ", max_exclusive: " + p_146599_.f_146592_) : DataResult.success(p_146599_);
   }, Function.identity());
   private final float f_146591_;
   private final float f_146592_;

   private UniformFloat(float p_146595_, float p_146596_) {
      this.f_146591_ = p_146595_;
      this.f_146592_ = p_146596_;
   }

   public static UniformFloat m_146605_(float p_146606_, float p_146607_) {
      if (p_146607_ <= p_146606_) {
         throw new IllegalArgumentException("Max must exceed min");
      } else {
         return new UniformFloat(p_146606_, p_146607_);
      }
   }

   public float m_142269_(Random p_146603_) {
      return Mth.m_144924_(p_146603_, this.f_146591_, this.f_146592_);
   }

   public float m_142735_() {
      return this.f_146591_;
   }

   public float m_142734_() {
      return this.f_146592_;
   }

   public FloatProviderType<?> m_141961_() {
      return FloatProviderType.f_146520_;
   }

   public String toString() {
      return "[" + this.f_146591_ + "-" + this.f_146592_ + "]";
   }
}