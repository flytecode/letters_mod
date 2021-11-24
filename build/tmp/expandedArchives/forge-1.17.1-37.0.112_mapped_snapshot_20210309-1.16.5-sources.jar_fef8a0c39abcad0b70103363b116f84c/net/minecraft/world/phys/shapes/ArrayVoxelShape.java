package net.minecraft.world.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import net.minecraft.Util;
import net.minecraft.core.Direction;

public class ArrayVoxelShape extends VoxelShape {
   private final DoubleList f_82563_;
   private final DoubleList f_82564_;
   private final DoubleList f_82565_;

   protected ArrayVoxelShape(DiscreteVoxelShape p_82572_, double[] p_82573_, double[] p_82574_, double[] p_82575_) {
      this(p_82572_, (DoubleList)DoubleArrayList.wrap(Arrays.copyOf(p_82573_, p_82572_.m_82828_() + 1)), (DoubleList)DoubleArrayList.wrap(Arrays.copyOf(p_82574_, p_82572_.m_82845_() + 1)), (DoubleList)DoubleArrayList.wrap(Arrays.copyOf(p_82575_, p_82572_.m_82852_() + 1)));
   }

   ArrayVoxelShape(DiscreteVoxelShape p_82567_, DoubleList p_82568_, DoubleList p_82569_, DoubleList p_82570_) {
      super(p_82567_);
      int i = p_82567_.m_82828_() + 1;
      int j = p_82567_.m_82845_() + 1;
      int k = p_82567_.m_82852_() + 1;
      if (i == p_82568_.size() && j == p_82569_.size() && k == p_82570_.size()) {
         this.f_82563_ = p_82568_;
         this.f_82564_ = p_82569_;
         this.f_82565_ = p_82570_;
      } else {
         throw (IllegalArgumentException)Util.m_137570_(new IllegalArgumentException("Lengths of point arrays must be consistent with the size of the VoxelShape."));
      }
   }

   protected DoubleList m_7700_(Direction.Axis p_82577_) {
      switch(p_82577_) {
      case X:
         return this.f_82563_;
      case Y:
         return this.f_82564_;
      case Z:
         return this.f_82565_;
      default:
         throw new IllegalArgumentException();
      }
   }
}