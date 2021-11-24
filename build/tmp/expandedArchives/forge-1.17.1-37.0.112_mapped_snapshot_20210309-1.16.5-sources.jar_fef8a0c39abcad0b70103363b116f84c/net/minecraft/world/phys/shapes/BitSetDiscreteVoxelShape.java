package net.minecraft.world.phys.shapes;

import java.util.BitSet;
import net.minecraft.core.Direction;

public final class BitSetDiscreteVoxelShape extends DiscreteVoxelShape {
   private final BitSet f_82580_;
   private int f_82581_;
   private int f_82582_;
   private int f_82583_;
   private int f_82584_;
   private int f_82585_;
   private int f_82586_;

   public BitSetDiscreteVoxelShape(int p_82588_, int p_82589_, int p_82590_) {
      super(p_82588_, p_82589_, p_82590_);
      this.f_82580_ = new BitSet(p_82588_ * p_82589_ * p_82590_);
      this.f_82581_ = p_82588_;
      this.f_82582_ = p_82589_;
      this.f_82583_ = p_82590_;
   }

   public static BitSetDiscreteVoxelShape m_165932_(int p_165933_, int p_165934_, int p_165935_, int p_165936_, int p_165937_, int p_165938_, int p_165939_, int p_165940_, int p_165941_) {
      BitSetDiscreteVoxelShape bitsetdiscretevoxelshape = new BitSetDiscreteVoxelShape(p_165933_, p_165934_, p_165935_);
      bitsetdiscretevoxelshape.f_82581_ = p_165936_;
      bitsetdiscretevoxelshape.f_82582_ = p_165937_;
      bitsetdiscretevoxelshape.f_82583_ = p_165938_;
      bitsetdiscretevoxelshape.f_82584_ = p_165939_;
      bitsetdiscretevoxelshape.f_82585_ = p_165940_;
      bitsetdiscretevoxelshape.f_82586_ = p_165941_;

      for(int i = p_165936_; i < p_165939_; ++i) {
         for(int j = p_165937_; j < p_165940_; ++j) {
            for(int k = p_165938_; k < p_165941_; ++k) {
               bitsetdiscretevoxelshape.m_165942_(i, j, k, false);
            }
         }
      }

      return bitsetdiscretevoxelshape;
   }

   public BitSetDiscreteVoxelShape(DiscreteVoxelShape p_82602_) {
      super(p_82602_.f_82781_, p_82602_.f_82782_, p_82602_.f_82783_);
      if (p_82602_ instanceof BitSetDiscreteVoxelShape) {
         this.f_82580_ = (BitSet)((BitSetDiscreteVoxelShape)p_82602_).f_82580_.clone();
      } else {
         this.f_82580_ = new BitSet(this.f_82781_ * this.f_82782_ * this.f_82783_);

         for(int i = 0; i < this.f_82781_; ++i) {
            for(int j = 0; j < this.f_82782_; ++j) {
               for(int k = 0; k < this.f_82783_; ++k) {
                  if (p_82602_.m_6696_(i, j, k)) {
                     this.f_82580_.set(this.m_82604_(i, j, k));
                  }
               }
            }
         }
      }

      this.f_82581_ = p_82602_.m_6538_(Direction.Axis.X);
      this.f_82582_ = p_82602_.m_6538_(Direction.Axis.Y);
      this.f_82583_ = p_82602_.m_6538_(Direction.Axis.Z);
      this.f_82584_ = p_82602_.m_6536_(Direction.Axis.X);
      this.f_82585_ = p_82602_.m_6536_(Direction.Axis.Y);
      this.f_82586_ = p_82602_.m_6536_(Direction.Axis.Z);
   }

   protected int m_82604_(int p_82605_, int p_82606_, int p_82607_) {
      return (p_82605_ * this.f_82782_ + p_82606_) * this.f_82783_ + p_82607_;
   }

   public boolean m_6696_(int p_82676_, int p_82677_, int p_82678_) {
      return this.f_82580_.get(this.m_82604_(p_82676_, p_82677_, p_82678_));
   }

   private void m_165942_(int p_165943_, int p_165944_, int p_165945_, boolean p_165946_) {
      this.f_82580_.set(this.m_82604_(p_165943_, p_165944_, p_165945_));
      if (p_165946_) {
         this.f_82581_ = Math.min(this.f_82581_, p_165943_);
         this.f_82582_ = Math.min(this.f_82582_, p_165944_);
         this.f_82583_ = Math.min(this.f_82583_, p_165945_);
         this.f_82584_ = Math.max(this.f_82584_, p_165943_ + 1);
         this.f_82585_ = Math.max(this.f_82585_, p_165944_ + 1);
         this.f_82586_ = Math.max(this.f_82586_, p_165945_ + 1);
      }

   }

   public void m_142703_(int p_165987_, int p_165988_, int p_165989_) {
      this.m_165942_(p_165987_, p_165988_, p_165989_, true);
   }

   public boolean m_6224_() {
      return this.f_82580_.isEmpty();
   }

   public int m_6538_(Direction.Axis p_82674_) {
      return p_82674_.m_7863_(this.f_82581_, this.f_82582_, this.f_82583_);
   }

