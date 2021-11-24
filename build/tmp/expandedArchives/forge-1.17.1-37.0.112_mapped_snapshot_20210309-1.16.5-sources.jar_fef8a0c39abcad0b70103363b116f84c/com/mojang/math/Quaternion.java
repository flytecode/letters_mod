package com.mojang.math;

import net.minecraft.util.Mth;

public final class Quaternion {
   public static final Quaternion f_80118_ = new Quaternion(0.0F, 0.0F, 0.0F, 1.0F);
   private float f_80119_;
   private float f_80120_;
   private float f_80121_;
   private float f_80122_;

   public Quaternion(float p_80125_, float p_80126_, float p_80127_, float p_80128_) {
      this.f_80119_ = p_80125_;
      this.f_80120_ = p_80126_;
      this.f_80121_ = p_80127_;
      this.f_80122_ = p_80128_;
   }

   public Quaternion(Vector3f p_80137_, float p_80138_, boolean p_80139_) {
      if (p_80139_) {
         p_80138_ *= ((float)Math.PI / 180F);
      }

      float f = m_80154_(p_80138_ / 2.0F);
      this.f_80119_ = p_80137_.m_122239_() * f;
      this.f_80120_ = p_80137_.m_122260_() * f;
      this.f_80121_ = p_80137_.m_122269_() * f;
      this.f_80122_ = m_80151_(p_80138_ / 2.0F);
   }

   public Quaternion(float p_80130_, float p_80131_, float p_80132_, boolean p_80133_) {
      if (p_80133_) {
         p_80130_ *= ((float)Math.PI / 180F);
         p_80131_ *= ((float)Math.PI / 180F);
         p_80132_ *= ((float)Math.PI / 180F);
      }

      float f = m_80154_(0.5F * p_80130_);
      float f1 = m_80151_(0.5F * p_80130_);
      float f2 = m_80154_(0.5F * p_80131_);
      float f3 = m_80151_(0.5F * p_80131_);
      float f4 = m_80154_(0.5F * p_80132_);
      float f5 = m_80151_(0.5F * p_80132_);
      this.f_80119_ = f * f3 * f5 + f1 * f2 * f4;
      this.f_80120_ = f1 * f2 * f5 - f * f3 * f4;
      this.f_80121_ = f * f2 * f5 + f1 * f3 * f4;
      this.f_80122_ = f1 * f3 * f5 - f * f2 * f4;
   }

   public Quaternion(Quaternion p_80135_) {
      this.f_80119_ = p_80135_.f_80119_;
      this.f_80120_ = p_80135_.f_80120_;
      this.f_80121_ = p_80135_.f_80121_;
      this.f_80122_ = p_80135_.f_80122_;
   }

   public static Quaternion m_175218_(float p_175219_, float p_175220_, float p_175221_) {
      Quaternion quaternion = f_80118_.m_80161_();
      quaternion.m_80148_(new Quaternion(0.0F, (float)Math.sin((double)(p_175219_ / 2.0F)), 0.0F, (float)Math.cos((double)(p_175219_ / 2.0F))));
      quaternion.m_80148_(new Quaternion((float)Math.sin((double)(p_175220_ / 2.0F)), 0.0F, 0.0F, (float)Math.cos((double)(p_175220_ / 2.0F))));
      quaternion.m_80148_(new Quaternion(0.0F, 0.0F, (float)Math.sin((double)(p_175221_ / 2.0F)), (float)Math.cos((double)(p_175221_ / 2.0F))));
      return quaternion;
   }

   public static Quaternion m_175225_(Vector3f p_175226_) {
      return m_175228_((float)Math.toRadians((double)p_175226_.m_122239_()), (float)Math.toRadians((double)p_175226_.m_122260_()), (float)Math.toRadians((double)p_175226_.m_122269_()));
   }

   public static Quaternion m_175232_(Vector3f p_175233_) {
      return m_175228_(p_175233_.m_122239_(), p_175233_.m_122260_(), p_175233_.m_122269_());
   }

