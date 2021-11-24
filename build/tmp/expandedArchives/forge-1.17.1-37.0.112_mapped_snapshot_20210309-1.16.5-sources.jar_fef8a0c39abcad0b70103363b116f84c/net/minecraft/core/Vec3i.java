package net.minecraft.core;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import java.util.stream.IntStream;
import javax.annotation.concurrent.Immutable;
import net.minecraft.Util;
import net.minecraft.util.Mth;

@Immutable
public class Vec3i implements Comparable<Vec3i> {
   public static final Codec<Vec3i> f_123287_ = Codec.INT_STREAM.comapFlatMap((p_123318_) -> {
      return Util.m_137539_(p_123318_, 3).map((p_175586_) -> {
         return new Vec3i(p_175586_[0], p_175586_[1], p_175586_[2]);
      });
   }, (p_123313_) -> {
      return IntStream.of(p_123313_.m_123341_(), p_123313_.m_123342_(), p_123313_.m_123343_());
   });
   public static final Vec3i f_123288_ = new Vec3i(0, 0, 0);
   private int f_123285_;
   private int f_123286_;
   private int f_123289_;

   public Vec3i(int p_123296_, int p_123297_, int p_123298_) {
      this.f_123285_ = p_123296_;
      this.f_123286_ = p_123297_;
      this.f_123289_ = p_123298_;
   }

   public Vec3i(double p_123292_, double p_123293_, double p_123294_) {
      this(Mth.m_14107_(p_123292_), Mth.m_14107_(p_123293_), Mth.m_14107_(p_123294_));
   }

   public boolean equals(Object p_123327_) {
      if (this == p_123327_) {
         return true;
      } else if (!(p_123327_ instanceof Vec3i)) {
         return false;
      } else {
         Vec3i vec3i = (Vec3i)p_123327_;
         if (this.m_123341_() != vec3i.m_123341_()) {
            return false;
         } else if (this.m_123342_() != vec3i.m_123342_()) {
            return false;
         } else {
            return this.m_123343_() == vec3i.m_123343_();
         }
      }
   }

   public int hashCode() {
      return (this.m_123342_() + this.m_123343_() * 31) * 31 + this.m_123341_();
   }

   public int compareTo(Vec3i p_123330_) {
      if (this.m_123342_() == p_123330_.m_123342_()) {
         return this.m_123343_() == p_123330_.m_123343_() ? this.m_123341_() - p_123330_.m_123341_() : this.m_123343_() - p_123330_.m_123343_();
      } else {
         return this.m_123342_() - p_123330_.m_123342_();
      }
   }

   public int m_123341_() {
      return this.f_123285_;
   }

   public int m_123342_() {
      return this.f_123286_;
   }

   public int m_123343_() {
      return this.f_123289_;
   }

   protected Vec3i m_142451_(int p_175605_) {
      this.f_123285_ = p_175605_;
      return this;
   }

   protected Vec3i m_142448_(int p_175604_) {
      this.f_123286_ = p_175604_;
      return this;
   }

   protected Vec3i m_142443_(int p_175603_) {
      this.f_123289_ = p_175603_;
      return this;
   }

   public Vec3i m_142022_(double p_175587_, double p_175588_, double p_175589_) {
      return p_175587_ == 0.0D && p_175588_ == 0.0D && p_175589_ == 0.0D ? this : new Vec3i((double)this.m_123341_() + p_175587_, (double)this.m_123342_() + p_175588_, (double)this.m_123343_() + p_175589_);
   }

   public Vec3i m_142082_(int p_175593_, int p_175594_, int p_175595_) {
      return p_175593_ == 0 && p_175594_ == 0 && p_175595_ == 0 ? this : new Vec3i(this.m_123341_() + p_175593_, this.m_123342_() + p_175594_, this.m_123343_() + p_175595_);
   }

   public Vec3i m_141952_(Vec3i p_175597_) {
      return this.m_142082_(p_175597_.m_123341_(), p_175597_.m_123342_(), p_175597_.m_123343_());
   }

   public Vec3i m_141950_(Vec3i p_175596_) {
      return this.m_142082_(-p_175596_.m_123341_(), -p_175596_.m_123342_(), -p_175596_.m_123343_());
   }

   public Vec3i m_142393_(int p_175602_) {
      if (p_175602_ == 1) {
         return this;
      } else {
         return p_175602_ == 0 ? f_123288_ : new Vec3i(this.m_123341_() * p_175602_, this.m_123342_() * p_175602_, this.m_123343_() * p_175602_);
      }
   }

   public Vec3i m_7494_() {
      return this.m_6630_(1);
   }

   public Vec3i m_6630_(int p_123336_) {
      return this.m_5484_(Direction.UP, p_123336_);
   }

   public Vec3i m_7495_() {
      return this.m_6625_(1);
   }

