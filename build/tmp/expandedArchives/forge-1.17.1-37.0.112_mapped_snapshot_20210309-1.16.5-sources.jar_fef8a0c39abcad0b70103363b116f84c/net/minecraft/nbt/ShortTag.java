package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class ShortTag extends NumericTag {
   private static final int f_178082_ = 80;
   public static final TagType<ShortTag> f_129244_ = new TagType<ShortTag>() {
      public ShortTag m_7300_(DataInput p_129277_, int p_129278_, NbtAccounter p_129279_) throws IOException {
         p_129279_.m_6800_(80L);
         return ShortTag.m_129258_(p_129277_.readShort());
      }

      public String m_5987_() {
         return "SHORT";
      }

      public String m_5986_() {
         return "TAG_Short";
      }

      public boolean m_7064_() {
         return true;
      }
   };
   private final short f_129245_;

   ShortTag(short p_129248_) {
      this.f_129245_ = p_129248_;
   }

   public static ShortTag m_129258_(short p_129259_) {
      return p_129259_ >= -128 && p_129259_ <= 1024 ? ShortTag.Cache.f_129286_[p_129259_ - -128] : new ShortTag(p_129259_);
   }

   public void m_6434_(DataOutput p_129254_) throws IOException {
      p_129254_.writeShort(this.f_129245_);
   }

   public byte m_7060_() {
      return 2;
   }

   public TagType<ShortTag> m_6458_() {
      return f_129244_;
   }

   public ShortTag m_6426_() {
      return this;
   }

   public boolean equals(Object p_129265_) {
      if (this == p_129265_) {
         return true;
      } else {
         return p_129265_ instanceof ShortTag && this.f_129245_ == ((ShortTag)p_129265_).f_129245_;
      }
   }

   public int hashCode() {
      return this.f_129245_;
   }

   public void m_142327_(TagVisitor p_178084_) {
      p_178084_.m_142183_(this);
   }

   public long m_7046_() {
      return (long)this.f_129245_;
   }

   public int m_7047_() {
      return this.f_129245_;
   }

   public short m_7053_() {
      return this.f_129245_;
   }

   public byte m_7063_() {
      return (byte)(this.f_129245_ & 255);
   }

   public double m_7061_() {
      return (double)this.f_129245_;
   }

   public float m_7057_() {
      return (float)this.f_129245_;
   }

   public Number m_8103_() {
      return this.f_129245_;
   }

   static class Cache {
      private static final int f_178085_ = 1024;
      private static final int f_178086_ = -128;
      static final ShortTag[] f_129286_ = new ShortTag[1153];

      private Cache() {
      }

      static {
         for(int i = 0; i < f_129286_.length; ++i) {
            f_129286_[i] = new ShortTag((short)(-128 + i));
         }

      }
   }
}