package com.mojang.math;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public final class Vector3f {
   public static final Codec<Vector3f> f_176762_ = Codec.FLOAT.listOf().comapFlatMap((p_176767_) -> {
      return Util.m_143795_(p_176767_, 3).map((p_176774_) -> {
         return new Vector3f(p_176774_.get(0), p_176774_.get(1), p_176774_.get(2));
      });
   }, (p_176776_) -> {
      return ImmutableList.of(p_176776_.f_122228_, p_176776_.f_122229_, p_176776_.f_122230_);
   });
   public static Vector3f f_122222_ = new Vector3f(-1.0F, 0.0F, 0.0F);
   public static Vector3f f_122223_ = new Vector3f(1.0F, 0.0F, 0.0F);
   public static Vector3f f_122224_ = new Vector3f(0.0F, -1.0F, 0.0F);
   public static Vector3f f_122225_ = new Vector3f(0.0F, 1.0F, 0.0F);
   public static Vector3f f_122226_ = new Vector3f(0.0F, 0.0F, -1.0F);
   public static Vector3f f_122227_ = new Vector3f(0.0F, 0.0F, 1.0F);
   public static Vector3f f_176763_ = new Vector3f(0.0F, 0.0F, 0.0F);
   private float f_122228_;
   private float f_122229_;
   private float f_122230_;

   public Vector3f() {
   }

   public Vector3f(float p_122234_, float p_122235_, float p_122236_) {
      this.f_122228_ = p_122234_;
      this.f_122229_ = p_122235_;
      this.f_122230_ = p_122236_;
   }

   public Vector3f(Vector4f p_176765_) {
      this(p_176765_.m_123601_(), p_176765_.m_123615_(), p_176765_.m_123616_());
   }

   public Vector3f(Vec3 p_122238_) {
      this((float)p_122238_.f_82479_, (float)p_122238_.f_82480_, (float)p_122238_.f_82481_);
   }

   public boolean equals(Object p_122283_) {
      if (this == p_122283_) {
         return true;
      } else if (p_122283_ != null && this.getClass() == p_122283_.getClass()) {
         Vector3f vector3f = (Vector3f)p_122283_;
         if (Float.compare(vector3f.f_122228_, this.f_122228_) != 0) {
            return false;
         } else if (Float.compare(vector3f.f_122229_, this.f_122229_) != 0) {
            return false;
         } else {
            return Float.compare(vector3f.f_122230_, this.f_122230_) == 0;
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int i = Float.floatToIntBits(this.f_122228_);
      i = 31 * i + Float.floatToIntBits(this.f_122229_);
      return 31 * i + Float.floatToIntBits(this.f_122230_);
   }

   public float m_122239_() {
      return this.f_122228_;
   }

   public float m_122260_() {
      return this.f_122229_;
   }

   public float m_122269_() {
      return this.f_122230_;
   }

   public void m_122261_(float p_122262_) {
      this.f_122228_ *= p_122262_;
      this.f_122229_ *= p_122262_;
      this.f_122230_ *= p_122262_;
   }

   public void m_122263_(float p_122264_, float p_122265_, float p_122266_) {
      this.f_122228_ *= p_122264_;
      this.f_122229_ *= p_122265_;
      this.f_122230_ *= p_122266_;
   }

   public void m_176770_(Vector3f p_176771_, Vector3f p_176772_) {
      this.f_122228_ = Mth.m_14036_(this.f_122228_, p_176771_.m_122239_(), p_176772_.m_122239_());
      this.f_122229_ = Mth.m_14036_(this.f_122229_, p_176771_.m_122239_(), p_176772_.m_122260_());
      this.f_122230_ = Mth.m_14036_(this.f_122230_, p_176771_.m_122269_(), p_176772_.m_122269_());
   }

   public void m_122242_(float p_122243_, float p_122244_) {
      this.f_122228_ = Mth.m_14036_(this.f_122228_, p_122243_, p_122244_);
      this.f_122229_ = Mth.m_14036_(this.f_122229_, p_122243_, p_122244_);
      this.f_122230_ = Mth.m_14036_(this.f_122230_, p_122243_, p_122244_);
   }

   public void m_122245_(float p_122246_, float p_122247_, float p_122248_) {
      this.f_122228_ = p_122246_;
      this.f_122229_ = p_122247_;
      this.f_122230_ = p_122248_;
   }

   public void m_176768_(Vector3f p_176769_) {
      this.f_122228_ = p_176769_.f_122228_;
      this.f_122229_ = p_176769_.f_122229_;
      this.f_122230_ = p_176769_.f_122230_;
   }

   public void m_122272_(float p_122273_, float p_122274_, float p_122275_) {
      this.f_122228_ += p_122273_;
      this.f_122229_ += p_122274_;
      this.f_122230_ += p_122275_;
   }

   public void m_122253_(Vector3f p_122254_) {
      this.f_122228_ += p_122254_.f_122228_;
      this.f_122229_ += p_122254_.f_122229_;
      this.f_122230_ += p_122254_.f_122230_;
   }

   public void m_122267_(Vector3f p_122268_) {
      this.f_122228_ -= p_122268_.f_122228_;
      this.f_122229_ -= p_122268_.f_122229_;
      this.f_122230_ -= p_122268_.f_122230_;
   }

   public float m_122276_(Vector3f p_122277_) {
      return this.f_122228_ * p_122277_.f_122228_ + this.f_122229_ * p_122277_.f_122229_ + this.f_122230_ * p_122277_.f_122230_;
   }

   public boolean m_122278_() {
      float f = this.f_122228_ * this.f_122228_ + this.f_122229_ * this.f_122229_ + this.f_122230_ * this.f_122230_;
      if ((double)f < 1.0E-5D) {
         return false;
      } else {
         float f1 = Mth.m_14195_(f);
         this.f_122228_ *= f1;
         this.f_122229_ *= f1;
         this.f_122230_ *= f1;
         return true;
      }
   }

   public void m_122279_(Vector3f p_122280_) {
      float f = this.f_122228_;
      float f1 = this.f_122229_;
      float f2 = this.f_122230_;
      float f3 = p_122280_.m_122239_();
      float f4 = p_122280_.m_122260_();
      float f5 = p_122280_.m_122269_();
      this.f_122228_ = f1 * f5 - f2 * f4;
      this.f_122229_ = f2 * f3 - f * f5;
      this.f_122230_ = f * f4 - f1 * f3;
   }

   public void m_122249_(Matrix3f p_122250_) {
      float f = this.f_122228_;
      float f1 = this.f_122229_;
      float f2 = this.f_122230_;
      this.f_122228_ = p_122250_.f_8134_ * f + p_122250_.f_8135_ * f1 + p_122250_.f_8136_ * f2;
      this.f_122229_ = p_122250_.f_8137_ * f + p_122250_.f_8138_ * f1 + p_122250_.f_8139_ * f2;
      this.f_122230_ = p_122250_.f_8140_ * f + p_122250_.f_8141_ * f1 + p_122250_.f_8142_ * f2;
   }

   public void m_122251_(Quaternion p_122252_) {
      Quaternion quaternion = new Quaternion(p_122252_);
      quaternion.m_80148_(new Quaternion(this.m_122239_(), this.m_122260_(), this.m_122269_(), 0.0F));
      Quaternion quaternion1 = new Quaternion(p_122252_);
      quaternion1.m_80157_();
      quaternion.m_80148_(quaternion1);
      this.m_122245_(quaternion.m_80140_(), quaternion.m_80150_(), quaternion.m_80153_());
   }

   public void m_122255_(Vector3f p_122256_, float p_122257_) {
      float f = 1.0F - p_122257_;
      this.f_122228_ = this.f_122228_ * f + p_122256_.f_122228_ * p_122257_;
      this.f_122229_ = this.f_122229_ * f + p_122256_.f_122229_ * p_122257_;
      this.f_122230_ = this.f_122230_ * f + p_122256_.f_122230_ * p_122257_;
   }

   public Quaternion m_122270_(float p_122271_) {
      return new Quaternion(this, p_122271_, false);
   }

   public Quaternion m_122240_(float p_122241_) {
      return new Quaternion(this, p_122241_, true);
   }

   public Vector3f m_122281_() {
      return new Vector3f(this.f_122228_, this.f_122229_, this.f_122230_);
   }

   public void m_122258_(Float2FloatFunction p_122259_) {
      this.f_122228_ = p_122259_.get(this.f_122228_);
      this.f_122229_ = p_122259_.get(this.f_122229_);
      this.f_122230_ = p_122259_.get(this.f_122230_);
   }

   public String toString() {
      return "[" + this.f_122228_ + ", " + this.f_122229_ + ", " + this.f_122230_ + "]";
   }

    public Vector3f(float[] values) {
        set(values);
    }
    public void set(float[] values) {
        this.f_122228_ = values[0];
        this.f_122229_ = values[1];
        this.f_122230_ = values[2];
    }
    public void setX(float x) { this.f_122228_ = x; }
    public void setY(float y) { this.f_122229_ = y; }
    public void setZ(float z) { this.f_122230_ = z; }
}
