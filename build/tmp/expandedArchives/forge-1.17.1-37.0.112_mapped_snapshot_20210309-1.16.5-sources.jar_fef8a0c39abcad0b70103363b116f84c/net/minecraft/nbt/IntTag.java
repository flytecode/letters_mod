package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class IntTag extends NumericTag {
   private static final int f_177982_ = 96;
   public static final TagType<IntTag> f_128670_ = new TagType<IntTag>() {
      public IntTag m_7300_(DataInput p_128703_, int p_128704_, NbtAccounter p_128705_) throws IOException {
         p_128705_.m_6800_(96L);
         return IntTag.m_128679_(p_128703_.readInt());
      }

      public String m_5987_() {
         return "INT";
      }

      public String m_5986_() {
         return "TAG_Int";
      }

      public boolean m_7064_() {
         return true;
      }
   };
   private final int f_128671_;

   IntTag(int p_128674_) {
      this.f_128671_ = p_128674_;
   }

   public static IntTag m_128679_(int p_128680_) {
      return p_128680_ >= -128 && p_128680_ <= 1024 ? IntTag.Cache.f_128712_[p_128680_ - -128] : new IntTag(p_128680_);
   }

   public void m_6434_(DataOutput p_128682_) throws IOException {
      p_128682_.writeInt(this.f_128671_);
   }

   public byte m_7060_() {
      return 3;
   }

   public TagType<IntTag> m_6458_() {
      return f_128670_;
   }

   public IntTag m_6426_() {
      return this;
   }

   public boolean equals(Object p_128691_) {
      if (this == p_128691_) {
         return true;
      } else {
         return p_128691_ instanceof IntTag && this.f_128671_ == ((IntTag)p_128691_).f_128671_;
      }
   }

   public int hashCode() {
      return this.f_128671_;
   }

   public void m_142327_(TagVisitor p_177984_) {
      p_177984_.m_142045_(this);
   }

   public long m_7046_() {
      return (long)this.f_128671_;
   }

   public int m_7047_() {
      return this.f_128671_;
   }

   public short m_7053_() {
      return (short)(this.f_128671_ & '\uffff');
   }

   public byte m_7063_() {
      return (byte)(this.f_128671_ & 255);
   }

   public double m_7061_() {
      return (double)this.f_128671_;
   }

   public float m_7057_() {
      return (float)this.f_128671_;
   }

   public Number m_8103_() {
      return this.f_128671_;
   }

   static class Cache {
      private static final int f_177985_ = 1024;
      private static final int f_177986_ = -128;
      static final IntTag[] f_128712_ = new IntTag[1153];

      private Cache() {
      }

      static {
         for(int i = 0; i < f_128712_.length; ++i) {
            f_128712_[i] = new IntTag(-128 + i);
         }

      }
   }
}