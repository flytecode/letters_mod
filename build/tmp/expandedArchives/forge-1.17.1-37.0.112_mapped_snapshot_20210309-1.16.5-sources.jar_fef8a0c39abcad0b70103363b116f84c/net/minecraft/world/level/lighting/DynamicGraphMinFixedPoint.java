package net.minecraft.world.level.lighting;

import it.unimi.dsi.fastutil.longs.Long2ByteMap;
import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongLinkedOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongList;
import java.util.function.LongPredicate;
import net.minecraft.util.Mth;

public abstract class DynamicGraphMinFixedPoint {
   private static final int f_164422_ = 255;
   private final int f_75537_;
   private final LongLinkedOpenHashSet[] f_75538_;
   private final Long2ByteMap f_75539_;
   private int f_75540_;
   private volatile boolean f_75541_;

   protected DynamicGraphMinFixedPoint(int p_75543_, final int p_75544_, final int p_75545_) {
      if (p_75543_ >= 254) {
         throw new IllegalArgumentException("Level count must be < 254.");
      } else {
         this.f_75537_ = p_75543_;
         this.f_75538_ = new LongLinkedOpenHashSet[p_75543_];

         for(int i = 0; i < p_75543_; ++i) {
            this.f_75538_[i] = new LongLinkedOpenHashSet(p_75544_, 0.5F) {
               protected void rehash(int p_75611_) {
                  if (p_75611_ > p_75544_) {
                     super.rehash(p_75611_);
                  }

               }
            };
         }

         this.f_75539_ = new Long2ByteOpenHashMap(p_75545_, 0.5F) {
            protected void rehash(int p_75620_) {
               if (p_75620_ > p_75545_) {
                  super.rehash(p_75620_);
               }

            }
         };
         this.f_75539_.defaultReturnValue((byte)-1);
         this.f_75540_ = p_75543_;
      }
   }

   private int m_75548_(int p_75549_, int p_75550_) {
      int i = p_75549_;
      if (p_75549_ > p_75550_) {
         i = p_75550_;
      }

      if (i > this.f_75537_ - 1) {
         i = this.f_75537_ - 1;
      }

      return i;
   }

   private void m_75546_(int p_75547_) {
      int i = this.f_75540_;
      this.f_75540_ = p_75547_;

      for(int j = i + 1; j < p_75547_; ++j) {
         if (!this.f_75538_[j].isEmpty()) {
            this.f_75540_ = j;
            break;
         }
      }

   }

   protected void m_75600_(long p_75601_) {
      int i = this.f_75539_.get(p_75601_) & 255;
      if (i != 255) {
         int j = this.m_6172_(p_75601_);
         int k = this.m_75548_(j, i);
         this.m_75558_(p_75601_, k, this.f_75537_, true);
         this.f_75541_ = this.f_75540_ < this.f_75537_;
      }
   }

   public void m_75581_(LongPredicate p_75582_) {
      LongList longlist = new LongArrayList();
      this.f_75539_.keySet().forEach((long p_75586_) -> {
         if (p_75582_.test(p_75586_)) {
            longlist.add(p_75586_);
         }

      });
      longlist.forEach((java.util.function.LongConsumer)this::m_75600_);
   }

   private void m_75558_(long p_75559_, int p_75560_, int p_75561_, boolean p_75562_) {
      if (p_75562_) {
         this.f_75539_.remove(p_75559_);
      }

      this.f_75538_[p_75560_].remove(p_75559_);
      if (this.f_75538_[p_75560_].isEmpty() && this.f_75540_ == p_75560_) {
         this.m_75546_(p_75561_);
      }

   }

   private void m_75554_(long p_75555_, int p_75556_, int p_75557_) {
      this.f_75539_.put(p_75555_, (byte)p_75556_);
      this.f_75538_[p_75557_].add(p_75555_);
      if (this.f_75540_ > p_75557_) {
         this.f_75540_ = p_75557_;
      }

   }

   protected void m_6185_(long p_75602_) {
      this.m_75576_(p_75602_, p_75602_, this.f_75537_ - 1, false);
   }

