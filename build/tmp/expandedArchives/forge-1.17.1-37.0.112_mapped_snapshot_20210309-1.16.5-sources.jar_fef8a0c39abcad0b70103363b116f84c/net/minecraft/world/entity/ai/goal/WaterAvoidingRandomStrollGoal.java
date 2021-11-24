package net.minecraft.world.entity.ai.goal;

import javax.annotation.Nullable;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

public class WaterAvoidingRandomStrollGoal extends RandomStrollGoal {
   public static final float f_148149_ = 0.001F;
   protected final float f_25985_;

   public WaterAvoidingRandomStrollGoal(PathfinderMob p_25987_, double p_25988_) {
      this(p_25987_, p_25988_, 0.001F);
   }

   public WaterAvoidingRandomStrollGoal(PathfinderMob p_25990_, double p_25991_, float p_25992_) {
      super(p_25990_, p_25991_);
      this.f_25985_ = p_25992_;
   }

   @Nullable
   protected Vec3 m_7037_() {
      if (this.f_25725_.m_20072_()) {
         Vec3 vec3 = LandRandomPos.m_148488_(this.f_25725_, 15, 7);
         return vec3 == null ? super.m_7037_() : vec3;
      } else {
         return this.f_25725_.m_21187_().nextFloat() >= this.f_25985_ ? LandRandomPos.m_148488_(this.f_25725_, 10, 7) : super.m_7037_();
      }
   }
}