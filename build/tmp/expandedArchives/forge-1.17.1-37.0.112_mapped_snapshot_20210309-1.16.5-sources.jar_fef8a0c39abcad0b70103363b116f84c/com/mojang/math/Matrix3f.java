package com.mojang.math;

import com.mojang.datafixers.util.Pair;
import java.nio.FloatBuffer;
import net.minecraft.util.Mth;
import org.apache.commons.lang3.tuple.Triple;

public final class Matrix3f {
   private static final int f_152761_ = 3;
   private static final float f_8143_ = 3.0F + 2.0F * (float)Math.sqrt(2.0D);
   private static final float f_8144_ = (float)Math.cos((Math.PI / 8D));
   private static final float f_8145_ = (float)Math.sin((Math.PI / 8D));
   private static final float f_8146_ = 1.0F / (float)Math.sqrt(2.0D);
   protected float f_8134_;
   protected float f_8135_;
   protected float f_8136_;
   protected float f_8137_;
   protected float f_8138_;
   protected float f_8139_;
   protected float f_8140_;
   protected float f_8141_;
   protected float f_8142_;

   public Matrix3f() {
   }

   public Matrix3f(Quaternion p_8154_) {
      float f = p_8154_.m_80140_();
      float f1 = p_8154_.m_80150_();
      float f2 = p_8154_.m_80153_();
      float f3 = p_8154_.m_80156_();
      float f4 = 2.0F * f * f;
      float f5 = 2.0F * f1 * f1;
      float f6 = 2.0F * f2 * f2;
      this.f_8134_ = 1.0F - f5 - f6;
      this.f_8138_ = 1.0F - f6 - f4;
      this.f_8142_ = 1.0F - f4 - f5;
      float f7 = f * f1;
      float f8 = f1 * f2;
      float f9 = f2 * f;
      float f10 = f * f3;
      float f11 = f1 * f3;
      float f12 = f2 * f3;
      this.f_8137_ = 2.0F * (f7 + f12);
      this.f_8135_ = 2.0F * (f7 - f12);
      this.f_8140_ = 2.0F * (f9 - f11);
      this.f_8136_ = 2.0F * (f9 + f11);
      this.f_8141_ = 2.0F * (f8 + f10);
      this.f_8139_ = 2.0F * (f8 - f10);
   }

   public static Matrix3f m_8174_(float p_8175_, float p_8176_, float p_8177_) {
      Matrix3f matrix3f = new Matrix3f();
      matrix3f.f_8134_ = p_8175_;
      matrix3f.f_8138_ = p_8176_;
      matrix3f.f_8142_ = p_8177_;
      return matrix3f;
   }

   public Matrix3f(Matrix4f p_8152_) {
      this.f_8134_ = p_8152_.f_27603_;
      this.f_8135_ = p_8152_.f_27604_;
      this.f_8136_ = p_8152_.f_27605_;
      this.f_8137_ = p_8152_.f_27607_;
      this.f_8138_ = p_8152_.f_27608_;
      this.f_8139_ = p_8152_.f_27609_;
      this.f_8140_ = p_8152_.f_27611_;
      this.f_8141_ = p_8152_.f_27612_;
      this.f_8142_ = p_8152_.f_27613_;
   }

   public Matrix3f(Matrix3f p_8150_) {
      this.f_8134_ = p_8150_.f_8134_;
      this.f_8135_ = p_8150_.f_8135_;
      this.f_8136_ = p_8150_.f_8136_;
      this.f_8137_ = p_8150_.f_8137_;
      this.f_8138_ = p_8150_.f_8138_;
      this.f_8139_ = p_8150_.f_8139_;
      this.f_8140_ = p_8150_.f_8140_;
      this.f_8141_ = p_8150_.f_8141_;
      this.f_8142_ = p_8150_.f_8142_;
   }

   private static Pair<Float, Float> m_8161_(float p_8162_, float p_8163_, float p_8164_) {
      float f = 2.0F * (p_8162_ - p_8164_);
      if (f_8143_ * p_8163_ * p_8163_ < f * f) {
         float f1 = Mth.m_14195_(p_8163_ * p_8163_ + f * f);
         return Pair.of(f1 * p_8163_, f1 * f);
      } else {
         return Pair.of(f_8145_, f_8144_);
      }
   }

