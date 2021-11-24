package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleList;

public class IdenticalMerger implements IndexMerger {
   private final DoubleList f_82901_;

   public IdenticalMerger(DoubleList p_82903_) {
      this.f_82901_ = p_82903_;
   }

   public boolean m_6200_(IndexMerger.IndexConsumer p_82906_) {
      int i = this.f_82901_.size() - 1;

      for(int j = 0; j < i; ++j) {
         if (!p_82906_.m_82908_(j, j, j)) {
            return false;
         }
      }

      return true;
   }

   public int size() {
      return this.f_82901_.size();
   }

   public DoubleList m_6241_() {
      return this.f_82901_;
   }
}