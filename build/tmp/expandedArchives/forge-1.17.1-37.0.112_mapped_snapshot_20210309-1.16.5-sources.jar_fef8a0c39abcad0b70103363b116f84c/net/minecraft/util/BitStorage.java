package net.minecraft.util;

import java.util.function.IntConsumer;
import javax.annotation.Nullable;
import net.minecraft.Util;
import org.apache.commons.lang3.Validate;

public class BitStorage {
   private static final int[] f_13496_ = new int[]{-1, -1, 0, Integer.MIN_VALUE, 0, 0, 1431655765, 1431655765, 0, Integer.MIN_VALUE, 0, 1, 858993459, 858993459, 0, 715827882, 715827882, 0, 613566756, 613566756, 0, Integer.MIN_VALUE, 0, 2, 477218588, 477218588, 0, 429496729, 429496729, 0, 390451572, 390451572, 0, 357913941, 357913941, 0, 330382099, 330382099, 0, 306783378, 306783378, 0, 286331153, 286331153, 0, Integer.MIN_VALUE, 0, 3, 252645135, 252645135, 0, 238609294, 238609294, 0, 226050910, 226050910, 0, 214748364, 214748364, 0, 204522252, 204522252, 0, 195225786, 195225786, 0, 186737708, 186737708, 0, 178956970, 178956970, 0, 171798691, 171798691, 0, 165191049, 165191049, 0, 159072862, 159072862, 0, 153391689, 153391689, 0, 148102320, 148102320, 0, 143165576, 143165576, 0, 138547332, 138547332, 0, Integer.MIN_VALUE, 0, 4, 130150524, 130150524, 0, 126322567, 126322567, 0, 122713351, 122713351, 0, 119304647, 119304647, 0, 116080197, 116080197, 0, 113025455, 113025455, 0, 110127366, 110127366, 0, 107374182, 107374182, 0, 104755299, 104755299, 0, 102261126, 102261126, 0, 99882960, 99882960, 0, 97612893, 97612893, 0, 95443717, 95443717, 0, 93368854, 93368854, 0, 91382282, 91382282, 0, 89478485, 89478485, 0, 87652393, 87652393, 0, 85899345, 85899345, 0, 84215045, 84215045, 0, 82595524, 82595524, 0, 81037118, 81037118, 0, 79536431, 79536431, 0, 78090314, 78090314, 0, 76695844, 76695844, 0, 75350303, 75350303, 0, 74051160, 74051160, 0, 72796055, 72796055, 0, 71582788, 71582788, 0, 70409299, 70409299, 0, 69273666, 69273666, 0, 68174084, 68174084, 0, Integer.MIN_VALUE, 0, 5};
   private final long[] f_13497_;
   private final int f_13498_;
   private final long f_13499_;
   private final int f_13500_;
   private final int f_13501_;
   private final int f_13502_;
   private final int f_13503_;
   private final int f_13504_;

   public BitStorage(int p_13507_, int p_13508_) {
      this(p_13507_, p_13508_, (long[])null);
   }

   public BitStorage(int p_13510_, int p_13511_, @Nullable long[] p_13512_) {
      Validate.inclusiveBetween(1L, 32L, (long)p_13510_);
      this.f_13500_ = p_13511_;
      this.f_13498_ = p_13510_;
      this.f_13499_ = (1L << p_13510_) - 1L;
      this.f_13501_ = (char)(64 / p_13510_);
      int i = 3 * (this.f_13501_ - 1);
      this.f_13502_ = f_13496_[i + 0];
      this.f_13503_ = f_13496_[i + 1];
      this.f_13504_ = f_13496_[i + 2];
      int j = (p_13511_ + this.f_13501_ - 1) / this.f_13501_;
      if (p_13512_ != null) {
         if (p_13512_.length != j) {
            throw (RuntimeException)Util.m_137570_(new RuntimeException("Invalid length given for storage, got: " + p_13512_.length + " but expected: " + j));
         }

         this.f_13497_ = p_13512_;
      } else {
         this.f_13497_ = new long[j];
      }

   }

   private int m_13522_(int p_13523_) {
      long i = Integer.toUnsignedLong(this.f_13502_);
      long j = Integer.toUnsignedLong(this.f_13503_);
      return (int)((long)p_13523_ * i + j >> 32 >> this.f_13504_);
   }

   public int m_13516_(int p_13517_, int p_13518_) {
      Validate.inclusiveBetween(0L, (long)(this.f_13500_ - 1), (long)p_13517_);
      Validate.inclusiveBetween(0L, this.f_13499_, (long)p_13518_);
      int i = this.m_13522_(p_13517_);
      long j = this.f_13497_[i];
      int k = (p_13517_ - i * this.f_13501_) * this.f_13498_;
      int l = (int)(j >> k & this.f_13499_);
      this.f_13497_[i] = j & ~(this.f_13499_ << k) | ((long)p_13518_ & this.f_13499_) << k;
      return l;
   }

   public void m_13524_(int p_13525_, int p_13526_) {
      Validate.inclusiveBetween(0L, (long)(this.f_13500_ - 1), (long)p_13525_);
      Validate.inclusiveBetween(0L, this.f_13499_, (long)p_13526_);
      int i = this.m_13522_(p_13525_);
      long j = this.f_13497_[i];
      int k = (p_13525_ - i * this.f_13501_) * this.f_13498_;
      this.f_13497_[i] = j & ~(this.f_13499_ << k) | ((long)p_13526_ & this.f_13499_) << k;
   }

   public int m_13514_(int p_13515_) {
      Validate.inclusiveBetween(0L, (long)(this.f_13500_ - 1), (long)p_13515_);
      int i = this.m_13522_(p_13515_);
      long j = this.f_13497_[i];
      int k = (p_13515_ - i * this.f_13501_) * this.f_13498_;
      return (int)(j >> k & this.f_13499_);
   }

   public long[] m_13513_() {
      return this.f_13497_;
   }

   public int m_13521_() {
      return this.f_13500_;
   }

   public int m_144604_() {
      return this.f_13498_;
   }

   public void m_13519_(IntConsumer p_13520_) {
      int i = 0;

      for(long j : this.f_13497_) {
         for(int k = 0; k < this.f_13501_; ++k) {
            p_13520_.accept((int)(j & this.f_13499_));
            j >>= this.f_13498_;
            ++i;
            if (i >= this.f_13500_) {
               return;
            }
         }
      }

   }
}