package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.Mob;

public class RandomLookAroundGoal extends Goal {
   private final Mob f_25715_;
   private double f_25716_;
   private double f_25717_;
   private int f_25718_;

   public RandomLookAroundGoal(Mob p_25720_) {
      this.f_25715_ = p_25720_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean m_8036_() {
      return this.f_25715_.m_21187_().nextFloat() < 0.02F;
   }

   public boolean m_8045_() {
      return this.f_25718_ >= 0;
   }

   public void m_8056_() {
      double d0 = (Math.PI * 2D) * this.f_25715_.m_21187_().nextDouble();
      this.f_25716_ = Math.cos(d0);
      this.f_25717_ = Math.sin(d0);
      this.f_25718_ = 20 + this.f_25715_.m_21187_().nextInt(20);
   }

   public void m_8037_() {
      --this.f_25718_;
      this.f_25715_.m_21563_().m_24946_(this.f_25715_.m_20185_() + this.f_25716_, this.f_25715_.m_20188_(), this.f_25715_.m_20189_() + this.f_25717_);
   }
}