   private static Pair<Float, Float> m_8158_(float p_8159_, float p_8160_) {
      float f = (float)Math.hypot((double)p_8159_, (double)p_8160_);
      float f1 = f > 1.0E-6F ? p_8160_ : 0.0F;
      float f2 = Math.abs(p_8159_) + Math.max(f, 1.0E-6F);
      if (p_8159_ < 0.0F) {
         float f3 = f1;
         f1 = f2;
         f2 = f3;
      }

      float f4 = Mth.m_14195_(f2 * f2 + f1 * f1);
      f2 = f2 * f4;
      f1 = f1 * f4;
      return Pair.of(f1, f2);
   }

   private static Quaternion m_8181_(Matrix3f p_8182_) {
      Matrix3f matrix3f = new Matrix3f();
      Quaternion quaternion = Quaternion.f_80118_.m_80161_();
      if (p_8182_.f_8135_ * p_8182_.f_8135_ + p_8182_.f_8137_ * p_8182_.f_8137_ > 1.0E-6F) {
         Pair<Float, Float> pair = m_8161_(p_8182_.f_8134_, 0.5F * (p_8182_.f_8135_ + p_8182_.f_8137_), p_8182_.f_8138_);
         Float f = pair.getFirst();
         Float f1 = pair.getSecond();
         Quaternion quaternion1 = new Quaternion(0.0F, 0.0F, f, f1);
         float f2 = f1 * f1 - f * f;
         float f3 = -2.0F * f * f1;
         float f4 = f1 * f1 + f * f;
         quaternion.m_80148_(quaternion1);
         matrix3f.m_8180_();
         matrix3f.f_8134_ = f2;
         matrix3f.f_8138_ = f2;
         matrix3f.f_8137_ = -f3;
         matrix3f.f_8135_ = f3;
         matrix3f.f_8142_ = f4;
         p_8182_.m_8178_(matrix3f);
         matrix3f.m_8155_();
         matrix3f.m_8178_(p_8182_);
         p_8182_.m_8169_(matrix3f);
      }

      if (p_8182_.f_8136_ * p_8182_.f_8136_ + p_8182_.f_8140_ * p_8182_.f_8140_ > 1.0E-6F) {
         Pair<Float, Float> pair1 = m_8161_(p_8182_.f_8134_, 0.5F * (p_8182_.f_8136_ + p_8182_.f_8140_), p_8182_.f_8142_);
         float f5 = -pair1.getFirst();
         Float f7 = pair1.getSecond();
         Quaternion quaternion2 = new Quaternion(0.0F, f5, 0.0F, f7);
         float f9 = f7 * f7 - f5 * f5;
         float f11 = -2.0F * f5 * f7;
         float f13 = f7 * f7 + f5 * f5;
         quaternion.m_80148_(quaternion2);
         matrix3f.m_8180_();
         matrix3f.f_8134_ = f9;
         matrix3f.f_8142_ = f9;
         matrix3f.f_8140_ = f11;
         matrix3f.f_8136_ = -f11;
         matrix3f.f_8138_ = f13;
         p_8182_.m_8178_(matrix3f);
         matrix3f.m_8155_();
         matrix3f.m_8178_(p_8182_);
         p_8182_.m_8169_(matrix3f);
      }

      if (p_8182_.f_8139_ * p_8182_.f_8139_ + p_8182_.f_8141_ * p_8182_.f_8141_ > 1.0E-6F) {
         Pair<Float, Float> pair2 = m_8161_(p_8182_.f_8138_, 0.5F * (p_8182_.f_8139_ + p_8182_.f_8141_), p_8182_.f_8142_);
         Float f6 = pair2.getFirst();
         Float f8 = pair2.getSecond();
         Quaternion quaternion3 = new Quaternion(f6, 0.0F, 0.0F, f8);
         float f10 = f8 * f8 - f6 * f6;
         float f12 = -2.0F * f6 * f8;
         float f14 = f8 * f8 + f6 * f6;
         quaternion.m_80148_(quaternion3);
         matrix3f.m_8180_();
         matrix3f.f_8138_ = f10;
         matrix3f.f_8142_ = f10;
         matrix3f.f_8141_ = -f12;
         matrix3f.f_8139_ = f12;
         matrix3f.f_8134_ = f14;
         p_8182_.m_8178_(matrix3f);
         matrix3f.m_8155_();
         matrix3f.m_8178_(p_8182_);
         p_8182_.m_8169_(matrix3f);
      }

      return quaternion;
   }

