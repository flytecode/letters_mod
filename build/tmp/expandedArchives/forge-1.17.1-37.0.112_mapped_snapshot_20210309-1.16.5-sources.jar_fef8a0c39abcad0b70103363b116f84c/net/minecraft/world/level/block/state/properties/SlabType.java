package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum SlabType implements StringRepresentable {
   TOP("top"),
   BOTTOM("bottom"),
   DOUBLE("double");

   private final String f_61769_;

   private SlabType(String p_61775_) {
      this.f_61769_ = p_61775_;
   }

   public String toString() {
      return this.f_61769_;
   }

   public String m_7912_() {
      return this.f_61769_;
   }
}