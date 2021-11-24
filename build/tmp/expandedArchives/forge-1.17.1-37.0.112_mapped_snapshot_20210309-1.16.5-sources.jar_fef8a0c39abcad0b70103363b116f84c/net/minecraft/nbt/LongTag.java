package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class LongTag extends NumericTag {
   private static final int f_177996_ = 128;
   public static final TagType<LongTag> f_128873_ = new TagType<LongTag>() {
      public LongTag m_7300_(DataInput p_128906_, int p_128907_, NbtAccounter p_128908_) throws IOException {
         p_128908_.m_6800_(128L);
         return LongTag.m_128882_(p_128906_.readLong());
      }

      public String m_5987_() {
         return "LONG";
      }

      public String m_5986_() {
         return "TAG_Long";
      }

      public boolean m_7064_() {
         return true;
      }
   };
   private final long f_128874_;

   LongTag(long p_128877_) {
      this.f_128874_ = p_128877_;
   }

   public static LongTag m_128882_(long p_128883_) {
      return p_128883_ >= -128L && p_128883_ <= 1024L ? LongTag.Cache.f_128915_[(int)p_128883_ - -128] : new LongTag(p_128883_);
   }

   public void m_6434_(DataOutput p_128885_) throws IOException {
      p_128885_.writeLong(this.f_128874_);
   }

   public byte m_7060_() {
      return 4;
   }

   public TagType<LongTag> m_6458_() {
      return f_128873_;
   }

   public LongTag m_6426_() {
      return this;
   }

   public boolean equals(Object p_128894_) {
      if (this == p_128894_) {
         return true;
      } else {
         return p_128894_ instanceof LongTag && this.f_128874_ == ((LongTag)p_128894_).f_128874_;
      }
   }

   public int hashCode() {
      return (int)(this.f_128874_ ^ this.f_128874_ >>> 32);
   }

   public void m_142327_(TagVisitor p_177998_) {
      p_177998_.m_142046_(this);
   }

   public long m_7046_() {
      return this.f_128874_;
   }

   public int m_7047_() {
      return (int)(this.f_128874_ & -1L);
   }

   public short m_7053_() {
      return (short)((int)(this.f_128874_ & 65535L));
   }

   public byte m_7063_() {
      return (byte)((int)(this.f_128874_ & 255L));
   }

   public double m_7061_() {
      return (double)this.f_128874_;
   }

   public float m_7057_() {
      return (float)this.f_128874_;
   }

   public Number m_8103_() {
      return this.f_128874_;
   }

   static class Cache {
      private static final int f_177999_ = 1024;
      private static final int f_178000_ = -128;
      static final LongTag[] f_128915_ = new LongTag[1153];

      private Cache() {
      }

      static {
         for(int i = 0; i < f_128915_.length; ++i) {
            f_128915_[i] = new LongTag((long)(-128 + i));
         }

      }
   }
}