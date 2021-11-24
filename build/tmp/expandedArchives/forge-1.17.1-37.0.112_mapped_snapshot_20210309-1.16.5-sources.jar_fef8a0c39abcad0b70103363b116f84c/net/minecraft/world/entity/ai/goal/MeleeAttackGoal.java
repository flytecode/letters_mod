package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class MeleeAttackGoal extends Goal {
   protected final PathfinderMob f_25540_;
   private final double f_25541_;
   private final boolean f_25542_;
   private Path f_25543_;
   private double f_25544_;
   private double f_25545_;
   private double f_25546_;
   private int f_25547_;
   private int f_25548_;
   private final int f_25549_ = 20;
   private long f_25550_;
   private static final long f_148125_ = 20L;
   private int failedPathFindingPenalty = 0;
   private boolean canPenalize = false;

   public MeleeAttackGoal(PathfinderMob p_25552_, double p_25553_, boolean p_25554_) {
      this.f_25540_ = p_25552_;
      this.f_25541_ = p_25553_;
      this.f_25542_ = p_25554_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean m_8036_() {
      long i = this.f_25540_.f_19853_.m_46467_();
      if (i - this.f_25550_ < 20L) {
         return false;
      } else {
         this.f_25550_ = i;
         LivingEntity livingentity = this.f_25540_.m_5448_();
         if (livingentity == null) {
            return false;
         } else if (!livingentity.m_6084_()) {
            return false;
         } else {
           if (canPenalize) {
               if (--this.f_25547_ <= 0) {
                  this.f_25543_ = this.f_25540_.m_21573_().m_6570_(livingentity, 0);
                  this.f_25547_ = 4 + this.f_25540_.m_21187_().nextInt(7);
                  return this.f_25543_ != null;
               } else {
                  return true;
               }
            }
            this.f_25543_ = this.f_25540_.m_21573_().m_6570_(livingentity, 0);
            if (this.f_25543_ != null) {
               return true;
            } else {
               return this.m_6639_(livingentity) >= this.f_25540_.m_20275_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_());
            }
         }
      }
   }

   public boolean m_8045_() {
      LivingEntity livingentity = this.f_25540_.m_5448_();
      if (livingentity == null) {
         return false;
      } else if (!livingentity.m_6084_()) {
         return false;
      } else if (!this.f_25542_) {
         return !this.f_25540_.m_21573_().m_26571_();
      } else if (!this.f_25540_.m_21444_(livingentity.m_142538_())) {
         return false;
      } else {
         return !(livingentity instanceof Player) || !livingentity.m_5833_() && !((Player)livingentity).m_7500_();
      }
   }

   public void m_8056_() {
      this.f_25540_.m_21573_().m_26536_(this.f_25543_, this.f_25541_);
      this.f_25540_.m_21561_(true);
      this.f_25547_ = 0;
      this.f_25548_ = 0;
   }

   public void m_8041_() {
      LivingEntity livingentity = this.f_25540_.m_5448_();
      if (!EntitySelector.f_20406_.test(livingentity)) {
         this.f_25540_.m_6710_((LivingEntity)null);
      }

      this.f_25540_.m_21561_(false);
      this.f_25540_.m_21573_().m_26573_();
   }

   public void m_8037_() {
      LivingEntity livingentity = this.f_25540_.m_5448_();
      this.f_25540_.m_21563_().m_24960_(livingentity, 30.0F, 30.0F);
      double d0 = this.f_25540_.m_20275_(livingentity.m_20185_(), livingentity.m_20186_(), livingentity.m_20189_());
      this.f_25547_ = Math.max(this.f_25547_ - 1, 0);
      if ((this.f_25542_ || this.f_25540_.m_21574_().m_148306_(livingentity)) && this.f_25547_ <= 0 && (this.f_25544_ == 0.0D && this.f_25545_ == 0.0D && this.f_25546_ == 0.0D || livingentity.m_20275_(this.f_25544_, this.f_25545_, this.f_25546_) >= 1.0D || this.f_25540_.m_21187_().nextFloat() < 0.05F)) {
         this.f_25544_ = livingentity.m_20185_();
         this.f_25545_ = livingentity.m_20186_();
         this.f_25546_ = livingentity.m_20189_();
         this.f_25547_ = 4 + this.f_25540_.m_21187_().nextInt(7);
         if (this.canPenalize) {
            this.f_25547_ += failedPathFindingPenalty;
            if (this.f_25540_.m_21573_().m_26570_() != null) {
               net.minecraft.world.level.pathfinder.Node finalPathPoint = this.f_25540_.m_21573_().m_26570_().m_77395_();
               if (finalPathPoint != null && livingentity.m_20275_(finalPathPoint.f_77271_, finalPathPoint.f_77272_, finalPathPoint.f_77273_) < 1)
                  failedPathFindingPenalty = 0;
               else
                  failedPathFindingPenalty += 10;
            } else {
               failedPathFindingPenalty += 10;
            }
         }
         if (d0 > 1024.0D) {
            this.f_25547_ += 10;
         } else if (d0 > 256.0D) {
            this.f_25547_ += 5;
         }

         if (!this.f_25540_.m_21573_().m_5624_(livingentity, this.f_25541_)) {
            this.f_25547_ += 15;
         }
      }

      this.f_25548_ = Math.max(this.f_25548_ - 1, 0);
      this.m_6739_(livingentity, d0);
   }

   protected void m_6739_(LivingEntity p_25557_, double p_25558_) {
      double d0 = this.m_6639_(p_25557_);
      if (p_25558_ <= d0 && this.f_25548_ <= 0) {
         this.m_25563_();
         this.f_25540_.m_6674_(InteractionHand.MAIN_HAND);
         this.f_25540_.m_7327_(p_25557_);
      }

   }

   protected void m_25563_() {
      this.f_25548_ = 20;
   }

   protected boolean m_25564_() {
      return this.f_25548_ <= 0;
   }

   protected int m_25565_() {
      return this.f_25548_;
   }

   protected int m_25566_() {
      return 20;
   }

   protected double m_6639_(LivingEntity p_25556_) {
      return (double)(this.f_25540_.m_20205_() * 2.0F * this.f_25540_.m_20205_() * 2.0F + p_25556_.m_20205_());
   }
}
