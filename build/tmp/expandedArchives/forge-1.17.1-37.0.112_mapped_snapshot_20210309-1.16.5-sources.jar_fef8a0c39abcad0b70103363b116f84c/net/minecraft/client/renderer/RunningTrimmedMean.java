package net.minecraft.client.renderer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RunningTrimmedMean {
   private final long[] f_110707_;
   private int f_110708_;
   private int f_110709_;

   public RunningTrimmedMean(int p_110711_) {
      this.f_110707_ = new long[p_110711_];
   }

   public long m_110712_(long p_110713_) {
      if (this.f_110708_ < this.f_110707_.length) {
         ++this.f_110708_;
      }

      this.f_110707_[this.f_110709_] = p_110713_;
      this.f_110709_ = (this.f_110709_ + 1) % this.f_110707_.length;
      long i = Long.MAX_VALUE;
      long j = Long.MIN_VALUE;
      long k = 0L;

      for(int l = 0; l < this.f_110708_; ++l) {
         long i1 = this.f_110707_[l];
         k += i1;
         i = Math.min(i, i1);
         j = Math.max(j, i1);
      }

      if (this.f_110708_ > 2) {
         k = k - (i + j);
         return k / (long)(this.f_110708_ - 2);
      } else {
         return k > 0L ? (long)this.f_110708_ / k : 0L;
      }
   }
}