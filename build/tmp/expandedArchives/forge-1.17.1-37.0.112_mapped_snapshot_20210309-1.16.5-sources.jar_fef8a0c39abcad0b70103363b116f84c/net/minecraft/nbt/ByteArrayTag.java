package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class ByteArrayTag extends CollectionTag<ByteTag> {
   private static final int f_177837_ = 192;
   public static final TagType<ByteArrayTag> f_128185_ = new TagType<ByteArrayTag>() {
      public ByteArrayTag m_7300_(DataInput p_128247_, int p_128248_, NbtAccounter p_128249_) throws IOException {
         p_128249_.m_6800_(192L);
         int i = p_128247_.readInt();
         p_128249_.m_6800_(8L * (long)i);
         byte[] abyte = new byte[i];
         p_128247_.readFully(abyte);
         return new ByteArrayTag(abyte);
      }

      public String m_5987_() {
         return "BYTE[]";
      }

      public String m_5986_() {
         return "TAG_Byte_Array";
      }
   };
   private byte[] f_128186_;

   public ByteArrayTag(byte[] p_128191_) {
      this.f_128186_ = p_128191_;
   }

   public ByteArrayTag(List<Byte> p_128189_) {
      this(m_128206_(p_128189_));
   }

   private static byte[] m_128206_(List<Byte> p_128207_) {
      byte[] abyte = new byte[p_128207_.size()];

      for(int i = 0; i < p_128207_.size(); ++i) {
         Byte obyte = p_128207_.get(i);
         abyte[i] = obyte == null ? 0 : obyte;
      }

      return abyte;
   }

   public void m_6434_(DataOutput p_128202_) throws IOException {
      p_128202_.writeInt(this.f_128186_.length);
      p_128202_.write(this.f_128186_);
   }

   public byte m_7060_() {
      return 7;
   }

   public TagType<ByteArrayTag> m_6458_() {
      return f_128185_;
   }

   public String toString() {
      return this.m_7916_();
   }

   public Tag m_6426_() {
      byte[] abyte = new byte[this.f_128186_.length];
      System.arraycopy(this.f_128186_, 0, abyte, 0, this.f_128186_.length);
      return new ByteArrayTag(abyte);
   }

   public boolean equals(Object p_128233_) {
      if (this == p_128233_) {
         return true;
      } else {
         return p_128233_ instanceof ByteArrayTag && Arrays.equals(this.f_128186_, ((ByteArrayTag)p_128233_).f_128186_);
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.f_128186_);
   }

   public void m_142327_(TagVisitor p_177839_) {
      p_177839_.m_142154_(this);
   }

   public byte[] m_128227_() {
      return this.f_128186_;
   }

   public int size() {
      return this.f_128186_.length;
   }

   public ByteTag get(int p_128194_) {
      return ByteTag.m_128266_(this.f_128186_[p_128194_]);
   }

   public ByteTag set(int p_128196_, ByteTag p_128197_) {
      byte b0 = this.f_128186_[p_128196_];
      this.f_128186_[p_128196_] = p_128197_.m_7063_();
      return ByteTag.m_128266_(b0);
   }

   public void add(int p_128215_, ByteTag p_128216_) {
      this.f_128186_ = ArrayUtils.add(this.f_128186_, p_128215_, p_128216_.m_7063_());
   }

   public boolean m_7615_(int p_128199_, Tag p_128200_) {
      if (p_128200_ instanceof NumericTag) {
         this.f_128186_[p_128199_] = ((NumericTag)p_128200_).m_7063_();
         return true;
      } else {
         return false;
      }
   }

   public boolean m_7614_(int p_128218_, Tag p_128219_) {
      if (p_128219_ instanceof NumericTag) {
         this.f_128186_ = ArrayUtils.add(this.f_128186_, p_128218_, ((NumericTag)p_128219_).m_7063_());
         return true;
      } else {
         return false;
      }
   }

   public ByteTag remove(int p_128213_) {
      byte b0 = this.f_128186_[p_128213_];
      this.f_128186_ = ArrayUtils.remove(this.f_128186_, p_128213_);
      return ByteTag.m_128266_(b0);
   }

   public byte m_7264_() {
      return 1;
   }

   public void clear() {
      this.f_128186_ = new byte[0];
   }
}