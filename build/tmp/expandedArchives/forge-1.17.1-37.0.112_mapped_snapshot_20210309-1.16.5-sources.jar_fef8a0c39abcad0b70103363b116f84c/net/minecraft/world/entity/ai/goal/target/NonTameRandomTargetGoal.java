package net.minecraft.world.entity.ai.goal.target;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;

public class NonTameRandomTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
   private final TamableAnimal f_26095_;

   public NonTameRandomTargetGoal(TamableAnimal p_26097_, Class<T> p_26098_, boolean p_26099_, @Nullable Predicate<LivingEntity> p_26100_) {
      super(p_26097_, p_26098_, 10, p_26099_, false, p_26100_);
      this.f_26095_ = p_26097_;
   }

   public boolean m_8036_() {
      return !this.f_26095_.m_21824_() && super.m_8036_();
   }

   public boolean m_8045_() {
      return this.f_26051_ != null ? this.f_26051_.m_26885_(this.f_26135_, this.f_26050_) : super.m_8045_();
   }
}