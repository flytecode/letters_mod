package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum PistonType implements StringRepresentable {
   DEFAULT("normal"),
   STICKY("sticky");

   private final String f_61674_;

   private PistonType(String p_61680_) {
      this.f_61674_ = p_61680_;
   }

   public String toString() {
      return this.f_61674_;
   }

   public String m_7912_() {
      return this.f_61674_;
   }
}