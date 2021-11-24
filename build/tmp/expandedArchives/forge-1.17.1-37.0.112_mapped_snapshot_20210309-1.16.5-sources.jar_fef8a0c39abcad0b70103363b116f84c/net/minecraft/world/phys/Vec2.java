package net.minecraft.world.phys;

import net.minecraft.util.Mth;

public class Vec2 {
   public static final Vec2 f_82462_ = new Vec2(0.0F, 0.0F);
   public static final Vec2 f_82463_ = new Vec2(1.0F, 1.0F);
   public static final Vec2 f_82464_ = new Vec2(1.0F, 0.0F);
   public static final Vec2 f_82465_ = new Vec2(-1.0F, 0.0F);
   public static final Vec2 f_82466_ = new Vec2(0.0F, 1.0F);
   public static final Vec2 f_82467_ = new Vec2(0.0F, -1.0F);
   public static final Vec2 f_82468_ = new Vec2(Float.MAX_VALUE, Float.MAX_VALUE);
   public static final Vec2 f_82469_ = new Vec2(Float.MIN_VALUE, Float.MIN_VALUE);
   public final float f_82470_;
   public final float f_82471_;

   public Vec2(float p_82474_, float p_82475_) {
      this.f_82470_ = p_82474_;
      this.f_82471_ = p_82475_;
   }

   public Vec2 m_165903_(float p_165904_) {
      return new Vec2(this.f_82470_ * p_165904_, this.f_82471_ * p_165904_);
   }

   public float m_165905_(Vec2 p_165906_) {
      return this.f_82470_ * p_165906_.f_82470_ + this.f_82471_ * p_165906_.f_82471_;
   }

   public Vec2 m_165910_(Vec2 p_165911_) {
      return new Vec2(this.f_82470_ + p_165911_.f_82470_, this.f_82471_ + p_165911_.f_82471_);
   }

   public Vec2 m_165908_(float p_165909_) {
      return new Vec2(this.f_82470_ + p_165909_, this.f_82471_ + p_165909_);
   }

   public boolean m_82476_(Vec2 p_82477_) {
      return this.f_82470_ == p_82477_.f_82470_ && this.f_82471_ == p_82477_.f_82471_;
   }

   public Vec2 m_165902_() {
      float f = Mth.m_14116_(this.f_82470_ * this.f_82470_ + this.f_82471_ * this.f_82471_);
      return f < 1.0E-4F ? f_82462_ : new Vec2(this.f_82470_ / f, this.f_82471_ / f);
   }

   public float m_165907_() {
      return Mth.m_14116_(this.f_82470_ * this.f_82470_ + this.f_82471_ * this.f_82471_);
   }

   public float m_165912_() {
      return this.f_82470_ * this.f_82470_ + this.f_82471_ * this.f_82471_;
   }

   public float m_165914_(Vec2 p_165915_) {
      float f = p_165915_.f_82470_ - this.f_82470_;
      float f1 = p_165915_.f_82471_ - this.f_82471_;
      return f * f + f1 * f1;
   }

   public Vec2 m_165913_() {
      return new Vec2(-this.f_82470_, -this.f_82471_);
   }
}