   public int m_6536_(Direction.Axis p_82680_) {
      return p_82680_.m_7863_(this.f_82584_, this.f_82585_, this.f_82586_);
   }

   static BitSetDiscreteVoxelShape m_82641_(DiscreteVoxelShape p_82642_, DiscreteVoxelShape p_82643_, IndexMerger p_82644_, IndexMerger p_82645_, IndexMerger p_82646_, BooleanOp p_82647_) {
      BitSetDiscreteVoxelShape bitsetdiscretevoxelshape = new BitSetDiscreteVoxelShape(p_82644_.size() - 1, p_82645_.size() - 1, p_82646_.size() - 1);
      int[] aint = new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
      p_82644_.m_6200_((p_82670_, p_82671_, p_82672_) -> {
         boolean[] aboolean = new boolean[]{false};
         p_82645_.m_6200_((p_165978_, p_165979_, p_165980_) -> {
            boolean[] aboolean1 = new boolean[]{false};
            p_82646_.m_6200_((p_165960_, p_165961_, p_165962_) -> {
               if (p_82647_.m_82701_(p_82642_.m_82846_(p_82670_, p_165978_, p_165960_), p_82643_.m_82846_(p_82671_, p_165979_, p_165961_))) {
                  bitsetdiscretevoxelshape.f_82580_.set(bitsetdiscretevoxelshape.m_82604_(p_82672_, p_165980_, p_165962_));
                  aint[2] = Math.min(aint[2], p_165962_);
                  aint[5] = Math.max(aint[5], p_165962_);
                  aboolean1[0] = true;
               }

               return true;
            });
            if (aboolean1[0]) {
               aint[1] = Math.min(aint[1], p_165980_);
               aint[4] = Math.max(aint[4], p_165980_);
               aboolean[0] = true;
            }

            return true;
         });
         if (aboolean[0]) {
            aint[0] = Math.min(aint[0], p_82672_);
            aint[3] = Math.max(aint[3], p_82672_);
         }

         return true;
      });
      bitsetdiscretevoxelshape.f_82581_ = aint[0];
      bitsetdiscretevoxelshape.f_82582_ = aint[1];
      bitsetdiscretevoxelshape.f_82583_ = aint[2];
      bitsetdiscretevoxelshape.f_82584_ = aint[3] + 1;
      bitsetdiscretevoxelshape.f_82585_ = aint[4] + 1;
      bitsetdiscretevoxelshape.f_82586_ = aint[5] + 1;
      return bitsetdiscretevoxelshape;
   }

   protected static void m_165963_(DiscreteVoxelShape p_165964_, DiscreteVoxelShape.IntLineConsumer p_165965_, boolean p_165966_) {
      BitSetDiscreteVoxelShape bitsetdiscretevoxelshape = new BitSetDiscreteVoxelShape(p_165964_);

      for(int i = 0; i < bitsetdiscretevoxelshape.f_82781_; ++i) {
         for(int j = 0; j < bitsetdiscretevoxelshape.f_82782_; ++j) {
            int k = -1;

            for(int l = 0; l <= bitsetdiscretevoxelshape.f_82783_; ++l) {
               if (bitsetdiscretevoxelshape.m_82846_(i, j, l)) {
                  if (p_165966_) {
                     if (k == -1) {
                        k = l;
                     }
                  } else {
                     p_165965_.m_82858_(i, j, l, i + 1, j + 1, l + 1);
                  }
               } else if (k != -1) {
                  int i1 = i;
                  int j1 = j;
                  bitsetdiscretevoxelshape.m_165981_(k, l, i, j);

                  while(bitsetdiscretevoxelshape.m_82608_(k, l, i1 + 1, j)) {
                     bitsetdiscretevoxelshape.m_165981_(k, l, i1 + 1, j);
                     ++i1;
                  }

                  while(bitsetdiscretevoxelshape.m_165926_(i, i1 + 1, k, l, j1 + 1)) {
                     for(int k1 = i; k1 <= i1; ++k1) {
                        bitsetdiscretevoxelshape.m_165981_(k, l, k1, j1 + 1);
                     }

                     ++j1;
                  }

                  p_165965_.m_82858_(i, j, k, i1 + 1, j1 + 1, l);
                  k = -1;
               }
            }
         }
      }

   }

   private boolean m_82608_(int p_82609_, int p_82610_, int p_82611_, int p_82612_) {
      if (p_82611_ < this.f_82781_ && p_82612_ < this.f_82782_) {
         return this.f_82580_.nextClearBit(this.m_82604_(p_82611_, p_82612_, p_82609_)) >= this.m_82604_(p_82611_, p_82612_, p_82610_);
      } else {
         return false;
      }
   }

   private boolean m_165926_(int p_165927_, int p_165928_, int p_165929_, int p_165930_, int p_165931_) {
      for(int i = p_165927_; i < p_165928_; ++i) {
         if (!this.m_82608_(p_165929_, p_165930_, i, p_165931_)) {
            return false;
         }
      }

      return true;
   }

   private void m_165981_(int p_165982_, int p_165983_, int p_165984_, int p_165985_) {
      this.f_82580_.clear(this.m_82604_(p_165984_, p_165985_, p_165982_), this.m_82604_(p_165984_, p_165985_, p_165983_));
   }
}