   private static void m_152765_(Matrix3f p_152766_, Quaternion p_152767_) {
      float f1 = p_152766_.f_8134_ * p_152766_.f_8134_ + p_152766_.f_8137_ * p_152766_.f_8137_ + p_152766_.f_8140_ * p_152766_.f_8140_;
      float f2 = p_152766_.f_8135_ * p_152766_.f_8135_ + p_152766_.f_8138_ * p_152766_.f_8138_ + p_152766_.f_8141_ * p_152766_.f_8141_;
      float f3 = p_152766_.f_8136_ * p_152766_.f_8136_ + p_152766_.f_8139_ * p_152766_.f_8139_ + p_152766_.f_8142_ * p_152766_.f_8142_;
      if (f1 < f2) {
         float f = p_152766_.f_8137_;
         p_152766_.f_8137_ = -p_152766_.f_8134_;
         p_152766_.f_8134_ = f;
         f = p_152766_.f_8138_;
         p_152766_.f_8138_ = -p_152766_.f_8135_;
         p_152766_.f_8135_ = f;
         f = p_152766_.f_8139_;
         p_152766_.f_8139_ = -p_152766_.f_8136_;
         p_152766_.f_8136_ = f;
         Quaternion quaternion = new Quaternion(0.0F, 0.0F, f_8146_, f_8146_);
         p_152767_.m_80148_(quaternion);
         f = f1;
         f1 = f2;
         f2 = f;
      }

      if (f1 < f3) {
         float f4 = p_152766_.f_8140_;
         p_152766_.f_8140_ = -p_152766_.f_8134_;
         p_152766_.f_8134_ = f4;
         f4 = p_152766_.f_8141_;
         p_152766_.f_8141_ = -p_152766_.f_8135_;
         p_152766_.f_8135_ = f4;
         f4 = p_152766_.f_8142_;
         p_152766_.f_8142_ = -p_152766_.f_8136_;
         p_152766_.f_8136_ = f4;
         Quaternion quaternion1 = new Quaternion(0.0F, f_8146_, 0.0F, f_8146_);
         p_152767_.m_80148_(quaternion1);
         f3 = f1;
      }

      if (f2 < f3) {
         float f5 = p_152766_.f_8140_;
         p_152766_.f_8140_ = -p_152766_.f_8137_;
         p_152766_.f_8137_ = f5;
         f5 = p_152766_.f_8141_;
         p_152766_.f_8141_ = -p_152766_.f_8138_;
         p_152766_.f_8138_ = f5;
         f5 = p_152766_.f_8142_;
         p_152766_.f_8142_ = -p_152766_.f_8139_;
         p_152766_.f_8139_ = f5;
         Quaternion quaternion2 = new Quaternion(f_8146_, 0.0F, 0.0F, f_8146_);
         p_152767_.m_80148_(quaternion2);
      }

   }

   public void m_8155_() {
      float f = this.f_8135_;
      this.f_8135_ = this.f_8137_;
      this.f_8137_ = f;
      f = this.f_8136_;
      this.f_8136_ = this.f_8140_;
      this.f_8140_ = f;
      f = this.f_8139_;
      this.f_8139_ = this.f_8141_;
      this.f_8141_ = f;
   }

