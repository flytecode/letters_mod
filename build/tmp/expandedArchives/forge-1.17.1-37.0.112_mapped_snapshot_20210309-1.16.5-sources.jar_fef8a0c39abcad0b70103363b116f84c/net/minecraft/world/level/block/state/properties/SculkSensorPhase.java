package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum SculkSensorPhase implements StringRepresentable {
   INACTIVE("inactive"),
   ACTIVE("active"),
   COOLDOWN("cooldown");

   private final String f_156044_;

   private SculkSensorPhase(String p_156050_) {
      this.f_156044_ = p_156050_;
   }

   public String toString() {
      return this.f_156044_;
   }

   public String m_7912_() {
      return this.f_156044_;
   }
}