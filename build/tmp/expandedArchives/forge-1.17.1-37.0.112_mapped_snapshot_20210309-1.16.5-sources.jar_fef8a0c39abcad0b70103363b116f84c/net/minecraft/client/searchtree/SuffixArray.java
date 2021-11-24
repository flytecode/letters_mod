package net.minecraft.client.searchtree;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.Arrays;
import it.unimi.dsi.fastutil.Swapper;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@OnlyIn(Dist.CLIENT)
public class SuffixArray<T> {
   private static final boolean f_119957_ = Boolean.parseBoolean(System.getProperty("SuffixArray.printComparisons", "false"));
   private static final boolean f_119958_ = Boolean.parseBoolean(System.getProperty("SuffixArray.printArray", "false"));
   private static final Logger f_119959_ = LogManager.getLogger();
   private static final int f_174963_ = -1;
   private static final int f_174964_ = -2;
   protected final List<T> f_119956_ = Lists.newArrayList();
   private final IntList f_119960_ = new IntArrayList();
   private final IntList f_119961_ = new IntArrayList();
   private IntList f_119962_ = new IntArrayList();
   private IntList f_119963_ = new IntArrayList();
   private int f_119964_;

   public void m_119970_(T p_119971_, String p_119972_) {
      this.f_119964_ = Math.max(this.f_119964_, p_119972_.length());
      int i = this.f_119956_.size();
      this.f_119956_.add(p_119971_);
      this.f_119961_.add(this.f_119960_.size());

      for(int j = 0; j < p_119972_.length(); ++j) {
         this.f_119962_.add(i);
         this.f_119963_.add(j);
         this.f_119960_.add(p_119972_.charAt(j));
      }

      this.f_119962_.add(i);
      this.f_119963_.add(p_119972_.length());
      this.f_119960_.add(-1);
   }

   public void m_119967_() {
      int i = this.f_119960_.size();
      int[] aint = new int[i];
      final int[] aint1 = new int[i];
      final int[] aint2 = new int[i];
      int[] aint3 = new int[i];
      IntComparator intcomparator = new IntComparator() {
         public int compare(int p_119993_, int p_119994_) {
            return aint1[p_119993_] == aint1[p_119994_] ? Integer.compare(aint2[p_119993_], aint2[p_119994_]) : Integer.compare(aint1[p_119993_], aint1[p_119994_]);
         }

         public int compare(Integer p_119996_, Integer p_119997_) {
            return this.compare(p_119996_.intValue(), p_119997_.intValue());
         }
      };
      Swapper swapper = (p_119982_, p_119983_) -> {
         if (p_119982_ != p_119983_) {
            int i2 = aint1[p_119982_];
            aint1[p_119982_] = aint1[p_119983_];
            aint1[p_119983_] = i2;
            i2 = aint2[p_119982_];
            aint2[p_119982_] = aint2[p_119983_];
            aint2[p_119983_] = i2;
            i2 = aint3[p_119982_];
            aint3[p_119982_] = aint3[p_119983_];
            aint3[p_119983_] = i2;
         }

      };

      for(int j = 0; j < i; ++j) {
         aint[j] = this.f_119960_.getInt(j);
      }

      int k1 = 1;

      for(int k = Math.min(i, this.f_119964_); k1 * 2 < k; k1 *= 2) {
         for(int l = 0; l < i; aint3[l] = l++) {
            aint1[l] = aint[l];
            aint2[l] = l + k1 < i ? aint[l + k1] : -2;
         }

         Arrays.quickSort(0, i, intcomparator, swapper);

         for(int l1 = 0; l1 < i; ++l1) {
            if (l1 > 0 && aint1[l1] == aint1[l1 - 1] && aint2[l1] == aint2[l1 - 1]) {
               aint[aint3[l1]] = aint[aint3[l1 - 1]];
            } else {
               aint[aint3[l1]] = l1;
            }
         }
      }

      IntList intlist1 = this.f_119962_;
      IntList intlist = this.f_119963_;
      this.f_119962_ = new IntArrayList(intlist1.size());
      this.f_119963_ = new IntArrayList(intlist.size());

      for(int i1 = 0; i1 < i; ++i1) {
         int j1 = aint3[i1];
         this.f_119962_.add(intlist1.getInt(j1));
         this.f_119963_.add(intlist.getInt(j1));
      }

      if (f_119958_) {
         this.m_119984_();
      }

   }

   private void m_119984_() {
      for(int i = 0; i < this.f_119962_.size(); ++i) {
         f_119959_.debug("{} {}", i, this.m_119968_(i));
      }

      f_119959_.debug("");
   }

   private String m_119968_(int p_119969_) {
      int i = this.f_119963_.getInt(p_119969_);
      int j = this.f_119961_.getInt(this.f_119962_.getInt(p_119969_));
      StringBuilder stringbuilder = new StringBuilder();

      for(int k = 0; j + k < this.f_119960_.size(); ++k) {
         if (k == i) {
            stringbuilder.append('^');
         }

         int l = this.f_119960_.get(j + k);
         if (l == -1) {
            break;
         }

         stringbuilder.append((char)l);
      }

      return stringbuilder.toString();
   }

   private int m_119975_(String p_119976_, int p_119977_) {
      int i = this.f_119961_.getInt(this.f_119962_.getInt(p_119977_));
      int j = this.f_119963_.getInt(p_119977_);

      for(int k = 0; k < p_119976_.length(); ++k) {
         int l = this.f_119960_.getInt(i + j + k);
         if (l == -1) {
            return 1;
         }

         char c0 = p_119976_.charAt(k);
         char c1 = (char)l;
         if (c0 < c1) {
            return -1;
         }

         if (c0 > c1) {
            return 1;
         }
      }

      return 0;
   }

   public List<T> m_119973_(String p_119974_) {
      int i = this.f_119962_.size();
      int j = 0;
      int k = i;

      while(j < k) {
         int l = j + (k - j) / 2;
         int i1 = this.m_119975_(p_119974_, l);
         if (f_119957_) {
            f_119959_.debug("comparing lower \"{}\" with {} \"{}\": {}", p_119974_, l, this.m_119968_(l), i1);
         }

         if (i1 > 0) {
            j = l + 1;
         } else {
            k = l;
         }
      }

      if (j >= 0 && j < i) {
         int i2 = j;
         k = i;

         while(j < k) {
            int j2 = j + (k - j) / 2;
            int j1 = this.m_119975_(p_119974_, j2);
            if (f_119957_) {
               f_119959_.debug("comparing upper \"{}\" with {} \"{}\": {}", p_119974_, j2, this.m_119968_(j2), j1);
            }

            if (j1 >= 0) {
               j = j2 + 1;
            } else {
               k = j2;
            }
         }

         int k2 = j;
         IntSet intset = new IntOpenHashSet();

         for(int k1 = i2; k1 < k2; ++k1) {
            intset.add(this.f_119962_.getInt(k1));
         }

         int[] aint = intset.toIntArray();
         java.util.Arrays.sort(aint);
         Set<T> set = Sets.newLinkedHashSet();

         for(int l1 : aint) {
            set.add(this.f_119956_.get(l1));
         }

         return Lists.newArrayList(set);
      } else {
         return Collections.emptyList();
      }
   }
}