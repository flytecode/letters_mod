package net.minecraft.world.phys.shapes;

import net.minecraft.core.AxisCycle;
import net.minecraft.core.Direction;

public abstract class DiscreteVoxelShape {
   private static final Direction.Axis[] f_82784_ = Direction.Axis.values();
   protected final int f_82781_;
   protected final int f_82782_;
   protected final int f_82783_;

   protected DiscreteVoxelShape(int p_82787_, int p_82788_, int p_82789_) {
      if (p_82787_ >= 0 && p_82788_ >= 0 && p_82789_ >= 0) {
         this.f_82781_ = p_82787_;
         this.f_82782_ = p_82788_;
         this.f_82783_ = p_82789_;
      } else {
         throw new IllegalArgumentException("Need all positive sizes: x: " + p_82787_ + ", y: " + p_82788_ + ", z: " + p_82789_);
      }
   }

   public boolean m_82822_(AxisCycle p_82823_, int p_82824_, int p_82825_, int p_82826_) {
      return this.m_82846_(p_82823_.m_7758_(p_82824_, p_82825_, p_82826_, Direction.Axis.X), p_82823_.m_7758_(p_82824_, p_82825_, p_82826_, Direction.Axis.Y), p_82823_.m_7758_(p_82824_, p_82825_, p_82826_, Direction.Axis.Z));
   }

   public boolean m_82846_(int p_82847_, int p_82848_, int p_82849_) {
      if (p_82847_ >= 0 && p_82848_ >= 0 && p_82849_ >= 0) {
         return p_82847_ < this.f_82781_ && p_82848_ < this.f_82782_ && p_82849_ < this.f_82783_ ? this.m_6696_(p_82847_, p_82848_, p_82849_) : false;
      } else {
         return false;
      }
   }

   public boolean m_82835_(AxisCycle p_82836_, int p_82837_, int p_82838_, int p_82839_) {
      return this.m_6696_(p_82836_.m_7758_(p_82837_, p_82838_, p_82839_, Direction.Axis.X), p_82836_.m_7758_(p_82837_, p_82838_, p_82839_, Direction.Axis.Y), p_82836_.m_7758_(p_82837_, p_82838_, p_82839_, Direction.Axis.Z));
   }

   public abstract boolean m_6696_(int p_82829_, int p_82830_, int p_82831_);

   public abstract void m_142703_(int p_165998_, int p_165999_, int p_166000_);

   public boolean m_6224_() {
      for(Direction.Axis direction$axis : f_82784_) {
         if (this.m_6538_(direction$axis) >= this.m_6536_(direction$axis)) {
            return true;
         }
      }

      return false;
   }

   public abstract int m_6538_(Direction.Axis p_82827_);

   public abstract int m_6536_(Direction.Axis p_82840_);

   public int m_165994_(Direction.Axis p_165995_, int p_165996_, int p_165997_) {
      int i = this.m_82850_(p_165995_);
      if (p_165996_ >= 0 && p_165997_ >= 0) {
         Direction.Axis direction$axis = AxisCycle.FORWARD.m_7314_(p_165995_);
         Direction.Axis direction$axis1 = AxisCycle.BACKWARD.m_7314_(p_165995_);
         if (p_165996_ < this.m_82850_(direction$axis) && p_165997_ < this.m_82850_(direction$axis1)) {
            AxisCycle axiscycle = AxisCycle.m_121799_(Direction.Axis.X, p_165995_);

            for(int j = 0; j < i; ++j) {
               if (this.m_82835_(axiscycle, j, p_165996_, p_165997_)) {
                  return j;
               }
            }

            return i;
         } else {
            return i;
         }
      } else {
         return i;
      }
   }

   public int m_82841_(Direction.Axis p_82842_, int p_82843_, int p_82844_) {
      if (p_82843_ >= 0 && p_82844_ >= 0) {
         Direction.Axis direction$axis = AxisCycle.FORWARD.m_7314_(p_82842_);
         Direction.Axis direction$axis1 = AxisCycle.BACKWARD.m_7314_(p_82842_);
         if (p_82843_ < this.m_82850_(direction$axis) && p_82844_ < this.m_82850_(direction$axis1)) {
            int i = this.m_82850_(p_82842_);
            AxisCycle axiscycle = AxisCycle.m_121799_(Direction.Axis.X, p_82842_);

            for(int j = i - 1; j >= 0; --j) {
               if (this.m_82835_(axiscycle, j, p_82843_, p_82844_)) {
                  return j + 1;
               }
            }

            return 0;
         } else {
            return 0;
         }
      } else {
         return 0;
      }
   }

   public int m_82850_(Direction.Axis p_82851_) {
      return p_82851_.m_7863_(this.f_82781_, this.f_82782_, this.f_82783_);
   }

   public int m_82828_() {
      return this.m_82850_(Direction.Axis.X);
   }

   public int m_82845_() {
      return this.m_82850_(Direction.Axis.Y);
   }

   public int m_82852_() {
      return this.m_82850_(Direction.Axis.Z);
   }

   public void m_82819_(DiscreteVoxelShape.IntLineConsumer p_82820_, boolean p_82821_) {
      this.m_82815_(p_82820_, AxisCycle.NONE, p_82821_);
      this.m_82815_(p_82820_, AxisCycle.FORWARD, p_82821_);
      this.m_82815_(p_82820_, AxisCycle.BACKWARD, p_82821_);
   }

