package net.minecraft.world.entity.ai.goal;

import java.util.EnumSet;

public abstract class Goal {
   private final EnumSet<Goal.Flag> f_25326_ = EnumSet.noneOf(Goal.Flag.class);

   public abstract boolean m_8036_();

   public boolean m_8045_() {
      return this.m_8036_();
   }

   public boolean m_6767_() {
      return true;
   }

   public void m_8056_() {
   }

   public void m_8041_() {
   }

   public void m_8037_() {
   }

   public void m_7021_(EnumSet<Goal.Flag> p_25328_) {
      this.f_25326_.clear();
      this.f_25326_.addAll(p_25328_);
   }

   public String toString() {
      return this.getClass().getSimpleName();
   }

   public EnumSet<Goal.Flag> m_7684_() {
      return this.f_25326_;
   }

   public static enum Flag {
      MOVE,
      LOOK,
      JUMP,
      TARGET;
   }
}