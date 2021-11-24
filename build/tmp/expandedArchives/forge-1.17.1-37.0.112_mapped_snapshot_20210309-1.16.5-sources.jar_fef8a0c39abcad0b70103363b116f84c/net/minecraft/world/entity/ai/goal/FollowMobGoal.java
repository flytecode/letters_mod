package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class FollowMobGoal extends Goal {
   private final Mob f_25261_;
   private final Predicate<Mob> f_25262_;
   private Mob f_25263_;
   private final double f_25264_;
   private final PathNavigation f_25265_;
   private int f_25266_;
   private final float f_25267_;
   private float f_25268_;
   private final float f_25269_;

   public FollowMobGoal(Mob p_25271_, double p_25272_, float p_25273_, float p_25274_) {
      this.f_25261_ = p_25271_;
      this.f_25262_ = (p_25278_) -> {
         return p_25278_ != null && p_25271_.getClass() != p_25278_.getClass();
      };
      this.f_25264_ = p_25272_;
      this.f_25265_ = p_25271_.m_21573_();
      this.f_25267_ = p_25273_;
      this.f_25269_ = p_25274_;
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
      if (!(p_25271_.m_21573_() instanceof GroundPathNavigation) && !(p_25271_.m_21573_() instanceof FlyingPathNavigation)) {
         throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
      }
   }

   public boolean m_8036_() {
      List<Mob> list = this.f_25261_.f_19853_.m_6443_(Mob.class, this.f_25261_.m_142469_().m_82400_((double)this.f_25269_), this.f_25262_);
      if (!list.isEmpty()) {
         for(Mob mob : list) {
            if (!mob.m_20145_()) {
               this.f_25263_ = mob;
               return true;
            }
         }
      }

      return false;
   }

   public boolean m_8045_() {
      return this.f_25263_ != null && !this.f_25265_.m_26571_() && this.f_25261_.m_20280_(this.f_25263_) > (double)(this.f_25267_ * this.f_25267_);
   }

   public void m_8056_() {
      this.f_25266_ = 0;
      this.f_25268_ = this.f_25261_.m_21439_(BlockPathTypes.WATER);
      this.f_25261_.m_21441_(BlockPathTypes.WATER, 0.0F);
   }

   public void m_8041_() {
      this.f_25263_ = null;
      this.f_25265_.m_26573_();
      this.f_25261_.m_21441_(BlockPathTypes.WATER, this.f_25268_);
   }

   public void m_8037_() {
      if (this.f_25263_ != null && !this.f_25261_.m_21523_()) {
         this.f_25261_.m_21563_().m_24960_(this.f_25263_, 10.0F, (float)this.f_25261_.m_8132_());
         if (--this.f_25266_ <= 0) {
            this.f_25266_ = 10;
            double d0 = this.f_25261_.m_20185_() - this.f_25263_.m_20185_();
            double d1 = this.f_25261_.m_20186_() - this.f_25263_.m_20186_();
            double d2 = this.f_25261_.m_20189_() - this.f_25263_.m_20189_();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (!(d3 <= (double)(this.f_25267_ * this.f_25267_))) {
               this.f_25265_.m_5624_(this.f_25263_, this.f_25264_);
            } else {
               this.f_25265_.m_26573_();
               LookControl lookcontrol = this.f_25263_.m_21563_();
               if (d3 <= (double)this.f_25267_ || lookcontrol.m_24969_() == this.f_25261_.m_20185_() && lookcontrol.m_24970_() == this.f_25261_.m_20186_() && lookcontrol.m_24971_() == this.f_25261_.m_20189_()) {
                  double d4 = this.f_25263_.m_20185_() - this.f_25261_.m_20185_();
                  double d5 = this.f_25263_.m_20189_() - this.f_25261_.m_20189_();
                  this.f_25265_.m_26519_(this.f_25261_.m_20185_() - d4, this.f_25261_.m_20186_(), this.f_25261_.m_20189_() - d5, this.f_25264_);
               }

            }
         }
      }
   }
}