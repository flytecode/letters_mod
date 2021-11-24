package net.minecraft.util;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;

public class SortedArraySet<T> extends AbstractSet<T> {
   private static final int f_144974_ = 10;
   private final Comparator<T> f_14240_;
   T[] f_14241_;
   int f_14242_;

   private SortedArraySet(int p_14244_, Comparator<T> p_14245_) {
      this.f_14240_ = p_14245_;
      if (p_14244_ < 0) {
         throw new IllegalArgumentException("Initial capacity (" + p_14244_ + ") is negative");
      } else {
         this.f_14241_ = (T[])m_14258_(new Object[p_14244_]);
      }
   }

   public static <T extends Comparable<T>> SortedArraySet<T> m_144975_() {
      return m_14246_(10);
   }

   public static <T extends Comparable<T>> SortedArraySet<T> m_14246_(int p_14247_) {
      return new SortedArraySet<>(p_14247_, Comparator.<T>naturalOrder());
   }

   public static <T> SortedArraySet<T> m_144976_(Comparator<T> p_144977_) {
      return m_144978_(p_144977_, 10);
   }

   public static <T> SortedArraySet<T> m_144978_(Comparator<T> p_144979_, int p_144980_) {
      return new SortedArraySet<>(p_144980_, p_144979_);
   }

   private static <T> T[] m_14258_(Object[] p_14259_) {
      return (T[])p_14259_;
   }

   private int m_14269_(T p_14270_) {
      return Arrays.binarySearch(this.f_14241_, 0, this.f_14242_, p_14270_, this.f_14240_);
   }

   private static int m_14263_(int p_14264_) {
      return -p_14264_ - 1;
   }

   public boolean add(T p_14261_) {
      int i = this.m_14269_(p_14261_);
      if (i >= 0) {
         return false;
      } else {
         int j = m_14263_(i);
         this.m_14255_(p_14261_, j);
         return true;
      }
   }

   private void m_14267_(int p_14268_) {
      if (p_14268_ > this.f_14241_.length) {
         if (this.f_14241_ != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            p_14268_ = (int)Math.max(Math.min((long)this.f_14241_.length + (long)(this.f_14241_.length >> 1), 2147483639L), (long)p_14268_);
         } else if (p_14268_ < 10) {
            p_14268_ = 10;
         }

         Object[] aobject = new Object[p_14268_];
         System.arraycopy(this.f_14241_, 0, aobject, 0, this.f_14242_);
         this.f_14241_ = (T[])m_14258_(aobject);
      }
   }

   private void m_14255_(T p_14256_, int p_14257_) {
      this.m_14267_(this.f_14242_ + 1);
      if (p_14257_ != this.f_14242_) {
         System.arraycopy(this.f_14241_, p_14257_, this.f_14241_, p_14257_ + 1, this.f_14242_ - p_14257_);
      }

      this.f_14241_[p_14257_] = p_14256_;
      ++this.f_14242_;
   }

   void m_14274_(int p_14275_) {
      --this.f_14242_;
      if (p_14275_ != this.f_14242_) {
         System.arraycopy(this.f_14241_, p_14275_ + 1, this.f_14241_, p_14275_, this.f_14242_ - p_14275_);
      }

      this.f_14241_[this.f_14242_] = null;
   }

   private T m_14276_(int p_14277_) {
      return this.f_14241_[p_14277_];
   }

   public T m_14253_(T p_14254_) {
      int i = this.m_14269_(p_14254_);
      if (i >= 0) {
         return this.m_14276_(i);
      } else {
         this.m_14255_(p_14254_, m_14263_(i));
         return p_14254_;
      }
   }

   public boolean remove(Object p_14282_) {
      int i = this.m_14269_((T)p_14282_);
      if (i >= 0) {
         this.m_14274_(i);
         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public T m_144981_(T p_144982_) {
      int i = this.m_14269_(p_144982_);
      return (T)(i >= 0 ? this.m_14276_(i) : null);
   }

   public T m_14262_() {
      return this.m_14276_(0);
   }

   public T m_144983_() {
      return this.m_14276_(this.f_14242_ - 1);
   }

   public boolean contains(Object p_14273_) {
      int i = this.m_14269_((T)p_14273_);
      return i >= 0;
   }

   public Iterator<T> iterator() {
      return new SortedArraySet.ArrayIterator();
   }

   public int size() {
      return this.f_14242_;
   }

   public Object[] toArray() {
      return this.f_14241_.clone();
   }

   public <U> U[] toArray(U[] p_14286_) {
      if (p_14286_.length < this.f_14242_) {
         return (U[])Arrays.copyOf(this.f_14241_, this.f_14242_, p_14286_.getClass());
      } else {
         System.arraycopy(this.f_14241_, 0, p_14286_, 0, this.f_14242_);
         if (p_14286_.length > this.f_14242_) {
            p_14286_[this.f_14242_] = null;
         }

         return p_14286_;
      }
   }

   public void clear() {
      Arrays.fill(this.f_14241_, 0, this.f_14242_, (Object)null);
      this.f_14242_ = 0;
   }

   public boolean equals(Object p_14279_) {
      if (this == p_14279_) {
         return true;
      } else {
         if (p_14279_ instanceof SortedArraySet) {
            SortedArraySet<?> sortedarrayset = (SortedArraySet)p_14279_;
            if (this.f_14240_.equals(sortedarrayset.f_14240_)) {
               return this.f_14242_ == sortedarrayset.f_14242_ && Arrays.equals(this.f_14241_, sortedarrayset.f_14241_);
            }
         }

         return super.equals(p_14279_);
      }
   }

   class ArrayIterator implements Iterator<T> {
      private int f_14288_;
      private int f_14289_ = -1;

      public boolean hasNext() {
         return this.f_14288_ < SortedArraySet.this.f_14242_;
      }

      public T next() {
         if (this.f_14288_ >= SortedArraySet.this.f_14242_) {
            throw new NoSuchElementException();
         } else {
            this.f_14289_ = this.f_14288_++;
            return SortedArraySet.this.f_14241_[this.f_14289_];
         }
      }

      public void remove() {
         if (this.f_14289_ == -1) {
            throw new IllegalStateException();
         } else {
            SortedArraySet.this.m_14274_(this.f_14289_);
            --this.f_14288_;
            this.f_14289_ = -1;
         }
      }
   }
}