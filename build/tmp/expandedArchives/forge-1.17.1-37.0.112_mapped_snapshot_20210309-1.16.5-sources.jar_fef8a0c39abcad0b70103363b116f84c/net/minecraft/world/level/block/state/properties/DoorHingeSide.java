package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum DoorHingeSide implements StringRepresentable {
   LEFT,
   RIGHT;

   public String toString() {
      return this.m_7912_();
   }

   public String m_7912_() {
      return this == LEFT ? "left" : "right";
   }
}