package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import net.minecraft.util.Mth;

public class FloatTag extends NumericTag {
   private static final int f_177864_ = 96;
   public static final FloatTag f_128559_ = new FloatTag(0.0F);
   public static final TagType<FloatTag> f_128560_ = new TagType<FloatTag>() {
      public FloatTag m_7300_(DataInput p_128590_, int p_128591_, NbtAccounter p_128592_) throws IOException {
         p_128592_.m_6800_(96L);
         return FloatTag.m_128566_(p_128590_.readFloat());
      }

      public String m_5987_() {
         return "FLOAT";
      }

      public String m_5986_() {
         return "TAG_Float";
      }

      public boolean m_7064_() {
         return true;
      }
   };
   private final float f_128561_;

   private FloatTag(float p_128564_) {
      this.f_128561_ = p_128564_;
   }

   public static FloatTag m_128566_(float p_128567_) {
      return p_128567_ == 0.0F ? f_128559_ : new FloatTag(p_128567_);
   }

   public void m_6434_(DataOutput p_128569_) throws IOException {
      p_128569_.writeFloat(this.f_128561_);
   }

   public byte m_7060_() {
      return 5;
   }

   public TagType<FloatTag> m_6458_() {
      return f_128560_;
   }

   public FloatTag m_6426_() {
      return this;
   }

   public boolean equals(Object p_128578_) {
      if (this == p_128578_) {
         return true;
      } else {
         return p_128578_ instanceof FloatTag && this.f_128561_ == ((FloatTag)p_128578_).f_128561_;
      }
   }

   public int hashCode() {
      return Float.floatToIntBits(this.f_128561_);
   }

   public void m_142327_(TagVisitor p_177866_) {
      p_177866_.m_142181_(this);
   }

   public long m_7046_() {
      return (long)this.f_128561_;
   }

   public int m_7047_() {
      return Mth.m_14143_(this.f_128561_);
   }

   public short m_7053_() {
      return (short)(Mth.m_14143_(this.f_128561_) & '\uffff');
   }

   public byte m_7063_() {
      return (byte)(Mth.m_14143_(this.f_128561_) & 255);
   }

   public double m_7061_() {
      return (double)this.f_128561_;
   }

   public float m_7057_() {
      return this.f_128561_;
   }

   public Number m_8103_() {
      return this.f_128561_;
   }
}