package net.minecraft.world.entity.ai.goal.target;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.phys.AABB;

public class HurtByTargetGoal extends TargetGoal {
   private static final TargetingConditions f_26032_ = TargetingConditions.m_148352_().m_148355_().m_26893_();
   private static final int f_148150_ = 10;
   private boolean f_26033_;
   private int f_26034_;
   private final Class<?>[] f_26035_;
   private Class<?>[] f_26036_;

   public HurtByTargetGoal(PathfinderMob p_26039_, Class<?>... p_26040_) {
      super(p_26039_, true);
      this.f_26035_ = p_26040_;
      this.m_7021_(EnumSet.of(Goal.Flag.TARGET));
   }

   public boolean m_8036_() {
      int i = this.f_26135_.m_21213_();
      LivingEntity livingentity = this.f_26135_.m_142581_();
      if (i != this.f_26034_ && livingentity != null) {
         if (livingentity.m_6095_() == EntityType.f_20532_ && this.f_26135_.f_19853_.m_46469_().m_46207_(GameRules.f_46127_)) {
            return false;
         } else {
            for(Class<?> oclass : this.f_26035_) {
               if (oclass.isAssignableFrom(livingentity.getClass())) {
                  return false;
               }
            }

            return this.m_26150_(livingentity, f_26032_);
         }
      } else {
         return false;
      }
   }

   public HurtByTargetGoal m_26044_(Class<?>... p_26045_) {
      this.f_26033_ = true;
      this.f_26036_ = p_26045_;
      return this;
   }

   public void m_8056_() {
      this.f_26135_.m_6710_(this.f_26135_.m_142581_());
      this.f_26137_ = this.f_26135_.m_5448_();
      this.f_26034_ = this.f_26135_.m_21213_();
      this.f_26138_ = 300;
      if (this.f_26033_) {
         this.m_26047_();
      }

      super.m_8056_();
   }

   protected void m_26047_() {
      double d0 = this.m_7623_();
      AABB aabb = AABB.m_82333_(this.f_26135_.m_20182_()).m_82377_(d0, 10.0D, d0);
      List<? extends Mob> list = this.f_26135_.f_19853_.m_6443_(this.f_26135_.getClass(), aabb, EntitySelector.f_20408_);
      Iterator iterator = list.iterator();

      while(true) {
         Mob mob;
         while(true) {
            if (!iterator.hasNext()) {
               return;
            }

            mob = (Mob)iterator.next();
            if (this.f_26135_ != mob && mob.m_5448_() == null && (!(this.f_26135_ instanceof TamableAnimal) || ((TamableAnimal)this.f_26135_).m_142480_() == ((TamableAnimal)mob).m_142480_()) && !mob.m_7307_(this.f_26135_.m_142581_())) {
               if (this.f_26036_ == null) {
                  break;
               }

               boolean flag = false;

               for(Class<?> oclass : this.f_26036_) {
                  if (mob.getClass() == oclass) {
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  break;
               }
            }
         }

         this.m_5766_(mob, this.f_26135_.m_142581_());
      }
   }

   protected void m_5766_(Mob p_26042_, LivingEntity p_26043_) {
      p_26042_.m_6710_(p_26043_);
   }
}