package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;

public class OcelotAttackGoal extends Goal {
   private final BlockGetter f_25653_;
   private final Mob f_25654_;
   private LivingEntity f_25655_;
   private int f_25656_;

   public OcelotAttackGoal(Mob p_25658_) {
      this.f_25654_ = p_25658_;
      this.f_25653_ = p_25658_.f_19853_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
   }

   public boolean m_8036_() {
      LivingEntity livingentity = this.f_25654_.m_5448_();
      if (livingentity == null) {
         return false;
      } else {
         this.f_25655_ = livingentity;
         return true;
      }
   }

   public boolean m_8045_() {
      if (!this.f_25655_.m_6084_()) {
         return false;
      } else if (this.f_25654_.m_20280_(this.f_25655_) > 225.0D) {
         return false;
      } else {
         return !this.f_25654_.m_21573_().m_26571_() || this.m_8036_();
      }
   }

   public void m_8041_() {
      this.f_25655_ = null;
      this.f_25654_.m_21573_().m_26573_();
   }

   public void m_8037_() {
      this.f_25654_.m_21563_().m_24960_(this.f_25655_, 30.0F, 30.0F);
      double d0 = (double)(this.f_25654_.m_20205_() * 2.0F * this.f_25654_.m_20205_() * 2.0F);
      double d1 = this.f_25654_.m_20275_(this.f_25655_.m_20185_(), this.f_25655_.m_20186_(), this.f_25655_.m_20189_());
      double d2 = 0.8D;
      if (d1 > d0 && d1 < 16.0D) {
         d2 = 1.33D;
      } else if (d1 < 225.0D) {
         d2 = 0.6D;
      }

      this.f_25654_.m_21573_().m_5624_(this.f_25655_, d2);
      this.f_25656_ = Math.max(this.f_25656_ - 1, 0);
      if (!(d1 > d0)) {
         if (this.f_25656_ <= 0) {
            this.f_25656_ = 20;
            this.f_25654_.m_7327_(this.f_25655_);
         }
      }
   }
}