   public static Quaternion m_175228_(float p_175229_, float p_175230_, float p_175231_) {
      Quaternion quaternion = f_80118_.m_80161_();
      quaternion.m_80148_(new Quaternion((float)Math.sin((double)(p_175229_ / 2.0F)), 0.0F, 0.0F, (float)Math.cos((double)(p_175229_ / 2.0F))));
      quaternion.m_80148_(new Quaternion(0.0F, (float)Math.sin((double)(p_175230_ / 2.0F)), 0.0F, (float)Math.cos((double)(p_175230_ / 2.0F))));
      quaternion.m_80148_(new Quaternion(0.0F, 0.0F, (float)Math.sin((double)(p_175231_ / 2.0F)), (float)Math.cos((double)(p_175231_ / 2.0F))));
      return quaternion;
   }

   public Vector3f m_175217_() {
      float f = this.m_80156_() * this.m_80156_();
      float f1 = this.m_80140_() * this.m_80140_();
      float f2 = this.m_80150_() * this.m_80150_();
      float f3 = this.m_80153_() * this.m_80153_();
      float f4 = f + f1 + f2 + f3;
      float f5 = 2.0F * this.m_80156_() * this.m_80140_() - 2.0F * this.m_80150_() * this.m_80153_();
      float f6 = (float)Math.asin((double)(f5 / f4));
      return Math.abs(f5) > 0.999F * f4 ? new Vector3f(2.0F * (float)Math.atan2((double)this.m_80140_(), (double)this.m_80156_()), f6, 0.0F) : new Vector3f((float)Math.atan2((double)(2.0F * this.m_80150_() * this.m_80153_() + 2.0F * this.m_80140_() * this.m_80156_()), (double)(f - f1 - f2 + f3)), f6, (float)Math.atan2((double)(2.0F * this.m_80140_() * this.m_80150_() + 2.0F * this.m_80156_() * this.m_80153_()), (double)(f + f1 - f2 - f3)));
   }

   public Vector3f m_175227_() {
      Vector3f vector3f = this.m_175217_();
      return new Vector3f((float)Math.toDegrees((double)vector3f.m_122239_()), (float)Math.toDegrees((double)vector3f.m_122260_()), (float)Math.toDegrees((double)vector3f.m_122269_()));
   }

   public Vector3f m_175234_() {
      float f = this.m_80156_() * this.m_80156_();
      float f1 = this.m_80140_() * this.m_80140_();
      float f2 = this.m_80150_() * this.m_80150_();
      float f3 = this.m_80153_() * this.m_80153_();
      float f4 = f + f1 + f2 + f3;
      float f5 = 2.0F * this.m_80156_() * this.m_80140_() - 2.0F * this.m_80150_() * this.m_80153_();
      float f6 = (float)Math.asin((double)(f5 / f4));
      return Math.abs(f5) > 0.999F * f4 ? new Vector3f(f6, 2.0F * (float)Math.atan2((double)this.m_80150_(), (double)this.m_80156_()), 0.0F) : new Vector3f(f6, (float)Math.atan2((double)(2.0F * this.m_80140_() * this.m_80153_() + 2.0F * this.m_80150_() * this.m_80156_()), (double)(f - f1 - f2 + f3)), (float)Math.atan2((double)(2.0F * this.m_80140_() * this.m_80150_() + 2.0F * this.m_80156_() * this.m_80153_()), (double)(f - f1 + f2 - f3)));
   }

   public Vector3f m_175235_() {
      Vector3f vector3f = this.m_175234_();
      return new Vector3f((float)Math.toDegrees((double)vector3f.m_122239_()), (float)Math.toDegrees((double)vector3f.m_122260_()), (float)Math.toDegrees((double)vector3f.m_122269_()));
   }

