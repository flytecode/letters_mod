package net.minecraft.world.entity.ai.control;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MoveControl implements Control {
   public static final float f_148053_ = 5.0E-4F;
   public static final float f_148054_ = 2.5000003E-7F;
   protected static final int f_148055_ = 90;
   protected final Mob f_24974_;
   protected double f_24975_;
   protected double f_24976_;
   protected double f_24977_;
   protected double f_24978_;
   protected float f_24979_;
   protected float f_24980_;
   protected MoveControl.Operation f_24981_ = MoveControl.Operation.WAIT;

   public MoveControl(Mob p_24983_) {
      this.f_24974_ = p_24983_;
   }

   public boolean m_24995_() {
      return this.f_24981_ == MoveControl.Operation.MOVE_TO;
   }

   public double m_24999_() {
      return this.f_24978_;
   }

   public void m_6849_(double p_24984_, double p_24985_, double p_24986_, double p_24987_) {
      this.f_24975_ = p_24984_;
      this.f_24976_ = p_24985_;
      this.f_24977_ = p_24986_;
      this.f_24978_ = p_24987_;
      if (this.f_24981_ != MoveControl.Operation.JUMPING) {
         this.f_24981_ = MoveControl.Operation.MOVE_TO;
      }

   }

   public void m_24988_(float p_24989_, float p_24990_) {
      this.f_24981_ = MoveControl.Operation.STRAFE;
      this.f_24979_ = p_24989_;
      this.f_24980_ = p_24990_;
      this.f_24978_ = 0.25D;
   }

   public void m_8126_() {
      if (this.f_24981_ == MoveControl.Operation.STRAFE) {
         float f = (float)this.f_24974_.m_21133_(Attributes.f_22279_);
         float f1 = (float)this.f_24978_ * f;
         float f2 = this.f_24979_;
         float f3 = this.f_24980_;
         float f4 = Mth.m_14116_(f2 * f2 + f3 * f3);
         if (f4 < 1.0F) {
            f4 = 1.0F;
         }

         f4 = f1 / f4;
         f2 = f2 * f4;
         f3 = f3 * f4;
         float f5 = Mth.m_14031_(this.f_24974_.m_146908_() * ((float)Math.PI / 180F));
         float f6 = Mth.m_14089_(this.f_24974_.m_146908_() * ((float)Math.PI / 180F));
         float f7 = f2 * f6 - f3 * f5;
         float f8 = f3 * f6 + f2 * f5;
         if (!this.m_24996_(f7, f8)) {
            this.f_24979_ = 1.0F;
            this.f_24980_ = 0.0F;
         }

         this.f_24974_.m_7910_(f1);
         this.f_24974_.m_21564_(this.f_24979_);
         this.f_24974_.m_21570_(this.f_24980_);
         this.f_24981_ = MoveControl.Operation.WAIT;
      } else if (this.f_24981_ == MoveControl.Operation.MOVE_TO) {
         this.f_24981_ = MoveControl.Operation.WAIT;
         double d0 = this.f_24975_ - this.f_24974_.m_20185_();
         double d1 = this.f_24977_ - this.f_24974_.m_20189_();
         double d2 = this.f_24976_ - this.f_24974_.m_20186_();
         double d3 = d0 * d0 + d2 * d2 + d1 * d1;
         if (d3 < (double)2.5000003E-7F) {
            this.f_24974_.m_21564_(0.0F);
            return;
         }

         float f9 = (float)(Mth.m_14136_(d1, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
         this.f_24974_.m_146922_(this.m_24991_(this.f_24974_.m_146908_(), f9, 90.0F));
         this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_)));
         BlockPos blockpos = this.f_24974_.m_142538_();
         BlockState blockstate = this.f_24974_.f_19853_.m_8055_(blockpos);
         VoxelShape voxelshape = blockstate.m_60812_(this.f_24974_.f_19853_, blockpos);
         if (d2 > (double)this.f_24974_.f_19793_ && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.f_24974_.m_20205_()) || !voxelshape.m_83281_() && this.f_24974_.m_20186_() < voxelshape.m_83297_(Direction.Axis.Y) + (double)blockpos.m_123342_() && !blockstate.m_60620_(BlockTags.f_13103_) && !blockstate.m_60620_(BlockTags.f_13039_)) {
            this.f_24974_.m_21569_().m_24901_();
            this.f_24981_ = MoveControl.Operation.JUMPING;
         }
      } else if (this.f_24981_ == MoveControl.Operation.JUMPING) {
         this.f_24974_.m_7910_((float)(this.f_24978_ * this.f_24974_.m_21133_(Attributes.f_22279_)));
         if (this.f_24974_.m_20096_()) {
            this.f_24981_ = MoveControl.Operation.WAIT;
         }
      } else {
         this.f_24974_.m_21564_(0.0F);
      }

   }

   private boolean m_24996_(float p_24997_, float p_24998_) {
      PathNavigation pathnavigation = this.f_24974_.m_21573_();
      if (pathnavigation != null) {
         NodeEvaluator nodeevaluator = pathnavigation.m_26575_();
         if (nodeevaluator != null && nodeevaluator.m_8086_(this.f_24974_.f_19853_, Mth.m_14107_(this.f_24974_.m_20185_() + (double)p_24997_), this.f_24974_.m_146904_(), Mth.m_14107_(this.f_24974_.m_20189_() + (double)p_24998_)) != BlockPathTypes.WALKABLE) {
            return false;
         }
      }

      return true;
   }

   protected float m_24991_(float p_24992_, float p_24993_, float p_24994_) {
      float f = Mth.m_14177_(p_24993_ - p_24992_);
      if (f > p_24994_) {
         f = p_24994_;
      }

      if (f < -p_24994_) {
         f = -p_24994_;
      }

      float f1 = p_24992_ + f;
      if (f1 < 0.0F) {
         f1 += 360.0F;
      } else if (f1 > 360.0F) {
         f1 -= 360.0F;
      }

      return f1;
   }

   public double m_25000_() {
      return this.f_24975_;
   }

   public double m_25001_() {
      return this.f_24976_;
   }

   public double m_25002_() {
      return this.f_24977_;
   }

   protected static enum Operation {
      WAIT,
      MOVE_TO,
      STRAFE,
      JUMPING;
   }
}