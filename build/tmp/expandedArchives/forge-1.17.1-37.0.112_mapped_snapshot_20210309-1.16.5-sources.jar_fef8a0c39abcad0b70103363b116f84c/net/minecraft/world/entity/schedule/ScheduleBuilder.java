package net.minecraft.world.entity.schedule;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleBuilder {
   private final Schedule f_38035_;
   private final List<ScheduleBuilder.ActivityTransition> f_38036_ = Lists.newArrayList();

   public ScheduleBuilder(Schedule p_38038_) {
      this.f_38035_ = p_38038_;
   }

   public ScheduleBuilder m_38040_(int p_38041_, Activity p_38042_) {
      this.f_38036_.add(new ScheduleBuilder.ActivityTransition(p_38041_, p_38042_));
      return this;
   }

   public Schedule m_38039_() {
      this.f_38036_.stream().map(ScheduleBuilder.ActivityTransition::m_38054_).collect(Collectors.toSet()).forEach(this.f_38035_::m_38024_);
      this.f_38036_.forEach((p_38044_) -> {
         Activity activity = p_38044_.m_38054_();
         this.f_38035_.m_38033_(activity).forEach((p_150245_) -> {
            p_150245_.m_38060_(p_38044_.m_38053_(), 0.0F);
         });
         this.f_38035_.m_38031_(activity).m_38060_(p_38044_.m_38053_(), 1.0F);
      });
      return this.f_38035_;
   }

   static class ActivityTransition {
      private final int f_38048_;
      private final Activity f_38049_;

      public ActivityTransition(int p_38051_, Activity p_38052_) {
         this.f_38048_ = p_38051_;
         this.f_38049_ = p_38052_;
      }

      public int m_38053_() {
         return this.f_38048_;
      }

      public Activity m_38054_() {
         return this.f_38049_;
      }
   }
}