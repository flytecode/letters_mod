package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;

public class LeapAtTargetGoal extends Goal {
   private final Mob f_25488_;
   private LivingEntity f_25489_;
   private final float f_25490_;

   public LeapAtTargetGoal(Mob p_25492_, float p_25493_) {
      this.f_25488_ = p_25492_;
      this.f_25490_ = p_25493_;
      this.m_7021_(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.f_25488_.m_20160_()) {
         return false;
      } else {
         this.f_25489_ = this.f_25488_.m_5448_();
         if (this.f_25489_ == null) {
            return false;
         } else {
            double d0 = this.f_25488_.m_20280_(this.f_25489_);
            if (!(d0 < 4.0D) && !(d0 > 16.0D)) {
               if (!this.f_25488_.m_20096_()) {
                  return false;
               } else {
                  return this.f_25488_.m_21187_().nextInt(5) == 0;
               }
            } else {
               return false;
            }
         }
      }
   }

   public boolean m_8045_() {
      return !this.f_25488_.m_20096_();
   }

   public void m_8056_() {
      Vec3 vec3 = this.f_25488_.m_20184_();
      Vec3 vec31 = new Vec3(this.f_25489_.m_20185_() - this.f_25488_.m_20185_(), 0.0D, this.f_25489_.m_20189_() - this.f_25488_.m_20189_());
      if (vec31.m_82556_() > 1.0E-7D) {
         vec31 = vec31.m_82541_().m_82490_(0.4D).m_82549_(vec3.m_82490_(0.2D));
      }

      this.f_25488_.m_20334_(vec31.f_82479_, (double)this.f_25490_, vec31.f_82481_);
   }
}