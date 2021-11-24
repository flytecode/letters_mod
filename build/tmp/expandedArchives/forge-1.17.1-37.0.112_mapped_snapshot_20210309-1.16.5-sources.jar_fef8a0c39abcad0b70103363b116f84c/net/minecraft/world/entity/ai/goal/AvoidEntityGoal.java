package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class AvoidEntityGoal<T extends LivingEntity> extends Goal {
   protected final PathfinderMob f_25015_;
   private final double f_25023_;
   private final double f_25024_;
   protected T f_25016_;
   protected final float f_25017_;
   protected Path f_25018_;
   protected final PathNavigation f_25019_;
   protected final Class<T> f_25020_;
   protected final Predicate<LivingEntity> f_25021_;
   protected final Predicate<LivingEntity> f_25022_;
   private final TargetingConditions f_25025_;

   public AvoidEntityGoal(PathfinderMob p_25027_, Class<T> p_25028_, float p_25029_, double p_25030_, double p_25031_) {
      this(p_25027_, p_25028_, (p_25052_) -> {
         return true;
      }, p_25029_, p_25030_, p_25031_, EntitySelector.f_20406_::test);
   }

   public AvoidEntityGoal(PathfinderMob p_25040_, Class<T> p_25041_, Predicate<LivingEntity> p_25042_, float p_25043_, double p_25044_, double p_25045_, Predicate<LivingEntity> p_25046_) {
      this.f_25015_ = p_25040_;
      this.f_25020_ = p_25041_;
      this.f_25021_ = p_25042_;
      this.f_25017_ = p_25043_;
      this.f_25023_ = p_25044_;
      this.f_25024_ = p_25045_;
      this.f_25022_ = p_25046_;
      this.f_25019_ = p_25040_.m_21573_();
      this.m_7021_(EnumSet.of(Goal.Flag.MOVE));
      this.f_25025_ = TargetingConditions.m_148352_().m_26883_((double)p_25043_).m_26888_(p_25046_.and(p_25042_));
   }

   public AvoidEntityGoal(PathfinderMob p_25033_, Class<T> p_25034_, float p_25035_, double p_25036_, double p_25037_, Predicate<LivingEntity> p_25038_) {
      this(p_25033_, p_25034_, (p_25049_) -> {
         return true;
      }, p_25035_, p_25036_, p_25037_, p_25038_);
   }

   public boolean m_8036_() {
      this.f_25016_ = this.f_25015_.f_19853_.m_45982_(this.f_25015_.f_19853_.m_6443_(this.f_25020_, this.f_25015_.m_142469_().m_82377_((double)this.f_25017_, 3.0D, (double)this.f_25017_), (p_148078_) -> {
         return true;
      }), this.f_25025_, this.f_25015_, this.f_25015_.m_20185_(), this.f_25015_.m_20186_(), this.f_25015_.m_20189_());
      if (this.f_25016_ == null) {
         return false;
      } else {
         Vec3 vec3 = DefaultRandomPos.m_148407_(this.f_25015_, 16, 7, this.f_25016_.m_20182_());
         if (vec3 == null) {
            return false;
         } else if (this.f_25016_.m_20275_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_) < this.f_25016_.m_20280_(this.f_25015_)) {
            return false;
         } else {
            this.f_25018_ = this.f_25019_.m_26524_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 0);
            return this.f_25018_ != null;
         }
      }
   }

   public boolean m_8045_() {
      return !this.f_25019_.m_26571_();
   }

   public void m_8056_() {
      this.f_25019_.m_26536_(this.f_25018_, this.f_25023_);
   }

   public void m_8041_() {
      this.f_25016_ = null;
   }

   public void m_8037_() {
      if (this.f_25015_.m_20280_(this.f_25016_) < 49.0D) {
         this.f_25015_.m_21573_().m_26517_(this.f_25024_);
      } else {
         this.f_25015_.m_21573_().m_26517_(this.f_25023_);
      }

   }
}