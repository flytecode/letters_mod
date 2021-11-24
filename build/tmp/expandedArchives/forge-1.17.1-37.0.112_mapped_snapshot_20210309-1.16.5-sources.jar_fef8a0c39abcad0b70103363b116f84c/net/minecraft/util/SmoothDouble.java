package net.minecraft.util;

public class SmoothDouble {
   private double f_14232_;
   private double f_14233_;
   private double f_14234_;

   public double m_14237_(double p_14238_, double p_14239_) {
      this.f_14232_ += p_14238_;
      double d0 = this.f_14232_ - this.f_14233_;
      double d1 = Mth.m_14139_(0.5D, this.f_14234_, d0);
      double d2 = Math.signum(d0);
      if (d2 * d0 > d2 * this.f_14234_) {
         d0 = d1;
      }

      this.f_14234_ = d1;
      this.f_14233_ += d0 * p_14239_;
      return d0 * p_14239_;
   }

   public void m_14236_() {
      this.f_14232_ = 0.0D;
      this.f_14233_ = 0.0D;
      this.f_14234_ = 0.0D;
   }
}