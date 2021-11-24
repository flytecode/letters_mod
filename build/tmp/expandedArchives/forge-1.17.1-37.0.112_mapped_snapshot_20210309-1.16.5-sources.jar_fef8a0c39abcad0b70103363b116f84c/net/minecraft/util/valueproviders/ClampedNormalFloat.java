package net.minecraft.util.valueproviders;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Random;
import java.util.function.Function;
import net.minecraft.util.Mth;

public class ClampedNormalFloat extends FloatProvider {
   public static final Codec<ClampedNormalFloat> f_146411_ = RecordCodecBuilder.<ClampedNormalFloat>create((p_146431_) -> {
      return p_146431_.group(Codec.FLOAT.fieldOf("mean").forGetter((p_146449_) -> {
         return p_146449_.f_146412_;
      }), Codec.FLOAT.fieldOf("deviation").forGetter((p_146447_) -> {
         return p_146447_.f_146413_;
      }), Codec.FLOAT.fieldOf("min").forGetter((p_146445_) -> {
         return p_146445_.f_146414_;
      }), Codec.FLOAT.fieldOf("max").forGetter((p_146442_) -> {
         return p_146442_.f_146415_;
      })).apply(p_146431_, ClampedNormalFloat::new);
   }).comapFlatMap((p_146429_) -> {
      return p_146429_.f_146415_ < p_146429_.f_146414_ ? DataResult.error("Max must be larger than min: [" + p_146429_.f_146414_ + ", " + p_146429_.f_146415_ + "]") : DataResult.success(p_146429_);
   }, Function.identity());
   private float f_146412_;
   private float f_146413_;
   private float f_146414_;
   private float f_146415_;

   public static ClampedNormalFloat m_146423_(float p_146424_, float p_146425_, float p_146426_, float p_146427_) {
      return new ClampedNormalFloat(p_146424_, p_146425_, p_146426_, p_146427_);
   }

   private ClampedNormalFloat(float p_146418_, float p_146419_, float p_146420_, float p_146421_) {
      this.f_146412_ = p_146418_;
      this.f_146413_ = p_146419_;
      this.f_146414_ = p_146420_;
      this.f_146415_ = p_146421_;
   }

   public float m_142269_(Random p_146433_) {
      return m_146434_(p_146433_, this.f_146412_, this.f_146413_, this.f_146414_, this.f_146415_);
   }

   public static float m_146434_(Random p_146435_, float p_146436_, float p_146437_, float p_146438_, float p_146439_) {
      return Mth.m_14036_(Mth.m_144935_(p_146435_, p_146436_, p_146437_), p_146438_, p_146439_);
   }

   public float m_142735_() {
      return this.f_146414_;
   }

   public float m_142734_() {
      return this.f_146415_;
   }

   public FloatProviderType<?> m_141961_() {
      return FloatProviderType.f_146521_;
   }

   public String toString() {
      return "normal(" + this.f_146412_ + ", " + this.f_146413_ + ") in [" + this.f_146414_ + "-" + this.f_146415_ + "]";
   }
}