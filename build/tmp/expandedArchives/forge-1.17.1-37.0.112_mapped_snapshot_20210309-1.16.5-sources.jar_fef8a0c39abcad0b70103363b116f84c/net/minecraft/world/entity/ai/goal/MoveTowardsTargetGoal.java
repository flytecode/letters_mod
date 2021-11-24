package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class MoveTowardsTargetGoal extends Goal {
   private final PathfinderMob f_25638_;
   private LivingEntity f_25639_;
   private double f_25640_;
   private double f_25641_;
   private double f_25642_;
   private final double f_25643_;
   private final float f_25644_;

   public MoveTowardsTargetGoal(PathfinderMob p_25646_, double p_25647_, float p_25648_) {
      this.f_25638_ = p_25646_;
      this.f_25643_ = p_25647_;
      this.f_25644_ = p_25648_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean m_8036_() {
      this.f_25639_ = this.f_25638_.m_5448_();
      if (this.f_25639_ == null) {
         return false;
      } else if (this.f_25639_.m_20280_(this.f_25638_) > (double)(this.f_25644_ * this.f_25644_)) {
         return false;
      } else {
         Vec3 vec3 = DefaultRandomPos.m_148412_(this.f_25638_, 16, 7, this.f_25639_.m_20182_(), (double)((float)Math.PI / 2F));
         if (vec3 == null) {
            return false;
         } else {
            this.f_25640_ = vec3.f_82479_;
            this.f_25641_ = vec3.f_82480_;
            this.f_25642_ = vec3.f_82481_;
            return true;
         }
      }
   }

   public boolean m_8045_() {
      return !this.f_25638_.m_21573_().m_26571_() && this.f_25639_.m_6084_() && this.f_25639_.m_20280_(this.f_25638_) < (double)(this.f_25644_ * this.f_25644_);
   }

   public void m_8041_() {
      this.f_25639_ = null;
   }

   public void m_8056_() {
      this.f_25638_.m_21573_().m_26519_(this.f_25640_, this.f_25641_, this.f_25642_, this.f_25643_);
   }
}