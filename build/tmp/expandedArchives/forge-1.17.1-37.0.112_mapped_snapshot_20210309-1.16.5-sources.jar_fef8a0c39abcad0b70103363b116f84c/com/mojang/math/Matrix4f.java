package com.mojang.math;

import java.nio.FloatBuffer;

public final class Matrix4f {
   private static final int f_162197_ = 4;
   protected float f_27603_;
   protected float f_27604_;
   protected float f_27605_;
   protected float f_27606_;
   protected float f_27607_;
   protected float f_27608_;
   protected float f_27609_;
   protected float f_27610_;
   protected float f_27611_;
   protected float f_27612_;
   protected float f_27613_;
   protected float f_27614_;
   protected float f_27615_;
   protected float f_27616_;
   protected float f_27617_;
   protected float f_27618_;

   public Matrix4f() {
   }

   public Matrix4f(Matrix4f p_27621_) {
      this.f_27603_ = p_27621_.f_27603_;
      this.f_27604_ = p_27621_.f_27604_;
      this.f_27605_ = p_27621_.f_27605_;
      this.f_27606_ = p_27621_.f_27606_;
      this.f_27607_ = p_27621_.f_27607_;
      this.f_27608_ = p_27621_.f_27608_;
      this.f_27609_ = p_27621_.f_27609_;
      this.f_27610_ = p_27621_.f_27610_;
      this.f_27611_ = p_27621_.f_27611_;
      this.f_27612_ = p_27621_.f_27612_;
      this.f_27613_ = p_27621_.f_27613_;
      this.f_27614_ = p_27621_.f_27614_;
      this.f_27615_ = p_27621_.f_27615_;
      this.f_27616_ = p_27621_.f_27616_;
      this.f_27617_ = p_27621_.f_27617_;
      this.f_27618_ = p_27621_.f_27618_;
   }

   public Matrix4f(Quaternion p_27623_) {
      float f = p_27623_.m_80140_();
      float f1 = p_27623_.m_80150_();
      float f2 = p_27623_.m_80153_();
      float f3 = p_27623_.m_80156_();
      float f4 = 2.0F * f * f;
      float f5 = 2.0F * f1 * f1;
      float f6 = 2.0F * f2 * f2;
      this.f_27603_ = 1.0F - f5 - f6;
      this.f_27608_ = 1.0F - f6 - f4;
      this.f_27613_ = 1.0F - f4 - f5;
      this.f_27618_ = 1.0F;
      float f7 = f * f1;
      float f8 = f1 * f2;
      float f9 = f2 * f;
      float f10 = f * f3;
      float f11 = f1 * f3;
      float f12 = f2 * f3;
      this.f_27607_ = 2.0F * (f7 + f12);
      this.f_27604_ = 2.0F * (f7 - f12);
      this.f_27611_ = 2.0F * (f9 - f11);
      this.f_27605_ = 2.0F * (f9 + f11);
      this.f_27612_ = 2.0F * (f8 + f10);
      this.f_27609_ = 2.0F * (f8 - f10);
   }

   public boolean m_162198_() {
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.f_27615_ = 1.0F;
      matrix4f.f_27616_ = 1.0F;
      matrix4f.f_27617_ = 1.0F;
      matrix4f.f_27618_ = 0.0F;
      Matrix4f matrix4f1 = this.m_27658_();
      matrix4f1.m_27644_(matrix4f);
      return m_162217_(matrix4f1.f_27603_ / matrix4f1.f_27606_) && m_162217_(matrix4f1.f_27607_ / matrix4f1.f_27610_) && m_162217_(matrix4f1.f_27611_ / matrix4f1.f_27614_) && m_162217_(matrix4f1.f_27604_ / matrix4f1.f_27606_) && m_162217_(matrix4f1.f_27608_ / matrix4f1.f_27610_) && m_162217_(matrix4f1.f_27612_ / matrix4f1.f_27614_) && m_162217_(matrix4f1.f_27605_ / matrix4f1.f_27606_) && m_162217_(matrix4f1.f_27609_ / matrix4f1.f_27610_) && m_162217_(matrix4f1.f_27613_ / matrix4f1.f_27614_);
   }

   private static boolean m_162217_(float p_162218_) {
      return (double)Math.abs(p_162218_ - (float)Math.round(p_162218_)) <= 1.0E-5D;
   }

