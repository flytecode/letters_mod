package net.minecraft.world.level.block.state.properties;

import net.minecraft.util.StringRepresentable;

public enum DoubleBlockHalf implements StringRepresentable {
   UPPER,
   LOWER;

   public String toString() {
      return this.m_7912_();
   }

   public String m_7912_() {
      return this == UPPER ? "upper" : "lower";
   }
}