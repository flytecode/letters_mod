package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum StairsShape implements StringRepresentable {
   STRAIGHT("straight"),
   INNER_LEFT("inner_left"),
   INNER_RIGHT("inner_right"),
   OUTER_LEFT("outer_left"),
   OUTER_RIGHT("outer_right");

   private final String f_61786_;

   private StairsShape(String p_61792_) {
      this.f_61786_ = p_61792_;
   }

   public String toString() {
      return this.f_61786_;
   }

   public String m_7912_() {
      return this.f_61786_;
   }
}