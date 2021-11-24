package net.minecraft.world.phys.shapes;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.math.DoubleMath;
import com.google.common.math.IntMath;
import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.Util;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public final class Shapes {
   public static final double f_166025_ = 1.0E-7D;
   public static final double f_166026_ = 1.0E-6D;
   private static final VoxelShape f_83037_ = Util.m_137537_(() -> {
      DiscreteVoxelShape discretevoxelshape = new BitSetDiscreteVoxelShape(1, 1, 1);
      discretevoxelshape.m_142703_(0, 0, 0);
      return new CubeVoxelShape(discretevoxelshape);
   });
   public static final VoxelShape f_83036_ = m_83048_(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
   private static final VoxelShape f_83038_ = new ArrayVoxelShape(new BitSetDiscreteVoxelShape(0, 0, 0), (DoubleList)(new DoubleArrayList(new double[]{0.0D})), (DoubleList)(new DoubleArrayList(new double[]{0.0D})), (DoubleList)(new DoubleArrayList(new double[]{0.0D})));

   public static VoxelShape m_83040_() {
      return f_83038_;
   }

   public static VoxelShape m_83144_() {
      return f_83037_;
   }

   public static VoxelShape m_83048_(double p_83049_, double p_83050_, double p_83051_, double p_83052_, double p_83053_, double p_83054_) {
      if (!(p_83049_ > p_83052_) && !(p_83050_ > p_83053_) && !(p_83051_ > p_83054_)) {
         return m_166049_(p_83049_, p_83050_, p_83051_, p_83052_, p_83053_, p_83054_);
      } else {
         throw new IllegalArgumentException("The min values need to be smaller or equals to the max values");
      }
   }

   public static VoxelShape m_166049_(double p_166050_, double p_166051_, double p_166052_, double p_166053_, double p_166054_, double p_166055_) {
      if (!(p_166053_ - p_166050_ < 1.0E-7D) && !(p_166054_ - p_166051_ < 1.0E-7D) && !(p_166055_ - p_166052_ < 1.0E-7D)) {
         int i = m_83041_(p_166050_, p_166053_);
         int j = m_83041_(p_166051_, p_166054_);
         int k = m_83041_(p_166052_, p_166055_);
         if (i >= 0 && j >= 0 && k >= 0) {
            if (i == 0 && j == 0 && k == 0) {
               return m_83144_();
            } else {
               int l = 1 << i;
               int i1 = 1 << j;
               int j1 = 1 << k;
               BitSetDiscreteVoxelShape bitsetdiscretevoxelshape = BitSetDiscreteVoxelShape.m_165932_(l, i1, j1, (int)Math.round(p_166050_ * (double)l), (int)Math.round(p_166051_ * (double)i1), (int)Math.round(p_166052_ * (double)j1), (int)Math.round(p_166053_ * (double)l), (int)Math.round(p_166054_ * (double)i1), (int)Math.round(p_166055_ * (double)j1));
               return new CubeVoxelShape(bitsetdiscretevoxelshape);
            }
         } else {
            return new ArrayVoxelShape(f_83037_.f_83211_, (DoubleList)DoubleArrayList.wrap(new double[]{p_166050_, p_166053_}), (DoubleList)DoubleArrayList.wrap(new double[]{p_166051_, p_166054_}), (DoubleList)DoubleArrayList.wrap(new double[]{p_166052_, p_166055_}));
         }
      } else {
         return m_83040_();
      }
   }

   public static VoxelShape m_83064_(AABB p_83065_) {
      return m_166049_(p_83065_.f_82288_, p_83065_.f_82289_, p_83065_.f_82290_, p_83065_.f_82291_, p_83065_.f_82292_, p_83065_.f_82293_);
   }

   @VisibleForTesting
   protected static int m_83041_(double p_83042_, double p_83043_) {
      if (!(p_83042_ < -1.0E-7D) && !(p_83043_ > 1.0000001D)) {
         for(int i = 0; i <= 3; ++i) {
            int j = 1 << i;
            double d0 = p_83042_ * (double)j;
            double d1 = p_83043_ * (double)j;
            boolean flag = Math.abs(d0 - (double)Math.round(d0)) < 1.0E-7D * (double)j;
            boolean flag1 = Math.abs(d1 - (double)Math.round(d1)) < 1.0E-7D * (double)j;
            if (flag && flag1) {
               return i;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   protected static long m_83055_(int p_83056_, int p_83057_) {
      return (long)p_83056_ * (long)(p_83057_ / IntMath.gcd(p_83056_, p_83057_));
   }

   public static VoxelShape m_83110_(VoxelShape p_83111_, VoxelShape p_83112_) {
      return m_83113_(p_83111_, p_83112_, BooleanOp.f_82695_);
   }

   public static VoxelShape m_83124_(VoxelShape p_83125_, VoxelShape... p_83126_) {
      return Arrays.stream(p_83126_).reduce(p_83125_, Shapes::m_83110_);
   }

   public static VoxelShape m_83113_(VoxelShape p_83114_, VoxelShape p_83115_, BooleanOp p_83116_) {
      return m_83148_(p_83114_, p_83115_, p_83116_).m_83296_();
   }

   public static VoxelShape m_83148_(VoxelShape p_83149_, VoxelShape p_83150_, BooleanOp p_83151_) {
      if (p_83151_.m_82701_(false, false)) {
         throw (IllegalArgumentException)Util.m_137570_(new IllegalArgumentException());
      } else if (p_83149_ == p_83150_) {
         return p_83151_.m_82701_(true, true) ? p_83149_ : m_83040_();
      } else {
         boolean flag = p_83151_.m_82701_(true, false);
         boolean flag1 = p_83151_.m_82701_(false, true);
         if (p_83149_.m_83281_()) {
            return flag1 ? p_83150_ : m_83040_();
         } else if (p_83150_.m_83281_()) {
            return flag ? p_83149_ : m_83040_();
         } else {
            IndexMerger indexmerger = m_83058_(1, p_83149_.m_7700_(Direction.Axis.X), p_83150_.m_7700_(Direction.Axis.X), flag, flag1);
            IndexMerger indexmerger1 = m_83058_(indexmerger.size() - 1, p_83149_.m_7700_(Direction.Axis.Y), p_83150_.m_7700_(Direction.Axis.Y), flag, flag1);
            IndexMerger indexmerger2 = m_83058_((indexmerger.size() - 1) * (indexmerger1.size() - 1), p_83149_.m_7700_(Direction.Axis.Z), p_83150_.m_7700_(Direction.Axis.Z), flag, flag1);
            BitSetDiscreteVoxelShape bitsetdiscretevoxelshape = BitSetDiscreteVoxelShape.m_82641_(p_83149_.f_83211_, p_83150_.f_83211_, indexmerger, indexmerger1, indexmerger2, p_83151_);
            return (VoxelShape)(indexmerger instanceof DiscreteCubeMerger && indexmerger1 instanceof DiscreteCubeMerger && indexmerger2 instanceof DiscreteCubeMerger ? new CubeVoxelShape(bitsetdiscretevoxelshape) : new ArrayVoxelShape(bitsetdiscretevoxelshape, indexmerger.m_6241_(), indexmerger1.m_6241_(), indexmerger2.m_6241_()));
         }
      }
   }

   public static boolean m_83157_(VoxelShape p_83158_, VoxelShape p_83159_, BooleanOp p_83160_) {
      if (p_83160_.m_82701_(false, false)) {
         throw (IllegalArgumentException)Util.m_137570_(new IllegalArgumentException());
      } else {
         boolean flag = p_83158_.m_83281_();
         boolean flag1 = p_83159_.m_83281_();
         if (!flag && !flag1) {
            if (p_83158_ == p_83159_) {
               return p_83160_.m_82701_(true, true);
            } else {
               boolean flag2 = p_83160_.m_82701_(true, false);
               boolean flag3 = p_83160_.m_82701_(false, true);

               for(Direction.Axis direction$axis : AxisCycle.f_121783_) {
                  if (p_83158_.m_83297_(direction$axis) < p_83159_.m_83288_(direction$axis) - 1.0E-7D) {
                     return flag2 || flag3;
                  }

                  if (p_83159_.m_83297_(direction$axis) < p_83158_.m_83288_(direction$axis) - 1.0E-7D) {
                     return flag2 || flag3;
                  }
               }

               IndexMerger indexmerger = m_83058_(1, p_83158_.m_7700_(Direction.Axis.X), p_83159_.m_7700_(Direction.Axis.X), flag2, flag3);
               IndexMerger indexmerger1 = m_83058_(indexmerger.size() - 1, p_83158_.m_7700_(Direction.Axis.Y), p_83159_.m_7700_(Direction.Axis.Y), flag2, flag3);
               IndexMerger indexmerger2 = m_83058_((indexmerger.size() - 1) * (indexmerger1.size() - 1), p_83158_.m_7700_(Direction.Axis.Z), p_83159_.m_7700_(Direction.Axis.Z), flag2, flag3);
               return m_83103_(indexmerger, indexmerger1, indexmerger2, p_83158_.f_83211_, p_83159_.f_83211_, p_83160_);
            }
         } else {
            return p_83160_.m_82701_(!flag, !flag1);
         }
      }
   }

   private static boolean m_83103_(IndexMerger p_83104_, IndexMerger p_83105_, IndexMerger p_83106_, DiscreteVoxelShape p_83107_, DiscreteVoxelShape p_83108_, BooleanOp p_83109_) {
      return !p_83104_.m_6200_((p_83100_, p_83101_, p_83102_) -> {
         return p_83105_.m_6200_((p_166046_, p_166047_, p_166048_) -> {
            return p_83106_.m_6200_((p_166036_, p_166037_, p_166038_) -> {
               return !p_83109_.m_82701_(p_83107_.m_82846_(p_83100_, p_166046_, p_166036_), p_83108_.m_82846_(p_83101_, p_166047_, p_166037_));
            });
         });
      });
   }

   public static double m_83134_(Direction.Axis p_83135_, AABB p_83136_, Stream<VoxelShape> p_83137_, double p_83138_) {
      for(Iterator<VoxelShape> iterator = p_83137_.iterator(); iterator.hasNext(); p_83138_ = iterator.next().m_83259_(p_83135_, p_83136_, p_83138_)) {
         if (Math.abs(p_83138_) < 1.0E-7D) {
            return 0.0D;
         }
      }

      return p_83138_;
   }

   public static double m_83127_(Direction.Axis p_83128_, AABB p_83129_, LevelReader p_83130_, double p_83131_, CollisionContext p_83132_, Stream<VoxelShape> p_83133_) {
      return m_83066_(p_83129_, p_83130_, p_83131_, p_83132_, AxisCycle.m_121799_(p_83128_, Direction.Axis.Z), p_83133_);
   }

   private static double m_83066_(AABB p_83067_, LevelReader p_83068_, double p_83069_, CollisionContext p_83070_, AxisCycle p_83071_, Stream<VoxelShape> p_83072_) {
      if (!(p_83067_.m_82362_() < 1.0E-6D) && !(p_83067_.m_82376_() < 1.0E-6D) && !(p_83067_.m_82385_() < 1.0E-6D)) {
         if (Math.abs(p_83069_) < 1.0E-7D) {
            return 0.0D;
         } else {
            AxisCycle axiscycle = p_83071_.m_7634_();
            Direction.Axis direction$axis = axiscycle.m_7314_(Direction.Axis.X);
            Direction.Axis direction$axis1 = axiscycle.m_7314_(Direction.Axis.Y);
            Direction.Axis direction$axis2 = axiscycle.m_7314_(Direction.Axis.Z);
            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
            int i = Mth.m_14107_(p_83067_.m_82340_(direction$axis) - 1.0E-7D) - 1;
            int j = Mth.m_14107_(p_83067_.m_82374_(direction$axis) + 1.0E-7D) + 1;
            int k = Mth.m_14107_(p_83067_.m_82340_(direction$axis1) - 1.0E-7D) - 1;
            int l = Mth.m_14107_(p_83067_.m_82374_(direction$axis1) + 1.0E-7D) + 1;
            double d0 = p_83067_.m_82340_(direction$axis2) - 1.0E-7D;
            double d1 = p_83067_.m_82374_(direction$axis2) + 1.0E-7D;
            boolean flag = p_83069_ > 0.0D;
            int i1 = flag ? Mth.m_14107_(p_83067_.m_82374_(direction$axis2) - 1.0E-7D) - 1 : Mth.m_14107_(p_83067_.m_82340_(direction$axis2) + 1.0E-7D) + 1;
            int j1 = m_83044_(p_83069_, d0, d1);
            int k1 = flag ? 1 : -1;
            int l1 = i1;

            while(true) {
               if (flag) {
                  if (l1 > j1) {
                     break;
                  }
               } else if (l1 < j1) {
                  break;
               }

               for(int i2 = i; i2 <= j; ++i2) {
                  for(int j2 = k; j2 <= l; ++j2) {
                     int k2 = 0;
                     if (i2 == i || i2 == j) {
                        ++k2;
                     }

                     if (j2 == k || j2 == l) {
                        ++k2;
                     }

                     if (l1 == i1 || l1 == j1) {
                        ++k2;
                     }

                     if (k2 < 3) {
                        blockpos$mutableblockpos.m_122139_(axiscycle, i2, j2, l1);
                        BlockState blockstate = p_83068_.m_8055_(blockpos$mutableblockpos);
                        if ((k2 != 1 || blockstate.m_60779_()) && (k2 != 2 || blockstate.m_60713_(Blocks.f_50110_))) {
                           p_83069_ = blockstate.m_60742_(p_83068_, blockpos$mutableblockpos, p_83070_).m_83259_(direction$axis2, p_83067_.m_82386_((double)(-blockpos$mutableblockpos.m_123341_()), (double)(-blockpos$mutableblockpos.m_123342_()), (double)(-blockpos$mutableblockpos.m_123343_())), p_83069_);
                           if (Math.abs(p_83069_) < 1.0E-7D) {
                              return 0.0D;
                           }

                           j1 = m_83044_(p_83069_, d0, d1);
                        }
                     }
                  }
               }

               l1 += k1;
            }

            double[] adouble = new double[]{p_83069_};
            p_83072_.forEach((p_83143_) -> {
               adouble[0] = p_83143_.m_83259_(direction$axis2, p_83067_, adouble[0]);
            });
            return adouble[0];
         }
      } else {
         return p_83069_;
      }
   }

   private static int m_83044_(double p_83045_, double p_83046_, double p_83047_) {
      return p_83045_ > 0.0D ? Mth.m_14107_(p_83047_ + p_83045_) + 1 : Mth.m_14107_(p_83046_ + p_83045_) - 1;
   }

   public static boolean m_83117_(VoxelShape p_83118_, VoxelShape p_83119_, Direction p_83120_) {
      if (p_83118_ == m_83144_() && p_83119_ == m_83144_()) {
         return true;
      } else if (p_83119_.m_83281_()) {
         return false;
      } else {
         Direction.Axis direction$axis = p_83120_.m_122434_();
         Direction.AxisDirection direction$axisdirection = p_83120_.m_122421_();
         VoxelShape voxelshape = direction$axisdirection == Direction.AxisDirection.POSITIVE ? p_83118_ : p_83119_;
         VoxelShape voxelshape1 = direction$axisdirection == Direction.AxisDirection.POSITIVE ? p_83119_ : p_83118_;
         BooleanOp booleanop = direction$axisdirection == Direction.AxisDirection.POSITIVE ? BooleanOp.f_82685_ : BooleanOp.f_82683_;
         return DoubleMath.fuzzyEquals(voxelshape.m_83297_(direction$axis), 1.0D, 1.0E-7D) && DoubleMath.fuzzyEquals(voxelshape1.m_83288_(direction$axis), 0.0D, 1.0E-7D) && !m_83157_(new SliceShape(voxelshape, direction$axis, voxelshape.f_83211_.m_82850_(direction$axis) - 1), new SliceShape(voxelshape1, direction$axis, 0), booleanop);
      }
   }

   public static VoxelShape m_83121_(VoxelShape p_83122_, Direction p_83123_) {
      if (p_83122_ == m_83144_()) {
         return m_83144_();
      } else {
         Direction.Axis direction$axis = p_83123_.m_122434_();
         boolean flag;
         int i;
         if (p_83123_.m_122421_() == Direction.AxisDirection.POSITIVE) {
            flag = DoubleMath.fuzzyEquals(p_83122_.m_83297_(direction$axis), 1.0D, 1.0E-7D);
            i = p_83122_.f_83211_.m_82850_(direction$axis) - 1;
         } else {
            flag = DoubleMath.fuzzyEquals(p_83122_.m_83288_(direction$axis), 0.0D, 1.0E-7D);
            i = 0;
         }

         return (VoxelShape)(!flag ? m_83040_() : new SliceShape(p_83122_, direction$axis, i));
      }
   }

   public static boolean m_83152_(VoxelShape p_83153_, VoxelShape p_83154_, Direction p_83155_) {
      if (p_83153_ != m_83144_() && p_83154_ != m_83144_()) {
         Direction.Axis direction$axis = p_83155_.m_122434_();
         Direction.AxisDirection direction$axisdirection = p_83155_.m_122421_();
         VoxelShape voxelshape = direction$axisdirection == Direction.AxisDirection.POSITIVE ? p_83153_ : p_83154_;
         VoxelShape voxelshape1 = direction$axisdirection == Direction.AxisDirection.POSITIVE ? p_83154_ : p_83153_;
         if (!DoubleMath.fuzzyEquals(voxelshape.m_83297_(direction$axis), 1.0D, 1.0E-7D)) {
            voxelshape = m_83040_();
         }

         if (!DoubleMath.fuzzyEquals(voxelshape1.m_83288_(direction$axis), 0.0D, 1.0E-7D)) {
            voxelshape1 = m_83040_();
         }

         return !m_83157_(m_83144_(), m_83148_(new SliceShape(voxelshape, direction$axis, voxelshape.f_83211_.m_82850_(direction$axis) - 1), new SliceShape(voxelshape1, direction$axis, 0), BooleanOp.f_82695_), BooleanOp.f_82685_);
      } else {
         return true;
      }
   }

   public static boolean m_83145_(VoxelShape p_83146_, VoxelShape p_83147_) {
      if (p_83146_ != m_83144_() && p_83147_ != m_83144_()) {
         if (p_83146_.m_83281_() && p_83147_.m_83281_()) {
            return false;
         } else {
            return !m_83157_(m_83144_(), m_83148_(p_83146_, p_83147_, BooleanOp.f_82695_), BooleanOp.f_82685_);
         }
      } else {
         return true;
      }
   }

   @VisibleForTesting
   protected static IndexMerger m_83058_(int p_83059_, DoubleList p_83060_, DoubleList p_83061_, boolean p_83062_, boolean p_83063_) {
      int i = p_83060_.size() - 1;
      int j = p_83061_.size() - 1;
      if (p_83060_ instanceof CubePointRange && p_83061_ instanceof CubePointRange) {
         long k = m_83055_(i, j);
         if ((long)p_83059_ * k <= 256L) {
            return new DiscreteCubeMerger(i, j);
         }
      }

      if (p_83060_.getDouble(i) < p_83061_.getDouble(0) - 1.0E-7D) {
         return new NonOverlappingMerger(p_83060_, p_83061_, false);
      } else if (p_83061_.getDouble(j) < p_83060_.getDouble(0) - 1.0E-7D) {
         return new NonOverlappingMerger(p_83061_, p_83060_, true);
      } else {
         return (IndexMerger)(i == j && Objects.equals(p_83060_, p_83061_) ? new IdenticalMerger(p_83060_) : new IndirectMerger(p_83060_, p_83061_, p_83062_, p_83063_));
      }
   }

   public interface DoubleLineConsumer {
      void m_83161_(double p_83162_, double p_83163_, double p_83164_, double p_83165_, double p_83166_, double p_83167_);
   }
}