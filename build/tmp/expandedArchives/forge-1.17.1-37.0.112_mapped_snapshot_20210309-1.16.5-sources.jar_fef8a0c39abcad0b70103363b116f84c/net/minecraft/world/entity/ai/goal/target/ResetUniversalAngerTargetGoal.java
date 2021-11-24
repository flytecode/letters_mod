package net.minecraft.world.entity.ai.goal.target;

import java.util.List;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;

public class ResetUniversalAngerTargetGoal<T extends Mob & NeutralMob> extends Goal {
   private static final int f_148154_ = 10;
   private final T f_26117_;
   private final boolean f_26118_;
   private int f_26119_;

   public ResetUniversalAngerTargetGoal(T p_26121_, boolean p_26122_) {
      this.f_26117_ = p_26121_;
      this.f_26118_ = p_26122_;
   }

   public boolean m_8036_() {
      return this.f_26117_.f_19853_.m_46469_().m_46207_(GameRules.f_46127_) && this.m_26129_();
   }

   private boolean m_26129_() {
      return this.f_26117_.m_142581_() != null && this.f_26117_.m_142581_().m_6095_() == EntityType.f_20532_ && this.f_26117_.m_21213_() > this.f_26119_;
   }

   public void m_8056_() {
      this.f_26119_ = this.f_26117_.m_21213_();
      this.f_26117_.m_21661_();
      if (this.f_26118_) {
         this.m_26130_().stream().filter((p_26127_) -> {
            return p_26127_ != this.f_26117_;
         }).map((p_26125_) -> {
            return (NeutralMob)p_26125_;
         }).forEach(NeutralMob::m_21661_);
      }

      super.m_8056_();
   }

   private List<? extends Mob> m_26130_() {
      double d0 = this.f_26117_.m_21133_(Attributes.f_22277_);
      AABB aabb = AABB.m_82333_(this.f_26117_.m_20182_()).m_82377_(d0, 10.0D, d0);
      return this.f_26117_.f_19853_.m_6443_(this.f_26117_.getClass(), aabb, EntitySelector.f_20408_);
   }
}