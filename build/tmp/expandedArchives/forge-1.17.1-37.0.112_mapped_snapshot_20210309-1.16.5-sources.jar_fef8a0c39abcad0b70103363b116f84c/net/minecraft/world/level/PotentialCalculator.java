package net.minecraft.world.level;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.core.BlockPos;

public class PotentialCalculator {
   private final List<PotentialCalculator.PointCharge> f_47190_ = Lists.newArrayList();

   public void m_47192_(BlockPos p_47193_, double p_47194_) {
      if (p_47194_ != 0.0D) {
         this.f_47190_.add(new PotentialCalculator.PointCharge(p_47193_, p_47194_));
      }

   }

   public double m_47195_(BlockPos p_47196_, double p_47197_) {
      if (p_47197_ == 0.0D) {
         return 0.0D;
      } else {
         double d0 = 0.0D;

         for(PotentialCalculator.PointCharge potentialcalculator$pointcharge : this.f_47190_) {
            d0 += potentialcalculator$pointcharge.m_47203_(p_47196_);
         }

         return d0 * p_47197_;
      }
   }

   static class PointCharge {
      private final BlockPos f_47198_;
      private final double f_47199_;

      public PointCharge(BlockPos p_47201_, double p_47202_) {
         this.f_47198_ = p_47201_;
         this.f_47199_ = p_47202_;
      }

      public double m_47203_(BlockPos p_47204_) {
         double d0 = this.f_47198_.m_123331_(p_47204_);
         return d0 == 0.0D ? Double.POSITIVE_INFINITY : this.f_47199_ / Math.sqrt(d0);
      }
   }
}