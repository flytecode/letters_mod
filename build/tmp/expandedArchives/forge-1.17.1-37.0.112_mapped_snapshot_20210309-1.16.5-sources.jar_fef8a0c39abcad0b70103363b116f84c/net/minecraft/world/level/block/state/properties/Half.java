package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum Half implements StringRepresentable {
   TOP("top"),
   BOTTOM("bottom");

   private final String f_61609_;

   private Half(String p_61615_) {
      this.f_61609_ = p_61615_;
   }

   public String toString() {
      return this.f_61609_;
   }

   public String m_7912_() {
      return this.f_61609_;
   }
}