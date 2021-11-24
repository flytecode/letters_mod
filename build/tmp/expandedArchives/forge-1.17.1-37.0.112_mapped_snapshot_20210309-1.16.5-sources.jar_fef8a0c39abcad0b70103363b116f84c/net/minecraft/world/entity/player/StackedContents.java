package net.minecraft.world.entity.player;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntAVLTreeSet;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntCollection;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.BitSet;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

public class StackedContents {
   private static final int f_150116_ = 0;
   public final Int2IntMap f_36451_ = new Int2IntOpenHashMap();

   public void m_36466_(ItemStack p_36467_) {
      if (!p_36467_.m_41768_() && !p_36467_.m_41793_() && !p_36467_.m_41788_()) {
         this.m_36491_(p_36467_);
      }

   }

   public void m_36491_(ItemStack p_36492_) {
      this.m_36468_(p_36492_, 64);
   }

   public void m_36468_(ItemStack p_36469_, int p_36470_) {
      if (!p_36469_.m_41619_()) {
         int i = m_36496_(p_36469_);
         int j = Math.min(p_36470_, p_36469_.m_41613_());
         this.m_36484_(i, j);
      }

   }

   public static int m_36496_(ItemStack p_36497_) {
      return Registry.f_122827_.m_7447_(p_36497_.m_41720_());
   }

   boolean m_36482_(int p_36483_) {
      return this.f_36451_.get(p_36483_) > 0;
   }

   int m_36456_(int p_36457_, int p_36458_) {
      int i = this.f_36451_.get(p_36457_);
      if (i >= p_36458_) {
         this.f_36451_.put(p_36457_, i - p_36458_);
         return p_36457_;
      } else {
         return 0;
      }
   }

   void m_36484_(int p_36485_, int p_36486_) {
      this.f_36451_.put(p_36485_, this.f_36451_.get(p_36485_) + p_36486_);
   }

   public boolean m_36475_(Recipe<?> p_36476_, @Nullable IntList p_36477_) {
      return this.m_36478_(p_36476_, p_36477_, 1);
   }

   public boolean m_36478_(Recipe<?> p_36479_, @Nullable IntList p_36480_, int p_36481_) {
      return (new StackedContents.RecipePicker(p_36479_)).m_36512_(p_36481_, p_36480_);
   }

   public int m_36493_(Recipe<?> p_36494_, @Nullable IntList p_36495_) {
      return this.m_36471_(p_36494_, Integer.MAX_VALUE, p_36495_);
   }

   public int m_36471_(Recipe<?> p_36472_, int p_36473_, @Nullable IntList p_36474_) {
      return (new StackedContents.RecipePicker(p_36472_)).m_36525_(p_36473_, p_36474_);
   }

   public static ItemStack m_36454_(int p_36455_) {
      return p_36455_ == 0 ? ItemStack.f_41583_ : new ItemStack(Item.m_41445_(p_36455_));
   }

   public void m_36453_() {
      this.f_36451_.clear();
   }

   class RecipePicker {
      private final Recipe<?> f_36499_;
      private final List<Ingredient> f_36500_ = Lists.newArrayList();
      private final int f_36501_;
      private final int[] f_36502_;
      private final int f_36503_;
      private final BitSet f_36504_;
      private final IntList f_36505_ = new IntArrayList();

      public RecipePicker(Recipe<?> p_36508_) {
         this.f_36499_ = p_36508_;
         this.f_36500_.addAll(p_36508_.m_7527_());
         this.f_36500_.removeIf(Ingredient::m_43947_);
         this.f_36501_ = this.f_36500_.size();
         this.f_36502_ = this.m_36509_();
         this.f_36503_ = this.f_36502_.length;
         this.f_36504_ = new BitSet(this.f_36501_ + this.f_36503_ + this.f_36501_ + this.f_36501_ * this.f_36503_);

         for(int i = 0; i < this.f_36500_.size(); ++i) {
            IntList intlist = this.f_36500_.get(i).m_43931_();

            for(int j = 0; j < this.f_36503_; ++j) {
               if (intlist.contains(this.f_36502_[j])) {
                  this.f_36504_.set(this.m_36546_(true, j, i));
               }
            }
         }

      }

      public boolean m_36512_(int p_36513_, @Nullable IntList p_36514_) {
         if (p_36513_ <= 0) {
            return true;
         } else {
            int i;
            for(i = 0; this.m_36510_(p_36513_); ++i) {
               StackedContents.this.m_36456_(this.f_36502_[this.f_36505_.getInt(0)], p_36513_);
               int j = this.f_36505_.size() - 1;
               this.m_36535_(this.f_36505_.getInt(j));

               for(int k = 0; k < j; ++k) {
                  this.m_36540_((k & 1) == 0, this.f_36505_.get(k), this.f_36505_.get(k + 1));
               }

               this.f_36505_.clear();
               this.f_36504_.clear(0, this.f_36501_ + this.f_36503_);
            }

            boolean flag = i == this.f_36501_;
            boolean flag1 = flag && p_36514_ != null;
            if (flag1) {
               p_36514_.clear();
            }

            this.f_36504_.clear(0, this.f_36501_ + this.f_36503_ + this.f_36501_);
            int l = 0;
            List<Ingredient> list = this.f_36499_.m_7527_();

            for(int i1 = 0; i1 < list.size(); ++i1) {
               if (flag1 && list.get(i1).m_43947_()) {
                  p_36514_.add(0);
               } else {
                  for(int j1 = 0; j1 < this.f_36503_; ++j1) {
                     if (this.m_36531_(false, l, j1)) {
                        this.m_36540_(true, j1, l);
                        StackedContents.this.m_36484_(this.f_36502_[j1], p_36513_);
                        if (flag1) {
                           p_36514_.add(this.f_36502_[j1]);
                        }
                     }
                  }

                  ++l;
               }
            }

            return flag;
         }
      }

