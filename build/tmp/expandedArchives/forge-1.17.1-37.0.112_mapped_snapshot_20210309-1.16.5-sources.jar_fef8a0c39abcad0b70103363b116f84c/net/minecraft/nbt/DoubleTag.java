package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.Mth;

public class DoubleTag extends NumericTag {
   private static final int f_177858_ = 128;
   public static final DoubleTag f_128493_ = new DoubleTag(0.0D);
   public static final TagType<DoubleTag> f_128494_ = new TagType<DoubleTag>() {
      public DoubleTag m_7300_(DataInput p_128524_, int p_128525_, NbtAccounter p_128526_) throws IOException {
         p_128526_.m_6800_(128L);
         return DoubleTag.m_128500_(p_128524_.readDouble());
      }

      public String m_5987_() {
         return "DOUBLE";
      }

      public String m_5986_() {
         return "TAG_Double";
      }

      public boolean m_7064_() {
         return true;
      }
   };
   private final double f_128495_;

   private DoubleTag(double p_128498_) {
      this.f_128495_ = p_128498_;
   }

   public static DoubleTag m_128500_(double p_128501_) {
      return p_128501_ == 0.0D ? f_128493_ : new DoubleTag(p_128501_);
   }

   public void m_6434_(DataOutput p_128503_) throws IOException {
      p_128503_.writeDouble(this.f_128495_);
   }

   public byte m_7060_() {
      return 6;
   }

   public TagType<DoubleTag> m_6458_() {
      return f_128494_;
   }

   public DoubleTag m_6426_() {
      return this;
   }

   public boolean equals(Object p_128512_) {
      if (this == p_128512_) {
         return true;
      } else {
         return p_128512_ instanceof DoubleTag && this.f_128495_ == ((DoubleTag)p_128512_).f_128495_;
      }
   }

   public int hashCode() {
      long i = Double.doubleToLongBits(this.f_128495_);
      return (int)(i ^ i >>> 32);
   }

   public void m_142327_(TagVisitor p_177860_) {
      p_177860_.m_142121_(this);
   }

   public long m_7046_() {
      return (long)Math.floor(this.f_128495_);
   }

   public int m_7047_() {
      return Mth.m_14107_(this.f_128495_);
   }

   public short m_7053_() {
      return (short)(Mth.m_14107_(this.f_128495_) & '\uffff');
   }

   public byte m_7063_() {
      return (byte)(Mth.m_14107_(this.f_128495_) & 255);
   }

   public double m_7061_() {
      return this.f_128495_;
   }

   public float m_7057_() {
      return (float)this.f_128495_;
   }

   public Number m_8103_() {
      return this.f_128495_;
   }
}