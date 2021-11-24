package com.mojang.math;

import net.minecraft.util.Mth;

public class Vector4f {
   private float f_123589_;
   private float f_123590_;
   private float f_123591_;
   private float f_123592_;

   public Vector4f() {
   }

   public Vector4f(float p_123595_, float p_123596_, float p_123597_, float p_123598_) {
      this.f_123589_ = p_123595_;
      this.f_123590_ = p_123596_;
      this.f_123591_ = p_123597_;
      this.f_123592_ = p_123598_;
   }

   public Vector4f(Vector3f p_123600_) {
      this(p_123600_.m_122239_(), p_123600_.m_122260_(), p_123600_.m_122269_(), 1.0F);
   }

   public boolean equals(Object p_123620_) {
      if (this == p_123620_) {
         return true;
      } else if (p_123620_ != null && this.getClass() == p_123620_.getClass()) {
         Vector4f vector4f = (Vector4f)p_123620_;
         if (Float.compare(vector4f.f_123589_, this.f_123589_) != 0) {
            return false;
         } else if (Float.compare(vector4f.f_123590_, this.f_123590_) != 0) {
            return false;
         } else if (Float.compare(vector4f.f_123591_, this.f_123591_) != 0) {
            return false;
         } else {
            return Float.compare(vector4f.f_123592_, this.f_123592_) == 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = Float.floatToIntBits(this.f_123589_);
      i = 31 * i + Float.floatToIntBits(this.f_123590_);
      i = 31 * i + Float.floatToIntBits(this.f_123591_);
      return 31 * i + Float.floatToIntBits(this.f_123592_);
   }

   public float m_123601_() {
      return this.f_123589_;
   }

   public float m_123615_() {
      return this.f_123590_;
   }

   public float m_123616_() {
      return this.f_123591_;
   }

   public float m_123617_() {
      return this.f_123592_;
   }

   public void m_176870_(float p_176871_) {
      this.f_123589_ *= p_176871_;
      this.f_123590_ *= p_176871_;
      this.f_123591_ *= p_176871_;
      this.f_123592_ *= p_176871_;
   }

   public void m_123611_(Vector3f p_123612_) {
      this.f_123589_ *= p_123612_.m_122239_();
      this.f_123590_ *= p_123612_.m_122260_();
      this.f_123591_ *= p_123612_.m_122269_();
   }

   public void m_123602_(float p_123603_, float p_123604_, float p_123605_, float p_123606_) {
      this.f_123589_ = p_123603_;
      this.f_123590_ = p_123604_;
      this.f_123591_ = p_123605_;
      this.f_123592_ = p_123606_;
   }

   public void m_176875_(float p_176876_, float p_176877_, float p_176878_, float p_176879_) {
      this.f_123589_ += p_176876_;
      this.f_123590_ += p_176877_;
      this.f_123591_ += p_176878_;
      this.f_123592_ += p_176879_;
   }

   public float m_123613_(Vector4f p_123614_) {
      return this.f_123589_ * p_123614_.f_123589_ + this.f_123590_ * p_123614_.f_123590_ + this.f_123591_ * p_123614_.f_123591_ + this.f_123592_ * p_123614_.f_123592_;
   }

   public boolean m_123618_() {
      float f = this.f_123589_ * this.f_123589_ + this.f_123590_ * this.f_123590_ + this.f_123591_ * this.f_123591_ + this.f_123592_ * this.f_123592_;
      if ((double)f < 1.0E-5D) {
         return false;
      } else {
         float f1 = Mth.m_14195_(f);
         this.f_123589_ *= f1;
         this.f_123590_ *= f1;
         this.f_123591_ *= f1;
         this.f_123592_ *= f1;
         return true;
      }
   }

   public void m_123607_(Matrix4f p_123608_) {
      float f = this.f_123589_;
      float f1 = this.f_123590_;
      float f2 = this.f_123591_;
      float f3 = this.f_123592_;
      this.f_123589_ = p_123608_.f_27603_ * f + p_123608_.f_27604_ * f1 + p_123608_.f_27605_ * f2 + p_123608_.f_27606_ * f3;
      this.f_123590_ = p_123608_.f_27607_ * f + p_123608_.f_27608_ * f1 + p_123608_.f_27609_ * f2 + p_123608_.f_27610_ * f3;
      this.f_123591_ = p_123608_.f_27611_ * f + p_123608_.f_27612_ * f1 + p_123608_.f_27613_ * f2 + p_123608_.f_27614_ * f3;
      this.f_123592_ = p_123608_.f_27615_ * f + p_123608_.f_27616_ * f1 + p_123608_.f_27617_ * f2 + p_123608_.f_27618_ * f3;
   }

   public void m_123609_(Quaternion p_123610_) {
      Quaternion quaternion = new Quaternion(p_123610_);
      quaternion.m_80148_(new Quaternion(this.m_123601_(), this.m_123615_(), this.m_123616_(), 0.0F));
      Quaternion quaternion1 = new Quaternion(p_123610_);
      quaternion1.m_80157_();
      quaternion.m_80148_(quaternion1);
      this.m_123602_(quaternion.m_80140_(), quaternion.m_80150_(), quaternion.m_80153_(), this.m_123617_());
   }

   public void m_123621_() {
      this.f_123589_ /= this.f_123592_;
      this.f_123590_ /= this.f_123592_;
      this.f_123591_ /= this.f_123592_;
      this.f_123592_ = 1.0F;
   }

   public void m_176872_(Vector4f p_176873_, float p_176874_) {
      float f = 1.0F - p_176874_;
      this.f_123589_ = this.f_123589_ * f + p_176873_.f_123589_ * p_176874_;
      this.f_123590_ = this.f_123590_ * f + p_176873_.f_123590_ * p_176874_;
      this.f_123591_ = this.f_123591_ * f + p_176873_.f_123591_ * p_176874_;
      this.f_123592_ = this.f_123592_ * f + p_176873_.f_123592_ * p_176874_;
   }

   public String toString() {
      return "[" + this.f_123589_ + ", " + this.f_123590_ + ", " + this.f_123591_ + ", " + this.f_123592_ + "]";
   }

    public void set(float[] values) {
        this.f_123589_ = values[0];
        this.f_123590_ = values[1];
        this.f_123591_ = values[2];
        this.f_123592_ = values[3];
    }
    public void setX(float x) { this.f_123589_ = x; }
    public void setY(float y) { this.f_123590_ = y; }
    public void setZ(float z) { this.f_123591_ = z; }
    public void setW(float w) { this.f_123592_ = w; }
}
