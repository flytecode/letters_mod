package net.minecraft.world.phys.shapes;

import com.google.common.collect.Lists;
import com.google.common.math.DoubleMath;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.Util;
import net.minecraft.core.AxisCycle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public abstract class VoxelShape {
   protected final DiscreteVoxelShape f_83211_;
   @Nullable
   private VoxelShape[] f_83212_;

   VoxelShape(DiscreteVoxelShape p_83214_) {
      this.f_83211_ = p_83214_;
   }

   public double m_83288_(Direction.Axis p_83289_) {
      int i = this.f_83211_.m_6538_(p_83289_);
      return i >= this.f_83211_.m_82850_(p_83289_) ? Double.POSITIVE_INFINITY : this.m_83256_(p_83289_, i);
   }

   public double m_83297_(Direction.Axis p_83298_) {
      int i = this.f_83211_.m_6536_(p_83298_);
      return i <= 0 ? Double.NEGATIVE_INFINITY : this.m_83256_(p_83298_, i);
   }

   public AABB m_83215_() {
      if (this.m_83281_()) {
         throw (UnsupportedOperationException)Util.m_137570_(new UnsupportedOperationException("No bounds for empty shape."));
      } else {
         return new AABB(this.m_83288_(Direction.Axis.X), this.m_83288_(Direction.Axis.Y), this.m_83288_(Direction.Axis.Z), this.m_83297_(Direction.Axis.X), this.m_83297_(Direction.Axis.Y), this.m_83297_(Direction.Axis.Z));
      }
   }

   protected double m_83256_(Direction.Axis p_83257_, int p_83258_) {
      return this.m_7700_(p_83257_).getDouble(p_83258_);
   }

   protected abstract DoubleList m_7700_(Direction.Axis p_83249_);

   public boolean m_83281_() {
      return this.f_83211_.m_6224_();
   }

   public VoxelShape m_83216_(double p_83217_, double p_83218_, double p_83219_) {
      return (VoxelShape)(this.m_83281_() ? Shapes.m_83040_() : new ArrayVoxelShape(this.f_83211_, (DoubleList)(new OffsetDoubleList(this.m_7700_(Direction.Axis.X), p_83217_)), (DoubleList)(new OffsetDoubleList(this.m_7700_(Direction.Axis.Y), p_83218_)), (DoubleList)(new OffsetDoubleList(this.m_7700_(Direction.Axis.Z), p_83219_))));
   }

   public VoxelShape m_83296_() {
      VoxelShape[] avoxelshape = new VoxelShape[]{Shapes.m_83040_()};
      this.m_83286_((p_83275_, p_83276_, p_83277_, p_83278_, p_83279_, p_83280_) -> {
         avoxelshape[0] = Shapes.m_83148_(avoxelshape[0], Shapes.m_83048_(p_83275_, p_83276_, p_83277_, p_83278_, p_83279_, p_83280_), BooleanOp.f_82695_);
      });
      return avoxelshape[0];
   }

   public void m_83224_(Shapes.DoubleLineConsumer p_83225_) {
      this.f_83211_.m_82819_((p_83228_, p_83229_, p_83230_, p_83231_, p_83232_, p_83233_) -> {
         p_83225_.m_83161_(this.m_83256_(Direction.Axis.X, p_83228_), this.m_83256_(Direction.Axis.Y, p_83229_), this.m_83256_(Direction.Axis.Z, p_83230_), this.m_83256_(Direction.Axis.X, p_83231_), this.m_83256_(Direction.Axis.Y, p_83232_), this.m_83256_(Direction.Axis.Z, p_83233_));
      }, true);
   }

   public void m_83286_(Shapes.DoubleLineConsumer p_83287_) {
      DoubleList doublelist = this.m_7700_(Direction.Axis.X);
      DoubleList doublelist1 = this.m_7700_(Direction.Axis.Y);
      DoubleList doublelist2 = this.m_7700_(Direction.Axis.Z);
      this.f_83211_.m_82832_((p_83239_, p_83240_, p_83241_, p_83242_, p_83243_, p_83244_) -> {
         p_83287_.m_83161_(doublelist.getDouble(p_83239_), doublelist1.getDouble(p_83240_), doublelist2.getDouble(p_83241_), doublelist.getDouble(p_83242_), doublelist1.getDouble(p_83243_), doublelist2.getDouble(p_83244_));
      }, true);
   }

   public List<AABB> m_83299_() {
      List<AABB> list = Lists.newArrayList();
      this.m_83286_((p_83267_, p_83268_, p_83269_, p_83270_, p_83271_, p_83272_) -> {
         list.add(new AABB(p_83267_, p_83268_, p_83269_, p_83270_, p_83271_, p_83272_));
      });
      return list;
   }

   public double m_166078_(Direction.Axis p_166079_, double p_166080_, double p_166081_) {
      Direction.Axis direction$axis = AxisCycle.FORWARD.m_7314_(p_166079_);
      Direction.Axis direction$axis1 = AxisCycle.BACKWARD.m_7314_(p_166079_);
      int i = this.m_6595_(direction$axis, p_166080_);
      int j = this.m_6595_(direction$axis1, p_166081_);
      int k = this.f_83211_.m_165994_(p_166079_, i, j);
      return k >= this.f_83211_.m_82850_(p_166079_) ? Double.POSITIVE_INFINITY : this.m_83256_(p_166079_, k);
   }

   public double m_83290_(Direction.Axis p_83291_, double p_83292_, double p_83293_) {
      Direction.Axis direction$axis = AxisCycle.FORWARD.m_7314_(p_83291_);
      Direction.Axis direction$axis1 = AxisCycle.BACKWARD.m_7314_(p_83291_);
      int i = this.m_6595_(direction$axis, p_83292_);
      int j = this.m_6595_(direction$axis1, p_83293_);
      int k = this.f_83211_.m_82841_(p_83291_, i, j);
      return k <= 0 ? Double.NEGATIVE_INFINITY : this.m_83256_(p_83291_, k);
   }

   protected int m_6595_(Direction.Axis p_83250_, double p_83251_) {
      return Mth.m_14049_(0, this.f_83211_.m_82850_(p_83250_) + 1, (p_166066_) -> {
         return p_83251_ < this.m_83256_(p_83250_, p_166066_);
      }) - 1;
   }

   @Nullable
   public BlockHitResult m_83220_(Vec3 p_83221_, Vec3 p_83222_, BlockPos p_83223_) {
      if (this.m_83281_()) {
         return null;
      } else {
         Vec3 vec3 = p_83222_.m_82546_(p_83221_);
         if (vec3.m_82556_() < 1.0E-7D) {
            return null;
         } else {
            Vec3 vec31 = p_83221_.m_82549_(vec3.m_82490_(0.001D));
            return this.f_83211_.m_82846_(this.m_6595_(Direction.Axis.X, vec31.f_82479_ - (double)p_83223_.m_123341_()), this.m_6595_(Direction.Axis.Y, vec31.f_82480_ - (double)p_83223_.m_123342_()), this.m_6595_(Direction.Axis.Z, vec31.f_82481_ - (double)p_83223_.m_123343_())) ? new BlockHitResult(vec31, Direction.m_122366_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_).m_122424_(), p_83223_, true) : AABB.m_82342_(this.m_83299_(), p_83221_, p_83222_, p_83223_);
         }
      }
   }

   public Optional<Vec3> m_166067_(Vec3 p_166068_) {
      if (this.m_83281_()) {
         return Optional.empty();
      } else {
         Vec3[] avec3 = new Vec3[1];
         this.m_83286_((p_166072_, p_166073_, p_166074_, p_166075_, p_166076_, p_166077_) -> {
            double d0 = Mth.m_14008_(p_166068_.m_7096_(), p_166072_, p_166075_);
            double d1 = Mth.m_14008_(p_166068_.m_7098_(), p_166073_, p_166076_);
            double d2 = Mth.m_14008_(p_166068_.m_7094_(), p_166074_, p_166077_);
            if (avec3[0] == null || p_166068_.m_82531_(d0, d1, d2) < p_166068_.m_82557_(avec3[0])) {
               avec3[0] = new Vec3(d0, d1, d2);
            }

         });
         return Optional.of(avec3[0]);
      }
   }

   public VoxelShape m_83263_(Direction p_83264_) {
      if (!this.m_83281_() && this != Shapes.m_83144_()) {
         if (this.f_83212_ != null) {
            VoxelShape voxelshape = this.f_83212_[p_83264_.ordinal()];
            if (voxelshape != null) {
               return voxelshape;
            }
         } else {
            this.f_83212_ = new VoxelShape[6];
         }

         VoxelShape voxelshape1 = this.m_83294_(p_83264_);
         this.f_83212_[p_83264_.ordinal()] = voxelshape1;
         return voxelshape1;
      } else {
         return this;
      }
   }

   private VoxelShape m_83294_(Direction p_83295_) {
      Direction.Axis direction$axis = p_83295_.m_122434_();
      DoubleList doublelist = this.m_7700_(direction$axis);
      if (doublelist.size() == 2 && DoubleMath.fuzzyEquals(doublelist.getDouble(0), 0.0D, 1.0E-7D) && DoubleMath.fuzzyEquals(doublelist.getDouble(1), 1.0D, 1.0E-7D)) {
         return this;
      } else {
         Direction.AxisDirection direction$axisdirection = p_83295_.m_122421_();
         int i = this.m_6595_(direction$axis, direction$axisdirection == Direction.AxisDirection.POSITIVE ? 0.9999999D : 1.0E-7D);
         return new SliceShape(this, direction$axis, i);
      }
   }

   public double m_83259_(Direction.Axis p_83260_, AABB p_83261_, double p_83262_) {
      return this.m_83245_(AxisCycle.m_121799_(p_83260_, Direction.Axis.X), p_83261_, p_83262_);
   }

   protected double m_83245_(AxisCycle p_83246_, AABB p_83247_, double p_83248_) {
      if (this.m_83281_()) {
         return p_83248_;
      } else if (Math.abs(p_83248_) < 1.0E-7D) {
         return 0.0D;
      } else {
         AxisCycle axiscycle = p_83246_.m_7634_();
         Direction.Axis direction$axis = axiscycle.m_7314_(Direction.Axis.X);
         Direction.Axis direction$axis1 = axiscycle.m_7314_(Direction.Axis.Y);
         Direction.Axis direction$axis2 = axiscycle.m_7314_(Direction.Axis.Z);
         double d0 = p_83247_.m_82374_(direction$axis);
         double d1 = p_83247_.m_82340_(direction$axis);
         int i = this.m_6595_(direction$axis, d1 + 1.0E-7D);
         int j = this.m_6595_(direction$axis, d0 - 1.0E-7D);
         int k = Math.max(0, this.m_6595_(direction$axis1, p_83247_.m_82340_(direction$axis1) + 1.0E-7D));
         int l = Math.min(this.f_83211_.m_82850_(direction$axis1), this.m_6595_(direction$axis1, p_83247_.m_82374_(direction$axis1) - 1.0E-7D) + 1);
         int i1 = Math.max(0, this.m_6595_(direction$axis2, p_83247_.m_82340_(direction$axis2) + 1.0E-7D));
         int j1 = Math.min(this.f_83211_.m_82850_(direction$axis2), this.m_6595_(direction$axis2, p_83247_.m_82374_(direction$axis2) - 1.0E-7D) + 1);
         int k1 = this.f_83211_.m_82850_(direction$axis);
         if (p_83248_ > 0.0D) {
            for(int l1 = j + 1; l1 < k1; ++l1) {
               for(int i2 = k; i2 < l; ++i2) {
                  for(int j2 = i1; j2 < j1; ++j2) {
                     if (this.f_83211_.m_82822_(axiscycle, l1, i2, j2)) {
                        double d2 = this.m_83256_(direction$axis, l1) - d0;
                        if (d2 >= -1.0E-7D) {
                           p_83248_ = Math.min(p_83248_, d2);
                        }

                        return p_83248_;
                     }
                  }
               }
            }
         } else if (p_83248_ < 0.0D) {
            for(int k2 = i - 1; k2 >= 0; --k2) {
               for(int l2 = k; l2 < l; ++l2) {
                  for(int i3 = i1; i3 < j1; ++i3) {
                     if (this.f_83211_.m_82822_(axiscycle, k2, l2, i3)) {
                        double d3 = this.m_83256_(direction$axis, k2 + 1) - d1;
                        if (d3 <= 1.0E-7D) {
                           p_83248_ = Math.max(p_83248_, d3);
                        }

                        return p_83248_;
                     }
                  }
               }
            }
         }

         return p_83248_;
      }
   }

   public String toString() {
      return this.m_83281_() ? "EMPTY" : "VoxelShape[" + this.m_83215_() + "]";
   }
}