      private int[] m_36509_() {
         IntCollection intcollection = new IntAVLTreeSet();

         for(Ingredient ingredient : this.f_36500_) {
            intcollection.addAll(ingredient.m_43931_());
         }

         IntIterator intiterator = intcollection.iterator();

         while(intiterator.hasNext()) {
            if (!StackedContents.this.m_36482_(intiterator.nextInt())) {
               intiterator.remove();
            }
         }

         return intcollection.toIntArray();
      }

      private boolean m_36510_(int p_36511_) {
         int i = this.f_36503_;

         for(int j = 0; j < i; ++j) {
            if (StackedContents.this.f_36451_.get(this.f_36502_[j]) >= p_36511_) {
               this.m_36515_(false, j);

               while(!this.f_36505_.isEmpty()) {
                  int k = this.f_36505_.size();
                  boolean flag = (k & 1) == 1;
                  int l = this.f_36505_.getInt(k - 1);
                  if (!flag && !this.m_36523_(l)) {
                     break;
                  }

                  int i1 = flag ? this.f_36501_ : i;

                  for(int j1 = 0; j1 < i1; ++j1) {
                     if (!this.m_36528_(flag, j1) && this.m_36518_(flag, l, j1) && this.m_36531_(flag, l, j1)) {
                        this.m_36515_(flag, j1);
                        break;
                     }
                  }

                  int k1 = this.f_36505_.size();
                  if (k1 == k) {
                     this.f_36505_.removeInt(k1 - 1);
                  }
               }

               if (!this.f_36505_.isEmpty()) {
                  return true;
               }
            }
         }

         return false;
      }

      private boolean m_36523_(int p_36524_) {
         return this.f_36504_.get(this.m_36544_(p_36524_));
      }

      private void m_36535_(int p_36536_) {
         this.f_36504_.set(this.m_36544_(p_36536_));
      }

      private int m_36544_(int p_36545_) {
         return this.f_36501_ + this.f_36503_ + p_36545_;
      }

      private boolean m_36518_(boolean p_36519_, int p_36520_, int p_36521_) {
         return this.f_36504_.get(this.m_36546_(p_36519_, p_36520_, p_36521_));
      }

      private boolean m_36531_(boolean p_36532_, int p_36533_, int p_36534_) {
         return p_36532_ != this.f_36504_.get(1 + this.m_36546_(p_36532_, p_36533_, p_36534_));
      }

      private void m_36540_(boolean p_36541_, int p_36542_, int p_36543_) {
         this.f_36504_.flip(1 + this.m_36546_(p_36541_, p_36542_, p_36543_));
      }

      private int m_36546_(boolean p_36547_, int p_36548_, int p_36549_) {
         int i = p_36547_ ? p_36548_ * this.f_36501_ + p_36549_ : p_36549_ * this.f_36501_ + p_36548_;
         return this.f_36501_ + this.f_36503_ + this.f_36501_ + 2 * i;
      }

      private void m_36515_(boolean p_36516_, int p_36517_) {
         this.f_36504_.set(this.m_36537_(p_36516_, p_36517_));
         this.f_36505_.add(p_36517_);
      }

      private boolean m_36528_(boolean p_36529_, int p_36530_) {
         return this.f_36504_.get(this.m_36537_(p_36529_, p_36530_));
      }

      private int m_36537_(boolean p_36538_, int p_36539_) {
         return (p_36538_ ? 0 : this.f_36501_) + p_36539_;
      }

      public int m_36525_(int p_36526_, @Nullable IntList p_36527_) {
         int i = 0;
         int j = Math.min(p_36526_, this.m_36522_()) + 1;

         while(true) {
            int k = (i + j) / 2;
            if (this.m_36512_(k, (IntList)null)) {
               if (j - i <= 1) {
                  if (k > 0) {
                     this.m_36512_(k, p_36527_);
                  }

                  return k;
               }

               i = k;
            } else {
               j = k;
            }
         }
      }

      private int m_36522_() {
         int i = Integer.MAX_VALUE;

         for(Ingredient ingredient : this.f_36500_) {
            int j = 0;

            for(int k : ingredient.m_43931_()) {
               j = Math.max(j, StackedContents.this.f_36451_.get(k));
            }

            if (i > 0) {
               i = Math.min(i, j);
            }
         }

         return i;
      }
   }
}