   private void m_82815_(DiscreteVoxelShape.IntLineConsumer p_82816_, AxisCycle p_82817_, boolean p_82818_) {
      AxisCycle axiscycle = p_82817_.m_7634_();
      int j = this.m_82850_(axiscycle.m_7314_(Direction.Axis.X));
      int k = this.m_82850_(axiscycle.m_7314_(Direction.Axis.Y));
      int l = this.m_82850_(axiscycle.m_7314_(Direction.Axis.Z));

      for(int i1 = 0; i1 <= j; ++i1) {
         for(int j1 = 0; j1 <= k; ++j1) {
            int i = -1;

            for(int k1 = 0; k1 <= l; ++k1) {
               int l1 = 0;
               int i2 = 0;

               for(int j2 = 0; j2 <= 1; ++j2) {
                  for(int k2 = 0; k2 <= 1; ++k2) {
                     if (this.m_82822_(axiscycle, i1 + j2 - 1, j1 + k2 - 1, k1)) {
                        ++l1;
                        i2 ^= j2 ^ k2;
                     }
                  }
               }

               if (l1 == 1 || l1 == 3 || l1 == 2 && (i2 & 1) == 0) {
                  if (p_82818_) {
                     if (i == -1) {
                        i = k1;
                     }
                  } else {
                     p_82816_.m_82858_(axiscycle.m_7758_(i1, j1, k1, Direction.Axis.X), axiscycle.m_7758_(i1, j1, k1, Direction.Axis.Y), axiscycle.m_7758_(i1, j1, k1, Direction.Axis.Z), axiscycle.m_7758_(i1, j1, k1 + 1, Direction.Axis.X), axiscycle.m_7758_(i1, j1, k1 + 1, Direction.Axis.Y), axiscycle.m_7758_(i1, j1, k1 + 1, Direction.Axis.Z));
                  }
               } else if (i != -1) {
                  p_82816_.m_82858_(axiscycle.m_7758_(i1, j1, i, Direction.Axis.X), axiscycle.m_7758_(i1, j1, i, Direction.Axis.Y), axiscycle.m_7758_(i1, j1, i, Direction.Axis.Z), axiscycle.m_7758_(i1, j1, k1, Direction.Axis.X), axiscycle.m_7758_(i1, j1, k1, Direction.Axis.Y), axiscycle.m_7758_(i1, j1, k1, Direction.Axis.Z));
                  i = -1;
               }
            }
         }
      }

   }

   public void m_82832_(DiscreteVoxelShape.IntLineConsumer p_82833_, boolean p_82834_) {
      BitSetDiscreteVoxelShape.m_165963_(this, p_82833_, p_82834_);
   }

   public void m_82810_(DiscreteVoxelShape.IntFaceConsumer p_82811_) {
      this.m_82812_(p_82811_, AxisCycle.NONE);
      this.m_82812_(p_82811_, AxisCycle.FORWARD);
      this.m_82812_(p_82811_, AxisCycle.BACKWARD);
   }

   private void m_82812_(DiscreteVoxelShape.IntFaceConsumer p_82813_, AxisCycle p_82814_) {
      AxisCycle axiscycle = p_82814_.m_7634_();
      Direction.Axis direction$axis = axiscycle.m_7314_(Direction.Axis.Z);
      int i = this.m_82850_(axiscycle.m_7314_(Direction.Axis.X));
      int j = this.m_82850_(axiscycle.m_7314_(Direction.Axis.Y));
      int k = this.m_82850_(direction$axis);
      Direction direction = Direction.m_122387_(direction$axis, Direction.AxisDirection.NEGATIVE);
      Direction direction1 = Direction.m_122387_(direction$axis, Direction.AxisDirection.POSITIVE);

      for(int l = 0; l < i; ++l) {
         for(int i1 = 0; i1 < j; ++i1) {
            boolean flag = false;

            for(int j1 = 0; j1 <= k; ++j1) {
               boolean flag1 = j1 != k && this.m_82835_(axiscycle, l, i1, j1);
               if (!flag && flag1) {
                  p_82813_.m_82853_(direction, axiscycle.m_7758_(l, i1, j1, Direction.Axis.X), axiscycle.m_7758_(l, i1, j1, Direction.Axis.Y), axiscycle.m_7758_(l, i1, j1, Direction.Axis.Z));
               }

               if (flag && !flag1) {
                  p_82813_.m_82853_(direction1, axiscycle.m_7758_(l, i1, j1 - 1, Direction.Axis.X), axiscycle.m_7758_(l, i1, j1 - 1, Direction.Axis.Y), axiscycle.m_7758_(l, i1, j1 - 1, Direction.Axis.Z));
               }

               flag = flag1;
            }
         }
      }

   }

   public interface IntFaceConsumer {
      void m_82853_(Direction p_82854_, int p_82855_, int p_82856_, int p_82857_);
   }

   public interface IntLineConsumer {
      void m_82858_(int p_82859_, int p_82860_, int p_82861_, int p_82862_, int p_82863_, int p_82864_);
   }
}