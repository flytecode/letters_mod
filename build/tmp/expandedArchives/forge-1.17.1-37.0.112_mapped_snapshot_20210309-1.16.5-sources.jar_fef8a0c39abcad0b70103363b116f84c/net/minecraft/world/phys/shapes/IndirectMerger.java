package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleLists;

public class IndirectMerger implements IndexMerger {
   private static final DoubleList f_166021_ = DoubleLists.unmodifiable(DoubleArrayList.wrap(new double[]{0.0D}));
   private final double[] f_82997_;
   private final int[] f_82998_;
   private final int[] f_82999_;
   private final int f_166022_;

   public IndirectMerger(DoubleList p_83001_, DoubleList p_83002_, boolean p_83003_, boolean p_83004_) {
      double d0 = Double.NaN;
      int i = p_83001_.size();
      int j = p_83002_.size();
      int k = i + j;
      this.f_82997_ = new double[k];
      this.f_82998_ = new int[k];
      this.f_82999_ = new int[k];
      boolean flag = !p_83003_;
      boolean flag1 = !p_83004_;
      int l = 0;
      int i1 = 0;
      int j1 = 0;

      while(true) {
         boolean flag4;
         while(true) {
            boolean flag2 = i1 >= i;
            boolean flag3 = j1 >= j;
            if (flag2 && flag3) {
               this.f_166022_ = Math.max(1, l);
               return;
            }

            flag4 = !flag2 && (flag3 || p_83001_.getDouble(i1) < p_83002_.getDouble(j1) + 1.0E-7D);
            if (flag4) {
               ++i1;
               if (!flag || j1 != 0 && !flag3) {
                  break;
               }
            } else {
               ++j1;
               if (!flag1 || i1 != 0 && !flag2) {
                  break;
               }
            }
         }

         int k1 = i1 - 1;
         int l1 = j1 - 1;
         double d1 = flag4 ? p_83001_.getDouble(k1) : p_83002_.getDouble(l1);
         if (!(d0 >= d1 - 1.0E-7D)) {
            this.f_82998_[l] = k1;
            this.f_82999_[l] = l1;
            this.f_82997_[l] = d1;
            ++l;
            d0 = d1;
         } else {
            this.f_82998_[l - 1] = k1;
            this.f_82999_[l - 1] = l1;
         }
      }
   }

   public boolean m_6200_(IndexMerger.IndexConsumer p_83007_) {
      int i = this.f_166022_ - 1;

      for(int j = 0; j < i; ++j) {
         if (!p_83007_.m_82908_(this.f_82998_[j], this.f_82999_[j], j)) {
            return false;
         }
      }

      return true;
   }

   public int size() {
      return this.f_166022_;
   }

   public DoubleList m_6241_() {
      return (DoubleList)(this.f_166022_ <= 1 ? f_166021_ : DoubleArrayList.wrap(this.f_82997_, this.f_166022_));
   }
}