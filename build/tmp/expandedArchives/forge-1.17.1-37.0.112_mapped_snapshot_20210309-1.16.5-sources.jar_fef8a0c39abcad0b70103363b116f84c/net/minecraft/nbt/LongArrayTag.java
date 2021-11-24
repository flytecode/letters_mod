package net.minecraft.nbt;

import it.unimi.dsi.fastutil.longs.LongSet;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class LongArrayTag extends CollectionTag<LongTag> {
   private static final int f_177993_ = 192;
   public static final TagType<LongArrayTag> f_128800_ = new TagType<LongArrayTag>() {
      public LongArrayTag m_7300_(DataInput p_128865_, int p_128866_, NbtAccounter p_128867_) throws IOException {
         p_128867_.m_6800_(192L);
         int i = p_128865_.readInt();
         p_128867_.m_6800_(64L * (long)i);
         long[] along = new long[i];

         for(int j = 0; j < i; ++j) {
            along[j] = p_128865_.readLong();
         }

         return new LongArrayTag(along);
      }

      public String m_5987_() {
         return "LONG[]";
      }

      public String m_5986_() {
         return "TAG_Long_Array";
      }
   };
   private long[] f_128801_;

   public LongArrayTag(long[] p_128808_) {
      this.f_128801_ = p_128808_;
   }

   public LongArrayTag(LongSet p_128804_) {
      this.f_128801_ = p_128804_.toLongArray();
   }

   public LongArrayTag(List<Long> p_128806_) {
      this(m_128823_(p_128806_));
   }

   private static long[] m_128823_(List<Long> p_128824_) {
      long[] along = new long[p_128824_.size()];

      for(int i = 0; i < p_128824_.size(); ++i) {
         Long olong = p_128824_.get(i);
         along[i] = olong == null ? 0L : olong;
      }

      return along;
   }

   public void m_6434_(DataOutput p_128819_) throws IOException {
      p_128819_.writeInt(this.f_128801_.length);

      for(long i : this.f_128801_) {
         p_128819_.writeLong(i);
      }

   }

   public byte m_7060_() {
      return 12;
   }

   public TagType<LongArrayTag> m_6458_() {
      return f_128800_;
   }

   public String toString() {
      return this.m_7916_();
   }

   public LongArrayTag m_6426_() {
      long[] along = new long[this.f_128801_.length];
      System.arraycopy(this.f_128801_, 0, along, 0, this.f_128801_.length);
      return new LongArrayTag(along);
   }

   public boolean equals(Object p_128850_) {
      if (this == p_128850_) {
         return true;
      } else {
         return p_128850_ instanceof LongArrayTag && Arrays.equals(this.f_128801_, ((LongArrayTag)p_128850_).f_128801_);
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.f_128801_);
   }

   public void m_142327_(TagVisitor p_177995_) {
      p_177995_.m_142309_(this);
   }

   public long[] m_128851_() {
      return this.f_128801_;
   }

   public int size() {
      return this.f_128801_.length;
   }

   public LongTag get(int p_128811_) {
      return LongTag.m_128882_(this.f_128801_[p_128811_]);
   }

   public LongTag set(int p_128813_, LongTag p_128814_) {
      long i = this.f_128801_[p_128813_];
      this.f_128801_[p_128813_] = p_128814_.m_7046_();
      return LongTag.m_128882_(i);
   }

   public void add(int p_128832_, LongTag p_128833_) {
      this.f_128801_ = ArrayUtils.add(this.f_128801_, p_128832_, p_128833_.m_7046_());
   }

   public boolean m_7615_(int p_128816_, Tag p_128817_) {
      if (p_128817_ instanceof NumericTag) {
         this.f_128801_[p_128816_] = ((NumericTag)p_128817_).m_7046_();
         return true;
      } else {
         return false;
      }
   }

   public boolean m_7614_(int p_128835_, Tag p_128836_) {
      if (p_128836_ instanceof NumericTag) {
         this.f_128801_ = ArrayUtils.add(this.f_128801_, p_128835_, ((NumericTag)p_128836_).m_7046_());
         return true;
      } else {
         return false;
      }
   }

   public LongTag remove(int p_128830_) {
      long i = this.f_128801_[p_128830_];
      this.f_128801_ = ArrayUtils.remove(this.f_128801_, p_128830_);
      return LongTag.m_128882_(i);
   }

   public byte m_7264_() {
      return 4;
   }

   public void clear() {
      this.f_128801_ = new long[0];
   }
}