package net.minecraft.util;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.core.IdMap;

public class CrudeIncrementalIntIdentityHashBiMap<K> implements IdMap<K> {
   public static final int f_144605_ = -1;
   private static final Object f_13545_ = null;
   private static final float f_144606_ = 0.8F;
   private K[] f_13546_;
   private int[] f_13547_;
   private K[] f_13548_;
   private int f_13549_;
   private int f_13550_;

   public CrudeIncrementalIntIdentityHashBiMap(int p_13553_) {
      p_13553_ = (int)((float)p_13553_ / 0.8F);
      this.f_13546_ = (K[])(new Object[p_13553_]);
      this.f_13547_ = new int[p_13553_];
      this.f_13548_ = (K[])(new Object[p_13553_]);
   }

   public int m_7447_(@Nullable K p_13558_) {
      return this.m_13567_(this.m_13563_(p_13558_, this.m_13573_(p_13558_)));
   }

   @Nullable
   public K m_7942_(int p_13556_) {
      return (K)(p_13556_ >= 0 && p_13556_ < this.f_13548_.length ? this.f_13548_[p_13556_] : null);
   }

   private int m_13567_(int p_13568_) {
      return p_13568_ == -1 ? -1 : this.f_13547_[p_13568_];
   }

   public boolean m_144609_(K p_144610_) {
      return this.m_7447_(p_144610_) != -1;
   }

   public boolean m_144607_(int p_144608_) {
      return this.m_7942_(p_144608_) != null;
   }

   public int m_13569_(K p_13570_) {
      int i = this.m_13566_();
      this.m_13559_(p_13570_, i);
      return i;
   }

   private int m_13566_() {
      while(this.f_13549_ < this.f_13548_.length && this.f_13548_[this.f_13549_] != null) {
         ++this.f_13549_;
      }

      return this.f_13549_;
   }

   private void m_13571_(int p_13572_) {
      K[] ak = this.f_13546_;
      int[] aint = this.f_13547_;
      this.f_13546_ = (K[])(new Object[p_13572_]);
      this.f_13547_ = new int[p_13572_];
      this.f_13548_ = (K[])(new Object[p_13572_]);
      this.f_13549_ = 0;
      this.f_13550_ = 0;

      for(int i = 0; i < ak.length; ++i) {
         if (ak[i] != null) {
            this.m_13559_(ak[i], aint[i]);
         }
      }

   }

   public void m_13559_(K p_13560_, int p_13561_) {
      int i = Math.max(p_13561_, this.f_13550_ + 1);
      if ((float)i >= (float)this.f_13546_.length * 0.8F) {
         int j;
         for(j = this.f_13546_.length << 1; j < p_13561_; j <<= 1) {
         }

         this.m_13571_(j);
      }

      int k = this.m_13575_(this.m_13573_(p_13560_));
      this.f_13546_[k] = p_13560_;
      this.f_13547_[k] = p_13561_;
      this.f_13548_[p_13561_] = p_13560_;
      ++this.f_13550_;
      if (p_13561_ == this.f_13549_) {
         ++this.f_13549_;
      }

   }

   private int m_13573_(@Nullable K p_13574_) {
      return (Mth.m_14183_(System.identityHashCode(p_13574_)) & Integer.MAX_VALUE) % this.f_13546_.length;
   }

   private int m_13563_(@Nullable K p_13564_, int p_13565_) {
      for(int i = p_13565_; i < this.f_13546_.length; ++i) {
         if (this.f_13546_[i] == p_13564_) {
            return i;
         }

         if (this.f_13546_[i] == f_13545_) {
            return -1;
         }
      }

      for(int j = 0; j < p_13565_; ++j) {
         if (this.f_13546_[j] == p_13564_) {
            return j;
         }

         if (this.f_13546_[j] == f_13545_) {
            return -1;
         }
      }

      return -1;
   }

   private int m_13575_(int p_13576_) {
      for(int i = p_13576_; i < this.f_13546_.length; ++i) {
         if (this.f_13546_[i] == f_13545_) {
            return i;
         }
      }

      for(int j = 0; j < p_13576_; ++j) {
         if (this.f_13546_[j] == f_13545_) {
            return j;
         }
      }

      throw new RuntimeException("Overflowed :(");
   }

   public Iterator<K> iterator() {
      return Iterators.filter(Iterators.forArray(this.f_13548_), Predicates.notNull());
   }

   public void m_13554_() {
      Arrays.fill(this.f_13546_, (Object)null);
      Arrays.fill(this.f_13548_, (Object)null);
      this.f_13549_ = 0;
      this.f_13550_ = 0;
   }

   public int m_13562_() {
      return this.f_13550_;
   }
}