   public Vec3i m_6625_(int p_123335_) {
      return this.m_5484_(Direction.DOWN, p_123335_);
   }

   public Vec3i m_142127_() {
      return this.m_142390_(1);
   }

   public Vec3i m_142390_(int p_175601_) {
      return this.m_5484_(Direction.NORTH, p_175601_);
   }

   public Vec3i m_142128_() {
      return this.m_142383_(1);
   }

   public Vec3i m_142383_(int p_175600_) {
      return this.m_5484_(Direction.SOUTH, p_175600_);
   }

   public Vec3i m_142125_() {
      return this.m_142386_(1);
   }

   public Vec3i m_142386_(int p_175599_) {
      return this.m_5484_(Direction.WEST, p_175599_);
   }

   public Vec3i m_142126_() {
      return this.m_142385_(1);
   }

   public Vec3i m_142385_(int p_175598_) {
      return this.m_5484_(Direction.EAST, p_175598_);
   }

   public Vec3i m_142300_(Direction p_175592_) {
      return this.m_5484_(p_175592_, 1);
   }

   public Vec3i m_5484_(Direction p_123321_, int p_123322_) {
      return p_123322_ == 0 ? this : new Vec3i(this.m_123341_() + p_123321_.m_122429_() * p_123322_, this.m_123342_() + p_123321_.m_122430_() * p_123322_, this.m_123343_() + p_123321_.m_122431_() * p_123322_);
   }

   public Vec3i m_142629_(Direction.Axis p_175590_, int p_175591_) {
      if (p_175591_ == 0) {
         return this;
      } else {
         int i = p_175590_ == Direction.Axis.X ? p_175591_ : 0;
         int j = p_175590_ == Direction.Axis.Y ? p_175591_ : 0;
         int k = p_175590_ == Direction.Axis.Z ? p_175591_ : 0;
         return new Vec3i(this.m_123341_() + i, this.m_123342_() + j, this.m_123343_() + k);
      }
   }

   public Vec3i m_7724_(Vec3i p_123325_) {
      return new Vec3i(this.m_123342_() * p_123325_.m_123343_() - this.m_123343_() * p_123325_.m_123342_(), this.m_123343_() * p_123325_.m_123341_() - this.m_123341_() * p_123325_.m_123343_(), this.m_123341_() * p_123325_.m_123342_() - this.m_123342_() * p_123325_.m_123341_());
   }

   public boolean m_123314_(Vec3i p_123315_, double p_123316_) {
      return this.m_123299_((double)p_123315_.m_123341_(), (double)p_123315_.m_123342_(), (double)p_123315_.m_123343_(), false) < p_123316_ * p_123316_;
   }

   public boolean m_123306_(Position p_123307_, double p_123308_) {
      return this.m_123299_(p_123307_.m_7096_(), p_123307_.m_7098_(), p_123307_.m_7094_(), true) < p_123308_ * p_123308_;
   }

   public double m_123331_(Vec3i p_123332_) {
      return this.m_123299_((double)p_123332_.m_123341_(), (double)p_123332_.m_123342_(), (double)p_123332_.m_123343_(), true);
   }

   public double m_123309_(Position p_123310_, boolean p_123311_) {
      return this.m_123299_(p_123310_.m_7096_(), p_123310_.m_7098_(), p_123310_.m_7094_(), p_123311_);
   }

   public double m_175582_(Vec3i p_175583_, boolean p_175584_) {
      return this.m_123299_((double)p_175583_.f_123285_, (double)p_175583_.f_123286_, (double)p_175583_.f_123289_, p_175584_);
   }

   public double m_123299_(double p_123300_, double p_123301_, double p_123302_, boolean p_123303_) {
      double d0 = p_123303_ ? 0.5D : 0.0D;
      double d1 = (double)this.m_123341_() + d0 - p_123300_;
      double d2 = (double)this.m_123342_() + d0 - p_123301_;
      double d3 = (double)this.m_123343_() + d0 - p_123302_;
      return d1 * d1 + d2 * d2 + d3 * d3;
   }

   public int m_123333_(Vec3i p_123334_) {
      float f = (float)Math.abs(p_123334_.m_123341_() - this.m_123341_());
      float f1 = (float)Math.abs(p_123334_.m_123342_() - this.m_123342_());
      float f2 = (float)Math.abs(p_123334_.m_123343_() - this.m_123343_());
      return (int)(f + f1 + f2);
   }

   public int m_123304_(Direction.Axis p_123305_) {
      return p_123305_.m_7863_(this.f_123285_, this.f_123286_, this.f_123289_);
   }

   public String toString() {
      return MoreObjects.toStringHelper(this).add("x", this.m_123341_()).add("y", this.m_123342_()).add("z", this.m_123343_()).toString();
   }

   public String m_123344_() {
      return this.m_123341_() + ", " + this.m_123342_() + ", " + this.m_123343_();
   }
}