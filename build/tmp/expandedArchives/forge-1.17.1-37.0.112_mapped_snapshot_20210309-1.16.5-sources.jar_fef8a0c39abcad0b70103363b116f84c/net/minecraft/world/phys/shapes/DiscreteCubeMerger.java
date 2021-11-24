package net.minecraft.world.phys.shapes;

import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;

public final class DiscreteCubeMerger implements IndexMerger {
   private final CubePointRange f_82771_;
   private final int f_165991_;
   private final int f_165992_;

   DiscreteCubeMerger(int p_82776_, int p_82777_) {
      this.f_82771_ = new CubePointRange((int)Shapes.m_83055_(p_82776_, p_82777_));
      int i = IntMath.gcd(p_82776_, p_82777_);
      this.f_165991_ = p_82776_ / i;
      this.f_165992_ = p_82777_ / i;
   }

   public boolean m_6200_(IndexMerger.IndexConsumer p_82780_) {
      int i = this.f_82771_.size() - 1;

      for(int j = 0; j < i; ++j) {
         if (!p_82780_.m_82908_(j / this.f_165992_, j / this.f_165991_, j)) {
            return false;
         }
      }

      return true;
   }

   public int size() {
      return this.f_82771_.size();
   }

   public DoubleList m_6241_() {
      return this.f_82771_;
   }
}