   public Triple<Quaternion, Vector3f, Quaternion> m_8173_() {
      Quaternion quaternion = Quaternion.f_80118_.m_80161_();
      Quaternion quaternion1 = Quaternion.f_80118_.m_80161_();
      Matrix3f matrix3f = this.m_8183_();
      matrix3f.m_8155_();
      matrix3f.m_8178_(this);

      for(int i = 0; i < 5; ++i) {
         quaternion1.m_80148_(m_8181_(matrix3f));
      }

      quaternion1.m_80160_();
      Matrix3f matrix3f4 = new Matrix3f(this);
      matrix3f4.m_8178_(new Matrix3f(quaternion1));
      float f = 1.0F;
      Pair<Float, Float> pair = m_8158_(matrix3f4.f_8134_, matrix3f4.f_8137_);
      Float f1 = pair.getFirst();
      Float f2 = pair.getSecond();
      float f3 = f2 * f2 - f1 * f1;
      float f4 = -2.0F * f1 * f2;
      float f5 = f2 * f2 + f1 * f1;
      Quaternion quaternion2 = new Quaternion(0.0F, 0.0F, f1, f2);
      quaternion.m_80148_(quaternion2);
      Matrix3f matrix3f1 = new Matrix3f();
      matrix3f1.m_8180_();
      matrix3f1.f_8134_ = f3;
      matrix3f1.f_8138_ = f3;
      matrix3f1.f_8137_ = f4;
      matrix3f1.f_8135_ = -f4;
      matrix3f1.f_8142_ = f5;
      f = f * f5;
      matrix3f1.m_8178_(matrix3f4);
      pair = m_8158_(matrix3f1.f_8134_, matrix3f1.f_8140_);
      float f6 = -pair.getFirst();
      Float f7 = pair.getSecond();
      float f8 = f7 * f7 - f6 * f6;
      float f9 = -2.0F * f6 * f7;
      float f10 = f7 * f7 + f6 * f6;
      Quaternion quaternion3 = new Quaternion(0.0F, f6, 0.0F, f7);
      quaternion.m_80148_(quaternion3);
      Matrix3f matrix3f2 = new Matrix3f();
      matrix3f2.m_8180_();
      matrix3f2.f_8134_ = f8;
      matrix3f2.f_8142_ = f8;
      matrix3f2.f_8140_ = -f9;
      matrix3f2.f_8136_ = f9;
      matrix3f2.f_8138_ = f10;
      f = f * f10;
      matrix3f2.m_8178_(matrix3f1);
      pair = m_8158_(matrix3f2.f_8138_, matrix3f2.f_8141_);
      Float f11 = pair.getFirst();
      Float f12 = pair.getSecond();
      float f13 = f12 * f12 - f11 * f11;
      float f14 = -2.0F * f11 * f12;
      float f15 = f12 * f12 + f11 * f11;
      Quaternion quaternion4 = new Quaternion(f11, 0.0F, 0.0F, f12);
      quaternion.m_80148_(quaternion4);
      Matrix3f matrix3f3 = new Matrix3f();
      matrix3f3.m_8180_();
      matrix3f3.f_8138_ = f13;
      matrix3f3.f_8142_ = f13;
      matrix3f3.f_8141_ = f14;
      matrix3f3.f_8139_ = -f14;
      matrix3f3.f_8134_ = f15;
      f = f * f15;
      matrix3f3.m_8178_(matrix3f2);
      f = 1.0F / f;
      quaternion.m_80141_((float)Math.sqrt((double)f));
      Vector3f vector3f = new Vector3f(matrix3f3.f_8134_ * f, matrix3f3.f_8138_ * f, matrix3f3.f_8142_ * f);
      return Triple.of(quaternion, vector3f, quaternion1);
   }

