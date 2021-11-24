package net.minecraft.world.entity.schedule;

import net.minecraft.core.Registry;

public class Activity extends net.minecraftforge.registries.ForgeRegistryEntry<Activity> {
   public static final Activity f_37978_ = m_37999_("core");
   public static final Activity f_37979_ = m_37999_("idle");
   public static final Activity f_37980_ = m_37999_("work");
   public static final Activity f_37981_ = m_37999_("play");
   public static final Activity f_37982_ = m_37999_("rest");
   public static final Activity f_37983_ = m_37999_("meet");
   public static final Activity f_37984_ = m_37999_("panic");
   public static final Activity f_37985_ = m_37999_("raid");
   public static final Activity f_37986_ = m_37999_("pre_raid");
   public static final Activity f_37987_ = m_37999_("hide");
   public static final Activity f_37988_ = m_37999_("fight");
   public static final Activity f_37989_ = m_37999_("celebrate");
   public static final Activity f_37990_ = m_37999_("admire_item");
   public static final Activity f_37991_ = m_37999_("avoid");
   public static final Activity f_37992_ = m_37999_("ride");
   public static final Activity f_150238_ = m_37999_("play_dead");
   public static final Activity f_150239_ = m_37999_("long_jump");
   public static final Activity f_150240_ = m_37999_("ram");
   private final String f_37993_;
   private final int f_37994_;

   public Activity(String p_37997_) {
      this.f_37993_ = p_37997_;
      this.f_37994_ = p_37997_.hashCode();
   }

   public String m_37998_() {
      return this.f_37993_;
   }

   private static Activity m_37999_(String p_38000_) {
      return Registry.m_122961_(Registry.f_122874_, p_38000_, new Activity(p_38000_));
   }

   public boolean equals(Object p_38002_) {
      if (this == p_38002_) {
         return true;
      } else if (p_38002_ != null && this.getClass() == p_38002_.getClass()) {
         Activity activity = (Activity)p_38002_;
         return this.f_37993_.equals(activity.f_37993_);
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.f_37994_;
   }

   public String toString() {
      return this.m_37998_();
   }
}
