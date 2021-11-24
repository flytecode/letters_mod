package net.minecraft.world.entity.ai.goal.target;

import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.scores.Team;

public abstract class TargetGoal extends Goal {
   private static final int f_148155_ = 0;
   private static final int f_148156_ = 1;
   private static final int f_148157_ = 2;
   protected final Mob f_26135_;
   protected final boolean f_26136_;
   private final boolean f_26131_;
   private int f_26132_;
   private int f_26133_;
   private int f_26134_;
   protected LivingEntity f_26137_;
   protected int f_26138_ = 60;

   public TargetGoal(Mob p_26140_, boolean p_26141_) {
      this(p_26140_, p_26141_, false);
   }

   public TargetGoal(Mob p_26143_, boolean p_26144_, boolean p_26145_) {
      this.f_26135_ = p_26143_;
      this.f_26136_ = p_26144_;
      this.f_26131_ = p_26145_;
   }

   public boolean m_8045_() {
      LivingEntity livingentity = this.f_26135_.m_5448_();
      if (livingentity == null) {
         livingentity = this.f_26137_;
      }

      if (livingentity == null) {
         return false;
      } else if (!this.f_26135_.m_6779_(livingentity)) {
         return false;
      } else {
         Team team = this.f_26135_.m_5647_();
         Team team1 = livingentity.m_5647_();
         if (team != null && team1 == team) {
            return false;
         } else {
            double d0 = this.m_7623_();
            if (this.f_26135_.m_20280_(livingentity) > d0 * d0) {
               return false;
            } else {
               if (this.f_26136_) {
                  if (this.f_26135_.m_21574_().m_148306_(livingentity)) {
                     this.f_26134_ = 0;
                  } else if (++this.f_26134_ > this.f_26138_) {
                     return false;
                  }
               }

               this.f_26135_.m_6710_(livingentity);
               return true;
            }
         }
      }
   }

   protected double m_7623_() {
      return this.f_26135_.m_21133_(Attributes.f_22277_);
   }

   public void m_8056_() {
      this.f_26132_ = 0;
      this.f_26133_ = 0;
      this.f_26134_ = 0;
   }

   public void m_8041_() {
      this.f_26135_.m_6710_((LivingEntity)null);
      this.f_26137_ = null;
   }

   protected boolean m_26150_(@Nullable LivingEntity p_26151_, TargetingConditions p_26152_) {
      if (p_26151_ == null) {
         return false;
      } else if (!p_26152_.m_26885_(this.f_26135_, p_26151_)) {
         return false;
      } else if (!this.f_26135_.m_21444_(p_26151_.m_142538_())) {
         return false;
      } else {
         if (this.f_26131_) {
            if (--this.f_26133_ <= 0) {
               this.f_26132_ = 0;
            }

            if (this.f_26132_ == 0) {
               this.f_26132_ = this.m_26148_(p_26151_) ? 1 : 2;
            }

            if (this.f_26132_ == 2) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean m_26148_(LivingEntity p_26149_) {
      this.f_26133_ = 10 + this.f_26135_.m_21187_().nextInt(5);
      Path path = this.f_26135_.m_21573_().m_6570_(p_26149_, 0);
      if (path == null) {
         return false;
      } else {
         Node node = path.m_77395_();
         if (node == null) {
            return false;
         } else {
            int i = node.f_77271_ - p_26149_.m_146903_();
            int j = node.f_77273_ - p_26149_.m_146907_();
            return (double)(i * i + j * j) <= 2.25D;
         }
      }
   }

   public TargetGoal m_26146_(int p_26147_) {
      this.f_26138_ = p_26147_;
      return this;
   }
}