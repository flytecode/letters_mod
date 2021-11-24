package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.AbstractDoubleList;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public class NonOverlappingMerger extends AbstractDoubleList implements IndexMerger {
   private final DoubleList f_83008_;
   private final DoubleList f_83009_;
   private final boolean f_83010_;

   protected NonOverlappingMerger(DoubleList p_83012_, DoubleList p_83013_, boolean p_83014_) {
      this.f_83008_ = p_83012_;
      this.f_83009_ = p_83013_;
      this.f_83010_ = p_83014_;
   }

   public int size() {
      return this.f_83008_.size() + this.f_83009_.size();
   }

   public boolean m_6200_(IndexMerger.IndexConsumer p_83017_) {
      return this.f_83010_ ? this.m_83023_((p_83020_, p_83021_, p_83022_) -> {
         return p_83017_.m_82908_(p_83021_, p_83020_, p_83022_);
      }) : this.m_83023_(p_83017_);
   }

   private boolean m_83023_(IndexMerger.IndexConsumer p_83024_) {
      int i = this.f_83008_.size();

      for(int j = 0; j < i; ++j) {
         if (!p_83024_.m_82908_(j, -1, j)) {
            return false;
         }
      }

      int l = this.f_83009_.size() - 1;

      for(int k = 0; k < l; ++k) {
         if (!p_83024_.m_82908_(i - 1, k, i + k)) {
            return false;
         }
      }

      return true;
   }

   public double getDouble(int p_83026_) {
      return p_83026_ < this.f_83008_.size() ? this.f_83008_.getDouble(p_83026_) : this.f_83009_.getDouble(p_83026_ - this.f_83008_.size());
   }

   public DoubleList m_6241_() {
      return this;
   }
}