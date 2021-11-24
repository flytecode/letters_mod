package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class MoveTowardsRestrictionGoal extends Goal {
   private final PathfinderMob f_25627_;
   private double f_25628_;
   private double f_25629_;
   private double f_25630_;
   private final double f_25631_;

   public MoveTowardsRestrictionGoal(PathfinderMob p_25633_, double p_25634_) {
      this.f_25627_ = p_25633_;
      this.f_25631_ = p_25634_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.f_25627_.m_21533_()) {
         return false;
      } else {
         Vec3 vec3 = DefaultRandomPos.m_148412_(this.f_25627_, 16, 7, Vec3.m_82539_(this.f_25627_.m_21534_()), (double)((float)Math.PI / 2F));
         if (vec3 == null) {
            return false;
         } else {
            this.f_25628_ = vec3.f_82479_;
            this.f_25629_ = vec3.f_82480_;
            this.f_25630_ = vec3.f_82481_;
            return true;
         }
      }
   }

   public boolean m_8045_() {
      return !this.f_25627_.m_21573_().m_26571_();
   }

   public void m_8056_() {
      this.f_25627_.m_21573_().m_26519_(this.f_25628_, this.f_25629_, this.f_25630_, this.f_25631_);
   }
}