   public boolean equals(Object p_27661_) {
      if (this == p_27661_) {
         return true;
      } else if (p_27661_ != null && this.getClass() == p_27661_.getClass()) {
         Matrix4f matrix4f = (Matrix4f)p_27661_;
         return Float.compare(matrix4f.f_27603_, this.f_27603_) == 0 && Float.compare(matrix4f.f_27604_, this.f_27604_) == 0 && Float.compare(matrix4f.f_27605_, this.f_27605_) == 0 && Float.compare(matrix4f.f_27606_, this.f_27606_) == 0 && Float.compare(matrix4f.f_27607_, this.f_27607_) == 0 && Float.compare(matrix4f.f_27608_, this.f_27608_) == 0 && Float.compare(matrix4f.f_27609_, this.f_27609_) == 0 && Float.compare(matrix4f.f_27610_, this.f_27610_) == 0 && Float.compare(matrix4f.f_27611_, this.f_27611_) == 0 && Float.compare(matrix4f.f_27612_, this.f_27612_) == 0 && Float.compare(matrix4f.f_27613_, this.f_27613_) == 0 && Float.compare(matrix4f.f_27614_, this.f_27614_) == 0 && Float.compare(matrix4f.f_27615_, this.f_27615_) == 0 && Float.compare(matrix4f.f_27616_, this.f_27616_) == 0 && Float.compare(matrix4f.f_27617_, this.f_27617_) == 0 && Float.compare(matrix4f.f_27618_, this.f_27618_) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = this.f_27603_ != 0.0F ? Float.floatToIntBits(this.f_27603_) : 0;
      i = 31 * i + (this.f_27604_ != 0.0F ? Float.floatToIntBits(this.f_27604_) : 0);
      i = 31 * i + (this.f_27605_ != 0.0F ? Float.floatToIntBits(this.f_27605_) : 0);
      i = 31 * i + (this.f_27606_ != 0.0F ? Float.floatToIntBits(this.f_27606_) : 0);
      i = 31 * i + (this.f_27607_ != 0.0F ? Float.floatToIntBits(this.f_27607_) : 0);
      i = 31 * i + (this.f_27608_ != 0.0F ? Float.floatToIntBits(this.f_27608_) : 0);
      i = 31 * i + (this.f_27609_ != 0.0F ? Float.floatToIntBits(this.f_27609_) : 0);
      i = 31 * i + (this.f_27610_ != 0.0F ? Float.floatToIntBits(this.f_27610_) : 0);
      i = 31 * i + (this.f_27611_ != 0.0F ? Float.floatToIntBits(this.f_27611_) : 0);
      i = 31 * i + (this.f_27612_ != 0.0F ? Float.floatToIntBits(this.f_27612_) : 0);
      i = 31 * i + (this.f_27613_ != 0.0F ? Float.floatToIntBits(this.f_27613_) : 0);
      i = 31 * i + (this.f_27614_ != 0.0F ? Float.floatToIntBits(this.f_27614_) : 0);
      i = 31 * i + (this.f_27615_ != 0.0F ? Float.floatToIntBits(this.f_27615_) : 0);
      i = 31 * i + (this.f_27616_ != 0.0F ? Float.floatToIntBits(this.f_27616_) : 0);
      i = 31 * i + (this.f_27617_ != 0.0F ? Float.floatToIntBits(this.f_27617_) : 0);
      return 31 * i + (this.f_27618_ != 0.0F ? Float.floatToIntBits(this.f_27618_) : 0);
   }

   private static int m_27641_(int p_27642_, int p_27643_) {
      return p_27643_ * 4 + p_27642_;
   }

   public void m_162212_(FloatBuffer p_162213_) {
      this.f_27603_ = p_162213_.get(m_27641_(0, 0));
      this.f_27604_ = p_162213_.get(m_27641_(0, 1));
      this.f_27605_ = p_162213_.get(m_27641_(0, 2));
      this.f_27606_ = p_162213_.get(m_27641_(0, 3));
      this.f_27607_ = p_162213_.get(m_27641_(1, 0));
      this.f_27608_ = p_162213_.get(m_27641_(1, 1));
      this.f_27609_ = p_162213_.get(m_27641_(1, 2));
      this.f_27610_ = p_162213_.get(m_27641_(1, 3));
      this.f_27611_ = p_162213_.get(m_27641_(2, 0));
      this.f_27612_ = p_162213_.get(m_27641_(2, 1));
      this.f_27613_ = p_162213_.get(m_27641_(2, 2));
      this.f_27614_ = p_162213_.get(m_27641_(2, 3));
      this.f_27615_ = p_162213_.get(m_27641_(3, 0));
      this.f_27616_ = p_162213_.get(m_27641_(3, 1));
      this.f_27617_ = p_162213_.get(m_27641_(3, 2));
      this.f_27618_ = p_162213_.get(m_27641_(3, 3));
   }

   public void m_162219_(FloatBuffer p_162220_) {
      this.f_27603_ = p_162220_.get(m_27641_(0, 0));
      this.f_27604_ = p_162220_.get(m_27641_(1, 0));
      this.f_27605_ = p_162220_.get(m_27641_(2, 0));
      this.f_27606_ = p_162220_.get(m_27641_(3, 0));
      this.f_27607_ = p_162220_.get(m_27641_(0, 1));
      this.f_27608_ = p_162220_.get(m_27641_(1, 1));
      this.f_27609_ = p_162220_.get(m_27641_(2, 1));
      this.f_27610_ = p_162220_.get(m_27641_(3, 1));
      this.f_27611_ = p_162220_.get(m_27641_(0, 2));
      this.f_27612_ = p_162220_.get(m_27641_(1, 2));
      this.f_27613_ = p_162220_.get(m_27641_(2, 2));
      this.f_27614_ = p_162220_.get(m_27641_(3, 2));
      this.f_27615_ = p_162220_.get(m_27641_(0, 3));
      this.f_27616_ = p_162220_.get(m_27641_(1, 3));
      this.f_27617_ = p_162220_.get(m_27641_(2, 3));
      this.f_27618_ = p_162220_.get(m_27641_(3, 3));
   }

   public void m_162214_(FloatBuffer p_162215_, boolean p_162216_) {
      if (p_162216_) {
         this.m_162219_(p_162215_);
      } else {
         this.m_162212_(p_162215_);
      }

   }

   public void m_162210_(Matrix4f p_162211_) {
      this.f_27603_ = p_162211_.f_27603_;
      this.f_27604_ = p_162211_.f_27604_;
      this.f_27605_ = p_162211_.f_27605_;
      this.f_27606_ = p_162211_.f_27606_;
      this.f_27607_ = p_162211_.f_27607_;
      this.f_27608_ = p_162211_.f_27608_;
      this.f_27609_ = p_162211_.f_27609_;
      this.f_27610_ = p_162211_.f_27610_;
      this.f_27611_ = p_162211_.f_27611_;
      this.f_27612_ = p_162211_.f_27612_;
      this.f_27613_ = p_162211_.f_27613_;
      this.f_27614_ = p_162211_.f_27614_;
      this.f_27615_ = p_162211_.f_27615_;
      this.f_27616_ = p_162211_.f_27616_;
      this.f_27617_ = p_162211_.f_27617_;
      this.f_27618_ = p_162211_.f_27618_;
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("Matrix4f:\n");
      stringbuilder.append(this.f_27603_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27604_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27605_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27606_);
      stringbuilder.append("\n");
      stringbuilder.append(this.f_27607_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27608_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27609_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27610_);
      stringbuilder.append("\n");
      stringbuilder.append(this.f_27611_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27612_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27613_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27614_);
      stringbuilder.append("\n");
      stringbuilder.append(this.f_27615_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27616_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27617_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_27618_);
      stringbuilder.append("\n");
      return stringbuilder.toString();
   }

   public void m_27650_(FloatBuffer p_27651_) {
      p_27651_.put(m_27641_(0, 0), this.f_27603_);
      p_27651_.put(m_27641_(0, 1), this.f_27604_);
      p_27651_.put(m_27641_(0, 2), this.f_27605_);
      p_27651_.put(m_27641_(0, 3), this.f_27606_);
      p_27651_.put(m_27641_(1, 0), this.f_27607_);
      p_27651_.put(m_27641_(1, 1), this.f_27608_);
      p_27651_.put(m_27641_(1, 2), this.f_27609_);
      p_27651_.put(m_27641_(1, 3), this.f_27610_);
      p_27651_.put(m_27641_(2, 0), this.f_27611_);
      p_27651_.put(m_27641_(2, 1), this.f_27612_);
      p_27651_.put(m_27641_(2, 2), this.f_27613_);
      p_27651_.put(m_27641_(2, 3), this.f_27614_);
      p_27651_.put(m_27641_(3, 0), this.f_27615_);
      p_27651_.put(m_27641_(3, 1), this.f_27616_);
      p_27651_.put(m_27641_(3, 2), this.f_27617_);
      p_27651_.put(m_27641_(3, 3), this.f_27618_);
   }

   public void m_162229_(FloatBuffer p_162230_) {
      p_162230_.put(m_27641_(0, 0), this.f_27603_);
      p_162230_.put(m_27641_(1, 0), this.f_27604_);
      p_162230_.put(m_27641_(2, 0), this.f_27605_);
      p_162230_.put(m_27641_(3, 0), this.f_27606_);
      p_162230_.put(m_27641_(0, 1), this.f_27607_);
      p_162230_.put(m_27641_(1, 1), this.f_27608_);
      p_162230_.put(m_27641_(2, 1), this.f_27609_);
      p_162230_.put(m_27641_(3, 1), this.f_27610_);
      p_162230_.put(m_27641_(0, 2), this.f_27611_);
      p_162230_.put(m_27641_(1, 2), this.f_27612_);
      p_162230_.put(m_27641_(2, 2), this.f_27613_);
      p_162230_.put(m_27641_(3, 2), this.f_27614_);
      p_162230_.put(m_27641_(0, 3), this.f_27615_);
      p_162230_.put(m_27641_(1, 3), this.f_27616_);
      p_162230_.put(m_27641_(2, 3), this.f_27617_);
      p_162230_.put(m_27641_(3, 3), this.f_27618_);
   }

   public void m_162221_(FloatBuffer p_162222_, boolean p_162223_) {
      if (p_162223_) {
         this.m_162229_(p_162222_);
      } else {
         this.m_27650_(p_162222_);
      }

   }

   public void m_27624_() {
      this.f_27603_ = 1.0F;
      this.f_27604_ = 0.0F;
      this.f_27605_ = 0.0F;
      this.f_27606_ = 0.0F;
      this.f_27607_ = 0.0F;
      this.f_27608_ = 1.0F;
      this.f_27609_ = 0.0F;
      this.f_27610_ = 0.0F;
      this.f_27611_ = 0.0F;
      this.f_27612_ = 0.0F;
      this.f_27613_ = 1.0F;
      this.f_27614_ = 0.0F;
      this.f_27615_ = 0.0F;
      this.f_27616_ = 0.0F;
      this.f_27617_ = 0.0F;
      this.f_27618_ = 1.0F;
   }

   public float m_27652_() {
      float f = this.f_27603_ * this.f_27608_ - this.f_27604_ * this.f_27607_;
      float f1 = this.f_27603_ * this.f_27609_ - this.f_27605_ * this.f_27607_;
      float f2 = this.f_27603_ * this.f_27610_ - this.f_27606_ * this.f_27607_;
      float f3 = this.f_27604_ * this.f_27609_ - this.f_27605_ * this.f_27608_;
      float f4 = this.f_27604_ * this.f_27610_ - this.f_27606_ * this.f_27608_;
      float f5 = this.f_27605_ * this.f_27610_ - this.f_27606_ * this.f_27609_;
      float f6 = this.f_27611_ * this.f_27616_ - this.f_27612_ * this.f_27615_;
      float f7 = this.f_27611_ * this.f_27617_ - this.f_27613_ * this.f_27615_;
      float f8 = this.f_27611_ * this.f_27618_ - this.f_27614_ * this.f_27615_;
      float f9 = this.f_27612_ * this.f_27617_ - this.f_27613_ * this.f_27616_;
      float f10 = this.f_27612_ * this.f_27618_ - this.f_27614_ * this.f_27616_;
      float f11 = this.f_27613_ * this.f_27618_ - this.f_27614_ * this.f_27617_;
      float f12 = this.f_27608_ * f11 - this.f_27609_ * f10 + this.f_27610_ * f9;
      float f13 = -this.f_27607_ * f11 + this.f_27609_ * f8 - this.f_27610_ * f7;
      float f14 = this.f_27607_ * f10 - this.f_27608_ * f8 + this.f_27610_ * f6;
      float f15 = -this.f_27607_ * f9 + this.f_27608_ * f7 - this.f_27609_ * f6;
      float f16 = -this.f_27604_ * f11 + this.f_27605_ * f10 - this.f_27606_ * f9;
      float f17 = this.f_27603_ * f11 - this.f_27605_ * f8 + this.f_27606_ * f7;
      float f18 = -this.f_27603_ * f10 + this.f_27604_ * f8 - this.f_27606_ * f6;
      float f19 = this.f_27603_ * f9 - this.f_27604_ * f7 + this.f_27605_ * f6;
      float f20 = this.f_27616_ * f5 - this.f_27617_ * f4 + this.f_27618_ * f3;
      float f21 = -this.f_27615_ * f5 + this.f_27617_ * f2 - this.f_27618_ * f1;
      float f22 = this.f_27615_ * f4 - this.f_27616_ * f2 + this.f_27618_ * f;
      float f23 = -this.f_27615_ * f3 + this.f_27616_ * f1 - this.f_27617_ * f;
      float f24 = -this.f_27612_ * f5 + this.f_27613_ * f4 - this.f_27614_ * f3;
      float f25 = this.f_27611_ * f5 - this.f_27613_ * f2 + this.f_27614_ * f1;
      float f26 = -this.f_27611_ * f4 + this.f_27612_ * f2 - this.f_27614_ * f;
      float f27 = this.f_27611_ * f3 - this.f_27612_ * f1 + this.f_27613_ * f;
      this.f_27603_ = f12;
      this.f_27607_ = f13;
      this.f_27611_ = f14;
      this.f_27615_ = f15;
      this.f_27604_ = f16;
      this.f_27608_ = f17;
      this.f_27612_ = f18;
      this.f_27616_ = f19;
      this.f_27605_ = f20;
      this.f_27609_ = f21;
      this.f_27613_ = f22;
      this.f_27617_ = f23;
      this.f_27606_ = f24;
      this.f_27610_ = f25;
      this.f_27614_ = f26;
      this.f_27618_ = f27;
      return f * f11 - f1 * f10 + f2 * f9 + f3 * f8 - f4 * f7 + f5 * f6;
   }

   public float m_162226_() {
      float f = this.f_27603_ * this.f_27608_ - this.f_27604_ * this.f_27607_;
      float f1 = this.f_27603_ * this.f_27609_ - this.f_27605_ * this.f_27607_;
      float f2 = this.f_27603_ * this.f_27610_ - this.f_27606_ * this.f_27607_;
      float f3 = this.f_27604_ * this.f_27609_ - this.f_27605_ * this.f_27608_;
      float f4 = this.f_27604_ * this.f_27610_ - this.f_27606_ * this.f_27608_;
      float f5 = this.f_27605_ * this.f_27610_ - this.f_27606_ * this.f_27609_;
      float f6 = this.f_27611_ * this.f_27616_ - this.f_27612_ * this.f_27615_;
      float f7 = this.f_27611_ * this.f_27617_ - this.f_27613_ * this.f_27615_;
      float f8 = this.f_27611_ * this.f_27618_ - this.f_27614_ * this.f_27615_;
      float f9 = this.f_27612_ * this.f_27617_ - this.f_27613_ * this.f_27616_;
      float f10 = this.f_27612_ * this.f_27618_ - this.f_27614_ * this.f_27616_;
      float f11 = this.f_27613_ * this.f_27618_ - this.f_27614_ * this.f_27617_;
      return f * f11 - f1 * f10 + f2 * f9 + f3 * f8 - f4 * f7 + f5 * f6;
   }

   public void m_27659_() {
      float f = this.f_27607_;
      this.f_27607_ = this.f_27604_;
      this.f_27604_ = f;
      f = this.f_27611_;
      this.f_27611_ = this.f_27605_;
      this.f_27605_ = f;
      f = this.f_27612_;
      this.f_27612_ = this.f_27609_;
      this.f_27609_ = f;
      f = this.f_27615_;
      this.f_27615_ = this.f_27606_;
      this.f_27606_ = f;
      f = this.f_27616_;
      this.f_27616_ = this.f_27610_;
      this.f_27610_ = f;
      f = this.f_27617_;
      this.f_27617_ = this.f_27614_;
      this.f_27614_ = f;
   }

   public boolean m_27657_() {
      float f = this.m_27652_();
      if (Math.abs(f) > 1.0E-6F) {
         this.m_27630_(f);
         return true;
      } else {
         return false;
      }
   }

   public void m_27644_(Matrix4f p_27645_) {
      float f = this.f_27603_ * p_27645_.f_27603_ + this.f_27604_ * p_27645_.f_27607_ + this.f_27605_ * p_27645_.f_27611_ + this.f_27606_ * p_27645_.f_27615_;
      float f1 = this.f_27603_ * p_27645_.f_27604_ + this.f_27604_ * p_27645_.f_27608_ + this.f_27605_ * p_27645_.f_27612_ + this.f_27606_ * p_27645_.f_27616_;
      float f2 = this.f_27603_ * p_27645_.f_27605_ + this.f_27604_ * p_27645_.f_27609_ + this.f_27605_ * p_27645_.f_27613_ + this.f_27606_ * p_27645_.f_27617_;
      float f3 = this.f_27603_ * p_27645_.f_27606_ + this.f_27604_ * p_27645_.f_27610_ + this.f_27605_ * p_27645_.f_27614_ + this.f_27606_ * p_27645_.f_27618_;
      float f4 = this.f_27607_ * p_27645_.f_27603_ + this.f_27608_ * p_27645_.f_27607_ + this.f_27609_ * p_27645_.f_27611_ + this.f_27610_ * p_27645_.f_27615_;
      float f5 = this.f_27607_ * p_27645_.f_27604_ + this.f_27608_ * p_27645_.f_27608_ + this.f_27609_ * p_27645_.f_27612_ + this.f_27610_ * p_27645_.f_27616_;
      float f6 = this.f_27607_ * p_27645_.f_27605_ + this.f_27608_ * p_27645_.f_27609_ + this.f_27609_ * p_27645_.f_27613_ + this.f_27610_ * p_27645_.f_27617_;
      float f7 = this.f_27607_ * p_27645_.f_27606_ + this.f_27608_ * p_27645_.f_27610_ + this.f_27609_ * p_27645_.f_27614_ + this.f_27610_ * p_27645_.f_27618_;
      float f8 = this.f_27611_ * p_27645_.f_27603_ + this.f_27612_ * p_27645_.f_27607_ + this.f_27613_ * p_27645_.f_27611_ + this.f_27614_ * p_27645_.f_27615_;
      float f9 = this.f_27611_ * p_27645_.f_27604_ + this.f_27612_ * p_27645_.f_27608_ + this.f_27613_ * p_27645_.f_27612_ + this.f_27614_ * p_27645_.f_27616_;
      float f10 = this.f_27611_ * p_27645_.f_27605_ + this.f_27612_ * p_27645_.f_27609_ + this.f_27613_ * p_27645_.f_27613_ + this.f_27614_ * p_27645_.f_27617_;
      float f11 = this.f_27611_ * p_27645_.f_27606_ + this.f_27612_ * p_27645_.f_27610_ + this.f_27613_ * p_27645_.f_27614_ + this.f_27614_ * p_27645_.f_27618_;
      float f12 = this.f_27615_ * p_27645_.f_27603_ + this.f_27616_ * p_27645_.f_27607_ + this.f_27617_ * p_27645_.f_27611_ + this.f_27618_ * p_27645_.f_27615_;
      float f13 = this.f_27615_ * p_27645_.f_27604_ + this.f_27616_ * p_27645_.f_27608_ + this.f_27617_ * p_27645_.f_27612_ + this.f_27618_ * p_27645_.f_27616_;
      float f14 = this.f_27615_ * p_27645_.f_27605_ + this.f_27616_ * p_27645_.f_27609_ + this.f_27617_ * p_27645_.f_27613_ + this.f_27618_ * p_27645_.f_27617_;
      float f15 = this.f_27615_ * p_27645_.f_27606_ + this.f_27616_ * p_27645_.f_27610_ + this.f_27617_ * p_27645_.f_27614_ + this.f_27618_ * p_27645_.f_27618_;
      this.f_27603_ = f;
      this.f_27604_ = f1;
      this.f_27605_ = f2;
      this.f_27606_ = f3;
      this.f_27607_ = f4;
      this.f_27608_ = f5;
      this.f_27609_ = f6;
      this.f_27610_ = f7;
      this.f_27611_ = f8;
      this.f_27612_ = f9;
      this.f_27613_ = f10;
      this.f_27614_ = f11;
      this.f_27615_ = f12;
      this.f_27616_ = f13;
      this.f_27617_ = f14;
      this.f_27618_ = f15;
   }

   public void m_27646_(Quaternion p_27647_) {
      this.m_27644_(new Matrix4f(p_27647_));
   }

   public void m_27630_(float p_27631_) {
      this.f_27603_ *= p_27631_;
      this.f_27604_ *= p_27631_;
      this.f_27605_ *= p_27631_;
      this.f_27606_ *= p_27631_;
      this.f_27607_ *= p_27631_;
      this.f_27608_ *= p_27631_;
      this.f_27609_ *= p_27631_;
      this.f_27610_ *= p_27631_;
      this.f_27611_ *= p_27631_;
      this.f_27612_ *= p_27631_;
      this.f_27613_ *= p_27631_;
      this.f_27614_ *= p_27631_;
      this.f_27615_ *= p_27631_;
      this.f_27616_ *= p_27631_;
      this.f_27617_ *= p_27631_;
      this.f_27618_ *= p_27631_;
   }

   public void m_162224_(Matrix4f p_162225_) {
      this.f_27603_ += p_162225_.f_27603_;
      this.f_27604_ += p_162225_.f_27604_;
      this.f_27605_ += p_162225_.f_27605_;
      this.f_27606_ += p_162225_.f_27606_;
      this.f_27607_ += p_162225_.f_27607_;
      this.f_27608_ += p_162225_.f_27608_;
      this.f_27609_ += p_162225_.f_27609_;
      this.f_27610_ += p_162225_.f_27610_;
      this.f_27611_ += p_162225_.f_27611_;
      this.f_27612_ += p_162225_.f_27612_;
      this.f_27613_ += p_162225_.f_27613_;
      this.f_27614_ += p_162225_.f_27614_;
      this.f_27615_ += p_162225_.f_27615_;
      this.f_27616_ += p_162225_.f_27616_;
      this.f_27617_ += p_162225_.f_27617_;
      this.f_27618_ += p_162225_.f_27618_;
   }

   public void m_162227_(Matrix4f p_162228_) {
      this.f_27603_ -= p_162228_.f_27603_;
      this.f_27604_ -= p_162228_.f_27604_;
      this.f_27605_ -= p_162228_.f_27605_;
      this.f_27606_ -= p_162228_.f_27606_;
      this.f_27607_ -= p_162228_.f_27607_;
      this.f_27608_ -= p_162228_.f_27608_;
      this.f_27609_ -= p_162228_.f_27609_;
      this.f_27610_ -= p_162228_.f_27610_;
      this.f_27611_ -= p_162228_.f_27611_;
      this.f_27612_ -= p_162228_.f_27612_;
      this.f_27613_ -= p_162228_.f_27613_;
      this.f_27614_ -= p_162228_.f_27614_;
      this.f_27615_ -= p_162228_.f_27615_;
      this.f_27616_ -= p_162228_.f_27616_;
      this.f_27617_ -= p_162228_.f_27617_;
      this.f_27618_ -= p_162228_.f_27618_;
   }

   public float m_162231_() {
      return this.f_27603_ + this.f_27608_ + this.f_27613_ + this.f_27618_;
   }

   public static Matrix4f m_27625_(double p_27626_, float p_27627_, float p_27628_, float p_27629_) {
      float f = (float)(1.0D / Math.tan(p_27626_ * (double)((float)Math.PI / 180F) / 2.0D));
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.f_27603_ = f / p_27627_;
      matrix4f.f_27608_ = f;
      matrix4f.f_27613_ = (p_27629_ + p_27628_) / (p_27628_ - p_27629_);
      matrix4f.f_27617_ = -1.0F;
      matrix4f.f_27614_ = 2.0F * p_27629_ * p_27628_ / (p_27628_ - p_27629_);
      return matrix4f;
   }

   public static Matrix4f m_27636_(float p_27637_, float p_27638_, float p_27639_, float p_27640_) {
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.f_27603_ = 2.0F / p_27637_;
      matrix4f.f_27608_ = 2.0F / p_27638_;
      float f = p_27640_ - p_27639_;
      matrix4f.f_27613_ = -2.0F / f;
      matrix4f.f_27618_ = 1.0F;
      matrix4f.f_27606_ = -1.0F;
      matrix4f.f_27610_ = 1.0F;
      matrix4f.f_27614_ = -(p_27640_ + p_27639_) / f;
      return matrix4f;
   }

   public static Matrix4f m_162203_(float p_162204_, float p_162205_, float p_162206_, float p_162207_, float p_162208_, float p_162209_) {
      Matrix4f matrix4f = new Matrix4f();
      float f = p_162205_ - p_162204_;
      float f1 = p_162206_ - p_162207_;
      float f2 = p_162209_ - p_162208_;
      matrix4f.f_27603_ = 2.0F / f;
      matrix4f.f_27608_ = 2.0F / f1;
      matrix4f.f_27613_ = -2.0F / f2;
      matrix4f.f_27606_ = -(p_162205_ + p_162204_) / f;
      matrix4f.f_27610_ = -(p_162206_ + p_162207_) / f1;
      matrix4f.f_27614_ = -(p_162209_ + p_162208_) / f2;
      matrix4f.f_27618_ = 1.0F;
      return matrix4f;
   }

   public void m_27648_(Vector3f p_27649_) {
      this.f_27606_ += p_27649_.m_122239_();
      this.f_27610_ += p_27649_.m_122260_();
      this.f_27614_ += p_27649_.m_122269_();
   }

   public Matrix4f m_27658_() {
      return new Matrix4f(this);
   }

   public void m_162199_(float p_162200_, float p_162201_, float p_162202_) {
      this.f_27606_ += this.f_27603_ * p_162200_ + this.f_27604_ * p_162201_ + this.f_27605_ * p_162202_;
      this.f_27610_ += this.f_27607_ * p_162200_ + this.f_27608_ * p_162201_ + this.f_27609_ * p_162202_;
      this.f_27614_ += this.f_27611_ * p_162200_ + this.f_27612_ * p_162201_ + this.f_27613_ * p_162202_;
      this.f_27618_ += this.f_27615_ * p_162200_ + this.f_27616_ * p_162201_ + this.f_27617_ * p_162202_;
   }

   public static Matrix4f m_27632_(float p_27633_, float p_27634_, float p_27635_) {
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.f_27603_ = p_27633_;
      matrix4f.f_27608_ = p_27634_;
      matrix4f.f_27613_ = p_27635_;
      matrix4f.f_27618_ = 1.0F;
      return matrix4f;
   }

   public static Matrix4f m_27653_(float p_27654_, float p_27655_, float p_27656_) {
      Matrix4f matrix4f = new Matrix4f();
      matrix4f.f_27603_ = 1.0F;
      matrix4f.f_27608_ = 1.0F;
      matrix4f.f_27613_ = 1.0F;
      matrix4f.f_27618_ = 1.0F;
      matrix4f.f_27606_ = p_27654_;
      matrix4f.f_27610_ = p_27655_;
      matrix4f.f_27614_ = p_27656_;
      return matrix4f;
   }

    // Forge start
    public Matrix4f(float[] values) {
        f_27603_ = values[0];
        f_27604_ = values[1];
        f_27605_ = values[2];
        f_27606_ = values[3];
        f_27607_ = values[4];
        f_27608_ = values[5];
        f_27609_ = values[6];
        f_27610_ = values[7];
        f_27611_ = values[8];
        f_27612_ = values[9];
        f_27613_ = values[10];
        f_27614_ = values[11];
        f_27615_ = values[12];
        f_27616_ = values[13];
        f_27617_ = values[14];
        f_27618_ = values[15];
    }

    public void multiplyBackward(Matrix4f other) {
        Matrix4f copy = other.m_27658_();
        copy.m_27644_(this);
        this.m_162210_(copy);
    }

    public void setTranslation(float x, float y, float z) {
        this.f_27603_ = 1.0F;
        this.f_27608_ = 1.0F;
        this.f_27613_ = 1.0F;
        this.f_27618_ = 1.0F;
        this.f_27606_ = x;
        this.f_27610_ = y;
        this.f_27614_ = z;
    }
}
