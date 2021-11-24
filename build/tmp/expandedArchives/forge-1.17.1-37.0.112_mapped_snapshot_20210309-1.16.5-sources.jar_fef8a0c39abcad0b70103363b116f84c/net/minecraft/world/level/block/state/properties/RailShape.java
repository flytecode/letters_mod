package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum RailShape implements StringRepresentable {
   NORTH_SOUTH("north_south"),
   EAST_WEST("east_west"),
   ASCENDING_EAST("ascending_east"),
   ASCENDING_WEST("ascending_west"),
   ASCENDING_NORTH("ascending_north"),
   ASCENDING_SOUTH("ascending_south"),
   SOUTH_EAST("south_east"),
   SOUTH_WEST("south_west"),
   NORTH_WEST("north_west"),
   NORTH_EAST("north_east");

   private final String f_61737_;

   private RailShape(String p_61743_) {
      this.f_61737_ = p_61743_;
   }

   public String m_156038_() {
      return this.f_61737_;
   }

   public String toString() {
      return this.f_61737_;
   }

   public boolean m_61745_() {
      return this == ASCENDING_NORTH || this == ASCENDING_EAST || this == ASCENDING_SOUTH || this == ASCENDING_WEST;
   }

   public String m_7912_() {
      return this.f_61737_;
   }
}