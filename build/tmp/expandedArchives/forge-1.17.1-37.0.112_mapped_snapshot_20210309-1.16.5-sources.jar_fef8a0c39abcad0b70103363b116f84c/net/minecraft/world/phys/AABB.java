package net.minecraft.world.phys;

import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class AABB {
   private static final double f_165879_ = 1.0E-7D;
   public final double f_82288_;
   public final double f_82289_;
   public final double f_82290_;
   public final double f_82291_;
   public final double f_82292_;
   public final double f_82293_;

   public AABB(double p_82295_, double p_82296_, double p_82297_, double p_82298_, double p_82299_, double p_82300_) {
      this.f_82288_ = Math.min(p_82295_, p_82298_);
      this.f_82289_ = Math.min(p_82296_, p_82299_);
      this.f_82290_ = Math.min(p_82297_, p_82300_);
      this.f_82291_ = Math.max(p_82295_, p_82298_);
      this.f_82292_ = Math.max(p_82296_, p_82299_);
      this.f_82293_ = Math.max(p_82297_, p_82300_);
   }

   public AABB(BlockPos p_82305_) {
      this((double)p_82305_.m_123341_(), (double)p_82305_.m_123342_(), (double)p_82305_.m_123343_(), (double)(p_82305_.m_123341_() + 1), (double)(p_82305_.m_123342_() + 1), (double)(p_82305_.m_123343_() + 1));
   }

   public AABB(BlockPos p_82307_, BlockPos p_82308_) {
      this((double)p_82307_.m_123341_(), (double)p_82307_.m_123342_(), (double)p_82307_.m_123343_(), (double)p_82308_.m_123341_(), (double)p_82308_.m_123342_(), (double)p_82308_.m_123343_());
   }

   public AABB(Vec3 p_82302_, Vec3 p_82303_) {
      this(p_82302_.f_82479_, p_82302_.f_82480_, p_82302_.f_82481_, p_82303_.f_82479_, p_82303_.f_82480_, p_82303_.f_82481_);
   }

   public static AABB m_82321_(BoundingBox p_82322_) {
      return new AABB((double)p_82322_.m_162395_(), (double)p_82322_.m_162396_(), (double)p_82322_.m_162398_(), (double)(p_82322_.m_162399_() + 1), (double)(p_82322_.m_162400_() + 1), (double)(p_82322_.m_162401_() + 1));
   }

   public static AABB m_82333_(Vec3 p_82334_) {
      return new AABB(p_82334_.f_82479_, p_82334_.f_82480_, p_82334_.f_82481_, p_82334_.f_82479_ + 1.0D, p_82334_.f_82480_ + 1.0D, p_82334_.f_82481_ + 1.0D);
   }

   public AABB m_165880_(double p_165881_) {
      return new AABB(p_165881_, this.f_82289_, this.f_82290_, this.f_82291_, this.f_82292_, this.f_82293_);
   }

   public AABB m_165887_(double p_165888_) {
      return new AABB(this.f_82288_, p_165888_, this.f_82290_, this.f_82291_, this.f_82292_, this.f_82293_);
   }

   public AABB m_165889_(double p_165890_) {
      return new AABB(this.f_82288_, this.f_82289_, p_165890_, this.f_82291_, this.f_82292_, this.f_82293_);
   }

   public AABB m_165891_(double p_165892_) {
      return new AABB(this.f_82288_, this.f_82289_, this.f_82290_, p_165892_, this.f_82292_, this.f_82293_);
   }

   public AABB m_165893_(double p_165894_) {
      return new AABB(this.f_82288_, this.f_82289_, this.f_82290_, this.f_82291_, p_165894_, this.f_82293_);
   }

   public AABB m_165895_(double p_165896_) {
      return new AABB(this.f_82288_, this.f_82289_, this.f_82290_, this.f_82291_, this.f_82292_, p_165896_);
   }

   public double m_82340_(Direction.Axis p_82341_) {
      return p_82341_.m_6150_(this.f_82288_, this.f_82289_, this.f_82290_);
   }

   public double m_82374_(Direction.Axis p_82375_) {
      return p_82375_.m_6150_(this.f_82291_, this.f_82292_, this.f_82293_);
   }

   public boolean equals(Object p_82398_) {
      if (this == p_82398_) {
         return true;
      } else if (!(p_82398_ instanceof AABB)) {
         return false;
      } else {
         AABB aabb = (AABB)p_82398_;
         if (Double.compare(aabb.f_82288_, this.f_82288_) != 0) {
            return false;
         } else if (Double.compare(aabb.f_82289_, this.f_82289_) != 0) {
            return false;
         } else if (Double.compare(aabb.f_82290_, this.f_82290_) != 0) {
            return false;
         } else if (Double.compare(aabb.f_82291_, this.f_82291_) != 0) {
            return false;
         } else if (Double.compare(aabb.f_82292_, this.f_82292_) != 0) {
            return false;
         } else {
            return Double.compare(aabb.f_82293_, this.f_82293_) == 0;
         }
      }
   }

   public int hashCode() {
      long i = Double.doubleToLongBits(this.f_82288_);
      int j = (int)(i ^ i >>> 32);
      i = Double.doubleToLongBits(this.f_82289_);
      j = 31 * j + (int)(i ^ i >>> 32);
      i = Double.doubleToLongBits(this.f_82290_);
      j = 31 * j + (int)(i ^ i >>> 32);
      i = Double.doubleToLongBits(this.f_82291_);
      j = 31 * j + (int)(i ^ i >>> 32);
      i = Double.doubleToLongBits(this.f_82292_);
      j = 31 * j + (int)(i ^ i >>> 32);
      i = Double.doubleToLongBits(this.f_82293_);
      return 31 * j + (int)(i ^ i >>> 32);
   }

   public AABB m_82310_(double p_82311_, double p_82312_, double p_82313_) {
      double d0 = this.f_82288_;
      double d1 = this.f_82289_;
      double d2 = this.f_82290_;
      double d3 = this.f_82291_;
      double d4 = this.f_82292_;
      double d5 = this.f_82293_;
      if (p_82311_ < 0.0D) {
         d0 -= p_82311_;
      } else if (p_82311_ > 0.0D) {
         d3 -= p_82311_;
      }

      if (p_82312_ < 0.0D) {
         d1 -= p_82312_;
      } else if (p_82312_ > 0.0D) {
         d4 -= p_82312_;
      }

      if (p_82313_ < 0.0D) {
         d2 -= p_82313_;
      } else if (p_82313_ > 0.0D) {
         d5 -= p_82313_;
      }

      return new AABB(d0, d1, d2, d3, d4, d5);
   }

   public AABB m_82369_(Vec3 p_82370_) {
      return this.m_82363_(p_82370_.f_82479_, p_82370_.f_82480_, p_82370_.f_82481_);
   }

   public AABB m_82363_(double p_82364_, double p_82365_, double p_82366_) {
      double d0 = this.f_82288_;
      double d1 = this.f_82289_;
      double d2 = this.f_82290_;
      double d3 = this.f_82291_;
      double d4 = this.f_82292_;
      double d5 = this.f_82293_;
      if (p_82364_ < 0.0D) {
         d0 += p_82364_;
      } else if (p_82364_ > 0.0D) {
         d3 += p_82364_;
      }

      if (p_82365_ < 0.0D) {
         d1 += p_82365_;
      } else if (p_82365_ > 0.0D) {
         d4 += p_82365_;
      }

      if (p_82366_ < 0.0D) {
         d2 += p_82366_;
      } else if (p_82366_ > 0.0D) {
         d5 += p_82366_;
      }

      return new AABB(d0, d1, d2, d3, d4, d5);
   }

   public AABB m_82377_(double p_82378_, double p_82379_, double p_82380_) {
      double d0 = this.f_82288_ - p_82378_;
      double d1 = this.f_82289_ - p_82379_;
      double d2 = this.f_82290_ - p_82380_;
      double d3 = this.f_82291_ + p_82378_;
      double d4 = this.f_82292_ + p_82379_;
      double d5 = this.f_82293_ + p_82380_;
      return new AABB(d0, d1, d2, d3, d4, d5);
   }

   public AABB m_82400_(double p_82401_) {
      return this.m_82377_(p_82401_, p_82401_, p_82401_);
   }

   public AABB m_82323_(AABB p_82324_) {
      double d0 = Math.max(this.f_82288_, p_82324_.f_82288_);
      double d1 = Math.max(this.f_82289_, p_82324_.f_82289_);
      double d2 = Math.max(this.f_82290_, p_82324_.f_82290_);
      double d3 = Math.min(this.f_82291_, p_82324_.f_82291_);
      double d4 = Math.min(this.f_82292_, p_82324_.f_82292_);
      double d5 = Math.min(this.f_82293_, p_82324_.f_82293_);
      return new AABB(d0, d1, d2, d3, d4, d5);
   }

   public AABB m_82367_(AABB p_82368_) {
      double d0 = Math.min(this.f_82288_, p_82368_.f_82288_);
      double d1 = Math.min(this.f_82289_, p_82368_.f_82289_);
      double d2 = Math.min(this.f_82290_, p_82368_.f_82290_);
      double d3 = Math.max(this.f_82291_, p_82368_.f_82291_);
      double d4 = Math.max(this.f_82292_, p_82368_.f_82292_);
      double d5 = Math.max(this.f_82293_, p_82368_.f_82293_);
      return new AABB(d0, d1, d2, d3, d4, d5);
   }

   public AABB m_82386_(double p_82387_, double p_82388_, double p_82389_) {
      return new AABB(this.f_82288_ + p_82387_, this.f_82289_ + p_82388_, this.f_82290_ + p_82389_, this.f_82291_ + p_82387_, this.f_82292_ + p_82388_, this.f_82293_ + p_82389_);
   }

   public AABB m_82338_(BlockPos p_82339_) {
      return new AABB(this.f_82288_ + (double)p_82339_.m_123341_(), this.f_82289_ + (double)p_82339_.m_123342_(), this.f_82290_ + (double)p_82339_.m_123343_(), this.f_82291_ + (double)p_82339_.m_123341_(), this.f_82292_ + (double)p_82339_.m_123342_(), this.f_82293_ + (double)p_82339_.m_123343_());
   }

   public AABB m_82383_(Vec3 p_82384_) {
      return this.m_82386_(p_82384_.f_82479_, p_82384_.f_82480_, p_82384_.f_82481_);
   }

   public boolean m_82381_(AABB p_82382_) {
      return this.m_82314_(p_82382_.f_82288_, p_82382_.f_82289_, p_82382_.f_82290_, p_82382_.f_82291_, p_82382_.f_82292_, p_82382_.f_82293_);
   }

   public boolean m_82314_(double p_82315_, double p_82316_, double p_82317_, double p_82318_, double p_82319_, double p_82320_) {
      return this.f_82288_ < p_82318_ && this.f_82291_ > p_82315_ && this.f_82289_ < p_82319_ && this.f_82292_ > p_82316_ && this.f_82290_ < p_82320_ && this.f_82293_ > p_82317_;
   }

   public boolean m_82335_(Vec3 p_82336_, Vec3 p_82337_) {
      return this.m_82314_(Math.min(p_82336_.f_82479_, p_82337_.f_82479_), Math.min(p_82336_.f_82480_, p_82337_.f_82480_), Math.min(p_82336_.f_82481_, p_82337_.f_82481_), Math.max(p_82336_.f_82479_, p_82337_.f_82479_), Math.max(p_82336_.f_82480_, p_82337_.f_82480_), Math.max(p_82336_.f_82481_, p_82337_.f_82481_));
   }

   public boolean m_82390_(Vec3 p_82391_) {
      return this.m_82393_(p_82391_.f_82479_, p_82391_.f_82480_, p_82391_.f_82481_);
   }

   public boolean m_82393_(double p_82394_, double p_82395_, double p_82396_) {
      return p_82394_ >= this.f_82288_ && p_82394_ < this.f_82291_ && p_82395_ >= this.f_82289_ && p_82395_ < this.f_82292_ && p_82396_ >= this.f_82290_ && p_82396_ < this.f_82293_;
   }

   public double m_82309_() {
      double d0 = this.m_82362_();
      double d1 = this.m_82376_();
      double d2 = this.m_82385_();
      return (d0 + d1 + d2) / 3.0D;
   }

   public double m_82362_() {
      return this.f_82291_ - this.f_82288_;
   }

   public double m_82376_() {
      return this.f_82292_ - this.f_82289_;
   }

   public double m_82385_() {
      return this.f_82293_ - this.f_82290_;
   }

   public AABB m_165897_(double p_165898_, double p_165899_, double p_165900_) {
      return this.m_82377_(-p_165898_, -p_165899_, -p_165900_);
   }

   public AABB m_82406_(double p_82407_) {
      return this.m_82400_(-p_82407_);
   }

   public Optional<Vec3> m_82371_(Vec3 p_82372_, Vec3 p_82373_) {
      double[] adouble = new double[]{1.0D};
      double d0 = p_82373_.f_82479_ - p_82372_.f_82479_;
      double d1 = p_82373_.f_82480_ - p_82372_.f_82480_;
      double d2 = p_82373_.f_82481_ - p_82372_.f_82481_;
      Direction direction = m_82325_(this, p_82372_, adouble, (Direction)null, d0, d1, d2);
      if (direction == null) {
         return Optional.empty();
      } else {
         double d3 = adouble[0];
         return Optional.of(p_82372_.m_82520_(d3 * d0, d3 * d1, d3 * d2));
      }
   }

   @Nullable
   public static BlockHitResult m_82342_(Iterable<AABB> p_82343_, Vec3 p_82344_, Vec3 p_82345_, BlockPos p_82346_) {
      double[] adouble = new double[]{1.0D};
      Direction direction = null;
      double d0 = p_82345_.f_82479_ - p_82344_.f_82479_;
      double d1 = p_82345_.f_82480_ - p_82344_.f_82480_;
      double d2 = p_82345_.f_82481_ - p_82344_.f_82481_;

      for(AABB aabb : p_82343_) {
         direction = m_82325_(aabb.m_82338_(p_82346_), p_82344_, adouble, direction, d0, d1, d2);
      }

      if (direction == null) {
         return null;
      } else {
         double d3 = adouble[0];
         return new BlockHitResult(p_82344_.m_82520_(d3 * d0, d3 * d1, d3 * d2), direction, p_82346_, false);
      }
   }

   @Nullable
   private static Direction m_82325_(AABB p_82326_, Vec3 p_82327_, double[] p_82328_, @Nullable Direction p_82329_, double p_82330_, double p_82331_, double p_82332_) {
      if (p_82330_ > 1.0E-7D) {
         p_82329_ = m_82347_(p_82328_, p_82329_, p_82330_, p_82331_, p_82332_, p_82326_.f_82288_, p_82326_.f_82289_, p_82326_.f_82292_, p_82326_.f_82290_, p_82326_.f_82293_, Direction.WEST, p_82327_.f_82479_, p_82327_.f_82480_, p_82327_.f_82481_);
      } else if (p_82330_ < -1.0E-7D) {
         p_82329_ = m_82347_(p_82328_, p_82329_, p_82330_, p_82331_, p_82332_, p_82326_.f_82291_, p_82326_.f_82289_, p_82326_.f_82292_, p_82326_.f_82290_, p_82326_.f_82293_, Direction.EAST, p_82327_.f_82479_, p_82327_.f_82480_, p_82327_.f_82481_);
      }

      if (p_82331_ > 1.0E-7D) {
         p_82329_ = m_82347_(p_82328_, p_82329_, p_82331_, p_82332_, p_82330_, p_82326_.f_82289_, p_82326_.f_82290_, p_82326_.f_82293_, p_82326_.f_82288_, p_82326_.f_82291_, Direction.DOWN, p_82327_.f_82480_, p_82327_.f_82481_, p_82327_.f_82479_);
      } else if (p_82331_ < -1.0E-7D) {
         p_82329_ = m_82347_(p_82328_, p_82329_, p_82331_, p_82332_, p_82330_, p_82326_.f_82292_, p_82326_.f_82290_, p_82326_.f_82293_, p_82326_.f_82288_, p_82326_.f_82291_, Direction.UP, p_82327_.f_82480_, p_82327_.f_82481_, p_82327_.f_82479_);
      }

      if (p_82332_ > 1.0E-7D) {
         p_82329_ = m_82347_(p_82328_, p_82329_, p_82332_, p_82330_, p_82331_, p_82326_.f_82290_, p_82326_.f_82288_, p_82326_.f_82291_, p_82326_.f_82289_, p_82326_.f_82292_, Direction.NORTH, p_82327_.f_82481_, p_82327_.f_82479_, p_82327_.f_82480_);
      } else if (p_82332_ < -1.0E-7D) {
         p_82329_ = m_82347_(p_82328_, p_82329_, p_82332_, p_82330_, p_82331_, p_82326_.f_82293_, p_82326_.f_82288_, p_82326_.f_82291_, p_82326_.f_82289_, p_82326_.f_82292_, Direction.SOUTH, p_82327_.f_82481_, p_82327_.f_82479_, p_82327_.f_82480_);
      }

      return p_82329_;
   }

   @Nullable
   private static Direction m_82347_(double[] p_82348_, @Nullable Direction p_82349_, double p_82350_, double p_82351_, double p_82352_, double p_82353_, double p_82354_, double p_82355_, double p_82356_, double p_82357_, Direction p_82358_, double p_82359_, double p_82360_, double p_82361_) {
      double d0 = (p_82353_ - p_82359_) / p_82350_;
      double d1 = p_82360_ + d0 * p_82351_;
      double d2 = p_82361_ + d0 * p_82352_;
      if (0.0D < d0 && d0 < p_82348_[0] && p_82354_ - 1.0E-7D < d1 && d1 < p_82355_ + 1.0E-7D && p_82356_ - 1.0E-7D < d2 && d2 < p_82357_ + 1.0E-7D) {
         p_82348_[0] = d0;
         return p_82358_;
      } else {
         return p_82349_;
      }
   }

   public String toString() {
      return "AABB[" + this.f_82288_ + ", " + this.f_82289_ + ", " + this.f_82290_ + "] -> [" + this.f_82291_ + ", " + this.f_82292_ + ", " + this.f_82293_ + "]";
   }

   public boolean m_82392_() {
      return Double.isNaN(this.f_82288_) || Double.isNaN(this.f_82289_) || Double.isNaN(this.f_82290_) || Double.isNaN(this.f_82291_) || Double.isNaN(this.f_82292_) || Double.isNaN(this.f_82293_);
   }

   public Vec3 m_82399_() {
      return new Vec3(Mth.m_14139_(0.5D, this.f_82288_, this.f_82291_), Mth.m_14139_(0.5D, this.f_82289_, this.f_82292_), Mth.m_14139_(0.5D, this.f_82290_, this.f_82293_));
   }

   public static AABB m_165882_(Vec3 p_165883_, double p_165884_, double p_165885_, double p_165886_) {
      return new AABB(p_165883_.f_82479_ - p_165884_ / 2.0D, p_165883_.f_82480_ - p_165885_ / 2.0D, p_165883_.f_82481_ - p_165886_ / 2.0D, p_165883_.f_82479_ + p_165884_ / 2.0D, p_165883_.f_82480_ + p_165885_ / 2.0D, p_165883_.f_82481_ + p_165886_ / 2.0D);
   }
}