   public boolean equals(Object p_80159_) {
      if (this == p_80159_) {
         return true;
      } else if (p_80159_ != null && this.getClass() == p_80159_.getClass()) {
         Quaternion quaternion = (Quaternion)p_80159_;
         if (Float.compare(quaternion.f_80119_, this.f_80119_) != 0) {
            return false;
         } else if (Float.compare(quaternion.f_80120_, this.f_80120_) != 0) {
            return false;
         } else if (Float.compare(quaternion.f_80121_, this.f_80121_) != 0) {
            return false;
         } else {
            return Float.compare(quaternion.f_80122_, this.f_80122_) == 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = Float.floatToIntBits(this.f_80119_);
      i = 31 * i + Float.floatToIntBits(this.f_80120_);
      i = 31 * i + Float.floatToIntBits(this.f_80121_);
      return 31 * i + Float.floatToIntBits(this.f_80122_);
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("Quaternion[").append(this.m_80156_()).append(" + ");
      stringbuilder.append(this.m_80140_()).append("i + ");
      stringbuilder.append(this.m_80150_()).append("j + ");
      stringbuilder.append(this.m_80153_()).append("k]");
      return stringbuilder.toString();
   }

   public float m_80140_() {
      return this.f_80119_;
   }

   public float m_80150_() {
      return this.f_80120_;
   }

   public float m_80153_() {
      return this.f_80121_;
   }

   public float m_80156_() {
      return this.f_80122_;
   }

   public void m_80148_(Quaternion p_80149_) {
      float f = this.m_80140_();
      float f1 = this.m_80150_();
      float f2 = this.m_80153_();
      float f3 = this.m_80156_();
      float f4 = p_80149_.m_80140_();
      float f5 = p_80149_.m_80150_();
      float f6 = p_80149_.m_80153_();
      float f7 = p_80149_.m_80156_();
      this.f_80119_ = f3 * f4 + f * f7 + f1 * f6 - f2 * f5;
      this.f_80120_ = f3 * f5 - f * f6 + f1 * f7 + f2 * f4;
      this.f_80121_ = f3 * f6 + f * f5 - f1 * f4 + f2 * f7;
      this.f_80122_ = f3 * f7 - f * f4 - f1 * f5 - f2 * f6;
   }

   public void m_80141_(float p_80142_) {
      this.f_80119_ *= p_80142_;
      this.f_80120_ *= p_80142_;
      this.f_80121_ *= p_80142_;
      this.f_80122_ *= p_80142_;
   }

   public void m_80157_() {
      this.f_80119_ = -this.f_80119_;
      this.f_80120_ = -this.f_80120_;
      this.f_80121_ = -this.f_80121_;
   }

   public void m_80143_(float p_80144_, float p_80145_, float p_80146_, float p_80147_) {
      this.f_80119_ = p_80144_;
      this.f_80120_ = p_80145_;
      this.f_80121_ = p_80146_;
      this.f_80122_ = p_80147_;
   }

   private static float m_80151_(float p_80152_) {
      return (float)Math.cos((double)p_80152_);
   }

   private static float m_80154_(float p_80155_) {
      return (float)Math.sin((double)p_80155_);
   }

   public void m_80160_() {
      float f = this.m_80140_() * this.m_80140_() + this.m_80150_() * this.m_80150_() + this.m_80153_() * this.m_80153_() + this.m_80156_() * this.m_80156_();
      if (f > 1.0E-6F) {
         float f1 = Mth.m_14195_(f);
         this.f_80119_ *= f1;
         this.f_80120_ *= f1;
         this.f_80121_ *= f1;
         this.f_80122_ *= f1;
      } else {
         this.f_80119_ = 0.0F;
         this.f_80120_ = 0.0F;
         this.f_80121_ = 0.0F;
         this.f_80122_ = 0.0F;
      }

   }

   public void m_175222_(Quaternion p_175223_, float p_175224_) {
      throw new UnsupportedOperationException();
   }

   public Quaternion m_80161_() {
      return new Quaternion(this);
   }
}