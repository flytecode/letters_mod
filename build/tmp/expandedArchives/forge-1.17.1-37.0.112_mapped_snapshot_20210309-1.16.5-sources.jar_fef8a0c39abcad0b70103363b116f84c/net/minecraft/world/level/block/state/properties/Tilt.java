package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum Tilt implements StringRepresentable {
   NONE("none", true),
   UNSTABLE("unstable", false),
   PARTIAL("partial", true),
   FULL("full", true);

   private final String f_156075_;
   private final boolean f_156076_;

   private Tilt(String p_156082_, boolean p_156083_) {
      this.f_156075_ = p_156082_;
      this.f_156076_ = p_156083_;
   }

   public String m_7912_() {
      return this.f_156075_;
   }

   public boolean m_156084_() {
      return this.f_156076_;
   }
}