   public boolean equals(Object p_8186_) {
      if (this == p_8186_) {
         return true;
      } else if (p_8186_ != null && this.getClass() == p_8186_.getClass()) {
         Matrix3f matrix3f = (Matrix3f)p_8186_;
         return Float.compare(matrix3f.f_8134_, this.f_8134_) == 0 && Float.compare(matrix3f.f_8135_, this.f_8135_) == 0 && Float.compare(matrix3f.f_8136_, this.f_8136_) == 0 && Float.compare(matrix3f.f_8137_, this.f_8137_) == 0 && Float.compare(matrix3f.f_8138_, this.f_8138_) == 0 && Float.compare(matrix3f.f_8139_, this.f_8139_) == 0 && Float.compare(matrix3f.f_8140_, this.f_8140_) == 0 && Float.compare(matrix3f.f_8141_, this.f_8141_) == 0 && Float.compare(matrix3f.f_8142_, this.f_8142_) == 0;
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = this.f_8134_ != 0.0F ? Float.floatToIntBits(this.f_8134_) : 0;
      i = 31 * i + (this.f_8135_ != 0.0F ? Float.floatToIntBits(this.f_8135_) : 0);
      i = 31 * i + (this.f_8136_ != 0.0F ? Float.floatToIntBits(this.f_8136_) : 0);
      i = 31 * i + (this.f_8137_ != 0.0F ? Float.floatToIntBits(this.f_8137_) : 0);
      i = 31 * i + (this.f_8138_ != 0.0F ? Float.floatToIntBits(this.f_8138_) : 0);
      i = 31 * i + (this.f_8139_ != 0.0F ? Float.floatToIntBits(this.f_8139_) : 0);
      i = 31 * i + (this.f_8140_ != 0.0F ? Float.floatToIntBits(this.f_8140_) : 0);
      i = 31 * i + (this.f_8141_ != 0.0F ? Float.floatToIntBits(this.f_8141_) : 0);
      return 31 * i + (this.f_8142_ != 0.0F ? Float.floatToIntBits(this.f_8142_) : 0);
   }

   private static int m_152762_(int p_152763_, int p_152764_) {
      return p_152764_ * 3 + p_152763_;
   }

   public void m_152768_(FloatBuffer p_152769_) {
      this.f_8134_ = p_152769_.get(m_152762_(0, 0));
      this.f_8135_ = p_152769_.get(m_152762_(0, 1));
      this.f_8136_ = p_152769_.get(m_152762_(0, 2));
      this.f_8137_ = p_152769_.get(m_152762_(1, 0));
      this.f_8138_ = p_152769_.get(m_152762_(1, 1));
      this.f_8139_ = p_152769_.get(m_152762_(1, 2));
      this.f_8140_ = p_152769_.get(m_152762_(2, 0));
      this.f_8141_ = p_152769_.get(m_152762_(2, 1));
      this.f_8142_ = p_152769_.get(m_152762_(2, 2));
   }

   public void m_152773_(FloatBuffer p_152774_) {
      this.f_8134_ = p_152774_.get(m_152762_(0, 0));
      this.f_8135_ = p_152774_.get(m_152762_(1, 0));
      this.f_8136_ = p_152774_.get(m_152762_(2, 0));
      this.f_8137_ = p_152774_.get(m_152762_(0, 1));
      this.f_8138_ = p_152774_.get(m_152762_(1, 1));
      this.f_8139_ = p_152774_.get(m_152762_(2, 1));
      this.f_8140_ = p_152774_.get(m_152762_(0, 2));
      this.f_8141_ = p_152774_.get(m_152762_(1, 2));
      this.f_8142_ = p_152774_.get(m_152762_(2, 2));
   }

   public void m_152770_(FloatBuffer p_152771_, boolean p_152772_) {
      if (p_152772_) {
         this.m_152773_(p_152771_);
      } else {
         this.m_152768_(p_152771_);
      }

   }

   public void m_8169_(Matrix3f p_8170_) {
      this.f_8134_ = p_8170_.f_8134_;
      this.f_8135_ = p_8170_.f_8135_;
      this.f_8136_ = p_8170_.f_8136_;
      this.f_8137_ = p_8170_.f_8137_;
      this.f_8138_ = p_8170_.f_8138_;
      this.f_8139_ = p_8170_.f_8139_;
      this.f_8140_ = p_8170_.f_8140_;
      this.f_8141_ = p_8170_.f_8141_;
      this.f_8142_ = p_8170_.f_8142_;
   }

   public String toString() {
      StringBuilder stringbuilder = new StringBuilder();
      stringbuilder.append("Matrix3f:\n");
      stringbuilder.append(this.f_8134_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_8135_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_8136_);
      stringbuilder.append("\n");
      stringbuilder.append(this.f_8137_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_8138_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_8139_);
      stringbuilder.append("\n");
      stringbuilder.append(this.f_8140_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_8141_);
      stringbuilder.append(" ");
      stringbuilder.append(this.f_8142_);
      stringbuilder.append("\n");
      return stringbuilder.toString();
   }

   public void m_152780_(FloatBuffer p_152781_) {
      p_152781_.put(m_152762_(0, 0), this.f_8134_);
      p_152781_.put(m_152762_(0, 1), this.f_8135_);
      p_152781_.put(m_152762_(0, 2), this.f_8136_);
      p_152781_.put(m_152762_(1, 0), this.f_8137_);
      p_152781_.put(m_152762_(1, 1), this.f_8138_);
      p_152781_.put(m_152762_(1, 2), this.f_8139_);
      p_152781_.put(m_152762_(2, 0), this.f_8140_);
      p_152781_.put(m_152762_(2, 1), this.f_8141_);
      p_152781_.put(m_152762_(2, 2), this.f_8142_);
   }

   public void m_152784_(FloatBuffer p_152785_) {
      p_152785_.put(m_152762_(0, 0), this.f_8134_);
      p_152785_.put(m_152762_(1, 0), this.f_8135_);
      p_152785_.put(m_152762_(2, 0), this.f_8136_);
      p_152785_.put(m_152762_(0, 1), this.f_8137_);
      p_152785_.put(m_152762_(1, 1), this.f_8138_);
      p_152785_.put(m_152762_(2, 1), this.f_8139_);
      p_152785_.put(m_152762_(0, 2), this.f_8140_);
      p_152785_.put(m_152762_(1, 2), this.f_8141_);
      p_152785_.put(m_152762_(2, 2), this.f_8142_);
   }

   public void m_152775_(FloatBuffer p_152776_, boolean p_152777_) {
      if (p_152777_) {
         this.m_152784_(p_152776_);
      } else {
         this.m_152780_(p_152776_);
      }

   }

   public void m_8180_() {
      this.f_8134_ = 1.0F;
      this.f_8135_ = 0.0F;
      this.f_8136_ = 0.0F;
      this.f_8137_ = 0.0F;
      this.f_8138_ = 1.0F;
      this.f_8139_ = 0.0F;
      this.f_8140_ = 0.0F;
      this.f_8141_ = 0.0F;
      this.f_8142_ = 1.0F;
   }

   public float m_8184_() {
      float f = this.f_8138_ * this.f_8142_ - this.f_8139_ * this.f_8141_;
      float f1 = -(this.f_8137_ * this.f_8142_ - this.f_8139_ * this.f_8140_);
      float f2 = this.f_8137_ * this.f_8141_ - this.f_8138_ * this.f_8140_;
      float f3 = -(this.f_8135_ * this.f_8142_ - this.f_8136_ * this.f_8141_);
      float f4 = this.f_8134_ * this.f_8142_ - this.f_8136_ * this.f_8140_;
      float f5 = -(this.f_8134_ * this.f_8141_ - this.f_8135_ * this.f_8140_);
      float f6 = this.f_8135_ * this.f_8139_ - this.f_8136_ * this.f_8138_;
      float f7 = -(this.f_8134_ * this.f_8139_ - this.f_8136_ * this.f_8137_);
      float f8 = this.f_8134_ * this.f_8138_ - this.f_8135_ * this.f_8137_;
      float f9 = this.f_8134_ * f + this.f_8135_ * f1 + this.f_8136_ * f2;
      this.f_8134_ = f;
      this.f_8137_ = f1;
      this.f_8140_ = f2;
      this.f_8135_ = f3;
      this.f_8138_ = f4;
      this.f_8141_ = f5;
      this.f_8136_ = f6;
      this.f_8139_ = f7;
      this.f_8142_ = f8;
      return f9;
   }

   public float m_152786_() {
      float f = this.f_8138_ * this.f_8142_ - this.f_8139_ * this.f_8141_;
      float f1 = -(this.f_8137_ * this.f_8142_ - this.f_8139_ * this.f_8140_);
      float f2 = this.f_8137_ * this.f_8141_ - this.f_8138_ * this.f_8140_;
      return this.f_8134_ * f + this.f_8135_ * f1 + this.f_8136_ * f2;
   }

   public boolean m_8187_() {
      float f = this.m_8184_();
      if (Math.abs(f) > 1.0E-6F) {
         this.m_8156_(f);
         return true;
      } else {
         return false;
      }
   }

   public void m_8165_(int p_8166_, int p_8167_, float p_8168_) {
      if (p_8166_ == 0) {
         if (p_8167_ == 0) {
            this.f_8134_ = p_8168_;
         } else if (p_8167_ == 1) {
            this.f_8135_ = p_8168_;
         } else {
            this.f_8136_ = p_8168_;
         }
      } else if (p_8166_ == 1) {
         if (p_8167_ == 0) {
            this.f_8137_ = p_8168_;
         } else if (p_8167_ == 1) {
            this.f_8138_ = p_8168_;
         } else {
            this.f_8139_ = p_8168_;
         }
      } else if (p_8167_ == 0) {
         this.f_8140_ = p_8168_;
      } else if (p_8167_ == 1) {
         this.f_8141_ = p_8168_;
      } else {
         this.f_8142_ = p_8168_;
      }

   }

   public void m_8178_(Matrix3f p_8179_) {
      float f = this.f_8134_ * p_8179_.f_8134_ + this.f_8135_ * p_8179_.f_8137_ + this.f_8136_ * p_8179_.f_8140_;
      float f1 = this.f_8134_ * p_8179_.f_8135_ + this.f_8135_ * p_8179_.f_8138_ + this.f_8136_ * p_8179_.f_8141_;
      float f2 = this.f_8134_ * p_8179_.f_8136_ + this.f_8135_ * p_8179_.f_8139_ + this.f_8136_ * p_8179_.f_8142_;
      float f3 = this.f_8137_ * p_8179_.f_8134_ + this.f_8138_ * p_8179_.f_8137_ + this.f_8139_ * p_8179_.f_8140_;
      float f4 = this.f_8137_ * p_8179_.f_8135_ + this.f_8138_ * p_8179_.f_8138_ + this.f_8139_ * p_8179_.f_8141_;
      float f5 = this.f_8137_ * p_8179_.f_8136_ + this.f_8138_ * p_8179_.f_8139_ + this.f_8139_ * p_8179_.f_8142_;
      float f6 = this.f_8140_ * p_8179_.f_8134_ + this.f_8141_ * p_8179_.f_8137_ + this.f_8142_ * p_8179_.f_8140_;
      float f7 = this.f_8140_ * p_8179_.f_8135_ + this.f_8141_ * p_8179_.f_8138_ + this.f_8142_ * p_8179_.f_8141_;
      float f8 = this.f_8140_ * p_8179_.f_8136_ + this.f_8141_ * p_8179_.f_8139_ + this.f_8142_ * p_8179_.f_8142_;
      this.f_8134_ = f;
      this.f_8135_ = f1;
      this.f_8136_ = f2;
      this.f_8137_ = f3;
      this.f_8138_ = f4;
      this.f_8139_ = f5;
      this.f_8140_ = f6;
      this.f_8141_ = f7;
      this.f_8142_ = f8;
   }

   public void m_8171_(Quaternion p_8172_) {
      this.m_8178_(new Matrix3f(p_8172_));
   }

   public void m_8156_(float p_8157_) {
      this.f_8134_ *= p_8157_;
      this.f_8135_ *= p_8157_;
      this.f_8136_ *= p_8157_;
      this.f_8137_ *= p_8157_;
      this.f_8138_ *= p_8157_;
      this.f_8139_ *= p_8157_;
      this.f_8140_ *= p_8157_;
      this.f_8141_ *= p_8157_;
      this.f_8142_ *= p_8157_;
   }

   public void m_152778_(Matrix3f p_152779_) {
      this.f_8134_ += p_152779_.f_8134_;
      this.f_8135_ += p_152779_.f_8135_;
      this.f_8136_ += p_152779_.f_8136_;
      this.f_8137_ += p_152779_.f_8137_;
      this.f_8138_ += p_152779_.f_8138_;
      this.f_8139_ += p_152779_.f_8139_;
      this.f_8140_ += p_152779_.f_8140_;
      this.f_8141_ += p_152779_.f_8141_;
      this.f_8142_ += p_152779_.f_8142_;
   }

   public void m_152782_(Matrix3f p_152783_) {
      this.f_8134_ -= p_152783_.f_8134_;
      this.f_8135_ -= p_152783_.f_8135_;
      this.f_8136_ -= p_152783_.f_8136_;
      this.f_8137_ -= p_152783_.f_8137_;
      this.f_8138_ -= p_152783_.f_8138_;
      this.f_8139_ -= p_152783_.f_8139_;
      this.f_8140_ -= p_152783_.f_8140_;
      this.f_8141_ -= p_152783_.f_8141_;
      this.f_8142_ -= p_152783_.f_8142_;
   }

   public float m_152787_() {
      return this.f_8134_ + this.f_8138_ + this.f_8142_;
   }

   public Matrix3f m_8183_() {
      return new Matrix3f(this);
   }

    public void multiplyBackward(Matrix3f other) {
        Matrix3f copy = other.m_8183_();
        copy.m_8178_(this);
        this.m_8169_(copy);
    }
}
