package net.minecraft.util.datafix;

import net.minecraft.util.Mth;
import org.apache.commons.lang3.Validate;

public class PackedBitStorage {
   private static final int f_145044_ = 6;
   private final long[] f_14550_;
   private final int f_14551_;
   private final long f_14552_;
   private final int f_14553_;

   public PackedBitStorage(int p_14555_, int p_14556_) {
      this(p_14555_, p_14556_, new long[Mth.m_144941_(p_14556_ * p_14555_, 64) / 64]);
   }

   public PackedBitStorage(int p_14558_, int p_14559_, long[] p_14560_) {
      Validate.inclusiveBetween(1L, 32L, (long)p_14558_);
      this.f_14553_ = p_14559_;
      this.f_14551_ = p_14558_;
      this.f_14550_ = p_14560_;
      this.f_14552_ = (1L << p_14558_) - 1L;
      int i = Mth.m_144941_(p_14559_ * p_14558_, 64) / 64;
      if (p_14560_.length != i) {
         throw new IllegalArgumentException("Invalid length given for storage, got: " + p_14560_.length + " but expected: " + i);
      }
   }

   public void m_14564_(int p_14565_, int p_14566_) {
      Validate.inclusiveBetween(0L, (long)(this.f_14553_ - 1), (long)p_14565_);
      Validate.inclusiveBetween(0L, this.f_14552_, (long)p_14566_);
      int i = p_14565_ * this.f_14551_;
      int j = i >> 6;
      int k = (p_14565_ + 1) * this.f_14551_ - 1 >> 6;
      int l = i ^ j << 6;
      this.f_14550_[j] = this.f_14550_[j] & ~(this.f_14552_ << l) | ((long)p_14566_ & this.f_14552_) << l;
      if (j != k) {
         int i1 = 64 - l;
         int j1 = this.f_14551_ - i1;
         this.f_14550_[k] = this.f_14550_[k] >>> j1 << j1 | ((long)p_14566_ & this.f_14552_) >> i1;
      }

   }

   public int m_14562_(int p_14563_) {
      Validate.inclusiveBetween(0L, (long)(this.f_14553_ - 1), (long)p_14563_);
      int i = p_14563_ * this.f_14551_;
      int j = i >> 6;
      int k = (p_14563_ + 1) * this.f_14551_ - 1 >> 6;
      int l = i ^ j << 6;
      if (j == k) {
         return (int)(this.f_14550_[j] >>> l & this.f_14552_);
      } else {
         int i1 = 64 - l;
         return (int)((this.f_14550_[j] >>> l | this.f_14550_[k] << i1) & this.f_14552_);
      }
   }

   public long[] m_14561_() {
      return this.f_14550_;
   }

   public int m_14567_() {
      return this.f_14551_;
   }
}