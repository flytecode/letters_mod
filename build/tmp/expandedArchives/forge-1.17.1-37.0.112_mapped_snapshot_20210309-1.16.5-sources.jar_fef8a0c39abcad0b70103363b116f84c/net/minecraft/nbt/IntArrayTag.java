package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

public class IntArrayTag extends CollectionTag<IntTag> {
   private static final int f_177867_ = 192;
   public static final TagType<IntArrayTag> f_128599_ = new TagType<IntArrayTag>() {
      public IntArrayTag m_7300_(DataInput p_128662_, int p_128663_, NbtAccounter p_128664_) throws IOException {
         p_128664_.m_6800_(192L);
         int i = p_128662_.readInt();
         p_128664_.m_6800_(32L * (long)i);
         int[] aint = new int[i];

         for(int j = 0; j < i; ++j) {
            aint[j] = p_128662_.readInt();
         }

         return new IntArrayTag(aint);
      }

      public String m_5987_() {
         return "INT[]";
      }

      public String m_5986_() {
         return "TAG_Int_Array";
      }
   };
   private int[] f_128600_;

   public IntArrayTag(int[] p_128605_) {
      this.f_128600_ = p_128605_;
   }

   public IntArrayTag(List<Integer> p_128603_) {
      this(m_128620_(p_128603_));
   }

   private static int[] m_128620_(List<Integer> p_128621_) {
      int[] aint = new int[p_128621_.size()];

      for(int i = 0; i < p_128621_.size(); ++i) {
         Integer integer = p_128621_.get(i);
         aint[i] = integer == null ? 0 : integer;
      }

      return aint;
   }

   public void m_6434_(DataOutput p_128616_) throws IOException {
      p_128616_.writeInt(this.f_128600_.length);

      for(int i : this.f_128600_) {
         p_128616_.writeInt(i);
      }

   }

   public byte m_7060_() {
      return 11;
   }

   public TagType<IntArrayTag> m_6458_() {
      return f_128599_;
   }

   public String toString() {
      return this.m_7916_();
   }

   public IntArrayTag m_6426_() {
      int[] aint = new int[this.f_128600_.length];
      System.arraycopy(this.f_128600_, 0, aint, 0, this.f_128600_.length);
      return new IntArrayTag(aint);
   }

   public boolean equals(Object p_128647_) {
      if (this == p_128647_) {
         return true;
      } else {
         return p_128647_ instanceof IntArrayTag && Arrays.equals(this.f_128600_, ((IntArrayTag)p_128647_).f_128600_);
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.f_128600_);
   }

   public int[] m_128648_() {
      return this.f_128600_;
   }

   public void m_142327_(TagVisitor p_177869_) {
      p_177869_.m_142251_(this);
   }

   public int size() {
      return this.f_128600_.length;
   }

   public IntTag get(int p_128608_) {
      return IntTag.m_128679_(this.f_128600_[p_128608_]);
   }

   public IntTag set(int p_128610_, IntTag p_128611_) {
      int i = this.f_128600_[p_128610_];
      this.f_128600_[p_128610_] = p_128611_.m_7047_();
      return IntTag.m_128679_(i);
   }

   public void add(int p_128629_, IntTag p_128630_) {
      this.f_128600_ = ArrayUtils.add(this.f_128600_, p_128629_, p_128630_.m_7047_());
   }

   public boolean m_7615_(int p_128613_, Tag p_128614_) {
      if (p_128614_ instanceof NumericTag) {
         this.f_128600_[p_128613_] = ((NumericTag)p_128614_).m_7047_();
         return true;
      } else {
         return false;
      }
   }

   public boolean m_7614_(int p_128632_, Tag p_128633_) {
      if (p_128633_ instanceof NumericTag) {
         this.f_128600_ = ArrayUtils.add(this.f_128600_, p_128632_, ((NumericTag)p_128633_).m_7047_());
         return true;
      } else {
         return false;
      }
   }

   public IntTag remove(int p_128627_) {
      int i = this.f_128600_[p_128627_];
      this.f_128600_ = ArrayUtils.remove(this.f_128600_, p_128627_);
      return IntTag.m_128679_(i);
   }

   public byte m_7264_() {
      return 3;
   }

   public void clear() {
      this.f_128600_ = new int[0];
   }
}