   protected void m_75576_(long p_75577_, long p_75578_, int p_75579_, boolean p_75580_) {
      this.m_75569_(p_75577_, p_75578_, p_75579_, this.m_6172_(p_75578_), this.f_75539_.get(p_75578_) & 255, p_75580_);
      this.f_75541_ = this.f_75540_ < this.f_75537_;
   }

   private void m_75569_(long p_75570_, long p_75571_, int p_75572_, int p_75573_, int p_75574_, boolean p_75575_) {
      if (!this.m_6163_(p_75571_)) {
         p_75572_ = Mth.m_14045_(p_75572_, 0, this.f_75537_ - 1);
         p_75573_ = Mth.m_14045_(p_75573_, 0, this.f_75537_ - 1);
         boolean flag;
         if (p_75574_ == 255) {
            flag = true;
            p_75574_ = p_75573_;
         } else {
            flag = false;
         }

         int i;
         if (p_75575_) {
            i = Math.min(p_75574_, p_75572_);
         } else {
            i = Mth.m_14045_(this.m_6357_(p_75571_, p_75570_, p_75572_), 0, this.f_75537_ - 1);
         }

         int j = this.m_75548_(p_75573_, p_75574_);
         if (p_75573_ != i) {
            int k = this.m_75548_(p_75573_, i);
            if (j != k && !flag) {
               this.m_75558_(p_75571_, j, k, false);
            }

            this.m_75554_(p_75571_, i, k);
         } else if (!flag) {
            this.m_75558_(p_75571_, j, this.f_75537_, true);
         }

      }
   }

   protected final void m_75593_(long p_75594_, long p_75595_, int p_75596_, boolean p_75597_) {
      int i = this.f_75539_.get(p_75595_) & 255;
      int j = Mth.m_14045_(this.m_6359_(p_75594_, p_75595_, p_75596_), 0, this.f_75537_ - 1);
      if (p_75597_) {
         this.m_75569_(p_75594_, p_75595_, j, this.m_6172_(p_75595_), i, true);
      } else {
         int k;
         boolean flag;
         if (i == 255) {
            flag = true;
            k = Mth.m_14045_(this.m_6172_(p_75595_), 0, this.f_75537_ - 1);
         } else {
            k = i;
            flag = false;
         }

         if (j == k) {
            this.m_75569_(p_75594_, p_75595_, this.f_75537_ - 1, flag ? k : this.m_6172_(p_75595_), i, false);
         }
      }

   }

   protected final boolean m_75587_() {
      return this.f_75541_;
   }

   protected final int m_75588_(int p_75589_) {
      if (this.f_75540_ >= this.f_75537_) {
         return p_75589_;
      } else {
         while(this.f_75540_ < this.f_75537_ && p_75589_ > 0) {
            --p_75589_;
            LongLinkedOpenHashSet longlinkedopenhashset = this.f_75538_[this.f_75540_];
            long i = longlinkedopenhashset.removeFirstLong();
            int j = Mth.m_14045_(this.m_6172_(i), 0, this.f_75537_ - 1);
            if (longlinkedopenhashset.isEmpty()) {
               this.m_75546_(this.f_75537_);
            }

            int k = this.f_75539_.remove(i) & 255;
            if (k < j) {
               this.m_7351_(i, k);
               this.m_7900_(i, k, true);
            } else if (k > j) {
               this.m_75554_(i, k, this.m_75548_(this.f_75537_ - 1, k));
               this.m_7351_(i, this.f_75537_ - 1);
               this.m_7900_(i, j, false);
            }
         }

         this.f_75541_ = this.f_75540_ < this.f_75537_;
         return p_75589_;
      }
   }

   public int m_75598_() {
      return this.f_75539_.size();
   }

   protected abstract boolean m_6163_(long p_75551_);

   protected abstract int m_6357_(long p_75566_, long p_75567_, int p_75568_);

   protected abstract void m_7900_(long p_75563_, int p_75564_, boolean p_75565_);

   protected abstract int m_6172_(long p_75599_);

   protected abstract void m_7351_(long p_75552_, int p_75553_);

   protected abstract int m_6359_(long p_75590_, long p_75591_, int p_75592_);

   protected int queuedUpdateSize() {
      return f_75539_.size();
   }
}
