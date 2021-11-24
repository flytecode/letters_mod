package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.HashCommon;
import it.unimi.dsi.fastutil.longs.Long2LongLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import java.util.NoSuchElementException;
import net.minecraft.util.Mth;

public class SpatialLongSet extends LongLinkedOpenHashSet {
   private final SpatialLongSet.InternalMap f_164460_;

   public SpatialLongSet(int p_164462_, float p_164463_) {
      super(p_164462_, p_164463_);
      this.f_164460_ = new SpatialLongSet.InternalMap(p_164462_ / 64, p_164463_);
   }

   public boolean add(long p_164465_) {
      return this.f_164460_.m_164499_(p_164465_);
   }

   public boolean rem(long p_164468_) {
      return this.f_164460_.m_164501_(p_164468_);
   }

   public long removeFirstLong() {
      return this.f_164460_.m_164485_();
   }

   public int size() {
      throw new UnsupportedOperationException();
   }

   public boolean isEmpty() {
      return this.f_164460_.isEmpty();
   }

   protected static class InternalMap extends Long2LongLinkedOpenHashMap {
      private static final int f_164471_ = Mth.m_14173_(60000000);
      private static final int f_164472_ = Mth.m_14173_(60000000);
      private static final int f_164473_ = 64 - f_164471_ - f_164472_;
      private static final int f_164474_ = 0;
      private static final int f_164475_ = f_164473_;
      private static final int f_164476_ = f_164473_ + f_164472_;
      private static final long f_164477_ = 3L << f_164476_ | 3L | 3L << f_164475_;
      private int f_164478_ = -1;
      private long f_164479_;
      private final int f_164480_;

      public InternalMap(int p_164483_, float p_164484_) {
         super(p_164483_, p_164484_);
         this.f_164480_ = p_164483_;
      }

      static long m_164489_(long p_164490_) {
         return p_164490_ & ~f_164477_;
      }

      static int m_164497_(long p_164498_) {
         int i = (int)(p_164498_ >>> f_164476_ & 3L);
         int j = (int)(p_164498_ >>> 0 & 3L);
         int k = (int)(p_164498_ >>> f_164475_ & 3L);
         return i << 4 | k << 2 | j;
      }

      static long m_164491_(long p_164492_, int p_164493_) {
         p_164492_ = p_164492_ | (long)(p_164493_ >>> 4 & 3) << f_164476_;
         p_164492_ = p_164492_ | (long)(p_164493_ >>> 2 & 3) << f_164475_;
         return p_164492_ | (long)(p_164493_ >>> 0 & 3) << 0;
      }

      public boolean m_164499_(long p_164500_) {
         long i = m_164489_(p_164500_);
         int j = m_164497_(p_164500_);
         long k = 1L << j;
         int l;
         if (i == 0L) {
            if (this.containsNullKey) {
               return this.m_164486_(this.n, k);
            }

            this.containsNullKey = true;
            l = this.n;
         } else {
            if (this.f_164478_ != -1 && i == this.f_164479_) {
               return this.m_164486_(this.f_164478_, k);
            }

            long[] along = this.key;
            l = (int)HashCommon.mix(i) & this.mask;

            for(long i1 = along[l]; i1 != 0L; i1 = along[l]) {
               if (i1 == i) {
                  this.f_164478_ = l;
                  this.f_164479_ = i;
                  return this.m_164486_(l, k);
               }

               l = l + 1 & this.mask;
            }
         }

         this.key[l] = i;
         this.value[l] = k;
         if (this.size == 0) {
            this.first = this.last = l;
            this.link[l] = -1L;
         } else {
            this.link[this.last] ^= (this.link[this.last] ^ (long)l & 4294967295L) & 4294967295L;
            this.link[l] = ((long)this.last & 4294967295L) << 32 | 4294967295L;
            this.last = l;
         }

         if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
         }

         return false;
      }

      private boolean m_164486_(int p_164487_, long p_164488_) {
         boolean flag = (this.value[p_164487_] & p_164488_) != 0L;
         this.value[p_164487_] |= p_164488_;
         return flag;
      }

      public boolean m_164501_(long p_164502_) {
         long i = m_164489_(p_164502_);
         int j = m_164497_(p_164502_);
         long k = 1L << j;
         if (i == 0L) {
            return this.containsNullKey ? this.m_164503_(k) : false;
         } else if (this.f_164478_ != -1 && i == this.f_164479_) {
            return this.m_164494_(this.f_164478_, k);
         } else {
            long[] along = this.key;
            int l = (int)HashCommon.mix(i) & this.mask;

            for(long i1 = along[l]; i1 != 0L; i1 = along[l]) {
               if (i == i1) {
                  this.f_164478_ = l;
                  this.f_164479_ = i;
                  return this.m_164494_(l, k);
               }

               l = l + 1 & this.mask;
            }

            return false;
         }
      }

      private boolean m_164503_(long p_164504_) {
         if ((this.value[this.n] & p_164504_) == 0L) {
            return false;
         } else {
            this.value[this.n] &= ~p_164504_;
            if (this.value[this.n] != 0L) {
               return true;
            } else {
               this.containsNullKey = false;
               --this.size;
               this.fixPointers(this.n);
               if (this.size < this.maxFill / 4 && this.n > 16) {
                  this.rehash(this.n / 2);
               }

               return true;
            }
         }
      }

      private boolean m_164494_(int p_164495_, long p_164496_) {
         if ((this.value[p_164495_] & p_164496_) == 0L) {
            return false;
         } else {
            this.value[p_164495_] &= ~p_164496_;
            if (this.value[p_164495_] != 0L) {
               return true;
            } else {
               this.f_164478_ = -1;
               --this.size;
               this.fixPointers(p_164495_);
               this.shiftKeys(p_164495_);
               if (this.size < this.maxFill / 4 && this.n > 16) {
                  this.rehash(this.n / 2);
               }

               return true;
            }
         }
      }

      public long m_164485_() {
         if (this.size == 0) {
            throw new NoSuchElementException();
         } else {
            int i = this.first;
            long j = this.key[i];
            int k = Long.numberOfTrailingZeros(this.value[i]);
            this.value[i] &= ~(1L << k);
            if (this.value[i] == 0L) {
               this.removeFirstLong();
               this.f_164478_ = -1;
            }

            return m_164491_(j, k);
         }
      }

      protected void rehash(int p_164506_) {
         if (p_164506_ > this.f_164480_) {
            super.rehash(p_164506_);
         }

      }
   }
}