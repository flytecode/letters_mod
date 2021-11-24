package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class FollowOwnerGoal extends Goal {
   public static final int f_148087_ = 12;
   private static final int f_148088_ = 2;
   private static final int f_148089_ = 3;
   private static final int f_148090_ = 1;
   private final TamableAnimal f_25283_;
   private LivingEntity f_25284_;
   private final LevelReader f_25285_;
   private final double f_25286_;
   private final PathNavigation f_25287_;
   private int f_25288_;
   private final float f_25289_;
   private final float f_25290_;
   private float f_25291_;
   private final boolean f_25292_;

   public FollowOwnerGoal(TamableAnimal p_25294_, double p_25295_, float p_25296_, float p_25297_, boolean p_25298_) {
      this.f_25283_ = p_25294_;
      this.f_25285_ = p_25294_.f_19853_;
      this.f_25286_ = p_25295_;
      this.f_25287_ = p_25294_.m_21573_();
      this.f_25290_ = p_25296_;
      this.f_25289_ = p_25297_;
      this.f_25292_ = p_25298_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      if (!(p_25294_.m_21573_() instanceof GroundPathNavigation) && !(p_25294_.m_21573_() instanceof FlyingPathNavigation)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
      }
   }

   public boolean m_8036_() {
      LivingEntity livingentity = this.f_25283_.m_142480_();
      if (livingentity == null) {
         return false;
      } else if (livingentity.m_5833_()) {
         return false;
      } else if (this.f_25283_.m_21827_()) {
         return false;
      } else if (this.f_25283_.m_20280_(livingentity) < (double)(this.f_25290_ * this.f_25290_)) {
         return false;
      } else {
         this.f_25284_ = livingentity;
         return true;
      }
   }

   public boolean m_8045_() {
      if (this.f_25287_.m_26571_()) {
         return false;
      } else if (this.f_25283_.m_21827_()) {
         return false;
      } else {
         return !(this.f_25283_.m_20280_(this.f_25284_) <= (double)(this.f_25289_ * this.f_25289_));
      }
   }

   public void m_8056_() {
      this.f_25288_ = 0;
      this.f_25291_ = this.f_25283_.m_21439_(BlockPathTypes.WATER);
      this.f_25283_.m_21441_(BlockPathTypes.WATER, 0.0F);
   }

   public void m_8041_() {
      this.f_25284_ = null;
      this.f_25287_.m_26573_();
      this.f_25283_.m_21441_(BlockPathTypes.WATER, this.f_25291_);
   }

   public void m_8037_() {
      this.f_25283_.m_21563_().m_24960_(this.f_25284_, 10.0F, (float)this.f_25283_.m_8132_());
      if (--this.f_25288_ <= 0) {
         this.f_25288_ = 10;
         if (!this.f_25283_.m_21523_() && !this.f_25283_.m_20159_()) {
            if (this.f_25283_.m_20280_(this.f_25284_) >= 144.0D) {
               this.m_25313_();
            } else {
               this.f_25287_.m_5624_(this.f_25284_, this.f_25286_);
            }

         }
      }
   }

   private void m_25313_() {
      BlockPos blockpos = this.f_25284_.m_142538_();

      for(int i = 0; i < 10; ++i) {
         int j = this.m_25300_(-3, 3);
         int k = this.m_25300_(-1, 1);
         int l = this.m_25300_(-3, 3);
         boolean flag = this.m_25303_(blockpos.m_123341_() + j, blockpos.m_123342_() + k, blockpos.m_123343_() + l);
         if (flag) {
            return;
         }
      }

   }

   private boolean m_25303_(int p_25304_, int p_25305_, int p_25306_) {
      if (Math.abs((double)p_25304_ - this.f_25284_.m_20185_()) < 2.0D && Math.abs((double)p_25306_ - this.f_25284_.m_20189_()) < 2.0D) {
         return false;
      } else if (!this.m_25307_(new BlockPos(p_25304_, p_25305_, p_25306_))) {
         return false;
      } else {
         this.f_25283_.m_7678_((double)p_25304_ + 0.5D, (double)p_25305_, (double)p_25306_ + 0.5D, this.f_25283_.m_146908_(), this.f_25283_.m_146909_());
         this.f_25287_.m_26573_();
         return true;
      }
   }

   private boolean m_25307_(BlockPos p_25308_) {
      BlockPathTypes blockpathtypes = WalkNodeEvaluator.m_77604_(this.f_25285_, p_25308_.m_122032_());
      if (blockpathtypes != BlockPathTypes.WALKABLE) {
         return false;
      } else {
         BlockState blockstate = this.f_25285_.m_8055_(p_25308_.m_7495_());
         if (!this.f_25292_ && blockstate.m_60734_() instanceof LeavesBlock) {
            return false;
         } else {
            BlockPos blockpos = p_25308_.m_141950_(this.f_25283_.m_142538_());
            return this.f_25285_.m_45756_(this.f_25283_, this.f_25283_.m_142469_().m_82338_(blockpos));
         }
      }
   }

   private int m_25300_(int p_25301_, int p_25302_) {
      return this.f_25283_.m_21187_().nextInt(p_25302_ - p_25301_ + 1) + p_25301_;
   }
}