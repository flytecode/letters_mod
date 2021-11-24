package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ByteTag extends NumericTag {
   private static final int f_177840_ = 72;
   public static final TagType<ByteTag> f_128255_ = new TagType<ByteTag>() {
      public ByteTag m_7300_(DataInput p_128292_, int p_128293_, NbtAccounter p_128294_) throws IOException {
         p_128294_.m_6800_(72L);
         return ByteTag.m_128266_(p_128292_.readByte());
      }

      public String m_5987_() {
         return "BYTE";
      }

      public String m_5986_() {
         return "TAG_Byte";
      }

      public boolean m_7064_() {
         return true;
      }
   };
   public static final ByteTag f_128256_ = m_128266_((byte)0);
   public static final ByteTag f_128257_ = m_128266_((byte)1);
   private final byte f_128258_;

   ByteTag(byte p_128261_) {
      this.f_128258_ = p_128261_;
   }

   public static ByteTag m_128266_(byte p_128267_) {
      return ByteTag.Cache.f_128301_[128 + p_128267_];
   }

   public static ByteTag m_128273_(boolean p_128274_) {
      return p_128274_ ? f_128257_ : f_128256_;
   }

   public void m_6434_(DataOutput p_128269_) throws IOException {
      p_128269_.writeByte(this.f_128258_);
   }

   public byte m_7060_() {
      return 1;
   }

   public TagType<ByteTag> m_6458_() {
      return f_128255_;
   }

   public ByteTag m_6426_() {
      return this;
   }

   public boolean equals(Object p_128280_) {
      if (this == p_128280_) {
         return true;
      } else {
         return p_128280_ instanceof ByteTag && this.f_128258_ == ((ByteTag)p_128280_).f_128258_;
      }
   }

   public int hashCode() {
      return this.f_128258_;
   }

   public void m_142327_(TagVisitor p_177842_) {
      p_177842_.m_141946_(this);
   }

   public long m_7046_() {
      return (long)this.f_128258_;
   }

   public int m_7047_() {
      return this.f_128258_;
   }

   public short m_7053_() {
      return (short)this.f_128258_;
   }

   public byte m_7063_() {
      return this.f_128258_;
   }

   public double m_7061_() {
      return (double)this.f_128258_;
   }

   public float m_7057_() {
      return (float)this.f_128258_;
   }

   public Number m_8103_() {
      return this.f_128258_;
   }

   static class Cache {
      static final ByteTag[] f_128301_ = new ByteTag[256];

      private Cache() {
      }

      static {
         for(int i = 0; i < f_128301_.length; ++i) {
            f_128301_[i] = new ByteTag((byte)(i - 128));
         }

      }
   }
}