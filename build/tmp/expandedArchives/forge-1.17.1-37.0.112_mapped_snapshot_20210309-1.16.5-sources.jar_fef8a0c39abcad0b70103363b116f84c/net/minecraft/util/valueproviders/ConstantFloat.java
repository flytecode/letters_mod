package net.minecraft.util.valueproviders;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;

public class ConstantFloat extends FloatProvider {
   public static final ConstantFloat f_146451_ = new ConstantFloat(0.0F);
   public static final Codec<ConstantFloat> f_146452_ = Codec.either(Codec.FLOAT, RecordCodecBuilder.<ConstantFloat>create((p_146465_) -> {
      return p_146465_.group(Codec.FLOAT.fieldOf("value").forGetter((p_146473_) -> {
         return p_146473_.f_146453_;
      })).apply(p_146465_, ConstantFloat::new);
   })).xmap((p_146463_) -> {
      return p_146463_.map(ConstantFloat::m_146458_, (p_146470_) -> {
         return p_146470_;
      });
   }, (p_146461_) -> {
      return Either.left(p_146461_.f_146453_);
   });
   private final float f_146453_;

   public static ConstantFloat m_146458_(float p_146459_) {
      return p_146459_ == 0.0F ? f_146451_ : new ConstantFloat(p_146459_);
   }

   private ConstantFloat(float p_146456_) {
      this.f_146453_ = p_146456_;
   }

   public float m_146474_() {
      return this.f_146453_;
   }

   public float m_142269_(Random p_146467_) {
      return this.f_146453_;
   }

   public float m_142735_() {
      return this.f_146453_;
   }

   public float m_142734_() {
      return this.f_146453_ + 1.0F;
   }

   public FloatProviderType<?> m_141961_() {
      return FloatProviderType.f_146519_;
   }

   public String toString() {
      return Float.toString(this.f_146453_);
   }
}